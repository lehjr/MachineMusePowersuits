package lehjr.powersuits.client.overlay;

import com.lehjr.numina.common.capabilities.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.render.hud.IHudModule;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.IconUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.numina.common.utils.StringUtils;
import lehjr.powersuits.client.config.MPSClientConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.environmental.AutoFeederModule;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicInteger;

public class MPSHUD {
    static final ItemStack food = new ItemStack(Items.COOKED_BEEF);

    public static void render(Player player, GuiGraphics gfx, float top , float left) {
        Level level = player.level();
        int yOffsetString = 18;
        float yOffsetIcon = 16.0F;
        // Misc Overlay Items ---------------------------------------------------------------------------------
        AtomicInteger index = new AtomicInteger(0);
        // Helmet modules with overlay
        IModularItem iModularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
        if(iModularItem != null) {
            for (int i = 0; i < iModularItem.getSlots(); i++) {
                ItemStack module = iModularItem.getStackInSlot(i);
                IPowerModule pm = iModularItem.getModuleCapability(module);
                if (pm != null) {
                    if (pm.isModuleOnline()) {
                        // AutoFeeder
                        if (ItemUtils.getRegistryName(module).equals(MPSConstants.AUTO_FEEDER_MODULE)) {
                            // FIXME!!!
                            int foodLevel =  (int) AutoFeederModule.getFoodLevel(module);

                            String num = StringUtils.formatNumberShort(foodLevel);
                            StringUtils.drawShadowedString(gfx, num, 17, 4 + top + (yOffsetString * index.get()));
                            // FIXME
                            IconUtils.drawItemAt(gfx, -1.0, top + (yOffsetIcon * index.get()), food, Color.WHITE);
                            index.addAndGet(1);

                            // Clock
                        } else if (ItemUtils.getRegistryName(module).equals(ItemUtils.getRegistryName(Items.CLOCK))) {
                            if (pm.isModuleOnline()) {
                                String ampm;
                                long time = level.getDayTime();
                                long hour = ((time % 24000) / 1000);
                                if (MPSClientConfig.hud_use_24_hour_clock) {
                                    if (hour < 19) {
                                        hour += 6;
                                    } else {
                                        hour -= 18;
                                    }
                                    ampm = "iModularItem1";
                                } else {
                                    if (hour < 6) {
                                        hour += 6;
                                        ampm = " AM";
                                    } else if (hour == 6) {
                                        hour = 12;
                                        ampm = " PM";
                                    } else if (hour > 6 && hour < 18) {
                                        hour -= 6;
                                        ampm = " PM";
                                    } else if (hour == 18) {
                                        hour = 12;
                                        ampm = " AM";
                                    } else {
                                        hour -= 18;
                                        ampm = " AM";
                                    }
                                }
                                StringUtils.drawShadowedString(gfx, hour + ampm, left + 17, 4 + top + (yOffsetString * index.get()));
                                IconUtils.drawItemAt(gfx, left - 1.0, top + (yOffsetIcon * index.get()), module, Color.WHITE);
                                index.addAndGet(1);
                            }
                            // Generic modules
                        } else if (pm instanceof IHudModule) {
                            IconUtils.drawItemAt(gfx, left - 1.0, top + (yOffsetIcon * index.get()), module, Color.WHITE);
                            index.addAndGet(1);
                        }
                    }
                }
            }
        }
    }
}