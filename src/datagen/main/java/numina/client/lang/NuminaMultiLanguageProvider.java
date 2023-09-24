package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NuminaMultiLanguageProvider extends MulitiLanguageProvider {
    public NuminaMultiLanguageProvider(DataGenerator gen, String modid) {
        super(gen, modid);
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
        addTranslationTopAll("itemGroup.numina", "Numina");
    }

    void addGui() {
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ladestation");
        tmp.put(Localizations.EN_US, "Charging Base");
        tmp.put(Localizations.FR_FR, "Base de Charge");
        tmp.put(Localizations.PT_BR, "Base de Carregamento");
        tmp.put(Localizations.PT_PT, "Base de Carregamento");
        tmp.put(Localizations.RU_RU, "Зарядная база");
        tmp.put(Localizations.ZH_CN, "充电底座");
        add("gui.numina.chargingbase", tmp);

        //
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ladestation");
        tmp.put(Localizations.EN_US, "Charges an entity's equipped items");
        tmp.put(Localizations.FR_FR, "Charge les articles équipés d’une entité");
        tmp.put(Localizations.PT_BR, "Cobra os itens equipados de uma entidade");
        tmp.put(Localizations.PT_PT, "Cobra os itens equipados de uma entidade");
        tmp.put(Localizations.RU_RU, "Взимает плату за экипировку объекта");
        tmp.put(Localizations.ZH_CN, "为实体装备的物品收费");
        add("tooltip.numina.charging_base",tmp);

        // Energy --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energie");
        tmp.put(Localizations.EN_US, "Energy");
        tmp.put(Localizations.FR_FR, "Énergie");
        tmp.put(Localizations.PT_BR, "Energia");
        tmp.put(Localizations.PT_PT, "Energia");
        tmp.put(Localizations.RU_RU, "Энергия");
        tmp.put(Localizations.ZH_CN, "能源");
        add("numina.energy", tmp);

        // FOV Fix Toggle ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Korrektur des Umschaltens des Sichtfelds");
        tmp.put(Localizations.EN_US, "Toggle field of view fix");
        tmp.put(Localizations.FR_FR, "Basculer le champ de vision fixe");
        tmp.put(Localizations.PT_BR, "Alternar correção de campo de visão");
        tmp.put(Localizations.PT_PT, "Alternar correção de campo de visão");
        tmp.put(Localizations.RU_RU, "Переключить исправление поля зрения");
        tmp.put(Localizations.ZH_CN, "切换视野修复");
        add("key.numina.fovfixtoggle", tmp);

        // FOV fix enabled -----------------------------------------------------------------------------
        new HashMap<>();
        tmp.put(Localizations.DE_DE, "Korrektur des Sichtfelds aktiviert");
        tmp.put(Localizations.EN_US, "Field of view fix enabled");
        tmp.put(Localizations.FR_FR, "Correction du champ de vision activée");
        tmp.put(Localizations.PT_BR, "Correção de campo de visão habilitada");
        tmp.put(Localizations.PT_PT, "Correção do campo de visão ativada");
        tmp.put(Localizations.RU_RU, "Исправление поля зрения включено");
        tmp.put(Localizations.ZH_CN, "视野修复已启用");
        add("message.numina.fovfixtoggle.enabled", tmp);

        // FOV fix disabled ----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Sichtfeldkorrektur deaktiviert");
        tmp.put(Localizations.EN_US, "Field of view fix disabled");
        tmp.put(Localizations.FR_FR, "Correction du champ de vision désactivée");
        tmp.put(Localizations.PT_BR, "Correção de campo de visão desabilitada");
        tmp.put(Localizations.PT_PT, "Correção do campo de visão desativada");
        tmp.put(Localizations.RU_RU, "Исправление поля зрения отключено");
        tmp.put(Localizations.ZH_CN, "视野修复已禁用");
        add("message.numina.fovfixtoggle.disabled", tmp);
    }

    void addArmorStand() {
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rüstungsständer");
        tmp.put(Localizations.EN_US, "Armor Stand");
        tmp.put(Localizations.FR_FR, "Support d’armure");
        tmp.put(Localizations.PT_BR, "Suporte de Blindagem");
        tmp.put(Localizations.PT_PT, "Suporte de armadura");
        tmp.put(Localizations.RU_RU, "Подставка для брони");
        tmp.put(Localizations.ZH_CN, "装甲架");
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rüstungsständer");
        tmp.put(Localizations.EN_US, "Armor Stand");
        tmp.put(Localizations.FR_FR, "Support d’armure");
        tmp.put(Localizations.PT_BR, "Suporte de Blindagem");
        tmp.put(Localizations.PT_PT, "Suporte de armadura");
        tmp.put(Localizations.RU_RU, "Подставка для брони");
        tmp.put(Localizations.ZH_CN, "装甲架");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE, tmp);
    }

    void addModuleTradeoffs() {
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Maximale Energie");
        tmp.put(Localizations.EN_US, "Maximum Energy");
        tmp.put(Localizations.FR_FR, "Énergie maximale");
        tmp.put(Localizations.PT_BR, "Energia Máxima");
        tmp.put(Localizations.PT_PT, "Energia Máxima");
        tmp.put(Localizations.RU_RU, "Максимальная энергия");
        tmp.put(Localizations.ZH_CN, "最大能量");
        add("module.tradeoff.maxEnergy", tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Maximale Übertragung pro Tick");
        tmp.put(Localizations.EN_US, "Maximum Transfer per Tick");
        tmp.put(Localizations.FR_FR, "Transfert maximum par tick");
        tmp.put(Localizations.PT_BR, "Transferência máxima por tick");
        tmp.put(Localizations.PT_PT, "Transferência máxima por tick");
        tmp.put(Localizations.RU_RU, "Максимальный перевод за тик");
        tmp.put(Localizations.ZH_CN, "每次报价的最大转账");
        add("module.tradeoff.maxTransfer", tmp);
    }

    void addChargingBase() {
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ladestation");
        tmp.put(Localizations.EN_US, "Charging Base");
        tmp.put(Localizations.FR_FR, "Base de charge");
        tmp.put(Localizations.PT_BR, "Base de Carregamento");
        tmp.put(Localizations.PT_PT, "Base de carregamento");
        tmp.put(Localizations.RU_RU, "Зарядная база");
        tmp.put(Localizations.ZH_CN, "充电底座");
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), tmp);
    }

    void addToolTips() {
        addTranslationTopAll("tooltip.numina.battery.energy", "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Modus ändern: Halte die Nummer des Hotbar-Slots gedrückt, in dem sich die Power Fist befindet.");
        tmp.put(Localizations.EN_US, "Press SHIFT for more information.");
        tmp.put(Localizations.FR_FR, "Changer de mode : appuyez longuement sur le numéro de l’emplacement de la barre d’accès dans laquelle se trouve le Power Fist.");
        tmp.put(Localizations.PT_BR, "Alterar modos: Pressione e mantenha pressionado o número do slot da barra de atalho em que o Power Fist está.");
        tmp.put(Localizations.PT_PT, "Alterar modos: prima sem soltar o número da ranhura da barra de atalho em que se encontra o Power Fist.");
        tmp.put(Localizations.RU_RU, "Нажмите SHIFT для получения дополнительной информации.");
        tmp.put(Localizations.ZH_CN, "更改模式：按住电源拳所在的热栏插槽的编号。");
        add("tooltip.numina.pressShift", tmp);

        // Mode Change ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Modus ändern: Halte die Nummer des Hotbar-Slots gedrückt, in dem sich die Power Fist befindet.");
        tmp.put(Localizations.EN_US, "Change modes: Press and hold the number of the hotbar slot the Power Fist is in.");
        tmp.put(Localizations.FR_FR, "Changer de mode : appuyez longuement sur le numéro de l’emplacement de la barre d’accès dans laquelle se trouve le Power Fist.");
        tmp.put(Localizations.PT_BR, "Alterar modos: Pressione e mantenha pressionado o número do slot da barra de atalho em que o Power Fist está.");
        tmp.put(Localizations.PT_PT, "Alterar modos: prima sem soltar o número da ranhura da barra de atalho em que se encontra o Power Fist.");
        tmp.put(Localizations.RU_RU, "Изменить режимы: нажмите и удерживайте номер слота горячей панели, в который входит Power Fist");
        tmp.put(Localizations.ZH_CN, "更改模式：按住电源拳所在的热栏插槽的编号。");
        add("tooltip.numina.changeModes", tmp);

        // Energy --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energie: ");
        tmp.put(Localizations.EN_US, "Energy: ");
        tmp.put(Localizations.FR_FR, "Énergie: ");
        tmp.put(Localizations.PT_BR, "Energia: ");
        tmp.put(Localizations.PT_PT, "Energia: ");
        tmp.put(Localizations.RU_RU, "Энергия: ");
        tmp.put(Localizations.ZH_CN, "能源： ");
        add("tooltip.numina.energy", tmp);

        // No Modules Installed ------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Keine verbauten Module! Dieser Gegenstand ist nutzlos, bis Sie einige Module an einem Basteltisch hinzufügen.");
        tmp.put(Localizations.EN_US, "No installed modules! This item is useless until you add some modules at a Tinker Table.");
        tmp.put(Localizations.FR_FR, "Aucun module installé! Cet élément est inutile tant que vous n’avez pas ajouté des modules à une table Tinker.");
        tmp.put(Localizations.PT_BR, "Nenhum módulo instalado! Este item é inútil até que você adicione alguns módulos em uma tabela Tinker.");
        tmp.put(Localizations.PT_PT, "Sem módulos instalados! Este item é inútil até que você adicione alguns módulos em uma Tinker Table.");
        tmp.put(Localizations.RU_RU, "Нет установленных модулей! Этот элемент бесполезен, пока вы не добавите некоторые модули в таблицу Tinker");
        tmp.put(Localizations.ZH_CN, "“未安装模块！在你把一些模块添加到修补表之前，这个项目是没有用的。");
        add("tooltip.numina.noModules", tmp);

        // Installed Modules: --------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Installierte Module: ");
        tmp.put(Localizations.EN_US, "Installed Modules:");
        tmp.put(Localizations.FR_FR, "Modules installés : ");
        tmp.put(Localizations.PT_BR, "Módulos instalados: ");
        tmp.put(Localizations.PT_PT, "Módulos instalados: ");
        tmp.put(Localizations.RU_RU, "Установленные модули: ");
        tmp.put(Localizations.ZH_CN, "已安装的模块： ");
        add("tooltip.numina.installedModules", tmp);

        // Mode: ---------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Modus:");
        tmp.put(Localizations.EN_US, "Mode: ");
        tmp.put(Localizations.FR_FR, "Mode:");
        tmp.put(Localizations.PT_BR, "Modo: ");
        tmp.put(Localizations.PT_PT, "Modo: ");
        tmp.put(Localizations.RU_RU, "Режим: ");
        tmp.put(Localizations.ZH_CN, "模式: ");
        add("tooltip.numina.mode", tmp);
    }

    void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Basisbatterie");
        tmp.put(Localizations.EN_US, "Basic Battery");
        tmp.put(Localizations.FR_FR, "Batterie de base");
        tmp.put(Localizations.PT_BR, "Bateria Básica");
        tmp.put(Localizations.PT_PT, "Bateria Básica");
        tmp.put(Localizations.RU_RU, "Основной аккумулятор");
        tmp.put(Localizations.ZH_CN, "基础电池");
        add(NuminaObjects.BASIC_BATTERY.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Integrieren Sie eine Batterie, damit der Gegenstand Energie speichern kann.");
        tmp.put(Localizations.EN_US, "Integrate a battery to allow the item to store energy.");
        tmp.put(Localizations.FR_FR, "Intègre une batterie pour permettre à l'objet de stocker de l'énergie.");
        tmp.put(Localizations.PT_BR, "Integra uma bateria a um item para fazer com que ele seja capaz de armazenar energia.");
        tmp.put(Localizations.PT_PT, "Integre uma bateria para permitir que o item armazene energia.");
        tmp.put(Localizations.RU_RU, "Аккумулятор, позволяющий предмету хранить FE энергию.");
        tmp.put(Localizations.ZH_CN, "内置电池，这样物品就可以储存一定能量了。");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), tmp);

        // Advanced Battery ----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Fortgeschrittene Batterie");
        tmp.put(Localizations.EN_US, "Advanced Battery");
        tmp.put(Localizations.FR_FR, "Batterie avancée");
        tmp.put(Localizations.PT_BR, "Bateria Avançada");
        tmp.put(Localizations.PT_PT, "Bateria Avançada");
        tmp.put(Localizations.RU_RU, "Продвинутый аккумулятор");
        tmp.put(Localizations.ZH_CN, "高级电池");
        add(NuminaObjects.ADVANCED_BATTERY.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Integrieren Sie eine fortschrittlichere Batterie, um mehr Energie zu speichern.");
        tmp.put(Localizations.EN_US, "Integrate a battery to allow the item to store energy.");
        tmp.put(Localizations.FR_FR, "Intègre une batterie avancée pour permettre à l'objet de stocker plus d'énergie.");
        tmp.put(Localizations.PT_BR, "Integra uma bateria avançada para armazenar ainda mais energia.");
        tmp.put(Localizations.PT_PT, "Integre uma bateria mais avançada para armazenar mais energia.");
        tmp.put(Localizations.RU_RU, "Продвинутый аккумулятор, позволяющий предмету хранить больше FE энергии.");
        tmp.put(Localizations.ZH_CN, "内置一个更高级的电池，可储存更多能量。");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), tmp);

        // Elite Battery -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Elitebatterie");
        tmp.put(Localizations.EN_US, "Elite Battery");
        tmp.put(Localizations.FR_FR, "batterie élite");
        tmp.put(Localizations.PT_BR, "Bateria Superior");
        tmp.put(Localizations.PT_PT, "Bateria Elite");
        tmp.put(Localizations.RU_RU, "Элитный аккумулятор");
        tmp.put(Localizations.ZH_CN, "精英电池");
        add(NuminaObjects.ELITE_BATTERY.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Integrieren Sie eine der fortschrittlichsten Batterien, um eine große Menge an Energie zu speichern.");
        tmp.put(Localizations.EN_US, "Integrate a the most advanced battery to store an extensive amount of energy.");
        tmp.put(Localizations.FR_FR, "Intègre une batterie plus avancée pour permettre un stockage énergétique étendu à l'objet.");
        tmp.put(Localizations.PT_BR, "Integra uma bateria superior para armazenar uma quantidade absurda de energia.");
        tmp.put(Localizations.PT_PT, "Integre a bateria mais avançada para armazenar uma grande quantidade de energia.");
        tmp.put(Localizations.RU_RU, "Самый элитный аккумулятор, позволяющий предмету хранить ещё больше FE энергии.");
        tmp.put(Localizations.ZH_CN, "内置一个最高级的电池，可储存大量能量。");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), tmp);

        // Ultimate Battery  ---------------------------------------------------------------------------
        tmp.put(Localizations.DE_DE, "Ultimative Batterie");
        tmp.put(Localizations.EN_US, "Ultimate Battery");
        tmp.put(Localizations.FR_FR, "Batterie ultime");
        tmp.put(Localizations.PT_BR, "Bateria Ultimate");
        tmp.put(Localizations.PT_PT, "Bateria Ultimate");
        tmp.put(Localizations.RU_RU, "Максимальная батарея");
        tmp.put(Localizations.ZH_CN, "终极电池");
        add(NuminaObjects.ULTIMATE_BATTERY.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Experimentelles High-End-Energiespeicher-Gerät, jetzt fast ohne Chance zu explodieren (wir denken). Trotz übler Gerüchte basiert es nicht auf gestohlener Alien-Technologie.");
        tmp.put(Localizations.EN_US, "Experimental high end power storage device, now with almost no chance of exploding (we think). Despite nasty rumors, it is not based on stolen alien technology.");
        tmp.put(Localizations.FR_FR, "Dispositif expérimental de stockage de l'alimentation haut de gamme, qui n'a presque aucune chance d'exploser (selon nous). Malgré de mauvaises rumeurs, il n’est pas basé sur une technologie extra-terrestre volée.");
        tmp.put(Localizations.PT_BR, "Dispositivo de armazenamento de energia high-end experimental, agora com quase nenhuma chance de explodir (pensamos). Apesar dos rumores desagradáveis, não é baseado em tecnologia alienígena roubada.");
        tmp.put(Localizations.PT_PT, "Dispositivo de armazenamento de energia high-end experimental, agora com quase nenhuma chance de explodir (pensamos). Apesar dos rumores desagradáveis, não é baseado em tecnologia alienígena roubada.");
        tmp.put(Localizations.RU_RU, "Экспериментальное высокопроизводительное устройство накопления FE энергии, теперь почти не имеющее шансов на взрыв (мы думаем). Несмотря на неприятные слухи, он не основан на украденных инопланетных технологиях.");
        tmp.put(Localizations.ZH_CN, "实验性的极高能量存储设备，几乎不会爆炸（我这么认为）。不要相信那些恶毒的谣言，这东西可不是什么偷自外星人的技术。");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), tmp);
    }

    void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Künstlicher Muskel");
        tmp.put(Localizations.EN_US, "Artificial Muscle");
        tmp.put(Localizations.FR_FR, "Muscle artificiel");
        tmp.put(Localizations.PT_BR, "Miofibra de Carbono");
        tmp.put(Localizations.PT_PT, "Músculo Artificial");
        tmp.put(Localizations.RU_RU, "Искусственные мышцы");
        tmp.put(Localizations.ZH_CN, "人造肌肉");
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein elektrischer, künstlicher Muskel mit weniger Bewegungsumfang als menschlicher Muskel, aber Größenordnungen mehr Kraft.");
        tmp.put(Localizations.EN_US, "An electrical, artificial muscle, with less range of movement than human muscle but orders of magnitude more strength.");
        tmp.put(Localizations.FR_FR, "Muscle électrique artificiel, avec une amplitude de mouvement inférieure à celle du muscle humain, mais une force de plusieurs ordres de grandeur.");
        tmp.put(Localizations.PT_BR, "Um pequeno feixe de fibras de carbono, refinadas para uso em músculos artificiais.");
        tmp.put(Localizations.PT_PT, "Um músculo elétrico artificial com menos amplitude de movimento que o músculo humano, mas ordens de grandeza maior.");
        tmp.put(Localizations.RU_RU, "Электрическая, искусственная мышца с меньшим диапазоном движения, чем человеческая мышца, но на порядок больше силы.");
        tmp.put(Localizations.ZH_CN, "一种电动人造肌肉，运动范围小于人体肌肉，但强度要高出几个数量级。");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), tmp);

        // Carbon Myofiber -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Karbonmyofaser");
        tmp.put(Localizations.EN_US, "Carbon Myofiber");
        tmp.put(Localizations.FR_FR, "Myofibre carbone");
        tmp.put(Localizations.PT_BR, "Músculo Artificial");
        tmp.put(Localizations.PT_PT, "Miofibra de Carbono");
        tmp.put(Localizations.RU_RU, "Углеродное мышечное волокно");
        tmp.put(Localizations.ZH_CN, "人造碳纤维肌肉");
        add(NuminaObjects.CARBON_MYOFIBER.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein kleines Bündel Kohlenstofffasern, raffiniert für den Einsatz in künstlichen Muskeln.");
        tmp.put(Localizations.EN_US, "A small bundle of carbon fibers, refined for use in artificial muscles.");
        tmp.put(Localizations.FR_FR, "Un petit paquet de fibres de carbone, affiné pour être utilisé dans les muscles artificiels.");
        tmp.put(Localizations.PT_BR, "Um músculo elétrico artificial com menos amplitude de movimento que o músculo humano, mas ordens de grandeza maior.");
        tmp.put(Localizations.PT_PT, "Um pequeno feixe de fibras de carbono, refinadas para uso em músculos artificiais.");
        tmp.put(Localizations.RU_RU, "Маленький пучок углеродных волокон, очищенный для использования в искусственных мышцах.");
        tmp.put(Localizations.ZH_CN, "一小束碳纤维，精制用于人造肌肉。");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), tmp);

        // Computer Chip -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Mikroprozessor");
        tmp.put(Localizations.EN_US, "Computer Chip");
        tmp.put(Localizations.FR_FR, "Puce informatique");
        tmp.put(Localizations.PT_BR, "Chip de Computador");
        tmp.put(Localizations.PT_PT, "Chip de Computador");
        tmp.put(Localizations.RU_RU, "Компьютерный чип");
        tmp.put(Localizations.ZH_CN, "电脑芯片");
        add(NuminaObjects.COMPUTER_CHIP.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Eine verbesserte Steuerschaltung, die eine CPU enthält, die zu komplexeren Berechnungen in der Lage ist.");
        tmp.put(Localizations.EN_US, "An upgraded control circuit that contains a CPU which is capable of more advanced calculations.");
        tmp.put(Localizations.FR_FR, "Un circuit de contrôle mis à niveau contenant un CPU capable d'effectuer des calculs plus avancés.");
        tmp.put(Localizations.PT_BR, "Um circuito de controle atualizado que contém uma CPU que é capaz de cálculos mais avançados.");
        tmp.put(Localizations.PT_PT, "Um circuito de controle atualizado que contém uma CPU que é capaz de cálculos mais avançados.");
        tmp.put(Localizations.RU_RU, "Обновленная схема управления, которая содержит процессор, способный выполнять более сложные вычисления.");
        tmp.put(Localizations.ZH_CN, "升级后的控制电路，包含一个能够进行更高级计算的CPU。");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), tmp);

        // Control Circuit -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Kontrollschaltkreis");
        tmp.put(Localizations.EN_US, "Control Circuit");
        tmp.put(Localizations.FR_FR, "Circuit de Contrôle");
        tmp.put(Localizations.PT_BR, "Circuito de Controle");
        tmp.put(Localizations.PT_PT, "Circuito de Controle");
        tmp.put(Localizations.RU_RU, "Микросхема управления");
        tmp.put(Localizations.ZH_CN, "控制电路");
        add(NuminaObjects.CONTROL_CIRCUIT.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein einfacher netzwerkfähiger Mikrocontroller zur Koordination einer einzelnen Komponente.");
        tmp.put(Localizations.EN_US, "A simple networkable microcontroller for coordinating an individual component.");
        tmp.put(Localizations.FR_FR, "Un microcontrôleur simple pouvant être mis en réseau pour coordonner un composant individuel.");
        tmp.put(Localizations.PT_BR, "Um microcontrolador de rede simples para coordenar um componente individual.");
        tmp.put(Localizations.PT_PT, "Um microcontrolador de rede simples para coordenar um componente individual.");
        tmp.put(Localizations.RU_RU, "Простой сетевой микроконтроллер для координации отдельного компонента.");
        tmp.put(Localizations.ZH_CN, "一个简单的可联网微控制器，用于协调单个组件。");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), tmp);

        // Force Field Emitter -------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Kraftfelderzeuger");
        tmp.put(Localizations.EN_US, "Force Field Emitter");
        tmp.put(Localizations.FR_FR, "Emetteur de champion de force");
        tmp.put(Localizations.PT_BR, "Emissor de Campo de Força");
        tmp.put(Localizations.PT_PT, "Emissor do Campo de Força");
        tmp.put(Localizations.RU_RU, "Излучатель силового поля");
        tmp.put(Localizations.ZH_CN, "力场发射器");
        add(NuminaObjects.FIELD_EMITTER.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein fortschrittliches Gerät, das elektromagnetische und Gravitationsfelder in einem Gebiet direkt manipuliert.");
        tmp.put(Localizations.EN_US, "An advanced device which directly manipulates electromagnetic and gravitational fields in an area.");
        tmp.put(Localizations.FR_FR, "Un appareil avancé qui manipule directement les champs électromagnétiques et gravitationnels dans une zone.");
        tmp.put(Localizations.PT_BR, "Um dispositivo avançado que manipula diretamente campos eletromagnéticos e gravitacionais em uma área.");
        tmp.put(Localizations.PT_PT, "Um dispositivo avançado que manipula diretamente campos eletromagnéticos e gravitacionais em uma área.");
        tmp.put(Localizations.RU_RU, "Передовое устройство, которое непосредственно манипулирует электромагнитными и гравитационными полями в области.");
        tmp.put(Localizations.ZH_CN, "一种直接操纵区域中的电磁场和重力场的高级设备。");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), tmp);

        // Glider Wing ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Gleiterflügel");
        tmp.put(Localizations.EN_US, "Glider Wing");
        tmp.put(Localizations.FR_FR, "Ailes de planeur");
        tmp.put(Localizations.PT_BR, "Asa de Planador");
        tmp.put(Localizations.PT_PT, "Asa de Planador");
        tmp.put(Localizations.RU_RU, "Планёрное крыло");
        tmp.put(Localizations.ZH_CN, "滑翔翼");
        add(NuminaObjects.GLIDER_WING.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein leichter aerodynamischer Flügel mit einem Elektromagneten für schnelles Ein- und Ausfahren.");
        tmp.put(Localizations.EN_US, "A lightweight aerodynamic wing with an electromagnet for quick deployment and retraction.");
        tmp.put(Localizations.FR_FR, "Une aile aérodynamique légère dotée d'un électroaimant pour un déploiement et une rétraction rapides.");
        tmp.put(Localizations.PT_BR, "Uma asa aerodinâmica leve com um eletroímã para rápida implantação e retração.");
        tmp.put(Localizations.PT_PT, "Uma asa aerodinâmica leve com um eletroímã para rápida implantação e retração.");
        tmp.put(Localizations.RU_RU, "Легкое аэродинамическое крыло с электромагнитом для быстрого развертывания и отвода.");
        tmp.put(Localizations.ZH_CN, "轻型空气动力学机翼，带有电磁铁，可快速展开和收回。");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), tmp);

        // Ion Thruster --------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ionenbeschleuniger");
        tmp.put(Localizations.EN_US, "Ion Thruster");
        tmp.put(Localizations.FR_FR, "Propulseur Ionique");
        tmp.put(Localizations.PT_BR, "Propulsor Iónico");
        tmp.put(Localizations.PT_PT, "Propulsor Iónico");
        tmp.put(Localizations.RU_RU, "Ионный двигатель");
        tmp.put(Localizations.ZH_CN, "离子推进器");
        add(NuminaObjects.ION_THRUSTER.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Im Wesentlichen ein Miniatur-Teilchenbeschleuniger. Beschleunigt Ionen auf fast Lichtgeschwindigkeit, um Schub zu erzeugen");
        tmp.put(Localizations.EN_US, "Essentially a miniature particle accelerator. Accelerates ions to near-light speed to produce thrust.");
        tmp.put(Localizations.FR_FR, "Essentiellement un accélérateur de particules miniature. Accélère les ions à une vitesse proche de la lumière pour produire une poussée.");
        tmp.put(Localizations.PT_BR, "Essencialmente um acelerador de partículas em miniatura. Acelera os íons até a velocidade de quase luz para produzir empuxo.");
        tmp.put(Localizations.PT_PT, "Essencialmente um acelerador de partículas em miniatura. Acelera os íons até a velocidade de quase luz para produzir empuxo.");
        tmp.put(Localizations.RU_RU, "По существу миниатюрный ускоритель частиц. Ускоряет ионы до почти световой скорости для получения тяги.");
        tmp.put(Localizations.ZH_CN, "本质上是一个微型粒子加速器。将离子加速到接近光速以产生推力。");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), tmp);

        // Hologram Emitter ----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Hologrammerzeuger");
        tmp.put(Localizations.EN_US, "Hologram Emitter");
        tmp.put(Localizations.FR_FR, "Emetteur d'hologramme");
        tmp.put(Localizations.PT_BR, "Emissor de Holograma");
        tmp.put(Localizations.PT_PT, "Emissor de Holograma");
        tmp.put(Localizations.RU_RU, "Излучатель голограммы");
        tmp.put(Localizations.ZH_CN, "全息发射器");
        add(NuminaObjects.LASER_EMITTER.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein mehrfarbiges Laser-Array, das die Erscheinung von etwas billig verändern kann.");
        tmp.put(Localizations.EN_US, "A multicolored laser array which can cheaply alter the appearance of something.");
        tmp.put(Localizations.FR_FR, "Un réseau laser multicolore qui peut modifier l'apparence de quelque chose à moindre coût.");
        tmp.put(Localizations.PT_BR, "Uma matriz laser multicolorida que pode alterar a aparência de algo barato.");
        tmp.put(Localizations.PT_PT, "Uma matriz laser multicolorida que pode alterar a aparência de algo barato.");
        tmp.put(Localizations.RU_RU, "Многоцветный лазерный массив, который может дешево изменить внешний вид.");
        tmp.put(Localizations.ZH_CN, "多色激光阵列，可以廉价地改变某物的外观。");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), tmp);

        // Magnet --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Magnet");
        tmp.put(Localizations.EN_US, "Magnet");
        tmp.put(Localizations.FR_FR, "Aimant");
        tmp.put(Localizations.PT_BR, "Magnético");
        tmp.put(Localizations.PT_PT, "Magnético");
        tmp.put(Localizations.RU_RU, "Магнит");
        tmp.put(Localizations.ZH_CN, "磁铁");
        add(NuminaObjects.MAGNET.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein metallisches Gerät, das ein Magnetfeld erzeugt, das Gegenstände zum Player zieht.");
        tmp.put(Localizations.EN_US, "A metallic device that generates a magnetic field which pulls items towards the player.");
        tmp.put(Localizations.FR_FR, "Un dispositif métallique qui génère un champ magnétique qui tire les objets vers le joueur.");
        tmp.put(Localizations.PT_BR, "Um dispositivo metálico que gera um campo magnético que puxa itens para o jogador.");
        tmp.put(Localizations.PT_PT, "Um dispositivo metálico que gera um campo magnético que puxa itens para o jogador.");
        tmp.put(Localizations.RU_RU, "Магнит, который тянет предметы к игроку.");
        tmp.put(Localizations.ZH_CN, "一种金属设备，可产生一个磁场，将物品拉向播放器。");
        addItemDescriptions(NuminaObjects.MAGNET.get(), tmp);

        // Myofiber Gel --------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Myofasergel");
        tmp.put(Localizations.EN_US, "Myofiber Gel");
        tmp.put(Localizations.FR_FR, "Gel myofibre");
        tmp.put(Localizations.PT_BR, "Gel de Miofibra");
        tmp.put(Localizations.PT_PT, "Gel de Miofibra");
        tmp.put(Localizations.RU_RU, "Гель из мышечного волокна");
        tmp.put(Localizations.ZH_CN, "人造凝胶肌肉");
        add(NuminaObjects.MYOFIBER_GEL.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Eine dicke, leitfähige Paste, perfekt für die Anpassung zwischen Muskelfasern in einem künstlichen Muskel.");
        tmp.put(Localizations.EN_US, "A thick, conductive paste, perfect for fitting between myofibers in an artificial muscle.");
        tmp.put(Localizations.FR_FR, "Pâte épaisse et conductrice, parfaite pour l’adaptation entre des myofibres dans un muscle artificiel. ");
        tmp.put(Localizations.PT_BR, "Uma pasta espessa e condutora, perfeita para encaixar entre miofibras em um músculo artificial.");
        tmp.put(Localizations.PT_PT, "Uma pasta espessa e condutora, perfeita para encaixar entre miofibras em um músculo artificial.");
        tmp.put(Localizations.RU_RU, "Толстая, проводящая паста, идеально подходящая для установки между миофибрами в искусственной мышце.");
        tmp.put(Localizations.ZH_CN, "一种厚实的导电膏，非常适合人造肌肉中肌纤维之间的贴合。");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), tmp);

        // Parachute -----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Fallschirm");
        tmp.put(Localizations.EN_US, "Parachute");
        tmp.put(Localizations.FR_FR, "Parachute");
        tmp.put(Localizations.PT_BR, "Paraquedas");
        tmp.put(Localizations.PT_PT, "Paraquedas");
        tmp.put(Localizations.RU_RU, "Парашют");
        tmp.put(Localizations.ZH_CN, "降落伞");
        add(NuminaObjects.PARACHUTE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein einfacher wiederverwendbarer Fallschirm, der in der Luft eingesetzt und geborgen werden kann.Diamantbeschichtung");
        tmp.put(Localizations.EN_US, "A simple reusable parachute which can be deployed and recovered in midair.");
        tmp.put(Localizations.FR_FR, "Un parachute réutilisable simple pouvant être déployé et récupéré en plein vol.");
        tmp.put(Localizations.PT_BR, "Um simples pára-quedas reutilizável que pode ser instalado e recuperado no ar.");
        tmp.put(Localizations.PT_PT, "Um simples pára-quedas reutilizável que pode ser instalado e recuperado no ar.");
        tmp.put(Localizations.RU_RU, "Простой многоразовый парашют, который можно развернуть и восстановить в воздухе.");
        tmp.put(Localizations.ZH_CN, "一个简单的可重复使用的降落伞，可在半空中部署和恢复。");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), tmp);

        // Rubber Hose ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Isolierter Gummischlauch");
        tmp.put(Localizations.EN_US, "Insulated Rubber Hose");
        tmp.put(Localizations.FR_FR, "Tuyau en caoutchouc isolé");
        tmp.put(Localizations.PT_BR, "Mangueira Isolada de Borracha");
        tmp.put(Localizations.PT_PT, "Mangueira Isolada de Borracha");
        tmp.put(Localizations.RU_RU, "Изолированный резиновый шланг");
        tmp.put(Localizations.ZH_CN, "绝缘橡胶管");
        add(NuminaObjects.RUBBER_HOSE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein stark isolierter Gummischlauch, der extremer Hitze oder Kälte standhält");
        tmp.put(Localizations.EN_US, "A heavily insulated rubber hose capable of withstanding extreme heat or cold");
        tmp.put(Localizations.FR_FR, "Tuyau en caoutchouc fortement isolé capable de résister à la chaleur ou au froid extrêmes");
        tmp.put(Localizations.PT_BR, "Uma mangueira de borracha fortemente isolada capaz de suportar calor ou frio extremos");
        tmp.put(Localizations.PT_PT, "Uma mangueira de borracha fortemente isolada capaz de suportar calor ou frio extremos");
        tmp.put(Localizations.RU_RU, "Сильно изолированный резиновый шланг, способный выдерживать сильную жару или холод");
        tmp.put(Localizations.ZH_CN, "一种高度绝缘的橡胶软管，能够承受极端高温或低温");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), tmp);

        // Servo Motor ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Servomotor");
        tmp.put(Localizations.EN_US, "Servo Motor");
        tmp.put(Localizations.FR_FR, "Moteur du servo");
        tmp.put(Localizations.PT_BR, "Servomotor");
        tmp.put(Localizations.PT_PT, "Servomotor");
        tmp.put(Localizations.RU_RU, "Серводвигатель");
        tmp.put(Localizations.ZH_CN, "伺服马达");
        add(NuminaObjects.SERVO.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein spezieller Motortyp, der mit einem pulsmodulierten Signal sehr präzise Bewegungen ausführt.");
        tmp.put(Localizations.EN_US, "A special type of motor which uses a pulse-modulated signal to enact very precise movements.");
        tmp.put(Localizations.FR_FR, "Un type spécial de moteur qui utilise un signal modulé par impulsions pour exécuter des mouvements très précis.");
        tmp.put(Localizations.PT_BR, "Um tipo especial de motor que usa um sinal modulado por pulsos para executar movimentos muito precisos.");
        tmp.put(Localizations.PT_PT, "Um tipo especial de motor que usa um sinal modulado por pulsos para executar movimentos muito precisos.");
        tmp.put(Localizations.RU_RU, "Специальный тип двигателя, который использует импульсно-модулированный сигнал для принятия очень точных движений.");
        tmp.put(Localizations.ZH_CN, "一种特殊类型的电机，它使用脉冲调制信号来实现非常精确的运动。");
        addItemDescriptions(NuminaObjects.SERVO.get(), tmp);

        // Solar Panel ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Solarzelle");
        tmp.put(Localizations.EN_US, "Solar Panel");
        tmp.put(Localizations.FR_FR, "Panneau Solaire");
        tmp.put(Localizations.PT_BR, "Painel Solar");
        tmp.put(Localizations.PT_PT, "Painel Solar");
        tmp.put(Localizations.RU_RU, "Панель солнечных батарей");
        tmp.put(Localizations.ZH_CN, "太阳能板");
        add(NuminaObjects.SOLAR_PANEL.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein lichtempfindliches Gerät, das Strom von der Sonne erzeugt");
        tmp.put(Localizations.EN_US, "A light sensitive device that will generate electricity from the sun.");
        tmp.put(Localizations.FR_FR, "Dispositif sensible à la lumière qui générera de l'électricité à partir du soleil.");
        tmp.put(Localizations.PT_BR, "Um dispositivo sensível à luz que irá gerar eletricidade a partir do sol.");
        tmp.put(Localizations.PT_PT, "Um dispositivo sensível à luz que irá gerar eletricidade a partir do sol.");
        tmp.put(Localizations.RU_RU, "светочувствительное устройство, которое будет генерировать электричество от солнца.");
        tmp.put(Localizations.ZH_CN, "一种光敏设备，可以从太阳发电。");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), tmp);


        // Solenoid ------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "MagnetspuleComponents");
        tmp.put(Localizations.EN_US, "Solenoid");
        tmp.put(Localizations.FR_FR, "Solénoïde");
        tmp.put(Localizations.PT_BR, "Solenoide");
        tmp.put(Localizations.PT_PT, "Solenoide");
        tmp.put(Localizations.RU_RU, "Соленоид");
        tmp.put(Localizations.ZH_CN, "螺线管");
        add(NuminaObjects.SOLENOID.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Drähte, die um einen ferromagnetischen Kern gewickelt sind, erzeugen einen grundlegenden Elektromagneten.");
        tmp.put(Localizations.EN_US, "Wires wound around a ferromagnetic core produces a basic electromagnet.");
        tmp.put(Localizations.FR_FR, "Les fils enroulés autour d'un noyau ferromagnétique produisent un électroaimant de base.");
        tmp.put(Localizations.PT_BR, "Os fios enrolados em torno de um núcleo ferromagnético produzem um eletroímã básico.");
        tmp.put(Localizations.PT_PT, "Os fios enrolados em torno de um núcleo ferromagnético produzem um eletroímã básico.");
        tmp.put(Localizations.RU_RU, "Провода, намотанные вокруг ферромагнитного сердечника, создают основной электромагнит.");
        tmp.put(Localizations.ZH_CN, "缠绕在铁磁芯上的导线产生一个基本的电磁铁。");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), tmp);

        // Wiring --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Kabel");
        tmp.put(Localizations.EN_US, "Wiring");
        tmp.put(Localizations.FR_FR, "Câblage");
        tmp.put(Localizations.PT_BR, "Fiação");
        tmp.put(Localizations.PT_PT, "Fiação");
        tmp.put(Localizations.RU_RU, "Провод");
        tmp.put(Localizations.ZH_CN, "线材");
        add(NuminaObjects.WIRING.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Eine spezielle Art der Verkabelung mit hoher Voltzahl und Präzision, die für die empfindliche Elektronik in Power Armor notwendig ist.");
        tmp.put(Localizations.EN_US, "A special type of wiring with high voltaic capacity and precision, necessary for the sensitive electronics in power armor.");
        tmp.put(Localizations.FR_FR, "Type de câblage spécial à haute capacité voltaïque et précision, nécessaire pour les composants électroniques sensibles dans une armure de protection.");
        tmp.put(Localizations.PT_BR, "Um tipo especial de fiação com alta capacidade e precisão voltaica, necessária para a eletrônica sensível na blindagem de potência.");
        tmp.put(Localizations.PT_PT, "Um tipo especial de fiação com alta capacidade e precisão voltaica, necessária para a eletrônica sensível na blindagem de potência.");
        tmp.put(Localizations.RU_RU, "Специальный тип проводки с высокой вольтовой емкостью и точностью, необходимой для чувствительной электроники в силовой броне.");
        tmp.put(Localizations.ZH_CN, "一种特殊类型的布线，具有高电容和高精度，是动力装甲中敏感电子元件所必需的。");
        addItemDescriptions(NuminaObjects.WIRING.get(), tmp);
    }

    void addModuleCategories() {
        // None ----------------------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Keiner");
        tmp.put(Localizations.EN_US, "None");
        tmp.put(Localizations.FR_FR, "Aucune");
        tmp.put(Localizations.PT_BR, "Nenhum");
        tmp.put(Localizations.PT_PT, "Nenhum");
        tmp.put(Localizations.RU_RU, "Никакой");
        tmp.put(Localizations.ZH_CN, "没有");
        add("numina.module.category.none", tmp);

        // Debug Modules -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Debug-Module");
        tmp.put(Localizations.EN_US, "Debug Modules");
        tmp.put(Localizations.FR_FR, "Modules de débogage");
        tmp.put(Localizations.PT_BR, "Depurar módulos");
        tmp.put(Localizations.PT_PT, "Depurar módulos");
        tmp.put(Localizations.RU_RU, "Отладочные модули");
        tmp.put(Localizations.ZH_CN, "调试模块");
        add("numina.module.category.debug", tmp);

        // Armor Modules -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Debug-Module");
        tmp.put(Localizations.EN_US, "Armor Modules");
        tmp.put(Localizations.FR_FR, "Modules de débogage");
        tmp.put(Localizations.PT_BR, "Módulos de depuração");
        tmp.put(Localizations.PT_PT, "Depurar módulos");
        tmp.put(Localizations.RU_RU, "Отладочные модули");
        tmp.put(Localizations.ZH_CN, "调试模块");
        add("numina.module.category.armor", tmp);

        // Energy Storage ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energiespeicherung");
        tmp.put(Localizations.EN_US, "Energy Storage");
        tmp.put(Localizations.FR_FR, "Stockage de l’énergie");
        tmp.put(Localizations.PT_BR, "Armazenamento de Energia");
        tmp.put(Localizations.PT_PT, "Armazenamento de energia");
        tmp.put(Localizations.RU_RU, "Аккумулирование энергии");
        tmp.put(Localizations.ZH_CN, "储能");
        add("numina.module.category.energystorage", tmp);

        // Energy Generation ---------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energieerzeugung");
        tmp.put(Localizations.EN_US, "Energy Generation");
        tmp.put(Localizations.FR_FR, "Production d’énergie");
        tmp.put(Localizations.PT_BR, "Geração de Energia");
        tmp.put(Localizations.PT_PT, "Geração de Energia");
        tmp.put(Localizations.RU_RU, "Производство энергии");
        tmp.put(Localizations.ZH_CN, "能源生产");
        add("numina.module.category.energygeneration", tmp);

        // Tool ----------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Werkzeug");
        tmp.put(Localizations.EN_US, "Tool");
        tmp.put(Localizations.FR_FR, "Outil");
        tmp.put(Localizations.PT_BR, "Ferramenta");
        tmp.put(Localizations.PT_PT, "Ferramenta");
        tmp.put(Localizations.RU_RU, "Инструмент");
        tmp.put(Localizations.ZH_CN, "工具");
        add("numina.module.category.tool", tmp);

        // Weapon --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Waffe");
        tmp.put(Localizations.EN_US, "Weapon");
        tmp.put(Localizations.FR_FR, "Arme");
        tmp.put(Localizations.PT_BR, "Arma");
        tmp.put(Localizations.PT_PT, "Arma");
        tmp.put(Localizations.RU_RU, "Weapon");
        tmp.put(Localizations.ZH_CN, "武器");
        add("numina.module.category.weapon", tmp);

        // Movement ------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Bewegung");
        tmp.put(Localizations.EN_US, "Movement");
        tmp.put(Localizations.FR_FR, "Mouvement");
        tmp.put(Localizations.PT_BR, "Movimento");
        tmp.put(Localizations.PT_PT, "Movimento");
        tmp.put(Localizations.RU_RU, "Движение");
        tmp.put(Localizations.ZH_CN, "运动");
        add("numina.module.category.movement", tmp);

        // Cosmetic ------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Kosmetikum");
        tmp.put(Localizations.EN_US, "Cosmetic");
        tmp.put(Localizations.FR_FR, "Cosmétique");
        tmp.put(Localizations.PT_BR, "Cosmético");
        tmp.put(Localizations.PT_PT, "Cosméticos");
        tmp.put(Localizations.RU_RU, "Косметический");
        tmp.put(Localizations.ZH_CN, "化妆品");
        add("numina.module.category.cosmetic", tmp);

        // Vision --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Vision");
        tmp.put(Localizations.EN_US, "Vision");
        tmp.put(Localizations.FR_FR, "Vision");
        tmp.put(Localizations.PT_BR, "Visão");
        tmp.put(Localizations.PT_PT, "Visão");
        tmp.put(Localizations.RU_RU, "Зрение");
        tmp.put(Localizations.ZH_CN, "视觉");
        add("numina.module.category.vision", tmp);

        // Environment ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Umwelt");
        tmp.put(Localizations.EN_US, "Environment");
        tmp.put(Localizations.FR_FR, "Environnement");
        tmp.put(Localizations.PT_BR, "Ambiente");
        tmp.put(Localizations.PT_PT, "Meio Ambiente");
        tmp.put(Localizations.RU_RU, "Окружающая среда");
        tmp.put(Localizations.ZH_CN, "环境");
        add("numina.module.category.environment", tmp);

        // Special -------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Spezial");
        tmp.put(Localizations.EN_US, "Special");
        tmp.put(Localizations.FR_FR, "Spéciale");
        tmp.put(Localizations.PT_BR, "Especial");
        tmp.put(Localizations.PT_PT, "Especial");
        tmp.put(Localizations.RU_RU, "Специальный");
        tmp.put(Localizations.ZH_CN, "特殊");
        add("numina.module.category.special", tmp);

        // Mining Enhancement --------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Verbesserung des Bergbaus");
        tmp.put(Localizations.EN_US, "Mining Enhancement");
        tmp.put(Localizations.FR_FR, "Amélioration de l’exploitation minière");
        tmp.put(Localizations.PT_BR, "Aprimoramento da mineração");
        tmp.put(Localizations.PT_PT, "Aprimoramento de mineração");
        tmp.put(Localizations.RU_RU, "Улучшение добычи полезных ископаемых");
        tmp.put(Localizations.ZH_CN, "挖矿增强");
        add("numina.module.category.miningenhancement", tmp);

        // Mining Enchantment --------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Bergbau-Verzauberung");
        tmp.put(Localizations.EN_US, "Mining Enchantment");
        tmp.put(Localizations.FR_FR, "Enchantement minier");
        tmp.put(Localizations.PT_BR, "Encantamento Mineiro");
        tmp.put(Localizations.PT_PT, "Encantamento Mineiro");
        tmp.put(Localizations.RU_RU, "Чары добычи полезных ископаемых");
        tmp.put(Localizations.ZH_CN, "采矿附魔");
        add("numina.module.category.miningenchantment", tmp);
    }
}
