package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

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
        add("gui.numina.chargingbase", "充電底座");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "對實體的裝備物品收費");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "能源");
        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "切換視野修復");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "啟用視場修復");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "視野修復已禁用");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "安裝");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC, "當玩家處於創作模式時，將模組安裝到選定的模組化專案中");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "全部安裝");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "當玩家處於創造模式時，將所有頂級相容模組安裝到選定的模組化專案中");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "裝甲架");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "裝甲架");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "最大能量");
        add("module.tradeoff.maxTransfer", "每筆報價的最大轉帳次數");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "充電底座");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "按 SHIFT 鍵瞭解更多資訊。");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "更改模式：按住 Power Fist 所在的快捷欄插槽編號。");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "能源：");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "沒有安裝模組！在Tinker Table中添加一些模組之前，此項是無用的。");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "已安裝的模組：");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "模式：");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "基本電池");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "集成電池以使物品能夠存儲能量。");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "先進電池");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "集成電池以使物品能夠存儲能量。");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "精英電池");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "集成最先進的電池以存儲大量能量。");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(),"終極電池");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "實驗性的高端電力存儲設備，現在幾乎沒有爆炸的機會（我們認u8d的機我認為）。儘管有令人討厭的謠言，但它並不是基於被盜的外星技術。");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "人造肌肉");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "一種電動的人造肌肉，其運動範圍比人類肌肉小，但力量要高出幾個數量級。");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "碳纖維肌纖維");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "一小束碳纖維，經過精製后可用於人造肌肉。");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "電腦晶片");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "升級的控制電路，包含能夠進行更高級計算的CPU。");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "控制電路");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "一個簡啚e的可聯網微控制聯網微控制制網微控制制制制聯網微控制可聯網微控制可uu5668，用於協調啚e個元件。");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "力場發射器");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "一種直接操縱區域電磁場和引力場的先進設備。");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "滑翔機翼");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "輕巧的空氣動que529b學機翼，帶有 電磁鐵，可快速展開和縮回。");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "離子推進器");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "本質上是一個微que578b粒子加速器。將 離子加速到接近se5149速以產生推力。");

        // Laser Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "全息圖發射器");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "一種五顏六色的激光陣列，可以廉價地改變某些東西的外觀。");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "磁鐵");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "一種金屬裝置，可產生磁場，將物品拉向玩家。");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "肌纖維凝膠");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "一種厚實的導電糊狀物，非常適合安裝在人造肌肉的肌纖維之間。");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "降落傘");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "一種簡單的可重複使用的降落傘，可以在半空中展開和回收。");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "絕緣橡膠軟管");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "一種能夠承受極熱或極冷的重絕緣橡膠軟管");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "伺服電機");
        addItemDescriptions(NuminaObjects.SERVO.get(), "一種特殊類型的que96fb機，它使用脈衝 調製信號來執行非常精確的運動。");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "太陽能電池板");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "一種從太陽發電的光敏設備。");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "螺線管");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "纏繞在鐵磁芯上的導線產生基本的電磁鐵。");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "佈線");
        addItemDescriptions(NuminaObjects.WIRING.get(), "一種特殊類型的佈線，具有高伏特容量和精度，是動力u88d甲中敏感電子設備所必需的。");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "裝甲模組");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "化妝品");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "調試模組");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "能源生產");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "儲能");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "環境");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "採礦附魔");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "採礦增強");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "運動");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "沒有");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "特殊");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "工具");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "視覺");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "武器");
    }
}
