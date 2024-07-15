package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.platform.Lighting;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.AdditionalInfo;
import lehjr.numina.common.utils.IconUtils;
import lehjr.numina.common.utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Extends the Clickable class to make a clickable Augmentation; note that this
 * will not be an actual item.
 *
 * @author MachineMuse
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class ClickableModule extends Clickable {
    boolean allowed;
    boolean installed = false;
    boolean isEnabled = true;
    boolean isVisible = true;
    ItemStack module;
    int inventorySlot;
    public final ModuleCategory category;
    Integer tier;
    ResourceLocation regName;

    public ClickableModule(@Nonnull ItemStack module, MusePoint2D position, int inventorySlot, ModuleCategory category) {
        super(MusePoint2D.ZERO, new MusePoint2D(16, 16));
        super.setPosition(position);
        this.module = module;
        this.inventorySlot = inventorySlot;
        this.category = category;
        allowed = NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).map(IPowerModule::isAllowed).orElse(false);
        tier = NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).map(IPowerModule::getTier).orElse(null);
        this.regName = BuiltInRegistries.ITEM.getKey(module.getItem());
    }

    @Nullable
    public Integer getTier() {
        return tier;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        if (containsPoint(x, y)) {
            return module.getTooltipLines(Item.TooltipContext.of(Minecraft.getInstance().level),
                    Minecraft.getInstance().player,Screen.hasShiftDown() ?
                    TooltipFlag.Default.ADVANCED :
                    TooltipFlag.Default.NORMAL);
        }
        return null;
    }

    public ResourceLocation getRegName() {
        return this.regName;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        // TODO: extra text and options to disable if player doesn't have the module available

        if (!getModule().isEmpty()) {
            IconUtils.drawModuleAt(gfx, left(), top(), getModule(), true);
            Lighting.setupForFlatItems();
            if (!allowed) {
                gfx.pose().pushPose();
                gfx.pose().translate(0, 0, 250);
                String string = StringUtils.wrapMultipleFormatTags("X", StringUtils.FormatCodes.Bold, StringUtils.FormatCodes.DarkRed);
                StringUtils.drawShadowedString(gfx, string, centerX() + 3, centerY() + 1);
                gfx.pose().popPose();
            } else if (installed) {
                gfx.pose().pushPose();
                gfx.pose().translate(0, 0,250);
                IconUtils.getIcon().checkmark.draw(gfx.pose(), left() + 1, top() + 1, Color.LIGHT_GREEN);
                gfx.pose().popPose();
            }
        }
    }

    @Nonnull
    public ItemStack getModule() {
        return module;
    }

    public boolean equals(ClickableModule other) {
        return this.module == other.getModule();
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public boolean isInstalled() {
        return installed;
    }
}