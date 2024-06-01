package lehjr.numina.client.gui.slot;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public abstract class IconSlotItemHandler extends SlotItemHandler implements IIConProvider {
    public IconSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
}