package net.machinemuse.powersuits.client.gui.keybinds;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.machinemuse.numina.client.gui.clickable.CheckBox;
import net.machinemuse.numina.client.gui.clickable.ClickableButton2;
import net.machinemuse.numina.client.gui.frame.IGuiFrame;
import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.geometry.IRect;
import net.machinemuse.numina.client.gui.geometry.MuseRect;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.render.MuseIconUtils;
import net.machinemuse.numina.client.render.MuseTextureUtils;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.common.module.IPowerModule;
import net.machinemuse.numina.common.module.IToggleableModule;
import net.machinemuse.powersuits.client.control.KeybindManager;
import net.machinemuse.powersuits.client.control.MPSKeyBinding;
import net.machinemuse.numina.client.gui.clickable.ClickableButton;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lehjr
 */
public class KeybindingFrame extends ScrollableFrame {
    @Nullable
    MPSKeyBinding keybindingToRemap = null;
    NonNullList<IPowerModule> iPowerModules = NonNullList.create();
    boolean selecting;

    //    //    Map<MPSKeyBinding, Pair<Checkbox, VanillaButton>> checkBoxList = new HashMap<>();
//    VanillaFrameScrollBar scrollBar;
    List<KeyBindSubFrame> keyBindSubFrames;

    public KeybindingFrame(MusePoint2D topleft, MusePoint2D bottomright, Colour borderColour, Colour insideColour) {
        super(new DrawableMuseRect(topleft, bottomright, borderColour, insideColour));
        keybindingToRemap = null;
        iPowerModules.addAll(ModuleManager.INSTANCE.getModuleMap().values().stream().filter(IToggleableModule.class::isInstance).collect(Collectors.toList()));
        selecting = false;
        loadConditions();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return super.mouseScrolled(mouseX, mouseY, dWheel);
    }

    public void handleKeyboard() {
        if (selecting && keybindingToRemap != null) {
            if (Keyboard.getEventKeyState()) {
                int key = Keyboard.getEventKey();




//                if (key != 0 && key < 256)
//                {
//                    return key < 0 ? Mouse.isButtonDown(key + 100) : Keyboard.isKeyDown(key);
//                }
//                else
//                {
//                    return false;
//                }





//               see GuiControls.keyTyped();


////                if (KeyBinding.HASH.containsItem(key)) {
//                if (keyBindingHelper.keyBindingHasKey(key)) {
//                    takenTime = System.currentTimeMillis();
//                }
////                if (!KeyBinding.HASH.containsItem(key)) {
//                if (!keyBindingHelper.keyBindingHasKey(key)) {
//                    addKeybind(key, true);
//                } else if (MPSConfig.INSTANCE.allowConflictingKeybinds()) {
//                    addKeybind(key, false);
//                }
                selecting = false;
            }
        }
    }

