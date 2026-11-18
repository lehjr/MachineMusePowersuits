package lehjr.numina.common.registration;

import lehjr.numina.common.block.ChargingBase;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NuminaBlocks {
    public static final DeferredRegister<Block> NUMINA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NuminaConstants.MOD_ID);

    public static final RegistryObject<ChargingBase> CHARGING_BASE_BLOCK = NUMINA_BLOCKS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            ChargingBase::new);

}
