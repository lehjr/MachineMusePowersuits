package com.github.lehjr.powersuits.item.module.special;

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
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Callable;

public class MagnetModule extends AbstractPowerModule {
    public MagnetModule() {
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
            this.ticker = new Ticker(module, EnumModuleCategory.SPECIAL, EnumModuleTarget.TORSOONLY, MPSSettings::getModuleConfig);
            this.ticker.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "FE");
            this.ticker.addTradeoffProperty(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, 2000);
            this.ticker.addBaseProperty(MPSConstants.RADIUS, 5);
            this.ticker.addTradeoffProperty(MPSConstants.POWER, MPSConstants.RADIUS, 10);
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
            public void onPlayerTickActive(PlayerEntity player, ItemStack stack) {
                int energyUSage = (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);

                if (ElectricItemUtils.getPlayerEnergy(player) > energyUSage) {
                    boolean isServerSide = !player.world.isRemote;

                    if ((player.world.getGameTime() % 20) == 0 && isServerSide) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyUSage);
                    }
                    int range = (int) applyPropertyModifiers(MPSConstants.RADIUS);
                    World world = player.world;
                    AxisAlignedBB bounds = player.getBoundingBox().grow(range);

                    if (isServerSide) {
                        bounds.expand(0.2000000029802322D, 0.2000000029802322D, 0.2000000029802322D);
                        if (stack.getDamage() >> 1 >= 7) {
                            List<ArrowEntity> arrows = world.getEntitiesWithinAABB(ArrowEntity.class, bounds);
                            for (ArrowEntity arrow : arrows) {
                                if ((arrow.pickupStatus == ArrowEntity.PickupStatus.ALLOWED) && (world.rand.nextInt(6) == 0)) {
                                    ItemEntity replacement = new ItemEntity(world, arrow.getPosX(), arrow.getPosY(), arrow.getPosZ(), new ItemStack(Items.ARROW));
                                    world.addEntity(replacement);
                                }
                                arrow.remove();
                            }
                        }
                    }

                    for (ItemEntity e : world.getEntitiesWithinAABB(ItemEntity.class, bounds)) {
                        if (e.isAlive() && !e.getItem().isEmpty() && !e.cannotPickup()) {
                            if (isServerSide) {
                                double x = player.getPosX() - e.getPosX();
                                double y = player.getPosY() - e.getPosY();
                                double z = player.getPosZ() - e.getPosZ();

                                double length = Math.sqrt(x * x + y * y + z * z) * 0.75D;

                                x = x / length + player.getMotion().x * 22.0D;
                                y = y / length + player.getMotion().y / 22.0D;
                                z = z / length + player.getMotion().z * 22.0D;

                                e.setMotion(x, y, z);

                                e.isAirBorne = true;
                                if (e.collidedHorizontally) {
                                    e.setMotion(e.getMotion().add(0, 1, 0));
                                }
                            } else if (world.rand.nextInt(20) == 0) {
                                float pitch = 0.85F - world.rand.nextFloat() * 3.0F / 10.0F;
                                world.playSound(e.getPosX(), e.getPosY(), e.getPosZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.6F, pitch, true);
                            }
                        }
                    }
                }
            }
        }
    }
}