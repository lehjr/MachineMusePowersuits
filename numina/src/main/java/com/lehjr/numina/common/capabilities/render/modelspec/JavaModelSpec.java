package com.lehjr.numina.common.capabilities.render.modelspec;

import net.minecraft.network.chat.Component;

/**
 * This is just a way of mapping a possible texture combinations for a piece of PowerArmor using the default vanilla model
 */
public class JavaModelSpec extends SpecBase {
    public JavaModelSpec(final String name, final boolean isDefault) {
        super(name, isDefault, SpecType.ARMOR_SKIN);
    }

    @Override
    public Component getDisaplayName() {
        return Component.translatable(new StringBuilder("javaModel.")
                .append(this.getName())
                .append(".specName")
                .toString());
    }

    @Override
    public String getOwnName() {
        String name = NuminaModelSpecRegistry.getInstance().getName(this);
        return (name != null) ? name : "";
    }
}