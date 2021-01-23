package com.github.lehjr.powersuits.item.module.movement;

import com.github.lehjr.numina.client.control.PlayerMovementInputWrapper;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.player.PlayerUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class ParachuteModule extends AbstractPowerModule {
    public ParachuteModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.MOVEMENT, EnumModuleTarget.TORSOONLY, MPSSettings::getModuleConfig);
            this.ticker.addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 1000, "FE");
            this.ticker.addTradeoffProperty(MPSConstants.THRUST, MPSConstants.SWIM_BOOST_AMOUNT, 1, "m/s");
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive (PlayerEntity player, ItemStack itemStack){
                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
                boolean hasGlider = false;
                PlayerUtils.resetFloatKickTicks(player);
                if (playerInput.sneakKey && player.getMotion().y < -0.1 && (!hasGlider || playerInput.moveForward <= 0)) {
                    double totalVelocity = Math.sqrt(player.getMotion().x * player.getMotion().x + player.getMotion().z * player.getMotion().z + player.getMotion().y * player.getMotion().y);
                    if (totalVelocity > 0) {
                        Vector3d motion = player.getMotion();
                        player.setMotion(
                                motion.x * 0.1 / totalVelocity,
                                motion.y * 0.1 / totalVelocity,
                                motion.z * 0.1 / totalVelocity);
                    }
                }
            }
        }
    }
}