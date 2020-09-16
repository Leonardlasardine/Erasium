package fr.leonard.erasium;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Erasium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Erasium.MODID)
public class ErasiumBlock {

    public static final Block erasium_ore = null, erasium_block = null;

    @SubscribeEvent
    public static void registerBlock(final RegistryEvent.Register<Block> e) {
        e.getRegistry().register(new ErasiumOre());
        e.getRegistry().register(new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(3)).setRegistryName("erasium_block"));
    }

    @SubscribeEvent
    public static void registerBlockItem(final RegistryEvent.Register<Item> e) {
        e.getRegistry().register(new BlockItem(erasium_ore, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_ore"));
        e.getRegistry().register(new BlockItem(erasium_block, new Item.Properties().group(Erasium.ErasiumGroup.instance)).setRegistryName("erasium_block"));
    }
}
