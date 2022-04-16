package lehjr.powersuits.client.gui.modding.module.install_salvage;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.util.client.gui.ExtendedContainerScreen;
import lehjr.numina.util.client.gui.clickable.ClickableButton;
import lehjr.numina.util.client.gui.frame.GUISpacer;
import lehjr.numina.util.client.gui.frame.LabelBox;
import lehjr.numina.util.client.gui.frame.MultiRectHolderFrame;
import lehjr.numina.util.client.gui.frame.PlayerInventoryFrame;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.math.Colour;
import lehjr.powersuits.client.ScrollableInventoryFrame2;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrameContainered;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.container.InstallSalvageContainer;
import lehjr.powersuits.network.MPSPackets;
import lehjr.powersuits.network.packets.CreativeInstallPacket;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InstallSalvageGui extends ExtendedContainerScreen<InstallSalvageContainer> {
    /** determins if the recipe book gui will be over the crafting gui */
    private boolean widthTooNarrow;

    protected PlayerInventoryFrame playerInventoryFrame;
    protected TabSelectFrame tabSelectFrame;
    int spacer = 7;
    ModularItemSelectionFrameContainered modularItemSelectionFrame;

    ScrollableInventoryFrame2 modularItemInventory;

    MultiRectHolderFrame mainHolder;
    protected CompatibleModuleDisplayFrame moduleSelectFrame;
    protected LabelBox modularSelectionLabel;

    public InstallSalvageGui(InstallSalvageContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title, 340, 217, false);

        /** clickable buttons on the top of the GUI */
        tabSelectFrame = new TabSelectFrame(playerInventory.player, 0);
        addFrame(tabSelectFrame);

        /** Compatible module frame */
        /** common width for left side frames */
        double leftFrameWidth = 157;
        MultiRectHolderFrame leftFrame = new MultiRectHolderFrame(false, true, 0,0);

        /** left label (takes place of spacer) */
        modularSelectionLabel = new LabelBox(leftFrameWidth, 15, new TranslatableComponent("gui.powersuits.compatible.modules"));
        leftFrame.addRect(modularSelectionLabel);

        /** the buttons that select the equipped modular item if any */
        modularItemSelectionFrame = new ModularItemSelectionFrameContainered(container, container.getEquipmentSlot());

        /** frame to display and allow selecting of installed modules */
        moduleSelectFrame = new CompatibleModuleDisplayFrame(modularItemSelectionFrame,
                new MusePoint2D(0,0),
                new MusePoint2D(leftFrameWidth, 195),
                Colour.DARK_GREY.withAlpha(1F), // backgroundColour
                new Colour(0.216F, 0.216F, 0.216F, 1F), // topBorderColour
                Colour.WHITE.withAlpha(0.8F)); // bottomBorderColour

        leftFrame.addRect(moduleSelectFrame);
        /** bottom left spacer */
        leftFrame.addRect(new GUISpacer(leftFrameWidth, spacer));
        leftFrame.doneAdding();



        /** player inventory */
        playerInventoryFrame = new PlayerInventoryFrame(container, container.getMainInventoryStart(), container.getHotbarInventoryStart(), ulGetter());


        addFrame(modularItemSelectionFrame);

        /** inventory frame for the modular item inventory. Changes with different selected modular items */
        modularItemInventory = new ScrollableInventoryFrame2(container, modularItemSelectionFrame, this.ulGetter());


        /** a box to group these 2 inventory frames together */
        MultiRectHolderFrame rightHolder = new MultiRectHolderFrame(false, true, 0,0);
        rightHolder.addRect(modularItemInventory);
        rightHolder.addRect(playerInventoryFrame);
        rightHolder.doneAdding();

        mainHolder = new MultiRectHolderFrame(true, true, 0, 0);
        mainHolder.addRect(new GUISpacer(10, spacer));
        mainHolder.addRect(leftFrame);

        mainHolder.addRect(rightHolder);
        mainHolder.doneAdding();
        addFrame(mainHolder);

        modularItemSelectionFrame.getCreativeInstallButton().setOnPressed(pressed -> {
//            ((ClickableButton)pressed).setEnabledBackground(Colour.DARK_GREY);
//            modularItemSelectionFrame.getCreativeInstallButton().playDownSound(Minecraft.getInstance().getSoundManager());

            moduleSelectFrame.getSelectedModule().ifPresent(clickie -> {
                MPSPackets.CHANNEL_INSTANCE.sendToServer(new CreativeInstallPacket(modularItemSelectionFrame.selectedType().get(), clickie.getModule().getItem().getRegistryName()));
            });
        });

        modularItemSelectionFrame.getCreativeInstallButton().setOnReleased(pressed -> {
            ((ClickableButton)pressed).setEnabledBackground(Colour.LIGHT_GREY);
        });
    }

    @Override
    public void tick() {
        super.tick();
        if(getMinecraft().player.isCreative()) {
            modularItemSelectionFrame.getCreativeInstallButton().enableAndShow();
        } else {
            modularItemSelectionFrame.getCreativeInstallButton().disableAndHide();
        }
    }

    @Override
    public void init() {
        super.init();
        /** do not call anything recipe book related before this */

        modularItemSelectionFrame.setMeLeftOf(mainHolder);
        modularItemSelectionFrame.setTop(mainHolder.finalTop());
        modularItemSelectionFrame.initGrowth();

        this.titleLabelX = 29;

        mainHolder.setPosition(backgroundRect.getPosition());
        mainHolder.initGrowth();

        tabSelectFrame.init(leftPos, getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
        moduleSelectFrame.loadModules(false);
    }

    /**
     * Overridden to fix inventory frames rendering behind background
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param frameTime
     */
    @Override
    public void renderBackgroundRect(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        matrixStack.pushPose();
        matrixStack.translate(0,0, -1);
        super.renderBackgroundRect(matrixStack, mouseX, mouseY, frameTime);
        matrixStack.popPose();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        modularItemSelectionFrame.setTop(mainHolder.finalTop());
        modularItemSelectionFrame.setRight(mainHolder.finalLeft());

        this.renderBackground(matrixStack);
        if (backgroundRect.doneGrowing()) {
            super.render(matrixStack, mouseX, mouseY, frameTime);
            this.renderTooltip(matrixStack, mouseX, mouseY);
            tabSelectFrame.render(matrixStack, mouseX, mouseY, frameTime);
            renderTooltip(matrixStack, mouseX, mouseY);
        } else {
            renderBackgroundRect(matrixStack, mouseX, mouseY, frameTime);
        }
    }

    @Override
    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        matrixStack.pushPose();
        matrixStack.translate(0,0, 10);
        this.modularItemInventory.renderLabels(matrixStack, mouseX, mouseY);
        this.playerInventoryFrame.renderLabels(matrixStack, mouseX, mouseY);
        modularSelectionLabel.renderLabel(matrixStack, (float) - ulGetter().getULShift().getX(), (float) - ulGetter().getULShift().getY() + 1);
        matrixStack.popPose();
    }
}