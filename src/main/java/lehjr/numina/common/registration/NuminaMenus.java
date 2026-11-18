package lehjr.numina.common.registration;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.container.ArmorStandMenu;
import lehjr.numina.common.container.ChargingBaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NuminaMenus {
        public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, NuminaConstants.MOD_ID);

    public static final RegistryObject<MenuType<ArmorStandMenu>> ARMOR_STAND_CONTAINER_TYPE = MENU_TYPES.register("armorstand_modding_container",
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                int entityID = data.readInt();
                Entity armorStand = inv.player.level().getEntity(entityID);
                if (armorStand instanceof ArmorStand) {
                    return new ArmorStandMenu(windowId, inv, (ArmorStand) armorStand);
                }
                return null;
            }));

    public static final RegistryObject<MenuType<ChargingBaseMenu>> CHARGING_BASE_CONTAINER_TYPE = MENU_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new ChargingBaseMenu(windowId, pos, inv);
            }));
}
