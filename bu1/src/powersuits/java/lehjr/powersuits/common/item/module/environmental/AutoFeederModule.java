package lehjr.powersuits.common.item.module.environmental;

import com.lehjr.numina.common.capabilities.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.numina.common.utils.TagUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AutoFeederModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        boolean useOldAutoFeeder;
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.HEADONLY);
            useOldAutoFeeder =  true; //MPSCommonConfig.
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 100);
            addBaseProperty(MPSConstants.EATING_EFFICIENCY, 50);
            addTradeoffProperty(MPSConstants.EFFICIENCY, MPSConstants.ENERGY_CONSUMPTION, 1000, "FE");
            addTradeoffProperty(MPSConstants.EFFICIENCY, MPSConstants.EATING_EFFICIENCY, 50);
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack modularItemStack) {
            float foodLevel = getFoodLevel(getModule());
            float saturationLevel = getSaturationLevel(getModule());
            Inventory inv = player.getInventory();
            double eatingEnergyConsumption = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            double efficiency = applyPropertyModifiers(MPSConstants.EATING_EFFICIENCY);

            FoodData foodStats = player.getFoodData();
            int foodNeeded = 20 - foodStats.getFoodLevel();
            float saturationNeeded = 20 - foodStats.getSaturationLevel();

            // this consumes all food in the player's inventory and stores the stats in a buffer
            if (useOldAutoFeeder) { // FIXME!!!!!
                for (int i = 0; i < inv.getContainerSize(); i++) {
                    ItemStack stack = inv.getItem(i);
                    FoodProperties foodProperties = stack.getFoodProperties(player);
                    if(foodProperties != null) {
                        for (int a = 0; a < stack.getCount(); a++) {
                            foodLevel += (float) (foodProperties.nutrition() * efficiency) * 0.01F;
                            //  copied this from FoodStats.addStats()
                            saturationLevel += (float) (Math.min(foodProperties.nutrition() * foodProperties.saturation() * 2.0D, 20D) * efficiency * 0.01);
                        }
                        player.getInventory().setItem(i, ItemStack.EMPTY);
                    }
                }
                setFoodLevel(modularItemStack, foodLevel, level);
                setSaturationLevel(modularItemStack, saturationLevel, level);
            } else {
                for (int i = 0; i < inv.getContainerSize(); i++) {
                    if (foodNeeded < foodLevel)
                        break;
                    ItemStack stack = inv.getItem(i);
                    FoodProperties foodProperties = stack.getFoodProperties(player);
                    if (foodProperties != null) {
                        while (true) {
                            if (foodNeeded > foodLevel) {
                                foodLevel += (float) (foodProperties.nutrition() * efficiency) * 0.01F;
                                //  copied this from FoodStats.addStats()
                                saturationLevel += (float) (Math.min(foodProperties.nutrition() * foodProperties.saturation() * 2.0D, 20D) * efficiency * 0.01);
                                stack.setCount(stack.getCount() - 1);
                                if (stack.getCount() == 0) {
                                    player.getInventory().setItem(i, ItemStack.EMPTY);
                                    break;
                                } else
                                    player.getInventory().setItem(i, stack);
                            } else
                                break;
                        }
                    }
                }
                setFoodLevel(modularItemStack, foodLevel, level);
                setSaturationLevel(modularItemStack, saturationLevel, level);
            }

            CompoundTag foodStatNBT = new CompoundTag();

            // only consume saturation if food is consumed. This keeps the food buffer from overloading with food while the
            //   saturation buffer drains completely.
            if (foodNeeded > 0 && getFoodLevel(modularItemStack) >= 1) {
                int foodUsed = 0;
                // if buffer has enough to fill player stat
                if (getFoodLevel(modularItemStack) >= foodNeeded && foodNeeded * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                    foodUsed = foodNeeded;
                    // if buffer has some but not enough to fill the player stat
                } else if ((foodNeeded - getFoodLevel(modularItemStack)) > 0 && getFoodLevel(modularItemStack) * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                    foodUsed = (int) getFoodLevel(modularItemStack);
                    // last resort where using just 1 unit from buffer
                } else if (eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player) && getFoodLevel(modularItemStack) >= 1) {
                    foodUsed = 1;
                }
                if (foodUsed > 0) {
                    // populate the tag with the nbt data
                    foodStats.addAdditionalSaveData(foodStatNBT);
                    foodStatNBT.putInt("foodLevel", foodStatNBT.getInt("foodLevel") + foodUsed);
                    // put the values back into foodstats
                    foodStats.readAdditionalSaveData(foodStatNBT);
                    // update getValue stored in buffer
                    setFoodLevel(modularItemStack, getFoodLevel(modularItemStack) - foodUsed, level);
                    // split the cost between using food and using saturation
                    ElectricItemUtils.drainPlayerEnergy(player, (int) (eatingEnergyConsumption * 0.5 * foodUsed), false);

                    if (saturationNeeded >= 1.0D) {
                        // using int for better precision
                        int saturationUsed = 0;
                        // if buffer has enough to fill player stat
                        if (getSaturationLevel(modularItemStack) >= saturationNeeded && saturationNeeded * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                            saturationUsed = (int) saturationNeeded;
                            // if buffer has some but not enough to fill the player stat
                        } else if ((saturationNeeded - getSaturationLevel(modularItemStack)) > 0 && getSaturationLevel(modularItemStack) * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                            saturationUsed = (int) getSaturationLevel(modularItemStack);
                            // last resort where using just 1 unit from buffer
                        } else if (eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player) && getSaturationLevel(modularItemStack) >= 1) {
                            saturationUsed = 1;
                        }

                        if (saturationUsed > 0) {
                            // populate the tag with the nbt data
                            foodStats.addAdditionalSaveData(foodStatNBT);
                            foodStatNBT.putFloat("foodSaturationLevel", foodStatNBT.getFloat("foodSaturationLevel") + saturationUsed);
                            // put the values back into foodstats
                            foodStats.readAdditionalSaveData(foodStatNBT);
                            // update getValue stored in buffer
                            setSaturationLevel(modularItemStack, getSaturationLevel(modularItemStack) - saturationUsed, level);
                            // split the cost between using food and using saturation
                            ElectricItemUtils.drainPlayerEnergy(player, (int) (eatingEnergyConsumption * 0.5 * saturationUsed), false);
                        }
                    }
                }
            }
        }

        // these setters should be called server side only -------------------------------------------------------------
        public void setFoodLevel(@Nonnull ItemStack stack, float food, Level level) {
            if(!level.isClientSide()) {
                IModeChangingItem mci = getMci(stack);
                if (mci != null) {
                    mci.setModuleFloat(ItemUtils.getRegistryName(getModule()), MPSConstants.TAG_FOOD, food);
                }
            }
        }

        public void setSaturationLevel(@Nonnull ItemStack stack, float saturation, Level level) {
            if (!level.isClientSide()) {
                IModeChangingItem mci = getMci(stack);
                if (mci != null) {
                    mci.setModuleFloat(ItemUtils.getRegistryName(getModule()), MPSConstants.TAG_SATURATION, saturation);
                }
            }
        }
    }

    public static float getFoodLevel(@Nonnull ItemStack stack) {
        return TagUtils.getModuleFloat(stack, MPSConstants.TAG_FOOD);
    }

    public static float getSaturationLevel(@Nonnull ItemStack stack) {
        return TagUtils.getModuleFloat(stack, MPSConstants.TAG_SATURATION);
    }

    @Nullable
    static IModeChangingItem getMci(@Nonnull ItemStack modularItemStack) {
        return modularItemStack.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
    }
}