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
import lehjr.numina.network.NuminaPackets;
import lehjr.numina.network.packets.CosmeticInfoPacket;
import lehjr.numina.util.capabilities.render.IArmorModelSpecNBT;
import lehjr.numina.util.capabilities.render.IHandHeldModelSpecNBT;
import lehjr.numina.util.capabilities.render.IModelSpecNBT;
import lehjr.numina.util.capabilities.render.CapabilityModelSpec;
import lehjr.numina.util.capabilities.render.modelspec.*;
import lehjr.numina.util.client.gui.GuiIcon;
import lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import lehjr.numina.util.client.render.MuseIconUtils;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Color;
import lehjr.numina.util.math.MuseMathUtils;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Mob;
import net.minecraft.inventory.EquipmentSlot;
import net.minecraft.nbt.CompoundTag;
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
    public ColorPickerFrame colourframe;
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
                                 ColorPickerFrame colourframe,
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
        setBackgroundColor(Color.BLACK.withAlpha(0.1F));
    }

    @Override
    public void drawBackground(PoseStack matrixStack) {

    }

    public Optional<IModelSpecNBT> getRenderCapability() {
        return this.itemSelector.getModularItemOrEmpty().getCapability(CapabilityModelSpec.RENDER)
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
                            EquipmentSlot slot = Mob.getEquipmentSlotForItem(iModelSpecNBT.getItemStack());
                            if (iModelSpecNBT instanceof IArmorModelSpecNBT) {
                                model.getPartSpecs().forEach(spec -> {
                                    if (spec.getBinding().getSlot().equals(slot)) {
                                        partSpecs.add(spec);
                                    }
                                });
                            } else if (iModelSpecNBT instanceof IHandHeldModelSpecNBT) {
                                model.getPartSpecs().forEach(spec -> {
                                    if (spec.getBinding().getSlot().getType().equals(EquipmentSlot.Type.HAND)) {
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
    public CompoundTag getOrDontGetSpecTag(PartSpecBase partSpec) {
        return getRenderCapability().map(iModelSpecNBT -> {
            CompoundTag specTag = new CompoundTag();
            CompoundTag renderTag = iModelSpecNBT.getRenderTag();
            if (renderTag != null /*&& !renderTag.isEmpty()*/) {
                // there can be many ModelPartSpecs
                if (partSpec instanceof ModelPartSpec) {
                    String name = ModelRegistry.getInstance().makeName(partSpec);
                    specTag = renderTag.contains(name) ? renderTag.getCompound(name) : null;
                }
                // Only one TexturePartSpec is allowed at a time, so figure out if this one is enabled
                if (partSpec instanceof TexturePartSpec && renderTag.contains(NuminaConstants.NBT_TEXTURESPEC_TAG)) {
                    CompoundTag texSpecTag = renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG);
                    if (partSpec.spec.getOwnName().equals(texSpecTag.getString(NuminaConstants.MODEL))) {
                        specTag = renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG);
                    }
                }
            }
            return specTag;
        }).orElse(new CompoundTag());
    }

    public CompoundTag getSpecTag(PartSpecBase partSpec) {
        CompoundTag nbt = getOrDontGetSpecTag(partSpec);
        return nbt != null ? nbt : new CompoundTag();
    }

    public CompoundTag getOrMakeSpecTag(PartSpecBase partSpec) {
        String name;
        CompoundTag nbt = getSpecTag(partSpec);
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
                    CompoundTag renderTag  = specNBT.getRenderTag();
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

    public void drawPartial(PoseStack matrixStack, double min, double max) {
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
            CompoundTag tagdata = getOrDontGetSpecTag(spec);

            if (tagdata != null) {
                int oldindex = spec.getColorIndex(tagdata);
                if (oldindex >= index && oldindex > 0) {
                    spec.setColorIndex(tagdata, oldindex - 1);
                    this.itemSelector.selectedType().ifPresent(slotType ->
                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata)));
                }
            }
        }
    }

    public void drawSpecPartial(PoseStack matrixStack, double x, double y, PartSpecBase partSpec) {
//        super.render(matrixStack, (int)x, (int)y, Minecraft.getInstance().getFrameTime()); // draws the border, mainly a debugging thing

        GuiIcon icon = MuseIconUtils.getIcon();
        CompoundTag tag = this.getSpecTag(partSpec);
        int selcomp = tag.isEmpty() ? 0 : (partSpec instanceof ModelPartSpec && ((ModelPartSpec) partSpec).getGlow(tag) ? 2 : 1);
        int selcolour = partSpec.getColorIndex(tag);

        icon.transparentArmor.draw(matrixStack, x, y, Color.WHITE);

        icon.normalArmor.draw(matrixStack, x+ iconWidth, y, Color.WHITE);

        if (partSpec instanceof ModelPartSpec) {
            icon.glowArmor.draw(matrixStack, x + 16, y, Color.WHITE);
        }

        icon.selectedArmorOverlay.draw(matrixStack, x + selcomp * iconWidth, y, Color.WHITE);

        double acc = (x + 28);
        for (int colour : colourframe.colours()) {
            icon.armorColorPatch.draw(matrixStack, acc, y, new Color(colour));
            acc += 8;
        }

        double textstartx = acc;
        if (selcomp > 0) {
            icon.selectedArmorOverlay.draw(matrixStack, x + 28 + selcolour * iconWidth, y, Color.WHITE);
        }
        MuseRenderer.drawText(matrixStack, partSpec.getDisaplayName(), (float) textstartx + 4, (float) y, Color.WHITE);
    }

    // FIXME
    public void drawOpenArrow(PoseStack matrixStack, double min, double max) {
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
                    .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
            buffer.vertex(matrix4f,(float)this.left() + 5, (float)MuseMathUtils.clampDouble(this.top() + 7, min, max), zLevel)
                    .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
            buffer.vertex(matrix4f,(float)this.left() + 7, (float)MuseMathUtils.clampDouble(this.top() + 3, min, max), zLevel)
                    .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
        } else {
            buffer.vertex(matrix4f,(float)this.left() + 3, (float)MuseMathUtils.clampDouble(this.top() + 3, min, max), zLevel)
                    .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
            buffer.vertex(matrix4f,(float)this.left() + 3, (float)MuseMathUtils.clampDouble(this.top() + 7, min, max), zLevel)
                    .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                    .uv2(0x00F000F0)
                    .endVertex();
            buffer.vertex(matrix4f,(float)this.left() + 7, (float)MuseMathUtils.clampDouble(this.top() + 5, min, max), zLevel)
                    .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
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
        CompoundTag tagdata;
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
            NuminaLogger.logger.debug("Line " + lineNumber + " Column " + columnNumber);

            switch (columnNumber) {
                // removes the associated tag from the render tag making the part not isEnabled
                case 0: {
                    tagname = spec instanceof TexturePartSpec ? NuminaConstants.NBT_TEXTURESPEC_TAG : ModelRegistry.getInstance().makeName(spec);
                    this.itemSelector.selectedType().ifPresent(slotType ->
                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, new CompoundTag())));
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
            spec.setColorIndex(tagdata, columnNumber);
            this.itemSelector.selectedType().ifPresent(slotType ->
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, tagname, tagdata)));
            return true;
        }
        return false;
    }
}