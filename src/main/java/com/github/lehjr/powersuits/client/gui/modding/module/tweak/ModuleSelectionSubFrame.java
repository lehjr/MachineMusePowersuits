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

package com.github.lehjr.powersuits.client.gui.modding.module.tweak;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableModule;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.client.sound.Musique;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ModuleSelectionSubFrame {
    protected List<ClickableModule> moduleButtons;
    protected RelativeRect border;
    protected EnumModuleCategory category;

    public ModuleSelectionSubFrame(EnumModuleCategory category, RelativeRect border) {
        this.category = category;
        this.border = border;
        this.moduleButtons = new ArrayList<>();
    }

    public ClickableModule addModule(@Nonnull ItemStack module, int index) {
        ClickableModule clickie = new ClickableModule(module, new MusePoint2D(0, 0), index, category);
        this.moduleButtons.add(clickie);
        refreshButtonPositions();
        return clickie;
    }

    public void drawPartial(MatrixStack matrixStack, int min, int max, float partialTicks) {
        refreshButtonPositions();
        MuseRenderer.drawShadowedString(matrixStack, this.category.getTranslation().getString(), border.left(), border.top(), Colour.WHITE);
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

    int selectedModule = -1;

    public ClickableModule getSelectedModule() {
        if (selectedModule >=0)
            return moduleButtons.get(selectedModule);
        return null;
    }

    public void resetSelection(){
        this.selectedModule = -1;
    }

    public boolean mouseClicked(double x, double y, int button) {
        if (border.containsPoint(x, y)) {
            for (ClickableModule module : moduleButtons) {
                if (module.hitBox((float) x, (float)y)) {
                    Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
                    selectedModule = moduleButtons.indexOf(module);
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
                    if (module.hitBox(x, y)) {
                        List<ITextComponent> toolTipText = new ArrayList<>();
                        toolTipText.add(module.getLocalizedName());
                        toolTipText.add(module.getLocalizedDescription());
                        return toolTipText;
                    }
                }
            }
        }
        return null;
    }

    public RelativeRect getBorder() {
        return border;
    }
}