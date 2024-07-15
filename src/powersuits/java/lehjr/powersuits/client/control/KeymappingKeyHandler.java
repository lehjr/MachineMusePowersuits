package lehjr.powersuits.client.control;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.capability.module.toggleable.IToggleableModule;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.PlayerUpdatePacketServerBound;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.MathUtils;
import lehjr.powersuits.client.gui.module.select.GuiModeSelector;
import lehjr.powersuits.common.constants.MPSConstants;
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

import java.util.*;
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

        NuminaCapabilities.getCapability(clientPlayer, NuminaCapabilities.PLAYER_KEYSTATES).ifPresent(playerCap -> {
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
                NuminaPackets.sendToServer(new PlayerUpdatePacketServerBound(byteOut));
            }
        });
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

        // Mode changinging GUI
        if (hotbarKeys[inventory.selected].isDown() && minecraft.isWindowActive()) {
            getMCItemCap(player).ifPresent(iModeChanging->{
                if(player.level().isClientSide) {
                    if (!(Minecraft.getInstance().screen instanceof GuiModeSelector)) {
                        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new GuiModeSelector(player, Component.literal("modeChanging"))));
                    }
                }
            });
        }

        /* cycleToolBackward/cycleToolForward */
        if (cycleToolBackward.isDown()) {
            assert minecraft.gameMode != null;
            minecraft.gameMode.tick();
            getMCItemCap(player).map(IModeChangingItem.class::cast).ifPresent(handler-> handler.cycleMode(player, 1));
        }

        if (cycleToolForward.isDown()) {
            assert minecraft.gameMode != null;
            minecraft.gameMode.tick();
            getMCItemCap(player).ifPresent(handler-> handler.cycleMode(player, -1));
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

    public static void loadKeyBindings() {
        for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
            ItemStack module = new ItemStack(item);
            IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
            if(pm != null) {
                NuminaLogger.logDebug("power module cap found for : " + module + ", pm class: " + pm.getClass());
                NuminaLogger.logDebug("is instanceof IToggleableModule: " + (pm instanceof IToggleableModule));

            }

            if(pm instanceof IToggleableModule) {
                // Tool settings are a bit odd
                if (pm.getTarget() == ModuleTarget.TOOLONLY) {
                    NuminaLogger.logDebug("moduleName " + ItemUtils.getRegistryName(item));

                    // Mining Enhancement
                    if (pm.getCategory() == ModuleCategory.MINING_ENHANCEMENT) {
                        NuminaLogger.logDebug("registering kb for mining enhancement: " + ItemUtils.getRegistryName(item));
                        registerKeybinding(ItemUtils.getRegistryName(item), false);
                    } else if(pm.getCategory() == ModuleCategory.MINING_ENCHANTMENT) {
                        NuminaLogger.logDebug("registering kb for mining enchantment: " + ItemUtils.getRegistryName(item));
                        registerKeybinding(ItemUtils.getRegistryName(item), false);
                    } else if (!IRightClickModule.class.isAssignableFrom(pm.getClass())) {
                        registerKeybinding(ItemUtils.getRegistryName(item), false);
                        NuminaLogger.logDebug("registering kb for some other module: " + ItemUtils.getRegistryName(item));
                    }
                } else {
                    NuminaLogger.logDebug("registering kb for armor module: " + ItemUtils.getRegistryName(item));
                    registerKeybinding(ItemUtils.getRegistryName(item), false);
                }
            } else {
                if (item == Items.CLOCK ||
                        item == Items.COMPASS || item == Items.RECOVERY_COMPASS || ItemUtils.getRegistryName(item).equals(new ResourceLocation("ae2:meteorite_compass"))) {
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
            NuminaLogger.logDebug("keymap handler did not register kb for " + registryName);
        }
    }

    static Optional<IModeChangingItem> getMCItemCap(Player player) {
        try {
             return NuminaCapabilities.getModeChangingModularItemCapability(player);
        } catch(Exception e) {
            NuminaLogger.logException("failed to get ModularItem capability: ", e);
        }
        return Optional.empty();
    }
}