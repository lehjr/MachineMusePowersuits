package com.lehjr.numina.api.capabilities.render.chameleon;

import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.network.packets.BlockNamePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Just a module that renders different based on the block it's set to break
 */
public interface IChameleon {

    Optional<ResourceLocation> getTargetBlockRegName();

    Optional<Block> getTargetBlock();

    @Nonnull
    ItemStack getStackToRender();

    default void setTargetBlockByRegName(ResourceLocation regName) {
        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new BlockNamePacket(regName));
    }

    default void setTargetBlock(Block block) {
        setTargetBlockByRegName(block.getRegistryName());
    }
}
