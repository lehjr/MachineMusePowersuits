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

package lehjr.powersuits.client.gui.modding.cosmetic.colourpicker;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.client.gui.clickable.Clickable;
import lehjr.numina.client.gui.clickable.ClickableLabel;
import lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import lehjr.numina.client.gui.clickable.slider.VanillaSlider;
import lehjr.numina.client.gui.frame.fixed.ScrollableFrame2;
import lehjr.numina.client.gui.gemoetry.DrawableTile;
import lehjr.numina.client.gui.gemoetry.IRect;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.render.IModelSpecNBT;
import lehjr.numina.common.capabilities.render.ModelSpecNBTCapability;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.ColourInfoPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
public class ColourPickerFrame extends ScrollableFrame2 {
    public ModularItemSelectionFrame itemSelector;
    protected List<IRect> rects = new ArrayList<>();
    static final List<String> slidersIds = new ArrayList<>(Arrays.asList( "red", "green", "blue", "alpha"));
    ColourBox colourBox;
    static final TranslationTextComponent COLOUR_PREFIX = new TranslationTextComponent("gui.powersuits.colourPrefix");
    public ClickableLabel colourLabel;
    public Optional<VanillaSlider> selectedSlider;
    public int selectedColour;
    public int decrAbove;
    VanillaFrameScrollBar scrollBar;

