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

package lehjr.powersuits.client.gui.modding.cosmetic.colorpicker;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.client.gui.clickable.Clickable;
import lehjr.numina.client.gui.clickable.ClickableLabel;
import lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import lehjr.numina.client.gui.clickable.slider.VanillaSlider;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.IRect;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.ColorInfoPacket;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
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
public class ColorPickerFrame extends ScrollableFrame {
    public ModularItemSelectionFrame itemSelector;
    protected List<IRect> rects = new ArrayList<>();
    static final List<String> slidersIds = new ArrayList<>(Arrays.asList( "red", "green", "blue", "alpha"));
    ColorBox colorBox;
    static final Component COLOR_PREFIX = Component.translatable("gui.powersuits.colorPrefix");
    public ClickableLabel colorLabel;
    public Optional<VanillaSlider> selectedSlider;
    public int selectedColor;
    public int decrAbove;
    VanillaFrameScrollBar scrollBar;

    public ColorPickerFrame(ModularItemSelectionFrame itemSelector, double left, double top, double right, double bottom) {
        super(new Rect(left, top, right, bottom));
        this.scrollBar = new VanillaFrameScrollBar(this, "slider");
        this.itemSelector = itemSelector;

        /** color sliders ( boxes 0-3 ) ------------------------------------------------------- */
        for (int i=0;  i< 4; i++) {
            makeSlider(slidersIds.get(i));
        }

        /** color selector box ( box 4 ) ------------------------------------------------------ */
        this.colorBox = new ColorBox(left() + 2, top(), left() + width() -8);
        colorBox.setBelow(rects.get(rects.size() -1));
        this.totalSize += colorBox.height();

        /** label ( box 5 ) -------------------------------------------------------------------- */
        this.colorLabel = new ClickableLabel(COLOR_PREFIX, new Rect(
                left() + 2, top(), left() + width() -8, top() +  Math.max(StringUtils.getStringHeight(), 20)));
//        colorLabel.setPosition(center());
        colorLabel.setBelow(colorBox);

        colorLabel.setOnPressed(pressed->{
            if (colors().length > selectedColor) {
                // todo: insert chat to player??? Maybe???
                Minecraft.getInstance().keyboardHandler.setClipboard(new Color(selectedColor).rgbaHexColor());
            }
        });
        this.totalSize += colorLabel.height();

        this.selectedSlider = Optional.empty();
        this.selectedColor = 0;
        this.decrAbove = -1;
    }

    public void makeSlider(String id) {
        Rect spacer = new Rect(left() + 2, top(), left() + width() -10, top() + 4);
        if (rects.size() > 0) {
            spacer.setBelow(rects.get(rects.size() -1));
        }
        rects.add(spacer);
        this.totalSize += spacer.height();

        VanillaSlider slider = new VanillaSlider(left() + 2, top(), width() - 10, id) {
            @Override
            public void updateSlider() {
                String valString = Integer.toString((int) Math.round(100 * sliderValue * (maxValue - minValue) + minValue));
                setMessage(Component.empty().append(displayString.getString()).append(" ").append(valString).append(suffix));
            }
        };

        slider.setDisplayString(Component.translatable(TagConstants.MODULE_TRADEOFF_PREFIX + id));
        slider.setShowDecimal(false);
        slider.setSuffix("%");
        slider.setActive(true);
        slider.setBelow(spacer);
        rects.add(slider);
        this.totalSize += slider.height();
    }

    public int[] colors() {
        return (getOrCreateColorTag() != null) ? getOrCreateColorTag().getAsIntArray() : new int[0];
    }

