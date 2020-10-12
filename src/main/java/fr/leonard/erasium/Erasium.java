package fr.leonard.erasium;

import fr.leonard.erasium.quarry.ErasiumContainerType;
import fr.leonard.erasium.quarry.ErasiumEntityTypes;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("erasium")
public class Erasium {

    public static final String MODID = "erasium";

    public Erasium() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ErasiumItem.ITEMS.register(modEventBus);
        ErasiumBlock.BLOCKS.register(modEventBus);
        ErasiumEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ErasiumContainerType.CONTAINER_TYPES.register(modEventBus);

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
            return new ItemStack(ErasiumItem.ERASIUM_INGOT.get());
        }
    }
}
