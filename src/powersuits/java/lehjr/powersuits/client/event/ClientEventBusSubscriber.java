package lehjr.powersuits.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.render.highlight.IHighlight;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.control.KeyMappingReaderWriter;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.gui.module.install_salvage.InstallSalvageGui;
import lehjr.powersuits.client.overlay.MPSOverlay;
import lehjr.powersuits.client.render.block.TinkerTableBER;
import lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.registration.MPSBlocks;
import lehjr.powersuits.common.registration.MPSEntities;
import lehjr.powersuits.common.registration.MPSMenuTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Arrays;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(ClientEventBusSubscriber::onFOVUpdate);
        NeoForge.EVENT_BUS.addListener(ClientEventBusSubscriber::renderBlockHighlight);
        NeoForge.EVENT_BUS.register(new KeymappingKeyHandler());
    }


    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
//        NuminaLogger.logDebug("keymappingevent loaded");
        KeyMappingReaderWriter.INSTANCE.readInKeybinds(); // read existing keybindings


        KeymappingKeyHandler.loadKeyBindings(); // check for possible additional
        KeymappingKeyHandler.keyMappings.values().forEach(event::register); // register keybinds
        Arrays.stream(KeymappingKeyHandler.keybindArray).forEach(event::register);








        MPSOverlay.makeKBDisplayList();
    }


    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
//        event.registerEntityRenderer(MPSEntities.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);
        event.registerEntityRenderer(MPSEntities.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);
        event.registerBlockEntityRenderer(MPSBlocks.TINKER_TABLE_BLOCKENTITY_TYPE.get(), TinkerTableBER::new);
    }

    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event) {
        event.register(MPSMenuTypes.INSTALL_SALVAGE_MENU_TYPE.get(), InstallSalvageGui::new);
    }


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(new ResourceLocation(MPSConstants.MOD_ID, "keybinds"), MPSOverlay.MPS_KEYBIND_OVERLAY);
//        event.registerAboveAll("mps_hud", MPSOverlay.MPS_HUD);
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ModelBakeEventHandler.INSTANCE.onModifyBakingResult(event);
    }

    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
    public static void onFOVUpdate(ComputeFovModifierEvent e) {
        IModularItem cap = ItemUtils.getItemFromEntitySlot(e.getPlayer(), EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
        if(cap != null) {
            ItemStack binoculars = cap.getOnlineModuleOrEmpty(MPSConstants.BINOCULARS_MODULE);
            if (!binoculars.isEmpty()) {
                IPowerModule pm = binoculars.getCapability(NuminaCapabilities.Module.POWER_MODULE);
                if (pm != null) {
                    e.setNewFovModifier((float) (e.getFovModifier() / pm.applyPropertyModifiers(MPSConstants.FOV)));
                }
            }
        }
    }

    /**
     * Just for a couple modules that can break multiple blocks at once
     * @param event
     */
    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
    public static void renderBlockHighlight(RenderHighlightEvent.Block event) {
        Color.WHITE.setShaderColor();

        if (event.getTarget().getType() != HitResult.Type.BLOCK || !(event.getCamera().getEntity() instanceof Player player)) {
            return;
        }

        IModeChangingItem mci = player.getMainHandItem().getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
        if (mci != null) {
            ItemStack module = mci.getActiveModule();
            IHighlight highlight = module.getCapability(NuminaCapabilities.HIGHLIGHT);
            if(highlight != null) {
                BlockHitResult result = (BlockHitResult) event.getTarget();
                NonNullList<BlockPos> blocks = highlight.getBlockPositions(result);
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
//                event.setCanceled(true);
            }
        }
    }
}