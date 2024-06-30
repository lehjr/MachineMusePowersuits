package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;

public class NuminaLanguageProvider_ZH_TW extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_ZH_TW(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "zh_tw");
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

    @Override
    public void addGui() {
//        add("gui.numina.chargingbase", "充電底座");
//        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "對實體的裝備物品收費");
//
//        // Energy --------------------------------------------------------------------------------------
//        add("numina.energy", "能源");
//        // FOV Fix Toggle ------------------------------------------------------------------------------
//        add("key.numina.fovfixtoggle", "切換視野修復");
//
//        // FOV fix enabled -----------------------------------------------------------------------------
//        add("message.numina.fovfixtoggle.enabled", "啟用視場修復");
//
//        // FOV fix disabled ----------------------------------------------------------------------------
//        add("message.numina.fovfixtoggle.disabled", "視野修復已禁用");
//
//        // Install (creative) --------------------------------------------------------------------------
//        add(NuminaConstants.GUI_CREATIVE_INSTALL, "安裝");
//
//        // Install (creative, description) -------------------------------------------------------------
//        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC, "當玩家處於創作模式時，將模組安裝到選定的模組化專案中");
//
//        // Install All (creative) ----------------------------------------------------------------------
//        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "全部安裝");
//
//        // Install All (creative, description) ---------------------------------------------------------
//        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "當玩家處於創造模式時，將所有頂級相容模組安裝到選定的模組化專案中");
    }

    @Override
    public void addArmorStand() {
//        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "裝甲架");
//        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "裝甲架");
    }

    @Override
    public void addModuleTradeoffs() {
//        add("module.tradeoff.maxEnergy",  "最大能量");
//        add("module.tradeoff.maxTransfer", "每筆報價的最大轉帳次數");
    }

    @Override
    public void addBlocks() {
//        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "充電底座");
    }

    @Override
    public void addToolTips() {


            /*






        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "\u66f4\u6539\u6a21\u5f0f\uff1a\u6309\u4f4f Power Fist \u6240\u5728\u7684\u5feb\u6377\u6b04\u63d2\u69fd\u7de8\u865f\u3002");
        add(NuminaConstants.TOOLTIP_ENERGY, "\u80fd\u6e90\uff1a");
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "\u5df2\u5b89\u88dd\u7684\u6a21\u7d44\uff1a");
        add(NuminaConstants.TOOLTIP_MODE, "\u6a21\u5f0f\uff1a");
        add(NuminaConstants.TOOLTIP_NO_MODULES, "\u6c92\u6709\u5b89\u88dd\u6a21\u7d44\uff01\u5728Tinker Table\u4e2d\u6dfb\u52a0\u4e00\u4e9b\u6a21\u7d44\u4e4b\u524d\uff0c\u6b64\u9805\u662f\u7121\u7528\u7684\u3002");
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "\u6309 SHIFT \u9375\u77ad\u89e3\u66f4\u591a\u8cc7\u8a0a\u3002");
     */








//        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");
//
//        // Press SHIFT ---------------------------------------------------------------------------------
//        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Press SHIFT for more information.");
//
//        // Mode Change ---------------------------------------------------------------------------------
//        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Change modes: Press and hold the number of the hotbar slot the Power Fist is in.");
//
//        // Energy --------------------------------------------------------------------------------------
//        add(NuminaConstants.TOOLTIP_ENERGY, "Energy: ");
//
//        // No Modules Installed ------------------------------------------------------------------------
//        add(NuminaConstants.TOOLTIP_NO_MODULES, "No installed modules! This item is useless until you add some modules at a Tinker Table.");
//
//        // Installed Modules: --------------------------------------------------------------------------
//        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES, "Installed Modules:");
//
//        // Mode: ---------------------------------------------------------------------------------------
//        add(NuminaConstants.TOOLTIP_MODE, "Mode: ");
    }

    @Override
    public void addBatteries() {
//        // Basic Battery -------------------------------------------------------------------------------
//        add(NuminaObjects.BASIC_BATTERY.get(), "基本電池");
//        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "集成電池以使物品能夠存儲能量。");
//
//        // Advanced Battery ----------------------------------------------------------------------------
//        add(NuminaObjects.ADVANCED_BATTERY.get(), "先進電池");
//        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "集成電池以使物品能夠存儲能量。");
//
//        // Elite Battery -------------------------------------------------------------------------------
//        add(NuminaObjects.ELITE_BATTERY.get(), "精英電池");
//        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "集成最先進的電池以存儲大量能量。");
//
//        // Ultimate Battery  ---------------------------------------------------------------------------
//        add(NuminaObjects.ULTIMATE_BATTERY.get(),"終極電池");
//        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "實驗性的高端電力存儲設備，現在幾乎沒有爆炸的機會（我們認u8d的機我認為）。儘管有令人討厭的謠言，但它並不是基於被盜的外星技術。");
    }

    @Override
    public void addComponents() {
//        // Artificial Muscle ---------------------------------------------------------------------------
//        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "人造肌肉");
//        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "一種電動的人造肌肉，其運動範圍比人類肌肉小，但力量要高出幾個數量級。");
//
//        // Carbon Myofiber -----------------------------------------------------------------------------
//        add(NuminaObjects.CARBON_MYOFIBER.get(), "碳纖維肌纖維");
//        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "一小束碳纖維，經過精製后可用於人造肌肉。");
//
//        // Computer Chip -------------------------------------------------------------------------------
//        add(NuminaObjects.COMPUTER_CHIP.get(), "電腦晶片");
//        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "升級的控制電路，包含能夠進行更高級計算的CPU。");
//
//        // Control Circuit -----------------------------------------------------------------------------
//        add(NuminaObjects.CONTROL_CIRCUIT.get(), "控制電路");
//        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "一個簡啚e的可聯網微控制聯網微控制制網微控制制制制聯網微控制可聯網微控制可uu5668，用於協調啚e個元件。");
//
//        // Force Field Emitter -------------------------------------------------------------------------
//        add(NuminaObjects.FIELD_EMITTER.get(), "力場發射器");
//        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "一種直接操縱區域電磁場和引力場的先進設備。");
//
//        // Glider Wing ---------------------------------------------------------------------------------
//        add(NuminaObjects.GLIDER_WING.get(), "滑翔機翼");
//        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "輕巧的空氣動que529b學機翼，帶有 電磁鐵，可快速展開和縮回。");
//
//        // Ion Thruster --------------------------------------------------------------------------------
//        add(NuminaObjects.ION_THRUSTER.get(), "離子推進器");
//        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "本質上是一個微que578b粒子加速器。將 離子加速到接近se5149速以產生推力。");
//
//        // Laser Emitter ----------------------------------------------------------------------------
//        add(NuminaObjects.LASER_EMITTER.get(), "全息圖發射器");
//        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "一種五顏六色的激光陣列，可以廉價地改變某些東西的外觀。");
//
//        // Magnet --------------------------------------------------------------------------------------
//        add(NuminaObjects.MAGNET.get(), "磁鐵");
//        addItemDescriptions(NuminaObjects.MAGNET.get(), "一種金屬裝置，可產生磁場，將物品拉向玩家。");
//
//        // Myofiber Gel --------------------------------------------------------------------------------
//        add(NuminaObjects.MYOFIBER_GEL.get(), "肌纖維凝膠");
//        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "一種厚實的導電糊狀物，非常適合安裝在人造肌肉的肌纖維之間。");
//
//        // Parachute -----------------------------------------------------------------------------------
//        add(NuminaObjects.PARACHUTE.get(), "降落傘");
//        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "一種簡單的可重複使用的降落傘，可以在半空中展開和回收。");
//
//        // Rubber Hose ---------------------------------------------------------------------------------
//        add(NuminaObjects.RUBBER_HOSE.get(), "絕緣橡膠軟管");
//        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "一種能夠承受極熱或極冷的重絕緣橡膠軟管");
//






        /*
        add(NuminaObjects.SERVO.get(), "\u4f3a\u670d\u96fb\u6a5f");
        addItemDescriptions(NuminaObjects.SERVO.get(), "\u4e00\u7a2e\u7279\u6b8a\u985e\u578b\u7684\u96fb\u6a5f\uff0c\u5b83\u4f7f\u7528\u8108\u885d\u8abf\u88fd\u4fe1\u865f\u4f86\u57f7\u884c\u975e\u5e38\u7cbe\u78ba\u7684\u904b\u52d5\u3002");
        add(NuminaObjects.SOLAR_PANEL.get(), "\u592a\u967d\u80fd\u96fb\u6c60\u677f","item.numina.component_solar_panel.desc":"\u4e00\u7a2e\u5f9e\u592a\u967d\u767c\u96fb\u7684\u5149\u654f\u8a2d\u5099\u3002");
        add(NuminaObjects.SOLENOID.get(), "\u87ba\u7dda\u7ba1");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "\u7e8f\u7e5e\u5728\u9435\u78c1\u82af\u4e0a\u7684\u5c0e\u7dda\u7522\u751f\u57fa\u672c\u7684\u96fb\u78c1\u9435\u3002");
        add(NuminaObjects.WIRING.get(), "\u4f48\u7dda");
        addItemDescriptions(NuminaObjects.WIRING.get(), "\u4e00\u7a2e\u7279\u6b8a\u985e\u578b\u7684\u4f48\u7dda\uff0c\u5177\u6709\u9ad8\u4f0f\u7279\u5bb9\u91cf\u548c\u7cbe\u5ea6\uff0c\u662f\u52d5\u529b\u88dd\u7532\u4e2d\u654f\u611f\u96fb\u5b50\u8a2d\u5099\u6240\u5fc5\u9700\u7684\u3002");

 */








//        // Servo Motor ---------------------------------------------------------------------------------
//        add(NuminaObjects.SERVO.get(), "Servo Motor");
//
//        addItemDescriptions(NuminaObjects.SERVO.get(), "A special type of motor which uses a pulse-modulated signal to enact very precise movements.");
//
//        // Solar Panel ---------------------------------------------------------------------------------
//        add(NuminaObjects.SOLAR_PANEL.get(), "Solar Panel");
//
//        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "A light sensitive device that will generate electricity from the sun.");
//
//        // Solenoid ------------------------------------------------------------------------------------
//        add(NuminaObjects.SOLENOID.get(), "Solenoid");
//
//        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Wires wound around a ferromagnetic core produces a basic electromagnet.");
//
//        // Wiring --------------------------------------------------------------------------------------
//        add(NuminaObjects.WIRING.get(), "Wiring");
//
//        addItemDescriptions(NuminaObjects.WIRING.get(), "A special type of wiring with high voltaic capacity and precision, necessary for the sensitive electronics in power armor.");
    }

    @Override
    public void addModuleCategories() {
//        // Armor Modules -------------------------------------------------------------------------------
//        add(ModuleCategory.ARMOR.getTranslationKey(), "裝甲模組");
//
//        // Cosmetic ------------------------------------------------------------------------------------
//        add(ModuleCategory.COSMETIC.getTranslationKey(), "化妝品");
//
//        // Debug Modules -------------------------------------------------------------------------------
//        add(ModuleCategory.DEBUG.getTranslationKey(), "調試模組");
//
//        // Energy Generation ---------------------------------------------------------------------------
//        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "能源生產");
//
//        // Energy Storage ------------------------------------------------------------------------------
//        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "儲能");
//
//        // Environment ---------------------------------------------------------------------------------
//        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "環境");
//
//        // Mining Enchantment --------------------------------------------------------------------------
//        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "採礦附魔");
//
//        // Mining Enhancement --------------------------------------------------------------------------
//        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "採礦增強");
//
//        // Movement ------------------------------------------------------------------------------------
//        add(ModuleCategory.MOVEMENT.getTranslationKey(), "運動");
//
//        // None ----------------------------------------------------------------------------------------
//        add(ModuleCategory.NONE.getTranslationKey(), "沒有");
//
//        // Special -------------------------------------------------------------------------------------
//        add(ModuleCategory.SPECIAL.getTranslationKey(), "特殊");
//
//        // Tool ----------------------------------------------------------------------------------------
//        add(ModuleCategory.TOOL.getTranslationKey(), "工具");
//
//        // Vision --------------------------------------------------------------------------------------
//        add(ModuleCategory.VISION.getTranslationKey(), "視覺");
//
//        // Weapon --------------------------------------------------------------------------------------
//        add(ModuleCategory.WEAPON.getTranslationKey(), "武器");
    }

    @Override
    public void addItemDescriptions(Item key, String description) {
//        add(key.getDescriptionId() + ".desc", description);
    }
}
