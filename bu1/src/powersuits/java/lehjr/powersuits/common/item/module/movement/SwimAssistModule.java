package lehjr.powersuits.common.item.module.movement;

import com.lehjr.numina.client.config.NuminaClientConfig;
import com.lehjr.numina.client.control.PlayerMovementInputWrapper;
import com.lehjr.numina.client.sound.Musique;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;

import javax.annotation.Nonnull;

public class SwimAssistModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 1000, "FE");
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.SWIM_BOOST_AMOUNT, 1, "m/s");

        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack itemStack) {
//                if (player.isSwimming()) { // doesn't work when strafing without "swimming"
            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
            if((player.isInWater() && !player.isPassenger()) && (playerInput.strafeLeftKey || playerInput.strafeRightKey || playerInput.forwardKey || playerInput.reverseKey || playerInput.jumpKey || player.isCrouching())) {
                double moveRatio = 0;
                if (playerInput.forwardKey) {
                    moveRatio += 1.0;
                }
                if (playerInput.strafeLeftKey || playerInput.strafeRightKey) {
                    moveRatio += 1.0;
                }
                if (playerInput.jumpKey || player.isCrouching()) {
                    moveRatio += 0.2 * 0.2;
                }
                double swimAssistRate = applyPropertyModifiers(MPSConstants.SWIM_BOOST_AMOUNT) * 0.05 * moveRatio;
                double swimEnergyConsumption = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);

                double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                if (swimEnergyConsumption < playerEnergy) {
                    if (level.isClientSide && NuminaClientConfig.useSounds) {
                        Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_SWIM_ASSIST.get(), SoundSource.PLAYERS, 1.0f, 1.0f, true);
                    } else if (
                        // every 20 ticks
                            (level.getGameTime() % 5) == 0) {
                        ElectricItemUtils.drainPlayerEnergy(player, (int) (swimEnergyConsumption) * 5, false);
                    }
                    MovementManager.thrust(player, swimAssistRate, true);
//                            setMovementModifier(getModule(), swimAssistRate * 100000, ForgeMod.SWIM_SPEED.get(), ForgeMod.SWIM_SPEED.get().getDescriptionId());
                } else {
                    onPlayerTickInactive(player, level, itemStack);
                }
            } else {
                onPlayerTickInactive(player, level, itemStack);
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack itemStack) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_SWIM_ASSIST.get());
            }
            SprintAssistModule.setMovementModifier(getModule(), 0, NeoForgeMod.SWIM_SPEED.value(), NeoForgeMod.SWIM_SPEED.value().getDescriptionId());
        }
    }
}