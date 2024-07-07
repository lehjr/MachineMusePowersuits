package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

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

    @Override
    public void addGui() {
        add("gui.numina.chargingbase", "Зарядная база");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Заряжает экипированные предметы сущности");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "Энергия");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "Исправление переключения поля зрения");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "Исправление поля зрения включено");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "Исправление поля зрения отключено");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "Устанавливать");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,"Устанавливает модуль в выбранный модульный элемент, когда плеер находится в творческом режиме");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Установить все");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Устанавливает все совместимые модули верхнего уровня в выбранный модульный предмет, когда игрок находится в творческом режиме.");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "Стойка для брони");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "Стойка для брони");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy", "Максимальная энергия");
        add("module.tradeoff.maxTransfer", "Максимальный перенос за тик");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "Зарядная база");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Pritisnite SHIFT za više informacija. ...");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Смена режимов: Нажмите и удерживайте номер ячейки на панели быстрого доступа, в которой находится Силовой кулак.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "Энергия: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "Никаких установленных модулей! Этот предмет бесполезен до тех пор, пока вы не добавите несколько модулей в Tinker Table.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "Установленные модули:");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "Режим: ");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "Базовый аккумулятор");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "Встройте батарею, чтобы позволить изделию накапливать энергию.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "Усовершенствованная батарея");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "Встройте батарею, чтобы позволить изделию накапливать энергию.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "Элитная батарея");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Встроенная самая современная батарея для хранения большого количества энергии.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "Идеальная батарея");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Экспериментальное высокопроизводительное накопитель энергии, теперь почти без шансов взорваться (как мы думаем). Несмотря на неприятные слухи, он не основан на украденных инопланетных технологиях.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Искусственные мышцы");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Электрическая, искусственная мышца с меньшим диапазоном движения, чем человеческая мышца, но на порядок больше силы.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "Углеродное мышечное волокно");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "Маленький пучок углеродных волокон, очищенный для использования в искусственных мышцах.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "Компьютерный чип");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "Обновленная схема управления, которая содержит процессор, способный выполнять более сложные вычисления.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "Микросхема управления");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "Простой сетевой микроконтроллер для координации отдельного компонента.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "Излучатель силового поля");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "Передовое устройство, которое непосредственно манипулирует электромагнитными и гравитационными полями в области.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Планёрное крыло");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "Легкое аэродинамическое крыло с электромагнитом для быстрого развертывания и отвода.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Ионный двигатель");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "По существу миниатюрный ускоритель частиц. Ускоряет ионы до почти световой скорости для получения тяги.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "Излучатель голограммы");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "Многоцветный лазерный массив, который может дешево изменить внешний вид.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Магнит");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "Магнит, который тянет предметы к игроку.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Гель из мышечного волокна");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "Толстая, проводящая паста, идеально подходящая для установки между миофибрами в искусственной мышце.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Парашют");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "Простой многоразовый парашют, который можно развернуть и восстановить в воздухе.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Изолированный резиновый шланг");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "Сильно изолированный резиновый шланг, способный выдерживать сильную жару или холод");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Серводвигатель");
        addItemDescriptions(NuminaObjects.SERVO.get(), "Специальный тип двигателя, который использует импульсно-модулированный сигнал для принятия очень точных движений.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Панель солнечных батарей");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "светочувствительное устройство, которое будет генерировать электричество от солнца.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Соленоид");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Провода, намотанные вокруг ферромагнитного сердечника, создают основной электромагнит.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "Провод");
        addItemDescriptions(NuminaObjects.WIRING.get(), "Специальный тип проводки с высокой вольтовой емкостью и точностью, необходимой для чувствительной электроники в силовой броне.");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "Модули брони");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "Косметический");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "Модули отладки");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "Выработка энергии");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "Аккумулирование энергии");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "Окружающая среда");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "Чары шахтёров");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "Совершенствование горнодобывающей промышленности");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "Движение");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "Никакой");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "Специальный");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "Инструмент");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "Зрение");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "Оружие");
    }
}