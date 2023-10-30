package powersuits.common;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import numina.client.config.DatagenConfig;
import numina.client.util.lang.translators.BingTranslator;
import numina.client.util.lang.translators.ITranslator;
import powersuits.client.lang.MPSMultiLanguageProvider;
import powersuits.common.loot.MPSBlockTagProvider;
import powersuits.common.loot.MPSLootTableProvider;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PowersuitsDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        //Client side data generators
        DatagenConfig config = new DatagenConfig(MPSConstants.MOD_ID);
        ITranslator translator = new BingTranslator(config);
        gen.addProvider(event.includeClient(), new MPSMultiLanguageProvider(gen, MPSConstants.MOD_ID, config, translator));
//        translator.quit();

        //Server side data generators
        gen.addProvider(event.includeServer(), new MPSLootTableProvider(gen));
        gen.addProvider(event.includeServer(), new MPSBlockTagProvider(gen, existingFileHelper));
    }
}