package net.machinemuse.powersuits.common.powermodule.armor;

import net.machinemuse.api.IModularItem;
import net.machinemuse.powersuits.client.events.MuseIcon;
import net.machinemuse.powersuits.common.items.ItemComponent;
import net.machinemuse.powersuits.common.powermodule.PowerModuleBase;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import java.util.List;

public class EnergyShieldModule extends PowerModuleBase {
    public static final String MODULE_ENERGY_SHIELD = "Energy Shield";

    public EnergyShieldModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.fieldEmitter, 2));
        addTradeoffProperty("Field Strength", MuseCommonStrings.ARMOR_VALUE_ENERGY, 6, " Points");
        addTradeoffProperty("Field Strength", MuseCommonStrings.ARMOR_ENERGY_CONSUMPTION, 500, "J");
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_ARMOR;
    }

    @Override
    public String getDataName() {
        return MODULE_ENERGY_SHIELD;
    }

    @Override
    public String getUnlocalizedName() { return "energyShield";
    }

    @Override
    public TextureAtlasSprite getIcon(ItemStack item) {
        return MuseIcon.energyShield;
    }
}