package lehjr.numina.client.gui.clickable;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScrollBar extends Widget {
    /** The location of the creative inventory tabs texture */
    private static final ResourceLocation CREATIVE_TABS_LOCATION = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    public ScrollBar(int posX, int posY, int width, int height, ITextComponent message) {
        super(posX, posY, width, height, message);
    }
}
