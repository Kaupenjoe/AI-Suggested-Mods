package net.kaupenjoe.gptmod.item;

import net.kaupenjoe.gptmod.GPTMod;
import net.kaupenjoe.gptmod.item.custom.FireOrbItem;
import net.kaupenjoe.gptmod.item.custom.RemoteItem;
import net.kaupenjoe.gptmod.item.custom.TeleporterOrbItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final Item TELEPORTER_ORB = registerItem("teleporter_orb",
            new TeleporterOrbItem(new Item.Properties().tab(ModCreativeModeTab.MAGIC_TAB).durability(16)));

    public static final Item Fire_ORB = registerItem("fire_orb",
            new FireOrbItem(new Item.Properties().tab(ModCreativeModeTab.MAGIC_TAB).durability(16)));

    public static final Item DRONE_REMOTE = registerItem("drone_remote",
            new RemoteItem(new Item.Properties().tab(ModCreativeModeTab.DRONE_TAB)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new ResourceLocation(GPTMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering Mod Items for " + GPTMod.MOD_ID);
    }
}
