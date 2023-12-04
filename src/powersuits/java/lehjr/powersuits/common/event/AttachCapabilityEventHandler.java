package lehjr.powersuits.common.event;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.externalitems.OtherModItemsAsModules;
import lehjr.numina.common.capabilities.module.hud.HudModule;
import lehjr.numina.common.capabilities.module.hud.IHudModule;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.common.item.ItemUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class AttachCapabilityEventHandler {
    public static void attachCapability(AttachCapabilitiesEvent<ItemStack> event) {
        final ItemStack itemStack = event.getObject();
        final ResourceLocation regName = ItemUtils.getRegistryName(itemStack);
        Map<ResourceLocation, ResourceLocation> externalTools = MPSSettings.getExternalModItemsAsToolModules();
        Map<ResourceLocation, ResourceLocation> externalWeapons = MPSSettings.getExternalModItemsAsWeaponModules();

        // Clock
        if (!event.getCapabilities().containsKey(MPSRegistryNames.CLOCK_MODULE) && event.getObject().getItem().equals(Items.CLOCK)) {
            IHudModule clock = new HudModule(itemStack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig, true);
            event.addCapability(MPSRegistryNames.CLOCK_MODULE, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == NuminaCapabilities.POWER_MODULE) {
                        clock.loadCapValues();
                        return LazyOptional.of(() -> (T) clock);
                    }
                    return LazyOptional.empty();
                }
            });

            // Compass
        } else if (!event.getCapabilities().containsKey(MPSRegistryNames.COMPASS_MODULE) && event.getObject().getItem().equals(Items.COMPASS)) {
            IHudModule compass = new HudModule(itemStack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig, true);

            event.addCapability(MPSRegistryNames.COMPASS_MODULE, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == NuminaCapabilities.POWER_MODULE) {
                        compass.loadCapValues();
                        return LazyOptional.of(() -> (T) compass);
                    }
                    return LazyOptional.empty();
                }
            });

            // Recovery Compass
        } else if (!event.getCapabilities().containsKey(MPSRegistryNames.RECOVERY_COMPASS) && event.getObject().getItem().equals(Items.RECOVERY_COMPASS)) {
            IHudModule compass = new HudModule(itemStack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig, true);
            event.addCapability(MPSRegistryNames.RECOVERY_COMPASS, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == NuminaCapabilities.POWER_MODULE) {
                        compass.loadCapValues();
                        return LazyOptional.of(() -> (T) compass);
                    }
                    return LazyOptional.empty();
                }
            });

            // AE2 Meteorite Compass (WIP only handheld model shows needle direction)
        } else if (!event.getCapabilities().containsKey(MPSRegistryNames.AE2_METEOR_COMPASS) && event.getObject().getItem().equals(ForgeRegistries.ITEMS.getValue(new ResourceLocation("ae2:meteorite_compass")))) {
            IHudModule compass = new HudModule(itemStack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig, true);
            event.addCapability(MPSRegistryNames.AE2_METEOR_COMPASS, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == NuminaCapabilities.POWER_MODULE) {
                        compass.loadCapValues();
                        return LazyOptional.of(() -> (T) compass);
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

                            public ItemStack quickMoveStack(Player pPlayer, int index) {
                                ItemStack itemstack = ItemStack.EMPTY;
                                Slot slot = this.slots.get(index);
                                if (slot != null && slot.hasItem()) {
                                    ItemStack itemstack1 = slot.getItem();
                                    itemstack = itemstack1.copy();
                                    if (index == 0) {
                                        if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                                            return ItemStack.EMPTY;
                                        }

                                        slot.onQuickCraft(itemstack1, itemstack);
                                    } else if (index >= 10 && index < 46) {
                                        if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                                            if (index < 37) {
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
                                    if (index == 0) {
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

        ResourceLocation capName = MPSRegistryNames.getRegName(itemStack.getDescriptionId());
        if (externalTools.containsKey(regName) && !event.getCapabilities().containsKey(capName)) {
            addCapabilityToItem(event, capName, itemStack, ModuleCategory.TOOL);
        } else if (externalWeapons.containsKey(regName) && !event.getCapabilities().containsKey(capName)) {
            addCapabilityToItem(event, capName, itemStack, ModuleCategory.WEAPON);
        }
    }

    static void addCapabilityToItem(AttachCapabilitiesEvent<ItemStack> event, ResourceLocation capName, @Nonnull ItemStack module, ModuleCategory category) {
        event.addCapability(capName, new ICapabilityProvider() {
            final OtherModItemsAsModules powerModule = new OtherModItemsAsModules(module, category, MPSSettings::getModuleConfig) {
                @Override
                public boolean isAllowed() {
                    return true;
                }
            };

            final LazyOptional<IPowerModule> powerModuleHolder = LazyOptional.of(() -> powerModule);

            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @org.jetbrains.annotations.Nullable Direction side) {
                final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
                if (powerModuleCapability.isPresent()) {
                    return powerModuleCapability;
                }
                return LazyOptional.empty();
            }
        });
    }
}
