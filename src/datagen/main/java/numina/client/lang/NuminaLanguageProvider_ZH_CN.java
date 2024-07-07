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

    @Override
    public void addGui() {
        add("gui.numina.chargingbase", "充电底座");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "为实体装备的物品收费");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "能源");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "切换视野修复");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "视野修复已启用");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "视野修复已禁用");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "安装");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,"当玩家处于创作模式时，将模块安装到选定的模块化项目中");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "全部安装");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "当玩家处于创造模式时，将所有顶级兼容模块安装到选定的模块化项目中");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "装甲架");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "装甲架");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "最大能量");
        add("module.tradeoff.maxTransfer", "每次报价的最大转账");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "充电底座");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "按 SHIFT 键了解更多信息。");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "更改模式：按住电源拳所在的热栏插槽的编号。");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "能源：");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "没有安装模块！在修补表中添加一些模块之前，此项是无用的。");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES, "已安装的模块：");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "模式：");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "基本电池");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "集成电池以允许物品储存能量。");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "先进电池");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "集成电池以允许物品储存能量。");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "精英电池");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "集成最先进的电池以存储大量能量。");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "终极电池");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "实验性的极高能量存储设备，几乎不会爆炸（我这么认为）。不要相信那些恶毒的谣言，这东西可不是什么偷自外星人的技术。");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "人造肌肉");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "一种电动人造肌肉，运动范围比人体肌肉小，但力量要大几个数量级。");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "碳纤维肌纤维");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "一小束碳纤维，精制用于人造肌肉。");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "电脑芯片");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "升级的控制电路，包含一个能够进行更高级计算的CPU。");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "控制电路");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "一个简单的可联网微控制器，用于协调单个组件。");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "力场发射器");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "一种先进的设备，可直接操纵一个区域中的电磁场和引力场。");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "滑翔机翼");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "带有电磁铁的轻型空气动力学机翼，可快速展开和缩回。");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "离子推进器");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "本质上是一个微型粒子加速器。将离子加速到近光速以产生推力。");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "全息影像发射器");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "一种五彩缤纷的激光阵列，可以廉价地改变某物的外观。");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "磁铁");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "一种金属装置，可产生磁场，将物品拉向玩家。");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "肌纤维凝胶");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "一种厚实的导电糊状物，非常适合在人造肌肉中的肌纤维之间贴合。");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "降落伞");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "一个简单的可重复使用的降落伞，可以在半空中部署和回收。");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "绝缘橡胶软管");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "高度绝缘的橡胶软管，能够承受极热或极冷");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "伺服电机");
        addItemDescriptions(NuminaObjects.SERVO.get(), "一种特殊类型的电机，它使用脉冲调制信号来执行非常精确的运动。");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "太阳能电池板");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "一种从太阳发电的光敏设备。");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "螺线管");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "缠绕在铁磁芯上的导线产生基本的电磁铁。");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "布线");
        addItemDescriptions(NuminaObjects.WIRING.get(), "一种具有高电压容量和精度的特殊类型的布线，是动力装甲中敏感电子设备所必需的。");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "装甲模块");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "化妆品");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "调试模块");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "能源生产");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "储能");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "环境");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "采矿附魔");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "挖矿增强");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "运动");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "没有");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "特殊");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "工具");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "视觉");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "武器");
    }
}
