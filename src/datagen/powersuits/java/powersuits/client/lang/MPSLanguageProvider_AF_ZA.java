package powersuits.client.lang;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSItems;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.DataGenerator;

public class MPSLanguageProvider_AF_ZA extends AbstractLangageProviderMPS {
    public MPSLanguageProvider_AF_ZA (DataGenerator output) {
        super(output, MPSConstants.MOD_ID, "af_za");
    }

    @Override
    public void addItemGroup() {
        add("itemGroup." + MPSConstants.MOD_ID, "Modular Powersuits");
    }

    @Override
    public void addModularItems() {
        // Helmet --------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_HELMET.get(), "Krag Pantser Helmet");

        // Chestplate ----------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_CHESTPLATE.get(), "Krag Pantser Borsplaat");

        // Leggings ------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_LEGGINGS.get(), "Krag Pantser Leggings");

        // Boots ---------------------------------------------------------------------------------------
        add(MPSItems.POWER_ARMOR_BOOTS.get(), "Krag pantser stewels");

        // Power Fist ----------------------------------------------------------------------------------
        add(MPSItems.POWER_FIST.get(), "Kragvuis");
    }

    @Override
    public void addModules() {
        // Armor =======================================================================================
        // Iron Plating --------------------------------------------------------------------------------
        add(MPSItems.IRON_PLATING_MODULE.get(), "Ysterplaat");
        addItemDescriptions(MPSItems.IRON_PLATING_MODULE.get(), "Ysterplate is swaar, maar bied 'n bietjie meer beskerming.");

        // Diamond Plating -----------------------------------------------------------------------------
        add(MPSItems.DIAMOND_PLATING_MODULE.get(), "Diamantplatering");
        addItemDescriptions(MPSItems.DIAMOND_PLATING_MODULE.get(), "Diamantplatering is moeiliker en duurder om te maak, maar bied beter beskerming.");

        // Netherite Plating ---------------------------------------------------------------------------
        add(MPSItems.NETHERITE_PLATING_MODULE.get(), "Netherite Plating");
        addItemDescriptions(MPSItems.NETHERITE_PLATING_MODULE.get(), "Pantserplaat gemaak van Netherite");

        // Energy Shield -------------------------------------------------------------------------------
        add(MPSItems.ENERGY_SHIELD_MODULE.get(), "Energie skild");
        addItemDescriptions(MPSItems.ENERGY_SHIELD_MODULE.get(), "Energieskerms is baie ligter as plating, maar verbruik energie.");

        // Cosmetic ====================================================================================
        // Transparent Armor ---------------------------------------------------------------------------
        add(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), "Aktiewe kamoeflering");
        addItemDescriptions(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), "Straal 'n hologram van jou omgewing uit om jouself amper onmerkbaar te maak.");

        // Energy Generation ===========================================================================
        // Coal Generator ------------------------------------------------------------------------------
        // TODO:
