package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

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
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Berechnet die ausgerüsteten Gegenstände eines Unternehmens");

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
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC,"Installiert das Modul in einem ausgewählten modularen Element, während sich der Spieler im Kreativmodus befindet");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Alle installieren");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Installiert alle Top-Tier-kompatiblen Module in einem ausgewählten modularen Element, während sich der Player im Kreativmodus befindet");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "Rüstungsständer");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "Rüstungsständer");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "Maximale Energie");
        add("module.tradeoff.maxTransfer", "Maximale Übertragung pro Tick");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "Ladestation");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Drücken Sie die UMSCHALTTASTE, um weitere Informationen zu erhalten.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Modus ändern: Halte die Nummer des Hotbar-Steckplatzes gedrückt, in dem sich die Power Fist befindet.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "Energie: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "Keine installierten Module! Dieser Gegenstand ist nutzlos, bis Sie einige Module an einem Tinker-Tisch hinzufügen.");

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
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Integrieren Sie eine der fortschrittlichsten Batterien, um eine große Menge an Energie zu speichern.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "Ultimative Batterie");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Experimenteller High-End-Stromspeicher, der jetzt fast keine Chance mehr hat, zu explodieren (wie wir denken). Trotz böser Gerüchte basiert es nicht auf gestohlener Alien-Technologie.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Künstlicher Muskel");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "An electrical, artificial muscle, with less range of movement than human muscle but orders of magnitude more strength.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "Carbon-Myofaser");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "Ein kleines Bündel aus Carbonfasern, veredelt für den Einsatz in künstlichen Muskeln.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "Computer-Chip");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "Ein verbesserter Steuerstromkreis, der eine CPU enthält, die in der Lage ist, erweiterte Berechnungen durchzuführen.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "Regelkreis");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "Ein einfacher vernetzbarer Mikrocontroller zur Abstimmung einer einzelnen Komponente.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "Kraftfeld-Emitter");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "Ein fortschrittliches Gerät, das elektromagnetische Felder und Gravitationsfelder in einem Gebiet direkt manipuliert.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Segelflugzeug-Flügel");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "Ein leichter aerodynamischer Flügel mit einem Elektromagneten für schnelles Aus- und Einfahren.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Ionen-Triebwerk");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "Im Wesentlichen ein Miniatur-Teilchenbeschleuniger. Beschleunigt Ionen auf nahezu Lichtgeschwindigkeit, um Schub zu erzeugen.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "Hologramm-Emitter");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "Ein mehrfarbiges Laser-Array, das das Aussehen von etwas kostengünstig verändern kann.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Magnet");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "Ein metallisches Gerät, das ein Magnetfeld erzeugt, das Gegenstände in Richtung des Spielers zieht.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Myofaser-Gel");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "Eine dicke, leitfähige Paste, die sich perfekt für die Anpassung zwischen Myofasern in einem künstlichen Muskel eignet.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Fallschirm");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "Ein einfacher, wiederverwendbarer Fallschirm, der in der Luft entfaltet und geborgen werden kann.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Isolierter Gummischlauch");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "Ein stark isolierter Gummischlauch, der extremer Hitze oder Kälte standhält");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Servomotor");
        addItemDescriptions(NuminaObjects.SERVO.get(), "Ein spezieller Motortyp, der ein pulsmoduliertes Signal verwendet, um sehr präzise Bewegungen auszuführen.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Sonnenkollektor");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "Ein lichtempfindliches Gerät, das Strom aus der Sonne erzeugt.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Spule");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Drähte, die um einen ferromagnetischen Kern gewickelt sind, erzeugen einen basischen Elektromagneten.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "Verdrahtung");
        addItemDescriptions(NuminaObjects.WIRING.get(), "Eine spezielle Art der Verkabelung mit hoher Voltaikkapazität und Präzision, die für die empfindliche Elektronik in Power Armors erforderlich ist.");
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
}
