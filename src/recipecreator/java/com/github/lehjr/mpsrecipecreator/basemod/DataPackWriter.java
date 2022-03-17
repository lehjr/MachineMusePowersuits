package com.github.lehjr.mpsrecipecreator.basemod;

import com.github.lehjr.mpsrecipecreator.network.NetHandler;
import com.github.lehjr.mpsrecipecreator.network.packets.RecipeWriterPacket;
import com.google.gson.*;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * write a data pack to the current world
 */
public enum DataPackWriter {
    INSTANCE;

    public Path mainDataPackDir;
    public File packMetaFile;
    public Path actualTargetDir;

    public void setDataFolder(String targetFolder) {
        // main datapack folder for these
        mainDataPackDir = Paths.get(targetFolder, "datapacks", "recipe_testing");
        packMetaFile = new File(mainDataPackDir.toAbsolutePath().toString(), "pack.mcmeta");
        actualTargetDir = Paths.get(mainDataPackDir.toAbsolutePath().toString(), "data", "rt", "recipes");
    }

    public static void writeRecipe(String recipe, String fileName) {
        NetHandler.CHANNEL_INSTANCE.sendToServer(new RecipeWriterPacket(recipe, fileName));
    }

    public void fileWriter(File file, String string, boolean overwrite) {
        try {
            Files.createDirectories(file.toPath().getParent());
            if (overwrite || !file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(string);
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPackMCMeta() {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        JsonParser jp = new JsonParser();

        JsonObject data = new JsonObject();
        data.addProperty("pack_format", 5);
        data.addProperty("description", "Recipes for testing");
        JsonObject packMeta = new JsonObject();
        packMeta.add("pack", data);

        JsonElement je = jp.parse(packMeta.toString());
        return gson.toJson(je);
    }
}
