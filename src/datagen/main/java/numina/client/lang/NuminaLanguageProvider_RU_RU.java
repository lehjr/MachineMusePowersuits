package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;

public class NuminaLanguageProvider_RU_RU extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_RU_RU(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "ru_ru");
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
    {add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "\u0417\u0430\u0440\u044f\u0434\u043d\u0430\u044f \u0431\u0430\u0437\u0430");
add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "\u0421\u0442\u043e\u0439\u043a\u0430 \u0434\u043b\u044f \u0431\u0440\u043e\u043d\u0438");
add("gui.numina.chargingbase", "\u0417\u0430\u0440\u044f\u0434\u043d\u0430\u044f \u0431\u0430\u0437\u0430");
add(NuminaConstants.GUI_CREATIVE_INSTALL"\u0423\u0441\u0442\u0430\u043d\u0430\u0432\u043b\u0438\u0432\u0430\u0442\u044c");
add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0432\u0441\u0435");
add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "\u0423\u0441\u0442\u0430\u043d\u0430\u0432\u043b\u0438\u0432\u0430\u0435\u0442 \u0432\u0441\u0435 \u0441\u043e\u0432\u043c\u0435\u0441\u0442\u0438\u043c\u044b\u0435 \u043c\u043e\u0434\u0443\u043b\u0438 \u0432\u0435\u0440\u0445\u043d\u0435\u0433\u043e \u0443\u0440\u043e\u0432\u043d\u044f \u0432 \u0432\u044b\u0431\u0440\u0430\u043d\u043d\u044b\u0439 \u043c\u043e\u0434\u0443\u043b\u044c\u043d\u044b\u0439 \u043f\u0440\u0435\u0434\u043c\u0435\u0442, \u043a\u043e\u0433\u0434\u0430 \u0438\u0433\u0440\u043e\u043a \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0442\u0432\u043e\u0440\u0447\u0435\u0441\u043a\u043e\u043c \u0440\u0435\u0436\u0438\u043c\u0435.");
add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,"\u0423\u0441\u0442\u0430\u043d\u0430\u0432\u043b\u0438\u0432\u0430\u0435\u0442 \u043c\u043e\u0434\u0443\u043b\u044c \u0432 \u0432\u044b\u0431\u0440\u0430\u043d\u043d\u044b\u0439 \u043c\u043e\u0434\u0443\u043b\u044c\u043d\u044b\u0439 \u044d\u043b\u0435\u043c\u0435\u043d\u0442, \u043a\u043e\u0433\u0434\u0430 \u043f\u043b\u0435\u0435\u0440 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0442\u0432\u043e\u0440\u0447\u0435\u0441\u043a\u043e\u043c \u0440\u0435\u0436\u0438\u043c\u0435");
add(NuminaObjects.ARMOR_STAND_ITEM.get(), "\u0421\u0442\u043e\u0439\u043a\u0430 \u0434\u043b\u044f \u0431\u0440\u043e\u043d\u0438");
add(NuminaObjects.ADVANCED_BATTERY.get(), \u0423\u0441\u043e\u0432\u0435\u0440\u0448\u0435\u043d\u0441\u0442\u0432\u043e\u0432\u0430\u043d\u043d\u0430\u044f \u0431\u0430\u0442\u0430\u0440\u0435\u044f");
addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "\u0412\u0441\u0442\u0440\u043e\u0439\u0442\u0435 \u0431\u0430\u0442\u0430\u0440\u0435\u044e, \u0447\u0442\u043e\u0431\u044b \u043f\u043e\u0437\u0432\u043e\u043b\u0438\u0442\u044c \u0438\u0437\u0434\u0435\u043b\u0438\u044e \u043d\u0430\u043a\u0430\u043f\u043b\u0438\u0432\u0430\u0442\u044c \u044d\u043d\u0435\u0440\u0433\u0438\u044e.");
add(NuminaObjects.BASIC_BATTERY.get(), "\u0411\u0430\u0437\u043e\u0432\u044b\u0439 \u0430\u043a\u043a\u0443\u043c\u0443\u043b\u044f\u0442\u043e\u0440");
addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "\u0412\u0441\u0442\u0440\u043e\u0439\u0442\u0435 \u0431\u0430\u0442\u0430\u0440\u0435\u044e, \u0447\u0442\u043e\u0431\u044b \u043f\u043e\u0437\u0432\u043e\u043b\u0438\u0442\u044c \u0438\u0437\u0434\u0435\u043b\u0438\u044e \u043d\u0430\u043a\u0430\u043f\u043b\u0438\u0432\u0430\u0442\u044c \u044d\u043d\u0435\u0440\u0433\u0438\u044e.");
add(NuminaObjects.ELITE_BATTERY.get(), "\u042d\u043b\u0438\u0442\u043d\u0430\u044f \u0431\u0430\u0442\u0430\u0440\u0435\u044f");
addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "\u0412\u0441\u0442\u0440\u043e\u0435\u043d\u043d\u0430\u044f \u0441\u0430\u043c\u0430\u044f \u0441\u043e\u0432\u0440\u0435\u043c\u0435\u043d\u043d\u0430\u044f \u0431\u0430\u0442\u0430\u0440\u0435\u044f \u0434\u043b\u044f \u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f \u0431\u043e\u043b\u044c\u0448\u043e\u0433\u043e \u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u0430 \u044d\u043d\u0435\u0440\u0433\u0438\u0438.");
add(NuminaObjects.ULTIMATE_BATTERY.get(), "\u0418\u0434\u0435\u0430\u043b\u044c\u043d\u0430\u044f \u0431\u0430\u0442\u0430\u0440\u0435\u044f");
addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "\u042d\u043a\u0441\u043f\u0435\u0440\u0438\u043c\u0435\u043d\u0442\u0430\u043b\u044c\u043d\u043e\u0435 \u0432\u044b\u0441\u043e\u043a\u043e\u043f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0435 \u043d\u0430\u043a\u043e\u043f\u0438\u0442\u0435\u043b\u044c \u044d\u043d\u0435\u0440\u0433\u0438\u0438, \u0442\u0435\u043f\u0435\u0440\u044c \u043f\u043e\u0447\u0442\u0438 \u0431\u0435\u0437 \u0448\u0430\u043d\u0441\u043e\u0432 \u0432\u0437\u043e\u0440\u0432\u0430\u0442\u044c\u0441\u044f (\u043a\u0430\u043a \u043c\u044b \u0434\u0443\u043c\u0430\u0435\u043c). \u041d\u0435\u0441\u043c\u043e\u0442\u0440\u044f \u043d\u0430 \u043d\u0435\u043f\u0440\u0438\u044f\u0442\u043d\u044b\u0435 \u0441\u043b\u0443\u0445\u0438, \u043e\u043d \u043d\u0435 \u043e\u0441\u043d\u043e\u0432\u0430\u043d \u043d\u0430 \u0443\u043a\u0440\u0430\u0434\u0435\u043d\u043d\u044b\u0445 \u0438\u043d\u043e\u043f\u043b\u0430\u043d\u0435\u0442\u043d\u044b\u0445 \u0442\u0435\u0445\u043d\u043e\u043b\u043e\u0433\u0438\u044f\u0445.");
add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "\u0418\u0441\u043a\u0443\u0441\u0441\u0442\u0432\u0435\u043d\u043d\u044b\u0435 \u043c\u044b\u0448\u0446\u044b");
addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "\u042d\u043b\u0435\u043a\u0442\u0440\u0438\u0447\u0435\u0441\u043a\u0430\u044f, \u0438\u0441\u043a\u0443\u0441\u0441\u0442\u0432\u0435\u043d\u043d\u0430\u044f \u043c\u044b\u0448\u0446\u0430 \u0441 \u043c\u0435\u043d\u044c\u0448\u0438\u043c \u0434\u0438\u0430\u043f\u0430\u0437\u043e\u043d\u043e\u043c \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f, \u0447\u0435\u043c \u0447\u0435\u043b\u043e\u0432\u0435\u0447\u0435\u0441\u043a\u0430\u044f \u043c\u044b\u0448\u0446\u0430, \u043d\u043e \u043d\u0430 \u043f\u043e\u0440\u044f\u0434\u043e\u043a \u0431\u043e\u043b\u044c\u0448\u0435 \u0441\u0438\u043b\u044b.");
add(NuminaObjects.CARBON_MYOFIBER.get(), "\u0423\u0433\u043b\u0435\u0440\u043e\u0434\u043d\u043e\u0435 \u043c\u044b\u0448\u0435\u0447\u043d\u043e\u0435 \u0432\u043e\u043b\u043e\u043a\u043d\u043e");
addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "\u041c\u0430\u043b\u0435\u043d\u044c\u043a\u0438\u0439 \u043f\u0443\u0447\u043e\u043a \u0443\u0433\u043b\u0435\u0440\u043e\u0434\u043d\u044b\u0445 \u0432\u043e\u043b\u043e\u043a\u043e\u043d, \u043e\u0447\u0438\u0449\u0435\u043d\u043d\u044b\u0439 \u0434\u043b\u044f \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f \u0432 \u0438\u0441\u043a\u0443\u0441\u0441\u0442\u0432\u0435\u043d\u043d\u044b\u0445 \u043c\u044b\u0448\u0446\u0430\u0445.");
add(NuminaObjects.COMPUTER_CHIP.get(), "\u041a\u043e\u043c\u043f\u044c\u044e\u0442\u0435\u0440\u043d\u044b\u0439 \u0447\u0438\u043f","item.numina.component_computer_chip.desc":"\u041e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u043d\u0430\u044f \u0441\u0445\u0435\u043c\u0430 \u0443\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u044f, \u043a\u043e\u0442\u043e\u0440\u0430\u044f \u0441\u043e\u0434\u0435\u0440\u0436\u0438\u0442 \u043f\u0440\u043e\u0446\u0435\u0441\u0441\u043e\u0440, \u0441\u043f\u043e\u0441\u043e\u0431\u043d\u044b\u0439 \u0432\u044b\u043f\u043e\u043b\u043d\u044f\u0442\u044c \u0431\u043e\u043b\u0435\u0435 \u0441\u043b\u043e\u0436\u043d\u044b\u0435 \u0432\u044b\u0447\u0438\u0441\u043b\u0435\u043d\u0438\u044f.");
add(NuminaObjects.CONTROL_CIRCUIT.get(), "\u041c\u0438\u043a\u0440\u043e\u0441\u0445\u0435\u043c\u0430 \u0443\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u044f");
addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), \u041f\u0440\u043e\u0441\u0442\u043e\u0439 \u0441\u0435\u0442\u0435\u0432\u043e\u0439 \u043c\u0438\u043a\u0440\u043e\u043a\u043e\u043d\u0442\u0440\u043e\u043b\u043b\u0435\u0440 \u0434\u043b\u044f \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0446\u0438\u0438 \u043e\u0442\u0434\u0435\u043b\u044c\u043d\u043e\u0433\u043e \u043a\u043e\u043c\u043f\u043e\u043d\u0435\u043d\u0442\u0430.","item.numina.component_field_emitter":"\u0418\u0437\u043b\u0443\u0447\u0430\u0442\u0435\u043b\u044c \u0441\u0438\u043b\u043e\u0432\u043e\u0433\u043e \u043f\u043e\u043b\u044f");
addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "\u041f\u0435\u0440\u0435\u0434\u043e\u0432\u043e\u0435 \u0443\u0441\u0442\u0440\u043e\u0439\u0441\u0442\u0432\u043e, \u043a\u043e\u0442\u043e\u0440\u043e\u0435 \u043d\u0435\u043f\u043e\u0441\u0440\u0435\u0434\u0441\u0442\u0432\u0435\u043d\u043d\u043e \u043c\u0430\u043d\u0438\u043f\u0443\u043b\u0438\u0440\u0443\u0435\u0442 \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043c\u0430\u0433\u043d\u0438\u0442\u043d\u044b\u043c\u0438 \u0438 \u0433\u0440\u0430\u0432\u0438\u0442\u0430\u0446\u0438\u043e\u043d\u043d\u044b\u043c\u0438 \u043f\u043e\u043b\u044f\u043c\u0438 \u0432 \u043e\u0431\u043b\u0430\u0441\u0442\u0438.");
add(NuminaObjects.GLIDER_WING.get(), "\u041f\u043b\u0430\u043d\u0451\u0440\u043d\u043e\u0435 \u043a\u0440\u044b\u043b\u043e");
addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "\u041b\u0435\u0433\u043a\u043e\u0435 \u0430\u044d\u0440\u043e\u0434\u0438\u043d\u0430\u043c\u0438\u0447\u0435\u0441\u043a\u043e\u0435 \u043a\u0440\u044b\u043b\u043e \u0441 \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043c\u0430\u0433\u043d\u0438\u0442\u043e\u043c \u0434\u043b\u044f \u0431\u044b\u0441\u0442\u0440\u043e\u0433\u043e \u0440\u0430\u0437\u0432\u0435\u0440\u0442\u044b\u0432\u0430\u043d\u0438\u044f \u0438 \u043e\u0442\u0432\u043e\u0434\u0430.");
add(NuminaObjects.ION_THRUSTER.get(), "\u0418\u043e\u043d\u043d\u044b\u0439 \u0434\u0432\u0438\u0433\u0430\u0442\u0435\u043b\u044c");
addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "\u041f\u043e \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443 \u043c\u0438\u043d\u0438\u0430\u0442\u044e\u0440\u043d\u044b\u0439 \u0443\u0441\u043a\u043e\u0440\u0438\u0442\u0435\u043b\u044c \u0447\u0430\u0441\u0442\u0438\u0446. \u0423\u0441\u043a\u043e\u0440\u044f\u0435\u0442 \u0438\u043e\u043d\u044b \u0434\u043e \u043f\u043e\u0447\u0442\u0438 \u0441\u0432\u0435\u0442\u043e\u0432\u043e\u0439 \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u0438 \u0434\u043b\u044f \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u0438\u044f \u0442\u044f\u0433\u0438.");
add(NuminaObjects.LASER_EMITTER.get(), "\u0418\u0437\u043b\u0443\u0447\u0430\u0442\u0435\u043b\u044c \u0433\u043e\u043b\u043e\u0433\u0440\u0430\u043c\u043c\u044b");
addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "\u041c\u043d\u043e\u0433\u043e\u0446\u0432\u0435\u0442\u043d\u044b\u0439 \u043b\u0430\u0437\u0435\u0440\u043d\u044b\u0439 \u043c\u0430\u0441\u0441\u0438\u0432, \u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u043c\u043e\u0436\u0435\u0442 \u0434\u0435\u0448\u0435\u0432\u043e \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u0432\u043d\u0435\u0448\u043d\u0438\u0439 \u0432\u0438\u0434.");
add(NuminaObjects.MAGNET.get(), "\u041c\u0430\u0433\u043d\u0438\u0442");
addItemDescriptions(NuminaObjects.MAGNET.get(), "\u041c\u0430\u0433\u043d\u0438\u0442, \u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u0442\u044f\u043d\u0435\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u043a \u0438\u0433\u0440\u043e\u043a\u0443.");
add(NuminaObjects.MYOFIBER_GEL.get(), "\u0413\u0435\u043b\u044c \u0438\u0437 \u043c\u044b\u0448\u0435\u0447\u043d\u043e\u0433\u043e \u0432\u043e\u043b\u043e\u043a\u043d\u0430");
addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "\u0422\u043e\u043b\u0441\u0442\u0430\u044f, \u043f\u0440\u043e\u0432\u043e\u0434\u044f\u0449\u0430\u044f \u043f\u0430\u0441\u0442\u0430, \u0438\u0434\u0435\u0430\u043b\u044c\u043d\u043e \u043f\u043e\u0434\u0445\u043e\u0434\u044f\u0449\u0430\u044f \u0434\u043b\u044f \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0438 \u043c\u0435\u0436\u0434\u0443 \u043c\u0438\u043e\u0444\u0438\u0431\u0440\u0430\u043c\u0438 \u0432 \u0438\u0441\u043a\u0443\u0441\u0441\u0442\u0432\u0435\u043d\u043d\u043e\u0439 \u043c\u044b\u0448\u0446\u0435.");
add(NuminaObjects.PARACHUTE.get(), "\u041f\u0430\u0440\u0430\u0448\u044e\u0442");
addItemDescriptions(NuminaObjects.PARACHUTE.get(), "\u041f\u0440\u043e\u0441\u0442\u043e\u0439 \u043c\u043d\u043e\u0433\u043e\u0440\u0430\u0437\u043e\u0432\u044b\u0439 \u043f\u0430\u0440\u0430\u0448\u044e\u0442, \u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u043c\u043e\u0436\u043d\u043e \u0440\u0430\u0437\u0432\u0435\u0440\u043d\u0443\u0442\u044c \u0438 \u0432\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0432 \u0432\u043e\u0437\u0434\u0443\u0445\u0435.");
add(NuminaObjects.RUBBER_HOSE.get(), "\u0418\u0437\u043e\u043b\u0438\u0440\u043e\u0432\u0430\u043d\u043d\u044b\u0439 \u0440\u0435\u0437\u0438\u043d\u043e\u0432\u044b\u0439 \u0448\u043b\u0430\u043d\u0433");
addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "\u0421\u0438\u043b\u044c\u043d\u043e \u0438\u0437\u043e\u043b\u0438\u0440\u043e\u0432\u0430\u043d\u043d\u044b\u0439 \u0440\u0435\u0437\u0438\u043d\u043e\u0432\u044b\u0439 \u0448\u043b\u0430\u043d\u0433, \u0441\u043f\u043e\u0441\u043e\u0431\u043d\u044b\u0439 \u0432\u044b\u0434\u0435\u0440\u0436\u0438\u0432\u0430\u0442\u044c \u0441\u0438\u043b\u044c\u043d\u0443\u044e \u0436\u0430\u0440\u0443 \u0438\u043b\u0438 \u0445\u043e\u043b\u043e\u0434");
add(NuminaObjects.SERVO.get(), "\u0421\u0435\u0440\u0432\u043e\u0434\u0432\u0438\u0433\u0430\u0442\u0435\u043b\u044c");
addItemDescriptions(NuminaObjects.SERVO.get(), "\u0421\u043f\u0435\u0446\u0438\u0430\u043b\u044c\u043d\u044b\u0439 \u0442\u0438\u043f \u0434\u0432\u0438\u0433\u0430\u0442\u0435\u043b\u044f, \u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0435\u0442 \u0438\u043c\u043f\u0443\u043b\u044c\u0441\u043d\u043e-\u043c\u043e\u0434\u0443\u043b\u0438\u0440\u043e\u0432\u0430\u043d\u043d\u044b\u0439 \u0441\u0438\u0433\u043d\u0430\u043b \u0434\u043b\u044f \u043f\u0440\u0438\u043d\u044f\u0442\u0438\u044f \u043e\u0447\u0435\u043d\u044c \u0442\u043e\u0447\u043d\u044b\u0445 \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u0439.");
add(NuminaObjects.SOLAR_PANEL.get(), "\u041f\u0430\u043d\u0435\u043b\u044c \u0441\u043e\u043b\u043d\u0435\u0447\u043d\u044b\u0445 \u0431\u0430\u0442\u0430\u0440\u0435\u0439","item.numina.component_solar_panel.desc":"\u0441\u0432\u0435\u0442\u043e\u0447\u0443\u0432\u0441\u0442\u0432\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0435 \u0443\u0441\u0442\u0440\u043e\u0439\u0441\u0442\u0432\u043e, \u043a\u043e\u0442\u043e\u0440\u043e\u0435 \u0431\u0443\u0434\u0435\u0442 \u0433\u0435\u043d\u0435\u0440\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u044d\u043b\u0435\u043a\u0442\u0440\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u043e\u0442 \u0441\u043e\u043b\u043d\u0446\u0430.");
add(NuminaObjects.SOLENOID.get(), "\u0421\u043e\u043b\u0435\u043d\u043e\u0438\u0434");
addItemDescriptions(NuminaObjects.SOLENOID.get(), "\u041f\u0440\u043e\u0432\u043e\u0434\u0430, \u043d\u0430\u043c\u043e\u0442\u0430\u043d\u043d\u044b\u0435 \u0432\u043e\u043a\u0440\u0443\u0433 \u0444\u0435\u0440\u0440\u043e\u043c\u0430\u0433\u043d\u0438\u0442\u043d\u043e\u0433\u043e \u0441\u0435\u0440\u0434\u0435\u0447\u043d\u0438\u043a\u0430, \u0441\u043e\u0437\u0434\u0430\u044e\u0442 \u043e\u0441\u043d\u043e\u0432\u043d\u043e\u0439 \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043c\u0430\u0433\u043d\u0438\u0442.");
add(NuminaObjects.WIRING.get(), "\u041f\u0440\u043e\u0432\u043e\u0434");
addItemDescriptions(NuminaObjects.WIRING.get(), "\u0421\u043f\u0435\u0446\u0438\u0430\u043b\u044c\u043d\u044b\u0439 \u0442\u0438\u043f \u043f\u0440\u043e\u0432\u043e\u0434\u043a\u0438 \u0441 \u0432\u044b\u0441\u043e\u043a\u043e\u0439 \u0432\u043e\u043b\u044c\u0442\u043e\u0432\u043e\u0439 \u0435\u043c\u043a\u043e\u0441\u0442\u044c\u044e \u0438 \u0442\u043e\u0447\u043d\u043e\u0441\u0442\u044c\u044e, \u043d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e\u0439 \u0434\u043b\u044f \u0447\u0443\u0432\u0441\u0442\u0432\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0439 \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043d\u0438\u043a\u0438 \u0432 \u0441\u0438\u043b\u043e\u0432\u043e\u0439 \u0431\u0440\u043e\u043d\u0435.","itemGroup.numina":"Numina");
add("key.numina.fovfixtoggle", "\u0418\u0441\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u043f\u0435\u0440\u0435\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u044f \u043f\u043e\u043b\u044f \u0437\u0440\u0435\u043d\u0438\u044f", add("message.numina.fovfixtoggle.disabled", "\u0418\u0441\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u043f\u043e\u043b\u044f \u0437\u0440\u0435\u043d\u0438\u044f \u043e\u0442\u043a\u043b\u044e\u0447\u0435\u043d\u043e");
add("message.numina.fovfixtoggle.enabled", "\u0418\u0441\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u043f\u043e\u043b\u044f \u0437\u0440\u0435\u043d\u0438\u044f \u0432\u043a\u043b\u044e\u0447\u0435\u043d\u043e");
add("module.tradeoff.maxEnergy", "\u041c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u0430\u044f \u044d\u043d\u0435\u0440\u0433\u0438\u044f");
add("module.tradeoff.maxTransfer", :"\u041c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u044b\u0439 \u043f\u0435\u0440\u0435\u043d\u043e\u0441 \u0437\u0430 \u0442\u0438\u043a");
add("numina.energy", "\u042d\u043d\u0435\u0440\u0433\u0438\u044f");
add(ModuleCategory.ARMOR.getTranslationKey(), "\u041c\u043e\u0434\u0443\u043b\u0438 \u0431\u0440\u043e\u043d\u0438");
add(ModuleCategory.COSMETIC.getTranslationKey(), "\u041a\u043e\u0441\u043c\u0435\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0439");
add(ModuleCategory.DEBUG.getTranslationKey(), "\u041c\u043e\u0434\u0443\u043b\u0438 \u043e\u0442\u043b\u0430\u0434\u043a\u0438");
add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "\u0412\u044b\u0440\u0430\u0431\u043e\u0442\u043a\u0430 \u044d\u043d\u0435\u0440\u0433\u0438\u0438");
add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "\u0410\u043a\u043a\u0443\u043c\u0443\u043b\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435 \u044d\u043d\u0435\u0440\u0433\u0438\u0438");
add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "\u041e\u043a\u0440\u0443\u0436\u0430\u044e\u0449\u0430\u044f \u0441\u0440\u0435\u0434\u0430");
add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "\u0427\u0430\u0440\u044b \u0448\u0430\u0445\u0442\u0451\u0440\u043e\u0432");
add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "\u0421\u043e\u0432\u0435\u0440\u0448\u0435\u043d\u0441\u0442\u0432\u043e\u0432\u0430\u043d\u0438\u0435 \u0433\u043e\u0440\u043d\u043e\u0434\u043e\u0431\u044b\u0432\u0430\u044e\u0449\u0435\u0439 \u043f\u0440\u043e\u043c\u044b\u0448\u043b\u0435\u043d\u043d\u043e\u0441\u0442\u0438");
add(ModuleCategory.MOVEMENT.getTranslationKey(), "\u0414\u0432\u0438\u0436\u0435\u043d\u0438\u0435");
add(ModuleCategory.NONE.getTranslationKey(), "\u041d\u0438\u043a\u0430\u043a\u043e\u0439");
add(ModuleCategory.SPECIAL.getTranslationKey(), "\u0421\u043f\u0435\u0446\u0438\u0430\u043b\u044c\u043d\u044b\u0439", add(ModuleCategory.TOOL.getTranslationKey(), "\u0418\u043d\u0441\u0442\u0440\u0443\u043c\u0435\u043d\u0442");
add(ModuleCategory.VISION.getTranslationKey(), "\u0417\u0440\u0435\u043d\u0438\u0435");
add(ModuleCategory.WEAPON.getTranslationKey(), :"\u041e\u0440\u0443\u0436\u0438\u0435","tooltip.numina.battery.energy":"%d/%d FE");
add(NuminaConstants.TOOLTIP_CHANGE_MODES, "\u0421\u043c\u0435\u043d\u0430 \u0440\u0435\u0436\u0438\u043c\u043e\u0432: \u041d\u0430\u0436\u043c\u0438\u0442\u0435 \u0438 \u0443\u0434\u0435\u0440\u0436\u0438\u0432\u0430\u0439\u0442\u0435 \u043d\u043e\u043c\u0435\u0440 \u044f\u0447\u0435\u0439\u043a\u0438 \u043d\u0430 \u043f\u0430\u043d\u0435\u043b\u0438 \u0431\u044b\u0441\u0442\u0440\u043e\u0433\u043e \u0434\u043e\u0441\u0442\u0443\u043f\u0430, \u0432 \u043a\u043e\u0442\u043e\u0440\u043e\u0439 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0421\u0438\u043b\u043e\u0432\u043e\u0439 \u043a\u0443\u043b\u0430\u043a.");
add(NuminaConstants.TOOLTIP_CHARGING_BASE, "\u0417\u0430\u0440\u044f\u0436\u0430\u0435\u0442 \u044d\u043a\u0438\u043f\u0438\u0440\u043e\u0432\u0430\u043d\u043d\u044b\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0438");
add(NuminaConstants.TOOLTIP_ENERGY, "\u042d\u043d\u0435\u0440\u0433\u0438\u044f: ");
add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u043d\u044b\u0435 \u043c\u043e\u0434\u0443\u043b\u0438:", add(NuminaConstants.TOOLTIP_MODE, "\u0420\u0435\u0436\u0438\u043c: ");
add(NuminaConstants.TOOLTIP_NO_MODULES, "\u041d\u0438\u043a\u0430\u043a\u0438\u0445 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u043d\u044b\u0445 \u043c\u043e\u0434\u0443\u043b\u0435\u0439! \u042d\u0442\u043e\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442 \u0431\u0435\u0441\u043f\u043e\u043b\u0435\u0437\u0435\u043d \u0434\u043e \u0442\u0435\u0445 \u043f\u043e\u0440, \u043f\u043e\u043a\u0430 \u0432\u044b \u043d\u0435 \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u0435 \u043d\u0435\u0441\u043a\u043e\u043b\u044c\u043a\u043e \u043c\u043e\u0434\u0443\u043b\u0435\u0439 \u0432 Tinker Table.");
add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Pritisnite SHIFT za vi\u0161e informacija. ..."}
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