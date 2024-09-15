package com.lehjr.numina.common.capabilities.render.color;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCodecs;
import net.neoforged.neoforge.attachment.AttachmentHolder;

public class ColorAttachmentStorage implements IColorTag {
    protected final AttachmentHolder parent;

    public ColorAttachmentStorage(AttachmentHolder parent) {
        this.parent = parent;
    }

    @Override
    public Color getColor() {
        return new Color(parent.getData(NuminaCodecs.COLOR_ATTACHMENT));
    }

    @Override
    public void setColor(Color color) {
        parent.setData(NuminaCodecs.COLOR_ATTACHMENT, color.getARGBInt());
    }
}
