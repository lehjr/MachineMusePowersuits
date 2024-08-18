package com.lehjr.numina.data.client;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaBlocks;
import com.lehjr.numina.common.registration.NuminaEntities;
import com.lehjr.numina.common.registration.NuminaItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

public class NuminaLanguageProvider_EN_US extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_EN_US(PackOutput output) {
        super(output, NuminaConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItemGroup();
        addGui();
        addArmorStand();
        addModuleTradeoffs();
        addChargingBase();
        addToolTips();
        addBatteries();
        addComponents();
        addModuleCategories();
    }

    @Override
    public void addItemGroup() {
        add(NuminaConstants.CREATIVE_TAB_TRANSLATION_KEY, "Numina");
    }

    @Override
    public void addGui() {
        add("gui.numina.chargingbase", "Charging Base");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Charges an entity's equipped items");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "Energy");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "Toggle field of view fix");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "Field of view fix enabled");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "Field of view fix disabled");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "Install");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC, "Installs module into selected modular item while player is in creative mode");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Install All");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Installs all top tier compatible modules into selected modular item while player is in creative mode");
    }

    @Override
    public void addArmorStand() {
        add(NuminaItems.ARMOR_STAND_ITEM.get(), "Armor Stand");
        add(NuminaEntities.ARMOR_STAND__ENTITY_TYPE.get(),  "Armor Stand");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "Maximum Energy");

