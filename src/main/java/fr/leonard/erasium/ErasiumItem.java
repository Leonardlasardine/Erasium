package fr.leonard.erasium;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

public class ErasiumItem {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Erasium.MODID);


    public static final RegistryObject<Item> ERASIUM_INGOT = ITEMS.register("erasium_ingot", () -> new Item(new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<ErasiumSnowball> ERASIUM_SNOWBALL = ITEMS.register("erasium_snowball",
            () -> new ErasiumSnowball(new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<ErasiumStick> ERASIUM_STICK= ITEMS.register("erasium_stick",
            () -> new ErasiumStick(new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<SwordItem> ERASIUM_SWORD = ITEMS.register("erasium_sword",
            () -> new SwordItem(ERASIUM_TOOL.ERASIUM_TOOL, 5, 8.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<PickaxeItem> ERASIUM_PICKAXE = ITEMS.register("erasium_pickaxe",
            () -> new PickaxeItem(ERASIUM_TOOL.ERASIUM_TOOL, 3, 10.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<AxeItem> ERASIUM_AXE = ITEMS.register("erasium_axe",
            () -> new AxeItem(ERASIUM_TOOL.ERASIUM_TOOL, 5, 3.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<ShovelItem> ERASIUM_SHOVEL = ITEMS.register("erasium_shovel",
            () -> new ShovelItem(ERASIUM_TOOL.ERASIUM_TOOL, 2, 6.0F, new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<HoeItem> ERASIUM_HOE = ITEMS.register("erasium_hoe",
            () -> new HoeItem(ERASIUM_TOOL.ERASIUM_TOOL, 2, new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<ErasiumHelmet> ERASIUM_HELMET = ITEMS.register("erasium_helmet",
            ErasiumHelmet::new);

    public static final RegistryObject<ArmorItem> ERASIUM_CHESTPLATE = ITEMS.register("erasium_chestplate",
            () -> new ArmorItem(ERASIUM_ARMOR.ERASIUM_ARMOR, EquipmentSlotType.CHEST, new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<ArmorItem> ERASIUM_LEGGINGS = ITEMS.register("erasium_leggings",
            () -> new ArmorItem(ERASIUM_ARMOR.ERASIUM_ARMOR, EquipmentSlotType.LEGS, new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<ArmorItem> ERASIUM_BOOTS = ITEMS.register("erasium_boots",
            () -> new ArmorItem(ERASIUM_ARMOR.ERASIUM_ARMOR, EquipmentSlotType.FEET, new Item.Properties().group(Erasium.ErasiumGroup.instance)));





    public static final RegistryObject<BlockItem> ERASIUM_ORE =
            ITEMS.register("erasium_ore", () -> new BlockItem(ErasiumBlock.ERASIUM_ORE.get(), new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<BlockItem> ERASIUM_BLOCK =
            ITEMS.register("erasium_block", () -> new BlockItem(ErasiumBlock.ERASIUM_BLOCK.get(), new Item.Properties().group(Erasium.ErasiumGroup.instance)));

    public static final RegistryObject<BlockItem> ERASIUM_QUARRY =
            ITEMS.register("erasium_quarry", () -> new BlockItem(ErasiumBlock.ERASIUM_QUARRY.get(), new Item.Properties().group(Erasium.ErasiumGroup.instance)));


    public enum ERASIUM_TOOL implements IItemTier {

        ERASIUM_TOOL(5, 2512, 10.0F, 7.0F, 16.0F, 25, () -> { return Ingredient.fromItems(ErasiumItem.ERASIUM_INGOT.get());});

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
            return Ingredient.fromItems(ErasiumItem.ERASIUM_INGOT.get());
        }
    }

    public enum ERASIUM_ARMOR implements IArmorMaterial {

        ERASIUM_ARMOR(Erasium.MODID + ":armor", new int[] {5, 8, 10, 5},2000, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F,() -> { return Ingredient.fromItems(ErasiumItem.ERASIUM_INGOT.get());});

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
            return Ingredient.fromItems(ErasiumItem.ERASIUM_INGOT.get());
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
    }
}
