package lehjr.numina.common.registration;

import lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static lehjr.numina.common.registration.NuminaBlocks.CHARGING_BASE_BLOCK;

public class NuminaBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NuminaConstants.MOD_ID);

    public static final RegistryObject<BlockEntityType<ChargingBaseBlockEntity>> CHARGING_BASE_BLOCK_ENTITY = TILE_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> BlockEntityType.Builder.of(ChargingBaseBlockEntity::new, CHARGING_BASE_BLOCK.get()).build(null));
}
