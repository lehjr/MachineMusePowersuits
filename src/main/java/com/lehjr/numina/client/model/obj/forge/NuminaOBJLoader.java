/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.lehjr.numina.client.model.obj.forge;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.obj.LineReader;
import net.minecraftforge.client.model.obj.MaterialLibrary;

import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Copied from Forge's OBJ Loader
 */
public class NuminaOBJLoader implements IModelLoader<NuminaOBJModel> {
    public static NuminaOBJLoader INSTANCE = new NuminaOBJLoader();

    private final Map<NuminaOBJModel.ModelSettings, NuminaOBJModel> modelCache = Maps.newHashMap();
    private final Map<ResourceLocation, MaterialLibrary> materialCache = Maps.newHashMap();

    private ResourceManager manager = Minecraft.getInstance().getResourceManager();

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        modelCache.clear();
        materialCache.clear();
        manager = resourceManager;
    }

    @Override
    public NuminaOBJModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        if (!modelContents.has("model"))
            throw new RuntimeException("OBJ Loader requires a 'model' key that points to a valid .OBJ model.");

        String modelLocation = modelContents.get("model").getAsString();

        boolean detectCullableFaces = GsonHelper.getAsBoolean(modelContents, "detectCullableFaces", true);
        boolean diffuseLighting = GsonHelper.getAsBoolean(modelContents, "diffuseLighting", false);
        boolean flipV = GsonHelper.getAsBoolean(modelContents, "flip-v", false);
        boolean ambientToFullbright = GsonHelper.getAsBoolean(modelContents, "ambientToFullbright", true);
        @Nullable
        String materialLibraryOverrideLocation = modelContents.has("materialLibraryOverride") ? GsonHelper.getAsString(modelContents, "materialLibraryOverride") : null;

        return loadModel(new NuminaOBJModel.ModelSettings(new ResourceLocation(modelLocation), detectCullableFaces, diffuseLighting, flipV, ambientToFullbright, materialLibraryOverrideLocation));
    }

    public NuminaOBJModel loadModel(NuminaOBJModel.ModelSettings settings) {
        return modelCache.computeIfAbsent(settings, (data) -> {

            try (Resource resource = manager.getResource(settings.modelLocation());
                 LineReader rdr = new LineReader(resource)) {
                return new NuminaOBJModel(rdr, settings);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Could not find OBJ model", e);
            } catch (Exception e) {
                throw new RuntimeException("Could not read OBJ model", e);
            }
        });
    }

    public MaterialLibrary loadMaterialLibrary(ResourceLocation materialLocation) {
        return materialCache.computeIfAbsent(materialLocation, (location) -> {
            try (Resource resource = manager.getResource(location);
                 LineReader rdr = new LineReader(resource)) {
                return new MaterialLibrary(rdr);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Could not find OBJ material library", e);
            } catch (Exception e) {
                throw new RuntimeException("Could not read OBJ material library", e);
            }
        });
    }
}