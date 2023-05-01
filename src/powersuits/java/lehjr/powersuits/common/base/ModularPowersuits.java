/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.base;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import lehjr.numina.common.config.ConfigHelper;
import lehjr.numina.common.item.ItemUtils;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.event.ModelBakeEventHandler;
import lehjr.powersuits.client.event.RenderEventHandler;
import lehjr.powersuits.client.gui.modding.module.install_salvage.InstallSalvageGui;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.event.EntityDamageEvent;
import lehjr.powersuits.common.event.HarvestEventHandler;
import lehjr.powersuits.common.event.LogoutEventHandler;
import lehjr.powersuits.common.network.MPSPackets;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod(MPSConstants.MOD_ID)
public class ModularPowersuits {
    public ModularPowersuits() {
        // Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MPSSettings.CLIENT_SPEC, ConfigHelper.setupConfigFile("mps-client-only.toml", MPSConstants.MOD_ID).getAbsolutePath());
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MPSSettings.SERVER_SPEC); // note config file location for dedicated server is stored in the world config

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        MPSItems.MPS_ITEMS.register(modEventBus);
        MPSBlocks.BLOCKS.register(modEventBus);
        MPSBlocks.BLOCKENTITY_TYPES.register(modEventBus);
        MPSEntities.ENTITY_TYPES.register(modEventBus);
        MPSMenuTypes.CONTAINER_TYPES.register(modEventBus);

        // Register the doClientStuff method for modloading
        modEventBus.addListener(this::setupClient);
//
//        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EntityDamageEvent::handleEntityDamageEvent); // FIXME: does nothing
        MinecraftForge.EVENT_BUS.addListener(EntityDamageEvent::entityAttackEventHandler); // FIXME: does nothing
//        MinecraftForge.EVENT_BUS.register(new PlayerUpdateHandler());
////        MinecraftForge.EVENT_BUS.register(MovementManager.INSTANCE);
////
        MinecraftForge.EVENT_BUS.addListener(HarvestEventHandler::handleHarvestCheck);
        MinecraftForge.EVENT_BUS.addListener(HarvestEventHandler::handleBreakSpeed);


        // handles loading and reloading event
        modEventBus.addListener((ModConfigEvent event) -> {
            new RuntimeException("Got config " + event.getConfig() + " name " + event.getConfig().getModId() + ":" + event.getConfig().getFileName());

            final ModConfig config = event.getConfig();
            if (config.getSpec()!= null && config.getSpec() == MPSSettings.SERVER_SPEC) {
                MPSSettings.getModuleConfig().setServerConfig(config);

                // This is actually for a feature that isn't even currently enabled :P
                // getFullPath can't be used on client if it isn't also hosting the server
                if (config instanceof CommentedFileConfig) {
                    CosmeticPresetSaveLoad.setConfigDirString(config.getFullPath().getParent().toString());
                    CosmeticPresetSaveLoad.copyPresetsFromJar(config.getFullPath().getParent().toString());
                }
            }
        });
    }

    /**
     * Setup common (clien/server) stuff
     */
    private void setup(final FMLCommonSetupEvent event) {
        MPSPackets.registerMPAPackets();
    }

    /**
     * Setup client related stuff
     */
    private void setupClient(final FMLClientSetupEvent event) {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onModelBake);
        modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onAddAdditional);
        modEventBus.addListener(RenderEventHandler.INSTANCE::preTextureStitch);

        MinecraftForge.EVENT_BUS.register(RenderEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new KeymappingKeyHandler());
        MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());

        MenuScreens.register(MPSMenuTypes.INSTALL_SALVAGE_MENU_TYPE.get(), InstallSalvageGui::new);

    }

    /**
     * Attach capabilities to a few existing items in order to use them as modules
     */
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<ItemStack> event) {
        final ItemStack itemStack = event.getObject();
        final ResourceLocation regName = ItemUtils.getRegistryName(itemStack);

        // AE2 Wireless terminal
        if (regName.equals(new ResourceLocation("appliedenergistics2:wireless_terminal"))) {
            IRightClickModule ae2wirelessterminal = new RightClickModule(itemStack, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {
                @Override
                public InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
                    // FIXME!!!
//                    Api.instance().registries().wireless().openWirelessTerminalGui(itemStackIn, worldIn, playerIn, hand);
                    return new InteractionResultHolder<>(InteractionResult.sidedSuccess(worldIn.isClientSide()), itemStackIn);
                }
            };

            event.addCapability(regName, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == NuminaCapabilities.POWER_MODULE) {
                        return LazyOptional.of(() -> (T) ae2wirelessterminal);
                    }
                    return LazyOptional.empty();
                }
            });
        }

        // Scannable scanner
