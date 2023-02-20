package lehjr.numina.client.gui.clickable.slider;

import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

public class VanillaTinkerSlider extends VanillaSlider {
    public CompoundNBT moduleTag;

    public VanillaTinkerSlider(MusePoint2D ul,
                                 double width,
                                 CompoundNBT moduleTag,
                                 String id,
                                 TranslationTextComponent label) {
        super(ul, width, id);
        this.moduleTag = moduleTag;
        setValue(getValue());
        setMessage(label);
        setActive(true);
    }

    @Override
    public double getSliderInternalValue() {
        return (moduleTag.contains(this.id())) ? moduleTag.getDouble(id()) : 0;
    }

    @Override
    public double getValue() {
        return (moduleTag.contains(this.id())) ? moduleTag.getDouble(id()) : 0;
    }

    @Override
    public void setValue(double value) {
        super.setValue(value);
    }

    @Override
    public void setValueByMouse(double value) {
        super.setValueByMouse(value);
        moduleTag.putDouble(id(), super.getValue());
    }

    @Override
    public void updateSlider() {
    }
}
