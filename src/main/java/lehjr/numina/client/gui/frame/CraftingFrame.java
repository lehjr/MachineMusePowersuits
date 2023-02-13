package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.IContainerULOffSet;
import lehjr.numina.client.gui.clickable.ClickableArrow;
import lehjr.numina.client.gui.clickable.IClickable;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.common.math.Colour;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CraftingFrame extends MultiRectHolderFrame {
    protected InventoryFrame craftingGrid, resultFrame;
    protected ClickableArrow arrow;

    public CraftingFrame(Container container, int resultIndex, int craftStartIndex, IContainerULOffSet.ulGetter ulGetter) {
        super(true, true, 0, 0);
        super.setHeight(54);

        /** Crafting grid ------------------------------------------------------------------------ */
        // slot 1-9
        craftingGrid = new InventoryFrame(
                container,
                3,
                3,
                new ArrayList<Integer>(){{
                    IntStream.range(craftStartIndex, craftStartIndex+9).forEach(i-> add(i));
                }}, ulGetter);
        addRect(craftingGrid);

        /** Arrow and its holder ----------------------------------------------------------------- */
        arrow = new ClickableArrow(0, 0, 0, 0, false, Colour.DARK_GREY, Colour.WHITE, Colour.BLACK);
        arrow.show();
        arrow.setDrawBorer(false);
        arrow.setWidth(24).setHeight(24);


        addRect(new RectHolderFrame(arrow, 36, 54) {
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return arrow.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                super.render(matrixStack, mouseX, mouseY, frameTime);
                arrow.render(matrixStack, mouseX, mouseY, frameTime);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return arrow.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public void update(double mouseX, double mouseY) {
                MusePoint2D position = center();
                if (!position.equals(arrow.center())) {
                    arrow.setPosition(position);
//                    arrow.initGrowth();
                }
            }

            @Override
            public List<ITextComponent> getToolTip(int x, int y) {
                return arrow.getToolTip(x, y);
            }
        });

        /** Result slot and its spacer ----------------------------------------------------------- */
        // slot 0
        resultFrame = new InventoryFrame(
                container,
                1,
                1,
                new ArrayList<Integer>(){{
                    IntStream.range(resultIndex, resultIndex+1).forEach(i-> add(i));
                }}, ulGetter).setSlotWidth(24).setSlotHeight(24);

        addRect(new RectHolderFrame(resultFrame,24, 54) {
            @Override
            public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                super.render(matrixStack, mouseX, mouseY, frameTime);
                resultFrame.render(matrixStack, mouseX, mouseY, frameTime);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return resultFrame.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return resultFrame.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public void update(double mouseX, double mouseY) {
                MusePoint2D position = center();
                if (!position.equals(resultFrame.center())) {
                    resultFrame.setPosition(position);
//                    resultFrame.initGrowth();
                }
            }

            @Override
            public List<ITextComponent> getToolTip(int x, int y) {
                return resultFrame.getToolTip(x, y);
            }
        });
        doneAdding();
    }

    public void setArrowOnPressed(IClickable.IPressable onPressed){
        arrow.setOnPressed(onPressed);
    }
}
