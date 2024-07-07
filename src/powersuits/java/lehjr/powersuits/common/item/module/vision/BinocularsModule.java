package lehjr.powersuits.common.item.module.vision;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.toggleable.ToggleableModule;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:08 AM, 4/24/13
 * <p>
 * Ported to Java by lehjr on 10/11/16.
 */
public class BinocularsModule extends AbstractPowerModule {
    public static class BinocularCap extends ToggleableModule {

        public BinocularCap(ItemStack module) {
            super(module, ModuleCategory.VISION, ModuleTarget.HEADONLY);
            addBaseProperty(MPSConstants.FOV, ()-> ()-> MPSCommonConfig.binocularsModuleFOVBase);
            addTradeoffProperty(MPSConstants.FIELD_OF_VIEW, MPSConstants.FOV, ()-> ()-> MPSCommonConfig.binocularsModuleFOVFieldOfViewMultiplier, "%");
        }

        @Override
        public boolean isAllowed() {
            return MPSCommonConfig.binocularsModuleIsAllowed;
        }

        @Override
        public int getTier() {
            return 2;
        }
    }
}