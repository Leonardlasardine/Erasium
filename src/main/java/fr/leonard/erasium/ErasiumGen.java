package fr.leonard.erasium;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

//@Mod.EventBusSubscriber(modid = Erasium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ErasiumGen {
//TODO Attendre que la génération soit disponible en version 1.16.2 ou changer pour la 1.16.1
    /*@SubscribeEvent
    public static void generateOres (FMLLoadCompleteEvent completeEvent) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            for (biome.getCategory() == Biome.Category.THEEND) {

            }
        }
    }

    private static void genOre (Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType fillerBlockType, BlockState blockState, int size) {
        OreFeatureConfig oreFeatureConfig =  new OreFeatureConfig(RuleTest., blockState, size)
    }*/
}
