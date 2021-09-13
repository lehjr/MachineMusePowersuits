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

package com.github.lehjr.powersuits.client.gui.modding.cosmetic;

import com.github.lehjr.numina.basemod.MuseLogger;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.CosmeticInfoPacket;
import com.github.lehjr.numina.util.capabilities.render.IArmorModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.IHandHeldModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.IModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import com.github.lehjr.numina.util.capabilities.render.modelspec.*;
import com.github.lehjr.numina.util.client.gui.GuiIcon;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.render.MuseIconUtils;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.github.lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

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
public class PartSpecManipSubFrame extends DrawableTile {
    public SpecBase model;
    public ColourPickerFrame colourframe;
    public ModularItemSelectionFrame itemSelector;
    public List<PartSpecBase> partSpecs;
    public boolean open;
    Minecraft minecraft;
    float zLevel;
    final double specHeight = 9;
    final double iconWidth = 8;

    public PartSpecManipSubFrame(SpecBase model,
                                 double left,
                                 double top,
                                 double right,
                                 double bottom,
                                 ColourPickerFrame colourframe,
                                 ModularItemSelectionFrame itemSelector,
                                 float zLevel) {
        super(left, top, right, bottom);
        this.model = model;
        this.colourframe = colourframe;
        this.itemSelector = itemSelector;
        this.refreshPartSpecs();
        this.open = true;
        this.zLevel = zLevel;
        minecraft = Minecraft.getInstance();
        setBackgroundColour(Colour.BLACK.withAlpha(0.1F));
    }

    @Override
    public void drawBackground(MatrixStack matrixStack) {

    }

    public Optional<IModelSpecNBT> getRenderCapability() {
        return this.itemSelector.getModularItemOrEmpty().getCapability(ModelSpecNBTCapability.RENDER)
                .filter(IModelSpecNBT.class::isInstance)
                .map(IModelSpecNBT.class::cast);
    }

    public List<PartSpecBase> getPartSpecs() {
        return partSpecs;
    }


    /**
     * FIXME!!! for some reason, armor model spec stuff is empty
     * @return
     */
    public void refreshPartSpecs() {
        this.partSpecs = new ArrayList<>();
        getRenderCapability()
                .ifPresent(iModelSpecNBT -> {
                            EquipmentSlotType slot = MobEntity.getEquipmentSlotForItem(iModelSpecNBT.getItemStack());
                            if (iModelSpecNBT instanceof IArmorModelSpecNBT) {
                                model.getPartSpecs().forEach(spec -> {
                                    if (spec.getBinding().getSlot().equals(slot)) {
                                        partSpecs.add(spec);
                                    }
                                });
                            } else if (iModelSpecNBT instanceof IHandHeldModelSpecNBT) {
                                model.getPartSpecs().forEach(spec -> {
                                    if (spec.getBinding().getSlot().getType().equals(EquipmentSlotType.Group.HAND)) {
                                        partSpecs.add(spec);
                                    }
                                });
                            }
                        });

        if (!partSpecs.isEmpty()) {
            if (this.open) {
                // name line plus all spec lines
                this.setHeight(specHeight + specHeight * partSpecs.size());
            } else {
                // name line only
                this.setHeight(specHeight);
            }
        }
    }

