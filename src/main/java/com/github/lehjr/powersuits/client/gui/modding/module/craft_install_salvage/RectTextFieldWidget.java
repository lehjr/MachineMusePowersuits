package com.github.lehjr.powersuits.client.gui.modding.module.craft_install_salvage;

import com.github.lehjr.numina.util.client.gui.gemoetry.WidgetWrapper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class RectTextFieldWidget extends WidgetWrapper {
    public RectTextFieldWidget(FontRenderer fontRenderer, int left, int top, int width, int height, ITextComponent message) {
        this(fontRenderer, left, top, width, height, null, message);
    }

    public RectTextFieldWidget(FontRenderer fontRenderer, int left, int top, int width, int height, @Nullable TextFieldWidget p_i232259_6_, ITextComponent message) {
        super(new TextFieldWidget(fontRenderer, left, top, width, height, p_i232259_6_, message));
    }
}
