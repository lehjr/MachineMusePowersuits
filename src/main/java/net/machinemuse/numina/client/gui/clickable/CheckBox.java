package net.machinemuse.numina.client.gui.clickable;

import net.machinemuse.numina.client.gui.geometry.DrawableTile;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.common.math.Colour;

import java.util.List;

public class CheckBox extends Clickable {
    protected boolean isChecked;
    protected DrawableTile tile;

    String label;
    final int id;

    public CheckBox(int id, MusePoint2D position, String displayString, boolean isChecked){
        this.id = id;
        MusePoint2D ul = position.plus(4, 4);
        this.tile = new DrawableTile(ul, ul.plus(8, 8), Colour.BLACK, Colour.DARKGREY);
        this.label = displayString;
        this.isChecked = isChecked;
        tile.setSmoothing(false);
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        tile.draw();
        if (isChecked) {
            MuseRenderer.drawString("x", tile.centerX() - 2, tile.centerY() - 5, Colour.WHITE);
        }
        MuseRenderer.drawString(label, tile.centerX() + 8, tile.centerY() - 4, Colour.WHITE);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean containsPoint(double mouseX, double mouseY) {
        return tile.containsPoint(mouseX, mouseY);
    }

    @Override
    public void move(double x, double y) {
        this.setPosition(getPosition().plus(x, y));
    }

    @Override
    public void setPosition(MusePoint2D position) {
        super.setPosition(position);
        tile.setPosition(position);
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        return null;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }
}
