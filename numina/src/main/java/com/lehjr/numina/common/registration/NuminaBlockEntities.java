package com.lehjr.numina.common.registration;

import com.lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NuminaBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NuminaConstants.MOD_ID);
    public static final Supplier<BlockEntityType<ChargingBaseBlockEntity>> CHARGING_BASE_BLOCK_ENTITY = BLOCKENTITY_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> BlockEntityType.Builder.of(ChargingBaseBlockEntity::new, NuminaBlocks.CHARGING_BASE_BLOCK.get()).build(null));
}
