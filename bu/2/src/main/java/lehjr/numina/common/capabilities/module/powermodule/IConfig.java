package lehjr.numina.common.capabilities.module.powermodule;

import com.google.common.collect.ImmutableList;
import net.neoforged.fml.config.ModConfig;

import javax.annotation.Nullable;
import java.util.Optional;

public interface IConfig {
    double getBasePropertyDoubleOrDefault(ImmutableList<String> configKey, double baseVal);

    double getTradeoffPropertyDoubleOrDefault(ImmutableList<String> configKey, double multiplier);

    int getTradeoffPropertyIntegerOrDefault(ImmutableList<String> configKey, int multiplier);

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
    boolean isModuleAllowed(ImmutableList<String> key);

    boolean getGenericBooleanProperty(ImmutableList<String> key);

    void setServerConfig(@Nullable ModConfig serverConfig);

    Optional<ModConfig> getModConfig();

    default boolean isLoadingDone() {
        return getModConfig().map(config ->config.getSpec().isCorrecting()).orElse(false);
    }
}