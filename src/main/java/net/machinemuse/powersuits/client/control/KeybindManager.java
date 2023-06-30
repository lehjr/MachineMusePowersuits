package net.machinemuse.powersuits.client.control;

import com.google.gson.*;
import net.machinemuse.numina.common.base.MuseLogger;
import net.machinemuse.numina.common.module.IPowerModule;
import net.machinemuse.numina.common.module.IToggleableModule;
import net.machinemuse.powersuits.client.event.RenderEventHandler;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum KeybindManager {
    INSTANCE;

    static final String formatVersionKey = "formatVersion";
    static final String dataName = "dataName"; // dataName is the unique identifier for modules in 1.12.2
    static final String showOnHudKey = "showOnHud";


    // FIXME: use for actual default key code or remove?
    static final String defaultKeyKey = "defaultKey"; // :-p
    static final String currentKey = "currentKey"; // FIXME: use correctly?
    static final String keyModifier = "keyModifier"; // default KeyModifier.NONE;



    /**
     * Register keybindings with default settings
     */
    public void makeKeybinds() {
        ModuleManager.INSTANCE.getModuleMap().values().stream().filter(IToggleableModule.class::isInstance).map(IToggleableModule.class::cast).forEach(iToggleableModule -> {
            registerKeyBindingFromModule(iToggleableModule);
        });
        readInKeybinds();
        writeOutKeybinds();
        RenderEventHandler.makeKBDisplayList();
    }

    /**
     * For loading older keybinding configurations
     */
    File getLegacyKeyBindConfig() {
        return new File(Loader.instance().getConfigDir() + "/machinemuse/", "powersuits-keybinds.cfg");
    }

    /**
     * For saving/loading keybind configurations
     */
    File getKeyBindConfig() {
        return new File(Loader.instance().getConfigDir() + "/machinemuse/" + "powersuits-keybinds.json");
    }

    public void writeOutKeybinds() {
        try {
            File file = getKeyBindConfig();
            if (!file.exists()) {
                Files.createDirectories(file.toPath().getParent());
                file.createNewFile();
            }
            JsonObject kbSettings = new JsonObject();
            kbSettings.addProperty(formatVersionKey, 2);
            Arrays.stream(Minecraft.getMinecraft().gameSettings.keyBindings)
                    .filter(MPSKeyBinding.class::isInstance)
                    .map(MPSKeyBinding.class::cast)
                    .forEach(keyBinding -> {
                        JsonObject jsonKBSetting = new JsonObject();
                        jsonKBSetting.addProperty(dataName, keyBinding.dataName);
                        jsonKBSetting.addProperty(showOnHudKey, keyBinding.showOnHud);
                        jsonKBSetting.addProperty(currentKey, keyBinding.getKeyCode());
                        if(keyBinding.getKeyModifier() != KeyModifier.NONE) {
                            jsonKBSetting.addProperty(keyModifier, keyBinding.getKeyModifier().toString());
                        }
                        kbSettings.add(keyBinding.getKeyDescription(), jsonKBSetting);
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

    public void readInKeybinds() {
        File file = getKeyBindConfig();
        if (!file.exists()) {
            readLegacyKeybinds();
            return;
        }

        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(file)) {
            Object object = jsonParser.parse(reader);
            if (object instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) object;
                Set<Map.Entry<String, JsonElement>> elements = jsonObject.entrySet();

                /** check for new format and load keybinds accordingly */
                if (jsonObject.has(formatVersionKey) && jsonObject.get(formatVersionKey).getAsInt() == 2) {
                    MuseLogger.logDebug("loading keybind format 2.0");
                    for (Map.Entry entry : elements) {
                        String name = (String) entry.getKey();

                        if (!(entry.getValue() instanceof JsonObject)) {
                            continue;
                        }
                        JsonObject data = ((JsonObject) entry.getValue()).getAsJsonObject();
                        KeyModifier modifier = KeyModifier.NONE;
                        boolean showOnHud = data.get(showOnHudKey).getAsBoolean();
                        int defaultKey = data.get(currentKey).getAsInt();
                        String moduleDataName = data.get(dataName).getAsString();
                        if (data.has(keyModifier)) {
                            modifier = KeyModifier.valueFromString(data.get(keyModifier).getAsString());
                        }
                        IPowerModule module = ModuleManager.INSTANCE.getModule(moduleDataName);
                        if (module != null) {
                            registerKeyBinding(module, false, showOnHud, defaultKey, modifier);
                        }
                    }

                    /** fallback if settings hasn't been converted to new format yet */
                } else {
                    MuseLogger.logDebug("loading keybind format 1.2");
                }
            }
        } catch (Exception e) {
            MuseLogger.logger.error("Problem reading in keyconfig :(");
            e.printStackTrace();
        }
    }

    public Stream<MPSKeyBinding> getMPSKeybinds() {
        return Arrays.stream(Minecraft.getMinecraft().gameSettings.keyBindings)
                .filter(MPSKeyBinding.class::isInstance)
                .map(MPSKeyBinding.class::cast);
    }

    public String parseName(KeyBinding keybind) {
        if (keybind.getKeyCode() < 0) {
            return "Mouse" + (keybind.getKeyCode() + 100);
        } else {
            return Keyboard.getKeyName(keybind.getKeyCode());
        }
    }

    public void readLegacyKeybinds() {
        try {
            File file = getLegacyKeyBindConfig();
            if (!file.exists()) {
                MuseLogger.logError("No powersuits keybind file found.");
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // every keybinding in this mess is at least 2 lines depending on how many modules are bound to each keybind
            boolean displayOnHUD = false;
            boolean toggleval = false;
            int keyCode = 0;

            // Only extracting the module data name and the KB settings. Module position is no longer used
            while (reader.ready()) {
                String line = reader.readLine();
                // first line contains contains the binding and settings
                if (line.contains(":")) {
                    String[] exploded = line.split(":");
                    keyCode = Integer.parseInt(exploded[0]);
                    if (exploded.length > 3) {
                        displayOnHUD = Boolean.parseBoolean(exploded[3]);
                    } else {
                        displayOnHUD = false;
                    }

                    if (exploded.length > 4) {
                        toggleval = Boolean.parseBoolean(exploded[4]);
                    } else {
                        toggleval = false;
                    }

                    // module dataName
                } else if (line.contains("~")) {
                    String[] exploded = line.split("~");
                    IPowerModule module = ModuleManager.INSTANCE.getModule(exploded[0]);
                    if (module != null) {
                        registerKeyBinding(module, toggleval, displayOnHUD, keyCode, KeyModifier.NONE);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            MuseLogger.logError("Problem reading in keyconfig :(");
            e.printStackTrace();
        }
    }

    public void registerKeyBindingFromModule(IPowerModule module) {
        String keybindingName = new StringBuilder(module.getUnlocalizedName()).append(".name").toString();
        registerKeyBinding(module.getDataName(), keybindingName, Keyboard.KEY_NONE, KeyModifier.NONE, "key.powersuits.category.moduleToggle", false, false);
    }

    public void registerKeyBinding(IPowerModule module, boolean toggleVal, boolean showOnHud, int keyCode, KeyModifier keyModifierIn) {
        String keybindingName = new StringBuilder(module.getUnlocalizedName()).append(".name").toString();
        registerKeyBinding(module.getDataName(), keybindingName, keyCode, keyModifierIn, "key.powersuits.category.moduleToggle", showOnHud, toggleVal);
    }

    public void registerKeyBinding(String moduleDataName, String keybindingName, int keyIn, KeyModifier keyModifierIn, String category, boolean showOnHud, boolean toggleVal) {
        List<KeyBinding> matches  = getMPSKeybinds().filter(kb -> kb.getKeyDescription().contains(keybindingName)).collect(Collectors.toList());

        // Create new key
        if (matches.isEmpty()) {
            // btw, creating a key without registering it will cause a crash when in the vanilla keybind settings GUI.
            MPSKeyBinding keybinding = new MPSKeyBinding(moduleDataName, keybindingName, keyIn, category, showOnHud);
            keybinding.toggleval = toggleVal;
            MuseLogger.logDebug("registering keybind for: " + moduleDataName);
            // register only at appropriate time
            ClientRegistry.registerKeyBinding(keybinding);
            // update existing key
        } else {
            MuseLogger.logDebug("updating keybinding for " + moduleDataName);
            matches.stream().filter(MPSKeyBinding.class::isInstance).map(MPSKeyBinding.class::cast).forEach(kb->{
                kb.showOnHud = showOnHud;
                if(kb.getKeyCode() != keyIn || kb.getKeyModifier() != keyModifierIn) {
                    if (keyModifierIn!= KeyModifier.NONE) {
                        kb.setKeyModifierAndCodeInternal(keyModifierIn, keyIn);
                    } else {
                        kb.setKeyInternal(keyIn);
                    }
                }
                kb.toggleval = toggleVal;
            });
        }
    }
}
