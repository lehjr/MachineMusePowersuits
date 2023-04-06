//package lehjr.mpsrecipecreator.network.packets;
//
//import lehjr.mpsrecipecreator.basemod.DataPackWriter;
//import lehjr.mpsrecipecreator.basemod.config.Config;
//import net.minecraft.entity.player.ServerPlayer;
//import net.minecraft.network.FriendlyByteBuf;
//
//import net.minecraft.server.MinecraftServer;
//import net.minecraftforge.fml.network.NetworkEvent;
//
//import java.io.File;
//import java.net.URI;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.function.Supplier;
//
//public class RecipeWriterPacket {
//    private final String fileName;
//    private final String recipe;
//
//    public RecipeWriterPacket(String recipe, String fileName) {
//        this.recipe = recipe;
//        this.fileName = fileName;
//
//        System.out.println("filename: " + fileName);
//    }
//
//    public static void encode(RecipeWriterPacket msg, FriendlyByteBuf packetBuffer) {
//        int recipeSize = msg.recipe.length() + 32;
//        packetBuffer.writeInt(recipeSize);
//        packetBuffer.func_211400_a(msg.recipe, recipeSize);
//
//        packetBuffer.writeInt(msg.fileName.length() + 32);
//        packetBuffer.func_180714_a(msg.fileName);
//    }
//
//    public static RecipeWriterPacket decode(FriendlyByteBuf packetBuffer) {
//        return new RecipeWriterPacket(
//                packetBuffer.func_150789_c(packetBuffer.readInt()),
//                packetBuffer.func_150789_c(packetBuffer.readInt()));
//    }
//
//    public static void handle(RecipeWriterPacket message, Supplier<NetworkEvent.Context> ctx) {
//        final ServerPlayer player = ctx.get().getSender();
//        ctx.get().enqueueWork(() -> {
//            MinecraftServer server = player.getServer();
//
//            URI gameFolderURI = server.func_71238_n().toURI().normalize();
//
//            Path datapackDir = null;
//
//            // single player and player obviously owns the server
//            if (server.isSinglePlayer()) {
//                //FIXME: game folder name would be better/more reliable
//                datapackDir = Paths.get(gameFolderURI)
//                        .resolve("saves")
//                        .resolve(server.func_240793_aU_().func_76065_j())
//                        .toAbsolutePath();
//
//                // multiplayer and player owns server or has permission to create recipes
//            } else if (server.isServerOwner(player.getGameProfile()) ||
//                    (Config.allowOppedPlayersToCreateOnServer() &&
//                            server.func_211833_a(player.getGameProfile()) >= Config.getOpLevelNeeded())) {
//
//                if (server.func_71262_S()) {
//                    // Fixme?
//                    datapackDir = Paths.get(gameFolderURI)
//                            .resolve(server.func_240793_aU_().func_76065_j()).toAbsolutePath();
//                    System.out.println("dedicated server detected with datapackDir at: " + datapackDir.toString());
//                } else {
//                    // Fixme?
//                    datapackDir = Paths.get(gameFolderURI)
//                            .resolve("saves")
//                            .resolve(server.func_240793_aU_().func_76065_j())
//                            .toAbsolutePath();
//                    System.out.println("multiplayer without dedicated server detected with datapackDir at: " + datapackDir.toString());
//                }
//            } else {
//                if (Config.allowOppedPlayersToCreateOnServer()) {
//                    player.func_145747_a(Component.literal("You do not have permission to create recipes :P" +
//                            "\nRequiredLevel: " + Config.getOpLevelNeeded() +
//                            "\nYourLevel: " + server.func_211833_a(player.getGameProfile())), player.getUUID());
//                } else {
//                    player.func_145747_a(Component.literal("Serve admin has disabled creating recipes on this server :P"), player.getUUID());
//                }
//            }
//
//            if(datapackDir != null && !message.recipe.isEmpty() && !message.fileName.isEmpty()) {
//
//                System.out.println("filename: " + message.fileName);
//
//
//                DataPackWriter.INSTANCE.setDataFolder(datapackDir.toString());
//
//                System.out.println(DataPackWriter.INSTANCE.packMetaFile.getAbsolutePath());
//
//                if (!DataPackWriter.INSTANCE.packMetaFile.exists()) {
//                    DataPackWriter.INSTANCE.fileWriter(DataPackWriter.INSTANCE.packMetaFile, DataPackWriter.INSTANCE.getPackMCMeta(), false);
//                }
//
//                File recipeFile = new File(DataPackWriter.INSTANCE.actualTargetDir.toAbsolutePath().toString(), message.fileName);
//                DataPackWriter.INSTANCE.fileWriter(recipeFile, message.recipe, Config.overwriteRecipes());
//
//                if (recipeFile.exists()) {
//                    player.func_145747_a(Component.literal("Server reloading data :P"), player.getUUID());
//                    server.func_195571_aL().func_197059_a(player.func_195051_bN(), "reload");
//                }
//            }
//        });
//        ctx.get().setPacketHandled(true);
//    }
//}
