package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;

public class NuminaLanguageProvider_DE_DE extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_DE_DE(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "de_de");
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
        add("gui.numina.chargingbase", "Ladestation");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Berechnet die ausger\u00fcsteten Gegenst\u00e4nde eines Unternehmens");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "Energie");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "Korrektur des Sichtfelds");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "Sichtfeldkorrektur aktiviert");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "Sichtfeld-Fix deaktiviert");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "Installieren");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,"Installiert das Modul in einem ausgew\u00e4hlten modularen Element, w\u00e4hrend sich der Spieler im Kreativmodus befindet");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Alle installieren");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Installiert alle Top-Tier-kompatiblen Module in einem ausgew\u00e4hlten modularen Element, w\u00e4hrend sich der Player im Kreativmodus befindet");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "R\u00fcstungsst\u00e4nder");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "R\u00fcstungsst\u00e4nder");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "Maximale Energie");
        add("module.tradeoff.maxTransfer", "Maximale \u00dcbertragung pro Tick");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "Ladestation");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Dr\u00fccken Sie die UMSCHALTTASTE, um weitere Informationen zu erhalten.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Modus \u00e4ndern: Halte die Nummer des Hotbar-Steckplatzes gedr\u00fcckt, in dem sich die Power Fist befindet.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "Energie: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "Keine installierten Module! Dieser Gegenstand ist nutzlos, bis Sie einige Module an einem Tinker-Tisch hinzuf\u00fcgen.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "Installierte Module:");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "Modus: ");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "Basis-Batterie");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "Integrieren Sie eine Batterie, damit der Gegenstand Energie speichern kann.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "Erweiterter Akku");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "Integrieren Sie eine Batterie, damit der Gegenstand Energie speichern kann.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "Elite-Akku");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Integrieren Sie eine der fortschrittlichsten Batterien, um eine gro\u00dfe Menge an Energie zu speichern.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "Ultimative Batterie");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Experimenteller High-End-Stromspeicher, der jetzt fast keine Chance mehr hat, zu explodieren (wie wir denken). Trotz b\u00f6ser Ger\u00fcchte basiert es nicht auf gestohlener Alien-Technologie.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "K\u00fcnstlicher Muskel");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "An electrical, artificial muscle, with less range of movement than human muscle but orders of magnitude more strength.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "Carbon-Myofaser");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "Ein kleines B\u00fcndel aus Carbonfasern, veredelt f\u00fcr den Einsatz in k\u00fcnstlichen Muskeln.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "Computer-Chip");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "Ein verbesserter Steuerstromkreis, der eine CPU enth\u00e4lt, die in der Lage ist, erweiterte Berechnungen durchzuf\u00fchren.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "Regelkreis");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "Ein einfacher vernetzbarer Mikrocontroller zur Abstimmung einer einzelnen Komponente.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "Kraftfeld-Emitter");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "Ein fortschrittliches Ger\u00e4t, das elektromagnetische Felder und Gravitationsfelder in einem Gebiet direkt manipuliert.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Segelflugzeug-Fl\u00fcgel");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "Ein leichter aerodynamischer Fl\u00fcgel mit einem Elektromagneten f\u00fcr schnelles Aus- und Einfahren.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Ionen-Triebwerk");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "Im Wesentlichen ein Miniatur-Teilchenbeschleuniger. Beschleunigt Ionen auf nahezu Lichtgeschwindigkeit, um Schub zu erzeugen.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "Hologramm-Emitter");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "Ein mehrfarbiges Laser-Array, das das Aussehen von etwas kosteng\u00fcnstig ver\u00e4ndern kann.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Magnet");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "Ein metallisches Ger\u00e4t, das ein Magnetfeld erzeugt, das Gegenst\u00e4nde in Richtung des Spielers zieht.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Myofaser-Gel");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "Eine dicke, leitf\u00e4hige Paste, die sich perfekt f\u00fcr die Anpassung zwischen Myofasern in einem k\u00fcnstlichen Muskel eignet.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Fallschirm");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "Ein einfacher, wiederverwendbarer Fallschirm, der in der Luft entfaltet und geborgen werden kann.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Isolierter Gummischlauch");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "Ein stark isolierter Gummischlauch, der extremer Hitze oder K\u00e4lte standh\u00e4lt");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Servomotor");
        addItemDescriptions(NuminaObjects.SERVO.get(), "Ein spezieller Motortyp, der ein pulsmoduliertes Signal verwendet, um sehr pr\u00e4zise Bewegungen auszuf\u00fchren.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Sonnenkollektor");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "Ein lichtempfindliches Ger\u00e4t, das Strom aus der Sonne erzeugt.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Spule");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Dr\u00e4hte, die um einen ferromagnetischen Kern gewickelt sind, erzeugen einen basischen Elektromagneten.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "Verdrahtung");
        addItemDescriptions(NuminaObjects.WIRING.get(), "Eine spezielle Art der Verkabelung mit hoher Voltaikkapazit\u00e4t und Pr\u00e4zision, die f\u00fcr die empfindliche Elektronik in Power Armors erforderlich ist.");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "Panzerungs-Module");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "Kosmetikum");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "Debuggen von Modulen");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "Energieerzeugung");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "Energiespeicherung");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "Umwelt");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "Bergbau-Verzauberung");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "Verbesserung des Bergbaus");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "Bewegung");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "Nichts");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "Spezial");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "Werkzeug");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "Vision");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "Waffe");
    }

    @Override
    public void addItemDescriptions(Item key, String description) {
        add(key.getDescriptionId() + ".desc", description);
    }
}
