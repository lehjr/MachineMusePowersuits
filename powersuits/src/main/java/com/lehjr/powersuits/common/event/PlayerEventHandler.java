package com.lehjr.powersuits.common.event;

import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.network.MPSPackets;
import com.lehjr.powersuits.common.network.packets.clientbound.ToggleableModuleListClientBound;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerEventHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        NonNullList<ResourceLocation> moduleNames = NonNullList.create();
        for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
            ItemStack module = new ItemStack(item);
            IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
            if (pm != null && pm.isAllowed() && pm instanceof IToggleableModule) {
                moduleNames.add(ItemUtils.getRegistryName(item));
            }
        }

        if(!moduleNames.isEmpty()) {
            MPSPackets.sendToPlayer(new ToggleableModuleListClientBound(moduleNames), (ServerPlayer) player);
        }
    }
}
