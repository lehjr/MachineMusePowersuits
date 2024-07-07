package lehjr.powersuits.client.gui.cosmetic.partmanip;

import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.IconUtils;

public class ArmorRadioButton extends RadioButton {
    public ArmorRadioButton(double leftPos, double topPos, Color color) {
        super(IconUtils.getIcon().normalArmor, 8, 8, leftPos, topPos);
    }

    public ArmorRadioButton(MusePoint2D pos, Color color) {
        super(IconUtils.getIcon().normalArmor, new MusePoint2D(8,8), pos);
    }
}