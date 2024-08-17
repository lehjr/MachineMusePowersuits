package com.lehjr.numina.common.recipe;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


/**
 * Recipe for installing modules into a modular item without the use of a TinkerTable
 * This is used in conjunction with a single nearly blank recipe where only the type is declared:
 * {
 *   "type": "numina:module_install"
 * }
 */
public class ModuleInstallationRecipe extends CustomRecipe {
    public ModuleInstallationRecipe(CraftingBookCategory categoryIn) {
        super(categoryIn);
    }

    @Override
    public boolean matches(CraftingInput container, Level level) {
        List<Integer> modularItemSlots = new ArrayList<>();
        List<Integer> moduleSlots = new ArrayList<>();

        for (int i=0; i < container.ingredientCount(); i ++) {
            ItemStack itemStack = container.getItem(i);
            if(!itemStack.isEmpty()) {
                IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(itemStack);
                IPowerModule pm = itemStack.getCapability(NuminaCapabilities.Module.POWER_MODULE);
                if(iModularItem != null) {
                    modularItemSlots.add(i);
                } else if (pm != null) {
                    moduleSlots.add(i);
                } else {
                    return false;
                }
            }
        }
        if (modularItemSlots.size() != 1 || moduleSlots.isEmpty()) {
            return false;
        }

//        if (modularItemSlots.size() != 1 || moduleSlots.size() != 1) {
//            return false;
//        }

        ItemStack modularItem = container.getItem(modularItemSlots.getFirst());
        IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(modularItem);
        if(iModularItem != null) {
            return moduleSlots.stream().allMatch(index-> {
                ItemStack module = container.getItem(index);
                if(iModularItem.isModuleInstalled(module)) {
                    return false;
                }
                for (int i=0; i< iModularItem.getSlots(); i++) {
                    if(iModularItem.isModuleValidForPlacement(i, module)){
                        return true;
                    }
                }
                return false;
            });
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput container, @Nonnull HolderLookup.Provider provider) {
        List<Integer> modularItemSlots = new ArrayList<>();
        List<Integer> moduleSlots = new ArrayList<>();

        for (int i=0; i < container.ingredientCount(); i ++) {
            ItemStack itemStack = container.getItem(i);
            if(!itemStack.isEmpty()) {
                IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(itemStack);
                if(iModularItem != null) {
                    modularItemSlots.add(i);
                }
                IPowerModule pm = itemStack.getCapability(NuminaCapabilities.Module.POWER_MODULE);
                if(pm != null) {
                    moduleSlots.add(i);
                }
            }
        }

        ItemStack modularItem = container.getItem(modularItemSlots.getFirst()).copy();
        IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(modularItem);
        if(iModularItem != null) {
            for(Integer index : moduleSlots) {
                ItemStack module = container.getItem(index);
                if(iModularItem.isModuleInstalled(module)) {
                    return ItemStack.EMPTY;
                } else {
                    for (int i=0; i< iModularItem.getSlots(); i++) {
                        if(iModularItem.isModuleValidForPlacement(i, module)){
                            iModularItem.insertItem(i, module, false);
                            break;
                        }
                    }
                }
            }
            return iModularItem.getModularItemStack().copy();
        }




//        // single module install
//        ItemStack modularItem = container.getItem(modularItemSlots.getFirst()).copy();
//        IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(modularItem);
//        if(iModularItem != null) {
//            ItemStack module = container.getItem(moduleSlots.getFirst());
//            for (int i=0; i< iModularItem.getSlots(); i++) {
//                if(iModularItem.isModuleValidForPlacement(i, module)){
//                    iModularItem.insertItem(i, module, false);
//                    break;
//                }
//            }
//            return iModularItem.getModularItemStack().copy();
//        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput inv) {
        return NonNullList.withSize(inv.ingredientCount(), ItemStack.EMPTY);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.ATTACHMENT.get();
    }
}







