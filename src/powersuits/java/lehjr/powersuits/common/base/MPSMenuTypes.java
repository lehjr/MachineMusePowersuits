package lehjr.powersuits.common.base;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.container.InstallSalvageMenu;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MPSMenuTypes {

    /**
     * AbstractContainerMenu Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MPSConstants.MOD_ID);

//    // Module crafting/install/salvage GUI
//    public static final RegistryObject<MenuType<InstallSalvageCraftAbstractContainerMenu>> SALVAGE_CRAFT_CONTAINER_TYPE =
//            CONTAINER_TYPES.register(MPSRegistryNames.INSTALL_SALVAGE_CRAFT_CONTAINER_TYPE,
//                    () -> IForgeMenuType.create((windowId, inv, data) -> new InstallSalvageCraftAbstractContainerMenu(windowId, inv)));

    public static final RegistryObject<MenuType<InstallSalvageMenu>> INSTALL_SALVAGE_MENU_TYPE =
            CONTAINER_TYPES.register(MPSRegistryNames.INSTALL_SALVAGE_CONTAINER_TYPE,
                    () -> IForgeMenuType.create((windowId, inv, data) -> {
//                        NuminaLogger.logError("doing something with install/salvage");

                        return new InstallSalvageMenu(windowId, inv, data.readEnum(EquipmentSlot.class));
                    }));
}
