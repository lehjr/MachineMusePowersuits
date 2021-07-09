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
import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import com.github.lehjr.numina.util.capabilities.render.modelspec.*;
import com.github.lehjr.numina.util.client.gui.GuiIcon;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableItem;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRelativeRect;
import com.github.lehjr.numina.util.client.render.MuseIconUtils;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:46 AM, 30/04/13
 * <p>
 * Ported to Java by lehjr on 11/2/16.
 */
public class PartSpecManipSubFrame {
    public SpecBase model;
    public ColourPickerFrame colourframe;
    public ItemSelectionFrame itemSelector;
    public MuseRelativeRect border;
    public List<PartSpecBase> partSpecs;
    public boolean open;
    Minecraft minecraft;
    float zLevel;

    public PartSpecManipSubFrame(SpecBase model,
                                 ColourPickerFrame colourframe,
                                 ItemSelectionFrame itemSelector,
                                 MuseRelativeRect border,
                                 float zLevel) {
        this.model = model;
        this.colourframe = colourframe;
        this.itemSelector = itemSelector;
        this.border = border;
        this.partSpecs = this.getPartSpecs();
        this.open = true;
        this.zLevel = zLevel;
        minecraft = Minecraft.getInstance();
    }

    /**
     * get all valid parts of model for the equipment itemSlot
     * Don't bother converting to Java stream with filter, the results are several times slower
     */
    /**
     * FIXME!!! for some reason, armor model spec stuff is empty
     * @return
     */
    private List<PartSpecBase> getPartSpecs() {
        List<PartSpecBase> specsArray = new ArrayList<>();
        Iterator<PartSpecBase> specIt = model.getPartSpecs().iterator();

        if (getSelectedItem() != null) {
            System.out.println("stack: " + getSelectedItem().getStack().serializeNBT());


            getSelectedItem().getStack().getCapability(ModelSpecNBTCapability.RENDER).ifPresent(specNBT ->{
                PartSpecBase spec;

                while (specIt.hasNext()) {
                    spec = specIt.next();
                    System.out.println("specky here: " + spec.getDisaplayName().getString());
                    System.out.println("specNBT class: " + specNBT.getClass());

                    // this COULD fail here if the wrong capability is applied, otherwise should be fine.

                    //// this part is failing, WRONG SPEC is being applied
                    if (specNBT instanceof IArmorModelSpecNBT) {
                        EquipmentSlotType slot = MobEntity.getSlotForItemStack(getSelectedItem().getStack());
                        System.out.println("slot here: " + slot);

                        if (spec.getBinding().getSlot() == slot) {
                            specsArray.add(spec);
                            System.out.println("spec added: " + spec.getDisaplayName());
                        }

                    // This part actually works
                    } else if (specNBT instanceof IHandHeldModelSpecNBT) {
                        if (spec.getBinding().getSlot().getSlotType().equals(EquipmentSlotType.Group.HAND)) {
                            specsArray.add(spec);
                            System.out.println("spec added: " + spec.getDisaplayName());
                        }
                    }
                    else {
                        System.out.println("spec not added: " + spec.getDisaplayName());
                    }
                }
                System.out.println("model: " + model.getDisaplayName());


                System.out.println("specArraySize: " + specsArray.size());
            });
        }


        return specsArray;
    }

    public ClickableItem getSelectedItem() {
        return this.itemSelector.getSelectedItem();
    }

