package com.github.lehjr.numina.client.event;


import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.client.render.entity.MPAArmorStandRenderer;
import com.github.lehjr.numina.constants.NuminaConstants;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(NuminaObjects.ARMOR_WORKSTATION__ENTITY_TYPE.get(), MPAArmorStandRenderer::new);
    }

    @SubscribeEvent
    public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
        AtlasTexture map = event.getMap();
        // only adds if it doesn't already exist
        if (map.getTextureLocation() == AtlasTexture.LOCATION_BLOCKS_TEXTURE) {
            event.addSprite(NuminaConstants.TEXTURE_WHITE_SHORT);
        }
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {

    }
}