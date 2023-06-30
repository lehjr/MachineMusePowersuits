package net.machinemuse.powersuits.client.gui.common;

import net.machinemuse.numina.client.gui.clickable.ClickableItem;
import net.machinemuse.numina.client.gui.geometry.*;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.client.sound.Musique;
import net.machinemuse.numina.common.item.MuseItemUtils;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.powersuits.client.sound.SoundDictionary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemSelectionFrame<T extends IRect> extends ScrollableFrame {
    public List<ClickableItem> itemButtons;
    protected int selectedItemStack = -1;
    protected EntityPlayer player;
    protected List<MusePoint2D> itemPoints;
    protected int lastItemSlot = -1;

    public ItemSelectionFrame(MusePoint2D topleft, MusePoint2D bottomright, Colour borderColour, Colour insideColour, EntityPlayer player) {
        super((T)new DrawableMuseRect(topleft, bottomright, borderColour, insideColour));
        this.player = player;
        List<Integer> slots = MuseItemUtils.getModularItemSlotsInInventory(player);
        loadPoints(slots.size());
        loadItems();
    }

    public int getLastItemSlot() {
        return lastItemSlot;
    }

    public int getSelectedItemSlot() {
        return selectedItemStack;
    }

    private void loadPoints(int num) {
        double centerX = (border.left() + border.right()) / 2;
        double centery = (border.top() + border.bottom()) / 2;
        itemPoints = new ArrayList();
        List<MusePoint2D> targetPoints = GradientAndArcCalculator.pointsInLine(num,
                new MusePoint2D(centerX, border.bottom()),
                new MusePoint2D(centerX, border.top()));
        for (MusePoint2D point : targetPoints) {
            // Fly from middle over 200 ms
            itemPoints.add(new FlyFromPointToPoint2D(
                    new MusePoint2D(centerX, centery),
                    point, 200));
        }
    }

    private void loadItems() {
        if (player != null) {
            itemButtons = new ArrayList<>();
//            double centerX = (border.left() + border.right()) / 2;
//            double centery = (border.top() + border.bottom()) / 2;
//            List<Integer> slots = MuseItemUtils.getModularItemSlotsEquiped(player);
            List<Integer> slots = MuseItemUtils.getModularItemSlotsInInventory(player);
            if (slots.size() > itemPoints.size()) {
                loadPoints(slots.size());
            }
            if (slots.size() > 0) {
                Iterator<MusePoint2D> pointiterator = itemPoints.iterator();

                for (int slot : slots) {
                    ClickableItem clickie = new ClickableItem(
                            player.inventory.getStackInSlot(slot),
                            pointiterator.next(), slot);
                    itemButtons.add(clickie);
                }
            }
        }
    }

    @Override
    public void update(double mouseX, double mouseY) {
        loadItems();
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        drawBackground(mouseX, mouseY, partialTicks);
        drawItems(mouseX, mouseY, partialTicks);
        drawSelection();
    }

    private void drawBackground(double mouseX, double mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
    }

    private void drawItems(double mouseX, double mouseY, float partialTicks) {
        for (ClickableItem item : itemButtons) {
            item.render(mouseX, mouseY, partialTicks);
        }
    }

    private void drawSelection() {
        if (selectedItemStack != -1) {
            MuseRenderer.drawCircleAround(
                    Math.floor(itemButtons.get(selectedItemStack).getPosition().getX()),
                    Math.floor(itemButtons.get(selectedItemStack).getPosition().getY()),
                    10);
        }
    }

    public boolean hasNoItems() {
        return itemButtons.size() == 0;
    }

    public ClickableItem getPreviousSelectedItem() {
        if (itemButtons.size() > lastItemSlot && lastItemSlot != -1) {
            return itemButtons.get(lastItemSlot);
        } else {
            return null;
        }
    }

    public ClickableItem getSelectedItem() {
        if (itemButtons.size() > selectedItemStack && selectedItemStack != -1) {
            return itemButtons.get(selectedItemStack);
        } else {
            return null;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int i = 0;
        for (ClickableItem item : itemButtons) {
            if (item.containsPoint(mouseX, mouseY)) {
                lastItemSlot = selectedItemStack;
                Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, SoundCategory.BLOCKS, 1, null);
                selectedItemStack = i;
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        int itemHover = -1;
        int i = 0;
        for (ClickableItem item : itemButtons) {
            if (item.containsPoint(mouseX, mouseY)) {
                itemHover = i;
                break;
            } else {
                i++;
            }
        }
        if (itemHover > -1) {
            return itemButtons.get(itemHover).getToolTip(mouseX, mouseY);
        } else {
            return null;
        }
    }
}
