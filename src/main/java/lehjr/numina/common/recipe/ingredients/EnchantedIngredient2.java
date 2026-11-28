//package lehjr.numina.common.recipe.ingredients;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSyntaxException;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.GsonHelper;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraftforge.common.crafting.AbstractIngredient;
//import net.minecraftforge.common.crafting.IIngredientSerializer;
//import net.minecraftforge.registries.ForgeRegistries;
//
//import javax.annotation.Nullable;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Stream;
//
//public class EnchantedIngredient2 extends AbstractIngredient {
//    private final Map<Enchantment, Integer> enchantments;
//
//    public EnchantedIngredient2(Map<Enchantment, Integer> enchantments) {
//        super(Stream.of(createDisplayValue(enchantments)));
//        this.enchantments = enchantments;
//    }
//
//    private static ItemValue createDisplayValue(Map<Enchantment, Integer> enchantments) {
//        ItemStack displayStack = new ItemStack(Items.ENCHANTED_BOOK);
//        enchantments.forEach(displayStack::enchant);
//        return new ItemValue(displayStack);
//    }
//
//    @Override
//    public boolean test(@Nullable ItemStack stack) {
//        if (stack == null || stack.isEmpty()) {
//            return false;
//        }
//        return enchantments.entrySet().stream()
//                .allMatch(entry -> EnchantmentHelper.getTagEnchantmentLevel(entry.getKey(), stack) >= entry.getValue());
//    }
//
//    @Override
//    public boolean isSimple() {
//        return false;
//    }
//
//    @Override
//    public IIngredientSerializer<? extends AbstractIngredient> getSerializer() {
//        return Serializer.INSTANCE;
//    }
//
//    @Override
//    public JsonElement toJson() {
//        JsonObject json = new JsonObject();
//        json.addProperty("slotType", Serializer.ID.toString());
//        JsonObject enchJson = new JsonObject();
//        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
//            ResourceLocation key = ForgeRegistries.ENCHANTMENTS.getKey(entry.getKey());
//            if (key != null) {
//                enchJson.addProperty(key.toString(), entry.getValue());
//            }
//        }
//        json.add("exclusive_set", enchJson);
//        return json;
//    }
//
//    public static class Serializer implements IIngredientSerializer<EnchantedIngredient2> {
//        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("numina", "min_enchanted");
//        public static final Serializer INSTANCE = new Serializer();
//
//        @Override
//        public EnchantedIngredient2 parse(FriendlyByteBuf buffer) {
//            int size = buffer.readVarInt();
//            Map<Enchantment, Integer> map = new HashMap<>(size);
//            for (int i = 0; i < size; i++) {
//                Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(buffer.readResourceLocation());
//                int level = buffer.readVarInt();
//                if (ench != null) {
//                    map.put(ench, level);
//                }
//            }
//            return new EnchantedIngredient2(map);
//        }
//
//        @Override
//        public EnchantedIngredient2 parse(JsonObject json) {
//            Map<Enchantment, Integer> map = new HashMap<>();
//            if (json.has("exclusive_set")) {
//                JsonObject enchJson = GsonHelper.getAsJsonObject(json, "exclusive_set");
//                for (Map.Entry<String, JsonElement> entry : enchJson.entrySet()) {
//                    ResourceLocation id = ResourceLocation.fromNamespaceAndPath(entry.getKey());
//                    Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(id);
//                    if (ench == null) {
//                        throw new JsonSyntaxException("Unknown enchantment '" + id + "'");
//                    }
//                    map.put(ench, entry.getValue().getAsInt());
//                }
//            }
//            return new EnchantedIngredient2(map);
//        }
//
//        @Override
//        public void write(FriendlyByteBuf buffer, EnchantedIngredient2 ingredient) {
//            buffer.writeVarInt(ingredient.enchantments.size());
//            for (Map.Entry<Enchantment, Integer> entry : ingredient.enchantments.entrySet()) {
//                ResourceLocation key = ForgeRegistries.ENCHANTMENTS.getKey(entry.getKey());
//                buffer.writeResourceLocation(key != null ? key : ResourceLocation.fromNamespaceAndPath("minecraft", "empty"));
//                buffer.writeVarInt(entry.getValue());
//            }
//        }
//    }
//}
