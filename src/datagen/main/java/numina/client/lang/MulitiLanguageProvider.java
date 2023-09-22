package numina.client.lang;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MulitiLanguageProvider implements DataProvider {
    public TranslationMap translationMap;
    Map<Localizations, NuminaLanguageProvider> languageProviderMap;
    public MulitiLanguageProvider(DataGenerator gen, String modid) {
        languageProviderMap = new HashMap<>();
        Arrays.stream(Localizations.values()).forEach(locale -> languageProviderMap.put(locale, new NuminaLanguageProvider(gen, modid, locale)));
        translationMap = new TranslationMap();
    }

    /**
     * extend this and populate translationMap first before calling this
     * @param pOutput
     * @throws IOException
     */
    @Override
    public void run(CachedOutput pOutput) throws IOException {
            languageProviderMap.forEach((locale, provider) -> {
                try {
                    System.out.println("locale: " + locale.getCode());
                    provider.setTranslations(translationMap.getTranslationsForLocale(locale));
                    provider.run(pOutput);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    public Map<String, String> getTranslationsForLocale(Localizations locale) {
        return translationMap.getTranslationsForLocale(locale);
    }

    public void add(String translationKey, Map<Localizations, String> translationsMapIn) {
        translationMap.addTranslations(translationKey, translationsMapIn);
    }

    public void add(Item key, Map<Localizations, String> translationsMapIn) {
        add(key.getDescriptionId(), translationsMapIn);
    }

    public void addItemDescriptions(Item key, Map<Localizations, String> translationsMapIn) {
        add(new StringBuilder(key.getDescriptionId()).append(".desc").toString(), translationsMapIn);
    }

    public void add(Supplier<? extends EntityType<?>> key, Map<Localizations, String> translationsMapIn) {
        add(key.get(), translationsMapIn);
    }

    public void add(EntityType<?> key, Map<Localizations, String> translationsMapIn) {
        add(key.getDescriptionId(), translationsMapIn);
    }

    public void add(Block key, Map<Localizations, String> translationsMapIn) {
        add(key.getDescriptionId(), translationsMapIn);
    }

    public void addTranslationTopAll(String key, String value) {
        add(key, new HashMap<>(){{
            Arrays.stream(Localizations.values()).forEach(locale-> put(locale, value));
        }});
    }



    @Override
    public String getName() {
        return "MultiLanguageProvider";
    }
}
