package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;

public class NuminaLanguageProvider_HE_IL extends AbstractLanguageProviderNumina {
    public NuminaLanguageProvider_HE_IL(DataGenerator gen) {
        super(gen, NuminaConstants.MOD_ID, "he_il");
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
        add("gui.numina.chargingbase", "בסיס טעינה");
        add(NuminaConstants.TOOLTIP_CHARGING_BASE, "חיוב פריטים מצוידים של ישות");

        // Energy --------------------------------------------------------------------------------------
        add("numina.energy", "אנרגיה");

        // FOV Fix Toggle ------------------------------------------------------------------------------
        add("key.numina.fovfixtoggle", "החלפת מצב של תיקון שדה תצוגה");

        // FOV fix enabled -----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.enabled", "תיקון שדה הראייה זמין");

        // FOV fix disabled ----------------------------------------------------------------------------
        add("message.numina.fovfixtoggle.disabled", "תיקון שדה ראייה מושבת");

        // Install (creative) --------------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL, "התקין");

        // Install (creative, description) -------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_DESC, "מתקין מודול בפריט מודולרי שנבחר בזמן שהנגן במצב יצירתי");

        // Install All (creative) ----------------------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL, "התקן הכל");

        // Install All (creative, description) ---------------------------------------------------------
        add(NuminaConstants.GUI_CREATIVE_INSTALL_ALL_DESC, "מתקין את כל המודולים התואמים ברמה העליונה לפריט מודולרי שנבחר בזמן שהשחקן נמצא במצב יצירתי");
    }

