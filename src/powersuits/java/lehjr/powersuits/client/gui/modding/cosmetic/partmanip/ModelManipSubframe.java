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

package lehjr.powersuits.client.gui.modding.cosmetic.partmanip;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.clickable.ClickableIndicatorArrow;
import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.frame.AbstractGuiFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.render.IArmorModelSpecNBT;
import lehjr.numina.common.capabilities.render.IHandHeldModelSpecNBT;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.capabilities.render.ModelSpecCapability;
import lehjr.numina.common.capabilities.render.modelspec.*;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.CosmeticInfoPacket;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.client.gui.modding.cosmetic.colourpicker.ColorRadioButton;
import lehjr.powersuits.client.gui.modding.cosmetic.colourpicker.ColourPickerFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:46 AM, 30/04/13
 * <p>
 * Ported to Java by lehjr on 11/2/16.
 */
public class ModelManipSubframe extends AbstractGuiFrame {
    ClickableIndicatorArrow openCloseButton;

    public SpecBase model;
    public ColourPickerFrame colourframe;
    public ModularItemSelectionFrame itemSelector;
    public List<PartManipSubFrame> parts;
    public boolean open;
    Minecraft minecraft;
    float zLevel;
    final double specHeight = 9;
    final double iconWidth = 8;

    public ModelManipSubframe(SpecBase model,
                              double left,
                              double top,
                              double right,
                              double bottom,
                              ColourPickerFrame colourframe,
                              ModularItemSelectionFrame itemSelector,
                              float zLevel) {
        super(new Rect(left, top, right, bottom));
        this.model = model;
        this.colourframe = colourframe;
        this.itemSelector = itemSelector;
        this.open = true;
        this.zLevel = zLevel;
        minecraft = Minecraft.getInstance();
        openCloseButton = new ClickableIndicatorArrow();
        openCloseButton.setOnPressed(pressed -> openClose());
        this.refreshPartSpecs(true);
    }

    public List<PartManipSubFrame> getParts() {
        return parts;
    }

    public Optional<IModelSpec> getRenderCapability() {
        return this.itemSelector.getModularItemOrEmpty().getCapability(ModelSpecCapability.RENDER)
                .filter(IModelSpec.class::isInstance)
                .map(IModelSpec.class::cast);
    }

    @Override
    public double bottom() {
        if (parts != null && !parts.isEmpty() && open) {
            return parts.get(parts.size()-1).bottom();
        }
        return super.bottom();
    }

    /**
     * FIXME: controls don't reflect actual tag settings on load and current code is kinda borked
     *
     *
     *
     * FIXME!!! for some reason, armor model spec stuff is empty
     * @return
     */
    public void refreshPartSpecs(boolean startClean) {

//        if (true) {
        this.parts = new ArrayList<>();
        getRenderCapability().ifPresent(iModelSpecNBT -> {
            CompoundTag renderTag = iModelSpecNBT.getRenderTag();
            if (startClean || parts.isEmpty()) {
                EquipmentSlot slot = Mob.getEquipmentSlotForItem(iModelSpecNBT.getItemStack());
                if (iModelSpecNBT instanceof IArmorModelSpecNBT) {
                    model.getPartSpecs().forEach(spec -> {
                        if (spec.getBinding().getSlot().equals(slot)) {
                            String tagName = spec instanceof TexturePartSpec ? TagConstants.TEXTURESPEC : ModelRegistry.getInstance().makeName(spec);

                            parts.add(createNewFrame(spec, renderTag.getCompound(tagName)));
                        }
                    });
                } else if (iModelSpecNBT instanceof IHandHeldModelSpecNBT) {
                    model.getPartSpecs().forEach(spec -> {
                        if (spec.getBinding().getSlot().getType().equals(EquipmentSlot.Type.HAND)) {
                            String tagName = spec instanceof TexturePartSpec ? TagConstants.TEXTURESPEC : ModelRegistry.getInstance().makeName(spec);
                            parts.add(createNewFrame(spec, renderTag.getCompound(tagName)));
                        }
                    });
                }

                parts.forEach(spec -> {
                    spec.setEnabled(this.open);
                    spec.setVisible(this.open);
                    spec.setTop(this.top() + specHeight);
                    // Only one TexturePartSpec is allowed at a time, so figure out if this one is enabled
                    if (spec.partSpec instanceof TexturePartSpec && renderTag.contains(TagConstants.TEXTURESPEC)) {
                        CompoundTag texSpecTag = renderTag.getCompound(TagConstants.TEXTURESPEC);
                        if (spec.partSpec.spec.getOwnName().equals(texSpecTag.getString(TagConstants.MODEL))) {
                            spec.tagdata = renderTag.getCompound(TagConstants.TEXTURESPEC);
                        } else {
                            spec.tagdata = new CompoundTag();
                        }
                    } else {
                        if (renderTag.contains(spec.tagname)) {
                            spec.tagdata = renderTag.getCompound(spec.tagname);
                        } else {
                            spec.tagdata = new CompoundTag();
                        }
                    }
                });
            }
        });

        if (!parts.isEmpty()) {
            if (this.open) {
                // name line plus all spec lines
                this.setHeight(specHeight + specHeight * parts.size());
            } else {
                // name line only
                this.setHeight(specHeight);
            }
        } else {
            this.setHeight(specHeight);
        }
    }

