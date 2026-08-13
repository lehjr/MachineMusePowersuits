package numina.client.model;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public abstract class AbstractItemModelProvider extends ItemModelProvider {
    public AbstractItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public ItemModelBuilder basicItem(Item item, String texturePath) {
        return this.basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item), texturePath));
    }

    public ItemModelBuilder basicItem(ResourceLocation item, String texturePath) {
        return this.getBuilder(item.toString()).parent(
            new ModelFile.UncheckedModelFile("item/generated")).texture("layer0",
            ResourceLocation.fromNamespaceAndPath(item.getNamespace(), texturePath));
    }

}
