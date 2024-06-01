//package lehjr.numina.common.capabilities.inventory;
//
//import net.minecraft.world.item.ItemStack;
//import org.apache.commons.lang3.tuple.Pair;
//import net.neoforged.neoforge.items.IItemHandlerModifiable;
//import net.neoforged.neoforge.items.wrapper.RangedWrapper;
//
//import javax.annotation.Nonnull;
//
//public class NuminaRangedWrapper extends RangedWrapper {
//    private final int minSlot;
//    private final int maxSlot;
//
//    public NuminaRangedWrapper(IItemHandlerModifiable compose, int minSlot, int maxSlotExclusive) {
//        super(compose, minSlot, maxSlotExclusive);
//        this.minSlot = minSlot;
//        this.maxSlot = maxSlotExclusive;
//    }
//    public boolean contains(int slot) {
//        return slot >= this.minSlot && slot < maxSlot;
//    }
//
//    public Pair<Integer, Integer> getRange() {
//        return Pair.of(minSlot, maxSlot);
//    }
//
//    @Override
//    public int getSlots() {
//        return super.getSlots();
//    }
//
//    @Nonnull
//    @Override
//    public ItemStack getStackInSlot(int slot) {
//        return super.getStackInSlot(slot - minSlot);
//    }
//
//    @Nonnull
//    @Override
//    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
//        return super.insertItem(slot - minSlot, stack, simulate);
//    }
//
//    @Nonnull
//    @Override
//    public ItemStack extractItem(int slot, int amount, boolean simulate) {
//        return super.extractItem(slot - minSlot, amount, simulate);
//    }
//
//    @Override
//    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
//        super.setStackInSlot(slot - minSlot, stack);
//    }
//
//    @Override
//    public int getSlotLimit(int slot) {
//        return super.getSlotLimit(slot - minSlot);
//    }
//
//    @Override
//    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
//        return super.isItemValid(slot - minSlot, stack);
//    }
//}
