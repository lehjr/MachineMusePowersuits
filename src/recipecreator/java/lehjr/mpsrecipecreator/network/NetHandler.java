//package lehjr.mpsrecipecreator.network;
//
//import lehjr.mpsrecipecreator.basemod.Constants;
//import lehjr.mpsrecipecreator.network.packets.RecipeWriterPacket;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.fml.network.NetworkRegistry;
//import net.minecraftforge.fml.network.simple.SimpleChannel;
//
//public class NetHandler {
//    private static final String PROTOCOL_VERSION = "1";
//    public static final SimpleChannel CHANNEL_INSTANCE = NetworkRegistry.newSimpleChannel(
//            new ResourceLocation(Constants.MOD_ID, "data"),
//            () -> PROTOCOL_VERSION,
//            PROTOCOL_VERSION::equals,
//            PROTOCOL_VERSION::equals
//    );
//
//    public static void registerMPALibPackets() {
//        int i =0;
//
//        CHANNEL_INSTANCE.registerMessage(
//                i++,
//                RecipeWriterPacket.class,
//                RecipeWriterPacket::encode,
//                RecipeWriterPacket::decode,
//                RecipeWriterPacket::handle);
//    }
//
//    public SimpleChannel getWrapper() {
//        return CHANNEL_INSTANCE;
//    }
//}
