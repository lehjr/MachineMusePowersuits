package powersuits.client.lang;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSItems;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

public class MPSLanguageProvider__EN_US extends AbstractLangageProviderMPS {
    public MPSLanguageProvider__EN_US(PackOutput output) {
        super(output, MPSConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItemGroup();
        addModularItems();
        addModules();
        addBlocks();
        addGui();
        addKeybinds();
        addModuleTradeoff();
        addModels();
    }

    @Override
    public void addItemGroup() {
        add("itemGroup." + MPSConstants.MOD_ID, "Modular Powersuits");
    }

    @Override
    public void addModularItems() {
        // Helmet --------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_HELMET.get(), "Power Armor Helmet");

        // Chestplate ----------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_CHESTPLATE.get(), "Power Armor Chestplate");

        // Leggings ------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_LEGGINGS.get(), "Power Armor Leggings");

        // Boots ---------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_BOOTS.get(), "Power Armor Boots");

        // Power Fist ----------------------------------------------------------------------------------
        add(MPSItems.POWER_FIST.get(), "Power Fist");
    }

    @Override
    public void addModules() {
        // Armor =======================================================================================
        // Iron Plating --------------------------------------------------------------------------------
        add(MPSItems.IRON_PLATING_MODULE.get(), "Iron Plating");

        addItemDescriptions(MPSItems.IRON_PLATING_MODULE.get(), "Iron plating is heavy but offers a bit more protection.");

        // Diamond Plating -----------------------------------------------------------------------------
        add(MPSItems.DIAMOND_PLATING_MODULE.get(), "Diamond Plating");

        addItemDescriptions(MPSItems.DIAMOND_PLATING_MODULE.get(),"Diamond Plating is harder and more expensive to make, but offers better protection.");

        // Netherite Plating ---------------------------------------------------------------------------
        add(MPSItems.NETHERITE_PLATING_MODULE.get(), "Netherite Plating");

        addItemDescriptions(MPSItems.NETHERITE_PLATING_MODULE.get(),"Armor plating made from Netherite");

        // Energy Shield -------------------------------------------------------------------------------
        add(MPSItems.ENERGY_SHIELD_MODULE.get(), "Energy Shield");

        addItemDescriptions(MPSItems.ENERGY_SHIELD_MODULE.get(), "Energy shields are much lighter than plating, but consume energy.");

        // Cosmetic ====================================================================================
        // Transparent Armor ---------------------------------------------------------------------------
        add(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), "Transparent Armor");
        addItemDescriptions(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), "Make the item transparent, so you can show off your skin without losing armor.");

        // Energy Generation ===========================================================================
        // Coal Generator ------------------------------------------------------------------------------
        // TODO:
//        add(MPSItems.COAL_GENERATOR_MODULE.get(), "Coal Generator");
//
//        addItemDescriptions(MPSItems.COAL_GENERATOR_MODULE.get(), "Generate power with solid fuels");

        // Solar Generator -----------------------------------------------------------------------------
        add(MPSItems.SOLAR_GENERATOR_MODULE.get(), "Solar Generator");

        addItemDescriptions(MPSItems.SOLAR_GENERATOR_MODULE.get(), "Let the sun power your adventures.");

