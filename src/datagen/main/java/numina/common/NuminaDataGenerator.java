package numina.common;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import numina.client.lang.NuminaLangParser;
import numina.client.lang.NuminaLanguageProvider;
import numina.client.lang.NuminaMultiLanguageProvider;
import numina.common.loot.NuminaBlockTagProvider;
import numina.common.loot.NuminaLootTableProvider;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NuminaDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        //Client side data generators
//        gen.addProvider(event.includeClient(), new NuminaLangParser(gen, existingFileHelper, NuminaConstants.MOD_ID, "main"));
//        gen.addProvider(event.includeClient(), new NuminaLanguageProvider(gen,NuminaConstants.MOD_ID, "us_en"));
        gen.addProvider(event.includeServer(), new NuminaMultiLanguageProvider(gen, NuminaConstants.MOD_ID));
        //Server side data generators
        gen.addProvider(event.includeServer(), new NuminaLootTableProvider(gen));
        gen.addProvider(event.includeServer(), new NuminaBlockTagProvider(gen, existingFileHelper));

    }
}

