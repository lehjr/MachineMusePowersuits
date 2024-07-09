package lehjr.numina.common.capability.inventory.modechanging;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modularitem.ModularItem;
import lehjr.numina.common.capability.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.ModeChangeRequestPacketServerBound;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModeChangingModularItem extends ModularItem implements IModeChangingItem {
    protected static int activeMode = -1;

    public ModeChangingModularItem(@NotNull ItemStack modularItem, int tier, int size) {
        super(modularItem, tier, size, true);
        activeMode = TagUtils.getModularItemIntOrDefault(modularItem, TAG_MODE, -1);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public BakedModel getInventoryModel() {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(getActiveModule());
    }

    @Override
    public List<Integer> getValidModes() {
        List<Integer>moduleIndexes = new ArrayList<>();

        // note: starting at 1 skips the power storage there
        for(int i=1; i < getSlots();  i++) {
            ItemStack module = getStackInSlot(i);
            if (isValidMode(module)) {
                moduleIndexes.add(i);
            }
        }
        return moduleIndexes;
    }

    @Override
    public boolean isValidMode(ResourceLocation mode) {
        return false;
    }

    @Override
    public int getActiveMode() {
        if (activeMode == -1) {
            List<Integer> validModes = getValidModes();
            if (!validModes.isEmpty()) {
                activeMode = validModes.get(0);
            }
        }
        return activeMode;
    }

    @Override
    public ItemStack getActiveModule() {
        int activeModeIndex = getActiveMode();
        ItemStack module = activeModeIndex != -1 ? getStackInSlot(activeModeIndex) : ItemStack.EMPTY;
        return NuminaCapabilities.getPowerModuleCapability(module).map(m->m.isAllowed() && (m instanceof IRightClickModule || m instanceof IOtherModItemsAsModules)).orElse(false)
                ? module : ItemStack.EMPTY;
    }

    @Override
    public void setActiveMode(int newMode) {
        activeMode = newMode;
//        onContentsChanged(newMode);// FIXME!!!
    }

    @Override
    public boolean hasActiveModule(ResourceLocation regName) {
        final int activeModeIndex = getActiveMode();

        ItemStack module = activeModeIndex != -1 ? getStackInSlot(activeModeIndex) : ItemStack.EMPTY;

        if (regName == ItemUtils.getRegistryName(module)) {
            return isModuleOnline(module);
        }
        for (int i = 0; i < getSlots(); i++) {
            if (i != activeModeIndex) {
                module = getStackInSlot(i);
                if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(regName)) {
                    return isModuleOnline(module);
                }
            }
        }
        return false;
    }

    @Override
    public void cycleMode(Player player, int dMode) {
        List<Integer> modes = this.getValidModes();
        if (!modes.isEmpty()) {
            int newIndex = clampMode(modes.indexOf(this.getActiveMode()) + dMode, modes.size());
            int newMode = modes.get(newIndex);
            if(player.level().isClientSide()) {
                NuminaPackets.sendToServer(new ModeChangeRequestPacketServerBound(newMode));
            }
//            this.setActiveMode(newMode, player.getInventory());
        }
    }

    @Override
    public int nextMode() {
        List<Integer> modes = this.getValidModes();
        if (!modes.isEmpty()) {
            int newindex = clampMode(modes.indexOf(getActiveMode()) + 1, modes.size());
            return modes.get(newindex);
        }
        else return -1;
    }

    @Override
    public int prevMode() {
        List<Integer> modes = this.getValidModes();
        if (!modes.isEmpty()) {
            int newindex = clampMode(modes.indexOf(getActiveMode()) - 1, modes.size());
            return modes.get(newindex);
        }
        else return -1;
    }

    private static int clampMode(int selection, int modesSize) {
        return (selection > 0) ? (selection % modesSize) : ((selection + modesSize * -selection) % modesSize);
    }

    @Override
    public boolean isModuleActiveAndOnline(ResourceLocation moduleName) {
        if (hasActiveModule(moduleName)) {
            return NuminaCapabilities.getPowerModuleCapability(getActiveModule()).map(IPowerModule::isModuleOnline).orElse(false);
        }
        return false;
    }
}