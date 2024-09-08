package com.lehjr.numina.common.recipe;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class ShapedModularItemUpgradeRecipe extends ShapedRecipe {
    public ShapedModularItemUpgradeRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result,
        boolean showNotification) {
        super(group, category, pattern, result, showNotification);
    }

    public ShapedModularItemUpgradeRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result) {
        super(group, category, pattern, result);
    }

    @Override
    public boolean isSpecial() {
        return super.isSpecial();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
        CompoundTag renderTag = null;

        // Get the default output
        NonNullList<ItemStack> modules = NonNullList.create();
        for(int i = 0; i < input.ingredientCount(); ++i) {
            ItemStack itemstack = input.getItem(i);
            IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(itemstack);
            if(iModularItem != null) {
                IModelSpec spec = itemstack.getCapability(NuminaCapabilities.RENDER);
                if(spec != null) {
                    renderTag = spec.getRenderTag();
                }

                for (int slot=0; slot < iModularItem.getSlots(); slot++) {
                    ItemStack module = iModularItem.getStackInSlot(slot);
                    if (!module.isEmpty()) {
                        modules.add(module);
                    }
                }
            }
        }
        ItemStack output = result.copy();
        IModularItem iModularItemDest = NuminaCapabilities.getModularItemOrModeChangingCapability(output);
        if(iModularItemDest != null && !modules.isEmpty()) {
            IModelSpec specDest = output.getCapability(NuminaCapabilities.RENDER);
            if(renderTag != null && !renderTag.isEmpty() && specDest != null) {
                specDest.setRenderTag(renderTag, NuminaConstants.RENDER_TAG);
            }
            for (ItemStack module : modules) {
                for (int slot = 0; slot < iModularItemDest.getSlots(); slot++) {
                    if (iModularItemDest.isModuleValidForPlacement(slot, module)) {
                        iModularItemDest.insertItem(slot, module, false);
                        break;
                    }
                }
            }
        }
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.MODULAR_ITEM_UPGRADE_RECIPE_SERIALIZER.get();
    }

    public static class ModularItemSerializer implements RecipeSerializer<ShapedModularItemUpgradeRecipe> {
        public static final MapCodec<ShapedModularItemUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(recipeInstance -> recipeInstance
            .group(Codec.STRING.optionalFieldOf("group", "").forGetter(ShapedModularItemUpgradeRecipe::getGroup),
                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(ShapedModularItemUpgradeRecipe::category),
                ShapedRecipePattern.MAP_CODEC.forGetter(shapedRecipe -> shapedRecipe.pattern),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(shapedRecipe -> shapedRecipe.result),
                Codec.BOOL.optionalFieldOf("show_notification", Boolean.valueOf(true)).forGetter(ShapedModularItemUpgradeRecipe::showNotification))
            .apply(recipeInstance, ShapedModularItemUpgradeRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ShapedModularItemUpgradeRecipe> STREAM_CODEC = StreamCodec.of(
            ModularItemSerializer::toNetwork, ModularItemSerializer::fromNetwork);

        @Override
        public MapCodec<ShapedModularItemUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapedModularItemUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ShapedModularItemUpgradeRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
            String group = byteBuf.readUtf();
            CraftingBookCategory craftingbookcategory = byteBuf.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(byteBuf);
            ItemStack result = ItemStack.STREAM_CODEC.decode(byteBuf);
            boolean showNotification = byteBuf.readBoolean();
            return new ShapedModularItemUpgradeRecipe(group, craftingbookcategory, shapedrecipepattern, result, showNotification);
        }

        private static void toNetwork(RegistryFriendlyByteBuf byteBuf, ShapedModularItemUpgradeRecipe upgradeRecipe) {
            byteBuf.writeUtf(upgradeRecipe.getGroup());
            byteBuf.writeEnum(upgradeRecipe.category());
            ShapedRecipePattern.STREAM_CODEC.encode(byteBuf, upgradeRecipe.pattern);
            ItemStack.STREAM_CODEC.encode(byteBuf, upgradeRecipe.result);
            byteBuf.writeBoolean(upgradeRecipe.showNotification());
        }
    }
}
