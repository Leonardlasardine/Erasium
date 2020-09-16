package fr.leonard.erasium;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Erasium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Erasium.MODID)
public class ErasiumItem {

    public static final Item erasium_ingot = null, erasium_sword = null, erasium_pickaxe = null, erasium_axe = null, erasium_shovel = null, erasium_hoe = null,
            erasium_helmet = null, erasium_chestplate = null, erasium_leggings = null, erasium_boots = null, erasium_snwoball = null, erasium_stick = null;

    @SubscribeEvent
    public static void registerItems (final RegistryEvent.Register <Item> e) {
        e.getRegistry().register(new Item(new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_ingot"));
        e.getRegistry().register(new ErasiumSnowball());
        e.getRegistry().register(new ErasiumStick());

        e.getRegistry().register(new SwordItem(ERASIUM_TOOL.ERASIUM_TOOL, 5, 8.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_sword"));
        e.getRegistry().register(new PickaxeItem(ERASIUM_TOOL.ERASIUM_TOOL, 3, 10.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_pickaxe"));
        e.getRegistry().register(new AxeItem(ERASIUM_TOOL.ERASIUM_TOOL, 5, 3.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_axe"));
        e.getRegistry().register(new ShovelItem(ERASIUM_TOOL.ERASIUM_TOOL, 2, 6.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_shovel"));
        e.getRegistry().register(new HoeItem(ERASIUM_TOOL.ERASIUM_TOOL, 2, 4.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_hoe"));

        e.getRegistry().register(new ArmorItem(ERASIUM_ARMOR.ERASIUM_ARMOR, EquipmentSlotType.HEAD, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_helmet"));
        e.getRegistry().register(new ArmorItem(ERASIUM_ARMOR.ERASIUM_ARMOR, EquipmentSlotType.CHEST, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_chestplate"));
        e.getRegistry().register(new ArmorItem(ERASIUM_ARMOR.ERASIUM_ARMOR, EquipmentSlotType.LEGS, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_leggings"));
        e.getRegistry().register(new ArmorItem(ERASIUM_ARMOR.ERASIUM_ARMOR, EquipmentSlotType.FEET, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_boots"));
    }

    public enum ERASIUM_TOOL implements IItemTier {

        ERASIUM_TOOL(5, 2512, 10.0F, 5.0F, 8.0F, 25, () -> { return Ingredient.fromItems(ErasiumItem.erasium_ingot);});

        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final float attackSpeed;
        private final int enchantability;
        private final LazyValue<Ingredient> repairMaterial;

        ERASIUM_TOOL(int harvestLevel, int maxUses, float efficiency, float attackDamage, float attackSpeed, int enchantability, Supplier<Ingredient> repairMaterial) {
            this.harvestLevel = harvestLevel;
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.enchantability = enchantability;
            this.repairMaterial = new LazyValue<>(repairMaterial);
        }

        @Override
        public int getMaxUses() {
            return maxUses;
        }

        @Override
        public float getEfficiency() {
            return efficiency;
        }

        @Override
        public float getAttackDamage() {
            return attackDamage;
        }

        @Override
        public int getHarvestLevel() {
            return harvestLevel;
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }


        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.fromItems(ErasiumItem.erasium_ingot);
        }
    }

    public enum ERASIUM_ARMOR implements IArmorMaterial {

        ERASIUM_ARMOR(Erasium.MODID + ":armor", new int[] {4, 7, 9, 4},2000, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F,() -> { return Ingredient.fromItems(ErasiumItem.erasium_ingot);});

        private final String name;
        private final int[] damageReductionAmount;
        private final int durability;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final LazyValue<Ingredient> repairMaterial;

        ERASIUM_ARMOR(String name, int[] damageReductionAmount, int durability, int enchantability, SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
            this.name = name;
            this.damageReductionAmount = damageReductionAmount;
            this.durability = durability;
            this.enchantability = enchantability;
            this.soundEvent = soundEvent;
            this.toughness = toughness;
            this.repairMaterial = new LazyValue<>(repairMaterial);
        }

        @Override
        @ParametersAreNonnullByDefault
        public int getDurability(EquipmentSlotType slotIn) {
            return durability;
        }

        @Override
        @ParametersAreNonnullByDefault
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return damageReductionAmount[slotIn.getIndex()];
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Override
        @Nonnull
        public SoundEvent getSoundEvent() {
            return soundEvent;
        }

        @Override
        @Nonnull
        public Ingredient getRepairMaterial() {
            return Ingredient.fromItems(ErasiumItem.erasium_ingot);
        }

        @Override
        @Nonnull
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float func_230304_f_() {
            return 0;
        }
    }
}
