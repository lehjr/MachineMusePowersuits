package lehjr.powersuits.common.base;

import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.block.TinkerTable;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import lehjr.powersuits.common.blockentity.TinkerTableBlockEntity;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MPSBlocks {
    /**
     * Blocks ------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MPSConstants.MOD_ID);

    public static final DeferredHolder<Block, TinkerTable> TINKER_TABLE_BLOCK = BLOCKS.register(MPSRegistryNames.TINKER_TABLE.getPath(), TinkerTable::new);

    public static final DeferredHolder<Block, LuxCapacitorBlock> LUX_CAPACITOR_BLOCK = BLOCKS.register(MPSRegistryNames.LUX_CAPACITOR.getPath(), LuxCapacitorBlock::new);

    /**
     * Block Entity Types -------------------------------------------------------------------------
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MPSConstants.MOD_ID);

    public static final Supplier<BlockEntityType<TinkerTableBlockEntity>> TINKER_TABLE_BLOCKENTITY_TYPE = BLOCKENTITY_TYPES.register(MPSRegistryNames.TINKER_TABLE.getPath(),
            () -> BlockEntityType.Builder.of(TinkerTableBlockEntity::new, TINKER_TABLE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<LuxCapacitorBlockEntity>> LUX_CAP_BLOCK_ENTITY_TYPE = BLOCKENTITY_TYPES.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            () -> BlockEntityType.Builder.of(LuxCapacitorBlockEntity::new, LUX_CAPACITOR_BLOCK.get()).build(null));
}
