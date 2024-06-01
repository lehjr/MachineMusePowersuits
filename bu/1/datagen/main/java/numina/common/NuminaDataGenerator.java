package numina.common;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import numina.client.lang.NuminaLanguageProvider_US_EN;
import numina.common.loot.NuminaBlockTagProvider;
import numina.common.loot.NuminaBockLoot;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NuminaDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper helper = event.getExistingFileHelper();

        //Client side data generators
        generator.addProvider(event.includeServer(), new NuminaLanguageProvider_US_EN(output));
//        translator.quit();

        //Server side data generators
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(NuminaBockLoot::new, LootContextParamSets.BLOCK))));

        generator.addProvider(event.includeServer(), new NuminaBlockTagProvider(
                generator,
                lookupProvider,
                helper));
    }
}

