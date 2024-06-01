/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package forge.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.obj.ObjMaterialLibrary;
import net.neoforged.neoforge.client.model.obj.ObjTokenizer;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * A loader for {@link NuminaObjModel OBJ models}.
 * <p>
 * Allows the user to enable automatic face culling, toggle quad shading, flip UVs, render emissively and specify a
 * {@link ObjMaterialLibrary material library} override.
 */
public class NuminaObjLoader implements IGeometryLoader<NuminaObjModel>, ResourceManagerReloadListener {
    public static NuminaObjLoader INSTANCE = new NuminaObjLoader();

    private final Map<NuminaObjModel.ModelSettings, NuminaObjModel> modelCache = Maps.newConcurrentMap();
    private final Map<ResourceLocation, ObjMaterialLibrary> materialCache = Maps.newConcurrentMap();

    private ResourceManager manager = Minecraft.getInstance().getResourceManager();

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        modelCache.clear();
        materialCache.clear();
        manager = resourceManager;
    }

    @Override
    public NuminaObjModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) {
        if (!jsonObject.has("model"))
            throw new JsonParseException("OBJ Loader requires a 'model' key that points to a valid .OBJ model.");

        String modelLocation = jsonObject.get("model").getAsString();

        boolean automaticCulling = GsonHelper.getAsBoolean(jsonObject, "automatic_culling", true);
        boolean shadeQuads = GsonHelper.getAsBoolean(jsonObject, "shade_quads", true);
        boolean flipV = GsonHelper.getAsBoolean(jsonObject, "flip_v", false);
        boolean emissiveAmbient = GsonHelper.getAsBoolean(jsonObject, "emissive_ambient", true);
        String mtlOverride = GsonHelper.getAsString(jsonObject, "mtl_override", null);

        // TODO: Deprecated names. To be removed in 1.20
        var deprecationWarningsBuilder = ImmutableMap.<String, String>builder();
        if (jsonObject.has("detectCullableFaces")) {
            automaticCulling = GsonHelper.getAsBoolean(jsonObject, "detectCullableFaces");
            deprecationWarningsBuilder.put("detectCullableFaces", "automatic_culling");
        }
        if (jsonObject.has("diffuseLighting")) {
            shadeQuads = GsonHelper.getAsBoolean(jsonObject, "diffuseLighting");
            deprecationWarningsBuilder.put("diffuseLighting", "shade_quads");
        }
        if (jsonObject.has("flip-v")) {
            flipV = GsonHelper.getAsBoolean(jsonObject, "flip-v");
            deprecationWarningsBuilder.put("flip-v", "flip_v");
        }
        if (jsonObject.has("ambientToFullbright")) {
            emissiveAmbient = GsonHelper.getAsBoolean(jsonObject, "ambientToFullbright");
            deprecationWarningsBuilder.put("ambientToFullbright", "emissive_ambient");
        }
        if (jsonObject.has("materialLibraryOverride")) {
            mtlOverride = GsonHelper.getAsString(jsonObject, "materialLibraryOverride");
            deprecationWarningsBuilder.put("materialLibraryOverride", "mtl_override");
        }

        return loadModel(new NuminaObjModel.ModelSettings(
                new ResourceLocation(modelLocation), automaticCulling, shadeQuads, flipV, emissiveAmbient, mtlOverride), deprecationWarningsBuilder.build());
    }

    public NuminaObjModel loadWithDefaultSettings(ResourceLocation location) {
        return loadModel(
                new NuminaObjModel.ModelSettings(
                        location,
                        true,
                        true,
                        false,
                        true,
                        null));
    }

    public NuminaObjModel loadModel(NuminaObjModel.ModelSettings settings) {
        return loadModel(settings, Map.of());
    }

    private NuminaObjModel loadModel(NuminaObjModel.ModelSettings settings, Map<String, String> deprecationWarnings) {
        return modelCache.computeIfAbsent(settings, (data) -> {
            NuminaLogger.logDebug("numinaOBJLoader model location: " + settings.modelLocation());
            Resource resource = manager.getResource(settings.modelLocation()).orElseThrow();
            try (ObjTokenizer tokenizer = new ObjTokenizer(resource.open())) {
                return NuminaObjModel.parse(tokenizer, settings, deprecationWarnings);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Could not find OBJ model", e);
            } catch (Exception e) {
                throw new RuntimeException("Could not read OBJ model", e);
            }
        });
    }

    public ObjMaterialLibrary loadMaterialLibrary(ResourceLocation materialLocation) {
        return materialCache.computeIfAbsent(materialLocation, (location) -> {
            Resource resource = manager.getResource(location).orElseThrow();
            try (ObjTokenizer rdr = new ObjTokenizer(resource.open())) {
                return new ObjMaterialLibrary(rdr);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Could not find OBJ material library", e);
            } catch (Exception e) {
                throw new RuntimeException("Could not read OBJ material library", e);
            }
        });
    }
}
