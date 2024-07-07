package lehjr.numina.common.capability.inventory.modechanging;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.enhancement.IMiningEnhancementModule;
import lehjr.numina.common.capability.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.capability.module.toggleable.IToggleableModule;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
            if (NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).map(pm -> pm.isModuleOnline()).orElse(false)) {
                gfx.renderItem(NuminaCapabilities.getCapability(module, NuminaCapabilities.CHAMELEON).map(iChameleon -> iChameleon.getStackToRender()).orElse(module), (int) currX, (int) currY);
            } else {
                IconUtils.drawModuleAt(gfx, currX, currY, NuminaCapabilities.getCapability(module, NuminaCapabilities.CHAMELEON).map(iChameleon -> iChameleon.getStackToRender()).orElse(module), false);
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
        ItemStack fist = player.getItemInHand(hand);
        return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .filter(IRightClickModule.class::isInstance)
                .map(IRightClickModule.class::cast)
                .map(rc -> rc.use(fist, level, player, hand)).orElse(fallback);
    }

    /**
     * @return useDuration from active module or fallback
     */
    default int getUseDuration() {
        return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .map(m -> m.getModule().getUseDuration())
                .orElse(72000);
    }

    default InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context, InteractionResult fallback) {
        return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .filter(IRightClickModule.class::isInstance)
                .map(IRightClickModule.class::cast)
                .map(m -> m.onItemUseFirst(itemStack, context))
                .orElse(fallback);
    }

    default InteractionResult useOn(UseOnContext context, InteractionResult fallback) {
        return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .filter(IRightClickModule.class::isInstance)
                .map(IRightClickModule.class::cast)
                .map(m -> m.useOn(context)).orElse(fallback);
    }

//    default ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entity) {
//        return getActiveModule().getCapability(NuminaCapabilities.POWER_MODULE)
//                .filter(IRightClickModule.class::isInstance)
//                .map(IRightClickModule.class::cast)
//                .map(m -> m.finishUsingItem(stack, worldIn, entity))
//                .orElse(stack);
//    }

    default void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .filter(IRightClickModule.class::isInstance)
                .map(IRightClickModule.class::cast)
                .ifPresent(m -> m.releaseUsing(stack, worldIn, entityLiving, timeLeft));
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


    default boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .filter(IMiningEnhancementModule.class::isInstance)
                .map(IMiningEnhancementModule.class::cast)
                .filter(IToggleableModule::isModuleOnline)
                .map(pm -> pm.onBlockStartBreak(itemstack, pos, player))
                .orElse(false);
    }

    default boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        double playerEnergy = ElectricItemUtils.getPlayerEnergy(entityLiving);
        return getInstalledModulesOfType(IBlockBreakingModule.class).stream().anyMatch(module ->
                NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE)
                        .filter(IBlockBreakingModule.class::isInstance)
                        .map(IBlockBreakingModule.class::cast)
                        .map(pm -> pm.mineBlock(powerFist, worldIn, state, pos, entityLiving, playerEnergy))
                        .orElse(false));
    }

    default float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return getInstalledModulesOfType(IBlockBreakingModule.class).stream()
                .filter(IBlockBreakingModule.class::isInstance)
                .map(IBlockBreakingModule.class::cast)
                .filter(pm -> pm.getEmulatedTool().getDestroySpeed(pState) > 1.0F)
                .max(Comparator.comparing(pm -> pm.getEmulatedTool().getDestroySpeed(pState)))
                .map(pm -> pm.getEmulatedTool().getDestroySpeed(pState)).orElse(1.0F);
    }

    default boolean isCorrectToolForDrops(ItemStack itemStack, BlockState state) {
        // FIXME

        return getInstalledModulesOfType(IBlockBreakingModule.class)
                .stream().anyMatch(module ->
                        NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE)
                                .filter(IBlockBreakingModule.class::isInstance)
                                .map(IBlockBreakingModule.class::cast)
                                .map(pm -> pm.getEmulatedTool().isCorrectToolForDrops(state)).orElse(false));
    }

    default ItemStack getActiveExternalModule() {
        return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                .filter(IOtherModItemsAsModules.class::isInstance)
                .map(IOtherModItemsAsModules.class::cast)
                .map(IPowerModule::getModule).orElse(getModularItemStack());
    }

    default boolean returnForeignModuleToModularItem(@Nonnull ItemStack module) {
        int slot = findInstalledModule(module);
        Optional<IOtherModItemsAsModules> foreignModuleCap = NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
        if (slot > -1 && foreignModuleCap.isPresent()) {
            updateModuleInSlot(slot, module);
            return true;
        }
        return false;
    }
}
