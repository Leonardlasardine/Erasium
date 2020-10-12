package fr.leonard.erasium;

import fr.leonard.erasium.quarry.ErasiumQuarry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ErasiumBlock {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Erasium.MODID);


    public static final RegistryObject<Block> ERASIUM_ORE = BLOCKS.register("erasium_ore", ErasiumOre::new);

    public static final RegistryObject<Block> ERASIUM_BLOCK = BLOCKS.register("erasium_block",
            () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 6.0f)
                    .harvestLevel(3).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).lightValue(4)));

    public static final RegistryObject<Block> ERASIUM_QUARRY = BLOCKS.register(
            "erasium_quarry", () -> new ErasiumQuarry(Block.Properties.create(Material.IRON)));
}
