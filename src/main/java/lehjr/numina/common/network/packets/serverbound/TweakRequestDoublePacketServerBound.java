package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.TweakRequestDoublePacketClientBound;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record TweakRequestDoublePacketServerBound(EquipmentSlot slotType, ResourceLocation moduleRegName, String tweakName, double tweakValue) implements CustomPacketPayload {
    public static final Type<TweakRequestDoublePacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "tweak_request_to_server"));

    @Override
    @NotNull
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

    public static void sendToClient(ServerPlayer entity, EquipmentSlot type, ResourceLocation moduleRegName, String tweakName, double tweakValue) {
        NuminaPackets.sendToPlayer(new TweakRequestDoublePacketClientBound(type, moduleRegName, tweakName, tweakValue), entity);
    }

    public static void handle(TweakRequestDoublePacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (data.moduleRegName != null && data.tweakName != null) {
                ItemStack stack = ItemUtils.getItemFromEntitySlot(player, data.slotType);

                NuminaCapabilities.getModularItemOrModeChangingCapability(stack)
                        .ifPresent(iItemHandler -> {
                            NuminaLogger.logDebug("setting module tweak name: " + data.tweakName + ", tweak value: " + data.tweakValue);
                            iItemHandler.setModuleTweakDouble(data.moduleRegName, data.tweakName, data.tweakValue);

                            ItemStack module = ItemStack.EMPTY;
                            for (int i = 0; i < iItemHandler.getSlots(); i++) {
                                ItemStack testStack = iItemHandler.getStackInSlot(i);
                                if (ItemUtils.getRegistryName(testStack).equals(data.moduleRegName)) {
                                    module = testStack;
                                    NuminaLogger.logDebug("found: " + module);

                                    break;
                                }
                            }

                            NuminaCapabilities.getPowerModuleCapability(module).ifPresent(pm->System.out.println("checking tweak value: " + pm.getModuleTag().get(data.tweakName)));
                        });

                sendToClient((ServerPlayer) player, data.slotType, data.moduleRegName, data.tweakName, data.tweakValue);
            }
        });
    }
}