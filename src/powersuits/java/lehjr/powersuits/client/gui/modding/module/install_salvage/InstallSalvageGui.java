package lehjr.powersuits.client.gui.modding.module.install_salvage;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.ExtendedContainerScreen;
import lehjr.numina.client.gui.clickable.button.VanillaButton;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrameContainered;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.item.ItemUtils;
import lehjr.powersuits.client.gui.ScrollableInventoryFrame2;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.container.InstallSalvageMenu;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.clientbound.CreativeInstallPacketClientBound;
import lehjr.powersuits.common.network.packets.serverbound.CreativeInstallPacketServerBound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class InstallSalvageGui extends ExtendedContainerScreen<InstallSalvageMenu> {
    Component modularItemInventoryLabel = Component.translatable("gui." + MPSConstants.MOD_ID + ".modularitem.inventory");
    Component moduleSelectionFrameLabel = Component.translatable(MPSConstants.GUI_COMPATIBLE_MODULES);
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/background/install_salvage.png");
    protected TabSelectFrame tabSelectFrame;
    ModularItemSelectionFrameContainered<InstallSalvageMenu> itemSelectFrame;

    ScrollableInventoryFrame2<InstallSalvageMenu> modularItemInventory;
    protected CompatibleModuleDisplayFrame moduleSelectFrame;
    VanillaButton button;

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

        /** clickable buttons on the top for selecting GUI ------------------------------------------------------------- */
        tabSelectFrame = new TabSelectFrame(this.leftPos, this.topPos, this.imageWidth, playerInventory.player, 0);
        tabSelectFrame.setPosition(center());
        tabSelectFrame.setBottom(topPos);
        addFrame(tabSelectFrame);

        /** the buttons that select the equipped modular item if any --------------------------------------------------- */
        itemSelectFrame = new ModularItemSelectionFrameContainered<>(menu, new MusePoint2D(leftPos - 30, topPos), menu.getEquipmentSlot());
        addFrame(itemSelectFrame);

        /** frame to display and allow selecting of installed modules -------------------------------------------------- */
        moduleSelectFrame = new CompatibleModuleDisplayFrame(itemSelectFrame, new Rect(leftPos + 8, topPos + 13, leftPos + 172, topPos + 208));
        itemSelectFrame.getCreativeInstallButton().setOnPressed(pressed -> {
            itemSelectFrame.getCreativeInstallButton().playDownSound(Minecraft.getInstance().getSoundManager());
            moduleSelectFrame.getSelectedModule().ifPresent(clickie -> MPSPackets.CHANNEL_INSTANCE.sendToServer(new CreativeInstallPacketServerBound(itemSelectFrame.selectedType().get(), ItemUtils.getRegistryName(clickie.getModule()))));
        });

        // not perfect but better than nothing, I think
        itemSelectFrame.getCreativeInstallAllButton().setOnPressed(pressed->{
            Optional<IModularItem> modularItemCap = itemSelectFrame.getModularItemCapability();
            if (modularItemCap.isPresent()) {
                itemSelectFrame.getCreativeInstallButton().playDownSound(Minecraft.getInstance().getSoundManager());
                NonNullList<ItemStack> compatibleModules = getCompatibleModuleList();
                for(ItemStack module : compatibleModules) {
                    Optional<IPowerModule> powerModule = module.getCapability(NuminaCapabilities.POWER_MODULE).resolve();
                    if (powerModule.isPresent()) {
                        ModuleCategory category = powerModule.map(IPowerModule::getCategory).orElse(ModuleCategory.NONE);
                        int tier = powerModule.map(IPowerModule::getTier).orElse(0);
                        if(tier == -1 || (tier >= 4 &&(category == ModuleCategory.ARMOR || category == ModuleCategory.ENERGY_STORAGE))) {
                            MPSPackets.CHANNEL_INSTANCE.sendToServer(new CreativeInstallPacketServerBound(itemSelectFrame.selectedType().get(), ItemUtils.getRegistryName(module)));
                        }
                    }
                }
            }
        });

        addFrame(moduleSelectFrame);

        /** inventory frame for the modular item inventory. Changes with different selected modular items */
        boolean keepOnReload = moduleSelectFrame != null;
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
    public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float frameTime) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, frameTime);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        this.font.draw(matrixStack, this.moduleSelectionFrameLabel,
                titleLabelX,
                (float)this.titleLabelY, 4210752);

        this.font.draw(matrixStack, this.modularItemInventoryLabel,
                (float)(titleLabelX + 173),
                (float)this.titleLabelY, 4210752);

        this.font.draw(matrixStack, this.playerInventory.getDisplayName(),
                (float)(this.inventoryLabelX),
                (float)(inventoryLabelY),
                4210752);
    }

    @Override
    public void renderBackground(@Nonnull PoseStack matrixStack) {
        super.renderBackground(matrixStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        blit(matrixStack, i, j, this.getBlitOffset(), 0, 0, imageWidth, imageHeight, 512, 512);
    }
}