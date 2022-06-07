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

package com.lehjr.numina.client.gui.frame;


import com.lehjr.numina.client.gui.geometry.IRect;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;

public interface IGuiFrame<T extends IRect> extends GuiEventListener, IRect {

    void setRect(T rect);

    T getRect();

    default void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {

    }

    /**
     * @param mouseX
     * @param mouseY
     * @param button
     * @return true if mouse click is inside this frame and is handled here, else false
     */
    default boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    /**
     * @param mouseX
     * @param mouseY
     * @param button
     * @return true if mouse release is inside this frame and is handled here, else false
     */
    default boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    /**
     * @param mouseX
     * @param mouseY
     * @param dWheel
     * @return true if mouse pointer is inside this frame and scroll is handled here, else false
     */
    default boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    /**
     * Miscellaneous functions required before rendering
     * @param mouseX
     * @param mouseY
     */
    default void update(double mouseX, double mouseY) {

    }

    /**
     * Render elements of this frame. Ordering is important.
     *
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    default void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {

    }

    /**
     * @param x mouseX
     * @param y mouseY
     * @return tooltip or null if not returning tooltip;
     */
    @Nullable
    default List<Component> getToolTip(int x, int y) {
        return null;
    }

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void setVisible(boolean visible);

    boolean isVisible();

    default void hide() {
        setVisible(false);
    }

    default void show() {
        setVisible(true);
    }

    default void enable() {
        setEnabled(true);
    }

    default void disable() {
        setEnabled(false);
    }

    default void enableAndShow() {
        enable();
        show();
    }

    default void disableAndHide() {
        disable();
        hide();
    }

    @Override
    default IRect init(double left, double top, double right, double bottom) {
        return getRect().init(left, top, right, bottom);
    }

    @Override
    default MusePoint2D getUL() {
        return getRect().getUL();
    }

    @Override
    default MusePoint2D getWH() {
        return getRect().getWH();
    }

    @Override
    default double left() {
        return getRect().left();
    }

    @Override
    default double finalLeft() {
        return getRect().finalLeft();
    }

    @Override
    default double top() {
        return getRect().top();
    }

    @Override
    default double finalTop() {
        return getRect().finalTop();
    }

    @Override
    default double right() {
        return getRect().right();
    }

    @Override
    default double finalRight() {
        return getRect().finalRight();
    }

    @Override
    default double bottom() {
        return getRect().bottom();
    }

    @Override
    default double finalBottom() {
        return getRect().finalBottom();
    }

    @Override
    default double width() {
        return getRect().width();
    }

    @Override
    default double finalWidth() {
        return getRect().finalWidth();
    }

    @Override
    default double height() {
        return getRect().height();
    }

    @Override
    default double finalHeight() {
        return getRect().finalHeight();
    }

    @Override
    default IRect setUL(MusePoint2D ul) {
        return getRect().setUL(ul);
    }

    @Override
    default IRect setWH(MusePoint2D wh) {
        return getRect().setWH(wh);
    }

    @Override
    default IRect setLeft(double value) {
        return getRect().setLeft(value);
    }

    @Override
    default IRect setRight(double value) {
        return getRect().setRight(value);
    }

    @Override
    default IRect setTop(double value) {
        return getRect().setTop(value);
    }

    @Override
    default IRect setBottom(double value) {
        return getRect().setBottom(value);
    }

    @Override
    default IRect setWidth(double value) {
        return getRect().setWidth(value);
    }

    @Override
    default IRect setHeight(double value) {
        return getRect().setHeight(value);
    }

    @Override
    default void move(MusePoint2D moveAmount) {
        getRect().move(moveAmount);
    }

    @Override
    default void move(double x, double y) {
        getRect().move(x, y);
    }

    @Override
    default void setPosition(MusePoint2D position) {
        getRect().setPosition(position);
    }

    @Override
    default boolean growFromMiddle() {
        return getRect().growFromMiddle();
    }

    @Override
    default boolean doneGrowing() {
        return getRect().doneGrowing();
    }

    @Override
    default void initGrowth() {
        getRect().initGrowth();
    }

    @Override
    default IRect setMeLeftOf(IRect otherRightOfMe) {
        return getRect().setMeLeftOf(otherRightOfMe);
    }

    @Override
    default IRect setMeRightOf(IRect otherLeftOfMe) {
        return getRect().setMeRightOf(otherLeftOfMe);
    }

    @Override
    default IRect setMeAbove(IRect otherBelowMe) {
        return getRect().setMeAbove(otherBelowMe);
    }

    @Override
    default IRect setMeBelow(IRect otherAboveMe) {
        return getRect().setMeBelow(otherAboveMe);
    }

    default void doThisOnSomeEvent() {};

    default void setDoThisOnSomeEvent(IDoThis iDoThis) {}

    @Override
    default void doThisOnChange() {}

    @Override
    default void setDoThisOnChange(IDoThis iDoThis) {}

    @Override
    default void setOnInit(IInit onInit) {
        getRect().setOnInit(onInit);
    }

    @Override
    default void onInit() {
        getRect().onInit();
    }
}