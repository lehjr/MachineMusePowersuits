package numina.client.lang;

import lehjr.numina.common.base.NuminaObjects;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;


/**
 * FIXME: only way to make this LESS?? tedious is to create a set of maps containing K<->V as a language set in another class and loop through the languages from the calling class
 * use a system add(String key, String en_us, String de_de, ...... this way all languages have all keys... and just wing it for missing keys
 */
public class NuminaLanguageProvider extends LanguageProvider {
    Map<String, String> translations;
    public NuminaLanguageProvider(DataGenerator gen, String modid, Localizations locale) {
        super(gen, modid, locale.code);
        this.translations = new HashMap<>();
    }

    public void setTranslations(Map<String, String> translations) {
        System.out.println("seting translations with mapsize: " +translations.size());

        this.translations = translations;
    }

    @Override
    protected void addTranslations() {
        translations.forEach((string, string2) -> add(string, string2));
    }



















    void addComponent(Item key, String translation, String description) {
        String id = key.getDescriptionId();
        add(key.getDescriptionId(), translation);
        add(new StringBuilder(id).append(".desc").toString(), description);
    }
}