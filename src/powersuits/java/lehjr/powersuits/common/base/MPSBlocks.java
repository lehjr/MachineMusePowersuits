package lehjr.powersuits.common.base;

import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.block.TinkerTable;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import lehjr.powersuits.common.blockentity.TinkerTableBlockEntity;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MPSBlocks {
    /**
     * Blocks ------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MPSConstants.MOD_ID);

    public static final RegistryObject<TinkerTable> TINKER_TABLE_BLOCK = BLOCKS.register(MPSRegistryNames.TINKER_TABLE.getPath(), TinkerTable::new);

    public static final RegistryObject<LuxCapacitorBlock> LUX_CAPACITOR_BLOCK = BLOCKS.register(MPSRegistryNames.LUX_CAPACITOR.getPath(), LuxCapacitorBlock::new);

    /**
     * Block Entity Types -------------------------------------------------------------------------
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MPSConstants.MOD_ID);

    public static final RegistryObject<BlockEntityType<TinkerTableBlockEntity>> TINKER_TABLE_BLOCKENTITY_TYPE = BLOCKENTITY_TYPES.register(MPSRegistryNames.TINKER_TABLE.getPath(),
            () -> BlockEntityType.Builder.of(TinkerTableBlockEntity::new, TINKER_TABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<LuxCapacitorBlockEntity>> LUX_CAP_BLOCK_ENTITY_TYPE = BLOCKENTITY_TYPES.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            () -> BlockEntityType.Builder.of(LuxCapacitorBlockEntity::new, LUX_CAPACITOR_BLOCK.get()).build(null));
}