//        if (regName.equals(new ResourceLocation("scannable:scanner"))) {
//            ScannableHandler.attach(event, MPSSettings::getModuleConfig);
//        }
//
//        if(regName.getNamespace().contains("refinedstorage") && regName.getPath().contains("wireless")) {
//            RSWirelessHandler.attach(event, MPSSettings::getModuleConfig);
////            System.out.println("regname" + regName);
////            /*
////            refinedstorage:wireless_grid
////            refinedstorage:wireless_fluid_grid
////            refinedstorage:wireless_grid
////            refinedstorage:wireless_fluid_grid
////            refinedstorage:wireless_grid
////            refinedstorage:wireless_fluid_grid
////            refinedstorage:wireless_grid
////             */
////
//        }



        // Clock
        if (!event.getCapabilities().containsKey(MPSRegistryNames.CLOCK_MODULE) && event.getObject().getItem().equals(Items.CLOCK)) {
            IToggleableModule clock = new ToggleableModule(itemStack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig, true);
            event.addCapability(MPSRegistryNames.CLOCK_MODULE, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == NuminaCapabilities.POWER_MODULE) {
                        clock.loadCapValues();
                        return LazyOptional.of(()->(T)clock);
                    }
                    return LazyOptional.empty();
                }
            });

        // Compass
        } else if (!event.getCapabilities().containsKey(MPSRegistryNames.COMPASS_MODULE) && event.getObject().getItem().equals(Items.COMPASS)) {
            IToggleableModule compass = new ToggleableModule(itemStack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig, true);

            event.addCapability(MPSRegistryNames.COMPASS_MODULE, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == NuminaCapabilities.POWER_MODULE) {
                        compass.loadCapValues();
                        return LazyOptional.of(()->(T)compass);
                    }
                    return LazyOptional.empty();
                }
            });

        // Crafting workbench
        } else if (!event.getCapabilities().containsKey(MPSRegistryNames.PORTABLE_WORKBENCH_MODULE) && event.getObject().getItem().equals(Items.CRAFTING_TABLE)) {
            final Component CONTAINER_NAME = Component.translatable("container.crafting");
            IRightClickModule rightClick = new RightClickModule(itemStack, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {
                @Override
                public InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
                    if (worldIn.isClientSide) {
                        return InteractionResultHolder.sidedSuccess(itemStackIn, worldIn.isClientSide);
                    } else {
                        SimpleMenuProvider container = new SimpleMenuProvider((id, inven, player) -> new CraftingMenu(id, inven, ContainerLevelAccess.NULL) {
                            @Override
                            public void slotsChanged(Container inventory) {
                                    slotChangedCraftingGrid(this, player.level, this.player, this.craftSlots, this.resultSlots);
                            }

                            @Override
                            public void removed(Player player) {
                                super.removed(player);
                                this.resultSlots.clearContent();
                                if (!player.level.isClientSide) {
                                    this.clearContainer(player, this.craftSlots);
                                }
                            }

                            @Override
                            public boolean stillValid(Player player) {
                                return true;
                            }

                            public ItemStack quickMoveStack(Player pPlayer, int p_82846_2_) {
                                ItemStack itemstack = ItemStack.EMPTY;
                                Slot slot = this.slots.get(p_82846_2_);
                                if (slot != null && slot.hasItem()) {
                                    ItemStack itemstack1 = slot.getItem();
                                    itemstack = itemstack1.copy();
                                    if (p_82846_2_ == 0) {
                                        if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                                            return ItemStack.EMPTY;
                                        }

                                        slot.onQuickCraft(itemstack1, itemstack);
                                    } else if (p_82846_2_ >= 10 && p_82846_2_ < 46) {
                                        if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                                            if (p_82846_2_ < 37) {
                                                if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                                                    return ItemStack.EMPTY;
                                                }
                                            } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                                                return ItemStack.EMPTY;
                                            }
                                        }
                                    } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                                        return ItemStack.EMPTY;
                                    }

                                    if (itemstack1.isEmpty()) {
                                        slot.set(ItemStack.EMPTY);
                                    } else {
                                        slot.setChanged();
                                    }

                                    if (itemstack1.getCount() == itemstack.getCount()) {
                                        return ItemStack.EMPTY;
                                    }

                                    slot.onTake(pPlayer, itemstack1);
                                    if (p_82846_2_ == 0) {
                                        pPlayer.drop(itemstack1, false);
                                    }
                                }
                                return itemstack;
                            }
                        }, MPSConstants.CRAFTING_TABLE_CONTAINER_NAME);
//                        NetworkHooks.openScreen((ServerPlayer) playerIn, container, buffer -> buffer.writeBlockPos(playerIn.blockPosition()));
                        NetworkHooks.openScreen((ServerPlayer) playerIn, container);
                        playerIn.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                        return InteractionResultHolder.consume(itemStackIn);
                    }
                }
            };

            event.addCapability(MPSRegistryNames.PORTABLE_WORKBENCH_MODULE, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == NuminaCapabilities.POWER_MODULE) {
                        return LazyOptional.of(()->(T)rightClick);
                    }
                    return LazyOptional.empty();
                }
            });
        }
    }
}