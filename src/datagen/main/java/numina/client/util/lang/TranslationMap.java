package numina.client.util.lang;

import numina.client.util.lang.translators.Language;

import java.util.HashMap;
import java.util.Map;

public class TranslationMap {
    Map<String, Map<Language, String>> translations;
    public TranslationMap() {
        translations = new HashMap<>();
    }

    // This is not the most efficient way to build the map, but it is the least tedious
    public void addTranslations(String translationKey,  Map<Language, String> translationsMapIn) {
        this.translations.put(translationKey, translationsMapIn);
    }

    public void addTranslation(String translationKey, Language lang, String translation) {
        Map<Language, String> tmp = translations.getOrDefault(translationKey, new HashMap<>());
        tmp.put(lang, translation);
        translations.put(translationKey, tmp);
    }

    public Map<Language, String> getTranslationsForKey(String key) {
        return translations.getOrDefault(key, new HashMap<>());
    }

    public Map<String, String> getTranslationsForLocale(Language locale) {
        Map<String, String> retMap = new HashMap<>();
        translations.forEach((string, localizationsStringMap) -> {
            if (localizationsStringMap.containsKey(locale)) {
                retMap.put(string, localizationsStringMap.get(locale));
            }
        });
        return retMap;
    }
}