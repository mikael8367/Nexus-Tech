package br.com.mikael.nexustech.registry;

import br.com.mikael.nexustech.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Central Forge registry event handler.
 * Add real Block and Item instances to these registries as content is created.
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class RegistryHandler {
    private RegistryHandler() {
        // Event subscriber class.
    }

    @SubscribeEvent
    public static void onBlocksRegister(RegistryEvent.Register<Block> event) {
        // Example later: event.getRegistry().register(ModBlocks.MACHINE);
    }

    @SubscribeEvent
    public static void onItemsRegister(RegistryEvent.Register<Item> event) {
        // Example later: event.getRegistry().register(ModItems.COMPONENT);
    }
}
