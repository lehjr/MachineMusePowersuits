package lehjr.powersuits.common.config;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MPSCommonConfig {
    /* General ======================================================================================================== */
    private static final ModConfigSpec.Builder GENERAL__SETTINGS_BUILDER = new ModConfigSpec.Builder()
            .comment("General settings")
            .push("General");

    private static final ModConfigSpec.DoubleValue GENERAL_MAX_FLYING_SPEED = GENERAL__SETTINGS_BUILDER
            .comment("Maximum flight speed (in m/s)")
            .translation(MPSConstants.CONFIG_GENERAL_MAX_FLYING_SPEED)
            .defineInRange("maximumFlyingSpeedmps", 25.0, 0, Float.MAX_VALUE);

    /* Armor ========================================================================================================== */
    private static final ModConfigSpec.Builder ARMOR__SETTINGS_BUILDER = GENERAL__SETTINGS_BUILDER
            .pop() // General Settings
            .push("Armor");

    // Prototype Armor ------------------------------------------------------------------------------------------------
    // Helm
    private static final ModConfigSpec.Builder ARMOR_HELM_1__SETTINGS_BUILDER = ARMOR__SETTINGS_BUILDER
            .push("Tier 1")
            .push("Tier 1 Armor Helm");

    private static final ModConfigSpec.IntValue ARMOR_HELM_1_INVENTORY_SLOTS = ARMOR_HELM_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_HELM_1__MAX_HEAT = ARMOR_HELM_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 1.0, 0, 1000.0D);

    // ChestPlate
    private static final ModConfigSpec.Builder ARMOR_CHESTPLATE_1__SETTINGS_BUILDER = ARMOR_HELM_1__SETTINGS_BUILDER
                    .pop()
                    .push("Tier 1 Armor ChestPlate");

    private static final ModConfigSpec.IntValue ARMOR_CHESTPLATE_1__INVENTORY_SLOTS = ARMOR_CHESTPLATE_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 6, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_CHESTPLATE_1__MAX_HEAT = ARMOR_CHESTPLATE_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 4.0, 0, 1000.0D);

    // Leggins
    private static final ModConfigSpec.Builder ARMOR_LEGGINGS_1__SETTINGS_BUILDER = ARMOR_CHESTPLATE_1__SETTINGS_BUILDER
                    .pop()
                    .push("Tier 1 Armor Leggings");

    private static final ModConfigSpec.IntValue ARMOR_LEGGINGS_1__INVENTORY_SLOTS = ARMOR_LEGGINGS_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_LEGGINGS_1__MAX_HEAT = ARMOR_LEGGINGS_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 3.0, 0, 1000.0D);

    // Boots
    private static final ModConfigSpec.Builder ARMOR_BOOTS_1__SETTINGS_BUILDER = ARMOR_LEGGINGS_1__SETTINGS_BUILDER
            .pop()
            .push("Tier 1 Armor Boots");

    private static final ModConfigSpec.IntValue ARMOR_BOOTS_1__INVENTORY_SLOTS =
            ARMOR_BOOTS_1__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);

    private static final ModConfigSpec.DoubleValue ARMOR_BOOTS_1__MAX_HEAT =
            ARMOR_BOOTS_1__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 1.0, 0, 1000.0D);

    // Prototype Armor Mk2 --------------------------------------------------------------------------------------------
    // Helm
    private static final ModConfigSpec.Builder ARMOR_HELM_2__SETTINGS_BUILDER = ARMOR_BOOTS_1__SETTINGS_BUILDER
            .pop() // boots
            .pop() // tier 1
            .push("Tier 2")
            .push("Tier 2 Armor Helm");

    private static final ModConfigSpec.IntValue ARMOR_HELM_2__INVENTORY_SLOTS = ARMOR_HELM_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_HELM_2__MAX_HEAT = ARMOR_HELM_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // ChestPlate
    private static final ModConfigSpec.Builder ARMOR_CHESTPLATE_2__SETTINGS_BUILDER = ARMOR_HELM_2__SETTINGS_BUILDER
            .pop() // helm
            .push("Tier 2 ChestPlate");

    private static final ModConfigSpec.IntValue ARMOR_CHESTPLATE_2__INVENTORY_SLOTS = ARMOR_CHESTPLATE_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_CHESTPLATE_2__MAX_HEAT = ARMOR_CHESTPLATE_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Leggins
    private static final ModConfigSpec.Builder TIER_2_ARMOR_LEGGINGS__SETTINGS_BUILDER = ARMOR_CHESTPLATE_2__SETTINGS_BUILDER
            .pop() // chestplate
            .push("Tier 2 Leggings");

    private static final ModConfigSpec.IntValue ARMOR_LEGGINGS_2__INVENTORY_SLOTS = TIER_2_ARMOR_LEGGINGS__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_LEGGINGS_2__MAX_HEAT = TIER_2_ARMOR_LEGGINGS__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Boots
    private static final ModConfigSpec.Builder TIER_2_ARMOR_BOOTS__SETTINGS_BUILDER = TIER_2_ARMOR_LEGGINGS__SETTINGS_BUILDER
            .pop() // leggings
            .push("Tier 2 Boots");

    private static final ModConfigSpec.IntValue ARMOR_BOOTS_2__INVENTORY_SLOTS = TIER_2_ARMOR_BOOTS__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_BOOTS_2__MAX_HEAT = TIER_2_ARMOR_BOOTS__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Armor Mk3 ------------------------------------------------------------------------------------------------------
    // Helm
    private static final ModConfigSpec.Builder TIER_3_ARMOR_HELM__SETTINGS_BUILDER = TIER_2_ARMOR_BOOTS__SETTINGS_BUILDER
            .pop() // boots
            .pop() // tier 2
            .push("Tier 3")
            .push("Tier 3 Armor Helm");

    private static final ModConfigSpec.IntValue ARMOR_HELM_3__INVENTORY_SLOTS = TIER_3_ARMOR_HELM__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_HELM_3__MAX_HEAT = TIER_3_ARMOR_HELM__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // ChestPlate
    private static final ModConfigSpec.Builder TIER_3_ARMOR_CHESTPLATE__SETTINGS_BUILDER = TIER_3_ARMOR_HELM__SETTINGS_BUILDER
            .pop() // helm
            .push("Tier 3 ChestPlate");

    private static final ModConfigSpec.IntValue ARMOR_CHESTPLATE_3__INVENTORY_SLOTS =
            TIER_3_ARMOR_CHESTPLATE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);

    private static final ModConfigSpec.DoubleValue ARMOR_CHESTPLATE_3__MAX_HEAT =
            TIER_3_ARMOR_CHESTPLATE__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Leggins
    private static final ModConfigSpec.Builder TIER_3_ARMOR_LEGGINGS__SETTINGS_BUILDER = TIER_3_ARMOR_CHESTPLATE__SETTINGS_BUILDER
            .pop() // chestplate
            .push("Tier 3 Armor Leggings");

    private static final ModConfigSpec.IntValue ARMOR_LEGGINGS_3__INVENTORY_SLOTS =
            TIER_3_ARMOR_LEGGINGS__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);

    private static final ModConfigSpec.DoubleValue ARMOR_LEGGINGS_3__MAX_HEAT =
            TIER_3_ARMOR_LEGGINGS__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Boots
    private static final ModConfigSpec.Builder TIER_3_ARMOR_BOOTS__SETTINGS_BUILDER = TIER_3_ARMOR_LEGGINGS__SETTINGS_BUILDER
            .pop() // leggings
            .push("Tier 3 Armor Boots");

    private static final ModConfigSpec.IntValue ARMOR_BOOTS_3__INVENTORY_SLOTS =
            TIER_3_ARMOR_BOOTS__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);

    private static final ModConfigSpec.DoubleValue ARMOR_BOOTS_3__MAX_HEAT =
            TIER_3_ARMOR_BOOTS__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Armor Mk4 ------------------------------------------------------------------------------------------------------
    // Helm
    private static final ModConfigSpec.Builder TIER_4_ARMOR_HELM__SETTINGS_BUILDER = TIER_3_ARMOR_BOOTS__SETTINGS_BUILDER
            .pop() // boots
            .pop() // tier 3
            .push("Tier 4")
            .push("Tier 4 Armor Helm");

    private static final ModConfigSpec.IntValue ARMOR_HELM_4__INVENTORY_SLOTS =
            TIER_4_ARMOR_HELM__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);

    private static final ModConfigSpec.DoubleValue ARMOR_HELMET_4__MAX_HEAT =
            TIER_4_ARMOR_HELM__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // ChestPlate
    private static final ModConfigSpec.Builder TIER_4_ARMOR_CHESTPLATE__SETTINGS_BUILDER = TIER_4_ARMOR_HELM__SETTINGS_BUILDER
            .pop() // helm
            .push("Tier 4 Armor ChestPlate");

    private static final ModConfigSpec.IntValue ARMOR_CHESTPLATE_4__INVENTORY_SLOTS =
            TIER_4_ARMOR_CHESTPLATE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);

    private static final ModConfigSpec.DoubleValue ARMOR_CHESTPLATE_4__MAX_HEAT =
            TIER_4_ARMOR_CHESTPLATE__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Leggins
    private static final ModConfigSpec.Builder TIER_4_ARMOR_LEGGINGS__SETTINGS_BUILDER = TIER_4_ARMOR_CHESTPLATE__SETTINGS_BUILDER
            .pop() // chestplate
            .push("Tier 4 Armor Leggings");

    private static final ModConfigSpec.IntValue ARMOR_LEGGINGS_4__INVENTORY_SLOTS =
            TIER_4_ARMOR_LEGGINGS__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);

    private static final ModConfigSpec.DoubleValue ARMOR_LEGGINGS_4__MAX_HEAT =
            TIER_4_ARMOR_LEGGINGS__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Boots
    private static final ModConfigSpec.Builder TIER_4_ARMOR_BOOTS__SETTINGS_BUILDER = TIER_4_ARMOR_LEGGINGS__SETTINGS_BUILDER
            .pop() // leggings
            .push("Tier 4 Armor Boots");

    private static final ModConfigSpec.IntValue ARMOR_BOOTS_4__INVENTORY_SLOTS =
            TIER_4_ARMOR_BOOTS__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);

    private static final ModConfigSpec.DoubleValue ARMOR_BOOTS_4__MAX_HEAT =
            TIER_4_ARMOR_BOOTS__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Power Fist =========================================================================================================
    // Tier 1
    private static final ModConfigSpec.Builder TIER_1_POWER_FIST__SETTINGS_BUILDER = TIER_4_ARMOR_BOOTS__SETTINGS_BUILDER
            .pop() // boots
            .pop() // tier 4
            .pop() // armor
            .push("Power Fist")
            .push("Tier 1");

    private static final ModConfigSpec.IntValue POWER_FIST_1__INVENTORY_SLOTS =
            TIER_1_POWER_FIST__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 5, 0, 10);

    private static final ModConfigSpec.DoubleValue POWER_FIST_1__MAX_HEAT =
            TIER_1_POWER_FIST__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 2
    private static final ModConfigSpec.Builder TIER_2_POWER_FIST__SETTINGS_BUILDER = TIER_1_POWER_FIST__SETTINGS_BUILDER
            .pop() // tier 1
            .push("Tier 2");

    private static final ModConfigSpec.IntValue POWER_FIST_2__INVENTORY_SLOTS =
            TIER_2_POWER_FIST__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 12, 0, 60);

    private static final ModConfigSpec.DoubleValue POWER_FIST_2__MAX_HEAT =
            TIER_2_POWER_FIST__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 3
    private static final ModConfigSpec.Builder TIER_3_POWER_FIST__SETTINGS_BUILDER = TIER_2_POWER_FIST__SETTINGS_BUILDER
            .pop() // tier 2
            .push("Tier 3");

    private static final ModConfigSpec.IntValue POWER_FIST_3__INVENTORY_SLOTS =
            TIER_3_POWER_FIST__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 30, 0, 100);

    private static final ModConfigSpec.DoubleValue POWER_FIST_TIER_3__MAX_HEAT =
            TIER_3_POWER_FIST__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 4
    private static final ModConfigSpec.Builder TIER_4_POWER_FIST__SETTINGS_BUILDER = TIER_3_POWER_FIST__SETTINGS_BUILDER
            .pop() // tier 3
            .push("Tier 4");

    private static final ModConfigSpec.IntValue POWER_FIST_4__INVENTORY_SLOTS =
            TIER_4_POWER_FIST__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 60, 0, 180);

    private static final ModConfigSpec.DoubleValue POWER_FIST_4__MAX_HEAT =
            TIER_4_POWER_FIST__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    /* Modules -------------------------------------------------------------------------------------------------------- */
    private static final ModConfigSpec.Builder MODULE__SETTINGS_BUILDER = TIER_4_POWER_FIST__SETTINGS_BUILDER
            .pop() // tier 4
            .pop() // Power Fist
            .push("Modules");

    // Armor ----------------------------------------------------------------------------------------------------------
    // Iron
    private static final ModConfigSpec.Builder IRON_ARMOR_MODULE__SETTINGS_BUILDER = MODULE__SETTINGS_BUILDER
            .push("Armor")
            .push("Iron Armor Plating");

    private static final ModConfigSpec.BooleanValue IRON_PLATING_MODULE__IS_ALLOWED =
            IRON_ARMOR_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__ARMOR_PHYSICAL =
            IRON_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL, 4.0, 0, 40.0D);

    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__KNOCKBACK_RESISTANCE =
            IRON_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.KNOCKBACK_RESISTANCE, 0.25, 0, 4.0D);

    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__MAX_HEAT =
            IRON_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 300.0, 0, 1000.0D);

    private static final ModConfigSpec.DoubleValue IRON_PLATING_MODULE__ARMOR_TOUGHNESS =
            IRON_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_TOUGHNESS, 1.0, 0, 100.0D);

    // Diamond
    private static final ModConfigSpec.Builder DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER = IRON_ARMOR_MODULE__SETTINGS_BUILDER
            .pop() // Iron
            .push("Diamond Armor Plating");

    private static final ModConfigSpec.BooleanValue DIAMOND_PLATING_MODULE__IS_ALLOWED =
            DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__ARMOR_PHYSICAL =
            DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL, 5.0, 0, 40.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__KNOCKBACK_RESISTANCE =
            DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.KNOCKBACK_RESISTANCE, 0.40, 0, 4.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__MAX_HEAT =
            DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT, 400.0, 0, 1000.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PLATING_MODULE__ARMOR_TOUGHNESS =
            DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_TOUGHNESS, 2.5, 0, 100.0D);

    // Netherite
    private static final ModConfigSpec.Builder NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER = DIAMOND_ARMOR_MODULE__SETTINGS_BUILDER
            .pop() // Diamond
            .push("Netherite Armor Plating");

    private static final ModConfigSpec.BooleanValue NETHERITE_PLATING_MODULE__IS_ALLOWED =
            NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED,
                            true);

    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__ARMOR_PHYSICAL =
            NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_VALUE_PHYSICAL,
                            7.5, 0, 40.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__KNOCKBACK_RESISTANCE =
            NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.KNOCKBACK_RESISTANCE,
                            1.5, 0, 8.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__MAX_HEAT =
            NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT,
                            750, 0, 1000.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PLATING_MODULE__ARMOR_TOUGHNESS =
            NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_TOUGHNESS,
                            3.5, 0, 100.0D);

    // Energy Shield
    private static final ModConfigSpec.Builder ENERGY_SHIELD_MODULE__SETTINGS_BUILDER = NETHERITE_ARMOR_MODULE__SETTINGS_BUILDER
            .pop() // Netherite
            .push("Energy Shield (Armor Plating)");

    private static final ModConfigSpec.BooleanValue ENERGY_SHIELD_MODULE__IS_ALLOWED =
            ENERGY_SHIELD_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED,
                            true);

    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD__FIELD_STRENGTH_MULTIPLIER =
            ENERGY_SHIELD_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.MODULE_FIELD_STRENGTH + "Multiplier", 6.0, 0, 1000);

    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD__ENERGY_PER_DAMAGE =
            ENERGY_SHIELD_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ARMOR_ENERGY_CONSUMPTION + "Multiplier", 5000, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_MODULE__MAX_HEAT =
            ENERGY_SHIELD_MODULE__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT +"Multiplier",
                            900, 0, 1000.0D);

    private static final ModConfigSpec.DoubleValue ENERGY_SHIELD_MODULE__KNOCKBACK_RESISTANCE =
            ENERGY_SHIELD_MODULE__SETTINGS_BUILDER
                    .defineInRange(NuminaConstants.MAXIMUM_HEAT +"Multiplier",
                            900, 0, 1000.0D);

    // Cosmetic -----------------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder COSMETIC_MODULE__SETTINGS_BUILDER = ENERGY_SHIELD_MODULE__SETTINGS_BUILDER
            .pop() // Energy Shield
            .pop() // Armor
            .push("Cosmetic Modules");

    // Transparent Armor
    private static final ModConfigSpec.Builder TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER = COSMETIC_MODULE__SETTINGS_BUILDER
            .push("Transparent Armor");

    private static final ModConfigSpec.BooleanValue TRANSPARENT_ARMOR_MODULE__IS_ALLOWED =
            TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED,
                            true);

    // Energy Generation --------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder ENERGY_GENERATION_MODULE__SETTINGS_BUILDER = TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER
            .pop() // Transparent Armor
            .pop() // Cosmetic
            .push("Energy Generation Modules");

