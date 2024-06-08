package lehjr.numina.common.capabilities.module.rightclick;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public interface IRightClickModule extends IPowerModule {

    /**
     * @param itemStackIn
     * @param worldIn
     * @param playerIn
     * @param hand
     * @return
     */

    default InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
        return new InteractionResultHolder<>(InteractionResult.PASS, itemStackIn);
    }

    /**
     * replaces previously used onRightClick method, adds a parameter
     * @param itemStackIn
     * @param playerIn
     * @param entity
     * @param hand
     * @return
     */
    default InteractionResultHolder<ItemStack> interactLivingEntity(ItemStack itemStackIn, Player playerIn, LivingEntity entity, InteractionHand hand) {
        return new InteractionResultHolder<>(InteractionResult.PASS, itemStackIn);
    }

    default InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    default InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return InteractionResult.PASS;
    }

    default void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    default ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        return itemStack.has(DataComponents.FOOD) ? entity.eat(level, itemStack) : itemStack;
    }
}