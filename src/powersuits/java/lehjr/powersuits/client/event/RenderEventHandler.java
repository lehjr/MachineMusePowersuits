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
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.client.gui.geometry.DrawableRect;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.client.control.KeybindManager;
import lehjr.powersuits.client.control.MPSKeyMapping;
import lehjr.powersuits.client.model.helper.MPSModelHelper;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RenderEventHandler {
    INSTANCE;
    private static boolean ownFly = false;
    private final DrawableRect frame = new DrawableRect(MPSSettings.getHudKeybindX(), MPSSettings.getHudKeybindY(), MPSSettings.getHudKeybindX() + (float) 16, MPSSettings.getHudKeybindY() +  16, true, Color.DARK_GREEN.withAlpha(0.2F), Color.GREEN.withAlpha(0.2F));


    /**
     * Just for a couple modules that can break multiple blocks at once
     * @param event
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderBlockHighlight(RenderHighlightEvent event) {
        Color.WHITE.setShaderColor();

        if (event.getTarget().getType() != HitResult.Type.BLOCK || !(event.getCamera().getEntity() instanceof Player)) {
            return;
        }

        Player player = ((Player) event.getCamera().getEntity());

        player.getMainHandItem().getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast).ifPresent(iModeChangingItem -> {
                    iModeChangingItem.getActiveModule().getCapability(NuminaCapabilities.HIGHLIGHT).ifPresent(iHighlight -> {
                        BlockHitResult result = (BlockHitResult) event.getTarget();
                        NonNullList<BlockPos> blocks = iHighlight.getBlockPositions(result);

                        if(blocks.isEmpty()) {
                            return;
                        }

                        PoseStack matrixStack = event.getPoseStack();
                        MultiBufferSource buffer = event.getMultiBufferSource();
                        VertexConsumer lineBuilder = buffer.getBuffer(RenderType.LINES);

                        double partialTicks = event.getPartialTick();
                        double x = player.xOld + (player.getX() - player.xOld) * partialTicks;
                        double y = player.yOld + player.getEyeHeight() + (player.getY() - player.yOld) * partialTicks;
                        double z = player.zOld + (player.getZ() - player.zOld) * partialTicks;

                        matrixStack.pushPose();
                        blocks.forEach(blockPos -> {
                            AABB aabb = new AABB(blockPos).move(-x, -y, -z);

                            LevelRenderer.renderLineBox(matrixStack, lineBuilder, aabb, blockPos.equals(result.getBlockPos()) ? 1 : 0 , 0, 0, 0.4F);
                        });
                        matrixStack.popPose();
                        event.setCanceled(true);
                    });
                });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void preTextureStitch(TextureStitchEvent event) {
        MPSModelHelper.loadArmorModels(event, null);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event) {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onPostRenderGameOverlayEvent(RenderGuiOverlayEvent.Post e) {
//        if (Re.getType() == RenderGameOverlayEvent.ElementType.LAYER) { // opaque rendering, completely ignores alpha setting
//        if (e.getType() == RenderGameOverlayEvent.ElementType.TEXT) { // this one allows translucent rendering
            this.drawKeybindToggles(e.getPoseStack());
            ClientOverlayHandler.INSTANCE.render(e);
//        }
    }

    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        if (!event.getEntity().getAbilities().flying && !event.getEntity().isOnGround() && this.playerHasFlightOn(event.getEntity())) {
            event.getEntity().getAbilities().flying = true;
            RenderEventHandler.ownFly = true;
        }

        if(event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iItemHandler -> iItemHandler.isModuleOnline(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE)).orElse(false)) {
            event.setCanceled(true);
        }
    }

    private boolean playerHasFlightOn(Player player) {
        return
                player.getItemBySlot(EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(iModularItem ->
                                iModularItem.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE)).orElse(false) ||

                        player.getItemBySlot(EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETPACK_MODULE) ||
                                                iModularItem.isModuleOnline(MPSRegistryNames.GLIDER_MODULE)).orElse(false) ||

                        player.getItemBySlot(EquipmentSlot.FEET).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETBOOTS_MODULE)).orElse(false);
    }

    @SubscribeEvent
    public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
        if (RenderEventHandler.ownFly) {
            RenderEventHandler.ownFly = false;
            event.getEntity().getAbilities().flying = false;
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(ComputeFovModifierEvent e) {
        e.getPlayer().getItemBySlot(EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(h-> {
                            if (h instanceof IModularItem) {
                                ItemStack binnoculars = h.getOnlineModuleOrEmpty(MPSRegistryNames.BINOCULARS_MODULE);
                                if (!binnoculars.isEmpty())
                                    e.setNewFovModifier((float) (e.getFovModifier() / binnoculars.getCapability(NuminaCapabilities.POWER_MODULE)
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
        Player player = Minecraft.getInstance().player;
        return Arrays.stream(EquipmentSlot.values()).filter(type ->player.getItemBySlot(type).getCapability(ForgeCapabilities.ITEM_HANDLER).filter(IModularItem.class::isInstance).isPresent()).findFirst().isPresent();
    }

    @OnlyIn(Dist.CLIENT)
    public void drawKeybindToggles(PoseStack matrixStack) {
        if (MPSSettings.displayHud() && isModularItemEquuiiped()) {
            Minecraft minecraft = Minecraft.getInstance();
            AtomicDouble top = new AtomicDouble(MPSSettings.getHudKeybindY());
            kbDisplayList.forEach(kbDisplay -> {
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

    class KBDisplay extends DrawableRect {
        List<MPSKeyMapping> boundKeybinds = new ArrayList<>();
        final InputConstants.Key finalId;
        public KBDisplay(MPSKeyMapping kb, double left, double top, double right) {
            super(left, top, right, top + 16, true, Color.DARK_GREEN.withAlpha(0.2F), Color.GREEN.withAlpha(0.2F));
            this.finalId = kb.getKey();
            boundKeybinds.add(kb);
        }

        public Component getLabel() {
            return finalId.getDisplayName();
        }

        public void addKeyBind(MPSKeyMapping kb) {
            if (!boundKeybinds.contains(kb)){
                boundKeybinds.add(kb);
            }
        }

        LocalPlayer getPlayer() {
            return Minecraft.getInstance().player;
        }

        @Override
        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
            float stringwidth = (float) StringUtils.getFontRenderer().width(getLabel());
            setWidth(stringwidth + 8 + boundKeybinds.stream().filter(kb->kb.showOnHud).collect(Collectors.toList()).size() * 18);
            super.render(matrixStack, 0, 0, partialTick);
            matrixStack.pushPose();
            matrixStack.translate(0,0,100);
            boolean kbToggleVal = boundKeybinds.stream().filter(kb->kb.toggleval).findFirst().isPresent();

            StringUtils.drawLeftAlignedText(matrixStack, getLabel(), (float) left() + 4, (float) top() + 9, (kbToggleVal) ? Color.RED : Color.GREEN);
            matrixStack.popPose();
            AtomicDouble x = new AtomicDouble(left() + stringwidth + 8);

            boundKeybinds.stream().filter(kb ->kb.showOnHud).forEach(kb ->{
                boolean active = false;
                // just using the icon
                ItemStack module = new ItemStack(ForgeRegistries.ITEMS.getValue(kb.registryName));
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    ItemStack stack = getPlayer().getItemBySlot(slot);
                    active = stack.getCapability(ForgeCapabilities.ITEM_HANDLER)
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
                NuminaRenderer.drawModuleAt(matrixStack, x.get(), top(), module, active);
                x.getAndAdd(16);
            });
        }
    }
}