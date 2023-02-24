package lehjr.numina.client.gui.clickable;

import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.client.gui.geometry.MusePoint2D;

public abstract class AbstractIconButton extends Clickable {
    GuiIcon.DrawableGuiIcon icon;

    public AbstractIconButton(GuiIcon.DrawableGuiIcon icon, double width, double height, double leftPos, double topPos) {
        this(icon, new MusePoint2D(width, height), new MusePoint2D(leftPos, topPos));
    }

    public AbstractIconButton(GuiIcon.DrawableGuiIcon icon, MusePoint2D wh, MusePoint2D ul) {
        super(ul, wh);
        this.icon = icon;
        super.setWH(wh);
        super.setUL(ul);
    }

    public GuiIcon.DrawableGuiIcon getIcon() {
        return icon;
    }
}