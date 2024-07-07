package lehjr.numina.common.capability;

import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;

public class SomeRandomClassName {

    public static HolderLookup.Provider provider() {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.registryAccess();
    }
}
