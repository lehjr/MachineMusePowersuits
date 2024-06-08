package lehjr.numina.common.capabilities.render.chameleon;

import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.BlockNamePacketServerBound;
import net.minecraft.core.registries.BuiltInRegistries;
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
        NuminaPackets.sendToServer(new BlockNamePacketServerBound(regName));
    }

    default void setTargetBlock(Block block) {
        setTargetBlockByRegName(BuiltInRegistries.BLOCK.getKey(block));
    }
}