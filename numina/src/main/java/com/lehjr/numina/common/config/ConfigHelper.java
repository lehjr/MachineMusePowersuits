package com.lehjr.numina.common.config;

import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHelper {
    public static File getConfigFolder(String modId) {
        return FMLPaths.CONFIGDIR.get().resolve("lehjr").resolve(modId).toFile();
    }

    public static File setupConfigFile(String fileName, String modId) {
        // NuminaConstants.MOD_ID
        Path configFile = FMLPaths.CONFIGDIR.get().resolve("lehjr").resolve(modId).resolve(fileName);
        File cfgFile = configFile.toFile();
        try {
            Files.createDirectories(configFile.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfgFile;
    }
}
