package lehjr.numina.common.capability.inventory.modechanging;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.enhancement.IMiningEnhancementModule;
import lehjr.numina.common.capability.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.capability.render.chameleon.IChameleon;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ToolAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IModeChangingItem extends IModularItem {
    String TAG_MODE = "mode";
//    String UNIQUE_ID = "unique_ID";

    @OnlyIn(Dist.CLIENT)
    @Nullable
    BakedModel getInventoryModel();

    @OnlyIn(Dist.CLIENT)
    default void drawModeChangeIcon(LocalPlayer player, int hotbarIndex, Minecraft mc, GuiGraphics gfx, float partialTick, int screenWidth, int screenHeight) {
        ItemStack module = getActiveModule();
        if (!module.isEmpty()) {
            double currX;
            double currY;

            int baroffset = 22;
            if (!player.isCreative()) {
                baroffset += 16;
                int totalArmorValue = player.getArmorValue();
                baroffset += 8 * (int) Math.ceil((double) totalArmorValue / 20); // 20 points per row @ 2 armor points per icon
            }
            baroffset = screenHeight - baroffset;
            currX = screenWidth / 2.0 - 89.0 + 20.0 * hotbarIndex;
            currY = baroffset - 18;
            Color.WHITE.setShaderColor();
            IPowerModule pm = getModuleCapability(module);

            if (pm == null) {
                return;
            }

            ItemStack stackToRender;
            if(pm instanceof IChameleon && !((IChameleon) pm).getStackToRender().isEmpty()) {
                stackToRender = ((IChameleon) pm).getStackToRender();
            } else {
                stackToRender = module.copy();
            }

            if (pm.isModuleOnline()) {
                gfx.renderItem(stackToRender, (int) currX, (int) currY);
            } else {
                IconUtils.drawModuleAt(gfx, currX, currY, stackToRender, false);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    default void drawModeChangingModularItemIcon(LocalPlayer player, int hotbarIndex, Minecraft mc, GuiGraphics gfx, float partialTick, int screenWidth, int screenHeight) {
        ItemStack modularItem = getModularItemStack();
        if (!modularItem.isEmpty()) {
            double currX;
            double currY;

            int baroffset = 22;
            if (!player.isCreative()) {
                baroffset += 16;
                int totalArmorValue = player.getArmorValue();
                baroffset += 8 * (int) Math.ceil((double) totalArmorValue / 20); // 20 points per row @ 2 armor points per icon
            }
            baroffset = screenHeight - baroffset;
            currX = screenWidth / 2.0 - 89.0 + 20.0 * hotbarIndex;
            currY = baroffset - 18;
            Color.WHITE.setShaderColor();
            gfx.renderItem(modularItem, (int) currX, (int) currY);
        }
    }

//    String getUniqueID();

    List<Integer> getValidModes();

    boolean isValidMode(ResourceLocation mode);

    default boolean isValidMode(@Nonnull ItemStack module) {
        if (module.isEmpty()) {
            return false;
        }

        IPowerModule pm = getModuleCapability(module);
        return (pm instanceof IRightClickModule || pm instanceof IOtherModItemsAsModules) && pm.isAllowed();// Allow selecting offline module? && pm.isModuleOnline();
    }

    int getActiveMode();

    ItemStack getActiveModule();

//    void setActiveMode(ResourceLocation moduleName);

    void setActiveMode(int newMode);

    boolean hasActiveModule(ResourceLocation regName);

    void cycleMode(Player player, int dMode);

    int nextMode();

    int prevMode();

    boolean isModuleActiveAndOnline(ResourceLocation moduleName);

    default InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand, InteractionResultHolder<ItemStack> fallback) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        ItemStack fist = player.getItemInHand(hand);
        if(pm instanceof IRightClickModule clickie) {
            return clickie.use(fist, level, player, hand);
        }
        return fallback;
    }

    /**
     * @return useDuration from active module or fallback
     */
    default int getUseDuration() {
        return getActiveModule().getUseDuration();
    }

    default InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context, InteractionResult fallback) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        if(pm instanceof IRightClickModule clickie) {
            return clickie.onItemUseFirst(itemStack, context);
        }
        return fallback;
    }

    default InteractionResult useOn(UseOnContext context, InteractionResult fallback) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        if(pm instanceof IRightClickModule clickie) {
            return clickie.useOn(context);
        }
        return fallback;
    }

//    default ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entity) {
//        return getActiveModule().getCapability(NuminaCapabilities.POWER_MODULE)
//                .filter(IRightClickModule.class::isInstance)
//                .map(IRightClickModule.class::cast)
//                .map(m -> m.finishUsingItem(stack, worldIn, entity))
//                .orElse(stack);
//    }

    default void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        NuminaLogger.logDebug("release using");
        IPowerModule pm = getModuleCapability(getActiveModule());
        if (pm instanceof IRightClickModule clickie) {
            clickie.releaseUsing(stack, level, entityLiving, timeLeft);
        }
    }

    default boolean canContinueUsing(@Nonnull ItemStack itemStack) {
//        return ItemStack.isSameItemSameTags(itemStack, getModularItemStack()) || ItemStack.isSameItemSameTags(itemStack, getActiveExternalModule());
        // fixme!!
        return true;
    }

    default boolean onUseTick(Level level, LivingEntity entity, int ticksRemaining) {
        return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .filter(IOtherModItemsAsModules.class::isInstance)
                .map(IOtherModItemsAsModules.class::cast).map(iOtherModItemsAsModules -> iOtherModItemsAsModules.onUseTick(level, entity, ticksRemaining)).orElse(true);
    }

    default boolean onBlockStartBreak(ItemStack itemstack, BlockHitResult hitResult, Player player, Level level) {
        IPowerModule pm = getModuleCapability(getActiveModule());
        if(pm instanceof IMiningEnhancementModule me) {
            return me.onBlockStartBreak(itemstack, hitResult,player, level);
        }
        return false;
    }

    default boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        double playerEnergy = ElectricItemUtils.getPlayerEnergy(entityLiving);
        for(int i = 0;i < getSlots(); i++) {
            IPowerModule pm = getModuleCapability(getStackInSlot(i));
            if (pm instanceof IBlockBreakingModule bb && bb.mineBlock(powerFist, worldIn, state, pos, entityLiving, playerEnergy)) {
                return true;
            }
        }
        return false;
    }

    default float getDestroySpeed(ItemStack pStack, BlockState state) {
        float highest = 1.0F;
        for(int i = 0;i < getSlots(); i++) {
            IPowerModule pm = getModuleCapability(getStackInSlot(i));
            if (pm instanceof IBlockBreakingModule bb) {
                float speed = bb.getEmulatedTool().getDestroySpeed(state);
                if(speed > highest) {
                    highest = speed;
                }
            }
        }
        return highest;
    }

    default boolean canPerformAction(ToolAction toolAction) {
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

    default boolean isCorrectToolForDrops(ItemStack itemStack, BlockState state) {
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

    default ItemStack getActiveExternalModule() {
        return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .filter(IOtherModItemsAsModules.class::isInstance)
                .map(IOtherModItemsAsModules.class::cast)
                .map(IPowerModule::getModule).orElse(getModularItemStack());
    }

    default boolean returnForeignModuleToModularItem(@Nonnull ItemStack module) {
        int slot = findInstalledModule(module);
        IOtherModItemsAsModules foreignModuleCap = module.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
        if (slot > -1 && foreignModuleCap != null) {
            updateModuleInSlot(slot, module);
            return true;
        }
        return false;
    }
}
