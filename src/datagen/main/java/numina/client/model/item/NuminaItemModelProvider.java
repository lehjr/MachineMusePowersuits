package numina.client.model.item;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class NuminaItemModelProvider extends AbstractItemModelProvider {
    public NuminaItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NuminaConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemEntity(NuminaItems.ARMOR_STAND_ITEM.get())
            .transforms()
            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
            .rotation(70, 0, 0)
            .translation(-50.5f, 1, 18.25f)
            .scale(0.62f, 0.62f, 0.62f)
            .end()

            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
            .rotation(70, 0, 0)
            .translation(4.25F, 1, 18.25F)
            .scale(0.62f, 0.62f, 0.62f)
            .end()

            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
            .rotation(0, 0, 352)
            .translation(3.75F, 11.5F, 0)
            .scale(0.42f, 0.4f, 0.4f)
            .end()

            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
            .rotation(0, 0, 352)
            .translation(11.5F, 11.5F, 0)
            .scale(0.42f, 0.4f, 0.4f)
            .end()

            .transform(ItemDisplayContext.GUI)
            .rotation(0, 0, 316)
            .translation(22.5F, 12, 0)
            .scale(1, 1, 1)
            .end()

            .transform(ItemDisplayContext.GROUND)
            .rotation(0, 0, 0)
            .translation( 0, 18.75F, 0)
            .scale(0.62F, 0.62F, 0.62F)
            .end()

            .transform(ItemDisplayContext.FIXED)
            .rotation(0, 180, 0)
            .translation(-6.25F, 17.75F, -6.50F)
            .scale( 0.75F, 0.75F, 0.75F)
            .end();

        // Batteries ----------------------------------------------------------------------------------------
        batteryItem(NuminaItems.BATTERY_1.get(), "basic");
        batteryItem(NuminaItems.BATTERY_2.get(), "advanced");
        batteryItem(NuminaItems.BATTERY_3.get(), "elite");
        batteryItem(NuminaItems.BATTERY_4.get(), "ultimate");

        // BlockItem ----------------------------------------------------------------------------------------
        blockItemEntity(NuminaItems.CHARGING_BASE_ITEM.get(),
            Objects.requireNonNull(modLoc("block/base_unpowered")))// texture location
            .transforms()
            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
            .rotation(75, 315, 0)
            .translation(0, 2.5F, 0)
            .scale( 0.375F, 0.375F, 0.375F)
            .end()

            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
            .rotation( 0, 315, 0 )
            .translation(0, 2.5F, 0)
            .scale(0.4F, 0.4F, 0.4F)
            .end()

            .transform(ItemDisplayContext.GUI)
            .rotation(30, 45, 0)
            .translation(0, 0, 0)
            .scale(0.625F, 0.625F, 0.625F)
            .end()

            .transform(ItemDisplayContext.GROUND)
            .rotation(0, 0, 0)
            .translation( 0, 3, 0)
            .scale(0.25F,  0.25F, 0.25F)
            .end()

            .transform(ItemDisplayContext.FIXED)
            .rotation(0, 180, 0)
            .translation(0, 0, 0)
            .scale( 1, 1, 1)
            .end();

        // Components ---------------------------------------------------------------------------------------
        componentItem(NuminaItems.ARTIFICIAL_MUSCLE.get(), "artificialmuscle");
        componentItem(NuminaItems.CAPACITOR_1.get(), "capacitor_1");
        componentItem(NuminaItems.CAPACITOR_2.get(), "capacitor_2");
        componentItem(NuminaItems.CAPACITOR_3.get(), "capacitor_3");
        componentItem(NuminaItems.CAPACITOR_4.get(), "capacitor_4");
        componentItem(NuminaItems.CARBON_MYOFIBER.get(), "myofiber");
        componentItem(NuminaItems.COMPUTER_CHIP.get(), "computerchip");
        componentItem(NuminaItems.CONTROL_CIRCUIT_1.get(), "controlcircuit_1");
        componentItem(NuminaItems.CONTROL_CIRCUIT_2.get(), "controlcircuit_2");
        componentItem(NuminaItems.CONTROL_CIRCUIT_3.get(), "controlcircuit_3");
        componentItem(NuminaItems.CONTROL_CIRCUIT_4.get(), "controlcircuit_4");
        componentItem(NuminaItems.FIELD_EMITTER.get(), "fieldemitter");
        componentItem(NuminaItems.GLIDER_WING.get(), "gliderwing");
        componentItem(NuminaItems.ION_THRUSTER.get(), "ionthruster");
        componentItem(NuminaItems.LASER_EMITTER.get(), "hologramemitter");
        componentItem(NuminaItems.MAGNET.get(), "magnetb");
        componentItem(NuminaItems.MYOFIBER_GEL.get(), "paste");
        componentItem(NuminaItems.PARACHUTE.get(), "parachuteitem");
        componentItem(NuminaItems.PLATING_DIAMOND.get(), "diamond_plate");
        componentItem(NuminaItems.PLATING_IRON.get(), "iron_plate");
        componentItem(NuminaItems.PLATING_NETHERITE.get(), "netherite_plate");
        componentItem(NuminaItems.RUBBER_HOSE.get(), "rubberhose");
        componentItem(NuminaItems.SERVO.get(), "servo");
        componentItem(NuminaItems.SOLAR_PANEL.get(), "solarpanel");
        componentItem(NuminaItems.SOLENOID.get(), "solenoid");
        componentItem(NuminaItems.WIRING_COPPER.get(), "wiring_copper");
        componentItem(NuminaItems.WIRING_GOLD.get(), "wiring_gold");

        // Smithing Templates -------------------------------------------------------------------------------
        upgrade(NuminaItems.TIER_2_SMITHING_UPGRADE_TEMPLATE.get(), "tier_2_smithing_template");
        upgrade(NuminaItems.TIER_3_SMITHING_UPGRADE_TEMPLATE.get(), "tier_3_smithing_template");
        upgrade(NuminaItems.TIER_4_SMITHING_UPGRADE_TEMPLATE.get(), "tier_4_smithing_template");
    }

    public ItemModelBuilder batteryItem(Item item, String texturePath) {
        return this.basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), "item/battery/" + texturePath);
    }

    public ItemModelBuilder componentItem(Item item, String texturePath) {
        return this.basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)),"item/component/" + texturePath);
    }

    public ItemModelBuilder upgrade(Item item, String texturePath) {
        return this.basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)),"item/upgrade/" + texturePath);
    }
}
