package fr.leonard.erasium;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("erasium")
public class Erasium {

    public static final String MODID = "erasium";

    public Erasium() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @MethodsReturnNonnullByDefault
    public static class ErasiumGroup extends ItemGroup {

        public static final ErasiumGroup instance = new ErasiumGroup(ItemGroup.GROUPS.length, "erasium");

        private ErasiumGroup(int index, String label){
            super(index, label);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ErasiumItem.erasium_ingot);
        }
    }
}
