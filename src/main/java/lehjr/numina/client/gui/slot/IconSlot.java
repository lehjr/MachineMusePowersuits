package lehjr.numina.client.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public abstract class IconSlot extends Slot implements IIConProvider {
    public IconSlot(IInventory iInventory, int inventoryIndex, int posX, int posY) {
        super(iInventory, inventoryIndex, posX, posY);
    }


}
    

