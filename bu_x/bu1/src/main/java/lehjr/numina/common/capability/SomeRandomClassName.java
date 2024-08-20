package com.lehjr.numina.common.capabilities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;

public class SomeRandomClassName {

    public static HolderLookup.Provider provider() {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.registryAccess();
    }
}
