package numina.client.lang;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class AbstractLanguageProviderNumina extends LanguageProvider implements ILanguageProviderNumina {
    public AbstractLanguageProviderNumina(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }


    @Override
    public void addItemDescriptions(Item key, String description) {
        add(key.getDescriptionId() + ".desc", description);
    }
}
