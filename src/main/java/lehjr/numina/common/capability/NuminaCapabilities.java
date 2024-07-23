package lehjr.numina.common.capability;

import lehjr.numina.common.capability.heat.IHeatStorage;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.capability.render.color.IColorTag;
import lehjr.numina.common.capability.render.modelspec.IModelSpec;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;
import java.util.Optional;


public class NuminaCapabilities {
    public static final class Inventory {
        public static final ItemCapability<IModularItem, Void> MODULAR_ITEM = ItemCapability.createVoid(create("modular_item"), IModularItem.class);
        public static final ItemCapability<IModeChangingItem, Void> MODE_CHANGING_MODULAR_ITEM = ItemCapability.createVoid(create("mode_changing_modular_tem"), IModeChangingItem.class);

    }

    public static final class Module {
        public static final ItemCapability<IPowerModule, Void> POWER_MODULE = ItemCapability.createVoid(create("powermodule"), IPowerModule.class);

        public static final ItemCapability<IOtherModItemsAsModules, Void> EXTERNAL_MOD_ITEMS_AS_MODULES = ItemCapability.createVoid(create("external_mod_items"), IOtherModItemsAsModules.class);

    }

    public static final ItemCapability<IModelSpec, Void> RENDER = ItemCapability.createVoid(create("render"), IModelSpec.class);

    public static final EntityCapability<IPlayerKeyStates, Void> PLAYER_KEYSTATES = EntityCapability.createVoid(create("keystates"), IPlayerKeyStates.class);

    public static final ItemCapability<IHeatStorage, Void> HEAT = ItemCapability.createVoid(create("heat"), IHeatStorage.class);

    public static final class ColorCap {
        public static final ItemCapability<IColorTag, Void> COLOR_ITEM = ItemCapability.createVoid(create("color"), IColorTag.class);
        public static final BlockCapability<IColorTag, Void> COLOR_BLOCK = BlockCapability.createVoid(create("color"), IColorTag.class);
    }

//    public static final ItemCapability<IHighlight, Void> HIGHLIGHT = ItemCapability.createVoid(create("highlight"), IHighlight.class);
//    public static final ItemCapability<IChameleon, Void> CHAMELEON = ItemCapability.createVoid(create("chameleon"), IChameleon.class);


    // Note: path can only be lower case or underscore
    private static ResourceLocation create(String path) {
        return new ResourceLocation(NuminaConstants.MOD_ID, path);
    }


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

    public static Optional<IModularItem> getModularItemOrModeChangingCapability(ItemStack modularItem) {
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

    /**
     * Yeah, not sure if this will fail or not
     * @return
     */
    public static HolderLookup.Provider getProvider() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return SomeRandomClassName.provider();
        }
        assert ServerLifecycleHooks.getCurrentServer() != null;
        return ServerLifecycleHooks.getCurrentServer().registryAccess();
    }

    public static HolderLookup.Provider getProvider(@Nonnull Level level) {
        return level.registryAccess();
    }
}
