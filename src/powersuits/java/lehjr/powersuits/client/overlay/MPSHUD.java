package lehjr.powersuits.client.overlay;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.render.hud.IHudModule;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.IconUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.StringUtils;
import lehjr.powersuits.client.config.MPSClientConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.concurrent.atomic.AtomicInteger;

public class MPSHUD {
    static final ItemStack food = new ItemStack(Items.COOKED_BEEF);

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    public static void render(Player player, GuiGraphics gfx, float top , float left) {
        int yOffsetString = 18;
        float yOffsetIcon = 16.0F;

        // Misc Overlay Items ---------------------------------------------------------------------------------
        AtomicInteger index = new AtomicInteger(0);

        // Helmet modules with overlay
        NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD), NuminaCapabilities.Inventory.MODULAR_ITEM)
                // Looping this way is far more efficient than looping for each module
                .ifPresent(h -> h.getInstalledModules().forEach(module -> {
                    NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).ifPresent(pm->{
                        if (pm.isModuleOnline()) {
                            // AutoFeeder
                            if (ItemUtils.getRegistryName(module).equals(MPSConstants.AUTO_FEEDER_MODULE)) {
                                // FIXME!!!
//                                int foodLevel =  (int) ((AutoFeederModule) module.getItem()).getFoodLevel(module);
                                int foodLevel = getRandomNumber(0, 100);

                                String num = StringUtils.formatNumberShort(foodLevel);
                                StringUtils.drawShadowedString(gfx, num, 17, 4 +  top + (yOffsetString * index.get()));
                                // FIXME
                                IconUtils.drawItemAt(gfx, -1.0, top + (yOffsetIcon * index.get()), food, Color.WHITE);
                                index.addAndGet(1);

                                // Clock
                            } else if (ItemUtils.getRegistryName(module).equals(ItemUtils.getRegistryName(Items.CLOCK))) {
                                ItemStack clock = module;
                                if (pm.isModuleOnline()) {
                                    String ampm;
                                    long time = player.level().getDayTime();
                                    long hour = ((time % 24000) / 1000);
                                    if (MPSClientConfig.hud_use_24_hour_clock) {
                                        if (hour < 19) {
                                            hour += 6;
                                        } else {
                                            hour -= 18;
                                        }
                                        ampm = "h";
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
                                    StringUtils.drawShadowedString(gfx, hour + ampm, left + 17, 4 + top  + (yOffsetString * index.get()));
                                    IconUtils.drawItemAt(gfx, left -1.0, top + (yOffsetIcon * index.get()), clock, Color.WHITE);
                                    index.addAndGet(1);
                                }
                                // Generic modules
                            } else if (pm instanceof IHudModule) {
                                IconUtils.drawItemAt(gfx, left -1.0, top + (yOffsetIcon * index.get()), module, Color.WHITE);
                                index.addAndGet(1);
                            }
                        }
                    });
                }));
    }
}