package lehjr.numina.integration.scannable;

import lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.numina.util.item.ItemUtils;
import li.cil.scannable.api.scanning.ScannerModule;
import li.cil.scannable.client.ScanManager;
import li.cil.scannable.client.audio.SoundManager;
import li.cil.scannable.common.capabilities.CapabilityScannerModule;
import li.cil.scannable.common.config.Constants;
import li.cil.scannable.common.config.Settings;
import li.cil.scannable.common.inventory.ItemHandlerScanner;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * This is mostly a recreation of the ItemScanner in Scannable, but wrapped in a capability
 */
public class TickingScanner extends PlayerTickModule implements IRightClickModule {
    public TickingScanner(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> moduleConfigGetterIn) {
        super(module, category, target, moduleConfigGetterIn, true);
    }

    @Override
    public ActionResult use(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
        final ItemStack module = ItemUtils.getActiveModuleOrEmpty(itemStackIn);

        if (!worldIn.isClientSide()) {
            int totalEnergy = ElectricItemUtils.getPlayerEnergy(playerIn);
            int energyNeeded = ElectricItemUtils.chargeItem(module, totalEnergy, true);
            if (energyNeeded > 0) {
                energyNeeded = ElectricItemUtils.drainPlayerEnergy(playerIn, energyNeeded, false);
                ElectricItemUtils.chargeItem(module, energyNeeded, false);
            }
        }

        if (playerIn.isShiftKeyDown()) {
            if (!worldIn.isClientSide) {
                final INamedContainerProvider containerProvider = new MPSScannerContainerProvider(playerIn, hand);
                NetworkHooks.openGui((ServerPlayerEntity) playerIn, containerProvider, buffer -> buffer.writeEnum(hand));
            }
        } else {
            final NonNullList<ItemStack> modules = collectModules(module);
            if (modules.isEmpty()) {
                if (worldIn.isClientSide) {
                    Minecraft.getInstance().gui.getChat().addMessage(new TranslationTextComponent(Constants.MESSAGE_NO_SCAN_MODULES));//, Constants.CHAT_LINE_ID);
                }
                playerIn.getCooldowns().addCooldown(itemStackIn.getItem(), 10);
                return ActionResult.fail(itemStackIn);
            }

            if (!tryConsumeEnergy(playerIn, modules, true)) {
                if (worldIn.isClientSide) {
                    Minecraft.getInstance().gui.getChat().addMessage(new TranslationTextComponent(Constants.MESSAGE_NOT_ENOUGH_ENERGY));//, Constants.CHAT_LINE_ID);
                }
                playerIn.getCooldowns().addCooldown(itemStackIn.getItem(), 10);
                return ActionResult.fail(itemStackIn);
            }

            playerIn.startUsingItem(hand);
            if (worldIn.isClientSide) {
                ScanManager.INSTANCE.beginScan(playerIn, modules);
                SoundManager.INSTANCE.playChargingSound();
            }
        }
        return ActionResult.success(itemStackIn);
    }

    @Override
    public ItemStack finishUsingItem(final ItemStack stack, final World world, final LivingEntity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return stack;
        }

        if (world.isClientSide) {
            SoundCanceler.cancelEquipSound();
        }

        final ItemStack module = ItemUtils.getActiveModuleOrEmpty(stack);

        final NonNullList<ItemStack> modules = collectModules(module);
        if (modules.isEmpty()) {
            return stack;
        }

        final boolean hasEnergy = tryConsumeEnergy((PlayerEntity) entity, modules, false);



        if (world.isClientSide) {
            SoundManager.INSTANCE.stopChargingSound();

            if (hasEnergy) {
                ScanManager.INSTANCE.updateScan(entity, true);
                SoundManager.INSTANCE.playActivateSound();
            } else {
                ScanManager.INSTANCE.cancelScan();
            }
        }

        final PlayerEntity player = (PlayerEntity) entity;
        player.getCooldowns().addCooldown(stack.getItem(), 40);
        return stack;
    }

    @Override
    public void releaseUsing(final ItemStack stack, final World world, final LivingEntity entity, final int timeLeft) {
        if (world.isClientSide) {
            ScanManager.INSTANCE.cancelScan();
            SoundManager.INSTANCE.stopChargingSound();
        }
    }

    @Override
    public void onPlayerTickActive(PlayerEntity player, @Nonnull ItemStack item) {
        if (player.getCommandSenderWorld().isClientSide) {
            ScanManager.INSTANCE.updateScan(player, false);
        }
    }

    @Override
    public boolean isModuleOnline() {
        return true;
    }

    @Override
    public void updateFromNBT() {

    }

    // Used to suppress the re-equip sound after finishing a scan (due to potential scanner item stack data change).
    private enum SoundCanceler {
        INSTANCE;

        public static void cancelEquipSound() {
            MinecraftForge.EVENT_BUS.register(SoundCanceler.INSTANCE);
        }

        @SubscribeEvent
        public void onPlaySoundAtEntityEvent(final PlaySoundAtEntityEvent event) {
            if (event.getSound() == SoundEvents.ARMOR_EQUIP_GENERIC) {
                event.setCanceled(true);
            }
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    private static NonNullList<ItemStack> collectModules(final ItemStack scanner) {

        NonNullList<ItemStack> modules = NonNullList.create();
        scanner.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(handler -> handler instanceof ItemHandlerScanner)
                .ifPresent(handler -> {
                    final IItemHandler activeModules = ((ItemHandlerScanner) handler).getActiveModules();
                    boolean hasScannerModules = false;
                    for (int slot = 0; slot < activeModules.getSlots(); slot++) {
                        final ItemStack module = activeModules.getStackInSlot(slot);
                        if (module.isEmpty()) {
                            continue;
                        }

                        modules.add(module);

                        final LazyOptional<ScannerModule> capability = module.getCapability(CapabilityScannerModule.SCANNER_MODULE_CAPABILITY);
                        hasScannerModules |= capability.map(ScannerModule::hasResultProvider).orElse(false);
                    }
                    if (!hasScannerModules) {
                        modules.clear();
                    }
                });
        return modules;
    }

    private static boolean tryConsumeEnergy(final PlayerEntity player, final List<ItemStack> modules, final boolean simulate) {
        if (!Settings.useEnergy) {
            return true;
        }

        if (player.isCreative()) {
            return true;
        }

        int totalCostAccumulator = 0;
        for (final ItemStack module : modules) {
            totalCostAccumulator += getModuleEnergyCost(player, module);
        }

        final int totalCost = totalCostAccumulator;
        final int extracted = ElectricItemUtils.drainPlayerEnergy(player, totalCost, simulate);
        if (extracted < totalCost) {
            return false;
        }

        return true;
    }


    static int getModuleEnergyCost(final PlayerEntity player, final ItemStack stack) {
        final LazyOptional<ScannerModule> module = stack.getCapability(CapabilityScannerModule.SCANNER_MODULE_CAPABILITY);
        return module.map(p -> p.getEnergyCost(player, stack)).orElse(0);
    }
}
