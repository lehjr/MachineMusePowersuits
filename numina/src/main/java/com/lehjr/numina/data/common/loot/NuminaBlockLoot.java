package com.lehjr.numina.data.common.loot;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaBlocks;
import com.lehjr.numina.common.registration.NuminaCodecs;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.stream.Collectors;

public class NuminaBlockLoot extends VanillaBlockLoot {
    public NuminaBlockLoot(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
        this.add(NuminaBlocks.CHARGING_BASE_BLOCK.get(), createChargedInventoryLoot(NuminaBlocks.CHARGING_BASE_BLOCK.get()));
    }

    protected LootTable.Builder createChargedInventoryLoot(Block block) {
        return LootTable.lootTable().withPool(
                applyExplosionCondition(block, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block)
                                .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                        .include(DataComponents.CONTAINER)
                                        .include(NuminaCodecs.ENERGY)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.keySet().stream()
                .filter(e -> e.getNamespace().equals(NuminaConstants.MOD_ID))
                .map(BuiltInRegistries.BLOCK::get)
                .collect(Collectors.toList());
    }
}

