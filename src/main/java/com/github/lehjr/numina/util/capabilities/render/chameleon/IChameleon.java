package com.github.lehjr.numina.util.capabilities.render.chameleon;

import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.BlockNamePacket;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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
