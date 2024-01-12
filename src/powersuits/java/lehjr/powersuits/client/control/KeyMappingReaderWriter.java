/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.client.control;

import com.google.gson.*;
import com.mojang.blaze3d.platform.InputConstants;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.config.ConfigHelper;
import lehjr.numina.common.item.ItemUtils;
import lehjr.powersuits.client.gui.overlay.MPSOverlay;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * For setting up the keybindings used in the onscreen display
 */
public enum KeyMappingReaderWriter {
    INSTANCE;
    static final String formatVersionKey = "formatVersion";
    static final String registryNameKey = "registryName";
    static final String showOnHudKey = "showOnHud";
    static final String defaultKeyKey = "defaultKey"; // :-p

    /**
     * For loading older keybinding configurations
     */
    File getLegacyKeyBindConfig() {
        return new File(ConfigHelper.setupConfigFile("powersuits-keybinds.cfg", MPSConstants.MOD_ID).getAbsolutePath());
    }

    /**
     * For saving/loading keybind configurations
     */
    File getKeyBindConfig() {
        return new File(ConfigHelper.setupConfigFile("powersuits-keybinds.json", MPSConstants.MOD_ID).getAbsolutePath());
    }

    public void writeOutKeybindSetings() {
        try {
            File file = getKeyBindConfig();
            if (!file.exists()) {
                Files.createDirectories(file.toPath().getParent());
                file.createNewFile();
            }
            JsonObject kbSettings = new JsonObject();
            kbSettings.addProperty(formatVersionKey, 2);
            Arrays.stream(Minecraft.getInstance().options.keyMappings)
                    .filter(MPSKeyMapping.class::isInstance)
                    .map(MPSKeyMapping.class::cast)
                    .forEach(keyBinding -> {
                        JsonObject jsonKBSetting = new JsonObject();
                        jsonKBSetting.addProperty(registryNameKey, keyBinding.registryName.toString());
                        jsonKBSetting.addProperty(showOnHudKey, keyBinding.showOnHud);
                        jsonKBSetting.addProperty(defaultKeyKey, keyBinding.getKey().getValue());
                        kbSettings.add(keyBinding.getName(), jsonKBSetting);
                    });
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(kbSettings.toString());
            String prettyJsonString = gson.toJson(je);
            fileWriter(file, prettyJsonString, true);
        } catch (Exception e) {
            NuminaLogger.logger.error("Problem writing out keyconfig :(");
            e.printStackTrace();
        }
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

    public Collection<MPSKeyMapping> getMPSKeyBinds() {
        return KeymappingKeyHandler.keyMappings.values();
    }

    public void readInKeybinds() {
        File file = getKeyBindConfig();
        if (!file.exists()) {
            readLegacyKeybinds();
            return;
        }

        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(getKeyBindConfig())) {
            Object object = jsonParser.parse(reader);
            if (object instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) object;
                Set<Map.Entry<String, JsonElement>> elements = jsonObject.entrySet();

                /** check for new format and load keybinds accordingly */
                if (jsonObject.has(formatVersionKey) && jsonObject.get(formatVersionKey).getAsInt() == 2) {
                    List<String> keybindNames = Arrays.stream(Minecraft.getInstance().options.keyMappings).map(keyBinding -> keyBinding.getName()).collect(Collectors.toList());

                    NuminaLogger.logDebug("loading keybind format 2.0");

                    for (Map.Entry entry : elements) {
                        String name = (String) entry.getKey();

                        if(!(entry.getValue() instanceof JsonObject)) {
                            continue;
                        }
                        JsonObject data = ((JsonObject) entry.getValue()).getAsJsonObject();
                        boolean showOnHud = data.get(showOnHudKey).getAsBoolean();
                        int defaultKey = data.get(defaultKeyKey).getAsInt();
                        ResourceLocation registryName = new ResourceLocation(data.get(registryNameKey).getAsString());
                        KeymappingKeyHandler.registerKeyBinding(registryName, name, defaultKey, MPSConstants.MPS_ITEM_GROUP, showOnHud, true);
                    }

                    /** fallback if settings hasn't been converted to new format yet */
                } else {
                    NuminaLogger.logDebug("loading keybind format 1.2");
                    for (Map.Entry entry : elements) {
                        String name = ((String) entry.getKey());
                        boolean value = jsonObject.get(name).getAsBoolean();
                        String name1 = name
                                .replace("keybinding.powersuits.clock", "keybinding.minecraft.clock")
                                .replace("keybinding.powersuits.compass", "keybinding.minecraft.compass");
                        KeymappingKeyHandler.registerKeyBinding(ItemUtils.getRegistryName(Items.AIR), name1, GLFW.GLFW_KEY_UNKNOWN, MPSConstants.MPS_ITEM_GROUP, value, true);

                    }
                }
            }
        } catch (Exception e) {
            NuminaLogger.logger.error("Problem reading in keyconfig :(");
            e.printStackTrace();
        }
        MPSOverlay.makeKBDisplayList();
//        RenderEventHandler.INSTANCE.makeKBDisplayList();
    }

    public void readLegacyKeybinds() {
        try {
            File file = getLegacyKeyBindConfig();
            if (!file.exists()) {
                NuminaLogger.logger.error("No modular power armor keybind file found.");
                writeOutKeybindSetings();
                return;
            }
            writeOutKeybindSetings();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            boolean displayOnHUD = false;
            boolean toggleval = false;
            InputConstants.Key id = null;

            while (reader.ready()) {
                String line = reader.readLine();
                /** get keybinding settings */
                // This is supposed to have the keybinding in one line followed by one or more lines of bound modules

                if (line.contains(":")) {
                    String[] exploded = line.split(":");
                    if (id == null) {
                        id = getInputByCode(Integer.parseInt(exploded[0]));

                        displayOnHUD = false;
                        if (exploded.length > 3) {
                            displayOnHUD = Boolean.parseBoolean(exploded[3]);
                        }

                        toggleval = false;
                        if (exploded.length > 4) {
                            toggleval = Boolean.parseBoolean(exploded[4]);
                        }
                    } else {
                        id = null;
                    }

                    /** bind modules to it */
                } else if (line.contains("~") && id != null) {
                    String[] exploded = line.split("~");
                    ResourceLocation regName = new ResourceLocation(MPSConstants.MOD_ID, exploded[0]);
                    boolean finalDisplayOnHUD = displayOnHUD;
                    InputConstants.Key finalId = id;
                    boolean finalToggleval = toggleval;
                    getMPSKeyBinds().stream().filter(kb ->kb.registryName.equals(regName)).findFirst().ifPresent(kb -> {
                        kb.showOnHud = finalDisplayOnHUD;
                        if (finalId != null) {
                            kb.setKeyInternal(finalId);
                        }
                        kb.toggleVal = finalToggleval; // Not saved or loaded in new system
                    });
                }
            }
            reader.close();
        } catch (Exception e) {
            NuminaLogger.logger.error("Problem reading in keyconfig :(");
            e.printStackTrace();
        }
    }

    public InputConstants.Key getInputByCode(int keyCode) {
        return InputConstants.Type.KEYSYM.getOrCreate(keyCode);
    }
}