package numina.common;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import numina.client.config.DatagenConfig;
import numina.client.lang.NuminaMultiLanguageProvider;
import numina.client.util.lang.translators.BingTranslator;
import numina.client.util.lang.translators.ITranslator;
import numina.common.loot.NuminaBlockTagProvider;
import numina.common.loot.NuminaLootTableProvider;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NuminaDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        //Client side data generators
        DatagenConfig config = new DatagenConfig(NuminaConstants.MOD_ID);
        ITranslator translator = new BingTranslator(config);
        gen.addProvider(event.includeServer(), new NuminaMultiLanguageProvider(gen, NuminaConstants.MOD_ID, config, translator));
//        translator.quit();

        //Server side data generators
        gen.addProvider(event.includeServer(), new NuminaLootTableProvider(gen));
        gen.addProvider(event.includeServer(), new NuminaBlockTagProvider(gen, event.getExistingFileHelper()));
    }
}

