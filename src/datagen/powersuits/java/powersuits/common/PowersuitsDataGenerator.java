package powersuits.common;

import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import powersuits.client.lang.MPSLanguageProvider_EN_US;
import powersuits.client.model.block.MPSBlockModelProvider;
import powersuits.client.model.block.MPSBlockStateProvider;
import powersuits.client.model.item.MPSItemModelProvider;
import powersuits.common.loot.MPSBlockLoot;
import powersuits.common.loot.MPSBlockTagProvider;
import powersuits.common.recipes.MPSRecipeGenerator;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MPSConstants.MOD_ID)
public class PowersuitsDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        //        //Client side data generators
        generator.addProvider(event.includeClient(), new MPSLanguageProvider_EN_US(output));
        generator.addProvider(event.includeClient(), new MPSItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new MPSBlockModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new MPSBlockStateProvider(output, helper));

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