    public ColourPickerFrame(ModularItemSelectionFrame itemSelector, double left, double top, double right, double bottom) {
        super(new Rect(left, top, right, bottom));
        this.scrollBar = new VanillaFrameScrollBar(this, "slider");
        this.scrollBar.setValue(0);
        this.itemSelector = itemSelector;

        /** colour sliders ( boxes 0-3 ) ------------------------------------------------------- */
        for (int i=0;  i< 4; i++) {
            makeSlider(slidersIds.get(i));
        }

        /** colour selector box ( box 4 ) ------------------------------------------------------ */
        this.colourBox = new ColourBox(left() + 2, top(), left() + width() -8);
        colourBox.setBelow(rects.get(rects.size() -1));
        this.totalSize += colourBox.height();

        /** label ( box 5 ) -------------------------------------------------------------------- */
        this.colourLabel = new ClickableLabel(COLOUR_PREFIX, new Rect(
                left() + 2, top(), left() + width() -8, top() +  Math.max(StringUtils.getStringHeight(), 20)));
//        colourLabel.setPosition(center());
        colourLabel.setBelow(colourBox);

        colourLabel.setOnPressed(pressed->{
            if (colours().length > selectedColour) {
                // todo: insert chat to player??? Maybe???
                Minecraft.getInstance().keyboardHandler.setClipboard(new Colour(selectedColour).hexColour());
            }
        });
        this.totalSize += colourLabel.height();

        this.selectedSlider = Optional.empty();
        this.selectedColour = 0;
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
                setMessage(new StringTextComponent("").append(displayString.getString()).append(" ").append(valString).append(suffix));
            }
        };

        slider.setDisplayString(new TranslationTextComponent(NuminaConstants.MODULE_TRADEOFF_PREFIX + id));
        slider.setShowDecimal(false);
        slider.setSuffix("%");
        slider.setActive(true);
        slider.setBelow(spacer);
        rects.add(slider);
        this.totalSize += slider.height();
    }

    public int[] colours() {
        return (getOrCreateColourTag() != null) ? getOrCreateColourTag().getAsIntArray() : new int[0];
    }

    public IntArrayNBT getOrCreateColourTag() {
        return this.itemSelector.getModularItemOrEmpty().getCapability(ModelSpecNBTCapability.RENDER)
                .filter(IModelSpecNBT.class::isInstance)
                .map(IModelSpecNBT.class::cast)
                .map(spec -> {
                    CompoundNBT renderSpec = spec.getRenderTag();
                    if (renderSpec != null && !renderSpec.isEmpty()) {
                        return new IntArrayNBT(spec.getColorArray());
                    }
                    return new IntArrayNBT(new int[0]);
                }).orElse(new IntArrayNBT(new int[0]));
    }

    public IntArrayNBT setColourTagMaybe(List<Integer> intList) {
        return itemSelector.getModularItemOrEmpty().getCapability(ModelSpecNBTCapability.RENDER)
                .filter(IModelSpecNBT.class::isInstance)
                .map(IModelSpecNBT.class::cast)
                .map(spec -> {
                    CompoundNBT renderSpec = spec.getRenderTag();
                    renderSpec.put(NuminaConstants.TAG_COLOURS, new IntArrayNBT(intList));
                    this.itemSelector.selectedType().ifPresent(slotType -> MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColourInfoPacket(slotType, this.colours())));
                    return (IntArrayNBT) renderSpec.get(NuminaConstants.TAG_COLOURS);
                }).orElse(new IntArrayNBT(new int[0]));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double y = mouseY + currentScrollPixels;
        if (super.mouseClicked(mouseX, mouseY, button)) {
            selectedSlider = rects.stream().filter(VanillaSlider.class::isInstance).map(VanillaSlider.class::cast).filter(slider-> slider.mouseClicked(mouseX, y, button)).findAny();
            if (selectedSlider.isPresent()) {
                return true;
            }

            if (colourLabel.mouseClicked(mouseX, y, button)) {
                return true;
            }
            if (colourBox.addColour(mouseX, y)) {
                return true;
            }
            if (colourBox.removeColour(mouseX, y)) {
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

    public void onSelectColour(int i) {
        Colour c = new Colour(this.colours()[i]);
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
        this.selectedColour = i;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        double scrolledY = mouseY + currentScrollPixels;
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        scrollBar.render(matrixStack, mouseX, mouseY, partialTicks);

        if (this.isVisible() && this.isEnabled()) {
            if (colours().length > selectedColour) {
                colourLabel.setLabel(new StringTextComponent("").append(COLOUR_PREFIX).append(" 0X").append(new Colour(colours()[selectedColour]).hexColour()));
            }
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            matrixStack.pushPose();
            matrixStack.translate(0.0, -this.currentScrollPixels, 0.0);

            rects.stream().filter(VanillaSlider.class::isInstance).map(VanillaSlider.class::cast).forEach(slider->
                    slider.render(matrixStack, mouseX, (int) scrolledY, partialTicks));
            this.colourBox.render(matrixStack, mouseX, (int) scrolledY, partialTicks);
            this.colourLabel.render(matrixStack, mouseX, (int) scrolledY, partialTicks);
            matrixStack.popPose();
            super.postRender(mouseX, mouseY, partialTicks);
        } else {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            super.postRender(mouseX, mouseY, partialTicks);
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
                    if (colours().length > selectedColour) {
                        colours()[selectedColour] = Colour.getInt((float) ((VanillaSlider)rects.get(1)).getSliderInternalValue(), (float) ((VanillaSlider)rects.get(3)).getSliderInternalValue(), (float) ((VanillaSlider)rects.get(5)).getSliderInternalValue(), (float) ((VanillaSlider)rects.get(7)).getSliderInternalValue());
                        this.itemSelector.selectedType().ifPresent(slotType -> MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColourInfoPacket(slotType, this.colours())));
                    }});
                // this just sets up the sliders on selecting an item
            } else if (!itemSelector.getModularItemOrEmpty().isEmpty() && colours().length > 0) {
                if (selectedColour <= colours().length - 1) {
                    onSelectColour(selectedColour);
                }
            }
        }
        scrollBar.setMaxValue(getMaxScrollPixels());
        scrollBar.setValueByMouse(mousey);
        setCurrentScrollPixels(scrollBar.getValue());
    }

    class ColourBox extends Clickable {
        public ColourBox(double left, double top, double right) {
            super(left,top,right,top + 18);
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
        boolean addColour(double x, double y) {
            if (y > this.top() + 8.0 && y < this.bottom()) {
                int colourCol = (int) (x - left() - 8.0) / 8;
                if (colourCol >= 0 && colourCol < colours().length) {
                    onSelectColour(colourCol);
                } else if (colourCol == colours().length) {
                    NuminaLogger.logger.debug("Adding");
                    List<Integer> intList = Arrays.stream(getIntArray(getOrCreateColourTag())).boxed().collect(Collectors.toList());
                    intList.add(Colour.WHITE.getInt());
                    setColourTagMaybe(intList);
                }
                return true;
            }
            return false;
        }

        boolean removeColour(double x, double y) {
            if (y > this.top() + 1.5 && y < this.top() + 7 && x > left() + 8 + selectedColour * 8 && x < left() + 16 + selectedColour * 8) {
                IntArrayNBT IntArrayNBT = getOrCreateColourTag();
                List<Integer> intList = Arrays.stream(getIntArray(IntArrayNBT)).boxed().collect(Collectors.toList());

                if (intList.size() > 1 && selectedColour <= intList.size() -1) {
                    intList.remove(selectedColour); // with integer list, will default to index rather than getValue
                    setColourTagMaybe(intList);
                    decrAbove = selectedColour;
                    if (selectedColour == getIntArray(IntArrayNBT).length) {
                        selectedColour = selectedColour - 1;
                    }
                    itemSelector.selectedType().ifPresent(slotType -> MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColourInfoPacket(slotType, IntArrayNBT.getAsIntArray())));
                }
                return true;
            }
            return false;
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            GuiIcon icon = IconUtils.getIcon();

            // colours
            for (int i=0; i < colours().length; i++) {
                icon.armorColourPatch.draw(matrixStack, this.left() + 8 + i * 8, this.top() + 8 , new Colour(colours()[i]));
            }

            icon.armorColourPatch.draw(matrixStack, this.left() + 8 + colours().length * 8, this.top() + 8, Colour.WHITE);
            icon.selectedArmorOverlay.draw(matrixStack, this.left() + 8 + selectedColour * 8, this.top() + 8, Colour.WHITE);
            icon.minusSign.draw(matrixStack, this.left() + 8 + selectedColour * 8, this.top(), Colour.RED);
            icon.plusSign.draw(matrixStack, this.left() + 8 + colours().length * 8, this.top() + 8, Colour.GREEN);
        }
    }
}