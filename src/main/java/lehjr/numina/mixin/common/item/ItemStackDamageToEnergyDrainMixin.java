package lehjr.numina.mixin.common.item;

import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Convert incoming ItemStack damage into energy drainage when using another mod's item as a module
 */
@Mixin(ItemStack.class)
public class ItemStackDamageToEnergyDrainMixin {

    @Inject(
        method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void redirectDamageToEnergy(int damage, ServerLevel level, @Nullable LivingEntity entity, Consumer<Item> onBreak, CallbackInfo ci) {
        // 1. Durability drops can happen without a source entity, so make sure it's present and a player
        if (entity instanceof ServerPlayer player) {
            ItemStack stack = (ItemStack) (Object) this;

            // 2. Check if this specific item stack has your module active
            if (isExternalModuleWithHost(stack)) {
                int energyCostPerDamage = 500;
                int totalEnergyRequired = damage * energyCostPerDamage;

                // 3. Fetch NeoForge Energy from the player entity cap
                double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                if (playerEnergy >= totalEnergyRequired) {
                    // Check if player has enough power
                    double extracted = ElectricItemUtils.drainPlayerEnergy(player, totalEnergyRequired, true);
                    if (extracted >= totalEnergyRequired) {
                        // Drain it completely
                        ElectricItemUtils.drainPlayerEnergy(player, totalEnergyRequired, false);

                        // Stop the method execution here so durability is unchanged
                        ci.cancel();
                    }
                }
            }
        }
    }

    private boolean isExternalModuleWithHost(@Nonnull ItemStack stack) {
        // Adapt this to your modular system logic
        IOtherModItemsAsModules cap = stack.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
        if(cap != null) {
            return cap.hasHostStack(stack);
        }
        return false;
    }
}
