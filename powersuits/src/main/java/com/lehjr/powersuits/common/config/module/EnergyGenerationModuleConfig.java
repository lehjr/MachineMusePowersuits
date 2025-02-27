package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnergyGenerationModuleConfig {
    // Combustion Generators ----------------------------------------------------------------------
    private static final ModConfigSpec.Builder COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER = new ModConfigSpec.Builder().push("Energy_Generation").push("Combustion_Generators").push("Combustion_Energy_Generator_Module_1");
    private static final ModConfigSpec.BooleanValue COMBUSTION_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder COMBUSTION_ENERGY_GENERATOR_MODULE_2__BUILDER = COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Combustion_Energy_Generator_Module_2");
    private static final ModConfigSpec.BooleanValue COMBUSTION_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = COMBUSTION_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);


    // Heat Generators ----------------------------------------------------------------------------
    private static final ModConfigSpec.Builder HEAT_ENERGY_GENERATOR_MODULE_1__BUILDER = COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().pop().push("Heat_Generators").push("Heat_Energy_Generator_Module_1");
    private static final ModConfigSpec.BooleanValue HEAT_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = HEAT_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder HEAT_ENERGY_GENERATOR_MODULE_2__BUILDER = HEAT_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Heat_Energy_Generator_Module_2");
    private static final ModConfigSpec.BooleanValue HEAT_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = HEAT_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Kinetic Generators -------------------------------------------------------------------------
    private static final ModConfigSpec.Builder KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER = HEAT_ENERGY_GENERATOR_MODULE_2__BUILDER.pop().pop().push("Kinetic_Generators").push("Kinetic_Energy_Generator_Module_1");
    private static final ModConfigSpec.BooleanValue KINETIC_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER = KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Kinetic_Energy_Generator_Module_2");
    private static final ModConfigSpec.BooleanValue KINETIC_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Solar Generators ---------------------------------------------------------------------------
    private static final ModConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER = KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER.pop().pop().push("Solar_Generators").push("Solar_Energy_Generator_Module_1");
    private static final ModConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Solar_Energy_Generator_Module_2");
    private static final ModConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.pop().pop().push("Solar_Energy_Generator_Module_3");
    private static final ModConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_3__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.pop().push("Solar_Energy_Generator_Module_4");
    private static final ModConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_4__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    public static final ModConfigSpec MPS_GENERATOR_MODULE_SPEC = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.build();


    // Combustion Generators

    // Heat Generators

    // Kinetic Generators

    // Solar Generators




    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_GENERATOR_MODULE_SPEC) {






        }
    }
}
