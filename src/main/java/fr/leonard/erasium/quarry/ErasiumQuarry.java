package fr.leonard.erasium.quarry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ErasiumQuarry extends Block {

    public ErasiumQuarry(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ErasiumEntityTypes.ERASIUM_QUARRY.get().create();
    }

    @Override
    public @NotNull ActionResultType onBlockActivated(@NotNull BlockState state, World worldIn, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand handIn, @NotNull BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof QuarryTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity)player, (QuarryTileEntity) tileEntity, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void onReplaced(BlockState state, @NotNull World worldIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof QuarryTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((QuarryTileEntity) tileEntity).getItems());
            }
        }
    }

    /**
     * Called when fire is updating, checks if a block face can catch fire.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @param face  The face that the fire is coming from
     * @return True if the face can be on fire, false otherwise.
     */
    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    /**
     * Determines if a specified mob type can spawn on this block, returning false will
     * prevent any mob from spawning on the block.
     *
     * @param state      The current state
     * @param world      The current world
     * @param pos        Block position in world
     * @param type       The Mob Category Type
     * @param entityType The Entity type
     * @return True to allow a mob of the specified category to spawn, false to prevent it.
     */
    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
    }
}
