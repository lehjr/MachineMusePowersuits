package powersuits.client.lang;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSItems;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.DataGenerator;

public class MPSLanguageProvider_EN_US extends AbstractLangageProviderMPS {
    public MPSLanguageProvider_EN_US(DataGenerator output) {
        super(output, MPSConstants.MOD_ID, "en_us");
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

        // Color ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_COLOR_PREFIX, "Color:");

        // Compatible Modules -------------------------------------------------------------------------
        add(MPSConstants.GUI_COMPATIBLE_MODULES, "Compatible Modules");

        // Energy Storage ------------------------------------------------------------------------------
        add(MPSConstants.GUI_ENERGY_STORAGE, "Energy Storage");

        // Equipped Totals -----------------------------------------------------------------------------
        add(MPSConstants.GUI_EQUIPPED_TOTALS, "Equipped Totals");

        // Installed Modules ---------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALLED_MODULES, "Installed Modules");

        // Show on HUD ---------------------------------------------------------------------------------
        add(MPSConstants.GUI_SHOW_ON_HUD, "Show on HUD:");

        // Install/Salvage -----------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL_SALVAGE, "Install/Salvage");

        // Keybinds ------------------------------------------------------------------------------------
        add(MPSConstants.GUI_KEYBINDS, "Keybinds");

        // Module Tweak --------------------------------------------------------------------------------
        add(MPSConstants.GUI_MODULE_TWEAK, "Module Tweak");

        // Visual --------------------------------------------------------------------------------------
        add(MPSConstants.GUI_VISUAL, "Visual");

        // Tinker --------------------------------------------------------------------------------------
        add(MPSConstants.GUI_TINKER_TABLE, "Tinker");

        // Modular Item Inventory ----------------------------------------------------------------------
        add(MPSConstants.GUI_MODULAR_ITEM_INVENTORY, "Modular Item Inventory");
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

        // Harvest Speed -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.HARVEST_SPEED, "Harvest Speed");

        // Heat Activation Percent ---------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_ACTIVATION_PERCENT, "Heat Activation Percent");

        // Heat Emission -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_EMISSION, "Heat Emission");

        // Heat Generation -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION, "Heat Generation");

        // Impact --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.IMPACT, "Impact");

        // Jetboots Thrust -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.JETBOOTS_THRUST, "Jetboots Thrust");

        // Jetpack Thrust ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.JETPACK_THRUST, "Jetpack Thrust");

        // Knockback Resistance ------------------------------------------------------------------------
        addTradeoff(MPSConstants.KNOCKBACK_RESISTANCE, "Knockback Resistance");

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
        // Go down -------------------------------------------------------------------------------------
        add(MPSConstants.GO_DOWN_KEY, "Go Down (MPS)");

        // Cycle Tool Backward (MPS) -------------------------------------------------------------------
        add(MPSConstants.CYCLE_TOOL_BACKWARD, "Cycle Tool Backward (MPS)");

        // Cycle Tool Forward (MPS) --------------------------------------------------------------------
        add(MPSConstants.CYCLE_TOOL_FORWARD, "Cycle Tool Forward (MPS)");

        // Open Keybind Gui ----------------------------------------------------------------------------
        add(MPSConstants.OPEN_KEYBIND_GUI, "Open Keybinding GUI (MPS)");

        // Open Cosmetic Gui ---------------------------------------------------------------------------
        add(MPSConstants.OPEN_COSMETIC_GUI, "Open Cosmetic GUI (MPS)");

        // Open Module Tweak Gui -----------------------------------------------------------------------
        add(MPSConstants.OPEN_MODULE_TWEAK_GUI, "Open Module Teak GUI (MPS)");

        // Open Install/Salvage Gui --------------------------------------------------------------------
        add(MPSConstants.OPEN_INSTALL_SALVAGE_GUI, "Open Install/Salvage GUI (MPS)");

        // Toggle Module -------------------------------------------------------------------------------
        add(MPSConstants.TOGGLE_MODULE, "Toggle %s");
    }

