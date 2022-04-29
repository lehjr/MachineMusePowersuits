package lehjr.numina.integration.appeneng2;

import appeng.api.config.*;
import appeng.api.features.IWirelessTermHandler;
import appeng.api.util.IConfigManager;
import appeng.core.AELog;
import appeng.core.Api;
import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.energy.ElectricItemUtils;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Eximius88 on 1/13/14.
 */
public class TerminalHandler implements IWirelessTermHandler {//}, IWirelessFluidTermHandler {
    public static final ResourceLocation WIRELESS_TERMINAL_REG = new ResourceLocation("appliedenergistics2:wireless_terminal");

    private static final String TAG_ENCRYPTION_KEY = "encryptionKey";
    @Override
    public boolean canHandle(ItemStack is) {
        return is.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(iModeChangingItem -> iModeChangingItem.isModuleActiveAndOnline(WIRELESS_TERMINAL_REG)).orElse(false);
    }

    @Override
    public boolean usePower(Player player, double amount, ItemStack is) {
        int drainVal = (int) PowerUnits.AE.convertTo(PowerUnits.RF, amount);
        if (ElectricItemUtils.getPlayerEnergy(player) > drainVal) {
            ElectricItemUtils.drainPlayerEnergy(player, drainVal, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPower(Player player, double amount, ItemStack is) {
        return ElectricItemUtils.getPlayerEnergy(player) > PowerUnits.AE.convertTo(PowerUnits.RF, amount);
    }

    @Override
    public IConfigManager getConfigManager(ItemStack is) {
        IConfigManager config = new WirelessConfig(is);
        config.registerSetting(Settings.SORT_BY, SortOrder.NAME);
        config.registerSetting(Settings.VIEW_MODE, ViewItems.ALL);
        config.registerSetting(Settings.SORT_DIRECTION, SortDir.ASCENDING);

        config.readFromNBT(is.getOrCreateTag());
        return config;
    }

    @Override
    public String getEncryptionKey(ItemStack item) {
        return item.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(iModeChangingItem -> {
                    ItemStack module = iModeChangingItem.getOnlineModuleOrEmpty(WIRELESS_TERMINAL_REG);
                    if (!module.isEmpty()) {
                        CompoundTag tag = openNbtData(module);
                        if (tag != null) {
                            return tag.getString(TAG_ENCRYPTION_KEY);
                        }

                        tag = openNbtData(item);
                        if (tag != null) {
                            String encKey = tag.getString(TAG_ENCRYPTION_KEY);
                            module.getTag().putString(TAG_ENCRYPTION_KEY, encKey);
                            return encKey;
                        }
                    }
                    return null;
                }).orElse(null);
    }

    @Override
    public void setEncryptionKey(ItemStack item, String encKey, String name) {
        item.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .ifPresent(iModeChangingItem -> {
                    ItemStack module = iModeChangingItem.getOnlineModuleOrEmpty(WIRELESS_TERMINAL_REG);
                    if (!module.isEmpty()) {
                        CompoundTag tag = openNbtData(item);
                        if (tag != null) {
                            tag.putString(TAG_ENCRYPTION_KEY, encKey);
                        }
                        iModeChangingItem.getOnlineModuleOrEmpty(WIRELESS_TERMINAL_REG).getTag().putString(TAG_ENCRYPTION_KEY, encKey);
                    }
                });
    }

    private static void registerAEHandler(TerminalHandler handler) {
        Api.instance().registries().wireless().registerWirelessHandler(handler);
    }

    public static void registerHandler() {
        if (ModList.get().isLoaded("appliedenergistics2")) {
            TerminalHandler handler = new TerminalHandler();
            registerAEHandler(handler);
        }
    }

    public static CompoundTag openNbtData(ItemStack item) {
        CompoundTag compound = item.getOrCreateTag();
        return compound;
    }

    class WirelessConfig implements IConfigManager {
        private final Map<Settings, Enum<?>> enums = new EnumMap<>(Settings.class);

        final ItemStack stack;

        public WirelessConfig(ItemStack itemStack) {
            stack = itemStack;
        }

        @Override
        public Set<Settings> getSettings() {
            return enums.keySet();
        }

        @Override
        public void registerSetting(Settings settingName, Enum<?> defaultValue) {
            if (!enums.containsKey(settingName)) {
                putSetting(settingName, defaultValue);
            }
        }

        @Override
        public Enum<?> getSetting(Settings settingName) {
            if (enums.containsKey(settingName)) {
                return enums.get(settingName);
            }
            return null;
        }

        @Override
        public Enum<?> putSetting(Settings settingName, Enum<?> newValue) {
            enums.put(settingName, newValue);
            writeToNBT(stack.getOrCreateTag());
            return newValue;
        }

        @Override
        public void writeToNBT(CompoundTag destination) {
            CompoundTag tag = new CompoundTag();
            if (destination.contains("configWirelessTerminal")) {
                tag = destination.getCompound("configWirelessTerminal");
            }

            for (Enum e : enums.keySet()) {
                tag.putString(e.name(), enums.get(e).toString());
            }
            destination.put("configWirelessTerminal", tag);
        }

        @Override
        public void readFromNBT(final CompoundTag src) {
            CompoundTag tag = null;
            if (src.contains("configWirelessTerminal")) {
                tag = src.getCompound("configWirelessTerminal");
            }

            if (tag == null) {
                return;
            }

            for (final Map.Entry<Settings, Enum<?>> entry : enums.entrySet()) {
                try {
                    if (tag.contains(entry.getKey().name())) {
                        String value = tag.getString(entry.getKey().name());

                        // Provides an upgrade path for the rename of this value in the API between rv1
                        // and rv2
                        if (value.equals("EXTACTABLE_ONLY")) {
                            value = StorageFilter.EXTRACTABLE_ONLY.toString();
                        } else if (value.equals("STOREABLE_AMOUNT")) {
                            value = LevelEmitterMode.STORABLE_AMOUNT.toString();
                        }

                        final Enum<?> oldValue = enums.get(entry.getKey());

                        final Enum<?> newValue = Enum.valueOf(oldValue.getClass(), value);

                        this.putSetting(entry.getKey(), newValue);
                    }
                } catch (final IllegalArgumentException e) {
                    AELog.debug(e);
                }
            }
        }
    }

    // new
    private static class LinkableHandler implements IGridLinkableHandler {
        private LinkableHandler() {
        }

        public boolean canLink(ItemStack stack) {
            return stack.getItem() instanceof WirelessTerminalItem;
        }

        public void link(ItemStack itemStack, long securityKey) {
            itemStack.getOrCreateTag().putLong("gridKey", securityKey);
        }

        public void unlink(ItemStack itemStack) {
            itemStack.removeTagKey("gridKey");
        }
    }

}
