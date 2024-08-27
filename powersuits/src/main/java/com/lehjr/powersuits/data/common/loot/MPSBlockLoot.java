package com.lehjr.powersuits.data.common.loot;

import com.lehjr.powersuits.common.registration.MPSBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;

import java.util.Collections;

public class MPSBlockLoot extends VanillaBlockLoot {
    public MPSBlockLoot(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
        this.dropSelf(MPSBlocks.TINKER_TABLE_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
//        return MPSBlocks.BLOCKS.getEntries().stream().filter(block -> block.get() instanceof TinkerTable).map(RegistryObject::get)::iterator;
        // Only the Tinker table should be harvestable
        return Collections.singleton(MPSBlocks.TINKER_TABLE_BLOCK.get());
    }
}