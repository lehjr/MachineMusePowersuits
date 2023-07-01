package net.machinemuse.powersuits.client.gui.module.tinker;

import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.common.item.MuseItemUtils;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.common.nbt.MuseNBTUtils;
import net.machinemuse.powersuits.common.utils.nbt.NBTTagAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Set;

public class StatsFrame extends ScrollableFrame {
    protected NBTTagCompound properties;
    protected ItemStack stack;
    protected Set<String> propertiesToList;

    public StatsFrame(MusePoint2D topleft, MusePoint2D bottomright,
                      Colour borderColour, Colour insideColour, ItemStack stack) {
        super(new DrawableMuseRect(topleft, bottomright, borderColour, insideColour));
        this.stack = stack;
        this.properties = MuseNBTUtils.getMuseItemTag(stack);
        this.propertiesToList = NBTTagAccessor.getMap(properties).keySet();
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        GL11.glPushMatrix();
        super.render(mouseX, mouseY, partialTicks);
        int xoffset = 8;
        int yoffset = 8;
        int i = 0;
        for (String propName : propertiesToList) {
            double propValue = MuseItemUtils.getDoubleOrZero(properties, propName);
            String propValueString = String.format("%.2f", propValue);
            double strlen = MuseRenderer.getStringWidth(propValueString);
            MuseRenderer.drawString(propName, left() + xoffset, top() + yoffset + i * 10);
            MuseRenderer.drawString(propValueString, bottom() - xoffset - strlen - 40, top() + yoffset + i * 10);
            i++;
        }
        GL11.glPopMatrix();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        // TODO Auto-generated method stub
        return null;
    }
}
