package lehjr.numina.common.capabilities.render.highlight;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class HighLightCapability {
    @CapabilityInject(IHighlight.class)
    public static Capability<IHighlight> HIGHLIGHT = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IHighlight.class, new Capability.IStorage<IHighlight>() {
                    @Override
                    public INBT writeNBT(Capability<IHighlight> capability, IHighlight instance, Direction side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IHighlight> capability, IHighlight instance, Direction side, INBT nbt) {
                        if (!(instance instanceof Highlight)) {
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                        }
                    }
                },
                () -> new Highlight());
    }
}