    @Override
    public void addModels() {
        // Generic armor model parts ===================================================================
        // Head ----------------------------------------------------------------------------------------
        addJavaModelPart("head", "Head", "Head");

        // Body ----------------------------------------------------------------------------------------
        addJavaModelPart("chest", "Body", "Body");

        // Chest ---------------------------------------------------------------------------------------
        addJavaModelPart("", "chest","Chest");

        // Left Arm ------------------------------------------------------------------------------------
        addJavaModelPart("chest", "LeftArm", "LeftArm");

        // Right Arm -----------------------------------------------------------------------------------
        addJavaModelPart("chest", "RightArm", "RightArm");

        // Legs ----------------------------------------------------------------------------------------
        addJavaModelPart("", "legs", "Legs");

        // Left Leg ------------------------------------------------------------------------------------
        addJavaModelPart("legs", "LeftLeg", "LeftLeg");

        // Right Leg -----------------------------------------------------------------------------------
        addJavaModelPart("legs", "RightLeg",  "RightLeg");

        // Feet ----------------------------------------------------------------------------------------
        addJavaModelPart("", "feet", "Feet");

        // Left Foot -----------------------------------------------------------------------------------
        addJavaModelPart("feet", "LeftFoot", "LeftFoot");

        // Right Foot ----------------------------------------------------------------------------------
        addJavaModelPart("feet", "RightFoot", "RightFoot");

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
        String MPS_HELM = addOBJModel("mps_helm", "MPS Helm");

        // Helmet --------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_main", "Helmet");

        // Tubes ---------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tubes", "Tubes");

        // Left Tube Entry -----------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tube_entry1", "Left Tube Entry");

        // Right Tube Entry ----------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tube_entry2", "Right Tube Entry");

        // Visor ---------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "visor", "Visor");

        // MPS Chestplate ------------------------------------------------------------------------------
        String MPS_CHEST = addOBJModel("mps_chest", "MPS Chestplate");

        // Chest Plating -------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "chest_main", "Chest Plating");

        // Chest Padding -------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "chest_padding", "Chest Padding");

        // Belt Crystal --------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "crystal_belt", "Belt Crystal");

        // Backpack ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "backpack", "Backpack");

        // Belt ----------------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "belt", "Belt");

        // Accessory ----------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "polySurface36", "Accessory");

        // MPS Arms ------------------------------------------------------------------------------------
        String MPS_ARMS = addOBJModel("mps_arms", "MPS Arms");

        // Left Arm ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "arms2", "Left Arm");

        // Right Arm -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "arms3", "Right Arm");

        // Left Shoulder Crystal -----------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "crystal_shoulder_1", "Left Shoulder Crystal");

        // Right Shoulder Crystal ----------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "crystal_shoulder_2", "Right Shoulder Crystal");

        // MPS Jetpack ---------------------------------------------------------------------------------
        String JETPACK = addOBJModel("jetpack", "MPS Jetpack");

        // Main ----------------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "default", "Main");

        // Secondary -----------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "jetpack5", "Secondary");

        // Lights --------------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "jetpack_glow", "Lights");

        // MPS Pantaloons ------------------------------------------------------------------------------
        String MPS_PANTALOONS = addOBJModel("mps_pantaloons", "MPS Pantaloons");

        // Right Leg -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_PANTALOONS, "leg1", "Right Leg");

        // Left Leg ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_PANTALOONS, "leg2", "Left Leg");

        // MPS Boots -----------------------------------------------------------------------------------
        String MPS_BOOTS = addOBJModel("mps_boots", "MPS Boots");

        // Right Boot ----------------------------------------------------------------------------------
        addOBJModelPart(MPS_BOOTS, "boots1", "Right Boot");

        // Left Boot -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_BOOTS, "boots2", "Left Boot");

        // Armor 2 =====================================================================================
        String ARMOR2 = addOBJModel("armor2", "Armor 2");

        // Helmet --------------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "helmetmain", "Helmet");

        // Helmet Tubes and Visor ----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "helmetglow1", "Helmet Tubes and Visor");

        // Front Chest Protection ----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestmain", "Front Chest Protection");

        // Chest Base ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestgray", "Chest Base");

        // Back Chest Protection -----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestback1", "Back Chest Protection");

        // Spinal Power Crystals -----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestglowback", "Spinal Power Crystals");

        // Front Power Crystal -------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestglowfront", "Front Power Crystal");

        // Arm and Shoulder Protection -----------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armmain", "Arm and Shoulder Protection");

        // Arm and Shoulder Protection 1 ---------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armmain1", "Arm and Shoulder Protection");

        // Lef Boot Glow -------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootglow", "Left Boot Glow");

        // Right Boot Glow -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootglow1", "Right Boot Glow");

        // Left Boot -----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootmain", "Left Boot");

        // Right Boot ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootmain1", "Right Boot");

        // Shoulder Light ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armglow", "Shoulder Light");

        // Shoulder Light 1 -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armglow1", "Shoulder Light");

        // Arm Base ------------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armgray",  "Arm Base");

        // Arm Base 1 ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armgray1", "Arm Base");

        // Left Leg Base -------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "leggray", "Left Leg Base");

        // Right Leg Base ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "leggray1", "Right Leg Base");

        // Left Leg Protection -------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legbit", "Left Leg Protection");

        // Right Leg Protection ------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legbit1", "Right Leg Protection");

        // Left Leg Light ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legglow", "Left Leg Light");

        // Right Leg Light -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legglow1", "Right Leg Light");
    }
}

