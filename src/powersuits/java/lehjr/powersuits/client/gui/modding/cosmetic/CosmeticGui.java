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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.ContainerlessGui;
import lehjr.numina.client.gui.frame.EntityRenderFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.client.gui.modding.cosmetic.colourpicker.ColourPickerFrame;
import lehjr.powersuits.client.gui.modding.cosmetic.partmanip.ModelManipFrame;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:32 PM, 29/04/13
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class CosmeticGui extends ContainerlessGui {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/background/cosmetic.png");
    Player player;
    ModularItemSelectionFrame itemSelectFrame;
    EntityRenderFrame renderframe;
    ColourPickerFrame colourpicker;
    ModelManipFrame partframe;
    TabSelectFrame tabSelectFrame;
    protected boolean allowCosmeticPresetCreation;
    protected boolean usingCosmeticPresets;

    public CosmeticGui(Inventory inventory, Component title) {
        super(title, 352, 217);
        this.player = inventory.player;
        this.minecraft = Minecraft.getInstance();
    }

    /**
     * Add the buttons (and other controls) to the screen.
     */
    @Override
    public void init() {
        super.init();
        frames.clear();
        usingCosmeticPresets = !MPSSettings.useLegacyCosmeticSystem();
        if (usingCosmeticPresets) {
            // check if player is the server owner
            if (minecraft.hasSingleplayerServer()) {
                allowCosmeticPresetCreation = player.getName().equals(minecraft.getSingleplayerServer().getSingleplayerProfile().getName());
            } else {
                // check if player is top level op
                ServerOpListEntry opEntry = minecraft.player.getServer().getPlayerList().getOps().get(player.getGameProfile());
                int opLevel = opEntry != null ? opEntry.getLevel() : 0;
                allowCosmeticPresetCreation = opLevel == 4;
            }
        } else {
            allowCosmeticPresetCreation = false;
        }

        /** for selecting the item to manipulate ------------------------------------------------ */
        EquipmentSlot type;
        if (itemSelectFrame != null) {
            type = itemSelectFrame.selectedType().orElse(EquipmentSlot.HEAD);
        } else {
            type = EquipmentSlot.HEAD;
        }
        itemSelectFrame = new ModularItemSelectionFrame(new MusePoint2D(leftPos - 30, topPos), type);
        itemSelectFrame.refreshRects();
        addFrame(itemSelectFrame);

        /** renders the player ----------------------------------------------------------------- */
        renderframe = new EntityRenderFrame(leftPos + 220, topPos + 14, leftPos + 344, topPos + 86);
        renderframe.setLivingEntity(getMinecraft().player);
        renderframe.setAllowDrag(true);
        renderframe.setAllowZoom(true);
        addFrame(renderframe);

        /** for picking the colours ------------------------------------------------------------ */
        colourpicker = new ColourPickerFrame(itemSelectFrame, leftPos + 220, topPos +91, leftPos + 344, topPos + 207);
        addFrame(colourpicker);

        /** for manipulating part selections --------------------------------------------------- */
        partframe = new ModelManipFrame(leftPos + 8, topPos + 14, leftPos + 211, topPos + 207, itemSelectFrame, colourpicker);
        addFrame(partframe);

        /** for selecting GUI ------------------------------------------------------------------ */
        tabSelectFrame = new TabSelectFrame(this.leftPos, this.topPos, this.imageWidth, player, 3);
        tabSelectFrame.setPosition(center());
        tabSelectFrame.setBottom(topPos);
        addFrame(tabSelectFrame);

        /** for selecting modular item to adjust settings for ---------------------------------- */
//        partframe.setWH(new MusePoint2D(201, 195));
//        partframe.setUL(new MusePoint2D(leftPos + 7, topPos + 13));

        // TODO
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
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public void renderBackground(PoseStack matrixStack) {
        super.renderBackground(matrixStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight, 512, 512);
    }

    @Override
    public void removed() {
        super.removed();
//        loadSaveResetSubFrame.onClose();
    }
}