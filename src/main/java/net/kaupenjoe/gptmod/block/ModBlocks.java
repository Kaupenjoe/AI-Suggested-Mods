package net.kaupenjoe.gptmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.kaupenjoe.gptmod.GPTMod;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModBlocks {



    private static Block registerBlock(String name, Block block, CreativeModeTab tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new ResourceLocation(GPTMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, CreativeModeTab tab) {
        return Registry.register(Registry.ITEM, new ResourceLocation(GPTMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static void registerModBlocks() {
        System.out.println("Registering ModBlocks for " + GPTMod.MOD_ID);
    }
}
