package numina;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NuminaDataGenerator {
    private NuminaDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if (event.includeClient()) {
            //Client side data generators
            // FIXME!!
//            gen.addProvider(new NuminaLangProvider(gen, existingFileHelper, NuminaConstants.MOD_ID, "main"));
        }
        if (event.includeServer()) {
            //Server side data generators

        }
    }
}

