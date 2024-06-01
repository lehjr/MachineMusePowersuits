package lehjr.numina.common.capabilities.module.rightclick;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;

public class RightClickModule extends PowerModule implements IRightClickModule {
    public RightClickModule(ModuleCategory category, ModuleTarget target) {
        super(category, target);
    }
}