    public void loadConditions() {
        this.keyBindSubFrames = new ArrayList<>();
        KeybindManager.INSTANCE.getMPSKeybinds()
                .forEach(keyBinding -> {
                    KeyBindSubFrame prev = keyBindSubFrames.size() > 0 ? keyBindSubFrames.get(keyBindSubFrames.size() -1) : null;
                    KeyBindSubFrame subFrame = new KeyBindSubFrame(left(), top(), width() - 8, keyBinding, prev);
                    keyBindSubFrames.add(subFrame);
                    this.totalSize += subFrame.height();
                });

//        System.out.println("keybinding sub frames size: " + keyBindSubFrames.size());
//        System.out.println("other keybinding sub frames size: " + KeybindManager.INSTANCE.getMPSKeybinds().collect(Collectors.toList()).size());
    }

//    static boolean isKeyPressed(int key) {
//        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
//    }
//
//    @Override
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//        super.render(matrixStack, mouseX, mouseY, partialTick);
//        if (this.isEnabled() && this.isVisible()) {
//            super.preRender(matrixStack, mouseX, mouseY, partialTick);
//            matrixStack.pushPose();
//            matrixStack.translate(0.0, -this.currentScrollPixels, 0.0);
//
//            for (KeyBindSubFrame subframe : keyBindSubFrames) {
//                subframe.render(matrixStack, mouseX, (int) (currentScrollPixels + mouseY), partialTick);
//            }
//            matrixStack.popPose();
//            super.postRender(mouseX, mouseY, partialTick);
//            scrollBar.render(matrixStack, mouseX, mouseY, partialTick);
//        } else {
//            super.preRender(matrixStack, mouseX, mouseY, partialTick);
//            super.postRender(mouseX, mouseY, partialTick);
//        }
//    }
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        if (this.isEnabled() && this.isVisible()) {
//            super.mouseClicked(mouseX, mouseY, button);
//            if (scrollBar.mouseClicked(mouseX, mouseY, button)) {
//                return true;
//            }
//
//            for (KeyBindSubFrame subframe : keyBindSubFrames) {
//                if (subframe.mouseClicked(mouseX, mouseY + getCurrentScrollPixels(), button)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void update(double mouseX, double mouseY) {
//        scrollBar.setMaxValue(getMaxScrollPixels());
//        scrollBar.setValueByMouse(mouseY);
//        setCurrentScrollPixels(scrollBar.getValue());
//    }
//
//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
//        boolean retVal = super.mouseScrolled(mouseX, mouseY, dWheel);
//        scrollBar.setValue(currentScrollPixels);
//        return retVal;
//    }
//
//    @Override
//    public boolean mouseReleased(double mouseX, double mouseY, int button) {
//        if (this.isEnabled()) {
//            return scrollBar.mouseReleased(mouseX, mouseY, button);
//        }
//        return super.mouseReleased(mouseX, mouseY, button);
//    }
//
//    @Override
//    public List<Component> getToolTip(double mouseX, double mouseY) {
//        if (this.isEnabled() && this.isVisible()) {
//            // Just because you can doesn't mean you should :P
//            return keyBindSubFrames.stream().filter(subframe -> subframe.getToolTip(mouseX, (int) (mouseY + getCurrentScrollPixels())) !=null).map(subframe -> subframe.getToolTip(mouseX, (int) (mouseY + getCurrentScrollPixels()))).findFirst().orElse(null) ;
//        }
//        return super.getToolTip(mouseX, mouseY);
//    }

//    class KeyBindSubFrame extends AbstractGuiFrame {
//        @Nonnull
//        IPowerModule module;
//        public GuiCheckBox checkbox;
//        public VanillaButton button;
//        public MPSKeyMapping kb;
//
//        public KeyBindSubFrame(double left, double top, double width, MPSKeyBinding kb, KeyBindSubFrame aboveThis) {
//            super(new Rect(left, top, left + width, top + 22).setBelow(aboveThis));
//            this.kb = kb;
//            this.module = ModuleManager.INSTANCE.getModule(kb.dataName);
//            this.checkbox = new Checkbox(
//                    left() + 25, // x
//                    top(), // y
//                    150, // width
//                    //20, // height
//                    Component.translatable(kb.getName()), kb.showOnHud);
//
//            this.checkbox.setOnPressed(pressed ->{
//                kb.showOnHud = this.checkbox.isChecked();
//                KeyMappingReaderWriter.INSTANCE.writeOutKeybindSetings();
//                MPSKeyBindHud.makeKBDisplayList();
////                RenderEventHandler.INSTANCE.makeKBDisplayList();
//            });
//
//            this.button = new VanillaButton(right() - 97, top() + 1, 95, kb.getKey().getDisplayName(), true);
//            this.button.setOnPressed(onPressed-> keybindingToRemap = kb);
//        }
//
//        @Override
//        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//            super.render(matrixStack, mouseX, mouseY, partialTick);
//            NuminaRenderer.drawModuleAt(matrixStack, left() + 2, top() + 3, module, true);
//            checkbox.render(matrixStack, mouseX, mouseY, partialTick);
//            if (keybindingToRemap != null && keybindingToRemap == kb) {
//                button.setLabel((Component.literal("> ")).append(kb.getKey().getDisplayName().copy().withStyle(ChatFormatting.YELLOW)).append(" <").withStyle(ChatFormatting.YELLOW));
//            } else {
//                button.setLabel(kb.getKey().getDisplayName().copy().withStyle( /* keyCodeModifierConflict ? */ ChatFormatting.WHITE /*: ChatFormatting.RED*/));
//            }
//            button.render(matrixStack, mouseX, mouseY, partialTick);
//        }
//
//        @Override
//        public boolean mouseClicked(double mouseX, double mouseY, int button) {
//            if(containsPoint(mouseX, mouseY) && this.isEnabled() && this.isVisible()) {
//                return checkbox.mouseClicked(mouseX, mouseY, button) || this.button.mouseClicked(mouseX, mouseY, button);
//            }
//            return false;
//        }
//
//        @Override
//        public boolean mouseReleased(double mouseX, double mouseY, int button) {
//            return checkbox.mouseReleased(mouseX, mouseY, button) || this.button.mouseReleased(mouseX, mouseY, button);
//        }
//
//        @Nullable
//        @Override
//        public List<String> getToolTip(int x, int y) {
//            if (checkbox.containsPoint(x, y)) {
//                return Arrays.asList(Component.translatable("gui.powersuits.showOnHud"));
//            }
//            return super.getToolTip(x, y);
//        }
//    }


    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        super.preRender(mouseX, mouseY, partialTicks);
        GlStateManager.pushMatrix();

//        System.out.println("current scroll pixels: " + currentScrollPixels);

        GlStateManager.translate(0, -currentScrollPixels, 0);
        keyBindSubFrames.forEach(subframe -> subframe.render(mouseX, mouseY, partialTicks));
        GlStateManager.popMatrix();
        super.postRender(mouseX, mouseY, partialTicks);
    }

