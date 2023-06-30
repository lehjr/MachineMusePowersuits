package net.machinemuse.powersuits.client.gui.common;

import net.machinemuse.numina.client.gui.clickable.ClickableButton;
import net.machinemuse.numina.client.gui.frame.IGuiFrame;
import net.machinemuse.numina.client.gui.geometry.IRect;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.geometry.MuseRect;
import net.machinemuse.powersuits.common.base.ModularPowersuits;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MachineMuse
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class TabSelectFrame<T extends IRect> implements IGuiFrame {
    EntityPlayer p;
    MusePoint2D topleft;
    MusePoint2D bottomright;
    int worldx;
    int worldy;
    int worldz;
    Map<ClickableButton, Integer> buttons = new HashMap<>();
    List<String> toolTip = new ArrayList<>();
    T border;


    public TabSelectFrame(EntityPlayer p, MusePoint2D topleft, MusePoint2D bottomright, int worldx, int worldy, int worldz) {
        this.p = p;
        this.topleft = topleft;
        this.bottomright = bottomright;
        this.worldx = worldx;
        this.worldy = worldy;
        this.worldz = worldz;

        setRect(new MuseRect(topleft, bottomright));

        this.buttons.put(new ClickableButton(I18n.format("gui.powersuits.tab.tinker"), topleft.midpoint(bottomright).minus(100, 0), worldy < 256 && worldy > 0), 0);
        this.buttons.put(new ClickableButton(I18n.format("gui.powersuits.tab.keybinds"), topleft.midpoint(bottomright), true), 1);
        this.buttons.put(new ClickableButton(I18n.format("gui.powersuits.tab.visual"), topleft.midpoint(bottomright).plus(100, 0), true), 3);
    }

    @Override
    public IRect getRect() {
        return border;
    }

    @Override
    public void setRect(IRect rect) {
        this.border = (T)rect;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ClickableButton b : buttons.keySet()) {
            if (b.isEnabled() && b.containsPoint(mouseX, mouseY)) {
                p.openGui(ModularPowersuits.getInstance(), buttons.get(b), p.world, worldx, worldy, worldz);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        for (ClickableButton b : buttons.keySet())
            b.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        return null;
    }
}
