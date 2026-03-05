package lehjr.powersuits.common.item.module.energygeneration.kinetic;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class KineticGeneratorModule extends AbstractPowerModule {
    public static class KineticGeneratorTickingCapability extends PlayerTickModule {

        public KineticGeneratorTickingCapability(ItemStack module) {
            super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.LEGSONLY);
            //                addBaseProperty(MPSConstants.ENERGY_GENERATION, 2000);
            //                addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 6000, "FE");
            //                addBaseProperty(MPSConstants.MOVEMENT_RESISTANCE, 0.01F);
            //                addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.MOVEMENT_RESISTANCE, 0.49F, "%");
        }

        @Override
        public boolean isAllowed() {
            return super.isAllowed();
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @NotNull ItemStack item) {
            super.onPlayerTickActive(player, level, item);
            if (player.getAbilities().flying || player.isPassenger() || player.isFallFlying() || !player.onGround())
                onPlayerTickInactive(player, level, item);

            // really hate running this check on every tick but needed for player speed adjustments
            if (ElectricItemUtils.getPlayerEnergy(player) < ElectricItemUtils.getMaxPlayerEnergy(player)) {
                // server side
                if (!player.level().isClientSide &&
                    // every 20 ticks
                    (player.level().getGameTime() % 20) == 0 &&
                    // player not jumping, flying, or riding
                    player.onGround()) {
                    double distance = player.walkDist - player.walkDistO;
                    ElectricItemUtils.givePlayerEnergy(player, (int) (distance * 20 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)), false);
                }
            }
        }
    }
}
