package powersuits.client.lang;

import lehjr.powersuits.common.base.MPSBlocks;
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
        addBlocks();
        addGui();
        addKeybinds();
        addModuleTradeoff();
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

        // High Efficiency Solar Generator -------------------------------------------------------------
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
        tmp.put(Localizations.DE_DE, "Verzauberung Seidenberührung");
        tmp.put(Localizations.EN_US, "Verzauberung \"Seidenberührung\"");
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
        tmp.put(Localizations.DE_DE, "Vein Miner");
        tmp.put(Localizations.EN_US, "Vein Miner");
        tmp.put(Localizations.FR_FR, "Mineur de veine");
        tmp.put(Localizations.PT_BR, "Veia Mineradora");
        tmp.put(Localizations.PT_PT, "Mineiro de veia");
        tmp.put(Localizations.RU_RU, "Жильный майнер");
        tmp.put(Localizations.ZH_CN, "矿脉矿工");
        add(MPSItems.VEIN_MINER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein Modul für den Abbau von Erzadern");
        tmp.put(Localizations.EN_US, "A module for mining ore veins");
        tmp.put(Localizations.FR_FR, "Un module pour l’extraction de veines de minerai");
        tmp.put(Localizations.PT_BR, "Um módulo para mineração de veios de minério");
        tmp.put(Localizations.PT_PT, "Um módulo para mineração de veios de minério");
        tmp.put(Localizations.RU_RU, "Модуль для отработки рудных жил");
        tmp.put(Localizations.ZH_CN, "矿脉开采模块");
        addItemDescriptions(MPSItems.VEIN_MINER_MODULE.get(), tmp);

        // Tunnel Bore ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Tunnelröhre");
        tmp.put(Localizations.EN_US, "Tunnel Bore");
        tmp.put(Localizations.FR_FR, "Tunnel de forage");
        tmp.put(Localizations.PT_BR, "Furo do Túnel");
        tmp.put(Localizations.PT_PT, "Furo do túnel");
        tmp.put(Localizations.RU_RU, "Туннельное отверстие");
        tmp.put(Localizations.ZH_CN, "隧道钻孔");
        add(MPSItems.TUNNEL_BORE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein Modul, mit dem das Spitzhackenmodul 5x5-Blöcke gleichzeitig abbauen kann.");
        tmp.put(Localizations.EN_US, "A module that enables the pickaxe module to mine 5x5 range blocks simultaneously.");
        tmp.put(Localizations.FR_FR, "Un module qui permet au module de pioche d’exploiter simultanément des blocs de portée 5x5.");
        tmp.put(Localizations.PT_BR, "Um módulo que permite que o módulo picareta minere blocos de alcance 5x5 simultaneamente.");
        tmp.put(Localizations.PT_PT, "Um módulo que permite que o módulo de picareta minere blocos de alcance 5x5 simultaneamente.");
        tmp.put(Localizations.RU_RU, "Модуль, который позволяет модулю кирки одновременно добывать блоки диапазона 5x5.");
        tmp.put(Localizations.ZH_CN, "一个升级工具，能够使镐模块同时挖掘 5x5 范围方块。");
        addItemDescriptions(MPSItems.TUNNEL_BORE_MODULE.get(), tmp);

        // Selective Miner -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Selektiver Bergbau");
        tmp.put(Localizations.EN_US, "Selective Miner");
        tmp.put(Localizations.FR_FR, "Mineur sélectif");
        tmp.put(Localizations.PT_BR, "Minerador Seletivo");
        tmp.put(Localizations.PT_PT, "Mineiro Seletivo");
        tmp.put(Localizations.RU_RU, "Селективный майнер");
        tmp.put(Localizations.ZH_CN, "选择性矿工");
        add(MPSItems.SELECTIVE_MINER.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Bricht Blöcke ähnlich wie der Venenminer, aber selektiv. Umschalttaste und klicken Sie, um den Blocktyp auszuwählen.");
        tmp.put(Localizations.EN_US, "Breaks blocks similar to the vein miner, but selectively. Shift and click to select block type.");
        tmp.put(Localizations.FR_FR, "Casse des blocs similaires au mineur de veines, mais de manière sélective. Maj et cliquez pour sélectionner le type de bloc.");
        tmp.put(Localizations.PT_BR, "Quebra blocos semelhantes ao minerador de veias, mas seletivamente. Shift e clique para selecionar o tipo de bloco.");
        tmp.put(Localizations.PT_PT, "Quebra blocos semelhantes ao mineiro de veia, mas seletivamente. Shift e clique para selecionar o tipo de bloco.");
        tmp.put(Localizations.RU_RU, "Разбивает блоки аналогично жильному добытчику, но выборочно. Shift и щелкните, чтобы выбрать тип блока.");
        tmp.put(Localizations.ZH_CN, "打破类似于矿脉矿工的区块，但有选择地。按住 Shift 并单击以选择块类型。");
        addItemDescriptions(MPSItems.SELECTIVE_MINER.get(), tmp);

        // Movement ====================================================================================
        // Blink Drive ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Blink-Laufwerk");
        tmp.put(Localizations.EN_US, "Blink Drive");
        tmp.put(Localizations.FR_FR, "Lecteur Blink");
        tmp.put(Localizations.PT_BR, "Unidade de piscar");
        tmp.put(Localizations.PT_PT, "Unidade Blink");
        tmp.put(Localizations.RU_RU, "Мигающий диск");
        tmp.put(Localizations.ZH_CN, "闪烁驱动器");
        add(MPSItems.BLINK_DRIVE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Gehen Sie von Punkt A über Punkt B zu Punkt C, wobei Punkt B eine Falte in Raum und Zeit ist.");
        tmp.put(Localizations.EN_US, "Get from point A to point C via point B, where point B is a fold in space & time.");
        tmp.put(Localizations.FR_FR, "Allez du point A au point C via le point B, où le point B est un pli dans l’espace et le temps.");
        tmp.put(Localizations.PT_BR, "Vá do ponto A ao ponto C através do ponto B, onde o ponto B é uma dobra no espaço e no tempo.");
        tmp.put(Localizations.PT_PT, "Vá do ponto A ao ponto C através do ponto B, onde o ponto B é uma dobra no espaço e no tempo.");
        tmp.put(Localizations.RU_RU, "Добраться из точки А в точку С можно через точку Б, где точка Б — это складка в пространстве и времени.\n");
        tmp.put(Localizations.ZH_CN, "通过B点从A点到C点，其中B点是空间和时间的褶皱。");
        addItemDescriptions(MPSItems.BLINK_DRIVE_MODULE.get(), tmp);

        // Uphill Step Assist --------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Bergauf-Tritthilfe");
        tmp.put(Localizations.EN_US, "Uphill Step Assist");
        tmp.put(Localizations.FR_FR, "Assistance aux pas en montée");
        tmp.put(Localizations.PT_BR, "Assistente de subida");
        tmp.put(Localizations.PT_PT, "Assistência de subida");
        tmp.put(Localizations.RU_RU, "Система помощи при подъеме в гору");
        tmp.put(Localizations.ZH_CN, "上坡台阶辅助");
        add(MPSItems.CLIMB_ASSIST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein Paar spezieller Servos ermöglicht es Ihnen, mühelos 1 m hohe Vorsprünge zu erklimmen.");
        tmp.put(Localizations.EN_US, "A pair of dedicated servos allow you to effortlessly step up 1m-high ledges.");
        tmp.put(Localizations.FR_FR, "Une paire de servomoteurs dédiés vous permet de monter sans effort sur des rebords de 1 m de haute.");
        tmp.put(Localizations.PT_BR, "Um par de servos dedicados permite que você aumente sem esforço as bordas de 1 m de altura.");
        tmp.put(Localizations.PT_PT, "Um par de servos dedicados permite-lhe subir sem esforço as bordas de 1 m de altura.");
        tmp.put(Localizations.RU_RU, "Пара специальных сервоприводов позволяет легко подниматься по уступам высотой 1 м.");
        tmp.put(Localizations.ZH_CN, "一对专用舵机可让您毫不费力地登上 1 米高的壁架。");
        addItemDescriptions(MPSItems.CLIMB_ASSIST_MODULE.get(), tmp);

        // Dimensional Tear Generator ------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Generator für dimensionale Risse");
        tmp.put(Localizations.EN_US, "Dimensional Tear Generator");
        tmp.put(Localizations.FR_FR, "Générateur de larmes dimensionnel");
        tmp.put(Localizations.PT_BR, "Gerador de Rasgo Dimensional");
        tmp.put(Localizations.PT_PT, "Gerador de lágrima dimensional");
        tmp.put(Localizations.RU_RU, "Генератор разрыва размеров");
        tmp.put(Localizations.ZH_CN, "尺寸撕裂发生器");
        add(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Erzeuge einen Riss im Raum-Zeit-Kontinuum, der den Spieler zu seinen relativen Koordinaten in der Unter- oder Oberwelt teleportiert.");
        tmp.put(Localizations.EN_US, "Generate a tear in the space-time continuum that will teleport the player to its relative coordinates in the nether or overworld.");
        tmp.put(Localizations.FR_FR, "Générez une déchirure dans le continuum espace-temps qui téléportera le joueur à ses coordonnées relatives dans le monde inférieur ou supérieur.");
        tmp.put(Localizations.PT_BR, "Gere uma ruptura no continuum espaço-tempo que teletransportará o jogador para suas coordenadas relativas na rede ou no sobremundo.");
        tmp.put(Localizations.PT_PT, "Gere uma rutura no continuum espaço-tempo que teletransportará o jogador para as suas coordenadas relativas no nether ou overworld.");
        tmp.put(Localizations.RU_RU, "Сгенерируйте разрыв в пространственно-временном континууме, который телепортирует игрока к его относительным координатам в нижнем или надземном мире.");
        tmp.put(Localizations.ZH_CN, "在时空连续体中生成撕裂，将玩家传送到其在下界或主世界中的相对坐标。");
        addItemDescriptions(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), tmp);

        // Flight Control ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Flugsteuerung");
        tmp.put(Localizations.EN_US, "Flight Control");
        tmp.put(Localizations.FR_FR, "Contrôle de vol");
        tmp.put(Localizations.PT_BR, "Controle de Voo");
        tmp.put(Localizations.PT_PT, "Controlo de Voo");
        tmp.put(Localizations.RU_RU, "Управление полетом");
        tmp.put(Localizations.ZH_CN, "飞行控制");
        add(MPSItems.FLIGHT_CONTROL_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Eine integrierte Steuerschaltung, die Ihnen hilft, besser zu fliegen. Drücken Sie Z, um nach unten zu gehen.");
        tmp.put(Localizations.EN_US, "An integrated control circuit to help you fly better. Press Z to go down.");
        tmp.put(Localizations.FR_FR, "Un circuit de contrôle intégré pour vous aider à mieux voler. Appuyez sur Z pour descendre.");
        tmp.put(Localizations.PT_BR, "Um circuito de controle integrado para ajudá-lo a voar melhor. Pressione Z para descer.");
        tmp.put(Localizations.PT_PT, "Um circuito de controlo integrado para o ajudar a voar melhor. Pressione Z para descer.");
        tmp.put(Localizations.RU_RU, "Интегрированная схема управления, которая поможет вам лучше летать. Нажмите Z, чтобы спуститься вниз.");
        tmp.put(Localizations.ZH_CN, "集成控制电路，帮助您更好地飞行。按 Z 向下。");
        addItemDescriptions(MPSItems.FLIGHT_CONTROL_MODULE.get(), tmp);

        // Glider --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Segelflugzeug");
        tmp.put(Localizations.EN_US, "Glider");
        tmp.put(Localizations.FR_FR, "Planeur");
        tmp.put(Localizations.PT_BR, "Planador");
        tmp.put(Localizations.PT_PT, "Planador");
        tmp.put(Localizations.RU_RU, "Планёр");
        tmp.put(Localizations.ZH_CN, "滑翔机");
        add(MPSItems.GLIDER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Wende ein paar Flügel an, um nach unten in Vorwärtsdynamik zu drehen. Drücke Schleichen+Vorwärts, während du fällst, um es zu aktivieren.");
        tmp.put(Localizations.EN_US, "Tack on some wings to turn downward into forward momentum. Press sneak+forward while falling to activate.");
        tmp.put(Localizations.FR_FR, "Accrochez certaines ailes pour tourner vers le bas dans un élan vers l’avant. Appuyez sur sneak + forward tout en tombant pour activer.");
        tmp.put(Localizations.PT_BR, "Agarre em algumas asas para virar para baixo em impulso para a frente. Pressione sneak+forward enquanto cai para ativar.");
        tmp.put(Localizations.PT_PT, "Agarre em algumas asas para virar para baixo em impulso para a frente. Pressione sneak+forward enquanto cai para ativar.");
        tmp.put(Localizations.RU_RU, "Нажмите на несколько крыльев, чтобы повернуть вниз в передний импульс. Нажмите sneak + forward во время падения, чтобы активировать.");
        tmp.put(Localizations.ZH_CN, "抓住一些翅膀，向下变成前进的动力。在跌落时按潜行+前进以激活。");
        addItemDescriptions(MPSItems.GLIDER_MODULE.get(), tmp);

        // Jet Boots -----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Jet-Stiefel");
        tmp.put(Localizations.EN_US, "Jet Boots");
        tmp.put(Localizations.FR_FR, "Bottes de jet");
        tmp.put(Localizations.PT_BR, "Botas a jato");
        tmp.put(Localizations.PT_PT, "Botas Jet");
        tmp.put(Localizations.RU_RU, "Реактивные ботинки");
        tmp.put(Localizations.ZH_CN, "喷气靴");
        add(MPSItems.JETBOOTS_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Jetboots sind nicht so stark wie ein Jetpack, aber sie sollten zumindest stark genug sein, um der Schwerkraft entgegenzuwirken.");
        tmp.put(Localizations.EN_US, "Jet boots are not as strong as a jetpack, but they should at least be strong enough to counteract gravity.");
        tmp.put(Localizations.FR_FR, "Les bottes de jet ne sont pas aussi solides qu’un jetpack, mais elles devraient au moins être assez solides pour contrer la gravité.");
        tmp.put(Localizations.PT_BR, "As botas a jato não são tão fortes quanto um jetpack, mas devem pelo menos ser fortes o suficiente para neutralizar a gravidade.");
        tmp.put(Localizations.PT_PT, "As botas a jato não são tão fortes quanto um jetpack, mas devem pelo menos ser fortes o suficiente para neutralizar a gravidade.");
        tmp.put(Localizations.RU_RU, "Реактивные ботинки не такие прочные, как реактивный ранец, но они, по крайней мере, должны быть достаточно прочными, чтобы противодействовать гравитации.");
        tmp.put(Localizations.ZH_CN, "喷气靴不如喷气背包坚固，但它们至少应该足够坚固以抵消重力。");
        addItemDescriptions(MPSItems.JETBOOTS_MODULE.get(), tmp);

        // Jetpack -------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Jetpack");
        tmp.put(Localizations.EN_US, "Jetpack");
        tmp.put(Localizations.FR_FR, "Jetpack");
        tmp.put(Localizations.PT_BR, "Pacote de jato");
        tmp.put(Localizations.PT_PT, "Jetpack");
        tmp.put(Localizations.RU_RU, "Реактивный ранец");
        tmp.put(Localizations.ZH_CN, "喷气背包");
        add(MPSItems.JETPACK_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein Jetpack sollte es dir ermöglichen, auf unbestimmte Zeit zu springen, oder zumindest bis dir die Kraft ausgeht.");
        tmp.put(Localizations.EN_US, "A jetpack should allow you to jump indefinitely, or at least until you run out of power.");
        tmp.put(Localizations.FR_FR, "Un jetpack devrait vous permettre de sauter indéfiniment, ou du moins jusqu’à ce que vous soyez à court de puissance.");
        tmp.put(Localizations.PT_BR, "Um jetpack deve permitir que você pule indefinidamente, ou pelo menos até ficar sem energia.");
        tmp.put(Localizations.PT_PT, "Um jetpack deve permitir que você salte indefinidamente, ou pelo menos até ficar sem energia.");
        tmp.put(Localizations.RU_RU, "Реактивный ранец должен позволять вам прыгать бесконечно или, по крайней мере, до тех пор, пока у вас не закончится заряд.");
        tmp.put(Localizations.ZH_CN, "喷气背包应该允许你无限期地跳跃，或者至少直到你耗尽动力。");
        addItemDescriptions(MPSItems.JETPACK_MODULE.get(), tmp);

        // Jump Assist ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Sprunghilfe");
        tmp.put(Localizations.EN_US, "Jump Assist");
        tmp.put(Localizations.FR_FR, "Aide au saut");
        tmp.put(Localizations.PT_BR, "Assistência de Salto");
        tmp.put(Localizations.PT_PT, "Assistência de salto");
        tmp.put(Localizations.RU_RU, "Помощь при прыжке");
        tmp.put(Localizations.ZH_CN, "跳跃辅助");
        add(MPSItems.JUMP_ASSIST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein weiterer Satz Servomotoren, mit denen Sie höher springen können.");
        tmp.put(Localizations.EN_US, "Another set of servo motors to help you jump higher.");
        tmp.put(Localizations.FR_FR, "Un autre ensemble de servomoteurs pour vous aider à sauter plus haute.");
        tmp.put(Localizations.PT_BR, "Outro conjunto de servomotores para ajudá-lo a saltar mais alto.");
        tmp.put(Localizations.PT_PT, "Outro conjunto de servomotores para ajudá-lo a saltar mais alto.");
        tmp.put(Localizations.RU_RU, "Еще один набор серводвигателей, которые помогут вам прыгнуть выше.");
        tmp.put(Localizations.ZH_CN, "另一套伺服电机可帮助您跳得更高。");
        addItemDescriptions(MPSItems.JUMP_ASSIST_MODULE.get(), tmp);

        // Parachute -----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Fallschirm");
        tmp.put(Localizations.EN_US, "Parachute");
        tmp.put(Localizations.FR_FR, "Parachute");
        tmp.put(Localizations.PT_BR, "Pára-quedas");
        tmp.put(Localizations.PT_PT, "Para-quedas");
        tmp.put(Localizations.RU_RU, "Парашют");
        tmp.put(Localizations.ZH_CN, "降落伞");
        add(MPSItems.PARACHUTE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Füge einen Fallschirm hinzu, um deinen Sinkflug zu verlangsamen. Aktiviere durch Drücken von Schleichen (standardmäßig Umschalttaste) in der Luft.");
        tmp.put(Localizations.EN_US, "Add a parachute to slow your descent. Activate by pressing sneak (defaults to Shift) in midair.");
        tmp.put(Localizations.FR_FR, "Ajoutez un parachute pour ralentir votre descente. Activez en appuyant sur sneak (par défaut sur Shift) dans les airs.");
        tmp.put(Localizations.PT_BR, "Adicione um paraquedas para retardar sua descida. Ative pressionando sneak (padrão para Shift) no ar.");
        tmp.put(Localizations.PT_PT, "Adicione um para-quedas para abrandar a sua descida. Ative pressionando sorrateiramente (o padrão é Shift) no ar.");
        tmp.put(Localizations.RU_RU, "Добавьте парашют, чтобы замедлить спуск. Активируйте, нажав клавишу sneak (по умолчанию Shift) в воздухе.");
        tmp.put(Localizations.ZH_CN, "添加降落伞以减慢下降速度。通过在半空中按潜行（默认为 Shift）激活。");
        addItemDescriptions(MPSItems.PARACHUTE_MODULE.get(), tmp);

        // Shock Absorber ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Stoßdämpfer");
        tmp.put(Localizations.EN_US, "Shock Absorber");
        tmp.put(Localizations.FR_FR, "Amortisseur");
        tmp.put(Localizations.PT_BR, "Amortecedor");
        tmp.put(Localizations.PT_PT, "Amortecedor");
        tmp.put(Localizations.RU_RU, "Амортизатор");
        tmp.put(Localizations.ZH_CN, "减震器");
        add(MPSItems.SHOCK_ABSORBER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Avec certains servos, ressorts et rembourrages, vous devriez être en mesure d’annuler une partie des dommages causés par la chute.");
        tmp.put(Localizations.EN_US, "With some servos, springs, and padding, you should be able to negate a portion of fall damage.");
        tmp.put(Localizations.FR_FR, "Avec certains servos, ressorts et rembourrages, vous devriez être en mesure d’annuler une partie des dommages causés par la chute.");
        tmp.put(Localizations.PT_BR, "Com alguns servos, molas e estofamento, você deve ser capaz de anular uma parte do dano de queda.");
        tmp.put(Localizations.PT_PT, "Com alguns servos, molas e preenchimento, você deve ser capaz de negar uma parte dos danos causados pela queda.");
        tmp.put(Localizations.RU_RU, "С некоторыми сервоприводами, пружинами и набивкой вы сможете свести на нет часть повреждений при падении.");
        tmp.put(Localizations.ZH_CN, "使用一些舵机、弹簧和衬垫，您应该能够抵消一部分坠落损坏。");
        addItemDescriptions(MPSItems.SHOCK_ABSORBER_MODULE.get(), tmp);

        // Sprint Assist -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Sprint-Assistent");
        tmp.put(Localizations.EN_US, "Sprint Assist");
        tmp.put(Localizations.FR_FR, "Assistance au sprint");
        tmp.put(Localizations.PT_BR, "Assistência de Sprint");
        tmp.put(Localizations.PT_PT, "Assistência Sprint");
        tmp.put(Localizations.RU_RU, "Помощь в спринте");
        tmp.put(Localizations.ZH_CN, "冲刺辅助");
        add(MPSItems.SPRINT_ASSIST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Eine Reihe von Servomotoren, die Ihnen helfen, schneller zu sprinten (doppelt nach vorne zu tippen) und schneller zu gehen.");
        tmp.put(Localizations.EN_US, "A set of servo motors to help you sprint (double-tap forward) and walk faster.");
        tmp.put(Localizations.FR_FR, "Un ensemble de servomoteurs pour vous aider à sprinter (appuyez deux fois vers l’avant) et à marcher plus vite.");
        tmp.put(Localizations.PT_BR, "Um conjunto de servomotores para ajudá-lo a sprint (toque duas vezes para frente) e andar mais rápido.");
        tmp.put(Localizations.PT_PT, "Um conjunto de servomotores para ajudá-lo a sprint (toque duplo para a frente) e andar mais rápido.");
        tmp.put(Localizations.RU_RU, "Набор серводвигателей, которые помогут вам бежать (дважды нажимать вперед) и идти быстрее.");
        tmp.put(Localizations.ZH_CN, "一组伺服电机，可帮助您冲刺（双击前进）并走得更快。");
        addItemDescriptions(MPSItems.SPRINT_ASSIST_MODULE.get(), tmp);

        // Swim Boost ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Schwimm-Boost");
        tmp.put(Localizations.EN_US, "Swim Boost");
        tmp.put(Localizations.FR_FR, "Boost de natation");
        tmp.put(Localizations.PT_BR, "Impulso de natação");
        tmp.put(Localizations.PT_PT, "Impulso de natação");
        tmp.put(Localizations.RU_RU, "Ускорение плавания");
        tmp.put(Localizations.ZH_CN, "游泳助推");
        add(MPSItems.SWIM_BOOST_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Durch den Umbau eines Ionentriebwerks für den Unterwassereinsatz können Sie möglicherweise zusätzlichen Vorwärts- (oder Rückwärts-) Schub hinzufügen, wenn Sie unter Wasser sind.");
        tmp.put(Localizations.EN_US, "By refitting an ion thruster for underwater use, you may be able to add extra forward (or backward) thrust when underwater.");
        tmp.put(Localizations.FR_FR, "En rééquipant un propulseur ionique pour une utilisation sous-marine, vous pourrez peut-être ajouter une poussée supplémentaire vers l’avant (ou vers l’arrière) sous l’eau.");
        tmp.put(Localizations.PT_BR, "Ao reajustar um propulsor de íons para uso subaquático, você pode adicionar impulso extra para frente (ou para trás) quando debaixo d'água.");
        tmp.put(Localizations.PT_PT, "Ao reequipar um propulsor de iões para utilização subaquática, poderá adicionar impulso extra para a frente (ou para trás) quando estiver debaixo de água.");
        tmp.put(Localizations.RU_RU, "Переоборудовав ионный двигатель для подводного использования, вы сможете добавить дополнительную тягу вперед (или назад) под водой.");
        tmp.put(Localizations.ZH_CN, "通过改装离子推进器以供水下使用，您可以在水下增加额外的向前（或向后）推力。");
        addItemDescriptions(MPSItems.SWIM_BOOST_MODULE.get(), tmp);

        // Special =====================================================================================
        // Transparent Armor ---------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Transparente Rüstung");
        tmp.put(Localizations.EN_US, "Transparent Armor");
        tmp.put(Localizations.FR_FR, "Armure transparente");
        tmp.put(Localizations.PT_BR, "Armadura Transparente");
        tmp.put(Localizations.PT_PT, "Armadura Transparente");
        tmp.put(Localizations.RU_RU, "Прозрачная броня");
        tmp.put(Localizations.ZH_CN, "透明装甲");
        add(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Machen Sie den Gegenstand transparent, damit Sie Ihre Haut zeigen können, ohne die Rüstung zu verlieren.");
        tmp.put(Localizations.EN_US, "Make the item transparent, so you can show off your skin without losing armor.");
        tmp.put(Localizations.FR_FR, "Rendez l’objet transparent, afin que vous puissiez montrer votre peau sans perdre d’armure.");
        tmp.put(Localizations.PT_BR, "Torne o item transparente, para que você possa mostrar sua pele sem perder a armadura.");
        tmp.put(Localizations.PT_PT, "Torne o item transparente, para que você possa mostrar sua pele sem perder a armadura.");
        tmp.put(Localizations.RU_RU, "Сделайте предмет прозрачным, чтобы вы могли похвастаться своей кожей, не теряя брони.");
        tmp.put(Localizations.ZH_CN, "使物品透明，这样您就可以在不失去盔甲的情况下炫耀您的皮肤。");
        addItemDescriptions(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), tmp);

        // Magnet --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Magnet");
        tmp.put(Localizations.EN_US, "Magnet");
        tmp.put(Localizations.FR_FR, "Aimant");
        tmp.put(Localizations.PT_BR, "Ímã");
        tmp.put(Localizations.PT_PT, "Íman");
        tmp.put(Localizations.RU_RU, "Магнит");
        tmp.put(Localizations.ZH_CN, "磁铁");
        add(MPSItems.MAGNET_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Erzeugt ein Magnetfeld, das stark genug ist, um Gegenstände in Richtung des Spielers zu ziehen.         WARNUNG: Dieses Modul verbraucht kontinuierlich Strom. Schalten Sie es aus, wenn es nicht benötigt wird.");
        tmp.put(Localizations.EN_US, "Generates a magnetic field strong enough to attract items towards the player.         WARNING:                   This module drains power continuously. Turn it off when not needed.");
        tmp.put(Localizations.FR_FR, "Génère un champ magnétique suffisamment puissant pour attirer les objets vers le joueur.         AVERTISSEMENT : Ce module décharge l’alimentation en continu. Éteignez-le lorsque vous n’en avez pas besoin.");
        tmp.put(Localizations.PT_BR, "Gera um campo magnético forte o suficiente para atrair itens para o jogador.         AVISO: Este módulo drena energia continuamente. Desligue-o quando não for necessário.");
        tmp.put(Localizations.PT_PT, "Gera um campo magnético forte o suficiente para atrair itens para o jogador.         AVISO: Este módulo drena energia continuamente. Desligue-o quando não for necessário.");
        tmp.put(Localizations.RU_RU, "Генерирует магнитное поле, достаточно сильное, чтобы притягивать предметы к игроку.         ПРЕДУПРЕЖДЕНИЕ: Этот модуль постоянно разряжает питание. Выключайте его, когда он не нужен.");
        tmp.put(Localizations.ZH_CN, "产生足够强的磁场，以吸引物品朝向玩家。        警告：此模块持续耗电。不需要时将其关闭。");
        addItemDescriptions(MPSItems.MAGNET_MODULE.get(), tmp);

        // Piglin Pacification Module ------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Piglin Beruhigungsmodul");
        tmp.put(Localizations.EN_US, "Piglin Pacification Module");
        tmp.put(Localizations.FR_FR, "Piglin Pacification Module");
        tmp.put(Localizations.PT_BR, "Módulo de Pacificação Piglin");
        tmp.put(Localizations.PT_PT, "Módulo de Pacificação Piglin");
        tmp.put(Localizations.RU_RU, "Модуль умиротворения Пиглина");
        tmp.put(Localizations.ZH_CN, "皮格林安抚模块");
        add(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Einfaches Modul, um Piglins neutral zu machen, als ob sie eine goldene Rüstung tragen würden");
        tmp.put(Localizations.EN_US, "Simple module to make Piglins neutral as if wearing gold armor");
        tmp.put(Localizations.FR_FR, "Module simple pour rendre les Piglins neutres comme s’ils portaient une armure en or");
        tmp.put(Localizations.PT_BR, "Módulo simples para tornar Piglins neutro como se usasse armadura dourada");
        tmp.put(Localizations.PT_PT, "Módulo simples para tornar Piglins neutro como se usasse armadura de ouro");
        tmp.put(Localizations.RU_RU, "Простой модуль, чтобы сделать пиглинов нейтральными, как если бы они носили золотые доспехи");
        tmp.put(Localizations.ZH_CN, "简单的模块，让皮林斯像穿着金色盔甲一样中立");
        addItemDescriptions(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), tmp);

        // Vision ======================================================================================
        // Binoculars ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Fernglas");
        tmp.put(Localizations.EN_US, "Binoculars");
        tmp.put(Localizations.FR_FR, "Jumelles");
        tmp.put(Localizations.PT_BR, "Binóculo");
        tmp.put(Localizations.PT_PT, "Binóculos");
        tmp.put(Localizations.RU_RU, "Бинокль");
        tmp.put(Localizations.ZH_CN, "望远镜");
        add(MPSItems.BINOCULARS_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Angesichts der Probleme, die Optifine in letzter Zeit geplagt haben, haben Sie beschlossen, diese Zoom-Fähigkeit selbst in die Hand zu nehmen.");
        tmp.put(Localizations.EN_US, "With the problems that have been plaguing Optifine lately, you've decided to take that Zoom ability into your own hands.");
        tmp.put(Localizations.FR_FR, "Avec les problèmes qui ont affligé Optifine ces derniers temps, vous avez décidé de prendre cette capacité de zoom en main.");
        tmp.put(Localizations.PT_BR, "Com os problemas que têm assolado o Optifine ultimamente, você decidiu tomar essa habilidade do Zoom em suas próprias mãos.");
        tmp.put(Localizations.PT_PT, "Com os problemas que têm assolado a Optifine ultimamente, você decidiu tomar essa capacidade de Zoom em suas próprias mãos.");
        tmp.put(Localizations.RU_RU, "Из-за проблем, которые в последнее время преследуют Optifine, вы решили взять эту возможность Zoom в свои руки.");
        tmp.put(Localizations.ZH_CN, "由于最近困扰Optifine的问题，您决定将Zoom功能掌握在自己手中。");
        addItemDescriptions(MPSItems.BINOCULARS_MODULE.get(), tmp);

        // Night Vision --------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Nachtsicht");
        tmp.put(Localizations.EN_US, "Night Vision");
        tmp.put(Localizations.FR_FR, "Nyctalopie");
        tmp.put(Localizations.PT_BR, "Visão Noturna");
        tmp.put(Localizations.PT_PT, "Visão Noturna");
        tmp.put(Localizations.RU_RU, "Ночное видение");
        tmp.put(Localizations.ZH_CN, "夜视");
        add(MPSItems.NIGHT_VISION_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Eine Augmented-Vision-Brille, die Ihnen hilft, nachts und unter Wasser zu sehen.");
        tmp.put(Localizations.EN_US, "A pair of augmented vision goggles to help you see at night and underwater.");
        tmp.put(Localizations.FR_FR, "Une paire de lunettes de vision augmentée pour vous aider à voir la nuit et sous l’eau.");
        tmp.put(Localizations.PT_BR, "Um par de óculos de visão aumentada para ajudá-lo a ver à noite e debaixo d'água.");
        tmp.put(Localizations.PT_PT, "Um par de óculos de visão aumentada para ajudá-lo a ver à noite e debaixo d'água.");
        tmp.put(Localizations.RU_RU, "Пара очков с дополненным зрением, которые помогут вам видеть ночью и под водой.");
        tmp.put(Localizations.ZH_CN, "一副增强型护目镜，可帮助您在夜间和水下看清事物。");
        addItemDescriptions(MPSItems.NIGHT_VISION_MODULE.get(), tmp);

        // Tools =======================================================================================
        // Axe -----------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Axt");
        tmp.put(Localizations.EN_US, "Axe");
        tmp.put(Localizations.FR_FR, "Hache");
        tmp.put(Localizations.PT_BR, "Machado");
        tmp.put(Localizations.PT_PT, "Machado");
        tmp.put(Localizations.RU_RU, "Топор");
        tmp.put(Localizations.ZH_CN, "斧");
        add(MPSItems.AXE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Äxte dienen hauptsächlich zum Fällen von Bäumen.");
        tmp.put(Localizations.EN_US, "Axes are mostly for chopping trees.");
        tmp.put(Localizations.FR_FR, "Les haches servent principalement à couper des arbres.");
        tmp.put(Localizations.PT_BR, "Os machados são principalmente para cortar árvores.");
        tmp.put(Localizations.PT_PT, "Os eixos são principalmente para cortar árvores.");
        tmp.put(Localizations.RU_RU, "Топоры в основном предназначены для рубки деревьев.");
        tmp.put(Localizations.ZH_CN, "斧头多用于砍树。");
        addItemDescriptions(MPSItems.AXE_MODULE.get(), tmp);

        // Diamond Drill Upgrade -----------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Diamantbohrer-Upgrade");
        tmp.put(Localizations.EN_US, "Diamond Drill Upgrade");
        tmp.put(Localizations.FR_FR, "Mise à niveau de Diamond Drill");
        tmp.put(Localizations.PT_BR, "Atualização da broca diamantada");
        tmp.put(Localizations.PT_PT, "Atualização do Diamond Drill");
        tmp.put(Localizations.RU_RU, "Модернизация алмазного сверла");
        tmp.put(Localizations.ZH_CN, "金刚石钻升级");
        add(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Füge Diamanten hinzu, damit dein Spitzhackenmodul Obsidian abbauen kann. *BENÖTIGT EIN SPITZHACKENMODUL, UM ZU FUNKTIONIEREN*");
        tmp.put(Localizations.EN_US, "Add diamonds to allow your pickaxe module to mine Obsidian. *REQUIRES PICKAXE MODULE TO WORK*");
        tmp.put(Localizations.FR_FR, "Ajoutez des diamants pour permettre à votre module de pioche d’extraire de l’obsidienne. * NÉCESSITE UN MODULE DE PIOCHE POUR FONCTIONNER *");
        tmp.put(Localizations.PT_BR, "Adicione diamantes para permitir que seu módulo picareta minere Obsidian. *REQUER MÓDULO PICARETA PARA FUNCIONAR*");
        tmp.put(Localizations.PT_PT, "Adicione diamantes para permitir que seu módulo de picareta minere Obsidian. *REQUER MÓDULO DE PICARETA PARA FUNCIONAR*");
        tmp.put(Localizations.RU_RU, "Добавляйте алмазы, чтобы ваш модуль кирки мог добывать обсидиан. *ДЛЯ РАБОТЫ ТРЕБУЕТСЯ МОДУЛЬ КИРКИ*");
        tmp.put(Localizations.ZH_CN, "添加钻石，让您的镐模块开采黑曜石。*需要镐模块才能工作*");
        addItemDescriptions(MPSItems.DIAMOND_PICK_UPGRADE_MODULE.get(), tmp);

        // Flint and Steel -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Feuerstein und Stahl");
        tmp.put(Localizations.EN_US, "Flint and Steel");
        tmp.put(Localizations.FR_FR, "silex et acier");
        tmp.put(Localizations.PT_BR, "Sílex e Aço");
        tmp.put(Localizations.PT_PT, "Sílex e Aço");
        tmp.put(Localizations.RU_RU, "Кремень и сталь");
        tmp.put(Localizations.ZH_CN, "燧石和钢");
        add(MPSItems.FLINT_AND_STEEL_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein tragbarer Zünder, der durch die Kraft der Energie Feuer erzeugt.");
        tmp.put(Localizations.EN_US, "A portable igniter that creates fire through the power of energy.");
        tmp.put(Localizations.FR_FR, "Un allumeur portable qui crée du feu grâce à la puissance de l’énergie.");
        tmp.put(Localizations.PT_BR, "Um ignitor portátil que cria fogo através do poder da energia.");
        tmp.put(Localizations.PT_PT, "Um ignitor portátil que cria fogo através do poder da energia.");
        tmp.put(Localizations.RU_RU, "Портативный воспламенитель, который создает огонь за счет силы энергии.");
        tmp.put(Localizations.ZH_CN, "一种便携式点火器，通过能量产生火灾。");
        addItemDescriptions(MPSItems.FLINT_AND_STEEL_MODULE.get(), tmp);

        // Rototiller ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rototiller");
        tmp.put(Localizations.EN_US, "Rototiller");
        tmp.put(Localizations.FR_FR, "Rototiller");
        tmp.put(Localizations.PT_BR, "Rototiller");
        tmp.put(Localizations.PT_PT, "Rototiller");
        tmp.put(Localizations.RU_RU, "Ротационный культиватор");
        tmp.put(Localizations.ZH_CN, "旋转耕耘机");
        add(MPSItems.HOE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ein automatisiertes Bodenbearbeitungs-Addon, das es einfach macht, große Landstriche auf einmal zu bestellen.");
        tmp.put(Localizations.EN_US, "An automated tilling addon to make it easy to till large swaths of land at once.");
        tmp.put(Localizations.FR_FR, "Un addon de labour automatisé pour faciliter la culture de grandes étendues de terre à la fois.");
        tmp.put(Localizations.PT_BR, "Um addon de lavoura automatizado para facilitar a lavoura de grandes extensões de terra de uma só vez.");
        tmp.put(Localizations.PT_PT, "Um addon de lavoura automatizado para facilitar o cultivo de grandes extensões de terra de uma só vez.");
        tmp.put(Localizations.RU_RU, "Аддон для автоматической обработки почвы, упрощающий одновременную обработку больших участков земли.");
        tmp.put(Localizations.ZH_CN, "自动耕作插件，可轻松一次耕种大片土地。");
        addItemDescriptions(MPSItems.HOE_MODULE.get(), tmp);

        // Leaf Blower ---------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Laubbläser");
        tmp.put(Localizations.EN_US, "Leaf Blower");
        tmp.put(Localizations.FR_FR, "Souffleur");
        tmp.put(Localizations.PT_BR, "Soprador de Folhas");
        tmp.put(Localizations.PT_PT, "Soprador de Folhas");
        tmp.put(Localizations.RU_RU, "Воздуходувка для листьев");
        tmp.put(Localizations.ZH_CN, "吹叶机");
        add(MPSItems.LEAF_BLOWER_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Erzeugen Sie einen Luftstrom, um Pflanzen aus dem Boden und Blätter von Bäumen zu schlagen.");
        tmp.put(Localizations.EN_US, "Create a torrent of air to knock plants out of the ground and leaves off of trees.");
        tmp.put(Localizations.FR_FR, "Créez un torrent d’air pour faire tomber les plantes du sol et les feuilles des arbres.");
        tmp.put(Localizations.PT_BR, "Crie uma torrente de ar para derrubar plantas do chão e folhas de árvores.");
        tmp.put(Localizations.PT_PT, "Crie uma torrente de ar para derrubar plantas do chão e folhas de árvores.");
        tmp.put(Localizations.RU_RU, "Создайте поток воздуха, чтобы выбить растения из земли и листья с деревьев.");
        tmp.put(Localizations.ZH_CN, "制造一股空气洪流，将植物从地下击落，将树叶从树上击落。");
        addItemDescriptions(MPSItems.LEAF_BLOWER_MODULE.get(), tmp);

        // Lux Capacitor -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Lux-Kondensator");
        tmp.put(Localizations.EN_US, "Lux Capacitor");
        tmp.put(Localizations.FR_FR, "Condensateur Lux");
        tmp.put(Localizations.PT_BR, "Lux Capacitor");
        tmp.put(Localizations.PT_PT, "Lux Capacitor");
        tmp.put(Localizations.RU_RU, "Конденсатор люкс");
        tmp.put(Localizations.ZH_CN, "勒克斯电容器");
        add(MPSItems.LUX_CAPACITOR_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Starten Sie eine praktisch unendliche Anzahl attraktiver Lichtquellen an der Wand.");
        tmp.put(Localizations.EN_US, "Launch a virtually infinite number of attractive light sources at the wall.");
        tmp.put(Localizations.FR_FR, "Lancez un nombre pratiquement infini de sources lumineuses attrayantes sur le mur.");
        tmp.put(Localizations.PT_BR, "Lançar um número virtualmente infinito de fontes de luz atraentes na parede.");
        tmp.put(Localizations.PT_PT, "Lançar um número virtualmente infinito de fontes de luz atraentes na parede.");
        tmp.put(Localizations.RU_RU, "Запускайте практически бесконечное количество привлекательных источников света на стене.");
        tmp.put(Localizations.ZH_CN, "在墙上发射几乎无限数量的有吸引力的光源。");
        addItemDescriptions(MPSItems.LUX_CAPACITOR_MODULE.get(), tmp);

        // Pickaxe -------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Spitzhacke");
        tmp.put(Localizations.EN_US, "Pickaxe");
        tmp.put(Localizations.FR_FR, "Pioche");
        tmp.put(Localizations.PT_BR, "Picareta");
        tmp.put(Localizations.PT_PT, "Picareta");
        tmp.put(Localizations.RU_RU, "Кирка");
        tmp.put(Localizations.ZH_CN, "镐头");
        add(MPSItems.PICKAXE_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Spitzhacken eignen sich gut für härtere Materialien wie Stein und Erz.");
        tmp.put(Localizations.EN_US, "Picks are good for harder materials like stone and ore.");
        tmp.put(Localizations.FR_FR, "Les pics sont bons pour les matériaux plus durs comme la pierre et le minerai.");
        tmp.put(Localizations.PT_BR, "As picaretas são boas para materiais mais duros, como pedra e minério.");
        tmp.put(Localizations.PT_PT, "As picaretas são boas para materiais mais duros, como pedra e minério.");
        tmp.put(Localizations.RU_RU, "Кирки хороши для более твердых материалов, таких как камень и руда.");
        tmp.put(Localizations.ZH_CN, "镐适用于较硬的材料，如石头和矿石。");
        addItemDescriptions(MPSItems.PICKAXE_MODULE.get(), tmp);

        // Shears --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Ciseaux");
        tmp.put(Localizations.EN_US, "Shears");
        tmp.put(Localizations.FR_FR, "Ciseaux");
        tmp.put(Localizations.PT_BR, "Tesoura");
        tmp.put(Localizations.PT_PT, "Tesouras");
        tmp.put(Localizations.RU_RU, "Ножницы");
        tmp.put(Localizations.ZH_CN, "剪");
        add(MPSItems.SHEARS_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Schneidet durch Blätter, Wolle und Schlingpflanzen gleichermaßen.");
        tmp.put(Localizations.EN_US, "Cuts through leaves, wool, and creepers alike.");
        tmp.put(Localizations.FR_FR, "Coupe à travers les feuilles, la laine et les lianes.");
        tmp.put(Localizations.PT_BR, "Corta folhas, lã e trepadeiras.");
        tmp.put(Localizations.PT_PT, "Corta folhas, lã e trepadeiras.");
        tmp.put(Localizations.RU_RU, "Прорезает листья, шерсть и лианы.");
        tmp.put(Localizations.ZH_CN, "切开树叶、羊毛和爬行者。");
        addItemDescriptions(MPSItems.SHEARS_MODULE.get(), tmp);

        // Shovel --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Schaufel");
        tmp.put(Localizations.EN_US, "Shovel");
        tmp.put(Localizations.FR_FR, "Pelle");
        tmp.put(Localizations.PT_BR, "Pá");
        tmp.put(Localizations.PT_PT, "Pá");
        tmp.put(Localizations.RU_RU, "Лопата");
        tmp.put(Localizations.ZH_CN, "铲");
        add(MPSItems.SHOVEL_MODULE.get(), tmp);

        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Schaufeln eignen sich gut für weiche Materialien wie Schmutz und Sand.");
        tmp.put(Localizations.EN_US, "Shovels are good for soft materials like dirt and sand.");
        tmp.put(Localizations.FR_FR, "Les pelles sont bonnes pour les matériaux mous comme la terre et le sable.");
        tmp.put(Localizations.PT_BR, "As pás são boas para materiais macios como sujeira e areia.");
        tmp.put(Localizations.PT_PT, "As pás são boas para materiais macios, como sujeira e areia.");
        tmp.put(Localizations.RU_RU, "Лопаты хороши для мягких материалов, таких как грязь и песок.");
        tmp.put(Localizations.ZH_CN, "铲子适用于泥土和沙子等软材料。");
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

    void addBlocks() {
        // Lux Capacitor -------------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Lux Capacitor");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSBlocks.LUX_CAPACITOR_BLOCK.get(), tmp);

        // Power Armor Tinker Table --------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Power Armor Tinker Table");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add(MPSBlocks.TINKER_TABLE_BLOCK.get(), tmp);
    }

    void addGui() {
        // FIXME: 2 different "install" translation and description sets

        // Armor ---------------------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Armor");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.armor", tmp);

        // Cancel --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Cancel");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.cancel", tmp);

        // Color ---------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Color:");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.colorPrefix", tmp);

        // Compatible Modules -------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Compatible Modules");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.compatible.modules", tmp);

        // Install (creative) --------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Install");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.creative.install", tmp);

        // Install (creative, description) -------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Installs module into selected modular item while player is in creative mode");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.creative.install.desc", tmp);

        // Energy Storage ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Energy Storage");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.energyStorage", tmp);

        // Equipped Totals -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Equipped Totals");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.equippedTotals", tmp);

        // Install -------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Install");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("gui.powersuits.install", tmp);

        //

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.install.desc", "Installs the mopdule in the selected item");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.installed.modules", "Installed Modules");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.load", "Load");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.new", "New");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.noModulesFound.line1", "No Modular Powersuit items");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.noModulesFound.line2", "found in inventory. Make some!");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.reset", "Reset");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.salvage", "Salvage");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.salvage.desc", "Remove module from selected item and return to player");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.save", "Save");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.saveAs", "Save as:");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("gui.powersuits.savesuccessful", "Save Successful");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.showOnHud", "Show on HUD:");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.tab.install.salvage", "Install/Salvage");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.tab.keybinds", "Keybinds");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.tab.module.tweak", "Module Tweak");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.tab.visual", "Visual");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("gui.powersuits.tinker", "Tinker");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        // FIXME: Inconsistant translation key
        add("powersuits.modularitem.inventory", "Modular Item Inventory");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
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















    void addModuleTradeoff() {
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.activationPercent", "Activation Percent");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.alpha", "Alpha");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);



        add("module.tradeoff.amperage", "Amperage");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        // FIXME: translation key changed?
        add("module.tradeoff.aoe2Limit", "Block Limit");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.aoeMiningDiameter", "AoE Mining Diameter");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);



        add("module.tradeoff.aquaHarvSpeed", "Harvest Speed");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.armorEnergy", "Armor (Energy)");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.armorEnergyPerDamage", "Energy Per Damage");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.armorPhysical", "Armor (Physical)");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.armorPoints", "pts");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.autoFeederEfficiency", "Auto-Feeder Efficiency");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.blinkDriveRange", "Range");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.blue", "Blue");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.blueHue", "Lux Capacitor Blue Hue");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.carryThrough", "Carry-through");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.compensation", "Compensation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.coolingBonus", "Cooling Bonus");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.daytimeEnergyGen", "Daytime Energy Generation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.daytimeHeatGen", "Daytime Heat Generation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.diameter", "Diameter");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.efficiency", "Efficiency");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.enchLevel", "Enchantment Level");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.energyCon", "Energy Consumption");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.energyGenerated", "Energy Generated");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.energyPerBlock", "Energy Per Block Per Second");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.fOVMult", "FOV multiplier");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.fieldOfView", "Field of View");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.fieldStrength", "Field Strength");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.fluidTankSize", "Tank Size");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.fortuneEnCon", "Fortune Energy Consumption");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.fortuneLevel", "Fortune Level");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.green", "Green");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.greenHue", "Lux Capacitor Green Hue");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.harvSpeed", "Harvest Speed");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.harvestSpeed", "Harvest Speed");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.heatActivationPercent", "Heat Activation Percent");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.heatEmission", "Heat Emission");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.heatGen", "Heat Generation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.heatGeneration", "Heat Generation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.impact", "Impact");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.jetbootsThrust", "Jetboots Thrust");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.jetpackThrust", "Jetpack Thrust");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.knockbackResistance", "Knockback Resistance");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.maxHeat", "Maximum Heat");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.meleeDamage", "Melee Damage");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.meleeKnockback", "Melee Knockback");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.movementResistance", "Movement Resistance");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.multiplier", "Multiplier");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.nightTimeEnergyGen", "Nighttime Energy Generation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.nightTimeHeatGen", "Nighttime Heat Generation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.opacity", "Lux Capacitor Opacity");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.overclock", "Overclock");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.plasmaDamage", "Plasma Damage At Full Charge");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.plasmaEnergyPerTick", "Plasma Energy Per Tick");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.plasmaExplosiveness", "Plasma Explosiveness");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.power", "Power");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.radius", "Radius");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.railgunEnergyCost", "Railgun Energy Cost");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.railgunHeatEm", "Railgun Heat Emission");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.railgunTotalImpulse", "Railgun Total Impulse");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.range", "Range");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.red", "Red");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.redHue", "Lux Capacitor Red Hue");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.silkTouchEnCon", "Silk Touch Energy Consumption");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.spinBladeDam", "Spinning Blade Damage");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.sprintAssist", "Sprint Assist");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.sprintEnergyCon", "Sprint Energy Consumption");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.sprintExComp", "Sprint Exhaustion Compensation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.sprintSpeedMult", "Sprint Speed Multiplier");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.thermalEnergyGen", "Energy Generation");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.thrust", "Thrust");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.underwaterMovBoost", "Underwater Movement Boost");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.vertically", "Verticality");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.voltage", "Voltage");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.walkingAssist", "Walking Assist");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.walkingEnergyCon", "Walking Energy Consumption");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("module.tradeoff.walkingSpeedMult", "Walking Speed Multiplier");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("module.tradeoff.yLookRatio", "Y-look ratio");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
    }


    void addKeybinds() {
        // FIXME: change this to something like "Toggle %s");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("keybinding.minecraft.clock", "Toggle Clock");
        add("keybinding.minecraft.compass", "Toggle Compass");
        add("keybinding.powersuits.selective_miner", "Toggle AoE");
        add("keybinding.powersuits.aqua_affinity", "Toggle Aqua Affinity");
        add("keybinding.powersuits.auto_feeder", "Toggle Auto-Feeder");
        add("keybinding.powersuits.binoculars", "Toggle Binoculars");
        add("keybinding.powersuits.climb_assist", "Toggle Uphill Step Assist");
        add("keybinding.powersuits.cooling_system", "Toggle Cooling System");
        add("keybinding.powersuits.cycleToolBackward", "Cycle Tool Backward (MPS)");
        add("keybinding.powersuits.cycleToolForward", "Cycle Tool Forward (MPS)");
        add("keybinding.powersuits.energy_shield", "Toggle Energy Shield");
        add("keybinding.powersuits.flight_control", "Toggle Flight Control");
        add("keybinding.powersuits.fluid_tank", "Toggle Fluid Tank");
        add("keybinding.powersuits.fortune", "Toggle Fortune Enchantment");
        add("keybinding.powersuits.generator_kinetic", "Toggle Kinetic Generator");
        add("keybinding.powersuits.generator_solar", "Toggle Solar Generator");
        add("keybinding.powersuits.generator_solar_adv", "Toggle Advanced Solar Generator");
        add("keybinding.powersuits.generator_thermal", "Toggle Thermal Generator");
        add("keybinding.powersuits.glider", "Toggle Glider");
        add("keybinding.powersuits.goDownKey", "Go Down (MPS Flight Control)");
        add("keybinding.powersuits.invisibility", "Toggle Active Camouflage");
        add("keybinding.powersuits.jet_boots", "Toggle Jet Boots");
        add("keybinding.powersuits.jetpack", "Toggle Jetpack");
        add("keybinding.powersuits.jump_assist", "Toggle Jump Assist");
        add("keybinding.powersuits.magnet", "Toggle Magnet");
        add("keybinding.powersuits.mob_repulsor", "Toggle Mob Repulsor");
        add("keybinding.powersuits.night_vision", "Toggle Night Vision");
        add("keybinding.powersuits.openCosmeticGUI", "Open MPS Cosmetic GUI");
        add("keybinding.powersuits.openInstallSalvageGUI", "Cosmetic GUI (MPS)");
        add("keybinding.powersuits.openKeybindGui", "Open MPS Keybind GUI");
        add("keybinding.powersuits.openModuleTweakGUI", "Open MPS Keybind GUI");
        add("keybinding.powersuits.parachute", "Toggle Parachute");
        add("keybinding.powersuits.shock_absorber", "Toggle Shock Absorber");
        add("keybinding.powersuits.silk_touch", "Toggle Silk Touch Enchantment");
        add("keybinding.powersuits.sprint_assist", "Toggle Sprint Assist");
        add("keybinding.powersuits.swim_assist", "Toggle); Swim Boost");
        add("keybinding.powersuits.transparent_armor", "Toggle Transparent Armor");
        add("keybinding.powersuits.tunnel_bore", "Toggle Tunnel Bore");
        add("keybinding.powersuits.vein_miner", "Toggle Vein Miner");
        add("keybinding.powersuits.water_electrolyzer", "Toggle Water Electrolyzer");
    }



    void addModels() {
        // Generic armor model parts ===================================================================
        // Head ----------------------------------------------------------------------------------------
        Map<Localizations, String> tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Kopf");
        tmp.put(Localizations.EN_US, "Head");
        tmp.put(Localizations.FR_FR, "Tête");
        tmp.put(Localizations.PT_BR, "Cabeça");
        tmp.put(Localizations.PT_PT, "Cabeça");
        tmp.put(Localizations.RU_RU, "Голова");
        tmp.put(Localizations.ZH_CN, "头");
        add("javaModel.head.Head.partName", tmp);

        // Body ----------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Körper");
        tmp.put(Localizations.EN_US, "Body");
        tmp.put(Localizations.FR_FR, "Corps");
        tmp.put(Localizations.PT_BR, "Corpo");
        tmp.put(Localizations.PT_PT, "Corpo");
        tmp.put(Localizations.RU_RU, "Тело");
        tmp.put(Localizations.ZH_CN, "身体");
        add("javaModel.chest.Body.partName", tmp);

        // Chest ---------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "");
        tmp.put(Localizations.EN_US, "Chest");
        tmp.put(Localizations.FR_FR, "");
        tmp.put(Localizations.PT_BR, "");
        tmp.put(Localizations.PT_PT, "");
        tmp.put(Localizations.RU_RU, "");
        tmp.put(Localizations.ZH_CN, "");
        add("javaModel.chest.partName.partName", tmp);

        // Left Arm ------------------------------------------------------------------------------------
        add("javaModel.chest.LeftArm.partName", "LeftArm");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.chest.RightArm.partName", "RightArm");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.legs.partName", "Legs");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.legs.LeftLeg.partName", "LeftLeg");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.legs.RightLeg.partName", "RightLeg");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.feet.partName", "Feet");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("javaModel.feet.LeftFoot.partName", "LeftFoot");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.feet.RightFoot.partName", "RightFoot");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);



        // Armor skins ---------------------------------------------------------------------------------
        add("javaModel.citizenjoe_armorskin.specName", "Citizen Joe Armor Skin");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.default_armorskin.specName", "Default Armor Skin");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        // Power Fist ----------------------------------------------------------------------------------
        add("javaModel.powerfist.specName", "Power Fist");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.mainarm.partName", "Main Arm");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("javaModel.powerfist.armorright.partName", "Armor Right");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("javaModel.powerfist.armorleft.partName", "Armor Left");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.wristtopright.partName", "Wrist Top Right");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.wristtopleft.partName", "Wrist Top Left");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.wristbottomright.partName", "Wrist Bottom Right");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.wristbottomleft.partName", "Wrist Bottom Left");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.fingerguard.partName", "Finger Guard");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.crystalholder.partName", "Crystal Holder");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.crystal.partName", "Crystal");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportright1.partName", "Support Right 1");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportright2.partName", "Support Right 2");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("javaModel.powerfist.supportright3.partName", "Support Right 3");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportright4.partName", "Support Right 4");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportright5.partName", "Support Right 5");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportbaseright.partName", "Support Base Right");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportrightfront.partName", "Support Right Front");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportbaseleft.partName", "Support Base Left");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportleftfront.partName", "Support Left Front");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportleft1.partName", "Support Left 1");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportleft2.partName", "Support Left 2");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportleft3.partName", "Support Left 3");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportleft4.partName", "Support Left 4");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.supportleft5.partName", "Support Left 5");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("javaModel.powerfist.palm.partName", "Palm");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("javaModel.powerfist.palm.index1.partName", "Index 1");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);


        add("javaModel.powerfist.palm.index1.index2.partName", "Index 2");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.palm.middlefinger1.partName", "Middle Finger 1");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.palm.middlefinger1.middlefinger2.partName", "Middle Finger 2");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.palm.ringfinger1.partName", "Ring Finger 1");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.palm.ringfinger1.ringfinger2.partName", "Ring Finger 2");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.palm.pinky1.partName", "Pinky 1");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.palm.pinky1.pinky2.partName", "Pinky 2");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.palm.thumb1.partName", "Thumb 1");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("javaModel.powerfist.palm.thumb1.thumb2.partName", "Thumb 2");


        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        // MPS Armor -----------------------------------------------------------------------------------
        add("model.mps_helm.modelName", "MPS Helm");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_helm.helm_main.partName", "Helmet");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_helm.helm_tube_entry1.partName", "Left Tube Entry");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_helm.helm_tube_entry2.partName", "Right Tube Entry");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_helm.helm_tubes.partName", "Tubes");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_helm.visor.partName", "Visor");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);



        add("model.mps_chest.modelName", "MPS Chestplate");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_chest.chest_main.partName", "Chest Plating");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_chest.chest_padding.partName", "Chest Padding");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_chest.crystal_belt.partName", "Belt Crystal");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_chest.backpack.partName", "Backpack");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_chest.belt.partName", "Belt");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_chest.polySurface36.partName", "Accessory");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_arms.modelName", "MPS Arms");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_arms.arms2.partName", "Left Arm");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_arms.arms3.partName", "Right Arm");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_arms.crystal_shoulder_1.partName", "Left Shoulder Crystal");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_arms.crystal_shoulder_2.partName", "Right Shoulder Crystal");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.jetpack.modelName", "MPS Jetpack");
        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.jetpack.default.partName", "Main");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.jetpack.jetpack5.partName", "Secondary");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.jetpack.jetpack_glow.partName", "Lights");



        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_pantaloons.modelName", "MPS Pantaloons");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_pantaloons.leg1.partName", "Right Leg");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_pantaloons.leg2.partName", "Left Leg");




        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_boots.modelName", "MPS Boots");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);
        add("model.mps_boots.boots1.partName", "Right Boot");

        //        Map<Localizations, String> tmp = new HashMap<>();
