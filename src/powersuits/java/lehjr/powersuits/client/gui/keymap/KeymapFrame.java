package lehjr.powersuits.client.gui.keymap;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.clickable.Checkbox;
import lehjr.numina.client.gui.clickable.button.VanillaButton;
import lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import lehjr.numina.client.gui.frame.AbstractGuiFrame;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.powersuits.client.control.KeyMappingReaderWriter;
import lehjr.powersuits.client.control.MPSKeyMapping;
import lehjr.powersuits.client.gui.overlay.MPSOverlay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lehjr
 */
public class KeymapFrame extends ScrollableFrame {
    @Nullable
    MPSKeyMapping keybindingToRemap = null;
    //    Map<MPSKeyBinding, Pair<Checkbox, VanillaButton>> checkBoxList = new HashMap<>();
    VanillaFrameScrollBar scrollBar;
    List<KeyBindSubFrame> keyBindSubFrames;

    public KeymapFrame(MusePoint2D topleft, MusePoint2D bottomright) {
        super(new Rect(topleft, bottomright));
        this.enableAndShow();
        keybindingToRemap = null;
        this.scrollBar = new VanillaFrameScrollBar(this, "slider");
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        loadConditions();
    }

    public void loadConditions() {
        this.keyBindSubFrames = new ArrayList<>();
        Arrays.stream(Minecraft.getInstance().options.keyMappings)
                .filter(MPSKeyMapping.class::isInstance)
                .map(MPSKeyMapping.class::cast)
                .forEach(keyBinding -> {
                    KeyBindSubFrame prev = keyBindSubFrames.size() > 0 ? keyBindSubFrames.get(keyBindSubFrames.size() -1) : null;
                    KeyBindSubFrame subFrame = new KeyBindSubFrame(left(), top(), width() - 8, keyBinding, prev);
                    keyBindSubFrames.add(subFrame);
                    this.totalSize += subFrame.height();
                });
    }

    static boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        super.render(matrixStack, mouseX, mouseY, partialTick);
        if (this.isEnabled() && this.isVisible()) {
            super.preRender(matrixStack, mouseX, mouseY, partialTick);
            matrixStack.pushPose();
            matrixStack.translate(0.0, -this.currentScrollPixels, 0.0);

            for (KeyBindSubFrame subframe : keyBindSubFrames) {
                subframe.render(matrixStack, mouseX, (int) (currentScrollPixels + mouseY), partialTick);
            }
            matrixStack.popPose();
            super.postRender(mouseX, mouseY, partialTick);
            scrollBar.render(matrixStack, mouseX, mouseY, partialTick);
        } else {
            super.preRender(matrixStack, mouseX, mouseY, partialTick);
            super.postRender(mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isEnabled() && this.isVisible()) {
            super.mouseClicked(mouseX, mouseY, button);
            if (scrollBar.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }

            for (KeyBindSubFrame subframe : keyBindSubFrames) {
                if (subframe.mouseClicked(mouseX, mouseY + getCurrentScrollPixels(), button)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        scrollBar.setMaxValue(getMaxScrollPixels());
        scrollBar.setValueByMouse(mouseY);
        setCurrentScrollPixels(scrollBar.getValue());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        boolean retVal = super.mouseScrolled(mouseX, mouseY, dWheel);
        scrollBar.setValue(currentScrollPixels);
        return retVal;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isEnabled()) {
            return scrollBar.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        if (this.isEnabled() && this.isVisible()) {
            // Just because you can doesn't mean you should :P
            return keyBindSubFrames.stream().filter(subframe -> subframe.getToolTip(x, (int) (y + getCurrentScrollPixels())) !=null).map(subframe -> subframe.getToolTip(x, (int) (y + getCurrentScrollPixels()))).findFirst().orElse(null) ;
        }
        return super.getToolTip(x, y);
    }

    class KeyBindSubFrame extends AbstractGuiFrame {
        @Nonnull
        ItemStack module;
        public Checkbox checkbox;
        public VanillaButton button;
        public MPSKeyMapping kb;

        public KeyBindSubFrame(double left, double top, double width, MPSKeyMapping kb, KeyBindSubFrame aboveThis) {
            super(new Rect(left, top, left + width, top + 22).setBelow(aboveThis));
            this.kb = kb;
            this.module = new ItemStack(ForgeRegistries.ITEMS.getValue(kb.registryName));
            this.checkbox = new Checkbox(
                    left() + 25, // x
                    top(), // y
                    150, // width
                    //20, // height
                    Component.translatable(module.getDescriptionId()), kb.showOnHud);

            this.checkbox.setOnPressed(pressed ->{
                kb.showOnHud = this.checkbox.isChecked();
                KeyMappingReaderWriter.INSTANCE.writeOutKeybindSetings();
                MPSOverlay.makeKBDisplayList();
//                RenderEventHandler.INSTANCE.makeKBDisplayList();
            });

            this.button = new VanillaButton(right() - 97, top() + 1, 95, kb.getKey().getDisplayName(), true);
            this.button.setOnPressed(onPressed-> keybindingToRemap = kb);
        }

        @Override
        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
            super.render(matrixStack, mouseX, mouseY, partialTick);
            NuminaRenderer.drawModuleAt(matrixStack, left() + 2, top() + 3, module, true);
            checkbox.render(matrixStack, mouseX, mouseY, partialTick);
            if (keybindingToRemap != null && keybindingToRemap == kb) {
                button.setLabel((Component.literal("> ")).append(kb.getKey().getDisplayName().copy().withStyle(ChatFormatting.YELLOW)).append(" <").withStyle(ChatFormatting.YELLOW));
            } else {
                button.setLabel(kb.getKey().getDisplayName().copy().withStyle( /* keyCodeModifierConflict ? */ ChatFormatting.WHITE /*: ChatFormatting.RED*/));
            }
            button.render(matrixStack, mouseX, mouseY, partialTick);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if(containsPoint(mouseX, mouseY) && this.isEnabled() && this.isVisible()) {
                return checkbox.mouseClicked(mouseX, mouseY, button) || this.button.mouseClicked(mouseX, mouseY, button);
            }
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return checkbox.mouseReleased(mouseX, mouseY, button) || this.button.mouseReleased(mouseX, mouseY, button);
        }

        @Nullable
        @Override
        public List<Component> getToolTip(int x, int y) {
            if (checkbox.containsPoint(x, y)) {
                return Arrays.asList(Component.translatable("gui.powersuits.showOnHud"));
            }
            return super.getToolTip(x, y);
        }
    }
}