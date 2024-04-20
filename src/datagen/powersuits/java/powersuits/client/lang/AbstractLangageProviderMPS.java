package powersuits.client.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class AbstractLangageProviderMPS  extends LanguageProvider implements ILanguageProviderMPS {
    public AbstractLangageProviderMPS(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }
}
