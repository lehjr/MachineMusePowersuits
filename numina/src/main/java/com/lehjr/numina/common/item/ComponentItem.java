package com.lehjr.numina.common.item;

import com.lehjr.numina.common.utils.AdditionalInfo;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ComponentItem extends Item {
    public ComponentItem() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> toolTips, TooltipFlag flags) {
        super.appendHoverText(itemStack, context, toolTips, flags);
        AdditionalInfo.appendHoverText(itemStack, context, toolTips, flags, Screen.hasShiftDown());
    }
}
