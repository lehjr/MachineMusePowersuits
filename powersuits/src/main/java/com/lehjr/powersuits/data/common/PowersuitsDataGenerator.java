package com.lehjr.powersuits.data.common;

import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.data.client.lang.MPSLanguageProvider_EN_US;
import com.lehjr.powersuits.data.common.loot.MPSBlockLoot;
import com.lehjr.powersuits.data.common.loot.MPSBlockTagProvider;
import com.lehjr.powersuits.data.common.recipes.MPSRecipeGenerator;
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

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PowersuitsDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        //        //Client side data generators
        generator.addProvider(event.includeClient(), new MPSLanguageProvider_EN_US(output));
        ////        translator.quit();
        //
        //        //Server side data generators
        ////        generator.addProvider(event.includeServer(), new MPSLootTableProvider(generator));
        generator.addProvider(event.includeServer(), new MPSBlockTagProvider(output, lookupProvider, helper));

        generator.addProvider(event.includeServer(), new LootTableProvider(
            output,
            Collections.emptySet(),
            List.of(new LootTableProvider.SubProviderEntry(MPSBlockLoot::new, LootContextParamSets.BLOCK)),
            lookupProvider));

        generator.addProvider(event.includeServer(), new MPSRecipeGenerator(output, lookupProvider));

    }
}
