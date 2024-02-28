package powersuits.common;

import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import numina.client.util.lang.translators.BingTranslator;
import numina.client.util.lang.translators.ITranslator;
import powersuits.client.lang.MPSMultiLanguageProvider;
import powersuits.common.loot.MPSBlockLoot;
import powersuits.common.loot.MPSBlockTagProvider;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PowersuitsDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper helper = event.getExistingFileHelper();

        //Client side data generators
        DatagenConfig config = new DatagenConfig(MPSConstants.MOD_ID);
        ITranslator translator = new BingTranslator(config);
        generator.addProvider(event.includeClient(), new MPSMultiLanguageProvider(generator, MPSConstants.MOD_ID, config, translator));
//        translator.quit();

        //Server side data generators
//        generator.addProvider(event.includeServer(), new MPSLootTableProvider(generator));
        generator.addProvider(event.includeServer(), new MPSBlockTagProvider(output, lookupProvider, helper));

        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(MPSBlockLoot::new, LootContextParamSets.BLOCK))));

    }
}