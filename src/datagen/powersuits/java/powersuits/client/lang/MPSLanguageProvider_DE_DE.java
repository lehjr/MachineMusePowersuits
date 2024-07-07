package powersuits.client.lang;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSItems;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.DataGenerator;

public class MPSLanguageProvider_DE_DE extends AbstractLangageProviderMPS {
    public MPSLanguageProvider_DE_DE(DataGenerator output) {
        super(output, MPSConstants.MOD_ID, "de_de");
    }
    @Override
    public void addItemGroup() {
        add("itemGroup." + MPSConstants.MOD_ID, "Modular Powersuits");
    }

    /*

  "module.tradeoff.maxHeat": "Maximale Wärme",
  "javaModel.legs.partName": "Beine",
 "module.tradeoff.blinkDriveRange": "Laufwerksbereich blinken",
 "javaModel.citizenjoe_armorskin.specName": "Bürger Joe Rüstung Haut",
 "keybinding.powersuits.cycleToolBackward": "Cycle Tool Backward (MPS)",
  "module.tradeoff.armorPoints": "Rüstungspunkte",
  "model.mps_boots.boots2.partName": "Left Boot",
  "itemGroup.powersuits": "Modular Powersuits",




    addPowerfistPart("palm.thumb1", "Daumen 1",
    addPowerfistPart("palm", "Palme",
    addPowerfistPart("palm.ringfinger1.ringfinger2", "Ringfinger 2",

    addOBJModelPart(JETPACK, "jetpack_glow", "Lights",



    addTradeoff(MPSConstants.SELECTIVE_MINER_LIMIT, "Block-Limit",

    addOBJModelPart(ARMOR2, "helmetmain", "Helm",
    addTradeoff(MPSConstants.FORTUNE_ENCHANTMENT_LEVEL, "Fortune Level",

addPowerfistPart("mainarm", "Hauptarm",

    addPowerfistPart("supportleftfront", "Support Left Front",


    addPowerfistPart("palm.index1",  "Index 1",
    ,
    addOBJModelPart(MPS_ARMS, "crystal_shoulder_1", "Left Shoulder Crystal",



    addOBJModelPart(MPS_BOOTS, "boots1", "Right Boot",



    addTradeoff(MPSConstants.BLADE_DAMAGE, "Beschädigung der sich drehenden Klinge",
    addPowerfistPart("supportleft4", "Unterstützung links 4",

    addPowerfistPart("wristbottomright", "Wrist Bottom Right",

    addTradeoff(MPSConstants.SPRINT_ENERGY_CONSUMPTION, "Sprint-Energieverbrauch",
    add(MPSItems.LIGHTNING_MODULE.get(), "Blitz-o-Mat",
    addOBJModelPart(ARMOR2, "legbit", "Schutz des linken Beins",


    addTradeoff(MPSConstants.ACTIVATION_PERCENT, "Activation Percent",
    addTradeoff(MPSConstants.SPRINT_ASSIST, "Sprint-Assistent",

    addPowerfistPart("armorleft", "Rüstung links",
    addPowerfistPart("armorleft", "Power-Faust",
    addTradeoff(MPSConstants.HEAT_GENERATION_DAY, "Wärmeerzeugung tagsüber",

    addTradeoff(MPSConstants.FLUID_TANK_SIZE, "Tank Size",

    addPowerfistPart("palm.pinky1", "Kleiner Finger 1",
    addOBJModelPart(JETPACK, "default","Main",
    add(MPSConstants.GUI_KEYBINDS, "Tastenkombinationen",
    addTradeoff(MPSConstants.HEAT_GENERATION_NIGHT, "Wärmeerzeugung bei Nacht",



    addPowerfistPart("fingerguard", "Fingerschutz",
    add(MPSConstants.GUI_COLOR_PREFIX, "Farbe:",

    addTradeoff(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION, "Energieverbrauch von Silk Touch",
    add(MPSConstants.GUI_INSTALLED_MODULES,  "Installed Modules",
    add(MPSConstants.GUI_SHOW_ON_HUD, "Show on HUD:",
    addOBJModelPart(ARMOR2, "chestmain", "Front Chest Protection",
    addTradeoff(MPSConstants.THRUST, "Schub",


    addTradeoff(MPSConstants.KNOCKBACK_RESISTANCE, "Knockback Resistance",
    addTradeoff(MPSConstants.RAILGUN_HEAT_EMISSION, "Railgun-Wärmeabgabe",
    addPowerfistPart("supportleft2", "Unterstützung links 2",
    addOBJModelPart(MPS_CHEST, "chest_main", "Brustüberzug",
    addTradeoff(MPSConstants.FLIGHT_VERTICALITY, "Y-Look-Verhältnis",
    ,
    add(MPSItems.FLINT_AND_STEEL_MODULE.get(), "Feuerzeug",
    addItemDescriptions(MPSItems.SWIM_BOOST_MODULE.get(), "Wenn Sie ein Ionentriebwerk für den Einsatz unter Wasser einsetzen, können Sie unter Wasser möglicherweise zusätzlichen Vorwärts- (oder Rückwärts-) Schub erzeugen.",
    addOBJModelPart(ARMOR2, "bootmain", "Linker Bootskörper",
    addTradeoff(MPSConstants.WALKING_ASSISTANCE, "Gehassistent",
    addJavaModelPart("legs", "RightLeg", "Rechtes Bein",
    addOBJModelPart(ARMOR2, "chestgray", "Brustbasis",
    addPowerfistPart("supportleft3", "Unterstützung links 3",
    addOBJModelPart(ARMOR2, "armmain", "Arm- und Schulterschutz",
    addOBJModelPart(ARMOR2, "chestglowback", "Spinal Power Crystals",
    addOBJModelPart(ARMOR2, "bootglow1", "Right Boot Glow",
    addTradeoff(MPSConstants.ENERGY_GENERATED, "Erzeugte Energie",

    addJavaModelPart("legs", "LeftLeg","Linkes Bein",





    addTradeoff(MPSConstants.RADIUS, "Radius",
    addTradeoff(MPSConstants.FIELD_OF_VIEW, "FOV-Multiplikator",
    addOBJModelPart(MPS_ARMS, "arms2", "Left Arm",
    addTradeoff(MPSConstants.ENCHANTMENT_LEVEL, "Verzauberungsstufe",
    addTradeoff(MPSConstants.ENERGY_GENERATION_DAY, "Energieerzeugung tagsüber",
    addTradeoff(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS, "Plasma-Explosivität",

    addOBJModelPart(ARMOR2, "leggray", "Linke Beinbasis",
    addJavaModelPart("", "feet", "Füße",

    String MPS_PANTALOONS = addOBJModel("mps_pantaloons", "MPS Pantaloons",
    addTradeoff(MPSConstants.ENERGY_CONSUMPTION, "Energieverbrauch",

    addTradeoff(MPSConstants.JETBOOTS_THRUST, "Jetboots Thrust",
    addOBJModelPart(ARMOR2, "armmain1", "Arm- und Schulterschutz",
    addTradeoff(MPSConstants.EFFICIENCY, "Effizienz",
    add(MPSConstants.GUI_VISUAL,  "Visuelles",
    addTradeoff(MPSConstants.RAILGUN_ENERGY_COST, "Railgun-Energiekosten",

    add(MPSConstants.GUI_MODULE_TWEAK, "Module Tweak",




    addTradeoff(MPSConstants.HEAT_ACTIVATION_PERCENT, "Heat Activation Percent",
    ,
    ,
    addTradeoff(MPSConstants.ALPHA, "Alpha",
    ,

    addTradeoff(MPSConstants.GREEN_HUE, "Lux-Kondensator Grüner Farbton",
    addOBJModelPart(ARMOR2, "legglow", "Linkes Beinlicht",
    String MPS_ARMS = addOBJModel("mps_arms", "MPS Arms",




    addJavaModelPart("chest", "Body", "Körper",


    addOBJModelPart(MPS_ARMS, "crystal_shoulder_2", "Right Shoulder Crystal",
    addTradeoff(MPSConstants.ARMOR_VALUE_PHYSICAL, "Rüstung (physisch)",

    addOBJModelPart(ARMOR2, "armglow", "Schulterlicht",

    addOBJModelPart(MPS_HELM, "helm_main", "Helm",
    addPowerfistPart("palm.ringfinger1", "Dedo anelar 1",
    add(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Transparente Rüstung",


    addTradeoff(MPSConstants.SPRINT_SPEED_MULTIPLIER, "Sprint-Geschwindigkeits-Multiplikator",
    addPowerfistPart("wristbottomleft", "Wrist Bottom Left",
    addTradeoff(MPSConstants.MULTIPLIER, "Multiplier",

    addOBJModelPart(ARMOR2, "bootglow", "Linke Boot Glow",
    addTradeoff(MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, "Plasmaenergie pro Tick",


    addPowerfistPart("wristtopright", "Handgelenk oben rechts",
    addOBJModelPart(MPS_HELM, "visor", "Visier",
    addItemDescriptions(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), "Fügen Sie Diamanten hinzu, damit Ihr Spitzhacke-Modul Obsidian mähen kann. * ERFORDERT PICKAXE-MODUL ZU ARBEITEN *",
    addPowerfistPart("supportright3", "Unterstützungsrecht 3",
    addTradeoff(MPSConstants.WALKING_ENERGY_CONSUMPTION, "Gehender Energieverbrauch",


 addTradeoff(MPSConstants.RED_HUE, "Lux-Kondensator-Rot-Farbton",
    addPowerfistPart("palm.index1.index2", "Index 2",
    addTradeoff(MPSConstants.IMPACT, "Auswirkung",



    addItemDescriptions(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Mache den Gegenstand transparent, damit du deine Haut zeigen kannst, ohne deine Rüstung zu verlieren.",
    add(MPSItems.PARACHUTE_MODULE.get(), "Fallschirm",

    add(MPSItems.FLUID_TANK_MODULE.get(), "Fluid Tank",
    addTradeoff(MPSConstants.ENERGY_GENERATION_NIGHT, "Energieerzeugung bei Nacht",

    addOBJModelPart(ARMOR2, "bootmain1", "Rechter Bootvorgang",
    addOBJModelPart(MPS_HELM, "helm_tubes", "Röhren",
    addJavaModelPart("feet", "RightFoot", "Rechter Fuß",
    addOBJModelPart(ARMOR2, "leggray1", "Rechte Beinbasis",
    String JETPACK = addOBJModel("jetpack", "MPS Jetpack",
    add(MPSItems.HOE_MODULE.get(), "Rototiller",
    addOBJModelPart(ARMOR2, "helmetglow1", "Helmtubes und Visier",
    addOBJModelPart(MPS_PANTALOONS, "leg1",  "Right Leg",
    addTradeoff(MPSConstants.GREEN, "Grün",

    addTradeoff(MPSConstants.HEAT_EMISSION, "Wärmeabgabe",
    addTradeoff(MPSConstants.FOOD_COMPENSATION, "Sprint-Ermüdungsausgleich",
    addOBJModelPart(ARMOR2, "armgray1", "Arm-Basis",
    addOBJModelPart(MPS_CHEST, "chest_padding", "Brustpolsterung",



    add(MPSConstants.GUI_ARMOR, "Rüstung",
    addPowerfistPart("palm.thumb1.thumb2", "Daumen 2",

    addOBJModelPart(ARMOR2, "armgray", "Armbasis",




    addOBJModelPart(MPS_ARMS, "arms3", "Right Arm",



    add(MPSConstants.CYCLE_TOOL_FORWARD, "Cycle Tool Forward (MPS)",

    String MPS_CHEST = addOBJModel("mps_chest", "MPS Brustplatte",


    addOBJModelPart(MPS_CHEST, "backpack", "Rucksack",








    addTradeoff(MPSConstants.HARVEST_SPEED, "Ernte-Geschwindigkeit",
    addItemDescriptions(MPSItems.SWIM_BOOST_MODULE.get(), "Eine Reihe von Servomotoren, die Ihnen beim Sprint helfen (Doppeltippen nach vorne) und schneller laufen.",
    addPowerfistPart("armorright", "Rüstung rechtsArmor Left",
    addPowerfistPart("supportrightfront", "Support Right Front",

    add(MPSItems.ENERGY_SHIELD_MODULE.get(), "سپر انرژی",
    add(MPSConstants.TOGGLE_MODULE, "%s umschalten",

    addOBJModelPart(JETPACK, "jetpack5", "Secondary",




    String MPS_BOOTS = addOBJModel("mps_boots", "MPS Boots",
  String MPS_HELM = addOBJModel("mps_helm", "MPS Helm",


*/

