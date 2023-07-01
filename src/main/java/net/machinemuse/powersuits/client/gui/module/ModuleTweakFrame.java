package net.machinemuse.powersuits.client.gui.module;

import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.common.module.IPowerModule;
import net.machinemuse.numina.common.nbt.MuseNBTUtils;
import net.machinemuse.numina.common.nbt.propertymodifier.IPropertyModifier;
import net.machinemuse.numina.common.nbt.propertymodifier.PropertyModifierLinearAdditiveDouble;
import net.machinemuse.numina.common.string.MuseStringUtils;
import net.machinemuse.powersuits.common.constants.MPSModuleConstants;
import net.machinemuse.powersuits.client.gui.common.ItemSelectionFrame;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.machinemuse.numina.client.gui.clickable.ClickableItem;
import net.machinemuse.powersuits.client.gui.common.ClickableTinkerSlider;
import net.machinemuse.powersuits.common.network.MPSPackets;
import net.machinemuse.powersuits.common.network.packets.MusePacketTweakRequestDouble;
import net.machinemuse.powersuits.common.powermodule.PowerModuleBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class ModuleTweakFrame extends ScrollableFrame {
    protected static double SCALERATIO = 0.75;
    protected static int margin = 4;
    protected ItemSelectionFrame itemTarget;
    protected ModuleSelectionFrame moduleTarget;
    protected List<ClickableTinkerSlider> sliders;
    protected Map<String, Double> propertyStrings;
    protected ClickableTinkerSlider selectedSlider;
    protected EntityPlayerSP player;

    public ModuleTweakFrame(
            EntityPlayerSP player,
            MusePoint2D topleft,
            MusePoint2D bottomright,
            Colour borderColour,
            Colour insideColour,
            ItemSelectionFrame itemTarget,
            ModuleSelectionFrame moduleTarget) {
        super(new DrawableMuseRect(topleft.times(1 / SCALERATIO), bottomright.times(1 / SCALERATIO), borderColour, insideColour));
        this.itemTarget = itemTarget;
        this.moduleTarget = moduleTarget;
        this.player = player;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        mouseX /= SCALERATIO;
        if (itemTarget.getSelectedItem() != null && moduleTarget.getSelectedModule() != null) {
            ItemStack stack = itemTarget.getSelectedItem().getItem();
            IPowerModule module = moduleTarget.getSelectedModule().getModule();
            if (ModuleManager.INSTANCE.itemHasModule(itemTarget.getSelectedItem().getItem(), moduleTarget.getSelectedModule().getModule().getDataName())) {
                loadTweaks(stack, module);
            } else {
                sliders = null;
                propertyStrings = null;
            }
        } else {
            sliders = null;
            propertyStrings = null;
        }
        if (selectedSlider != null) {
            selectedSlider.setValueByX(mouseX);
        }
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        if (sliders != null) {
            GL11.glPushMatrix();
            GL11.glScaled(SCALERATIO, SCALERATIO, SCALERATIO);
            super.render(mouseX, mouseY, partialTicks);
            MuseRenderer.drawCenteredString("Tinker", (left() + right()) / 2, top() + 2);
            for (ClickableTinkerSlider slider : sliders) {
                slider.render(mouseX, mouseY, partialTicks);
            }
            int nexty = (int) (sliders.size() * 20 + top() + 23);
            for (Map.Entry<String, Double> property : propertyStrings.entrySet()) {
                String formattedValue = MuseStringUtils.formatNumberFromUnits(property.getValue(), PowerModuleBase.getUnit(property.getKey()));
                String name = property.getKey();
                double valueWidth = MuseRenderer.getStringWidth(formattedValue);
                double allowedNameWidth = width() - valueWidth - margin * 2;

                List<String> namesList = MuseStringUtils.wrapStringToVisualLength(
                        I18n.format(MPSModuleConstants.MODULE_TRADEOFF_PREFIX + name), allowedNameWidth);
                for (int i = 0; i < namesList.size(); i++) {
                    MuseRenderer.drawString(namesList.get(i), left() + margin, nexty + 9 * i);
                }
                MuseRenderer.drawRightAlignedString(formattedValue, right() - margin, nexty + 9 * (namesList.size() - 1) / 2);
                nexty += 9 * namesList.size() + 1;

            }
            GL11.glPopMatrix();
        }
    }

    private void loadTweaks(ItemStack stack, IPowerModule module) {
        NBTTagCompound itemTag = MuseNBTUtils.getMuseItemTag(stack);
        NBTTagCompound moduleTag = itemTag.getCompoundTag(module.getDataName());

        propertyStrings = new HashMap();
        Set<String> tweaks = new HashSet<String>();

        Map<String, List<IPropertyModifier>> propertyModifiers = module.getPropertyModifiers();
        for (Map.Entry<String, List<IPropertyModifier>> property : propertyModifiers.entrySet()) {
            double currValue = 0;
            for (IPropertyModifier modifier : property.getValue()) {
                currValue = (double) modifier.applyModifier(moduleTag, currValue);
                if (modifier instanceof PropertyModifierLinearAdditiveDouble) {
                    tweaks.add(((PropertyModifierLinearAdditiveDouble) modifier).getTradeoffName());
                }
            }
            propertyStrings.put(property.getKey(), currValue);
        }

        sliders = new LinkedList();
        int y = 0;
        for (String tweak : tweaks) {
            y += 20;
            MusePoint2D center = new MusePoint2D((left() + right()) / 2, top() + y);
            ClickableTinkerSlider slider = new ClickableTinkerSlider(
                    center,
                    right() - left() - 8,
                    moduleTag,
                    tweak, I18n.format(MPSModuleConstants.MODULE_TRADEOFF_PREFIX + tweak));
            sliders.add(slider);
            if (selectedSlider != null && slider.containsPoint(center.getX(), center.getY())) {
                selectedSlider = slider;
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX /= SCALERATIO;
        mouseY /= SCALERATIO;
        if (button == 0) {
            if (sliders != null) {
                for (ClickableTinkerSlider slider : sliders) {
                    if (slider.containsPoint(mouseX, mouseY)) {
                        selectedSlider = slider;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (selectedSlider != null && itemTarget.getSelectedItem() != null && moduleTarget.getSelectedModule() != null) {
            ClickableItem item = itemTarget.getSelectedItem();
            IPowerModule module = moduleTarget.getSelectedModule().getModule();
            MPSPackets.sendToServer(
                    new MusePacketTweakRequestDouble(player, item.inventorySlot, module.getDataName(), selectedSlider.id(), selectedSlider.getValue()));
            return true;
        }
        if (button == 0) {
            selectedSlider = null;
            return true;
        }
        return false;
    }
}