    @Override
    public void addArmorStand() {
        add(NuminaObjects.ARMOR_STAND_ITEM.get(), "מעמד שריון");
        add(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), "מעמד שריון");
    }

    @Override
    public void addModuleTradeoffs() {
        add("module.tradeoff.maxEnergy",  "מקסימום אנרגיה");
        add("module.tradeoff.maxTransfer", "העברה מקסימלית לכל טיק");
    }

    @Override
    public void addBlocks() {
        add(NuminaObjects.CHARGING_BASE_BLOCK.get(), "בסיס טעינה");
    }

    @Override
    public void addToolTips() {
        add(NuminaConstants.TOOLTIP_BATTERY_ENERGY, "%d/%d FE");

        // Press SHIFT ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_PRESS_SHIFT, "הקש SHIFT לקבלת מידע נוסף.");

        // Mode Change ---------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_CHANGE_MODES, "שינוי מצבים: לחץ/י והחזק/י את מספר חריץ הפס החם שבו נמצא Power Fist.");

        // Energy --------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_ENERGY, "אנרגיה: ");

        // No Modules Installed ------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_NO_MODULES, "אין מודולים מותקנים! פריט זה חסר תועלת עד להוספת מודולים מסוימים בטבלת Tinker.");

        // Installed Modules: --------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_INSTALLED_MODULES,  "מודולים מותקנים:");

        // Mode: ---------------------------------------------------------------------------------------
        add(NuminaConstants.TOOLTIP_MODE, "מצב: ");
    }

    @Override
    public void addBatteries() {
        // Basic Battery -------------------------------------------------------------------------------
        add(NuminaObjects.BASIC_BATTERY.get(), "סוללה בסיסית");
        addItemDescriptions(NuminaObjects.BASIC_BATTERY.get(), "שלב סוללה כדי לאפשר לפריט לאגור אנרגיה.");

        // Advanced Battery ----------------------------------------------------------------------------
        add(NuminaObjects.ADVANCED_BATTERY.get(), "סוללה מתקדמת");
        addItemDescriptions(NuminaObjects.ADVANCED_BATTERY.get(), "שלב סוללה כדי לאפשר לפריט לאגור אנרגיה.");

        // Elite Battery -------------------------------------------------------------------------------
        add(NuminaObjects.ELITE_BATTERY.get(), "סוללת עלית");
        addItemDescriptions(NuminaObjects.ELITE_BATTERY.get(), "שלב סוללה מתקדמת ביותר כדי לאחסן כמות גדולה של אנרגיה.");

        // Ultimate Battery  ---------------------------------------------------------------------------
        add(NuminaObjects.ULTIMATE_BATTERY.get(), "סוללה אולטימטיבית");
        addItemDescriptions(NuminaObjects.ULTIMATE_BATTERY.get(), "התקן אחסון כוח ניסיוני ברמה גבוהה, עכשיו כמעט ללא סיכוי להתפוצץ (אנחנו חושבים). למרות שמועות מגעילות, הוא אינו מבוסס על טכנולוגיה חייזרית גנובה.");
    }

    @Override
    public void addComponents() {
        // Artificial Muscle ---------------------------------------------------------------------------
        add(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "שריר מלאכותי");
        addItemDescriptions(NuminaObjects.ARTIFICIAL_MUSCLE.get(), "שריר חשמלי, מלאכותי, עם טווח תנועה קטן יותר משריר אנושי אך בסדרי גודל יותר כוח.");

        // Carbon Myofiber -----------------------------------------------------------------------------
        add(NuminaObjects.CARBON_MYOFIBER.get(), "מיופייבר פחמן");
        addItemDescriptions(NuminaObjects.CARBON_MYOFIBER.get(), "צרור קטן של סיבי פחמן, מזוקק לשימוש בשרירים מלאכותיים.");

        // Computer Chip -------------------------------------------------------------------------------
        add(NuminaObjects.COMPUTER_CHIP.get(), "שבב מחשב");
        addItemDescriptions(NuminaObjects.COMPUTER_CHIP.get(), "מעגל בקרה משודרג המכיל מעבד המסוגל לבצע חישובים מתקדמים יותר.");

        // Control Circuit -----------------------------------------------------------------------------
        add(NuminaObjects.CONTROL_CIRCUIT.get(), "מעגל בקרה");
        addItemDescriptions(NuminaObjects.CONTROL_CIRCUIT.get(), "מיקרו-בקר פשוט לרשת לתיאום רכיב בודד.");

        // Force Field Emitter -------------------------------------------------------------------------
        add(NuminaObjects.FIELD_EMITTER.get(), "פולט שדה כוח");
        addItemDescriptions(NuminaObjects.FIELD_EMITTER.get(), "מכשיר מתקדם המתפעל ישירות שדות אלקטרומגנטיים וכבידה באזור.");

        // Glider Wing ---------------------------------------------------------------------------------
        add(NuminaObjects.GLIDER_WING.get(), "כנף דאון");
        addItemDescriptions(NuminaObjects.GLIDER_WING.get(), "כנף אווירודינמית קלת משקל עם אלקטרומגנט לפריסה ושליפה מהירה.");

        // Ion Thruster --------------------------------------------------------------------------------
        add(NuminaObjects.ION_THRUSTER.get(), "מדחף יונים");
        addItemDescriptions(NuminaObjects.ION_THRUSTER.get(), "למעשה מאיץ חלקיקים מיניאטורי. מאיץ יונים למהירות קרובה לאור כדי לייצר דחף.");

        // Hologram Emitter ----------------------------------------------------------------------------
        add(NuminaObjects.LASER_EMITTER.get(), "פולט הולוגרמה");
        addItemDescriptions(NuminaObjects.LASER_EMITTER.get(), "מערך לייזר צבעוני שיכול לשנות בזול את המראה של משהו.");

        // Magnet --------------------------------------------------------------------------------------
        add(NuminaObjects.MAGNET.get(), "מגנט");
        addItemDescriptions(NuminaObjects.MAGNET.get(), "מכשיר מתכתי המייצר שדה מגנטי המושך פריטים לכיוון השחקן.");

        // Myofiber Gel --------------------------------------------------------------------------------
        add(NuminaObjects.MYOFIBER_GEL.get(), "מיופייבר ג'ל");
        addItemDescriptions(NuminaObjects.MYOFIBER_GEL.get(), "משחה סמיכה ומוליכה, מושלמת להתאמה בין שרירי שריר מלאכותיים.");

        // Parachute -----------------------------------------------------------------------------------
        add(NuminaObjects.PARACHUTE.get(), "מצנח");
        addItemDescriptions(NuminaObjects.PARACHUTE.get(), "מצנח רב פעמי פשוט שניתן לפרוס ולהתאושש באוויר.");

        // Rubber Hose ---------------------------------------------------------------------------------
        add(NuminaObjects.RUBBER_HOSE.get(), "צינור גומי מבודד");
        addItemDescriptions(NuminaObjects.RUBBER_HOSE.get(), "צינור גומי מבודד היטב המסוגל לעמוד בחום או קור קיצוניים");

        // Servo Motor ---------------------------------------------------------------------------------
        add(NuminaObjects.SERVO.get(), "מנוע סרוו");
        addItemDescriptions(NuminaObjects.SERVO.get(), "סוג מיוחד של מנוע המשתמש באות מווסת דופק כדי לבצע תנועות מדויקות מאוד.");

        // Solar Panel ---------------------------------------------------------------------------------
        add(NuminaObjects.SOLAR_PANEL.get(), "פאנל סולארי");
        addItemDescriptions(NuminaObjects.SOLAR_PANEL.get(), "מכשיר רגיש לאור שיפיק חשמל מהשמש.");

        // Solenoid ------------------------------------------------------------------------------------
        add(NuminaObjects.SOLENOID.get(), "סולנואיד");
        addItemDescriptions(NuminaObjects.SOLENOID.get(), "חוטים סביב ליבה פרומגנטית מייצרים אלקטרומגנט בסיסי.");

        // Wiring --------------------------------------------------------------------------------------
        add(NuminaObjects.WIRING.get(), "חיווט");
        addItemDescriptions(NuminaObjects.WIRING.get(), "סוג מיוחד של חיווט עם קיבולת וולטאית גבוהה ודיוק, הכרחי עבור אלקטרוניקה רגישה בשריון כוח.");
    }

    @Override
    public void addModuleCategories() {
        // Armor Modules -------------------------------------------------------------------------------
        add(ModuleCategory.ARMOR.getTranslationKey(), "מודולי שריון");

        // Cosmetic ------------------------------------------------------------------------------------
        add(ModuleCategory.COSMETIC.getTranslationKey(), "קוסמטיים");

        // Debug Modules -------------------------------------------------------------------------------
        add(ModuleCategory.DEBUG.getTranslationKey(), "מודולי איתור באגים");

        // Energy Generation ---------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_GENERATION.getTranslationKey(), "ייצור אנרגיה");

        // Energy Storage ------------------------------------------------------------------------------
        add(ModuleCategory.ENERGY_STORAGE.getTranslationKey(), "אגירת אנרגיה");

        // Environment ---------------------------------------------------------------------------------
        add(ModuleCategory.ENVIRONMENTAL.getTranslationKey(), "סביבה");

        // Mining Enchantment --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENCHANTMENT.getTranslationKey(), "קסם הכרייה");

        // Mining Enhancement --------------------------------------------------------------------------
        add(ModuleCategory.MINING_ENHANCEMENT.getTranslationKey(), "השבחת כרייה");

        // Movement ------------------------------------------------------------------------------------
        add(ModuleCategory.MOVEMENT.getTranslationKey(), "תנועה");

        // None ----------------------------------------------------------------------------------------
        add(ModuleCategory.NONE.getTranslationKey(), "ללא");

        // Special -------------------------------------------------------------------------------------
        add(ModuleCategory.SPECIAL.getTranslationKey(), "מיוחד");

        // Tool ----------------------------------------------------------------------------------------
        add(ModuleCategory.TOOL.getTranslationKey(), "כלי");

        // Vision --------------------------------------------------------------------------------------
        add(ModuleCategory.VISION.getTranslationKey(), "חזון");

        // Weapon --------------------------------------------------------------------------------------
        add(ModuleCategory.WEAPON.getTranslationKey(), "נשק");
    }
}
