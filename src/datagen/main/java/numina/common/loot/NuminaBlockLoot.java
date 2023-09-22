package numina.common.loot;

import lehjr.numina.common.base.NuminaObjects;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

public class NuminaBlockLoot extends BlockLoot {

    @Override
    protected void addTables() {
        this.add(NuminaObjects.CHARGING_BASE_BLOCK.get(), NuminaBlockLoot::createChargedInventoryLoot);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return NuminaObjects.NUMINA_BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected static LootTable.Builder createChargedInventoryLoot(Block block) {
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


}
