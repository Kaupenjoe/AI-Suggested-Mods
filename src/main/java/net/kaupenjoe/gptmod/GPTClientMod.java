package net.kaupenjoe.gptmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.kaupenjoe.gptmod.entity.ModEntities;
import net.kaupenjoe.gptmod.entity.client.DroneEntityRenderer;

public class GPTClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.DRONE, DroneEntityRenderer::new);
	}
}
