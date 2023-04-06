package lehjr.powersuits.client.gui.modding.module.install_salvage;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.ExtendedContainerScreen;
import lehjr.numina.client.gui.clickable.button.VanillaButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.client.ScrollableInventoryFrame2;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrameContainered;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.container.InstallSalvageMenu;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.CreativeInstallPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InstallSalvageGui extends ExtendedContainerScreen<InstallSalvageMenu> {
    Component modularItemInventoryLabel = Component.translatable(MPSConstants.MOD_ID + ".modularitem.inventory");
    Component moduleSelectionFrameLabel = Component.translatable("gui.powersuits.compatible.modules");
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/background/install_salvage.png");
    protected TabSelectFrame tabSelectFrame;
    ModularItemSelectionFrameContainered itemSelectFrame;

    ScrollableInventoryFrame2 modularItemInventory;
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
        itemSelectFrame = new ModularItemSelectionFrameContainered(menu, new MusePoint2D(leftPos - 30, topPos), menu.getEquipmentSlot());
        addFrame(itemSelectFrame);

        /** frame to display and allow selecting of installed modules -------------------------------------------------- */
        moduleSelectFrame = new CompatibleModuleDisplayFrame(itemSelectFrame, new Rect(leftPos + 8, topPos + 13, leftPos + 172, topPos + 208));
        itemSelectFrame.getCreativeInstallButton().setOnPressed(pressed -> {
            itemSelectFrame.getCreativeInstallButton().playDownSound(Minecraft.getInstance().getSoundManager());
            moduleSelectFrame.getSelectedModule().ifPresent(clickie -> {
                MPSPackets.CHANNEL_INSTANCE.sendToServer(new CreativeInstallPacket(itemSelectFrame.selectedType().get(), ItemUtils.getRegistryName(clickie.getModule())));
            });
        });

        itemSelectFrame.getCreativeInstallButton().setOnReleased(pressed -> {

//            ((VanillaButton)pressed).setEnabledBackground(Color.LIGHT_GRAY);
        });
        addFrame(moduleSelectFrame);

        /** inventory frame for the modular item inventory. Changes with different selected modular items */
        boolean keepOnReload = moduleSelectFrame != null;
        modularItemInventory = new ScrollableInventoryFrame2(menu, new Rect(178,14,353,118), itemSelectFrame, this.ulGetter());
        moduleSelectFrame.loadModules(keepOnReload);
        addFrame(modularItemInventory);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        // FIXME: replace button with something more native
        if(getMinecraft().player.isCreative()) {
            itemSelectFrame.getCreativeInstallButton().enableAndShow();
        } else {
            itemSelectFrame.getCreativeInstallButton().disableAndHide();
        }
    }
    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float frameTime) {
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
    public void renderBackground(PoseStack matrixStack) {
        super.renderBackground(matrixStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, this.getBlitOffset(), 0, 0, imageWidth, imageHeight, 512, 512);
    }
}