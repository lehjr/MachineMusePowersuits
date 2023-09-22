package numina.client.lang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TranslationMap {
    Map<String, Map<Localizations, String>> translations;
    public TranslationMap() {
        translations = new HashMap<>();
    }

    // This is not the most efficient way to build the map, but it is the least tedious
    public void addTranslations(String translationKey,  Map<Localizations, String> translationsMapIn) {
        this.translations.put(translationKey, translationsMapIn);
    }

    public Map<String, String> getTranslationsForLocale(Localizations locale) {
        Map<String, String> retMap = new HashMap<>();
        translations.forEach((string, localizationsStringMap) -> retMap.put(string, localizationsStringMap.getOrDefault(locale, "")));
        return retMap;
    }
}