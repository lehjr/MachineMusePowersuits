package com.lehjr.numina.common.capabilities;

import com.lehjr.numina.common.capabilities.heat.IHeatStorage;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import com.lehjr.numina.common.capabilities.render.color.IColorTag;
import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;


public class NuminaCapabilities {










    public static <T, C> Optional<T> getCapability(ItemStack itemStack, ItemCapability<T, C> capability, C context) {
        return Optional.ofNullable(itemStack.getCapability(capability, context));
    }

    public static <T> Optional<T> getCapability(ItemStack itemStack, ItemCapability<T, Void> capability) {
        return Optional.ofNullable(itemStack.getCapability(capability, null));
    }

    public static <T> Optional<T> getCapability(Entity entity, EntityCapability<T, Void> capability) {
        return Optional.ofNullable(entity.getCapability(capability, null));
    }

    public static <T, C> Optional<T> getCapability(Entity entity, EntityCapability<T, C> capability, C context) {
        return Optional.ofNullable(entity.getCapability(capability, context));
    }

    public static Optional<IModeChangingItem> getModeChangingModularItemCapability(Player player) {
        ItemStack itemStack = player.getInventory().getSelected();
        Optional<IModeChangingItem> mcItemCap = getModeChangingModularItemCapability(itemStack);
        if (mcItemCap.isPresent()) {
            return mcItemCap;
        }
        HolderLookup.Provider provider = getProvider(player.level());

        Optional<IOtherModItemsAsModules> foreignModuleCap = getForeignItemAsModuleCap(itemStack);
        return foreignModuleCap.flatMap(iOtherModItemsAsModules -> iOtherModItemsAsModules.getStoredModeChangingModuleCapInStorage(provider));
    }

    public static Optional<IOtherModItemsAsModules> getForeignItemAsModuleCap(ItemStack module) {
        return getCapability(module, Module.POWER_MODULE)
                .filter( m-> m.isAllowed() && m instanceof IOtherModItemsAsModules).map(IOtherModItemsAsModules.class::cast);
    }

    public static Optional<IModularItem> getModularItemCapability(ItemStack modularItem) {
        return getCapability(modularItem, Inventory.MODULAR_ITEM)
                .map(IModularItem.class::cast);
    }

    public static Optional<IModularItem> getOptionalModularItemOrModeChangingCapability(ItemStack modularItem) {
        Optional<IModularItem> cap = getModularItemCapability(modularItem);
        if (cap.isPresent()) {
            return cap;
        }
        return getModeChangingModularItemCapability(modularItem).map(IModularItem.class::cast);
    }




    public static Optional<IModeChangingItem> getModeChangingModularItemCapability(ItemStack modularItem) {
        return getCapability(modularItem, Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(IModeChangingItem.class::cast);
    }

    public static Optional<IPowerModule> getPowerModuleCapability(ItemStack module) {
        return getCapability(module, Module.POWER_MODULE).map(IPowerModule.class::cast);
    }

    public static Optional<IModelSpec> getRenderCapability(ItemStack module) {
        return getCapability(module, RENDER).map(IModelSpec.class::cast);
    }

//    /**
//     * Yeah, not sure if this will fail or not
//     * @return
//     */
//    public static HolderLookup.Provider getProvider() {
//        if (FMLEnvironment.dist == Dist.CLIENT) {
//            return SomeRandomClassName.provider();
//        }
//        assert ServerLifecycleHooks.getCurrentServer() != null;
//        return ServerLifecycleHooks.getCurrentServer().registryAccess();
//    }


}