    public IntArrayTag getOrCreateColorTag() {
        return this.itemSelector.getModularItemOrEmpty().getCapability(NuminaCapabilities.RENDER)
                .filter(IModelSpec.class::isInstance)
                .map(IModelSpec.class::cast)
                .map(spec -> {
                    CompoundTag renderSpec = spec.getRenderTag();
                    if (renderSpec != null && !renderSpec.isEmpty()) {
                        this.itemSelector.selectedType().ifPresent(slotType -> NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ColorInfoPacket(slotType, new int[]{-1})));
                        return new IntArrayTag(spec.getColorArray());
                    }
                    return new IntArrayTag(new int[]{-1});
                }).orElse(new IntArrayTag(new int[]{-1}));
    }

    public IntArrayTag setColorTagMaybe(List<Integer> intList) {
        return itemSelector.getModularItemOrEmpty().getCapability(NuminaCapabilities.RENDER)
                .filter(IModelSpec.class::isInstance)
                .map(IModelSpec.class::cast)
                .map(spec -> {
                    CompoundTag renderSpec = spec.getRenderTag();
                    renderSpec.put(TagConstants.COLORS, new IntArrayTag(intList));
                    this.itemSelector.selectedType().ifPresent(slotType -> NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ColorInfoPacket(slotType, this.colors())));
                    return (IntArrayTag) renderSpec.get(TagConstants.COLORS);

                }).orElse(new IntArrayTag(new int[]{-1}));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double y = mouseY + currentScrollPixels;
        if (super.mouseClicked(mouseX, mouseY, button)) {
            selectedSlider = rects.stream().filter(VanillaSlider.class::isInstance).map(VanillaSlider.class::cast).filter(slider-> slider.mouseClicked(mouseX, y, button)).findAny();
            if (selectedSlider.isPresent()) {
                return true;
            }

            if (colorLabel.mouseClicked(mouseX, y, button)) {
                return true;
            }
            if (colorBox.addColor(mouseX, y)) {
                return true;
            }
            if (colorBox.removeColor(mouseX, y)) {
                return true;
            }

            return scrollBar.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isEnabled()) {
            this.selectedSlider = Optional.empty();
            return scrollBar.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        boolean retVal = super.mouseScrolled(mouseX, mouseY, dWheel);
        scrollBar.setValue(currentScrollPixels);
        return retVal;
    }

    public void onSelectColor(int i) {
        Color c = new Color(this.colors()[i]);
        rects.stream().filter(VanillaSlider.class::isInstance).map(VanillaSlider.class::cast).forEach(slider-> {
            int index = slidersIds.indexOf(slider.id());
            switch(index) {
                case 0: {
                    slider.setValue(c.r);
                    break;
                }

                case 1: {
                    slider.setValue(c.g);
                    break;
                }

                case 2: {
                    slider.setValue(c.b);
                    break;
                }

                case 3: {
                    slider.setValue(c.a);
                    break;
                }
            }
        });
        this.selectedColor = i;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        double scrolledY = mouseY + currentScrollPixels;
        super.render(matrixStack, mouseX, mouseY, partialTick);
        scrollBar.render(matrixStack, mouseX, mouseY, partialTick);

        if (this.isVisible() && this.isEnabled()) {
            if (colors().length > selectedColor) {
                colorLabel.setLabel(Component.empty().append(COLOR_PREFIX).append(" 0X").append(new Color(colors()[selectedColor]).rgbaHexColor()));
            }
            super.preRender(matrixStack, mouseX, mouseY, partialTick);
            matrixStack.pushPose();
            matrixStack.translate(0.0, -this.currentScrollPixels, 0.0);

            rects.stream().filter(VanillaSlider.class::isInstance).map(VanillaSlider.class::cast).forEach(slider->
                    slider.render(matrixStack, mouseX, (int) scrolledY, partialTick));
            this.colorBox.render(matrixStack, mouseX, (int) scrolledY, partialTick);
            this.colorLabel.render(matrixStack, mouseX, (int) scrolledY, partialTick);
            matrixStack.popPose();
            super.postRender(mouseX, mouseY, partialTick);
        } else {
            super.preRender(matrixStack, mouseX, mouseY, partialTick);
            super.postRender(mouseX, mouseY, partialTick);
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
//            sliders.forEach(slider->slider.update(mousex, mousey)); // enabling makes all sliders act as if they are dragging
            if (selectedSlider.isPresent()) {
                this.selectedSlider.ifPresent(slider-> {
                    slider.setValueByMouse(mousex);
                    if (colors().length > selectedColor) {
                        colors()[selectedColor] = Color.getARGBInt((float) ((VanillaSlider)rects.get(1)).getSliderInternalValue(), (float) ((VanillaSlider)rects.get(3)).getSliderInternalValue(), (float) ((VanillaSlider)rects.get(5)).getSliderInternalValue(), (float) ((VanillaSlider)rects.get(7)).getSliderInternalValue());
                        this.itemSelector.selectedType().ifPresent(slotType -> NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ColorInfoPacket(slotType, this.colors())));
                    }});
                // this just sets up the sliders on selecting an item
            } else if (!itemSelector.getModularItemOrEmpty().isEmpty() && colors().length > 0) {
                if (selectedColor <= colors().length - 1) {
                    onSelectColor(selectedColor);
                }
            }
        }
        scrollBar.setMaxValue(getMaxScrollPixels());
        scrollBar.setValueByMouse(mousey);
        setCurrentScrollPixels(scrollBar.getValue());
    }

    class ColorBox extends Clickable {
        public ColorBox(double left, double top, double right) {
            super(left,top,right,top + 18);
        }

        public int[] getIntArray(IntArrayTag e) {
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
                int colorCol = (int) (x - left() - 8.0) / 8;
                if (colorCol >= 0 && colorCol < colors().length) {
                    onSelectColor(colorCol);
                } else if (colorCol == colors().length) {
                    NuminaLogger.logger.debug("Adding color");
                    List<Integer> intList = Arrays.stream(getIntArray(getOrCreateColorTag())).boxed().collect(Collectors.toList());
                    intList.add(Color.WHITE.getARGBInt());
                    setColorTagMaybe(intList);
                }
                return true;
            }
            return false;
        }

        boolean removeColor(double x, double y) {
            if (y > this.top() + 1.5 && y < this.top() + 7 && x > left() + 8 + selectedColor * 8 && x < left() + 16 + selectedColor * 8) {
                IntArrayTag IntArrayTag = getOrCreateColorTag();
                List<Integer> intList = Arrays.stream(getIntArray(IntArrayTag)).boxed().collect(Collectors.toList());

                if (intList.size() > 1 && selectedColor <= intList.size() -1) {
                    intList.remove(selectedColor); // with integer list, will default to index rather than getValue
                    setColorTagMaybe(intList);
                    decrAbove = selectedColor;
                    if (selectedColor == getIntArray(IntArrayTag).length) {
                        selectedColor = selectedColor - 1;
                    }
                    itemSelector.selectedType().ifPresent(slotType -> NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ColorInfoPacket(slotType, IntArrayTag.getAsIntArray())));
                }
                return true;
            }
            return false;
        }

        @Override
        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
            GuiIcon icon = IconUtils.getIcon();

            // colors
            for (int i=0; i < colors().length; i++) {
                icon.colorclicker.draw(matrixStack, this.left() + 8 + i * 8, this.top() + 8 , new Color(colors()[i]));
            }

            icon.colorclicker.draw(matrixStack, this.left() + 8 + colors().length * 8, this.top() + 8, Color.WHITE);
            icon.armordisplayselect.draw(matrixStack, this.left() + 8 + selectedColor * 8, this.top() + 8, Color.WHITE);
            icon.minusSign.draw(matrixStack, this.left() + 8 + selectedColor * 8, this.top(), Color.RED);
            icon.plusSign.draw(matrixStack, this.left() + 8 + colors().length * 8, this.top() + 8, Color.GREEN);
        }
    }
}
