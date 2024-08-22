package com.lehjr.numina.data.common;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.data.client.NuminaLanguageProvider_EN_US;
import com.lehjr.numina.data.common.loot.NuminaBlockLoot;
import com.lehjr.numina.data.common.loot.NuminaBlockTagProvider;
import com.lehjr.numina.data.common.recipes.NuminaRecipeGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NuminaDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        NuminaLogger.logError("Hello from Numina Data generator");
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper helper = event.getExistingFileHelper();

        //Client side data generators
        generator.addProvider(event.includeServer(), new NuminaLanguageProvider_EN_US(output));
//        translator.quit();

//        PackOutput pOutput,
//        Set<ResourceKey<LootTable>> pRequiredTables,
//        List<LootTableProvider.SubProviderEntry> pSubProviders,
//        CompletableFuture<HolderLookup.Provider> pRegistries

        //Server side data generators
        generator.addProvider(event.includeServer(), new LootTableProvider(
                output,
                Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(NuminaBlockLoot::new, LootContextParamSets.BLOCK)),
                lookupProvider));

        generator.addProvider(event.includeServer(), new NuminaBlockTagProvider(
                generator,
                lookupProvider,
                helper));

        NuminaLogger.logError("output: " + output.getOutputFolder().toAbsolutePath());

        generator.addProvider(event.includeServer(), new NuminaRecipeGenerator(output, lookupProvider));
    }
}
