package br.com.mikael.nexustech.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/** Client-only setup. Do not reference Minecraft client classes from CommonProxy. */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // Client-only setup, such as renderers, belongs here.
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        // Client-only key bindings and screens belong here.
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
