package com.lehjr.powersuits.client.control;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.network.packets.serverbound.PlayerUpdatePacketServerBound;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.numina.common.utils.MathUtils;
import com.lehjr.powersuits.client.gui.module.select.GuiModeSelector;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class KeymappingKeyHandler {
    Minecraft minecraft;
    public static final KeyMapping goDownKey = new KeyMapping(Component.translatable("keybinding.powersuits.goDownKey").getString(), GLFW.GLFW_KEY_Z, MPSConstants.MPS_ITEM_GROUP);
    public static final KeyMapping cycleToolBackward = new KeyMapping(Component.translatable("keybinding.powersuits.cycleToolBackward").getString(), GLFW.GLFW_KEY_UNKNOWN, MPSConstants.MPS_ITEM_GROUP);
    public static final KeyMapping cycleToolForward = new KeyMapping(Component.translatable("keybinding.powersuits.cycleToolForward").getString(), GLFW.GLFW_KEY_UNKNOWN, MPSConstants.MPS_ITEM_GROUP);
    public static final KeyMapping openKeybindGUI = new KeyMapping(Component.translatable("keybinding.powersuits.openKeybindGui").getString()/*"Open MPS Keybind GUI"*/, GLFW.GLFW_KEY_UNKNOWN, MPSConstants.MPS_ITEM_GROUP);
    public static final KeyMapping openCosmeticGUI = new KeyMapping(Component.translatable("keybinding.powersuits.openCosmeticGUI").getString() /*Cosmetic GUI (MPS)"*/, GLFW.GLFW_KEY_UNKNOWN, MPSConstants.MPS_ITEM_GROUP);
    public static final KeyMapping openModuleTweakGUI = new KeyMapping(Component.translatable("keybinding.powersuits.openModuleTweakGUI").getString() /*Open MPS Keybind GUI"*/, GLFW.GLFW_KEY_UNKNOWN, MPSConstants.MPS_ITEM_GROUP);
    public static final KeyMapping openInstallSalvageGUI = new KeyMapping(Component.translatable("keybinding.powersuits.openInstallSalvageGUI").getString() /*Cosmetic GUI (MPS)"*/, GLFW.GLFW_KEY_UNKNOWN, MPSConstants.MPS_ITEM_GROUP);
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

        IPlayerKeyStates keyStates = clientPlayer.getCapability(NuminaCapabilities.PLAYER_KEYSTATES);
        if (keyStates != null) {
            boolean markForSync = false;

            // minecraft.player.input
            boolean forwardKeyState = minecraft.options.keyUp.isDown();
            boolean reverseKeyState = minecraft.options.keyDown.isDown();
            boolean strafeLeftKeyState = minecraft.options.keyLeft.isDown();
            boolean strafeRightKeyState = minecraft.options.keyRight.isDown();
            boolean downKeyState = goDownKey.isDown();
            boolean jumpKeyState = minecraft.options.keyJump.isDown();


            if (keyStates.getForwardKeyState() != forwardKeyState) {
                keyStates.setForwardKeyState(forwardKeyState);
                markForSync = true;
            }

            if (keyStates.getReverseKeyState() != reverseKeyState) {
                keyStates.setReverseKeyState(reverseKeyState);
                markForSync = true;
            }

            if (keyStates.getLeftStrafeKeyState() != strafeLeftKeyState) {
                keyStates.setLeftStrafeKeyState(strafeLeftKeyState);
                markForSync = true;
            }

            if (keyStates.getRightStrafeKeyState() != strafeRightKeyState) {
                keyStates.setRightStrafeKeyState(strafeRightKeyState);
                markForSync = true;
            }

            if (keyStates.getDownKeyState() != downKeyState) {
                keyStates.setDownKeyState(downKeyState);
                markForSync = true;
            }

            if (keyStates.getJumpKeyState() != jumpKeyState) {
                keyStates.setJumpKeyState(jumpKeyState);
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
                NuminaPackets.sendToServer(new PlayerUpdatePacketServerBound(byteOut));
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key e) {
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        getMPSKeyMappings().stream().filter(KeyMapping::isDown).forEach(MPSKeyMapping::toggleModules);

        KeyMapping[] hotbarKeys = minecraft.options.keyHotbarSlots;
        updatePlayerValues(player);
        Inventory inventory = player.getInventory();

        IModeChangingItem mci = getModeChangingItem(player);






        // Mode changinging GUI
        if (hotbarKeys[inventory.selected].isDown() && minecraft.isWindowActive()) {
            if(mci != null) {
                if (!(Minecraft.getInstance().screen instanceof GuiModeSelector)) {
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new GuiModeSelector(player, Component.literal("modeChanging"))));
                }
            }
        }

        /* cycleToolBackward/cycleToolForward */
        if (cycleToolBackward.isDown()) {
            assert minecraft.gameMode != null;
            minecraft.gameMode.tick();
            if(mci != null) {
                mci.cycleMode(player, 1);
            }
        }

        if (cycleToolForward.isDown()) {
            assert minecraft.gameMode != null;
            minecraft.gameMode.tick();
            if(mci != null) {
                mci.cycleMode(player, -1);
            }
        }

        /**  TODO: server config option to disable these and enforce tinker table requirement */
        /*
        if (openKeybindGUI.isDown()) {
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new TinkerKeymapGui(player, Component.translatable("gui.powersuits.tab.keybinds.toggle"))));
        }

        if (openCosmeticGUI.isDown()) {
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new CosmeticGui(player.getInventory(), Component.translatable("gui.tinkertable"))));
        }

        if (openModuleTweakGUI.isDown()) {
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModuleTweakGui(Component.translatable("gui.tinkertable"))));
        }

        if (openInstallSalvageGUI.isDown()) {
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(EquipmentSlot.MAINHAND));
        }
        */
    }

    // FIXME: move away from dynamic loading, just create a static list and be done with it.

    public static void loadKeyModuleBindings() {
        // Armor ----------------------------------------------------------------------------------
        registerKeybinding(MPSConstants.ENERGY_SHIELD_MODULE, false);

        // Cosmetic -------------------------------------------------------------------------------
        registerKeybinding(MPSConstants.TRANSPARENT_ARMOR_MODULE, false);

        // Energy Generation ----------------------------------------------------------------------
        // TODO/FIXME

        // Environmental --------------------------------------------------------------------------
        // FIXME/TODO cooling modules

        registerKeybinding(MPSConstants.AUTO_FEEDER_MODULE, false);
        registerKeybinding(MPSConstants.MOB_REPULSOR_MODULE, false);
        registerKeybinding(MPSConstants.WATER_ELECTROLYZER_MODULE, false);

        // Mining Enchantment ---------------------------------------------------------------------
        registerKeybinding(MPSConstants.AQUA_AFFINITY_MODULE, false);
        registerKeybinding(MPSConstants.FORTUNE_MODULE, false);
        registerKeybinding(MPSConstants.SILK_TOUCH_MODULE, false);

        // Mining Enhancement ---------------------------------------------------------------------
        registerKeybinding(MPSConstants.SELECTIVE_MINER_MODULE, false);
        registerKeybinding(MPSConstants.TUNNEL_BORE_MODULE, false);
        registerKeybinding(MPSConstants.VEIN_MINER_MODULE, false);

        // Movement -------------------------------------------------------------------------------
        registerKeybinding(MPSConstants.CLIMB_ASSIST_MODULE, false);
        registerKeybinding(MPSConstants.FLIGHT_CONTROL_MODULE, false);
        registerKeybinding(MPSConstants.GLIDER_MODULE, false);
        registerKeybinding(MPSConstants.JETBOOTS_MODULE, false);
        registerKeybinding(MPSConstants.JETPACK_MODULE, false);
        registerKeybinding(MPSConstants.PARACHUTE_MODULE, false);
        registerKeybinding(MPSConstants.SHOCK_ABSORBER_MODULE, false);
        registerKeybinding(MPSConstants.SPRINT_ASSIST_MODULE, false);
        registerKeybinding(MPSConstants.SWIM_BOOST_MODULE, false);

        // TODO!!! FINISH!!!





        for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
            ItemStack module = new ItemStack(item);
            IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
            if(pm != null) {
                NuminaLogger.logDebug("power module cap found for : " + module + ", pm class: " + pm.getClass());
                NuminaLogger.logDebug("is instanceof IToggleableModule: " + (pm instanceof IToggleableModule));

            } else {
                NuminaLogger.logDebug("not a valid module: " + module +", pm: " + pm);
            }

            if(pm instanceof IToggleableModule) {
//                // Tool settings are a bit odd
//                if (pm.getTarget() == ModuleTarget.TOOLONLY) {
//                    NuminaLogger.logDebug("moduleName " + ItemUtils.getRegistryName(item));
//
//                    // Mining Enhancement
//                    if (pm.getCategory() == ModuleCategory.MINING_ENHANCEMENT) {
//                        NuminaLogger.logDebug("registering kb for mining enhancement: " + ItemUtils.getRegistryName(item));
//                        registerKeybinding(ItemUtils.getRegistryName(item), false);
//                    } else if(pm.getCategory() == ModuleCategory.MINING_ENCHANTMENT) {
//                        NuminaLogger.logDebug("registering kb for mining enchantment: " + ItemUtils.getRegistryName(item));
//                        registerKeybinding(ItemUtils.getRegistryName(item), false);
//                    } else if (!IRightClickModule.class.isAssignableFrom(pm.getClass())) {
//                        registerKeybinding(ItemUtils.getRegistryName(item), false);
//                        NuminaLogger.logDebug("registering kb for some other module: " + ItemUtils.getRegistryName(item));
//                    } else {
//                        NuminaLogger.logDebug("NOT registering kb for module: " + ItemUtils.getRegistryName(item));
//                    }
//                } else {
                NuminaLogger.logDebug("registering kb for armor module: " + ItemUtils.getRegistryName(item));
                registerKeybinding(ItemUtils.getRegistryName(item), false);
//                }
            } else {
                if (item == Items.CLOCK ||
                        item == Items.COMPASS || item == Items.RECOVERY_COMPASS || ItemUtils.getRegistryName(item).equals(ResourceLocation.parse("ae2:meteorite_compass"))) {
                    registerKeybinding(ItemUtils.getRegistryName(item), false);
                }
            }
        }
    }

    public static List<MPSKeyMapping> getMPSKeyMappings() {
        return Arrays.stream(Minecraft.getInstance().options.keyMappings).filter(MPSKeyMapping.class::isInstance).map(MPSKeyMapping.class::cast).collect(Collectors.toList());
    }

    public static Map<String, MPSKeyMapping> keyMappings = new HashMap<>();

    public static void registerKeybinding(ResourceLocation registryName, boolean showOnHud) {
        NuminaLogger.logDebug("trying to register kb for : " + registryName);
        String keybindingName = "keybinding." + registryName.getNamespace() + "." + registryName.getPath();
        registerKeyBinding(registryName, keybindingName, GLFW.GLFW_KEY_UNKNOWN, MPSConstants.MPS_ITEM_GROUP, showOnHud, false);
    }

    public static void registerKeyBinding(ResourceLocation registryName, String  keybindingName, int keyIn, String category, boolean showOnHud, boolean overwrite) {
        if (overwrite || !keyMappings.containsKey(keybindingName) ) {
            NuminaLogger.logDebug("actually registering kb for : " + registryName);
            keyMappings.put(keybindingName, new MPSKeyMapping(registryName, keybindingName, keyIn, category, showOnHud));
        } else {
            NuminaLogger.logDebug("keymap handler did not register kb for " + registryName + ", overwrite: " + overwrite + "keymapping already registered: " + keyMappings.containsKey(keybindingName));
        }
    }

    @Nullable
    static IModeChangingItem getModeChangingItem(Player player) {
        return NuminaCapabilities.getModeChangingModularItem(player.getMainHandItem());
    }

}