    @Nullable
    public CompoundNBT getOrDontGetSpecTag(PartSpecBase partSpec) {
        if (this.getSelectedItem() == null) {
            return null;
        }

        AtomicReference<CompoundNBT> specTag = new AtomicReference<>(null);

        getSelectedItem().getStack().getCapability(ModelSpecNBTCapability.RENDER).ifPresent(specNBT->{
            CompoundNBT renderTag = specNBT.getRenderTag();

            if (renderTag != null && !renderTag.isEmpty()) {
                // there can be many ModelPartSpecs
                if (partSpec instanceof ModelPartSpec) {
                    String name = ModelRegistry.getInstance().makeName(partSpec);
                    specTag.set(renderTag.contains(name) ? renderTag.getCompound(name) : null);

//                    System.out.println("spec tag here: " + specTag);
                }
                // Only one TexturePartSpec is allowed at a time, so figure out if this one is enabled
                if (partSpec instanceof TexturePartSpec && renderTag.contains(NuminaConstants.NBT_TEXTURESPEC_TAG)) {
                    CompoundNBT texSpecTag = renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG);
                    if (partSpec.spec.getOwnName().equals(texSpecTag.getString(NuminaConstants.TAG_MODEL))) {
                        specTag.set(renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG));
                    }
//                    System.out.println("spec tag here: " + specTag);
                }
            }
//            else {
//                System.out.println("default spec tag here: " + specNBT.getDefaultRenderTag());
//                specTag.set(specNBT.getDefaultRenderTag());
//                System.out.println("renderTag is empty");
//            }
        });
        return specTag.get();
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
            if (getSelectedItem() != null) {
                this.getSelectedItem().getStack().getCapability(ModelSpecNBTCapability.RENDER).ifPresent(specNBT->{
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

    public void updateItems() {
        this.partSpecs = getPartSpecs();
        this.border.setHeight((partSpecs.size() > 0) ? (partSpecs.size() * 8 + 10) : 0);
    }

    public void drawPartial(MatrixStack matrixStack, double min, double max) {
        if (!partSpecs.isEmpty()) {
            MuseRenderer.drawString(matrixStack, model.getDisaplayName(), border.left() + 8, border.top());
            drawOpenArrow(matrixStack, min, max);
            if (open) {
                int y = (int) (border.top() + 8);
                for (PartSpecBase spec : partSpecs) {
                    drawSpecPartial(matrixStack, border.left(), y, spec);
                    y += 8;
                }
            }
        } else {
            System.out.println("specs empty");
        }
    }

    public void decrAbove(int index) {
        for (PartSpecBase spec : partSpecs) {
            String tagname = ModelRegistry.getInstance().makeName(spec);
            ClientPlayerEntity player = minecraft.player;
            CompoundNBT tagdata = getOrDontGetSpecTag(spec);

            if (tagdata != null) {
                int oldindex = spec.getColourIndex(tagdata);
                if (oldindex >= index && oldindex > 0) {
                    spec.setColourIndex(tagdata, oldindex - 1);
                    // how would world not be remote here?
                    if (player.world.isRemote) {
                        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(getSelectedItem().inventorySlot, tagname, tagdata));
                    }
                }
            }
        }
    }

    public void drawSpecPartial(MatrixStack matrixStack, double x, double y, PartSpecBase partSpec) {
        GuiIcon icon = MuseIconUtils.getIcon();
        CompoundNBT tag = this.getSpecTag(partSpec);
        int selcomp = tag.isEmpty() ? 0 : (partSpec instanceof ModelPartSpec && ((ModelPartSpec) partSpec).getGlow(tag) ? 2 : 1);
        int selcolour = partSpec.getColourIndex(tag);

        icon.transparentArmor.draw(matrixStack, x, y, Colour.WHITE);

        icon.normalArmor.draw(matrixStack, x+8, y, Colour.WHITE);

        if (partSpec instanceof ModelPartSpec) {
            icon.glowArmor.draw(matrixStack, x + 16, y, Colour.WHITE);
        }

        icon.selectedArmorOverlay.draw(matrixStack, x + selcomp * 8, y, Colour.WHITE);

        double acc = (x + 28);
        for (int colour : colourframe.colours()) {
            icon.armorColourPatch.draw(matrixStack, acc, y, new Colour(colour));
            acc += 8;
        }

        double textstartx = acc;
        if (selcomp > 0) {
            icon.selectedArmorOverlay.draw(matrixStack, x + 28 + selcolour * 8, y, Colour.WHITE);
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

        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR_LIGHTMAP);
        if (this.open) {
            buffer.pos(matrix4f,(float)this.border.left() + 3, (float) MuseMathUtils.clampDouble(this.border.top() + 3, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .lightmap(0x00F000F0)
                    .endVertex();
            buffer.pos(matrix4f,(float)this.border.left() + 5, (float)MuseMathUtils.clampDouble(this.border.top() + 7, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .lightmap(0x00F000F0)
                    .endVertex();
            buffer.pos(matrix4f,(float)this.border.left() + 7, (float)MuseMathUtils.clampDouble(this.border.top() + 3, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .lightmap(0x00F000F0)
                    .endVertex();
        } else {
            buffer.pos(matrix4f,(float)this.border.left() + 3, (float)MuseMathUtils.clampDouble(this.border.top() + 3, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .lightmap(0x00F000F0)
                    .endVertex();
            buffer.pos(matrix4f,(float)this.border.left() + 3, (float)MuseMathUtils.clampDouble(this.border.top() + 7, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .lightmap(0x00F000F0)
                    .endVertex();
            buffer.pos(matrix4f,(float)this.border.left() + 7, (float)MuseMathUtils.clampDouble(this.border.top() + 5, min, max), zLevel)
                    .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                    .lightmap(0x00F000F0)
                    .endVertex();
        }
        tessellator.draw();
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    public MuseRelativeRect getBorder() {
        if (this.open) {
            border.setHeight(9 + 9 * partSpecs.size());
        } else {
            this.border.setHeight(9.0F);
        }
        return this.border;
    }

    public boolean tryMouseClick(double x, double y) {
        ClientPlayerEntity player = minecraft.player;
        CompoundNBT tagdata;
        String tagname;

        // player clicked outside the area
        if (x < this.border.left() || x > this.border.right() || y < this.border.top() || y > this.border.bottom())
            return false;

            // opens the list of partSpecs
        else if (x > this.border.left() + 2 && x < this.border.left() + 8 && y > this.border.top() + 2 && y < this.border.top() + 8) {
            this.open = !this.open;
            this.getBorder();
            return true;

            // player clicked one of the icons for off/on/glowOn
        } else if (x < this.border.left() + 24 && y > this.border.top() + 8) {
            int lineNumber = (int) ((y - this.border.top() - 8) / 8);
            int columnNumber = (int) ((x - this.border.left()) / 8);
            PartSpecBase spec = partSpecs.get(Math.max(Math.min(lineNumber, partSpecs.size() - 1), 0));
            MuseLogger.logger.debug("Line " + lineNumber + " Column " + columnNumber);

            switch (columnNumber) {
                // removes the associated tag from the render tag making the part not isEnabled
                case 0: {
                    tagname = spec instanceof TexturePartSpec ? NuminaConstants.NBT_TEXTURESPEC_TAG : ModelRegistry.getInstance().makeName(spec);
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(this.getSelectedItem().inventorySlot, tagname, new CompoundNBT()));

                    this.updateItems();
                    return true;
                }

                // set part to isEnabled
                case 1: {
                    tagname = spec instanceof TexturePartSpec ? NuminaConstants.NBT_TEXTURESPEC_TAG : ModelRegistry.getInstance().makeName(spec);
                    tagdata = this.getOrMakeSpecTag(spec);
                    if (spec instanceof ModelPartSpec) {
                        ((ModelPartSpec) spec).setGlow(tagdata, false);
                    }
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(this.getSelectedItem().inventorySlot, tagname, tagdata));

                    this.updateItems();
                    return true;
                }

                // glow on
                case 2: {
                    if (spec instanceof ModelPartSpec) {
                        tagname = ModelRegistry.getInstance().makeName(spec);
                        tagdata = this.getOrMakeSpecTag(spec);
                        ((ModelPartSpec) spec).setGlow(tagdata, true);
                        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(this.getSelectedItem().inventorySlot, tagname, tagdata));
                        this.updateItems();
                        return true;
                    }
                    return false;
                }
                default:
                    return false;
            }
        }
        // player clicked a color icon
        else if (x > this.border.left() + 28 && x < this.border.left() + 28 + this.colourframe.colours().length * 8) {
            int lineNumber = (int) ((y - this.border.top() - 8) / 8);
            int columnNumber = (int) ((x - this.border.left() - 28) / 8);
            PartSpecBase spec = partSpecs.get(Math.max(Math.min(lineNumber, partSpecs.size() - 1), 0));
            tagname = spec instanceof TexturePartSpec ? NuminaConstants.NBT_TEXTURESPEC_TAG : ModelRegistry.getInstance().makeName(spec);
            tagdata = this.getOrMakeSpecTag(spec);
            spec.setColourIndex(tagdata, columnNumber);
            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(this.getSelectedItem().inventorySlot, tagname, tagdata));
            return true;
        }
        return false;
    }
}