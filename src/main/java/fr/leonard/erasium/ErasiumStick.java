package fr.leonard.erasium;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ErasiumStick extends Item {
    public ErasiumStick() {
        super(new Item.Properties().group(Erasium.ErasiumGroup.instance));
        setRegistryName("erasium_stick");
    }


    /**
     * Copie de ErasiumSnowBall
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

            ItemStack itemstack = playerIn.getHeldItem(handIn);
            playerIn.inventory.addItemStackToInventory(new ItemStack(ErasiumItem.erasium_stick));
        worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            if (!worldIn.isRemote) {
                ErasiumSnowballEntity snowballentity = new ErasiumSnowballEntity(worldIn, playerIn);
                snowballentity.setItem(itemstack);
                snowballentity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
                worldIn.addEntity(snowballentity);
            }

            playerIn.addStat(Stats.ITEM_USED.get(this));
            if (!playerIn.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }
}

