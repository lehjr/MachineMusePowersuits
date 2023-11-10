package lehjr.numina.common.entity;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IMixinPlayer {
    private Player otherSelf() {
        return (Player) this;
    }
    /**
     * Returns the actual ItemStack that getItemBySlot would return
     * @param slot
     * @return
     */
    default ItemStack getActualItemBySlot(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return otherSelf().getInventory().getSelected();
        } else if (slot == EquipmentSlot.OFFHAND) {
            return otherSelf().getInventory().offhand.get(0);
        } else {
            return slot.getType() == EquipmentSlot.Type.ARMOR ? otherSelf().getInventory().armor.get(slot.getIndex()) : ItemStack.EMPTY;
        }
    }
}
