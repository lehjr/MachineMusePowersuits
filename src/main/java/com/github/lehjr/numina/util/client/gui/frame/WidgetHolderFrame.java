package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.WidgetWrapper;
import net.minecraft.client.gui.widget.Widget;

public abstract class WidgetHolderFrame extends RectHolderFrame {
    public WidgetHolderFrame(Widget widget, double widthIn, double heightIn) {
        this(new WidgetWrapper(widget), widthIn, heightIn);
    }

    public WidgetHolderFrame(WidgetWrapper wrapper, double widthIn, double heightIn) {
        super(wrapper, widthIn, heightIn);
    }

    public WidgetHolderFrame(Widget widget, double widthIn, double heightIn, RectPlacement placement) {
        this(new WidgetWrapper(widget), widthIn, heightIn, placement);
    }

    public WidgetHolderFrame(WidgetWrapper wrapper, double widthIn, double heightIn, RectPlacement placement) {
        super(wrapper, widthIn, heightIn, placement);
    }

    public Widget getWidget() {
        return ((WidgetWrapper)rect).getWidget();
    }

    @Override
    public RelativeRect getRect() {
        return super.getRect();
    }
}
