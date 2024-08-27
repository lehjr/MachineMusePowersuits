package com.lehjr.powersuits.client.gui.module.install_salvage;

import com.lehjr.numina.client.gui.ExtendedContainerScreen;
import com.lehjr.numina.client.gui.frame.ModularItemSelectionFrameContainered;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.client.gui.common.ScrollableInventoryFrame2;
import com.lehjr.powersuits.client.gui.common.TabSelectFrame;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.container.InstallSalvageMenu;
import com.lehjr.powersuits.common.network.MPSPackets;
import com.lehjr.powersuits.common.network.packets.serverbound.CreativeInstallPacketServerBound;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class InstallSalvageGui extends ExtendedContainerScreen<InstallSalvageMenu> {
    Component modularItemInventoryLabel = Component.translatable("gui." + MPSConstants.MOD_ID + ".modularitem.inventory");
    Component moduleSelectionFrameLabel = Component.translatable(MPSConstants.GUI_COMPATIBLE_MODULES);
    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "textures/gui/background/install_salvage.png");
    protected TabSelectFrame tabSelectFrame;
    ModularItemSelectionFrameContainered<InstallSalvageMenu> itemSelectFrame;

    ScrollableInventoryFrame2<InstallSalvageMenu> modularItemInventory;
    protected CompatibleModuleDisplayFrame moduleSelectFrame;

    Inventory playerInventory;

    public InstallSalvageGui(InstallSalvageMenu container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title, 352, 217);
        this.playerInventory = playerInventory;
    }

    @Override
    public void init() {
        super.init();
        this.inventoryLabelX = this.imageWidth - 170;
        this.inventoryLabelY = this.imageHeight - 94;
        this.titleLabelX = 12;
        this.titleLabelY = 5;

        // clickable buttons on the top for selecting GUI -------------------------------------------------------------
        tabSelectFrame = new TabSelectFrame(this.leftPos, this.topPos, this.imageWidth, playerInventory.player, 0);
        tabSelectFrame.setPosition(center());
        tabSelectFrame.setBottom(topPos);
        addFrame(tabSelectFrame);

        // the buttons that select the equipped modular item if any ---------------------------------------------------
        itemSelectFrame = new ModularItemSelectionFrameContainered<>(menu, new MusePoint2D(leftPos - 30, topPos), menu.getEquipmentSlot());
        addFrame(itemSelectFrame);

        /// frame to display and allow selecting of installed modules --------------------------------------------------
        moduleSelectFrame = new CompatibleModuleDisplayFrame(itemSelectFrame, new Rect(leftPos + 8, topPos + 13, leftPos + 172, topPos + 208));
        itemSelectFrame.getCreativeInstallButton().setOnPressed(pressed -> {
            itemSelectFrame.getCreativeInstallButton().playDownSound(Minecraft.getInstance().getSoundManager());
            if (moduleSelectFrame.getSelectedModule() != null) {
                MPSPackets.sendToServer(new CreativeInstallPacketServerBound(itemSelectFrame.selectedType().get(), ItemUtils.getRegistryName(moduleSelectFrame.getSelectedModule().getModule())));
            }
        });

        // not perfect but better than nothing, I think
        itemSelectFrame.getCreativeInstallAllButton().setOnPressed(pressed->{
            IModularItem modularItemCap = itemSelectFrame.getModularItemCapability();
            if (modularItemCap != null) {
                itemSelectFrame.getCreativeInstallButton().playDownSound(Minecraft.getInstance().getSoundManager());
                NonNullList<ItemStack> compatibleModules = getCompatibleModuleList();
                for(ItemStack module : compatibleModules) {
                    IPowerModule powerModule = modularItemCap.getModuleCapability(module);
                    if (powerModule != null) {
                        ModuleCategory category = powerModule.getCategory();
                        int tier = powerModule.getTier();
                        if(tier == -1 || (tier >= 4 &&(category == ModuleCategory.ARMOR || category == ModuleCategory.ENERGY_STORAGE))) {
                            MPSPackets.sendToServer(new CreativeInstallPacketServerBound(itemSelectFrame.selectedType().get(), ItemUtils.getRegistryName(module)));
                        }
                    }
                }
            }
        });

        addFrame(moduleSelectFrame);

        boolean keepOnReload = moduleSelectFrame != null;
        // inventory frame for the modular item inventory. Changes with different selected modular items --------------
        modularItemInventory = new ScrollableInventoryFrame2<>(menu, new Rect(178,14,353,118), itemSelectFrame, this.ulGetter());
        moduleSelectFrame.loadModules(keepOnReload);
        addFrame(modularItemInventory);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        // FIXME: replace button with something more native
        if(Objects.requireNonNull(getMinecraft().player).isCreative()) {
            itemSelectFrame.getCreativeInstallButton().enableAndShow();
            itemSelectFrame.getCreativeInstallAllButton().enableAndShow();
        } else {
            itemSelectFrame.getCreativeInstallButton().disableAndHide();
            itemSelectFrame.getCreativeInstallAllButton().disableAndHide();
        }
    }

    NonNullList<ItemStack> getCompatibleModuleList() {
        return moduleSelectFrame.getPossibleItems();
    }

    @Override
    public void render(@Nonnull GuiGraphics gfx, int mouseX, int mouseY, float frameTime) {
//        this.renderBackground(gfx, mouseX, mouseY, frameTime);
        super.render(gfx, mouseX, mouseY, frameTime);
        this.renderTooltip(gfx, mouseX, mouseY);
    }

    @Override
    public void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawString(this.font,
                this.moduleSelectionFrameLabel,
                this.titleLabelX,
                this.titleLabelY,
                4210752, false);

        gfx.drawString(this.font,
                this.modularItemInventoryLabel,
                this.titleLabelX + 173,
                this.titleLabelY, 4210752, false);

        gfx.drawString(this.font,
                this.playerInventory.getDisplayName(),
                this.inventoryLabelX,
                inventoryLabelY,
                4210752,
                false);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.leftPos;
        int j = this.topPos;
        gfx.blit(BACKGROUND, i, j, 0, 0, imageWidth, imageHeight, 512, 512);
        super.renderBg(gfx, partialTick, mouseX, mouseY);
    }
}