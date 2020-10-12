package fr.leonard.erasium;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ErasiumStick extends Item {
    public ErasiumStick(Properties group) {
        super(new Item.Properties().group(Erasium.ErasiumGroup.instance));
    }


    /**
     * Copie de ErasiumSnowBall
     */
    public @NotNull ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @NotNull Hand handIn) {

        //Give
        playerIn.addItemStackToInventory(new ItemStack(ErasiumItem.ERASIUM_STICK.get()));

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            ErasiumSnowballEntity entity = new ErasiumSnowballEntity(worldIn, playerIn);
            entity.setItem(itemstack);
            entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(entity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        return ActionResult.resultSuccess(itemstack);
    }
}