    @Override
    public void addModularItems() {
        // Helmet --------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_HELMET.get(), "Power-Rüstungs-Helm");

        // Chestplate ----------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_CHESTPLATE.get(), "Power-Rüstungs-Brustpanzer");

        // Leggings ------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_LEGGINGS.get(), "Powerrüstung Gamaschen");

        // Boots ---------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_BOOTS.get(), "Power-Rüstungs-Stiefel");

        // Power Fist ----------------------------------------------------------------------------------
        add(MPSItems.POWER_FIST.get(), "Power-Faust");
    }

    @Override
    public void addModules() {
        // Armor =======================================================================================
        // Iron Plating --------------------------------------------------------------------------------
        add(MPSItems.IRON_PLATING_MODULE.get(), "Eisenbeschichtung");
        addItemDescriptions(MPSItems.IRON_PLATING_MODULE.get(), "Die Eisenbeschichtung ist schwer, bietet aber etwas mehr Schutz.");

        // Diamond Plating -----------------------------------------------------------------------------
        add(MPSItems.DIAMOND_PLATING_MODULE.get(), "Diamantbeschichtung");
        addItemDescriptions(MPSItems.DIAMOND_PLATING_MODULE.get(), "Diamond Plating ist schwieriger und teurer in der Herstellung, bietet aber einen besseren Schutz.");

        // Netherite Plating ---------------------------------------------------------------------------
        add(MPSItems.NETHERITE_PLATING_MODULE.get(), "Netherit-Beschichtung");
        addItemDescriptions(MPSItems.NETHERITE_PLATING_MODULE.get(), "Panzerung aus Netherit");

        // Energy Shield -------------------------------------------------------------------------------
        add(MPSItems.ENERGY_SHIELD_MODULE.get(), "Energieschild");
        addItemDescriptions(MPSItems.ENERGY_SHIELD_MODULE.get(), "Energieschilde sind viel leichter als Plattierungen, verbrauchen aber Energie.");

        // Cosmetic ====================================================================================
        // Transparent Armor ---------------------------------------------------------------------------
        add(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), "Transparentrüstung");
        addItemDescriptions(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), "Mache den Gegenstand transparent, damit du deine Haut zeigen kannst, ohne die Rüstung zu verlieren.");

        // Energy Generation ===========================================================================
        // Coal Generator ------------------------------------------------------------------------------
        // TODO:
