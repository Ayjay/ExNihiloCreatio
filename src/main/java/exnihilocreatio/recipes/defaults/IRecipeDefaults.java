package exnihilocreatio.recipes.defaults;

import exnihilocreatio.registries.registries.*;

public interface IRecipeDefaults {
    default String getMODID() {return null;}

    default String getFileName() {return null;}

    default void registerCompost(CompostRegistry registry){}

    default void registerCrook(CrookRegistry registry){}

    default void registerSieve(SieveRegistry registry){}

    default void registerHammer(HammerRegistry registry){}

    default void registerHeat(HeatRegistry registry){}

    default void registerBarrelLiquidBlacklist(BarrelLiquidBlacklistRegistry registry){}

    default void registerFluidOnTop(FluidOnTopRegistry registry){}

    default void registerOreChunks(OreRegistry registry){}

    default void registerFluidTransform(FluidTransformRegistry registry){}

    default void registerFluidBlockTransform(FluidBlockTransformerRegistry registry){}

    default void registerFluidItemFluid(FluidItemFluidRegistry registry){}

    default void registerCrucibleStone(CrucibleRegistry registry){}

    default void registerCrucibleWood(CrucibleRegistry registry){}

    default void registerMilk(MilkEntityRegistry registry){}

}
