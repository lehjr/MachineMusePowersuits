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
        // Auto Feeder ---------------------------------------------------------------------------------
        new HashMap<>();
        tmp.put(Localizations.DE_DE, "Auto-Ernährungeinheit");
        tmp.put(Localizations.EN_US, "Auto-Feeder");
        tmp.put(Localizations.FR_FR, "Nourriture-Automatique");
        tmp.put(Localizations.PT_BR, "Alimentador automático");
        tmp.put(Localizations.PT_PT, "Alimentador automático");
        tmp.put(Localizations.RU_RU, "Автоподатчик");
        tmp.put(Localizations.ZH_CN, "自动喂食模块");
        add(MPSItems.AUTO_FEEDER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Immer wenn du hungrig bist, greift dieses Modul das Lebensmittel von unten nach links aus deinem Inventar, füttert es und speichert den Rest für später.");
        tmp.put(Localizations.EN_US, "Whenever you're hungry, this module will grab the bottom-left-most food item from your inventory and feed it to you, storing the rest for later.");
        tmp.put(Localizations.FR_FR, "Chaque fois que vous aurez faim, ce module prendra l’aliment le plus en bas à gauche de votre inventaire et vous le fournira, en stockant le reste pour plus tard.");
        tmp.put(Localizations.PT_BR, "Sempre que você estiver com fome, este módulo irá pegar o item alimentar mais à esquerda do seu inventário e alimentá-lo para você, armazenando o restante para mais tarde.");
        tmp.put(Localizations.PT_PT, "Sempre que você estiver com fome, este módulo irá pegar o item alimentar mais à esquerda do seu inventário e alimentá-lo para você, armazenando o restante para mais tarde.");
        tmp.put(Localizations.RU_RU, "Всякий раз, когда вы голодны, этот модуль будет захватывать нижний левый элемент питания из вашего инвентаря и подавать его вам, сохраняя остальное на потом.");
        tmp.put(Localizations.ZH_CN, "当你感到饥饿的时候，该模块会使用背包中最左下方的食物给你喂食，多余的食物会被储存起来以备不时之需。");
        addItemDescriptions(MPSItems.AUTO_FEEDER_MODULE.get(), tmp);

        // Cooling System ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Kühlsystem");
        tmp.put(Localizations.EN_US, "Cooling System");
        tmp.put(Localizations.FR_FR, "Système de refroidissement");
        tmp.put(Localizations.PT_BR, "Sistema de Resfriamento");
        tmp.put(Localizations.PT_PT, "Sistema de Arrefecimento");
        tmp.put(Localizations.RU_RU, "Система охлаждения");
        tmp.put(Localizations.ZH_CN, "基础冷却系统");
        add(MPSItems.COOLING_SYSTEM_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Kühlt wärmeerzeugende Module schneller ab. Fügen Sie ein Flüssigkeitstankmodul und eine Flüssigkeit hinzu, um die Leistung zu verbessern.");
        tmp.put(Localizations.EN_US, "Cools down heat-producing modules quicker. Add a fluid tank module and fluid to enhance performance.");
        tmp.put(Localizations.FR_FR, "Refroidit plus rapidement les modules produisant de la chaleur. Ajoutez un module de réservoir de fluide et du fluide pour améliorer les performances.");
        tmp.put(Localizations.PT_BR, "Resfria os módulos produtores de calor mais rapidamente. Adicione um módulo de tanque de fluido e fluido para melhorar o desempenho.");
        tmp.put(Localizations.PT_PT, "Resfria os módulos produtores de calor mais rapidamente. Adicione um módulo de tanque de fluido e fluido para melhorar o desempenho.");
        tmp.put(Localizations.RU_RU, "Быстрее охлаждает тепловыделяющие модули. Добавьте модуль резервуара для жидкости и жидкость для повышения производительности.");
        tmp.put(Localizations.ZH_CN, "更快地冷却发热模块。添加油箱模块和油液以提高性能。");
        addItemDescriptions(MPSItems.COOLING_SYSTEM_MODULE.get(), tmp);

        // Fluid Tank  ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Flüssigkeitstank");
        tmp.put(Localizations.EN_US, "Fluid Tank");
        tmp.put(Localizations.FR_FR, "Réservoir de fluide");
        tmp.put(Localizations.PT_BR, "Tanque de Fluido");
        tmp.put(Localizations.PT_PT, "Tanque de fluido");
        tmp.put(Localizations.RU_RU, "Резервуар для жидкости");
        tmp.put(Localizations.ZH_CN, "油箱");
        add(MPSItems.FLUID_TANK_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Speichert Flüssigkeit, um die Leistung des Kühlsystems zu verbessern.");
        tmp.put(Localizations.EN_US, "Stores fluid to enhance the performance of the cooling system.");
        tmp.put(Localizations.FR_FR, "Stocke le fluide pour améliorer les performances du système de refroidissement.");
        tmp.put(Localizations.PT_BR, "Armazena fluido para melhorar o desempenho do sistema de refrigeração.");
        tmp.put(Localizations.PT_PT, "Armazena fluido para melhorar o desempenho do sistema de refrigeração.");
        tmp.put(Localizations.RU_RU, "Накапливает жидкость для повышения производительности системы охлаждения.");
        tmp.put(Localizations.ZH_CN, "储存流体以提高冷却系统的性能。");
        addItemDescriptions(MPSItems.FLUID_TANK_MODULE.get(), tmp);

        // Mob Repulsor --------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Mob-Repulsor");
        tmp.put(Localizations.EN_US, "Mob Repulsor");
        tmp.put(Localizations.FR_FR, "Répulsif de la foule");
        tmp.put(Localizations.PT_BR, "Repulsor da mob");
        tmp.put(Localizations.PT_PT, "Repulsor da mob");
        tmp.put(Localizations.RU_RU, "Отпугиватель мобов");
        tmp.put(Localizations.ZH_CN, "排斥力场模块");
        add(MPSItems.MOB_REPULSOR_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Stößt Mobs von dir weg, wenn er aktiviert wird, verbraucht aber ständig Energie. Es wird dringend empfohlen, dieses Modul aufgrund des hohen Energieverbrauchs auf eine Tastenkombination einzustellen.");
        tmp.put(Localizations.EN_US, "Pushes mobs away from you when activated, but constantly drains power. It is highly recommended that you set this module to a keybind because of the high energy draw.");
        tmp.put(Localizations.FR_FR, "Repousse les foules loin de vous lorsqu’il est activé, mais draine constamment de l’énergie. Il est fortement recommandé de définir ce module sur un keybind en raison de la forte consommation d’énergie.");
        tmp.put(Localizations.PT_BR, "Empurra os mobs para longe de você quando ativado, mas constantemente drena energia. É altamente recomendável que você defina este módulo para um keybind por causa do alto consumo de energia.");
        tmp.put(Localizations.PT_PT, "Empurra os mobs para longe de você quando ativado, mas constantemente drena energia. É altamente recomendável que você defina este módulo para um keybind por causa do alto consumo de energia.");
        tmp.put(Localizations.RU_RU, "Отталкивает от себя мобов при активации, но постоянно истощает силу. Настоятельно рекомендуется установить для этого модуля привязку клавиш из-за высокого энергопотребления.");
        tmp.put(Localizations.ZH_CN, "激活时会将怪物推开，但会持续消耗能量。鉴于该模块能耗高，强烈建议将该模块绑定快捷键。");
        addItemDescriptions(MPSItems.MOB_REPULSOR_MODULE.get(), tmp);

        // Water Electrolyzer --------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Wasserelektrolysierer");
        tmp.put(Localizations.EN_US, "Water Electrolyzer");
        tmp.put(Localizations.FR_FR, "Electrolyseur d'eau");
        tmp.put(Localizations.PT_BR, "Eletrolisador de Água");
        tmp.put(Localizations.PT_PT, "Eletrolisador de Água");
        tmp.put(Localizations.RU_RU, "Электролизёр воды");
        tmp.put(Localizations.ZH_CN, "电解水模块");
        add(MPSItems.WATER_ELECTROLYZER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Wenn dir die Luft ausgeht, wird dieses Modul das Wasser um dich herum aufblasen und eine kleine Blase zum Atmen elektrolysieren.");
        tmp.put(Localizations.EN_US, "When you run out of air, this module will jolt the water around you, electrolyzing a small bubble to breathe from.");
        tmp.put(Localizations.FR_FR, "Quand vous serez à court d'air, ce module électrifiera l'eau autour de vous, créera des bulles d'air par électrolyse pour permettre de respirer.");
        tmp.put(Localizations.PT_BR, "Quando você ficar sem ar embaixo d'água, este módulo irá eletrolisar a água ao seu redor, criando uma bolha com oxigênio dentro.");
        tmp.put(Localizations.PT_PT, "Quando você ficar sem ar, este módulo irá sacudir a água ao seu redor, eletrolisando uma pequena bolha para respirar.");
        tmp.put(Localizations.RU_RU, "Когда у вас заканчивается воздух, этот модуль будет трясти вокруг вас водой, электролизуя небольшой пузырь, чтобы дышать.");
        tmp.put(Localizations.ZH_CN, "当你在水下断氧时，这个模块将会震荡并电解你周围的水，产生可以用来呼吸的小气泡。");
        addItemDescriptions(MPSItems.WATER_ELECTROLYZER_MODULE.get(), tmp);

        // Mining Enchantments =========================================================================
        // Aqua Affinity -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Wasseraffinität");
        tmp.put(Localizations.EN_US, "Aqua Affinity");
        tmp.put(Localizations.FR_FR, "Affinité aquatique");
        tmp.put(Localizations.PT_BR, "Melhoria - Impermeabilidade");
        tmp.put(Localizations.PT_PT, "Afinidade Aquática");
        tmp.put(Localizations.RU_RU, "Родство с водой");
        tmp.put(Localizations.ZH_CN, "水亲和模块");
        add(MPSItems.AQUA_AFFINITY_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Verringert die Geschwindigkeit für die Verwendung Ihres Werkzeugs unter Wasser.");
        tmp.put(Localizations.EN_US, "Reduces the speed penalty for using your tool underwater.");
        tmp.put(Localizations.FR_FR, "Reduit la pénalité de vitesse d'utilisation d'un outil sous l'eau.");
        tmp.put(Localizations.PT_BR, "Faz com que a sua mão cibernética não seja contida pela pressão da água, fazendo com que a velocidade de operação dela seja a mesma, não importando se estiver embaixo d'água ou ao ar livre.");
        tmp.put(Localizations.PT_PT, "Reduz a penalidade de velocidade para usar sua ferramenta embaixo d'água.");
        tmp.put(Localizations.RU_RU, "Уменьшает понижение скорости инструмента под водой.");
        tmp.put(Localizations.ZH_CN, "提高你在水下使用工具的速度。");
        addItemDescriptions(MPSItems.AQUA_AFFINITY_MODULE.get(), tmp);

        // Silk Touch Enchantment ----------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Verzauberung \"Seidenberührung\"");
        tmp.put(Localizations.EN_US, "Silk Touch Enchantment");
        tmp.put(Localizations.FR_FR, "Enchantement Silk Touch");
        tmp.put(Localizations.PT_BR, "Encantamento Silk Touch");
        tmp.put(Localizations.PT_PT, "Encantamento Silk Touch");
        tmp.put(Localizations.RU_RU, "Зачарование Шёлковое касание");
        tmp.put(Localizations.ZH_CN, "精准采集附魔");
        add(MPSItems.SILK_TOUCH_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein Modul, das die Verzauberung \"Silk Touch\" bereitstellt");
        tmp.put(Localizations.EN_US, "A module that provides the Silk Touch enchantment");
        tmp.put(Localizations.FR_FR, "Un module qui procure l’enchantement Silk Touch");
        tmp.put(Localizations.PT_BR, "Um módulo que proporciona o encantamento do Silk Touch");
        tmp.put(Localizations.PT_PT, "Um módulo que proporciona o encantamento Silk Touch");
        tmp.put(Localizations.RU_RU, "Модуль который обеспечивает зачарование Шёлковое касание");
        tmp.put(Localizations.ZH_CN, "来自精准采集附魔的模块。");
        addItemDescriptions(MPSItems.SILK_TOUCH_MODULE.get(), tmp);

        // Fortune Enchantment -------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Glücksverzauberung");
        tmp.put(Localizations.EN_US, "Fortune Enchantment");
        tmp.put(Localizations.FR_FR, "Enchantement de fortune");
        tmp.put(Localizations.PT_BR, "Encantamento da Fortuna");
        tmp.put(Localizations.PT_PT, "Encantamento da Fortuna");
        tmp.put(Localizations.RU_RU, "Чары Фортуны");
        tmp.put(Localizations.ZH_CN, "财富魅力");
        add(MPSItems.FORTUNE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein Modul, das für die Glücksverzauberung sorgt.");
        tmp.put(Localizations.EN_US, "A module that provides the fortune enchantment.");
        tmp.put(Localizations.FR_FR, "Un module qui procure l’enchantement de la fortune.");
        tmp.put(Localizations.PT_BR, "Um módulo que proporciona o encantamento da fortuna.");
        tmp.put(Localizations.PT_PT, "Um módulo que proporciona o encantamento da fortuna.");
        tmp.put(Localizations.RU_RU, "Модуль, обеспечивающий зачарование фортуны.");
        tmp.put(Localizations.ZH_CN, "提供财富附魔的模块。");
        addItemDescriptions(MPSItems.FORTUNE_MODULE.get(), tmp);

        // Mining Enhancements =========================================================================
        // Vein Miner ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Vein Miner");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.VEIN_MINER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "A module for mining ore veins");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.VEIN_MINER_MODULE.get(), tmp);

        // Tunnel Bore ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Tunnel Bore");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.TUNNEL_BORE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "An updrade that will allow the pickaxe module to mine up to a 5x5 area of blocks");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.TUNNEL_BORE_MODULE.get(), tmp);

        // Selective Miner -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Selective Miner");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.SELECTIVE_MINER.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US,  "Breaks blocks similar to the vein miner, but selectively. Shift and click to select block type");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.SELECTIVE_MINER.get(), tmp);

        // Movement ====================================================================================
        // Blink Drive ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Blink Drive");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.BLINK_DRIVE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Get from point A to point C via point B, where point B is a fold in space & time.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.BLINK_DRIVE_MODULE.get(), tmp);

        // Uphill Step Assist --------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Uphill Step Assist");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.CLIMB_ASSIST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "A pair of dedicated servos allow you to effortlessly step up 1m-high ledges.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.CLIMB_ASSIST_MODULE.get(), tmp);

        // Dimensional Tear Generator ------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Dimensional Tear Generator");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Generate a tear in the space-time continuum that will teleport the player to its relative coordinates in the nether or overworld.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), tmp);

        // Flight Control ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Flight Control");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.FLIGHT_CONTROL_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "An integrated control circuit to help you fly better. Press Z to go down.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.FLIGHT_CONTROL_MODULE.get(), tmp);

        // Glider --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Glider");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.GLIDER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Tack on some wings to turn downward into forward momentum. Press sneak+forward while falling to activate.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.GLIDER_MODULE.get(), tmp);

        // Jet Boots -----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Jet Boots");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.JETBOOTS_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Jet boots are not as strong as a jetpack, but they should at least be strong enough to counteract gravity.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.JETBOOTS_MODULE.get(), tmp);

        // Jetpack -------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Jetpack");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.JETPACK_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "A jetpack should allow you to jump indefinitely, or at least until you run out of power.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.JETPACK_MODULE.get(), tmp);

        // Jump Assist ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Jump Assist");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.JUMP_ASSIST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Another set of servo motors to help you jump higher.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.JUMP_ASSIST_MODULE.get(), tmp);

        // Parachute -----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Parachute");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.PARACHUTE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Add a parachute to slow your descent. Activate by pressing sneak (defaults to Shift) in midair.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.PARACHUTE_MODULE.get(), tmp);

        // Shock Absorber ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Shock Absorber");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.SHOCK_ABSORBER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "With some servos, springs, and padding, you should be able to negate a portion of fall damage.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.SHOCK_ABSORBER_MODULE.get(), tmp);

        // Sprint Assist -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Sprint Assist");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.SPRINT_ASSIST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "A set of servo motors to help you sprint (double-tap forward) and walk faster.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.SPRINT_ASSIST_MODULE.get(), tmp);

        // Swim Boost ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Swim Boost");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.SWIM_BOOST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "By refitting an ion thruster for underwater use, you may be able to add extra forward (or backward) thrust when underwater.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.SWIM_BOOST_MODULE.get(), tmp);

        // Special =====================================================================================
        // Transparent Armor ---------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Transparent Armor");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Make the item transparent, so you can show off your skin without losing armor.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), tmp);

        // Magnet --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Magnet");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.MAGNET_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Generates a magnetic field strong enough to attract items towards the player.         WARNING:                   This module drains power continuously. Turn it off when not needed.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.MAGNET_MODULE.get(), tmp);

        // Piglin Pacification Module ------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Piglin Pacification Module");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Simple module to make Piglins neutral as if wearing gold armor");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), tmp);

        // Vision ======================================================================================
        // Binoculars ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Binoculars");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.BINOCULARS_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "With the problems that have been plaguing Optifine lately, you've decided to take that Zoom ability into your own hands.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.BINOCULARS_MODULE.get(), tmp);

        // Night Vision --------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Night Vision");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.NIGHT_VISION_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "A pair of augmented vision goggles to help you see at night and underwater.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.NIGHT_VISION_MODULE.get(), tmp);

        // Tools =======================================================================================
        // Axe -----------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US,  "Axe");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.AXE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Axes are mostly for chopping trees.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.AXE_MODULE.get(), tmp);

        // Diamond Drill Upgrade -----------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Diamond Drill Upgrade");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Add diamonds to allow your pickaxe module to mine Obsidian. *REQUIRES PICKAXE MODULE TO WORK*");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), tmp);

        // Flint and Steel -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Flint and Steel");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.FLINT_AND_STEEL_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "A portable igniter that creates fire through the power of energy.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.FLINT_AND_STEEL_MODULE.get(), tmp);

        // Rototiller ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Rototiller");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.HOE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "An automated tilling addon to make it easy to till large swaths of land at once.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.HOE_MODULE.get(), tmp);

        // Leaf Blower ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Leaf Blower");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.LEAF_BLOWER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Create a torrent of air to knock plants out of the ground and leaves off of trees.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.LEAF_BLOWER_MODULE.get(), tmp);

        // Lux Capacitor -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Lux Capacitor");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.LUX_CAPACITOR_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Launch a virtually infinite number of attractive light sources at the wall.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.LUX_CAPACITOR_MODULE.get(), tmp);

        // Pickaxe -------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Pickaxe");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.PICKAXE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Picks are good for harder materials like stone and ore.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.PICKAXE_MODULE.get(), tmp);

        // Shears --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Shears");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.SHEARS_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Cuts through leaves, wool, and creepers alike.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.SHEARS_MODULE.get(), tmp);

        // Shovel --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Shovel");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.SHOVEL_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Shovels are good for soft materials like dirt and sand.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.SHOVEL_MODULE.get(), tmp);


        // Debug =======================================================================================
        // TODO

        // Weapons =====================================================================================
        // Blade Launcher ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Blade Launcher");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.BLADE_LAUNCHER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Launches a spinning blade of death (or shearing).");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.BLADE_LAUNCHER_MODULE.get(), tmp);

        // Lightning Summoner ------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Lightning Summoner");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.LIGHTNING_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Allows you to summon lightning for a large energy cost.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.LIGHTNING_MODULE.get(), tmp);

        // Melee Assist --------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Melee Assist");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.MELEE_ASSIST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "A much simpler addon, makes your powertool punches hit harder.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.MELEE_ASSIST_MODULE.get(), tmp);

        // Plasma Cannon -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Plasma Cannon");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.PLASMA_CANNON_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Use electrical arcs in a containment field to superheat air to a plasma and launch it at enemies.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.PLASMA_CANNON_MODULE.get(), tmp);

        // Railgun -------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Railgun");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSItems.RAILGUN_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "An assembly which accelerates a projectile to supersonic speeds using magnetic force. Heavy recoil.");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        addItemDescriptions(MPSItems.RAILGUN_MODULE.get(), tmp);
    }


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

}
