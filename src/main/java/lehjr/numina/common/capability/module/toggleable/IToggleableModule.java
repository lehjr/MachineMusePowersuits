package lehjr.numina.common.capability.module.toggleable;

import lehjr.numina.common.capability.module.powermodule.IPowerModule;

public interface IToggleableModule extends IPowerModule {
    void toggleModule(boolean online);

    @Override
    boolean isModuleOnline();
}