//        tmp.put(Localizations.DE_DE, "");
//        tmp.put(Localizations.EN_US, "");
//        tmp.put(Localizations.FR_FR, "");
//        tmp.put(Localizations.PT_BR, "");
//        tmp.put(Localizations.PT_PT, "");
//        tmp.put(Localizations.RU_RU, "");
//        tmp.put(Localizations.ZH_CN, "");
//        add(, tmp);

        add("model.mps_boots.boots2.partName", "Left Boot");

        // Armor 2 =====================================================================================
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rüstung 2");
        tmp.put(Localizations.EN_US, "Armor 2");
        tmp.put(Localizations.FR_FR, "Armure 2");
        tmp.put(Localizations.PT_BR, "Armadura 2");
        tmp.put(Localizations.PT_PT, "Armadura 2");
        tmp.put(Localizations.RU_RU, "Броня 2");
        tmp.put(Localizations.ZH_CN, "护甲 2");
        add("model.armor2.modelName", tmp);

        // Helmet --------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Helm");
        tmp.put(Localizations.EN_US, "Helmet");
        tmp.put(Localizations.FR_FR, "Casque");
        tmp.put(Localizations.PT_BR, "Capacete");
        tmp.put(Localizations.PT_PT, "Capacete");
        tmp.put(Localizations.RU_RU, "Шлем");
        tmp.put(Localizations.ZH_CN, "头盔");
        add("model.armor2.helmetmain.partName", tmp);

        // Helmet Tubes and Visor ----------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Helmschläuche und Visier");
        tmp.put(Localizations.EN_US, "Helmet Tubes and Visor");
        tmp.put(Localizations.FR_FR, "Tubes de casque et visière");
        tmp.put(Localizations.PT_BR, "Tubos e Viseira para Capacete");
        tmp.put(Localizations.PT_PT, "Tubos para capacete e viseira");
        tmp.put(Localizations.RU_RU, "Трубки шлема и забрало");
        tmp.put(Localizations.ZH_CN, "头盔管和遮阳板");
        add("model.armor2.helmetglow1.partName", tmp);

        // Front Chest Protection ----------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Vorderer Brustschutz");
        tmp.put(Localizations.EN_US, "Front Chest Protection");
        tmp.put(Localizations.FR_FR, "Protection thoracique avant");
        tmp.put(Localizations.PT_BR, "Proteção frontal do peito");
        tmp.put(Localizations.PT_PT, "Proteção frontal do peito");
        tmp.put(Localizations.RU_RU, "Передняя защита грудной клетки");
        tmp.put(Localizations.ZH_CN, "前胸保护");
        add("model.armor2.chestmain.partName", tmp);

        // Chest Base ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Brustkorb");
        tmp.put(Localizations.EN_US, "Chest Base");
        tmp.put(Localizations.FR_FR, "Base de la poitrine");
        tmp.put(Localizations.PT_BR, "Base do peito");
        tmp.put(Localizations.PT_PT, "Base do peito");
        tmp.put(Localizations.RU_RU, "Основание груди");
        tmp.put(Localizations.ZH_CN, "胸部底座");
        add("model.armor2.chestgray.partName", tmp);

        // Back Chest Protection -----------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rücken-Brustschutz");
        tmp.put(Localizations.EN_US, "Back Chest Protection");
        tmp.put(Localizations.FR_FR, "Protection thoracique arrière");
        tmp.put(Localizations.PT_BR, "Proteção torácica nas costas");
        tmp.put(Localizations.PT_PT, "Proteção do peito das costas");
        tmp.put(Localizations.RU_RU, "Защита спины грудной клетки");
        tmp.put(Localizations.ZH_CN, "背部保护");
        add("model.armor2.chestback1.partName", tmp);

        // Spinal Power Crystals -----------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Spinale Kraftkristalle");
        tmp.put(Localizations.EN_US, "Spinal Power Crystals");
        tmp.put(Localizations.FR_FR, "Cristaux de puissance vertébrale");
        tmp.put(Localizations.PT_BR, "Cristais de Potência Espinhal");
        tmp.put(Localizations.PT_PT, "Cristais de potência espinhal");
        tmp.put(Localizations.RU_RU, "Кристаллы спинальной силы");
        tmp.put(Localizations.ZH_CN, "脊髓力量晶体");
        add("model.armor2.chestglowback.partName", tmp);

        // Front Power Crystal -------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Vorderer Power-Kristall");
        tmp.put(Localizations.EN_US, "Front Power Crystal");
        tmp.put(Localizations.FR_FR, "Cristal de puissance avant");
        tmp.put(Localizations.PT_BR, "Cristal de potência frontal");
        tmp.put(Localizations.PT_PT, "Cristal de potência frontal");
        tmp.put(Localizations.RU_RU, "Фронтальный силовой кристалл");
        tmp.put(Localizations.ZH_CN, "前电源晶体");
        add("model.armor2.chestglowfront.partName", tmp);

        // Arm and Shoulder Protection -----------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Arm- und Schulterschutz");
        tmp.put(Localizations.EN_US, "Arm and Shoulder Protection");
        tmp.put(Localizations.FR_FR, "Protection des bras et des épaules");
        tmp.put(Localizations.PT_BR, "Proteção de braço e ombro");
        tmp.put(Localizations.PT_PT, "Proteção de braços e ombros");
        tmp.put(Localizations.RU_RU, "Защита рук и плеч");
        tmp.put(Localizations.ZH_CN, "手臂和肩部保护");
        add("model.armor2.armmain.partName", tmp);

        // Arm and Shoulder Protection 1 ---------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Arm- und Schulterschutz");
        tmp.put(Localizations.EN_US, "Arm and Shoulder Protection");
        tmp.put(Localizations.FR_FR, "Protection des bras et des épaules");
        tmp.put(Localizations.PT_BR, "Proteção de braço e ombro");
        tmp.put(Localizations.PT_PT, "Proteção de braços e ombros");
        tmp.put(Localizations.RU_RU, "Защита рук и плеч");
        tmp.put(Localizations.ZH_CN, "手臂和肩部保护");
        add("model.armor2.armmain1.partName", tmp);

        // Lef Boot Glow -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Leuchten des linken Stiefels");
        tmp.put(Localizations.EN_US, "Left Boot Glow");
        tmp.put(Localizations.FR_FR, "Lueur de démarrage gauche");
        tmp.put(Localizations.PT_BR, "Brilho da inicialização esquerda");
        tmp.put(Localizations.PT_PT, "Brilho esquerdo da inicialização");
        tmp.put(Localizations.RU_RU, "Свечение левого ботинка");
        tmp.put(Localizations.ZH_CN, "左靴发光");
        add("model.armor2.bootglow.partName", tmp);

        // Right Boot Glow -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rechter Stiefelglanz");
        tmp.put(Localizations.EN_US, "Right Boot Glow");
        tmp.put(Localizations.FR_FR, "Lueur de démarrage droite");
        tmp.put(Localizations.PT_BR, "Brilho da inicialização direita");
        tmp.put(Localizations.PT_PT, "Brilho de arranque direito");
        tmp.put(Localizations.RU_RU, "«Свечение правого ботинка»");
        tmp.put(Localizations.ZH_CN, "右靴发光");
        add("model.armor2.bootglow1.partName", tmp);

        // Left Boot -----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Linker Kofferraum");
        tmp.put(Localizations.EN_US, "Left Boot");
        tmp.put(Localizations.FR_FR, "Démarrage gauche");
        tmp.put(Localizations.PT_BR, "Inicialização esquerda");
        tmp.put(Localizations.PT_PT, "Arranque esquerdo");
        tmp.put(Localizations.RU_RU, "Левый ботинок");
        tmp.put(Localizations.ZH_CN, "左靴");
        add("model.armor2.bootmain.partName", tmp);

        // Right Boot ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rechter Stiefel");
        tmp.put(Localizations.EN_US, "Right Boot");
        tmp.put(Localizations.FR_FR, "Démarrage droit");
        tmp.put(Localizations.PT_BR, "Inicialização Direita");
        tmp.put(Localizations.PT_PT, "Arranque direito");
        tmp.put(Localizations.RU_RU, "Правый ботинок");
        tmp.put(Localizations.ZH_CN, "右靴");
        add("model.armor2.bootmain1.partName", tmp);

        // Shoulder Light ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Schulterlicht");
        tmp.put(Localizations.EN_US, "Shoulder Light");
        tmp.put(Localizations.FR_FR, "Lumière d’épaule");
        tmp.put(Localizations.PT_BR, "Luz de ombro");
        tmp.put(Localizations.PT_PT, "Luz de ombro");
        tmp.put(Localizations.RU_RU, "Плечевой свет");
        tmp.put(Localizations.ZH_CN, "“肩光。");
        add("model.armor2.armglow.partName", tmp);

        // Shoulder Light 1 -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Schulterlicht");
        tmp.put(Localizations.EN_US, "Shoulder Light");
        tmp.put(Localizations.FR_FR, "Lumière d’épaule");
        tmp.put(Localizations.PT_BR, "Luz de ombro");
        tmp.put(Localizations.PT_PT, "Luz de ombro");
        tmp.put(Localizations.RU_RU, "Плечевой свет");
        tmp.put(Localizations.ZH_CN, "“肩光。");
        add("model.armor2.armglow1.partName", tmp);

        // Arm Base ------------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Armbasis");
        tmp.put(Localizations.EN_US, "Arm Base");
        tmp.put(Localizations.FR_FR, "Base du bras");
        tmp.put(Localizations.PT_BR, "Base do braço");
        tmp.put(Localizations.PT_PT, "Base do braço");
        tmp.put(Localizations.RU_RU, "Основание для рук");
        tmp.put(Localizations.ZH_CN, "手臂基件");
        add("model.armor2.armgray.partName", tmp);

        // Arm Base 1 ----------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Armbasis");
        tmp.put(Localizations.EN_US, "Arm Base");
        tmp.put(Localizations.FR_FR, "Base du bras");
        tmp.put(Localizations.PT_BR, "Base do braço");
        tmp.put(Localizations.PT_PT, "Base do braço");
        tmp.put(Localizations.RU_RU, "Основание для рук");
        tmp.put(Localizations.ZH_CN, "手臂基件");
        add("model.armor2.armgray1.partName", tmp);

        // Left Leg Base -------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Linke Beinbasis");
        tmp.put(Localizations.EN_US, "Left Leg Base");
        tmp.put(Localizations.FR_FR, "Base de la jambe gauche");
        tmp.put(Localizations.PT_BR, "Base da Perna Esquerda");
        tmp.put(Localizations.PT_PT, "Base da Perna Esquerda");
        tmp.put(Localizations.RU_RU, "Основание левой ноги");
        tmp.put(Localizations.ZH_CN, "左腿基件");
        add("model.armor2.leggray.partName", tmp);

        // Right Leg Base ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rechte Beinbasis");
        tmp.put(Localizations.EN_US, "Right Leg Base");
        tmp.put(Localizations.FR_FR, "Base de la jambe droite");
        tmp.put(Localizations.PT_BR, "Base da Perna Direita");
        tmp.put(Localizations.PT_PT, "Base da Perna Direita");
        tmp.put(Localizations.RU_RU, "Правая нога");
        tmp.put(Localizations.ZH_CN, "右腿基件");
        add("model.armor2.leggray1.partName", tmp);

        // Left Leg Protection -------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Schutz für das linke Bein");
        tmp.put(Localizations.EN_US, "Left Leg Protection");
        tmp.put(Localizations.FR_FR, "Protection de la jambe gauche");
        tmp.put(Localizations.PT_BR, "Proteção da perna esquerda");
        tmp.put(Localizations.PT_PT, "Proteção da perna esquerda");
        tmp.put(Localizations.RU_RU, "Защита левой ноги");
        tmp.put(Localizations.ZH_CN, "左腿保护");
        add("model.armor2.legbit.partName", tmp);

        // Right Leg Protection ------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Schutz für das rechte Bein");
        tmp.put(Localizations.EN_US, "Right Leg Protection");
        tmp.put(Localizations.FR_FR, "Protection de la jambe droite");
        tmp.put(Localizations.PT_BR, "Proteção da Perna Direita");
        tmp.put(Localizations.PT_PT, "Proteção da Perna Direita");
        tmp.put(Localizations.RU_RU, "Защита правой ноги");
        tmp.put(Localizations.ZH_CN, "右腿保护");
        add("model.armor2.legbit1.partName", tmp);

        // Left Leg Light ------------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Linkes Beinlicht");
        tmp.put(Localizations.EN_US, "Left Leg Light");
        tmp.put(Localizations.FR_FR, "Lumière de la jambe gauche");
        tmp.put(Localizations.PT_BR, "Luz da perna esquerda");
        tmp.put(Localizations.PT_PT, "Luz da perna esquerda");
        tmp.put(Localizations.RU_RU, "Легкая левая нога");
        tmp.put(Localizations.ZH_CN, "左腿灯");
        add("model.armor2.legglow.partName", tmp);

        // Right Leg Light -----------------------------------------------------------------------------
        tmp = new HashMap<>();
        tmp.put(Localizations.DE_DE, "Rechtes Beinlicht");
        tmp.put(Localizations.EN_US, "Right Leg Light");
        tmp.put(Localizations.FR_FR, "Lumière de jambe droite");
        tmp.put(Localizations.PT_BR, "Luz da perna direita");
        tmp.put(Localizations.PT_PT, "Luz Perna Direita");
        tmp.put(Localizations.RU_RU, "Правая нога Свет");
        tmp.put(Localizations.ZH_CN, "右腿灯");
        add("model.armor2.legglow1.partName", tmp);
    }
}
