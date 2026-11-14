package br.com.mikael.nexustech;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * Main entry point for Nexus Tech.
 *
 * This is the initial Forge 1.12.2 project scaffold. Gameplay content
 * (blocks, items, machines and recipes) can be added in separate classes.
 */
@Mod(
    modid = NexusTech.MOD_ID,
    name = NexusTech.NAME,
    version = NexusTech.VERSION,
    acceptedMinecraftVersions = "[1.12.2]",
    dependencies = "required-after:forge"
)
public final class NexusTech {
    public static final String MOD_ID = "nexus_tech";
    public static final String NAME = "Nexus Tech";
    public static final String VERSION = "1.0.0";

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("Nexus Tech carregando no Minecraft 1.12.2.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Nexus Tech inicializado.");
    }
}
