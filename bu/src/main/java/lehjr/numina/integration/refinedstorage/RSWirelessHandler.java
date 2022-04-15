package lehjr.numina.integration.refinedstorage;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.item.NetworkItem;
import com.refinedmods.refinedstorage.util.NetworkUtils;
import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.numina.util.item.ItemUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import com.refinedmods.refinedstorage.inventory.player.PlayerSlot;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class RSWirelessHandler {
    private static final String NBT_NODE_X = "NodeX";
    private static final String NBT_NODE_Y = "NodeY";
    private static final String NBT_NODE_Z = "NodeZ";
    private static final String NBT_DIMENSION = "Dimension";

    // refinedstorage
    public static void attach(AttachCapabilitiesEvent<ItemStack> event, Callable<IConfig> moduleConfigGetterIn) {
        final ItemStack itemStack = event.getObject();
        final ResourceLocation regName = itemStack.getItem().getRegistryName();
        final ResourceLocation wireless_grid = new ResourceLocation("refinedstorage:wireless_grid");
        final ResourceLocation wireless_fluid_grid = new ResourceLocation("refinedstorage:wireless_fluid_grid");
            /*
            known possibilities
            --------------------
            refinedstorage:wireless_grid
            refinedstorage:wireless_fluid_grid
             */
        if (regName.equals(wireless_grid) || regName.equals(wireless_fluid_grid)) {
            IRightClickModule rsTerminal = new RightClickModule(itemStack, EnumModuleCategory.TOOL, EnumModuleTarget.TOOLONLY, moduleConfigGetterIn) {
                @Override
                public ActionResult use(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                    ItemStack module = ItemUtils.getActiveModuleOrEmpty(itemStackIn);

                    if (!worldIn.isClientSide()) {
                        int totalEnergy = ElectricItemUtils.getPlayerEnergy(playerIn);
                        int energyNeeded = ElectricItemUtils.chargeItem(module, totalEnergy, true);
                        if (energyNeeded > 0) {
                            energyNeeded = ElectricItemUtils.drainPlayerEnergy(playerIn, energyNeeded, false);
                            ElectricItemUtils.chargeItem(module, energyNeeded, false);
                        }
                    }

                    if (!worldIn.isClientSide && module.getItem() instanceof NetworkItem) {
                        ((NetworkItem)module.getItem()).applyNetwork(worldIn.getServer(), module, n -> n.getNetworkItemManager().open(playerIn, module, PlayerSlot.getSlotForHand(playerIn, hand)), err -> playerIn.sendMessage(err, playerIn.getUUID()));
                    }
                    return ActionResult.success(itemStackIn);
                }

                @Override
                public ActionResultType onItemUseFirst(ItemStack itemStackIn, ItemUseContext context) {
                    ItemStack module = ItemUtils.getActiveModuleOrEmpty(itemStackIn);
                    INetwork network = NetworkUtils.getNetworkFromNode(NetworkUtils.getNodeFromTile(context.getLevel().getBlockEntity(context.getClickedPos())));
                    if (network != null) {
                        CompoundNBT tag = module.getTag();

                        if (tag == null) {
                            tag = new CompoundNBT();
                        }

                        tag.putInt(NBT_NODE_X, network.getPosition().getX());
                        tag.putInt(NBT_NODE_Y, network.getPosition().getY());
                        tag.putInt(NBT_NODE_Z, network.getPosition().getZ());
                        tag.putString(NBT_DIMENSION, context.getLevel().dimension().location().toString());

                        module.setTag(tag);

                        return ActionResultType.SUCCESS;
                    }

                    return ActionResultType.PASS;
                }
            };

            event.addCapability(regName, new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == PowerModuleCapability.POWER_MODULE) {
                        return LazyOptional.of(() -> (T) rsTerminal);
                    }
                    return LazyOptional.empty();
                }
            });
        }
    }
}