        add("module.tradeoff.maxTransfer", "Maximum Transfer per Tick");
    }

    @Override
    public void addChargingBase() {
        add(NuminaBlocks.CHARGING_BASE_BLOCK.get(), "Charging Base");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Press SHIFT for more information.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Change modes: Press and hold the number of the hotbar slot the Power Fist is in.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "Energy: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "No installed modules! This item is useless until you add some modules at a Tinker Table.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES, "Installed Modules:");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "Mode: ");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaItems.BATTERY_1.get(), "Basic Battery");

        addItemDescriptions(NuminaItems.BATTERY_1.get(), "Integrate a battery to allow the item to store energy.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaItems.BATTERY_2.get(), "Advanced Battery");

        addItemDescriptions(NuminaItems.BATTERY_2.get(), "Integrate a battery to allow the item to store energy.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaItems.BATTERY_3.get(), "Elite Battery");

        addItemDescriptions(NuminaItems.BATTERY_3.get(), "Integrate a the most advanced battery to store an extensive amount of energy.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaItems.BATTERY_4.get(), "Ultimate Battery");

        addItemDescriptions(NuminaItems.BATTERY_4.get(), "Experimental high end power storage device, now with almost no chance of exploding (we think). Despite nasty rumors, it is not based on stolen alien technology.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaItems.ARTIFICIAL_MUSCLE.get(), "Artificial Muscle");
        addItemDescriptions(NuminaItems.ARTIFICIAL_MUSCLE.get(), "An electrical, artificial muscle, with less range of movement than human muscle but orders of magnitude more strength.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaItems.CARBON_MYOFIBER.get(), "Carbon Myofiber");
        addItemDescriptions(NuminaItems.CARBON_MYOFIBER.get(), "A small bundle of carbon fibers, refined for use in artificial muscles.");

        // Capacitor -----------------------------------------------------------------------------------
        add(NuminaItems.CAPACITOR_1.get(), "Capacitor Mk1");
        addItemDescriptions(NuminaItems.CAPACITOR_1.get(), "A very basic capacitor.");

        add(NuminaItems.CAPACITOR_2.get(), "Capacitor Mk2");
        addItemDescriptions(NuminaItems.CAPACITOR_2.get(), "A slightly improved capacitor.");

        add(NuminaItems.CAPACITOR_3.get(), "Capacitor Mk3");
        addItemDescriptions(NuminaItems.CAPACITOR_3.get(), "A drastically improved capacitor.");

        add(NuminaItems.CAPACITOR_4.get(), "Capacitor Mk4");
        addItemDescriptions(NuminaItems.CAPACITOR_4.get(), "A drastically improved, overly powerful capacitor.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaItems.COMPUTER_CHIP.get(), "Computer Chip");
        addItemDescriptions(NuminaItems.COMPUTER_CHIP.get(), "An upgraded control circuit that contains a CPU which is capable of more advanced calculations.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaItems.CONTROL_CIRCUIT_1.get(),  "Primitive Control Circuit");
        addItemDescriptions(NuminaItems.CONTROL_CIRCUIT_1.get(), "Are you sure this thing will actually work? Is it supposed to smoke like that?");

        add(NuminaItems.CONTROL_CIRCUIT_2.get(),  "Prototype Control Circuit");
        addItemDescriptions(NuminaItems.CONTROL_CIRCUIT_2.get(), "Oh, look. It kind of works now.");

        add(NuminaItems.CONTROL_CIRCUIT_3.get(),  "Prototype Control Circuit Mk2");
        addItemDescriptions(NuminaItems.CONTROL_CIRCUIT_3.get(), "A simple networkable microcontroller for coordinating an individual component.");

        add(NuminaItems.CONTROL_CIRCUIT_4.get(),  "Control Circuit");
        addItemDescriptions(NuminaItems.CONTROL_CIRCUIT_4.get(),  "A highly advanced networkable microcontroller.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaItems.FIELD_EMITTER.get(), "Force Field Emitter");

        addItemDescriptions(NuminaItems.FIELD_EMITTER.get(), "An advanced device which directly manipulates electromagnetic and gravitational fields in an area.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaItems.GLIDER_WING.get(), "Glider Wing");

        addItemDescriptions(NuminaItems.GLIDER_WING.get(), "A lightweight aerodynamic wing with an electromagnet for quick deployment and retraction.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaItems.ION_THRUSTER.get(), "Ion Thruster");

        addItemDescriptions(NuminaItems.ION_THRUSTER.get(), "Essentially a miniature particle accelerator. Accelerates ions to near-light speed to produce thrust.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaItems.LASER_EMITTER.get(), "Hologram Emitter");

        addItemDescriptions(NuminaItems.LASER_EMITTER.get(), "A multicolored laser array which can cheaply alter the appearance of something.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaItems.MAGNET.get(), "Magnet");

        addItemDescriptions(NuminaItems.MAGNET.get(), "A metallic device that generates a magnetic field which pulls items towards the player.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaItems.MYOFIBER_GEL.get(), "Myofiber Gel");

        addItemDescriptions(NuminaItems.MYOFIBER_GEL.get(), "A thick, conductive paste, perfect for fitting between myofibers in an artificial muscle.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaItems.PARACHUTE.get(), "Parachute");

        addItemDescriptions(NuminaItems.PARACHUTE.get(), "A simple reusable parachute which can be deployed and recovered in midair.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaItems.RUBBER_HOSE.get(), "Insulated Rubber Hose");

        addItemDescriptions(NuminaItems.RUBBER_HOSE.get(), "A heavily insulated rubber hose capable of withstanding extreme heat or cold");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaItems.SERVO.get(), "Servo Motor");

        addItemDescriptions(NuminaItems.SERVO.get(), "A special type of motor which uses a pulse-modulated signal to enact very precise movements.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaItems.SOLAR_PANEL.get(), "Solar Panel");

        addItemDescriptions(NuminaItems.SOLAR_PANEL.get(), "A light sensitive device that will generate electricity from the sun.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaItems.SOLENOID.get(), "Solenoid");

        addItemDescriptions(NuminaItems.SOLENOID.get(), "Wires wound around a ferromagnetic core produces a basic electromagnet.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaItems.WIRING_COPPER.get(), "Wiring");

        addItemDescriptions(NuminaItems.WIRING_COPPER.get(), "A special type of wiring with high voltaic capacity and precision, necessary for the sensitive electronics in power armor.");
    }

    @Override
    public void addModuleCategories() {
        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "None");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "Debug Modules");

        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "Armor Modules");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "Energy Storage");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "Energy Generation");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "Tool");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "Weapon");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "Movement");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "Cosmetic");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "Vision");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "Environment");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "Special");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(),  "Mining Enhancement");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "Mining Enchantment");
    }

    @Override
    public void addItemDescriptions(Item key, String description) {
        add(key.getDescriptionId() + ".desc", description);
    }
}
