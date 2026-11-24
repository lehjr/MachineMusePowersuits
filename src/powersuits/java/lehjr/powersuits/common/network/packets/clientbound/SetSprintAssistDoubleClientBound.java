package lehjr.powersuits.common.network.packets.clientbound;

import lehjr.powersuits.common.network.packets.clienthandlers.SetSprintAssistDoubleClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SetSprintAssistDoubleClientBound(double boost) {
    public static void write(SetSprintAssistDoubleClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeDouble(msg.boost);
    }

    public static SetSprintAssistDoubleClientBound read(FriendlyByteBuf packetBuffer) {
        return new SetSprintAssistDoubleClientBound(packetBuffer.readDouble());
    }

    public static void handle(SetSprintAssistDoubleClientBound data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                SetSprintAssistDoubleClientHandler.handlePacket(data, ctx);
            }
        });
    }
}
