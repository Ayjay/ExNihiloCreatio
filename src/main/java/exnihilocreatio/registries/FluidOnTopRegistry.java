package exnihilocreatio.registries;

import exnihilocreatio.registries.manager.ExNihiloRegistryManager;
import exnihilocreatio.util.IStackInfo;
import net.minecraftforge.fluids.Fluid;

@Deprecated
public class FluidOnTopRegistry {

    public static boolean isValidRecipe(Fluid fluidInBarrel, Fluid fluidOnTop) {
        return ExNihiloRegistryManager.FLUID_ON_TOP_REGISTRY.isValidRecipe(fluidInBarrel, fluidOnTop);
    }

    public static IStackInfo getTransformedBlock(Fluid fluidInBarrel, Fluid fluidOnTop) {
        return ExNihiloRegistryManager.FLUID_ON_TOP_REGISTRY.getTransformedBlock(fluidInBarrel, fluidOnTop);
    }

    public void register(Fluid fluidInBarrel, Fluid fluidOnTop, IStackInfo result) {
        ExNihiloRegistryManager.FLUID_ON_TOP_REGISTRY.register(fluidInBarrel, fluidOnTop, result);
    }
}
