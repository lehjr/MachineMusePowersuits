package com.github.lehjr.powersuits.item.module.environmental;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * Created by User: Andrew2448
 * 8:26 PM 4/25/13
 */
public class MobRepulsorModule extends AbstractPowerModule {
    public MobRepulsorModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundNBT nbt){
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.ENVIRONMENTAL, EnumModuleTarget.TORSOONLY, MPSSettings::getModuleConfig);
            this.ticker.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 2500, "FE");
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, @Nonnull ItemStack item) {
                int energyConsumption = (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption) {
                    if (player.world.getGameTime() % 20 == 0) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
                    }
                    repulse(player.world, player.getPosition());
                }
            }

            public void repulse(World world, BlockPos playerPos) {
                float distance = 5.0F;
                AxisAlignedBB area = new AxisAlignedBB(
                        playerPos.add(-distance, -distance, -distance),
                        playerPos.add(distance, distance, distance));

                for (Entity entity : world.getEntitiesWithinAABB(MobEntity.class, area)) {
                    push(entity, playerPos);
                }

                for (ArrowEntity entity : world.getEntitiesWithinAABB(ArrowEntity.class, area)) {
                    push(entity, playerPos);
                }

                for (FireballEntity entity : world.getEntitiesWithinAABB(FireballEntity.class, area)) {
                    push(entity, playerPos);
                }

                for (PotionEntity entity : world.getEntitiesWithinAABB(PotionEntity.class, area)) {
                    push(entity, playerPos);
                }
            }

            private void push(Entity entity, BlockPos playerPos) {
                if (!(entity instanceof PlayerEntity) && !(entity instanceof EnderDragonEntity)) {
                    BlockPos distance = playerPos.subtract(entity.getPosition());

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
                        Vector3d motion = entity.getMotion();
                        entity.setMotion(motion.x + d5, motion.y + d6, motion.z + d7);
                    }
                }
            }
        }
    }
}