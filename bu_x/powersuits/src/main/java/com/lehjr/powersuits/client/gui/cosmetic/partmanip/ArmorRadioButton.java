package com.lehjr.powersuits.client.gui.cosmetic.partmanip;

import com.lehjr.numina.client.gui.clickable.RadioButton;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.IconUtils;

public class ArmorRadioButton extends RadioButton {
    public ArmorRadioButton(double leftPos, double topPos, Color color) {
        super(IconUtils.getIcon().normalArmor, 8, 8, leftPos, topPos);
    }

    public ArmorRadioButton(MusePoint2D pos, Color color) {
        super(IconUtils.getIcon().normalArmor, new MusePoint2D(8,8), pos);
    }
}