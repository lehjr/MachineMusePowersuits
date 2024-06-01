package lehjr.numina.common.capabilities.module.powermodule;

import com.google.common.collect.ImmutableList;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public interface IPowerModule extends IConfigGetter {






    default boolean getGenericBooleanProperty(ImmutableList key, Callable<IConfig> config, boolean defBool) {
        return getConfig(config).map(iconfig-> iconfig.getGenericBooleanProperty(key)).orElse(defBool);
    }


    // TODO: move to somewhere else??
    @OnlyIn(Dist.CLIENT) // only used by the client for display purposes
    default String getUnit(@Nonnull String propertyName) {
        return UnitMap.MAP.getUnit(propertyName);
    }





}
