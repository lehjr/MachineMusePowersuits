package lehjr.numina.common.container.slot;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;

public abstract class IconSlot extends Slot implements IIConProvider {
    public IconSlot(SimpleContainer iInventory, int inventoryIndex, int posX, int posY) {
        super(iInventory, inventoryIndex, posX, posY);
    }
}
    

