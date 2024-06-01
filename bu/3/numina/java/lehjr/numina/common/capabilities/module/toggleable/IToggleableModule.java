package lehjr.numina.common.capabilities.module.toggleable;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;

public interface IToggleableModule extends IPowerModule {
    void toggleModule(boolean online);

    @Override
    boolean isModuleOnline();
}