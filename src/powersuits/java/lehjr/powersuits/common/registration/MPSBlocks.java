package lehjr.powersuits.common.registration;

import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.block.TinkerTable;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import lehjr.powersuits.common.blockentity.TinkerTableBlockEntity;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MPSBlocks {
    /**
     * Blocks -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> MPS_BLOCKS = DeferredRegister.createBlocks(MPSConstants.MOD_ID);

    public static final DeferredHolder<Block, TinkerTable> TINKER_TABLE_BLOCK = MPS_BLOCKS.register(MPSConstants.TINKER_TABLE.getPath(), TinkerTable::new);

    public static final DeferredHolder<Block, LuxCapacitorBlock> LUX_CAPACITOR_BLOCK = MPS_BLOCKS.register(MPSConstants.LUX_CAPACITOR.getPath(), LuxCapacitorBlock::new);

    /**
     * Block Entity Types -------------------------------------------------------------------------
     */
    public static final DeferredRegister<BlockEntityType<?>> MPS_BLOCKENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MPSConstants.MOD_ID);

    public static final Supplier<BlockEntityType<TinkerTableBlockEntity>> TINKER_TABLE_BLOCKENTITY_TYPE = MPS_BLOCKENTITY_TYPES.register(MPSConstants.TINKER_TABLE.getPath(),
            () -> BlockEntityType.Builder.of(TinkerTableBlockEntity::new, TINKER_TABLE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<LuxCapacitorBlockEntity>> LUX_CAP_BLOCK_ENTITY_TYPE = MPS_BLOCKENTITY_TYPES.register(MPSConstants.LUX_CAPACITOR.getPath(),
            () -> BlockEntityType.Builder.of(LuxCapacitorBlockEntity::new, LUX_CAPACITOR_BLOCK.get()).build(null));

}