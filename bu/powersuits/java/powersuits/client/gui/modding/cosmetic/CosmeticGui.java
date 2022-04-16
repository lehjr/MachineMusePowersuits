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

package lehjr.powersuits.client.gui.modding.cosmetic;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.util.client.gui.ContainerlessGui;
import lehjr.numina.util.client.gui.frame.EntityRenderFrame;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.config.MPSSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.server.management.OpEntry;
import net.minecraft.util.text.ITextComponent;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:32 PM, 29/04/13
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class CosmeticGui extends ContainerlessGui {
    Player player;
    final int spacer = 7;

    ModularItemSelectionFrame itemSelectFrame;
    EntityRenderFrame renderframe;
    ColourPickerFrame colourpicker;
    PartManipContainer partframe;


    TabSelectFrame tabSelectFrame;

    protected final boolean allowCosmeticPresetCreation;
    protected final boolean usingCosmeticPresets;

    public CosmeticGui(PlayerInventory inventory, ITextComponent title) {
        super(title, 340, 217, false);
        this.player = inventory.player;
        this.minecraft = Minecraft.getInstance();

        usingCosmeticPresets = !MPSSettings.useLegacyCosmeticSystem();
        if (usingCosmeticPresets) {
            // check if player is the server owner
            if (minecraft.hasSingleplayerServer()) {
                allowCosmeticPresetCreation = player.getName().equals(minecraft.getSingleplayerServer().getSingleplayerName());
            } else {
                // check if player is top level op
                OpEntry opEntry = minecraft.player.getServer().getPlayerList().getOps().get(player.getGameProfile());
                int opLevel = opEntry != null ? opEntry.getLevel() : 0;
                allowCosmeticPresetCreation = opLevel == 4;
            }
        } else {
            allowCosmeticPresetCreation = false;
        }

        itemSelectFrame = new ModularItemSelectionFrame();
        addFrame(itemSelectFrame);

        /** renders the player ----------------------------------------------------------------- */
        renderframe = new EntityRenderFrame(false);
        renderframe.setWidth(120);
        renderframe.setHeight(90);
        renderframe.setLivingEntity(getMinecraft().player);
        renderframe.setAllowDrag(true);
        renderframe.setAllowZoom(true);
        addFrame(renderframe);

        /** for picking the colours ------------------------------------------------------------ */
        colourpicker = new ColourPickerFrame(itemSelectFrame, 120, 106) ;
        addFrame(colourpicker);

        /** for manipulating part selections --------------------------------------------------- */
        partframe = new PartManipContainer(itemSelectFrame, colourpicker);
        addFrame(partframe);

        /** for selecting GUI ------------------------------------------------------------------ */
        tabSelectFrame = new TabSelectFrame(player, 3);
        addFrame(tabSelectFrame);

        /** for selecting modular item to adjust settings for ---------------------------------- */
        itemSelectFrame.setDoThisOnChange(doThis -> partframe.refreshModelframes());
    }

    /**
     * Add the buttons (and other controls) to the screen.
     */
    @Override
    public void init() {
        super.init();
        tabSelectFrame.initFromBackgroundRect(this.backgroundRect);

        itemSelectFrame.setMeLeftOf(backgroundRect); // does nothing
        itemSelectFrame.setTop(backgroundRect.finalTop()); // displaces buttons
        itemSelectFrame.setRight(backgroundRect.finalLeft());  // displaces buttons
        itemSelectFrame.initGrowth();


        renderframe.setRight(backgroundRect.finalRight() - spacer);
        renderframe.setTop(backgroundRect.finalTop() + spacer);

        colourpicker.setTop(renderframe.finalBottom() + spacer);
        colourpicker.setRight(backgroundRect.finalRight() - spacer);

        partframe.init(
                backgroundRect.finalLeft()  + spacer,
                backgroundRect.finalTop() + spacer,
                colourpicker.finalLeft() - spacer,
                backgroundRect.finalBottom() - spacer
        );


//        // if not using presets then only the reset button is displayed
//        loadSaveResetSubFrame = new LoadSaveResetSubFrame(
//                colourpicker,
//                player,
//                new Rect(
//                        absX(0.18f),
//                        absY(-0.23f),
//                        absX(0.95f),
//                        absY(-0.025f)),
//                Colour.LIGHTBLUE.withAlpha(0.8F),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                itemSelect,
//                usingCosmeticPresets,
//                allowCosmeticPresetCreation,
//                partframe,
//                cosmeticFrame);
//        frames.add(loadSaveResetSubFrame);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (backgroundRect.width() == backgroundRect.finalWidth() && backgroundRect.height() == backgroundRect.finalHeight()) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            renderTooltip(matrixStack, mouseX, mouseY);
        } else {
            this.renderBackground(matrixStack);
        }
    }

//    @Override
//    public boolean mouseClicked(double x, double y, int button) {
//        itemSelectFrame.getModularItemOrEmpty().getCapability(ModelSpecNBTCapability.RENDER)
//                .filter(IModelSpecNBT.class::isInstance)
//                .map(IModelSpecNBT.class::cast).ifPresent(spec -> System.out.println(spec.getRenderTag()));
//        return super.mouseClicked(x, y, button);
//    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
    }

    @Override
    public void removed() {
        super.removed();
//        loadSaveResetSubFrame.onClose();
    }
    // FIXME!!! I have no idea what this return value should be based on... 0 documentation
//    @Override
//    public boolean charTyped(char typedChar, int keyCode) {
//        boolean ret = super.charTyped(typedChar, keyCode);
//        if (loadSaveResetSubFrame != null)
//            loadSaveResetSubFrame.charTyped(typedChar, keyCode);
//        return ret;
//    }
}