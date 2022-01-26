package fr.leonard.erasium;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Erasium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ErasiumGen {

    public static OreFeatureConfig.FillerBlockType END_STONE = OreFeatureConfig.FillerBlockType.create("END_STONE", "end_stone", new BlockMatcher(Blocks.END_STONE));

    @SubscribeEvent
    public static void generateOres (FMLLoadCompleteEvent completeEvent) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.NETHER) {

            }
            if (biome.getCategory() == Biome.Category.THEEND) {
                genOre(biome, 4, 4, 5, 150, END_STONE, ErasiumBlock.ERASIUM_ORE.get().getDefaultState(), 12);
            }
            else {
                genOre(biome, 4, 4, 5, 30, OreFeatureConfig.FillerBlockType.NATURAL_STONE, ErasiumBlock.ERASIUM_ORE.get().getDefaultState(), 12);
            }
        }
    }

    private static void genOre (Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType fillerBlockType, BlockState blockState, int size) {
        CountRangeConfig countRangeConfig = new CountRangeConfig(count, bottomOffset, topOffset, max);
        OreFeatureConfig featureConfig = new OreFeatureConfig(fillerBlockType, blockState, size);
        ConfiguredPlacement configuredPlacement = Placement.COUNT_RANGE.configure(countRangeConfig);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(featureConfig).withPlacement(configuredPlacement));
    }
}
