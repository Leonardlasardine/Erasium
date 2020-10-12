package fr.leonard.erasium;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ErasiumHelmet extends ArmorItem {
    public ErasiumHelmet() {
        super(ErasiumItem.ERASIUM_ARMOR.ERASIUM_ARMOR, EquipmentSlotType.HEAD, new Item.Properties().group(Erasium.ErasiumGroup.instance));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 20, 3, false, false));
    }
}
