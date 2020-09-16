package fr.leonard.erasium;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ErasiumOre extends OreBlock {
    public ErasiumOre() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(3));
        setRegistryName("erasium_ore");
    }
}
