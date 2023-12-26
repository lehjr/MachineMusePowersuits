package numina.client.util.lang;

import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import numina.client.config.DatagenConfig;
import numina.client.util.lang.translators.Language;

import java.util.Map;
import java.util.TreeMap;

public class LanguageProviderHelper extends LanguageProvider {
    Map<String, String> translations;
    DatagenConfig config;
    Language lang;

    public LanguageProviderHelper(DataGenerator gen, String modid, Language lang, DatagenConfig config) {
        super(gen, modid, lang.mc_label());
        this.translations = new TreeMap<>();
        this.lang = lang;
        this.config = config;
    }

    public Language getLanguage() {
        return lang;
    }

    public void setTranslations(Map<String, String> translations) {
        NuminaLogger.logDebug("setting translations for " + lang.mc_label() + " with mapsize: " +translations.size());
        this.translations = translations;
    }

    @Override
    protected void addTranslations() {
        NuminaLogger.logDebug("adding " + translations.size() +"  entries to translations for language " + lang.mc_label());

        translations.forEach(this::add);
    }
}