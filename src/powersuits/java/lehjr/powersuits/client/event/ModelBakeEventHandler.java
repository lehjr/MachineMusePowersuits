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

package lehjr.powersuits.client.event;


import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.client.model.block.LuxCapacitorModelWrapper;
import lehjr.powersuits.client.model.helper.LuxCapHelper;
import lehjr.powersuits.client.model.item.PowerFistModelWrapper;
import lehjr.powersuits.client.render.item.MPSBEWLR;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum ModelBakeEventHandler {
    INSTANCE;
    public static final ResourceLocation plasmaBall = new ResourceLocation(MPSConstants.MOD_ID, "entity/plasma");


//    Map<Direction, Map<DIR, List<BakedQuad>>> quadMap = new HashMap<>();
//
//    Map<Direction, BakedModel> luxCapacitorBlockModels = new HashMap<>();


    public MPSBEWLR MPSBERINSTANCE = new MPSBEWLR();

//    ModelResourceLocation luxCapItemLocation = new ModelResourceLocation(MPSRegistryNames.LUX_CAPACITOR, "inventory");
//    ModelResourceLocation luxCapModuleLocation = new ModelResourceLocation(MPSRegistryNames.LUX_CAPACITOR_MODULE, "inventory");

    public static final ModelResourceLocation powerFistIconLocation = new ModelResourceLocation(MPSRegistryNames.POWER_FIST, "inventory");

    RandomSource rand = RandomSource.create();


    @SubscribeEvent
    public void onModelBake(ModelEvent.BakingCompleted event) {
        Map<ResourceLocation, BakedModel> registry = event.getModels();
        for (Direction facing : Direction.values()) {
            BakedModel model = registry.get(new ModelResourceLocation(new ResourceLocation("powersuits:luxcapacitor"), "facing=" + facing + ",waterlogged=true"));
            if(model instanceof OBJBakedCompositeModel) {
                HashMap<DIR, List<BakedQuad>> map = new HashMap<>();
                for (DIR dir : DIR.values()) {
                    map.put(dir, model.getQuads(null, dir.direction, rand, LuxCapHelper.getBlockLensModelData(Color.WHITE.getARGBInt()), RenderTypeGroup.EMPTY.entity()));
                }
//                INSTANCE.quadMap.put(facing, map);
//                INSTANCE.luxCapacitorBlockModels.put(facing, model);
            }
        }

        event.getModels().keySet().stream().filter(resourceLocation -> resourceLocation.toString().contains("powersuits:luxcapacitor")).forEach(resourceLocation -> {
            BakedModel model = event.getModels().get(resourceLocation);
            if (model instanceof OBJBakedCompositeModel) {
                event.getModels().put(resourceLocation, new LuxCapacitorModelWrapper((OBJBakedCompositeModel)model));
            }
        });

//        event.getModels().keySet().stream().filter(resourceLocation -> resourceLocation.toString().contains("powersuits:powerfist")).forEach(resourceLocation -> NuminaLogger.logError("modelLocation: " + resourceLocation));


//        event.getModels().keySet().stream().filter(resourceLocation -> resourceLocation.toString().contains("powersuits:luxcapacitor")).forEach(resourceLocation -> {
//            event.getModels().put(resourceLocation, new LuxCapModelWrapper2(event.getModels().get(resourceLocation)));
//        });

//        event.getModels().keySet().stream().filter(resourceLocation -> resourceLocation.toString().contains("powersuits:powerfist")).forEach(resourceLocation -> NuminaLogger.logError("modelLocation: " + resourceLocation));
//



        BakedModel powerFistIcon = event.getModels().get(powerFistIconLocation);
        if (!OBJBakedCompositeModel.class.isInstance(powerFistIcon)) {
            event.getModels().put(powerFistIconLocation, new PowerFistModelWrapper(powerFistIcon));
        }




//        MPSModelHelper.loadArmorModels(null, event.getModelBakery());
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
    public void onAddAdditional(ModelEvent.RegisterAdditional e) {
        e.register(plasmaBall);
        locations.forEach(location -> e.register(location));
    }

    public static List<ResourceLocation> locations = new ArrayList<>();

    public BakedModel getBakedItemModel(ResourceLocation location) {
        return  Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(location);
    }
}