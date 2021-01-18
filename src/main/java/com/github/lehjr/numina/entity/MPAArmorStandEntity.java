package com.github.lehjr.numina.entity;

import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.container.ArmorStandContainer;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * TODO: decide if keeping this or just using vanilla armor stand. This really doesn't do anything, ... YET?
 */


public class MPAArmorStandEntity extends ArmorStandEntity {
    public MPAArmorStandEntity(EntityType<? extends MPAArmorStandEntity> entityType, World world) {
        super(entityType, world);
        setShowArms(true);
    }

    public MPAArmorStandEntity(World worldIn, double posX, double posY, double posZ) {
        super(worldIn, posX, posY, posZ);
    }

    //func_233666_p_ ---> registerAttributes()
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D);
    }

    @Override
    protected void breakArmorStand(DamageSource source) {
        Block.spawnAsEntity(this.world, this.getPosition(), new ItemStack(NuminaObjects.ARMOR_STAND_ITEM.get()));
        this.func_213816_g(source);
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (!this.hasMarker() && itemstack.getItem() != Items.NAME_TAG) {
            if (player.isSpectator()) {
                return ActionResultType.SUCCESS;
            } else if (player.world.isRemote) {
                return ActionResultType.SUCCESS;
            } else {
                    player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1.0F, 1.0F);
                    NetworkHooks.openGui((ServerPlayerEntity) player,
                            new SimpleNamedContainerProvider((windowID, playerInventory, playerEntity) ->
                                    new ArmorStandContainer(windowID, playerInventory, (ArmorStandEntity) getEntity()),
                                    new TranslationTextComponent("screen.numina.armor_stand")),
                            buf -> buf.writeInt(getEntityId()));
                return ActionResultType.SUCCESS;
            }
        } else {
            return ActionResultType.PASS;
        }
    }
}
