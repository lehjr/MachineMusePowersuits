///*
// * Copyright (c) 2019 MachineMuse, Lehjr
// * All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *  * Redistributions of source code must retain the above copyright notice, this
// *    list of conditions and the following disclaimer.
// *
// *  * Redistributions in binary form must reproduce the above copyright notice,
// *    this list of conditions and the following disclaimer in the documentation
// *    and/or other materials provided with the distribution.
// *
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.numina.client.gui.slot;
//
//import lehjr.numina.client.gui.clickable.IClickable;
//import lehjr.numina.client.gui.geometry.MusePoint2D;
//import lehjr.numina.client.gui.geometry.SpiralPointToPoint2D;
//import lehjr.numina.client.render.MuseRenderer;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.util.text.Component;
//
//import java.util.List;
//
//public class ClickableItemSlotWrapper implements IClickable {
//    protected IPressable onPressed;
//    protected IReleasable onReleased;
//
//    public static final int offsetx = 8;
//    public static final int offsety = 8;
//
//    protected Slot slot;
//    boolean isEnabled = true;
//    boolean isVisible = true;
//
//
//    SpiralPointToPoint2D spiralPosition;
//    public ClickableItemSlotWrapper(Slot slot, SpiralPointToPoint2D position) {
//        this.slot = slot;
//        this.spiralPosition = position;
//    }
//
//
//    public ClickableItemSlotWrapper(Slot slot, MusePoint2D position) {
//        this.slot = slot;
//        this.move(position);
//    }
//
//    public ClickableItemSlotWrapper(Slot slot) {
//        this.slot = slot;
//    }
//
//    @Override
//    public void render(int mouseX, int mouseY, float partialTicks) {
//        if(spiralPosition != null)
//            move(spiralPosition);
//
//        MuseRenderer.drawItemAt(
//                getPosition().getX() - offsetx,
//                getPosition().getY() - offsety, slot.getStack());
//    }
//
//    @Override
//    public void move(float x, float y) {
//        slot.xPos = (int) x;
//        slot.yPos = (int) y;
//    }
//
//    @Override
//    public MusePoint2D getPosition() {
//        return new MusePoint2D(slot.xPos, slot.yPos);
//    }
//
//    @Override
//    public void setPosition(MusePoint2D position) {
//        this.move(position.getX(), position.getY());
//    }
//
//    @Override
//    public boolean hitBox(float x, float y) {
//        boolean hitx = Math.abs(x - getPosition().getX()) < offsetx;
//        boolean hity = Math.abs(y - getPosition().getY()) < offsety;
//        return hitx && hity;
//    }
//
//    @Override
//    public List<Component> getToolTip() {
//        return !slot.getStack().isEmpty() ? slot.getStack().getTooltip(Minecraft.getInstance().player, ITooltipFlag.TooltipFlags.NORMAL) : null;
//    }
//
//    @Override
//    public void setEnabled(boolean enabled) {
//        this.isEnabled = enabled;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    @Override
//    public void setVisible(boolean visible) {
//        this.isVisible = visible;
//    }
//
//    @Override
//    public boolean isVisible() {
//        return isVisible;
//    }
//
//    @Override
//    public void setOnPressed(IPressable onPressed) {
//        this.onPressed = onPressed;
//    }
//
//    @Override
//    public void setOnReleased(IReleasable onReleased) {
//        this.onReleased = onReleased;
//    }
//
//    @Override
//    public void onReleased() {
//        if (this.isVisible && this.isEnabled && this.onReleased != null) {
//            this.onReleased.onReleased(this);
//        }
//    }
//
//    @Override
//    public void onPressed() {
//        if (this.isVisible && this.isEnabled && this.onPressed != null) {
//            this.onPressed.onPressed(this);
//        }
//    }
//}