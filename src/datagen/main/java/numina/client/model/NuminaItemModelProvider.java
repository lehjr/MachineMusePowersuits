package numina.client.model;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class NuminaItemModelProvider extends AbstractItemModelProvider {
    public NuminaItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NuminaConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //        // Generates a basic generated item model pointing to textures/item/example_item.png
        //        basicItem(ModItems.EXAMPLE_ITEM.get());
        //
        //        // Handheld tool example
        //        handheldItem(ModItems.EXAMPLE_SWORD.get());

        //        armor_stand :
//        {
//            "parent": "builtin/entity",
//            "display": {
//            "gui": {
//                "rotation": [ 0, 0, 316 ],
//                "translation": [ 22.25, 12.00, 0.00 ],
//                "scale": [ 1.00, 1.00, 1.00 ]
//            },
//            "ground": {
//                "rotation": [ 0, 0, 0 ],
//                "translation": [ 0.00, 18.75, 0.00 ],
//                "scale": [ 0.62, 0.62, 0.62 ]
//            },
//            "fixed": {
//                "rotation": [ 0, 180, 0 ],
//                "translation": [ -6.25, 17.75, -6.50 ],
//                "scale": [ 0.75, 0.75, 0.75 ]
//            },
//            "thirdperson_righthand": {
//                "rotation": [ 70, 0, 0 ],
//                "translation": [ 4.25, 1.00, 18.25 ],
//                "scale": [ 0.62, 0.62, 0.62 ]
//            },
//            "thirdperson_lefthand": {
//                "rotation": [ 70, 0, 0 ],
//                "translation": [ -5.50, 1.00, 18.25 ],
//                "scale": [ 0.62, 0.62, 0.62 ]
//            },
//            "firstperson_righthand": {
//                "rotation": [ 0, 0, 352 ],
//                "translation": [ 11.50, 11.50, 0.00 ],
//                "scale": [ 0.40, 0.40, 0.40 ]
//            },
//            "firstperson_lefthand": {
//                "rotation": [ 0, 0, 352 ],
//                "translation": [ 3.75, 11.50, 0.00 ],
//                "scale": [ 0.40, 0.40, 0.40 ]
//            }
//        }
//        }

        // Batteries ----------------------------------------------------------------------------------------
        batteryItem(NuminaItems.BATTERY_1.get(), "basic");
        batteryItem(NuminaItems.BATTERY_2.get(), "advanced");
        batteryItem(NuminaItems.BATTERY_3.get(), "elite");
        batteryItem(NuminaItems.BATTERY_4.get(), "ultimate");

        //        charging_base

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
        componentItem(NuminaItems.TIER_2_SMITHING_UPGRADE_TEMPLATE.get(), "tier_2_smithing_upgrade_template");
        componentItem(NuminaItems.TIER_3_SMITHING_UPGRADE_TEMPLATE.get(), "tier_3_smithing_upgrade_template");
        componentItem(NuminaItems.TIER_4_SMITHING_UPGRADE_TEMPLATE.get(), "tier_4_smithing_upgrade_template");
    }

    public ItemModelBuilder batteryItem(Item item, String texturePath) {
        return this.basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item),"item/battery/" + texturePath));
    }

    public ItemModelBuilder componentItem(Item item, String texturePath) {
        return this.basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item),"item/component/" + texturePath));
    }

    public ItemModelBuilder upgrade(Item item, String texturePath) {
        return this.basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item),"item/upgrade/" + texturePath));
    }
}
