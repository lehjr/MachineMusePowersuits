package lehjr.numina.common.capabilities.module.externalitems;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.imixin.common.item.IUseOnContextMixn;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface IOtherModItemsAsModules extends IRightClickModule {
    String MODE_CHANGING_MODULAR_ITEM = "modeChangingModularItem";

    /**
     * Note: returning the itemStackIn wrapped in the InteractionResult because external code will set the held item to that
     * @param itemStackIn
     * @param worldIn
     * @param playerIn
     * @param hand
     * @return
     */
    @Override
    default InteractionResultHolder<ItemStack> use(@Nonnull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
        InteractionResultHolder<ItemStack> tmp = getModule().use(worldIn, playerIn, hand);
        return new InteractionResultHolder<>(tmp.getResult(), itemStackIn);
    }

    @Override
    default InteractionResultHolder<ItemStack> interactLivingEntity(ItemStack itemStackIn, Player playerIn, LivingEntity entity, InteractionHand hand) {
        InteractionResult ret = getModule().interactLivingEntity(playerIn, entity,hand);
        return new InteractionResultHolder<>(ret, itemStackIn);
    }

    default boolean onUseTick(Level level, LivingEntity entity, int ticksRemaining) {
        getModule().onUseTick(level, entity, ticksRemaining);
        return false;
    }

    @Override
    default InteractionResult useOn(UseOnContext context) {
        return getModule().useOn(new UseOnContext(context.getLevel(), context.getPlayer(), context.getHand(), getModule(), ((IUseOnContextMixn)context).machineMusePowersuits$getBlockHitResult()));
    }

    @Override
    default InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return getModule().onItemUseFirst(new UseOnContext(context.getLevel(), context.getPlayer(), context.getHand(), getModule(), ((IUseOnContextMixn)context).machineMusePowersuits$getBlockHitResult()));
    }

    @Override
    default void releaseUsing(@NotNull ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        getModule().releaseUsing(worldIn, entityLiving, timeLeft);
    }

    @Override
    default ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        getModule().finishUsingItem(level, entity);
        return itemStack;
    }

    default void storeHostStack(@Nonnull ItemStack hostStack) {
        ItemStack module = getModule();
        CompoundTag tag = module.getOrCreateTag();
        tag.put(MODE_CHANGING_MODULAR_ITEM, hostStack.serializeNBT());
        module.setTag(tag);
        setModuleStack(module);
    }

    @Nonnull
    default ItemStack retrieveHostStack() {
        ItemStack module = getModule();
        CompoundTag tag = module.getOrCreateTag();
        if (tag.contains(MODE_CHANGING_MODULAR_ITEM)){
            CompoundTag stackTag = tag.getCompound(MODE_CHANGING_MODULAR_ITEM);

            // this part probably won't matter when the item gets returned
            tag.remove(MODE_CHANGING_MODULAR_ITEM);
            module.setTag(tag);
            return ItemStack.of(stackTag);
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    default IModeChangingItem getStoredModeChangingModuleCapInStorage() {
        ItemStack module = getModule();
        CompoundTag tag = module.getOrCreateTag();
        if (tag.contains(MODE_CHANGING_MODULAR_ITEM)){
            CompoundTag stackTag = tag.getCompound(MODE_CHANGING_MODULAR_ITEM);
            return NuminaCapabilities.getModeChangingModularItem(ItemStack.of(stackTag));
        }
        return null;
    }

    void setModuleStack(@Nonnull ItemStack stack);
}