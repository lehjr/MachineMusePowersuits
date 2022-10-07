package com.lehjr.powersuits.client.gui.common.selection.module;

import com.lehjr.numina.client.gui.clickable.ClickableModule;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.RelativeRect;
import com.lehjr.numina.client.sound.Musique;
import com.lehjr.numina.client.sound.SoundDictionary;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.string.StringUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ModuleSelectionSubFrame {
    public List<ClickableModuleWidget> moduleButtons;
    public int selectedModule = -1;
    int oldSelected = -1;
    protected RelativeRect border;
    public ModuleCategory category;

    public ModuleSelectionSubFrame(ModuleCategory category, RelativeRect border) {
        this.category = category;
        this.border = border;
        this.moduleButtons = new ArrayList<>();
    }

    public ClickableModuleWidget addModule(@Nonnull ItemStack module, int index) {
        ClickableModuleWidget clickie = new ClickableModuleWidget(module, new MusePoint2D(0, 0), index, category);
        this.moduleButtons.add(clickie);
        Collections.sort(moduleButtons, Comparator.comparing(ClickableModuleWidget::getTier));
        refreshButtonPositions();
        return clickie;
    }

    public void drawPartial(PoseStack matrixStack, int min, int max, float partialTicks) {
        refreshButtonPositions();
        StringUtils.drawShadowedString(matrixStack, this.category.getTranslation().getString(), border.left(), border.top(), Color.WHITE);
        for (ClickableModuleWidget clickie : moduleButtons) {
            clickie.render(matrixStack, min, max, partialTicks);
        }
    }

    public void refreshButtonPositions() {
        int col = 0, row = 0;
        int topMargin = 20;
        int leftMargin = 10;

        for (ClickableModuleWidget clickie : moduleButtons) {
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

    public ClickableModuleWidget getSelectedModule() {
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
            for (ClickableModuleWidget module : moduleButtons) {
                if (module.hitBox(x, y)) {
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
    public List<Component> getToolTip(int x, int y) {
        // Widgets have their own tooltip render code ?


        if (border.containsPoint(x, y)) {
            if (moduleButtons != null) {
                for (ClickableModuleWidget module : moduleButtons) {
                    if (module.hitBox(x, y)) {
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