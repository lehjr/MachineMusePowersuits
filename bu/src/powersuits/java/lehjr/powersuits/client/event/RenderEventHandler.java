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

package lehjr.powersuits.client.event;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.render.highlight.HighLightCapability;
import lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Colour;
import lehjr.powersuits.client.control.KeybindManager;
import lehjr.powersuits.client.control.MPSKeyBinding;
import lehjr.powersuits.client.model.helper.MPSModelHelper;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.constants.MPSRegistryNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RenderEventHandler {
    INSTANCE;
    private static boolean ownFly = false;
    private final DrawableRelativeRect frame = new DrawableRelativeRect(MPSSettings.getHudKeybindX(), MPSSettings.getHudKeybindY(), MPSSettings.getHudKeybindX() + (float) 16, MPSSettings.getHudKeybindY() +  16, true, Colour.DARK_GREEN.withAlpha(0.2F), Colour.GREEN.withAlpha(0.2F));


    /**
     * Just for a couple modules that can break multiple blocks at once
     * @param event
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderBlockHighlight(DrawHighlightEvent event) {
        if (event.getTarget().getType() != RayTraceResult.Type.BLOCK || !(event.getInfo().getEntity() instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = ((PlayerEntity) event.getInfo().getEntity());

        player.getMainHandItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast).ifPresent(iModeChangingItem -> {
                    iModeChangingItem.getActiveModule().getCapability(HighLightCapability.HIGHLIGHT).ifPresent(iHighlight -> {
                        BlockRayTraceResult result = (BlockRayTraceResult) event.getTarget();
                        NonNullList<BlockPos> blocks = iHighlight.getBlockPositions(result);

                        if(blocks.isEmpty()) {
                            return;
                        }

                        MatrixStack matrixStack = event.getMatrix();
                        IRenderTypeBuffer buffer = event.getBuffers();
                        IVertexBuilder lineBuilder = buffer.getBuffer(RenderType.LINES);

                        double partialTicks = event.getPartialTicks();
                        double x = player.xOld + (player.getX() - player.xOld) * partialTicks;
                        double y = player.yOld + player.getEyeHeight() + (player.getY() - player.yOld) * partialTicks;
                        double z = player.zOld + (player.getZ() - player.zOld) * partialTicks;

                        matrixStack.pushPose();
                        blocks.forEach(blockPos -> {
                            AxisAlignedBB aabb = new AxisAlignedBB(blockPos).move(-x, -y, -z);

                            WorldRenderer.renderLineBox(matrixStack, lineBuilder, aabb, blockPos.equals(result.getBlockPos()) ? 1 : 0 , 0, 0, 0.4F);
                        });
                        matrixStack.popPose();
                        event.setCanceled(true);
                    });
                });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void preTextureStitch(TextureStitchEvent.Pre event) {
        MPSModelHelper.loadArmorModels(event, null);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event) {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onPostRenderGameOverlayEvent(RenderGameOverlayEvent.Post e) {
        RenderGameOverlayEvent.ElementType elementType = e.getType();
        if (RenderGameOverlayEvent.ElementType.HOTBAR.equals(elementType)) {
            this.drawKeybindToggles(e.getMatrixStack());
        }
    }

    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        if (!event.getPlayer().abilities.flying && !event.getPlayer().isOnGround() && this.playerHasFlightOn(event.getPlayer())) {
            event.getPlayer().abilities.flying = true;
            RenderEventHandler.ownFly = true;
        }
    }

    private boolean playerHasFlightOn(PlayerEntity player) {
        return
                player.getItemBySlot(EquipmentSlotType.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(iModularItem ->
                                iModularItem.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE_REGNAME)).orElse(false) ||

                        player.getItemBySlot(EquipmentSlotType.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETPACK_MODULE_REGNAME) ||
                                                iModularItem.isModuleOnline(MPSRegistryNames.GLIDER_MODULE_REGNAME)).orElse(false) ||

                        player.getItemBySlot(EquipmentSlotType.FEET).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETBOOTS_MODULE_REGNAME)).orElse(false);
    }

    @SubscribeEvent
    public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
        if (RenderEventHandler.ownFly) {
            RenderEventHandler.ownFly = false;
            event.getPlayer().abilities.flying = false;
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent e) {
        e.getEntity().getItemBySlot(EquipmentSlotType.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(h-> {
                            if (h instanceof IModularItem) {
                                ItemStack binnoculars = h.getOnlineModuleOrEmpty(MPSRegistryNames.BINOCULARS_MODULE_REGNAME);
                                if (!binnoculars.isEmpty())
                                    e.setNewfov((float) (e.getNewfov() / binnoculars.getCapability(PowerModuleCapability.POWER_MODULE)
                                            .map(m->m.applyPropertyModifiers(MPSConstants.FOV)).orElse(1D)));
                            }
                        }
                );
    }

    final List<KBDisplay> kbDisplayList = new ArrayList<>();
    public void makeKBDisplayList() {
        kbDisplayList.clear();
        KeybindManager.INSTANCE.getMPSKeyBinds().stream().filter(kb->!kb.isUnbound()).filter(kb->kb.showOnHud).forEach(kb->{
            Optional<KBDisplay> kbDisplay = kbDisplayList.stream().filter(kbd->kbd.finalId.equals(kb.getKey())).findFirst();
            if (kbDisplay.isPresent()) {
                kbDisplay.map(kbd->kbd.boundKeybinds.add(kb));
            } else {
                kbDisplayList.add(new KBDisplay(kb, MPSSettings.getHudKeybindX(), MPSSettings.getHudKeybindY(), MPSSettings.getHudKeybindX() + (float) 16));
            }
        });
    }

    boolean isModularItemEquuiiped() {
        PlayerEntity player = Minecraft.getInstance().player;
        return Arrays.stream(EquipmentSlotType.values()).filter(type ->player.getItemBySlot(type).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).filter(IModularItem.class::isInstance).isPresent()).findFirst().isPresent();
    }

    @OnlyIn(Dist.CLIENT)
    public void drawKeybindToggles(MatrixStack matrixStack) {
        if (MPSSettings.displayHud() && isModularItemEquuiiped()) {
            Minecraft minecraft = Minecraft.getInstance();
            AtomicDouble top = new AtomicDouble(MPSSettings.getHudKeybindY());
            kbDisplayList.forEach(kbDisplay -> {
//                System.out.println("\nkbDisplay.boundKeybinds: " + kbDisplay.boundKeybinds);

                if (!kbDisplay.boundKeybinds.isEmpty()) {
                    kbDisplay.setLeft(MPSSettings.getHudKeybindX());
                    kbDisplay.setTop(top.get());
                    kbDisplay.setBottom(top.get() + 16);
                    kbDisplay.render(matrixStack, 0, 0, minecraft.getFrameTime());
                    top.getAndAdd(16);
                }
            });
        }
    }

    class KBDisplay extends DrawableRelativeRect {
        List<MPSKeyBinding> boundKeybinds = new ArrayList<>();
        final InputMappings.Input finalId;
        public KBDisplay(MPSKeyBinding kb, double left, double top, double right) {
            super(left, top, right, top + 16, true, Colour.DARK_GREEN.withAlpha(0.2F), Colour.GREEN.withAlpha(0.2F));
            this.finalId = kb.getKey();
            boundKeybinds.add(kb);
        }

        public ITextComponent getLabel() {
            return finalId.getDisplayName();
        }

        public void addKeyBind(MPSKeyBinding kb) {
            if (!boundKeybinds.contains(kb)){
                boundKeybinds.add(kb);
            }
        }

        ClientPlayerEntity getPlayer() {
            return Minecraft.getInstance().player;
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
            float stringwidth = (float) MuseRenderer.getFontRenderer().width(getLabel());
            setWidth(stringwidth + 8 + boundKeybinds.stream().filter(kb->kb.showOnHud).collect(Collectors.toList()).size() * 18);
            super.render(matrixStack, 0, 0, frameTime);
            matrixStack.pushPose();
            matrixStack.translate(0,0,100);
            boolean kbToggleVal = boundKeybinds.stream().filter(kb->kb.toggleval).findFirst().isPresent();

            MuseRenderer.drawLeftAlignedText(matrixStack, getLabel(), (float) left() + 4, (float) top() + 9, (kbToggleVal) ? Colour.RED : Colour.GREEN);
            matrixStack.popPose();
            AtomicDouble x = new AtomicDouble(left() + stringwidth + 8);

            boundKeybinds.stream().filter(kb ->kb.showOnHud).forEach(kb ->{
                boolean active = false;
                // just using the icon
                ItemStack module = new ItemStack(ForgeRegistries.ITEMS.getValue(kb.registryName));
                for (EquipmentSlotType slot : EquipmentSlotType.values()) {
                    ItemStack stack = getPlayer().getItemBySlot(slot);
                    active = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .map(iItemHandler -> {
                                if (iItemHandler instanceof IModeChangingItem) {
                                    return ((IModeChangingItem) iItemHandler).isModuleActiveAndOnline(kb.registryName);
                                }
                                return iItemHandler.isModuleOnline(kb.registryName);
                            }).orElse(false);
//                    System.out.println(kb.getKey().getName() +", " + kb.registryName + ", active: " + active);

                    // stop at the first active instance
                    if(active) {
                        break;
                    }
                }
                MuseRenderer.drawModuleAt(matrixStack, x.get(), top(), module, active);
                x.getAndAdd(16);
            });
        }
    }
}