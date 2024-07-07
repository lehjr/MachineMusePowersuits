package lehjr.numina.common.capability.render.modelspec;

import net.minecraft.world.entity.EquipmentSlot;

import javax.annotation.Nullable;

public class SpecBinding {
    private final MorphTarget target;
    private final EquipmentSlot slot;
    private final String itemStateString;

    public SpecBinding(@Nullable MorphTarget target, @Nullable EquipmentSlot slot, @Nullable String itemState) {
        this.target = target;
        this.slot = slot;
        this.itemStateString = (itemState != null || !itemState.isEmpty()) ? itemState : "all";
    }

    public SpecBinding(EquipmentSlot slot, @Nullable String itemState) {
        this.target = null;
        this.slot = slot;
        this.itemStateString = (itemState != null || !itemState.isEmpty()) ? itemState : "all";
    }

    @Nullable
    public MorphTarget getTarget() {
        return target;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public String getItemState() {
        return itemStateString;
    }
}