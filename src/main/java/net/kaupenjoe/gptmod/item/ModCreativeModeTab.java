package net.kaupenjoe.gptmod.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.kaupenjoe.gptmod.GPTMod;
import net.kaupenjoe.gptmod.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab MAGIC_TAB = FabricItemGroupBuilder.create(new ResourceLocation(GPTMod.MOD_ID,"magic-tab"))
            .icon(() -> new ItemStack(ModItems.TELEPORTER_ORB)).build();

    public static final CreativeModeTab DRONE_TAB = FabricItemGroupBuilder.create(new ResourceLocation(GPTMod.MOD_ID,"drone-tab"))
            .icon(() -> new ItemStack(ModItems.DRONE_REMOTE)).build();
}