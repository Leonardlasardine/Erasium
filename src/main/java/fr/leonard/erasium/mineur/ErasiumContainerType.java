package fr.leonard.erasium.mineur;

import fr.leonard.erasium.Erasium;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ErasiumContainerType {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Erasium.MODID);

    public static  final RegistryObject<ContainerType<QuarryContainer>> QUARRY_CONTAINER = CONTAINER_TYPES.register("erasium_quarry", () -> IForgeContainerType.create(QuarryContainer::new));
}
