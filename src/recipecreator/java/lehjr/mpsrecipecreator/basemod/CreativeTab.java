//package lehjr.mpsrecipecreator.basemod;
//
//import net.minecraft.item.CreativeModeTab;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
///**
// * @author lehjr
// */
//public class CreativeTab extends CreativeModeTab {
//    public CreativeTab() {
//        super(Constants.MOD_ID);
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public ItemStack makeIcon() {
//        return new ItemStack(ModObjects.INSTANCE.recipeWorkBench);
//    }
//}