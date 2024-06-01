package lehjr.numina.client.event;

import lehjr.numina.client.config.ClientConfig;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class NuminaFOVUpdateEventHandler {
    public static final Lazy<KeyMapping> fovToggleKey = Lazy.of(()-> new KeyMapping("key..numina.fovfixtoggle", GLFW.GLFW_KEY_UNKNOWN, "key.categories." + NuminaConstants.MOD_ID));

    public static boolean fovIsActive = ClientConfig.fovFixDefaultState();

    @SubscribeEvent
    public static void onFOVUpdate(ComputeFovModifierEvent e) {
        if (ClientConfig.useFovFix()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (fovToggleKey.get().consumeClick()) {
                fovIsActive = !fovIsActive;
                if (player == null) {
                    return;
                }

                if (fovIsActive) {
                    player.sendSystemMessage(Component.translatable("message.numina.fovfixtoggle.disabled"));//, player.getUUID());
                } else {
                    player.sendSystemMessage(Component.translatable("message.numina.fovfixtoggle.disabled"));//, player.getUUID());
                }
            }

            if (fovIsActive) {
                AttributeInstance attributeinstance = e.getPlayer().getAttribute(Attributes.MOVEMENT_SPEED);
                assert attributeinstance != null;
                e.setNewFovModifier((float) (e.getNewFovModifier() / ((attributeinstance.getValue() / e.getPlayer().getAbilities().getWalkingSpeed() + 1.0) / 2.0)));if (ClientConfig.useFovNormalize()) {
                    if (e.getPlayer().isSprinting()) {
                        e.setNewFovModifier(e.getNewFovModifier() + 0.15F);
                    }
                }
            }
        }
    }
}
