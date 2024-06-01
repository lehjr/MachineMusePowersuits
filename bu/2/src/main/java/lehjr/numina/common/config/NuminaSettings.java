package lehjr.numina.common.config;

import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.constants.NuminaConstants;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.Optional;

public class NuminaSettings {

    /** Server Settings --------------------------------------------------------------------------- */
    public static int chargingBaseMaxPower() {
        return getActiveConfig().map(config-> config.ARMOR_STAND_MAX_POWER.get()).orElse(10000);
    }

    static Optional<CommonConfig> getActiveConfig() {
        return Optional.ofNullable(CommonConfig.COMMON_SPEC.isLoaded() ? CommonConfig.COMMON_CONFIG : null);
    }

    /** Modules ----------------------------------------------------------------------------------- */
//    private static volatile ModuleConfig moduleConfig;
    static Lazy<IConfig> moduleConfig = Lazy.of(() ->new ModuleConfig(NuminaConstants.MOD_ID));

    public static IConfig getModuleConfig() {
//        if (moduleConfig == null) {
//            synchronized (ModuleConfig.class) {
//                if (moduleConfig == null) {
//                    moduleConfig = Lazy.of(new ModuleConfig(NuminaConstants.MOD_ID));
//                }
//            }
//        }
//        return moduleConfig;
        return moduleConfig.get();
    }
}
