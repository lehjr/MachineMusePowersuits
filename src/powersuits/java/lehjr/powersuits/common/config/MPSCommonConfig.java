package lehjr.powersuits.common.config;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MPSCommonConfig {

    /**
     *  General -------------------------------------------------------------------------------------------------------
     */
    private static final ModConfigSpec.Builder GENERAL_SETTINGS_BUILDER = new ModConfigSpec.Builder()
            .comment("General settings")
            .push("General");

    private static final ModConfigSpec.DoubleValue GENERAL_MAX_FLYING_SPEED = GENERAL_SETTINGS_BUILDER
            .comment("Maximum flight speed (in m/s)")
            .translation(MPSConstants.CONFIG_GENERAL_MAX_FLYING_SPEED)
            .defineInRange("maximumFlyingSpeedmps", 25.0, 0, Float.MAX_VALUE);


//    protected ModConfigSpec.DoubleValue
//            GENERAL_BASE_MAX_HEAT_POWERFIST,
//            GENERAL_BASE_MAX_HEAT_HELMET,
//            GENERAL_BASE_MAX_HEAT_CHEST,
//            GENERAL_BASE_MAX_HEAT_LEGS,
//            GENERAL_BASE_MAX_HEAT_FEET;

/*

#General settings
[General]
	#Maximum flight speed (in m/s)
	#Range: 0.0 ~ 3.4028234663852886E38
	maximumFlyingSpeedmps = 25.0
	#PowerFistModel2 Base Heat Cap
	#Range: 0.0 ~ 5000.0
	baseMaxHeatPowerFist = 5.0
	#Power Armor Helmet Heat Cap
	#Range: 0.0 ~ 5000.0
	baseMaxHeatHelmet = 5.0
	#Power Armor Chestplate Heat Cap
	#Range: 0.0 ~ 5000.0
	baseMaxHeatChest = 20.0
	#Power Armor Leggings Heat Cap
	#Range: 0.0 ~ 5000.0
	baseMaxHeatLegs = 15.0
	#Power Armor Boots Heat Cap
	#Range: 0.0 ~ 5000.0
	baseMaxHeatFeet = 5.0
	#Ore tag list for vein miner module.
	veinMinerOres = ["forge:ores/iron", "forge:ores/copper", "forge:ores/tin", "forge:ores/lead", "forge:ores/aluminum", "forge:ores/aluminium", "forge:ores/silver", "forge:ores/gold", "forge:ores/cinnabar", "forge:ores/zinc", "forge:ores/uranium", "forge:ores/platinum", "forge:ores/bismuth", "forge:ores/osmium", "forge:ores/coal", "forge:ores/redstone", "minecraft:glowstone", "forge:ores/diamond", "forge:ores/lapis", "forge:ores/quartz", "forge:ores/fluorite"]
	#Block registry name whitelist for the vein miner module.
	#Use for blocks that don't have an ore tag or to fine tune which blocks to break
	veinMinerBlocks = []
	#Items from other mods to allow as tools in the Power Fist
	externalToolItems = ["ae2:certus_quartz_wrench", "ae2:entropy_manipulator", "scannable:scanner", "dankstorage:dank_6"]
	#Items from other mods to allow as weapons in the Power Fist
	externalWeaponItems = []
*/

    /**
     * Modules --------------------------------------------------------------------------------------------------------
     */
    // Armor ----------------------------------------------------------------------------------------------------------
    // Iron
    private static final ModConfigSpec.Builder IRON_ARMOR_MODULE_SETTINGS_BUILDER = GENERAL_SETTINGS_BUILDER
            .pop()
            .push("Modules")
            .push("Armor")
            .push("Iron Armor Plating");

    private static final ModConfigSpec.BooleanValue IRON_PLATING_MODULE__IS_ALLOWED =
            IRON_ARMOR_MODULE_SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__ARMOR_PHYSICAL =
            IRON_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL, 4.0, 0, 40.0D);

    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__KNOCKBACK_RESISTANCE =
            IRON_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.KNOCKBACK_RESISTANCE, 0.25, 0, 4.0D);

    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__MAX_HEAT =
            IRON_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 300.0, 0, 1000.0D);

    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__ARMOR_TOUGHNESS =
            IRON_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_TOUGHNESS, 1.0, 0, 100.0D);


    // Diamond
    private static final ModConfigSpec.Builder DIAMOND_ARMOR_MODULE_SETTINGS_BUILDER = IRON_ARMOR_MODULE_SETTINGS_BUILDER
            .pop()
            .push("Diamond Armor Plating");

    private static final ModConfigSpec.BooleanValue DIAMOND_PLATING_MODULE__IS_ALLOWED =
            DIAMOND_ARMOR_MODULE_SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__ARMOR_PHYSICAL =
            DIAMOND_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL, 5.0, 0, 40.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__KNOCKBACK_RESISTANCE =
            DIAMOND_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.KNOCKBACK_RESISTANCE, 0.40, 0, 4.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__MAX_HEAT =
            DIAMOND_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 400.0, 0, 1000.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__ARMOR_TOUGHNESS =
            DIAMOND_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_TOUGHNESS, 2.5, 0, 100.0D);

    // Netherite
    private static final ModConfigSpec.Builder NETHERITE_ARMOR_MODULE_SETTINGS_BUILDER = DIAMOND_ARMOR_MODULE_SETTINGS_BUILDER
            .pop()
            .push("Netherite Armor Plating");

    private static final ModConfigSpec.BooleanValue NETHERITE_PLATING_MODULE__IS_ALLOWED =
            NETHERITE_ARMOR_MODULE_SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED,
                            true);

    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__ARMOR_PHYSICAL =
            NETHERITE_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL,
                            7.5, 0, 40.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__KNOCKBACK_RESISTANCE =
            NETHERITE_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.KNOCKBACK_RESISTANCE,
                            1.5, 0, 8.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__MAX_HEAT =
            NETHERITE_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT,
                            750, 0, 1000.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__ARMOR_TOUGHNESS =
            NETHERITE_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_TOUGHNESS,
                            3.5, 0, 100.0D);






    /*
#Model cosmetic settings
[Cosmetic]
	#Use legacy cosmetic configuration instead of cosmetic presets
	useLegacyCosmeticSystem = true
	#Allow high polly armor models instead of just skins
	allowHighPollyArmorModuels = true
	#Allow PowerFistModel2 model to be customized
	allowPowerFistCustomization = true

[Modules]

	[Modules.Armor]






		[Modules.Armor.energy_shield]
			#Range: 0.0 ~ 1.7976931348623157E308
			armorEnergy_fieldStrength_multiplier = 6.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			armorEnergyPerDamage_fieldStrength_multiplier = 5000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			maxHeat_fieldStrength_multiplier = 500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_knockbackResistance = 0.25

	[Modules.Cosmetic]

		[Modules.Cosmetic.transparent_armor]
			isAllowed = true

	[Modules.Energy_Generation]

		[Modules.Energy_Generation.generator_kinetic]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyPerBlock = 2000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyPerBlock_energyGenerated_multiplier = 6000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_movementResistance = 0.009999999776482582
			#Range: 0.0 ~ 1.7976931348623157E308
			movementResistance_energyGenerated_multiplier = 0.49000000953674316

		[Modules.Energy_Generation.generator_solar]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_daytimeEnergyGen = 15000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_nightTimeEnergyGen = 1500.0

		[Modules.Energy_Generation.generator_solar_adv]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_daytimeEnergyGen = 45000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_nightTimeEnergyGen = 1500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_daytimeHeatGen = 15.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_nightTimeHeatGen = 5.0

		[Modules.Energy_Generation.generator_thermal]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyPerBlock = 250.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyPerBlock_energyGenerated_multiplier = 250.0

	[Modules.Environment]

		[Modules.Environment.fluid_tank]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_fluidTankSize = 20000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_heatActivationPercent = 0.5
			#Range: 0.0 ~ 1.7976931348623157E308
			heatActivationPercent_activationPercent_multiplier = 0.5

		[Modules.Environment.auto_feeder]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 100.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_autoFeederEfficiency = 50.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_efficiency_multiplier = 1000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			autoFeederEfficiency_efficiency_multiplier = 50.0

		[Modules.Environment.cooling_system]
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_energyCon_multiplier = 40.0
			#Range: 0.0 ~ 1.7976931348623157E308
			coolingBonen_usergyCon_multiplier = 1.0

		[Modules.Environment.water_electrolyzer]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 10000.0
			isAllowed = true

		[Modules.Environment.mob_repulsor]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 2500.0
			isAllowed = true

	[Modules.Mining_Enhancement]

		[Modules.Mining_Enhancement.aoe_pick_upgrade]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_diameter_multiplier = 9500.0
			#Range: > 0
			aoeMiningDiameter_diameter_multiplier = 5

		[Modules.Mining_Enhancement.fortune]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_fortuneEnCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			fortuneEnCon_enchLevel_multiplier = 9500.0
			#Range: > 0
			fortuneLevel_enchLevel_multiplier = 3

		[Modules.Mining_Enhancement.tunnel_bore]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_diameter_multiplier = 9500.0
			#Range: > 0
			aoeMiningDiameter_diameter_multiplier = 5

		[Modules.Mining_Enhancement.vein_miner]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true

		[Modules.Mining_Enhancement.silk_touch]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_silkTouchEnCon = 2500.0
			isAllowed = true

		[Modules.Mining_Enhancement.aqua_affinity]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 0.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_aquaHarvSpeed = 0.20000000298023224
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_power_multiplier = 1000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			aquaHarvSpeed_power_multiplier = 0.800000011920929

		[Modules.Mining_Enhancement.selective_miner]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_aoe2Limit = 1.0
			#Range: > 0
			aoe2Limit_aoe2Limit_multiplier = 59

	[Modules.Movement]

		[Modules.Movement.jump_assist]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 0.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_power_multiplier = 250.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_multiplier = 1.0
			#Range: 0.0 ~ 1.7976931348623157E308
			multiplier_power_multiplier = 4.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_compensation_multiplier = 50.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_sprintExComp = 0.0
			#Range: 0.0 ~ 1.7976931348623157E308
			sprintExComp_compensation_multiplier = 1.0

		[Modules.Movement.swim_assist]
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_thrust_multiplier = 1000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			underwaterMovBoost_thrust_multiplier = 1.0

		[Modules.Movement.parachute]
			isAllowed = true

		[Modules.Movement.dim_rift_gen]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_heatGen = 55.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 200000.0

		[Modules.Movement.sprint_assist]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_sprintEnergyCon = 0.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			sprintEnergyCon_sprintAssist_multiplier = 5000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_sprintSpeedMult = 0.1
			#Range: 0.0 ~ 1.7976931348623157E308
			sprintSpeedMult_sprintAssist_multiplier = 2.49
			#Range: 0.0 ~ 1.7976931348623157E308
			sprintEnergyCon_compensation_multiplier = 2000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_sprintExComp = 0.0
			#Range: 0.0 ~ 1.7976931348623157E308
			sprintExComp_compensation_multiplier = 1.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_walkingEnergyCon = 0.0
			#Range: 0.0 ~ 1.7976931348623157E308
			walkingEnergyCon_walkingAssist_multiplier = 5000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_walkingSpeedMult = 0.01
			#Range: 0.0 ~ 1.7976931348623157E308
			walkingSpeedMult_walkingAssist_multiplier = 1.99

		[Modules.Movement.jetpack]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 0.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_jetpackThrust = 0.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_thrust_multiplier = 15000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			jetpackThrust_thrust_multiplier = 0.1599999964237213

		[Modules.Movement.climb_assist]
			isAllowed = true

		[Modules.Movement.shock_absorber]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 0.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_power_multiplier = 100.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_multiplier = 0.0
			#Range: 0.0 ~ 1.7976931348623157E308
			multiplier_power_multiplier = 10.0

		[Modules.Movement.flight_control]
			#Range: 0.0 ~ 1.7976931348623157E308
			yLookRatio_vertically_multiplier = 1.0
			isAllowed = true

		[Modules.Movement.glider]
			isAllowed = true

		[Modules.Movement.jet_boots]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 0.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_jetbootsThrust = 0.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_thrust_multiplier = 750.0
			#Range: 0.0 ~ 1.7976931348623157E308
			jetbootsThrust_thrust_multiplier = 0.07999999821186066

		[Modules.Movement.blink_drive]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 10000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_blinkDriveRange = 5.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_range_multiplier = 30000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			blinkDriveRange_range_multiplier = 59.0

	[Modules.Special]

		[Modules.Special.piglin_pacification_module]
			isAllowed = true

		[Modules.Special.magnet]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_radius = 1.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 5.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_radius_multiplier = 2000.0
			#Range: > 0
			radius_radius_multiplier = 9

		[Modules.Special.invisibility]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_invisibilityEnergy = 100.0
			isAllowed = true

		[Modules.Special.item_minecraft_clock]
			isAllowed = true

		[Modules.Special.item_minecraft_compass]
			isAllowed = true

	[Modules.Tool]

		[Modules.Tool.block_powersuits_tinkertable]
			isAllowed = true

		[Modules.Tool.item_refinedstorage_wireless_grid]
			isAllowed = true

		[Modules.Tool.luxcapacitor_module]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 1000.0
			isAllowed = true
			#Range: 0.0 ~ 1.0
			redHue_red_multiplier = 1.0
			#Range: 0.0 ~ 1.0
			greenHue_green_multiplier = 1.0
			#Range: 0.0 ~ 1.0
			blueHue_blue_multiplier = 1.0
			#Range: 0.0 ~ 1.0
			opacity_alpha_multiplier = 1.0

		[Modules.Tool.diamond_pick_upgrade]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true

		[Modules.Tool.pickaxe]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_harvestSpeed = 8.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_overclock_multiplier = 9500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			harvestSpeed_overclock_multiplier = 52.0

		[Modules.Tool.shovel]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_harvestSpeed = 8.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_overclock_multiplier = 9500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			harvestSpeed_overclock_multiplier = 22.0

		[Modules.Tool.item_appliedenergistics2_wireless_terminal]
			isAllowed = true

		[Modules.Tool.leaf_blower]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_radius_multiplier = 9500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_radius = 1.0
			#Range: 0.0 ~ 1.7976931348623157E308
			radius_radius_multiplier = 15.0

		[Modules.Tool.item_scannable_scanner]
			isAllowed = true

		[Modules.Tool.axe]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_harvestSpeed = 8.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_overclock_multiplier = 9500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			harvestSpeed_overclock_multiplier = 22.0

		[Modules.Tool.item_refinedstorage_wireless_fluid_grid]
			isAllowed = true

		[Modules.Tool.shears]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 1000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_harvestSpeed = 8.0

		[Modules.Tool.block_minecraft_crafting_table]
			isAllowed = true

		[Modules.Tool.flint_and_steel]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 10000.0
			isAllowed = true

		[Modules.Tool.hoe]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_radius_multiplier = 9500.0
			#Range: > 0
			radius_radius_multiplier = 8
			#Range: 0.0 ~ 1.7976931348623157E308
			base_harvestSpeed = 8.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_overclock_multiplier = 9500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			harvestSpeed_overclock_multiplier = 22.0

	[Modules.Vision]

		[Modules.Vision.night_vision]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 100.0
			isAllowed = true

		[Modules.Vision.binoculars]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_fieldOfView = 0.5
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			fieldOfView_fOVMult_multiplier = 9.5

	[Modules.Weapon]

		[Modules.Weapon.plasma_cannon]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_plasmaEnergyPerTick = 100.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_plasmaDamage = 2.0
			#Range: 0.0 ~ 1.7976931348623157E308
			plasmaEnergyPerTick_amperage_multiplier = 1500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			plasmaDamage_amperage_multiplier = 38.0
			#Range: 0.0 ~ 1.7976931348623157E308
			plasmaEnergyPerTick_voltage_multiplier = 500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			plasmaExplosiveness_voltage_multiplier = 0.5

		[Modules.Weapon.melee_assist]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 10.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_meleeDamage = 2.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_impact_multiplier = 1000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			meleeDamage_impact_multiplier = 8.0
			#Range: 0.0 ~ 1.7976931348623157E308
			energyCon_carryThrough_multiplier = 200.0
			#Range: 0.0 ~ 1.7976931348623157E308
			meleeKnockback_carryThrough_multiplier = 1.0

		[Modules.Weapon.blade_launcher]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 5000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_spinBladeDam = 6.0

		[Modules.Weapon.railgun]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_railgunTotalImpulse = 500.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_railgunEnergyCost = 5000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			base_railgunHeatEm = 2.0
			#Range: 0.0 ~ 1.7976931348623157E308
			railgunTotalImpulse_voltage_multiplier = 2500.0
			#Range: 0.0 ~ 1.7976931348623157E308
			railgunEnergyCost_voltage_multiplier = 25000.0
			#Range: 0.0 ~ 1.7976931348623157E308
			railgunHeatEm_voltage_multiplier = 10.0

		[Modules.Weapon.lightning_summoner]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 4900000.0
			isAllowed = true
			#Range: 0.0 ~ 1.7976931348623157E308
			base_heatEmission = 100.0

 */










    // Energy Shield
    private static final ModConfigSpec.Builder ENERGY_SHIELD_ARMOR_MODULE_SETTINGS_BUILDER = NETHERITE_ARMOR_MODULE_SETTINGS_BUILDER
            .pop()
            .push("Energy Shield (Armor Plating)");

    private static final ModConfigSpec.BooleanValue ENERGY_SHIELD_PLATING_MODULE__IS_ALLOWED =
            ENERGY_SHIELD_ARMOR_MODULE_SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_PLATING_MODULE__ARMOR_PHYSICAL =
            ENERGY_SHIELD_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL, 7.5, 0, 40.0D);

    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_PLATING_MODULE__KNOCKBACK_RESISTANCE =
            ENERGY_SHIELD_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.KNOCKBACK_RESISTANCE, 1.5, 0, 8.0D);

    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_PLATING_MODULE__MAX_HEAT =
            ENERGY_SHIELD_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 750.0, 0, 1000.0D);

    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_PLATING_MODULE__ARMOR_TOUGHNESS =
            ENERGY_SHIELD_ARMOR_MODULE_SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_TOUGHNESS, 3.5, 0, 100.0D);





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








    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();














    public static final ModConfigSpec COMMON_SPEC = BUILDER.build();



    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == COMMON_SPEC) {
            // MODULES --------------------------------------------------------
            // Armor
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




        }
    }
}