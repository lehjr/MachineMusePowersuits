package com.github.lehjr.numina.util.nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;

public class NBT2Json {
    public static JsonObject CompoundNBT2Json(CompoundNBT nbt, JsonObject jsonObjectIn) {
        for (String key : nbt.keySet()) {
            switch(nbt.getTagId(key)) {
                // Note this is also how a bool is stored
                case Constants.NBT.TAG_BYTE: // 1
                    jsonObjectIn.addProperty(key, nbt.getByte(key));
                    break;

                case Constants.NBT.TAG_SHORT: // 2
                    jsonObjectIn.addProperty(key, nbt.getShort(key));
                    break;

                case Constants.NBT.TAG_INT: // 3
                    jsonObjectIn.addProperty(key, nbt.getInt(key));
                    break;

                case Constants.NBT.TAG_LONG: // 4
                    jsonObjectIn.addProperty(key, nbt.getLong(key));
                    break;

                case Constants.NBT.TAG_FLOAT: // 5
                    jsonObjectIn.addProperty(key, nbt.getFloat(key));
                    break;

                case Constants.NBT.TAG_DOUBLE: // 6
                    jsonObjectIn.addProperty(key, nbt.getDouble(key));
                    break;

                case Constants.NBT.TAG_BYTE_ARRAY: // 7
                    JsonArray array = new JsonArray();
                    for (byte b : nbt.getByteArray(key)) {
                        array.add(b);
                    }

                    // filter out empty tags
                    if (array.size() > 0) {
                        jsonObjectIn.add(key, array);
                    }
                    break;

                case Constants.NBT.TAG_STRING: // 8
                    jsonObjectIn.addProperty(key, nbt.getString(key));
                    break;

                case Constants.NBT.TAG_LIST: // 9
                    System.out.println("nbt List is broken: " + key + "<>" + nbt.get(key));
//                    jsonObjectIn.addProperty(key, nbt.getList(key, 0)); // kind of list?
                    break;

                case Constants.NBT.TAG_COMPOUND: // 10
                    // filter out empty compound tags
                    if (!nbt.getCompound(key).isEmpty()) {
                        jsonObjectIn.add(key, CompoundNBT2Json(nbt.getCompound(key), new JsonObject()));
                    }
                    break;

                case Constants.NBT.TAG_INT_ARRAY: // 11
                    JsonArray array1 = new JsonArray();
                    for (int b : nbt.getIntArray(key)) {
                        array1.add(b);
                    }

                    // filter out empty tags
                    if (array1.size() > 0) {
                        jsonObjectIn.add(key, array1);
                    }
                    break;

                case Constants.NBT.TAG_LONG_ARRAY: // 12
                    JsonArray array2 = new JsonArray();
                    for (long b : nbt.getLongArray(key)) {
                        array2.add(b);
                    }

                    // filter out empty tags
                    if (array2.size() > 0) {
                        jsonObjectIn.add(key, array2);
                    }
                    break;
            }
        }
        return jsonObjectIn;
    }
}