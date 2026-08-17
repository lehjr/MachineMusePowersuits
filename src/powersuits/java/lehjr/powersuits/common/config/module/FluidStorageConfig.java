package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class FluidStorageConfig {
    private static final ModConfigSpec.Builder FLUID_STORAGE_MODULE_BUILDER = new ModConfigSpec.Builder().push("FluidStorage");

    // Coolant Tank
    private static final ModConfigSpec.Builder COOLANT_TANK_MODULE_1__BUILDER = FLUID_STORAGE_MODULE_BUILDER.push("Coolant_Tank_1");
    private static final ModConfigSpec.BooleanValue COOLANT_TANK_MODULE_1__IS_ALLOWED = COOLANT_TANK_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue COOLANT_TANK_MODULE_1_CAPACITY = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 1000, 1000, 100000);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_1__HEAT_ACTIVATION_PERCENT_BASE  = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_BASE , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_1__HEAT_ACTIVATION_PERCENT_MULTIPLIER  = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_MULTIPLIER , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_1__ENERGY_CONSUMPTION_BASE  = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE , 1, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_1__ENERGY_CONSUMPTION_MULTIPLIER  = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_MULTIPLIER , 40, 0, 1000000D);

    private static final ModConfigSpec.Builder COOLANT_TANK_MODULE_2__BUILDER = COOLANT_TANK_MODULE_1__BUILDER.pop().push("Coolant_Tank_2");
    private static final ModConfigSpec.BooleanValue COOLANT_TANK_MODULE_2__IS_ALLOWED = COOLANT_TANK_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue COOLANT_TANK_MODULE_2_CAPACITY = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 2000, 1000, 100000);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_2__HEAT_ACTIVATION_PERCENT_BASE  = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_BASE , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_2__HEAT_ACTIVATION_PERCENT_MULTIPLIER  = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_MULTIPLIER , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_2__ENERGY_CONSUMPTION_BASE  = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE , 1, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_2__ENERGY_CONSUMPTION_MULTIPLIER  = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_MULTIPLIER , 40, 0, 1000000D);

    private static final ModConfigSpec.Builder COOLANT_TANK_MODULE_3__BUILDER = COOLANT_TANK_MODULE_2__BUILDER.pop().push("Coolant_Tank_3");
    private static final ModConfigSpec.BooleanValue COOLANT_TANK_MODULE_3__IS_ALLOWED = COOLANT_TANK_MODULE_3__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue COOLANT_TANK_MODULE_3_CAPACITY = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 3000, 1000, 100000);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_3__HEAT_ACTIVATION_PERCENT_BASE  = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_BASE , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_3__HEAT_ACTIVATION_PERCENT_MULTIPLIER  = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_MULTIPLIER , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_3__ENERGY_CONSUMPTION_BASE  = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE , 1, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_3__ENERGY_CONSUMPTION_MULTIPLIER  = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_MULTIPLIER , 40, 0, 1000000D);

    private static final ModConfigSpec.Builder COOLANT_TANK_MODULE_4__BUILDER = COOLANT_TANK_MODULE_3__BUILDER.pop().push("Coolant_Tank_4");
    private static final ModConfigSpec.BooleanValue COOLANT_TANK_MODULE_4__IS_ALLOWED = COOLANT_TANK_MODULE_4__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue COOLANT_TANK_MODULE_4_CAPACITY = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 4000, 1000, 100000);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_4__HEAT_ACTIVATION_PERCENT_BASE  = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_BASE , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_4__HEAT_ACTIVATION_PERCENT_MULTIPLIER  = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_MULTIPLIER , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_4__ENERGY_CONSUMPTION_BASE  = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE , 1, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_4__ENERGY_CONSUMPTION_MULTIPLIER  = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_MULTIPLIER , 40, 0, 1000000D);

    public static final ModConfigSpec FLUID_STORAGE_MODULE_SPEC = COOLANT_TANK_MODULE_4__BUILDER.build();

    // Coolant Tank
    public static boolean coolantTankModuleIsAllowed1;
    public static int coolantTankModuleCapacity1;
    public static double coolantTankModuleHeatActivationPercentBase1;
    public static double coolantTankModuleHeatActivationPercentMultiplier1;
    public static double coolantTankModuleEnergyConsumptionBase1;
    public static double coolantTankModuleEnergyConsumptionBMultiplier1;

    public static boolean coolantTankModuleIsAllowed2;
    public static int coolantTankModuleCapacity2;
    public static double coolantTankModuleHeatActivationPercentBase2;
    public static double coolantTankModuleHeatActivationPercentMultiplier2;
    public static double coolantTankModuleEnergyConsumptionBase2;
    public static double coolantTankModuleEnergyConsumptionBMultiplier2;

    public static boolean coolantTankModuleIsAllowed3;
    public static int coolantTankModuleCapacity3;
    public static double coolantTankModuleHeatActivationPercentBase3;
    public static double coolantTankModuleHeatActivationPercentMultiplier3;
    public static double coolantTankModuleEnergyConsumptionBase3;
    public static double coolantTankModuleEnergyConsumptionBMultiplier3;

    public static boolean coolantTankModuleIsAllowed4;
    public static int coolantTankModuleCapacity4;
    public static double coolantTankModuleHeatActivationPercentBase4;
    public static double coolantTankModuleHeatActivationPercentMultiplier4;
    public static double coolantTankModuleEnergyConsumptionBase4;
    public static double coolantTankModuleEnergyConsumptionBMultiplier4;

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == FLUID_STORAGE_MODULE_SPEC) {

            // Coolant Tank
            coolantTankModuleIsAllowed1 = COOLANT_TANK_MODULE_1__IS_ALLOWED.get();
            coolantTankModuleCapacity1 = COOLANT_TANK_MODULE_1_CAPACITY.get();
            coolantTankModuleHeatActivationPercentBase1 = COOLANT_TANK_MODULE_1__HEAT_ACTIVATION_PERCENT_BASE.get();
            coolantTankModuleHeatActivationPercentMultiplier1 = COOLANT_TANK_MODULE_1__HEAT_ACTIVATION_PERCENT_MULTIPLIER.get();
            coolantTankModuleEnergyConsumptionBase1 = COOLANT_TANK_MODULE_1__ENERGY_CONSUMPTION_BASE.get();
            coolantTankModuleEnergyConsumptionBMultiplier1 = COOLANT_TANK_MODULE_1__ENERGY_CONSUMPTION_MULTIPLIER.get();

            coolantTankModuleIsAllowed2 = COOLANT_TANK_MODULE_2__IS_ALLOWED.get();
            coolantTankModuleCapacity2 = COOLANT_TANK_MODULE_2_CAPACITY.get();
            coolantTankModuleHeatActivationPercentBase2 = COOLANT_TANK_MODULE_2__HEAT_ACTIVATION_PERCENT_BASE.get();
            coolantTankModuleHeatActivationPercentMultiplier2 = COOLANT_TANK_MODULE_2__HEAT_ACTIVATION_PERCENT_MULTIPLIER.get();
            coolantTankModuleEnergyConsumptionBase2 = COOLANT_TANK_MODULE_2__ENERGY_CONSUMPTION_BASE.get();
            coolantTankModuleEnergyConsumptionBMultiplier2 = COOLANT_TANK_MODULE_2__ENERGY_CONSUMPTION_MULTIPLIER.get();

            coolantTankModuleIsAllowed3 = COOLANT_TANK_MODULE_3__IS_ALLOWED.get();
            coolantTankModuleCapacity3 = COOLANT_TANK_MODULE_3_CAPACITY.get();
            coolantTankModuleHeatActivationPercentBase3 = COOLANT_TANK_MODULE_3__HEAT_ACTIVATION_PERCENT_BASE.get();
            coolantTankModuleHeatActivationPercentMultiplier3 = COOLANT_TANK_MODULE_3__HEAT_ACTIVATION_PERCENT_MULTIPLIER.get();
            coolantTankModuleEnergyConsumptionBase3 = COOLANT_TANK_MODULE_3__ENERGY_CONSUMPTION_BASE.get();
            coolantTankModuleEnergyConsumptionBMultiplier3 = COOLANT_TANK_MODULE_3__ENERGY_CONSUMPTION_MULTIPLIER.get();

            coolantTankModuleIsAllowed4 = COOLANT_TANK_MODULE_4__IS_ALLOWED.get();
            coolantTankModuleCapacity4 = COOLANT_TANK_MODULE_4_CAPACITY.get();
            coolantTankModuleHeatActivationPercentBase4 = COOLANT_TANK_MODULE_4__HEAT_ACTIVATION_PERCENT_BASE.get();
            coolantTankModuleHeatActivationPercentMultiplier4 = COOLANT_TANK_MODULE_4__HEAT_ACTIVATION_PERCENT_MULTIPLIER.get();
            coolantTankModuleEnergyConsumptionBase4 = COOLANT_TANK_MODULE_4__ENERGY_CONSUMPTION_BASE.get();
            coolantTankModuleEnergyConsumptionBMultiplier4 = COOLANT_TANK_MODULE_4__ENERGY_CONSUMPTION_MULTIPLIER.get();
        }
    }
}
