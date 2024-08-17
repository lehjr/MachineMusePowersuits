package com.lehjr.numina.common.registration;

import com.lehjr.numina.common.block.ChargingBase;
import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NuminaBlocks {
    public static final DeferredRegister<Block> NUMINA_BLOCKS = DeferredRegister.createBlocks(NuminaConstants.MOD_ID);
    public static final DeferredHolder<Block, ChargingBase> CHARGING_BASE_BLOCK = NUMINA_BLOCKS.register(NuminaConstants.CHARGING_BASE_REGNAME, ChargingBase::new);

}
