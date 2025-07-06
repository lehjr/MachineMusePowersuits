package com.lehjr.powersuits.data.client.lang;

import com.lehjr.numina.data.client.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class AbstractLangageProviderMPS extends AbstractLanguageProvider {
    public AbstractLangageProviderMPS(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        addItemGroup();
        addModularItems();
        addModules();
        addBlocks();
        addGui();
        addKeybinds();
        addModuleTradeoffs();
        addModels();
    }

    public abstract void addModularItems();

    public abstract void addKeybinds();

    public abstract void addModels();

    public void addPowerfistPart(String part, String translation) {
        add("javaModel.powerfist." + part + ".partName", translation);
    }

    public void addModel2Part(String part, String translation) {
        add("model.armor2." + part + ".partName", translation);
    }
}
