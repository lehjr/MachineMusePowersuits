package powersuits.client.lang;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class MPSLanguageProvider extends LanguageProvider {
    public MPSLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {

        addModules();
        addBlocks();
        addGui();
        addKeybinds();
        addModuleTradeoff();
    }



    void addModule(Item key, String translation, String description) {
        String id = key.getDescriptionId();
        add(key.getDescriptionId(), translation);
        add(new StringBuilder(id).append(".desc").toString(), description);
    }

    void addModules() {




    }

    void addBlocks() {
        add(MPSBlocks.LUX_CAPACITOR_BLOCK.get(), "Lux Capacitor");
        add(MPSBlocks.TINKER_TABLE_BLOCK.get(), "Power Armor Tinker Table");
    }

    void addGui() {
        add("gui.powersuits.armor", "Armor");
        add("gui.powersuits.cancel", "Cancel");
        add("gui.powersuits.colorPrefix", "Color:");
        add("gui.powersuits.compatible.modules", "Compatible Modules");
        add("gui.powersuits.creative.install", "Install");
        add("gui.powersuits.creative.install.desc", "Installs module into selected modular item while player is in creative mode");
        add("gui.powersuits.energyStorage", "Energy Storage");
        add("gui.powersuits.equippedTotals", "Equipped Totals");
        add("gui.powersuits.install", "Install");
        add("gui.powersuits.install.desc", "Installs the mopdule in the selected item");
        add("gui.powersuits.installed.modules", "Installed Modules");
        add("gui.powersuits.load", "Load");
        add("gui.powersuits.new", "New");
        add("gui.powersuits.noModulesFound.line1", "No Modular Powersuit items");
        add("gui.powersuits.noModulesFound.line2", "found in inventory. Make some!");
        add("gui.powersuits.reset", "Reset");
        add("gui.powersuits.salvage", "Salvage");
        add("gui.powersuits.salvage.desc", "Remove module from selected item and return to player");
        add("gui.powersuits.save", "Save");
        add("gui.powersuits.saveAs", "Save as:");
        add("gui.powersuits.savesuccessful", "Save Successful");
        add("gui.powersuits.showOnHud", "Show on HUD:");
        add("gui.powersuits.tab.install.salvage", "Install/Salvage");
        add("gui.powersuits.tab.keybinds", "Keybinds");
        add("gui.powersuits.tab.module.tweak", "Module Tweak");
        add("gui.powersuits.tab.visual", "Visual");
        add("gui.powersuits.tinker", "Tinker");
        // FIXME: Inconsistant translation key
        add("powersuits.modularitem.inventory", "Modular Item Inventory");
    }

    void addKeybinds() {
        add("keybinding.minecraft.clock", "Toggle Clock");
        add("keybinding.minecraft.compass", "Toggle Compass");
        add("keybinding.powersuits.selective_miner", "Toggle AoE");
        add("keybinding.powersuits.aqua_affinity", "Toggle Aqua Affinity");
        add("keybinding.powersuits.auto_feeder", "Toggle Auto-Feeder");
        add("keybinding.powersuits.binoculars", "Toggle Binoculars");
        add("keybinding.powersuits.climb_assist", "Toggle Uphill Step Assist");
        add("keybinding.powersuits.cooling_system", "Toggle Cooling System");
        add("keybinding.powersuits.cycleToolBackward", "Cycle Tool Backward (MPS)");
        add("keybinding.powersuits.cycleToolForward", "Cycle Tool Forward (MPS)");
        add("keybinding.powersuits.energy_shield", "Toggle Energy Shield");
        add("keybinding.powersuits.flight_control", "Toggle Flight Control");
        add("keybinding.powersuits.fluid_tank", "Toggle Fluid Tank");
        add("keybinding.powersuits.fortune", "Toggle Fortune Enchantment");
        add("keybinding.powersuits.generator_kinetic", "Toggle Kinetic Generator");
        add("keybinding.powersuits.generator_solar", "Toggle Solar Generator");
        add("keybinding.powersuits.generator_solar_adv", "Toggle Advanced Solar Generator");
        add("keybinding.powersuits.generator_thermal", "Toggle Thermal Generator");
        add("keybinding.powersuits.glider", "Toggle Glider");
        add("keybinding.powersuits.goDownKey", "Go Down (MPS Flight Control)");
        add("keybinding.powersuits.invisibility", "Toggle Active Camouflage");
        add("keybinding.powersuits.jet_boots", "Toggle Jet Boots");
        add("keybinding.powersuits.jetpack", "Toggle Jetpack");
        add("keybinding.powersuits.jump_assist", "Toggle Jump Assist");
        add("keybinding.powersuits.magnet", "Toggle Magnet");
        add("keybinding.powersuits.mob_repulsor", "Toggle Mob Repulsor");
        add("keybinding.powersuits.night_vision", "Toggle Night Vision");
        add("keybinding.powersuits.openCosmeticGUI", "Open MPS Cosmetic GUI");
        add("keybinding.powersuits.openInstallSalvageGUI", "Cosmetic GUI (MPS)");
        add("keybinding.powersuits.openKeybindGui", "Open MPS Keybind GUI");
        add("keybinding.powersuits.openModuleTweakGUI", "Open MPS Keybind GUI");
        add("keybinding.powersuits.parachute", "Toggle Parachute");
        add("keybinding.powersuits.shock_absorber", "Toggle Shock Absorber");
        add("keybinding.powersuits.silk_touch", "Toggle Silk Touch Enchantment");
        add("keybinding.powersuits.sprint_assist", "Toggle Sprint Assist");
        add("keybinding.powersuits.swim_assist", "Toggle); Swim Boost");
        add("keybinding.powersuits.transparent_armor", "Toggle Transparent Armor");
        add("keybinding.powersuits.tunnel_bore", "Toggle Tunnel Bore");
        add("keybinding.powersuits.vein_miner", "Toggle Vein Miner");
        add("keybinding.powersuits.water_electrolyzer", "Toggle Water Electrolyzer");
    }

    void addModels() {
        // Generic armor model parts -------------------------------------------------------------------
        add("javaModel.head.Head.partName", "Head");

        add("javaModel.chest.Body.partName", "Body");
        add("javaModel.chest.partName.partName", "Chest");
        add("javaModel.chest.LeftArm.partName", "LeftArm");
        add("javaModel.chest.RightArm.partName", "RightArm");

        add("javaModel.legs.partName", "Legs");
        add("javaModel.legs.LeftLeg.partName", "LeftLeg");
        add("javaModel.legs.RightLeg.partName", "RightLeg");

        add("javaModel.feet.partName", "Feet");
        add("javaModel.feet.LeftFoot.partName", "LeftFoot");
        add("javaModel.feet.RightFoot.partName", "RightFoot");

        // Armor skins ---------------------------------------------------------------------------------
        add("javaModel.citizenjoe_armorskin.specName", "Citizen Joe Armor Skin");
        add("javaModel.default_armorskin.specName", "Default Armor Skin");

        // Power Fist ----------------------------------------------------------------------------------
        add("javaModel.powerfist.specName", "Power Fist");
        add("javaModel.powerfist.mainarm.partName", "Main Arm");
        add("javaModel.powerfist.armorright.partName", "Armor Right");
        add("javaModel.powerfist.armorleft.partName", "Armor Left");
        add("javaModel.powerfist.wristtopright.partName", "Wrist Top Right");
        add("javaModel.powerfist.wristtopleft.partName", "Wrist Top Left");
        add("javaModel.powerfist.wristbottomright.partName", "Wrist Bottom Right");
        add("javaModel.powerfist.wristbottomleft.partName", "Wrist Bottom Left");
        add("javaModel.powerfist.fingerguard.partName", "Finger Guard");
        add("javaModel.powerfist.crystalholder.partName", "Crystal Holder");
        add("javaModel.powerfist.crystal.partName", "Crystal");
        add("javaModel.powerfist.supportright1.partName", "Support Right 1");
        add("javaModel.powerfist.supportright2.partName", "Support Right 2");
        add("javaModel.powerfist.supportright3.partName", "Support Right 3");
        add("javaModel.powerfist.supportright4.partName", "Support Right 4");
        add("javaModel.powerfist.supportright5.partName", "Support Right 5");
        add("javaModel.powerfist.supportbaseright.partName", "Support Base Right");
        add("javaModel.powerfist.supportrightfront.partName", "Support Right Front");
        add("javaModel.powerfist.supportbaseleft.partName", "Support Base Left");
        add("javaModel.powerfist.supportleftfront.partName", "Support Left Front");
        add("javaModel.powerfist.supportleft1.partName", "Support Left 1");
        add("javaModel.powerfist.supportleft2.partName", "Support Left 2");
        add("javaModel.powerfist.supportleft3.partName", "Support Left 3");
        add("javaModel.powerfist.supportleft4.partName", "Support Left 4");
        add("javaModel.powerfist.supportleft5.partName", "Support Left 5");
        add("javaModel.powerfist.palm.partName", "Palm");
        add("javaModel.powerfist.palm.index1.partName", "Index 1");
        add("javaModel.powerfist.palm.index1.index2.partName", "Index 2");
        add("javaModel.powerfist.palm.middlefinger1.partName", "Middle Finger 1");
        add("javaModel.powerfist.palm.middlefinger1.middlefinger2.partName", "Middle Finger 2");
        add("javaModel.powerfist.palm.ringfinger1.partName", "Ring Finger 1");
        add("javaModel.powerfist.palm.ringfinger1.ringfinger2.partName", "Ring Finger 2");
        add("javaModel.powerfist.palm.pinky1.partName", "Pinky 1");
        add("javaModel.powerfist.palm.pinky1.pinky2.partName", "Pinky 2");
        add("javaModel.powerfist.palm.thumb1.partName", "Thumb 1");
        add("javaModel.powerfist.palm.thumb1.thumb2.partName", "Thumb 2");

        // MPS Armor -----------------------------------------------------------------------------------
        add("model.mps_helm.modelName", "MPS Helm");
        add("model.mps_helm.helm_main.partName", "Helmet");
        add("model.mps_helm.helm_tube_entry1.partName", "Left Tube Entry");
        add("model.mps_helm.helm_tube_entry2.partName", "Right Tube Entry");
        add("model.mps_helm.helm_tubes.partName", "Tubes");
        add("model.mps_helm.visor.partName", "Visor");

        add("model.mps_chest.modelName", "MPS Chestplate");
        add("model.mps_chest.chest_main.partName", "Chest Plating");
        add("model.mps_chest.chest_padding.partName", "Chest Padding");
        add("model.mps_chest.crystal_belt.partName", "Belt Crystal");
        add("model.mps_chest.backpack.partName", "Backpack");
        add("model.mps_chest.belt.partName", "Belt");
        add("model.mps_chest.polySurface36.partName", "Accessory");

        add("model.mps_arms.modelName", "MPS Arms");
        add("model.mps_arms.arms2.partName", "Left Arm");
        add("model.mps_arms.arms3.partName", "Right Arm");
        add("model.mps_arms.crystal_shoulder_1.partName", "Left Shoulder Crystal");
        add("model.mps_arms.crystal_shoulder_2.partName", "Right Shoulder Crystal");

        add("model.jetpack.modelName", "MPS Jetpack");
        add("model.jetpack.default.partName", "Main");
        add("model.jetpack.jetpack5.partName", "Secondary");
        add("model.jetpack.jetpack_glow.partName", "Lights");

        add("model.mps_pantaloons.modelName", "MPS Pantaloons");
        add("model.mps_pantaloons.leg1.partName", "Right Leg");
        add("model.mps_pantaloons.leg2.partName", "Left Leg");

        add("model.mps_boots.modelName", "MPS Boots");
        add("model.mps_boots.boots1.partName", "Right Boot");
        add("model.mps_boots.boots2.partName", "Left Boot");

        // Armor 2 -------------------------------------------------------------------------------------
        add("model.armor2.modelName", "Armor 2");

        add("model.armor2.helmetmain.partName", "Helmet");
        add("model.armor2.helmetglow1.partName", "Helmet Tubes and Visor");

        add("model.armor2.chestmain.partName", "Front Chest Protection");
        add("model.armor2.chestgray.partName", "Chest Base");
        add("model.armor2.chestback1.partName", "Back Chest Protection");
        add("model.armor2.chestglowback.partName", "Spinal Power Crystals");
        add("model.armor2.chestglowfront.partName", "Frontal Power Crystal");

        add("model.armor2.armmain.partName", "Arm and Shoulder Protection");
        add("model.armor2.armmain1.partName", "Arm and Shoulder Protection");
        add("model.armor2.bootglow.partName", "Left Boot Glow");
        add("model.armor2.bootglow1.partName", "Right Boot Glow");
        add("model.armor2.bootmain.partName", "Left Boot");
        add("model.armor2.bootmain1.partName", "Right Boot");
        add("model.armor2.armglow.partName", "Shoulder Light");
        add("model.armor2.armglow1.partName", "Shoulder Light");
        add("model.armor2.armgray.partName", "Arm Base");
        add("model.armor2.armgray1.partName", "Arm Base");

        add("model.armor2.leggray.partName", "Left Leg Base");
        add("model.armor2.leggray1.partName", "Right Leg Base");
        add("model.armor2.legbit.partName", "Left Leg Protection");
        add("model.armor2.legbit1.partName", "Right Leg Protection");
        add("model.armor2.legglow.partName", "Left Leg Light");
        add("model.armor2.legglow1.partName", "Right Leg Light");
    }

    void addModuleTradeoff() {
        add("module.tradeoff.activationPercent", "Activation Percent");
        add("module.tradeoff.alpha", "Alpha");
        add("module.tradeoff.amperage", "Amperage");

        // FIXME: translation key changed?
        add("module.tradeoff.aoe2Limit", "Block Limit");

        add("module.tradeoff.aoeMiningDiameter", "AoE Mining Diameter");
        add("module.tradeoff.aquaHarvSpeed", "Harvest Speed");
        add("module.tradeoff.armorEnergy", "Armor (Energy)");
        add("module.tradeoff.armorEnergyPerDamage", "Energy Per Damage");
        add("module.tradeoff.armorPhysical", "Armor (Physical)");
        add("module.tradeoff.armorPoints", "pts");
        add("module.tradeoff.autoFeederEfficiency", "Auto-Feeder Efficiency");
        add("module.tradeoff.blinkDriveRange", "Range");
        add("module.tradeoff.blue", "Blue");
        add("module.tradeoff.blueHue", "Lux Capacitor Blue Hue");
        add("module.tradeoff.carryThrough", "Carry-through");
        add("module.tradeoff.compensation", "Compensation");
        add("module.tradeoff.coolingBonus", "Cooling Bonus");
        add("module.tradeoff.daytimeEnergyGen", "Daytime Energy Generation");
        add("module.tradeoff.daytimeHeatGen", "Daytime Heat Generation");
        add("module.tradeoff.diameter", "Diameter");
        add("module.tradeoff.efficiency", "Efficiency");
        add("module.tradeoff.enchLevel", "Enchantment Level");
        add("module.tradeoff.energyCon", "Energy Consumption");
        add("module.tradeoff.energyGenerated", "Energy Generated");
        add("module.tradeoff.energyPerBlock", "Energy Per Block Per Second");
        add("module.tradeoff.fOVMult", "FOV multiplier");
        add("module.tradeoff.fieldOfView", "Field of View");
        add("module.tradeoff.fieldStrength", "Field Strength");
        add("module.tradeoff.fluidTankSize", "Tank Size");
        add("module.tradeoff.fortuneEnCon", "Fortune Energy Consumption");
        add("module.tradeoff.fortuneLevel", "Fortune Level");
        add("module.tradeoff.green", "Green");
        add("module.tradeoff.greenHue", "Lux Capacitor Green Hue");
        add("module.tradeoff.harvSpeed", "Harvest Speed");
        add("module.tradeoff.harvestSpeed", "Harvest Speed");
        add("module.tradeoff.heatActivationPercent", "Heat Activation Percent");
        add("module.tradeoff.heatEmission", "Heat Emission");
        add("module.tradeoff.heatGen", "Heat Generation");
        add("module.tradeoff.heatGeneration", "Heat Generation");
        add("module.tradeoff.impact", "Impact");
        add("module.tradeoff.jetbootsThrust", "Jetboots Thrust");
        add("module.tradeoff.jetpackThrust", "Jetpack Thrust");
        add("module.tradeoff.knockbackResistance", "Knockback Resistance");
        add("module.tradeoff.maxHeat", "Maximum Heat");
        add("module.tradeoff.meleeDamage", "Melee Damage");
        add("module.tradeoff.meleeKnockback", "Melee Knockback");
        add("module.tradeoff.movementResistance", "Movement Resistance");
        add("module.tradeoff.multiplier", "Multiplier");
        add("module.tradeoff.nightTimeEnergyGen", "Nighttime Energy Generation");
        add("module.tradeoff.nightTimeHeatGen", "Nighttime Heat Generation");
        add("module.tradeoff.opacity", "Lux Capacitor Opacity");
        add("module.tradeoff.overclock", "Overclock");
        add("module.tradeoff.plasmaDamage", "Plasma Damage At Full Charge");
        add("module.tradeoff.plasmaEnergyPerTick", "Plasma Energy Per Tick");
        add("module.tradeoff.plasmaExplosiveness", "Plasma Explosiveness");
        add("module.tradeoff.power", "Power");
        add("module.tradeoff.radius", "Radius");
        add("module.tradeoff.railgunEnergyCost", "Railgun Energy Cost");
        add("module.tradeoff.railgunHeatEm", "Railgun Heat Emission");
        add("module.tradeoff.railgunTotalImpulse", "Railgun Total Impulse");
        add("module.tradeoff.range", "Range");
        add("module.tradeoff.red", "Red");
        add("module.tradeoff.redHue", "Lux Capacitor Red Hue");
        add("module.tradeoff.silkTouchEnCon", "Silk Touch Energy Consumption");
        add("module.tradeoff.spinBladeDam", "Spinning Blade Damage");
        add("module.tradeoff.sprintAssist", "Sprint Assist");
        add("module.tradeoff.sprintEnergyCon", "Sprint Energy Consumption");
        add("module.tradeoff.sprintExComp", "Sprint Exhaustion Compensation");
        add("module.tradeoff.sprintSpeedMult", "Sprint Speed Multiplier");
        add("module.tradeoff.thermalEnergyGen", "Energy Generation");
        add("module.tradeoff.thrust", "Thrust");
        add("module.tradeoff.underwaterMovBoost", "Underwater Movement Boost");
        add("module.tradeoff.vertically", "Verticality");
        add("module.tradeoff.voltage", "Voltage");
        add("module.tradeoff.walkingAssist", "Walking Assist");
        add("module.tradeoff.walkingEnergyCon", "Walking Energy Consumption");
        add("module.tradeoff.walkingSpeedMult", "Walking Speed Multiplier");
        add("module.tradeoff.yLookRatio", "Y-look ratio");
    }
}
