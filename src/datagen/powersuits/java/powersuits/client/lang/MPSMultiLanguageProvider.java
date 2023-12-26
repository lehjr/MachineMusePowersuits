package powersuits.client.lang;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSItems;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import numina.client.config.DatagenConfig;
import numina.client.util.lang.MulitiLanguageProvider;
import numina.client.util.lang.translators.ITranslator;
import numina.client.util.lang.translators.Language;

import java.io.IOException;

public class MPSMultiLanguageProvider extends MulitiLanguageProvider {
    Language mainLanguage;
    public MPSMultiLanguageProvider(DataGenerator gen, String modid, DatagenConfig config, ITranslator translator) {
        super(gen, modid, config, modid, translator);
        this.mainLanguage = config.getMainLanguageCode();
    }

    @Override
    public void run(CachedOutput output) throws IOException {
        addItemGroup();
        addModularItems();
        addModules();
        addBlocks();
        addGui();
        addKeybinds();
        addModuleTradeoff();
        addModels();
        super.run(output);
    }

    void addItemGroup() {
        addTranslationTopAll("itemGroup." + MPSConstants.MOD_ID, "Modular Powersuits");
    }

    void addModularItems() {
        // Helmet --------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_HELMET.get(), mainLanguage, "Power Armor Helmet");

        // Chestplate ----------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_CHESTPLATE.get(), mainLanguage, "Power Armor Chestplate");

        // Leggings ------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_LEGGINGS.get(), mainLanguage, "Power Armor Leggings");

        // Boots ---------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_BOOTS.get(), mainLanguage, "Power Armor Boots");

        // Power Fist ----------------------------------------------------------------------------------
        add(MPSItems.POWER_FIST.get(), mainLanguage, "Power Fist");
    }

    void addModules() {
        // Armor =======================================================================================
        // Iron Plating --------------------------------------------------------------------------------
        add(MPSItems.IRON_PLATING_MODULE.get(), mainLanguage, "Iron Plating");

        addItemDescriptions(MPSItems.IRON_PLATING_MODULE.get(), mainLanguage, "Iron plating is heavy but offers a bit more protection.");

        // Diamond Plating -----------------------------------------------------------------------------
        add(MPSItems.DIAMOND_PLATING_MODULE.get(), mainLanguage, "Diamond Plating");

        addItemDescriptions(MPSItems.DIAMOND_PLATING_MODULE.get(),mainLanguage, "Diamond Plating is harder and more expensive to make, but offers better protection.");

        // Netherite Plating ---------------------------------------------------------------------------
        add(MPSItems.NETHERITE_PLATING_MODULE.get(), mainLanguage, "Netherite Plating");

        addItemDescriptions(MPSItems.NETHERITE_PLATING_MODULE.get(),mainLanguage, "Armor plating made from Netherite");

        // Energy Shield -------------------------------------------------------------------------------
        add(MPSItems.ENERGY_SHIELD_MODULE.get(), mainLanguage, "Energy Shield");

        addItemDescriptions(MPSItems.ENERGY_SHIELD_MODULE.get(), mainLanguage, "Energy shields are much lighter than plating, but consume energy.");

        // Cosmetic ====================================================================================
        // Active Camouflage ---------------------------------------------------------------------------
        add(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), mainLanguage, "Active Camouflage");

        addItemDescriptions(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), mainLanguage, "Emit a hologram of your surroundings to make yourself almost imperceptible.");

        // Energy Generation ===========================================================================
        // Coal Generator ------------------------------------------------------------------------------
        // TODO:
