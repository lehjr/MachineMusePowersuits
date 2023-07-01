package net.machinemuse.powersuits.client.gui.keybinds;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.machinemuse.numina.client.gui.clickable.CheckBox;
import net.machinemuse.numina.client.gui.clickable.ClickableButton2;
import net.machinemuse.numina.client.gui.frame.IGuiFrame;
import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.geometry.IRect;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.geometry.MuseRect;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.client.render.MuseIconUtils;
import net.machinemuse.numina.client.render.MuseTextureUtils;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.common.module.IPowerModule;
import net.machinemuse.numina.common.module.IToggleableModule;
import net.machinemuse.powersuits.client.control.KeybindManager;
import net.machinemuse.powersuits.client.control.MPSKeyBinding;
import net.machinemuse.powersuits.client.event.RenderEventHandler;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.io.IOException;
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
        if (super.mouseClicked(mouseX, mouseY, button)) {
            for (KeyBindSubFrame subframe : keyBindSubFrames) {
                if (subframe.mouseClicked(mouseX, mouseY + getCurrentScrollPixels(), button)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return super.mouseScrolled(mouseX, mouseY, dWheel);
    }

    public void loadConditions() {
        this.keyBindSubFrames = new ArrayList<>();
        KeybindManager.INSTANCE.getMPSKeybinds()
                .forEach(keyBinding -> {
                    KeyBindSubFrame prev = keyBindSubFrames.size() > 0 ? keyBindSubFrames.get(keyBindSubFrames.size() - 1) : null;
                    KeyBindSubFrame subFrame = new KeyBindSubFrame(left(), top(), width() - 8, keyBinding, prev);
                    keyBindSubFrames.add(subFrame);
                    this.totalSize += subFrame.height();
                });
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.keybindingToRemap != null) {
            if (keyCode == 1) {
                this.keybindingToRemap.setKeyModifierAndCode(KeyModifier.NONE, 0);
                Minecraft.getMinecraft().gameSettings.setOptionKeyBinding(this.keybindingToRemap, 0);
            } else if (keyCode != 0) {
                this.keybindingToRemap.setKeyModifierAndCode(KeyModifier.getActiveModifier(), keyCode);
                Minecraft.getMinecraft().gameSettings.setOptionKeyBinding(this.keybindingToRemap, keyCode);
            } else if (typedChar > 0) {
                this.keybindingToRemap.setKeyModifierAndCode(KeyModifier.getActiveModifier(), typedChar + 256);
                Minecraft.getMinecraft().gameSettings.setOptionKeyBinding(this.keybindingToRemap, typedChar + 256);
            }

            if (!KeyModifier.isKeyCodeModifier(keyCode))
                this.keybindingToRemap = null;
//            this.time = Minecraft.getSystemTime();
            KeyBinding.resetKeyBindingArrayAndHash();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }


    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        super.preRender(mouseX, mouseY, partialTicks);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, -currentScrollPixels, 0);
        keyBindSubFrames.forEach(subframe -> subframe.render(mouseX, mouseY, partialTicks));
        GlStateManager.popMatrix();
        super.postRender(mouseX, mouseY, partialTicks);
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        if (this.isEnabled() && this.isVisible()) {
            // Just because you can doesn't mean you should :P
            return keyBindSubFrames.stream().filter(subframe -> subframe.getToolTip(mouseX, (int) (mouseY + getCurrentScrollPixels())) != null).map(subframe -> subframe.getToolTip(mouseX, (int) (mouseY + getCurrentScrollPixels()))).findFirst().orElse(null);
        }
        return super.getToolTip(mouseX, mouseY);
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
                    I18n.translateToLocal(module.getUnlocalizedName() + ".name"), kb.showOnHud);
            // checkbox.setWidth(150);
            this.checkbox.setOnPressed(pressed -> {
                kb.showOnHud = this.checkbox.isChecked();
                KeybindManager.INSTANCE.writeOutKeybinds();
                RenderEventHandler.makeKBDisplayList();
            });
            this.button = new ClickableButton2(right() - 97, top() + 1, 95, true);
            this.button.setOnPressed(onPressed -> keybindingToRemap = kb);
        }

        @Override
        public IRect getRect() {
            return border;
        }

        @Override
        public void setRect(IRect rect) {
            this.border = (T) rect;
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
            if (containsPoint(mouseX, mouseY)) {
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