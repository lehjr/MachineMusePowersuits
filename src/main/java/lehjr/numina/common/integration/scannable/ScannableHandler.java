//package lehjr.numina.common.integration.scannable;
//
//import lehjr.numina.common.capabilities.module.powermodule.*;
//import lehjr.numina.common.item.ItemUtils;
//import li.cil.scannable.common.item.ItemScanner;
//import net.minecraft.core.Direction;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.concurrent.Callable;
//
///**
// * A hacky mess to make the Scannable scanner act like a module, yet still work
// */
//public class ScannableHandler {
//    public static void attach(AttachCapabilitiesEvent<ItemStack> event, Callable<IConfig> moduleConfigGetterIn) {
//        final ItemStack itemStack = event.getObject();
//
//        final TickingScanner scanner = new TickingScanner(itemStack, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, moduleConfigGetterIn);
//        final LazyOptional<IPowerModule> scannerHolder = LazyOptional.of(()-> scanner);
//
//        event.addCapability(new ResourceLocation("scannable:scanner"), new ICapabilityProvider() {
//            @Nonnull
//            @Override
//            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//                final LazyOptional<T> scannerCapability = PowerModuleCapability.POWER_MODULE.orEmpty(cap, scannerHolder);
//                if (cap == PowerModuleCapability.POWER_MODULE) {
//                    return scannerCapability;
//                }
//                return LazyOptional.empty();
//            }
//        });
//    }
//
//    public static boolean isScanner(@Nonnull ItemStack itemStack) {
//        return ItemScanner.isScanner(ItemUtils.getActiveModuleOrEmpty(itemStack));
//    }
//}