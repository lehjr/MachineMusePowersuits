package lehjr.numina.common.registration;

import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import lehjr.numina.common.capabilities.render.color.IColorTag;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NuminaCapabilities {
    public static final class Inventory {
        public static final Capability<IModularItem> MODULAR_ITEM = CapabilityManager.get(new CapabilityToken<>(){});
        public static final Capability<IModeChangingItem> MODE_CHANGING_MODULAR_ITEM = CapabilityManager.get(new CapabilityToken<>(){});
    }

    public static final class Module {
        public static final Capability<IPowerModule> POWER_MODULE = CapabilityManager.get(new CapabilityToken<>(){});
        public static final Capability<IOtherModItemsAsModules> EXTERNAL_MOD_ITEMS_AS_MODULES = CapabilityManager.get(new CapabilityToken<>(){});
    }

    public static final Capability<IHeatStorage> HEAT = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IColorTag> COLOR = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IModelSpec> RENDER = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IHighlight> HIGHLIGHT = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IChameleon> CHAMELEON = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IPlayerKeyStates> PLAYER_KEYSTATES = CapabilityManager.get(new CapabilityToken<>(){});


    @Nullable
    public static IModeChangingItem getModeChangingModularItem(ItemStack modularItem) {
        return modularItem.getCapability(Inventory.MODE_CHANGING_MODULAR_ITEM).resolve().orElse(null);
    }

    @Nullable
    public static IModeChangingItem getModeChangingModularItemOrStored(ItemStack modularItem, Level level) {
        return getModeChangingModularItemOrStored(modularItem);
    }

    @Nullable
    public static IModeChangingItem getModeChangingModularItemOrStored(ItemStack modularItem) {
        IModeChangingItem mcmi = getModeChangingModularItem(modularItem);
        if(mcmi != null) {
            return mcmi;
        }
        IOtherModItemsAsModules foreignModuleCap = modularItem.getCapability(Module.EXTERNAL_MOD_ITEMS_AS_MODULES).resolve().orElse(null);
        if(foreignModuleCap != null) {
            return foreignModuleCap.getStoredModeChangingModuleCapInStorage();
        }
        return null;
    }

    @Nullable
    public static IModularItem getModularItem(ItemStack modularItem) {
        return modularItem.getCapability(Inventory.MODULAR_ITEM).resolve().orElse(null);
    }

    public static boolean modularItemCapIsPresent(@Nonnull ItemStack itemStack) {
        return getModularItem(itemStack) != null;
    }

    public static boolean modularItemCapIsPresent(Player player, EquipmentSlot slot) {
        return modularItemCapIsPresent(player.getItemBySlot(slot));
    }

    public static boolean modeChangingModularItemCapIsPresent(@Nonnull ItemStack modularItem) {
        return getModeChangingModularItem(modularItem) != null;
    }


    @Nullable
    public static IModularItem getModularItemOrModeChangingCapability(ItemStack modularItem, Level level) {
        IModularItem cap = getModeChangingModularItemOrStored(modularItem, level);
        if (cap != null) {
            return cap;
        }
        return modularItem.getCapability(Inventory.MODULAR_ITEM).resolve().orElse(null);
    }

    @Nullable
    public static IModularItem getModularItemOrModeChangingCapability(ItemStack modularItem) {
        IModularItem cap = getModeChangingModularItem(modularItem);
        if (cap != null) {
            return cap;
        }
        return modularItem.getCapability(Inventory.MODULAR_ITEM).resolve().orElse(null);
    }

    public static HolderLookup.Provider getProvider(@Nonnull Level level) {
        return level.registryAccess();
    }


    public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.register(IHeatStorage.class);
        event.register(IColorTag.class);

        event.register(IModularItem.class);
        event.register(IModeChangingItem.class);

        // Modules
        event.register(IPowerModule.class);
        event.register(IModelSpec.class);
        event.register(IHighlight.class);
        event.register(IChameleon.class);

        // Player
        event.register(IPlayerKeyStates.class);
    }
}
