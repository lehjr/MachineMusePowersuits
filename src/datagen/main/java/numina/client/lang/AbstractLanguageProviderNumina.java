package numina.client.lang;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class AbstractLanguageProviderNumina extends LanguageProvider implements ILanguageProviderNumina {
    public AbstractLanguageProviderNumina(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }
}
