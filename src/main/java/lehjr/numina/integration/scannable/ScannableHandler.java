package lehjr.numina.integration.scannable;

import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.item.ItemUtils;
import li.cil.scannable.common.item.ItemScanner;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * A hacky mess to make the Scannable scanner act like a module, yet still work
 */
public class ScannableHandler {
    public static void attach(AttachCapabilitiesEvent<ItemStack> event, Callable<IConfig> moduleConfigGetterIn) {
        final ItemStack itemStack = event.getObject();

        TickingScanner scanner = new TickingScanner(itemStack, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, moduleConfigGetterIn);

        event.addCapability(new ResourceLocation("scannable:scanner"), new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == PowerModuleCapability.POWER_MODULE) {
                    return LazyOptional.of(() -> (T) scanner);
                }
                return LazyOptional.empty();
            }
        });

    }

    public static boolean isScanner(@Nonnull ItemStack itemStack) {
        return ItemScanner.isScanner(ItemUtils.getActiveModuleOrEmpty(itemStack));
    }
}