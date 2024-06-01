package lehjr.numina.client.event;


import forge.NuminaObjLoader;
import lehjr.numina.client.gui.NuminaIcons;
import lehjr.numina.client.gui.overlay.ModeChangingIconOverlay;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.client.render.entity.NuminaArmorStandRenderer;
import lehjr.numina.client.render.item.NuminaArmorLayer;
import lehjr.numina.client.screen.ArmorStandScreen;
import lehjr.numina.client.screen.ChargingBaseScreen;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(modid=NuminaConstants.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
public class ClientEventBusSubscriber {












//    @SubscribeEvent
//    public static void doMoreClientStuff(IEventBus modEventBus) {
//        modEventBus.register(ClientEventBusSubscriber .class);
//        modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onAddAdditional);
//    }


}

