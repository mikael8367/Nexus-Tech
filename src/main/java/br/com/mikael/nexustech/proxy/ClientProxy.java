package br.com.mikael.nexustech.proxy;

import br.com.mikael.nexustech.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Client-only setup. Do not reference Minecraft client classes from CommonProxy. */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    /** Every block item needs an explicit "inventory" model, otherwise it renders as the missing texture. */
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        for (Block block : ModBlocks.BLOCKS) {
            Item item = Item.getItemFromBlock(block);
            if (item != null && block.getRegistryName() != null) {
                ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(block.getRegistryName(), "inventory"));
            }
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        // Tier colours for the upgraded energy cells and the machines (models use tintindex 0).
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
            (state, world, pos, tint) -> tierColor(state.getBlock()),
            ModBlocks.ENERGY_CELL_ADVANCED, ModBlocks.ENERGY_CELL_ELITE, ModBlocks.ENERGY_CELL_ULTIMATE,
            ModBlocks.ELECTRIC_FURNACE, ModBlocks.INDUSTRIAL_SMELTER);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
            (stack, tint) -> tierColor(Block.getBlockFromItem(stack.getItem())),
            ModBlocks.ENERGY_CELL_ADVANCED, ModBlocks.ENERGY_CELL_ELITE, ModBlocks.ENERGY_CELL_ULTIMATE,
            ModBlocks.ELECTRIC_FURNACE, ModBlocks.INDUSTRIAL_SMELTER);
    }

    private static int tierColor(Block block) {
        if (block == ModBlocks.ENERGY_CELL_ADVANCED) return 0xFFD700;
        if (block == ModBlocks.ENERGY_CELL_ELITE) return 0x55FFFF;
        if (block == ModBlocks.ENERGY_CELL_ULTIMATE) return 0xFF55FF;
        if (block == ModBlocks.ELECTRIC_FURNACE) return 0x9FC7FF;
        if (block == ModBlocks.INDUSTRIAL_SMELTER) return 0xFFB070;
        return 0xFFFFFF;
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
