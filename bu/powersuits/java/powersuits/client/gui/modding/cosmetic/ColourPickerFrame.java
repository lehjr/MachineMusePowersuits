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

import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.basemod.NuminaLogger;
import lehjr.numina.constants.NuminaConstants;
import lehjr.numina.util.capabilities.render.IModelSpecNBT;
import lehjr.numina.util.capabilities.render.CapabilityModelSpec;
import lehjr.numina.util.client.gui.GuiIcon;
import lehjr.numina.util.client.gui.clickable.ClickableLabel;
import lehjr.numina.util.client.gui.clickable.ClickableSlider;
import lehjr.numina.util.client.gui.clickable.IClickable;
import lehjr.numina.util.client.gui.frame.GUISpacer;
import lehjr.numina.util.client.gui.frame.ScrollableMultiRectFrame;
import lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import lehjr.numina.util.client.render.MuseIconUtils;
import lehjr.numina.util.math.Color;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.network.MPSPackets;
import lehjr.powersuits.network.packets.ColorInfoPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.util.text.Component;
import net.minecraft.util.text.TranslatableComponent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:19 AM, 03/05/13
 * <p>
 * Ported to Java by lehjr on 11/2/16.
 */
public class ColorPickerFrame extends ScrollableMultiRectFrame {

    // FIXME: slider click not working, clickable colour label not rendering


    public ModularItemSelectionFrame itemSelector;
    protected ClickableSlider[] sliders = new ClickableSlider[4];
    final String[] slidersIds = new String[] { "red", "green", "blue", "alpha" };

    ScrollableColorBox colourBox;
    String COLOUR_PREFIX = I18n.get("gui.powersuits.colourPrefix");

    public ClickableLabel colourLabel;

    public Optional<ClickableSlider> selectedSlider = Optional.empty();
    public int selectedColor;
    public int decrAbove;

    public ColorPickerFrame(ModularItemSelectionFrame itemSelector, double width, double height) {
        super(true, width, height, width, height);
        setBackground(new DrawableTile(0,0,0,0));
        setMargin(7);

        this.itemSelector = itemSelector;
        setTotalSize(120);

        addRect(new GUISpacer(width - margin * 2, 6));

        /** colour sliders ( boxes 0-3 ) ------------------------------------------------------- */
        // sliders
        for (int i=0;  i< 4; i++) {
            sliders[i] = getSlider(slidersIds[i], i, width - margin * 2);
        }

        /** colour selector box ( box 4 ) ------------------------------------------------------ */
        this.colourBox = new ScrollableColorBox();
        this.colourBox.setWidth(width - margin * 2);
        this.colourBox.setHeight(18);
        addRect(this.colourBox);

        /** label ( box 5 ) -------------------------------------------------------------------- */
        this.colourLabel = new ClickableLabel(COLOUR_PREFIX, getPosition());
        this.colourLabel.setWidth(width - margin * 2);
        this.colourLabel.setDrawBackground(false);
//        this.colourLabel.setHeight(10);
        addRect(colourLabel);
        doneAdding();

        colourLabel.setOnPressed(pressed->{
            if (colours().length > selectedColor) {
                // todo: insert chat to player
                Minecraft.getInstance().keyboardHandler.setClipboard(new Color(selectedColor).hexColor());
            }
        });

        this.selectedSlider = Optional.empty();
        this.selectedColor = 0;
        this.decrAbove = -1;
    }

    public ClickableSlider getSlider(String id, int index, double width) {
        ClickableSlider slider = new ClickableSlider(center(), width, id, new TranslatableComponent(NuminaConstants.MODULE_TRADEOFF_PREFIX + id));
        addRect(slider);
        slider.setLabelColor(Color.WHITE);
        slider.setOnPressed(pressed -> selectedSlider = Optional.of(slider));
        return slider;
    }

    public int[] colours() {
        return (getOrCreateColorTag() != null) ? getOrCreateColorTag().getAsIntArray() : new int[0];
    }

    public IntArrayNBT getOrCreateColorTag() {
        return this.itemSelector.getModularItemOrEmpty().getCapability(CapabilityModelSpec.RENDER)
                .filter(IModelSpecNBT.class::isInstance)
                .map(IModelSpecNBT.class::cast)
                .map(spec -> {
                    CompoundTag renderSpec = spec.getRenderTag();
                    if (renderSpec != null && !renderSpec.isEmpty()) {
                        return new IntArrayNBT(spec.getColorArray());
                    }
                    return new IntArrayNBT(new int[0]);
                }).orElse(new IntArrayNBT(new int[0]));
    }

