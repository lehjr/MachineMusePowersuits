//package lehjr.numina.client.gui.gemoetry;
//
//import net.minecraft.client.gui.widget.Widget;
//
//import javax.annotation.Nonnull;
//
///**
// * A wrapper for vanilla widgets used for positioning
// * @param <T>
// */
//public class WidgetWrapper<T extends Widget> extends Rect {
//    T widget;
//
//    public WidgetWrapper(@Nonnull T widget) {
//        super(MusePoint2D.ZERO, MusePoint2D.ZERO);
//        this.widget = widget;
//        super.setWidth(widget.getWidth());
//        super.setHeight(widget.getHeight());
//    }
//
//    public T getWidget() {
//        return widget;
//    }
//
//    void setWidgetPosition() {
//        widget.x = (int) left();
//        widget.y = (int) top();
//    }
//
//    void setWidgetWidthHeight() {
//        widget.setWidth((int) width());
//        widget.setHeight((int) height());
//    }
//
//    @Override
//    public Rect setUL(MusePoint2D ul) {
//        super.setUL(ul);
//        setWidgetPosition();
//        return this;
//    }
//
//    @Override
//    public Rect setWH(MusePoint2D wh) {
//        super.setWH(wh);
//        setWidgetWidthHeight();
//        return this;
//    }
//
//    @Override
//    public Rect setLeft(double value) {
//        return super.setLeft(value);
//    }
//
//    @Override
//    public Rect setRight(double value) {
//        return super.setRight(value);
//    }
//
//    @Override
//    public Rect setTop(double value) {
//        return super.setTop(value);
//    }
//
//    @Override
//    public Rect setBottom(double value) {
//        return super.setBottom(value);
//    }
//
//    @Override
//    public Rect setWidth(double value) {
//        return super.setWidth(value);
//    }
//
//    @Override
//    public Rect setHeight(double value) {
//        return super.setHeight(value);
//    }
//
//    @Override
//    public void moveBy(MusePoint2D amount) {
//        super.moveBy(amount);
//        setWidgetPosition();
//    }
//
//    @Override
//    public void moveBy(double x, double y) {
//        super.moveBy(x, y);
//        setWidgetPosition();
//    }
//
//    @Override
//    public void setPosition(MusePoint2D positionIn) {
//        super.setPosition(positionIn);
//        setWidgetPosition();
//    }
//
//    @Override
//    public Rect setLeftOf(IRect otherRightOfMe) {
//        super.setLeftOf(otherRightOfMe);
//        setWidgetPosition();
//        return this;
//    }
//
//    @Override
//    public Rect setRightOf(IRect otherLeftOfMe) {
//        super.setRightOf(otherLeftOfMe);
//        setWidgetPosition();
//        return this;
//    }
//
//    @Override
//    public Rect setAbove(IRect otherBelowMe) {
//        super.setAbove(otherBelowMe);
//        setWidgetPosition();
//        return this;
//    }
//
//    @Override
//    public Rect setBelow(IRect otherAboveMe) {
//        super.setBelow(otherAboveMe);
//        setWidgetPosition();
//        return this;
//    }
//}
