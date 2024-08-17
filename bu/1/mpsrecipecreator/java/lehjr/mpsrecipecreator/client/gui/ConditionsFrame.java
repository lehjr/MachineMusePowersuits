package lehjr.mpsrecipecreator.client.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lehjr.mpsrecipecreator.basemod.ConditionsJsonLoader;
import com.lehjr.numina.client.gui.clickable.Checkbox;
import com.lehjr.numina.client.gui.frame.ScrollableFrame;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.Rect;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lehjr
 */
public class ConditionsFrame extends ScrollableFrame {
    Map<Checkbox, JsonObject> checkBoxList = new HashMap<>();

    public ConditionsFrame() {
        super(new Rect(MusePoint2D.ZERO, MusePoint2D.ZERO));
    }

    public void init() {
        loadConditions();
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

                        Checkbox checkbox = new Checkbox(
                                starterPoint.plus(0, checkBoxList.size() * 10),
                                condition, false);
                        checkBoxList.put(checkbox, jsonObject);
                    }
                });
            }
            // moves the checkboxes without recreating them so their state is preserved
        } else {
            int i =0;
            for (Checkbox checkBox : checkBoxList.keySet()) {
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
        for (Checkbox box : checkBoxList.keySet()) {
            if (box.isChecked()) {
                array.add(checkBoxList.get(box));
            }
        }
        return array;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (this.isEnabled() && this.isVisible()) {
            this.setCurrentScrollPixels(Math.min(getCurrentScrollPixels(), getMaxScrollPixels()));
            super.preRender(gfx, mouseX, mouseY, partialTick);
            gfx.pose().pushPose();
            gfx.pose().translate(0, (float) -getCurrentScrollPixels(), 0);
            for (Checkbox checkBox : checkBoxList.keySet()) {
                checkBox.render(gfx, mouseX, mouseY, partialTick);
            }
            gfx.pose().popPose();
            super.postRender(gfx, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isEnabled() && this.isVisible()) {
            super.mouseClicked(mouseX, mouseY, button);

            for (Checkbox checkBox : checkBoxList.keySet()) {
                if (checkBox.mouseClicked(mouseX, mouseY + getCurrentScrollPixels(), button)) {
                    return true;
                }
            }
        }
        return false;
    }
}