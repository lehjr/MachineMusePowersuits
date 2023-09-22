package lehjr.mpsrecipecreator.basemod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author lehjr
 */
public class CreativeTab extends CreativeModeTab {
    public CreativeTab() {
        super(MPSRCConstants.MOD_ID);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModObjects.INSTANCE.RECIPE_WORKBENCH_ITEM.get());    }
}