package net.machinemuse.powersuits.client.gui.cosmetic;

import net.machinemuse.numina.client.gui.clickable.ClickableLabel;
import net.machinemuse.numina.client.gui.clickable.ClickableSlider;
import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.geometry.IRect;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.geometry.MuseRect;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.client.gui.scrollable.ScrollableLabel;
import net.machinemuse.numina.client.gui.scrollable.ScrollableRectangle;
import net.machinemuse.numina.client.gui.scrollable.ScrollableSlider;
import net.machinemuse.numina.common.base.MuseLogger;
import net.machinemuse.numina.common.constants.ModelSpecTags;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.powersuits.client.gui.GuiIcons;
import net.machinemuse.powersuits.client.gui.common.ItemSelectionFrame;
import net.machinemuse.powersuits.common.constants.MPSModuleConstants;
import net.machinemuse.powersuits.common.item.armor.ItemPowerArmor;
import net.machinemuse.powersuits.common.network.MPSPackets;
import net.machinemuse.powersuits.common.network.packets.MusePacketColourInfo;
import net.machinemuse.powersuits.common.utils.nbt.MPSNBTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:19 AM, 03/05/13
 * <p>
 * Ported to Java by lehjr on 11/2/16.
 */

// TODO: create base class and make this extend it for cosmetic preset friendly setup
public class ColourPickerFrame<T extends IRect> extends ScrollableFrame {
    public ItemSelectionFrame itemSelector;
    public ScrollableSlider rslider;
    public ScrollableSlider gslider;
    public ScrollableSlider bslider;
    public ScrollableSlider aslider;
    ScrollableColourBox colourBox;
    String COLOUR_PREFIX = I18n.format("gui.powersuits.colourPrefix");
    public ScrollableLabel colourLabel;
    public ScrollableSlider selectedSlider;
    public int selectedColour;
    public int decrAbove;
    ScrollableRectangle[] rectangles;

    public ColourPickerFrame(MusePoint2D topleft, MusePoint2D bottomright, Colour borderColour, Colour insideColour, ItemSelectionFrame itemSelector) {
        super(new DrawableMuseRect(topleft, bottomright, borderColour, insideColour));
        this.itemSelector = itemSelector;
        this.rectangles = new ScrollableRectangle[6];
        this.totalSize = 120;

        // sliders boxes 0-3
        this.rslider = getScrollableSlider("red", null, 0);
        this.gslider = getScrollableSlider("green", rslider, 1);
        this.bslider = getScrollableSlider("blue", gslider, 2);
        this.aslider = getScrollableSlider("alpha", bslider, 3);

        // box 4 is for the color icon stuff
        this.colourBox = new ScrollableColourBox(new MuseRect(left(), this.aslider.bottom(), right(), this.aslider.bottom()+ 25));
        this.colourBox.setBelow(aslider);
        rectangles[4] = colourBox;

        // box 5 is the label
        MuseRect colourLabelBox = new MuseRect(left(), this.colourBox.bottom(), right(), this.colourBox.bottom() + 20);
        this.colourLabel = new ScrollableLabel(
                new ClickableLabel(COLOUR_PREFIX, new MusePoint2D(colourLabelBox.centerX(), colourLabelBox.centerY())), colourLabelBox);

        colourLabel.setBelow(colourBox);
        rectangles[5] = this.colourLabel;

        this.selectedSlider = null;
        this.selectedColour = 0;
        this.decrAbove = -1;
        this.enable();
    }

    public ScrollableSlider getScrollableSlider(String id, ScrollableRectangle prev, int index) {
        MuseRect newborder = new MuseRect(left(), prev != null ? prev.bottom() : top(), right(), (prev != null ? prev.bottom() : top()) + 18);
        ClickableSlider slider = new ClickableSlider(new MusePoint2D(newborder.centerX(),
                newborder.centerY()),
                newborder.width() - 15, id,
                I18n.format(MPSModuleConstants.MODULE_TRADEOFF_PREFIX + id));
        ScrollableSlider scrollableSlider = new ScrollableSlider(slider, newborder);
        scrollableSlider.setBelow((prev != null) ? prev : null);
        rectangles[index] = scrollableSlider;
        return scrollableSlider;
    }

    public int[] colours() {
        return (getOrCreateColourTag() != null) ? getOrCreateColourTag().getIntArray() : new int[0];
    }

