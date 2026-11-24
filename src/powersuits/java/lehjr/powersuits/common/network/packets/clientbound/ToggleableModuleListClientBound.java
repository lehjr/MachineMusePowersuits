package lehjr.powersuits.common.network.packets.clientbound;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.network.packets.clienthandlers.ToggleableModuleListClientHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public record ToggleableModuleListClientBound(NonNullList<ResourceLocation> moduleNames) {


    public static ToggleableModuleListClientBound read(FriendlyByteBuf packetBuffer) {
        return new ToggleableModuleListClientBound(getModuleList(packetBuffer));
    }

    public static void write(ToggleableModuleListClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeCollection(msg.moduleNames, FriendlyByteBuf::writeResourceLocation);
    }

    static NonNullList<ResourceLocation> getModuleList(FriendlyByteBuf packetBuffer) {
        List<ResourceLocation> list = packetBuffer.readList(FriendlyByteBuf::readResourceLocation);
        NonNullList<ResourceLocation> list2 = NonNullList.create();
        list2.addAll(list);
        return list2;
    }

    public static void handle(ToggleableModuleListClientBound data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ToggleableModuleListClientHandler.handlePacket(data, ctx);
            }
        });
    }
}
