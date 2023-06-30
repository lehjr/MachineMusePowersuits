package net.machinemuse.numina.client.gui.clickable;

import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.SoundEvents;

import java.util.List;

public interface IClickable {
    void render(double mouseX, double mouseY, float partialTicks);

    void move(double x, double y);

    MusePoint2D getPosition();

    void enable();

    void disable();

    void hide();

    void show();

    default void disableAndHide() {
        disable();
        hide();
    }

    default void enableAndShow() {
        enable();
        show();
    }

    boolean isEnabled();

    boolean isVisible();

    boolean containsPoint(double mouseX, double mouseY);

    List getToolTip(double mouseX, double mouseY);

    default boolean mouseCLicked(double mouseX, double mouseY, int button) {
        return this.containsPoint(mouseX, mouseY) && isEnabled() && isVisible();
    }

    void setOnPressed(IPressable onPressed);

    void setOnReleased(IReleasable onReleased);

    void onPressed();

    void onReleased();

    interface IPressable {
        void onPressed(IClickable doThis);
    }

    interface IReleasable {
        void onReleased(IClickable doThis);
    }

    default boolean mouseClicked(double mouseX, double mouseY, int button) {
        if( this.isEnabled() && this.isVisible() && containsPoint(mouseX, mouseY)) {
            if (button == 0 || button == 1) {
                this.onPressed();
            }
            this.playPressSound(Minecraft.getMinecraft().getSoundHandler());
            return true;
        }
        return false;
    }

    default boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(this.isEnabled() && this.isVisible()) {
            if (button == 0 || button == 1) {
                this.onReleased();
            }
        }
        return false;
    }

    default void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
