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

package com.github.lehjr.powersuits.client.gui.modechanging;

import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.ModeChangeRequestPacket;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableModule;
import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.*;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;

public class RadialModeSelectionFrame implements IGuiFrame {
    boolean visible = true;
    boolean enabled = true;
    protected final long spawnTime;
    protected List<ClickableModule> modeButtons = new ArrayList<>();
    protected int selectedModuleOriginal = -1;
    protected int selectedModuleNew = -1;

    protected PlayerEntity player;
    protected MusePoint2D center;
    protected double radius;
    protected ItemStack stack;
    RelativeRect rect;
    float zLevel;

    public RadialModeSelectionFrame(MusePoint2D topleft, MusePoint2D bottomright, PlayerEntity player, float zLevel) {
        spawnTime = System.currentTimeMillis();
        this.player = player;
        rect = new RelativeRect(topleft, bottomright);
        center = rect.center();
        this.radius = Math.min(rect.height(), rect.width());
        this.stack = player.inventory.getSelected();
        this.zLevel = zLevel;
        loadItems();
    }

    @Override
    public IRect init(double left, double top, double right, double bottom) {
        rect.init(left, top, right, bottom);
        center = rect.center();
        this.radius = Math.min(rect.height(), rect.width());
        modeButtons.clear();
        return this;
    }

    @Override
    public MusePoint2D getUL() {
        return rect.getUL();
    }

    @Override
    public MusePoint2D getWH() {
        return rect.getWH();
    }

    @Override
    public double left() {
        return rect.left();
    }

    @Override
    public double finalLeft() {
        return rect.finalLeft();
    }

    @Override
    public double top() {
        return rect.top();
    }

    @Override
    public double finalTop() {
        return rect.finalTop();
    }

    @Override
    public double right() {
        return rect.right();
    }

    @Override
    public double finalRight() {
        return rect.finalRight();
    }

    @Override
    public double bottom() {
        return rect.bottom();
    }

    @Override
    public double finalBottom() {
        return rect.finalBottom();
    }

    @Override
    public double width() {
        return rect.width();
    }

    @Override
    public double finalWidth() {
        return rect.finalWidth();
    }

    @Override
    public double height() {
        return rect.height();
    }

    @Override
    public double finalHeight() {
        return rect.finalHeight();
    }

    @Override
    public IRect setUL(MusePoint2D musePoint2D) {
        return rect.setUL(musePoint2D);
    }

    @Override
    public IRect setWH(MusePoint2D musePoint2D) {
        return rect.setWH(musePoint2D);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scroll) {
        return false;
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        return false;
    }

    @Override
    public void update(double mousex, double mousey) {
        //Update items
        loadItems();
        //Determine which mode is selected
        if (System.currentTimeMillis() - spawnTime > 250) {
            selectModule((float) mousex, (float) mousey);
        }
        //Switch to selected mode if mode changed
        if (getSelectedModule() != null && selectedModuleOriginal != selectedModuleNew) {
            // update to detect mode changes
            selectedModuleOriginal = selectedModuleNew;
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler->{
                if (handler instanceof IModeChangingItem) {
                    ((IModeChangingItem) handler).setActiveMode(getSelectedModule().getInventorySlot());
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ModeChangeRequestPacket(getSelectedModule().getInventorySlot(), player.inventory.selected));
                }
            });
        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, int mouseX, int mouseY, float partialTicks) {
        //Draw the installed power fist modes
        for (ClickableModule mode : modeButtons) {
            mode.render(matrixStackIn, mouseX, mouseY, partialTicks);
        }
        //Draw the selected mode indicator
        drawSelection(matrixStackIn);
    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float v) {
        return null;
    }

    private void loadItems() {
        if (player != null && modeButtons.isEmpty()) {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler->{
                if (handler instanceof IModeChangingItem) {
                    List<Integer> modes = ((IModeChangingItem) handler).getValidModes();
                    int activeMode = ((IModeChangingItem) handler).getActiveMode();
                    if (activeMode > 0)
                        selectedModuleOriginal = activeMode;
                    int modeNum = 0;
                    for (int mode : modes) {
                        ClickableModule clickie = new ClickableModule(handler.getStackInSlot(mode), new SpiralPointToPoint2D(center, radius, ((3D * Math.PI / 2) - ((2D * Math.PI * modeNum) / modes.size())), 250D), mode, EnumModuleCategory.NONE);
                        modeButtons.add(clickie);
                        modeNum ++;
                    }
                }
            });
        }
    }

    private void selectModule(float x, float y) {
        if (modeButtons != null) {
            int i = 0;
            for (ClickableModule module : modeButtons) {
                if (module.hitBox(x, y)) {
                    selectedModuleNew = i;
                    break;
                }
                i++;
            }
        }
    }

    public ClickableModule getSelectedModule() {
        if (modeButtons.size() > selectedModuleNew && selectedModuleNew != -1) {
            return modeButtons.get(selectedModuleNew);
        } else {
            return null;
        }
    }

    public void drawSelection(MatrixStack matrixStackIn) {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            MusePoint2D pos = module.getPosition();
            MuseRenderer.drawCircleAround(matrixStackIn, pos.getX(), pos.getY(), 10, zLevel);
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            return module.getToolTip(x, y);
        }
        return null;
    }

    @Override
    public IRect getRect() {
        return rect;
    }

    @Override
    public void setEnabled(boolean b) {
        enabled = b;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setVisible(boolean b) {
        visible = b;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public IRect setLeft(double v) {
        return rect.setLeft(v);
    }

    @Override
    public IRect setRight(double v) {
        return rect.setRight(v);
    }

    @Override
    public IRect setTop(double v) {
        return rect.setTop(v);
    }

    @Override
    public IRect setBottom(double v) {
        return rect.setBottom(v);
    }

    @Override
    public IRect setWidth(double v) {
        return rect.setWidth(v);
    }

    @Override
    public IRect setHeight(double v) {
        return rect.setHeight(v);
    }

    @Override
    public void move(MusePoint2D musePoint2D) {
        rect.move(musePoint2D);
    }

    @Override
    public void move(double v, double v1) {
        rect.move(v, v1);
    }

    @Override
    public void setPosition(MusePoint2D musePoint2D) {
        rect.setPosition(musePoint2D);
    }

    @Override
    public boolean growFromMiddle() {
        return rect.growFromMiddle();
    }

    @Override
    public void initGrowth() {
        rect.initGrowth();
    }

    @Override
    public IRect setMeLeftOf(IRect relativeRect) {
        return rect.setMeLeftOf(relativeRect);
    }

    @Override
    public IRect setMeRightOf(IRect relativeRect) {
        return rect.setMeRightOf(relativeRect);
    }

    @Override
    public IRect setMeAbove(IRect relativeRect) {
        return rect.setMeAbove(relativeRect);
    }

    @Override
    public IRect setMeBelow(IRect relativeRect) {
        return null;
    }

    @Override
    public void setOnInit(IInit iInit) {

    }

    @Override
    public void onInit() {

    }
}