package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

public class NuminaLanguageProvider_FR_FR extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_FR_FR(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "fr_fr");
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
        add("gui.numina.chargingbase", "Base de chargement");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Charge les objets équipés d’une entité");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "Énergie");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "Basculer le champ de vision fixe");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "Correction du champ de vision activée");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "Correction du champ de vision désactivée");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "Installer");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC, "Installe le module dans l’élément modulaire sélectionné lorsque la joueuse est en mode créatif");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Tout installer");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Installe tous les modules compatibles de niveau supérieur dans l’objet modulaire sélectionné lorsque le joueur est en mode créatif");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "Support d’armure");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "Support d’armure");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "Énergie maximale");
        add("module.tradeoff.maxTransfer", "Transfert maximum par tick");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "Base de chargement");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Appuyez sur MAJ pour plus d’informations.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Changer de mode : Appuyez longuement sur le numéro de l’emplacement de la barre de raccourcis dans lequel se trouve le Power Fist.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "Énergie: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "Aucun module installé ! Cet objet est inutile tant que vous n’avez pas ajouté des modules à une table de bricolage.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES, "Modules installés: ");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "Mode: ");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "Batterie de base");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "Intégrez une batterie pour permettre à l’article de stocker de l’énergie.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "Batterie avancée");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "Intégrez une batterie pour permettre à l’article de stocker de l’énergie.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "Batterie Elite");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Intégrez une batterie la plus avancée pour stocker une grande quantité d’énergie.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "Batterie ultime");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Dispositif expérimental de stockage d’énergie haut de gamme, maintenant presque aucune chance d’exploser (nous pensons). Malgré les mauvaises rumeurs, il n’est pas basé sur une technologie extraterrestre volée.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Muscle artificiel");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Un muscle électrique artificiel, avec moins d’amplitude de mouvement que le muscle humain, mais beaucoup plus de force.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "Myofibre de carbone");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "Un petit faisceau de fibres de carbone, raffiné pour être utilisé dans les muscles artificiels.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "Puce d’ordinateur");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "Un circuit de contrôle amélioré qui contient un processeur capable d’effectuer des calculs plus avancés.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "Circuit de contrôle");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "Un microcontrôleur simple pouvant être mis en réseau pour la coordination d’un composant individuel.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "Émettrice de champ de force");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "Un appareil avancé qui manipule directement les champs électromagnétiques et gravitationnels dans une zone.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Aile de planeur");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "Une aile aérodynamique légère avec un électroaimant pour un déploiement et une rétraction rapides.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Ionique Thuster");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "Essentiellement un accélérateur de particules miniature. Accélère les ions à une vitesse proche de celle de la lumière pour produire une poussée.");

        // Laser Emitter -------------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "Émetteur d’hologramme");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "Un réseau laser multicolore qui peut modifier à moindre coût l’apparence de quelque chose.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Aimant");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "Un dispositif métallique qui génère un champ magnétique qui attire les objets vers la joueuse.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Gel de myofibre");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "Une pâte épaisse et conductrice, parfaite pour s’insérer entre les myofibres d’un muscle artificiel.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Parachute");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "Un simple parachute réutilisable qui peut être déployé et récupéré dans les airs.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Tuyau en caoutchouc isolé");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "Un tuyau en caoutchouc fortement isolé capable de résister à une chaleur ou à un froid extrême");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Servomoteur");
        addItemDescriptions(NuminaObjects.SERVO.get(), "Un type spécial de moteur qui utilise un signal modulé par impulsions pour effectuer des mouvements très précis.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Panneau solaire");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(),"Un appareil sensible à la lumière qui produira de l’électricité à partir du soleil.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Solénoïde");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Des fils enroulés autour d’un noyau ferromagnétique produisent un électroaimant de base.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "Câblage");
        addItemDescriptions(NuminaObjects.WIRING.get(), "Un type spécial de câblage avec une capacité voltaïque et une précision élevées, nécessaire pour l’électronique sensible dans l’armure assistée.");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "Modules d’armure");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "Cosmétique");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "Modules de débogage");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "Production d’énergie");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "Stockage de l’énergie");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "Environnement");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "Enchantement minier");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "Amélioration de l’exploitation minière");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "Mouvement");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "Aucune");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "Spéciale");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "Outil");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "Vision");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "Arme");
    }
}
