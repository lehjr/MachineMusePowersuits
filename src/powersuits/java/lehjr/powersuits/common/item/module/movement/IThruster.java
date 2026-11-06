package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.control.PlayerMovementInputWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IThruster {

    double getDesiredThrust();

    void playSound(Player player, Level level, double percentDesiredThrust);

    void stopSound(Player player, Level level);
}
