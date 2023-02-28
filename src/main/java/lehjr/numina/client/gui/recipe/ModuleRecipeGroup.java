//package lehjr.numina.client.gui.recipe;
//
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import javax.annotation.Nonnull;
//
///**
// * Just a quick and dirty way of adding the tier and "group" to the recipe list for faster sorting
// * None of the parent fields need to be accessible since they're still populated the same way
// */
//@OnlyIn(Dist.CLIENT)
//public class ModuleRecipeGroup extends RecipeList {
//    final int tier;
//    @Nonnull
//    final String group;
//
//    public ModuleRecipeGroup(RecipeList iRecipes, int tier, @Nonnull String group) {
//        super(iRecipes.func_192711_b());
//        this.tier = tier;
//        this.group = group;
//    }
//
//    @Override
//    public boolean func_194209_a() {
//        return super.func_194209_a();
//    }
//
//    public int getTier() {
//        return tier;
//    }
//
//    public String getGroup() {
//        return group;
//    }
//}