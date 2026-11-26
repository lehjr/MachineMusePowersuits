package lehjr.powersuits.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.PlayerUpdatePacketServerBound;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.control.KeyMappingReaderWriter;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.gui.module.install_salvage.InstallSalvageGui;
import lehjr.powersuits.client.overlay.MPSOverlay;
import lehjr.powersuits.client.render.blockentity.TinkerTableBER;
import lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.registration.MPSBlocks;
import lehjr.powersuits.common.registration.MPSEntities;
import lehjr.powersuits.common.registration.MPSMenuTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Arrays;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    /**
     * Setup client related stuff
     */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(ClientEventBusSubscriber::onFOVUpdate);
        MinecraftForge.EVENT_BUS.addListener(ClientEventBusSubscriber::renderBlockHighlight);
        MinecraftForge.EVENT_BUS.register(new KeymappingKeyHandler());
        MinecraftForge.EVENT_BUS.addListener(ClientEventBusSubscriber::onClientTick);
        MinecraftForge.EVENT_BUS.addListener(ClientEventBusSubscriber::onPlayerLogout);
        MinecraftForge.EVENT_BUS.addListener(ClientEventBusSubscriber::onScreenInitPost);

        event.enqueueWork(ClientEventBusSubscriber::registerScreen);
    }

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
//        NuminaLogger.logDebug("keymappingevent loaded");
        KeyMappingReaderWriter.INSTANCE.readInKeybinds(); // read existing keybindings
        KeymappingKeyHandler.loadKeyBindings(); // check for possible additional
        KeymappingKeyHandler.keyMappings.values().forEach(kb-> event.register(kb)); // register keybinds
        Arrays.stream(KeymappingKeyHandler.keybindArray).forEach(kb->event.register(kb));

        MPSOverlay.makeKBDisplayList();
    }

//    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        // Check if the player is in-game but a screen is open OR window is unfocused
        if (mc.player != null && (mc.screen != null || !mc.isWindowActive())) {
            IPlayerKeyStates keyStates = mc.player.getCapability(NuminaCapabilities.PLAYER_KEYSTATES).resolve().orElse(null);

            // If your local cache thinks keys are pressed, force a release
            if (keyStates != null && keyStates.getStates() > 0) {
                keyStates.setStates((byte)0);

                // Pack your cleared byte (0) and send to server
                byte clearedByte = 0;
                NuminaPackets.sendToServer(new PlayerUpdatePacketServerBound(clearedByte));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MPSEntities.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);
        event.registerBlockEntityRenderer(MPSBlocks.TINKER_TABLE_BLOCKENTITY_TYPE.get(), TinkerTableBER::new);
    }

    private static boolean ownFly = false;

    @SubscribeEvent
    public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
        if (ownFly) {
            ownFly = false;
            event.getEntity().getAbilities().flying = false;
        }
    }

    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        //FIME!!! WTF?
        if (!event.getEntity().getAbilities().flying && !event.getEntity().onGround() && this.playerHasFlightOn(event.getEntity())) {
            event.getEntity().getAbilities().flying = true;
            ownFly = true;
        }

        if(ItemUtils.getItemFromEntitySlot(event.getEntity(), EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iItemHandler -> iItemHandler.isModuleOnline(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE)).orElse(false)) {
            event.setCanceled(true);
        }
    }

    private boolean playerHasFlightOn(Player player) {
        IModularItem headCap = NuminaCapabilities.getModularItem(ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD));
        IPowerModule flightControl = headCap == null ? null : headCap.getModuleCapabilityOnlineOrNull(MPSRegistryNames.FLIGHT_CONTROL_MODULE);
        if (flightControl != null) {
            IModularItem chestCap = NuminaCapabilities.getModularItem(ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.CHEST));
            IPowerModule jetpack = chestCap == null ? null : chestCap.getModuleCapabilityOnlineOrNull(MPSRegistryNames.JETPACK_MODULE);
            IPowerModule glider = chestCap == null ? null : chestCap.getModuleCapabilityOnlineOrNull(MPSRegistryNames.GLIDER_MODULE);

            IModularItem feetCap = NuminaCapabilities.getModularItem(ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.FEET));
            IPowerModule jetboots = feetCap == null ? null : feetCap.getModuleCapabilityOnlineOrNull(MPSRegistryNames.JETBOOTS_MODULE);
            return (jetpack != null || glider != null || jetboots != null);
        }
        return false;
    }


