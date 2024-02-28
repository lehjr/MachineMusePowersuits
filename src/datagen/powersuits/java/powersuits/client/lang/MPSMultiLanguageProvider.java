package powersuits.client.lang;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSItems;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import numina.client.util.lang.MulitiLanguageProvider;
import numina.client.util.lang.translators.ITranslator;
import numina.client.util.lang.translators.Language;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class MPSMultiLanguageProvider extends MulitiLanguageProvider {
    Language mainLanguage;
    public MPSMultiLanguageProvider(DataGenerator gen, String modid, DatagenConfig config, ITranslator translator) {
        super(gen, modid, config, modid, translator);
        this.mainLanguage = config.getMainLanguageCode();
    }

