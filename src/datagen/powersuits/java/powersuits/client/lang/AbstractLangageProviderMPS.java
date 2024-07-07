package powersuits.client.lang;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class AbstractLangageProviderMPS  extends LanguageProvider implements ILanguageProviderMPS {
    public AbstractLangageProviderMPS(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        addItemGroup();
        addModularItems();
        addModules();
        addBlocks();
        addGui();
        addKeybinds();
        addModuleTradeoff();
        addModels();
    }

    public static final String PARTNAME = ".partName";

    @Override
    public void addJavaModelPart(String parent, String part, String translation) {
        add("javaModel." + parent + "." + part + PARTNAME, translation);
    }

    @Override
    public void addPowerfistPart(String part, String translation) {
        addJavaModelPart("powerfist", part, translation);
    }

    public String addOBJModel(String modelName, String translation) {
        add("model." + modelName +".modelName", translation);
        return modelName;
    }

    @Override
    public void addOBJModelPart(String parent, String part, String translation) {
        add("model." + parent + "." +  part + PARTNAME, translation);
    }

    @Override
    public void addTradeoff(String tradeoff, String translation) {
        add("module.tradeoff." + tradeoff, translation);
    }

    @Override
    public void addItemDescriptions(Item key, String description) {
        add(key.getDescriptionId() + ".desc", description);
    }
}
