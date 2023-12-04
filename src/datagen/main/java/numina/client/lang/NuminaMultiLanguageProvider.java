package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import numina.client.config.DatagenConfig;
import numina.client.util.lang.MulitiLanguageProvider;
import numina.client.util.lang.translators.ITranslator;
import numina.client.util.lang.translators.Language;

import java.io.IOException;

public class NuminaMultiLanguageProvider extends MulitiLanguageProvider {
    Language mainLanguage;
    public NuminaMultiLanguageProvider(DataGenerator gen, String modid, DatagenConfig config, ITranslator translator) {
        super(gen, modid, config, "main", translator);
        this.mainLanguage = config.getMainLanguageCode();
    }

    @Override
    public void run(CachedOutput output) throws IOException {
        addItemGroup();
        addGui();
        addArmorStand();
        addModuleTradeoffs();
        addChargingBase();
        addToolTips();
        addBatteries();
        addComponents();
        addModuleCategories();
        super.run(output);
    }

    void addItemGroup() {
        addTranslationTopAll(NuminaConstants.ITEM_GROUP_TRANSLATION_KEY, "Numina");
    }

    void addGui() {
        add("gui.numina.chargingbase", mcCodeToLang("en_us"), "Charging Base");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, mcCodeToLang("en_us"), "Charges an entity's equipped items");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", mainLanguage, "Energy");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", mainLanguage, "Toggle field of view fix");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", mainLanguage, "Field of view fix enabled");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", mainLanguage, "Field of view fix disabled");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL,  mainLanguage, "Install");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,   mainLanguage, "Installs module into selected modular item while player is in creative mode");
    }

    void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), mainLanguage, "Armor Stand");

        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE,  mainLanguage, "Armor Stand");
    }

    void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  mainLanguage, "Maximum Energy");

        add("module.tradeoff.maxTransfer", mainLanguage, "Maximum Transfer per Tick");
    }

    void addChargingBase() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), mainLanguage, "Charging Base");
    }

    void addToolTips() {
        addTranslationTopAll(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, mainLanguage, "Press SHIFT for more information.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, mainLanguage, "Change modes: Press and hold the number of the hotbar slot the Power Fist is in.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, mainLanguage, "Energy: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, mainLanguage, "No installed modules! This item is useless until you add some modules at a Tinker Table.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES, mainLanguage, "Installed Modules:");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, mainLanguage, "Mode: ");
    }

    void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), mainLanguage, "Basic Battery");

        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(),mainLanguage, "Integrate a battery to allow the item to store energy.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), mainLanguage, "Advanced Battery");

        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), mainLanguage, "Integrate a battery to allow the item to store energy.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), mainLanguage, "Elite Battery");

        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), mainLanguage, "Integrate a the most advanced battery to store an extensive amount of energy.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), mainLanguage, "Ultimate Battery");

        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), mainLanguage, "Experimental high end power storage device, now with almost no chance of exploding (we think). Despite nasty rumors, it is not based on stolen alien technology.");

        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), mcCodeToLang("zh_tw.json"), "实验性的极高能量存储设备，几乎不会爆炸（我这么认为）。不要相信那些恶毒的谣言，这东西可不是什么偷自外星人的技术。");
    }

    void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), mainLanguage, "Artificial Muscle");

        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), mainLanguage, "An electrical, artificial muscle, with less range of movement than human muscle but orders of magnitude more strength.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), mainLanguage, "Carbon Myofiber");

        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), mainLanguage, "A small bundle of carbon fibers, refined for use in artificial muscles.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), mainLanguage, "Computer Chip");

        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), mainLanguage, "An upgraded control circuit that contains a CPU which is capable of more advanced calculations.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(),  mainLanguage, "Control Circuit");

        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), mainLanguage, "A simple networkable microcontroller for coordinating an individual component.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(),mainLanguage, "Force Field Emitter");

        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), mainLanguage, "An advanced device which directly manipulates electromagnetic and gravitational fields in an area.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), mainLanguage, "Glider Wing");

        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), mainLanguage, "A lightweight aerodynamic wing with an electromagnet for quick deployment and retraction.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), mainLanguage, "Ion Thruster");

        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), mainLanguage, "Essentially a miniature particle accelerator. Accelerates ions to near-light speed to produce thrust.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), mainLanguage, "Hologram Emitter");

        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), mainLanguage, "A multicolored laser array which can cheaply alter the appearance of something.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), mainLanguage, "Magnet");

        addItemDescriptions(NuminaObjects.MAGNET.get(), mainLanguage, "A metallic device that generates a magnetic field which pulls items towards the player.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), mainLanguage, "Myofiber Gel");

        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), mainLanguage, "A thick, conductive paste, perfect for fitting between myofibers in an artificial muscle.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), mainLanguage, "Parachute");

        addItemDescriptions(NuminaObjects.PARACHUTE.get(), mainLanguage, "A simple reusable parachute which can be deployed and recovered in midair.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), mainLanguage, "Insulated Rubber Hose");

        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), mainLanguage, "A heavily insulated rubber hose capable of withstanding extreme heat or cold");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), mainLanguage, "Servo Motor");

        addItemDescriptions(NuminaObjects.SERVO.get(), mainLanguage, "A special type of motor which uses a pulse-modulated signal to enact very precise movements.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), mainLanguage, "Solar Panel");

        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), mainLanguage, "A light sensitive device that will generate electricity from the sun.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), mainLanguage, "Solenoid");

        addItemDescriptions(NuminaObjects.SOLENOID.get(), mainLanguage, "Wires wound around a ferromagnetic core produces a basic electromagnet.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), mainLanguage, "Wiring");

        addItemDescriptions(NuminaObjects.WIRING.get(), mainLanguage, "A special type of wiring with high voltaic capacity and precision, necessary for the sensitive electronics in power armor.");
    }

    void addModuleCategories() {
        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), mainLanguage, "None");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), mainLanguage, "Debug Modules");

        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), mainLanguage, "Armor Modules");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), mainLanguage, "Energy Storage");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), mainLanguage, "Energy Generation");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), mainLanguage, "Tool");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), mainLanguage, "Weapon");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), mainLanguage, "Movement");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), mainLanguage, "Cosmetic");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), mainLanguage, "Vision");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), mainLanguage, "Environment");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), mainLanguage, "Special");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(),  mainLanguage, "Mining Enhancement");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), mainLanguage, "Mining Enchantment");
    }
}
