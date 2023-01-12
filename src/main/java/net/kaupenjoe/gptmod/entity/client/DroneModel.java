package net.kaupenjoe.gptmod.entity.client;

import net.kaupenjoe.gptmod.GPTMod;
import net.kaupenjoe.gptmod.entity.custom.DroneEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DroneModel extends AnimatedGeoModel<DroneEntity> {
    @Override
    public ResourceLocation getModelResource(DroneEntity object) {
        return new ResourceLocation(GPTMod.MOD_ID, "geo/drone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DroneEntity object) {
        return new ResourceLocation(GPTMod.MOD_ID, "textures/entity/drone.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DroneEntity animatable) {
        return new ResourceLocation(GPTMod.MOD_ID, "animations/drone.animation.json");
    }
}
