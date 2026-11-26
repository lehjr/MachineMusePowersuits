package lehjr.numina.common.recipe;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.registration.RecipeSerializersRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ModuleInstallationRecipe extends CustomRecipe {

    public ModuleInstallationRecipe(ResourceLocation idIn, CraftingBookCategory categoryIn) {
        super(idIn, categoryIn);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        List<Integer> modularItemSlots = new ArrayList<>();
        List<Integer> moduleSlots = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack itemStack = container.getItem(i);
            if(!itemStack.isEmpty()) {
                IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(itemStack);
                IPowerModule pm = itemStack.getCapability(NuminaCapabilities.Module.POWER_MODULE).resolve().orElse(null);
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

        ItemStack modularItem = container.getItem(modularItemSlots.get(0));
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
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        List<Integer> modularItemSlots = new ArrayList<>();
        List<Integer> moduleSlots = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack itemStack = container.getItem(i);
            if(!itemStack.isEmpty()) {
                IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(itemStack);
                if(iModularItem != null) {
                    modularItemSlots.add(i);
                }
                IPowerModule pm = itemStack.getCapability(NuminaCapabilities.Module.POWER_MODULE).resolve().orElse(null);
                if(pm != null) {
                    moduleSlots.add(i);
                }
            }
        }

        ItemStack modularItem = container.getItem(modularItemSlots.get(0)).copy();
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
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        return NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.ATTACHMENT.get();
    }
}







