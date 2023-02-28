//package lehjr.mpsrecipecreator.client.gui;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import lehjr.mpsrecipecreator.basemod.ConditionsJsonLoader;
//import lehjr.numina.client.gui.clickable.Checkbox;
//import lehjr.numina.client.gui.frame.ScrollableFrame;
//import lehjr.numina.client.gui.geometry.MusePoint2D;
//import lehjr.numina.client.gui.geometry.Rect;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author lehjr
// */
//public class ConditionsFrame extends ScrollableFrame {
//    Map<Checkbox, JsonObject> checkBoxList = new HashMap<>();
//
//    public ConditionsFrame(MusePoint2D topleft, MusePoint2D bottomright) {
//        super(new Rect(topleft, bottomright));
//    }
//
////    @Override
////    public Rect init(double left, double top, double right, double bottom) {
////        super.init(left, top, right, bottom);
////        loadConditions();
////        return this;
////    }
//
//    @Override
//    public void setVisible(boolean visible) {
//        super.setVisible(visible);
//        loadConditions();
//    }
//
//    public void loadConditions() {
//        MusePoint2D starterPoint = this.getUL().copy().plus(8, 14);
//        if (checkBoxList.isEmpty()) {
//            JsonArray jsonArray = ConditionsJsonLoader.getConditionsFromFile();
//            if (jsonArray != null) {
//                jsonArray.forEach(jsonElement -> {
//                    if (jsonElement instanceof JsonObject) {
//                        JsonObject jsonObject = jsonElement.getAsJsonObject();
//                        String condition = jsonObject.get("display_name").getAsString();
//                        jsonObject.remove("display_name");
//
//                        Checkbox checkbox = new Checkbox(
//                                starterPoint.plus(0, checkBoxList.size() * 10),
//                                condition, false);
//                        checkBoxList.put(checkbox, jsonObject);
//                    }
//                });
//            }
//            // moves the checkboxes without recreating them so their state is preserved
//        } else {
//            int i =0;
//            for (Checkbox checkBox : checkBoxList.keySet()) {
//                checkBox.setPosition(starterPoint.plus(0, i * 10));
//                i++;
//            }
//        }
//        this.setTotalSize(checkBoxList.size() * 12);
//    }
//
//    /**
//     * Note that the conditions aren't setup for multiple conditions at this time
//     */
//    public JsonArray getJsonArray() {
//        JsonArray array = new JsonArray();
//        for (Checkbox box : checkBoxList.keySet()) {
//            if (box.isChecked()) {
//                array.add(checkBoxList.get(box));
//            }
//        }
//        return array;
//    }
//
//    @Override
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//        if (this.isEnabled() && this.isVisible()) {
//            this.setCurrentScrollPixels(Math.min(getCurrentScrollPixels(), getMaxScrollPixels()));
//            super.preRender(matrixStack, mouseX, mouseY, partialTick);
//            RenderSystem.pushPose();
//            RenderSystem.translatef(0, (float) -getCurrentScrollPixels(), 0);
//            for (Checkbox checkBox : checkBoxList.keySet()) {
//                checkBox.render(matrixStack, mouseX, mouseY, partialTick);
//            }
//            RenderSystem.popMatrix();
//            super.postRender(mouseX, mouseY, partialTick);
//        }
//    }
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        if (this.isEnabled() && this.isVisible()) {
//            super.mouseClicked(mouseX, mouseY, button);
//
//            for (Checkbox checkBox : checkBoxList.keySet()) {
//                if (checkBox.mouseClicked(mouseX, mouseY + getCurrentScrollPixels(), button)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//}