import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PowersuitsDataGenerator {
    private PowersuitsDataGenerator() {
    }

//    @SubscribeEvent
//    public static void gatherData(GatherDataEvent event) {
//        DataGenerator gen = event.getGenerator();
//        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
//        if (event.includeClient()) {
//            //Client side data generators
//            gen.addProvider(new NuminaLangProvider(gen, existingFileHelper, MPSConstants.MOD_ID, MPSConstants.MOD_ID));
//        }
//        if (event.includeServer()) {
//            //Server side data generators
//
//        }
//    }
}