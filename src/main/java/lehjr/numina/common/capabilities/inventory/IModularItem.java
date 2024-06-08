package lehjr.numina.common.capabilities.inventory;

import com.mojang.datafixers.util.Pair;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.StringUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public interface IModularItem extends IItemHandler, IItemHandlerModifiable {
    void setRangedWrapperMap(Map<ModuleCategory, RangedWrapper> rangedWrappers);

    boolean isModuleValid(@Nonnull ItemStack module);

    boolean isModuleInstalled(ResourceLocation regName);

    default boolean isModuleInstalled(@Nonnull ItemStack module) {
        return NuminaCapabilities.getCapability(module, NuminaCapabilities.PowerModule.POWER_MODULE).isPresent() && isModuleInstalled(ItemUtils.getRegistryName(module));
    }

    default boolean isModuleInstalled(Item item) {
        return isModuleInstalled(ItemUtils.getRegistryName(item));
    }

    boolean isModuleOnline(ResourceLocation moduleName);

    void toggleModule(ResourceLocation moduleName, boolean online);

    @Nullable
    Pair<Integer, Integer> getRangeForCategory(ModuleCategory category);

    List<ResourceLocation> getInstalledModuleNames();

    NonNullList<ItemStack> getInstalledModules();

    NonNullList<ItemStack> getInstalledModulesOfType(Class<? extends IPowerModule> type);

    @Nonnull
    ItemStack getOnlineModuleOrEmpty(ResourceLocation regName);

    @Nonnull
    ItemStack getModularItemStack();

    void tick(Player player);

    default String formatInfo(String string, double value) {
        return string + '\t' + StringUtils.formatNumberShort(value);
    }

    boolean setModuleTweakDouble(ResourceLocation moduleName, String key, double value);

    default int findInstalledModule(@Nonnull ItemStack module) {
        ResourceLocation registryName = ItemUtils.getRegistryName(module);
        return findInstalledModule(registryName);
    }

    default int findInstalledModule(ResourceLocation registryName) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack testStack = getStackInSlot(i);
            if (!testStack.isEmpty()) {
                if (ItemUtils.getRegistryName(testStack).equals(registryName)) {
                    return i;
                }
            }
        }
        return -1;
    }
}