package lehjr.numina.common.capabilities.render.chameleon;

import lehjr.numina.common.capabilities.CapabilityUpdate;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.BlockNamePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Just a module that renders different based on the block it's set to break
 */
public interface IChameleon extends CapabilityUpdate {

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