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

package com.github.lehjr.numina.client.gui;

import com.github.lehjr.numina.container.ArmorStandContainer;
import com.github.lehjr.numina.util.client.gui.ExtendedContainerScreen;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ArmorStandGui extends ExtendedContainerScreen<ArmorStandContainer> {
    protected DrawableRelativeRect backgroundRect;
    // player render frame = 72H x 32W
    DrawableTile playerBackground, armorStandBackground;
    protected InventoryFrame playerArmor, playerShield, armorStandArmor, armorStandHands, mainInventory, hotbar;
    final int slotWidth = 18;
    final int slotHeight = 18;
    int spacer = 7;
    ArmorStandEntity armorStandEntity;
    /** The old x position of the mouse pointer */
    private float oldMouseX = 0;
    /** The old y position of the mouse pointer */
    private float oldMouseY = 0;

    public ArmorStandGui(ArmorStandContainer containerIn, PlayerInventory inv, ITextComponent titleIn) {
        super(containerIn, inv, titleIn);

        backgroundRect = new DrawableRelativeRect(0, 0, 0, 0, true,
                new Colour(0.776F, 0.776F, 0.776F, 1F),
                Colour.BLACK);

        this.armorStandEntity = containerIn.getArmorStandEntity();

        // slot 0-3
        armorStandArmor = new InventoryFrame(this.container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset() -1,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                1, 4, new ArrayList<Integer>(){{
            IntStream.range(0, 4).forEach(i-> add(i));
        }});
        addFrame(armorStandArmor);

        // slot 3-5
        armorStandHands = new InventoryFrame(this.container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset() -1,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                1, 2, new ArrayList<Integer>(){{
            IntStream.range(4, 6).forEach(i-> add(i));
        }});
        addFrame(armorStandHands);

        // slot 6-9
        playerArmor = new InventoryFrame(this.container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset() -1,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                1, 4, new ArrayList<Integer>(){{
            IntStream.range(6, 10).forEach(i-> add(i));
        }});
        addFrame(playerArmor);

        // slot 10-36
        mainInventory = new InventoryFrame(this.container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset() -1,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                9, 3, new ArrayList<Integer>(){{
            IntStream.range(10, 37).forEach(i-> add(i));
        }});
        addFrame(mainInventory);

        // slot 37 -46
        hotbar = new InventoryFrame(this.container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset() -1,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                9, 1, new ArrayList<Integer>(){{
            IntStream.range(37, 46).forEach(i-> add(i));
        }});
        addFrame(hotbar);

        // slot 46
        playerShield = new InventoryFrame(this.container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset() -1,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                1, 1, new ArrayList<Integer>(){{
            IntStream.range(46, 47).forEach(i-> add(i));
        }});
        addFrame(playerShield);

        playerBackground = new DrawableTile(new MusePoint2D(0,0), new MusePoint2D(0, 0)).setBackgroundColour(Colour.BLACK);
        armorStandBackground= new DrawableTile(new MusePoint2D(0,0), new MusePoint2D(0, 0)).setBackgroundColour(Colour.BLACK);
    }

    MusePoint2D getUlOffset () {
        return new MusePoint2D(guiLeft + 8, guiTop + 8);
    }
    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        backgroundRect.setTargetDimensions(new MusePoint2D(getGuiLeft(), getGuiTop()), new MusePoint2D(getXSize(), getYSize()));

        armorStandArmor.setUlShift(getUlOffset());
        armorStandArmor.init(
                backgroundRect.finalRight() - spacer - slotWidth,
                backgroundRect.finalTop() + spacer,
                backgroundRect.finalRight() - spacer,
                backgroundRect.finalTop() + spacer + slotHeight * 4);
        armorStandArmor.setzLevel(1);

        armorStandBackground.setTargetDimensions(
                armorStandArmor.finalLeft() - 2 -48,
                armorStandArmor.finalTop(),
                armorStandArmor.finalLeft() - 2,
                armorStandArmor.finalBottom());

        armorStandHands.setUlShift(getUlOffset());
        armorStandHands.setWidth(slotWidth);
        armorStandHands.setHeight(slotHeight * 2);
        armorStandHands.setPosition(new MusePoint2D(backgroundRect.centerx(), armorStandBackground.finalTop() + slotHeight));
        armorStandHands.setzLevel(1);

        playerArmor.setUlShift(getUlOffset());
        playerArmor.init(
                backgroundRect.finalLeft() + spacer,
                backgroundRect.finalTop() + spacer,
                backgroundRect.finalLeft() + spacer + slotWidth,
                backgroundRect.finalTop() + spacer + slotHeight * 4);
        playerArmor.setzLevel(1);

        playerBackground.setTargetDimensions(
                playerArmor.finalRight() + 2,
                playerArmor.finalTop(),
                playerArmor.finalRight() + 2 + 48,
                playerArmor.finalBottom());

        hotbar.setUlShift(getUlOffset());
        hotbar.init(
                backgroundRect.finalLeft() + spacer,
                backgroundRect.finalBottom() - spacer - slotHeight,
                backgroundRect.finalLeft() + spacer + 9 * slotWidth,
                backgroundRect.finalBottom() - spacer);
        hotbar.setzLevel(1);

        mainInventory.setUlShift(getUlOffset());
        mainInventory.init(
                backgroundRect.finalLeft() + spacer,
                backgroundRect.finalBottom() - spacer - slotHeight - 3.25F - 3 * slotHeight,
                backgroundRect.finalLeft() + spacer + 9 * slotWidth,
                backgroundRect.finalBottom() - spacer - slotHeight - 3.25F);
        mainInventory.setzLevel(1);

        playerShield.setUlShift(getUlOffset());
        playerShield.setWidth(slotWidth);
        playerShield.setHeight(slotHeight);
        playerShield.setPosition(new MusePoint2D(backgroundRect.centerx(),
                playerBackground.finalBottom() - (slotHeight * 0.5)));
        playerShield.setzLevel(1);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        backgroundRect.draw(matrixStack, 0);
        super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, x, y);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawEntityOnScreen(this.guiLeft + 51, this.guiTop + 75, 30, (float)(this.guiLeft + 51) - this.oldMouseX, (float)(this.guiTop + 75 - 50) - this.oldMouseY, this.minecraft.player);
        drawEntityOnScreen((float) (armorStandBackground.centerx()), this.guiTop + 75, 30, (float)(this.guiLeft + 51) - this.oldMouseX, (float)(this.guiTop + 75 - 50) - this.oldMouseY, this.armorStandEntity);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        if (backgroundRect.height() == backgroundRect.finalHeight() && backgroundRect.width() == backgroundRect.finalWidth()) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            playerBackground.draw(matrixStack, 1);
            armorStandBackground.draw(matrixStack, 1);

            this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
            this.oldMouseX = (float) mouseX;
            this.oldMouseY = (float) mouseY;
        } else {
            backgroundRect.draw(matrixStack, 0);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
    }

    // coppied from player inventory
    public static void drawEntityOnScreen(float posX, float posY, float scale, float mouseX, float mouseY, LivingEntity livingEntity) {
        float f = (float)Math.atan(mouseX / 40.0F);
        float f1 = (float)Math.atan(mouseY / 40.0F);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(posX, posY, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale(scale, scale, scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.multiply(quaternion1);
        matrixstack.rotate(quaternion);
        float f2 = livingEntity.renderYawOffset;
        float f3 = livingEntity.rotationYaw;
        float f4 = livingEntity.rotationPitch;
        float f5 = livingEntity.prevRotationYawHead;
        float f6 = livingEntity.rotationYawHead;
        livingEntity.renderYawOffset = 180.0F + f * 20.0F;
        livingEntity.rotationYaw = 180.0F + f * 40.0F;
        livingEntity.rotationPitch = -f1 * 20.0F;
        livingEntity.rotationYawHead = livingEntity.rotationYaw;
        livingEntity.prevRotationYawHead = livingEntity.rotationYaw;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
        quaternion1.conjugate();
        entityrenderermanager.setCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderermanager.renderEntityStatic(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
        });
        irendertypebuffer$impl.finish();
        entityrenderermanager.setRenderShadow(true);
        livingEntity.renderYawOffset = f2;
        livingEntity.rotationYaw = f3;
        livingEntity.rotationPitch = f4;
        livingEntity.prevRotationYawHead = f5;
        livingEntity.rotationYawHead = f6;
        RenderSystem.popMatrix();
    }
}
