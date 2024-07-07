package lehjr.powersuits.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public enum RenderEventHandler {
    INSTANCE;
    private static boolean ownFly = false;
//    private final DrawableRect frame = new DrawableRect(MPSSettings.getHudKeybindX(), MPSSettings.getHudKeybindY(), MPSSettings.getHudKeybindX() + (float) 16, MPSSettings.getHudKeybindY() +  16, true, Color.DARK_GREEN.withAlpha(0.2F), Color.GREEN.withAlpha(0.2F));

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
//        MPSModelHelper.loadArmorModels(event, null);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event) {
    }

//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public void onPostRenderGameOverlayEvent(RenderGuiOverlayEvent.Pre /*CustomizeGuiOverlayEvent*/ e) {
////        if (Re.getType() == RenderGameOverlayEvent.ElementType.LAYER) { // opaque rendering, completely ignores alpha setting
//        if (e.getOverlay().id().equals(VanillaGuiOverlay.TITLE_TEXT.id())) { // this one allows translucent rendering
//            this.drawKeybindToggles(e.getGuiGraphics());
////            ClientOverlayHandler.INSTANCE.render(e);
//        }
//    }

    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        if (!event.getEntity().getAbilities().flying && !event.getEntity().onGround() && this.playerHasFlightOn(event.getEntity())) {
            event.getEntity().getAbilities().flying = true;
            RenderEventHandler.ownFly = true;
        }

        if(ItemUtils.getItemFromEntitySlot(event.getEntity(), EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iItemHandler -> iItemHandler.isModuleOnline(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE)).orElse(false)) {
            event.setCanceled(true);
        }
    }

    private boolean playerHasFlightOn(Player player) {
        return
                ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(iModularItem ->
                                iModularItem.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE)).orElse(false) ||

                        ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETPACK_MODULE) ||
                                                iModularItem.isModuleOnline(MPSRegistryNames.GLIDER_MODULE)).orElse(false) ||

                        ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.FEET).getCapability(ForgeCapabilities.ITEM_HANDLER)
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
        ItemUtils.getItemFromEntitySlot(e.getPlayer(), EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(h-> {
                            ItemStack binoculars = h.getOnlineModuleOrEmpty(MPSRegistryNames.BINOCULARS_MODULE);
                            if (!binoculars.isEmpty())
                                e.setNewFovModifier((float) (e.getFovModifier() / binoculars.getCapability(NuminaCapabilities.POWER_MODULE)
                                        .map(m->m.applyPropertyModifiers(MPSConstants.FOV)).orElse(1D)));
                        }
                );
    }
}