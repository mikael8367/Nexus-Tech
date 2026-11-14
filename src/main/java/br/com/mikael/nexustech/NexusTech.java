package br.com.mikael.nexustech;

import br.com.mikael.nexustech.proxy.CommonProxy;
import br.com.mikael.nexustech.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = Reference.MOD_ID,
    name = Reference.NAME,
    version = Reference.VERSION,
    acceptedMinecraftVersions = "[1.12.2]",
    dependencies = "required-after:forge"
)
public final class NexusTech {
    @Mod.Instance(Reference.MOD_ID)
    public static NexusTech INSTANCE;

    @SidedProxy(
        clientSide = Reference.CLIENT_PROXY,
        serverSide = Reference.COMMON_PROXY
    )
    public static CommonProxy PROXY;

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("{} is loading for Minecraft 1.12.2.", Reference.NAME);
        PROXY.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init(event);
        logger.info("{} initialized.", Reference.NAME);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit(event);
        logger.info("{} finished post-initialization.", Reference.NAME);
    }
}
