package lehjr.powersuits.client.gui.overlay;

import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.hud.IHudModule;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.item.module.environmental.AutoFeederModule;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.concurrent.atomic.AtomicInteger;

public class MPSHUD {
    static final ItemStack food = new ItemStack(Items.COOKED_BEEF);

    public static void render(Player player, GuiGraphics gfx, float top , float left) {
        int yOffsetString = 18;
        float yOffsetIcon = 16.0F;

        // Misc Overlay Items ---------------------------------------------------------------------------------
        AtomicInteger index = new AtomicInteger(0);

        // Helmet modules with overlay
        ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                // Looping this way is far more efficient than looping for each module
                .ifPresent(h -> h.getInstalledModules().forEach(module -> {
                    module.getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(pm->{
                        if (pm.isModuleOnline()) {
                            // AutoFeeder
                            if (ItemUtils.getRegistryName(module).equals(MPSRegistryNames.AUTO_FEEDER_MODULE)) {
                                int foodLevel = (int) ((AutoFeederModule) module.getItem()).getFoodLevel(module);
                                String num = StringUtils.formatNumberShort(foodLevel);
                                StringUtils.drawShadowedString(gfx, num, 17, 4 +  top + (yOffsetString * index.get()));
                                // FIXME
                                NuminaRenderer.drawItemAt(gfx, -1.0, top + (yOffsetIcon * index.get()), food, Color.WHITE);
                                index.addAndGet(1);

                                // Clock
                            } else if (ItemUtils.getRegistryName(module).equals(ItemUtils.getRegistryName(Items.CLOCK))) {
                                ItemStack clock = module;
                                if (pm.isModuleOnline()) {
                                    String ampm;
                                    long time = player.level().getDayTime();
                                    long hour = ((time % 24000) / 1000);
                                    if (MPSSettings.use24HourClock()) {
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
                                    NuminaRenderer.drawItemAt(gfx, left -1.0, top + (yOffsetIcon * index.get()), clock, Color.WHITE);
                                    index.addAndGet(1);
                                }
                                // Generic modules
                            } else if (pm instanceof IHudModule) {
                                NuminaRenderer.drawItemAt(gfx, left -1.0, top + (yOffsetIcon * index.get()), module, Color.WHITE);
                                index.addAndGet(1);
                            }
                        }
                    });
                }));
    }
}