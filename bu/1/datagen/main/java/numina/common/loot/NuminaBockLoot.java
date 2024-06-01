package numina.common.loot;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.stream.Collectors;

public class NuminaBockLoot extends VanillaBlockLoot {
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
                                .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                        .copy("inv", "BlockEntityTag.inv")
                                        .copy("energy", "BlockEntityTag.energy"))
                                .apply(SetContainerContents.setContents(NuminaObjects.CHARGING_BASE_BLOCK_ENTITY.get())))));
//                                        .withEntry(DynamicLoot.dynamicEntry(ShulkerBoxBlock.CONTENTS))))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(NuminaConstants.MOD_ID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
