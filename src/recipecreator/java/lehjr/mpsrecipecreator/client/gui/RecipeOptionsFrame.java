//package lehjr.mpsrecipecreator.client.gui;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import lehjr.numina.client.gui.clickable.Checkbox;
//import lehjr.numina.client.gui.clickable.ClickableButton;
//import lehjr.numina.client.gui.clickable.ClickableLabel;
//import lehjr.numina.client.gui.frame.ScrollableFrame;
//import lehjr.numina.client.gui.gemoetry.MusePoint2D;
//import lehjr.numina.client.sound.Musique;
//import lehjr.numina.common.math.Colour;
//import net.minecraft.util.SoundEvents;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.network.chat.TranslatableComponent;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author lehjr
// */
//public class RecipeOptionsFrame extends ScrollableFrame {
//    protected List<Checkbox> checkBoxes = new ArrayList<>();
//    protected List<ClickableButton> buttons = new ArrayList<>();
//
//    final int spacer = 4;
//    private Checkbox
//            shapeless,
//            mirrored,
//            conditions;
//    private ClickableButton save;
//    private ClickableButton reset;
//    private ClickableLabel title;
//    ConditionsFrame conditionsFrame;
//
//    public RecipeOptionsFrame(
//            Colour backgroundColour,
//            Colour conditionsBorder,
//            MPARCGui mparcGui) {
//        super();
//        setBackgroundColour(backgroundColour);
//
//        MusePoint2D starterPoint = MusePoint2D.ZERO;
//        this.title = new ClickableLabel(new TextComponent("Recipe Options"), starterPoint);
//
//        System.out.println("startpoint: " + starterPoint);
//
//        title.setMode(ClickableLabel.JustifyMode.LEFT);
//
//
//        Checkbox test =  new Checkbox(MusePoint2D.ZERO, "Shapeless", false);
//
//        System.out.println(test);
//
//
//        shapeless = addCheckBox(test
////                new CheckBox(starterPoint, "Shapeless", false)
//        );//ID_SHAPELESS
//        mirrored = addCheckBox(new Checkbox(starterPoint, "Mirrored", true));//ID_MIRRORED
//        conditions = addCheckBox(new Checkbox(starterPoint, "Conditions", false));//ID_CONDITIONS // fixme... not tied to anything yet
//
//        shapeless.setOnPressed(press->{
//            mirrored.setEnabled(!(shapeless.isChecked()));
//            if (!mirrored.isEnabled()){
//                mirrored.setChecked(false);
//            }
//        });
//
//        conditions.setOnPressed(press->{
//            if (conditions.isChecked()) {
//                conditionsFrame.loadConditions();
//                conditionsFrame.show();
//                conditionsFrame.enable();
//            } else {
//                conditionsFrame.disable();
//                conditionsFrame.hide();
//            }
//        });
//
//        conditionsFrame = new ConditionsFrame(
//                MusePoint2D.ZERO, MusePoint2D.ZERO,
//                Colour.PINK,
//                conditionsBorder,
//                Colour.MAGENTA
//        );
//        conditionsFrame.disable();
//        conditionsFrame.hide();
//
//        save = addButton(new TranslatableComponent("mpsrc.gui.save"));
//        save.setOnPressed(pressed->{
//            Musique.playClientSound(SoundEvents.UI_BUTTON_CLICK,1);
//            mparcGui.save();
//        });
//
//        reset = addButton(new TranslatableComponent("mpsrc.gui.resetrecipe"));
//        reset.setOnPressed(pressed-> {
//            Musique.playClientSound(SoundEvents.UI_BUTTON_CLICK, 1);
//            mparcGui.resetRecipes();
//        });
//    }
//
//    public boolean isShapeless() {
//        return shapeless.isChecked();
//    }
//
//    // fixme: should only be enabled if shaped recipe
//    public boolean isMirrored() {
//        return mirrored.isChecked();
//    }
//
////    @Override
////    public ScrollableFrame init(double left, double top, double right, double bottom) {
////        super.init(left, top, right, bottom);
////
////        float nextLineRC = 0;
////        MusePoint2D genericRecipeCol = new MusePoint2D(left + 8, top + 8);
////        title.setPosition(genericRecipeCol);
////        shapeless.setPosition(genericRecipeCol.plus(0, nextLineRC+=12));
////        mirrored.setPosition(genericRecipeCol.plus(0, nextLineRC+=12));
////        conditions.setPosition(genericRecipeCol.plus(0, nextLineRC+=12));
////
////        conditionsFrame.init(
////                left + 3,
////                conditions.centerY() + spacer * 2,
////                right - 3,
////                bottom - spacer
////        );
////        save.setPosition(new MusePoint2D(right, top).copy().plus(-(spacer + save.width() * 0.5F), spacer + save.height() * 0.5F));
////        reset.setPosition(save.center().plus(0, 24F));
////        return this;
////    }
//
//    @Override
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        if (isVisible()) {
//            super.render(matrixStack, mouseX, mouseY, partialTicks);
//            title.render(matrixStack, mouseX, mouseY, partialTicks);
//
//            for (Checkbox checkBox : checkBoxes) {
//                checkBox.render(matrixStack, mouseX, mouseY, partialTicks);
//            }
//
//            for (ClickableButton button : buttons) {
//                button.render(matrixStack, mouseX, mouseY, partialTicks);
//            }
//
//            conditionsFrame.render(matrixStack, mouseX, mouseY, partialTicks);
//        }
//    }
//
//    @Override
//    public void update(double x, double y) {
//        super.update(x, y);
//        if (conditionsFrame.isEnabled() && conditionsFrame.isVisible()) {
//            conditionsFrame.update(x, y);
//        }
//    }
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        if (isVisible() && isEnabled()) {
//            super.mouseClicked(mouseX, mouseY, button);
//
//            for (Checkbox checkBox : checkBoxes) {
//                if (checkBox.mouseClicked(mouseX, mouseY, button)) {
//                    return true;
//                }
//            }
//
//            for (ClickableButton lbutton : buttons) {
//                if (lbutton.mouseClicked(mouseX, mouseY, button)) {
//                    return true;
//                }
//            }
//            conditionsFrame.mouseClicked(mouseX, mouseY, button);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
//        if (conditionsFrame.isVisible() && conditionsFrame.mouseScrolled(mouseX, mouseY, dWheel)) {
//            return true;
//        }
//        return super.mouseScrolled(mouseX, mouseY, dWheel);
//    }
//
//    @Override
//    public boolean mouseReleased(double mouseX, double mouseY, int button) {
//        if (isVisible() && isEnabled()) {
//            super.mouseReleased(mouseX, mouseY, button);
//            if (conditionsFrame.mouseReleased(mouseX, mouseY, button)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    ClickableButton addButton(Component label) {
//        ClickableButton button = new ClickableButton(label, MusePoint2D.ZERO, true);
//        button.setBorderColour(Colour.BLACK);
//        button.setDisabledBackground(Colour.RED);
//        button.setEnabledBackground(Colour.DARK_GREY);
//        button.setWidth(110);
//        button.setHeight(20);
//
//
//        this.buttons.add(button);
//        return button;
//    }
//
//    Checkbox addCheckBox(Checkbox checkBox) {
//        checkBoxes.add(checkBox);
//        return checkBox;
//    }
//}