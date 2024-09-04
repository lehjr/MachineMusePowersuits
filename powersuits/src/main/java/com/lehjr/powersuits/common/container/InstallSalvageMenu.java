package com.lehjr.powersuits.common.container;

import com.lehjr.numina.client.gui.slot.HideableSlot;
import com.lehjr.numina.client.gui.slot.HideableSlotItemHandler;
import com.lehjr.numina.client.gui.slot.IHideableSlot;
import com.lehjr.numina.client.gui.slot.IIConProvider;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.IconUtils;
import com.lehjr.numina.common.utils.MathUtils;
import com.lehjr.powersuits.common.registration.MPSMenuTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class InstallSalvageMenu extends AbstractContainerMenu {
    EquipmentSlot slotType;
    int mainInventoryStart = 0;
    int hotbarInventoryStart = 0;
    double mouseX;
    double mouseY;
    boolean preserve;

    public InstallSalvageMenu(int containerID, Inventory playerInventory, EquipmentSlot slotType) {
        this(containerID, playerInventory, slotType, false, 0, 0);
    }

    public InstallSalvageMenu(int containerID, Inventory playerInventory, EquipmentSlot slotType, boolean preserve, double mouseX, double mouseY) {
        super(MPSMenuTypes.INSTALL_SALVAGE_MENU_TYPE.get(), containerID);
        this.slotType = slotType;

        this.preserve = preserve;
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        int row, col;
        int parentSlot = slotType == EquipmentSlot.MAINHAND ? playerInventory.selected : equipmentSlotToParent(slotType);
        ItemStack modularItemStack = playerInventory.player.getItemBySlot(slotType);

        IModularItem modularItemCapability = NuminaCapabilities.getModularItemOrModeChangingCapability(modularItemStack);
        if (modularItemCapability != null) {
            addSlots(modularItemCapability, parentSlot);
        }

        mainInventoryStart = this.slots.size();

        // Main inventory
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 178 + col * 18, 133 + row * 18));
            }
        }

        hotbarInventoryStart = this.slots.size();

        // Hotbar with pickup disabled for modular items
        for(col = 0; col < 9; ++col) {
            if (col == playerInventory.selected && modularItemCapability != null) {
                this.addSlot(new Slot(playerInventory, col, 178 + col * 18, 191) {
                    @Override
                    public boolean mayPickup(Player player) {
                        return false;
                    }
                });
            } else {
                this.addSlot(new Slot(playerInventory, col, 178 + col * 18, 191));
            }
        }

        for (Slot slot : this.slots) {
            if(slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();
            }
        }
    }

    void addSlots(IModularItem cap, int parentSlot) {

        int innerrow = 0;
        int innercol = 0;
        for (int modularItemInvIndex = 0; modularItemInvIndex < cap.getSlots(); modularItemInvIndex ++) {
            innercol = modularItemInvIndex % 16;
            innerrow = (modularItemInvIndex - innercol)/16;
            int finalModularItemInvIndex = modularItemInvIndex;

            if (MathUtils.isIntInRange(cap.getRangeForCategory(ModuleCategory.ARMOR), modularItemInvIndex)) {
                // OFFHAND
                addSlot(new IconSlotItemHandler(cap, parentSlot, modularItemInvIndex, 178 + innercol * 18, 14 + innerrow * 18) {

                    @OnlyIn(Dist.CLIENT)
                    @Override
                    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                        return IconUtils.getSlotBackground(EquipmentSlot.OFFHAND);
                    }

                    @OnlyIn(Dist.CLIENT)
                    @Override
                    public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {

                    }

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        NuminaLogger.logDebug("may place <" + stack +"> " + cap.isModuleValidForPlacement(finalModularItemInvIndex, stack));

                        return cap.isModuleValidForPlacement(finalModularItemInvIndex, stack);
                    }
                });

            } else {
                for (ModuleCategory category: ModuleCategory.values()) {
                    if((MathUtils.isIntInRange(cap.getRangeForCategory(category), modularItemInvIndex))) {
                        NuminaLogger.logDebug("category " + category +" is in range for slot: " + modularItemInvIndex);

                        Pair<ResourceLocation, ResourceLocation> iconPair = IconUtils.getIconForCategory(category);
                        if(iconPair != null){ if(iconPair.getFirst().getPath().equals(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS.getPath())) {
                            addSlot(new IconSlotItemHandler(cap, parentSlot, finalModularItemInvIndex, 178 + innercol * 18, 14 + innerrow * 18) {
                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {
                                    // FIXME: fix icon

                                    IconUtils.getIcon().energystorage.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
                                }

                                @Override
                                public boolean mayPlace(ItemStack stack) {
                                    NuminaLogger.logDebug("may place <" + stack + "> " + cap.isModuleValidForPlacement(finalModularItemInvIndex, stack));

                                    return cap.isModuleValidForPlacement(finalModularItemInvIndex, stack);
                                }
                            });
                        } else {
                            addSlot(new HideableSlotItemHandler(cap, parentSlot, modularItemInvIndex, 178 + innercol * 18, 14 + innerrow * 18) {
                                @Override
                                @Nullable
                                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                                    return iconPair;
                                }

                                @Override
                                public boolean mayPlace(ItemStack stack) {
                                    NuminaLogger.logDebug("may place <" + stack +"> " + cap.isModuleValidForPlacement(finalModularItemInvIndex, stack));
                                    return cap.isModuleValidForPlacement(finalModularItemInvIndex, stack);
                                }
                            });
                        }





                        } else {
                            // Generic slot (Category NONE)
                            addSlot(new HideableSlotItemHandler(cap, parentSlot, modularItemInvIndex, 178 + innercol * 18, 14 + innerrow * 18) {
                                @Override
                                public boolean mayPlace(ItemStack stack) {
                                    NuminaLogger.logDebug("may place <" + stack +"> " + cap.isModuleValidForPlacement(finalModularItemInvIndex, stack));
                                    return cap.isModuleValidForPlacement(finalModularItemInvIndex, stack);
                                }
                            });
                        }
                    }
                }
            }

        }
    }

    public boolean preserveMouse() {
        return preserve;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public EquipmentSlot getEquipmentSlot() {
        return slotType;
    }

    int equipmentSlotToParent(EquipmentSlot slotType) {
        switch (slotType) {
        case HEAD:
            return 39;
        case CHEST:
            return 38;
        case LEGS:
            return 37;
        case FEET:
            return 36;
        case OFFHAND:
            return 40;
        default:
            return  -1;
        }
    }

    public int getMainInventoryStart() {
        return mainInventoryStart;
    }

    public int getHotbarInventoryStart() {
        return hotbarInventoryStart;
    }

    // InventoryMenu version
    @Override
    public void removed(Player player) {
        super.removed(player);
    }


    // player container version
    @Override
    public boolean stillValid(Player player) {
        return true;
        //        return this.container.stillValid(player);
    }


    /**
     * Only handles shift clicking
     * @param player
     * @param index
     * @return
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            // hotbar
            if (index < mainInventoryStart) {
                if (!this.moveItemStackTo(itemStack1, mainInventoryStart, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

            } else if (!this.moveItemStackTo(itemStack1, 0, mainInventoryStart, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    abstract static class IconSlotItemHandler extends HideableSlotItemHandler implements IIConProvider {
        public IconSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition) {
            super(itemHandler, parent, index, xPosition, yPosition);
        }

        public IconSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition, boolean isEnabled) {
            super(itemHandler, parent, index, xPosition, yPosition, isEnabled);
        }
    }

    abstract static class IconSlot extends HideableSlot implements IIConProvider {
        public IconSlot(Container container, int parent, int index, int xPosition, int yPosition) {
            super(container, parent, index, xPosition, yPosition);
        }

        public IconSlot(Container container, int parent, int index, int xPosition, int yPosition, boolean isEnabled) {
            super(container, parent, index, xPosition, yPosition, isEnabled);
        }
    }
}

