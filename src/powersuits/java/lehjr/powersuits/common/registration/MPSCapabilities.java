package lehjr.powersuits.common.registration;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.powersuits.common.item.module.armor.DiamondPlatingModule;
import lehjr.powersuits.common.item.module.armor.IronPlatingModule;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class MPSCapabilities {

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(NuminaCapabilities.PowerModule.POWER_MODULE, (stack, ctx)-> new IronPlatingModule.IronArmorPlatingCapabilityWrapper(stack), MPSItems.IRON_PLATING_MODULE.get());
        event.registerItem(NuminaCapabilities.PowerModule.POWER_MODULE, (stack, ctx)-> new DiamondPlatingModule.DiamondArmorPlatingCapabilityWrapper(stack), MPSItems.DIAMOND_PLATING_MODULE.get());

    }
}