//        add(MPSItems.COAL_GENERATOR_MODULE.get(), "Coal Generator");
//
//        addItemDescriptions(MPSItems.COAL_GENERATOR_MODULE.get(), "Generate power with solid fuels");

        // Solar Generator -----------------------------------------------------------------------------
        add(MPSItems.SOLAR_GENERATOR_MODULE.get(), "Solargenerator");
        addItemDescriptions(MPSItems.SOLAR_GENERATOR_MODULE.get(), "Lassen Sie die Sonne Ihre Abenteuer antreiben.");

        // High Efficiency Solar Generator -------------------------------------------------------------
        add(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), "Hocheffizienter Solargenerator");
        addItemDescriptions(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), "Ein Solargenerator mit der 3-fachen Stromerzeugung des Standard-Solargenerators.");

        // Kinetic Generator ---------------------------------------------------------------------------
        add(MPSItems.KINETIC_GENERATOR_MODULE.get(), "Kinetischer Generator");
        addItemDescriptions(MPSItems.KINETIC_GENERATOR_MODULE.get(), "Erzeugen Sie Kraft mit Ihrer Bewegung.");

        // Thermal Generator ---------------------------------------------------------------------------
        add(MPSItems.THERMAL_GENERATOR_MODULE.get(), "Thermischer Generator");
        addItemDescriptions(MPSItems.THERMAL_GENERATOR_MODULE.get(), "Erzeugen Sie Strom aus extremen Wärmemengen.");

        // Environmental ===============================================================================
        // Auto Feeder ---------------------------------------------------------------------------------
        add(MPSItems.AUTO_FEEDER_MODULE.get(),  "Automatischer Einzug");
        addItemDescriptions(MPSItems.AUTO_FEEDER_MODULE.get(), "Wann immer du hungrig bist, schnappt sich dieses Modul das Lebensmittel ganz unten links aus deinem Inventar und füttert es dir, während du den Rest für später aufbewahrst.");

        // Cooling System ------------------------------------------------------------------------------
        add(MPSItems.COOLING_SYSTEM_MODULE.get(), "Grund Kühlsystem");
        addItemDescriptions(MPSItems.COOLING_SYSTEM_MODULE.get(), "Kühlt wärmerzeugende Module schneller ab.");

        // Fluid Tank  ---------------------------------------------------------------------------------
        add(MPSItems.FLUID_TANK_MODULE.get(),  "Flüssigkeitstank");
        addItemDescriptions(MPSItems.FLUID_TANK_MODULE.get(), "Speichert Flüssigkeit, um die Leistung des Kühlsystems zu verbessern.");

        // Mob Repulsor --------------------------------------------------------------------------------
        add(MPSItems.MOB_REPULSOR_MODULE.get(), "Mob Repulsor");
        addItemDescriptions(MPSItems.MOB_REPULSOR_MODULE.get(), "Schiebt Mobs von dir weg, wenn es aktiviert wird, aber es verbraucht ständig Energie. Es wird dringend empfohlen, dieses Modul wegen des hohen Energieverbrauchs auf eine Tastatur zu setzen.");

        // Water Electrolyzer --------------------------------------------------------------------------
        add(MPSItems.WATER_ELECTROLYZER_MODULE.get(), "Wasser-Elektrolyseur");
        addItemDescriptions(MPSItems.WATER_ELECTROLYZER_MODULE.get(), "Wenn Ihnen die Luft ausgeht, rüttelt dieses Modul das Wasser um Sie herum und elektrolysiert eine kleine Blase, aus der Sie atmen können.");

        // Mining Enchantments =========================================================================
        // Aqua Affinity -------------------------------------------------------------------------------
        add(MPSItems.AQUA_AFFINITY_MODULE.get(), "Aqua Affinität");
        addItemDescriptions(MPSItems.AQUA_AFFINITY_MODULE.get(), "Reduziert die Geschwindigkeitseinbußen bei der Verwendung Ihres Werkzeugs unter Wasser.");

        // Silk Touch Enchantment ----------------------------------------------------------------------
        add(MPSItems.SILK_TOUCH_MODULE.get(), "Silk Touch Verzauberung");
        addItemDescriptions(MPSItems.SILK_TOUCH_MODULE.get(), "Ein Modul, das die Silk Touch-Verzauberung bietet");

        // Fortune Enchantment -------------------------------------------------------------------------
        add(MPSItems.FORTUNE_MODULE.get(), "Glücks-Verzauberung");
        addItemDescriptions(MPSItems.FORTUNE_MODULE.get(), "Ein Modul, das für die Glücksverzauberung sorgt.");

        // Mining Enhancements =========================================================================
        // Vein Miner ----------------------------------------------------------------------------------
        add(MPSItems.VEIN_MINER_MODULE.get(), "Erzgang-Bergmann");
        addItemDescriptions(MPSItems.VEIN_MINER_MODULE.get(), "Ein Modul für den Abbau von Erzadern");

        // Tunnel Bore ---------------------------------------------------------------------------------
        add(MPSItems.TUNNEL_BORE_MODULE.get(),  "Tunnel-Röhre");
        addItemDescriptions(MPSItems.TUNNEL_BORE_MODULE.get(), "Ein Modul, das es dem Spitzhackenmodul ermöglicht, 5x5-Range-Blöcke gleichzeitig abzubauen.");

        // Selective Miner -----------------------------------------------------------------------------
        add(MPSItems.SELECTIVE_MINER.get(), "Selektiver Miner");
        addItemDescriptions(MPSItems.SELECTIVE_MINER.get(), "Bricht Blöcke ähnlich wie der Erzgang-Miner, aber selektiv. Drücken Sie die Umschalttaste und klicken Sie, um den Blocktyp auszuwählen.");

        // Movement ====================================================================================
        // Blink Drive ---------------------------------------------------------------------------------
        add(MPSItems.BLINK_DRIVE_MODULE.get(), "Teleportationsgetriebe");
        addItemDescriptions(MPSItems.BLINK_DRIVE_MODULE.get(), "Erhalten Sie von Punkt A zu Punkt C über Punkt B, wo Punkt B eine Falte in Raum und Zeit ist.");

        // Uphill Step Assist --------------------------------------------------------------------------
        add(MPSItems.CLIMB_ASSIST_MODULE.get(), "Steigungsassistent");
        addItemDescriptions(MPSItems.CLIMB_ASSIST_MODULE.get(), "Mit einem Paar dedizierter Servos können Sie problemlos 1 m hohe Leisten hochstufen.");

        // Dimensional Tear Generator ------------------------------------------------------------------
        add(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), "Dimensionaler Tränengenerator");
        addItemDescriptions(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), "Erzeuge einen Riss im Raum-Zeit-Kontinuum, der den Spieler zu seinen relativen Koordinaten in der Unter- oder Oberwelt teleportiert.");

        // Flight Control ------------------------------------------------------------------------------
        add(MPSItems.FLIGHT_CONTROL_MODULE.get(), "Flugkontrolle");
        addItemDescriptions(MPSItems.FLIGHT_CONTROL_MODULE.get(), "Eine integrierte Steuerschaltung, die Ihnen hilft, besser zu fliegen. Drücke Z um runter zu gehen.");

        // Glider --------------------------------------------------------------------------------------
        add(MPSItems.GLIDER_MODULE.get(), "Gleiter");
        addItemDescriptions(MPSItems.GLIDER_MODULE.get(), "Tack auf einigen Flügeln, um nach unten in Vorwärtsmomentum zu drehen. Drücken Sie Sneak + vorwärts, während Sie fallen, um zu aktivieren.");

        // Jet Boots -----------------------------------------------------------------------------------
        add(MPSItems.JETBOOTS_MODULE.get(), "Raketenstiefel");
        addItemDescriptions(MPSItems.JETBOOTS_MODULE.get(), "Jet-Boots sind nicht so stark wie ein Jetpack, aber sie sollten zumindest stark genug sein, um der Schwerkraft entgegenzuwirken.");

        // Jetpack -------------------------------------------------------------------------------------
        add(MPSItems.JETPACK_MODULE.get(), "Raketenrucksack");
        addItemDescriptions(MPSItems.JETPACK_MODULE.get(), "Ein Jetpack sollte dir erlauben, unbegrenzt zu springen, oder zumindest bis dir die Energie ausgeht.");

        // Jump Assist ---------------------------------------------------------------------------------
        add(MPSItems.JUMP_ASSIST_MODULE.get(), "Sprungassistent");
        addItemDescriptions(MPSItems.JUMP_ASSIST_MODULE.get(), "Ein weiterer Satz Servomotoren hilft Ihnen, höher zu springen.");

        // Parachute -----------------------------------------------------------------------------------
        add(MPSItems.PARACHUTE_MODULE.get(), "Parachute");
        addItemDescriptions(MPSItems.PARACHUTE_MODULE.get(), "Fügen Sie einen Fallschirm hinzu, um Ihren Abstieg zu verlangsamen. Aktivieren Sie diese Option, indem Sie in der Luft auf Sneak drücken (Standardeinstellung: Shift).");

        // Shock Absorber ------------------------------------------------------------------------------
        add(MPSItems.SHOCK_ABSORBER_MODULE.get(), "Schockabsorber");
        addItemDescriptions(MPSItems.SHOCK_ABSORBER_MODULE.get(), "Mit einigen Servos, Federn und Polsterungen sollten Sie in der Lage sein, einen Teil des Sturzschadens zu negieren.");

        // Sprint Assist -------------------------------------------------------------------------------
        add(MPSItems.SPRINT_ASSIST_MODULE.get(), "Laufassistent");
        addItemDescriptions(MPSItems.SPRINT_ASSIST_MODULE.get(), "A set of servo motors to help you sprint (double-tap forward) and walk faster.");

        // Swim Boost ----------------------------------------------------------------------------------
        add(MPSItems.SWIM_BOOST_MODULE.get(), "Schwimmturbo");
        addItemDescriptions(MPSItems.SWIM_BOOST_MODULE.get(), "By refitting an ion thruster for underwater use, you may be able to add extra forward (or backward) thrust when underwater.");

        // Special =====================================================================================
        // Active Camouflage ---------------------------------------------------------------------------
        add(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Active Camouflage");
        addItemDescriptions(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Emit a hologram of your surroundings to make yourself almost imperceptible.");

        // Magnet --------------------------------------------------------------------------------------
        add(MPSItems.MAGNET_MODULE.get(), "Magnet");
        addItemDescriptions(MPSItems.MAGNET_MODULE.get(), "Erzeugt ein Magnetfeld, das stark genug ist, Gegenstände gegen den Spieler zu ziehen. WARNUNG: Dieses Modul leitet kontinuierlich Strom ab. Schalten Sie es aus, wenn es nicht benötigt wird.");

        // Piglin Pacification Module ------------------------------------------------------------------
        add(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), "Piglin Beruhigungsmodul");
        addItemDescriptions(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), "Ahmt eine goldene Rüstung zur Befriedung von Piglin nach.");

        // Vision ======================================================================================
        // Binoculars ----------------------------------------------------------------------------------
        add(MPSItems.BINOCULARS_MODULE.get(), "Ferngläser");
        addItemDescriptions(MPSItems.BINOCULARS_MODULE.get(),  "Mit den Problemen, die Optifine in letzter Zeit plagten, haben Sie sich entschieden, diese Zoom-Fähigkeit in Ihre eigenen Hände zu nehmen.");

        // Night Vision --------------------------------------------------------------------------------
        add(MPSItems.NIGHT_VISION_MODULE.get(), "Nachtsichtgerät");
        addItemDescriptions(MPSItems.NIGHT_VISION_MODULE.get(), "Ein Paar augmentierte Sichtschutzbrillen, damit Sie nachts und unter Wasser sehen können.");

        // Tools =======================================================================================
        // Axe -----------------------------------------------------------------------------------------
        add(MPSItems.AXE_MODULE.get(),  "Axt");
        addItemDescriptions(MPSItems.AXE_MODULE.get(), "Äxte dienen hauptsächlich zum Hacken von Bäumen.");

        // Diamond Drill Upgrade -----------------------------------------------------------------------
        add(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), "Diamantbohrer-Upgrade");
        addItemDescriptions(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), "Add diamonds to allow your pickaxe module to mine Obsidian. *REQUIRES PICKAXE MODULE TO WORK*");

        // Flint and Steel -----------------------------------------------------------------------------
        add(MPSItems.FLINT_AND_STEEL_MODULE.get(), "Flint and Steel");
        addItemDescriptions(MPSItems.FLINT_AND_STEEL_MODULE.get(),"Ein tragbarer Zünder, der Feuer durch die Kraft der Energie erzeugt.");

        // Rototiller ----------------------------------------------------------------------------------
        add(MPSItems.HOE_MODULE.get(), "Rototiller");
        addItemDescriptions(MPSItems.HOE_MODULE.get(), "Ein automatisiertes Pflüge-Addon, das es einfach macht, große Landstriche auf einmal zu bearbeiten.");

        // Leaf Blower ---------------------------------------------------------------------------------
        add(MPSItems.LEAF_BLOWER_MODULE.get(), "Laubgebläse");
        addItemDescriptions(MPSItems.LEAF_BLOWER_MODULE.get(), "Erschaffe einen Strom von Luft, um Pflanzen aus dem Boden zu werfen und von Bäumen zu entfernen.");

        // Lux Capacitor -------------------------------------------------------------------------------
        add(MPSItems.LUX_CAPACITOR_MODULE.get(), "Luxkompensator");
        addItemDescriptions(MPSItems.LUX_CAPACITOR_MODULE.get(), "Starte eine nahezu unendliche Anzahl attraktiver Lichtquellen an der Wand.");

        // Pickaxe -------------------------------------------------------------------------------------
        add(MPSItems.PICKAXE_MODULE.get(), "Spitzhacke");
        addItemDescriptions(MPSItems.PICKAXE_MODULE.get(), "Picks sind gut für härtere Materialien wie Stein und Erz.");

        // Shears --------------------------------------------------------------------------------------
        add(MPSItems.SHEARS_MODULE.get(), "Scheren");
        addItemDescriptions(MPSItems.SHEARS_MODULE.get(), "Schneidet Blätter, Wolle und Schlingpflanzen gleichermaßen.");

        // Shovel --------------------------------------------------------------------------------------
        add(MPSItems.SHOVEL_MODULE.get(), "Schaufel");
        addItemDescriptions(MPSItems.SHOVEL_MODULE.get(), "Schaufeln sind gut für weiche Materialien wie Schmutz und Sand.");

        // Debug =======================================================================================
        // TODO

        // Weapons =====================================================================================
        // Blade Launcher ------------------------------------------------------------------------------
        add(MPSItems.BLADE_LAUNCHER_MODULE.get(),  "Klingenwerfer");
        addItemDescriptions(MPSItems.BLADE_LAUNCHER_MODULE.get(),  "Startet eine sich drehende Klinge des Todes (oder Scherens).");

        // Lightning Summoner ------------------------------------------------------------------------
        add(MPSItems.LIGHTNING_MODULE.get(), "Blitzbeschwörer");
        addItemDescriptions(MPSItems.LIGHTNING_MODULE.get(), "Erlaubt das Aufladen von Blitzen zu hohen Energiekosten.");

        // Melee Assist --------------------------------------------------------------------------------
        add(MPSItems.MELEE_ASSIST_MODULE.get(), "Nahkampfassistent");
        addItemDescriptions(MPSItems.MELEE_ASSIST_MODULE.get(), "Ein viel einfacheres Addon, macht Ihre Powertoolschläge härter schlagen.");

        // Plasma Cannon -------------------------------------------------------------------------------
        add(MPSItems.PLASMA_CANNON_MODULE.get(), "Plasmakanone");
        addItemDescriptions(MPSItems.PLASMA_CANNON_MODULE.get(), "Verwenden Sie elektrische Lichtbögen in einem Eindämmungsfeld, um Luft zu einem Plasma zu überhitzen und es bei Feinden abzuschießen.");

        // Railgun -------------------------------------------------------------------------------------
        add(MPSItems.RAILGUN_MODULE.get(), "Elektromagnetische Kanone");
        addItemDescriptions(MPSItems.RAILGUN_MODULE.get(), "Eine Baugruppe, die ein Geschoss mit Magnetkraft auf Überschallgeschwindigkeit beschleunigt. Starker Rückstoß.");
    }

    @Override
    public void addBlocks() {
        // Lux Capacitor -------------------------------------------------------------------------------
        add(MPSBlocks.LUX_CAPACITOR_BLOCK.get(), "Luxkompensator");

        // Power Armor Tinker Table --------------------------------------------------------------------
        add(MPSBlocks.TINKER_TABLE_BLOCK.get(), "Energierüstungsbasteltisch");
    }

    @Override
    public void addGui() {
        // Armor ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_ARMOR, "Rüstung");

        // Color ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_COLOR_PREFIX, "Color:");

        // Compatible Modules -------------------------------------------------------------------------
        add(MPSConstants.GUI_COMPATIBLE_MODULES, "Kompatible Module");

        // Energy Storage ------------------------------------------------------------------------------
        add(MPSConstants.GUI_ENERGY_STORAGE, "Energiespeicherung");

        // Equipped Totals -----------------------------------------------------------------------------
        add(MPSConstants.GUI_EQUIPPED_TOTALS, "Ausgerüstete Summen");

        // Installed Modules ---------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALLED_MODULES, "Installierte Module");

        // Show on HUD ---------------------------------------------------------------------------------
        add(MPSConstants.GUI_SHOW_ON_HUD, "Show on HUD:");

        // Install/Salvage -----------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL_SALVAGE, "Installieren/Bergen");

        // Keybinds ------------------------------------------------------------------------------------
        add(MPSConstants.GUI_KEYBINDS, "Keybinds");

        // Module Tweak --------------------------------------------------------------------------------
        add(MPSConstants.GUI_MODULE_TWEAK, "Module Tweak");

        // Visual --------------------------------------------------------------------------------------
        add(MPSConstants.GUI_VISUAL, "Visual");

        // Tinker --------------------------------------------------------------------------------------
        add(MPSConstants.GUI_TINKER_TABLE, "Bastlen");

        // Modular Item Inventory ----------------------------------------------------------------------
        add(MPSConstants.GUI_MODULAR_ITEM_INVENTORY, "Modularer Artikelbestand");
    }

    @Override
    public void addModuleTradeoff() {
        // Activation Percent --------------------------------------------------------------------------
        addTradeoff(MPSConstants.ACTIVATION_PERCENT, "Activation Percent");

        // Alpha ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ALPHA, "Alpha");

        // Amperage ------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.AMPERAGE, "Stromstärke");

        // Block Limit ---------------------------------------------------------------------------------
        addTradeoff(MPSConstants.SELECTIVE_MINER_LIMIT, "Block Limit");

        // Armor (Energy) ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_VALUE_ENERGY, "Rüstung (Energie)");

        // Energy Per Damage ---------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_ENERGY_CONSUMPTION, "Energie pro Schaden");

        // Armor (Physical) ----------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_VALUE_PHYSICAL, "Armor (Physical)");

        // pts -----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_POINTS, "pts");

        // Auto-Feeder Efficiency ----------------------------------------------------------------------
        addTradeoff(MPSConstants.EATING_EFFICIENCY, "Auto-Feeder Efficiency");

        // Range ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.BLINK_DRIVE_RANGE, "Range");

        // Blue ----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.BLUE, "Blau");

        // Lux Capacitor Blue Hue ----------------------------------------------------------------------
        addTradeoff(MPSConstants.BLUE_HUE, "Lux-Kondensator Blue Hue");

        // Carry-through -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.CARRY_THROUGH, "Durchführung");

        // Compensation --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.COMPENSATION, "Entschädigung");

        // Cooling Bonus -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.COOLING_BONUS, "Kühlbonus");

        // Daytime Energy Generation -------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION_DAY, "Daytime Energy Generation");

        // Daytime Heat Generation ---------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION_DAY, "Daytime Heat Generation");

        // Diameter ------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.DIAMETER, "Durchmesser");

        // Efficiency ----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.EFFICIENCY, "Efficiency");

        // Enchantment Level ---------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENCHANTMENT_LEVEL, "Enchantment Level");

        // Energy Consumption --------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_CONSUMPTION, "Energy Consumption");

        // Energy Generated ----------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATED, "Energy Generated");

        // Energy Per Block Per Second -----------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION, "Energie pro Block pro Sekunde");

        // FOV multiplier ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FIELD_OF_VIEW, "FOV multiplier");

        // Field of View -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FOV, "Field of View");

        // Field Strength ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.MODULE_FIELD_STRENGTH, "Sichtfeld");

        // Tank Size -----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FLUID_TANK_SIZE, "Tank Size");

        // Fortune Energy Consumption ------------------------------------------------------------------
        addTradeoff(MPSConstants.FORTUNE_ENERGY_CONSUMPTION, "Fortune Energieverbrauch");

        // Fortune Level -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FORTUNE_ENCHANTMENT_LEVEL, "Fortune Level");

        // Green ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.GREEN, "Green");

        // Lux Capacitor Green Hue ---------------------------------------------------------------------
        addTradeoff(MPSConstants.GREEN_HUE, "Lux Capacitor Green Hue");

        // Harvest Speed -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.HARVEST_SPEED, "Geschwindigkeit der Ernte");

        // Heat Activation Percent ---------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_ACTIVATION_PERCENT, "Heat Activation Percent");

        // Heat Emission -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_EMISSION, "Heat Emission");

        // Heat Generation -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION, "Hitzeentwicklung");

        // Impact --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.IMPACT, "Impact");

        // Jetboots Thrust -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.JETBOOTS_THRUST, "Jetboots Thrust");

        // Jetpack Thrust ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.JETPACK_THRUST, "Jetpack-Schub");

        // Knockback Resistance ------------------------------------------------------------------------
        addTradeoff(MPSConstants.KNOCKBACK_RESISTANCE, "Knockback Resistance");

        // Melee Damage --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.PUNCH_DAMAGE, "Nahkampfschaden");

        // Melee Knockback -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.PUNCH_KNOCKBACK, "Nahkampf-Rückstoß");

        // Movement Resistance -------------------------------------------------------------------------
        addTradeoff(MPSConstants.MOVEMENT_RESISTANCE, "Bewegungswiderstand");

        // Multiplier ----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.MULTIPLIER, "Multiplier");

        // Nighttime Energy Generation -----------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION_NIGHT, "Nighttime Energy Generation");

        // Nighttime Heat Generation -------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION_NIGHT, "Nighttime Heat Generation");

        // Lux Capacitor Opacity -----------------------------------------------------------------------
        addTradeoff(MPSConstants.OPACITY, "Lux Kondensator Opazität");

        // Overclock -----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.OVERCLOCK, "Übertakten");

        // Plasma Damage At Full Charge ----------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, "Plasmaschaden bei voller Ladung");

        // Plasma Energy Per Tick ----------------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, "Plasma Energy Per Tick");

        // Plasma Explosiveness ------------------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS, "Plasma Explosiveness");

        // Power ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.POWER, "Leistung");

        // Radius --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RADIUS, "Radius");

        // Railgun Energy Cost -------------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_ENERGY_COST, "Railgun Energy Cost");

        // Railgun Heat Emission -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_HEAT_EMISSION, "Railgun Heat Emission");

        // Railgun Total Impulse -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_TOTAL_IMPULSE, "Gesamtimpuls der Schiene");

        // Range ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RANGE, "Bereich");

        // Red -----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RED, "Rot");

        // Lux Capacitor Red Hue -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RED_HUE, "Lux Capacitor Red Hue");

        // Silk Touch Energy Consumption ---------------------------------------------------------------
        addTradeoff(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION, "Silk Touch Energy Consumption");

        // Spinning Blade Damage -----------------------------------------------------------------------
        addTradeoff(MPSConstants.BLADE_DAMAGE, "Spinning Blade Damage");

        // Sprint Assist -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_ASSIST, "Sprint Assist");

        // Sprint Energy Consumption -------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_ENERGY_CONSUMPTION, "Sprint Energy Consumption");

        // Sprint Exhaustion Compensation --------------------------------------------------------------
        addTradeoff(MPSConstants.FOOD_COMPENSATION, "Sprint Exhaustion Compensation");

        // Sprint Speed Multiplier ---------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_SPEED_MULTIPLIER, "Sprint Speed Multiplier");

        // Thrust --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.THRUST, "Thrust");

        // Underwater Movement Boost -------------------------------------------------------------------
        addTradeoff(MPSConstants.SWIM_BOOST_AMOUNT, "Unterwasserbewegungsschub");

        // Verticality ---------------------------------------------------------------------------------
        addTradeoff(MPSConstants.VERTICALITY, "Vertikalität");

        // Voltage -------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.VOLTAGE, "Spannung");

        // Walking Assist ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_ASSISTANCE, "Walking Assist");

        // Walking Energy Consumption ------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_ENERGY_CONSUMPTION, "Walking Energy Consumption");

        // Walking Speed Multiplier --------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_SPEED_MULTIPLIER, "Multiplikator für Gehgeschwindigkeit");

        // Y-look ratio --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FLIGHT_VERTICALITY, "Y-look ratio");
    }

    @Override
    public void addKeybinds() {
        // Go down -------------------------------------------------------------------------------------
        add(MPSConstants.GO_DOWN_KEY, "Go Down (MPS)");

        // Cycle Tool Backward (MPS) -------------------------------------------------------------------
        add(MPSConstants.CYCLE_TOOL_BACKWARD, "Cycle Tool Backward (MPS)");

        // Cycle Tool Forward (MPS) --------------------------------------------------------------------
        add(MPSConstants.CYCLE_TOOL_FORWARD, "Cycle Tool Forward (MPS)");

        // Open Keybind Gui ----------------------------------------------------------------------------
        add(MPSConstants.OPEN_KEYBIND_GUI, "Open Keybinding GUI (MPS)");

        // Open Cosmetic Gui ---------------------------------------------------------------------------
        add(MPSConstants.OPEN_COSMETIC_GUI, "Open Cosmetic GUI (MPS)");

        // Open Module Tweak Gui -----------------------------------------------------------------------
        add(MPSConstants.OPEN_MODULE_TWEAK_GUI, "Open Module Teak GUI (MPS)");

        // Open Install/Salvage Gui --------------------------------------------------------------------
        add(MPSConstants.OPEN_INSTALL_SALVAGE_GUI, "Open Install/Salvage GUI (MPS)");

        // Toggle Module -------------------------------------------------------------------------------
        add(MPSConstants.TOGGLE_MODULE, "Toggle %s");
    }

    @Override
    public void addModels() {
        // Generic armor model parts ===================================================================
        // Head ----------------------------------------------------------------------------------------
        addJavaModelPart("head", "Head", "Kopf");

        // Body ----------------------------------------------------------------------------------------
        addJavaModelPart("chest", "Body", "Body");

        // Chest ---------------------------------------------------------------------------------------
        addJavaModelPart("", "chest", "Brust");

        // Left Arm ------------------------------------------------------------------------------------
        addJavaModelPart("chest", "LeftArm", "Linker Arm");

        // Right Arm -----------------------------------------------------------------------------------
        addJavaModelPart("chest", "RightArm", "Rechter Arm");

        // Legs ----------------------------------------------------------------------------------------
        addJavaModelPart("", "legs", "Legs");

        // Left Leg ------------------------------------------------------------------------------------
        addJavaModelPart("legs", "LeftLeg", "LeftLeg");

        // Right Leg -----------------------------------------------------------------------------------
        addJavaModelPart("legs", "RightLeg",  "RightLeg");

        // Feet ----------------------------------------------------------------------------------------
        addJavaModelPart("", "feet", "Feet");

        // Left Foot -----------------------------------------------------------------------------------
        addJavaModelPart("feet", "LeftFoot", "Linker Fuß");

        // Right Foot ----------------------------------------------------------------------------------
        addJavaModelPart("feet", "RightFoot", "RightFoot");

        // Armor skins ---------------------------------------------------------------------------------
        add("javaModel.citizenjoe_armorskin.specName", "Citizen Joe Armor Skin");

        // Default ArmorSkin ---------------------------------------------------------------------------
        add("javaModel.default_armorskin.specName", "Standard-Rüstungshaut");

        // Power Fist ==================================================================================
        add("javaModel.powerfist.specName", "Power Fist");

        // Main Arm ------------------------------------------------------------------------------------
        addPowerfistPart("mainarm", "Main Arm");

        // Armor Right ---------------------------------------------------------------------------------
        addPowerfistPart("armorright", "Armor Right");

        // Armor Left ----------------------------------------------------------------------------------
        addPowerfistPart("armorleft", "Armor Left");

        // Wrist Top Right -----------------------------------------------------------------------------
        addPowerfistPart("wristtopright", "Wrist Top Right");

        // Wrist Top Left ------------------------------------------------------------------------------
        addPowerfistPart("wristtopleft", "Handgelenk oben links");

        // Wrist Bottom Right --------------------------------------------------------------------------
        addPowerfistPart("wristbottomright", "Wrist Bottom Right");

        // Wrist Bottom Left ---------------------------------------------------------------------------
        addPowerfistPart("wristbottomleft", "Wrist Bottom Left");

        // Finger Guard --------------------------------------------------------------------------------
        addPowerfistPart("fingerguard", "Finger Guard");

        // Crystal Holder ------------------------------------------------------------------------------
        addPowerfistPart("crystalholder", "Kristallhalter");

        // Crystal -------------------------------------------------------------------------------------
        addPowerfistPart("crystal", "Kristall");

        // Support Right 1 -----------------------------------------------------------------------------
        addPowerfistPart("supportright1", "Unterstützungsrecht 1");

        // Support Right 2 -----------------------------------------------------------------------------
        addPowerfistPart("supportright2", "Unterstützungsrecht 2");

        // Support Right 3 -----------------------------------------------------------------------------
        addPowerfistPart("supportright3", "Support Right 3");

        // Support Right 4 -----------------------------------------------------------------------------
        addPowerfistPart("supportright4", "Unterstützungsrecht 4");

        // Support Right 5 -----------------------------------------------------------------------------
        addPowerfistPart("supportright5", "Unterstützungsrecht 5");

        // Support Base Right --------------------------------------------------------------------------
        addPowerfistPart("supportbaseright", "Basis rechts unterstützen");

        // Support Right Front -------------------------------------------------------------------------
        addPowerfistPart("supportrightfront", "Support Right Front");

        // Support Base Left ---------------------------------------------------------------------------
        addPowerfistPart("supportbaseleft", "Unterstützungsbasis links");

        // Support Left Front --------------------------------------------------------------------------
        addPowerfistPart("supportleftfront", "Support Left Front");

        // Support Left 1 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft1", "Unterstützung links 1");

        // Support Left 2 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft2", "Support Left 2");

        // Support Left 3 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft3", "Support Left 3");

        // Support Left 4 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft4", "Support Left 4");

        // Support Left 5 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft5", "Unterstützung links 5");

        // Palm ----------------------------------------------------------------------------------------
        addPowerfistPart("palm", "Palm");

        // Index 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.index1", "Index 1");

        // Index 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.index1.index2", "Index 2");

        // Middle Finger 1 -----------------------------------------------------------------------------
        addPowerfistPart("palm.middlefinger1", "Mittelfinger 1");

        // Middle Finger 2 -----------------------------------------------------------------------------
        addPowerfistPart("palm.middlefinger1.middlefinger2", "Mittelfinger 2");

        // Ring Finger 1 -------------------------------------------------------------------------------
        addPowerfistPart("palm.ringfinger1", "Ring Finger 1");

        // Ring Finger 2 -------------------------------------------------------------------------------
        addPowerfistPart("palm.ringfinger1.ringfinger2", "Ring Finger 2");

        // Pinky 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.pinky1", "Pinky 1");

        // Pinky 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.pinky1.pinky2", "Kleiner Finger 2");

        // Thumb 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.thumb1", "Thumb 1");

        // Thumb 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.thumb1.thumb2", "Thumb 2");

        // MPS Armor ===================================================================================
        // Helm ----------------------------------------------------------------------------------------
        String MPS_HELM = addOBJModel("mps_helm", "MPS Helm");

        // Helmet --------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_main", "Helmet");

        // Tubes ---------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tubes", "Tubes");

        // Left Tube Entry -----------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tube_entry1", "Linke Tube-Eingabe");

        // Right Tube Entry ----------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tube_entry2", "Rechter Rohreintritt");

        // Visor ---------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "visor", "Visor");

        // MPS Chestplate ------------------------------------------------------------------------------
        String MPS_CHEST = addOBJModel("mps_chest", "MPS Chestplate");

        // Chest Plating -------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "chest_main", "Chest Plating");

        // Chest Padding -------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "chest_padding", "Chest Padding");

        // Belt Crystal --------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "crystal_belt", "Gürtel Kristall");

        // Backpack ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "backpack", "Backpack");

        // Belt ----------------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "belt", "Gürtel");

        // Accessory ----------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "polySurface36",  "Zubehörteil");

        // MPS Arms ------------------------------------------------------------------------------------
        String MPS_ARMS = addOBJModel("mps_arms", "MPS Arms");

        // Left Arm ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "arms2", "Left Arm");

        // Right Arm -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "arms3", "Right Arm");

        // Left Shoulder Crystal -----------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "crystal_shoulder_1", "Left Shoulder Crystal");

        // Right Shoulder Crystal ----------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "crystal_shoulder_2", "Right Shoulder Crystal");

        // MPS Jetpack ---------------------------------------------------------------------------------
        String JETPACK = addOBJModel("jetpack", "MPS Jetpack");

        // Main ----------------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "default", "Main");

        // Secondary -----------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "jetpack5", "Secondary");

        // Lights --------------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "jetpack_glow", "Lights");

        // MPS Pantaloons ------------------------------------------------------------------------------
        String MPS_PANTALOONS = addOBJModel("mps_pantaloons", "MPS Pantaloons");

        // Right Leg -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_PANTALOONS, "leg1", "Right Leg");

        // Left Leg ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_PANTALOONS, "leg2", "Linkes Bein");

        // MPS Boots -----------------------------------------------------------------------------------
        String MPS_BOOTS = addOBJModel("mps_boots", "MPS Boots");

        // Right Boot ----------------------------------------------------------------------------------
        addOBJModelPart(MPS_BOOTS, "boots1", "Right Boot");

        // Left Boot -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_BOOTS, "boots2", "Left Boot");

        // Armor 2 =====================================================================================
        String ARMOR2 = addOBJModel("armor2", "Rüstung 2");

        // Helmet --------------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "helmetmain", "Helmet");

        // Helmet Tubes and Visor ----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "helmetglow1", "Helmet Tubes and Visor");

        // Front Chest Protection ----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestmain", "Front Chest Protection");

        // Chest Base ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestgray", "Chest Base");

        // Back Chest Protection -----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestback1", "Zurück Brustschutz");

        // Spinal Power Crystals -----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestglowback", "Spinal Power Crystals");

        // Front Power Crystal -------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestglowfront", "Frontaler Power-Kristall");

        // Arm and Shoulder Protection -----------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armmain", "Arm and Shoulder Protection");

        // Arm and Shoulder Protection 1 ---------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armmain1", "Arm and Shoulder Protection");

        // Lef Boot Glow -------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootglow", "Left Boot Glow");

        // Right Boot Glow -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootglow1", "Right Boot Glow");

        // Left Boot -----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootmain", "Left Boot");

        // Right Boot ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootmain1", "Right Boot");

        // Shoulder Light ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armglow", "Shoulder Light");

        // Shoulder Light 1 -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armglow1", "Schulterlicht");

        // Arm Base ------------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armgray",  "Arm Base");

        // Arm Base 1 ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armgray1", "Arm Base");

        // Left Leg Base -------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "leggray", "Left Leg Base");

        // Right Leg Base ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "leggray1", "Right Leg Base");

        // Left Leg Protection -------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legbit", "Left Leg Protection");

        // Right Leg Protection ------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legbit1", "Schutz des rechten Beins");

        // Left Leg Light ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legglow", "Left Leg Light");

        // Right Leg Light -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legglow1", "Lichter für das rechte Bein");
    }
}
