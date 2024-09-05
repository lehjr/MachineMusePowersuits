package com.lehjr.numina.common.network.packets.clienthandlers;

import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CosmeticInfoPacketClientHandler {
    public static void handlePacket(EquipmentSlot slotType, String tagName, CompoundTag tagData) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            IModelSpec spec = ItemUtils.getItemFromEntitySlot(player, slotType).getCapability(NuminaCapabilities.RENDER);
            if(spec != null) {
                ItemStack newStack = spec.setRenderTag(tagData, tagName);
            }
        }
    }
}
