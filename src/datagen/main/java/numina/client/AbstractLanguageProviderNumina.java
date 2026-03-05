package numina.client;

import net.minecraft.data.PackOutput;

public abstract class AbstractLanguageProviderNumina extends AbstractLanguageProvider {
    public AbstractLanguageProviderNumina(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        addItemGroup();
        addGui();
        addArmorStand();
        addModuleTradeoffs();
        addBlocks();
        addToolTips();
        addModules();
        addComponents();
        addModuleCategories();
    }

    @Override
    public abstract void addItemGroup();

    @Override
    public abstract void addGui();

    public abstract void addArmorStand();

    @Override
    public abstract void addModuleTradeoffs();

    @Override
    public abstract void addBlocks();

    public abstract void addToolTips();

    @Override
    public abstract void addModules();

    public abstract void addComponents();

    public abstract void addModuleCategories();
}