//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public void onPostRenderGameOverlayEvent(RenderGuiOverlayEvent.Pre /*CustomizeGuiOverlayEvent*/ e) {
////        if (Re.getType() == RenderGameOverlayEvent.ElementType.LAYER) { // opaque rendering, completely ignores alpha setting
//        if (e.getOverlay().id().equals(VanillaGuiOverlay.TITLE_TEXT.id())) { // this one allows translucent rendering
//            this.drawKeybindToggles(e.getGuiGraphics());
//            ClientOverlayHandler.INSTANCE.render(e);
//        }
//    }

//    @OnlyIn(Dist.CLIENT)
//    public void drawKeybindToggles(GuiGraphics gfx) {
////        if (MPSSettings.displayHud() && isModularItemEquuiiped()) {
////            Minecraft minecraft = Minecraft.getInstance();
////            AtomicDouble top = new AtomicDouble(MPSSettings.getHudKeybindY());
////            kbDisplayList.forEach(kbDisplay -> {
////                if (!kbDisplay.boundKeybinds.isEmpty()) {
////                    kbDisplay.setLeft(MPSSettings.getHudKeybindX());
////                    kbDisplay.setTop(top.get());
////                    kbDisplay.setBottom(top.get() + 16);
////                    kbDisplay.render(matrixStack, 0, 0, minecraft.getFrameTime());
////                    top.getAndAdd(16);
////                }
////            });
////        }
//    }





    public static void registerScreen() {
        MenuScreens.register(MPSMenuTypes.INSTALL_SALVAGE_MENU_TYPE.get(), InstallSalvageGui::new);
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("mps_keybinds", MPSOverlay.MPS_KEYBIND_OVERLAY);
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

    public static void onFOVUpdate(ComputeFovModifierEvent e) {
        IModularItem cap = ItemUtils.getItemFromEntitySlot(e.getPlayer(), EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null);
        if(cap != null) {
            ItemStack binoculars = cap.getOnlineModuleOrEmpty(MPSConstants.BINOCULARS_MODULE);
            if (!binoculars.isEmpty()) {
                IPowerModule pm = cap.getModuleCapability(binoculars);
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

            if (pm instanceof IHighlight highlight && pm.isModuleOnline()) {
                playerEnergy = playerEnergy - pm.getEnergyUsage();
                NonNullList<IBlockBreakingModule> modules = NonNullList.create();
                for (int i = 0; i < mci.getSlots(); i++) {
                    IPowerModule pm1 = mci.getModuleCapability(mci.getStackInSlot(i));
                    if (pm1 instanceof IBlockBreakingModule bbm && pm1 != pm) {
                        modules.add(bbm);
                    }
                }

                HashMap<IHighlight.BlockPostionData, Integer> blockPositions = highlight
                        .getBlockPositions(
                                heldItem,
                                result,
                                player,
                                level,
                                modules,
                                playerEnergy
                        );

                if (blockPositions.isEmpty()) {
                    return;
                }

                PoseStack matrixStack = event.getPoseStack();
                MultiBufferSource buffer = event.getMultiBufferSource();
                VertexConsumer lineBuilder = buffer.getBuffer(RenderType.LINES);

                // FIX 1: Extract the true camera position vector.
                // This accurately shifts the matrix origin regardless of first or third-person views.
                Camera camera = event.getCamera();
                Vec3 cameraPos = camera.getPosition();
                double camX = cameraPos.x();
                double camY = cameraPos.y();
                double camZ = cameraPos.z();

                matrixStack.pushPose();

                blockPositions.forEach((blockPostionData, miningLevel) -> {
                    BlockPos blockPos = blockPostionData.pos();

                    // FIX 2: Move the bounding box relative to the true camera position
                    AABB aabb = new AABB(blockPos).move(-camX, -camY, -camZ);

                    float r = 0F;
                    float g = 0F;
                    float b = 0;
                    float a = 0.2F;

                    // Render the targeted block at full brightness to distinguish it from others
                    if (blockPos.compareTo(result.getBlockPos()) == 0) {
                        a = 1;
                    }

                    if (blockPostionData.canHarvest()) {
                        if (miningLevel == 1) {
                            g = 0.8F;
                        } else {
                            g = 1F;
                        }
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

//    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Musique.stopAllPlayerSounds();
    }

    /**
     * Restore the mouse cursor position when switching between Modular items in the Install/Salvage Gui
     * @param event
     */
//    @SubscribeEvent
    public static void onScreenInitPost(ScreenEvent.Init.Post event) {
        if(event.getScreen() instanceof InstallSalvageGui) {
            ((InstallSalvageGui) event.getScreen()).restoreMouse();
        }
    }

}


