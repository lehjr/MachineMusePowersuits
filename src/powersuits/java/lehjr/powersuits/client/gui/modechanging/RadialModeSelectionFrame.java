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

package lehjr.powersuits.client.gui.modechanging;

import lehjr.numina.client.gui.clickable.ClickableModule;
import lehjr.numina.client.gui.frame.AbstractGuiFrame;
import lehjr.numina.client.gui.geometry.IDrawable;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.gui.geometry.SpiralPointToPoint2D;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.ModeChangeRequestPacketServerBound;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*
TODO: revamp with a set of concentric circles to sort modules by category
 */
public class RadialModeSelectionFrame extends AbstractGuiFrame {
    boolean visible = true;
    boolean enabled = true;
    protected final long spawnTime;
    protected List<ClickableModule> modeButtons = new ArrayList<>();
    protected int selectedModuleOriginal = -1;
    protected int selectedModuleNew = -1;

    protected Player player;
    protected double radius;
    float zLevel;

    public RadialModeSelectionFrame(MusePoint2D topLeft, MusePoint2D bottomRight, Player player, float zLevel) {
        super(new Rect(topLeft, bottomRight));
        spawnTime = System.currentTimeMillis();
        this.player = player;
        this.radius = Math.min(width(), height());
        this.zLevel = zLevel;
        loadItems();
    }

    @Override
    public void update(double mouseX, double mouseY) {
        //Update items
        loadItems();
        //Determine which mode is selected
        if (System.currentTimeMillis() - spawnTime > 250) {
            selectModule(mouseX, mouseY);
        }

        Optional<IModeChangingItem> mcic = ItemUtils.getModeChangingModularItemCapability(player);
        //Switch to selected mode if mode changed
        if (mcic.isPresent() && getSelectedModule() != null && selectedModuleOriginal != selectedModuleNew) {
            // update to detect mode changes
            selectedModuleOriginal = selectedModuleNew;
            ItemUtils.getModeChangingModularItemCapability(player).ifPresent(handler-> NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ModeChangeRequestPacketServerBound(getSelectedModule().getInventorySlot())));
        }
    }

    @Override
    public void render(GuiGraphics matrixStackIn, int mouseX, int mouseY, float partialTick) {
        //Draw the installed power fist modes
        for (ClickableModule mode : modeButtons) {
            mode.render(matrixStackIn, mouseX, mouseY, partialTick);
        }
        //Draw the selected mode indicator
        drawSelection(matrixStackIn);
    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float v) {
        return this;
    }

    private void loadItems() {
        if (player != null && modeButtons.isEmpty()) {
            Optional<IModeChangingItem> mcic = ItemUtils.getModeChangingModularItemCapability(player);
            mcic.ifPresent(handler->{
                List<Integer> modes = handler.getValidModes();
                int activeMode = handler.getActiveMode();
                if (activeMode > 0)
                    selectedModuleOriginal = activeMode;
                int modeNum = 0;
                for (int mode : modes) {
                    ClickableModule clickie = new ClickableModule(handler.getStackInSlot(mode), new SpiralPointToPoint2D(center(), radius, ((3D * Math.PI / 2) - ((2D * Math.PI * modeNum) / modes.size())), 250D), mode, ModuleCategory.NONE);
                    modeButtons.add(clickie);
                    modeNum ++;
                }
            });
        }
    }

    private void selectModule(double x, double y) {
        if (modeButtons != null) {
            for(int i =0; i<modeButtons.size(); i++ ) {
                if (modeButtons.get(i).containsPoint(x, y)) {
                    selectedModuleNew = i;
                    break;
                }
            }
        }
    }

    public ClickableModule getSelectedModule() {
        if (modeButtons.size() > selectedModuleNew && selectedModuleNew != -1) {
            return modeButtons.get(selectedModuleNew);
        } else {
            return null;
        }
    }

    public void drawSelection(GuiGraphics gfx) {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            MusePoint2D pos = module.center();
            NuminaRenderer.drawCircleAround(gfx.pose(), pos.x(), pos.y(), 10, zLevel);
        }
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            return module.getToolTip(x, y);
        }
        return null;
    }

    @Override
    public void setEnabled(boolean b) {
        enabled = b;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setVisible(boolean b) {
        visible = b;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}