//        add(MPSItems.COAL_GENERATOR_MODULE.get(), mainLanguage, "Coal Generator");
//
//        addItemDescriptions(MPSItems.COAL_GENERATOR_MODULE.get(), mainLanguage, "Generate power with solid fuels");

        // Solar Generator -----------------------------------------------------------------------------
        add(MPSItems.SOLAR_GENERATOR_MODULE.get(), mainLanguage, "Solar Generator");

        addItemDescriptions(MPSItems.SOLAR_GENERATOR_MODULE.get(), mainLanguage, "Let the sun power your adventures.");

        // High Efficiency Solar Generator -------------------------------------------------------------
        add(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), mainLanguage, "High Efficiency Solar Generator");

        addItemDescriptions(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), mainLanguage, "A solar generator with 3 times the power generation of the standard solar generator.");

        // Kinetic Generator ---------------------------------------------------------------------------
        add(MPSItems.KINETIC_GENERATOR_MODULE.get(), mainLanguage, "Kinetic Generator");

        addItemDescriptions(MPSItems.KINETIC_GENERATOR_MODULE.get(), mainLanguage, "Generate power with your movement.");

        // Thermal Generator ---------------------------------------------------------------------------
        add(MPSItems.THERMAL_GENERATOR_MODULE.get(), mainLanguage, "Thermal Generator");

        addItemDescriptions(MPSItems.THERMAL_GENERATOR_MODULE.get(), mainLanguage, "Generate power from extreme amounts of heat.");

        // Environmental ===============================================================================
        // Auto Feeder ---------------------------------------------------------------------------------
        add(MPSItems.AUTO_FEEDER_MODULE.get(), mainLanguage, "Auto-Feeder");

        addItemDescriptions(MPSItems.AUTO_FEEDER_MODULE.get(), mainLanguage, "Whenever you're hungry, this module will grab the bottom-left-most food item from your inventory and feed it to you, storing the rest for later.");

        // Cooling System ------------------------------------------------------------------------------
        add(MPSItems.COOLING_SYSTEM_MODULE.get(), mainLanguage, "Cooling System");

        addItemDescriptions(MPSItems.COOLING_SYSTEM_MODULE.get(),  mainLanguage, "Cools down heat-producing modules quicker. Add a fluid tank module and fluid to enhance performance.");

        // Fluid Tank  ---------------------------------------------------------------------------------
        add(MPSItems.FLUID_TANK_MODULE.get(),  mainLanguage, "Fluid Tank");

        addItemDescriptions(MPSItems.FLUID_TANK_MODULE.get(), mainLanguage, "Stores fluid to enhance the performance of the cooling system.");

        // Mob Repulsor --------------------------------------------------------------------------------
        add(MPSItems.MOB_REPULSOR_MODULE.get(), mainLanguage, "Mob Repulsor");

        addItemDescriptions(MPSItems.MOB_REPULSOR_MODULE.get(), mainLanguage, "Pushes mobs away from you when activated, but constantly drains power. It is highly recommended that you set this module to a keybind because of the high energy draw.");

        // Water Electrolyzer --------------------------------------------------------------------------
        add(MPSItems.WATER_ELECTROLYZER_MODULE.get(), mainLanguage, "Water Electrolyzer");

        addItemDescriptions(MPSItems.WATER_ELECTROLYZER_MODULE.get(), mainLanguage, "When you run out of air, this module will jolt the water around you, electrolyzing a small bubble to breathe from.");

        // Mining Enchantments =========================================================================
        // Aqua Affinity -------------------------------------------------------------------------------
        add(MPSItems.AQUA_AFFINITY_MODULE.get(), mainLanguage, "Aqua Affinity");

        addItemDescriptions(MPSItems.AQUA_AFFINITY_MODULE.get(), mainLanguage, "Reduces the speed penalty for using your tool underwater.");

        // Silk Touch Enchantment ----------------------------------------------------------------------
        add(MPSItems.SILK_TOUCH_MODULE.get(), mainLanguage, "Silk Touch Enchantment");

        addItemDescriptions(MPSItems.SILK_TOUCH_MODULE.get(), mainLanguage, "A module that provides the Silk Touch enchantment");

        // Fortune Enchantment -------------------------------------------------------------------------
        add(MPSItems.FORTUNE_MODULE.get(), mainLanguage, "Fortune Enchantment");

        addItemDescriptions(MPSItems.FORTUNE_MODULE.get(), mainLanguage, "A module that provides the fortune enchantment.");

        // Mining Enhancements =========================================================================
        // Vein Miner ----------------------------------------------------------------------------------
        add(MPSItems.VEIN_MINER_MODULE.get(), mainLanguage, "Vein Miner");

        addItemDescriptions(MPSItems.VEIN_MINER_MODULE.get(), mainLanguage, "A module for mining ore veins");

        // Tunnel Bore ---------------------------------------------------------------------------------
        add(MPSItems.TUNNEL_BORE_MODULE.get(), mainLanguage, "Tunnel Bore");

        addItemDescriptions(MPSItems.TUNNEL_BORE_MODULE.get(), mainLanguage, "A module that enables the pickaxe module to mine 5x5 range blocks simultaneously.");

        // Selective Miner -----------------------------------------------------------------------------
        add(MPSItems.SELECTIVE_MINER.get(), mainLanguage, "Selective Miner");

        addItemDescriptions(MPSItems.SELECTIVE_MINER.get(), mainLanguage, "Breaks blocks similar to the vein miner, but selectively. Shift and click to select block type.");

        // Movement ====================================================================================
        // Blink Drive ---------------------------------------------------------------------------------
        add(MPSItems.BLINK_DRIVE_MODULE.get(), mainLanguage, "Blink Drive");

        addItemDescriptions(MPSItems.BLINK_DRIVE_MODULE.get(), mainLanguage, "Get from point A to point C via point B, where point B is a fold in space & time.");

        // Uphill Step Assist --------------------------------------------------------------------------
        add(MPSItems.CLIMB_ASSIST_MODULE.get(), mainLanguage, "Uphill Step Assist");

        addItemDescriptions(MPSItems.CLIMB_ASSIST_MODULE.get(), mainLanguage, "A pair of dedicated servos allow you to effortlessly step up 1m-high ledges.");

        // Dimensional Tear Generator ------------------------------------------------------------------
        add(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), mainLanguage, "Dimensional Tear Generator");

        addItemDescriptions(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), mainLanguage, "Generate a tear in the space-time continuum that will teleport the player to its relative coordinates in the nether or overworld.");

        // Flight Control ------------------------------------------------------------------------------
        add(MPSItems.FLIGHT_CONTROL_MODULE.get(), mainLanguage, "Flight Control");

        addItemDescriptions(MPSItems.FLIGHT_CONTROL_MODULE.get(), mainLanguage, "An integrated control circuit to help you fly better. Press Z to go down.");

        // Glider --------------------------------------------------------------------------------------
        add(MPSItems.GLIDER_MODULE.get(), mainLanguage, "Glider");

        addItemDescriptions(MPSItems.GLIDER_MODULE.get(),mainLanguage, "Tack on some wings to turn downward into forward momentum. Press sneak+forward while falling to activate.");

        // Jet Boots -----------------------------------------------------------------------------------
        add(MPSItems.JETBOOTS_MODULE.get(), mainLanguage, "Jet Boots");

        addItemDescriptions(MPSItems.JETBOOTS_MODULE.get(), mainLanguage, "Jet boots are not as strong as a jetpack, but they should at least be strong enough to counteract gravity.");

        // Jetpack -------------------------------------------------------------------------------------
        add(MPSItems.JETPACK_MODULE.get(), mainLanguage, "Jetpack");

        addItemDescriptions(MPSItems.JETPACK_MODULE.get(), mainLanguage, "A jetpack should allow you to jump indefinitely, or at least until you run out of power.");

        // Jump Assist ---------------------------------------------------------------------------------
        add(MPSItems.JUMP_ASSIST_MODULE.get(), mainLanguage, "Jump Assist");

        addItemDescriptions(MPSItems.JUMP_ASSIST_MODULE.get(), mainLanguage, "Another set of servo motors to help you jump higher.");

        // Parachute -----------------------------------------------------------------------------------
        add(MPSItems.PARACHUTE_MODULE.get(), mainLanguage, "Parachute");

        addItemDescriptions(MPSItems.PARACHUTE_MODULE.get(), mainLanguage, "Add a parachute to slow your descent. Activate by pressing sneak (defaults to Shift) in midair.");

        // Shock Absorber ------------------------------------------------------------------------------
        add(MPSItems.SHOCK_ABSORBER_MODULE.get(), mainLanguage, "Shock Absorber");

        addItemDescriptions(MPSItems.SHOCK_ABSORBER_MODULE.get(),mainLanguage, "With some servos, springs, and padding, you should be able to negate a portion of fall damage.");

        // Sprint Assist -------------------------------------------------------------------------------
        add(MPSItems.SPRINT_ASSIST_MODULE.get(), mainLanguage, "Sprint Assist");

        addItemDescriptions(MPSItems.SPRINT_ASSIST_MODULE.get(), mainLanguage, "A set of servo motors to help you sprint (double-tap forward) and walk faster.");

        // Swim Boost ----------------------------------------------------------------------------------
        add(MPSItems.SWIM_BOOST_MODULE.get(), mainLanguage, "Swim Boost");

        addItemDescriptions(MPSItems.SWIM_BOOST_MODULE.get(), mainLanguage, "By refitting an ion thruster for underwater use, you may be able to add extra forward (or backward) thrust when underwater.");

        // Special =====================================================================================
        // Transparent Armor ---------------------------------------------------------------------------
        add(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), mainLanguage, "Transparent Armor");

        addItemDescriptions(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), mainLanguage, "Make the item transparent, so you can show off your skin without losing armor.");

        // Magnet --------------------------------------------------------------------------------------
        add(MPSItems.MAGNET_MODULE.get(), mainLanguage, "Magnet");

        addItemDescriptions(MPSItems.MAGNET_MODULE.get(), mainLanguage, "Generates a magnetic field strong enough to attract items towards the player.         WARNING:                   This module drains power continuously. Turn it off when not needed.");

        // Piglin Pacification Module ------------------------------------------------------------------
        add(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), mainLanguage, "Piglin Pacification Module");

        addItemDescriptions(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), mainLanguage, "Simple module to make Piglins neutral as if wearing gold armor");

        // Vision ======================================================================================
        // Binoculars ----------------------------------------------------------------------------------
        add(MPSItems.BINOCULARS_MODULE.get(), mainLanguage, "Binoculars");

        addItemDescriptions(MPSItems.BINOCULARS_MODULE.get(), mainLanguage, "With the problems that have been plaguing Optifine lately, you've decided to take that Zoom ability into your own hands.");

        // Night Vision --------------------------------------------------------------------------------
        add(MPSItems.NIGHT_VISION_MODULE.get(), mainLanguage, "Night Vision");

        addItemDescriptions(MPSItems.NIGHT_VISION_MODULE.get(), mainLanguage, "A pair of augmented vision goggles to help you see at night and underwater.");

        // Tools =======================================================================================
        // Axe -----------------------------------------------------------------------------------------
        add(MPSItems.AXE_MODULE.get(), mainLanguage, "Axe");

        addItemDescriptions(MPSItems.AXE_MODULE.get(), mainLanguage, "Axes are mostly for chopping trees.");

        // Diamond Drill Upgrade -----------------------------------------------------------------------
        add(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), mainLanguage, "Diamond Drill Upgrade");

        addItemDescriptions(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), mainLanguage, "Add diamonds to allow your pickaxe module to mine Obsidian. *REQUIRES PICKAXE MODULE TO WORK*");

        // Flint and Steel -----------------------------------------------------------------------------
        add(MPSItems.FLINT_AND_STEEL_MODULE.get(), mainLanguage, "Flint and Steel");

        addItemDescriptions(MPSItems.FLINT_AND_STEEL_MODULE.get(), mainLanguage, "A portable igniter that creates fire through the power of energy.");

        // Rototiller ----------------------------------------------------------------------------------
        add(MPSItems.HOE_MODULE.get(), mcCodeToLang("ru_ru"), "Ротационный культиватор");

        addItemDescriptions(MPSItems.HOE_MODULE.get(), mainLanguage, "An automated tilling addon to make it easy to till large swaths of land at once.");

        // Leaf Blower ---------------------------------------------------------------------------------
        add(MPSItems.LEAF_BLOWER_MODULE.get(), mainLanguage, "Leaf Blower");

        addItemDescriptions(MPSItems.LEAF_BLOWER_MODULE.get(), mainLanguage, "Create a torrent of air to knock plants out of the ground and leaves off of trees.");

        // Lux Capacitor -------------------------------------------------------------------------------
        add(MPSItems.LUX_CAPACITOR_MODULE.get(), mainLanguage, "Lux Capacitor");

        addItemDescriptions(MPSItems.LUX_CAPACITOR_MODULE.get(), mainLanguage, "Launch a virtually infinite number of attractive light sources at the wall.");

        // Pickaxe -------------------------------------------------------------------------------------
        add(MPSItems.PICKAXE_MODULE.get(), mainLanguage, "Pickaxe");

        addItemDescriptions(MPSItems.PICKAXE_MODULE.get(), mainLanguage, "Picks are good for harder materials like stone and ore.");

        // Shears --------------------------------------------------------------------------------------
        add(MPSItems.SHEARS_MODULE.get(), mainLanguage, "Shears");

        addItemDescriptions(MPSItems.SHEARS_MODULE.get(), mainLanguage, "Cuts through leaves, wool, and creepers alike.");

        // Shovel --------------------------------------------------------------------------------------
        add(MPSItems.SHOVEL_MODULE.get(), mainLanguage, "Shovel");

        addItemDescriptions(MPSItems.SHOVEL_MODULE.get(), mainLanguage, "Shovels are good for soft materials like dirt and sand.");

        // Debug =======================================================================================
        // TODO

        // Weapons =====================================================================================
        // Blade Launcher ------------------------------------------------------------------------------
        add(MPSItems.BLADE_LAUNCHER_MODULE.get(), mainLanguage, "Blade Launcher");

        addItemDescriptions(MPSItems.BLADE_LAUNCHER_MODULE.get(), mainLanguage, "Launches a spinning blade of death (or shearing).");

        // Lightning Summoner ------------------------------------------------------------------------
        add(MPSItems.LIGHTNING_MODULE.get(), mainLanguage, "Lightning Summoner");

        addItemDescriptions(MPSItems.LIGHTNING_MODULE.get(), mainLanguage, "Allows you to summon lightning for a large energy cost.");

        // Melee Assist --------------------------------------------------------------------------------
        add(MPSItems.MELEE_ASSIST_MODULE.get(), mainLanguage, "Melee Assist");

        addItemDescriptions(MPSItems.MELEE_ASSIST_MODULE.get(), mainLanguage, "A much simpler addon, makes your powertool punches hit harder.");

        // Plasma Cannon -------------------------------------------------------------------------------
        add(MPSItems.PLASMA_CANNON_MODULE.get(), mainLanguage, "Plasma Cannon");

        addItemDescriptions(MPSItems.PLASMA_CANNON_MODULE.get(), mainLanguage, "Use electrical arcs in a containment field to superheat air to a plasma and launch it at enemies.");

        // Railgun -------------------------------------------------------------------------------------
        add(MPSItems.RAILGUN_MODULE.get(), mainLanguage, "Railgun");

        addItemDescriptions(MPSItems.RAILGUN_MODULE.get(), mainLanguage, "An assembly which accelerates a projectile to supersonic speeds using magnetic force. Heavy recoil.");
    }

    void addBlocks() {
        // Lux Capacitor -------------------------------------------------------------------------------
        add(MPSBlocks.LUX_CAPACITOR_BLOCK.get(), mainLanguage, "Lux Capacitor");

        // Power Armor Tinker Table --------------------------------------------------------------------
        add(MPSBlocks.TINKER_TABLE_BLOCK.get(), mainLanguage, "Power Armor Tinker Table");
    }

    void addGui() {
        // Armor ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_ARMOR, mainLanguage, "Armor");

        // Cancel --------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".cancel", mainLanguage, "Cancel");

        // Color ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_COLOR_PREFIX, mainLanguage, "Color:");

        // Compatible Modules -------------------------------------------------------------------------
        add(MPSConstants.GUI_COMPATIBLE_MODULES, mainLanguage, "Compatible Modules");

        // Energy Storage ------------------------------------------------------------------------------
        add(MPSConstants.GUI_ENERGY_STORAGE, mainLanguage, "Energy Storage");

        // Equipped Totals -----------------------------------------------------------------------------
        add(MPSConstants.GUI_EQUIPPED_TOTALS, mainLanguage, "Equipped Totals");

        // Install -------------------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL, mainLanguage, "Install");

        // Install description -------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL_DESC, mainLanguage, "Installs the mopdule in the selected item");

        // Installed Modules ---------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".installed.modules", mainLanguage, "Installed Modules");

        // Load ----------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".load", mainLanguage, "Load");

        // New -----------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".new", mainLanguage, "New");

        // No modular items ----------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".line1", mainLanguage, "No Modular Powersuit items \n found in inventory. Make some!");

        // Reset ---------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".reset", mainLanguage, "Reset");

        // Salvage -------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".salvage", mainLanguage, "Salvage");

        // Salvage description -------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".salvage.desc", mainLanguage, "Remove module from selected item and return to player");

        // Save ----------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".save", mainLanguage, "Save");

        // Save as -------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".saveAs", mainLanguage, "Save as:");

        // Save Successful -----------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".savesuccessful", mainLanguage, "Save Successful");

        // Show on HUD ---------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".showOnHud", mainLanguage, "Show on HUD:");

        // Install/Salvage -----------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL_SALVAGE, mainLanguage, "Install/Salvage");

        // Keybinds ------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".tab.keybinds", mainLanguage, "Keybinds");

        // Module Tweak --------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".tab.module.tweak", mainLanguage, "Module Tweak");

        // Visual --------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".tab.visual",mainLanguage, "Visual");

        // Tinker --------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".tinker", mainLanguage, "Tinker");

        // Modular Item Inventory ----------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".modularitem.inventory", mainLanguage, "Modular Item Inventory");
    }

    void addModuleTradeoff() {
        // Activation Percent --------------------------------------------------------------------------
        add("module.tradeoff.activationPercent", mainLanguage, "Activation Percent");

        // Alpha ---------------------------------------------------------------------------------------
        add("module.tradeoff.alpha", mainLanguage, "Alpha");

        // Amperage ------------------------------------------------------------------------------------
        add("module.tradeoff.amperage", mainLanguage, "Amperage");

        // Block Limit ---------------------------------------------------------------------------------
        add("module.tradeoff.selMinerLimit", mainLanguage, "Block Limit");

        // Mining Diameter -----------------------------------------------------------------------------
        add("module.tradeoff.miningDiameter", mainLanguage, "Mining Diameter");

        // Harvest Speed -------------------------------------------------------------------------------
        add("module.tradeoff.aquaHarvSpeed", mainLanguage, "Harvest Speed");

        // Armor (Energy) ------------------------------------------------------------------------------
        add("module.tradeoff.armorEnergy", mainLanguage, "Armor (Energy)");

        // Energy Per Damage ---------------------------------------------------------------------------
        add("module.tradeoff.armorEnergyPerDamage", mainLanguage, "Energy Per Damage");

        // Armor (Physical) ----------------------------------------------------------------------------
        add("module.tradeoff.armorPhysical", mainLanguage, "Armor (Physical)");

        // pts -----------------------------------------------------------------------------------------
        add("module.tradeoff.armorPoints", mainLanguage, "pts");

        // Auto-Feeder Efficiency ----------------------------------------------------------------------
        add("module.tradeoff.armorPoints", mainLanguage, "Auto-Feeder Efficiency");

        // Range ---------------------------------------------------------------------------------------
        add("module.tradeoff.blinkDriveRange", mainLanguage, "Range");

        // Blue ----------------------------------------------------------------------------------------
        add("module.tradeoff.blue", mainLanguage, "Blue");

        // Lux Capacitor Blue Hue ----------------------------------------------------------------------
        add("module.tradeoff.blueHue", mainLanguage, "Lux Capacitor Blue Hue");

        // Carry-through -------------------------------------------------------------------------------
        add("module.tradeoff.carryThrough", mainLanguage, "Carry-through");

        // Compensation --------------------------------------------------------------------------------
        add("module.tradeoff.compensation", mainLanguage, "Compensation");

        // Cooling Bonus -------------------------------------------------------------------------------
        add("module.tradeoff.coolingBonus", mainLanguage, "Cooling Bonus");

        // Daytime Energy Generation -------------------------------------------------------------------
        add("module.tradeoff.daytimeEnergyGen", mainLanguage, "Daytime Energy Generation");

        // Daytime Heat Generation ---------------------------------------------------------------------
        add("module.tradeoff.daytimeHeatGen", mainLanguage, "Daytime Heat Generation");

        // Diameter ------------------------------------------------------------------------------------
        add("module.tradeoff.diameter", mainLanguage, "Diameter");

        // Efficiency ----------------------------------------------------------------------------------
        add("module.tradeoff.efficiency", mainLanguage, "Efficiency");

        // Enchantment Level ---------------------------------------------------------------------------
        add("module.tradeoff.enchLevel", mainLanguage, "Enchantment Level");

        // Energy Consumption --------------------------------------------------------------------------
        add("module.tradeoff.energyCon", mainLanguage, "Energy Consumption");

        // Energy Generated ----------------------------------------------------------------------------
        add("module.tradeoff.energyGenerated", mainLanguage, "Energy Generated");

        // Energy Per Block Per Second -----------------------------------------------------------------
        add("module.tradeoff.energyPerBlock", mainLanguage, "Energy Per Block Per Second");

        // FOV multiplier ------------------------------------------------------------------------------
        add("module.tradeoff.fOVMult", mainLanguage, "FOV multiplier");

        // Field of View -------------------------------------------------------------------------------
        add("module.tradeoff.fieldOfView", mainLanguage, "Field of View");

        // Field Strength ------------------------------------------------------------------------------
        add("module.tradeoff.fieldOfView", mainLanguage, "Field Strength");

        // Tank Size -----------------------------------------------------------------------------------
        add("module.tradeoff.fluidTankSize", mainLanguage, "Tank Size");

        // Fortune Energy Consumption ------------------------------------------------------------------
        add("module.tradeoff.fortuneEnCon", mainLanguage, "Fortune Energy Consumption");

        // Fortune Level -------------------------------------------------------------------------------
        add("module.tradeoff.fortuneLevel", mainLanguage, "Fortune Level");

        // Green ---------------------------------------------------------------------------------------
        add("module.tradeoff.green", mainLanguage, "Green");

        // Lux Capacitor Green Hue ---------------------------------------------------------------------
        add("module.tradeoff.greenHue", mainLanguage, "Lux Capacitor Green Hue");

        // Harvest Speed -------------------------------------------------------------------------------
        add("module.tradeoff.harvSpeed", mainLanguage, "Harvest Speed");

        // Harvest Speed -------------------------------------------------------------------------------
        add("module.tradeoff.harvestSpeed", mainLanguage, "Harvest Speed");

        // Heat Activation Percent ---------------------------------------------------------------------
        add("module.tradeoff.heatActivationPercent", mainLanguage, "Heat Activation Percent");

        // Heat Emission -------------------------------------------------------------------------------
        add("module.tradeoff.heatEmission", mainLanguage, "Heat Emission");

        // Heat Generation -----------------------------------------------------------------------------
        add("module.tradeoff.heatGen", mainLanguage, "Heat Generation");

        // Heat Generation -----------------------------------------------------------------------------
        add("module.tradeoff.heatGeneration", mainLanguage, "Heat Generation");

        // Impact --------------------------------------------------------------------------------------
        add("module.tradeoff.impact", mainLanguage, "Impact");

        // Jetboots Thrust -----------------------------------------------------------------------------
        add("module.tradeoff.jetbootsThrust", mainLanguage, "Jetboots Thrust");

        // Jetpack Thrust ------------------------------------------------------------------------------
        add("module.tradeoff.jetpackThrust", mainLanguage, "Jetpack Thrust");

        // Knockback Resistance ------------------------------------------------------------------------
        add("module.tradeoff.knockbackResistance", mainLanguage, "Knockback Resistance");

        // Maximum Heat --------------------------------------------------------------------------------
        add("module.tradeoff.maxHeat", mainLanguage, "Maximum Heat");

        // Melee Damage --------------------------------------------------------------------------------
        add("module.tradeoff.meleeDamage", mainLanguage, "Melee Damage");

        // Melee Knockback -----------------------------------------------------------------------------
        add("module.tradeoff.meleeKnockback", mainLanguage, "Melee Knockback");

        // Movement Resistance -------------------------------------------------------------------------
        add("module.tradeoff.movementResistance", mainLanguage, "Movement Resistance");

        // Multiplier ----------------------------------------------------------------------------------
        add("module.tradeoff.multiplier", mainLanguage, "Multiplier");

        // Nighttime Energy Generation -----------------------------------------------------------------
        add("module.tradeoff.nightTimeEnergyGen", mainLanguage, "Nighttime Energy Generation");

        // Nighttime Heat Generation -------------------------------------------------------------------
        add("module.tradeoff.nightTimeHeatGen", mainLanguage, "Nighttime Heat Generation");

        // Lux Capacitor Opacity -----------------------------------------------------------------------
        add("module.tradeoff.opacity", mainLanguage, "Lux Capacitor Opacity");

        // Overclock -----------------------------------------------------------------------------------
        add("module.tradeoff.overclock", mainLanguage, "Overclock");

        // Plasma Damage At Full Charge ----------------------------------------------------------------
        add("module.tradeoff.plasmaDamage", mainLanguage, "Plasma Damage At Full Charge");

        // Plasma Energy Per Tick ----------------------------------------------------------------------
        add("module.tradeoff.plasmaEnergyPerTick", mainLanguage, "Plasma Energy Per Tick");

        // Plasma Explosiveness ------------------------------------------------------------------------
        add("module.tradeoff.plasmaExplosiveness", mainLanguage, "Plasma Explosiveness");

        // Power ---------------------------------------------------------------------------------------
        add("module.tradeoff.power", mainLanguage, "Power");

        // Radius --------------------------------------------------------------------------------------
        add("module.tradeoff.radius", mainLanguage, "Radius");

        // Railgun Energy Cost -------------------------------------------------------------------------
        add("module.tradeoff.railgunEnergyCost", mainLanguage, "Railgun Energy Cost");

        // Railgun Heat Emission -----------------------------------------------------------------------
        add("module.tradeoff.railgunHeatEm", mainLanguage, "Railgun Heat Emission");

        // Railgun Total Impulse -----------------------------------------------------------------------
        add("module.tradeoff.railgunTotalImpulse", mainLanguage, "Railgun Total Impulse");

        // Range ---------------------------------------------------------------------------------------
        add("module.tradeoff.range", mainLanguage, "Range");

        // Red -----------------------------------------------------------------------------------------
        add("module.tradeoff.red", mainLanguage, "Red");

        // Lux Capacitor Red Hue -----------------------------------------------------------------------
        add("module.tradeoff.redHue", mainLanguage, "Lux Capacitor Red Hue");

        // Silk Touch Energy Consumption ---------------------------------------------------------------
        add("module.tradeoff.silkTouchEnCon", mainLanguage, "Silk Touch Energy Consumption");

        // Spinning Blade Damage -----------------------------------------------------------------------
        add("module.tradeoff.spinBladeDam", mainLanguage, "Spinning Blade Damage");

        // Sprint Assist -------------------------------------------------------------------------------
        add("module.tradeoff.sprintAssist", mainLanguage, "Sprint Assist");

        // Sprint Energy Consumption -------------------------------------------------------------------
        add("module.tradeoff.sprintEnergyCon", mainLanguage, "Sprint Energy Consumption");

        // Sprint Exhaustion Compensation --------------------------------------------------------------
        add("module.tradeoff.sprintExComp", mainLanguage, "Sprint Exhaustion Compensation");

        // Sprint Speed Multiplier ---------------------------------------------------------------------
        add("module.tradeoff.sprintSpeedMult", mainLanguage, "Sprint Speed Multiplier");

        // Energy Generation ---------------------------------------------------------------------------
        add("module.tradeoff.thermalEnergyGen", mainLanguage, "Energy Generation");

        // Thrust --------------------------------------------------------------------------------------
        add("module.tradeoff.thrust", mainLanguage, "Thrust");

        // Underwater Movement Boost -------------------------------------------------------------------
        add("module.tradeoff.underwaterMovBoost", mainLanguage, "Underwater Movement Boost");

        // Verticality ---------------------------------------------------------------------------------
        add("module.tradeoff.vertically", mainLanguage, "Verticality");

        // Voltage -------------------------------------------------------------------------------------
        add("module.tradeoff.voltage", mainLanguage, "Voltage");

        // Walking Assist ------------------------------------------------------------------------------
        add("module.tradeoff.walkingAssist", mainLanguage, "Walking Assist");

        // Walking Energy Consumption ------------------------------------------------------------------
        add("module.tradeoff.walkingEnergyCon", mainLanguage, "Walking Energy Consumption");

        // Walking Speed Multiplier --------------------------------------------------------------------
        add("module.tradeoff.walkingSpeedMult", mainLanguage, "Walking Speed Multiplier");

        // Y-look ratio --------------------------------------------------------------------------------
        add("module.tradeoff.yLookRatio", mainLanguage, "Y-look ratio");
    }


    void addKeybinds() {
        // Cycle Tool Backward (MPS) -------------------------------------------------------------------
        add("keybinding.powersuits.cycleToolBackward", mainLanguage, "Cycle Tool Backward (MPS)");

        // Cycle Tool Forward (MPS) --------------------------------------------------------------------
        add("keybinding.powersuits.cycleToolForward", mainLanguage, "Cycle Tool Forward (MPS)");

        // Toggle Module -------------------------------------------------------------------------------
        add("keybinding." + MPSConstants.MOD_ID + ".toggleModule", mainLanguage, "Toggle %s");
    }

    void addModels() {
        // Generic armor model parts ===================================================================
        // Head ----------------------------------------------------------------------------------------
        add("javaModel.head.Head.partName", mainLanguage, "Head");

        // Body ----------------------------------------------------------------------------------------
        add("javaModel.chest.Body.partName", mainLanguage, "Body");

        // Chest ---------------------------------------------------------------------------------------
        add("javaModel.chest.partName.partName",mainLanguage, "Chest");

        // Left Arm ------------------------------------------------------------------------------------
        add("javaModel.chest.LeftArm.partName", mainLanguage, "LeftArm");

        // Right Arm -----------------------------------------------------------------------------------
        add("javaModel.chest.RightArm.partName", mainLanguage, "RightArm");

        // Legs ----------------------------------------------------------------------------------------
        add("javaModel.legs.partName", mainLanguage, "Legs");

        // Left Leg ------------------------------------------------------------------------------------
        add("javaModel.legs.LeftLeg.partName", mainLanguage, "LeftLeg");

        // Right Leg -----------------------------------------------------------------------------------
        add("javaModel.legs.RightLeg.partName", mainLanguage,  "RightLeg");

        // Feet ----------------------------------------------------------------------------------------
        add("javaModel.feet.partName", mainLanguage, "Feet");

        // Left Foot -----------------------------------------------------------------------------------
        add("javaModel.feet.LeftFoot.partName", mainLanguage, "LeftFoot");

        // Right Foot ----------------------------------------------------------------------------------
        add("javaModel.feet.RightFoot.partName", mainLanguage, "RightFoot");

        // Armor skins ---------------------------------------------------------------------------------
        add("javaModel.citizenjoe_armorskin.specName", mainLanguage, "Citizen Joe Armor Skin");

        // Default ArmorSkin ---------------------------------------------------------------------------
        add("javaModel.default_armorskin.specName", mainLanguage, "Default Armor Skin");

        // Power Fist ==================================================================================
        add("javaModel.powerfist.specName", mainLanguage, "Power Fist");

        // Main Arm ------------------------------------------------------------------------------------
        add("javaModel.powerfist.mainarm.partName", mainLanguage, "Main Arm");

        // Armor Right ---------------------------------------------------------------------------------
        add("javaModel.powerfist.armorright.partName", mainLanguage, "Armor Right");

        // Armor Left ----------------------------------------------------------------------------------
        add("javaModel.powerfist.armorleft.partName", mainLanguage, "Armor Left");

        // Wrist Top Right -----------------------------------------------------------------------------
        add("javaModel.powerfist.wristtopright.partName", mainLanguage, "Wrist Top Right");

        // Wrist Top Left ------------------------------------------------------------------------------
        add("javaModel.powerfist.wristtopleft.partName", mainLanguage, "Wrist Top Left");

        // Wrist Bottom Right --------------------------------------------------------------------------
        add("javaModel.powerfist.wristbottomright.partName", mainLanguage, "Wrist Bottom Right");

        // Wrist Bottom Left ---------------------------------------------------------------------------
        add("javaModel.powerfist.wristbottomleft.partName", mainLanguage, "Wrist Bottom Left");

        // Finger Guard --------------------------------------------------------------------------------
        add("javaModel.powerfist.fingerguard.partName", mainLanguage, "Finger Guard");

        // Crystal Holder ------------------------------------------------------------------------------
        add("javaModel.powerfist.crystalholder.partName", mainLanguage, "Crystal Holder");

        // Crystal -------------------------------------------------------------------------------------
        add("javaModel.powerfist.crystal.partName", mainLanguage, "Crystal");

        // Support Right 1 -----------------------------------------------------------------------------
        add("javaModel.powerfist.supportright1.partName", mainLanguage, "Support Right 1");

        // Support Right 2 -----------------------------------------------------------------------------
        add("javaModel.powerfist.supportright2.partName", mainLanguage, "Support Right 2");

        // Support Right 3 -----------------------------------------------------------------------------
        add("javaModel.powerfist.supportright3.partName", mainLanguage, "Support Right 3");

        // Support Right 4 -----------------------------------------------------------------------------
        add("javaModel.powerfist.supportright4.partName", mainLanguage, "Support Right 4");

        // Support Right 5 -----------------------------------------------------------------------------
        add("javaModel.powerfist.supportright5.partName", mainLanguage, "Support Right 5");

        // Support Base Right --------------------------------------------------------------------------
        add("javaModel.powerfist.supportbaseright.partName", mainLanguage, "Support Base Right");

        // Support Right Front -------------------------------------------------------------------------
        add("javaModel.powerfist.supportrightfront.partName", mainLanguage, "Support Right Front");

        // Support Base Left ---------------------------------------------------------------------------
        add("javaModel.powerfist.supportbaseleft.partName", mainLanguage, "Support Base Left");

        // Support Left Front --------------------------------------------------------------------------
        add("javaModel.powerfist.supportleftfront.partName", mainLanguage, "Support Left Front");

        // Support Left 1 ------------------------------------------------------------------------------
        add("javaModel.powerfist.supportleft1.partName", mainLanguage, "Support Left 1");

        // Support Left 2 ------------------------------------------------------------------------------
        add("javaModel.powerfist.supportleft2.partName", mainLanguage, "Support Left 2");

        // Support Left 3 ------------------------------------------------------------------------------
        add("javaModel.powerfist.supportleft3.partName", mainLanguage, "Support Left 3");

        // Support Left 4 ------------------------------------------------------------------------------
        add("javaModel.powerfist.supportleft4.partName", mainLanguage, "Support Left 4");

        // Support Left 5 ------------------------------------------------------------------------------
        add("javaModel.powerfist.supportleft5.partName", mainLanguage, "Support Left 5");

        // Palm ----------------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.partName", mainLanguage, "Palm");

        // Index 1 -------------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.index1.partName", mainLanguage, "Index 1");

        // Index 2 -------------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.index1.index2.partName", mainLanguage, "Index 2");

        // Middle Finger 1 -----------------------------------------------------------------------------
        add("javaModel.powerfist.palm.middlefinger1.partName", mainLanguage, "Middle Finger 1");

        // Middle Finger 2 -----------------------------------------------------------------------------
        add("javaModel.powerfist.palm.middlefinger1.middlefinger2.partName", mainLanguage, "Middle Finger 2");

        // Ring Finger 1 -------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.ringfinger1.partName", mainLanguage, "Ring Finger 1");

        // Ring Finger 2 -------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.ringfinger1.ringfinger2.partName", mainLanguage, "Ring Finger 2");

        // Pinky 1 -------------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.pinky1.partName", mainLanguage, "Pinky 1");

        // Pinky 2 -------------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.pinky1.pinky2.partName", mainLanguage, "Pinky 2");

        // Thumb 1 -------------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.thumb1.partName", mainLanguage, "Thumb 1");

        // Thumb 2 -------------------------------------------------------------------------------------
        add("javaModel.powerfist.palm.thumb1.thumb2.partName", mainLanguage, "Thumb 2");

        // MPS Armor ===================================================================================
        // Helm ----------------------------------------------------------------------------------------
        add("model.mps_helm.modelName", mainLanguage, "MPS Helm");

        // Helmet --------------------------------------------------------------------------------------
        add("model.mps_helm.helm_main.partName", mainLanguage, "Helmet");

        // Tubes ---------------------------------------------------------------------------------------
        add("model.mps_helm.helm_tubes.partName", mainLanguage, "Tubes");

        // Left Tube Entry -----------------------------------------------------------------------------
        add("model.mps_helm.helm_tube_entry1.partName", mainLanguage, "Left Tube Entry");

        // Right Tube Entry ----------------------------------------------------------------------------
        add("model.mps_helm.helm_tube_entry2.partName", mainLanguage, "Right Tube Entry");

        // Visor ---------------------------------------------------------------------------------------
        add("model.mps_helm.visor.partName", mainLanguage, "Visor");

        // MPS Chestplate ------------------------------------------------------------------------------
        add("model.mps_chest.modelName", mainLanguage, "MPS Chestplate");

        // Chest Plating -------------------------------------------------------------------------------
        add("model.mps_chest.chest_main.partName", mainLanguage, "Chest Plating");

        // Chest Padding -------------------------------------------------------------------------------
        add("model.mps_chest.chest_padding.partName", mainLanguage, "Chest Padding");

        // Belt Crystal --------------------------------------------------------------------------------
        add("model.mps_chest.crystal_belt.partName", mainLanguage, "Belt Crystal");

        // Backpack ------------------------------------------------------------------------------------
        add("model.mps_chest.backpack.partName", mainLanguage, "Backpack");

        // Belt ----------------------------------------------------------------------------------------
        add("model.mps_chest.belt.partName", mainLanguage, "Belt");

        // Accessory ----------------------------------------------------------------------------------
        add("model.mps_chest.polySurface36.partName", mainLanguage, "Accessory");

        // MPS Arms ------------------------------------------------------------------------------------
        add("model.mps_arms.modelName", mainLanguage, "MPS Arms");

        // Left Arm ------------------------------------------------------------------------------------
        add("model.mps_arms.arms2.partName", mainLanguage, "Left Arm");

        // Right Arm -----------------------------------------------------------------------------------
        add("model.mps_arms.arms3.partName", mainLanguage, "Right Arm");

        // Left Shoulder Crystal -----------------------------------------------------------------------
        add("model.mps_arms.crystal_shoulder_1.partName", mainLanguage, "Left Shoulder Crystal");

        // Right Shoulder Crystal ----------------------------------------------------------------------
        add("model.mps_arms.crystal_shoulder_2.partName", mainLanguage, "Right Shoulder Crystal");

        // MPS Jetpack ---------------------------------------------------------------------------------
        add("model.jetpack.modelName", mainLanguage, "MPS Jetpack");

        // Main ----------------------------------------------------------------------------------------
        add("model.jetpack.default.partName", mainLanguage, "Main");

        // Secondary -----------------------------------------------------------------------------------
        add("model.jetpack.jetpack5.partName", mainLanguage, "Secondary");

        // Lights --------------------------------------------------------------------------------------
        add("model.jetpack.jetpack_glow.partName", mainLanguage, "Lights");

        // MPS Pantaloons ------------------------------------------------------------------------------
        add("model.mps_pantaloons.modelName", mainLanguage, "MPS Pantaloons");

        // Right Leg -----------------------------------------------------------------------------------
        add("model.mps_pantaloons.leg1.partName", mainLanguage, "Right Leg");

        // Left Leg ------------------------------------------------------------------------------------
        add("model.mps_pantaloons.leg2.partName", mainLanguage, "Left Leg");

        // MPS Boots -----------------------------------------------------------------------------------
        add("model.mps_boots.modelName", mainLanguage, "MPS Boots");

        // Right Boot ----------------------------------------------------------------------------------
        add("model.mps_boots.boots1.partName", mainLanguage, "Right Boot");

        // Left Boot -----------------------------------------------------------------------------------
        add("model.mps_boots.boots2.partName", mainLanguage, "Left Boot");

        // Armor 2 =====================================================================================
        add("model.armor2.modelName", mainLanguage, "Armor 2");

        // Helmet --------------------------------------------------------------------------------------
        add("model.armor2.helmetmain.partName", mainLanguage, "Helmet");

        // Helmet Tubes and Visor ----------------------------------------------------------------------
        add("model.armor2.helmetglow1.partName", mainLanguage, "Helmet Tubes and Visor");

        // Front Chest Protection ----------------------------------------------------------------------
        add("model.armor2.chestmain.partName", mainLanguage, "Front Chest Protection");

        // Chest Base ----------------------------------------------------------------------------------
        add("model.armor2.chestgray.partName", mainLanguage, "Chest Base");

        // Back Chest Protection -----------------------------------------------------------------------
        add("model.armor2.chestback1.partName", mainLanguage, "Back Chest Protection");

        // Spinal Power Crystals -----------------------------------------------------------------------
        add("model.armor2.chestglowback.partName", mainLanguage, "Spinal Power Crystals");

        // Front Power Crystal -------------------------------------------------------------------------
        add("model.armor2.chestglowfront.partName", mainLanguage, "Front Power Crystal");

        // Arm and Shoulder Protection -----------------------------------------------------------------
        add("model.armor2.armmain.partName", mainLanguage, "Arm and Shoulder Protection");

        // Arm and Shoulder Protection 1 ---------------------------------------------------------------
        add("model.armor2.armmain1.partName", mainLanguage, "Arm and Shoulder Protection");

        // Lef Boot Glow -------------------------------------------------------------------------------
        add("model.armor2.bootglow.partName", mainLanguage, "Left Boot Glow");

        // Right Boot Glow -----------------------------------------------------------------------------
        add("model.armor2.bootglow1.partName", mainLanguage, "Right Boot Glow");

        // Left Boot -----------------------------------------------------------------------------------
        add("model.armor2.bootmain.partName", mainLanguage, "Left Boot");

        // Right Boot ----------------------------------------------------------------------------------
        add("model.armor2.bootmain1.partName", mainLanguage, "Right Boot");

        // Shoulder Light ------------------------------------------------------------------------------
        add("model.armor2.armglow.partName", mainLanguage, "Shoulder Light");

        // Shoulder Light 1 -----------------------------------------------------------------------------
        add("model.armor2.armglow1.partName", mainLanguage, "Shoulder Light");

        // Arm Base ------------------------------------------------------------------------------------
        add("model.armor2.armgray.partName",  mainLanguage, "Arm Base");

        // Arm Base 1 ----------------------------------------------------------------------------------
        add("model.armor2.armgray1.partName", mainLanguage, "Arm Base");

        // Left Leg Base -------------------------------------------------------------------------------
        add("model.armor2.leggray.partName", mainLanguage, "Left Leg Base");

        // Right Leg Base ------------------------------------------------------------------------------
        add("model.armor2.leggray1.partName", mainLanguage, "Right Leg Base");

        // Left Leg Protection -------------------------------------------------------------------------
        add("model.armor2.legbit.partName", mainLanguage, "Left Leg Protection");

        // Right Leg Protection ------------------------------------------------------------------------
        add("model.armor2.legbit1.partName", mainLanguage, "Right Leg Protection");

        // Left Leg Light ------------------------------------------------------------------------------
        add("model.armor2.legglow.partName", mainLanguage, "Left Leg Light");

        // Right Leg Light -----------------------------------------------------------------------------
        add("model.armor2.legglow1.partName", mainLanguage, "Right Leg Light");
    }
}
