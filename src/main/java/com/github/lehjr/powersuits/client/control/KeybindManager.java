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

package com.github.lehjr.powersuits.client.control;

import com.github.lehjr.numina.basemod.MuseLogger;
import com.github.lehjr.numina.config.ConfigHelper;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.powersuits.client.gui.clickable.ClickableKeybinding;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * For setting up the keybindings used in the onscreen display
 */
public enum KeybindManager {
    INSTANCE;

    public static final String mps = "itemGroup.powersuits";
    public static final KeyBinding goDownKey = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.goDownKey").getKey(), GLFW.GLFW_KEY_Z, mps);
    public static final KeyBinding cycleToolBackward = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.cycleToolBackward").getKey(), GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyBinding cycleToolForward = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.cycleToolForward").getKey(), GLFW.GLFW_KEY_UNKNOWN, mps);

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

    void RegisterKeybinding(ResourceLocation registryName) {
        MPSKeyBinding kb = new MPSKeyBinding(registryName, "keybinding.powersuits." + registryName.getPath(), GLFW.GLFW_KEY_UNKNOWN, mps);
        ClientRegistry.registerKeyBinding(kb);
    }

    /**
     * Populates the KB map
     */
    public void RegKeyBindings() {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.getRegistryName().getNamespace().contains(MPSConstants.MOD_ID)) {
                new ItemStack(item).getCapability(PowerModuleCapability.POWER_MODULE)
                        .filter(IToggleableModule.class::isInstance)
                        .map(IToggleableModule.class::cast)
                        .ifPresent(pm -> {
                            // Tool settings are a bit odd
                            if (pm.getTarget() == EnumModuleTarget.TOOLONLY) {
                                if (pm.getCategory() == EnumModuleCategory.MINING_ENHANCEMENT) {
                                    RegisterKeybinding(item.getRegistryName());
                                } else if (!IRightClickModule.class.isAssignableFrom(pm.getClass())) {
                                    RegisterKeybinding(item.getRegistryName());
                                }
                            } else {
                                RegisterKeybinding(item.getRegistryName());
                            }
                        });
            }
        }
    }


    public void writeOutKeybindSetings() {
        try {
            File file = getKeyBindConfig();
            if (!file.exists()) {
                Files.createDirectories(file.toPath().getParent());
                file.createNewFile();
            }
            JsonObject kbSettings = new JsonObject();
            Arrays.stream(Minecraft.getInstance().options.keyMappings)
                    .filter(MPSKeyBinding.class::isInstance)
                    .map(MPSKeyBinding.class::cast)
                    .forEach(keyBinding -> {
                        System.out.println("keyBinding.getName(): " + keyBinding.getName());

                        kbSettings.addProperty(keyBinding.getName(), keyBinding.showOnHud);
                    });
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(kbSettings.toString());
            String prettyJsonString = gson.toJson(je);
            fileWriter(file, prettyJsonString, true);
        } catch (Exception e) {
            MuseLogger.logger.error("Problem writing out keyconfig :(");
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

    public List<MPSKeyBinding> getMPSKeyBinds() {
        return Arrays.stream(Minecraft.getInstance().options.keyMappings).filter(MPSKeyBinding.class::isInstance).map(MPSKeyBinding.class::cast).collect(Collectors.toList());
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
                for (Map.Entry entry : elements) {
                    String name = (String) entry.getKey();
                    boolean value = jsonObject.get(name).getAsBoolean();
                    getMPSKeyBinds().stream().filter(kb->kb.getName().equals(name)).findFirst().ifPresent(kb->kb.showOnHud = value);
                }
            }
        } catch (Exception e) {
            MuseLogger.logger.error("Problem reading in keyconfig :(");
            e.printStackTrace();
        }
    }

    public void readLegacyKeybinds() {
        System.out.println("loading legacy keybinds");

        try {
            File file = getLegacyKeyBindConfig();
            if (!file.exists()) {
                MuseLogger.logger.error("No modular power armor keybind file found.");
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            boolean displayOnHUD = false;
            boolean toggleval = false;
            InputMappings.Input id = null;

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
                    InputMappings.Input finalId = id;
                    boolean finalToggleval = toggleval;
                    getMPSKeyBinds().stream().filter(kb ->kb.registryName.equals(regName)).findFirst().ifPresent(kb -> {
                                kb.showOnHud = finalDisplayOnHUD;
                                if (finalId != null) {
                                    kb.setKey(finalId);
                                }
                                kb.toggleval = finalToggleval; // Not saved or loaded in new system
                            });
                }
            }
            reader.close();
        } catch (Exception e) {
            MuseLogger.logger.error("Problem reading in keyconfig :(");
            e.printStackTrace();
        }
    }

    public InputMappings.Input getInputByCode(int keyCode) {
        return InputMappings.Type.KEYSYM.getOrCreate(keyCode);
    }
}