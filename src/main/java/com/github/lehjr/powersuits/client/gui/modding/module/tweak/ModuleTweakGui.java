package com.github.lehjr.powersuits.client.gui.modding.module.tweak;

import com.github.lehjr.numina.util.client.gui.ContainerlessGui;
import com.github.lehjr.powersuits.client.gui.common.TabSelectFrame;
import net.minecraft.util.text.ITextComponent;

public class ModuleTweakGui extends ContainerlessGui {
    /** top gui selection button set */
    protected TabSelectFrame tabSelectFrame;
    /** commonly used spacer value */
    int spacer = 7;


    public ModuleTweakGui(ITextComponent titleIn) {
        super(titleIn, 340, 217);
    }
}