    public NBTTagIntArray getOrCreateColourTag() {
        if (this.itemSelector.getSelectedItem() == null) {
            return null;
        }

        NBTTagCompound renderSpec = MPSNBTUtils.getMuseRenderTag(this.itemSelector.getSelectedItem().getItem());
        if (renderSpec.hasKey(ModelSpecTags.TAG_COLOURS) && renderSpec.getTag(ModelSpecTags.TAG_COLOURS) instanceof NBTTagIntArray) {
            return (NBTTagIntArray) renderSpec.getTag(ModelSpecTags.TAG_COLOURS);
        } else {
            Item item = this.itemSelector.getSelectedItem().getItem().getItem();
            if (item instanceof ItemPowerArmor) {
                ItemPowerArmor itemPowerArmor = (ItemPowerArmor) item;
                int[] intArray = {itemPowerArmor.getColorFromItemStack(this.itemSelector.getSelectedItem().getItem()).getInt()};
                renderSpec.setIntArray(ModelSpecTags.TAG_COLOURS, intArray);
            } else {
                int[] intArray2 = new int[]{Colour.WHITE.getInt()};
                renderSpec.setIntArray(ModelSpecTags.TAG_COLOURS, intArray2);
            }
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player.world.isRemote) {
                MPSPackets.sendToServer(new MusePacketColourInfo(player, this.itemSelector.getSelectedItem().inventorySlot, this.colours()));
            }
            return (NBTTagIntArray) renderSpec.getTag(ModelSpecTags.TAG_COLOURS);
        }
    }

    public NBTTagIntArray setColourTagMaybe(List<Integer> intList) {
        if (this.itemSelector.getSelectedItem() == null) {
            return null;
        }
        NBTTagCompound renderSpec = MPSNBTUtils.getMuseRenderTag(this.itemSelector.getSelectedItem().getItem());
        renderSpec.setTag(ModelSpecTags.TAG_COLOURS, new NBTTagIntArray(intList));
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player.world.isRemote) {
            MPSPackets.sendToServer(new MusePacketColourInfo(player, this.itemSelector.getSelectedItem().inventorySlot, this.colours()));
        }
        return (NBTTagIntArray) renderSpec.getTag(ModelSpecTags.TAG_COLOURS);
    }

    public ArrayList<Integer> importColours() {
        return new ArrayList<Integer>();
    }

    public void refreshColours() {
        //    getOrCreateColourTag.map(coloursTag => {
        //      val colourints: Array[Int] = coloursTag.intArray
        //      val colourset: HashSet[Int] = HashSet.empty ++ colours ++ colourints
        //      val colourarray = colourset.toArray
        //      coloursTag.intArray = colourarray
        //    })
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isEnabled())
            this.selectedSlider = null;
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        if (this.isEnabled()) {
            if (this.selectedSlider != null) {
                this.selectedSlider.getSlider().setValueByX(mouseX);
                if (colours().length > selectedColour) {
                    colours()[selectedColour] = Colour.getInt(rslider.getValue(), gslider.getValue(), bslider.getValue(), aslider.getValue());

                    EntityPlayerSP player = Minecraft.getMinecraft().player;
                    if (player.world.isRemote)
                        MPSPackets.sendToServer(new MusePacketColourInfo(player, itemSelector.getSelectedItem().inventorySlot, colours()));
                }
                // this just sets up the sliders on selecting an item
            } else if (itemSelector.getSelectedItem() != null && colours().length > 0) {
                if (selectedColour <= colours().length -1)
                    onSelectColour(selectedColour);
            }
        }
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        if (this.isVisible()) {
            setCurrentScrollPixels(Math.min(getCurrentScrollPixels(), getMaxScrollPixels()));

            if (colours().length > selectedColour) {
                colourLabel.setText(COLOUR_PREFIX + " 0X" + new Colour(colours()[selectedColour]).hexColour());
            }

            super.preRender(mouseX, mouseY, partialTicks);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0, - getCurrentScrollPixels(), 0);
            for (ScrollableRectangle f : rectangles) {
                f.render(mouseX, mouseY, partialTicks);
            }
            GlStateManager.popMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        return null;
    }

    public void onSelectColour(int i) {
        Colour c = new Colour(this.colours()[i]);
        this.rslider.setValue(c.r);
        this.gslider.setValue(c.g);
        this.bslider.setValue(c.b);
        this.aslider.setValue(c.a);
        this.selectedColour = i;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean retVal = false;
        if (this.isEnabled()) {
            mouseY = mouseY + getCurrentScrollPixels();
            if (this.rslider.hitBox(mouseX, mouseY)) {
                this.selectedSlider = this.rslider;
                retVal = true;
            } else if (this.gslider.hitBox(mouseX, mouseY)) {
                this.selectedSlider = this.gslider;
                retVal = true;
            } else if (this.bslider.hitBox(mouseX, mouseY)) {
                retVal = true;
                this.selectedSlider = this.bslider;
            } else if (this.aslider.hitBox(mouseX, mouseY)) {
                this.selectedSlider = this.aslider;
                retVal = true;
            } else
                this.selectedSlider = null;

            colourBox.addColour(mouseX, mouseY);
            colourBox.removeColour(mouseX, mouseY);

            if (colourLabel.hitbox(mouseX, mouseY) && colours().length > selectedColour) {
                // todo: insert chat to player...
//            System.out.println("copying to clipboard: " + "0x" + new Colour(selectedColour).hexColour());
                StringSelection selection = new StringSelection(new Colour(selectedColour).hexColour());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        }
        return retVal;
    }

    public int[] getIntArray(NBTTagIntArray e) {
        if (e == null) // null when no armor item selected
            return new int[0];
        return e.getIntArray();
    }

    class ScrollableColourBox extends ScrollableRectangle {
        public ScrollableColourBox(MuseRect relativeRect) {
            super(relativeRect);
        }

        boolean addColour(double x, double y) {
            if (y > this.centerY() + 8.5 && y < this.centerY() + 16.5 ) {
                int colourCol = (int) (x - left() - 8.0) / 8;
                if (colourCol >= 0 && colourCol < colours().length) {
                    onSelectColour(colourCol);
                } else if (colourCol == colours().length) {
                    MuseLogger.logDebug("Adding");
                    List<Integer> intList = Arrays.stream(getIntArray(getOrCreateColourTag())).boxed().collect(Collectors.toList());
                    intList.add(Colour.WHITE.getInt());
                    setColourTagMaybe(intList);
                }
                return true;
            }
            return false;
        }

        boolean removeColour(double x, double y) {
            if (y > this.centerY() + 0.5 && y < this.centerY() + 8.5 && x > left() + 8 + selectedColour * 8 && x < left() + 16 + selectedColour * 8) {
                NBTTagIntArray nbtTagIntArray = getOrCreateColourTag();
                List<Integer> intList = Arrays.stream(getIntArray(nbtTagIntArray)).boxed().collect(Collectors.toList());

                if (intList.size() > 1 && selectedColour <= intList.size() -1) {
                    intList.remove(selectedColour); // with integer list, will default to index rather than getValue

                    setColourTagMaybe(intList);

                    decrAbove = selectedColour;
                    if (selectedColour == getIntArray(nbtTagIntArray).length) {
                        selectedColour = selectedColour - 1;
                    }

                    EntityPlayerSP player = Minecraft.getMinecraft().player;
                    if (player.world.isRemote)
                        MPSPackets.sendToServer(new MusePacketColourInfo(player, itemSelector.getSelectedItem().inventorySlot, nbtTagIntArray.getIntArray()));
                }
                return true;
            }
            return false;
        }

        public void render(double mouseX, double mouseY, float partialTicks) {
            // colours
            for (int i=0; i < colours().length; i++) {
                new GuiIcons.ArmourColourPatch(this.left() + 8 + i * 8, this.centerY() + 8 , new Colour(colours()[i]), null, null, null, null);
            }

            new GuiIcons.ArmourColourPatch(this.left() + 8 + colours().length * 8, this.centerY() + 8, Colour.WHITE, null, null, null, null);
            new GuiIcons.SelectedArmorOverlay(this.left() + 8 + selectedColour * 8, this.centerY() + 8, Colour.WHITE, null, null, null, null);
            new GuiIcons.MinusSign(this.left() + 8 + selectedColour * 8, this.centerY(), Colour.RED, null, null, null, null);
            new GuiIcons.PlusSign(this.left() + 8 + colours().length * 8, this.centerY() + 8, Colour.GREEN, null, null, null, null);
        }
    }
}
