package numina.client.config;

import com.google.gson.*;
import numina.client.util.lang.translators.EnumWebDriver;
import numina.client.util.lang.translators.Language;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class DatagenConfig {
    static final String WEB_DRIVER = "webDriver";
    static final String VALUE = "value";
    static final String DESCRIPTION = "description";
    static final String SOURCE_LANGUAGE_CODE = "sourceMCLangCode";
    static final String MINECRAFT_LANGUAGES = "minecraft_languages";
    static final String GOOGLE_KEY = "google_key";
    static final String BING_KEY = "bing_key";
    static final String MC_LABEL = "mc_label";
    static final String ISO_639_3 = "iso639-3";
    static final String TARGET_LANGUAGES = "targetLanguages";
    static final String USE_ALL_POSSIBLE_LANGUAGES = "useAllPossibleLanguages";
    static final String TRANSLATIONS_AVAILABLE = "translationsAvailable";

    Map<String, Language> fastLookup = new HashMap<>();


    List<String> DEFAULT_TARGETS = Arrays.asList(
            "pt_pt",
            "pt_br",
            "de_de",
            "fr_fr",
            "he_il",
            "zh_tw.json");
    File configFile;
    JsonObject json;
    public DatagenConfig(String modID) {
        this.configFile = getConfigPath(modID);
        this.load();

        // lazy way of populating when not used
        getLanguageCodesUsed();
        getAvailableTranslations();
    }

    /**
     * @return source language (Minecraft language code, usually en_us)
     */
    public Language getMainLanguageCode() {
        String code;
        if (json == null || !json.has(SOURCE_LANGUAGE_CODE) || json.getAsJsonObject(SOURCE_LANGUAGE_CODE).has(VALUE)) {
            setMainLanguageCode();
        }
        code = json.getAsJsonObject(SOURCE_LANGUAGE_CODE).get(VALUE).getAsString().toLowerCase();
        return getLanguageFromMC_Code(getMinecraftLanguages(), code);
    }

    /**
     * Creates the default Minecraft language code (en_us)
     */
    private void setMainLanguageCode() {
        JsonObject tmp = new JsonObject();
        tmp.addProperty(DESCRIPTION, "default language code (usually en_us)");
        tmp.addProperty(VALUE, "en_us");
        json.add(SOURCE_LANGUAGE_CODE, tmp);
        save();
    }

    /**
     *
     * @return List of Minecraft language codes
     */
    public List<Language> getLanguageCodesUsed() {
        if (!useAllPossibleLanguages()) {
            if (json == null) {
                this.json = new JsonObject();
            }
            if (json.has(TARGET_LANGUAGES)) {
                JsonArray array = json.get(TARGET_LANGUAGES).getAsJsonArray();
                List<String> mc_codes = new ArrayList<>();
                array.forEach(element -> mc_codes.add(element.getAsString()));
                return getLanguages(mc_codes);
            } else {
                setLanguageCodesUsed();
            }
            return getLanguages(DEFAULT_TARGETS);
        } return getAllPossibleLanguages();
    }

    List<Language> getLanguages(List<String> mc_codes) {
        List<Language> retList = new ArrayList<>();
        List<Language> langs = getMinecraftLanguages();
        mc_codes.forEach(mc_label -> {
            Language lang = getLanguageFromMC_Code(langs, mc_label);
            if (lang != null) {
                retList.add(lang);
            }
        });
        return retList;
    }

    public Language getLanguageFromMC_Code(String mc_code) {
        if (fastLookup.containsKey(mc_code)) {
            return fastLookup.get(mc_code);
        }
        return getLanguageFromMC_Code(getMinecraftLanguages(), mc_code);
    }


    @Nullable
    Language getLanguageFromMC_Code(List<Language> langs, String mc_code){
        if (fastLookup.containsKey(mc_code)) {
            return fastLookup.get(mc_code);
        }
        Language language = langs.stream().filter(lang->lang.mc_label().equalsIgnoreCase(mc_code)).findFirst().orElse(null);
        if (language != null) {
            fastLookup.put(mc_code, language);
        }
        return language;
    }

    private void setLanguageCodesUsed() {
        if (json == null) {
            this.json = new JsonObject();
        }

        JsonArray tmp = new JsonArray();
        DEFAULT_TARGETS.forEach(tmp::add);
        this.json.add(TARGET_LANGUAGES, tmp);
        save();
    }

    public List<Language> getAllPossibleLanguages() {
        if (json == null) {
            this.json = new JsonObject();
            getMinecraftLanguages();
        }
        // fixme: currently this will only use bing
        return getMinecraftLanguages().stream().filter(lang->!lang.bing_key().isBlank()).toList();

//        return getMinecraftLanguages().stream().filter(lang->!lang.google_key().isBlank()).isBlank()).toList();
    }

    public boolean useAllPossibleLanguages() {
        if (json == null) {
            this.json = new JsonObject();
        }
        if (!json.has(USE_ALL_POSSIBLE_LANGUAGES) || !json.getAsJsonObject(USE_ALL_POSSIBLE_LANGUAGES).has(VALUE)) {
            setUseAllPossibleLanguages();
        }
        return json.getAsJsonObject(USE_ALL_POSSIBLE_LANGUAGES).getAsJsonPrimitive(VALUE).getAsBoolean();
    }

    public void setUseAllPossibleLanguages() {
        if (json == null) {
            this.json = new JsonObject();
        }
        json.add(USE_ALL_POSSIBLE_LANGUAGES, new MakeJson()
                .add(DESCRIPTION, "use all languages that have a translator available")
                .add(VALUE, true)
                .getJson());
        save();
    }

    public EnumWebDriver getWebDriverType() {
        if (json != null && json.has(WEB_DRIVER) && json.getAsJsonObject(WEB_DRIVER).has(VALUE)) {
            String wb = json.getAsJsonObject(WEB_DRIVER).get(VALUE).getAsString().toUpperCase();
            return EnumWebDriver.valueOf(wb);
        }
        setWebDriver();
        return EnumWebDriver.VOID_DRIVER;
    }

    private void setWebDriver() {
        if (json == null) {
            this.json = new JsonObject();
        }

        String[] optionalValues = {
                "CHROME_DRIVER",
                "FIREFOX_DRIVER",
                "EDGE_DRIVER",
                "IE_DRIVER",
                "SAFARI_DRIVER",
                "VOID_DRIVER"
        };
        JsonArray ov = new JsonArray(optionalValues.length);
        Arrays.stream(optionalValues).forEach(ov::add);

        json.add(WEB_DRIVER, new MakeJson()
                .addProperty(DESCRIPTION, "selects which webdriver to use for automatic translation (disables automatic translation)")
                .add( "optional values", ov)
                .addProperty(VALUE, "FIREFOX_DRIVER")
                .getJson());
        save();
    }


    private void load() {
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(configFile)) {
            Object object = jsonParser.parse(reader);
            if (object instanceof JsonObject) {
                this.json = (JsonObject) object;
            } else {
                this.json = new JsonObject();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean saveConfig = false;
        // set default values
        if (json != null) {
            if (!(json.has(SOURCE_LANGUAGE_CODE) && json.getAsJsonObject(SOURCE_LANGUAGE_CODE).has(VALUE))) {
                saveConfig = true;
                setMainLanguageCode();
            }

            if(!(json.has(WEB_DRIVER) && json.getAsJsonObject(WEB_DRIVER).has(VALUE))) {
                saveConfig = true;
                setWebDriver();
            }

            if (saveConfig) {
                save();
            }
        }
    }

    public List<Language> getMinecraftLanguages() {
        Map<String, Map<String, String>> tmp = new HashMap<>();
        List<Language> ret = new ArrayList<>();
        if (json.has(MINECRAFT_LANGUAGES)) {
            JsonObject obj =json.getAsJsonObject(MINECRAFT_LANGUAGES);
            obj.keySet().forEach(key->{
                JsonObject obj2 = obj.getAsJsonObject(key);
                Map<String, String> tmp2 = new HashMap<>();
                obj2.keySet().forEach(key2-> tmp2.put(key2, obj2.get(key2).getAsString()));
                tmp.put(key, tmp2);
            });
            tmp.forEach((name, map)->{
                ret.add(new Language(name,
                        map.getOrDefault(MC_LABEL, ""),
                        map.getOrDefault(BING_KEY, ""),
                        map.getOrDefault(GOOGLE_KEY, ""),
                        map.getOrDefault(ISO_639_3, "")));
            });
        } else {
            setMinecraftLanguages();
            setAvailableTranslations();
        }



        return ret;
    }

    /**
     *
     * @param langName language the keys are stored under
     * @param translator
     * @param langKey key of the
     * @param translatorKey name of the key in the config.json
     * @return
     */
    public boolean addLanguageToMinecraft(String langName, String translator, String langKey, String translatorKey) {
        boolean added = false;
        if (json.has(MINECRAFT_LANGUAGES)) {
            JsonObject obj = json.getAsJsonObject(MINECRAFT_LANGUAGES);
            if(obj.has(langName)) {
                JsonObject obj2 = obj.getAsJsonObject(langName);
                obj2.addProperty(translator, langKey);
                obj.add(langName, obj2);
                json.add(MINECRAFT_LANGUAGES, obj);
                JsonObject obj3 = json.getAsJsonObject(translatorKey);
                obj3.remove(langName);
                json.add(translatorKey, obj3);

                added = true;
            }
        }
        if (added) {
            save();
        }
        return added;
    }

    public Map<String, String> getSomething(String key) {
        Map<String, String> retMap = new HashMap<>();
        if (json.has(key)) {
            JsonObject obj = json.getAsJsonObject(key);
            try {
                obj.keySet().forEach(mapKey -> retMap.put(mapKey, obj.get(mapKey).getAsString()));
                return retMap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retMap;
    }

    public void set(String key, JsonObject obj) {
        json.add(key, obj);
        save();
    }

    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        JsonElement je = JsonParser.parseString(json.toString());
        String prettyJsonString = gson.toJson(je);
        FileWriter writer;
        try {
            writer = new FileWriter(configFile);
            writer.write(prettyJsonString);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Language> getAvailableTranslations() {
        if(json == null) {
            setAvailableTranslations();
        }
        List<Language> ret = new ArrayList<>();
        if (json.has(TRANSLATIONS_AVAILABLE) && json.get(TRANSLATIONS_AVAILABLE) instanceof JsonArray) {

        } else {
            setAvailableTranslations();
        }
        return ret;
    }


    void setAvailableTranslations() {
        if(json == null) {
            json = new JsonObject();
        }
        json.add(TRANSLATIONS_AVAILABLE, new MakeJson()
                .add("Lingala", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ln")
                        .addProperty(BING_KEY, "ln")
                        .getJson())

                .add("Serbian", new MakeJson()
                        .addProperty(GOOGLE_KEY,  "sr")
                        .getJson())

                .add("Marathi", new MakeJson()
                        .addProperty(GOOGLE_KEY, "mr")
                        .addProperty(BING_KEY, "mr")
                        .getJson())

                .add("Hmong", new MakeJson()
                        .addProperty(GOOGLE_KEY, "hmn")
                        .getJson())

                .add("Zulu", new MakeJson()
                        .addProperty(GOOGLE_KEY, "zu")
                        .addProperty(BING_KEY, "zu")
                        .getJson())

                .add("Shona", new MakeJson()
                        .addProperty(GOOGLE_KEY, "sn")
                        .addProperty(BING_KEY, "sn")
                        .getJson())

                .add("Luganda", new MakeJson()
                        .addProperty(GOOGLE_KEY, "lg")
                        .getJson())

                .add("Samoan", new MakeJson()
                        .addProperty(GOOGLE_KEY, "sm")
                        .addProperty(BING_KEY, "sm")
                        .getJson())

                .add("Kinyarwanda", new MakeJson()
                        .addProperty(GOOGLE_KEY, "rw")
                        .addProperty(BING_KEY, "rw")
                        .getJson())

                .add("Tajik", new MakeJson()
                        .addProperty(GOOGLE_KEY, "tg")
                        .getJson())

                .add("Bambara", new MakeJson()
                        .addProperty(GOOGLE_KEY, "bm")
                        .getJson())

                .add("Portuguese", new MakeJson()
                        .addProperty(GOOGLE_KEY, "pt")
                        .getJson())

                .add("Ilocano", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ilo")
                        .getJson())

                .add("Meiteilon (Manipuri)", new MakeJson()
                        .addProperty(GOOGLE_KEY, "mni-Mtei")
                        .getJson())

                .add("Kurdish (Sorani)", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ckb")
                        .getJson())

                .add("Scots Gaelic", new MakeJson()
                        .addProperty(GOOGLE_KEY, "gd")
                        .getJson())

                .add("Norwegian", new MakeJson()
                        .addProperty(GOOGLE_KEY, "no")
                        .addProperty(BING_KEY, "nb")
                        .getJson())

                .add("Malagasy", new MakeJson()
                        .addProperty(GOOGLE_KEY, "mg")
                        .addProperty(BING_KEY, "mg")
                        .getJson())

                .add("Chichewa", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ny")
                        .getJson())


                .add("Dogri", new MakeJson()
                        .addProperty(GOOGLE_KEY, "doi")
                        .addProperty(BING_KEY, "doi")
                        .getJson())

                .add("Tigrinya", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ti")
                        .addProperty(BING_KEY, "ti")
                        .getJson())

                .add("Haitian Creole", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ht")
                        .addProperty(BING_KEY, "ht")
                        .getJson())

                .add("Swahili", new MakeJson()
                        .addProperty(GOOGLE_KEY, "sw")
                        .addProperty(BING_KEY, "sw")
                        .getJson())

                .add("Urdu", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ur")
                        .addProperty(BING_KEY, "ur")
                        .getJson())

                .add("Twi", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ak")
                        .getJson())

                .add("Maithili", new MakeJson()
                        .addProperty(GOOGLE_KEY, "mai")
                        .addProperty(BING_KEY, "mai")
                        .getJson())

                .add("Dhivehi", new MakeJson()
                        .addProperty(GOOGLE_KEY, "dv")
                        .getJson())

                .add("Guarani", new MakeJson()
                        .addProperty(GOOGLE_KEY, "gn")
                        .getJson())

                .add("Sindhi", new MakeJson()
                        .addProperty(GOOGLE_KEY, "sd")
                        .addProperty(BING_KEY, "sd")
                        .getJson())

                .add("Bengali", new MakeJson()
                        .addProperty(GOOGLE_KEY, "bn")
                        .getJson())

                .add("Pashto", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ps")
                        .addProperty(BING_KEY, "ps")
                        .getJson())

                .add("Javanese", new MakeJson()
                        .addProperty(GOOGLE_KEY, "jw")
                        .getJson())

                .add("Gujarati", new MakeJson()
                        .addProperty(GOOGLE_KEY, "gu")
                        .addProperty(BING_KEY, "gu")
                        .getJson())

                .add("Bhojpuri", new MakeJson()
                        .addProperty(GOOGLE_KEY, "bho")
                        .addProperty(BING_KEY, "bho")
                        .getJson())

                .add("Detect language", new MakeJson()
                        .addProperty(GOOGLE_KEY, "auto")
                        .getJson())

                .add("Corsican", new MakeJson()
                        .addProperty(GOOGLE_KEY, "co")
                        .getJson())

                .add("Sundanese", new MakeJson()
                        .addProperty(GOOGLE_KEY, "su")
                        .getJson())

                .add("Myanmar (Burmese)", new MakeJson()
                        .addProperty(GOOGLE_KEY, "my")
                        .addProperty(BING_KEY, "my")
                        .getJson())

                .add("Uzbek", new MakeJson()
                        .addProperty(GOOGLE_KEY, "uz")
                        .getJson())

                .add("Tsonga", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ts")
                        .getJson())

                .add("Sepedi", new MakeJson()
                        .addProperty(GOOGLE_KEY, "nso")
                        .getJson())

                .add("Krio", new MakeJson()
                        .addProperty(GOOGLE_KEY, "kri")
                        .getJson())

                .add("Kurdish (Kurmanji)", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ku")
                        .getJson())

                .add("Telugu", new MakeJson()
                        .addProperty(GOOGLE_KEY, "te")
                        .addProperty(BING_KEY, "te")
                        .getJson())

                .add("Ewe", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ee")
                        .getJson())

                .add("Mizo", new MakeJson()
                        .addProperty(GOOGLE_KEY, "lus")
                        .getJson())

                .add("Turkmen", new MakeJson()
                        .addProperty(GOOGLE_KEY, "tk")
                        .addProperty(BING_KEY, "tk")
                        .getJson())

                .add("Maori", new MakeJson()
                        .addProperty(GOOGLE_KEY, "mi")
                        .getJson())

                .add("Nepali", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ne")
                        .addProperty(BING_KEY, "ne")
                        .getJson())

                .add("Cebuano", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ceb")
                        .getJson())

                .add("Aymara", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ay")
                        .getJson())

                .add("Oromo", new MakeJson()
                        .addProperty(GOOGLE_KEY, "om")
                        .getJson())

                .add("Malayalam", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ml")
                        .addProperty(BING_KEY, "ml")
                        .getJson())

                .add("Xhosa", new MakeJson()
                        .addProperty(GOOGLE_KEY, "xh")
                        .addProperty(BING_KEY, "xh")
                        .getJson())

                .add("Konkani", new MakeJson()
                        .addProperty(GOOGLE_KEY, "gom")
                        .addProperty(BING_KEY, "gom")
                        .getJson())

                .add("Uyghur", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ug")
                        .addProperty(BING_KEY, "ug")
                        .getJson())

                .add("Assamese", new MakeJson()
                        .addProperty(GOOGLE_KEY, "as")
                        .addProperty(BING_KEY, "as")
                        .getJson())

                .add("Sesotho", new MakeJson()
                        .addProperty(GOOGLE_KEY, "st")
                        .addProperty(BING_KEY, "st")
                        .getJson())

                .add("Odia (Oriya)", new MakeJson()
                        .addProperty(GOOGLE_KEY, "or")
                        .getJson())

                .add("Khmer", new MakeJson()
                        .addProperty(GOOGLE_KEY, "km")
                        .addProperty(BING_KEY, "km")
                        .getJson())

                .add("Hausa", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ha")
                        .addProperty(BING_KEY, "ha")
                        .getJson())

                .add("Quechua", new MakeJson()
                        .addProperty(GOOGLE_KEY, "qu")
                        .getJson())

                .add("Sinhala", new MakeJson()
                        .addProperty(GOOGLE_KEY, "si")
                        .addProperty(BING_KEY, "si")
                        .getJson())

                .add("Amharic", new MakeJson()
                        .addProperty(GOOGLE_KEY, "am")
                        .addProperty(BING_KEY, "am")
                        .getJson())

                .add("Kyrgyz", new MakeJson()
                        .addProperty(GOOGLE_KEY, "ky")
                        .addProperty(BING_KEY, "ky")
                        .getJson())

                .add("Punjabi", new MakeJson()
                        .addProperty(GOOGLE_KEY, "pa")
                        .addProperty(BING_KEY, "pa")
                        .getJson())

                .add("Sanskrit", new MakeJson()
                        .addProperty(GOOGLE_KEY, "sa")
                        .getJson())

                .add("Mongolian (Cyrillic)", new MakeJson()
                        .addProperty(BING_KEY, "mn-Cyrl")
                        .getJson())

                .add("Rundi", new MakeJson()
                        .addProperty(BING_KEY, "run")
                        .getJson())

                .add("Sesotho sa Leboa", new MakeJson()
                        .addProperty(BING_KEY, "nso")
                        .getJson())

                .add("Cantonese (Traditional)", new MakeJson()
                        .addProperty(BING_KEY, "yue")
                        .getJson())

                .add("Kurdish (Central)", new MakeJson()
                        .addProperty(BING_KEY, "ku")
                        .getJson())

                .add("Divehi", new MakeJson()
                        .addProperty(BING_KEY, "dv")
                        .getJson())

                .add("Hmong Daw", new MakeJson()
                        .addProperty(BING_KEY, "mww")
                        .getJson())

                .add("Māori", new MakeJson()
                        .addProperty(BING_KEY, "mi")
                        .getJson())

                .add("Tahitian", new MakeJson()
                        .addProperty(BING_KEY, "ty")
                        .getJson())

                .add("Bodo", new MakeJson()
                        .addProperty(BING_KEY, "brx")
                        .getJson())

                .add("Ganda", new MakeJson()
                        .addProperty(BING_KEY, "lug")
                        .getJson())

                .add("Chinese (Literary)", new MakeJson()
                        .addProperty(BING_KEY, "lzh")
                        .getJson())

                .add("Lower Sorbian", new MakeJson()
                        .addProperty(BING_KEY, "dsb")
                        .getJson())

                .add("Dari", new MakeJson()
                        .addProperty(BING_KEY, "prs")
                        .getJson())

                .add("Kurdish (Northern)", new MakeJson()
                        .addProperty(BING_KEY, "kmr")
                        .getJson())

                .add("Mongolian (Traditional)", new MakeJson()
                        .addProperty(BING_KEY, "mn-Mong")
                        .getJson())

                .add("Setswana", new MakeJson()
                        .addProperty(BING_KEY, "tn")
                        .getJson())

                .add("Querétaro Otomi", new MakeJson()
                        .addProperty(BING_KEY, "otq")
                        .getJson())

                .add("Kashmiri", new MakeJson()
                        .addProperty(BING_KEY, "ks")
                        .getJson())

                .add("Tibetan", new MakeJson()
                        .addProperty(BING_KEY, "bo")
                        .getJson())

                .add("Uzbek (Latin)", new MakeJson()
                        .addProperty(BING_KEY, "uz")
                        .getJson())

                .add("Fijian", new MakeJson()
                        .addProperty(BING_KEY, "fj")
                        .getJson())

                .add("Bangla", new MakeJson()
                        .addProperty(BING_KEY, "bn")
                        .getJson())

                .add("Tongan", new MakeJson()
                        .addProperty(BING_KEY, "to")
                        .getJson())

                .add("Odia", new MakeJson()
                        .addProperty(BING_KEY, "or")
                        .getJson())

                .add("Upper Sorbian", new MakeJson()
                        .addProperty(BING_KEY, "hsb")
                        .getJson())

                .add("Inuktitut", new MakeJson()
                        .addProperty(BING_KEY, "iu")
                        .getJson())

                .add("Inuinnaqtun", new MakeJson()
                        .addProperty(BING_KEY, "ikt")
                        .getJson())

                .add("Inuktitut (Latin)", new MakeJson()
                        .addProperty(BING_KEY, "iu-Latn")
                        .getJson())

                .add("Yucatec Maya", new MakeJson()
                        .addProperty(BING_KEY, "yua")
                        .getJson())

                .add("Nyanja", new MakeJson()
                        .addProperty(BING_KEY, "nya")
                        .getJson()).getJson());
        save();
    }

    void setMinecraftLanguages() {
        if (json == null) {
            json = new JsonObject();
        }

        json.add(MINECRAFT_LANGUAGES, new MakeJson()
                .add("Afrikaans (Suid-Afrika)", new MakeJson()
                        .addProperty(MC_LABEL,  "af_za")
                        .addProperty(ISO_639_3, "afr_ZA")
                        .addProperty(BING_KEY, "af")
                        .addProperty(GOOGLE_KEY, "af")
                        .getJson())

                .add("Arabic", new MakeJson()
                        .addProperty(MC_LABEL, "ar_sa")
                        .addProperty(ISO_639_3, "ara_SA")
                        .addProperty(BING_KEY, "ar")
                        .addProperty(GOOGLE_KEY, "ar")
                        .getJson())

                .add("Asturian", new MakeJson()
                        .addProperty(MC_LABEL, "ast_es")
                        .addProperty(ISO_639_3, "ast_ES")
                        .getJson())

                .add("Azerbaijani", new MakeJson()
                        .addProperty(MC_LABEL, "az_az")
                        .addProperty(ISO_639_3, "aze_AZ")
                        .addProperty(BING_KEY, "az")
                        .addProperty(GOOGLE_KEY, "az")
                        .getJson())

                .add("Bashkir", new MakeJson()
                        .addProperty(MC_LABEL, "ba_ru")
                        .addProperty(ISO_639_3, "bak_RU")
                        .addProperty(BING_KEY, "ba")
                        .getJson())

                .add("Bavarian", new MakeJson()
                        .addProperty(MC_LABEL, "bar")
                        .addProperty(ISO_639_3, "bar_DE")
                        .getJson())

                .add("Belarusian", new MakeJson()
                        .addProperty(MC_LABEL, "be_by")
                        .addProperty(ISO_639_3, "bel_BY")
                        .addProperty(GOOGLE_KEY, "be")
                        .getJson())

                .add("Bulgarian", new MakeJson()
                        .addProperty(MC_LABEL, "bg_bg")
                        .addProperty(ISO_639_3, "bul_BG")
                        .addProperty(BING_KEY, "bg")
                        .addProperty(GOOGLE_KEY, "bg")
                        .getJson())

                .add("Breton", new MakeJson()
                        .addProperty(MC_LABEL, "br_fr")
                        .addProperty(ISO_639_3, "bre_FR")
                        .getJson())

                .add("Brabantian", new MakeJson()
                        .addProperty(MC_LABEL, "brb")
                        .addProperty(ISO_639_3, "qbr_NL")
                        .getJson())

                .add("Bosnian", new MakeJson()
                        .addProperty(MC_LABEL, "bs_ba")
                        .addProperty(ISO_639_3, "bos_BA")
                        .addProperty(BING_KEY, "bs")
                        .addProperty(GOOGLE_KEY, "bs")
                        .getJson())

                .add("Catalan", new MakeJson()
                        .addProperty(MC_LABEL, "ca_es")
                        .addProperty(ISO_639_3, "cat_ES")
                        .addProperty(BING_KEY, "ca")
                        .addProperty(GOOGLE_KEY, "ca")
                        .getJson())

                .add("Czech", new MakeJson()
                        .addProperty(MC_LABEL, "cs_cz")
                        .addProperty(ISO_639_3, "ces_CZ")
                        .addProperty(BING_KEY, "cs")
                        .addProperty(GOOGLE_KEY, "cs")
                        .getJson())

                .add("Welsh", new MakeJson()
                        .addProperty(MC_LABEL, "cy_gb")
                        .addProperty(ISO_639_3, "cym_GB")
                        .addProperty(BING_KEY, "cy")
                        .addProperty(GOOGLE_KEY, "cy")
                        .getJson())

                .add("Danish", new MakeJson()
                        .addProperty(MC_LABEL, "da_dk")
                        .addProperty(ISO_639_3, "dan_DK")
                        .addProperty(BING_KEY, "da")
                        .addProperty(GOOGLE_KEY, "da")
                        .getJson())

                .add("Austrian German", new MakeJson()
                        .addProperty(MC_LABEL, "de_at")
                        .addProperty(ISO_639_3, "bar_AT")
                        .getJson())

                .add("Swiss German", new MakeJson()
                        .addProperty(MC_LABEL, "de_ch")
                        .addProperty(ISO_639_3, "gsw_CH")
                        .getJson())

                .add("German", new MakeJson()
                        .addProperty(MC_LABEL, "de_de")
                        .addProperty(ISO_639_3, "deu_DE")
                        .addProperty(BING_KEY, "de")
                        .addProperty(GOOGLE_KEY, "de")
                        .getJson())

                .add("Greek", new MakeJson()
                        .addProperty(MC_LABEL, "el_gr")
                        .addProperty(ISO_639_3, "ell_GR")
                        .addProperty(BING_KEY, "el")
                        .addProperty(GOOGLE_KEY, "el")
                        .getJson())

                .add("Australian English", new MakeJson()
                        .addProperty(MC_LABEL, "en_au")
                        .addProperty(ISO_639_3, "eng_AU")
                        .getJson())

                .add("Canadian English", new MakeJson()
                        .addProperty(MC_LABEL, "en_ca")
                        .addProperty(ISO_639_3, "eng_CA")
                        .getJson())

                .add("British English", new MakeJson()
                        .addProperty(MC_LABEL, "en_gb")
                        .addProperty(ISO_639_3, "eng_GB")
                        .getJson())

                .add("New Zealand English", new MakeJson()
                        .addProperty(MC_LABEL, "en_nz")
                        .addProperty(ISO_639_3, "eng_NZ")
                        .getJson())

                .add("Pirate English", new MakeJson()
                        .addProperty(MC_LABEL, "en_pt")
                        .addProperty(ISO_639_3, "qpe")
                        .getJson())

                .add("Upside down British English", new MakeJson()
                        .addProperty(MC_LABEL, "en_ud")
                        .addProperty(ISO_639_3, "eng-Qabs_GB")
                        .getJson())

                .add("American English", new MakeJson()
                        .addProperty(MC_LABEL, "en_us")
                        .addProperty(ISO_639_3, "")
                        .addProperty(BING_KEY, "en")
                        .addProperty(GOOGLE_KEY, "en")
                        .getJson())

                .add("Modern English minus borrowed words", new MakeJson()
                        .addProperty(MC_LABEL, "enp")
                        .addProperty(ISO_639_3, "qep")
                        .getJson())

                .add("Early Modern English", new MakeJson()
                        .addProperty(MC_LABEL, "enws")
                        .addProperty(ISO_639_3, "qes")
                        .getJson())

                .add("Esperanto", new MakeJson()
                        .addProperty(MC_LABEL, "eo_uy")
                        .addProperty(ISO_639_3, "epo")
                        .addProperty(GOOGLE_KEY, "eo")
                        .getJson())

                .add("Argentinian Spanish", new MakeJson()
                        .addProperty(MC_LABEL, "es_ar")
                        .addProperty(ISO_639_3, "spa_AR")
                        .getJson())

                .add("Chilean Spanish", new MakeJson()
                        .addProperty(MC_LABEL, "es_cl")
                        .addProperty(ISO_639_3, "spa_CL")
                        .getJson())

                .add("Ecuadorian Spanish", new MakeJson()
                        .addProperty(MC_LABEL, "es_ec")
                        .addProperty(ISO_639_3, "spa_EC")
                        .getJson())

                .add("European Spanish", new MakeJson()
                        .addProperty(MC_LABEL, "es_es")
                        .addProperty(ISO_639_3, "spa_ES")
                        .addProperty(BING_KEY, "es")
                        .addProperty(GOOGLE_KEY, "es")
                        .getJson())

                .add("Mexican Spanish", new MakeJson()
                        .addProperty(MC_LABEL, "es_mx")
                        .addProperty(ISO_639_3, "spa_MX")
                        .getJson())

                .add("Uruguayan Spanish", new MakeJson()
                        .addProperty(MC_LABEL, "es_uy")
                        .addProperty(ISO_639_3, "spa_UY")
                        .getJson())

                .add("Venezuelan Spanish", new MakeJson()
                        .addProperty(MC_LABEL, "es_ve")
                        .addProperty(ISO_639_3, "spa_VE")
                        .getJson())

                .add("Andalusian", new MakeJson()
                        .addProperty(MC_LABEL, "esan")
                        .addProperty(ISO_639_3, "spa_ES-AN")
                        .getJson())

                .add("Estonian", new MakeJson()
                        .addProperty(MC_LABEL, "et_ee")
                        .addProperty(ISO_639_3, "est_EE")
                        .addProperty(BING_KEY, "et")
                        .addProperty(GOOGLE_KEY, "et")
                        .getJson())

                .add("Basque", new MakeJson()
                        .addProperty(MC_LABEL, "eu_es")
                        .addProperty(ISO_639_3, "eus_ES")
                        .addProperty(BING_KEY, "eu")
                        .addProperty(GOOGLE_KEY, "eu")
                        .getJson())

                .add("Persian", new MakeJson()
                        .addProperty(MC_LABEL, "fa_ir")
                        .addProperty(ISO_639_3, "fas_IR")
                        .addProperty(BING_KEY, "fa")
                        .addProperty(GOOGLE_KEY, "fa")
                        .getJson())

                .add("Finnish", new MakeJson()
                        .addProperty(MC_LABEL, "fi_fi")
                        .addProperty(ISO_639_3, "fin_FI")
                        .addProperty(BING_KEY, "fi")
                        .addProperty(GOOGLE_KEY, "fi")
                        .getJson())

                .add("Filipino", new MakeJson()
                        .addProperty(MC_LABEL, "fil_ph")
                        .addProperty(ISO_639_3, "fil_PH")
                        .addProperty(BING_KEY, "fil")
                        .addProperty(GOOGLE_KEY, "tl")
                        .getJson())

                .add("Faroese", new MakeJson()
                        .addProperty(MC_LABEL, "fo_fo")
                        .addProperty(ISO_639_3, "fao_FO")
                        .addProperty(BING_KEY, "fo")
                        .getJson())

                .add("Canadian French", new MakeJson()
                        .addProperty(MC_LABEL, "fr_ca")
                        .addProperty(ISO_639_3, "fra_CA")
                        .addProperty(BING_KEY, "fr-CA")
                        .getJson())

                .add("European French", new MakeJson()
                        .addProperty(MC_LABEL, "fr_fr")
                        .addProperty(ISO_639_3, "fra_FR")
                        .addProperty(BING_KEY, "fr")
                        .addProperty(GOOGLE_KEY, "fr")
                        .getJson())

                .add("East Franconian", new MakeJson()
                        .addProperty(MC_LABEL, "fra_de")
                        .addProperty(ISO_639_3, "vmf_DE")
                        .getJson())

                .add("Friulian", new MakeJson()
                        .addProperty(MC_LABEL, "fur_it")
                        .addProperty(ISO_639_3, "fur_IT")
                        .getJson())

                .add("Frisian", new MakeJson()
                        .addProperty(MC_LABEL, "fy_nl")
                        .addProperty(ISO_639_3, "fry_NL")
                        .addProperty(GOOGLE_KEY, "fy")
                        .getJson())

                .add("Irish", new MakeJson()
                        .addProperty(MC_LABEL, "ga_ie")
                        .addProperty(ISO_639_3, "gle_IE")
                        .addProperty(BING_KEY, "ga")
                        .addProperty(GOOGLE_KEY, "ga")
                        .getJson())

                .add("Scottish Gaelic", new MakeJson()
                        .addProperty(MC_LABEL, "gd_gb")
                        .addProperty(ISO_639_3, "gla_GB")
                        .getJson())

                .add("Galician", new MakeJson()
                        .addProperty(MC_LABEL, "gl_es")
                        .addProperty(ISO_639_3, "glg_ES")
                        .addProperty(BING_KEY, "gl")
                        .addProperty(GOOGLE_KEY, "gl")
                        .getJson())

                .add("Hawaiian", new MakeJson()
                        .addProperty(MC_LABEL, "haw_us")
                        .addProperty(ISO_639_3, "haw_US")
                        .addProperty(GOOGLE_KEY, "haw")
                        .getJson())

                .add("Hebrew", new MakeJson()
                        .addProperty(MC_LABEL, "he_il")
                        .addProperty(ISO_639_3, "heb_IL")
                        .addProperty(BING_KEY, "he")
                        .addProperty(GOOGLE_KEY, "iw")
                        .getJson())

                .add("Hindi", new MakeJson()
                        .addProperty(MC_LABEL, "hi_in")
                        .addProperty(ISO_639_3, "hin_IN")
                        .addProperty(BING_KEY, "hi")
                        .addProperty(GOOGLE_KEY, "hi")
                        .getJson())

                .add("Croatian", new MakeJson()
                        .addProperty(MC_LABEL, "hr_hr")
                        .addProperty(ISO_639_3, "hrv_HR")
                        .addProperty(BING_KEY, "hr")
                        .addProperty(GOOGLE_KEY, "hr")
                        .getJson())

                .add("Hungarian", new MakeJson()
                        .addProperty(MC_LABEL, "hu_hu")
                        .addProperty(ISO_639_3, "hun_HU")
                        .addProperty(BING_KEY, "hu")
                        .addProperty(GOOGLE_KEY, "hu")
                        .getJson())

                .add("Armenian", new MakeJson()
                        .addProperty(MC_LABEL, "hy_am")
                        .addProperty(ISO_639_3, "hye_AM")
                        .addProperty(BING_KEY, "hy")
                        .addProperty(GOOGLE_KEY, "hy")
                        .getJson())

                .add("Indonesian", new MakeJson()
                        .addProperty(MC_LABEL, "id_id")
                        .addProperty(ISO_639_3, "ind_ID")
                        .addProperty(BING_KEY, "id")
                        .addProperty(GOOGLE_KEY, "id")
                        .getJson())

                .add("Igbo", new MakeJson()
                        .addProperty(MC_LABEL, "ig_ng")
                        .addProperty(ISO_639_3, "ibo_NG")
                        .addProperty(BING_KEY, "ig")
                        .addProperty(GOOGLE_KEY, "ig")
                        .getJson())

                .add("Ido", new MakeJson()
                        .addProperty(MC_LABEL, "io_en")
                        .addProperty(ISO_639_3, "ido")
                        .getJson())

                .add("Icelandic", new MakeJson()
                        .addProperty(MC_LABEL, "is_is")
                        .addProperty(ISO_639_3, "isl_IS")
                        .addProperty(BING_KEY, "is")
                        .addProperty(GOOGLE_KEY, "is")
                        .getJson())

                .add("Interslavic", new MakeJson()
                        .addProperty(MC_LABEL, "isv")
                        .addProperty(ISO_639_3, "qis")
                        .getJson())

                .add("Italian", new MakeJson()
                        .addProperty(MC_LABEL, "it_it")
                        .addProperty(ISO_639_3, "ita_IT")
                        .addProperty(BING_KEY, "it")
                        .addProperty(GOOGLE_KEY, "it")
                        .getJson())

                .add("Japanese", new MakeJson()
                        .addProperty(MC_LABEL, "ja_jp")
                        .addProperty(ISO_639_3, "jpn_JP")
                        .addProperty(BING_KEY, "ja")
                        .addProperty(GOOGLE_KEY, "ja")
                        .getJson())

                .add("Lojban", new MakeJson()
                        .addProperty(MC_LABEL, "jbo_en")
                        .addProperty(ISO_639_3, "jbo")
                        .getJson())


                .add("Georgian", new MakeJson()
                        .addProperty(MC_LABEL, "ka_ge")
                        .addProperty(ISO_639_3, "kat_GE")
                        .addProperty(BING_KEY, "ka")
                        .addProperty(GOOGLE_KEY, "ka")
                        .getJson())


                .add("Kazakh", new MakeJson()
                        .addProperty(MC_LABEL, "kk_kz")
                        .addProperty(ISO_639_3, "kaz_KZ")
                        .addProperty(BING_KEY, "kk")
                        .addProperty(GOOGLE_KEY, "kk")
                        .getJson())

                .add("Kannada", new MakeJson()
                        .addProperty(MC_LABEL, "kn_in")
                        .addProperty(ISO_639_3, "kan_IN")
                        .addProperty(BING_KEY, "kn")
                        .addProperty(GOOGLE_KEY, "kn")
                        .getJson())

                .add("Korean", new MakeJson()
                        .addProperty(MC_LABEL, "ko_kr")
                        .addProperty(ISO_639_3, "kor_KR")
                        .addProperty(BING_KEY, "ko")
                        .addProperty(GOOGLE_KEY, "ko")
                        .getJson())

                .add("Kölsch/Ripuarian", new MakeJson()
                        .addProperty(MC_LABEL, "ksh")
                        .addProperty(ISO_639_3, "ksh_DE")
                        .getJson())

                .add("Cornish", new MakeJson()
                        .addProperty(MC_LABEL, "kw_gb")
                        .addProperty(ISO_639_3, "cor_GB")
                        .getJson())

                .add("Latin", new MakeJson()
                        .addProperty(MC_LABEL, "la_la")
                        .addProperty(ISO_639_3, "lat_VA")
                        .addProperty(GOOGLE_KEY, "la")
                        .getJson())

                .add("Luxembourgish", new MakeJson()
                        .addProperty(MC_LABEL, "lb_lu")
                        .addProperty(ISO_639_3, "ltz_LU")
                        .addProperty(GOOGLE_KEY, "lb")
                        .getJson())

                .add("Limburgish", new MakeJson()
                        .addProperty(MC_LABEL, "li_li")
                        .addProperty(ISO_639_3, "lim_NL")
                        .getJson())

                .add("Lombard", new MakeJson()
                        .addProperty(MC_LABEL, "lmo")
                        .addProperty(ISO_639_3, "lmo_IT")
                        .getJson())

                .add("Lao", new MakeJson()
                        .addProperty(MC_LABEL, "lo_la")
                        .addProperty(ISO_639_3, "lao_LA")
                        .addProperty(BING_KEY, "lo")
                        .addProperty(GOOGLE_KEY, "lo")
                        .getJson())

                .add("LOLCAT", new MakeJson()
                        .addProperty(MC_LABEL, "lol_us")
                        .addProperty(ISO_639_3, "qll")
                        .getJson())

                .add("Lithuanian", new MakeJson()
                        .addProperty(MC_LABEL, "lt_lt")
                        .addProperty(ISO_639_3, "lit_LT")
                        .addProperty(BING_KEY, "lt")
                        .addProperty(GOOGLE_KEY, "lt")
                        .getJson())

                .add("Latvian", new MakeJson()
                        .addProperty(MC_LABEL, "lv_lv")
                        .addProperty(ISO_639_3, "lav_LV")
                        .addProperty(BING_KEY, "lv")
                        .addProperty(GOOGLE_KEY, "lv")
                        .getJson())

                .add("Classical Chinese", new MakeJson()
                        .addProperty(MC_LABEL, "lzh")
                        .addProperty(ISO_639_3, "lzh")
                        .getJson())

                .add("Macedonian", new MakeJson()
                        .addProperty(MC_LABEL, "mk_mk")
                        .addProperty(ISO_639_3, "mkd_MK")
                        .addProperty(BING_KEY, "mk")
                        .addProperty(GOOGLE_KEY, "mk")
                        .getJson())

                .add("Mongolian", new MakeJson()
                        .addProperty(MC_LABEL, "mn_mn")
                        .addProperty(ISO_639_3, "mon_MN")
                        .addProperty(GOOGLE_KEY, "mn")
                        .getJson())

                .add("Malay", new MakeJson()
                        .addProperty(MC_LABEL, "ms_my")
                        .addProperty(ISO_639_3, "zlm_MY")
                        .addProperty(BING_KEY, "ms")
                        .addProperty(GOOGLE_KEY, "ms")
                        .getJson())

                .add("Maltese", new MakeJson()
                        .addProperty(MC_LABEL, "mt_mt")
                        .addProperty(ISO_639_3, "mlt_MT")
                        .addProperty(BING_KEY, "mt")
                        .addProperty(GOOGLE_KEY, "mt")
                        .getJson())

                .add("Nahuatl", new MakeJson()
                        .addProperty(MC_LABEL, "nah")
                        .addProperty(ISO_639_3, "nhe_MX")
                        .getJson())

                .add("Low German", new MakeJson()
                        .addProperty(MC_LABEL, "nds_de")
                        .addProperty(ISO_639_3, "nds_DE")
                        .getJson())

                .add("Dutch, Flemish", new MakeJson()
                        .addProperty(MC_LABEL, "nl_be")
                        .addProperty(ISO_639_3, "nld_BE")
                        .getJson())


                .add("Dutch", new MakeJson()
                        .addProperty(MC_LABEL, "nl_nl")
                        .addProperty(ISO_639_3, "nld_NL")
                        .addProperty(BING_KEY, "nl")
                        .addProperty(GOOGLE_KEY, "nl")
                        .getJson())

                .add("Norwegian Nynorsk", new MakeJson()
                        .addProperty(MC_LABEL, "nn_no")
                        .addProperty(ISO_639_3, "nno_NO")
                        .getJson())

                .add("Norwegian Bokmål", new MakeJson()
                        .addProperty(MC_LABEL, "no_no")
                        .addProperty(ISO_639_3, "nob_NO")
                        .getJson())

                .add("Occitan", new MakeJson()
                        .addProperty(MC_LABEL, "oc_fr")
                        .addProperty(ISO_639_3, "oci_FR")
                        .getJson())

                .add("Elfdalian", new MakeJson()
                        .addProperty(MC_LABEL, "ovd")
                        .addProperty(ISO_639_3, "ovd_SE")
                        .getJson())

                .add("Polish", new MakeJson()
                        .addProperty(MC_LABEL, "pl_pl")
                        .addProperty(ISO_639_3, "pol_PL")
                        .addProperty(BING_KEY, "pl")
                        .addProperty(GOOGLE_KEY, "pl")
                        .getJson())

                .add("Brazilian Portuguese", new MakeJson()
                        .addProperty(MC_LABEL, "pt_br")
                        .addProperty(ISO_639_3, "por_BR")
                        .addProperty(BING_KEY, "pt")
                        .getJson())

                .add("European Portuguese", new MakeJson()
                        .addProperty(MC_LABEL, "pt_pt")
                        .addProperty(ISO_639_3, "por_PT")
                        .addProperty(BING_KEY, "pt-PT")
                        .getJson())

                .add("Quenya (Form of Elvish from LOTR)", new MakeJson()
                        .addProperty(MC_LABEL, "qya_aa")
                        .addProperty(ISO_639_3, "qya")
                        .getJson())

                .add("Romanian", new MakeJson()
                        .addProperty(MC_LABEL, "ro_ro")
                        .addProperty(ISO_639_3, "ron_RO")
                        .addProperty(BING_KEY, "ro")
                        .addProperty(GOOGLE_KEY, "ro")
                        .getJson())

                .add("Russian (Pre-revolutionary)", new MakeJson()
                        .addProperty(MC_LABEL, "rpr")
                        .addProperty(ISO_639_3, "qpr")
                        .getJson())

                .add("Russian", new MakeJson()
                        .addProperty(MC_LABEL, "ru_ru")
                        .addProperty(ISO_639_3, "rus_RU")
                        .addProperty(BING_KEY, "ru")
                        .addProperty(GOOGLE_KEY, "ru")
                        .getJson())

                .add("Rusyn", new MakeJson()
                        .addProperty(MC_LABEL, "ry_ua")
                        .addProperty(ISO_639_3, "rue_UA")
                        .getJson())

                .add("Yakut", new MakeJson()
                        .addProperty(MC_LABEL, "sah_sah")
                        .addProperty(ISO_639_3, "sah_RU")
                        .getJson())

                .add("Northern Sami", new MakeJson()
                        .addProperty(MC_LABEL, "se_no")
                        .addProperty(ISO_639_3, "sme_NO")
                        .getJson())

                .add("Slovak", new MakeJson()
                        .addProperty(MC_LABEL, "sk_sk")
                        .addProperty(ISO_639_3, "slk_SK")
                        .addProperty(BING_KEY, "sk")
                        .addProperty(GOOGLE_KEY, "sk")
                        .getJson())

                .add("Slovenian", new MakeJson()
                        .addProperty(MC_LABEL, "sl_si")
                        .addProperty(ISO_639_3, "slv_SI")
                        .addProperty(BING_KEY, "sl")
                        .addProperty(GOOGLE_KEY, "sl")
                        .getJson())

                .add("Somali", new MakeJson()
                        .addProperty(MC_LABEL, "so_so")
                        .addProperty(ISO_639_3, "som_SO")
                        .addProperty(BING_KEY, "so")
                        .addProperty(GOOGLE_KEY, "so")
                        .getJson())

                .add("Albanian", new MakeJson()
                        .addProperty(MC_LABEL, "sq_al")
                        .addProperty(ISO_639_3, "sqi_AL")
                        .addProperty(BING_KEY, "sq")
                        .addProperty(GOOGLE_KEY, "sq")
                        .getJson())

                .add("Serbian (Latin)", new MakeJson()
                        .addProperty(MC_LABEL, "sr_cs")
                        .addProperty(ISO_639_3, "srp-Latn_RS")
                        .addProperty(BING_KEY, "sr-Latn")
                        .getJson())

                .add("Serbian (Cyrillic)", new MakeJson()
                        .addProperty(MC_LABEL, "sr_sp")
                        .addProperty(ISO_639_3, "srp-Cyrl_RS")
                        .addProperty(BING_KEY, "sr-Cyrl")
                        .getJson())

                .add("Swedish", new MakeJson()
                        .addProperty(MC_LABEL, "sv_se")
                        .addProperty(ISO_639_3, "swe_SE")
                        .addProperty(BING_KEY, "sv")
                        .addProperty(GOOGLE_KEY, "sv")
                        .getJson())

                .add("Upper Saxon German", new MakeJson()
                        .addProperty(MC_LABEL, "sxu")
                        .addProperty(ISO_639_3, "sxu_DE")
                        .getJson())

                .add("Silesian", new MakeJson()
                        .addProperty(MC_LABEL, "szl")
                        .addProperty(ISO_639_3, "szl_PL")
                        .getJson())

                .add("Tamil", new MakeJson()
                        .addProperty(MC_LABEL, "ta_in")
                        .addProperty(ISO_639_3, "tam_IN")
                        .addProperty(BING_KEY, "ta")
                        .addProperty(GOOGLE_KEY, "ta")
                        .getJson())

                .add("Thai", new MakeJson()
                        .addProperty(MC_LABEL, "th_th")
                        .addProperty(ISO_639_3, "tha_TH")
                        .addProperty(BING_KEY, "th")
                        .addProperty(GOOGLE_KEY, "th")
                        .getJson())

                .add("Tagalog", new MakeJson()
                        .addProperty(MC_LABEL, "tl_ph")
                        .addProperty(ISO_639_3, "tgl_PH")
                        .getJson())

                .add("Klingon", new MakeJson()
                        .addProperty(MC_LABEL, "tlh_aa")
                        .addProperty(ISO_639_3, "tlh")
                        .addProperty(BING_KEY, "tlh-Latn")
                        .getJson())

                .add("Toki Pona", new MakeJson()
                        .addProperty(MC_LABEL, "tok")
                        .addProperty(ISO_639_3, "tok")
                        .getJson())

                .add("Turkish", new MakeJson()
                        .addProperty(MC_LABEL, "tr_tr")
                        .addProperty(ISO_639_3, "tur_TR")
                        .addProperty(BING_KEY, "tr")
                        .addProperty(GOOGLE_KEY, "tr")
                        .getJson())

                .add("Tatar", new MakeJson()
                        .addProperty(MC_LABEL, "tt_ru")
                        .addProperty(ISO_639_3, "tat_RU")
                        .addProperty(BING_KEY, "tt")
                        .addProperty(GOOGLE_KEY, "tt")
                        .getJson())

                .add("Ukrainian", new MakeJson()
                        .addProperty(MC_LABEL, "uk_ua")
                        .addProperty(ISO_639_3, "ukr_UA")
                        .addProperty(BING_KEY, "uk")
                        .addProperty(GOOGLE_KEY, "uk")
                        .getJson())

                .add("Valencian", new MakeJson()
                        .addProperty(MC_LABEL, "val_es")
                        .addProperty(ISO_639_3, "cat_ES-VC")
                        .getJson())

                .add("Venetian", new MakeJson()
                        .addProperty(MC_LABEL, "vec_it")
                        .addProperty(ISO_639_3, "vec_IT")
                        .getJson())

                .add("Vietnamese", new MakeJson()
                        .addProperty(MC_LABEL, "vi_vn")
                        .addProperty(ISO_639_3, "vie_VN")
                        .addProperty(BING_KEY, "vi")
                        .addProperty(GOOGLE_KEY, "vi")
                        .getJson())

                .add("Yiddish", new MakeJson()
                        .addProperty(MC_LABEL, "yi_de")
                        .addProperty(ISO_639_3, "yid_IL")
                        .addProperty(GOOGLE_KEY, "yi")
                        .getJson())

                .add("Yoruba", new MakeJson()
                        .addProperty(MC_LABEL, "yo_ng")
                        .addProperty(ISO_639_3, "yor_NG")
                        .addProperty(BING_KEY, "yo")
                        .addProperty(GOOGLE_KEY, "yo")
                        .getJson())

                .add("Chinese Simplified (China Mandarin)", new MakeJson()
                        .addProperty(MC_LABEL, "zh_tw.json")
                        .addProperty(ISO_639_3, "zho-Hans_CN")
                        .addProperty(BING_KEY, "zh-Hans")
                        .addProperty(GOOGLE_KEY, "zh-CN")
                        .getJson())

                .add("Chinese Traditional (Hong Kong Mix)", new MakeJson()
                        .addProperty(MC_LABEL, "zh_hk")
                        .addProperty(ISO_639_3, "zho-Hant_HK")
                        .getJson())

                .add("Chinese Traditional (Taiwan Mandarin)", new MakeJson()
                        .addProperty(MC_LABEL, "zh_tw")
                        .addProperty(ISO_639_3, "zho-Hant_TW")
                        .addProperty(BING_KEY, "zh-Hant")
                        .addProperty(GOOGLE_KEY, "zh-TW")
                        .getJson())

                .add("Malay (Jawi)", new MakeJson()
                        .addProperty(MC_LABEL, "zlm_arab")
                        .addProperty(ISO_639_3, "zlm-Arab_MY")
                        .getJson())

                .add("Malay (Jawi)",
                        new MakeJson()
                                .addProperty(MC_LABEL, "zlm_arab")
                                .addProperty(ISO_639_3, "zlm-Arab_MY")
                                .getJson())
                .getJson());
        save();
    }

    class MakeJson {
        JsonObject obj;

        public MakeJson() {
            this.obj = new JsonObject();
        }

        public MakeJson(JsonObject jsonObject) {
            this.obj = jsonObject;
        }

        public MakeJson addProperty(String key, String value) {
            obj.addProperty(key, value);
            return this;
        }

        public MakeJson add(String key, String value) {
            obj.addProperty(key, value);
            return this;
        }

        public MakeJson add(String key, JsonArray value) {
            obj.add(key, value);
            return this;
        }

        public MakeJson add(String key, JsonObject object) {
            obj.add(key, object);
            return this;
        }

        public MakeJson add(String key, Boolean value) {
            obj.addProperty(key, value);
            return this;
        }

        JsonObject getJson() {
            return obj;
        }
    }

    public static File getConfigPath(String modid) {
        File configFile = Paths.get("config/lehjr").resolve(modid).resolve("config.json").toAbsolutePath().toFile();
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configFile;
    }
}