    public class KeyBindSubFrame<T extends IRect> implements IGuiFrame {
        IPowerModule module;
        public CheckBox checkbox;
        public ClickableButton2 button;
        public MPSKeyBinding kb;
        T border;

        public KeyBindSubFrame(double left, double top, double width, MPSKeyBinding kb, KeyBindSubFrame aboveThis) {
            setRect(new MuseRect(left, top, left + width, top + 22, false));
            setBelow(aboveThis);
            this.kb = kb;
            this.module = ModuleManager.INSTANCE.getModule(kb.dataName);
            this.checkbox = new CheckBox(-1,
                    new MusePoint2D(left() + 28, // x
                            top() + 4), // y
                    // width
                    //20, // height
                    I18n.translateToLocal(module.getUnlocalizedName()+".name"), kb.showOnHud);
            // checkbox.setWidth(150);
            this.checkbox.setOnPressed(pressed ->{
                kb.showOnHud = this.checkbox.isChecked();
                KeybindManager.INSTANCE.writeOutKeybinds();
//                MPSKeyBindHud.makeKBDisplayList(); // FIXME: will still need something if this nature
//                RenderEventHandler.INSTANCE.makeKBDisplayList();
            });

            // FIXME: keyCode can be negative?? but is an index?? so errors when looking up name (and probably when using)

            this.button = new ClickableButton2(right() - 97, top() + 1,  95, true);


//            Keyboard.getKeyName(kb.getKeyCode()),

//        this.button.setWidth(95); // FIXME
            this.button.setOnPressed(onPressed-> keybindingToRemap = kb);
        }

        @Override
        public IRect getRect() {
            return border;
        }

        @Override
        public void setRect(IRect rect) {
            this.border = (T)rect;
        }

        @Override
        public void render(double mouseX, double mouseY, float partialTicks) {
            MuseTextureUtils.pushTexture(MuseTextureUtils.TEXTURE_QUILT);
            MuseIconUtils.drawIconAt(left() + 6, top() + 3, module.getIcon(null), Colour.WHITE);
            MuseTextureUtils.popTexture();
            checkbox.render(mouseX, mouseY, partialTicks);
            if (keybindingToRemap != null && keybindingToRemap == kb) {
                button.label = ChatFormatting.YELLOW + "> " + ChatFormatting.WHITE + Keyboard.getKeyName(kb.getKeyCode()) + ChatFormatting.YELLOW + " <";
            } else {
                button.label = (keyCodeModifierConflict() ? ChatFormatting.RED : ChatFormatting.WHITE) + Keyboard.getKeyName(kb.getKeyCode());
            }
            button.render(mouseX, mouseY, partialTicks);
        }

        boolean keyCodeModifierConflict() {
            return false;
        }

        @Override
        public void update(double mousex, double mousey) {

        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if(containsPoint(mouseX, mouseY)) {
                return checkbox.mouseClicked(mouseX, mouseY, button) || this.button.mouseClicked(mouseX, mouseY, button);
            }
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return checkbox.mouseReleased(mouseX, mouseY, button) || this.button.mouseReleased(mouseX, mouseY, button);
        }

        @Override
        public List<String> getToolTip(double mouseX, double mouseY) {
            if (checkbox.containsPoint(mouseX, mouseY)) {
                return Arrays.asList(I18n.translateToLocal("gui.powersuits.showOnHud"));
            }
            return null;
        }
    }
}