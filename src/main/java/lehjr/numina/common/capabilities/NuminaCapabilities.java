package lehjr.numina.common.capabilities;

import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.capabilities.inventory.IModularItem;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import lehjr.numina.common.capabilities.render.color.IColorTag;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

import java.util.Optional;


public class NuminaCapabilities {
    public static final class Inventory {
        public static final ItemCapability<IModularItem, Void> MODULAR_ITEM = ItemCapability.createVoid(create("modular_item"), IModularItem.class);
        public static final ItemCapability<IModeChangingItem, Void> MODE_CHANGING_MODULAR_ITEM = ItemCapability.createVoid(create("mode_changing_modular_tem"), IModeChangingItem.class);

    }

    public static final class PowerModule {
        public static final ItemCapability<IPowerModule, Void> POWER_MODULE = ItemCapability.createVoid(create("powermodule"), IPowerModule.class);

        public static final ItemCapability<IOtherModItemsAsModules, Void> EXTERNAL_MOD_ITEMS_AS_MODULES = ItemCapability.createVoid(create("external_mod_items"), IOtherModItemsAsModules.class);

    }

    public static final ItemCapability<IModelSpec, Void> RENDER = ItemCapability.createVoid(create("render"), IModelSpec.class);

    public static final EntityCapability<IPlayerKeyStates, Void> PLAYER_KEYSTATES = EntityCapability.createVoid(create("keystates"), IPlayerKeyStates.class);

    public static final ItemCapability<IHeatStorage, Void> HEAT = ItemCapability.createVoid(create("heat"), IHeatStorage.class);

    public static final ItemCapability<IColorTag, Void> COLOR = ItemCapability.createVoid(create("color"), IColorTag.class);


    public static final ItemCapability<IHighlight, Void> HIGHLIGHT = ItemCapability.createVoid(create("highlight"), IHighlight.class);

    public static final ItemCapability<IChameleon, Void> CHAMELEON = ItemCapability.createVoid(create("chameleon"), IChameleon.class);


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
}