    @Nullable
    public CompoundNBT getOrDontGetSpecTag(PartSpecBase partSpec) {
        return getRenderCapability().map(iModelSpecNBT -> {
            CompoundNBT specTag = new CompoundNBT();
            CompoundNBT renderTag = iModelSpecNBT.getRenderTag();
            if (renderTag != null && !renderTag.isEmpty()) {
                // there can be many ModelPartSpecs
                if (partSpec instanceof ModelPartSpec) {
                    String name = ModelRegistry.getInstance().makeName(partSpec);
                    specTag = renderTag.contains(name) ? renderTag.getCompound(name) : null;
                }
                // Only one TexturePartSpec is allowed at a time, so figure out if this one is enabled
                if (partSpec instanceof TexturePartSpec && renderTag.contains(NuminaConstants.NBT_TEXTURESPEC_TAG)) {
                    CompoundNBT texSpecTag = renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG);
                    if (partSpec.spec.getOwnName().equals(texSpecTag.getString(NuminaConstants.TAG_MODEL))) {
                        specTag = renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG);
                    }
                }
            }
            return specTag;
        }).orElse(null);
    }

    public CompoundNBT getSpecTag(PartSpecBase partSpec) {
        CompoundNBT nbt = getOrDontGetSpecTag(partSpec);
        return nbt != null ? nbt : new CompoundNBT();
    }

    public CompoundNBT getOrMakeSpecTag(PartSpecBase partSpec) {
        String name;
        CompoundNBT nbt = getSpecTag(partSpec);
        if (nbt.isEmpty()) {
            if (partSpec instanceof ModelPartSpec) {
                name = ModelRegistry.getInstance().makeName(partSpec);
                ((ModelPartSpec) partSpec).multiSet(nbt, null, null);
            } else {
                name = NuminaConstants.NBT_TEXTURESPEC_TAG;
                partSpec.multiSet(nbt, null);
            }

            // update the render tag client side. The server side update is called below.
            if (getRenderCapability() != null) {
                this.getRenderCapability().ifPresent(specNBT->{
                    CompoundNBT renderTag  = specNBT.getRenderTag();
                    if (renderTag != null && !renderTag.isEmpty()) {
                        renderTag.put(name, nbt);
                        specNBT.setRenderTag(renderTag, NuminaConstants.TAG_RENDER);
                    }
                });
            }
        }
        return nbt;
    }

    public boolean isOpen() {
        return open;
    }

    public void drawPartial(MatrixStack matrixStack, double min, double max) {
        if (!partSpecs.isEmpty()) {
            MuseRenderer.drawShadowedString(matrixStack, model.getDisaplayName(), left() + iconWidth, top());
            drawOpenArrow(matrixStack, min, max);
            if (open) {
                int y = (int) (top() + specHeight);
                for (PartSpecBase spec : partSpecs) {
                    drawSpecPartial(matrixStack, left(), y, spec);
                    y += specHeight;
                }
            }
        } else {
//            System.out.println("specs empty");
        }
    }

    public void decrAbove(int index) {
        for (PartSpecBase spec : partSpecs) {
            String tagname = ModelRegistry.getInstance().makeName(spec);
            CompoundNBT tagdata = getOrDontGetSpecTag(spec);

            if (tagdata != null) {
                int oldindex = spec.getColourIndex(tagdata);
                if (oldindex >= index && oldindex > 0) {
                    spec.setColourIndex(tagdata, oldindex - 1);
                    this.itemSelector.selectedType().ifPresent(slotType ->
                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata)));
                }
            }
        }
    }

    public void drawSpecPartial(MatrixStack matrixStack, double x, double y, PartSpecBase partSpec) {
        super.render(matrixStack, (int)x, (int)y, Minecraft.getInstance().getFrameTime());

        GuiIcon icon = MuseIconUtils.getIcon();
        CompoundNBT tag = this.getSpecTag(partSpec);
        int selcomp = tag.isEmpty() ? 0 : (partSpec instanceof ModelPartSpec && ((ModelPartSpec) partSpec).getGlow(tag) ? 2 : 1);
        int selcolour = partSpec.getColourIndex(tag);

        icon.transparentArmor.draw(matrixStack, x, y, Colour.WHITE);

        icon.normalArmor.draw(matrixStack, x+ iconWidth, y, Colour.WHITE);

        if (partSpec instanceof ModelPartSpec) {
            icon.glowArmor.draw(matrixStack, x + 16, y, Colour.WHITE);
        }

        icon.selectedArmorOverlay.draw(matrixStack, x + selcomp * iconWidth, y, Colour.WHITE);

        double acc = (x + 28);
        for (int colour : colourframe.colours()) {
            icon.armorColourPatch.draw(matrixStack, acc, y, new Colour(colour));
            acc += 8;
        }

        double textstartx = acc;
        if (selcomp > 0) {
            icon.selectedArmorOverlay.draw(matrixStack, x + 28 + selcolour * iconWidth, y, Colour.WHITE);
        }
        MuseRenderer.drawText(matrixStack, partSpec.getDisaplayName(), (float) textstartx + 4, (float) y, Colour.WHITE);
    }

    // FIXME
    public void drawOpenArrow(MatrixStack matrixStack, double min, double max) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Matrix4f matrix4f = matrixStack.last().pose();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR_LIGHTMAP);
        if (this.open) {
            buffer.vertex(matrix4f,(float)this.left() + 3, (float) MuseMathUtils.clampDouble(this.top() + 3, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
            buffer.vertex(matrix4f,(float)this.left() + 5, (float)MuseMathUtils.clampDouble(this.top() + 7, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
            buffer.vertex(matrix4f,(float)this.left() + 7, (float)MuseMathUtils.clampDouble(this.top() + 3, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
        } else {
            buffer.vertex(matrix4f,(float)this.left() + 3, (float)MuseMathUtils.clampDouble(this.top() + 3, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
            buffer.vertex(matrix4f,(float)this.left() + 3, (float)MuseMathUtils.clampDouble(this.top() + 7, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
            buffer.vertex(matrix4f,(float)this.left() + 7, (float)MuseMathUtils.clampDouble(this.top() + 5, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
        }
        tessellator.end();
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }



    public boolean tryMouseClick(double x, double y) {
        CompoundNBT tagdata;
        String tagname;

        // player clicked outside the area
        if (!this.containsPoint(x, y)) {
            return false;

            // opens the list of partSpecs
        } else if (x > this.left() + 2 && x < this.left() + iconWidth && y > this.top() + 2 && y < this.top() + specHeight) {
            this.open = !this.open;
            this.refreshPartSpecs();
            return true;

            // player clicked one of the icons for off/on/glowOn
        } else if (x < this.left() + 24 && y > this.top() + specHeight) {
            int lineNumber = (int) ((y - this.top() - specHeight) / specHeight);
            int columnNumber = (int) ((x - this.left()) / iconWidth);
            PartSpecBase spec = partSpecs.get(Math.max(Math.min(lineNumber, partSpecs.size() - 1), 0));
            MuseLogger.logger.debug("Line " + lineNumber + " Column " + columnNumber);

            switch (columnNumber) {
                // removes the associated tag from the render tag making the part not isEnabled
                case 0: {
                    tagname = spec instanceof TexturePartSpec ? NuminaConstants.NBT_TEXTURESPEC_TAG : ModelRegistry.getInstance().makeName(spec);
                    this.itemSelector.selectedType().ifPresent(slotType ->
                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, new CompoundNBT())));
                    this.refreshPartSpecs();
                    return true;
                }

                // set part to isEnabled
                case 1: {
                    tagname = spec instanceof TexturePartSpec ? NuminaConstants.NBT_TEXTURESPEC_TAG : ModelRegistry.getInstance().makeName(spec);
                    tagdata = this.getOrMakeSpecTag(spec);
                    if (spec instanceof ModelPartSpec) {
                        ((ModelPartSpec) spec).setGlow(tagdata, false);
                    }
                    this.itemSelector.selectedType().ifPresent(slotType ->
                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata)));
                    this.refreshPartSpecs();
                    return true;
                }

                // glow on
                case 2: {
                    if (spec instanceof ModelPartSpec) {
                        tagname = ModelRegistry.getInstance().makeName(spec);
                        tagdata = this.getOrMakeSpecTag(spec);
                        ((ModelPartSpec) spec).setGlow(tagdata, true);
                        this.itemSelector.selectedType().ifPresent(slotType ->
                                NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata)));
                        this.refreshPartSpecs();
                        return true;
                    }
                    return false;
                }
                default:
                    return false;
            }
        }
        // player clicked a color icon
        else if (x > this.left() + 28 && x < this.left() + 28 + this.colourframe.colours().length * iconWidth) {
            int lineNumber = (int) ((y - this.top() - specHeight) / specHeight);
            int columnNumber = (int) ((x - this.left() - 28) / iconWidth);
            PartSpecBase spec = partSpecs.get(Math.max(Math.min(lineNumber, partSpecs.size() - 1), 0));
            tagname = spec instanceof TexturePartSpec ? NuminaConstants.NBT_TEXTURESPEC_TAG : ModelRegistry.getInstance().makeName(spec);
            tagdata = this.getOrMakeSpecTag(spec);
            spec.setColourIndex(tagdata, columnNumber);
            this.itemSelector.selectedType().ifPresent(slotType ->
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata)));
            return true;
        }
        return false;
    }
}