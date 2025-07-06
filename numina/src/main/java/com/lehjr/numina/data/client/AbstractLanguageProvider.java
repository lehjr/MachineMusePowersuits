package com.lehjr.numina.data.client;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class AbstractLanguageProvider extends LanguageProvider {
    public AbstractLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    public abstract void addItemGroup();

    public abstract void addGui();

    public abstract void addModules();

    public abstract void addBlocks();

    public abstract void addModuleTradeoffs();

    public void addItemDescriptions(Item key, String description) {
        add(key.getDescriptionId() + ".desc", description);
    }

    public void addTradeoff(String tradeoff, String translation) {
        add("module.tradeoff." + tradeoff, translation);
    }
}
