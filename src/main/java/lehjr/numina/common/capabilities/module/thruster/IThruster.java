package lehjr.numina.common.capabilities.module.thruster;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IThruster extends IPowerModule {

    double getDesiredThrust();

    void playSound(Player player, Level level, double percentDesiredThrust);

    void stopSound(Player player, Level level);
}
