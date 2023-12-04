package numina.client.util.lang;

import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import numina.client.config.DatagenConfig;
import numina.client.util.lang.translators.Language;

import java.util.HashMap;
import java.util.Map;

public class LanguageProviderHelper extends LanguageProvider {
    Map<String, String> translations;
    DatagenConfig config;
    Language lang;

    public LanguageProviderHelper(DataGenerator gen, String modid, Language lang, DatagenConfig config) {
        super(gen, modid, lang.mc_label());
        this.translations = new HashMap<>();
        this.lang = lang;
        this.config = config;
    }

    public Language getLanguage() {
        return lang;
    }

    public void setTranslations(Map<String, String> translations) {
        NuminaLogger.logDebug("setting translations with mapsize: " +translations.size());

        this.translations = translations;
    }

    @Override
    protected void addTranslations() {
        translations.forEach(this::add);
    }
}