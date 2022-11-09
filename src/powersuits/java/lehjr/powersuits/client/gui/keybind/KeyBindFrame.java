package lehjr.powersuits.client.gui.keybind;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import lehjr.numina.client.gui.clickable.Checkbox2;
import lehjr.numina.client.gui.clickable.ClickableButton2;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.RelativeRect;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.math.Colour;
import lehjr.powersuits.client.control.KeybindManager;
import lehjr.powersuits.client.control.MPSKeyBinding;
import lehjr.powersuits.client.event.RenderEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lehjr
 */
public class KeyBindFrame extends ScrollableFrame {
    @Nullable
    MPSKeyBinding keybindingToRemap = null;
   Map<MPSKeyBinding, Pair<Checkbox2, ClickableButton2>> checkBoxList = new HashMap<>();

   public KeyBindFrame(MusePoint2D topleft, MusePoint2D bottomright) {
        super(topleft, bottomright, Colour.DARK_GREY, new Colour(0.216F, 0.216F, 0.216F, 1F), Colour.WHITE.withAlpha(0.8F));
        this.enableAndShow();
        keybindingToRemap = null;
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
        Arrays.stream(Minecraft.getInstance().options.keyMappings)
                .filter(MPSKeyBinding.class::isInstance)
                .map(MPSKeyBinding.class::cast)
                .forEach(keyBinding -> {
                    Checkbox2 checkboxButton = new Checkbox2(
                            0, // x
                            0, // y
                            150, // width
                            //20, // height
                            new TranslationTextComponent(keyBinding.getName()),
                            keyBinding.showOnHud) {
                        @Override
                        public void onPress() {
                            super.onPress();
                            keyBinding.showOnHud = selected();
                            KeybindManager.INSTANCE.writeOutKeybindSetings();
                            RenderEventHandler.INSTANCE.makeKBDisplayList();
                        }
                    };
                    ClickableButton2 button = new ClickableButton2(keyBinding.getKey().getDisplayName(), MusePoint2D.ZERO, true);
                    button.setOnPressed(onPressed->
                    {
                        keybindingToRemap = keyBinding;
                    });
                    button.setWidth(95);
                    button.setHeight(16);
                    checkBoxList.put(keyBinding, Pair.of(checkboxButton, button));
                });
        this.setTotalSize(checkBoxList.size() * 20);
    }

    static boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (this.isEnabled() && this.isVisible()) {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.pushMatrix();
            RenderSystem.translated(0.0, -this.currentScrollPixels, 0.0);
            double y = top() + 8;
            for (Map.Entry<MPSKeyBinding, Pair<Checkbox2, ClickableButton2>> entry : checkBoxList.entrySet()) {
                NuminaRenderer.drawModuleAt(matrixStack, finalLeft() + 2, y -8, new ItemStack(ForgeRegistries.ITEMS.getValue(entry.getKey().registryName)), true);
                MPSKeyBinding kb = entry.getKey();
                Checkbox2 checkbox = entry.getValue().getFirst();
                checkbox.x = (int) (finalLeft() + 24);
                checkbox.y = (int) y -10; // - half height
                checkbox.visible = true;

                if (checkbox.isMouseOver(mouseX, mouseY) && !checkbox.isFocused()) {
                    checkbox.changeFocus(true);
                } else if (checkbox.isFocused()) {
                    checkbox.changeFocus(true);
                }

                checkbox.render(matrixStack, mouseX, mouseY, partialTicks);
                ClickableButton2 button = entry.getValue().getSecond();

                if (keybindingToRemap != null && keybindingToRemap == kb) {
                    button.setLable((new StringTextComponent("> ")).append(kb.getKey().getDisplayName().copy().withStyle(TextFormatting.YELLOW)).append(" <").withStyle(TextFormatting.YELLOW));
                } else  {
                    button.setLable(kb.getKey().getDisplayName().copy().withStyle( /* keyCodeModifierConflict ? */ TextFormatting.WHITE /*: TextFormatting.RED*/));
                }
                button.setRight(finalRight() -2);
                button.setTop(y-8);
                button.render(matrixStack, mouseX, mouseY, partialTicks);
                y += 20;
            }

            RenderSystem.popMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        } else {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isEnabled() && this.isVisible()) {
            super.mouseClicked(mouseX, mouseY, button);
            for (Pair<Checkbox2, ClickableButton2> pair : checkBoxList.values()) {
                if (pair.getFirst().mouseClicked(mouseX, mouseY + getCurrentScrollPixels(), button) ||
                        pair.getSecond().mouseClicked(mouseX, mouseY + getCurrentScrollPixels(), button)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        if (this.isEnabled() && this.isVisible()) {
            for (Pair<Checkbox2, ClickableButton2> pair : checkBoxList.values()) {
                if (pair.getFirst().isMouseOver(x, y)) {
                    return Arrays.asList(new TranslationTextComponent("gui.powersuits.showOnHUD"));
                }
            }
        }
        return super.getToolTip(x, y);
    }
}