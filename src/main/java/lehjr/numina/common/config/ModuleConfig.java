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

package lehjr.numina.common.config;

import com.google.common.collect.ImmutableList;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Allows a semi-dynamic config reference, also generates config entries during development
 */
public class ModuleConfig implements IConfig {
    Optional<ModConfig> serverConfig = Optional.empty();
    Map<String, Map<String, ArrayList<String>>> outputMap;
    final String MOD_ID;

    public ModuleConfig(String mod_ID) {
        outputMap = new HashMap<>();
        this.MOD_ID = mod_ID;
    }

    @Override
    public void setServerConfig(@Nullable ModConfig serverConfig) {
        this.serverConfig = Optional.ofNullable(serverConfig);
    }

    @Override
    public Optional<ModConfig> getModConfig() {
        return serverConfig;
    }

    final boolean generateNewConfigValues = false;


    boolean isInDevMode() {
        // fixme: find better way of doing this
//        ModList.get().getModContainerById(NuminaConstants.MOD_ID).get().getModInfo().getModProperties();

        return ModList.get().getModContainerById(NuminaConstants.MOD_ID).map(container ->container.getModInfo().getOwningFile().getFileProperties().isEmpty()).orElse(false);
    }

    /**
     * FIXME: might be better with categories as keys with a value as a map of module name and ArrayList<String> of entries
     *  this way these don't get duplicate catagory entries in the output file
     */

    void addtoMap(String category, String moduleName, String entry) {
        // values may not be missing if config is not yet present (such as during startup)
        if (getModConfig().isPresent()) {
            NuminaLogger.logDebug("adding to map: " + category + ", " + moduleName + ", " + entry);

            Map<String, ArrayList<String>> modulesForCategory;
            ArrayList<String> moduleSettings;

            // check if the category is already in the map
            if (outputMap.containsKey(category)) {
                modulesForCategory = outputMap.get(category);
                if (modulesForCategory.containsKey(moduleName)) {
                    moduleSettings = modulesForCategory.get(moduleName);
                    if (moduleSettings.contains(entry)) {
                        return;
                    }
                } else {
                    moduleSettings = new ArrayList<>();
                }
            } else {
                modulesForCategory = new HashMap<>();
                moduleSettings = new ArrayList<>();
            }

            moduleSettings.add(entry);
            modulesForCategory.put(moduleName, moduleSettings);

            outputMap.put(category, modulesForCategory);
        }
    }

    // once the builder has been built, it cannot be changed.
    public void writeMissingConfigValues() {
        if (outputMap.isEmpty() || !isInDevMode()) {
            return;
        }

        getModConfig().ifPresent(config-> NuminaLogger.logDebug("configData for " + MOD_ID + ": " + config.getConfigData()));

        NuminaLogger.logDebug("MODULE MAP SET SIZE: " + outputMap.size());

        StringBuilder outString = new StringBuilder("builder.push(\"Modules\");\n");

        for (Map.Entry<String, Map<String, ArrayList<String>>> categoryMapEntry : outputMap.entrySet()) {
            String moduleCategory = categoryMapEntry.getKey();
            outString.append("builder.push(\"").append(moduleCategory).append("\");\n");

            Map<String, ArrayList<String>> moduleMapEntry = categoryMapEntry.getValue();

            for (Map.Entry<String, ArrayList<String>> entry: moduleMapEntry.entrySet()) {
                String moduleName = entry.getKey();
                ArrayList<String> moduleSettings = entry.getValue();

                outString.append("builder.push(\"").append(moduleName).append("\");\n");
                for (String moduleLine : moduleSettings) {
                    outString.append(moduleLine);
                }
                outString.append("builder.pop();\n");
            }
            outString.append("builder.pop();\n");

        }
        outString.append("builder.pop();\n");
        try {
            FileUtils.writeStringToFile(ConfigHelper.setupConfigFile("missingConfigs.txt", MOD_ID), outString.toString(), Charset.defaultCharset(), false);
        } catch (Exception e) {

        }
    }

    /**
     * @param configKey used to lookup config value
     *     ImmutableList.of(
     *     "Modules", // modules section of config
     *     categoryTitle, // modules matching category (formatted without whitespace)
     *     moduleName, // unique key derived from descriptionID
     *     "base_" + propertyName; // specific config setting
     * @param baseVal
     * @return
     */
    @Override
    public double getBasePropertyDoubleOrDefault(ImmutableList<String> configKey, double baseVal) {
        // Add to map
        if (isInDevMode() && generateNewConfigValues) {
            addtoMap(configKey.get(1), // categoryTitle
                    configKey.get(2), // moduleName,
                    new StringBuilder("builder.defineInRange(\"")
                            .append(configKey.get(3)).append("\", ")
                            .append(baseVal).append("D, ")
                            .append(0).append(", ")
                            .append(Double.MAX_VALUE)
                            .append(");\n").toString());

            isModuleAllowed(getIsAllowed(configKey));// initialize the value
        }

        // if config is not null then look up value and add it if not present
        return (double) getModConfig().map(config -> {
            // config data is null when logging out and back in?
            if (config.getConfigData() != null) {
                Object object = config.getConfigData().getOrElse(configKey, baseVal);
                if (object != null && object instanceof Integer) {
                    int i = (int) object;
                    return Double.valueOf(i);
                } else if (object instanceof Double) {
                    return object;
                }
                return baseVal;
            }
            return baseVal;
        }).orElse(baseVal);
    }

