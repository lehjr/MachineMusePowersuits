package net.machinemuse.powersuits.common.fluid;

import net.machinemuse.powersuits.common.constants.MPSModConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidNitrogen extends Fluid {
    public static final String name = "liquid_nitrogen";

    // Note: Naming convention seems a bit weird, but fluids are handled a bit differently.
    public LiquidNitrogen() {
        super(name, new ResourceLocation(MPSModConstants.MODID, "fluids/" + name + "_still"), new ResourceLocation(MPSModConstants.MODID, "fluids/" + name + "_flow"));
        setTemperature(70);
        setViscosity(200);
    }
}
