package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class ClimbAssistModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, ItemStack item) {
////                ForgeMod#STEP_HEIGHT
//                player.getAttribute()
//                player.maxUpStep().setMaxUpStep().maxUpStep = 1.001F;
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, ItemStack item) {
//                if (player.maxUpStep == 1.001F) {
//                    player.maxUpStep = 0.5001F;
//                }
        }
    }
}
