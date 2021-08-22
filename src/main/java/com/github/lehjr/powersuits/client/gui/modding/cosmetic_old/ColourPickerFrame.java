///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package com.github.lehjr.powersuits.client.gui.modding.cosmetic;
//
//import com.github.lehjr.numina.basemod.MuseLogger;
//import com.github.lehjr.numina.constants.NuminaConstants;
//import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
//import com.github.lehjr.numina.util.client.gui.GuiIcon;
//import com.github.lehjr.numina.util.client.gui.clickable.ClickableLabel;
//import com.github.lehjr.numina.util.client.gui.clickable.ClickableSlider;
//import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
//import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
//import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
//import com.github.lehjr.numina.util.client.render.MuseIconUtils;
//import com.github.lehjr.numina.util.math.Colour;
//import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
//import com.github.lehjr.powersuits.client.gui.obsolete.ScrollableLabel;
//import com.github.lehjr.powersuits.client.gui.obsolete.ScrollableRectangle;
//import com.github.lehjr.powersuits.client.gui.obsolete.ScrollableSlider;
//import com.github.lehjr.powersuits.network.MPSPackets;
//import com.github.lehjr.powersuits.network.packets.ColourInfoPacket;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.player.ClientPlayerEntity;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.nbt.IntArrayNBT;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TranslationTextComponent;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Author: MachineMuse (Claire Semple)
// * Created: 4:19 AM, 03/05/13
// * <p>
// * Ported to Java by lehjr on 11/2/16.
// */
//public class ColourPickerFrame extends ScrollableFrame {
//    public ItemSelectionFrame itemSelector;
//    public ScrollableSlider rslider;
//    public ScrollableSlider gslider;
//    public ScrollableSlider bslider;
//    public ScrollableSlider aslider;
//    ScrollableColourBox colourBox;
//    String COLOUR_PREFIX = I18n.get("gui.powersuits.colourPrefix");
//
//    public ScrollableLabel colourLabel;
//
//    public ScrollableSlider selectedSlider;
//    public int selectedColour;
//    public int decrAbove;
//    ScrollableRectangle[] rectangles;
//
//    public ColourPickerFrame(MusePoint2D topleft, MusePoint2D bottomright, float zLevel, Colour borderColour, Colour insideColour, ItemSelectionFrame itemSelector) {
//        super(topleft, bottomright, zLevel, borderColour, insideColour);
//        this.itemSelector = itemSelector;
//    }
//
//    @Override
//    public RelativeRect init(double left, double top, double right, double bottom) {
//        super.init(left, top, right, bottom);
//
//        if (itemSelector.hasNoItems()) {
//            this.disable();
//        } else {
//            this.enable();
//        }
//
//        this.rectangles = new ScrollableRectangle[6];
//        this.totalSize = 120;
//
//        // sliders boxes 0-3
//        this.rslider = getScrollableSlider("red", null, 0);
//        this.gslider = getScrollableSlider("green", rslider, 1);
//        this.bslider = getScrollableSlider("blue", gslider, 2);
//        this.aslider = getScrollableSlider("alpha", bslider, 3);
//
//        // box 4 is for the color icon stuff
//        this.colourBox = new ScrollableColourBox(new RelativeRect(getRect().left(), this.aslider.bottom(), this.getRect().right(), this.aslider.bottom() + 25));
//        this.colourBox.setMeBelow(aslider);
//        rectangles[4] = colourBox;
//
//        // box 5 is the label
//        RelativeRect colourLabelBox = new RelativeRect(getRect().left(), this.colourBox.bottom(), this.getRect().right(), this.colourBox.bottom() + 20);
//        this.colourLabel = new ScrollableLabel(
//                new ClickableLabel(COLOUR_PREFIX, new MusePoint2D(colourLabelBox.centerx(), colourLabelBox.centery())), colourLabelBox);
//
//        colourLabel.setMeBelow(colourBox);
//        rectangles[5] = this.colourLabel;
//
//        this.selectedSlider = null;
//        this.selectedColour = 0;
//        this.decrAbove = -1;
//        return this;
//    }
//
//    public ScrollableSlider getScrollableSlider(String id, ScrollableRectangle prev, int index) {
//        RelativeRect newborder = new RelativeRect(getRect().left(), prev != null ? prev.bottom() : this.getRect().top(),
//                this.getRect().right(), (prev != null ? prev.bottom() : this.getRect().top()) + 18);
//        ClickableSlider slider =
//                new ClickableSlider(new MusePoint2D(newborder.centerx(), newborder.centery()), newborder.width() - 15, id, new TranslationTextComponent(NuminaConstants.MODULE_TRADEOFF_PREFIX + id));
//        ScrollableSlider scrollableSlider = new ScrollableSlider(slider, newborder);
//        scrollableSlider.setMeBelow((prev != null) ? prev : null);
//        rectangles[index] = scrollableSlider;
//        return scrollableSlider;
//    }
//
//    public int[] colours() {
//        return (getOrCreateColourTag() != null) ? getOrCreateColourTag().getAsIntArray() : new int[0];
//    }
//
//    public IntArrayNBT getOrCreateColourTag() {
//        if (this.itemSelector.getSelectedItem() == null) {
//            return null;
//        }
//        return itemSelector.getSelectedItem().getStack().getCapability(ModelSpecNBTCapability.RENDER).map(spec -> {
//            CompoundNBT renderSpec = spec.getRenderTag();
//            if (renderSpec != null && !renderSpec.isEmpty()) {
//                return new IntArrayNBT(spec.getColorArray());
//            }
//            return new IntArrayNBT(new int[0]);
//        }).orElse(new IntArrayNBT(new int[0]));
//    }
//
//    public IntArrayNBT setColourTagMaybe(List<Integer> intList) {
//        if (this.itemSelector.getSelectedItem() == null) {
//            return null;
//        }
//        return itemSelector.getSelectedItem().getStack().getCapability(ModelSpecNBTCapability.RENDER).map(spec -> {
//            CompoundNBT renderSpec = spec.getRenderTag();
//            renderSpec.put(NuminaConstants.TAG_COLOURS, new IntArrayNBT(intList));
//            ClientPlayerEntity player = Minecraft.getInstance().player;
//            if (player.level.isClientSide) {
//                MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColourInfoPacket(this.itemSelector.getSelectedItem().getSlotIndex(), this.colours()));
//            }
//            return (IntArrayNBT) renderSpec.get(NuminaConstants.TAG_COLOURS);
//        }).orElse(new IntArrayNBT(new int[0]));
//    }
//
//    @Override
//    public boolean mouseReleased(double x, double y, int button) {
//        if (this.isEnabled())
//            this.selectedSlider = null;
//        return false;
//    }
//
//    @Override
//    public void update(double mousex, double mousey) {
//        super.update(mousex, mousey);
//        if (this.isEnabled()) {
//            if (this.selectedSlider != null) {
//                this.selectedSlider.getSlider().setValueByX(mousex);
//                if (colours().length > selectedColour) {
//                    colours()[selectedColour] = Colour.getInt((float) rslider.getValue(), (float) gslider.getValue(), (float) bslider.getValue(), (float) aslider.getValue());
//
//                    ClientPlayerEntity player = Minecraft.getInstance().player;
//                    if (player.level.isClientSide)
//                        MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColourInfoPacket(itemSelector.getSelectedItem().inventorySlot, colours()));
//                }
//                // this just sets up the sliders on selecting an item
//            } else if (itemSelector.getSelectedItem() != null && colours().length > 0) {
//                if (selectedColour <= colours().length - 1)
//                    onSelectColour(selectedColour);
//            }
//        }
//    }
//
//    @Override
//    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        if (this.isVisible()) {
//            this.currentScrollPixels = Math.min(currentScrollPixels, getMaxScrollPixels());
//
//            if (colours().length > selectedColour) {
//                colourLabel.setText(COLOUR_PREFIX + " 0X" + new Colour(colours()[selectedColour]).hexColour());
//            }
//
//            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
//            RenderSystem.pushMatrix();
//            RenderSystem.translatef(0, -currentScrollPixels, 0);
//            for (ScrollableRectangle f : rectangles) {
//                f.render(matrixStack, mouseX, mouseY, partialTicks);
//            }
//            RenderSystem.popMatrix();
//            super.postRender(mouseX, mouseY, partialTicks);
//        }
//    }
//
//    @Override
//    public List<ITextComponent> getToolTip(int x, int y) {
//        return null;
//    }
//
//    public void onSelectColour(int i) {
//        Colour c = new Colour(this.colours()[i]);
//        this.rslider.setValue(c.r);
//        this.gslider.setValue(c.g);
//        this.bslider.setValue(c.b);
//        this.aslider.setValue(c.a);
//        this.selectedColour = i;
//    }
//
//    @Override
//    public boolean mouseClicked(double x, double y, int button) {
//        if (this.isEnabled()) {
//            y = y + currentScrollPixels;
//
//            if (this.rslider.hitBox((float) x, (float) y)) {
//                this.selectedSlider = this.rslider;
//            } else if (this.gslider.hitBox((float) x, (float) y)) {
//                this.selectedSlider = this.gslider;
//            } else if (this.bslider.hitBox((float) x, (float) y)) {
//                this.selectedSlider = this.bslider;
//            } else if(this.aslider.hitBox((float)x,(float) y)) {
//                this.selectedSlider =this.aslider;
//            } else{
//                this.selectedSlider=null;
//            }
//
//            colourBox.addColour(x, y);
//            colourBox.removeColour(x, y);
//
//            if (colourLabel.hitbox((float) x, (float) y) && colours().length > selectedColour) {
//                // todo: insert chat to player
//                Minecraft.getInstance().keyboardHandler.setClipboard(new Colour(selectedColour).hexColour());
//            }
//        }
//        return false;
//    }
//
//    public int[] getIntArray(IntArrayNBT e) {
//        if (e == null) // null when no armor item selected
//            return new int[0];
//        return e.getAsIntArray();
//    }
//
//    class ScrollableColourBox extends ScrollableRectangle {
//        public ScrollableColourBox(RelativeRect relativeRect) {
//            super(relativeRect);
//        }
//
//        boolean addColour(double x, double y) {
//            if (y > this.centery() + 8.5 && y < this.centery() + 16.5 ) {
//                int colourCol = (int) (x - left() - 8.0) / 8;
//                if (colourCol >= 0 && colourCol < colours().length) {
//                    onSelectColour(colourCol);
//                } else if (colourCol == colours().length) {
//                    MuseLogger.logger.debug("Adding");
//                    List<Integer> intList = Arrays.stream(getIntArray(getOrCreateColourTag())).boxed().collect(Collectors.toList());
//                    intList.add(Colour.WHITE.getInt());
//                    setColourTagMaybe(intList);
//                }
//                return true;
//            }
//            return false;
//        }
//
//        boolean removeColour(double x, double y) {
//            if (y > this.centery() + 0.5 && y < this.centery() + 8.5 && x > left() + 8 + selectedColour * 8 && x < left() + 16 + selectedColour * 8) {
//                IntArrayNBT IntArrayNBT = getOrCreateColourTag();
//                List<Integer> intList = Arrays.stream(getIntArray(IntArrayNBT)).boxed().collect(Collectors.toList());
//
//                if (intList.size() > 1 && selectedColour <= intList.size() -1) {
//                    intList.remove(selectedColour); // with integer list, will default to index rather than getValue
//
//                    setColourTagMaybe(intList);
//
//                    decrAbove = selectedColour;
//                    if (selectedColour == getIntArray(IntArrayNBT).length) {
//                        selectedColour = selectedColour - 1;
//                    }
//
//                    ClientPlayerEntity player = Minecraft.getInstance().player;
//                    if (player.level.isClientSide)
//                        MPSPackets.CHANNEL_INSTANCE.sendToServer(new ColourInfoPacket(itemSelector.getSelectedItem().getSlotIndex(), IntArrayNBT.getAsIntArray()));
//                }
//                return true;
//            }
//            return false;
//        }
//
//        @Override
//        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//            GuiIcon icon = MuseIconUtils.getIcon();
//
//            // colours
//            for (int i=0; i < colours().length; i++) {
//                icon.armorColourPatch.draw(matrixStack, this.left() + 8 + i * 8, this.centery() + 8 , new Colour(colours()[i]));
//            }
//
//            icon.armorColourPatch.draw(matrixStack, this.left() + 8 + colours().length * 8, this.centery() + 8, Colour.WHITE);
//            icon.selectedArmorOverlay.draw(matrixStack, this.left() + 8 + selectedColour * 8, this.centery() + 8, Colour.WHITE);
//            icon.minusSign.draw(matrixStack, this.left() + 8 + selectedColour * 8, this.centery(), Colour.RED);
//            icon.plusSign.draw(matrixStack, this.left() + 8 + colours().length * 8, this.centery() + 8, Colour.GREEN);
//        }
//    }
//}
