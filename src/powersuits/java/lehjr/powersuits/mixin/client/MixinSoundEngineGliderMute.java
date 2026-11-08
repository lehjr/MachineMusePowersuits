package lehjr.powersuits.mixin.client;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.ElytraOnPlayerSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stops the elytra flight sound so we can launch our own
 */
@Mixin(SoundEngine.class)
public class MixinSoundEngineGliderMute {
    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void interceptAndMuteGlider(SoundInstance soundInstance, CallbackInfo ci) {
        // Evaluate the runtime class type safely inside the method execution
        if (soundInstance instanceof ElytraOnPlayerSoundInstance) {
            var player = Minecraft.getInstance().player;
            if (player != null) {
                IModularItem chestCap = player.getItemBySlot(EquipmentSlot.CHEST).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
                if (chestCap != null && chestCap.isModuleOnline(MPSConstants.GLIDER_MODULE)) {

                    // CANCEL THE SOUND: Stop the sound loop from registering in the mixer channel
                    ci.cancel();
                }
            }
        }
    }
}
