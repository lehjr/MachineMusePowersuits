package com.lehjr.mpsrecipecreator.network.packets;

import com.lehjr.mpsrecipecreator.basemod.DataPackWriter;
import com.lehjr.mpsrecipecreator.basemod.config.Config;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

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

    public static void encode(RecipeWriterPacket msg, PacketBuffer packetBuffer) {
        int recipeSize = msg.recipe.length() + 32;
        packetBuffer.writeInt(recipeSize);
        packetBuffer.writeUtf(msg.recipe, recipeSize);

        packetBuffer.writeInt(msg.fileName.length() + 32);
        packetBuffer.writeUtf(msg.fileName);
    }

    public static RecipeWriterPacket decode(PacketBuffer packetBuffer) {
        return new RecipeWriterPacket(
                packetBuffer.readUtf(packetBuffer.readInt()),
                packetBuffer.readUtf(packetBuffer.readInt()));
    }

    public static void handle(RecipeWriterPacket message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity player = ctx.get().getSender();
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
                    player.sendMessage(new StringTextComponent("You do not have permission to create recipes :P" +
                            "\nRequiredLevel: " + Config.getOpLevelNeeded() +
                            "\nYourLevel: " + server.getProfilePermissions(player.getGameProfile())), player.getUUID());
                } else {
                    player.sendMessage(new StringTextComponent("Serve admin has disabled creating recipes on this server :P"), player.getUUID());
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
                    player.sendMessage(new StringTextComponent("Server reloading data :P"), player.getUUID());
                    server.getCommands().performCommand(player.createCommandSourceStack(), "reload");
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
