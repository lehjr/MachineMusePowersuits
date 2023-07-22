package lehjr.numina.common.base;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class NuminaCreativeTab extends CreativeModeTab {
    public NuminaCreativeTab() {
        super(NuminaConstants.MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(NuminaObjects.ARMOR_STAND_ITEM.get());
    }
}
