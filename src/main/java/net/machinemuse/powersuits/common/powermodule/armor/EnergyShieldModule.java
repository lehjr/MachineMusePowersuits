package net.machinemuse.powersuits.common.powermodule.armor;

import net.machinemuse.numina.common.constants.NuminaNBTConstants;
import net.machinemuse.numina.common.item.MuseItemUtils;
import net.machinemuse.numina.common.module.EnumModuleCategory;
import net.machinemuse.numina.common.module.EnumModuleTarget;
import net.machinemuse.powersuits.common.constants.MPSModuleConstants;
import net.machinemuse.powersuits.client.event.MuseIcon;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.machinemuse.powersuits.common.item.ItemComponent;
import net.machinemuse.powersuits.common.powermodule.PowerModuleBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

public class EnergyShieldModule extends PowerModuleBase {
    public EnergyShieldModule(EnumModuleTarget moduleTarget) {
        super(moduleTarget);
        ModuleManager.INSTANCE.addInstallCost(this.getDataName(), MuseItemUtils.copyAndResize(ItemComponent.fieldEmitter, 2));

        addTradeoffPropertyDouble(MPSModuleConstants.MODULE_FIELD_STRENGTH, MPSModuleConstants.ARMOR_VALUE_ENERGY, 6,
                MPSModuleConstants.MODULE_TRADEOFF_PREFIX + MPSModuleConstants.ARMOR_POINTS);
        addTradeoffPropertyDouble(MPSModuleConstants.MODULE_FIELD_STRENGTH, MPSModuleConstants.ARMOR_ENERGY_CONSUMPTION, 5000, "RF");
        addTradeoffPropertyDouble(MPSModuleConstants.MODULE_FIELD_STRENGTH, NuminaNBTConstants.MAXIMUM_HEAT, 500, "");
    }

    @Override
    public String getDataName() {
        return MPSModuleConstants.MODULE_ENERGY_SHIELD__DATANAME;
    }

    @Override
    public TextureAtlasSprite getIcon(ItemStack item) {
        return MuseIcon.energyShield;
    }

    @Override
    public EnumModuleCategory getCategory() {
        return EnumModuleCategory.CATEGORY_ARMOR;
    }
}