    /**
     * Creates the frame that displays the model part controls and part display name
     * @param partSpec
     * @return
     */
    public PartManipSubFrame createNewFrame(PartSpecBase partSpec, CompoundTag tagdata) {
        PartManipSubFrame newFrame = new PartManipSubFrame(
                partSpec,
                this.left(),
                this.top() + specHeight,
                this.width(),
                specHeight,
                tagdata);

        if (parts.size() > 0) {
            newFrame.setBelow(parts.get(parts.size() -1));
        }
        return newFrame;
    }

    /**
     * Updates settings for each part after a color is deleted
     * @param index
     */
    public void decrAbove(int index) {
        for (PartManipSubFrame subFrame : parts) {
            subFrame.decrAbove(index);
        }
    }

    /**
     * Gets the current NBT tag from the model
     * @param partSpec
     * @return
     */
    @Nullable
    public CompoundTag getSpecTagOrEmpty(PartSpecBase partSpec) {
        return getRenderCapability().map(iModelSpecNBT -> {
            CompoundTag specTag = new CompoundTag();
            CompoundTag renderTag = iModelSpecNBT.getRenderTag();
            if (renderTag != null /*&& !renderTag.isEmpty()*/) {
                // there can be many ModelPartSpecs
                if (partSpec instanceof ModelPartSpec) {
                    String name = ModelRegistry.getInstance().makeName(partSpec);
                    specTag = renderTag.contains(name) ? renderTag.getCompound(name) : new CompoundTag();
                }
                // Only one TexturePartSpec is allowed at a time, so figure out if this one is enabled
                if (partSpec instanceof TexturePartSpec && renderTag.contains(TagConstants.TEXTURESPEC)) {
                    CompoundTag texSpecTag = renderTag.getCompound(TagConstants.TEXTURESPEC);
                    if (partSpec.spec.getOwnName().equals(texSpecTag.getString(TagConstants.MODEL))) {
                        specTag = renderTag.getCompound(TagConstants.TEXTURESPEC);
                    }
                }
            }
            return specTag;
        }).orElse(new CompoundTag()); // this only returns empty
    }

    /**
     * Fixme: why are we setting render tag here
     * @param partSpec
     * @return
     */
    public CompoundTag getOrMakeSpecTag(PartSpecBase partSpec) {
        String name;
        CompoundTag nbt = getSpecTagOrEmpty(partSpec);
        if (nbt.isEmpty()) {
            if (partSpec instanceof ModelPartSpec) {
                name = ModelRegistry.getInstance().makeName(partSpec);
                ((ModelPartSpec) partSpec).multiSet(nbt, null, null);
            } else {
                name = TagConstants.TEXTURESPEC;
                partSpec.multiSet(nbt, null);
            }

            // update the render tag client side. The server side update is called below.
            if (getRenderCapability() != null) {
                this.getRenderCapability().ifPresent(specNBT->{
//                    CompoundTag renderTag  = specNBT.getRenderTag();
                    CompoundTag renderTag  = specNBT.getRenderTag().copy();
                    if (renderTag != null && !renderTag.isEmpty()) {
                        renderTag.put(name, nbt);
                        // FIXME this is screwing up render tags!!!!!
//                        specNBT.setRenderTag(renderTag, TagConstants.RENDER);
                    }
                });
            }
        }
        return nbt;
    }

    public boolean isOpen() {
        return open;
    }

