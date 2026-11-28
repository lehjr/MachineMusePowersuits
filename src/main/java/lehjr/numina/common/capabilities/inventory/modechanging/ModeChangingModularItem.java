package lehjr.numina.common.capabilities.inventory.modechanging;

import lehjr.numina.common.capabilities.inventory.modularitem.ModularItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.enhancement.IMiningEnhancementModule;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.serverbound.ModeChangeRequestPacketServerBound;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ModeChangingModularItem extends ModularItem implements IModeChangingItem {
    protected static int activeMode = -1;

    public ModeChangingModularItem(@Nonnull ItemStack modularItem, int tier, int size) {
        super(modularItem, tier, size, true);
        activeMode = TagUtils.getModularItemIntOrDefault(modularItem, TAG_MODE, -1);
    }

    @Override
    public List<Integer> getValidModes() {
        List<Integer>moduleIndexes = new ArrayList<>();

        for(int i=0; i < getSlots();  i++) {
            ItemStack module = getStackInSlot(i);
            if (isValidMode(module)) {
                moduleIndexes.add(i);
            }
        }
        return moduleIndexes;
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
        if(activeModeIndex >=0 && activeModeIndex < getSlots()) {
            ItemStack module = getStackInSlot(activeModeIndex);
            IPowerModule pm = getModuleCapability(module);
            if ((pm instanceof IRightClickModule || pm instanceof IOtherModItemsAsModules) && pm.isAllowed()) {
                return module;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setActiveMode(int newMode) {
        activeMode = newMode;
        TagUtils.setModularItemInt(getModularItemStack(), TAG_MODE, newMode);
    }

    @Override
    public boolean hasActiveModule(Item item) {
        return hasActiveModule(getActiveMode(), item);
    }

    @Override
    public boolean hasActiveModule(int index, Item item) {
        final int activeModeIndex = getActiveMode();
        if(isSlotValid(activeModeIndex)  && index == activeModeIndex) {
            ItemStack module = getStackInSlot(activeModeIndex);
            if(module.is(item)) {
                return isModuleOnline(module);
            }
        }
        return false;
    }

    @Override
    public boolean hasActiveModule(ResourceLocation regName) {
        return hasActiveModule(BuiltInRegistries.ITEM.get(regName));
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
    public boolean isModuleActiveAndOnline(Item module) {
        int activeModeIndex = getActiveMode();
        if(activeModeIndex >= 0 && activeModeIndex < getSlots()) {
            ItemStack activeModule = getStackInSlot(activeModeIndex);
            if(!activeModule.isEmpty() && activeModule.is(module)) {
                IPowerModule pm = getModuleCapability(getActiveModule());
                return pm != null && pm.isModuleOnline() && pm.isAllowed();
            }
        }
        return false;
    }

    @Override
    public boolean isModuleActiveAndOnline(ResourceLocation moduleName) {
        return isModuleActiveAndOnline(BuiltInRegistries.ITEM.get(moduleName));
    }

    @Override
    public boolean isValidMode(@Nonnull ItemStack module) {
        if (module.isEmpty()) {
            return false;
        }

        IPowerModule pm = getModuleCapability(module);
        return (pm instanceof IRightClickModule || pm instanceof IOtherModItemsAsModules) && pm.isAllowed();// Allow selecting offline module? && pm.isModuleOnline();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand, InteractionResultHolder<ItemStack> fallback) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        ItemStack fist = player.getItemInHand(hand);
        if(pm instanceof IRightClickModule clickie &&
            pm.isAllowed() &&
            pm.isModuleOnline()) {
            return clickie.use(fist, level, player, hand);
        }
        return fallback;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context, InteractionResult fallback) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        if(pm instanceof IRightClickModule clickie && pm.isAllowed() && pm.isModuleOnline()) {
            return clickie.onItemUseFirst(itemStack, context);
        }
        return fallback;
    }

    @Override
    public InteractionResult useOn(UseOnContext context, InteractionResult fallback) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        if(pm instanceof IRightClickModule clickie && pm.isAllowed() && pm.isModuleOnline()) {
            return clickie.useOn(context);
        } else {
//            NuminaLogger.logDebug("not right click item? ");
        }
        return fallback;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        if (pm instanceof IRightClickModule rightClickModule) {
            rightClickModule.releaseUsing(stack, level, entityLiving, timeLeft);
        }
    }

    // Fixme: maybe just call releaseUsing with 0 timeLeft?
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        if (pm instanceof IRightClickModule rightClickModule) {
            rightClickModule.finishUsingItem(stack, level, livingEntity);
        }
        return getModularItemStack();
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockHitResult hitResult, Player player, Level level) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        if(pm instanceof IMiningEnhancementModule me && me.isAllowed() && me.isModuleOnline()) {
            return me.onBlockStartBreak(itemstack, hitResult,player, level);
        }
        return false;
    }

    @Override
    public boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        double playerEnergy = ElectricItemUtils.getPlayerEnergy(entityLiving);
        for(int i = 0;i < getSlots(); i++) {
            IPowerModule pm = getModuleCapability(getStackInSlot(i));
            if (pm instanceof IBlockBreakingModule bb &&
                bb.isAllowed() &&
                bb.isModuleOnline() &&
                bb.mineBlock(powerFist, worldIn, state, pos, entityLiving, playerEnergy)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState state) {
        float highest = 1.0F;
        for(int i = 0;i < getSlots(); i++) {
            IPowerModule pm = getModuleCapability(getStackInSlot(i));
            if (pm instanceof IBlockBreakingModule bb &&
                bb.isAllowed() &&
                bb.isModuleOnline()) {
                float speed = bb.getEmulatedTool().getDestroySpeed(state);
                if(speed > highest) {
                    highest = speed;
                }
            }
        }
        return highest;
    }

    @Override
    public boolean canPerformAction(ItemAbility toolAction) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
            if (pm != null && pm.isAllowed() && pm.isModuleOnline() && pm instanceof IBlockBreakingModule blockBreakingModule) {
                if (blockBreakingModule.canPerformAction(toolAction)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack itemStack, BlockState state) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
            if (pm != null && pm.isAllowed() && pm.isModuleOnline() && pm instanceof IBlockBreakingModule blockBreakingModule) {
                if (blockBreakingModule.getEmulatedTool().isCorrectToolForDrops(state)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean returnForeignModuleToModularItem(@Nonnull ItemStack module) {
        int slot = findInstalledModule(module);
        IOtherModItemsAsModules foreignModuleCap = module.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
        if (slot > -1 && foreignModuleCap != null) {
            updateModuleInSlot(slot, module);
            return true;
        }
        return false;
    }
}
