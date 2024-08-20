package lehjr.mpsrecipecreator.network;

import com.lehjr.mpsrecipecreator.basemod.MPSRCConstants;
import com.lehjr.mpsrecipecreator.network.packets.RecipeWriterPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL_INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(MPSRCConstants.MOD_ID, "data"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int i =0;

        CHANNEL_INSTANCE.registerMessage(
                i++,
                RecipeWriterPacket.class,
                RecipeWriterPacket::encode,
                RecipeWriterPacket::decode,
                RecipeWriterPacket::handle);
    }

    public SimpleChannel getWrapper() {
        return CHANNEL_INSTANCE;
    }
}
