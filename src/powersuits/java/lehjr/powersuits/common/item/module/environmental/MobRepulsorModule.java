package lehjr.powersuits.common.item.module.environmental;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

/**
 * Created by User: Andrew2448
 * 8:26 PM 4/25/13
 */
public class MobRepulsorModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.HEADONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 2500, "FE");
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item) {
            int energyConsumption = (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption) {
                if (level.getGameTime() % 20 == 0) {
                    ElectricItemUtils.drainPlayerEnergy(player, energyConsumption, false);
                }
                repulse(level, player.blockPosition());
            }
        }

        // FIXME: check for instances instead of direct references
        public void repulse(Level world, BlockPos playerPos) {
            int distance = 5;

            AABB area = new AABB(
                    // min
                    playerPos.getX() -distance,
                    playerPos.getY() -distance,
                    playerPos.getZ() -distance,
                    // max
                    playerPos.getX() +distance,
                    playerPos.getY() +distance,
                    playerPos.getZ() +distance);


            for (Entity entity : world.getEntitiesOfClass(Mob.class, area)) {
                push(entity, playerPos);
            }

            for (Arrow entity : world.getEntitiesOfClass(Arrow.class, area)) {
                push(entity, playerPos);
            }

            for (Fireball entity : world.getEntitiesOfClass(Fireball.class, area)) {
                push(entity, playerPos);
            }

            for (ThrownPotion entity : world.getEntitiesOfClass(ThrownPotion.class, area)) {
                push(entity, playerPos);
            }
        }

        private void push(Entity entity, BlockPos playerPos) {
            if (!(entity instanceof Player) && !(entity instanceof EnderDragon)) {
                BlockPos distance = playerPos.subtract(entity.blockPosition());

                double dX = distance.getX();
                double dY = distance.getY();
                double dZ = distance.getZ();
                double d4 = dX * dX + dY * dY + dZ * dZ;
                d4 *= d4;
                if (d4 <= Math.pow(6.0D, 4.0D)) {
                    double d5 = -(dX * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
                    double d6 = -(dY * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
                    double d7 = -(dZ * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
                    if (d5 > 0.0D) {
                        d5 = 0.22D;
                    } else if (d5 < 0.0D) {
                        d5 = -0.22D;
                    }
                    if (d6 > 0.2D) {
                        d6 = 0.12D;
                    } else if (d6 < -0.1D) {
                        d6 = 0.12D;
                    }
                    if (d7 > 0.0D) {
                        d7 = 0.22D;
                    } else if (d7 < 0.0D) {
                        d7 = -0.22D;
                    }
                    Vec3 motion = entity.getDeltaMovement();
                    entity.setDeltaMovement(motion.x + d5, motion.y + d6, motion.z + d7);
                }
            }
        }
    }
}