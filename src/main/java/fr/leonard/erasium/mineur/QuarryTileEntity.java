package fr.leonard.erasium.mineur;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.stream.IntStream;

public class QuarryTileEntity extends LockableLootTileEntity implements ITickableTileEntity {

    public static int x, y, z, tick;
    boolean initialized = false;
    public static int radius = 3;
    public static boolean dropBlock = false;
    boolean isChestFull = false;
    public static boolean isActive = false;

    private NonNullList<ItemStack> chestContents = NonNullList.withSize(36, ItemStack.EMPTY);
    protected int numPlayersUsing;
    private final IItemHandlerModifiable items = createHandler();
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

    @Override
    public void tick() {
        if (!initialized) {
            init();
        }
        if (isActive) { //1 seconde par block
            tick++;
            if (tick == radius * 40) {
                tick = 0;
                if (y > 0) {
                    execute();
                }
            }
        }
        assert world != null;
        if (world.isRemote()) {
            if (isActive) {
                QuarryScreen.onOff = "On";
            } else {
                QuarryScreen.onOff = "Off";
            }
            if (dropBlock) {
                QuarryScreen.stock = "Drop";
            } else {
                QuarryScreen.stock = "Stock";
            }
        }
    }

    private void init() {
        initialized = true;
        x = this.pos.getX() + 1;
        y = this.pos.getY() - 1;
        z = this.pos.getZ();
        tick = 0;
    }

    private void execute() {
        for (int x1 = 0; x1 < radius; x1++) {
            for (int z1 = 0; z1 < radius; z1++) {
                Block blocksRemoved;
                BlockPos posToBreak = new BlockPos(x + x1, y, z + z1);
                assert this.world != null;
                blocksRemoved = this.world.getBlockState(posToBreak).getBlock();
                destroyBlock(posToBreak, dropBlock, blocksRemoved);
            }
        }
        y--;
    }

    private void destroyBlock(BlockPos pos, boolean dropBlock, Block blockRemoved) {
        assert world != null;
        BlockState blockstate = world.getBlockState(pos);
        if (!blockstate.isAir(world, pos) && blockstate.getBlock() != Blocks.BEDROCK) {
            IFluidState ifluidstate = world.getFluidState(pos); //I don't know why, I have see on a tutorial
            //FMLClientHandler.instance().getServer().worldServerForDimension(0).destroyBlock(x, y, z, false);
            //if (!world.isRemote()) { // Doesn't work
            world.playEvent(2001, pos, Block.getStateId(blockstate));
                world.removeBlock(pos, false);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.removeBlock(pos, false);
                world.destroyBlock(pos, false);
// Only Solo Worlds Objects.requireNonNull(Minecraft.getInstance().getIntegratedServer()).getWorld(DimensionType.OVERWORLD).destroyBlock(new BlockPos(x, y, z), true);
            //}
            TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
            if (dropBlock) {
                Block.spawnDrops(blockstate, world, this.pos.add(0, 1.5, 0), tileentity, null, ItemStack.EMPTY);
            } else if (!isChestFull) {
                for (int i = 0; i < 36; i++) {
                    //Coffre plein
                    for (int j = 0; j < 36; j++) {
                        if (getStackInSlot(j).isEmpty()) {
                            break;
                        }
                        if (j == 35) {
                            assert Minecraft.getInstance().player != null;
                            sendMessage();
                            isChestFull = true;
                            break;
                        }
                    }
                    ItemStack theStack = new ItemStack(blockstate.getBlock().asItem());
                    if (getStackInSlot(i).isEmpty()) {
                        setInventorySlotContents(i, new ItemStack(blockRemoved.asItem()));
                        break;
                    } else if (getStackInSlot(i).isItemEqual(theStack) && getStackInSlot(i).getCount() < 64) {
                        setInventorySlotContents(i, new ItemStack(blockRemoved.asItem(), getStackInSlot(i).getCount() + 1));
                        break;
                    } else if (getStackInSlot(i + 1).isEmpty()) {
                        setInventorySlotContents(i + 1, new ItemStack(blockRemoved.asItem()));
                        break;
                    }
                }
            }
            world.setBlockState(pos, ifluidstate.getBlockState(), 3);
        }
    }

