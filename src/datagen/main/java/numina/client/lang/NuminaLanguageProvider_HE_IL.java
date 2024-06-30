package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;

public class NuminaLanguageProvider_HE_IL extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_HE_IL(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "he_il");
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
    {add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "\u05d1\u05e1\u05d9\u05e1 \u05d8\u05e2\u05d9\u05e0\u05d4");
add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "\u05de\u05e2\u05de\u05d3 \u05e9\u05e8\u05d9\u05d5\u05df");
add("gui.numina.chargingbase", "\u05d1\u05e1\u05d9\u05e1 \u05d8\u05e2\u05d9\u05e0\u05d4");
add(NuminaConstants.GUI_CREATIVE_INSTALL"\u05d4\u05ea\u05e7\u05d9\u05df");
add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "\u05d4\u05ea\u05e7\u05df \u05d4\u05db\u05dc");
add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "\u05de\u05ea\u05e7\u05d9\u05df \u05d0\u05ea \u05db\u05dc \u05d4\u05de\u05d5\u05d3\u05d5\u05dc\u05d9\u05dd \u05d4\u05ea\u05d5\u05d0\u05de\u05d9\u05dd \u05d1\u05e8\u05de\u05d4 \u05d4\u05e2\u05dc\u05d9\u05d5\u05e0\u05d4 \u05dc\u05e4\u05e8\u05d9\u05d8 \u05de\u05d5\u05d3\u05d5\u05dc\u05e8\u05d9 \u05e9\u05e0\u05d1\u05d7\u05e8 \u05d1\u05d6\u05de\u05df \u05e9\u05d4\u05e9\u05d7\u05e7\u05df \u05e0\u05de\u05e6\u05d0 \u05d1\u05de\u05e6\u05d1 \u05d9\u05e6\u05d9\u05e8\u05ea\u05d9");
add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,"\u05de\u05ea\u05e7\u05d9\u05df \u05de\u05d5\u05d3\u05d5\u05dc \u05d1\u05e4\u05e8\u05d9\u05d8 \u05de\u05d5\u05d3\u05d5\u05dc\u05e8\u05d9 \u05e9\u05e0\u05d1\u05d7\u05e8 \u05d1\u05d6\u05de\u05df \u05e9\u05d4\u05e0\u05d2\u05df \u05d1\u05de\u05e6\u05d1 \u05d9\u05e6\u05d9\u05e8\u05ea\u05d9");
add(NuminaObjects.ARMOR_STAND_ITEM.get(), "\u05de\u05e2\u05de\u05d3 \u05e9\u05e8\u05d9\u05d5\u05df");
add(NuminaObjects.ADVANCED_BATTERY.get(), \u05e1\u05d5\u05dc\u05dc\u05d4 \u05de\u05ea\u05e7\u05d3\u05de\u05ea");
addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "\u05e9\u05dc\u05d1 \u05e1\u05d5\u05dc\u05dc\u05d4 \u05db\u05d3\u05d9 \u05dc\u05d0\u05e4\u05e9\u05e8 \u05dc\u05e4\u05e8\u05d9\u05d8 \u05dc\u05d0\u05d2\u05d5\u05e8 \u05d0\u05e0\u05e8\u05d2\u05d9\u05d4.");
add(NuminaObjects.BASIC_BATTERY.get(), "\u05e1\u05d5\u05dc\u05dc\u05d4 \u05d1\u05e1\u05d9\u05e1\u05d9\u05ea");
addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "\u05e9\u05dc\u05d1 \u05e1\u05d5\u05dc\u05dc\u05d4 \u05db\u05d3\u05d9 \u05dc\u05d0\u05e4\u05e9\u05e8 \u05dc\u05e4\u05e8\u05d9\u05d8 \u05dc\u05d0\u05d2\u05d5\u05e8 \u05d0\u05e0\u05e8\u05d2\u05d9\u05d4.");
add(NuminaObjects.ELITE_BATTERY.get(), "\u05e1\u05d5\u05dc\u05dc\u05ea \u05e2\u05dc\u05d9\u05ea");
addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "\u05e9\u05dc\u05d1 \u05e1\u05d5\u05dc\u05dc\u05d4 \u05de\u05ea\u05e7\u05d3\u05de\u05ea \u05d1\u05d9\u05d5\u05ea\u05e8 \u05db\u05d3\u05d9 \u05dc\u05d0\u05d7\u05e1\u05df \u05db\u05de\u05d5\u05ea \u05d2\u05d3\u05d5\u05dc\u05d4 \u05e9\u05dc \u05d0\u05e0\u05e8\u05d2\u05d9\u05d4.");
add(NuminaObjects.ULTIMATE_BATTERY.get(), "\u05e1\u05d5\u05dc\u05dc\u05d4 \u05d0\u05d5\u05dc\u05d8\u05d9\u05de\u05d8\u05d9\u05d1\u05d9\u05ea");
addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "\u05d4\u05ea\u05e7\u05df \u05d0\u05d7\u05e1\u05d5\u05df \u05db\u05d5\u05d7 \u05e0\u05d9\u05e1\u05d9\u05d5\u05e0\u05d9 \u05d1\u05e8\u05de\u05d4 \u05d2\u05d1\u05d5\u05d4\u05d4, \u05e2\u05db\u05e9\u05d9\u05d5 \u05db\u05de\u05e2\u05d8 \u05dc\u05dc\u05d0 \u05e1\u05d9\u05db\u05d5\u05d9 \u05dc\u05d4\u05ea\u05e4\u05d5\u05e6\u05e5 (\u05d0\u05e0\u05d7\u05e0\u05d5 \u05d7\u05d5\u05e9\u05d1\u05d9\u05dd). \u05dc\u05de\u05e8\u05d5\u05ea \u05e9\u05de\u05d5\u05e2\u05d5\u05ea \u05de\u05d2\u05e2\u05d9\u05dc\u05d5\u05ea, \u05d4\u05d5\u05d0 \u05d0\u05d9\u05e0\u05d5 \u05de\u05d1\u05d5\u05e1\u05e1 \u05e2\u05dc \u05d8\u05db\u05e0\u05d5\u05dc\u05d5\u05d2\u05d9\u05d4 \u05d7\u05d9\u05d9\u05d6\u05e8\u05d9\u05ea \u05d2\u05e0\u05d5\u05d1\u05d4.");
add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "\u05e9\u05e8\u05d9\u05e8 \u05de\u05dc\u05d0\u05db\u05d5\u05ea\u05d9");
addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "\u05e9\u05e8\u05d9\u05e8 \u05d7\u05e9\u05de\u05dc\u05d9, \u05de\u05dc\u05d0\u05db\u05d5\u05ea\u05d9, \u05e2\u05dd \u05d8\u05d5\u05d5\u05d7 \u05ea\u05e0\u05d5\u05e2\u05d4 \u05e7\u05d8\u05df \u05d9\u05d5\u05ea\u05e8 \u05de\u05e9\u05e8\u05d9\u05e8 \u05d0\u05e0\u05d5\u05e9\u05d9 \u05d0\u05da \u05d1\u05e1\u05d3\u05e8\u05d9 \u05d2\u05d5\u05d3\u05dc \u05d9\u05d5\u05ea\u05e8 \u05db\u05d5\u05d7.");
add(NuminaObjects.CARBON_MYOFIBER.get(), "\u05de\u05d9\u05d5\u05e4\u05d9\u05d9\u05d1\u05e8 \u05e4\u05d7\u05de\u05df");
addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "\u05e6\u05e8\u05d5\u05e8 \u05e7\u05d8\u05df \u05e9\u05dc \u05e1\u05d9\u05d1\u05d9 \u05e4\u05d7\u05de\u05df, \u05de\u05d6\u05d5\u05e7\u05e7 \u05dc\u05e9\u05d9\u05de\u05d5\u05e9 \u05d1\u05e9\u05e8\u05d9\u05e8\u05d9\u05dd \u05de\u05dc\u05d0\u05db\u05d5\u05ea\u05d9\u05d9\u05dd.");
add(NuminaObjects.COMPUTER_CHIP.get(), "\u05e9\u05d1\u05d1 \u05de\u05d7\u05e9\u05d1","item.numina.component_computer_chip.desc":"\u05de\u05e2\u05d2\u05dc \u05d1\u05e7\u05e8\u05d4 \u05de\u05e9\u05d5\u05d3\u05e8\u05d2 \u05d4\u05de\u05db\u05d9\u05dc \u05de\u05e2\u05d1\u05d3 \u05d4\u05de\u05e1\u05d5\u05d2\u05dc \u05dc\u05d1\u05e6\u05e2 \u05d7\u05d9\u05e9\u05d5\u05d1\u05d9\u05dd \u05de\u05ea\u05e7\u05d3\u05de\u05d9\u05dd \u05d9\u05d5\u05ea\u05e8.");
add(NuminaObjects.CONTROL_CIRCUIT.get(), "\u05de\u05e2\u05d2\u05dc \u05d1\u05e7\u05e8\u05d4");
addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), \u05de\u05d9\u05e7\u05e8\u05d5-\u05d1\u05e7\u05e8 \u05e4\u05e9\u05d5\u05d8 \u05dc\u05e8\u05e9\u05ea \u05dc\u05ea\u05d9\u05d0\u05d5\u05dd \u05e8\u05db\u05d9\u05d1 \u05d1\u05d5\u05d3\u05d3.","item.numina.component_field_emitter":"\u05e4\u05d5\u05dc\u05d8 \u05e9\u05d3\u05d4 \u05db\u05d5\u05d7");
addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "\u05de\u05db\u05e9\u05d9\u05e8 \u05de\u05ea\u05e7\u05d3\u05dd \u05d4\u05de\u05ea\u05e4\u05e2\u05dc \u05d9\u05e9\u05d9\u05e8\u05d5\u05ea \u05e9\u05d3\u05d5\u05ea \u05d0\u05dc\u05e7\u05d8\u05e8\u05d5\u05de\u05d2\u05e0\u05d8\u05d9\u05d9\u05dd \u05d5\u05db\u05d1\u05d9\u05d3\u05d4 \u05d1\u05d0\u05d6\u05d5\u05e8.");
add(NuminaObjects.GLIDER_WING.get(), "\u05db\u05e0\u05e3 \u05d3\u05d0\u05d5\u05df");
addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "\u05db\u05e0\u05e3 \u05d0\u05d5\u05d5\u05d9\u05e8\u05d5\u05d3\u05d9\u05e0\u05de\u05d9\u05ea \u05e7\u05dc\u05ea \u05de\u05e9\u05e7\u05dc \u05e2\u05dd \u05d0\u05dc\u05e7\u05d8\u05e8\u05d5\u05de\u05d2\u05e0\u05d8 \u05dc\u05e4\u05e8\u05d9\u05e1\u05d4 \u05d5\u05e9\u05dc\u05d9\u05e4\u05d4 \u05de\u05d4\u05d9\u05e8\u05d4.");
add(NuminaObjects.ION_THRUSTER.get(), "\u05de\u05d3\u05d7\u05e3 \u05d9\u05d5\u05e0\u05d9\u05dd");
addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "\u05dc\u05de\u05e2\u05e9\u05d4 \u05de\u05d0\u05d9\u05e5 \u05d7\u05dc\u05e7\u05d9\u05e7\u05d9\u05dd \u05de\u05d9\u05e0\u05d9\u05d0\u05d8\u05d5\u05e8\u05d9. \u05de\u05d0\u05d9\u05e5 \u05d9\u05d5\u05e0\u05d9\u05dd \u05dc\u05de\u05d4\u05d9\u05e8\u05d5\u05ea \u05e7\u05e8\u05d5\u05d1\u05d4 \u05dc\u05d0\u05d5\u05e8 \u05db\u05d3\u05d9 \u05dc\u05d9\u05d9\u05e6\u05e8 \u05d3\u05d7\u05e3.");
add(NuminaObjects.LASER_EMITTER.get(), "\u05e4\u05d5\u05dc\u05d8 \u05d4\u05d5\u05dc\u05d5\u05d2\u05e8\u05de\u05d4");
addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "\u05de\u05e2\u05e8\u05da \u05dc\u05d9\u05d9\u05d6\u05e8 \u05e6\u05d1\u05e2\u05d5\u05e0\u05d9 \u05e9\u05d9\u05db\u05d5\u05dc \u05dc\u05e9\u05e0\u05d5\u05ea \u05d1\u05d6\u05d5\u05dc \u05d0\u05ea \u05d4\u05de\u05e8\u05d0\u05d4 \u05e9\u05dc \u05de\u05e9\u05d4\u05d5.");
add(NuminaObjects.MAGNET.get(), "\u05de\u05d2\u05e0\u05d8");
addItemDescriptions(NuminaObjects.MAGNET.get(), "\u05de\u05db\u05e9\u05d9\u05e8 \u05de\u05ea\u05db\u05ea\u05d9 \u05d4\u05de\u05d9\u05d9\u05e6\u05e8 \u05e9\u05d3\u05d4 \u05de\u05d2\u05e0\u05d8\u05d9 \u05d4\u05de\u05d5\u05e9\u05da \u05e4\u05e8\u05d9\u05d8\u05d9\u05dd \u05dc\u05db\u05d9\u05d5\u05d5\u05df \u05d4\u05e9\u05d7\u05e7\u05df.");
add(NuminaObjects.MYOFIBER_GEL.get(), "\u05de\u05d9\u05d5\u05e4\u05d9\u05d9\u05d1\u05e8 \u05d2'\u05dc");
addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "\u05de\u05e9\u05d7\u05d4 \u05e1\u05de\u05d9\u05db\u05d4 \u05d5\u05de\u05d5\u05dc\u05d9\u05db\u05d4, \u05de\u05d5\u05e9\u05dc\u05de\u05ea \u05dc\u05d4\u05ea\u05d0\u05de\u05d4 \u05d1\u05d9\u05df \u05e9\u05e8\u05d9\u05e8\u05d9 \u05e9\u05e8\u05d9\u05e8 \u05de\u05dc\u05d0\u05db\u05d5\u05ea\u05d9\u05d9\u05dd.");
add(NuminaObjects.PARACHUTE.get(), "\u05de\u05e6\u05e0\u05d7");
addItemDescriptions(NuminaObjects.PARACHUTE.get(), "\u05de\u05e6\u05e0\u05d7 \u05e8\u05d1 \u05e4\u05e2\u05de\u05d9 \u05e4\u05e9\u05d5\u05d8 \u05e9\u05e0\u05d9\u05ea\u05df \u05dc\u05e4\u05e8\u05d5\u05e1 \u05d5\u05dc\u05d4\u05ea\u05d0\u05d5\u05e9\u05e9 \u05d1\u05d0\u05d5\u05d5\u05d9\u05e8.");
add(NuminaObjects.RUBBER_HOSE.get(), "\u05e6\u05d9\u05e0\u05d5\u05e8 \u05d2\u05d5\u05de\u05d9 \u05de\u05d1\u05d5\u05d3\u05d3");
addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "\u05e6\u05d9\u05e0\u05d5\u05e8 \u05d2\u05d5\u05de\u05d9 \u05de\u05d1\u05d5\u05d3\u05d3 \u05d4\u05d9\u05d8\u05d1 \u05d4\u05de\u05e1\u05d5\u05d2\u05dc \u05dc\u05e2\u05de\u05d5\u05d3 \u05d1\u05d7\u05d5\u05dd \u05d0\u05d5 \u05e7\u05d5\u05e8 \u05e7\u05d9\u05e6\u05d5\u05e0\u05d9\u05d9\u05dd");
add(NuminaObjects.SERVO.get(), "\u05de\u05e0\u05d5\u05e2 \u05e1\u05e8\u05d5\u05d5");
addItemDescriptions(NuminaObjects.SERVO.get(), "\u05e1\u05d5\u05d2 \u05de\u05d9\u05d5\u05d7\u05d3 \u05e9\u05dc \u05de\u05e0\u05d5\u05e2 \u05d4\u05de\u05e9\u05ea\u05de\u05e9 \u05d1\u05d0\u05d5\u05ea \u05de\u05d5\u05d5\u05e1\u05ea \u05d3\u05d5\u05e4\u05e7 \u05db\u05d3\u05d9 \u05dc\u05d1\u05e6\u05e2 \u05ea\u05e0\u05d5\u05e2\u05d5\u05ea \u05de\u05d3\u05d5\u05d9\u05e7\u05d5\u05ea \u05de\u05d0\u05d5\u05d3.");
add(NuminaObjects.SOLAR_PANEL.get(), "\u05e4\u05d0\u05e0\u05dc \u05e1\u05d5\u05dc\u05d0\u05e8\u05d9","item.numina.component_solar_panel.desc":"\u05de\u05db\u05e9\u05d9\u05e8 \u05e8\u05d2\u05d9\u05e9 \u05dc\u05d0\u05d5\u05e8 \u05e9\u05d9\u05e4\u05d9\u05e7 \u05d7\u05e9\u05de\u05dc \u05de\u05d4\u05e9\u05de\u05e9.");
add(NuminaObjects.SOLENOID.get(), "\u05e1\u05d5\u05dc\u05e0\u05d5\u05d0\u05d9\u05d3");
addItemDescriptions(NuminaObjects.SOLENOID.get(), "\u05d7\u05d5\u05d8\u05d9\u05dd \u05e1\u05d1\u05d9\u05d1 \u05dc\u05d9\u05d1\u05d4 \u05e4\u05e8\u05d5\u05de\u05d2\u05e0\u05d8\u05d9\u05ea \u05de\u05d9\u05d9\u05e6\u05e8\u05d9\u05dd \u05d0\u05dc\u05e7\u05d8\u05e8\u05d5\u05de\u05d2\u05e0\u05d8 \u05d1\u05e1\u05d9\u05e1\u05d9.");
add(NuminaObjects.WIRING.get(), "\u05d7\u05d9\u05d5\u05d5\u05d8");
addItemDescriptions(NuminaObjects.WIRING.get(), "\u05e1\u05d5\u05d2 \u05de\u05d9\u05d5\u05d7\u05d3 \u05e9\u05dc \u05d7\u05d9\u05d5\u05d5\u05d8 \u05e2\u05dd \u05e7\u05d9\u05d1\u05d5\u05dc\u05ea \u05d5\u05d5\u05dc\u05d8\u05d0\u05d9\u05ea \u05d2\u05d1\u05d5\u05d4\u05d4 \u05d5\u05d3\u05d9\u05d5\u05e7, \u05d4\u05db\u05e8\u05d7\u05d9 \u05e2\u05d1\u05d5\u05e8 \u05d0\u05dc\u05e7\u05d8\u05e8\u05d5\u05e0\u05d9\u05e7\u05d4 \u05e8\u05d2\u05d9\u05e9\u05d4 \u05d1\u05e9\u05e8\u05d9\u05d5\u05df \u05db\u05d5\u05d7.","itemGroup.numina":"Numina");
add("key.numina.fovfixtoggle", "\u05d4\u05d7\u05dc\u05e4\u05ea \u05de\u05e6\u05d1 \u05e9\u05dc \u05ea\u05d9\u05e7\u05d5\u05df \u05e9\u05d3\u05d4 \u05ea\u05e6\u05d5\u05d2\u05d4", add("message.numina.fovfixtoggle.disabled", "\u05ea\u05d9\u05e7\u05d5\u05df \u05e9\u05d3\u05d4 \u05e8\u05d0\u05d9\u05d9\u05d4 \u05de\u05d5\u05e9\u05d1\u05ea");
add("message.numina.fovfixtoggle.enabled", "\u05ea\u05d9\u05e7\u05d5\u05df \u05e9\u05d3\u05d4 \u05d4\u05e8\u05d0\u05d9\u05d9\u05d4 \u05d6\u05de\u05d9\u05df");
add("module.tradeoff.maxEnergy",  "\u05de\u05e7\u05e1\u05d9\u05de\u05d5\u05dd \u05d0\u05e0\u05e8\u05d2\u05d9\u05d4");
add("module.tradeoff.maxTransfer", :"\u05d4\u05e2\u05d1\u05e8\u05d4 \u05de\u05e7\u05e1\u05d9\u05de\u05dc\u05d9\u05ea \u05dc\u05db\u05dc \u05d8\u05d9\u05e7");
add("numina.energy", "\u05d0\u05e0\u05e8\u05d2\u05d9\u05d4");
add(ModuleCategory.ARMOR.getTranslationKey(), "\u05de\u05d5\u05d3\u05d5\u05dc\u05d9 \u05e9\u05e8\u05d9\u05d5\u05df");
add(ModuleCategory.COSMETIC.getTranslationKey(), "\u05e7\u05d5\u05e1\u05de\u05d8\u05d9\u05d9\u05dd");
add(ModuleCategory.DEBUG.getTranslationKey(), "\u05de\u05d5\u05d3\u05d5\u05dc\u05d9 \u05d0\u05d9\u05ea\u05d5\u05e8 \u05d1\u05d0\u05d2\u05d9\u05dd");
add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "\u05d9\u05d9\u05e6\u05d5\u05e8 \u05d0\u05e0\u05e8\u05d2\u05d9\u05d4");
add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "\u05d0\u05d2\u05d9\u05e8\u05ea \u05d0\u05e0\u05e8\u05d2\u05d9\u05d4");
add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "\u05e1\u05d1\u05d9\u05d1\u05d4");
add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "\u05e7\u05e1\u05dd \u05d4\u05db\u05e8\u05d9\u05d9\u05d4");
add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "\u05d4\u05e9\u05d1\u05d7\u05ea \u05db\u05e8\u05d9\u05d9\u05d4");
add(ModuleCategory.MOVEMENT.getTranslationKey(), "\u05ea\u05e0\u05d5\u05e2\u05d4");
add(ModuleCategory.NONE.getTranslationKey(), "\u05dc\u05dc\u05d0");
add(ModuleCategory.SPECIAL.getTranslationKey(), "\u05de\u05d9\u05d5\u05d7\u05d3", add(ModuleCategory.TOOL.getTranslationKey(), "\u05db\u05dc\u05d9");
add(ModuleCategory.VISION.getTranslationKey(), "\u05d7\u05d6\u05d5\u05df");
add(ModuleCategory.WEAPON.getTranslationKey(), :"\u05e0\u05e9\u05e7","tooltip.numina.battery.energy":"%d/%d FE");
add(NuminaConstants.TOOLTIP_CHANGE_MODES, "\u05e9\u05d9\u05e0\u05d5\u05d9 \u05de\u05e6\u05d1\u05d9\u05dd: \u05dc\u05d7\u05e5/\u05d9 \u05d5\u05d4\u05d7\u05d6\u05e7/\u05d9 \u05d0\u05ea \u05de\u05e1\u05e4\u05e8 \u05d7\u05e8\u05d9\u05e5 \u05d4\u05e4\u05e1 \u05d4\u05d7\u05dd \u05e9\u05d1\u05d5 \u05e0\u05de\u05e6\u05d0 Power Fist.");
add(NuminaConstants.TOOLTIP_CHARGING_BASE, "\u05d7\u05d9\u05d5\u05d1 \u05e4\u05e8\u05d9\u05d8\u05d9\u05dd \u05de\u05e6\u05d5\u05d9\u05d3\u05d9\u05dd \u05e9\u05dc \u05d9\u05e9\u05d5\u05ea");
add(NuminaConstants.TOOLTIP_ENERGY, "\u05d0\u05e0\u05e8\u05d2\u05d9\u05d4: ");
add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "\u05de\u05d5\u05d3\u05d5\u05dc\u05d9\u05dd \u05de\u05d5\u05ea\u05e7\u05e0\u05d9\u05dd:", add(NuminaConstants.TOOLTIP_MODE, "\u05de\u05e6\u05d1: ");
add(NuminaConstants.TOOLTIP_NO_MODULES, "\u05d0\u05d9\u05df \u05de\u05d5\u05d3\u05d5\u05dc\u05d9\u05dd \u05de\u05d5\u05ea\u05e7\u05e0\u05d9\u05dd! \u05e4\u05e8\u05d9\u05d8 \u05d6\u05d4 \u05d7\u05e1\u05e8 \u05ea\u05d5\u05e2\u05dc\u05ea \u05e2\u05d3 \u05dc\u05d4\u05d5\u05e1\u05e4\u05ea \u05de\u05d5\u05d3\u05d5\u05dc\u05d9\u05dd \u05de\u05e1\u05d5\u05d9\u05de\u05d9\u05dd \u05d1\u05d8\u05d1\u05dc\u05ea Tinker.");
add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "\u05d4\u05e7\u05e9 SHIFT \u05dc\u05e7\u05d1\u05dc\u05ea \u05de\u05d9\u05d3\u05e2 \u05e0\u05d5\u05e1\u05e3."}
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

    @Override
    public void addItemDescriptions(Item key, String description) {
        add(key.getDescriptionId() + ".desc", description);
    }
}
