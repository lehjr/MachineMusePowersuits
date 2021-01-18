package com.github.lehjr.numina.config;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModuleConfig implements IConfig {
    Optional<ModConfig> serverConfig = Optional.empty();
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

    boolean isInDevMode() {
        // fixme: find better way of doing this
        ModList.get().getModContainerById(NuminaConstants.MOD_ID).get().getModInfo().getModProperties();

        return ModList.get().getModContainerById(NuminaConstants.MOD_ID).map(container ->container.getModInfo().getOwningFile().getFileProperties().isEmpty()).orElse(false);
    }

    /**
     * FIXME: might be better with categories as keys with a value as a map of module name and ArrayList<String> of entries
     *  this way these don't get duplicate catagory entries in the output file
     */
    Map<String, Map<String, ArrayList<String>>> outputMap;
    void addtoMap(String category, String moduleName, String entry) {
        System.out.println("adding to map: " + category + ", " + moduleName + ", " + entry );

        Map<String, ArrayList<String>> modulesForCategory;
        ArrayList<String> moduleSettings;

        // check if the category is already in the map
        if (outputMap.containsKey(category)) {
            modulesForCategory = outputMap.get(category);
            if(modulesForCategory.containsKey(moduleName)) {
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

    // once the builder has been built, it cannot be changed.
    public void writeMissingConfigValues() {
        if (outputMap.isEmpty() || !isInDevMode()) {
            return;
        }

        getModConfig().ifPresent(config->System.out.println("configData for " + MOD_ID + ": " + config.getConfigData()));

        System.out.println("MODULE MAP SET SIZE: " + outputMap.size());

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

    @Override
    public double getBasePropertyDoubleOrDefault (
            EnumModuleCategory category,
            @Nonnull ItemStack module,
            String propertyName, double baseVal) {
        String moduleName = itemTranslationKeyToConfigKey(module.getTranslationKey());
        String entry = "base_" + propertyName;

        // if config is not null then look up value and add it if not present
        return getModConfig().map(config -> {
            ArrayList<String> key = new ArrayList<String>() {{
                add("Modules");
                add(category.getName());
                add(moduleName);
                add(entry);
            }};

            if (config.getConfigData().contains(key)) {
                return config.getConfigData().get(key);
            } else if (!isInDevMode()) {
                config.getConfigData().set(key, baseVal);
            }
            return baseVal;

            // Add to map then return base val
        }).orElseGet(()->{
            addtoMap(category.getName(),
                    moduleName,
                    new StringBuilder("builder.defineInRange(\"")
                            .append(entry).append("\", ")
                            .append(baseVal).append("D, ")
                            .append(0).append(", ")
                            .append(Double.MAX_VALUE)
                            .append(");\n").toString());
            isModuleAllowed(category, module); // initialize the value
            return baseVal;
        });
    }

    @Override
    public double getTradeoffPropertyDoubleOrDefault(
            EnumModuleCategory category,
            @Nonnull ItemStack module,
            String tradeoffName,
            String propertyName,
            double multiplier) {

        String moduleName = itemTranslationKeyToConfigKey(module.getTranslationKey());
        String entry = propertyName + "_" + tradeoffName + "_multiplier";

        return getModConfig().map(config-> {
            ArrayList<String> key = new ArrayList<String>() {{
                add("Modules");
                add(category.getName().replace(" ", "_"));
                add(moduleName);
                add(entry);
            }};

            if (config.getConfigData().contains(key)) {
                double val = config.getConfigData().get(key);
                System.out.println("common config value: " + config.getConfigData().get(key));
                return val;
            } else if (!isInDevMode()) {
                config.getConfigData().set(key, multiplier);
            }
            return multiplier;
        }).orElseGet(()->{
            addtoMap(category.getName(),
                    moduleName,
                    new StringBuilder("builder.defineInRange(\"")
                            .append(entry).append("\", ")
                            .append(multiplier).append("D, ")
                            .append(0).append(", ")
                            .append(Double.MAX_VALUE)
                            .append(");\n").toString());
            isModuleAllowed(category, module);
            return multiplier;
        });
    }

    @Override
    public int getTradeoffPropertyIntegerOrDefault(EnumModuleCategory category, @Nonnull ItemStack module, String tradeoffName, String propertyName, int multiplier) {
        String moduleName = itemTranslationKeyToConfigKey(module.getTranslationKey());
        String entry = propertyName + "_" + tradeoffName + "_multiplier";

        return getModConfig().map(config->{
            ArrayList<String> key = new ArrayList<String>() {{
                add("Modules");
                add(category.getName());
                add(moduleName);
                add(entry);
            }};
            if (config.getConfigData().contains(key)) {
                return config.getConfigData().get(key);
            } else if (!isInDevMode()) {
                config.getConfigData().set(key, multiplier);
            }
            return multiplier;
        }).orElseGet(()->{
            addtoMap(category.getName(),
                    moduleName,
                    new StringBuilder("builder.defineInRange(\"")
                            .append(entry).append("\", ")
                            .append(multiplier).append(", ")
                            .append(0).append(", ")
                            .append(Integer.MAX_VALUE)
                            .append(");\n").toString());
            isModuleAllowed(category, module);

            return multiplier;
        });
    }

    @Override
    public boolean isModuleAllowed(EnumModuleCategory category, @Nonnull ItemStack module) {
        String moduleName = itemTranslationKeyToConfigKey(module.getTranslationKey());
        String entry = "isAllowed";

        return getModConfig().map(config->{
            ArrayList<String> key = new ArrayList<String>() {{
                add("Modules");
                add(category.getName());
                add(moduleName);
                add(entry);
            }};

            if (config.getConfigData().contains(key)) {
                return config.getConfigData().get(key);
            } else if (!isInDevMode()) {
                config.getConfigData().set(key, true);
            }
            return true;
        }).orElseGet(()->{
            addtoMap(category.getName(), moduleName, new StringBuilder("builder.define(\"").append(entry).append("\", true);\n").toString());
            return true;
        });
    }

    String itemTranslationKeyToConfigKey(String translationKey) {
        // drop the prefix for MPA modules and replace "dots" with underscores
        final String itemPrefix = "item." + MOD_ID + ".";
        if (translationKey.startsWith(itemPrefix )){
            translationKey = translationKey.substring(itemPrefix .length());
        }
        return translationKey.replace(".", "_");
    }
}
