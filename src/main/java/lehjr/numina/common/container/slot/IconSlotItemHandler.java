package lehjr.numina.common.container.slot;

import net.neoforged.neoforge.items.IItemHandler;

public abstract class IconSlotItemHandler extends HideableSlotItemHandler implements IIConProvider {
    public IconSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition) {
        super(itemHandler, parent, index, xPosition, yPosition);
    }


//    public IconSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition, boolean isEnabled) {
//        super(itemHandler, parent, index, xPosition, yPosition, isEnabled);
//    }
}
