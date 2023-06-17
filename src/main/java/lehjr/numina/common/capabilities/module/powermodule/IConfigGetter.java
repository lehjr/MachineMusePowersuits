package lehjr.numina.common.capabilities.module.powermodule;

import java.util.Optional;
import java.util.concurrent.Callable;

public interface IConfigGetter {
    default Optional<IConfig> getConfig(Callable<IConfig> moduleConfigGetter) {
        try {
            return Optional.ofNullable(moduleConfigGetter.call());
        } catch (Exception e) {
            // not initialized yet
            // TODO: debug message?
            e.printStackTrace();
        }
        return null;
    }
}
