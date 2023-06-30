package net.machinemuse.powersuits.common.powermodule.movement;

import net.machinemuse.numina.common.item.MuseItemUtils;
import net.machinemuse.numina.common.module.EnumModuleCategory;
import net.machinemuse.numina.common.module.EnumModuleTarget;
import net.machinemuse.numina.common.module.IToggleableModule;
import net.machinemuse.powersuits.common.constants.MPSModuleConstants;
import net.machinemuse.powersuits.client.event.MuseIcon;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.machinemuse.powersuits.common.item.ItemComponent;
import net.machinemuse.powersuits.common.powermodule.PowerModuleBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ShockAbsorberModule extends PowerModuleBase implements IToggleableModule {
    public ShockAbsorberModule(EnumModuleTarget moduleTarget) {
        super(moduleTarget);
        ModuleManager.INSTANCE.addInstallCost(getDataName(), MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 2));
        ModuleManager.INSTANCE.addInstallCost(getDataName(), new ItemStack(Blocks.WOOL, 2));
        addBasePropertyDouble(MPSModuleConstants.SHOCK_ABSORB_ENERGY_CONSUMPTION, 0, "RF/m");
        addTradeoffPropertyDouble(MPSModuleConstants.POWER, MPSModuleConstants.SHOCK_ABSORB_ENERGY_CONSUMPTION, 100);
        addBasePropertyDouble(MPSModuleConstants.SHOCK_ABSORB_MULTIPLIER, 0, "%");
        addTradeoffPropertyDouble(MPSModuleConstants.POWER, MPSModuleConstants.SHOCK_ABSORB_MULTIPLIER, 10);
    }

    @Override
    public EnumModuleCategory getCategory() {
        return EnumModuleCategory.CATEGORY_MOVEMENT;
    }

    @Override
    public String getDataName() {
        return MPSModuleConstants.MODULE_SHOCK_ABSORBER__DATANAME;
    }

    @Override
    public TextureAtlasSprite getIcon(ItemStack item) {
        return MuseIcon.shockAbsorber;
    }
}