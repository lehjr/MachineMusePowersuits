package numina.common.damage;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.HeatUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HeatDamageDataGen {

//    // In your datagen class
//    @SubscribeEvent // on the mod event bus
//    public static void onGatherData(GatherDataEvent event) {
//        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
//        event.getGenerator().addProvider(event.includeServer(),
//            output ->
//            new DatapackBuiltinEntriesProvider(output, lookupProvider,
//
//            new RegistrySetBuilder()
//            // Add a datapack builtin entry provider for damage types. If this lambda becomes longer,
//            // this should probably be extracted into a separate method for the sake of readability.
//            .add(Registries.DAMAGE_TYPE, bootstrap -> {
//                // Use new DamageType() to create an in-code representation of a damage type.
//                // The parameters map to the values of the JSON file, in the order seen above.
//                // All parameters except for the message id and the exhaustion value are optional.
//                bootstrap.register(HeatUtils.OVERHEAT_DAMAGE,
//                    new DamageType(HeatUtils.OVERHEAT_DAMAGE.location(), DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f, DamageEffects.HURT,
//                        DeathMessageType.DEFAULT));
//            }),
//            // Add datapack providers for other datapack entries, if applicable.
//                            .add(...),
//            Set.of(NuminaConstants.MOD_ID)
//
//        ));
//    }


    // In your datagen class
    @SubscribeEvent // on the mod event bus
    public static void onGatherData(GatherDataEvent event) {
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.getGenerator().addProvider(true, (DataProvider.Factory<DatapackBuiltinEntriesProvider>) packOutput
            -> new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, bootstrap ->
                bootstrap.register(HeatUtils.OVERHEAT_DAMAGE, new DamageType(HeatUtils.OVERHEAT_DAMAGE.location() + ".damage", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F, DamageEffects.HURT, DeathMessageType.DEFAULT)))
            , Set.of(NuminaConstants.MOD_ID))
        );
    }
}
