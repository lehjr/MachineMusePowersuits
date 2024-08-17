package lehjr.powersuits.client.event;


import com.lehjr.numina.client.model.helper.LuxCapHelper;
import com.lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import com.lehjr.numina.common.math.Color;
import lehjr.powersuits.client.model.block.LuxCapacitorModelWrapper;
import lehjr.powersuits.client.model.item.PowerFistModelWrapper;
import lehjr.powersuits.client.render.item.MPSBEWLR;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum ModelBakeEventHandler {
    INSTANCE;
    public static final ResourceLocation plasmaBall = ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "entity/plasma");

    public MPSBEWLR MPSBERINSTANCE = new MPSBEWLR();

    public static final ModelResourceLocation powerFistIconLocation1 = new ModelResourceLocation(MPSConstants.POWER_FIST_1, "inventory");
    public static final ModelResourceLocation powerFistIconLocation2 = new ModelResourceLocation(MPSConstants.POWER_FIST_2, "inventory");
    public static final ModelResourceLocation powerFistIconLocation3 = new ModelResourceLocation(MPSConstants.POWER_FIST_3, "inventory");
    public static final ModelResourceLocation powerFistIconLocation4 = new ModelResourceLocation(MPSConstants.POWER_FIST_4, "inventory");

    RandomSource rand = RandomSource.create();


    @SubscribeEvent
    public void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> registry = event.getModels();
        for (Direction facing : Direction.values()) {
            BakedModel model = registry.get(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath("powersuits:luxcapacitor"), "facing=" + facing + ",waterlogged=true"));
            if(model instanceof OBJBakedCompositeModel) {
                HashMap<DIR, List<BakedQuad>> map = new HashMap<>();
                for (DIR dir : DIR.values()) {
                    map.put(dir, model.getQuads(null, dir.direction, rand, LuxCapHelper.getBlockLensModelData(Color.WHITE.getARGBInt()), RenderTypeGroup.EMPTY.entity()));
                }
            }
        }

        event.getModels().keySet().stream().filter(resourceLocation -> resourceLocation.toString().contains("powersuits:luxcapacitor")).forEach(resourceLocation -> {
            System.out.println("fixme model bake event mps");
            BakedModel model = event.getModels().get(resourceLocation);
            if (model instanceof OBJBakedCompositeModel) {
                event.getModels().put(resourceLocation, new LuxCapacitorModelWrapper((OBJBakedCompositeModel)model));
            }
        });

        BakedModel powerFistIcon1 = event.getModels().get(powerFistIconLocation1);
        BakedModel powerFistIcon2 = event.getModels().get(powerFistIconLocation2);
        BakedModel powerFistIcon3 = event.getModels().get(powerFistIconLocation3);
        BakedModel powerFistIcon4 = event.getModels().get(powerFistIconLocation4);

        if (!OBJBakedCompositeModel.class.isInstance(powerFistIcon1)) {
            event.getModels().put(powerFistIconLocation1, new PowerFistModelWrapper(powerFistIcon1));
            event.getModels().put(powerFistIconLocation2, new PowerFistModelWrapper(powerFistIcon2));
            event.getModels().put(powerFistIconLocation3, new PowerFistModelWrapper(powerFistIcon3));
            event.getModels().put(powerFistIconLocation4, new PowerFistModelWrapper(powerFistIcon4));
        }
    }

    public enum DIR {
        DOWN(Direction.DOWN),
        UP(Direction.UP),
        NORTH(Direction.NORTH),
        SOUTH(Direction.SOUTH),
        WEST(Direction.WEST),
        EAST(Direction.EAST),
        NONE(null);
        public final Direction direction;
        DIR(Direction direction) {
            this.direction = direction;
        }
    }

    @SubscribeEvent
    public static void onAddAdditional(ModelEvent.RegisterAdditional e) {
        e.register(plasmaBall);
        locations.forEach(location -> e.register(location));
    }

    public static List<ResourceLocation> locations = new ArrayList<>();

    public BakedModel getBakedItemModel(ResourceLocation location) {
        return  Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(location);
    }
}