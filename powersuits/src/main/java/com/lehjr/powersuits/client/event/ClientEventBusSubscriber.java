package com.lehjr.powersuits.client.event;

import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.render.highlight.IHighlight;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.client.control.KeyMappingReaderWriter;
import com.lehjr.powersuits.client.control.KeymappingKeyHandler;
import com.lehjr.powersuits.client.gui.module.install_salvage.InstallSalvageGui;
import com.lehjr.powersuits.client.overlay.MPSOverlay;
import com.lehjr.powersuits.client.render.block.TinkerTableBER;
import com.lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import com.lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import com.lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import com.lehjr.powersuits.client.render.item.MPSBEWLR;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.registration.MPSBlocks;
import com.lehjr.powersuits.common.registration.MPSEntities;
import com.lehjr.powersuits.common.registration.MPSItems;
import com.lehjr.powersuits.common.registration.MPSMenuTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
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


        KeymappingKeyHandler.loadKeyModuleBindings(); // check for possible additional
        KeymappingKeyHandler.keyMappings.values().forEach(event::register); // register keybinds
        Arrays.stream(KeymappingKeyHandler.keybindArray).forEach(event::register);


        MPSOverlay.makeKBDisplayList();
    }


    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        //        event.registerEntityRenderer(MPSEntities.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);
        event.registerEntityRenderer(MPSEntities.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
        //        event.registerEntityRenderer(MPSEntities.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBallEntityRenderer2::new);
        event.registerEntityRenderer(MPSEntities.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);
        event.registerBlockEntityRenderer(MPSBlocks.TINKER_TABLE_BLOCKENTITY_TYPE.get(), TinkerTableBER::new);
    }

    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event) {
        event.register(MPSMenuTypes.INSTALL_SALVAGE_MENU_TYPE.get(), InstallSalvageGui::new);
    }


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "keybinds"), MPSOverlay.MPS_KEYBIND_OVERLAY);
        //        event.registerAboveAll("mps_hud", MPSOverlay.MPS_HUD);
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ModelBakeEventHandler.INSTANCE.onModifyBakingResult(event);
    }

    @SubscribeEvent
    public static void onAddAdditional(ModelEvent.RegisterAdditional e) {
        ModelBakeEventHandler.onAddAdditional(e);
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
        Level level = Minecraft.getInstance().level;

        if (event.getTarget().getType() != HitResult.Type.BLOCK || !(event.getCamera().getEntity() instanceof Player player) || level == null) {
            return;
        }

        BlockHitResult result = event.getTarget();
        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        ItemStack heldItem = player.getMainHandItem();
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(heldItem);
        if (mci != null) {
            ItemStack module = mci.getActiveModule();
            IPowerModule pm = mci.getModuleCapability(module);

            if(pm instanceof IHighlight highlight && pm.isModuleOnline()) {
                playerEnergy = playerEnergy - pm.getEnergyUsage();
                NonNullList<IBlockBreakingModule> modules = NonNullList.create();
                for (int i=0; i< mci.getSlots(); i++) {
                    IPowerModule pm1 = mci.getModuleCapability(mci.getStackInSlot(i));
                    if(pm1 instanceof IBlockBreakingModule bbm && pm1 != pm) {
                        modules.add(bbm);
                    }
                }

                NonNullList<IHighlight.BlockPostions> blockPositions = highlight
                    .getBlockPositions(
                        heldItem,
                        result,
                        player,
                        level,
                        modules,
                        playerEnergy
                    );

                if(blockPositions.isEmpty()) {
                    return;
                }

                PoseStack matrixStack = event.getPoseStack();
                MultiBufferSource buffer = event.getMultiBufferSource();
                VertexConsumer lineBuilder = buffer.getBuffer(RenderType.LINES);

                double partialTicks = event.getDeltaTracker().getGameTimeDeltaPartialTick(true); //  FIXME: IS this correct

                double x = player.xOld + (player.getX() - player.xOld) * partialTicks;
                double y = player.yOld + player.getEyeHeight() + (player.getY() - player.yOld) * partialTicks;
                double z = player.zOld + (player.getZ() - player.zOld) * partialTicks;
                matrixStack.pushPose();

                blockPositions.forEach(blockPostion -> {
                    BlockPos blockPos = blockPostion.pos();
                    AABB aabb = new AABB(blockPos).move(-x, -y, -z);

                    float r=0F;
                    float g=0F;
                    float b=0;
                    float a=0.2F;

                    // Render the targeted block at full brightness to distinguish it from others
                    if (blockPos.compareTo(result.getBlockPos()) == 0) {
                        a=1;
                    }

                    if (blockPostion.canHarvest()) {
                        g = 1;
                    } else {
                        r = 1;
                    }

                    LevelRenderer.renderLineBox(matrixStack, lineBuilder, aabb, r, g, b, a);
                });
                matrixStack.popPose();
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void clientExtensionEvent(RegisterClientExtensionsEvent event) {
        final BlockEntityWithoutLevelRenderer renderer = ModelBakeEventHandler.INSTANCE.MPSBERINSTANCE;

        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                ((MPSBEWLR)renderer).setFiringData(new MPSBEWLR.FiringData(player, arm, itemInHand));
                return IClientItemExtensions.super.applyForgeHandTransform(poseStack, player, arm, itemInHand, partialTick, equipProcess, swingProcess);
            }
        }, MPSItems.POWER_FIST_1.get(), MPSItems.POWER_FIST_2.get(), MPSItems.POWER_FIST_3.get(), MPSItems.POWER_FIST_4.get());

        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        }, MPSItems.TINKER_TABLE_ITEM.get());
    }
}