// TODO


    // Environmental --------------------------------------------------------------------------------------------------











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



		[Modules.Tool.diamond_pick_upgrade]
			#Range: 0.0 ~ 1.7976931348623157E308
			base_energyCon = 500.0
			isAllowed = true



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



		[Modules.Tool.item_scannable_scanner]
			isAllowed = true



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










 */

    // Axe
    private static final ModConfigSpec.Builder STONE_AXE_MODULE_BUILDER = ENERGY_GENERATION_MODULE__SETTINGS_BUILDER
//            .pop() // TODO
            .pop() // Energy Generation
            .push("Tool Modules")
            .push("Block Breaking")
            .push("Axes")
            .push("Stone Axe Module");


    private static final ModConfigSpec.BooleanValue STONE_AXE_MODULE__IS_ALLOWED =
            STONE_AXE_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue STONE_AXE_MODULE__ENERGY_CONSUMPTION_BASE =
            STONE_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue STONE_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            STONE_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue STONE_AXE_MODULE__HARVEST_SPEED_BASE =
            STONE_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue STONE_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            STONE_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Iron
    private static final ModConfigSpec.Builder IRON_AXE_MODULE_BUILDER = STONE_AXE_MODULE_BUILDER
            .pop() // Stone
            .push("Iron Axe Module");

    private static final ModConfigSpec.BooleanValue IRON_AXE_MODULE__IS_ALLOWED =
            IRON_AXE_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue IRON_AXE_MODULE__ENERGY_CONSUMPTION_BASE =
            IRON_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue IRON_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            IRON_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue IRON_AXE_MODULE__HARVEST_SPEED_BASE =
            IRON_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue IRON_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            IRON_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Diamond Pickaxe
    private static final ModConfigSpec.Builder DIAMOND_AXE_MODULE_BUILDER = IRON_AXE_MODULE_BUILDER
            .pop() // Diamond Pickaxe
            .push("Diamond Axe");

    private static final ModConfigSpec.BooleanValue DIAMOND_AXE_MODULE__IS_ALLOWED =
            DIAMOND_AXE_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue DIAMOND_AXE_MODULE__ENERGY_CONSUMPTION_BASE =
            DIAMOND_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            DIAMOND_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_AXE_MODULE__HARVEST_SPEED_BASE =
            DIAMOND_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            DIAMOND_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    private static final ModConfigSpec.Builder NETHERITE_AXE_MODULE_BUILDER = DIAMOND_AXE_MODULE_BUILDER
            .pop() // Diamond Pickaxe
            .push("Netherite Axe");

    private static final ModConfigSpec.BooleanValue NETHERITE_AXE_MODULE__IS_ALLOWED =
            NETHERITE_AXE_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue NETHERITE_AXE_MODULE__ENERGY_CONSUMPTION_BASE =
            NETHERITE_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            NETHERITE_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_AXE_MODULE__HARVEST_SPEED_BASE =
            NETHERITE_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            NETHERITE_AXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);


    /*
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
*/















    //  Pickaxe
    private static final ModConfigSpec.Builder STONE_PICKAXE_MODULE_BUILDER = NETHERITE_AXE_MODULE_BUILDER
            .pop() // Netherite Axe
            .pop() // Axes
            .push("PickAxes")
            .push("Stone PickAxe Module");


    private static final ModConfigSpec.BooleanValue STONE_PICKAXE_MODULE__IS_ALLOWED =
            STONE_PICKAXE_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE =
            STONE_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            STONE_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__HARVEST_SPEED_BASE =
            STONE_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            STONE_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 4, 0, 100.0D);

    // Iron
    private static final ModConfigSpec.Builder IRON_PICKAXE_MODULE_BUILDER = STONE_PICKAXE_MODULE_BUILDER
            .pop() // Stone
            .push("Iron PickAxe Module");

    private static final ModConfigSpec.BooleanValue IRON_PICKAXE_MODULE__IS_ALLOWED =
            IRON_PICKAXE_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE =
            IRON_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            IRON_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__HARVEST_SPEED_BASE =
            IRON_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            IRON_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Diamond Pickaxe
    private static final ModConfigSpec.Builder DIAMOND_PICKAXE_MODULE_BUILDER = IRON_PICKAXE_MODULE_BUILDER
            .pop() // Diamond Pickaxe
            .push("Diamond Pickaxe");

    private static final ModConfigSpec.BooleanValue DIAMOND_PICKAXE_MODULE__IS_ALLOWED =
            DIAMOND_PICKAXE_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE =
            DIAMOND_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            DIAMOND_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_BASE =
            DIAMOND_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            DIAMOND_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    private static final ModConfigSpec.Builder NETHERITE_PICKAXE_MODULE_BUILDER = DIAMOND_PICKAXE_MODULE_BUILDER
            .pop() // Diamond Pickaxe
            .push("Netherite Pickaxe");

    private static final ModConfigSpec.BooleanValue NETHERITE_PICKAXE_MODULE__IS_ALLOWED =
            NETHERITE_PICKAXE_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE =
            NETHERITE_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            NETHERITE_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_BASE =
            NETHERITE_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            NETHERITE_PICKAXE_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    //  Shovel
    private static final ModConfigSpec.Builder STONE_SHOVEL_MODULE_BUILDER = NETHERITE_PICKAXE_MODULE_BUILDER
            .pop() // Netherite Pickaxe
            .pop() // Pickaxes
            .push("Shovels")
            .push("Stone Shovel Module");

    private static final ModConfigSpec.BooleanValue STONE_SHOVEL_MODULE__IS_ALLOWED =
            STONE_SHOVEL_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue STONE_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE =
            STONE_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue STONE_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            STONE_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue STONE_SHOVEL_MODULE__HARVEST_SPEED_BASE =
            STONE_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue STONE_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            STONE_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Iron
    private static final ModConfigSpec.Builder IRON_SHOVEL_MODULE_BUILDER = STONE_SHOVEL_MODULE_BUILDER
            .pop() // Stone
            .push("Iron Shovel Module");

    private static final ModConfigSpec.BooleanValue IRON_SHOVEL_MODULE__IS_ALLOWED =
            IRON_SHOVEL_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue IRON_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE =
            IRON_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue IRON_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            IRON_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue IRON_SHOVEL_MODULE__HARVEST_SPEED_BASE =
            IRON_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue IRON_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            IRON_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Diamond Shovel
    private static final ModConfigSpec.Builder DIAMOND_SHOVEL_MODULE_BUILDER = IRON_SHOVEL_MODULE_BUILDER
            .pop() // Diamond Shovel
            .push("Diamond Shovel");

    private static final ModConfigSpec.BooleanValue DIAMOND_SHOVEL_MODULE__IS_ALLOWED =
            DIAMOND_SHOVEL_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue DIAMOND_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE =
            DIAMOND_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            DIAMOND_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_SHOVEL_MODULE__HARVEST_SPEED_BASE =
            DIAMOND_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue DIAMOND_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            DIAMOND_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    private static final ModConfigSpec.Builder NETHERITE_SHOVEL_MODULE_BUILDER = DIAMOND_SHOVEL_MODULE_BUILDER
            .pop() // Diamond Shovel
            .push("Netherite Shovel");

    private static final ModConfigSpec.BooleanValue NETHERITE_SHOVEL_MODULE__IS_ALLOWED =
            NETHERITE_SHOVEL_MODULE_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue NETHERITE_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE =
            NETHERITE_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER =
            NETHERITE_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_SHOVEL_MODULE__HARVEST_SPEED_BASE =
            NETHERITE_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue NETHERITE_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER =
            NETHERITE_SHOVEL_MODULE_BUILDER
                    .defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);














    // Flint and Steel
    private static final ModConfigSpec.Builder FLINT_AND_STEEL_MODULE__SETTINGS_BUILDER = NETHERITE_SHOVEL_MODULE_BUILDER
            .pop() // Netherite Shovel
            .pop() // Shovels
            .push("Miscellaneous")
            .push("Flint and Steel");

    private static final ModConfigSpec.BooleanValue FLINT_AND_STEEL__IS_ALLOWED =
            FLINT_AND_STEEL_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    // FIXME


    // Leaf Blower
    private static final ModConfigSpec.Builder LEAF_BLOWER_MODULE__SETTINGS_BUILDER = FLINT_AND_STEEL_MODULE__SETTINGS_BUILDER
            .pop() // Tool
            .push("Leaf Blower Module");

    private static final ModConfigSpec.BooleanValue LEAF_BLOWER_MODULE__IS_ALLOWED =
            LEAF_BLOWER_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);
