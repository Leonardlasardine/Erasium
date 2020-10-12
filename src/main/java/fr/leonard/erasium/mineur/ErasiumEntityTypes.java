package fr.leonard.erasium.mineur;

import fr.leonard.erasium.Erasium;
import fr.leonard.erasium.ErasiumBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ErasiumEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Erasium.MODID);

    public static final RegistryObject<TileEntityType<QuarryTileEntity>> ERASIUM_QUARRY
            = TILE_ENTITY_TYPES.register("erasium_quarry", () -> TileEntityType.Builder.create(QuarryTileEntity::new, ErasiumBlock.ERASIUM_QUARRY.get()).build(null));
}
