package com.github.lehjr.powersuits.dev.crafting.client.gui.recipebooktest;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.slot.IHideableSlot;
import com.github.lehjr.numina.util.client.gui.slot.UniversalSlot;
import com.github.lehjr.numina.util.math.Colour;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Range;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.*;
import java.util.stream.IntStream;

public class TestFrame extends ScrollableFrame implements IContainerULOffSet {
    /**
     * Starting with the Inventory frame....
     * make this actually scrollable... use the new slider on the side
     * slots should only be rendered if they completely fit in the window
     * should this frame be reused or should it be hidden when switching to new modular Item?
     * just reuse the frame... otherwise it's a mess of hiding 5 frames while the 6th is active
     *
     * Likely only used in the
     *
     *
     */







    ulGetter ulGetter;
    Container container;
    Colour gridColour;
    public final int gridWidth;
    public final int gridHeight;
    List<Integer> slotIndexes;
    List<DrawableTile> tiles;
    MusePoint2D slot_ulShift;
    boolean drawBackground;
    boolean drawBorder;
    int slotWidth;
    int slotHeight;

    public TestFrame(Container containerIn, int gridWidth, int gridHeight, List<Integer> slotIndexesIn, ulGetter ulGetter) {
        this(containerIn, Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY, gridWidth, gridHeight, slotIndexesIn, ulGetter);
    }

    public TestFrame(Container containerIn, Colour backgroundColour, Colour borderColour, Colour gridColourIn, int gridWidth, int gridHeight, List<Integer> slotIndexesIn, ulGetter ulGetter) {
        super(backgroundColour, borderColour);

        Map<EquipmentSlotType, Pair<Integer, Integer>> equipmentSlotRangeMap = new HashMap();
        HashBiMap<EquipmentSlotType, Slot> equipmentSlotTypeMap = HashBiMap.create();



//        IntStream.range(0, 10).forEach(n -> System.out.println("stream test: " + n));
      /*

        would like to be able to translate container slot to equipmentslot and back .. bimap?

        Data needed:
        ---------------------------------
        we can get the itemstack from the equipment slot...
            then the capability from there...
               just need the indices







       */



        this.slot_ulShift = new MusePoint2D(0.0D, 0.0D);
        this.drawBackground = false;
        this.drawBorder = false;
        this.slotWidth = 18;
        this.slotHeight = 18;
        this.ulGetter = ulGetter;
        this.container = containerIn;
        this.gridColour = gridColourIn;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.slotIndexes = slotIndexesIn;
        this.tiles = new ArrayList();
        super.setWidth((double)(this.slotWidth * gridWidth)).setHeight((double)(this.slotHeight * gridHeight));
        this.setUL(new MusePoint2D(0.0D, 0.0D));
    }

    public DrawableRelativeRect setBackgroundColour(Colour backgroundColour) {
        super.setBackgroundColour(backgroundColour);
        return this;
    }

    public DrawableRelativeRect setBorderColour(Colour borderColour) {
        super.setBorderColour(borderColour);
        return null;
    }

    public DrawableRelativeRect setGridColour(Colour gridColour) {
        this.gridColour = gridColour;
        return this;
    }

    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    public void setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
    }

    public void loadSlots() {
        this.slot_ulShift = this.getULShift(this);
        MusePoint2D ul = new MusePoint2D(this.finalLeft(), this.finalTop());
        this.tiles = new ArrayList();
        int i = 0;

        for(int row = 0; row < this.gridHeight; ++row) {
            for(int col = 0; col < this.gridWidth; ++col) {
                if (i == this.slotIndexes.size()) {
                    return;
                }

                this.tiles.add((new DrawableTile(ul, ul.plus(new MusePoint2D((double)this.slotWidth, (double)this.slotHeight)))).setBorderShrinkValue(0.5F));
                if (i > 0) {
                    if (col > 0) {
                        ((DrawableTile)this.tiles.get(i)).setMeRightOf((IRect)this.tiles.get(i - 1));
                    }

                    if (row > 0) {
                        ((DrawableTile)this.tiles.get(i)).setMeBelow((IRect)this.tiles.get(i - this.gridWidth));
                    }
                }

                MusePoint2D position = (new MusePoint2D(((DrawableTile)this.tiles.get(i)).finalLeft(), ((DrawableTile)this.tiles.get(i)).finalTop())).minus(this.slot_ulShift);
                Slot slot = this.container.getSlot((Integer)this.slotIndexes.get(i));
                if (slot instanceof UniversalSlot) {
                    ((UniversalSlot)slot).setPosition(position);
                } else if (slot instanceof IHideableSlot) {
                    ((IHideableSlot)slot).setPosition(position);
                } else {
                    slot.x = (int)position.getX();
                    slot.y = (int)position.getY();
                }

                ++i;
            }
        }

    }

    public TestFrame setSlotWidth(int slotWidthIn) {
        this.slotWidth = slotWidthIn;
        this.setWH(new MusePoint2D((double)this.slotWidth, (double)this.slotHeight));
        return this;
    }

    public TestFrame setSlotHeight(int slotHeightIn) {
        this.slotHeight = slotHeightIn;
        this.setWH(new MusePoint2D((double)this.slotWidth, (double)this.slotHeight));
        return this;
    }

    public int getSlotWidth() {
        return this.slotWidth;
    }

    public int getSlotHeight() {
        return this.gridHeight;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int buton) {
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    public void initGrowth() {
        super.initGrowth();
        this.loadSlots();
    }

    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        this.loadSlots();
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(0);
        if (this.drawBorder || this.drawBackground) {
            buffer = this.getVertices(3.0D);
        }

        if (this.drawBackground) {
            this.drawBackground(matrixStack, buffer);
        }

        if (this.tiles != null && !this.tiles.isEmpty()) {
            Iterator var6 = this.tiles.iterator();

            while(var6.hasNext()) {
                DrawableTile tile = (DrawableTile)var6.next();
                tile.render(matrixStack, mouseX, mouseY, frameTime);
            }
        }

        if (this.drawBorder) {
            this.drawBorder(matrixStack, buffer);
        }

    }

    public List<ITextComponent> getToolTip(int i, int i1) {
        return null;
    }

    public void setULGetter(ulGetter ulGetter) {
        this.ulGetter = ulGetter;
    }

    public MusePoint2D getULShift(IContainerULOffSet frame) {
        int offset = 16;
        return this.ulGetter == null ? (new MusePoint2D(0.0D, 0.0D)).plus((double)(offset - this.slotWidth) * 0.5D, (double)(offset - this.slotHeight) * 0.5D) : this.ulGetter.getULShift(frame).plus((double)(offset - this.slotWidth) * 0.5D, (double)(offset - this.slotHeight) * 0.5D);
    }
}