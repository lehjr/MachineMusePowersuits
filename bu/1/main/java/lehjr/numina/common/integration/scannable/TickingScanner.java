//package com.lehjr.numina.common.integration.scannable;
//
//import com.lehjr.numina.common.capabilities.module.powermodule.IConfig;
//import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
//import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
//import com.lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
//import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
//import com.lehjr.numina.common.energy.ElectricItemUtils;
//import com.lehjr.numina.common.utils.ItemUtils;
//import li.cil.scannable.api.scanning.ScannerModule;
//import li.cil.scannable.client.ScanManager;
//import li.cil.scannable.client.audio.SoundManager;
//import li.cil.scannable.common.capabilities.CapabilityScannerModule;
//import li.cil.scannable.common.config.Constants;
//import li.cil.scannable.common.config.Settings;
//import li.cil.scannable.common.inventory.ItemHandlerScanner;
//import net.minecraft.client.Minecraft;
//import net.minecraft.core.NonNullList;
//import net.minecraft.entity.player.ServerPlayer;
//import net.minecraft.inventory.container.INamedAbstractContainerMenuProvider;
//import net.minecraft.network.chat.Component;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.SoundEvents;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.network.NetworkHooks;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.IItemHandler;
//
//import javax.annotation.Nonnull;
//import java.util.List;
//import java.util.concurrent.Callable;
//
///**
// * This is mostly a recreation of the ItemScanner in Scannable, but wrapped in a capability
// */
//public class TickingScanner extends PlayerTickModule implements IRightClickModule {
//    public TickingScanner(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> moduleConfigGetterIn) {
//        super(module, category, target, moduleConfigGetterIn, true);
//    }
//
//    @Override
//    public int getUseDuration() {
//        return getModule().getUseDuration();
//    }
//
//    @Override
//    public ActionResult use(ItemStack itemStackIn, Level worldIn, Player playerIn, Hand hand) {
//        final ItemStack module = ItemUtils.getActiveModuleOrEmpty(itemStackIn);
//
//        if (!worldIn.isClientSide()) {
//            int totalEnergy = ElectricItemUtils.getPlayerEnergy(playerIn);
//            int energyNeeded = ElectricItemUtils.chargeItem(module, totalEnergy, true);
//            if (energyNeeded > 0) {
//                energyNeeded = ElectricItemUtils.drainPlayerEnergy(playerIn, energyNeeded, false);
//                ElectricItemUtils.chargeItem(module, energyNeeded, false);
//            }
//        }
//
//        if (playerIn.func_225608_bj_()) {
//            if (!worldIn.isClientSide) {
//                final INamedAbstractContainerMenuProvider containerProvider = new MPSScannerAbstractContainerMenuProvider(playerIn, hand);
//                NetworkHooks.openGui((ServerPlayer) playerIn, containerProvider, buffer -> buffer.func_179249_a(hand));
//            }
//        } else {
//            final NonNullList<ItemStack> modules = collectModules(module);
//            if (modules.isEmpty()) {
//                if (worldIn.isClientSide) {
//                    Minecraft.getInstance().field_71456_v.func_146158_b().func_146227_a(Component.translatable(Constants.MESSAGE_NO_SCAN_MODULES));//, Constants.CHAT_LINE_ID);
//                }
//                playerIn.func_184811_cZ().func_185145_a(itemStackIn.getItem(), 10);
//                return ActionResult.func_226251_d_(itemStackIn);
//            }
//
//            if (!tryConsumeEnergy(playerIn, modules, true)) {
//                if (worldIn.isClientSide) {
//                    Minecraft.getInstance().field_71456_v.func_146158_b().func_146227_a(Component.translatable(Constants.MESSAGE_NOT_ENOUGH_ENERGY));//, Constants.CHAT_LINE_ID);
//                }
//                playerIn.func_184811_cZ().func_185145_a(itemStackIn.getItem(), 10);
//                return ActionResult.func_226251_d_(itemStackIn);
//            }
//
//            playerIn.func_184598_c(hand);
//            if (worldIn.isClientSide) {
//                ScanManager.INSTANCE.beginScan(playerIn, modules);
//                SoundManager.INSTANCE.playChargingSound();
//            }
//        }
//        return ActionResult.func_226248_a_(itemStackIn);
//    }
//
//    @Override
//    public ItemStack finishUsingItem(final ItemStack stack, final Level world, final LivingEntity entity) {
//        if (!(entity instanceof Player)) {
//            return stack;
//        }
//
//        if (world.isClientSide) {
//            SoundCanceler.cancelEquipSound();
//        }
//
//        final NonNullList<ItemStack> modules = collectModules(module);
//        if (modules.isEmpty()) {
//            return stack;
//        }
//
//        final boolean hasEnergy = tryConsumeEnergy((Player) entity, modules, false);
//        if (world.isClientSide) {
//            SoundManager.INSTANCE.stopChargingSound();
//
//            if (hasEnergy) {
//                ScanManager.INSTANCE.updateScan(entity, true);
//                SoundManager.INSTANCE.playActivateSound();
//            } else {
//                ScanManager.INSTANCE.cancelScan();
//            }
//        }
//
//        final Player player = (Player) entity;
//        player.func_184811_cZ().func_185145_a(stack.getItem(), 40);
//        return stack;
//    }
//
//    @Override
//    public void releaseUsing(final ItemStack stack, final Level world, final LivingEntity entity, final int timeLeft) {
//        if (world.isClientSide) {
//            ScanManager.INSTANCE.cancelScan();
//            SoundManager.INSTANCE.stopChargingSound();
//        }
//    }
//
//    @Override
//    public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
//        if (player.isUsingItem() && player.func_130014_f_().isClientSide) {
//            ScanManager.INSTANCE.updateScan(player, false);
//        }
//    }
//
//    @Override
//    public boolean isModuleOnline() {
//        return true;
//    }
//
//    @Override
//    public void loadCapValues() {
//
//    }
//
//    // Used to suppress the re-equip sound after finishing a scan (due to potential scanner item stack data change).
//    private enum SoundCanceler {
//        INSTANCE;
//
//        public static void cancelEquipSound() {
//            MinecraftForge.EVENT_BUS.register(SoundCanceler.INSTANCE);
//        }
//
//        @SubscribeEvent
//        public void onPlaySoundAtEntityEvent(final PlaySoundAtEntityEvent event) {
//            if (event.getSound() == SoundEvents.field_187719_p) {
//                event.setCanceled(true);
//            }
//            MinecraftForge.EVENT_BUS.unregister(this);
//        }
//    }
//
//    private static NonNullList<ItemStack> collectModules(final ItemStack scanner) {
//
//        NonNullList<ItemStack> modules = NonNullList.create();
//        scanner.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                .filter(handler -> handler instanceof ItemHandlerScanner)
//                .ifPresent(handler -> {
//                    final IItemHandler activeModules = ((ItemHandlerScanner) handler).getActiveModules();
//                    boolean hasScannerModules = false;
//                    for (int slot = 0; slot < activeModules.getSlots(); slot++) {
//                        final ItemStack module = activeModules.getStackInSlot(slot);
//                        if (module.isEmpty()) {
//                            continue;
//                        }
//
//                        modules.add(module);
//
//                        final LazyOptional<ScannerModule> capability = module.getCapability(CapabilityScannerModule.SCANNER_MODULE_CAPABILITY);
//                        hasScannerModules |= capability.map(ScannerModule::hasResultProvider).orElse(false);
//                    }
//                    if (!hasScannerModules) {
//                        modules.clear();
//                    }
//                });
//        return modules;
//    }
//
//    private static boolean tryConsumeEnergy(final Player player, final List<ItemStack> modules, final boolean simulate) {
//        if (!Settings.useEnergy) {
//            return true;
//        }
//
//        if (player.isCreative()) {
//            return true;
//        }
//
//        int totalCostAccumulator = 0;
//        for (final ItemStack module : modules) {
//            totalCostAccumulator += getModuleEnergyCost(player, module);
//        }
//
//        final int totalCost = totalCostAccumulator;
//        final int extracted = ElectricItemUtils.drainPlayerEnergy(player, totalCost, simulate);
//        if (extracted < totalCost) {
//            return false;
//        }
//
//        return true;
//    }
//
//    static int getModuleEnergyCost(final Player player, final ItemStack stack) {
//        final LazyOptional<ScannerModule> module = stack.getCapability(CapabilityScannerModule.SCANNER_MODULE_CAPABILITY);
//        return module.map(p -> p.getEnergyCost(player, stack)).orElse(0);
//    }
//}
