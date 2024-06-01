package lehjr.numina.common.recipe;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ModuleInstallationRecipe extends CustomRecipe {

    public ModuleInstallationRecipe(CraftingBookCategory categoryIn) {
        super(categoryIn);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack modularItem = ItemStack.EMPTY;
        NonNullList<ItemStack> modules = NonNullList.create();

        // filter the list of items
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (isModularItem(stack)) {
                    // should only be one modular item in the recipe
                    if (!modularItem.isEmpty()) {
                        return false;
                    }
                    modularItem = stack;

                } else if (isModule(stack)) {
                    if (listContains(stack, modules)) {
                        return false;
                    }
                    modules.add(stack);

                // if item is not a module or modular item
                } else {
                    return false;
                }
            }
        }

        if (modularItem.isEmpty() || modules.isEmpty()) {
            return false;
        }

        return getModularItemCap(modularItem).map(iModularItem -> {
            for (ItemStack module: modules) {

                if (!iModularItem.isModuleValid(module)) {
                    return false;
                }
                // todo: allow replace?
                if(iModularItem.isModuleInstalled(module)){
                    return false;
                }

                Optional<IPowerModule> pm = getPowerModuleCap(module);
                if(pm.isPresent()) {
                    ModuleCategory category = getCategory(pm);
                    int tier = getTier(pm);
                    Pair<Integer, Integer> range = iModularItem.getRangeForCategory(category);
                    if (range != null) {
                        for (int i = range.getKey(); i< range.getValue(); i++) {
                            ItemStack tesStack = iModularItem.getStackInSlot(i);
                            if (tesStack.isEmpty()) {
                                return true;
                            }

                            if (tier < -1) {
                                Optional<IPowerModule> pmcapTest = getPowerModuleCap(tesStack);
                                ModuleCategory testCategory = getCategory(pmcapTest);
                                int testTier = getTier(pmcapTest);
                                if (testCategory.equals(category)) {
                                    return tier > testTier;
                                }
                            }
                        }
                    } else {
                        boolean canInstall = false;
                        for(int i = 0; i< iModularItem.getSlots(); i++) {
                            if(iModularItem.isItemValid(i, module) && iModularItem.getStackInSlot(i).isEmpty()) {
                                canInstall = true;
                                break;
                            }
                        }
                        if(!canInstall) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }).orElse(false) && !modularItem.isEmpty() && !modules.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack modularItem = ItemStack.EMPTY;
        NonNullList<ItemStack> modules = NonNullList.create();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (isModularItem(stack) && modularItem.isEmpty()) {
                    modularItem = stack;
                } else if (isModule(stack)){
                    modules.add(stack);
                }
            }
        }

        ItemStack copy = modularItem.copy();
        Optional<IModularItem> modularItemCap = getModularItemCap(copy);
        modularItemCap.ifPresent(iModularItem -> {
            modules.forEach(module -> {
                for (int i=0; i<iModularItem.getSlots(); i++) {
                    if (iModularItem.isItemValid(i, module)) {
                        iModularItem.insertItem(i, module, false);
                        break;
                    }
                }
            });
        });

        return copy;
    }

    boolean listContains(@Nonnull ItemStack test, NonNullList<ItemStack> stackList) {
        if (stackList.isEmpty()) {
            return false;
        }
        for(int i=0; i < stackList.size(); i++){
            if (ItemStack.isSameItemSameTags(test, stackList.get(i))){
                return true;
            }
        }
        return false;
    }

    Optional<IPowerModule> getPowerModuleCap(@Nonnull ItemStack itemStack) {
        return NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.POWER_MODULE)
                .map(IPowerModule.class::cast);
    }

    ModuleCategory getCategory(Optional<IPowerModule> pmcap) {
        return pmcap.map(IPowerModule::getCategory).orElse(ModuleCategory.NONE);
    }

    int getTier(Optional<IPowerModule> pmcap) {
        return pmcap.map(IPowerModule::getTier).orElse(-1);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    public boolean isModule(@Nonnull ItemStack itemStack) {
        return getPowerModuleCap(itemStack).isPresent();
    }

    Optional<IModularItem> getModularItemCap(@Nonnull ItemStack itemStack) {
        return NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.MODULAR_ITEM)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast);
    }

    public boolean isModularItem(ItemStack itemStack) {
        return getModularItemCap(itemStack).isPresent();
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







