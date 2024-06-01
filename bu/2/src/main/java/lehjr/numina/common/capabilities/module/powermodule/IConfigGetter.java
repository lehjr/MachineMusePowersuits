package lehjr.numina.common.capabilities.module.powermodule;

import lehjr.numina.common.base.NuminaLogger;

import java.util.Optional;
import java.util.concurrent.Callable;

public interface IConfigGetter {
    default Optional<IConfig> getConfig(Callable<IConfig> moduleConfigGetter) {
        try {
            return Optional.ofNullable(moduleConfigGetter.call());
        } catch (Exception e) {
            NuminaLogger.logException("Loading config too early? ", e);
        }
        return Optional.empty();
    }
}
