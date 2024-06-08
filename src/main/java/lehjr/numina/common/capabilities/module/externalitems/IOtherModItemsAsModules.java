package lehjr.numina.common.capabilities.module.externalitems;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.utils.TagUtils;
import lehjr.numina.imixin.common.item.IUseOnContextMixn;
import net.minecraft.core.HolderLookup;
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
import java.util.Objects;
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

//    default String getUniqueID() {
//        CompoundTag tag = getModule().getOrCreateTag();
//        if (tag.contains(IModeChangingItem.UNIQUE_ID)) {
//            return tag.getString(IModeChangingItem.UNIQUE_ID);
//        }
//        return "";
//    }
//
//    default void setUniqueID(String uniqueID) {
//        CompoundTag tag = getModule().getOrCreateTag();
//        if(uniqueID.isBlank()) {
//            tag.remove(IModeChangingItem.UNIQUE_ID);
//        } else {
//            tag.putString(IModeChangingItem.UNIQUE_ID, uniqueID);
//        }
//        getModule().setTag(tag);
//    }

    default void storeHostStack(HolderLookup.Provider provider, @Nonnull ItemStack hostStack) {
        ItemStack module = getModule();
        CompoundTag tag = TagUtils.getTag(module);
        tag.put(MODE_CHANGING_MODULAR_ITEM, Objects.requireNonNull(hostStack.save(provider, tag)));

        TagUtils.setTag(module, tag);
        setModuleStack(module);
    }

    @Nonnull
    default ItemStack retrieveHostStack(HolderLookup.Provider provider) {
        ItemStack module = getModule();
        CompoundTag tag = TagUtils.getTag(module);
        if (tag.contains(MODE_CHANGING_MODULAR_ITEM)){
            CompoundTag stackTag = tag.getCompound(MODE_CHANGING_MODULAR_ITEM);

            // this part probably won't matter when the item gets returned
            tag.remove(MODE_CHANGING_MODULAR_ITEM);
            TagUtils.setTag(module, tag);
            return ItemStack.parse(provider, stackTag).orElse(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }

    default Optional<IModeChangingItem> getStoredModeChangingModuleCapInStorage(HolderLookup.Provider provider) {
        ItemStack module = getModule();
        CompoundTag tag = TagUtils.getTag(module);
        if (tag.contains(MODE_CHANGING_MODULAR_ITEM)){
            CompoundTag stackTag = tag.getCompound(MODE_CHANGING_MODULAR_ITEM);
            Optional<ItemStack> optionalStack = ItemStack.parse(provider, stackTag);
            if (optionalStack.isPresent()) {
                return NuminaCapabilities.getCapability(optionalStack.get(), NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
            }
        }
        return Optional.empty();
    }

    void setModuleStack(@Nonnull ItemStack stack);
}