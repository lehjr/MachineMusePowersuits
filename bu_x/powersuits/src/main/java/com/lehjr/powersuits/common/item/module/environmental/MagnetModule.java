package com.lehjr.powersuits.common.item.module.environmental;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import java.util.List;

public class MagnetModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target) {
            super(module, category, target);
                addBaseProperty(MPSConstants.RADIUS, 1, "m");
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 5, "FE");
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, 2000);
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, 9, "m", 1, 0);
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack stack) {
            int energyUSage = (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            if (ElectricItemUtils.getPlayerEnergy(player) > energyUSage) {
                boolean isServerSide = !level.isClientSide;

                if ((level.getGameTime() % 20) == 0 && isServerSide) {
                    ElectricItemUtils.drainPlayerEnergy(player, energyUSage, false);
                }
                int range = (int) applyPropertyModifiers(MPSConstants.RADIUS);
                AABB bounds = player.getBoundingBox().inflate(range);

                if (isServerSide) {
                    bounds.expandTowards(0.2000000029802322D, 0.2000000029802322D, 0.2000000029802322D);
                    if (stack.getDamageValue() >> 1 >= 7) {
                        List<Arrow> arrows = level.getEntitiesOfClass(Arrow.class, bounds);
                        for (Arrow arrow : arrows) {
                            if ((arrow.pickup == AbstractArrow.Pickup.ALLOWED) && (level.random.nextInt(6) == 0)) {
                                ItemEntity replacement = new ItemEntity(level, arrow.getX(), arrow.getY(), arrow.getZ(), new ItemStack(Items.ARROW));
                                level.addFreshEntity(replacement);
                            }
                            arrow.remove(Entity.RemovalReason.DISCARDED);
                        }
                    }
                }

                for (ItemEntity e : level.getEntitiesOfClass(ItemEntity.class, bounds)) {
                    if (e.isAlive() && !e.getItem().isEmpty() && !e.hasPickUpDelay()) {
                        if (isServerSide) {
                            double x = player.getX() - e.getX();
                            double y = player.getY() - e.getY();
                            double z = player.getZ() - e.getZ();

                            double length = Math.sqrt(x * x + y * y + z * z) * 0.75D;

                            x = x / length + player.getDeltaMovement().x * 22.0D;
                            y = y / length + player.getDeltaMovement().y / 22.0D;
                            z = z / length + player.getDeltaMovement().z * 22.0D;

                            e.setDeltaMovement(x, y, z);

                            e.hasImpulse = true;
                            if (e.horizontalCollision) {
                                e.setDeltaMovement(e.getDeltaMovement().add(0, 1, 0));
                            }
                        } else if (level.random.nextInt(20) == 0) {
                            float pitch = 0.85F - level.random.nextFloat() * 3.0F / 10.0F;
                            level.playLocalSound(e.getX(), e.getY(), e.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.6F, pitch, true);
                        }
                    }
                }
            }
        }
    }
}