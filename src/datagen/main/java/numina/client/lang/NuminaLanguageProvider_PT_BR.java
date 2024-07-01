package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

public class NuminaLanguageProvider_PT_BR extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_PT_BR(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "pt_br");
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
    {add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "Base de Carregamento");
add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "Suporte de Blindagem");
add("gui.numina.chargingbase", "Base de Carregamento");
add(NuminaConstants.GUI_CREATIVE_INSTALL"Instalar");
add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Instalar tudo");
add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Instala todos os módulos compat\u00edveis de n\u00edvel superior no item modular selecionado enquanto o jogador est\u00e1 no modo criativo");
add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,"Instala o m\u00f3dulo no item modular selecionado enquanto o jogador est\u00e1 no modo criativo");
add(NuminaObjects.ARMOR_STAND_ITEM.get(), "Suporte de Blindagem");



add("key.numina.fovfixtoggle", "Alternar corre\u00e7\u00e3o de campo de vis\u00e3o", add("message.numina.fovfixtoggle.disabled", "Corre\u00e7\u00e3o de campo de vis\u00e3o desabilitada");
add("message.numina.fovfixtoggle.enabled", "Corre\u00e7\u00e3o de campo de vis\u00e3o habilitada");
add("module.tradeoff.maxEnergy",  "Energia M\u00e1xima");
add("module.tradeoff.maxTransfer", :"Transfer\u00eancia m\u00e1xima por tick");
add("numina.energy", "Energia");

add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Alterar modos: Pressione e mantenha pressionado o n\u00famero do slot da barra de atalho em que o Power Fist est\u00e1.");
add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Cobra os itens equipados de uma entidade");
add(NuminaConstants.TOOLTIP_ENERGY, "Energia: ");
add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "M\u00f3dulos instalados:", add(NuminaConstants.TOOLTIP_MODE, "Modo: ");
add(NuminaConstants.TOOLTIP_NO_MODULES, "Nenhum m\u00f3dulo instalado! Este item \u00e9 in\u00fatil at\u00e9 que voc\u00ea adicione alguns m\u00f3dulos em uma tabela Tinker.");
add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Pressione SHIFT para obter mais informa\u00e7\u00f5es."}
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




        /*
    add(NuminaObjects.ADVANCED_BATTERY.get(), Bateria avan\u00e7ada");
addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "Integre uma bateria para permitir que o item armazene energia.");
add(NuminaObjects.BASIC_BATTERY.get(), "Bateria B\u00e1sica");
addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "Integre uma bateria para permitir que o item armazene energia.");
add(NuminaObjects.ELITE_BATTERY.get(), "Bateria Elite");
addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Integre uma bateria a mais avan\u00e7ada para armazenar uma grande quantidade de energia.");
add(NuminaObjects.ULTIMATE_BATTERY.get(), "Bateria definitiva");
addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Dispositivo experimental de armazenamento de energia de ponta, agora com quase nenhuma chance de explodir (pensamos). Apesar dos rumores desagrad\u00e1veis, ele n\u00e3o \u00e9 baseado em tecnologia alien\u00edgena roubada.");


add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Músculo Artificial");
addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Um m\u00fasculo el\u00e9trico, artificial, com menos amplitude de movimento que o m\u00fasculo humano, mas ordens de grandeza mais for\u00e7a.");
add(NuminaObjects.CARBON_MYOFIBER.get(), "Mi\u00f3fibra de Carbono");
addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "Um pequeno feixe de fibras de carbono, refinado para uso em m\u00fasculos artificiais.");
add(NuminaObjects.COMPUTER_CHIP.get(), "Chip de Computador","item.numina.component_computer_chip.desc":"Um circuito de controle atualizado que cont\u00e9m uma CPU que \u00e9 capaz de c\u00e1lculos mais avan\u00e7ados.");
add(NuminaObjects.CONTROL_CIRCUIT.get(), "Circuito de Controle");
addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), Um microcontrolador simples em rede para coordenar um componente individual.","item.numina.component_field_emitter":"Emissor de Campo de For\u00e7a");
addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "Um dispositivo avan\u00e7ado que manipula diretamente campos eletromagn\u00e9ticos e gravitacionais em uma \u00e1rea.");

 */
        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Asa de planador");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "Uma asa aerodinâmica leve com um eletroímã para rápida implantação e retração.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Propulsor de íons");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "Essencialmente um acelerador de partículas em miniatura. Acelera íons à velocidade próxima da luz para produzir empuxo.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "Emissor de holograma");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "Uma matriz laser multicolorida que pode alterar baratamente a aparência de algo.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Ímã");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "Um dispositivo metálico que gera um campo magnético que puxa itens em direção ao jogador.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Gel de Miofibra");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "Uma pasta espessa e condutora, perfeita para encaixar entre miofibras em um músculo artificial.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Pára-quedas");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "Um simples paraquedas reutilizável que pode ser implantado e recuperado no ar.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Mangueira de borracha isolada");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "Uma mangueira de borracha fortemente isolada capaz de suportar calor ou frio extremos");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Servo Motor");
        addItemDescriptions(NuminaObjects.SERVO.get(), "Um tipo especial de motor que usa um sinal modulado por pulso para executar movimentos muito precisos.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Painel solar");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "Um dispositivo sensível à luz que irá gerar eletricidade a partir do sol.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Solenóide");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Fios enrolados em torno de um núcleo ferromagnético produzem um eletroímã básico.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "Cablagem");
        addItemDescriptions(NuminaObjects.WIRING.get(), "Um tipo especial de fiação com alta capacidade voltaica e precisão, necessária para a eletrônica sensível em blindagem de potência.");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "Módulos de Blindagem");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "Cosmético");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "Módulos de depuración");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "Geração de Energia");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "Armazenamento de Energia");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "Ambiente");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "Encantamento Mineiro");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "Aprimoramento da mineração");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "Movimento");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "Nenhum");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "Especial");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "Ferramenta");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "Visão");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "Arma");
    }
}
