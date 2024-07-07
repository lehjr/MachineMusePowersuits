package numina.common.loot;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class NuminaBlockLoot extends VanillaBlockLoot {

//    public NuminaBlockLoot(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
//        this(output, List.of(
//                new LootTableProvider.SubProviderEntry(MekanismBlockLootTables::new, LootContextParamSets.BLOCK),
//                new LootTableProvider.SubProviderEntry(MekanismEntityLootTables::new, LootContextParamSets.ENTITY)
//        ), provider);
//    }
//
//    protected NuminaBlockLoot(PackOutput output, List<LootTableProvider.SubProviderEntry> subProviders, CompletableFuture<HolderLookup.Provider> provider) {
//        this(output, Collections.emptySet(), subProviders, provider);
//    }
//
//    protected BaseLootProvider(PackOutput output,
//                               Set<ResourceKey<LootTable>> requiredTables,
//                               List<LootTableProvider.SubProviderEntry> subProviders,
//                               CompletableFuture<HolderLookup.Provider> provider) {
//        super(output, requiredTables, subProviders, provider);
//    }


    @Override
    protected void generate() {
        this.add(NuminaObjects.CHARGING_BASE_BLOCK.get(), createChargedInventoryLoot(NuminaObjects.CHARGING_BASE_BLOCK.get()));
    }

    protected LootTable.Builder createChargedInventoryLoot(Block block) {
        return LootTable.lootTable().withPool(
                applyExplosionCondition(block, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block)
                                .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                        .include(DataComponents.CONTAINER)
                                        .include(NuminaObjects.ENERGY)))));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.keySet().stream()
                .filter(e -> e.getNamespace().equals(NuminaConstants.MOD_ID))
                .map(BuiltInRegistries.BLOCK::get)
                .collect(Collectors.toList());
    }
}

