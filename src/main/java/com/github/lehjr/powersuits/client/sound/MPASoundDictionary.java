package com.github.lehjr.powersuits.client.sound;

import com.github.lehjr.powersuits.constants.MPSConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, value = Dist.CLIENT)
public class MPASoundDictionary {
    public static SoundEvent GLIDER = initSound("glider");
    public static SoundEvent JETBOOTS = initSound("jet_boots");
    public static SoundEvent JETPACK = initSound("jetpack");
    public static SoundEvent JUMP_ASSIST = initSound("jump_assist");
    public static SoundEvent SWIM_ASSIST = initSound("swim_assist");
    public static SoundEvent ELECTROLYZER = initSound("water_electrolyzer");

    static {
        new MPASoundDictionary();
    }

    @SubscribeEvent
    public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                GLIDER,
                JETBOOTS,
                JETPACK,
                JUMP_ASSIST,
                SWIM_ASSIST,
                ELECTROLYZER);
    }

    private static SoundEvent initSound(String soundName) {
        ResourceLocation location = new ResourceLocation(MPSConstants.MOD_ID, soundName);
        SoundEvent event = new SoundEvent(location).setRegistryName(location);
        return event;
    }
}
