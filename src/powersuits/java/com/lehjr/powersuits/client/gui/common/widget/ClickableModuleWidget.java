package com.lehjr.powersuits.client.gui.common.widget;

import com.lehjr.powersuits.client.gui.common.selection.modularitem.ModularItemSelectionTab;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

/**
 * TODO: 3 states ("no ingredients/disabled", "can craft", "installed"
 *
 *
 *
 *
 */
public class ClickableModuleWidget {
    State state;





    public enum State {
        CRAFTABLE(0,0),
        DISABLED(0, 0),
        INSTALLED(0,0);

        boolean hovered = false;



        int width = 18;
        int height = 18;
        int textStartLeft;
        int textStartTop;




        final ResourceLocation background = ModularItemSelectionTab.BACKGROUND_LOCATION;
        private State(int left, int top) {

        }

        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {


        }

        boolean isMouseOver

    }
}