    /**
     * Draw model display name, open/close indicator, and parts with their controls
     * Fixme: replace with a normal render loop
     *
     * @param matrixStack
     */
    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        if (!parts.isEmpty()) {
            StringUtils.drawShadowedString(matrixStack, model.getDisaplayName(), left() + iconWidth, top() + StringUtils.getStringHeight() - iconWidth);
            openCloseButton.setPosition(new MusePoint2D(this.left() + 2, this.top() + iconWidth * 0.5));
            openCloseButton.setDirection(open? ClickableIndicatorArrow.ArrowDirection.DOWN : ClickableIndicatorArrow.ArrowDirection.RIGHT);
            openCloseButton.render(matrixStack, mouseX, mouseY, partialTick);
            if (open) {
                for (PartManipSubFrame partFrame : parts) {
                    partFrame.render(matrixStack, mouseX, mouseY, partialTick);
                }
            }
        }
    }

    public void openClose() {
        this.open =!open;
        refreshPartSpecs(false);
    }

    /**
     * FIXME!!!! actual buttons????
     * @param x
     * @param y
     * @return
     */
    public boolean mouseClicked(double x, double y, int button) {
        // player clicked outside the area
        if (!this.containsPoint(x, y)) {
            return false;
        }

        // opens the list of partSpecs
        /** Open/Close button */
        if (openCloseButton.mouseClicked(x, y, button)) {
            return true;
        }

        for (PartManipSubFrame partFrame: parts) {
            if (partFrame.mouseClicked(x, y, button)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Note that these don't actually move, just rendered to look that way.
     */
    class PartManipSubFrame extends AbstractGuiFrame {
        PartSpecBase partSpec;
        CompoundTag tagdata;
        String tagname;

        RadioButton transparent;
        RadioButton normal;
        RadioButton glow;
        ArrayList<RadioButton> buttons;
        Rect spacerRect;
        List<ColorRadioButton> colorButtons;

        public PartManipSubFrame(PartSpecBase partSpec, double left, double top, double width, double height, CompoundTag tagdataIn) {
            super(new Rect(left, top, left + width, top + height));
            this.partSpec = partSpec;
            this.tagname = partSpec instanceof TexturePartSpec ? TagConstants.TEXTURESPEC : ModelRegistry.getInstance().makeName(partSpec);
            this.tagdata = tagdataIn;
            this.buttons = new ArrayList<>();
            this.colorButtons = new ArrayList<>();

            /** Transparent (turns rendering off for part) */
            transparent = new RadioButton(IconUtils.getIcon().transparentArmor, 8, 8, 0,0);
            transparent.setOnPressed(pressed -> {
                tagdata = new CompoundTag();
                itemSelector.selectedType().ifPresent(slotType -> {
                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata));
                        }
                );
            });
            buttons.add(transparent);

            /** Normal (enables rendering of part with normal lighting) */
            normal = new RadioButton(IconUtils.getIcon().normalArmor, 8, 8, 0,0);
            normal.setOnPressed(pressed -> {
                tagdata = getOrMakeSpecTag(partSpec);
                if (partSpec instanceof ModelPartSpec) {
                    ((ModelPartSpec) partSpec).setGlow(tagdata, false);
                }
                itemSelector.selectedType().ifPresent(slotType -> {
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata));
                });
            });
            normal.setRightOf(transparent);
            buttons.add(normal);

            /** Glow (enables rendering of part with full brightness ) */
            glow = new RadioButton(IconUtils.getIcon().glowArmor, 8, 8, 0,0);
            glow.setOnPressed(pressed -> {
                if (partSpec instanceof ModelPartSpec) {
                    tagname = ModelRegistry.getInstance().makeName(partSpec);
                    tagdata = getOrMakeSpecTag(partSpec);
                    ((ModelPartSpec) partSpec).setGlow(tagdata, true);
                    itemSelector.selectedType().ifPresent(slotType -> {
                        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata));
                    });
                }
            });
            glow.setEnabled(partSpec instanceof ModelPartSpec);
            glow.setRightOf(normal);
            buttons.add(glow);

            /** pick selected button according to current part settings */
            int selcomp = tagdata.isEmpty() ? 0 : (partSpec instanceof ModelPartSpec && ((ModelPartSpec) partSpec).getGlow(tagdata) ? 2 : 1);
            buttons.get(selcomp).isSelected = true;
            spacerRect = new Rect(MusePoint2D.ZERO, new MusePoint2D(4, height)).setRightOf(glow);
            //----------------------------

            for (int i=0; i < colourframe.colours().length; i++) {
                Color colour = new Color(colourframe.colours()[i]);
                ColorRadioButton colorRadioButton = makeColourButton(colour);
                colorButtons.add(colorRadioButton);
            }
        }

        public void init() {
        }

        /**
         * Fixme??? no color button cleanup?
         *
         * @param index
         */
        public void decrAbove(int index) {
            this.tagdata = getOrMakeSpecTag(partSpec);
            if (tagdata != null) {
                int oldindex = partSpec.getColourIndex(tagdata);
                if (oldindex >= index && oldindex > 0) {
                    int newIndex = oldindex -1;
                    partSpec.setColourIndex(tagdata, newIndex);
                    // remove extra buttons
                    while (colorButtons.size() > colourframe.colours().length) {
                        colorButtons.remove(colorButtons.size() -1);
                    }

                    // button arrayList is quite fluid so avoid index out of bounds
                    for(int i =0; i < Math.min(colorButtons.size(), colourframe.colours().length); i++) {
                        ColorRadioButton button = colorButtons.get(i);
                        button.setColour(new Color(colourframe.colours()[i]));
                        button.isSelected = i == newIndex;
                    }

                    itemSelector.selectedType().ifPresent(slotType -> {
                        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata));
                    });
                }
            }
        }

        ColorRadioButton makeColourButton(Color colour) {
            ColorRadioButton colorRadioButton = new ColorRadioButton(0,0, colour);
            colorRadioButton.setOnPressed(pressed -> {
                int index  = colorButtons.indexOf(colorRadioButton);
                partSpec.setColourIndex(tagdata, index);
                itemSelector.selectedType().ifPresent(slotType -> {
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata));
                });
            });

            int colorIndex = partSpec.getColourIndex(tagdata);
            int i = colorButtons.size();
            colorRadioButton.isSelected = i==colorIndex;
            if (i == 0) {
                colorRadioButton.setRightOf(spacerRect);
            } else {
                colorRadioButton.setRightOf(colorButtons.get(i-1));
            }
            return colorRadioButton;
        }

        @Override
        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
            if( this.isEnabled() && this.isVisible()) {
                if(tagdata == null || tagdata.isEmpty()) {
                    transparent.isSelected = true;
                    normal.isSelected = false;
                    glow.isSelected = false;

                } else if (partSpec instanceof ModelPartSpec && ((ModelPartSpec) partSpec).getGlow(tagdata)) {
                    transparent.isSelected = false;
                    normal.isSelected = false;
                    glow.isSelected = true;

                } else {
                    transparent.isSelected = false;
                    normal.isSelected = true;
                    glow.isSelected = false;
                }

                MusePoint2D pos = new MusePoint2D(this.left() + 3, this.centerY());
                for(int i = 0; i < buttons.size(); i++) {
                    RadioButton button = buttons.get(i);
                    button.setPosition(pos);
                    button.render(matrixStack, mouseX, mouseY, partialTick);
                }

                spacerRect.setPosition(pos);

                // remove extra buttons
                while (colorButtons.size() > colourframe.colours().length) {
                    colorButtons.remove(colorButtons.size() -1);
                }

                // add missing buttons
                while (colorButtons.size() < colourframe.colours().length) {
                    Color colour = new Color(colourframe.colours()[colorButtons.size()]); // button not added yet
                    colorButtons.add(makeColourButton(colour));
                }

                for(int i =0; i < Math.min(colorButtons.size(), colourframe.colours().length); i++) {
                    ColorRadioButton button = colorButtons.get(i);
                    button.setPosition(pos);
                    button.setColour(new Color(colourframe.colours()[i]));
                    button.isSelected = i == partSpec.getColourIndex(tagdata);
                    button.render(matrixStack, mouseX, mouseY, partialTick);
                }

                if (!colorButtons.isEmpty()) {
                    StringUtils.drawText(matrixStack, partSpec.getDisaplayName(),
                            colorButtons.get(colorButtons.size() - 1).right() + 4,
                            top() + StringUtils.getStringHeight() - iconWidth,
                            Color.WHITE);
                } else {
                    NuminaLogger.logError("color buttons shouldn't be empty");
                }
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            int i, j;
            for(i=0; i < buttons.size(); i++) {
                if (buttons.get(i).mouseClicked(mouseX, mouseY, button)) {
                    for (j=0; j < buttons.size(); j++) {
                        buttons.get(j).isSelected = i==j;
                    }
                    return true;
                }
            }

            for(i=0; i < colorButtons.size(); i++) {
                if (colorButtons.get(i).mouseClicked(mouseX, mouseY, button)) {
                    for (j=0; j < colorButtons.size(); j++) {
                        colorButtons.get(j).isSelected = i==j;
                    }
                    return true;
                }
            }
            return false;
        }
    }
}