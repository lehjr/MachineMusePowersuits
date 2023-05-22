package lehjr.mpsrecipecreator.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.clickable.Checkbox;
import lehjr.numina.client.gui.clickable.ClickableLabel;
import lehjr.numina.client.gui.clickable.button.VanillaButton;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.sound.Musique;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lehjr
 */
public class RecipeOptionsFrame extends ScrollableFrame {
    protected List<Checkbox> checkBoxes = new ArrayList<>();
    protected List<VanillaButton> buttons = new ArrayList<>();

    final int spacer = 4;
    private Checkbox
            shapeless,
            mirrored,
            conditions;
    private VanillaButton save;
    private VanillaButton reset;
    private ClickableLabel title;
    ConditionsFrame conditionsFrame;

    public RecipeOptionsFrame(MPSRCGui mpsrcGui) {
        super(new Rect(MusePoint2D.ZERO, MusePoint2D.ZERO));
        MusePoint2D starterPoint = MusePoint2D.ZERO;
        this.title = new ClickableLabel(Component.literal("Recipe Options"), starterPoint);
        title.setMode(ClickableLabel.JustifyMode.LEFT);

        shapeless = addCheckBox(new Checkbox(MusePoint2D.ZERO, "Shapeless", false));//ID_SHAPELESS
        mirrored = addCheckBox(new Checkbox(starterPoint, "Mirrored", true));//ID_MIRRORED
        conditions = addCheckBox(new Checkbox(starterPoint, "Conditions", false));//ID_CONDITIONS // fixme... not tied to anything yet

        shapeless.setOnPressed(press->{
            mirrored.setEnabled(!(shapeless.isChecked()));
            if (!mirrored.isEnabled()){
                mirrored.setChecked(false);
            }
        });

        conditions.setOnPressed(press->{
            if (conditions.isChecked()) {
                conditionsFrame.loadConditions();
                conditionsFrame.show();
                conditionsFrame.enable();
            } else {
                conditionsFrame.disable();
                conditionsFrame.hide();
            }
        });

        conditionsFrame = new ConditionsFrame();
        conditionsFrame.disable();
        conditionsFrame.hide();

        save = addButton(Component.translatable("mpsrc.gui.save"));
        save.setOnPressed(pressed->{
            Musique.playClientSound(SoundEvents.UI_BUTTON_CLICK.get(),1);
            mpsrcGui.save();
        });

        reset = addButton(Component.translatable("mpsrc.gui.resetrecipe"));
        reset.setOnPressed(pressed-> {
            Musique.playClientSound(SoundEvents.UI_BUTTON_CLICK.get(), 1);
            mpsrcGui.resetRecipes();
        });
    }

    public boolean isShapeless() {
        return shapeless.isChecked();
    }

    // fixme: should only be enabled if shaped recipe
    public boolean isMirrored() {
        return mirrored.isChecked();
    }

    public ScrollableFrame init() {
        float nextLineRC = 0;
        MusePoint2D genericRecipeCol = new MusePoint2D(left() + 8, top() + 8);
        title.setPosition(genericRecipeCol);
        shapeless.setPosition(genericRecipeCol.plus(0, nextLineRC+=12));
        mirrored.setPosition(genericRecipeCol.plus(0, nextLineRC+=12));
        conditions.setPosition(genericRecipeCol.plus(0, nextLineRC+=12));
        conditionsFrame.setUL(new MusePoint2D(left(), bottom() + 4));
        conditionsFrame.setWH(new MusePoint2D(width(), 51));
        save.setPosition(new MusePoint2D(right(), top()).copy().plus(-(spacer + save.width() * 0.5F), spacer + save.height() * 0.5F));
        reset.setPosition(save.center().plus(0, 24F));
        return this;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            title.render(matrixStack, mouseX, mouseY, partialTicks);

            for (Checkbox checkBox : checkBoxes) {
                checkBox.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            for (VanillaButton button : buttons) {
                button.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            conditionsFrame.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void update(double x, double y) {
        super.update(x, y);
        if (conditionsFrame.isEnabled() && conditionsFrame.isVisible()) {
            conditionsFrame.update(x, y);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isVisible() && isEnabled()) {
            super.mouseClicked(mouseX, mouseY, button);

            for (Checkbox checkBox : checkBoxes) {
                if (checkBox.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }

            for (VanillaButton lbutton : buttons) {
                if (lbutton.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }
            conditionsFrame.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (conditionsFrame.isVisible() && conditionsFrame.mouseScrolled(mouseX, mouseY, dWheel)) {
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, dWheel);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isVisible() && isEnabled()) {
            super.mouseReleased(mouseX, mouseY, button);
            if (conditionsFrame.mouseReleased(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    VanillaButton addButton(Component label) {
        VanillaButton button = new VanillaButton(MusePoint2D.ZERO, label, true);
        button.setWidth(110);
        button.setHeight(20);


        this.buttons.add(button);
        return button;
    }

    Checkbox addCheckBox(Checkbox checkBox) {
        checkBoxes.add(checkBox);
        return checkBox;
    }
}