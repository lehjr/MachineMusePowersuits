package lehjr.mpsrecipecreator.client.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.mpsrecipecreator.basemod.ConditionsJsonLoader;
import lehjr.numina.client.gui.clickable.CheckBox;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.RelativeRect;
import lehjr.numina.common.math.Colour;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lehjr
 */
public class ConditionsFrame extends ScrollableFrame {
    Map<CheckBox, JsonObject> checkBoxList = new HashMap<>();

    public ConditionsFrame(MusePoint2D topleft, MusePoint2D bottomright, Colour background, Colour topBorder, Colour bottomBorder) {
        super(topleft, bottomright, background, topBorder, bottomBorder);
    }

    @Override
    public RelativeRect init(double left, double top, double right, double bottom) {
        super.init(left, top, right, bottom);
        loadConditions();
        return this;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        loadConditions();
    }

    public void loadConditions() {
        MusePoint2D starterPoint = this.getUL().copy().plus(8, 14);
        if (checkBoxList.isEmpty()) {
            JsonArray jsonArray = ConditionsJsonLoader.getConditionsFromFile();
            if (jsonArray != null) {
                jsonArray.forEach(jsonElement -> {
                    if (jsonElement instanceof JsonObject) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        String condition = jsonObject.get("display_name").getAsString();
                        jsonObject.remove("display_name");

                        CheckBox checkbox = new CheckBox(
                                starterPoint.plus(0, checkBoxList.size() * 10),
                                condition, false);
                        checkBoxList.put(checkbox, jsonObject);
                    }
                });
            }
            // moves the checkboxes without recreating them so their state is preserved
        } else {
            int i =0;
            for (CheckBox checkBox : checkBoxList.keySet()) {
                checkBox.setPosition(starterPoint.plus(0, i * 10));
                i++;
            }
        }
        this.setTotalSize(checkBoxList.size() * 12);
    }

    /**
     * Note that the conditions aren't setup for multiple conditions at this time
     */
    public JsonArray getJsonArray() {
        JsonArray array = new JsonArray();
        for (CheckBox box : checkBoxList.keySet()) {
            if (box.isChecked()) {
                array.add(checkBoxList.get(box));
            }
        }
        return array;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isEnabled() && this.isVisible()) {
            this.setCurrentScrollPixels(Math.min(getCurrentScrollPixels(), getMaxScrollPixels()));
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0, -getCurrentScrollPixels(), 0);
            for (CheckBox checkBox : checkBoxList.keySet()) {
                checkBox.render(matrixStack, mouseX, mouseY, partialTicks);
            }
            RenderSystem.popMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isEnabled() && this.isVisible()) {
            super.mouseClicked(mouseX, mouseY, button);

            for (CheckBox checkBox : checkBoxList.keySet()) {
                if (checkBox.mouseClicked(mouseX, mouseY + getCurrentScrollPixels(), button)) {
                    return true;
                }
            }
        }
        return false;
    }
}