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

package com.github.lehjr.powersuits.basemod;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.github.lehjr.numina.config.ConfigHelper;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.ToggleableModule;
import com.github.lehjr.powersuits.client.control.KeybindKeyHandler;
import com.github.lehjr.powersuits.client.event.ClientTickHandler;
import com.github.lehjr.powersuits.client.event.ModelBakeEventHandler;
import com.github.lehjr.powersuits.client.event.RenderEventHandler;
import com.github.lehjr.powersuits.client.gui.modding.module.install_salvage.InstallSalvageGui;
import com.github.lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import com.github.lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import com.github.lehjr.powersuits.client.render.entity.RailGunBoltRenderer;
import com.github.lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.github.lehjr.powersuits.container.InstallSalvageContainer;
import com.github.lehjr.powersuits.event.*;
import com.github.lehjr.powersuits.network.MPSPackets;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkHooks;

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

        // Register the doClientStuff method for modloading
        modEventBus.addListener(this::setupClient);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EntityDamageEvent::handleEntityDamageEvent);
        MinecraftForge.EVENT_BUS.addListener(EntityDamageEvent::entityAttackEventHandler);
        MinecraftForge.EVENT_BUS.register(new PlayerUpdateHandler());
        MinecraftForge.EVENT_BUS.register(MovementManager.INSTANCE);

        MinecraftForge.EVENT_BUS.addListener(HarvestEventHandler::handleHarvestCheck);
        MinecraftForge.EVENT_BUS.addListener(HarvestEventHandler::handleBreakSpeed);

        MPSObjects.ITEMS.register(modEventBus);
        MPSObjects.BLOCKS.register(modEventBus);
        MPSObjects.TILE_TYPES.register(modEventBus);
        MPSObjects.ENTITY_TYPES.register(modEventBus);
        MPSObjects.CONTAINER_TYPES.register(modEventBus);

        // handles loading and reloading event
        modEventBus.addListener((ModConfig.ModConfigEvent event) -> {
            new RuntimeException("Got config " + event.getConfig() + " name " + event.getConfig().getModId() + ":" + event.getConfig().getFileName());

            final ModConfig config = event.getConfig();
            if (config.getSpec() == MPSSettings.SERVER_SPEC) {
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
        modEventBus.addListener(RenderEventHandler.INSTANCE::preTextureStitch);

        MinecraftForge.EVENT_BUS.register(RenderEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new KeybindKeyHandler());
        MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());

        MinecraftForge.EVENT_BUS.addListener(PlayerLoginHandler::onPlayerLoginClient);// just to populated keybinds -_-

        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);

//        ScreenManager.registerFactory(MPSObjects.MODULE_CONFIG_CONTAINER_TYPE, TinkerModuleGui::new);
//        ScreenManager.register(MPSObjects.SALVAGE_CRAFT_CONTAINER_TYPE.get(), CraftInstallSalvageGui::new);

        ScreenManager.register(MPSObjects.INSTALL_SALVAGE_CONTAINER_TYPE.get(), InstallSalvageGui::new);



//        ScreenManager.register(MPSObjects.TINKERTABLE_CONTAINER_TYPE.get(), TinkerTableGui::new); // FIXME: replace with module craft/install/salvage GUI + module tweak GUI

//        ScreenManager.register(NuminaObjects.NUMINA_CRAFTING_CONTAINER_TYPE.get(), TinkerCraftingGUI::new);


//        ClientRegistry.bindTileEntityRenderer(MPSObjects.TINKER_TABLE_TILE_TYPE.get(), TinkerTableRenderer::new); //?
    }

    /**
     * Attach capabilities to a few existing items in order to use them as modules
     */
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<ItemStack> event) {
        // Clock
        if (!event.getCapabilities().containsKey(MPSRegistryNames.CLOCK_MODULE_REG) && event.getObject().getItem().equals(Items.CLOCK)) {
            final ItemStack stack = event.getObject();

            IToggleableModule clock = new ToggleableModule(stack, EnumModuleCategory.SPECIAL, EnumModuleTarget.HEADONLY, MPSSettings::getModuleConfig, true);
            event.addCapability(MPSRegistryNames.CLOCK_MODULE_REG, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == PowerModuleCapability.POWER_MODULE) {
                        return LazyOptional.of(()->(T)clock);
                    }
                    return LazyOptional.empty();
                }
            });

            // Compass
        } else if (!event.getCapabilities().containsKey(MPSRegistryNames.COMPASS_MODULE_REG) && event.getObject().getItem().equals(Items.COMPASS)) {
            final ItemStack stack = event.getObject();
            IToggleableModule compass = new ToggleableModule(stack, EnumModuleCategory.SPECIAL, EnumModuleTarget.HEADONLY, MPSSettings::getModuleConfig, true);

            event.addCapability(MPSRegistryNames.COMPASS_MODULE_REG, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == PowerModuleCapability.POWER_MODULE) {
                        return LazyOptional.of(()->(T)compass);
                    }
                    return LazyOptional.empty();
                }
            });

            // Crafting workbench
        } else if (!event.getCapabilities().containsKey(MPSRegistryNames.PORTABLE_WORKBENCH_MODULE_REG) && event.getObject().getItem().equals(Items.CRAFTING_TABLE)) {
            final ItemStack stack = event.getObject();
            final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.crafting");
            IRightClickModule rightClick = new RightClickModule(stack, EnumModuleCategory.TOOL, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {
                @Override
                public ActionResult onItemRightClick(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                    if (worldIn.isClientSide) {
                        return ActionResult.success(itemStackIn);
                    } else {
//                        playerIn.openMenu(                            new SimpleNamedContainerProvider((id, inven, player) -> new WorkbenchContainer(id, inven, IWorldPosCallable.create(player.level, player.blockPosition())), MPSConstants.CRAFTING_TABLE_CONTAINER_NAME));
                        INamedContainerProvider container = new SimpleNamedContainerProvider((id, inven, player) -> new WorkbenchContainer(id, inven, /* IWorldPosCallable.create(player.level, player.blockPosition()) */ IWorldPosCallable.NULL) {
                            @Override
                            public void slotsChanged(IInventory p_75130_1_) {
                                WorkbenchContainer.slotChangedCraftingGrid(this.containerId, this.player.level, this.player, this.craftSlots, this.resultSlots);
                            }

                            @Override
                            public void removed(PlayerEntity p_75134_1_) {
                                super.removed(p_75134_1_);
                                this.resultSlots.clearContent();
                                if (!p_75134_1_.level.isClientSide) {
                                    this.clearContainer(p_75134_1_, p_75134_1_.level, this.craftSlots);
                                }
                            }

                            @Override
                            public boolean stillValid(PlayerEntity p_75145_1_) {
                                return true;
                            }

                            public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
                                ItemStack itemstack = ItemStack.EMPTY;
                                Slot slot = this.slots.get(p_82846_2_);
                                if (slot != null && slot.hasItem()) {
                                    ItemStack itemstack1 = slot.getItem();
                                    itemstack = itemstack1.copy();
                                    if (p_82846_2_ == 0) {
//                                        this.access.execute((p_217067_2_, p_217067_3_) -> {
//                                            itemstack1.getItem().onCraftedBy(itemstack1, p_217067_2_, p_82846_1_);
//                                        });
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

                                    ItemStack itemstack2 = slot.onTake(p_82846_1_, itemstack1);
                                    if (p_82846_2_ == 0) {
                                        p_82846_1_.drop(itemstack2, false);
                                    }
                                }

                                return itemstack;
                            }


                        }, MPSConstants.CRAFTING_TABLE_CONTAINER_NAME);
                        NetworkHooks.openGui((ServerPlayerEntity) playerIn, container, buffer -> buffer.writeBlockPos(playerIn.blockPosition()));
                        NetworkHooks.openGui((ServerPlayerEntity) playerIn, container);//, buffer -> buffer.writeBlockPos(playerIn.blockPosition()));

//                        NetworkHooks.openGui((ServerPlayerEntity) playerIn, new SimpleNamedContainerProvider((id, inven, player) -> new WorkbenchContainer(id, inven, IWorldPosCallable.create(player.level, player.blockPosition())), MPSConstants.CRAFTING_TABLE_CONTAINER_NAME));
                        playerIn.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                        return ActionResult.consume(itemStackIn);
                    }



//                    if (!worldIn.isClientSide()) {
//                        NetworkHooks.openGui((ServerPlayerEntity) playerIn,
//                            new SimpleNamedContainerProvider((id, inven, player) -> new WorkbenchContainer(id, inven, IWorldPosCallable.create(player.level, player.blockPosition())), MPSConstants.CRAFTING_TABLE_CONTAINER_NAME));
//                        //                                new SimpleNamedContainerProvider((id, inventory, player) ->
////                                        new WorkbenchContainer(id, inventory)/*, IWorldPosCallable.of(worldIn, playerIn.getPosition()))*/, MPSConstants.CRAFTING_TABLE_CONTAINER_NAME));
//                        return ActionResult.consume(itemStackIn);
//                    }
//                    return ActionResult.success(itemStackIn);


                }
            };

            event.addCapability(MPSRegistryNames.PORTABLE_WORKBENCH_MODULE_REG, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == PowerModuleCapability.POWER_MODULE) {
                        return LazyOptional.of(()->(T)rightClick);
                    }
                    return LazyOptional.empty();
                }
            });
        }
    }
}