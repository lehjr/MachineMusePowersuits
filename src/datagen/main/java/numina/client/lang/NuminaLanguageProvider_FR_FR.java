package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;

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
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Charge les objets \u00e9quip\u00e9s d\u2019une entit\u00e9");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "\u00c9nergie");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "Basculer le champ de vision fixe");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "Correction du champ de vision activ\u00e9e");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "Correction du champ de vision d\u00e9sactiv\u00e9e");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "Installer");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC, "Installe le module dans l\u2019\u00e9l\u00e9ment modulaire s\u00e9lectionn\u00e9 lorsque la joueuse est en mode cr\u00e9atif");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Tout installer");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Installe tous les modules compatibles de niveau sup\u00e9rieur dans l\u2019objet modulaire s\u00e9lectionn\u00e9 lorsque le joueur est en mode cr\u00e9atif");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "Support d\u2019armure");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "Support d\u2019armure");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "\u00c9nergie maximale");
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
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Appuyez sur MAJ pour plus d\u2019informations.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Changer de mode : Appuyez longuement sur le num\u00e9ro de l\u2019emplacement de la barre de raccourcis dans lequel se trouve le Power Fist.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "\u00c9nergie: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "Aucun module install\u00e9 ! Cet objet est inutile tant que vous n\u2019avez pas ajout\u00e9 des modules \u00e0 une table de bricolage.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES, "Modules installés: ");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "Mode: ");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "Batterie de base");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "Int\u00e9grez une batterie pour permettre \u00e0 l\u2019article de stocker de l\u2019\u00e9nergie.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "Batterie avanc\u00e9e");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "Int\u00e9grez une batterie pour permettre \u00e0 l\u2019article de stocker de l\u2019\u00e9nergie.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "Batterie Elite");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Int\u00e9grez une batterie la plus avanc\u00e9e pour stocker une grande quantit\u00e9 d\u2019\u00e9nergie.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "Batterie ultime");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Dispositif exp\u00e9rimental de stockage d\u2019\u00e9nergie haut de gamme, maintenant presque aucune chance d\u2019exploser (nous pensons). Malgr\u00e9 les mauvaises rumeurs, il n\u2019est pas bas\u00e9 sur une technologie extraterrestre vol\u00e9e.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Muscle artificiel");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Un muscle \u00e9lectrique artificiel, avec moins d\u2019amplitude de mouvement que le muscle humain, mais beaucoup plus de force.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "Myofibre de carbone");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "Un petit faisceau de fibres de carbone, raffin\u00e9 pour \u00eatre utilis\u00e9 dans les muscles artificiels.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "Puce d\u2019ordinateur");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "Un circuit de contr\u00f4le am\u00e9lior\u00e9 qui contient un processeur capable d\u2019effectuer des calculs plus avanc\u00e9s.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "Circuit de contr\u00f4le");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "Un microcontr\u00f4leur simple pouvant \u00eatre mis en r\u00e9seau pour la coordination d\u2019un composant individuel.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "\u00c9mettrice de champ de force");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "Un appareil avanc\u00e9 qui manipule directement les champs \u00e9lectromagn\u00e9tiques et gravitationnels dans une zone.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Aile de planeur");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "Une aile a\u00e9rodynamique l\u00e9g\u00e8re avec un \u00e9lectroaimant pour un d\u00e9ploiement et une r\u00e9traction rapides.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Ionique Thuster");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "Essentiellement un accélérateur de particules miniature. Accélère les ions à une vitesse proche de celle de la lumière pour produire une poussée.");

        // Laser Emitter -------------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "\u00c9metteur d\u2019hologramme");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "Un r\u00e9seau laser multicolore qui peut modifier \u00e0 moindre co\u00fbt l\u2019apparence de quelque chose.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Aimant");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "Un dispositif m\u00e9tallique qui g\u00e9n\u00e8re un champ magn\u00e9tique qui attire les objets vers la joueuse.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Gel de myofibre");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "Une p\u00e2te \u00e9paisse et conductrice, parfaite pour s\u2019ins\u00e9rer entre les myofibres d\u2019un muscle artificiel.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Parachute");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "Un simple parachute r\u00e9utilisable qui peut \u00eatre d\u00e9ploy\u00e9 et r\u00e9cup\u00e9r\u00e9 dans les airs.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Tuyau en caoutchouc isol\u00e9");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "Un tuyau en caoutchouc fortement isol\u00e9 capable de r\u00e9sister \u00e0 une chaleur ou \u00e0 un froid extr\u00eame");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Servomoteur");
        addItemDescriptions(NuminaObjects.SERVO.get(), "Un type sp\u00e9cial de moteur qui utilise un signal modul\u00e9 par impulsions pour effectuer des mouvements tr\u00e8s pr\u00e9cis.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Panneau solaire");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(),"Un appareil sensible \u00e0 la lumi\u00e8re qui produira de l\u2019\u00e9lectricit\u00e9 \u00e0 partir du soleil.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Sol\u00e9no\u00efde");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Des fils enroul\u00e9s autour d\u2019un noyau ferromagn\u00e9tique produisent un \u00e9lectroaimant de base.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "C\u00e2blage");
        addItemDescriptions(NuminaObjects.WIRING.get(), "Un type sp\u00e9cial de c\u00e2blage avec une capacit\u00e9 volta\u00efque et une pr\u00e9cision \u00e9lev\u00e9es, n\u00e9cessaire pour l\u2019\u00e9lectronique sensible dans l\u2019armure assist\u00e9e.");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "Modules d\u2019armure");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "Cosm\u00e9tique");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "Modules de d\u00e9bogage");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "Production d\u2019\u00e9nergie");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "Stockage de l\u2019\u00e9nergie");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "Environnement");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "Enchantement minier");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "Am\u00e9lioration de l\u2019exploitation mini\u00e8re");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "Mouvement");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "Aucune");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "Sp\u00e9ciale");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "Outil");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "Vision");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "Arme");
    }

    @Override
    public void addItemDescriptions(Item key, String description) {
        add(key.getDescriptionId() + ".desc", description);
    }
}
