/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.client.gui.modding.module.tweak;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.ClickableModule;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ModuleSelectionSubFrame {
    public List<ClickableModule> moduleButtons;
    public int selectedModule = -1;
    int oldSelected = -1;
    protected Rect border;
    public ModuleCategory category;

    public ModuleSelectionSubFrame(ModuleCategory category, Rect border) {
        this.category = category;
        this.border = border;
        this.moduleButtons = new ArrayList<>();
    }

    public ClickableModule addModule(@Nonnull ItemStack module, int index) {
        ClickableModule clickie = new ClickableModule(module, new MusePoint2D(0, 0), index, category);
        this.moduleButtons.add(clickie);
        Collections.sort(moduleButtons, Comparator.comparing(ClickableModule::getTier));
        refreshButtonPositions();
        return clickie;
    }

    public void drawPartial(MatrixStack matrixStack, int min, int max, float partialTicks) {
        refreshButtonPositions();
        StringUtils.drawShadowedString(matrixStack, this.category.getTranslation().getString(), border.left(), border.top(), Colour.WHITE);
        for (ClickableModule clickie : moduleButtons) {
            clickie.render(matrixStack, min, max, partialTicks);
        }
    }

    public void refreshButtonPositions() {
        int col = 0, row = 0;
        int topMargin = 20;
        int leftMargin = 10;

        for (ClickableModule clickie : moduleButtons) {
            if (col > 4) {
                col = 0;
                row++;
            }
            double x = border.left() + leftMargin + 20 * col;
            double y = border.top() + topMargin + 20 * row;
            clickie.setPosition(new MusePoint2D(x, y));
            col++;
        }
        // top and bottom margin + iconHeight * rows
        border.setHeight(topMargin * 2 + 20 * row);
    }

    public ClickableModule getSelectedModule() {
        if (selectedModule >=0) {
            return moduleButtons.get(selectedModule);
        }
        return null;
    }

    public void resetSelection(){
        this.selectedModule = -1;
        this.oldSelected = -1;
    }

    public boolean mouseClicked(double x, double y, int button) {
        if (border.containsPoint(x, y)) {
            for (ClickableModule module : moduleButtons) {
                if (module.containsPoint((float) x, (float)y)) {
                    Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
                    selectedModule = moduleButtons.indexOf(module);
                    if (selectedModule != oldSelected) {
                        oldSelected = selectedModule;
                        onSelected();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: better tooltips? Fix clickable module tooltips at source instead of this workaround?
    public List<ITextComponent> getToolTip(int x, int y) {
        if (border.containsPoint(x, y)) {
            if (moduleButtons != null) {
                for (ClickableModule module : moduleButtons) {
                    if (module.containsPoint(x, y)) {
                        return module.getToolTip(x, y);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sets code to be executed when a new item is selected
     * @param doThisIn
     */
    OnSelectNewModule doThis;
    public void setDoOnNewSelect(OnSelectNewModule doThisIn) {
        doThis = doThisIn;
    }

    /**
     * runs preset code when new module is selected
     */
    void onSelected() {
        if(this.doThis != null) {
            this.doThis.onSelected(this);
        }
    }

    public interface OnSelectNewModule {
        void onSelected(ModuleSelectionSubFrame doThis);
    }
}