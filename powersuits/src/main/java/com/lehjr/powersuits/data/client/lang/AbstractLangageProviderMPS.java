package com.lehjr.powersuits.data.client.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class AbstractLangageProviderMPS  extends LanguageProvider implements ILanguageProviderMPS {
    public AbstractLangageProviderMPS(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }
}