    /**
     *
     * @param configKey provides a path for parsing the config
     * @param multiplier default multiplier value
     *     ImmutableList.of(
     *     "Modules", // modules section of config
     *     categoryTitle, // modules matching category (formatted without whitespace)
     *     moduleName, // unique key derived from descriptionID
     *     $entry ); // specific config setting
     * @return
     */
    @Override
    public double getTradeoffPropertyDoubleOrDefault(ImmutableList<String> configKey, double multiplier) {
        if (isInDevMode() && generateNewConfigValues) {
            addtoMap(configKey.get(1),
                    configKey.get(2),
                    new StringBuilder("builder.defineInRange(\"")
                            .append(configKey.get(3)).append("\", ")
                            .append(multiplier).append("D, ")
                            .append(0).append(", ")
                            .append(Double.MAX_VALUE)
                            .append(");\n").toString());
            isModuleAllowed(getIsAllowed(configKey));// initialize the value
        }

        return (double) getModConfig().map(config-> {
            if (config.getConfigData() != null) {
                Object object = config.getConfigData().getOrElse(configKey, multiplier);
                if (object != null && object instanceof Integer) {
                    int i = (int) object;
                    return Double.valueOf(i);
                } else if (object instanceof Double) {
                    return object;
                }
                return multiplier;
            }
            return multiplier;
        }).orElse(multiplier);
    }

    /**
     *
     * @param configKey provides a path for parsing the config
     * @param multiplier default multiplier value
     *     ImmutableList.of(
     *     "Modules", // modules section of config
     *     categoryTitle, // modules matching category (formatted without whitespace)
     *     moduleName, // unique key derived from descriptionID
     *     $entry ); // specific config setting
     * @return
     */
    @Override
    public int getTradeoffPropertyIntegerOrDefault(ImmutableList<String> configKey, int multiplier) {
        if (isInDevMode() && generateNewConfigValues) {
            addtoMap(configKey.get(1),
                    configKey.get(2),
                    new StringBuilder("builder.defineInRange(\"")
                            .append(configKey.get(3)).append("\", ")
                            .append(multiplier).append(", ")
                            .append(0).append(", ")
                            .append(Integer.MAX_VALUE)
                            .append(");\n").toString());
            isModuleAllowed(getIsAllowed(configKey));// initialize the value
        }

        return (int) getModConfig().map(config-> {
            if (config.getConfigData() != null) {
                Object object = config.getConfigData().getOrElse(configKey, multiplier);
                if (object != null && object instanceof Integer) {
                    return object;
                } else if (object instanceof Double) {
                    double i = (double) object;
                    return i;
                }
                return multiplier;
            }
            return multiplier;
        }).orElse(multiplier);
    }

    /**
     *
     * @param key provides a path for parsing the config
     *
     *     ImmutableList.of(
     *     "Modules", // modules section of config
     *     categoryTitle, // modules matching category (formatted without whitespace)
     *     moduleName, // unique key derived from descriptionID
     *     "isAllowed" ); // specific config setting
     * @return
     */
    @Override
    public boolean isModuleAllowed(ImmutableList<String> key) {
        if (isInDevMode() && generateNewConfigValues) {
            addtoMap(
                    key.get(1), // categoryTitle
                    key.get(2), // moduleName
                    new StringBuilder("builder.define(\"")
                            .append(key.get(3)) // isAllowed
                            .append("\", true);\n").toString());
        }

        return getModConfig().map(config->{
            if (config.getConfigData() != null) {
                return config.getConfigData().getOrElse(key, true);
            }
            return true;
        }).orElse(true);
    }

    ImmutableList<String> getIsAllowed(ImmutableList<String> configKey) {
        return ImmutableList.of(
                configKey.get(0),
                configKey.get(1),
                configKey.get(2),
                "isAllowed");// initialize the value
    }

    @Override
    public boolean getGenericBooleanProperty(ImmutableList<String> key) {
        if (isInDevMode() && generateNewConfigValues) {
            addtoMap(
                    key.get(1), // categoryTitle
                    key.get(2), // moduleName
                    new StringBuilder("builder.define(\"")
                            .append(key.get(3)) // isAllowed
                            .append("\", true);\n").toString());
        }

        return getModConfig().map(config->{
            if (config.getConfigData() != null) {
                return config.getConfigData().getOrElse(key, true);
            }
            return true;
        }).orElse(true);
    }
}