// FIXME
/*
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
*/


    // Lux Capacitor
    private static final ModConfigSpec.Builder LUX_CAPACITOR_MODULE__SETTINGS_BUILDER = LEAF_BLOWER_MODULE__SETTINGS_BUILDER
            .pop() // Leaf Blower
            .push("Lux Capacitor Module");

    private static final ModConfigSpec.BooleanValue LUX_CAPACITOR_MODULE__IS_ALLOWED =
            LUX_CAPACITOR_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue LUX_CAPACITOR_MODULE__ENERGY_CONSUMPTION_BASE =
            LUX_CAPACITOR_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 1000, 0, 100000.0D);

    // Vision ---------------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder VISION_MODULE__SETTINGS_BUILDER = LUX_CAPACITOR_MODULE__SETTINGS_BUILDER
            .pop() // Lux Capacitor
            .pop() // Misc
            .pop() // Tool
            .push("Vision Modules");

    // Binnoculars
    private static final ModConfigSpec.Builder BINOCULARS_MODULE__SETTINGS_BUILDER = VISION_MODULE__SETTINGS_BUILDER
            .push("Binnoculars");

    private static final ModConfigSpec.BooleanValue BINOCULARS_MODULE__IS_ALLOWED =
            BINOCULARS_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue BINOCULARS_MODULE__FOV_BASE =
            BINOCULARS_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.FOV_BASE, 0.5, 0, 100.0D);

    private static final ModConfigSpec.DoubleValue BINOCULARS_MODULE__FOV_FIELD_OF_VIEW_MULITIPLIER =
            BINOCULARS_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.FOV_FIELD_OF_VIEW_MULITIPLIER, 9.5, 0, 100.0D);

    // Night Vision
    private static final ModConfigSpec.Builder NIGHT_VISION_MODULE__SETTINGS_BUILDER = BINOCULARS_MODULE__SETTINGS_BUILDER
            .pop() // Binoculars
            .push("Night Vision");

    private static final ModConfigSpec.BooleanValue NIGHT_VISION_MODULE__IS_ALLOWED =
            NIGHT_VISION_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);


    // Weapon ---------------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder WEAPON_MODULE__SETTINGS_BUILDER = NIGHT_VISION_MODULE__SETTINGS_BUILDER
            .pop() // Night Vision
            .pop() // Vision
            .push("Weapon Modules");

    // Blade Launcher
    private static final ModConfigSpec.Builder BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER = WEAPON_MODULE__SETTINGS_BUILDER
            .push("Blade Launcher");

    private static final ModConfigSpec.BooleanValue BLADE_LAUNCHER_MODULE__IS_ALLOWED =
            BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue BLADE_LAUNCHER_MODULE__ENERGY_CONSUMPTION_BASE =
            BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue BLADE_LAUNCHER_MODULE__SPINNING_BLADE_DAMAGE =
            BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.BLADE_DAMAGE, 6, 0, 100000.0D);

    // Lightning Summoner
    private static final ModConfigSpec.Builder LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER = BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER
            .pop() // Blade Launcer
            .push("Lightning Summoner Launcher");

    private static final ModConfigSpec.BooleanValue LIGHTNING_SUMMONER_MODULE__IS_ALLOWED =
            LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue LIGHTNING_SUMMONER__ENERGY_CONSUMPTION_BASE =
            LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 4900000.0, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue LIGHTNING_SUMMONER__HEAT_EMISSION =
            LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.HEAT_EMISSION, 100, 0, 100000.0D);

    // Melee Assist
    private static final ModConfigSpec.Builder MELEE_ASSIST_MODULE__SETTINGS_BUILDER = LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER
            .pop() // Blade Launcher
            .push("Melee Assist");

    private static final ModConfigSpec.BooleanValue MELEE_ASSIST_MODULE__IS_ALLOWED =
            MELEE_ASSIST_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__ENERGY_CONSUMPTION_BASE =
            MELEE_ASSIST_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 10.0, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__ENERGY_CONSUMPTION_IMPACT_MULTIPLIER =
            MELEE_ASSIST_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_IMPACT_MULTIPLIER, 1000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__ENERGY_CONSUMPTION_CARRY_THROUGH_MULTIPLIER =
            MELEE_ASSIST_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_CARRY_THROUGH_MULTIPLIER, 200, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__PUNCH_DAMAGE_BASE =
            MELEE_ASSIST_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.PUNCH_DAMAGE_BASE, 2, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__PUNCH_DAMAGE_IMPACT_MULTIPLIER =
            MELEE_ASSIST_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.PUNCH_DAMAGE_IMPACT_MULTIPLIER, 2, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__PUNCH_KNOCKBACK_CARRY_THROUGH_MULTIPLIER =
            MELEE_ASSIST_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.PUNCH_KNOCKBACK_CARRY_THROUGH_MULTIPLIER, 1, 0, 100000.0D);

    // Plasma Cannon
    private static final ModConfigSpec.Builder PLASMA_CANNON_MODULE__SETTINGS_BUILDER = MELEE_ASSIST_MODULE__SETTINGS_BUILDER
            .pop() // Melee Assist
            .push("Plasma Cannon");

    private static final ModConfigSpec.BooleanValue PLASMA_CANNON_MODULE__IS_ALLOWED =
            PLASMA_CANNON_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__ENERGY_PER_TICK_BASE =
            PLASMA_CANNON_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_PER_TICK_BASE, 100, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__ENERGY_PER_TICK_VOLTAGE_MULTIPLIER =
            PLASMA_CANNON_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_PER_TICK_VOLTAGE_MULTIPLIER, 500, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__ENERGY_PER_TICK_AMPERAGE_MULTIPLIER =
            PLASMA_CANNON_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_PER_TICK_AMPERAGE_MULTIPLIER, 1500, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__DAMAGE_AT_FULL_CHARGE_BASE =
            PLASMA_CANNON_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE_BASE, 2, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__DAMAGE_AT_FULL_CHARGE_AMPERAGE_MULTIPLIER =
            PLASMA_CANNON_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE_AMPERAGE_MULTIPLIER, 38, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__EXPLOSIVENESS_VOLTAGE_MULTIPLIER =
            PLASMA_CANNON_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS_VOLTAGE_MULTIPLIER, 0.5, 0, 100000.0D);

    // RailGun
    private static final ModConfigSpec.Builder RAILGUN_MODULE__SETTINGS_BUILDER = PLASMA_CANNON_MODULE__SETTINGS_BUILDER
            .pop() // Melee Assist
            .push("Railgun");

    private static final ModConfigSpec.BooleanValue RAILGUN_MODULE__IS_ALLOWED =
            RAILGUN_MODULE__SETTINGS_BUILDER
                    .define(NuminaConstants.CONFIG_IS_ALLOWED, true);


    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__ENERGY_CONSUMPTION_BASE=
            RAILGUN_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__ENERGY_CONSUMPTION_VOLTAGE_MULTIPLIER =
            RAILGUN_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.ENERGY_CONSUMPTION_VOLTAGE_MULTIPLIER, 25000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__TOTAL_IMPULSE_BASE=
            RAILGUN_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.TOTAL_IMPULSE_BASE, 500, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__TOTAL_IMPULSE_VOLTAGE_MULTIPLIER =
            RAILGUN_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.TOTAL_IMPULSE_VOLTAGE_MULTIPLIER, 25000, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__HEAT_EMISSION_BASE=
            RAILGUN_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.HEAT_EMMISSION_BASE, 5, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__HEAT_EMISSION_VOLTAGE_MULTIPLIER =
            RAILGUN_MODULE__SETTINGS_BUILDER
                    .defineInRange(MPSConstants.HEAT_EMMISSION_BASE, 10, 0, 100000.0D);


    public static final ModConfigSpec COMMON_SPEC = RAILGUN_MODULE__SETTINGS_BUILDER.build();

    /* General ---------------------------------------------------------------- */
    public static double maxFlyingSpeed;

    /* Armor ------------------------------------------------------------------ */
    public static int armorHelmInventorySlots1;
    public static double armorHelmMaxHeat1;

    public static int armorChestPlateInventorySlots1;
    public static Double armorChestPlateMaxHeat1;

    public static int armorLeggingsInventorySlots1;
    public static Double armorLeggingsMaxHeat1;

    public static int armorBootsInventorySlots1;
    public static Double armorBootsMaxHeat1;

    public static int armorHelmInventorySlots2;
    public static Double armorHelmMaxHeat2;

    public static int armorChestPlateInventorySlots2;
    public static Double armorChestPlateMaxHeat2;

    public static int armorLeggingsInventorySlots2;
    public static Double armorLeggingsMaxHeat2;

    public static int armorBootsInventorySlots2;
    public static Double armorBootsMaxHeat2;

    public static int armorHelmInventorySlots3;
    public static Double armorHelmMaxHeat3;

    public static int armorChestPlateInventorySlots3;
    public static Double armorChestPlateMaxHeat3;

    public static int armorLeggingsInventorySlots3;
    public static Double armorLeggingsMaxHeat3;

    public static int armorBootsInventorySlots3;
    public static Double armorBootsMaxHeat3;

    public static int armorHelmInventorySlots4;
    public static Double armorHelmMaxHeat4;

    public static int armorChestPlateInventorySlots4;
    public static Double armorChestPlateMaxHeat4;

    public static int armorLeggingsInventorySlots4;
    public static Double armorLeggingsMaxHeat4;

    public static int armorBootsInventorySlots4;
    public static Double armorBootsMaxHeat4;

    // Power Fist -------------------------------------------------------------
    public static int powerFistInventorySlots1;
    public static double powerFistMaxHeat1;

    public static int powerFistInventorySlots2;
    public static double powerFistMaxHeat2;

    public static int powerFistInventorySlots3;
    public static double powerFistMaxHeat3;

    public static int powerFistInventorySlots4;
    public static double powerFistMaxHeat4;


    /* Modules ---------------------------------------------------------------- */
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

    // Cosmetic ---------------------------------------------------------------
    public static boolean isTransparentArmorAllowed;





    // VISION -----------------------------------------------------------------
    // Binoculars
    public static boolean binocularsModuleIsAllowed;
    public static double binocularsModuleFOVBase;
    public static double binocularsModuleFOVFieldOfViewMultiplier;
    // Night Vision
    public static boolean nightVisionModuleIsAllowed;

    // TOOL -------------------------------------------------------------------
    // Stone axe
    public static boolean stoneAxeModuleIsAllowed;
    public static double stoneAxeModuleEnergyConsumptionBase;
    public static double stoneAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double stoneAxeModuleHarvestSpeedBase;
    public static double stoneAxeModuleHarvestSpeedOverclockMultiplier;

    // Iron axe
    public static boolean ironAxeModuleIsAllowed;
    public static double ironAxeModuleEnergyConsumptionBase;
    public static double ironAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double ironAxeModuleHarvestSpeedBase;
    public static double ironAxeModuleHarvestSpeedOverclockMultiplier;

    // Diamond axe
    public static boolean diamondAxeModuleIsAllowed;
    public static double diamondAxeModuleEnergyConsumptionBase;
    public static double diamondAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double diamondAxeModuleHarvestSpeedBase;
    public static double diamondAxeModuleHarvestSpeedOverclockMultiplier;

    // Netherite axe
    public static boolean netheriteAxeModuleIsAllowed;
    public static double netheriteAxeModuleEnergyConsumptionBase;
    public static double netheriteAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double netheriteAxeModuleHarvestSpeedBase;
    public static double netheriteAxeModuleHarvestSpeedOverclockMultiplier;

    // Stone Pickaxe
    public static boolean stonePickAxeModuleIsAllowed;
    public static double stonePickAxeModuleEnergyConsumptionBase;
    public static double stonePickAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double stonePickAxeModuleHarvestSpeedBase;
    public static double stonePickAxeModuleHarvestSpeedOverclockMultiplier;

    // Iron Pickaxe
    public static boolean ironPickAxeModuleIsAllowed;
    public static double ironPickAxeModuleEnergyConsumptionBase;
    public static double ironPickAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double ironPickAxeModuleHarvestSpeedBase;
    public static double ironPickAxeModuleHarvestSpeedOverclockMultiplier;

    // Diamond Pickaxe
    public static boolean diamondPickAxeModuleIsAllowed;
    public static double diamondPickAxeModuleEnergyConsumptionBase;
    public static double diamondPickAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double diamondPickAxeModuleHarvestSpeedBase;
    public static double diamondPickAxeModuleHarvestSpeedOverclockMultiplier;

    // Netherite Pickaxe
    public static boolean netheritePickAxeModuleIsAllowed;
    public static double netheritePickAxeModuleEnergyConsumptionBase;
    public static double netheritePickAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double netheritePickAxeModuleHarvestSpeedBase;
    public static double netheritePickAxeModuleHarvestSpeedOverclockMultiplier;


    // Stone Shovel
    public static boolean stoneShovelModuleIsAllowed;
    public static double stoneShovelModuleEnergyConsumptionBase;
    public static double stoneShovelModuleEnergyConsumptionOverclockMultiplier;
    public static double stoneShovelModuleHarvestSpeedBase;
    public static double stoneShovelModuleHarvestSpeedOverclockMultiplier;

    // Iron Shovel
    public static boolean ironShovelModuleIsAllowed;
    public static double ironShovelModuleEnergyConsumptionBase;
    public static double ironShovelModuleEnergyConsumptionOverclockMultiplier;
    public static double ironShovelModuleHarvestSpeedBase;
    public static double ironShovelModuleHarvestSpeedOverclockMultiplier;

    // Diamond Shovel
    public static boolean diamondShovelModuleIsAllowed;
    public static double diamondShovelModuleEnergyConsumptionBase;
    public static double diamondShovelModuleEnergyConsumptionOverclockMultiplier;
    public static double diamondShovelModuleHarvestSpeedBase;
    public static double diamondShovelModuleHarvestSpeedOverclockMultiplier;

    // Netherite Shovel
    public static boolean netheriteShovelModuleIsAllowed;
    public static double netheriteShovelModuleEnergyConsumptionBase;
    public static double netheriteShovelModuleEnergyConsumptionOverclockMultiplier;
    public static double netheriteShovelModuleHarvestSpeedBase;
    public static double netheriteShovelModuleHarvestSpeedOverclockMultiplier;

    // Lux Capacitor
    public static boolean luxCapacitorModuleIsAllowed;
    public static double luxCapacitorEnergyConsumptionBase;

    // WEAPON -----------------------------------------------------------------
    // Blade Launcher
    public static boolean bladeLauncherIsAllowed;
    public static double bladeLauncherEnergyConsumption;
    public static double bladeLauncherSpinningBladeDamage;

    // Lightning Summoner
    public static boolean lightningSummonerIsAllowed;
    public static double lightningSummonerEnergyConsumption;
    public static double lightningSummonerHeatEmission;

    // Melee Assist
    public static boolean meleeAssistIsAllowed;
    public static double meleeAssistEnergyConsumptionBase;
    public static double meleeAssistEnergyConsumptionImpactMultiplier;
    public static double meleeAssistEnergyConsumptionCarryThroughMultiplier;
    public static double meleeAssistPunchDamageImpactMultiplier;
    public static double meleeAssistPunchDamageBase;
    public static double meleeAssistPunchKnockBackCarryThroughMultiplier;

    // Plasma Cannon
    public static boolean plasmaCannonIsAllowed;
    public static double plasmaCannonEnergyPerTickBase;
    public static double plasmaCannonEnergyPerTickVoltageMultiplier;
    public static double plasmaCannonEnergyPerTickAmperageMultiplier;
    public static double plasmaCannonDamageAtFullChargeBase;
    public static double plasmaCannonDamageAtFullChargeAmperageMultiplier;
    public static double plasmaCannonExplosivenessVoltageMultiplier;

    // Railgun
    public static boolean railgunIsAllowed;
    public static double railgunEnergyConsumptionBase;
    public static double railgunEnergyConsumptionVoltageMultiplier;
    public static double railgunTotalImpulseBase;
    public static double railgunTotalImpulseVoltageMultiplier;
    public static double railgunHeatEmissionBase;
    public static double railgunHeatEmissionVoltageMultiplier;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == COMMON_SPEC) {
            // General --------------------------------------------------------
            maxFlyingSpeed = GENERAL_MAX_FLYING_SPEED.get();



            // ARMOR ----------------------------------------------------------
            // Tier1
            armorHelmInventorySlots1 = ARMOR_HELM_1_INVENTORY_SLOTS.get();
            armorHelmMaxHeat1 = ARMOR_HELM_1__MAX_HEAT.get();

            armorChestPlateInventorySlots1 = ARMOR_CHESTPLATE_1__INVENTORY_SLOTS.get();
            armorChestPlateMaxHeat1 = ARMOR_CHESTPLATE_1__MAX_HEAT.get();

            armorLeggingsInventorySlots1 = ARMOR_LEGGINGS_1__INVENTORY_SLOTS.get();
            armorLeggingsMaxHeat1 = ARMOR_LEGGINGS_1__MAX_HEAT.get();

            armorBootsInventorySlots1 = ARMOR_BOOTS_1__INVENTORY_SLOTS.get();
            armorBootsMaxHeat1 = ARMOR_BOOTS_1__MAX_HEAT.get();

            // Tier 2
            armorHelmInventorySlots2 = ARMOR_HELM_2__INVENTORY_SLOTS.get();
            armorHelmMaxHeat2 = ARMOR_HELM_2__MAX_HEAT.get();

            armorChestPlateInventorySlots2 = ARMOR_CHESTPLATE_2__INVENTORY_SLOTS.get();
            armorChestPlateMaxHeat2 = ARMOR_CHESTPLATE_2__MAX_HEAT.get();

            armorLeggingsInventorySlots2 = ARMOR_LEGGINGS_2__INVENTORY_SLOTS.get();
            armorLeggingsMaxHeat2 = ARMOR_LEGGINGS_2__MAX_HEAT.get();

            armorBootsInventorySlots2 = ARMOR_BOOTS_2__INVENTORY_SLOTS.get();
            armorBootsMaxHeat2 = ARMOR_BOOTS_2__MAX_HEAT.get();

            // Tier 3
            armorHelmInventorySlots3 = ARMOR_HELM_3__INVENTORY_SLOTS.get();
            armorHelmMaxHeat3 = ARMOR_HELM_3__MAX_HEAT.get();

            armorChestPlateInventorySlots3 = ARMOR_CHESTPLATE_3__INVENTORY_SLOTS.get();
            armorChestPlateMaxHeat3 = ARMOR_CHESTPLATE_3__MAX_HEAT.get();

            armorLeggingsInventorySlots3 = ARMOR_LEGGINGS_3__INVENTORY_SLOTS.get();
            armorLeggingsMaxHeat3 = ARMOR_LEGGINGS_3__MAX_HEAT.get();

            armorBootsInventorySlots3 = ARMOR_BOOTS_3__INVENTORY_SLOTS.get();
            armorBootsMaxHeat3 = ARMOR_BOOTS_3__MAX_HEAT.get();

            // Tier 4
            armorHelmInventorySlots4 = ARMOR_HELM_4__INVENTORY_SLOTS.get();
            armorHelmMaxHeat4 = ARMOR_HELMET_4__MAX_HEAT.get();

            armorChestPlateInventorySlots4 = ARMOR_CHESTPLATE_4__INVENTORY_SLOTS.get();
            armorChestPlateMaxHeat4 = ARMOR_CHESTPLATE_4__MAX_HEAT.get();

            armorLeggingsInventorySlots4 = ARMOR_LEGGINGS_4__INVENTORY_SLOTS.get();
            armorLeggingsMaxHeat4 = ARMOR_LEGGINGS_4__MAX_HEAT.get();

            armorBootsInventorySlots4 = ARMOR_BOOTS_4__INVENTORY_SLOTS.get();
            armorBootsMaxHeat4 = ARMOR_BOOTS_4__MAX_HEAT.get();

            // Power Fist -----------------------------------------------------
            powerFistInventorySlots1 = POWER_FIST_1__INVENTORY_SLOTS.get();
            powerFistMaxHeat1 = POWER_FIST_1__MAX_HEAT.get();

            powerFistInventorySlots2 = POWER_FIST_2__INVENTORY_SLOTS.get();
            powerFistMaxHeat2 = POWER_FIST_2__MAX_HEAT.get();

            powerFistInventorySlots3 = POWER_FIST_3__INVENTORY_SLOTS.get();
            powerFistMaxHeat3 = POWER_FIST_TIER_3__MAX_HEAT.get();

            powerFistInventorySlots4 = POWER_FIST_4__INVENTORY_SLOTS.get();
            powerFistMaxHeat4 = POWER_FIST_4__MAX_HEAT.get();


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
            energyShieldIsAllowed = ENERGY_SHIELD_MODULE__IS_ALLOWED.get();
            energyShieldFieldStrengthMultiplier = ENERGY_SHIELD__FIELD_STRENGTH_MULTIPLIER.get();
//            energyShieldEnergyConsumptionMultiplier;
//            energyShieldMaxHeatMultiplier;
//            energyShieldKnockBackResistanceMultiplier;
//            energyShieldToughnessMultiplier;

            // Cosmetic -------------------------------------------------------
            // Transparent Armor
            isTransparentArmorAllowed = TRANSPARENT_ARMOR_MODULE__IS_ALLOWED.get();


            // VISION ---------------------------------------------------------





            // TOOL -----------------------------------------------------------
            // Stone Axe
            stoneAxeModuleIsAllowed = STONE_AXE_MODULE__IS_ALLOWED.get();
            stoneAxeModuleEnergyConsumptionBase = STONE_AXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            stoneAxeModuleEnergyConsumptionOverclockMultiplier = STONE_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            stoneAxeModuleHarvestSpeedBase = STONE_AXE_MODULE__HARVEST_SPEED_BASE.get();
            stoneAxeModuleHarvestSpeedOverclockMultiplier = STONE_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Iron Axe
            ironAxeModuleIsAllowed = IRON_AXE_MODULE__IS_ALLOWED.get();
            ironAxeModuleEnergyConsumptionBase = IRON_AXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            ironAxeModuleEnergyConsumptionOverclockMultiplier = IRON_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            ironAxeModuleHarvestSpeedBase = IRON_AXE_MODULE__HARVEST_SPEED_BASE.get();
            ironAxeModuleHarvestSpeedOverclockMultiplier = IRON_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Diamond Axe
            diamondAxeModuleIsAllowed = DIAMOND_AXE_MODULE__IS_ALLOWED.get();
            diamondAxeModuleEnergyConsumptionBase = DIAMOND_AXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            diamondAxeModuleEnergyConsumptionOverclockMultiplier = DIAMOND_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            diamondAxeModuleHarvestSpeedBase = DIAMOND_AXE_MODULE__HARVEST_SPEED_BASE.get();
            diamondAxeModuleHarvestSpeedOverclockMultiplier = DIAMOND_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Netherite Axe
            netheriteAxeModuleIsAllowed = NETHERITE_AXE_MODULE__IS_ALLOWED.get();
            netheriteAxeModuleEnergyConsumptionBase = NETHERITE_AXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            netheriteAxeModuleEnergyConsumptionOverclockMultiplier = NETHERITE_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            netheriteAxeModuleHarvestSpeedBase = NETHERITE_AXE_MODULE__HARVEST_SPEED_BASE.get();
            netheriteAxeModuleHarvestSpeedOverclockMultiplier = NETHERITE_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Stone Pickaxe
            stonePickAxeModuleIsAllowed = STONE_PICKAXE_MODULE__IS_ALLOWED.get();
            stonePickAxeModuleEnergyConsumptionBase = STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            stonePickAxeModuleEnergyConsumptionOverclockMultiplier = STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            stonePickAxeModuleHarvestSpeedBase = STONE_PICKAXE_MODULE__HARVEST_SPEED_BASE.get();
            stonePickAxeModuleHarvestSpeedOverclockMultiplier = STONE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Iron Pickaxe
            ironPickAxeModuleIsAllowed = IRON_PICKAXE_MODULE__IS_ALLOWED.get();
            ironPickAxeModuleEnergyConsumptionBase = IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            ironPickAxeModuleEnergyConsumptionOverclockMultiplier = IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            ironPickAxeModuleHarvestSpeedBase = IRON_PICKAXE_MODULE__HARVEST_SPEED_BASE.get();
            ironPickAxeModuleHarvestSpeedOverclockMultiplier = IRON_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Diamond Pickaxe
            diamondPickAxeModuleIsAllowed = DIAMOND_PICKAXE_MODULE__IS_ALLOWED.get();
            diamondPickAxeModuleEnergyConsumptionBase = DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            diamondPickAxeModuleEnergyConsumptionOverclockMultiplier = DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            diamondPickAxeModuleHarvestSpeedBase = DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_BASE.get();
            diamondPickAxeModuleHarvestSpeedOverclockMultiplier = DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Netherite Pickaxe
            netheritePickAxeModuleIsAllowed = NETHERITE_PICKAXE_MODULE__IS_ALLOWED.get();
            netheritePickAxeModuleEnergyConsumptionBase = NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            netheritePickAxeModuleEnergyConsumptionOverclockMultiplier = NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            netheritePickAxeModuleHarvestSpeedBase = NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_BASE.get();
            netheritePickAxeModuleHarvestSpeedOverclockMultiplier = NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Stone Shovel
            stoneShovelModuleIsAllowed = STONE_SHOVEL_MODULE__IS_ALLOWED.get();
            stoneShovelModuleEnergyConsumptionBase = STONE_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE.get();
            stoneShovelModuleEnergyConsumptionOverclockMultiplier = STONE_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            stoneShovelModuleHarvestSpeedBase = STONE_SHOVEL_MODULE__HARVEST_SPEED_BASE.get();
            stoneShovelModuleHarvestSpeedOverclockMultiplier = STONE_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Iron Shovel
            ironShovelModuleIsAllowed = IRON_SHOVEL_MODULE__IS_ALLOWED.get();
            ironShovelModuleEnergyConsumptionBase = IRON_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE.get();
            ironShovelModuleEnergyConsumptionOverclockMultiplier = IRON_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            ironShovelModuleHarvestSpeedBase = IRON_SHOVEL_MODULE__HARVEST_SPEED_BASE.get();
            ironShovelModuleHarvestSpeedOverclockMultiplier = IRON_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Diamond Shovel
            diamondShovelModuleIsAllowed = DIAMOND_SHOVEL_MODULE__IS_ALLOWED.get();
            diamondShovelModuleEnergyConsumptionBase = DIAMOND_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE.get();
            diamondShovelModuleEnergyConsumptionOverclockMultiplier = DIAMOND_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            diamondShovelModuleHarvestSpeedBase = DIAMOND_SHOVEL_MODULE__HARVEST_SPEED_BASE.get();
            diamondShovelModuleHarvestSpeedOverclockMultiplier = DIAMOND_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Netherite Shovel
            netheriteShovelModuleIsAllowed = NETHERITE_SHOVEL_MODULE__IS_ALLOWED.get();
            netheriteShovelModuleEnergyConsumptionBase = NETHERITE_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE.get();
            netheriteShovelModuleEnergyConsumptionOverclockMultiplier = NETHERITE_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            netheriteShovelModuleHarvestSpeedBase = NETHERITE_SHOVEL_MODULE__HARVEST_SPEED_BASE.get();
            netheriteShovelModuleHarvestSpeedOverclockMultiplier = NETHERITE_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();




            // Lux Capacitor
            luxCapacitorModuleIsAllowed = LUX_CAPACITOR_MODULE__IS_ALLOWED.get();
            luxCapacitorEnergyConsumptionBase = LUX_CAPACITOR_MODULE__ENERGY_CONSUMPTION_BASE.get();

            // VISION ---------------------------------------------------------
            // Binoculars
            binocularsModuleIsAllowed = BINOCULARS_MODULE__IS_ALLOWED.get();
            binocularsModuleFOVBase = BINOCULARS_MODULE__FOV_BASE.get();
            binocularsModuleFOVFieldOfViewMultiplier = BINOCULARS_MODULE__FOV_FIELD_OF_VIEW_MULITIPLIER.get();
            // Night Vision
            nightVisionModuleIsAllowed = NIGHT_VISION_MODULE__IS_ALLOWED.get();

            // WEAPON ---------------------------------------------------------
            // Blade Launcher
            bladeLauncherIsAllowed = BLADE_LAUNCHER_MODULE__IS_ALLOWED.get();
            bladeLauncherEnergyConsumption = BLADE_LAUNCHER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            bladeLauncherSpinningBladeDamage = BLADE_LAUNCHER_MODULE__SPINNING_BLADE_DAMAGE.get();

            // Lightining Summoner
            lightningSummonerIsAllowed = LIGHTNING_SUMMONER_MODULE__IS_ALLOWED.get();
            lightningSummonerEnergyConsumption = LIGHTNING_SUMMONER__ENERGY_CONSUMPTION_BASE.get();
            lightningSummonerHeatEmission = LIGHTNING_SUMMONER__HEAT_EMISSION.get();

            // Melee Assist
            meleeAssistIsAllowed = MELEE_ASSIST_MODULE__IS_ALLOWED.get();
            meleeAssistEnergyConsumptionBase = MELEE_ASSIST__ENERGY_CONSUMPTION_BASE.get();
            meleeAssistEnergyConsumptionImpactMultiplier = MELEE_ASSIST__ENERGY_CONSUMPTION_IMPACT_MULTIPLIER.get();
            meleeAssistEnergyConsumptionCarryThroughMultiplier = MELEE_ASSIST__ENERGY_CONSUMPTION_CARRY_THROUGH_MULTIPLIER.get();
            meleeAssistPunchDamageImpactMultiplier = MELEE_ASSIST__PUNCH_DAMAGE_IMPACT_MULTIPLIER.get();
            meleeAssistPunchDamageBase = MELEE_ASSIST__PUNCH_DAMAGE_BASE.get();
            meleeAssistPunchKnockBackCarryThroughMultiplier = MELEE_ASSIST__PUNCH_KNOCKBACK_CARRY_THROUGH_MULTIPLIER.get();

            // Plasma Cannon
            plasmaCannonIsAllowed = PLASMA_CANNON_MODULE__IS_ALLOWED.get();
            plasmaCannonEnergyPerTickBase = PLASMA_CANNON__ENERGY_PER_TICK_BASE.get();
            plasmaCannonEnergyPerTickVoltageMultiplier = PLASMA_CANNON__ENERGY_PER_TICK_VOLTAGE_MULTIPLIER.get();
            plasmaCannonEnergyPerTickAmperageMultiplier = PLASMA_CANNON__ENERGY_PER_TICK_AMPERAGE_MULTIPLIER.get();
            plasmaCannonDamageAtFullChargeBase = PLASMA_CANNON__DAMAGE_AT_FULL_CHARGE_BASE.get();
            plasmaCannonDamageAtFullChargeAmperageMultiplier = PLASMA_CANNON__DAMAGE_AT_FULL_CHARGE_AMPERAGE_MULTIPLIER.get();
            plasmaCannonExplosivenessVoltageMultiplier = PLASMA_CANNON__EXPLOSIVENESS_VOLTAGE_MULTIPLIER.get();

            // Railgun
            railgunIsAllowed = RAILGUN_MODULE__IS_ALLOWED.get();
            railgunEnergyConsumptionBase = RAILGUN_MODULE__ENERGY_CONSUMPTION_BASE.get();
            railgunEnergyConsumptionVoltageMultiplier = RAILGUN_MODULE__ENERGY_CONSUMPTION_VOLTAGE_MULTIPLIER.get();
            railgunTotalImpulseBase = RAILGUN_MODULE__TOTAL_IMPULSE_BASE.get();
            railgunTotalImpulseVoltageMultiplier = RAILGUN_MODULE__TOTAL_IMPULSE_VOLTAGE_MULTIPLIER.get();
            railgunHeatEmissionBase = RAILGUN_MODULE__HEAT_EMISSION_BASE.get();
            railgunHeatEmissionVoltageMultiplier = RAILGUN_MODULE__HEAT_EMISSION_VOLTAGE_MULTIPLIER.get();
        }
    }
}