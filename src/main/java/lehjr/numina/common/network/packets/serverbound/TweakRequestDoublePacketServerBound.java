package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record TweakRequestDoublePacketServerBound(EquipmentSlot slotType, ResourceLocation moduleRegName, String tweakName, double tweakValue) implements CustomPacketPayload {
    public static final Type<TweakRequestDoublePacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "tweak_request_to_server"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, TweakRequestDoublePacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(TweakRequestDoublePacketServerBound::write, TweakRequestDoublePacketServerBound::new);

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeResourceLocation(moduleRegName);
        packetBuffer.writeUtf(tweakName);
        packetBuffer.writeDouble(tweakValue);
    }

    public TweakRequestDoublePacketServerBound(FriendlyByteBuf packetBuffer) {
        this(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readResourceLocation(),
                packetBuffer.readUtf(500),
                packetBuffer.readDouble());
    }

    public static void handle(TweakRequestDoublePacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (data.moduleRegName != null && data.tweakName != null) {
                ItemStack stack = ItemUtils.getItemFromEntitySlot(player, data.slotType);

                NuminaCapabilities.getOptionalModularItemOrModeChangingCapability(stack)
                        .ifPresent(iItemHandler -> iItemHandler.setModuleDouble(data.moduleRegName, data.tweakName, data.tweakValue));
            }
        });
    }
}