//        add(MPSItems.COAL_GENERATOR_MODULE.get(), "Coal Generator");
//
//        addItemDescriptions(MPSItems.COAL_GENERATOR_MODULE.get(), "Generate power with solid fuels");

        // Solar Generator -----------------------------------------------------------------------------
        add(MPSItems.SOLAR_GENERATOR_MODULE.get(), "Sonkragopwekker");
        addItemDescriptions(MPSItems.SOLAR_GENERATOR_MODULE.get(), "Laat die son jou avonture aandryf.");

        // High Efficiency Solar Generator -------------------------------------------------------------
        add(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), "Sonkragopwekker met hoë doeltreffendheid");
        addItemDescriptions(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), "'n Sonkragopwekker met 3 keer die kragopwekking van die standaard sonkragopwekker.");

        // Kinetic Generator ---------------------------------------------------------------------------
        add(MPSItems.KINETIC_GENERATOR_MODULE.get(), "Kinetiese kragopwekker");
        addItemDescriptions(MPSItems.KINETIC_GENERATOR_MODULE.get(), "Wek krag op met jou beweging.");

        // Thermal Generator ---------------------------------------------------------------------------
        add(MPSItems.THERMAL_GENERATOR_MODULE.get(), "Termiese kragopwekker");
        addItemDescriptions(MPSItems.THERMAL_GENERATOR_MODULE.get(), "Genereer krag uit uiterste hoeveelhede hitte.");

        // Environmental ===============================================================================
        // Auto Feeder ---------------------------------------------------------------------------------
        add(MPSItems.AUTO_FEEDER_MODULE.get(),  "Outo-voerder");
        addItemDescriptions(MPSItems.AUTO_FEEDER_MODULE.get(), "Wanneer jy honger is, sal hierdie module die onderste linkerkantste kositem uit jou voorraad gryp en dit aan jou voer en die res stoor vir later.");

        // Cooling System ------------------------------------------------------------------------------
        add(MPSItems.COOLING_SYSTEM_MODULE.get(), "Verkoelingstelsel");
        addItemDescriptions(MPSItems.COOLING_SYSTEM_MODULE.get(), "Koel hitteproduserende modules vinniger af. Voeg 'n vloeistoftenkmodule en vloeistof by om prestasie te verbeter.");

        // Fluid Tank  ---------------------------------------------------------------------------------
        add(MPSItems.FLUID_TANK_MODULE.get(), "Vloeistof tenk");
        addItemDescriptions(MPSItems.FLUID_TANK_MODULE.get(), "Stoor vloeistof om die werkverrigting van die verkoelingstelsel te verbeter.");

        // Mob Repulsor --------------------------------------------------------------------------------
        add(MPSItems.MOB_REPULSOR_MODULE.get(), "Mob Repulsor");
        addItemDescriptions(MPSItems.MOB_REPULSOR_MODULE.get(), "Stoot skares van jou af weg wanneer dit geaktiveer word, maar dreineer voortdurend krag. Dit word sterk aanbeveel dat u hierdie module op 'n sleutelbinding stel as gevolg van die hoë energietrekking.");

        // Water Electrolyzer --------------------------------------------------------------------------
        add(MPSItems.WATER_ELECTROLYZER_MODULE.get(), "Water Elektrolyzer");
        addItemDescriptions(MPSItems.WATER_ELECTROLYZER_MODULE.get(), "As u nie meer lug het nie, sal hierdie module die water rondom u stoot en 'n klein borrel elektroliseer om van asem te haal.");

        // Mining Enchantments =========================================================================
        // Aqua Affinity -------------------------------------------------------------------------------
        add(MPSItems.AQUA_AFFINITY_MODULE.get(), "Aqua Affiniteit");
        addItemDescriptions(MPSItems.AQUA_AFFINITY_MODULE.get(), "Verminder die spoedboete vir die gebruik van jou gereedskap onder water.");

        // Silk Touch Enchantment ----------------------------------------------------------------------
        add(MPSItems.SILK_TOUCH_MODULE.get(), "Silk Touch Betowering");
        addItemDescriptions(MPSItems.SILK_TOUCH_MODULE.get(), "'N Module wat die betowering van die Silk Touch bied");

        // Fortune Enchantment -------------------------------------------------------------------------
        add(MPSItems.FORTUNE_MODULE.get(), "Fortuin betowering");
        addItemDescriptions(MPSItems.FORTUNE_MODULE.get(), "'n Module wat die fortuin betowering bied.");

        // Mining Enhancements =========================================================================
        // Vein Miner ----------------------------------------------------------------------------------
        add(MPSItems.VEIN_MINER_MODULE.get(), "Aar mynwerker");
        addItemDescriptions(MPSItems.VEIN_MINER_MODULE.get(), "'n Module vir die ontginning van ertsare");

        // Tunnel Bore ---------------------------------------------------------------------------------
        add(MPSItems.TUNNEL_BORE_MODULE.get(),  "Tonnel boor");
        addItemDescriptions(MPSItems.TUNNEL_BORE_MODULE.get(), "'N Module waarmee die piksteelmodule gelyktydig 5x5-reeksblokke kan ontgin.");

        // Selective Miner -----------------------------------------------------------------------------
        add(MPSItems.SELECTIVE_MINER.get(), "Selektiewe mynwerker");
        addItemDescriptions(MPSItems.SELECTIVE_MINER.get(), "Breek blokke soortgelyk aan die aarmyner, maar selektief. Skuif en klik om bloktipe te kies.");

        // Movement ====================================================================================
        // Blink Drive ---------------------------------------------------------------------------------
        add(MPSItems.BLINK_DRIVE_MODULE.get(), "Blink ry");
        addItemDescriptions(MPSItems.BLINK_DRIVE_MODULE.get(), "Kom van punt A na punt C via punt B, waar punt B 'n vou in ruimte en tyd is.");

        // Uphill Step Assist --------------------------------------------------------------------------
        add(MPSItems.CLIMB_ASSIST_MODULE.get(), "Opdraande staphulp");
        addItemDescriptions(MPSItems.CLIMB_ASSIST_MODULE.get(), "Met 'n paar toegewyde servo's kan u moeiteloos 1 m hoë rande opstap.");

        // Dimensional Tear Generator ------------------------------------------------------------------
        add(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), "Dimensionele skeurgenerator");
        addItemDescriptions(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), "Genereer 'n traan in die ruimte-tyd kontinuum wat die speler sal teleporteer na sy relatiewe koördinate in die neter of oorwêreld.");

        // Flight Control ------------------------------------------------------------------------------
        add(MPSItems.FLIGHT_CONTROL_MODULE.get(), "Vlugbeheer");
        addItemDescriptions(MPSItems.FLIGHT_CONTROL_MODULE.get(), "'n Geïntegreerde beheerkring om jou te help om beter te vlieg. Druk Z om af te gaan.");

        // Glider --------------------------------------------------------------------------------------
        add(MPSItems.GLIDER_MODULE.get(), "Sweeftuig");
        addItemDescriptions(MPSItems.GLIDER_MODULE.get(), "Pak 'n paar vlerke aan om afwaarts te draai in voorwaartse momentum. Druk sluip + vorentoe terwyl u val om te aktiveer.");

        // Jet Boots -----------------------------------------------------------------------------------
        add(MPSItems.JETBOOTS_MODULE.get(), "Jet stewels");
        addItemDescriptions(MPSItems.JETBOOTS_MODULE.get(), "Straalstewels is nie so sterk soos 'n jetpack nie, maar hulle moet ten minste sterk genoeg wees om swaartekrag teen te werk.");

        // Jetpack -------------------------------------------------------------------------------------
        add(MPSItems.JETPACK_MODULE.get(), "Jetpack");
        addItemDescriptions(MPSItems.JETPACK_MODULE.get(), "Met 'n jetpack moet u onbepaald kan spring, of ten minste totdat u nie meer krag het nie.");

        // Jump Assist ---------------------------------------------------------------------------------
        add(MPSItems.JUMP_ASSIST_MODULE.get(), "Springhulp");
        addItemDescriptions(MPSItems.JUMP_ASSIST_MODULE.get(), "Nog 'n stel servomotors om jou te help om hoër te spring.");

        // Parachute -----------------------------------------------------------------------------------
        add(MPSItems.PARACHUTE_MODULE.get(), "Valskerm");
        addItemDescriptions(MPSItems.PARACHUTE_MODULE.get(), "Voeg 'n valskerm by om jou afkoms te vertraag. Aktiveer deur te druk sluip (standaard na Shift) in die lug.");

        // Shock Absorber ------------------------------------------------------------------------------
        add(MPSItems.SHOCK_ABSORBER_MODULE.get(), "Skokbreker");
        addItemDescriptions(MPSItems.SHOCK_ABSORBER_MODULE.get(), "Met 'n paar servo's, vere en vulling behoort jy 'n gedeelte van herfsskade te kan negeer.");

        // Sprint Assist -------------------------------------------------------------------------------
        add(MPSItems.SPRINT_ASSIST_MODULE.get(), "Sprint Assist");
        addItemDescriptions(MPSItems.SPRINT_ASSIST_MODULE.get(), "A set of servo motors to help you sprint (double-tap forward) and walk faster.");

        // Swim Boost ----------------------------------------------------------------------------------
        add(MPSItems.SWIM_BOOST_MODULE.get(), "Swem hupstoot");
        addItemDescriptions(MPSItems.SWIM_BOOST_MODULE.get(), "Deur 'n ioonstoot vir onderwatergebruik op te knap, kan jy dalk ekstra vorentoe (of agtertoe) stoot byvoeg wanneer jy onder water is.");

        // Special =====================================================================================
        // Active Camouflage ---------------------------------------------------------------------------
        add(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Deursigtige wapenrusting");
        addItemDescriptions(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Maak die item deursigtig, sodat jy jou vel kan wys sonder om pantser te verloor.");

        // Magnet --------------------------------------------------------------------------------------
        add(MPSItems.MAGNET_MODULE.get(), "Magneet");
        addItemDescriptions(MPSItems.MAGNET_MODULE.get(), "Genereer 'n magnetiese veld wat sterk genoeg is om items na die speler te lok.         WAARSKUWING: Hierdie module dreineer voortdurend krag. Skakel dit af as dit nie nodig is nie.");

        // Piglin Pacification Module ------------------------------------------------------------------
        add(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), "Piglin Pasifisering Module");
        addItemDescriptions(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), "Eenvoudige module om Piglins neutraal te maak asof hy goue pantser dra");

        // Vision ======================================================================================
        // Binoculars ----------------------------------------------------------------------------------
        add(MPSItems.BINOCULARS_MODULE.get(), "Verkyker");
        addItemDescriptions(MPSItems.BINOCULARS_MODULE.get(),  "Met die probleme wat Optifine die afgelope tyd teister, het jy besluit om daardie Zoom-vermoë in eie hande te neem.");

        // Night Vision --------------------------------------------------------------------------------
        add(MPSItems.NIGHT_VISION_MODULE.get(), "Nagsig");
        addItemDescriptions(MPSItems.NIGHT_VISION_MODULE.get(), "'N Paar uitgebreide visiebril om u snags en onder water te sien.");

        // Tools =======================================================================================
        // Axe -----------------------------------------------------------------------------------------
        add(MPSItems.AXE_MODULE.get(),  "Byl");
        addItemDescriptions(MPSItems.AXE_MODULE.get(), "Byle is meestal om bome te kap.");

        // Diamond Drill Upgrade -----------------------------------------------------------------------
        add(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), "Opgradering van diamantboor");
        addItemDescriptions(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), "Voeg diamante by sodat u piksteelmodule Obsidian kan myn. *VEREIS PIKSTEELMODULE OM TE WERK*");

        // Flint and Steel -----------------------------------------------------------------------------
        add(MPSItems.FLINT_AND_STEEL_MODULE.get(), "Vuursteen en staal");
        addItemDescriptions(MPSItems.FLINT_AND_STEEL_MODULE.get(),"'N Draagbare ontsteker wat vuur skep deur die krag van energie.");

        // Rototiller ----------------------------------------------------------------------------------
        add(MPSItems.HOE_MODULE.get(), "Rototiller");
        addItemDescriptions(MPSItems.HOE_MODULE.get(), "'N Outomatiese bewerkingsbyvoeging om dit maklik te maak om groot stukke grond tegelyk te bewerk.");

        // Leaf Blower ---------------------------------------------------------------------------------
        add(MPSItems.LEAF_BLOWER_MODULE.get(), "Blaar Blaser");
        addItemDescriptions(MPSItems.LEAF_BLOWER_MODULE.get(), "Skep 'n stortvloed lug om plante uit die grond en blare van bome af te slaan.");

        // Lux Capacitor -------------------------------------------------------------------------------
        add(MPSItems.LUX_CAPACITOR_MODULE.get(), "Lux Kapasitor");
        addItemDescriptions(MPSItems.LUX_CAPACITOR_MODULE.get(), "Begin 'n feitlik oneindige aantal aantreklike ligbronne aan die muur.");

        // Pickaxe -------------------------------------------------------------------------------------
        add(MPSItems.PICKAXE_MODULE.get(), "Piksteel");
        addItemDescriptions(MPSItems.PICKAXE_MODULE.get(), "Optel is goed vir harder materiale soos klip en erts.");

        // Shears --------------------------------------------------------------------------------------
        add(MPSItems.SHEARS_MODULE.get(), "Skêr");
        addItemDescriptions(MPSItems.SHEARS_MODULE.get(), "Sny deur blare, wol en rankplante.");

        // Shovel --------------------------------------------------------------------------------------
        add(MPSItems.SHOVEL_MODULE.get(), "Skopgraaf");
        addItemDescriptions(MPSItems.SHOVEL_MODULE.get(), "Skoppe is goed vir sagte materiale soos vuil en sand.");

        // Debug =======================================================================================
        // TODO

        // Weapons =====================================================================================
        // Blade Launcher ------------------------------------------------------------------------------
        add(MPSItems.BLADE_LAUNCHER_MODULE.get(),  "Blade Lanseerder");
        addItemDescriptions(MPSItems.BLADE_LAUNCHER_MODULE.get(),  "Begin 'n draaiende lem van die dood (of skeer).");

        // Lightning Summoner ------------------------------------------------------------------------
        add(MPSItems.LIGHTNING_MODULE.get(), "Weerlig dagvaarding");
        addItemDescriptions(MPSItems.LIGHTNING_MODULE.get(), "Hiermee kan u weerlig ontbied vir 'n groot energiekoste.");

        // Melee Assist --------------------------------------------------------------------------------
        add(MPSItems.MELEE_ASSIST_MODULE.get(), "Melee Assist");
        addItemDescriptions(MPSItems.MELEE_ASSIST_MODULE.get(), "'n Baie eenvoudiger byvoeging, laat jou kraggereedskappons harder slaan.");

        // Plasma Cannon -------------------------------------------------------------------------------
        add(MPSItems.PLASMA_CANNON_MODULE.get(), "Plasma-kanon");
        addItemDescriptions(MPSItems.PLASMA_CANNON_MODULE.get(), "Gebruik elektriese boë in 'n insluitingsveld om lug na 'n plasma te verhit en dit by vyande te lanseer.");

        // Railgun -------------------------------------------------------------------------------------
        add(MPSItems.RAILGUN_MODULE.get(), "Spoorgeweer");
        addItemDescriptions(MPSItems.RAILGUN_MODULE.get(), "'N Samestelling wat 'n projektiel versnel tot supersoniese snelhede met behulp van magnetiese krag. Swaar terugslag.");
    }

    @Override
    public void addBlocks() {
        // Lux Capacitor -------------------------------------------------------------------------------
        add(MPSBlocks.LUX_CAPACITOR_BLOCK.get(), "Lux Kapasitor");

        // Power Armor Tinker Table --------------------------------------------------------------------
        add(MPSBlocks.TINKER_TABLE_BLOCK.get(), "Power Armor Tinker Tafel");
    }

    @Override
    public void addGui() {
        // Armor ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_ARMOR, "Wapenrusting");

        // Color ---------------------------------------------------------------------------------------
        add(MPSConstants.GUI_COLOR_PREFIX, "Kleur:");

        // Compatible Modules -------------------------------------------------------------------------
        add(MPSConstants.GUI_COMPATIBLE_MODULES, "Versoenbare modules");

        // Energy Storage ------------------------------------------------------------------------------
        add(MPSConstants.GUI_ENERGY_STORAGE, "Energie berging");

        // Equipped Totals -----------------------------------------------------------------------------
        add(MPSConstants.GUI_EQUIPPED_TOTALS, "Toegeruste totale");

        // Installed Modules ---------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALLED_MODULES,  "Geïnstalleerde modules");

        // Show on HUD ---------------------------------------------------------------------------------
        add(MPSConstants.GUI_SHOW_ON_HUD, "Wys op HUD:");

        // Install/Salvage -----------------------------------------------------------------------------
        add(MPSConstants.GUI_INSTALL_SALVAGE, "Installeer / berging");

        // Keybinds ------------------------------------------------------------------------------------
        add(MPSConstants.GUI_KEYBINDS, "Sleutelbindings");

        // Module Tweak --------------------------------------------------------------------------------
        add(MPSConstants.GUI_MODULE_TWEAK, "Module Tweak");

        // Visual --------------------------------------------------------------------------------------
        add(MPSConstants.GUI_VISUAL,  "Visuele");

        // Tinker --------------------------------------------------------------------------------------
        add(MPSConstants.GUI_TINKER_TABLE, "Tinker");

        // Modular Item Inventory ----------------------------------------------------------------------
        add(MPSConstants.GUI_MODULAR_ITEM_INVENTORY, "Modulêre item inventaris");
    }

    @Override
    public void addModuleTradeoff() {
        // Activation Percent --------------------------------------------------------------------------
        addTradeoff(MPSConstants.ACTIVATION_PERCENT, "Aktivering persent");

        // Alpha ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ALPHA, "Alfa");

        // Amperage ------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.AMPERAGE, "Amperage");

        // Block Limit ---------------------------------------------------------------------------------
        addTradeoff(MPSConstants.SELECTIVE_MINER_LIMIT, "Blok limiet");

        // Armor (Energy) ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_VALUE_ENERGY, "Wapenrusting (Energie)");

        // Energy Per Damage ---------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_ENERGY_CONSUMPTION, "Energie per skade");

        // Armor (Physical) ----------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_VALUE_PHYSICAL, "Wapenrusting (Fisies)");

        // pts -----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.ARMOR_POINTS, "pts");

        // Auto-Feeder Efficiency ----------------------------------------------------------------------
        addTradeoff(MPSConstants.EATING_EFFICIENCY, "Auto-Feeder Efficiency");

        // Range ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.BLINK_DRIVE_RANGE, "Range");

        // Blue ----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.BLUE, "Blou");

        // Lux Capacitor Blue Hue ----------------------------------------------------------------------
        addTradeoff(MPSConstants.BLUE_HUE, "Lux Kapasitor Blou Hue");

        // Carry-through -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.CARRY_THROUGH, "Deurvoer");

        // Compensation --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.COMPENSATION, "Vergoeding");

        // Cooling Bonus -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.COOLING_BONUS, "Verkoeling bonus");

        // Daytime Energy Generation -------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION_DAY, "Bedags energieopwekking");

        // Daytime Heat Generation ---------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION_DAY, "Hitte opwekking gedurende die dag");

        // Diameter ------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.DIAMETER, "Deursnee");

        // Efficiency ----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.EFFICIENCY, "Doeltreffendheid");

        // Enchantment Level ---------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENCHANTMENT_LEVEL, "Betoweringsvlak");

        // Energy Consumption --------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_CONSUMPTION, "Energieverbruik");

        // Energy Generated ----------------------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATED, "Energie opgewek");

        // Energy Per Block Per Second -----------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION, "energie per blok per sekonde");

        // FOV multiplier ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FIELD_OF_VIEW, "FOV vermenigvuldiger");

        // Field of View -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FOV, "Field of View");

        // Field Strength ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.MODULE_FIELD_STRENGTH, "Veld sterkte");

        // Tank Size -----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FLUID_TANK_SIZE, "Tenk grootte");

        // Fortune Energy Consumption ------------------------------------------------------------------
        addTradeoff(MPSConstants.FORTUNE_ENERGY_CONSUMPTION, "Fortuin Energieverbruik");

        // Fortune Level -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FORTUNE_ENCHANTMENT_LEVEL, "Fortuin vlak");

        // Green ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.GREEN, "Groen");

        // Lux Capacitor Green Hue ---------------------------------------------------------------------
        addTradeoff(MPSConstants.GREEN_HUE, "Lux Kapasitor Groen Hue");

//        // Harvest Speed -------------------------------------------------------------------------------
//        addTradeoff(harvSpeed", "Harvest Speed");

        // Harvest Speed -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.HARVEST_SPEED, "Oes spoed");

        // Heat Activation Percent ---------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_ACTIVATION_PERCENT, "Hitte-aktivering persent");

        // Heat Emission -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_EMISSION, "Hitte-emissie");

        // Heat Generation -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION, "Hitte opwekking");

        // Impact --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.IMPACT, "Impak");

        // Jetboots Thrust -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.JETBOOTS_THRUST, "Jetboots stoot");

        // Jetpack Thrust ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.JETPACK_THRUST, "Jetpack stoot");

        // Knockback Resistance ------------------------------------------------------------------------
        addTradeoff(MPSConstants.KNOCKBACK_RESISTANCE, "Terugslagweerstand");

//        // Maximum Heat --------------------------------------------------------------------------------
//        addTradeoff(maxHeat", "Maximum Heat");

        // Melee Damage --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.PUNCH_DAMAGE, "Melee Skade");

        // Melee Knockback -----------------------------------------------------------------------------
        addTradeoff(MPSConstants.PUNCH_KNOCKBACK, "Melee Terugslag");

        // Movement Resistance -------------------------------------------------------------------------
        addTradeoff(MPSConstants.MOVEMENT_RESISTANCE, "Beweging weerstand");

        // Multiplier ----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.MULTIPLIER, "Vermenigvuldiger");

        // Nighttime Energy Generation -----------------------------------------------------------------
        addTradeoff(MPSConstants.ENERGY_GENERATION_NIGHT, "Nag energie opwekking");

        // Nighttime Heat Generation -------------------------------------------------------------------
        addTradeoff(MPSConstants.HEAT_GENERATION_NIGHT, "Nag hitte opwekking");

        // Lux Capacitor Opacity -----------------------------------------------------------------------
        addTradeoff(MPSConstants.OPACITY, "Lux Kapasitor Ondeursigtigheid");

        // Overclock -----------------------------------------------------------------------------------
        addTradeoff(MPSConstants.OVERCLOCK, "Oorklok");

        // Plasma Damage At Full Charge ----------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, "Plasmaskade teen volle lading");

        // Plasma Energy Per Tick ----------------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, "Plasma-energie per bosluis");

        // Plasma Explosiveness ------------------------------------------------------------------------
        addTradeoff(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS, "Plasma plofbaarheid");

        // Power ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.POWER, "Krag");

        // Radius --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RADIUS, "Radius");

        // Railgun Energy Cost -------------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_ENERGY_COST, "Railgun Energie Koste");

        // Railgun Heat Emission -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_HEAT_EMISSION, "Railgun hitte-emissie");

        // Railgun Total Impulse -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RAILGUN_TOTAL_IMPULSE, "Railgun Totale Impuls");

        // Range ---------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RANGE, "Reeks");

        // Red -----------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.RED, "Rooi");

        // Lux Capacitor Red Hue -----------------------------------------------------------------------
        addTradeoff(MPSConstants.RED_HUE, "Lux Kondensator Rooi Hue");

        // Silk Touch Energy Consumption ---------------------------------------------------------------
        addTradeoff(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION, "Silk Touch Energieverbruik");

        // Spinning Blade Damage -----------------------------------------------------------------------
        addTradeoff(MPSConstants.BLADE_DAMAGE, "Draai lem skade");

        // Sprint Assist -------------------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_ASSIST, "Sprint Assist");

        // Sprint Energy Consumption -------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_ENERGY_CONSUMPTION, "Sprint Energieverbruik");

        // Sprint Exhaustion Compensation --------------------------------------------------------------
        addTradeoff(MPSConstants.FOOD_COMPENSATION, "Sprint uitputting vergoeding");

        // Sprint Speed Multiplier ---------------------------------------------------------------------
        addTradeoff(MPSConstants.SPRINT_SPEED_MULTIPLIER, "Sprint spoed vermenigvuldiger");

//        // Energy Generation ---------------------------------------------------------------------------
//        addTradeoff(thermalEnergyGen", "Energy Generation");

        // Thrust --------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.THRUST, "Stoot");

        // Underwater Movement Boost -------------------------------------------------------------------
        addTradeoff(MPSConstants.SWIM_BOOST_AMOUNT, "Onderwater beweging hupstoot");

        // Verticality ---------------------------------------------------------------------------------
        addTradeoff(MPSConstants.VERTICALITY, "Vertikaliteit");

        // Voltage -------------------------------------------------------------------------------------
        addTradeoff(MPSConstants.VOLTAGE, "Spanning");

        // Walking Assist ------------------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_ASSISTANCE, "Stap hulp");

        // Walking Energy Consumption ------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_ENERGY_CONSUMPTION, "Stap energieverbruik");

        // Walking Speed Multiplier --------------------------------------------------------------------
        addTradeoff(MPSConstants.WALKING_SPEED_MULTIPLIER, "Loop spoed vermenigvuldiger");

        // Y-look ratio --------------------------------------------------------------------------------
        addTradeoff(MPSConstants.FLIGHT_VERTICALITY, "Y-kyk verhouding");
    }

    @Override
    public void addKeybinds() {
        // Go down -------------------------------------------------------------------------------------
        add(MPSConstants.GO_DOWN_KEY, "Gaan af (MPS)n");

        // Cycle Tool Backward (MPS) -------------------------------------------------------------------
        add(MPSConstants.CYCLE_TOOL_BACKWARD, "Fietsgereedskap agteruit (MPS)");

        // Cycle Tool Forward (MPS) --------------------------------------------------------------------
        add(MPSConstants.CYCLE_TOOL_FORWARD, "Fietsgereedskap vorentoe (MPS)");

        // Open Keybind Gui ----------------------------------------------------------------------------
        add(MPSConstants.OPEN_KEYBIND_GUI, "Oop Kosmetiese GUI (MPS)");

        // Open Cosmetic Gui ---------------------------------------------------------------------------
        add(MPSConstants.OPEN_COSMETIC_GUI, "Open Cosmetic GUI (MPS)");

        // Open Module Tweak Gui -----------------------------------------------------------------------
        add(MPSConstants.OPEN_MODULE_TWEAK_GUI, "Maak module kiaat GUI (MPS) oop");

        // Open Install/Salvage Gui --------------------------------------------------------------------
        add(MPSConstants.OPEN_INSTALL_SALVAGE_GUI, "Maak installeer / berging GUI (MPS) oop");

        // Toggle Module -------------------------------------------------------------------------------
        add(MPSConstants.TOGGLE_MODULE, "Wissel %s");
    }

    @Override
    public void addModels() {
        // Generic armor model parts ===================================================================
        // Head ----------------------------------------------------------------------------------------
        addJavaModelPart("head", "Head", "Kop");

        // Body ----------------------------------------------------------------------------------------
        addJavaModelPart("chest", "Body", "Liggaam");

        // Chest ---------------------------------------------------------------------------------------
        addJavaModelPart("", "chest", "Bors");

        // Left Arm ------------------------------------------------------------------------------------
        addJavaModelPart("chest", "LeftArm", "Linkerarm");

        // Right Arm -----------------------------------------------------------------------------------
        addJavaModelPart("chest", "RightArm", "Regterarm");

        // Legs ----------------------------------------------------------------------------------------
        addJavaModelPart("", "legs","Bene");

        // Left Leg ------------------------------------------------------------------------------------
        addJavaModelPart("legs", "LeftLeg","LeftLeg");

        // Right Leg -----------------------------------------------------------------------------------
        addJavaModelPart("legs", "RightLeg", "RightLeg");

        // Feet ----------------------------------------------------------------------------------------
        addJavaModelPart("", "feet", "Voete");

        // Left Foot -----------------------------------------------------------------------------------
        addJavaModelPart("feet", "LeftFoot", "Linkervoet");

        // Right Foot ----------------------------------------------------------------------------------
        addJavaModelPart("feet", "RightFoot", "Regtervoet");

        // Armor skins ---------------------------------------------------------------------------------
        add("javaModel.citizenjoe_armorskin.specName", "Burger Joe Pantservel");

        // Default ArmorSkin ---------------------------------------------------------------------------
        add("javaModel.default_armorskin.specName", "Standaard pantservel");

        // Power Fist ==================================================================================
        add("javaModel.powerfist.specName", "Power Fist");

        // Main Arm ------------------------------------------------------------------------------------
        addPowerfistPart("mainarm", "Hoofarm");

        // Armor Right ---------------------------------------------------------------------------------
        addPowerfistPart("armorright", "Pantser reg");

        // Armor Left ----------------------------------------------------------------------------------
        addPowerfistPart("armorleft", "Wapenrusting links");

        // Wrist Top Right -----------------------------------------------------------------------------
        addPowerfistPart("wristtopright", "Pols regs bo");

        // Wrist Top Left ------------------------------------------------------------------------------
        addPowerfistPart("wristtopleft", "Pols links bo");

        // Wrist Bottom Right --------------------------------------------------------------------------
        addPowerfistPart("wristbottomright", "Pols regs onder");

        // Wrist Bottom Left ---------------------------------------------------------------------------
        addPowerfistPart("wristbottomleft", "Pols links onder");

        // Finger Guard --------------------------------------------------------------------------------
        addPowerfistPart("fingerguard", "Vingerwag");

        // Crystal Holder ------------------------------------------------------------------------------
        addPowerfistPart("crystalholder", "Kristalhouer");

        // Crystal -------------------------------------------------------------------------------------
        addPowerfistPart("crystal", "Kristal");

        // Support Right 1 -----------------------------------------------------------------------------
        addPowerfistPart("supportright1", "Ondersteun reg 1");

        // Support Right 2 -----------------------------------------------------------------------------
        addPowerfistPart("supportright2", "Ondersteun reg 2");

        // Support Right 3 -----------------------------------------------------------------------------
        addPowerfistPart("supportright3", "Ondersteun reg 3");

        // Support Right 4 -----------------------------------------------------------------------------
        addPowerfistPart("supportright4", "Ondersteun reg 4");

        // Support Right 5 -----------------------------------------------------------------------------
        addPowerfistPart("supportright5", "Ondersteuning Reg 5");

        // Support Base Right --------------------------------------------------------------------------
        addPowerfistPart("supportbaseright", "Ondersteuningsbasis reg");

        // Support Right Front -------------------------------------------------------------------------
        addPowerfistPart("supportrightfront", "Ondersteun regs voor");

        // Support Base Left ---------------------------------------------------------------------------
        addPowerfistPart("supportbaseleft", "Ondersteuningsbasis links");

        // Support Left Front --------------------------------------------------------------------------
        addPowerfistPart("supportleftfront", "Ondersteun linkerfront");

        // Support Left 1 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft1", "Ondersteuning links 1");

        // Support Left 2 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft2", "Ondersteuning Links 2");

        // Support Left 3 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft3", "Ondersteuning links 3");

        // Support Left 4 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft4", "Ondersteuning Links 4");

        // Support Left 5 ------------------------------------------------------------------------------
        addPowerfistPart("supportleft5", "Ondersteuning links 5");

        // Palm ----------------------------------------------------------------------------------------
        addPowerfistPart("palm", "Palm");

        // Index 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.index1",  "Indeks 1");

        // Index 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.index1.index2", "Indeks 2");

        // Middle Finger 1 -----------------------------------------------------------------------------
        addPowerfistPart("palm.middlefinger1", "Middelvinger 1");

        // Middle Finger 2 -----------------------------------------------------------------------------
        addPowerfistPart("palm.middlefinger1.middlefinger2", "Middelvinger 2");

        // Ring Finger 1 -------------------------------------------------------------------------------
        addPowerfistPart("palm.ringfinger1", "Ringvinger 1");

        // Ring Finger 2 -------------------------------------------------------------------------------
        addPowerfistPart("palm.ringfinger1.ringfinger2", "Ringvinger 2");

        // Pinky 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.pinky1", "Pinky 1");

        // Pinky 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.pinky1.pinky2", "Pinky 2");

        // Thumb 1 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.thumb1", "Thumb 1");

        // Thumb 2 -------------------------------------------------------------------------------------
        addPowerfistPart("palm.thumb1.thumb2", "Duim 2");

        // MPS Armor ===================================================================================
        // Helm ----------------------------------------------------------------------------------------
        String MPS_HELM = addOBJModel("mps_helm", "MPS Helm");

        // Helmet --------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_main", "Helm");

        // Tubes ---------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tubes", "Buise");

        // Left Tube Entry -----------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tube_entry1", "Toegang tot die linkerbuis");

        // Right Tube Entry ----------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "helm_tube_entry2", "Regte buisinskrywing");

        // Visor ---------------------------------------------------------------------------------------
        addOBJModelPart(MPS_HELM, "visor", "Visor");

        // MPS Chestplate ------------------------------------------------------------------------------
        String MPS_CHEST = addOBJModel("mps_chest", "MPS Borsplaat");

        // Chest Plating -------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "chest_main", "Bors platering");

        // Chest Padding -------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "chest_padding", "Bors vulling");

        // Belt Crystal --------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "crystal_belt", "Gordel kristal");

        // Backpack ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "backpack", "Rugsak");

        // Belt ----------------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "belt", "Gordel");

        // Accessory ----------------------------------------------------------------------------------
        addOBJModelPart(MPS_CHEST, "polySurface36",  "Bykomstigheid");

        // MPS Arms ------------------------------------------------------------------------------------
        String MPS_ARMS = addOBJModel("mps_arms", "MPS Arms");

        // Left Arm ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "arms2", "Linkerarm");

        // Right Arm -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "arms3", "Regterarm");

        // Left Shoulder Crystal -----------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "crystal_shoulder_1", "Linker skouer kristal");

        // Right Shoulder Crystal ----------------------------------------------------------------------
        addOBJModelPart(MPS_ARMS, "crystal_shoulder_2", "Regter skouer kristal");

        // MPS Jetpack ---------------------------------------------------------------------------------
        String JETPACK = addOBJModel("jetpack", "MPS Jetpack");

        // Main ----------------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "default","Belangrikste");

        // Secondary -----------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "jetpack5", "Sekondêre");

        // Lights --------------------------------------------------------------------------------------
        addOBJModelPart(JETPACK, "jetpack_glow", "Ligte");

        // MPS Pantaloons ------------------------------------------------------------------------------
        String MPS_PANTALOONS = addOBJModel("mps_pantaloons", "MPS Pantaloons");

        // Right Leg -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_PANTALOONS, "leg1", "Regterbeen");

        // Left Leg ------------------------------------------------------------------------------------
        addOBJModelPart(MPS_PANTALOONS, "leg2", "Linkerbeen");

        // MPS Boots -----------------------------------------------------------------------------------
        String MPS_BOOTS = addOBJModel("mps_boots", "MPS-stewels");

        // Right Boot ----------------------------------------------------------------------------------
        addOBJModelPart(MPS_BOOTS, "boots1", "Regte kattebak");

        // Left Boot -----------------------------------------------------------------------------------
        addOBJModelPart(MPS_BOOTS, "boots2", "Linker kattebak");

        // Armor 2 =====================================================================================
        String ARMOR2 = addOBJModel("armor2", "Wapenrusting 2");

        // Helmet --------------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "helmetmain", "Helm");

        // Helmet Tubes and Visor ----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "helmetglow1", "Helmbuise en visier");

        // Front Chest Protection ----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestmain", "Voorste bors beskerming");

        // Chest Base ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestgray", "Bors basis");

        // Back Chest Protection -----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestback1", "Rugbors beskerming");

        // Spinal Power Crystals -----------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestglowback", "Spinale kragkristalle");

        // Front Power Crystal -------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "chestglowfront", "Voorste kragkristal");

        // Arm and Shoulder Protection -----------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armmain", "Arm- en skouerbeskerming");

        // Arm and Shoulder Protection 1 ---------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armmain1", "Arm- en skouerbeskerming");

        // Lef Boot Glow -------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootglow", "Linker stewel gloei");

        // Right Boot Glow -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootglow1", "Regter kattebak gloei");

        // Left Boot -----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootmain", "Linker kattebak");

        // Right Boot ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "bootmain1", "Regte kattebak");

        // Shoulder Light ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armglow", "Skouer lig");

        // Shoulder Light 1 -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armglow1", "Skouer lig");

        // Arm Base ------------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armgray", "Arm basis");

        // Arm Base 1 ----------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "armgray1", "Arm basis");

        // Left Leg Base -------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "leggray", "Linkerbeenbasis");

        // Right Leg Base ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "leggray1", "Regterbeen basis");

        // Left Leg Protection -------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legbit", "Beskerming van die linkerbeen");

        // Right Leg Protection ------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legbit1", "Regterbeenbeskerming");

        // Left Leg Light ------------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legglow", "Linkerbeenlig");

        // Right Leg Light -----------------------------------------------------------------------------
        addOBJModelPart(ARMOR2, "legglow1", "Regterbeenlig");
    }
}