    public IntArrayNBT setColorTagMaybe(List<Integer> intList) {
        return itemSelector.getModularItemOrEmpty().getCapability(CapabilityModelSpec.RENDER)
                .filter(IModelSpecNBT.class::isInstance)
                .map(IModelSpecNBT.class::cast)
                .map(spec -> {
                    CompoundTag renderSpec = spec.getRenderTag();
                    renderSpec.put(NuminaConstants.COLOR, new IntArrayNBT(intList));
                    this.itemSelector.selectedType().ifPresent(slotType -> MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColorInfoPacket(slotType, this.colours())));
                    return (IntArrayNBT) renderSpec.get(NuminaConstants.COLOR;
                }).orElse(new IntArrayNBT(new int[0]));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (containsPoint(mouseX, mouseY)) {
            if (this.isEnabled() && super.mouseClicked(mouseX, mouseY, button) ) {
                final double scrolledY = mouseY + currentScrollPixels;
                colourBox.addColor(mouseX, scrolledY);
                colourBox.removeColor(mouseX, scrolledY);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        if (this.isEnabled()) {
            this.selectedSlider = Optional.empty();
        }
        return super.mouseReleased(x, y, button);
    }

    public void onSelectColor(int i) {
        Color c = new Color(this.colours()[i]);
        this.sliders[0].setValue(c.r);
        this.sliders[1].setValue(c.g);
        this.sliders[2].setValue(c.b);
        this.sliders[3].setValue(c.a);
        this.selectedColor = i;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        final int scrolledY = mouseY + currentScrollPixels;

        renderBackground(matrixStack, mouseX, scrolledY, partialTicks);
        if (this.isVisible()) {
            this.currentScrollPixels = Math.min(currentScrollPixels, getMaxScrollPixels());
            if (colours().length > selectedColor) {
                colourLabel.setLabel(COLOUR_PREFIX + " 0X" + new Color(colours()[selectedColor]).hexColor());
            }
            super.preRender(matrixStack, mouseX, scrolledY, partialTicks);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0, - currentScrollPixels, 0);
            refreshRects();
            Arrays.stream(this.sliders).forEach(slider -> {
                slider.render(matrixStack, mouseX, scrolledY, partialTicks);
            });
            this.colourBox.render(matrixStack, mouseX, scrolledY, partialTicks);
            this.colourLabel.render(matrixStack, mouseX, scrolledY, partialTicks);
            RenderSystem.popPose();
            super.postRender(mouseX, scrolledY, partialTicks);
        }
    }

    @Override
    public void update(double mousex, double mousey) {
        super.update(mousex, mousey);

        if (!itemSelector.playerHasModularItems()) {
            this.disable();
        } else {
            this.enable();
        }

        if (this.isEnabled()) {
            Arrays.stream(sliders).forEach(slider->slider.update(mousex, mousey + getCurrentScrollPixels()));
            if (selectedSlider.isPresent()) {
                this.selectedSlider.ifPresent(slider-> {
                    slider.setValueByX(mousex);
                    if (colours().length > selectedColor) {
                        colours()[selectedColor] = Color.getInt((float) sliders[0].getValue(), (float) sliders[1].getValue(), (float) sliders[2].getValue(), (float) sliders[3].getValue());
                        this.itemSelector.selectedType().ifPresent(slotType -> MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColorInfoPacket(slotType, this.colours())));
                    }});
                // this just sets up the sliders on selecting an item
            } else if (!itemSelector.getModularItemOrEmpty().isEmpty() && colours().length > 0) {
                if (selectedColor <= colours().length - 1) {
                    onSelectColor(selectedColor);
                }
            }
        }
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        return null;
    }



    class ScrollableColorBox extends DrawableTile implements IClickable {
        public ScrollableColorBox() {
            super(0,0,0,0);
        }

        public int[] getIntArray(IntArrayNBT e) {
            if (e == null) // null when no armor item selected
                return new int[0];
            return e.getAsIntArray();
        }

        /**
         * handles clicking on the "+" circle
         *
         * @param x
         * @param y
         * @return
         */
        boolean addColor(double x, double y) {
            if (y > this.top() + 8.0 && y < this.bottom()) {
                int colourCol = (int) (x - left() - 8.0) / 8;
                if (colourCol >= 0 && colourCol < colours().length) {
                    onSelectColor(colourCol);
                } else if (colourCol == colours().length) {
                    NuminaLogger.logger.debug("Adding");
                    List<Integer> intList = Arrays.stream(getIntArray(getOrCreateColorTag())).boxed().collect(Collectors.toList());
                    intList.add(Color.WHITE.getInt());
                    setColorTagMaybe(intList);
                }
                return true;
            }
            return false;
        }

        boolean removeColor(double x, double y) {
            if (y > this.top() + 1.5 && y < this.top() + 7 && x > left() + 8 + selectedColor * 8 && x < left() + 16 + selectedColor * 8) {
                IntArrayNBT IntArrayNBT = getOrCreateColorTag();
                List<Integer> intList = Arrays.stream(getIntArray(IntArrayNBT)).boxed().collect(Collectors.toList());

                if (intList.size() > 1 && selectedColor <= intList.size() -1) {
                    intList.remove(selectedColor); // with integer list, will default to index rather than getValue
                    setColorTagMaybe(intList);
                    decrAbove = selectedColor;
                    if (selectedColor == getIntArray(IntArrayNBT).length) {
                        selectedColor = selectedColor - 1;
                    }
                    itemSelector.selectedType().ifPresent(slotType -> MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColorInfoPacket(slotType, IntArrayNBT.getAsIntArray())));
                }
                return true;
            }
            return false;
        }

        @Override
        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            GuiIcon icon = MuseIconUtils.getIcon();

            // colours
            for (int i=0; i < colours().length; i++) {
                icon.armorColorPatch.draw(matrixStack, this.left() + 8 + i * 8, this.top() + 8 , new Color(colours()[i]));
            }

            icon.armorColorPatch.draw(matrixStack, this.left() + 8 + colours().length * 8, this.top() + 8, Color.WHITE);
            icon.selectedArmorOverlay.draw(matrixStack, this.left() + 8 + selectedColor * 8, this.top() + 8, Color.WHITE);
            icon.minusSign.draw(matrixStack, this.left() + 8 + selectedColor * 8, this.top(), Color.RED);
            icon.plusSign.draw(matrixStack, this.left() + 8 + colours().length * 8, this.top() + 8, Color.GREEN);
        }

        @Override
        public void setEnabled(boolean b) {

        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public void setVisible(boolean b) {

        }

        @Override
        public boolean isVisible() {
            return true;
        }

        @Override
        public void setOnPressed(IPressable iPressable) {

        }

        @Override
        public void setOnReleased(IReleasable iReleasable) {

        }

        @Override
        public void onPressed() {

        }

        @Override
        public void onReleased() {

        }
    }
}
