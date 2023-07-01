package net.machinemuse.powersuits.client.gui.cosmetic;

import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.geometry.MuseRect;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.powersuits.client.gui.common.ItemSelectionFrame;
import net.machinemuse.powersuits.common.config.MPSConfig;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CosmeticPresetContainer extends ScrollableFrame {
    public ItemSelectionFrame itemSelect;
    public ColourPickerFrame colourSelect;
    public MusePoint2D topleft;
    public MusePoint2D bottomright;
    public Integer lastItemSlot;
    public List<CosmeticPresetSelectionSubframe> presetFrames;
    protected boolean enabled;
    protected boolean visibile;

    public CosmeticPresetContainer(ItemSelectionFrame itemSelect,
                                   ColourPickerFrame colourSelect,
                                   MusePoint2D topleft,
                                   MusePoint2D bottomright,
                                   Colour borderColour,
                                   Colour insideColour) {
        super(new DrawableMuseRect(topleft, bottomright, borderColour, insideColour));
        this.itemSelect = itemSelect;
        this.colourSelect = colourSelect;
        this.topleft = topleft;
        this.bottomright = bottomright;
        this.lastItemSlot = null;
        this.presetFrames = getPresetFrames();
        this.visibile = true;
        this.enabled = true;
    }

    @Nonnull
    public ItemStack getItem() {
        return (itemSelect.getSelectedItem() != null) ? itemSelect.getSelectedItem().getItem() : ItemStack.EMPTY;
    }

    @Nullable
    public Integer getItemSlot() {
        return (itemSelect.getSelectedItem() != null) ? itemSelect.getSelectedItem().inventorySlot : null;
    }

    public List<CosmeticPresetSelectionSubframe> getPresetFrames() {
        List<CosmeticPresetSelectionSubframe> cosmeticFrameList = new ArrayList<>();
        CosmeticPresetSelectionSubframe newFrame;
        CosmeticPresetSelectionSubframe prev = null;
        for (String name :  MPSConfig.INSTANCE.getCosmeticPresets(getItem()).keySet()) {
            newFrame = createNewFrame(name, prev);
            prev = newFrame;
            cosmeticFrameList.add(newFrame);
        }
        return cosmeticFrameList;
    }

    public CosmeticPresetSelectionSubframe createNewFrame(String label, CosmeticPresetSelectionSubframe prev) {
        MuseRect newborder = new MuseRect(left() + 8, top() + 10, right(), top() + 24);
        newborder.setBelow((prev != null) ? prev.border : null);
        return new CosmeticPresetSelectionSubframe(label, new MusePoint2D(newborder.left(), newborder.centerY()),  this.itemSelect, newborder);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (enabled) {
            if (button == 0) {
                for (CosmeticPresetSelectionSubframe frame : presetFrames) {
                    if (frame.hitbox(mouseX, mouseY))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);

        if (enabled) {
            if (!Objects.equals(lastItemSlot, getItemSlot())) {
                lastItemSlot = getItemSlot();

                presetFrames = getPresetFrames();
                double x = 0;
                for (CosmeticPresetSelectionSubframe subframe : presetFrames) {
//                subframe.updateItems();
                    x += subframe.border.bottom();
                }
                this.totalSize = (int) x;
//        }
                if (colourSelect.decrAbove > -1) {
//            decrAbove(colourSelect.decrAbove);
                    colourSelect.decrAbove = -1;
                }
            }
        }
    }

    public void hide () {
        visibile = false;
    }

    public void show() {
        visibile = true;
    }

    public boolean isVisible() {
        return visibile;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        if (visibile) {
            super.preRender(mouseX, mouseY, partialTicks);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (double) (-this.currentScrollPixels), 0.0);
            for (CosmeticPresetSelectionSubframe f : presetFrames) {
                f.render(mouseX, mouseY, partialTicks);
            }
            GL11.glPopMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }
}
