package com.github.lehjr.powersuits.client.gui.modding.cosmetic;

import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
                                   float zLevel,
                                   Colour borderColour,
                                   Colour insideColour) {
        super(topleft, bottomright, zLevel, borderColour, insideColour);
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
        return (itemSelect.getSelectedItem() != null) ? itemSelect.getSelectedItem().getStack() : ItemStack.EMPTY;
    }

    @Nullable
    public Integer getItemSlot() {
        return (itemSelect.getSelectedItem() != null) ? itemSelect.getSelectedItem().getSlotIndex() : null;
    }

    public List<CosmeticPresetSelectionSubframe> getPresetFrames() {
        List<CosmeticPresetSelectionSubframe> cosmeticFrameList = new ArrayList<>();
//        CosmeticPresetSelectionSubframe newFrame;
//        CosmeticPresetSelectionSubframe prev = null;
//        for (String name :  MPSSettings::getModuleConfig.getCosmeticPresets(getItem()).keySet()) {
//            newFrame = createNewFrame(name, prev);
//            prev = newFrame;
//            cosmeticFrameList.add(newFrame);
//        }
        return cosmeticFrameList;
    }

    public CosmeticPresetSelectionSubframe createNewFrame(String label, CosmeticPresetSelectionSubframe prev) {
        MuseRelativeRect newborder = new MuseRelativeRect(this.border.left() + 8, this.border.top() + 10, this.border.right(), this.border.top() + 24);
        newborder.setMeBelow((prev != null) ? prev.border : null);
        return new CosmeticPresetSelectionSubframe(label, new MusePoint2D(newborder.left(), newborder.centery()),  this.itemSelect, newborder);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (enabled) {
            if (button == 0) {
                for (CosmeticPresetSelectionSubframe frame : presetFrames) {
                    if (frame.hitbox(x, y))
                        return true;
                }
            }
        }
        return false;
    }

//    @Override
//    public void update(double mouseX, double mouseY) {
//        super.update(mouseX, mouseY);
//
//        if (enabled) {
//            if (!Objects.equals(lastItemSlot, getItemSlot())) {
//                lastItemSlot = getItemSlot();
//
//                presetFrames = getPresetFrames();
//                double x = 0;
//                for (CosmeticPresetSelectionSubframe subframe : presetFrames) {
////                subframe.updateItems();
//                    x += subframe.border.bottom();
//                }
//                this.totalsize = (int) x;
////        }
//                if (colourSelect.decrAbove > -1) {
////            decrAbove(colourSelect.decrAbove);
//                    colourSelect.decrAbove = -1;
//                }
//            }
//        }
//    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)  {
        if (isVisible()) {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (double) (-this.currentscrollpixels), 0.0);
            for (CosmeticPresetSelectionSubframe f : presetFrames) {
                f.render(matrixStack, mouseX, mouseY, partialTicks, getzLevel());
            }
            GL11.glPopMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }
}
