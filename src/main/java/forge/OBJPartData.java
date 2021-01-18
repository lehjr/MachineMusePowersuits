package forge;

import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.Map;

public class OBJPartData {
    public static final ModelProperty<OBJPartData> SUBMODEL_DATA = new ModelProperty<>();
    public static final ModelProperty<Integer> COLOUR = new ModelProperty<>();
    public static final ModelProperty<Boolean> VISIBLE = new ModelProperty<>();
    public static final ModelProperty<Boolean> GLOW = new ModelProperty<>();

    private final Map<String, IModelData> parts;

    public OBJPartData(Map<String, IModelData> parts) {
        this.parts = parts;
    }

    public IModelData getSubmodelData(String name) {
        return parts.getOrDefault(name, EmptyModelData.INSTANCE);
    }

    public void putSubmodelData(String name, IModelData data) {
        parts.put(name, data);
    }

    public static IModelData makeOBJPartData(boolean glow, boolean visible, int colour) {
        return new ModelDataMap.Builder()
                .withInitial(GLOW, glow)
                .withInitial(COLOUR, colour)
                .withInitial(VISIBLE, visible)
                .build();
    }

    public static IModelData getOBJPartData(IModelData extraData, String name) {
        OBJPartData data = extraData.getData(SUBMODEL_DATA);
        if (data == null) {
            return EmptyModelData.INSTANCE;
        }
        return data.getSubmodelData(name);
    }
}