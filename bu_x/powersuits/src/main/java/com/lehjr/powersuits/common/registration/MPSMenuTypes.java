package com.lehjr.powersuits.common.registration;

import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.container.InstallSalvageMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MPSMenuTypes {
    /**
     * AbstractContainerMenu Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MPSConstants.MOD_ID);

    public static final Supplier<MenuType<InstallSalvageMenu>> INSTALL_SALVAGE_MENU_TYPE =
            MENU_TYPES.register(MPSConstants.INSTALL_SALVAGE_CONTAINER_TYPE,
                    () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                        EquipmentSlot slot = data.readEnum(EquipmentSlot.class);
                        return new InstallSalvageMenu(windowId, inv, slot);
                    }));
}
