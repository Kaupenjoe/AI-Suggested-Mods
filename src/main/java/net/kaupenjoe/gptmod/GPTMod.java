package net.kaupenjoe.gptmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.kaupenjoe.gptmod.entity.ModEntities;
import net.kaupenjoe.gptmod.entity.custom.DroneEntity;
import net.kaupenjoe.gptmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GPTMod implements ModInitializer {
	public static final String MOD_ID = "gpt-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();

		FabricDefaultAttributeRegistry.register(ModEntities.DRONE, DroneEntity.setAttributes());
	}
}
