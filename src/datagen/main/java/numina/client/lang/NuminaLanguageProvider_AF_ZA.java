package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

public class NuminaLanguageProvider_AF_ZA extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_AF_ZA(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "af_za");
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
        add("gui.numina.chargingbase", "Laai basis");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "Koste die toegeruste items van 'n entiteit");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "Energie");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "Wissel gesigsveld reg");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "Gesigsveldoplossing geaktiveer");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "Gesigsveld herstel gedeaktiveer");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "Installeer");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC, "Installeer module in geselekteerde modulêre item terwyl speler in kreatiewe modus is");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "Installeer alles");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "Installeer alle topvlakversoenbare modules in geselekteerde modulêre item terwyl speler in kreatiewe modus is");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "Pantserstaander");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "Pantserstaander");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy", "Maksimum energie");
        add("module.tradeoff.maxTransfer", "Maksimum oordrag per regmerkie");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "Laai basis");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "Druk SHIFT vir meer inligting.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "Verander modusse: Druk en hou die nommer van die hotbar-gleuf waarin die Power Fist is, ingedruk.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "Energie: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "Geen geïnstalleerde modules nie! Hierdie item is nutteloos totdat u 'n paar modules by 'n Tinker Table voeg.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES, "Geïnstalleerde modules:");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "Modus: ");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "Basiese battery");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "Integreer 'n battery sodat die item energie kan stoor.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "Gevorderde battery");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "Integreer 'n battery sodat die item energie kan stoor.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "Elite Battery");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "Integreer 'n die mees gevorderde battery om 'n uitgebreide hoeveelheid energie te stoor.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "Uiteindelike battery");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "Eksperimentele hoë-end kragbergingstoestel, nou met byna geen kans om te ontplof nie (dink ons). Ondanks nare gerugte is dit nie gebaseer op gesteelde uitheemse tegnologie nie.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "Kunsmatige spiere");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "'N Elektriese, kunsmatige spier, met minder beweging as menslike spiere, maar orde van grootte meer krag.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "Koolstof myofiber");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "'N Klein bondel koolstofvesels, verfyn vir gebruik in kunsmatige spiere.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "Rekenaar chip");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "'N Opgegradeerde beheerkring wat 'n SVE bevat wat meer gevorderde berekeninge kan doen.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "Beheer stroombaan");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "'N Eenvoudige netwerkbare mikrobeheerder vir die koördinering van 'n individuele komponent.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "Force Field Emitter");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "'N Gevorderde toestel wat elektromagnetiese en gravitasievelde in 'n gebied direk manipuleer.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "Sweefvleuel");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "'N Liggewig aërodinamiese vleuel met 'n elektromagneet vir vinnige ontplooiing en terugtrekking.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "Ioon stoot");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "In wese 'n miniatuur deeltjieversneller. Versnel ione tot naby ligspoed om stoot te produseer.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "Hologram Emitter");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "'N Veelkleurige laserskikking wat die voorkoms van iets goedkoop kan verander.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "Magneet");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "'N Metaaltoestel wat 'n magnetiese veld genereer wat items na die speler trek.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "Myofiber Gel");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "'N Dik, geleidende pasta, ideaal om tussen myofibers in 'n kunsmatige spier te pas.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "Valskerm");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "'N Eenvoudige herbruikbare valskerm wat in die lug ontplooi en herwin kan word.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "Geïsoleerde rubberslang");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "'N Swaar geïsoleerde rubberslang wat uiterste hitte of koue kan weerstaan");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "Servo Motor");
        addItemDescriptions(NuminaObjects.SERVO.get(), "'N Spesiale tipe motor wat 'n polsgemoduleerde sein gebruik om baie presiese bewegings uit te voer.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "Sonpaneel");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "'n Ligsensitiewe toestel wat elektrisiteit van die son sal opwek.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "Solenoïde");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "Drade wat om 'n ferromagnetiese kern gewikkel is, produseer 'n basiese elektromagneet.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "Bedrading");
        addItemDescriptions(NuminaObjects.WIRING.get(), "'N Spesiale soort bedrading met 'n hoë voltaïese kapasiteit en presisie, wat nodig is vir die sensitiewe elektronika in kragpantser.");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "Pantser modules");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "Kosmetiese");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "Ontfout modules");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "Energie-opwekking");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "Energie berging");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "Omgewing");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "Mynbou betowering");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(),  "Mynbouverbetering");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "Beweging");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "Geen");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "Spesiale");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "Instrument");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "Visie");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "Wapen");
    }
}