package lehjr.powersuits.common.capabilities.item.armor;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.Callable;

public class ArmorPlatingCapabilityWrapper extends PowerModule {
    public ArmorPlatingCapabilityWrapper(ItemStack module) {
        super(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY);

        addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL,
                ()-> () -> MPSCommonConfig.ironPlatingArmorPhysical,
                NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);

        addBaseProperty(NuminaConstants.MAXIMUM_HEAT,
                ()-> () -> MPSCommonConfig.ironPlatingMaxHeat);

        addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE,
                ()-> () -> MPSCommonConfig.ironPlatingKnockBackResistance);

        addBaseProperty(MPSConstants.ARMOR_TOUGHNESS,
                ()-> () -> MPSCommonConfig.ironPlatingArmorToughness);
    }

    @Override
    public boolean isAllowed() {
        return MPSCommonConfig.ironPlatingIsAllowed;
    }
}
