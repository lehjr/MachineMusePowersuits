package net.machinemuse.powersuits.common.powermodule.tool;

import net.machinemuse.numina.common.item.MuseItemUtils;
import net.machinemuse.numina.common.module.EnumModuleCategory;
import net.machinemuse.numina.common.module.EnumModuleTarget;
import net.machinemuse.powersuits.client.event.MuseIcon;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.machinemuse.powersuits.common.constants.MPSModuleConstants;
import net.machinemuse.powersuits.common.item.ItemComponent;
import net.machinemuse.powersuits.common.powermodule.PowerModuleBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

/**
 * Created by User: Andrew2448
 * 7:21 PM 4/25/13
 */
public class MFFSFieldTeleporterModule extends PowerModuleBase {
    public MFFSFieldTeleporterModule(EnumModuleTarget moduleTarget) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        super(moduleTarget);
        ModuleManager.INSTANCE.addInstallCost(getDataName(), MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));

        addBasePropertyDouble(MPSModuleConstants.FIELD_TELEPORTER_ENERGY_CONSUMPTION, 200000, "RF");
    }

    @Override
    public EnumModuleCategory getCategory() {
        return EnumModuleCategory.CATEGORY_TOOL;
    }

    @Override
    public String getDataName() {
        return MPSModuleConstants.MODULE_FIELD_TELEPORTER__DATANAME;
    }

    @Override
    public TextureAtlasSprite getIcon(ItemStack item) {
        return MuseIcon.mffsFieldTeleporter;
    }
}