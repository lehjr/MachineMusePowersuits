package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

public class NuminaLanguageProvider_ZH_CN extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_ZH_CN(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        addItemGroup();
        addGui();
        addArmorStand();
        addModuleTradeoffs();
        addBlocks();
        addToolTips();
        addBatteries();
        addComponents();
        addModuleCategories();
    }

    @Override
    public void addItemGroup() {
        add(NuminaConstants.ITEM_GROUP_TRANSLATION_KEY, "Numina");
    }

    /*
    {add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "\u5145\u7535\u5e95\u5ea7");
add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "\u88c5\u7532\u67b6");
add("gui.numina.chargingbase", "\u5145\u7535\u5e95\u5ea7");
add(NuminaConstants.GUI_CREATIVE_INSTALL"\u5b89\u88c5");
add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "\u5168\u90e8\u5b89\u88c5");
add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "\u5f53\u73a9\u5bb6\u5904\u4e8e\u521b\u9020\u6a21\u5f0f\u65f6\uff0c\u5c06\u6240\u6709\u9876\u7ea7\u517c\u5bb9\u6a21\u5757\u5b89\u88c5\u5230\u9009\u5b9a\u7684\u6a21\u5757\u5316\u9879\u76ee\u4e2d");
add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,"\u5f53\u73a9\u5bb6\u5904\u4e8e\u521b\u4f5c\u6a21\u5f0f\u65f6\uff0c\u5c06\u6a21\u5757\u5b89\u88c5\u5230\u9009\u5b9a\u7684\u6a21\u5757\u5316\u9879\u76ee\u4e2d");
add(NuminaObjects.ARMOR_STAND_ITEM.get(), "\u88c5\u7532\u67b6");
add(NuminaObjects.ADVANCED_BATTERY.get(), \u5148\u8fdb\u7535\u6c60");
addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "\u96c6\u6210\u7535\u6c60\u4ee5\u5141\u8bb8\u7269\u54c1\u50a8\u5b58\u80fd\u91cf\u3002");
add(NuminaObjects.BASIC_BATTERY.get(), "\u57fa\u672c\u7535\u6c60");
addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "\u96c6\u6210\u7535\u6c60\u4ee5\u5141\u8bb8\u7269\u54c1\u50a8\u5b58\u80fd\u91cf\u3002");
add(NuminaObjects.ELITE_BATTERY.get(), "\u7cbe\u82f1\u7535\u6c60");
addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "\u96c6\u6210\u6700\u5148\u8fdb\u7684\u7535\u6c60\u4ee5\u5b58\u50a8\u5927\u91cf\u80fd\u91cf\u3002");
add(NuminaObjects.ULTIMATE_BATTERY.get(), "\u7ec8\u6781\u7535\u6c60");
addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "\u5b9e\u9a8c\u6027\u7684\u6781\u9ad8\u80fd\u91cf\u5b58\u50a8\u8bbe\u5907\uff0c\u51e0\u4e4e\u4e0d\u4f1a\u7206\u70b8\uff08\u6211\u8fd9\u4e48\u8ba4\u4e3a\uff09\u3002\u4e0d\u8981\u76f8\u4fe1\u90a3\u4e9b\u6076\u6bd2\u7684\u8c23\u8a00\uff0c\u8fd9\u4e1c\u897f\u53ef\u4e0d\u662f\u4ec0\u4e48\u5077\u81ea\u5916\u661f\u4eba\u7684\u6280\u672f\u3002");
add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "\u4eba\u9020\u808c\u8089");
addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "\u4e00\u79cd\u7535\u52a8\u4eba\u9020\u808c\u8089\uff0c\u8fd0\u52a8\u8303\u56f4\u6bd4\u4eba\u4f53\u808c\u8089\u5c0f\uff0c\u4f46\u529b\u91cf\u8981\u5927\u51e0\u4e2a\u6570\u91cf\u7ea7\u3002");
add(NuminaObjects.CARBON_MYOFIBER.get(), "\u78b3\u7ea4\u7ef4\u808c\u7ea4\u7ef4");
addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "\u4e00\u5c0f\u675f\u78b3\u7ea4\u7ef4\uff0c\u7cbe\u5236\u7528\u4e8e\u4eba\u9020\u808c\u8089\u3002");
add(NuminaObjects.COMPUTER_CHIP.get(), "\u7535\u8111\u82af\u7247","item.numina.component_computer_chip.desc":"\u5347\u7ea7\u7684\u63a7\u5236\u7535\u8def\uff0c\u5305\u542b\u4e00\u4e2a\u80fd\u591f\u8fdb\u884c\u66f4\u9ad8\u7ea7\u8ba1\u7b97\u7684CPU\u3002");
add(NuminaObjects.CONTROL_CIRCUIT.get(), "\u63a7\u5236\u7535\u8def");
addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), \u4e00\u4e2a\u7b80\u5355\u7684\u53ef\u8054\u7f51\u5fae\u63a7\u5236\u5668\uff0c\u7528\u4e8e\u534f\u8c03\u5355\u4e2a\u7ec4\u4ef6\u3002","item.numina.component_field_emitter":"\u529b\u573a\u53d1\u5c04\u5668");
addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "\u4e00\u79cd\u5148\u8fdb\u7684\u8bbe\u5907\uff0c\u53ef\u76f4\u63a5\u64cd\u7eb5\u4e00\u4e2a\u533a\u57df\u4e2d\u7684\u7535\u78c1\u573a\u548c\u5f15\u529b\u573a\u3002");
add(NuminaObjects.GLIDER_WING.get(), "\u6ed1\u7fd4\u673a\u7ffc");
addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "\u5e26\u6709\u7535\u78c1\u94c1\u7684\u8f7b\u578b\u7a7a\u6c14\u52a8\u529b\u5b66\u673a\u7ffc\uff0c\u53ef\u5feb\u901f\u5c55\u5f00\u548c\u7f29\u56de\u3002");
add(NuminaObjects.ION_THRUSTER.get(), "\u79bb\u5b50\u63a8\u8fdb\u5668");
addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "\u672c\u8d28\u4e0a\u662f\u4e00\u4e2a\u5fae\u578b\u7c92\u5b50\u52a0\u901f\u5668\u3002\u5c06\u79bb\u5b50\u52a0\u901f\u5230\u8fd1\u5149\u901f\u4ee5\u4ea7\u751f\u63a8\u529b\u3002");
add(NuminaObjects.LASER_EMITTER.get(), "\u5168\u606f\u5f71\u50cf\u53d1\u5c04\u5668");
addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "\u4e00\u79cd\u4e94\u5f69\u7f24\u7eb7\u7684\u6fc0\u5149\u9635\u5217\uff0c\u53ef\u4ee5\u5ec9\u4ef7\u5730\u6539\u53d8\u67d0\u7269\u7684\u5916\u89c2\u3002");
add(NuminaObjects.MAGNET.get(), "\u78c1\u94c1");
addItemDescriptions(NuminaObjects.MAGNET.get(), "\u4e00\u79cd\u91d1\u5c5e\u88c5\u7f6e\uff0c\u53ef\u4ea7\u751f\u78c1\u573a\uff0c\u5c06\u7269\u54c1\u62c9\u5411\u73a9\u5bb6\u3002");
add(NuminaObjects.MYOFIBER_GEL.get(), "\u808c\u7ea4\u7ef4\u51dd\u80f6");
addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "\u4e00\u79cd\u539a\u5b9e\u7684\u5bfc\u7535\u7cca\u72b6\u7269\uff0c\u975e\u5e38\u9002\u5408\u5728\u4eba\u9020\u808c\u8089\u4e2d\u7684\u808c\u7ea4\u7ef4\u4e4b\u95f4\u8d34\u5408\u3002");
add(NuminaObjects.PARACHUTE.get(), "\u964d\u843d\u4f1e");
addItemDescriptions(NuminaObjects.PARACHUTE.get(), "\u4e00\u4e2a\u7b80\u5355\u7684\u53ef\u91cd\u590d\u4f7f\u7528\u7684\u964d\u843d\u4f1e\uff0c\u53ef\u4ee5\u5728\u534a\u7a7a\u4e2d\u90e8\u7f72\u548c\u56de\u6536\u3002");
add(NuminaObjects.RUBBER_HOSE.get(), "\u7edd\u7f18\u6a61\u80f6\u8f6f\u7ba1");
addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "\u9ad8\u5ea6\u7edd\u7f18\u7684\u6a61\u80f6\u8f6f\u7ba1\uff0c\u80fd\u591f\u627f\u53d7\u6781\u70ed\u6216\u6781\u51b7");
add(NuminaObjects.SERVO.get(), "\u4f3a\u670d\u7535\u673a");
addItemDescriptions(NuminaObjects.SERVO.get(), "\u4e00\u79cd\u7279\u6b8a\u7c7b\u578b\u7684\u7535\u673a\uff0c\u5b83\u4f7f\u7528\u8109\u51b2\u8c03\u5236\u4fe1\u53f7\u6765\u6267\u884c\u975e\u5e38\u7cbe\u786e\u7684\u8fd0\u52a8\u3002");
add(NuminaObjects.SOLAR_PANEL.get(), "\u592a\u9633\u80fd\u7535\u6c60\u677f","item.numina.component_solar_panel.desc":"\u4e00\u79cd\u4ece\u592a\u9633\u53d1\u7535\u7684\u5149\u654f\u8bbe\u5907\u3002");
add(NuminaObjects.SOLENOID.get(), "\u87ba\u7ebf\u7ba1");
addItemDescriptions(NuminaObjects.SOLENOID.get(), "\u7f20\u7ed5\u5728\u94c1\u78c1\u82af\u4e0a\u7684\u5bfc\u7ebf\u4ea7\u751f\u57fa\u672c\u7684\u7535\u78c1\u94c1\u3002");
add(NuminaObjects.WIRING.get(), "\u5e03\u7ebf");
addItemDescriptions(NuminaObjects.WIRING.get(), "\u4e00\u79cd\u5177\u6709\u9ad8\u7535\u538b\u5bb9\u91cf\u548c\u7cbe\u5ea6\u7684\u7279\u6b8a\u7c7b\u578b\u7684\u5e03\u7ebf\uff0c\u662f\u52a8\u529b\u88c5\u7532\u4e2d\u654f\u611f\u7535\u5b50\u8bbe\u5907\u6240\u5fc5\u9700\u7684\u3002","itemGroup.numina":"Numina");
add("key.numina.fovfixtoggle", "\u5207\u6362\u89c6\u91ce\u4fee\u590d", add("message.numina.fovfixtoggle.disabled", "\u89c6\u91ce\u4fee\u590d\u5df2\u7981\u7528");
add("message.numina.fovfixtoggle.enabled", "\u89c6\u91ce\u4fee\u590d\u5df2\u542f\u7528");
add("module.tradeoff.maxEnergy",  "\u6700\u5927\u80fd\u91cf");
add("module.tradeoff.maxTransfer", :"\u6bcf\u6b21\u62a5\u4ef7\u7684\u6700\u5927\u8f6c\u8d26");
add("numina.energy", "\u80fd\u6e90");
add(ModuleCategory.ARMOR.getTranslationKey(), "\u88c5\u7532\u6a21\u5757");
add(ModuleCategory.COSMETIC.getTranslationKey(), "\u5316\u5986\u54c1");
add(ModuleCategory.DEBUG.getTranslationKey(), "\u8c03\u8bd5\u6a21\u5757");
add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "\u80fd\u6e90\u751f\u4ea7");
add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "\u50a8\u80fd");
add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "\u73af\u5883");
add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "\u91c7\u77ff\u9644\u9b54");
add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "\u6316\u77ff\u589e\u5f3a");
add(ModuleCategory.MOVEMENT.getTranslationKey(), "\u8fd0\u52a8");
add(ModuleCategory.NONE.getTranslationKey(), "\u6ca1\u6709");
add(ModuleCategory.SPECIAL.getTranslationKey(), "\u7279\u6b8a", add(ModuleCategory.TOOL.getTranslationKey(), "\u5de5\u5177");
add(ModuleCategory.VISION.getTranslationKey(), "\u89c6\u89c9");
add(ModuleCategory.WEAPON.getTranslationKey(), :"\u6b66\u5668","tooltip.numina.battery.energy":"%d/%d FE");
add(NuminaConstants.TOOLTIP_CHANGE_MODES, "\u66f4\u6539\u6a21\u5f0f\uff1a\u6309\u4f4f\u7535\u6e90\u62f3\u6240\u5728\u7684\u70ed\u680f\u63d2\u69fd\u7684\u7f16\u53f7\u3002");
add(NuminaConstants.TOOLTIP_CHARGING_BASE, "\u4e3a\u5b9e\u4f53\u88c5\u5907\u7684\u7269\u54c1\u6536\u8d39");
add(NuminaConstants.TOOLTIP_ENERGY, "\u80fd\u6e90\uff1a");
add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "\u5df2\u5b89\u88c5\u7684\u6a21\u5757\uff1a", add(NuminaConstants.TOOLTIP_MODE, "\u6a21\u5f0f\uff1a");
add(NuminaConstants.TOOLTIP_NO_MODULES, "\u6ca1\u6709\u5b89\u88c5\u6a21\u5757\uff01\u5728\u4fee\u8865\u8868\u4e2d\u6dfb\u52a0\u4e00\u4e9b\u6a21\u5757\u4e4b\u524d\uff0c\u6b64\u9879\u662f\u65e0\u7528\u7684\u3002");
add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "\u6309 SHIFT \u952e\u4e86\u89e3\u66f4\u591a\u4fe1\u606f\u3002"}
     */


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
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "Armor Stand");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(),  "Armor Stand");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "Maximum Energy");

        add("module.tradeoff.maxTransfer", "Maximum Transfer per Tick");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "Charging Base");
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
        add(NuminaObjects.BASIC_BATTERY.get(), "Basic Battery");

        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "Integrate a battery to allow the item to store energy.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "Advanced Battery");

        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "Integrate a battery to allow the item to store energy.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "Elite Battery");

        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Integrate a the most advanced battery to store an extensive amount of energy.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "Ultimate Battery");

        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Experimental high end power storage device, now with almost no chance of exploding (we think). Despite nasty rumors, it is not based on stolen alien technology.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Artificial Muscle");

        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "An electrical, artificial muscle, with less range of movement than human muscle but orders of magnitude more strength.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "Carbon Myofiber");

        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "A small bundle of carbon fibers, refined for use in artificial muscles.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "Computer Chip");

        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "An upgraded control circuit that contains a CPU which is capable of more advanced calculations.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(),  "Control Circuit");

        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "A simple networkable microcontroller for coordinating an individual component.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "Force Field Emitter");

        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "An advanced device which directly manipulates electromagnetic and gravitational fields in an area.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Glider Wing");

        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "A lightweight aerodynamic wing with an electromagnet for quick deployment and retraction.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Ion Thruster");

        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "Essentially a miniature particle accelerator. Accelerates ions to near-light speed to produce thrust.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "Hologram Emitter");

        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "A multicolored laser array which can cheaply alter the appearance of something.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Magnet");

        addItemDescriptions(NuminaObjects.MAGNET.get(), "A metallic device that generates a magnetic field which pulls items towards the player.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Myofiber Gel");

        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "A thick, conductive paste, perfect for fitting between myofibers in an artificial muscle.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Parachute");

        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "A simple reusable parachute which can be deployed and recovered in midair.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Insulated Rubber Hose");

        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "A heavily insulated rubber hose capable of withstanding extreme heat or cold");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Servo Motor");

        addItemDescriptions(NuminaObjects.SERVO.get(), "A special type of motor which uses a pulse-modulated signal to enact very precise movements.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Solar Panel");

        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "A light sensitive device that will generate electricity from the sun.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Solenoid");

        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Wires wound around a ferromagnetic core produces a basic electromagnet.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "Wiring");

        addItemDescriptions(NuminaObjects.WIRING.get(), "A special type of wiring with high voltaic capacity and precision, necessary for the sensitive electronics in power armor.");
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
}
