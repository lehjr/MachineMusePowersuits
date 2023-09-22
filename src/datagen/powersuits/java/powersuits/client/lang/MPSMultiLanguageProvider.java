package powersuits.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.powersuits.common.base.MPSItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import numina.client.lang.Localizations;
import numina.client.lang.MulitiLanguageProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MPSMultiLanguageProvider extends MulitiLanguageProvider {
    public MPSMultiLanguageProvider(DataGenerator gen, String modid) {
        super(gen, modid);
    }

    @Override
    public void run(CachedOutput pOutput) throws IOException {
        addItemGroup();
        addModularItems();
        addModules();




        super.run(pOutput);
    }

    void addItemGroup() {
        addTranslationTopAll("itemGroup.powersuits", "Modular Powersuits");
    }

    void addModularItems() {
        // Helmet --------------------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energiehelm");
        tmp.put(Localizations.EN_US, "Power Armor Helmet");
        tmp.put(Localizations.FR_FR, "Casque d'Armure énergétique");
        tmp.put(Localizations.PT_BR, "Capacete do Exotraje");
        tmp.put(Localizations.PT_PT, "Capacete de Armadura de Poder");
        tmp.put(Localizations.RU_RU, "Силовой шлем");
        tmp.put(Localizations.ZH_CN, "动力头盔");
        add(MPSItems.POWER_ARMOR_HELMET.get(), tmp);

        // Chestplate ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energiebrustplatte");
        tmp.put(Localizations.EN_US, "Power Armor Chestplate");
        tmp.put(Localizations.FR_FR, "Plastron d'Armure énergétique");
        tmp.put(Localizations.PT_BR, "Peitoral do Exotraje");
        tmp.put(Localizations.PT_PT, "Chapa de Proteção de Armadura");
        tmp.put(Localizations.RU_RU, "Силовая кираса");
        tmp.put(Localizations.ZH_CN, "动力护甲");
        add(MPSItems.POWER_ARMOR_CHESTPLATE.get(), tmp);

        // Leggings ------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energiehose");
        tmp.put(Localizations.EN_US, "Power Armor Leggings");
        tmp.put(Localizations.FR_FR, "Pantalon d'Armure énergétique");
        tmp.put(Localizations.PT_BR, "Pernas do Exotraje");
        tmp.put(Localizations.PT_PT, "Leggings de Armadura de Força");
        tmp.put(Localizations.RU_RU, "Силовые поножи");
        tmp.put(Localizations.ZH_CN, "动力护腿");
        add(MPSItems.POWER_ARMOR_LEGGINGS.get(), tmp);

        // Boots ---------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energiestiefel");
        tmp.put(Localizations.EN_US, "Power Armor Boots");
        tmp.put(Localizations.FR_FR, "Bottes d'Armure énergétique");
        tmp.put(Localizations.PT_BR, "Botas do Exotraje");
        tmp.put(Localizations.PT_PT, "Botas de armadura de poder");
        tmp.put(Localizations.RU_RU, "Силовые ботинки");
        tmp.put(Localizations.ZH_CN, "动力靴子");
        add(MPSItems.POWER_ARMOR_BOOTS.get(), tmp);

        // Power Fist ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energiefaust");
        tmp.put(Localizations.EN_US, "Power Fist");
        tmp.put(Localizations.FR_FR, "Poing énergétique");
        tmp.put(Localizations.PT_BR, "Mãos Cibernéticas");
        tmp.put(Localizations.PT_PT, "Punho De Poder");
        tmp.put(Localizations.RU_RU, "Силовой кулак");
        tmp.put(Localizations.ZH_CN, "动力拳套");
        add(MPSItems.POWER_FIST.get(), tmp);
    }
// pt pt translations

    void addModules() {
        // Armor =======================================================================================
        // Iron Plating --------------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Eisenüberzug");
        tmp.put(Localizations.EN_US, "Iron Plating");
        tmp.put(Localizations.FR_FR, "Plaquage de fer");
        tmp.put(Localizations.PT_BR, "Chapeamento de Ferro");
        tmp.put(Localizations.PT_PT, "Chapeamento de Ferro");
        tmp.put(Localizations.RU_RU, "Основная обшивка");
        tmp.put(Localizations.ZH_CN, "铁质电镀板");
        add(MPSItems.IRON_PLATING_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Eisenplattierung ist schwer, bietet aber ein wenig mehr Schutz.");
        tmp.put(Localizations.EN_US, "Iron plating is heavy but offers a bit more protection.");
        tmp.put(Localizations.FR_FR, "Le fer est lourd mais offre un peu plus de protection.");
        tmp.put(Localizations.PT_BR, "Chapeamento de ferro é pesado, mas oferece proteção um pouco mais.");
        tmp.put(Localizations.PT_PT, "Chapeamento de ferro é pesado, mas oferece proteção um pouco mais.");
        tmp.put(Localizations.RU_RU, "Некоторые тщательно спланированные металлические доспехи.");
        tmp.put(Localizations.ZH_CN, "铁质电镀板很重，但提供了更多保护。");
        addItemDescriptions(MPSItems.IRON_PLATING_MODULE.get(), tmp);

        // Diamond Plating -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Diamantplatte");
        tmp.put(Localizations.EN_US, "Diamond Plating");
        tmp.put(Localizations.FR_FR, "Plaquage en Diamant");
        tmp.put(Localizations.PT_BR, "Blindagem de Diamante");
        tmp.put(Localizations.PT_PT, "Blindagem de Diamante");
        tmp.put(Localizations.RU_RU, "Продвинутая обшивка");
        tmp.put(Localizations.ZH_CN, "钻石电镀板");
        add(MPSItems.DIAMOND_PLATING_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Das Diamantplattieren ist schwieriger und teurer in der Herstellung, bietet jedoch einen besseren Schutz.");
        tmp.put(Localizations.EN_US, "Diamond Plating is harder and more expensive to make, but offers better protection.");
        tmp.put(Localizations.FR_FR, "Le plaquage en diamant est plus léger, plus dur et protège plus mais est aussi plus difficile à faire.");
        tmp.put(Localizations.PT_BR, "As Placas melhoradas são mais leves, mais resistentes e protegem mais do que as placas simples, mas são muito mais difíceis de serem construídas.");
        tmp.put(Localizations.PT_PT, "O revestimento avançado é mais leve, mais duro e mais protetor que o Basic, mas muito mais difícil de fazer.");
        tmp.put(Localizations.RU_RU, "Некоторые тщательно расположенные доспехи редкого и сильного материала.");
        tmp.put(Localizations.ZH_CN, "钻石电镀板更轻，更坚固，比基础的还硬，不过难以制造。");
        addItemDescriptions(MPSItems.DIAMOND_PLATING_MODULE.get(), tmp);

        // Netherite Plating ---------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Netherit-Beschichtung");
        tmp.put(Localizations.EN_US, "Netherite Plating");
        tmp.put(Localizations.FR_FR, "Placage de netherite");
        tmp.put(Localizations.PT_BR, "Chapeamento Netherite");
        tmp.put(Localizations.PT_PT, "Revestimento Netherite");
        tmp.put(Localizations.RU_RU, "Незеритовое покрытие");
        tmp.put(Localizations.ZH_CN, "下界电镀");
        add(MPSItems.NETHERITE_PLATING_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Panzerung aus Netherit");
        tmp.put(Localizations.EN_US, "Armor plating made from Netherite");
        tmp.put(Localizations.FR_FR, "Placage d’armure en Netherite");
        tmp.put(Localizations.PT_BR, "Revestimento de armadura feito de Netherite");
        tmp.put(Localizations.PT_PT, "Armor chapeamento feito de Netherite");
        tmp.put(Localizations.RU_RU, "Броня из незерита");
        tmp.put(Localizations.ZH_CN, "由下界石制成的装甲镀层");
        addItemDescriptions(MPSItems.NETHERITE_PLATING_MODULE.get(), tmp);

        // Energy Shield -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energieschild");
        tmp.put(Localizations.EN_US, "Energy Shield");
        tmp.put(Localizations.FR_FR, "Champ énergétique");
        tmp.put(Localizations.PT_BR, "Campo de Energia");
        tmp.put(Localizations.PT_PT, "Campo de Energia");
        tmp.put(Localizations.RU_RU, "Энергетический щит");
        tmp.put(Localizations.ZH_CN, "能量护盾");
        add(MPSItems.ENERGY_SHIELD_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Energieschilde sind viel leichter als Plattieren, verbrauchen aber Energie.");
        tmp.put(Localizations.EN_US, "Energy shields are much lighter than plating, but consume energy.");
        tmp.put(Localizations.FR_FR, "Les champs énergétiques sont plus légers que les plaquages, mais conomme de l'énergie.");
        tmp.put(Localizations.PT_BR, "Esse sistema de proteção é extremamente leve, mas consome energia.");
        tmp.put(Localizations.PT_PT, "Os escudos de energia são muito mais leves que o revestimento, mas consomem energia.");
        tmp.put(Localizations.RU_RU, "Энергетические щит намного легче обшивок, но потребляют энергию.");
        tmp.put(Localizations.ZH_CN, "能量护盾比电镀板轻多了，但是需要消耗能量。");
        addItemDescriptions(MPSItems.ENERGY_SHIELD_MODULE.get(), tmp);


        // Cosmetic ====================================================================================
        // Active Camouflage ---------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Tarnung");
        tmp.put(Localizations.EN_US, "Active Camouflage");
        tmp.put(Localizations.FR_FR, "Camouflage actif");
        tmp.put(Localizations.PT_BR, "Camuflagem Ativa");
        tmp.put(Localizations.PT_PT, "Camuflagem Ativa");
        tmp.put(Localizations.RU_RU, "Активный камуфляж");
        tmp.put(Localizations.ZH_CN, "动态伪装模块");
        add(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Emittiere ein Hologramm deiner Umgebung, um dich fast unmerklich zu machen.");
        tmp.put(Localizations.EN_US, "Emit a hologram of your surroundings to make yourself almost imperceptible.");
        tmp.put(Localizations.FR_FR, "Emet un hologramme de votre environnement pour vous rendre quasi-invisible.");
        tmp.put(Localizations.PT_BR, "Dobra a luz ao seu redor para lhe deixar quase imperceptível.");
        tmp.put(Localizations.PT_PT, "Emite um holograma do seu entorno para se tornar quase imperceptível.");
        tmp.put(Localizations.RU_RU, "Излучает голограмму местности, чтобы сделать Вас почти незаметным.");
        tmp.put(Localizations.ZH_CN, "向周围发射全息投影，使你不易被察觉到。");
        addItemDescriptions(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), tmp);



        // Energy Generation ===========================================================================
        // Coal Generator ------------------------------------------------------------------------------
        // TODO:
//        addModule(MPSItems.COAL_GENERATOR_MODULE.get(), "Coal Generator","Generate power with solid fuels");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
//
//        tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        addItemDescriptions(, tmp);

        // Solar Generator -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Photovoltaikgenerator");
        tmp.put(Localizations.EN_US, "Solar Generator");
        tmp.put(Localizations.FR_FR, "Générateur Solaire");
        tmp.put(Localizations.PT_BR, "Gerador Solar");
        tmp.put(Localizations.PT_PT, "Gerador Solar");
        tmp.put(Localizations.RU_RU, "Солнечный генератор");
        tmp.put(Localizations.ZH_CN, "太阳能发电模块");
        add(MPSItems.SOLAR_GENERATOR_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Lass die Sonne deine Abenteuer antreiben.");
        tmp.put(Localizations.EN_US, "Let the sun power your adventures.");
        tmp.put(Localizations.FR_FR, "Laissez le soleil alimenter vos aventures.");
        tmp.put(Localizations.PT_BR, "Deixe o sol potencializar suas aventuras.");
        tmp.put(Localizations.PT_PT, "Deixe o sol potencializar suas aventuras.");
        tmp.put(Localizations.RU_RU, "Пусть солнце привит ваши приключения.");
        tmp.put(Localizations.ZH_CN, "让烈日成为你冒险的坚实后盾。");
        addItemDescriptions(MPSItems.SOLAR_GENERATOR_MODULE.get(), tmp);

        // "High Efficiency Solar Generator" -----------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Hocheffizienter Solargenerator");
        tmp.put(Localizations.EN_US, "High Efficiency Solar Generator");
        tmp.put(Localizations.FR_FR, "Générateur solaire à haute efficacité");
        tmp.put(Localizations.PT_BR, "Gerador Solar de Alta Eficiência");
        tmp.put(Localizations.PT_PT, "Gerador Solar de Alta Eficiência");
        tmp.put(Localizations.RU_RU, "Высокоэффективный солнечный генератор");
        tmp.put(Localizations.ZH_CN, "高效太阳能发电模块");
        add(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein Solargenerator mit 3-facher Stromerzeugung des Standard-Solargenerators.");
        tmp.put(Localizations.EN_US, "A solar generator with 3 times the power generation of the standard solar generator.");
        tmp.put(Localizations.FR_FR, "Un générateur solaire produisant 3 fois plus d'énergie que le générateur solaire standard.");
        tmp.put(Localizations.PT_BR, "Um gerador solar com 3 vezes a geração de energia do gerador solar padrão.");
        tmp.put(Localizations.PT_PT, "Um gerador solar com 3 vezes a geração de energia do gerador solar padrão.");
        tmp.put(Localizations.RU_RU, "Солнечный генератор, в 3 раза превышающий мощность генератора солнечной энергии.");
        tmp.put(Localizations.ZH_CN, "一个太阳能发电模块，产能是普通太阳能发电模块的三倍。");
        addItemDescriptions(MPSItems.ADVANCED_SOLAR_GENERATOR_MODULE.get(), tmp);

        // Kinetic Generator ---------------------------------------------------------------------------
        tmp.put(Localizations.DE_DE, "Kinetischer Generator");
        tmp.put(Localizations.EN_US, "Kinetic Generator");
        tmp.put(Localizations.FR_FR, "Générateur Cinétique");
        tmp.put(Localizations.PT_BR, "Gerador Cinético");
        tmp.put(Localizations.PT_PT, "Gerador Cinético");
        tmp.put(Localizations.RU_RU, "Кинетический генератор");
        tmp.put(Localizations.ZH_CN, "动能发电模块");
        add(MPSItems.KINETIC_GENERATOR_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Erzeugen Sie Kraft mit Ihrer Bewegung.");
        tmp.put(Localizations.EN_US, "Generate power with your movement.");
        tmp.put(Localizations.FR_FR, "Générez de la puissance avec votre mouvement.");
        tmp.put(Localizations.PT_BR, "Gere poder com seu movimento.");
        tmp.put(Localizations.PT_PT, "Gere poder com seu movimento.");
        tmp.put(Localizations.RU_RU, "Сгенерировать энергию FE с вашим движением.");
        tmp.put(Localizations.ZH_CN, "利用你的移动来进行发电。");
        addItemDescriptions(MPSItems.KINETIC_GENERATOR_MODULE.get(), tmp);

        // Thermal Generator ---------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Thermischer Generator");
        tmp.put(Localizations.EN_US, "Thermal Generator");
        tmp.put(Localizations.FR_FR, "Générateur Thermique");
        tmp.put(Localizations.PT_BR, "Gerador Térmico");
        tmp.put(Localizations.PT_PT, "Gerador Térmico");
        tmp.put(Localizations.RU_RU, "Тепловой генератор");
        tmp.put(Localizations.ZH_CN, "热能发电模块");
        add(MPSItems.THERMAL_GENERATOR_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Erzeugt Energie aus extremen Wärmemengen.");
        tmp.put(Localizations.EN_US, "Generate power from extreme amounts of heat.");
        tmp.put(Localizations.FR_FR, "Génère de l'énergie à partir de quantités de chaleur extrêmes.");
        tmp.put(Localizations.PT_BR, "Gerar energia a partir de quantidades extremas de calor.");
        tmp.put(Localizations.PT_PT, "Gerar energia a partir de quantidades extremas de calor.");
        tmp.put(Localizations.RU_RU, "Генерировать энергию FE от экстремальных количеств тепла.");
        tmp.put(Localizations.ZH_CN, "利用大量热能来进行发电。");
        addItemDescriptions(MPSItems.THERMAL_GENERATOR_MODULE.get(), tmp);

        // Environmental ===============================================================================





//        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
//
//        tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        addItemDescriptions(, tmp);


        /*

        addModule(MPSItems.AUTO_FEEDER_MODULE.get(), "Auto-Feeder",
                "Whenever you're hungry, this module will grab the bottom-left-most food item from your inventory and feed it to you, storing the rest for later.");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
//
//        tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        addItemDescriptions(, tmp);



        addModule(MPSItems.COOLING_SYSTEM_MODULE.get(), "Cooling System",
                "Cools down heat-producing modules quicker. Add a fluid tank module and fluid to enhance performance.");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
//
//        tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        addItemDescriptions(, tmp);

        addModule(MPSItems.FLUID_TANK_MODULE.get(), "Fluid Tank", "Stores fluid to enhance the performance of the cooling system.");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
//
//        tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        addItemDescriptions(, tmp);

        addModule(MPSItems.MOB_REPULSOR_MODULE.get(),  "Mob Repulsor",
                "Pushes mobs away from you when activated, but constantly drains power. It is highly recommended that you set this module to a keybind because of the high energy draw.");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
//
//        tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        addItemDescriptions(, tmp);

        addModule(MPSItems.WATER_ELECTROLYZER_MODULE.get(),"Water Electrolyzer",
                "When you run out of air, this module will jolt the water around you, electrolyzing a small bubble to breathe from.");

//        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
//
//        tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        addItemDescriptions(, tmp);












        // Mining Enchantments -------------------------------------------------------------------------
        addModule(MPSItems.AQUA_AFFINITY_MODULE.get(), "Aqua Affinity", "Reduces the speed penalty for using your tool underwater.");
        addModule(MPSItems.SILK_TOUCH_MODULE.get(), "Silk Touch Enchantment", "A module that provides the Silk Touch enchantment");
        addModule(MPSItems.FORTUNE_MODULE.get(), "Fortune Enchantment", "A module that provides the fortune enchantment.");

        // Mining Enhancements -------------------------------------------------------------------------
        addModule(MPSItems.VEIN_MINER_MODULE.get(), "Vein Miner", "A module for mining ore veins");
        // WIP
        addModule(MPSItems.TUNNEL_BORE_MODULE.get(), "Tunnel Bore","An updrade that will allow the pickaxe module to mine up to a 5x5 area of blocks");
        addModule(MPSItems.SELECTIVE_MINER.get(), "Unnamed Experimental module",
                "breaks blocks similar to the vein miner, but selectively. Shift and click to select block type");

        // Movement -----------------------------------------------------------------------------------
        addModule(MPSItems.BLINK_DRIVE_MODULE.get(), "Blink Drive", "Get from point A to point C via point B, where point B is a fold in space & time.");
        addModule(MPSItems.CLIMB_ASSIST_MODULE.get(), "Uphill Step Assist", "A pair of dedicated servos allow you to effortlessly step up 1m-high ledges.");
        addModule(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), "Dimensional Tear Generator",
                "Generate a tear in the space-time continuum that will teleport the player to its relative coordinates in the nether or overworld.");
        addModule(MPSItems.FLIGHT_CONTROL_MODULE.get(), "Flight Control", "An integrated control circuit to help you fly better. Press Z to go down.");
        addModule(MPSItems.GLIDER_MODULE.get(), "Glider", "Tack on some wings to turn downward into forward momentum. Press sneak+forward while falling to activate.");
        addModule(MPSItems.JETBOOTS_MODULE.get(),"Jet Boots",
                "Jet boots are not as strong as a jetpack, but they should at least be strong enough to counteract gravity.");
        addModule(MPSItems.JETPACK_MODULE.get(),"Jetpack",
                "A jetpack should allow you to jump indefinitely, or at least until you run out of power.");
        addModule(MPSItems.JUMP_ASSIST_MODULE.get(), "Jump Assist",
                "Another set of servo motors to help you jump higher.");
        addModule(MPSItems.PARACHUTE_MODULE.get(), "Parachute",
                "Add a parachute to slow your descent. Activate by pressing sneak (defaults to Shift) in midair.");
        addModule(MPSItems.SHOCK_ABSORBER_MODULE.get(),"Shock Absorber",
                "With some servos, springs, and padding, you should be able to negate a portion of fall damage.");
        addModule(MPSItems.SPRINT_ASSIST_MODULE.get(), "Sprint Assist",
                "A set of servo motors to help you sprint (double-tap forward) and walk faster.");
        addModule(MPSItems.SWIM_BOOST_MODULE.get(), "Swim Boost",
                "By refitting an ion thruster for underwater use, you may be able to add extra forward (or backward) thrust when underwater.");

        // Special ------------------------------------------------------------------------------------
        addModule(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "Transparent Armor",
                "Make the item transparent, so you can show off your skin without losing armor.");
        addModule(MPSItems.MAGNET_MODULE.get(), "Magnet",
                "Generates a magnetic field strong enough to attract items towards the player.         WARNING:                   This module drains power continuously. Turn it off when not needed.");
        addModule(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), "Piglin Pacification Module",
                "Simple module to make Piglins neutral as if wearing gold armor");

        // Vision -------------------------------------------------------------------------------------
        addModule(MPSItems.BINOCULARS_MODULE.get(), "Binoculars",
                "With the problems that have been plaguing Optifine lately, you've decided to take that Zoom ability into your own hands.");
        addModule(MPSItems.NIGHT_VISION_MODULE.get(), "Night Vision",
                "A pair of augmented vision goggles to help you see at night and underwater.");

        // Tools --------------------------------------------------------------------------
        addModule(MPSItems.AXE_MODULE.get(), "Axe", "Axes are mostly for chopping trees.");

        addModule(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), "Diamond Drill Upgrade",
                "Add diamonds to allow your pickaxe module to mine Obsidian. *REQUIRES PICKAXE MODULE TO WORK*");
        addModule(MPSItems.FLINT_AND_STEEL_MODULE.get(), "Flint and Steel",
                "A portable igniter that creates fire through the power of energy.");
        addModule(MPSItems.HOE_MODULE.get(), "Rototiller",
                "An automated tilling addon to make it easy to till large swaths of land at once.");
        addModule(MPSItems.LEAF_BLOWER_MODULE.get(), "Leaf Blower",
                "Create a torrent of air to knock plants out of the ground and leaves off of trees.");
        addModule(MPSItems.LUX_CAPACITOR_MODULE.get(), "Lux Capacitor",
                "Launch a virtually infinite number of attractive light sources at the wall.");
        addModule(MPSItems.PICKAXE_MODULE.get(), "Pickaxe",
                "Picks are good for harder materials like stone and ore.");
        addModule(MPSItems.SHEARS_MODULE.get(), "Shears",
                "Cuts through leaves, wool, and creepers alike.");
        addModule(MPSItems.SHOVEL_MODULE.get(), "Shovel",
                "Shovels are good for soft materials like dirt and sand.");

        // Debug --------------------------------------------------------------------------------------
        // todo

        // Weapons ------------------------------------------------------------------------------------
        addModule(MPSItems.BLADE_LAUNCHER_MODULE.get(), "Blade Launcher",
                "Launches a spinning blade of death (or shearing).");
        addModule(MPSItems.LIGHTNING_MODULE.get(), "Lightning Summoner",
                "Allows you to summon lightning for a large energy cost.");
        addModule(MPSItems.MELEE_ASSIST_MODULE.get(), "Melee Assist",
                "A much simpler addon, makes your powertool punches hit harder.");
        addModule(MPSItems.PLASMA_CANNON_MODULE.get(), "Plasma Cannon",
                "Use electrical arcs in a containment field to superheat air to a plasma and launch it at enemies.");
        addModule(MPSItems.RAILGUN_MODULE.get(), "Railgun",
                "An assembly which accelerates a projectile to supersonic speeds using magnetic force. Heavy recoil.");

         */


    }




}
