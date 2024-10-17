package com.lehjr.powersuits.common.capabilities.item.module;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.powersuits.common.config.module.ToolModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.container.InstallSalvageMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TinkerTableCap extends RightClickModule {

    public TinkerTableCap(ItemStack module)  {
        super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
    }

    @Override
    public boolean isAllowed() {
        return ToolModuleConfig.tinkerTableIsAllowed;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(itemStackIn, level.isClientSide);
        } else {
            SimpleMenuProvider container = new SimpleMenuProvider((id, inventory, player) -> new InstallSalvageMenu(id, inventory, EquipmentSlot.MAINHAND), Component.translatable(MPSConstants.GUI_INSTALL_SALVAGE));
            playerIn.openMenu(container, buf -> buf.writeEnum(EquipmentSlot.MAINHAND));
            return InteractionResultHolder.consume(itemStackIn);
        }
    }
}
