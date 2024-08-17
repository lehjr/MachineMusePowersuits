package com.lehjr.numina.common.capabilities.inventory.modechanging;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.IconUtils;
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
import net.neoforged.neoforge.common.ItemAbility;

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
    default void drawModeChangeIcon(LocalPlayer player, int hotbarIndex, GuiGraphics gfx, int screenWidth, int screenHeight) {
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
    default void drawModeChangingModularItemIcon(LocalPlayer player, int hotbarIndex, GuiGraphics gfx, int screenWidth, int screenHeight) {
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

    List<Integer> getValidModes();

    boolean isValidMode(ResourceLocation mode);

    boolean isValidMode(@Nonnull ItemStack module);

    int getActiveMode();

    ItemStack getActiveModule();

    void setActiveMode(int newMode);

    boolean hasActiveModule(ResourceLocation regName);

    void cycleMode(Player player, int dMode);

    int nextMode();

    int prevMode();

    boolean isModuleActiveAndOnline(ResourceLocation moduleName);

    InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand, InteractionResultHolder<ItemStack> fallback);

    /**
     * @return useDuration from active module or fallback
     */
    default int getUseDuration(LivingEntity entity) {
        return getActiveModule().getUseDuration(entity);
    }

    InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context, InteractionResult fallback);

    InteractionResult useOn(UseOnContext context, InteractionResult fallback);

//    default ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entity) {
//        return getActiveModule().getCapability(NuminaCapabilities.POWER_MODULE)
//                .filter(IRightClickModule.class::isInstance)
//                .map(IRightClickModule.class::cast)
//                .map(m -> m.finishUsingItem(stack, worldIn, entity))
//                .orElse(stack);
//    }

    void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft);

    default boolean canContinueUsing(@Nonnull ItemStack itemStack) {
//        return ItemStack.isSameItemSameTags(itemStack, getModularItemStack()) || ItemStack.isSameItemSameTags(itemStack, getActiveExternalModule());
        // fixme!!
        return true;
    }

//    /**
//     * Not entirely sure this does what was intended. It was supposed to be for using other mod's items as modules, but probably was a failed experiment
//     * @param level
//     * @param entity
//     * @param ticksRemaining
//     * @return
//     */
//    @Deprecated(forRemoval = true)
//    boolean onUseTick(Level level, LivingEntity entity, int ticksRemaining);

    boolean onBlockStartBreak(ItemStack itemstack, BlockHitResult hitResult, Player player, Level level);

    boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving);



    /**
     * Get the base block breaking speed for and from the emulated tool
     * @param pStack
     * @param state
     * @return
     */
    float getDestroySpeed(ItemStack pStack, BlockState state);

    boolean canPerformAction(ItemAbility toolAction);

    boolean isCorrectToolForDrops(ItemStack itemStack, BlockState state);

    default ItemStack getActiveExternalModule() {
        ItemStack module = getActiveModule();
        IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
        if(pm instanceof IOtherModItemsAsModules) {
            return module;
        }
        return getModularItemStack();
    }


    boolean returnForeignModuleToModularItem(@Nonnull ItemStack module);
}