        // High Efficiency Solar Generator -------------------------------------------------------------
        add(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), "High Efficiency Solar Generator");

        addItemDescriptions(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), "A solar generator with 3 times the power generation of the standard solar generator.");

        // Kinetic Generator ---------------------------------------------------------------------------
        add(MPSItems.KINETIC_GENERATOR_MODULE.get(), "Kinetic Generator");

        addItemDescriptions(MPSItems.KINETIC_GENERATOR_MODULE.get(), "Generate power with your movement.");

        // Thermal Generator ---------------------------------------------------------------------------
        add(MPSItems.THERMAL_GENERATOR_MODULE.get(), "Thermal Generator");

        addItemDescriptions(MPSItems.THERMAL_GENERATOR_MODULE.get(), "Generate power from extreme amounts of heat.");

        // Environmental ===============================================================================
        // Auto Feeder ---------------------------------------------------------------------------------
        add(MPSItems.AUTO_FEEDER_MODULE.get(), "Auto-Feeder");

        addItemDescriptions(MPSItems.AUTO_FEEDER_MODULE.get(), "Whenever you're hungry, this module will grab the bottom-left-most food item from your inventory and feed it to you, storing the rest for later.");

        // Cooling System ------------------------------------------------------------------------------
        add(MPSItems.COOLING_SYSTEM_MODULE.get(), "Cooling System");

        addItemDescriptions(MPSItems.COOLING_SYSTEM_MODULE.get(),  "Cools down heat-producing modules quicker. Add a fluid tank module and fluid to enhance performance.");

        // Fluid Tank  ---------------------------------------------------------------------------------
        add(MPSItems.FLUID_TANK_MODULE.get(),  "Fluid Tank");

        addItemDescriptions(MPSItems.FLUID_TANK_MODULE.get(), "Stores fluid to enhance the performance of the cooling system.");

        // Mob Repulsor --------------------------------------------------------------------------------
        add(MPSItems.MOB_REPULSOR_MODULE.get(), "Mob Repulsor");

        addItemDescriptions(MPSItems.MOB_REPULSOR_MODULE.get(), "Pushes mobs away from you when activated, but constantly drains power. It is highly recommended that you set this module to a keybind because of the high energy draw.");

        // Water Electrolyzer --------------------------------------------------------------------------
        add(MPSItems.WATER_ELECTROLYZER_MODULE.get(), "Water Electrolyzer");

        addItemDescriptions(MPSItems.WATER_ELECTROLYZER_MODULE.get(), "When you run out of air, this module will jolt the water around you, electrolyzing a small bubble to breathe from.");

        // Mining Enchantments =========================================================================
        // Aqua Affinity -------------------------------------------------------------------------------
        add(MPSItems.AQUA_AFFINITY_MODULE.get(), "Aqua Affinity");

        addItemDescriptions(MPSItems.AQUA_AFFINITY_MODULE.get(), "Reduces the speed penalty for using your tool underwater.");

        // Silk Touch Enchantment ----------------------------------------------------------------------
        add(MPSItems.SILK_TOUCH_MODULE.get(), "Silk Touch Enchantment");

        addItemDescriptions(MPSItems.SILK_TOUCH_MODULE.get(), "A module that provides the Silk Touch enchantment");

        // Fortune Enchantment -------------------------------------------------------------------------
        add(MPSItems.FORTUNE_MODULE.get(), "Fortune Enchantment");

        addItemDescriptions(MPSItems.FORTUNE_MODULE.get(), "A module that provides the fortune enchantment.");

        // Mining Enhancements =========================================================================
        // Vein Miner ----------------------------------------------------------------------------------
        add(MPSItems.VEIN_MINER_MODULE.get(), "Vein Miner");

        addItemDescriptions(MPSItems.VEIN_MINER_MODULE.get(), "A module for mining ore veins");

        // Tunnel Bore ---------------------------------------------------------------------------------
        add(MPSItems.TUNNEL_BORE_MODULE.get(), "Tunnel Bore");

        addItemDescriptions(MPSItems.TUNNEL_BORE_MODULE.get(), "A module that enables the pickaxe module to mine 5x5 range blocks simultaneously.");

        // Selective Miner -----------------------------------------------------------------------------
        add(MPSItems.SELECTIVE_MINER.get(), "Selective Miner");

        addItemDescriptions(MPSItems.SELECTIVE_MINER.get(), "Breaks blocks similar to the vein miner, but selectively. Shift and click to select block type.");

        // Movement ====================================================================================
        // Blink Drive ---------------------------------------------------------------------------------
        add(MPSItems.BLINK_DRIVE_MODULE.get(), "Blink Drive");

        addItemDescriptions(MPSItems.BLINK_DRIVE_MODULE.get(), "Get from point A to point C via point B, where point B is a fold in space & time.");

        // Uphill Step Assist --------------------------------------------------------------------------
        add(MPSItems.CLIMB_ASSIST_MODULE.get(), "Uphill Step Assist");

        addItemDescriptions(MPSItems.CLIMB_ASSIST_MODULE.get(), "A pair of dedicated servos allow you to effortlessly step up 1m-high ledges.");

        // Dimensional Tear Generator ------------------------------------------------------------------
        add(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), "Dimensional Tear Generator");

        addItemDescriptions(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), "Generate a tear in the space-time continuum that will teleport the player to its relative coordinates in the nether or overworld.");

        // Flight Control ------------------------------------------------------------------------------
        add(MPSItems.FLIGHT_CONTROL_MODULE.get(), "Flight Control");

        addItemDescriptions(MPSItems.FLIGHT_CONTROL_MODULE.get(), "An integrated control circuit to help you fly better. Press Z to go down.");

        // Glider --------------------------------------------------------------------------------------
        add(MPSItems.GLIDER_MODULE.get(), "Glider");

        addItemDescriptions(MPSItems.GLIDER_MODULE.get(),"Tack on some wings to turn downward into forward momentum. Press sneak+forward while falling to activate.");

        // Jet Boots -----------------------------------------------------------------------------------
        add(MPSItems.JETBOOTS_MODULE.get(), "Jet Boots");

        addItemDescriptions(MPSItems.JETBOOTS_MODULE.get(), "Jet boots are not as strong as a jetpack, but they should at least be strong enough to counteract gravity.");

        // Jetpack -------------------------------------------------------------------------------------
        add(MPSItems.JETPACK_MODULE.get(), "Jetpack");

        addItemDescriptions(MPSItems.JETPACK_MODULE.get(), "A jetpack should allow you to jump indefinitely, or at least until you run out of power.");

        // Jump Assist ---------------------------------------------------------------------------------
        add(MPSItems.JUMP_ASSIST_MODULE.get(), "Jump Assist");

        addItemDescriptions(MPSItems.JUMP_ASSIST_MODULE.get(), "Another set of servo motors to help you jump higher.");

        // Parachute -----------------------------------------------------------------------------------
        add(MPSItems.PARACHUTE_MODULE.get(), "Parachute");

        addItemDescriptions(MPSItems.PARACHUTE_MODULE.get(), "Add a parachute to slow your descent. Activate by pressing sneak (defaults to Shift) in midair.");

        // Shock Absorber ------------------------------------------------------------------------------
        add(MPSItems.SHOCK_ABSORBER_MODULE.get(), "Shock Absorber");

        addItemDescriptions(MPSItems.SHOCK_ABSORBER_MODULE.get(),"With some servos, springs, and padding, you should be able to negate a portion of fall damage.");

        // Sprint Assist -------------------------------------------------------------------------------
        add(MPSItems.SPRINT_ASSIST_MODULE.get(), "Sprint Assist");

        addItemDescriptions(MPSItems.SPRINT_ASSIST_MODULE.get(), "A set of servo motors to help you sprint (double-tap forward) and walk faster.");

        // Swim Boost ----------------------------------------------------------------------------------
        add(MPSItems.SWIM_BOOST_MODULE.get(), "Swim Boost");

        addItemDescriptions(MPSItems.SWIM_BOOST_MODULE.get(), "By refitting an ion thruster for underwater use, you may be able to add extra forward (or backward) thrust when underwater.");

        // Special =====================================================================================
        // Active Camouflage ---------------------------------------------------------------------------
        add(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Active Camouflage");
        addItemDescriptions(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Emit a hologram of your surroundings to make yourself almost imperceptible.");

        // Magnet --------------------------------------------------------------------------------------
        add(MPSItems.MAGNET_MODULE.get(), "Magnet");

        addItemDescriptions(MPSItems.MAGNET_MODULE.get(), "Generates a magnetic field strong enough to attract items towards the player.         WARNING:                   This module drains power continuously. Turn it off when not needed.");

        // Piglin Pacification Module ------------------------------------------------------------------
        add(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), "Piglin Pacification Module");

        addItemDescriptions(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), "Simple module to make Piglins neutral as if wearing gold armor");

        // Vision ======================================================================================
        // Binoculars ----------------------------------------------------------------------------------
        add(MPSItems.BINOCULARS_MODULE.get(), "Binoculars");

        addItemDescriptions(MPSItems.BINOCULARS_MODULE.get(), "With the problems that have been plaguing Optifine lately, you've decided to take that Zoom ability into your own hands.");

        // Night Vision --------------------------------------------------------------------------------
        add(MPSItems.NIGHT_VISION_MODULE.get(), "Night Vision");

        addItemDescriptions(MPSItems.NIGHT_VISION_MODULE.get(), "A pair of augmented vision goggles to help you see at night and underwater.");

        // Tools =======================================================================================
        // Axe -----------------------------------------------------------------------------------------
        add(MPSItems.AXE_MODULE.get(), "Axe");

        addItemDescriptions(MPSItems.AXE_MODULE.get(), "Axes are mostly for chopping trees.");

        // Diamond Drill Upgrade -----------------------------------------------------------------------
        add(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), "Diamond Drill Upgrade");

        addItemDescriptions(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), "Add diamonds to allow your pickaxe module to mine Obsidian. *REQUIRES PICKAXE MODULE TO WORK*");

        // Flint and Steel -----------------------------------------------------------------------------
        add(MPSItems.FLINT_AND_STEEL_MODULE.get(), "Flint and Steel");

        addItemDescriptions(MPSItems.FLINT_AND_STEEL_MODULE.get(), "A portable igniter that creates fire through the power of energy.");

        // Rototiller ----------------------------------------------------------------------------------
        add(MPSItems.HOE_MODULE.get(), "Rototiller");

        addItemDescriptions(MPSItems.HOE_MODULE.get(), "An automated tilling addon to make it easy to till large swaths of land at once.");

        // Leaf Blower ---------------------------------------------------------------------------------
        add(MPSItems.LEAF_BLOWER_MODULE.get(), "Leaf Blower");

        addItemDescriptions(MPSItems.LEAF_BLOWER_MODULE.get(), "Create a torrent of air to knock plants out of the ground and leaves off of trees.");

        // Lux Capacitor -------------------------------------------------------------------------------
        add(MPSItems.LUX_CAPACITOR_MODULE.get(), "Lux Capacitor");

        addItemDescriptions(MPSItems.LUX_CAPACITOR_MODULE.get(), "Launch a virtually infinite number of attractive light sources at the wall.");

        // Pickaxe -------------------------------------------------------------------------------------
        add(MPSItems.PICKAXE_MODULE.get(), "Pickaxe");

        addItemDescriptions(MPSItems.PICKAXE_MODULE.get(), "Picks are good for harder materials like stone and ore.");

        // Shears --------------------------------------------------------------------------------------
        add(MPSItems.SHEARS_MODULE.get(), "Shears");

        addItemDescriptions(MPSItems.SHEARS_MODULE.get(), "Cuts through leaves, wool, and creepers alike.");

        // Shovel --------------------------------------------------------------------------------------
        add(MPSItems.SHOVEL_MODULE.get(), "Shovel");

        addItemDescriptions(MPSItems.SHOVEL_MODULE.get(), "Shovels are good for soft materials like dirt and sand.");

        // Debug =======================================================================================
        // TODO

        // Weapons =====================================================================================
        // Blade Launcher ------------------------------------------------------------------------------
        add(MPSItems.BLADE_LAUNCHER_MODULE.get(), "Blade Launcher");

        addItemDescriptions(MPSItems.BLADE_LAUNCHER_MODULE.get(), "Launches a spinning blade of death (or shearing).");

        // Lightning Summoner ------------------------------------------------------------------------
        add(MPSItems.LIGHTNING_MODULE.get(), "Lightning Summoner");

        addItemDescriptions(MPSItems.LIGHTNING_MODULE.get(), "Allows you to summon lightning for a large energy cost.");

        // Melee Assist --------------------------------------------------------------------------------
        add(MPSItems.MELEE_ASSIST_MODULE.get(), "Melee Assist");

        addItemDescriptions(MPSItems.MELEE_ASSIST_MODULE.get(), "A much simpler addon, makes your powertool punches hit harder.");

        // Plasma Cannon -------------------------------------------------------------------------------
        add(MPSItems.PLASMA_CANNON_MODULE.get(), "Plasma Cannon");

        addItemDescriptions(MPSItems.PLASMA_CANNON_MODULE.get(), "Use electrical arcs in a containment field to superheat air to a plasma and launch it at enemies.");

        // Railgun -------------------------------------------------------------------------------------
        add(MPSItems.RAILGUN_MODULE.get(), "Railgun");

        addItemDescriptions(MPSItems.RAILGUN_MODULE.get(), "An assembly which accelerates a projectile to supersonic speeds using magnetic force. Heavy recoil.");
    }

    @Override
    public void addBlocks() {
        // Lux Capacitor -------------------------------------------------------------------------------
        add(MPSBlocks.LUX_CAPACITOR_BLOCK.get(), "Lux Capacitor");

        // Power Armor Tinker Table --------------------------------------------------------------------
        add(MPSBlocks.TINKER_TABLE_BLOCK.get(), "Power Armor Tinker Table");
    }

    @Override
    public void addGui() {
        // Armor ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_ARMOR, "Armor");

        // Cancel --------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".cancel", "Cancel");

        // Color ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_COLOR_PREFIX, "Color:");

        // Compatible Modules -------------------------------------------------------------------------
        add(MPSConstants.GUI_COMPATIBLE_MODULES, "Compatible Modules");

        // Energy Storage ------------------------------------------------------------------------------
        add(MPSConstants.GUI_ENERGY_STORAGE, "Energy Storage");

        // Equipped Totals -----------------------------------------------------------------------------
        add(MPSConstants.GUI_EQUIPPED_TOTALS, "Equipped Totals");

        // Install -------------------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL, "Install");

        // Install description -------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL_DESC, "Installs the mopdule in the selected item");

        // Installed Modules ---------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".installed.modules", "Installed Modules");

        // Load ----------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".load", "Load");

        // New -----------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".new", "New");

        // No modular items ----------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".line1", "No Modular Powersuit items \n found in inventory. Make some!");

        // Reset ---------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".reset", "Reset");

        // Salvage -------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".salvage", "Salvage");

        // Salvage description -------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".salvage.desc", "Remove module from selected item and return to player");

        // Save ----------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".save", "Save");

        // Save as -------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".saveAs", "Save as:");

        // Save Successful -----------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".savesuccessful", "Save Successful");

        // Show on HUD ---------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".showOnHud", "Show on HUD:");

        // Install/Salvage -----------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL_SALVAGE, "Install/Salvage");

        // Keybinds ------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".tab.keybinds", "Keybinds");

        // Module Tweak --------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".tab.module.tweak", "Module Tweak");

        // Visual --------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".tab.visual","Visual");

        // Tinker --------------------------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".tinker", "Tinker");

        // Modular Item Inventory ----------------------------------------------------------------------
        add("gui." + MPSConstants.MOD_ID + ".modularitem.inventory", "Modular Item Inventory");
    }

    @Override
    public void addModuleTradeoff() {
        // Activation Percent --------------------------------------------------------------------------
        addTradeoff(MPSConstants.ACTIVATION_PERCENT, "Activation Percent");

        // Alpha ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ALPHA, "Alpha");

        // Amperage ------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.AMPERAGE, "Amperage");

        // Block Limit ---------------------------------------------------------------------------------
        addTradeoff(MPSConstants.SELECTIVE_MINER_LIMIT, "Block Limit");

//        // Mining Diameter -----------------------------------------------------------------------------
//        addTradeoff(miningDiameter", "Mining Diameter");

        // Harvest Speed -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.AQUA_HARVEST_SPEED, "Harvest Speed");

        // Armor (Energy) ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_VALUE_ENERGY, "Armor (Energy)");

        // Energy Per Damage ---------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_ENERGY_CONSUMPTION, "Energy Per Damage");

        // Armor (Physical) ----------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_VALUE_PHYSICAL, "Armor (Physical)");

        // pts -----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_POINTS, "pts");

        // Auto-Feeder Efficiency ----------------------------------------------------------------------
        addTradeoff(MPSConstants.EATING_EFFICIENCY, "Auto-Feeder Efficiency");

        // Range ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.BLINK_DRIVE_RANGE, "Range");

        // Blue ----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.BLUE, "Blue");

        // Lux Capacitor Blue Hue ----------------------------------------------------------------------
        addTradeoff(MPSConstants.BLUE_HUE, "Lux Capacitor Blue Hue");

        // Carry-through -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.CARRY_THROUGH, "Carry-through");

        // Compensation --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.COMPENSATION, "Compensation");

        // Cooling Bonus -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.COOLING_BONUS, "Cooling Bonus");

        // Daytime Energy Generation -------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION_DAY, "Daytime Energy Generation");

        // Daytime Heat Generation ---------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION_DAY, "Daytime Heat Generation");

        // Diameter ------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.DIAMETER, "Diameter");

        // Efficiency ----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.EFFICIENCY, "Efficiency");

        // Enchantment Level ---------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENCHANTMENT_LEVEL, "Enchantment Level");

        // Energy Consumption --------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_CONSUMPTION, "Energy Consumption");

        // Energy Generated ----------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATED, "Energy Generated");

        // Energy Per Block Per Second -----------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION, "Energy Per Block Per Second");

        // FOV multiplier ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FIELD_OF_VIEW, "FOV multiplier");

        // Field of View -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FOV, "Field of View");

        // Field Strength ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.MODULE_FIELD_STRENGTH, "Field Strength");

        // Tank Size -----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FLUID_TANK_SIZE, "Tank Size");

        // Fortune Energy Consumption ------------------------------------------------------------------
        addTradeoff(MPSConstants.FORTUNE_ENERGY_CONSUMPTION, "Fortune Energy Consumption");

        // Fortune Level -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FORTUNE_ENCHANTMENT_LEVEL, "Fortune Level");

        // Green ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.GREEN, "Green");

        // Lux Capacitor Green Hue ---------------------------------------------------------------------
        addTradeoff(MPSConstants.GREEN_HUE, "Lux Capacitor Green Hue");

//        // Harvest Speed -------------------------------------------------------------------------------
//        addTradeoff(harvSpeed", "Harvest Speed");

        // Harvest Speed -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.HARVEST_SPEED, "Harvest Speed");

        // Heat Activation Percent ---------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_ACTIVATION_PERCENT, "Heat Activation Percent");

        // Heat Emission -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_EMISSION, "Heat Emission");

        // Heat Generation -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION, "Heat Generation");

//        // Heat Generation -----------------------------------------------------------------------------
//        addTradeoff(heatGeneration", "Heat Generation");

        // Impact --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.IMPACT, "Impact");

        // Jetboots Thrust -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.JETBOOTS_THRUST, "Jetboots Thrust");

        // Jetpack Thrust ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.JETPACK_THRUST, "Jetpack Thrust");

        // Knockback Resistance ------------------------------------------------------------------------
        addTradeoff(MPSConstants.KNOCKBACK_RESISTANCE, "Knockback Resistance");

//        // Maximum Heat --------------------------------------------------------------------------------
//        addTradeoff(maxHeat", "Maximum Heat");

        // Melee Damage --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.PUNCH_DAMAGE, "Melee Damage");

        // Melee Knockback -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.PUNCH_KNOCKBACK, "Melee Knockback");

        // Movement Resistance -------------------------------------------------------------------------
        addTradeoff(MPSConstants.MOVEMENT_RESISTANCE, "Movement Resistance");

        // Multiplier ----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.MULTIPLIER, "Multiplier");

        // Nighttime Energy Generation -----------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION_NIGHT, "Nighttime Energy Generation");

        // Nighttime Heat Generation -------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION_NIGHT, "Nighttime Heat Generation");

        // Lux Capacitor Opacity -----------------------------------------------------------------------
        addTradeoff(MPSConstants.OPACITY, "Lux Capacitor Opacity");

        // Overclock -----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.OVERCLOCK, "Overclock");

        // Plasma Damage At Full Charge ----------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, "Plasma Damage At Full Charge");

        // Plasma Energy Per Tick ----------------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, "Plasma Energy Per Tick");

        // Plasma Explosiveness ------------------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS, "Plasma Explosiveness");

        // Power ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.POWER, "Power");

        // Radius --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RADIUS, "Radius");

        // Railgun Energy Cost -------------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_ENERGY_COST, "Railgun Energy Cost");

        // Railgun Heat Emission -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_HEAT_EMISSION, "Railgun Heat Emission");

        // Railgun Total Impulse -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_TOTAL_IMPULSE, "Railgun Total Impulse");

        // Range ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RANGE, "Range");

        // Red -----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RED, "Red");

        // Lux Capacitor Red Hue -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RED_HUE, "Lux Capacitor Red Hue");

        // Silk Touch Energy Consumption ---------------------------------------------------------------
        addTradeoff(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION, "Silk Touch Energy Consumption");

        // Spinning Blade Damage -----------------------------------------------------------------------
        addTradeoff(MPSConstants.BLADE_DAMAGE, "Spinning Blade Damage");

        // Sprint Assist -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_ASSIST, "Sprint Assist");

        // Sprint Energy Consumption -------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_ENERGY_CONSUMPTION, "Sprint Energy Consumption");

        // Sprint Exhaustion Compensation --------------------------------------------------------------
        addTradeoff(MPSConstants.FOOD_COMPENSATION, "Sprint Exhaustion Compensation");

        // Sprint Speed Multiplier ---------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_SPEED_MULTIPLIER, "Sprint Speed Multiplier");

//        // Energy Generation ---------------------------------------------------------------------------
//        addTradeoff(thermalEnergyGen", "Energy Generation");

        // Thrust --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.THRUST, "Thrust");

        // Underwater Movement Boost -------------------------------------------------------------------
        addTradeoff(MPSConstants.SWIM_BOOST_AMOUNT, "Underwater Movement Boost");

        // Verticality ---------------------------------------------------------------------------------
        addTradeoff(MPSConstants.VERTICALITY, "Verticality");

        // Voltage -------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.VOLTAGE, "Voltage");

        // Walking Assist ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_ASSISTANCE, "Walking Assist");

        // Walking Energy Consumption ------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_ENERGY_CONSUMPTION, "Walking Energy Consumption");

        // Walking Speed Multiplier --------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_SPEED_MULTIPLIER, "Walking Speed Multiplier");

        // Y-look ratio --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FLIGHT_VERTICALITY, "Y-look ratio");
    }

    @Override
    public void addKeybinds() {
        // Cycle Tool Backward (MPS) -------------------------------------------------------------------
        add("keybinding." + MPSConstants.MOD_ID + ".cycleToolBackward", "Cycle Tool Backward (MPS)");

        // Cycle Tool Forward (MPS) --------------------------------------------------------------------
        add("keybinding." + MPSConstants.MOD_ID + ".cycleToolForward", "Cycle Tool Forward (MPS)");

        // Toggle Module -------------------------------------------------------------------------------
        add("keybinding." + MPSConstants.MOD_ID + ".toggleModule", "Toggle %s");
    }

    @Override
    public void addModels() {
        // Generic armor model parts ===================================================================
        // Head ----------------------------------------------------------------------------------------
        add("javaModel.head.Head.partName", "Head");

        // Body ----------------------------------------------------------------------------------------
        add("javaModel.chest.Body.partName", "Body");

        // Chest ---------------------------------------------------------------------------------------
        add("javaModel.chest.partName.partName","Chest");

        // Left Arm ------------------------------------------------------------------------------------
        add("javaModel.chest.LeftArm.partName", "LeftArm");

        // Right Arm -----------------------------------------------------------------------------------
        add("javaModel.chest.RightArm.partName", "RightArm");

        // Legs ----------------------------------------------------------------------------------------
        add("javaModel.legs.partName", "Legs");

        // Left Leg ------------------------------------------------------------------------------------
        add("javaModel.legs.LeftLeg.partName", "LeftLeg");

        // Right Leg -----------------------------------------------------------------------------------
        add("javaModel.legs.RightLeg.partName",  "RightLeg");

        // Feet ----------------------------------------------------------------------------------------
        add("javaModel.feet.partName", "Feet");

        // Left Foot -----------------------------------------------------------------------------------
        add("javaModel.feet.LeftFoot.partName", "LeftFoot");

        // Right Foot ----------------------------------------------------------------------------------
        add("javaModel.feet.RightFoot.partName", "RightFoot");

        // Armor skins ---------------------------------------------------------------------------------
        add("javaModel.citizenjoe_armorskin.specName", "Citizen Joe Armor Skin");

        // Default ArmorSkin ---------------------------------------------------------------------------
        add("javaModel.default_armorskin.specName", "Default Armor Skin");

        // Power Fist ==================================================================================
        add("javaModel.powerfist.specName", "Power Fist");

        // Main Arm ------------------------------------------------------------------------------------
        addPowerfistPart("mainarm", "Main Arm");

        // Armor Right ---------------------------------------------------------------------------------
        addPowerfistPart("armorright", "Armor Right");

        // Armor Left ----------------------------------------------------------------------------------
        addPowerfistPart("armorleft", "Armor Left");

        // Wrist Top Right -----------------------------------------------------------------------------
        addPowerfistPart("wristtopright", "Wrist Top Right");

        // Wrist Top Left ------------------------------------------------------------------------------
        addPowerfistPart("wristtopleft", "Wrist Top Left");

        // Wrist Bottom Right --------------------------------------------------------------------------
        addPowerfistPart("wristbottomright", "Wrist Bottom Right");

        // Wrist Bottom Left ---------------------------------------------------------------------------
        addPowerfistPart("wristbottomleft", "Wrist Bottom Left");

        // Finger Guard --------------------------------------------------------------------------------
        addPowerfistPart("fingerguard", "Finger Guard");

        // Crystal Holder ------------------------------------------------------------------------------
        addPowerfistPart("crystalholder", "Crystal Holder");

        // Crystal -------------------------------------------------------------------------------------
        addPowerfistPart("crystal", "Crystal");

        // Support Right 1 -----------------------------------------------------------------------------
        addPowerfistPart("supportright1", "Support Right 1");

        // Support Right 2 -----------------------------------------------------------------------------
        addPowerfistPart("supportright2", "Support Right 2");

        // Support Right 3 -----------------------------------------------------------------------------
        addPowerfistPart("supportright3", "Support Right 3");

        // Support Right 4 -----------------------------------------------------------------------------
        addPowerfistPart("supportright4", "Support Right 4");

        // Support Right 5 -----------------------------------------------------------------------------
        addPowerfistPart("supportright5", "Support Right 5");

        // Support Base Right --------------------------------------------------------------------------
        addPowerfistPart("supportbaseright", "Support Base Right");

        // Support Right Front -------------------------------------------------------------------------
        addPowerfistPart("supportrightfront", "Support Right Front");

        // Support Base Left ---------------------------------------------------------------------------
        addPowerfistPart("supportbaseleft", "Support Base Left");

        // Support Left Front --------------------------------------------------------------------------
        addPowerfistPart("supportleftfront", "Support Left Front");

        // Support Left 1 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft1", "Support Left 1");

        // Support Left 2 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft2", "Support Left 2");

        // Support Left 3 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft3", "Support Left 3");

        // Support Left 4 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft4", "Support Left 4");

        // Support Left 5 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft5", "Support Left 5");

        // Palm ----------------------------------------------------------------------------------------
        addPowerfistPart("palm", "Palm");

        // Index 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.index1", "Index 1");

        // Index 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.index1.index2", "Index 2");

        // Middle Finger 1 -----------------------------------------------------------------------------
        addPowerfistPart("palm.middlefinger1", "Middle Finger 1");

        // Middle Finger 2 -----------------------------------------------------------------------------
        addPowerfistPart("palm.middlefinger1.middlefinger2", "Middle Finger 2");

        // Ring Finger 1 -------------------------------------------------------------------------------
        addPowerfistPart("palm.ringfinger1", "Ring Finger 1");

        // Ring Finger 2 -------------------------------------------------------------------------------
        addPowerfistPart("palm.ringfinger1.ringfinger2", "Ring Finger 2");

        // Pinky 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.pinky1", "Pinky 1");

        // Pinky 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.pinky1.pinky2", "Pinky 2");

        // Thumb 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.thumb1", "Thumb 1");

        // Thumb 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.thumb1.thumb2", "Thumb 2");

        // MPS Armor ===================================================================================
        // Helm ----------------------------------------------------------------------------------------
        add("model.mps_helm.modelName", "MPS Helm");

        // Helmet --------------------------------------------------------------------------------------
        add("model.mps_helm.helm_main.partName", "Helmet");

        // Tubes ---------------------------------------------------------------------------------------
        add("model.mps_helm.helm_tubes.partName", "Tubes");

        // Left Tube Entry -----------------------------------------------------------------------------
        add("model.mps_helm.helm_tube_entry1.partName", "Left Tube Entry");

        // Right Tube Entry ----------------------------------------------------------------------------
        add("model.mps_helm.helm_tube_entry2.partName", "Right Tube Entry");

        // Visor ---------------------------------------------------------------------------------------
        add("model.mps_helm.visor.partName", "Visor");

        // MPS Chestplate ------------------------------------------------------------------------------
        add("model.mps_chest.modelName", "MPS Chestplate");

        // Chest Plating -------------------------------------------------------------------------------
        add("model.mps_chest.chest_main.partName", "Chest Plating");

        // Chest Padding -------------------------------------------------------------------------------
        add("model.mps_chest.chest_padding.partName", "Chest Padding");

        // Belt Crystal --------------------------------------------------------------------------------
        add("model.mps_chest.crystal_belt.partName", "Belt Crystal");

        // Backpack ------------------------------------------------------------------------------------
        add("model.mps_chest.backpack.partName", "Backpack");

        // Belt ----------------------------------------------------------------------------------------
        add("model.mps_chest.belt.partName", "Belt");

        // Accessory ----------------------------------------------------------------------------------
        add("model.mps_chest.polySurface36.partName", "Accessory");

        // MPS Arms ------------------------------------------------------------------------------------
        add("model.mps_arms.modelName", "MPS Arms");

        // Left Arm ------------------------------------------------------------------------------------
        add("model.mps_arms.arms2.partName", "Left Arm");

        // Right Arm -----------------------------------------------------------------------------------
        add("model.mps_arms.arms3.partName", "Right Arm");

        // Left Shoulder Crystal -----------------------------------------------------------------------
        add("model.mps_arms.crystal_shoulder_1.partName", "Left Shoulder Crystal");

        // Right Shoulder Crystal ----------------------------------------------------------------------
        add("model.mps_arms.crystal_shoulder_2.partName", "Right Shoulder Crystal");

        // MPS Jetpack ---------------------------------------------------------------------------------
        add("model.jetpack.modelName", "MPS Jetpack");

        // Main ----------------------------------------------------------------------------------------
        add("model.jetpack.default.partName", "Main");

        // Secondary -----------------------------------------------------------------------------------
        add("model.jetpack.jetpack5.partName", "Secondary");

        // Lights --------------------------------------------------------------------------------------
        add("model.jetpack.jetpack_glow.partName", "Lights");

        // MPS Pantaloons ------------------------------------------------------------------------------
        add("model.mps_pantaloons.modelName", "MPS Pantaloons");

        // Right Leg -----------------------------------------------------------------------------------
        add("model.mps_pantaloons.leg1.partName", "Right Leg");

        // Left Leg ------------------------------------------------------------------------------------
        add("model.mps_pantaloons.leg2.partName", "Left Leg");

        // MPS Boots -----------------------------------------------------------------------------------
        add("model.mps_boots.modelName", "MPS Boots");

        // Right Boot ----------------------------------------------------------------------------------
        add("model.mps_boots.boots1.partName", "Right Boot");

        // Left Boot -----------------------------------------------------------------------------------
        add("model.mps_boots.boots2.partName", "Left Boot");

        // Armor 2 =====================================================================================
        add("model.armor2.modelName", "Armor 2");

        // Helmet --------------------------------------------------------------------------------------
        addModel2Part("helmetmain", "Helmet");

        // Helmet Tubes and Visor ----------------------------------------------------------------------
        addModel2Part("helmetglow1", "Helmet Tubes and Visor");

        // Front Chest Protection ----------------------------------------------------------------------
        addModel2Part("chestmain", "Front Chest Protection");

        // Chest Base ----------------------------------------------------------------------------------
        addModel2Part("chestgray", "Chest Base");

        // Back Chest Protection -----------------------------------------------------------------------
        addModel2Part("chestback1", "Back Chest Protection");

        // Spinal Power Crystals -----------------------------------------------------------------------
        addModel2Part("chestglowback", "Spinal Power Crystals");

        // Front Power Crystal -------------------------------------------------------------------------
        addModel2Part("chestglowfront", "Front Power Crystal");

        // Arm and Shoulder Protection -----------------------------------------------------------------
        addModel2Part("armmain", "Arm and Shoulder Protection");

        // Arm and Shoulder Protection 1 ---------------------------------------------------------------
        addModel2Part("armmain1", "Arm and Shoulder Protection");

        // Lef Boot Glow -------------------------------------------------------------------------------
        addModel2Part("bootglow", "Left Boot Glow");

        // Right Boot Glow -----------------------------------------------------------------------------
        addModel2Part("bootglow1", "Right Boot Glow");

        // Left Boot -----------------------------------------------------------------------------------
        addModel2Part("bootmain", "Left Boot");

        // Right Boot ----------------------------------------------------------------------------------
        addModel2Part("bootmain1", "Right Boot");

        // Shoulder Light ------------------------------------------------------------------------------
        addModel2Part("armglow", "Shoulder Light");

        // Shoulder Light 1 -----------------------------------------------------------------------------
        addModel2Part("armglow1", "Shoulder Light");

        // Arm Base ------------------------------------------------------------------------------------
        addModel2Part("armgray",  "Arm Base");

        // Arm Base 1 ----------------------------------------------------------------------------------
        addModel2Part("armgray1", "Arm Base");

        // Left Leg Base -------------------------------------------------------------------------------
        addModel2Part("leggray", "Left Leg Base");

        // Right Leg Base ------------------------------------------------------------------------------
        addModel2Part("leggray1", "Right Leg Base");

        // Left Leg Protection -------------------------------------------------------------------------
        addModel2Part("legbit", "Left Leg Protection");

        // Right Leg Protection ------------------------------------------------------------------------
        addModel2Part("legbit1", "Right Leg Protection");

        // Left Leg Light ------------------------------------------------------------------------------
        addModel2Part("legglow", "Left Leg Light");

        // Right Leg Light -----------------------------------------------------------------------------
        addModel2Part("legglow1", "Right Leg Light");
    }

    @Override
    public void addPowerfistPart(String part, String translation) {
        add("javaModel.powerfist." + part + ".partName", translation);
    }

    @Override
    public void addTradeoff(String tradeoff, String translation) {
        add("module.tradeoff." + tradeoff, translation);
    }

    @Override
    public void addModel2Part(String part, String translation) {
        add("model.armor2." + part + ".partName", translation);
    }

    @Override
    public void addItemDescriptions(Item key, String description) {
        add(key.getDescriptionId() + ".desc", description);
    }
}

