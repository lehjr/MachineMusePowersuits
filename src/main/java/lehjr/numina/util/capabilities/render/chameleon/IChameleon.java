package lehjr.numina.util.capabilities.render.chameleon;

import lehjr.numina.network.NuminaPackets;
import lehjr.numina.network.packets.BlockNamePacket;
import lehjr.numina.util.nbt.MuseNBTUtils;
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
    default ItemStack getStackToRender() {
        return getTargetBlock().map(block -> new ItemStack(block.asItem())).orElse(getModule());
    }

    @Nonnull
    ItemStack getModule();

    default void setTargetBlockByRegName(ResourceLocation regName) {
        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new BlockNamePacket(regName));
        MuseNBTUtils.setModuleResourceLocation(getModule(), Chameleon.BLOCK, regName);
    }

    default void setTargetBlock(Block block) {
        setTargetBlockByRegName(block.getRegistryName());
    }
}
