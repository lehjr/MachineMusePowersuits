package lehjr.powersuits.client.event;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.control.KeyMappingReaderWriter;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.overlay.MPSOverlay;
import lehjr.powersuits.client.render.blockentity.TinkerTableBER;
import lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.registration.MPSBlocks;
import lehjr.powersuits.common.registration.MPSEntities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {


    private static boolean ownFly = false;

    @SubscribeEvent
    public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
        if (ownFly) {
            ownFly = false;
            event.getEntity().getAbilities().flying = false;
        }
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
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
//        NuminaLogger.logDebug("keymappingevent loaded");
        KeyMappingReaderWriter.INSTANCE.readInKeybinds(); // read existing keybindings


        KeymappingKeyHandler.loadKeyBindings(); // check for possible additional
        KeymappingKeyHandler.keyMappings.values().forEach(kb-> event.register(kb)); // register keybinds
        Arrays.stream(KeymappingKeyHandler.keybindArray).forEach(kb->event.register(kb));








        MPSOverlay.makeKBDisplayList();
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("mps_keybinds", MPSOverlay.MPS_KEYBIND_OVERLAY);
//        event.registerAboveAll("mps_hud", MPSOverlay.MPS_HUD);
    }

    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        //FIME!!! WTF?
//        if (!event.getEntity().getAbilities().flying && !event.getEntity().onGround() && this.playerHasFlightOn(event.getEntity())) {
//            event.getEntity().getAbilities().flying = true;
//            ownFly = true;
//        }

        if(ItemUtils.getItemFromEntitySlot(event.getEntity(), EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iItemHandler -> iItemHandler.isModuleOnline(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE)).orElse(false)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
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








}


