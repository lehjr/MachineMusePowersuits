//package lehjr.powersuits.mixin.client;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.player.LocalPlayer;
//import net.minecraft.client.resources.sounds.ElytraOnPlayerSoundInstance;
//import net.minecraft.client.resources.sounds.SoundInstance;
//import net.minecraft.world.entity.player.Player;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(ElytraOnPlayerSoundInstance.class)
//public class MixinElytraSound {
//    @Shadow @Final
//    private Player player;
//
//    @Inject(method = "<init>", at = @At("RETURN"))
//    private void stopSoundStacking(LocalPlayer player, CallbackInfo ci) {
//        // Run this strictly on the client side
//        if (player.level().isClientSide() && player == Minecraft.getInstance().player) {
//
//            // Access the active sounds currently running in the engine
//            for (SoundInstance activeSound : Minecraft.getInstance().getSoundManager().soundEngine.instanceToChannel.keySet()) {
//
//                // If another Elytra loop is already playing (or fading out) for this player, kill this new one immediately
//                if (activeSound instanceof ElytraOnPlayerSoundInstance && activeSound != (Object)this) {
//                    Minecraft.getInstance().getSoundManager().stop(activeSound);
//
//                    // Alternatively, to keep the old one and discard the new one:
//                    // ((SoundInstanceAccessor)this).setStopped(true);
//                }
//            }
//        }
//    }
//}
