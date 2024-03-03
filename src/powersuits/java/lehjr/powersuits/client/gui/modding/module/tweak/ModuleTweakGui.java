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

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.ContainerlessGui;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.Optional;

/**
 * Requires all module and inventory slots be accounted for before constructing
 *
 *
 */
public class ModuleTweakGui extends ContainerlessGui {
    Component MODULE_SELECTION_LABEL = Component.translatable("gui.powersuits.installed.modules");
    Component TINKER_FRAME_LABEL = Component.translatable("gui.powersuits.tinker");
    Component SUMMARY_FRAME_LABEL = Component.translatable(MPSConstants.GUI_EQUIPPED_TOTALS);

    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/background/module_tweak.png");
    protected ModularItemSelectionFrame itemSelectFrame;
    protected ModuleSelectionFrame moduleSelectFrame;
    protected DetailedSummaryFrame summaryFrame;
    protected ModuleTweakFrame tweakFrame;
    protected TabSelectFrame tabSelectFrame;
    LocalPlayer player;

    public ModuleTweakGui(Component title) {
        super(title, 352, 217);
        this.minecraft = Minecraft.getInstance();
        this.player = Minecraft.getInstance().player;
    }

    @Override
    public void init() {
        super.init();
        frames.clear();

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

        /** for selecting GUI ------------------------------------------------------------------ */
        tabSelectFrame = new TabSelectFrame(this.leftPos, this.topPos, this.imageWidth, player, 1);
        tabSelectFrame.setPosition(center());
        tabSelectFrame.setBottom(topPos);
        addFrame(tabSelectFrame);

        /** frame to display and allow selecting of installed modules -------------------------------------------------- */
        boolean keepOnReload = moduleSelectFrame != null;
        moduleSelectFrame = new ModuleSelectionFrame(itemSelectFrame, new Rect(leftPos + 8, topPos + 13, leftPos + 172, topPos + 207));
        moduleSelectFrame.loadModules(keepOnReload); // <- this probably won't make any difference
        addFrame(moduleSelectFrame);

        /** setup call to make the modules reload when new button pressed */
        itemSelectFrame.setOnChanged(()-> {
            moduleSelectFrame.selectedModule = Optional.ofNullable(null);
            moduleSelectFrame.loadModules(false);
            tweakFrame.resetScroll();
        });

        summaryFrame = new DetailedSummaryFrame(new Rect(leftPos + 176, topPos + 12, leftPos + 344,topPos + 46),
                itemSelectFrame);
        addFrame(summaryFrame);

        tweakFrame = new ModuleTweakFrame(new Rect(leftPos + 176, topPos + 58, leftPos + 345, topPos + 208),
        itemSelectFrame,
        moduleSelectFrame);
        addFrame(tweakFrame);
   }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        super.render(gfx, mouseX, mouseY, partialTicks);
        renderTooltip(gfx, mouseX, mouseY);
    }

    @Override
    public void renderBackground(GuiGraphics gfx) {
        super.renderBackground(gfx);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        gfx.blit(BACKGROUND, i, j, 0, 0, imageWidth, imageHeight, 512, 512);
    }

    @Override
    public void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;

        gfx.drawString(font,
                this.MODULE_SELECTION_LABEL,
                leftPos + 12,
                topPos + 5,
                4210752,
                false);

        gfx.drawString(font,
                this.TINKER_FRAME_LABEL,
                leftPos + 184,
                topPos + 49,
                4210752,
                false);

        gfx.drawString(font,
                this.SUMMARY_FRAME_LABEL,
                leftPos + 184,
                topPos + 5,
                4210752,
                false);
    }
}