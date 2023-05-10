/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.client.control;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.PlayerUpdatePacket;
import lehjr.powersuits.client.gui.modechanging.GuiModeSelector;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class KeymappingKeyHandler {
    Minecraft minecraft;
    // FIXME: Translations
    public static final String mps =  "itemGroup.powersuits";

    public static final KeyMapping goDownKey = new KeyMapping(Component.translatable("keybinding.powersuits.goDownKey").getString(), GLFW.GLFW_KEY_Z, mps);
    public static final KeyMapping cycleToolBackward = new KeyMapping(Component.translatable("keybinding.powersuits.cycleToolBackward").getString(), GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping cycleToolForward = new KeyMapping(Component.translatable("keybinding.powersuits.cycleToolForward").getString(), GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping openKeybindGUI = new KeyMapping(Component.translatable("keybinding.powersuits.openKeybindGui").getString()/*"Open MPS Keybind GUI"*/, GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping openCosmeticGUI = new KeyMapping(Component.translatable("keybinding.powersuits.openCosmeticGUI").getString() /*Cosmetic GUI (MPS)"*/, GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping openModuleTweakGUI = new KeyMapping(Component.translatable("keybinding.powersuits.openModuleTweakGUI").getString() /*Open MPS Keybind GUI"*/, GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping openInstallSalvageGUI = new KeyMapping(Component.translatable("keybinding.powersuits.openInstallSalvageGUI").getString() /*Cosmetic GUI (MPS)"*/, GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping[] keybindArray = new KeyMapping[]{goDownKey, cycleToolBackward, cycleToolForward, openKeybindGUI, openCosmeticGUI, openModuleTweakGUI, openInstallSalvageGUI};

    public static boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    public KeymappingKeyHandler() {
        minecraft = Minecraft.getInstance();
    }

    void updatePlayerValues(LocalPlayer clientPlayer) {
        if (clientPlayer == null) {
            return;
        }

        clientPlayer.getCapability(NuminaCapabilities.PLAYER_KEYSTATES).ifPresent(playerCap -> {
            boolean markForSync = false;

            // minecraft.player.input
            boolean forwardKeyState = minecraft.options.keyUp.isDown();
            boolean reverseKeyState = minecraft.options.keyDown.isDown();
            boolean strafeLeftKeyState = minecraft.options.keyLeft.isDown();
            boolean strafeRightKeyState = minecraft.options.keyRight.isDown();
            boolean downKeyState = goDownKey.isDown();
            boolean jumpKeyState = minecraft.options.keyJump.isDown();


            if (playerCap.getForwardKeyState() != forwardKeyState) {
                playerCap.setForwardKeyState(forwardKeyState);
                markForSync = true;
            }

            if (playerCap.getReverseKeyState() != reverseKeyState) {
                playerCap.setReverseKeyState(reverseKeyState);
                markForSync = true;
            }

            if (playerCap.getLeftStrafeKeyState() != strafeLeftKeyState) {
                playerCap.setLeftStrafeKeyState(strafeLeftKeyState);
                markForSync = true;
            }

            if (playerCap.getRightStrafeKeyState() != strafeRightKeyState) {
                playerCap.setRightStrafeKeyState(strafeRightKeyState);
                markForSync = true;
            }

            if (playerCap.getDownKeyState() != downKeyState) {
                playerCap.setDownKeyState(downKeyState);
                markForSync = true;
            }

            if (playerCap.getJumpKeyState() != jumpKeyState) {
                playerCap.setJumpKeyState(jumpKeyState);
                markForSync = true;
            }

            boolean[] boolArray = new boolean[]{
                    forwardKeyState,
                    reverseKeyState,
                    strafeLeftKeyState,
                    strafeRightKeyState,
                    downKeyState,
                    jumpKeyState,
                    false,
                    false
            };

            byte byteOut = MathUtils.boolArrayToByte(boolArray);

            if (markForSync) {
                NuminaPackets.CHANNEL_INSTANCE.sendToServer(new PlayerUpdatePacket(byteOut));
            }
        });
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key e) {
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        getMPSKeyMappings().stream().filter(kb->kb.isDown()).forEach(kb->{
            kb.toggleModules();
        });

        KeyMapping[] hotbarKeys = minecraft.options.keyHotbarSlots;
        updatePlayerValues(player);

        // Mode changinging GUI
        if (hotbarKeys[player.getInventory().selected].isDown() && minecraft.isWindowActive()) {
            player.getInventory().getSelected().getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(iModeChanging->{
                        if(player.level.isClientSide) {
                            if (!(Minecraft.getInstance().screen instanceof GuiModeSelector)) {
                                Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new GuiModeSelector(player, Component.literal("modeChanging"))));
                            }
                        }
                    });
        }

        /* cycleToolBackward/cycleToolForward */
        if (cycleToolBackward.isDown()) {
            minecraft.gameMode.tick();
            player.getInventory().getItem(player.getInventory().selected).getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> handler.cycleMode(player, 1));
        }

        if (cycleToolForward.isDown()) {
            minecraft.gameMode.tick();
            player.getInventory().getItem(player.getInventory().selected).getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> handler.cycleMode(player, -1));
        }
    }

    public static void loadKeyBindings() {
        NonNullList<ItemStack> modules = NonNullList.create();
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            new ItemStack(item).getCapability(NuminaCapabilities.POWER_MODULE)
                    .filter(IToggleableModule.class::isInstance)
                    .map(IToggleableModule.class::cast)
                    .ifPresent(pm -> {
                        // Tool settings are a bit odd
                        if (pm.getTarget() == ModuleTarget.TOOLONLY) {
                            if (pm.getCategory() == ModuleCategory.MINING_ENHANCEMENT) {
                                modules.add(pm.getModuleStack());
                                registerKeybinding(ItemUtils.getRegistryName(item), false);
                            } else if (!IRightClickModule.class.isAssignableFrom(pm.getClass())) {
                                modules.add(pm.getModuleStack());
                                registerKeybinding(ItemUtils.getRegistryName(item), false);
                            }
                        } else {
                            modules.add(pm.getModuleStack());
                            registerKeybinding(ItemUtils.getRegistryName(item), false);
                        }
                    });
        }
    }

    public static List<MPSKeyMapping> getMPSKeyMappings() {
        return Arrays.stream(Minecraft.getInstance().options.keyMappings).filter(MPSKeyMapping.class::isInstance).map(MPSKeyMapping.class::cast).collect(Collectors.toList());
    }

    public static Map<String, MPSKeyMapping> keyMappings = new HashMap<>();

    public static void registerKeybinding(ResourceLocation registryName, boolean showOnHud) {
        String keybindingName = new StringBuilder("keybinding.").append(registryName.getNamespace()).append(".").append(registryName.getPath()).toString();
        registerKeyBinding(registryName, keybindingName, GLFW.GLFW_KEY_UNKNOWN, mps, showOnHud, false);
    }

    public static void registerKeyBinding(ResourceLocation registryName, String  keybindingName, int keyIn, String category, boolean showOnHud, boolean overwrite) {
        if (overwrite || !keyMappings.containsKey(keybindingName) ) {
            keyMappings.put(keybindingName, new MPSKeyMapping(registryName, keybindingName, keyIn, category, showOnHud));
        }
    }
}