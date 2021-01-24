package com.github.lehjr.powersuits.item.module.movement;

import com.github.lehjr.numina.client.control.PlayerMovementInputWrapper;
import com.github.lehjr.numina.config.NuminaSettings;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.client.sound.Musique;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.client.sound.MPSSoundDictionary;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.github.lehjr.powersuits.event.MovementManager;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class JetPackModule extends AbstractPowerModule {
    public JetPackModule() {
        super();
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

            this.ticker.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "RF/t");
            this.ticker.addBaseProperty(MPSConstants.JETPACK_THRUST, 0, "N");
            this.ticker.addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 1500);
            this.ticker.addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETPACK_THRUST, 0.16F);
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
            public void onPlayerTickActive(PlayerEntity player, ItemStack torso) {
                if (player.isInWater()) {
                    return;
                }

                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);

                ItemStack helmet = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
                boolean hasFlightControl = helmet.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(m->
                        m instanceof IModularItem && ((IModularItem) m).isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE_REGNAME)).orElse(false);
                double jetEnergy = 0;
                double thrust = 0;
                jetEnergy += applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                thrust += applyPropertyModifiers(MPSConstants.JETPACK_THRUST);

                if (jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) {
                    if (hasFlightControl && thrust > 0) {
                        thrust = MovementManager.INSTANCE.thrust(player, thrust, true);
                        if (player.world.isRemote && NuminaSettings.useSounds()) {
                            Musique.playerSound(player, MPSSoundDictionary.JETPACK, SoundCategory.PLAYERS, (float) (thrust * 6.25), 1.0f, true);
                        }
                        ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy));
                    } else if (playerInput.jumpKey) {//&& player.motionY < 0.5) {
                        thrust = MovementManager.INSTANCE.thrust(player, thrust, false);
                        if (player.world.isRemote && NuminaSettings.useSounds()) {
                            Musique.playerSound(player, MPSSoundDictionary.JETPACK, SoundCategory.PLAYERS, (float) (thrust * 6.25), 1.0f, true);
                        }
                        ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy));
                    } else {
                        if (player.world.isRemote && NuminaSettings.useSounds()) {
                            Musique.stopPlayerSound(player, MPSSoundDictionary.JETPACK);
                        }
                    }
                } else {
                    if (player.world.isRemote && NuminaSettings.useSounds()) {
                        Musique.stopPlayerSound(player, MPSSoundDictionary.JETPACK);
                    }
                }
            }

            @Override
            public void onPlayerTickInactive(PlayerEntity player, ItemStack item) {
                if (player.world.isRemote && NuminaSettings.useSounds()) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.JETPACK);
                }
            }
        }
    }
}