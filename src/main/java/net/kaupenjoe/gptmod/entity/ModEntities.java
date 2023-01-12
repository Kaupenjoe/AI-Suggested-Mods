package net.kaupenjoe.gptmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.kaupenjoe.gptmod.GPTMod;
import net.kaupenjoe.gptmod.entity.custom.DroneEntity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final EntityType<DroneEntity> DRONE = Registry.register(
            Registry.ENTITY_TYPE, new ResourceLocation(GPTMod.MOD_ID, "drone"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, DroneEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
}
