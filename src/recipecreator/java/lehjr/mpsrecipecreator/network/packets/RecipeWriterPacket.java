package lehjr.mpsrecipecreator.network.packets;

import lehjr.mpsrecipecreator.basemod.DataPackWriter;
import lehjr.mpsrecipecreator.basemod.config.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class RecipeWriterPacket {
    private final String fileName;
    private final String recipe;

    public RecipeWriterPacket(String recipe, String fileName) {
        this.recipe = recipe;
        this.fileName = fileName;

        System.out.println("filename: " + fileName);
    }

    public static void encode(RecipeWriterPacket msg, FriendlyByteBuf packetBuffer) {
        int recipeSize = msg.recipe.length() + 32;
        packetBuffer.writeInt(recipeSize);
        packetBuffer.writeUtf(msg.recipe, recipeSize);

        packetBuffer.writeInt(msg.fileName.length() + 32);
        packetBuffer.writeUtf(msg.fileName);
    }

    public static RecipeWriterPacket decode(FriendlyByteBuf packetBuffer) {
        return new RecipeWriterPacket(
                packetBuffer.readUtf(packetBuffer.readInt()),
                packetBuffer.readUtf(packetBuffer.readInt()));
    }

    public static void handle(RecipeWriterPacket message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayer player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            MinecraftServer server = player.getServer();

            URI gameFolderURI = server.getServerDirectory().toURI().normalize();

            Path datapackDir = null;

            // single player and player obviously owns the server
            if (server.isSingleplayer()) {
                //FIXME: game folder name would be better/more reliable
                datapackDir = Paths.get(gameFolderURI)
                        .resolve("saves")
                        .resolve(server.getWorldData().getLevelName())
                        .toAbsolutePath();

                // multiplayer and player owns server or has permission to create recipes
            } else if (server.isSingleplayerOwner(player.getGameProfile()) ||
                    (Config.allowOppedPlayersToCreateOnServer() &&
                            server.getProfilePermissions(player.getGameProfile()) >= Config.getOpLevelNeeded())) {

                if (server.isDedicatedServer()) {
                    // Fixme?
                    datapackDir = Paths.get(gameFolderURI)
                            .resolve(server.getWorldData().getLevelName()).toAbsolutePath();
                    System.out.println("dedicated server detected with datapackDir at: " + datapackDir.toString());
                } else {
                    // Fixme?
                    datapackDir = Paths.get(gameFolderURI)
                            .resolve("saves")
                            .resolve(server.getWorldData().getLevelName())
                            .toAbsolutePath();
                    System.out.println("multiplayer without dedicated server detected with datapackDir at: " + datapackDir.toString());
                }
            } else {
                if (Config.allowOppedPlayersToCreateOnServer()) {
                    player.sendSystemMessage(Component.literal("You do not have permission to create recipes :P" +
                            "\nRequiredLevel: " + Config.getOpLevelNeeded() +
                            "\nYourLevel: " + server.getProfilePermissions(player.getGameProfile())));
                } else {
                    player.sendSystemMessage(Component.literal("Serve admin has disabled creating recipes on this server :P"));
                }
            }

            if(datapackDir != null && !message.recipe.isEmpty() && !message.fileName.isEmpty()) {

                System.out.println("filename: " + message.fileName);

                DataPackWriter.INSTANCE.setDataFolder(datapackDir.toString());

                System.out.println(DataPackWriter.INSTANCE.packMetaFile.getAbsolutePath());

                if (!DataPackWriter.INSTANCE.packMetaFile.exists()) {
                    DataPackWriter.INSTANCE.fileWriter(DataPackWriter.INSTANCE.packMetaFile, DataPackWriter.INSTANCE.getPackMCMeta(), false);
                }

                File recipeFile = new File(DataPackWriter.INSTANCE.actualTargetDir.toAbsolutePath().toString(), message.fileName);
                DataPackWriter.INSTANCE.fileWriter(recipeFile, message.recipe, Config.overwriteRecipes());

                if (recipeFile.exists()) {
                    CommandSourceStack commandsourcestack = player.createCommandSourceStack();
                    if(commandsourcestack.hasPermission(2)) {
                        player.sendSystemMessage(Component.literal("Server reloading data :P"));
                        server.getCommands().performPrefixedCommand(player.createCommandSourceStack(), "reload");
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
