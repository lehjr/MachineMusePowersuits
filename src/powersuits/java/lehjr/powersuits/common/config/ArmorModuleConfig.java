package lehjr.powersuits.common.config;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ArmorModuleConfig {

    // Armor Modules --------------------------------------------------------------------------------------------------
    // Iron
    private static final ModConfigSpec.Builder IRON_ARMOR_MODULE__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Armor_Modules").push("Iron_Armor_Plating");
    private static final ModConfigSpec.BooleanValue IRON_PLATING_MODULE__IS_ALLOWED = IRON_ARMOR_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__ARMOR_PHYSICAL = IRON_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL, 4.0, 0, 40.0D);
    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__KNOCKBACK_RESISTANCE = IRON_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.KNOCKBACK_RESISTANCE, 0.25, 0, 4.0D);
    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__MAX_HEAT = IRON_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 300.0, 0, 1000.0D);
    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__ARMOR_TOUGHNESS = IRON_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ARMOR_TOUGHNESS, 1.0, 0, 100.0D);

    // Diamond
    private static final ModConfigSpec.Builder DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER = IRON_ARMOR_MODULE__SETTINGS_BUILDER.pop().push("Diamond_Armor_Plating");
    private static final ModConfigSpec.BooleanValue DIAMOND_PLATING_MODULE__IS_ALLOWED = DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__ARMOR_PHYSICAL = DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL, 5.0, 0, 40.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__KNOCKBACK_RESISTANCE = DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.KNOCKBACK_RESISTANCE, 0.40, 0, 4.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__MAX_HEAT = DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 400.0, 0, 1000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__ARMOR_TOUGHNESS = DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ARMOR_TOUGHNESS, 2.5, 0, 100.0D);

    // Netherite
    private static final ModConfigSpec.Builder NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER = DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER.pop().push("Netherite_Armor_Plating");
    private static final ModConfigSpec.BooleanValue NETHERITE_PLATING_MODULE__IS_ALLOWED = NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__ARMOR_PHYSICAL = NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL, 7.5, 0, 40.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__KNOCKBACK_RESISTANCE = NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.KNOCKBACK_RESISTANCE, 1.5, 0, 8.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__MAX_HEAT = NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 750, 0, 1000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__ARMOR_TOUGHNESS = NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ARMOR_TOUGHNESS, 3.5, 0, 100.0D);

    // Energy Shield
    private static final ModConfigSpec.Builder ENERGY_SHIELD_MODULE__SETTINGS_BUILDER = NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER.pop().push("Energy_Shield_(Armor_Plating)");
    private static final ModConfigSpec.BooleanValue ENERGY_SHIELD_MODULE__IS_ALLOWED = ENERGY_SHIELD_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD__FIELD_STRENGTH_MULTIPLIER = ENERGY_SHIELD_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.MODULE_FIELD_STRENGTH + "Multiplier", 6.0, 0, 1000);
    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD__ENERGY_PER_DAMAGE = ENERGY_SHIELD_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ARMOR_ENERGY_CONSUMPTION + "Multiplier", 5000, 0, Double.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_MODULE__MAX_HEAT = ENERGY_SHIELD_MODULE__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT +"Multiplier", 900, 0, 1000.0D);
    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_MODULE__ARMOR_TOUGHNESS = ENERGY_SHIELD_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ARMOR_TOUGHNESS +"Multiplier", 4.5, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_MODULE__KNOCKBACK_RESISTANCE = ENERGY_SHIELD_MODULE__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT +"Multiplier", 900, 0, 1000.0D);

    public static final ModConfigSpec MPS_ARMOR_MODULE_CONFIG_SPEC = ENERGY_SHIELD_MODULE__SETTINGS_BUILDER.build();


    // Armor ------------------------------------------------------------------
    // Iron
    public static boolean ironPlatingIsAllowed;
    public static double ironPlatingArmorPhysical;
    public static double ironPlatingKnockBackResistance;
    public static double ironPlatingMaxHeat;
    public static double ironPlatingArmorToughness;

    // Diamond
    public static boolean diamondPlatingIsAllowed;
    public static double diamondPlatingArmorPhysical;
    public static double diamondPlatingKnockBackResistance;
    public static double diamondPlatingMaxHeat;
    public static double diamondPlatingArmorToughness;

    // Netherite
    public static boolean netheritePlatingIsAllowed;
    public static double netheritePlatingArmorPhysical;
    public static double netheritePlatingKnockBackResistance;
    public static double netheritePlatingMaxHeat;
    public static double netheritePlatingArmorToughness;

    // Energy Shield
    public static boolean energyShieldIsAllowed;
    public static double energyShieldFieldStrengthMultiplier;
    public static double energyShieldEnergyConsumptionMultiplier;
    public static double energyShieldMaxHeatMultiplier;
    public static double energyShieldKnockBackResistanceMultiplier;
    public static double energyShieldToughnessMultiplier;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_ARMOR_MODULE_CONFIG_SPEC) {
            // Armor Modules --------------------------------------------------------
            // Iron
            ironPlatingIsAllowed = IRON_PLATING_MODULE__IS_ALLOWED.get();
            ironPlatingArmorPhysical = IRON_PLATING_MODULE__ARMOR_PHYSICAL.get();
            ironPlatingKnockBackResistance = IRON_PLATING_MODULE__KNOCKBACK_RESISTANCE.get();
            ironPlatingMaxHeat = IRON_PLATING_MODULE__MAX_HEAT.get();
            ironPlatingArmorToughness = IRON_PLATING_MODULE__ARMOR_TOUGHNESS.get();

            // Diamond --------------------------------------------------------
            diamondPlatingIsAllowed = DIAMOND_PLATING_MODULE__IS_ALLOWED.get();
            diamondPlatingArmorPhysical = DIAMOND_PLATING_MODULE__ARMOR_PHYSICAL.get();
            diamondPlatingKnockBackResistance = DIAMOND_PLATING_MODULE__KNOCKBACK_RESISTANCE.get();
            diamondPlatingMaxHeat = DIAMOND_PLATING_MODULE__MAX_HEAT.get();
            diamondPlatingArmorToughness = DIAMOND_PLATING_MODULE__ARMOR_TOUGHNESS.get();

            // Diamond --------------------------------------------------------
            netheritePlatingIsAllowed = NETHERITE_PLATING_MODULE__IS_ALLOWED.get();
            netheritePlatingArmorPhysical = NETHERITE_PLATING_MODULE__ARMOR_PHYSICAL.get();
            netheritePlatingKnockBackResistance = NETHERITE_PLATING_MODULE__KNOCKBACK_RESISTANCE.get();
            netheritePlatingMaxHeat = NETHERITE_PLATING_MODULE__MAX_HEAT.get();
            netheritePlatingArmorToughness = NETHERITE_PLATING_MODULE__ARMOR_TOUGHNESS.get();

            // EnergyShield ---------------------------------------------------
            energyShieldIsAllowed = ENERGY_SHIELD_MODULE__IS_ALLOWED.get();
            energyShieldFieldStrengthMultiplier = ENERGY_SHIELD__FIELD_STRENGTH_MULTIPLIER.get();
            energyShieldEnergyConsumptionMultiplier = ENERGY_SHIELD__ENERGY_PER_DAMAGE.get();
            energyShieldMaxHeatMultiplier = ENERGY_SHIELD_MODULE__MAX_HEAT.get();
            energyShieldKnockBackResistanceMultiplier = ENERGY_SHIELD_MODULE__KNOCKBACK_RESISTANCE.get();
            energyShieldToughnessMultiplier = ENERGY_SHIELD_MODULE__ARMOR_TOUGHNESS.get();
        }
    }
}
