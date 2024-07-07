package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

public class NuminaLanguageProvider_PT_PT extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_PT_PT(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "pt_pt");
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
        add("gui.numina.chargingbase", "Base de carregamento");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Cobra os itens equipados de uma entidade");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "Energia");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "Alternar correção de campo de visão");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "Correção do campo de visão ativada");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "Correção do campo de visão desativada");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "Instalar");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC, "Instala o módulo no item modular selecionado enquanto o player está no modo criativo");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Instalar tudo");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Instala todos os módulos compatíveis de nível superior no item modular selecionado enquanto o player está no modo criativo");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "Suporte de armadura");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "Suporte de armadura");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "Energia Máxima");
        add("module.tradeoff.maxTransfer", "Transferência máxima por tick");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "Base de carregamento");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Pressione SHIFT para obter mais informações.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Alterar modos: prima sem soltar o número da ranhura da barra de atalho em que se encontra o Power Fist.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "Energia: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "Sem módulos instalados! Este item é inútil até que você adicione alguns módulos em uma Tinker Table.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES, "Módulos instalados:");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "Modo: ");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "Bateria básica");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "Integre uma bateria para permitir que o item armazene energia.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "Bateria Avançada");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "Integre uma bateria para permitir que o item armazene energia.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "Bateria Elite");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Integre uma das baterias mais avançadas para armazenar uma grande quantidade de energia.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "Bateria Ultimate");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Dispositivo experimental de armazenamento de energia de ponta, agora com quase nenhuma chance de explodir (pensamos). Apesar de rumores desagradáveis, não é baseado em tecnologia alienígena roubada.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Músculo Artificial");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Um músculo elétrico, artificial, com menor amplitude de movimento do que o músculo humano, mas ordens de magnitude mais força.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "Miofibra de Carbono");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "Um pequeno feixe de fibras de carbono, refinado para uso em músculos artificiais.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "Chip de computador");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "Um circuito de controle atualizado que contém uma CPU capaz de cálculos mais avançados.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "Circuito de Controlo");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "Um microcontrolador simples e em rede para coordenar um componente individual.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "Emissor de campo de força");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "Um dispositivo avançado que manipula diretamente campos eletromagnéticos e gravitacionais em uma área.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Asa planadora");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "Uma asa aerodinâmica leve com um eletroímã para rápida implantação e retração.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Propulsor de iões");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "Essencialmente um acelerador de partículas em miniatura. Acelera os iões a uma velocidade próxima da luz para produzir impulso.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "Emissor de holograma");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "Uma matriz de laser multicolorida que pode alterar a aparência de algo.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Íman");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "Um dispositivo metálico que gera um campo magnético que puxa itens em direção ao jogador.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Gel de Miofibra");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "Uma pasta espessa e condutora, perfeita para encaixar entre miofibras num músculo artificial.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Para-quedas");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "Um para-quedas reutilizável simples que pode ser implantado e recuperado no ar.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Mangueira de borracha isolada");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "Uma mangueira de borracha altamente isolada capaz de suportar calor ou frio extremos");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Servo Motor");
        addItemDescriptions(NuminaObjects.SERVO.get(), "Um tipo especial de motor que usa um sinal modulado por pulso para executar movimentos muito precisos.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Painel Solar");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "Um dispositivo sensível à luz que irá gerar eletricidade a partir do sol.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Solenoide");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Fios enrolados em torno de um núcleo ferromagnético produzem um eletroímã básico.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "Cablagem");
        addItemDescriptions(NuminaObjects.WIRING.get(), "Um tipo especial de fiação com alta capacidade voltaica e precisão, necessária para a eletrônica sensível na armadura de potência.");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "Módulos de armadura");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "Cosméticos");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "Depurar módulos");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "Geração de Energia");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "Armazenamento de energia");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "Meio Ambiente");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "Encantamento Mineiro");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "Aprimoramento de mineração");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "Movimento");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "Nenhum");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "Especial");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "Tool");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "Visão");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "Arma");
    }
}