    public QuarryTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public QuarryTileEntity() {
        this(ErasiumEntityTypes.ERASIUM_QUARRY.get());
    }

    @Override
    public int getSizeInventory() {
        return 36;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    public void setItems(@NotNull NonNullList<ItemStack> itemsIn) {
        this.chestContents = itemsIn;
    }

    @Override
    protected @NotNull ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.erasium_quarry");
    }

    @Override
    protected @NotNull Container createMenu(int id, @NotNull PlayerInventory player) {
        return new QuarryContainer(id, player, this);
    }

    @Override
    public void setInventorySlotContents(int index, @NotNull ItemStack stack) {
        super.setInventorySlotContents(index, stack);
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int index) {
        return super.getStackInSlot(index);
    }

    @Override
    public @NotNull CompoundNBT write(@NotNull CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.chestContents);
        }

        compound.put("values", NBTHelper.toNBT(this));
        return compound;
    }

    @Override
    public void read(@NotNull CompoundNBT compound) {
        super.read(compound);
        CompoundNBT values = compound.getCompound("values");
        x = values.getInt("x");
        y = values.getInt("y");
        z = values.getInt("z");
        tick = 0;
        initialized = true;
        isActive = values.getBoolean("isActive");
        isChestFull = values.getBoolean("isChestFull");

        this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.chestContents);
        }
    }

    private void playSound(SoundEvent sound) {
        double dx = (double) this.pos.getX() + 0.5D;
        double dy = (double) this.pos.getY() + 0.5D;
        double dz = (double) this.pos.getZ() + 0.5D;
        assert this.world != null;
        this.world.playSound(null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f,
                this.world.rand.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.receiveClientEvent(id, type);
        }
    }

    @Override
    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof ErasiumQuarry) {
            assert this.world != null;
            this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, block);
        }
    }

    public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
        BlockState blockstate = reader.getBlockState(pos);
        if (blockstate.hasTileEntity()) {
            TileEntity tileentity = reader.getTileEntity(pos);
            if (tileentity instanceof QuarryTileEntity) {
                return ((QuarryTileEntity) tileentity).numPlayersUsing;
            }
        }
        return 0;
    }


    private static IntStream func_213972_a(IInventory p_213972_0_, Direction p_213972_1_) {
        return p_213972_0_ instanceof ISidedInventory ? IntStream.of(((ISidedInventory) p_213972_0_).getSlotsForFace(p_213972_1_)) : IntStream.range(0, p_213972_0_.getSizeInventory());
    }

    /**
     * Returns false if the inventory has any room to place items in
     */
    private boolean isInventoryFull(IInventory inventoryIn, Direction side) {
        return func_213972_a(inventoryIn, side).allMatch((p_213970_1_) -> {
            ItemStack itemstack = inventoryIn.getStackInSlot(p_213970_1_);
            return itemstack.getCount() >= itemstack.getMaxStackSize();
        });
    }

    /**
     * Returns false if the specified IInventory contains any items
     */
    private static boolean isInventoryEmpty(IInventory inventoryIn, Direction side) {
        return func_213972_a(inventoryIn, side).allMatch((p_213973_1_) -> inventoryIn.getStackInSlot(p_213973_1_).isEmpty());
    }


    public static void swapContents(QuarryTileEntity te, QuarryTileEntity otherTe) {
        NonNullList<ItemStack> list = te.getItems();
        te.setItems(otherTe.getItems());
        otherTe.setItems(list);
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if (this.itemHandler != null) {
            this.itemHandler.invalidate();
            this.itemHandler = null;
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void remove() {
        super.remove();
        if (itemHandler != null) {
            itemHandler.invalidate();
        }
        isActive = false;
        isChestFull = false;
        x = 0;
        y = 0;
        z = 0;
        tick = 0;
        radius = 3;
        QuarryScreen.onOff = "Off";
    }

    @OnlyIn(Dist.CLIENT)
    private static void sendMessage() {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendChatMessage("Mineur Plein");
        }
    }
}
