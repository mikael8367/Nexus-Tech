package br.com.mikael.nexustech.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/** Code shared by the physical client and dedicated server. */
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        // Register shared systems and initialize common configuration here.
    }

    public void init(FMLInitializationEvent event) {
        // Register recipes, handlers and integrations here.
    }

    public void postInit(FMLPostInitializationEvent event) {
        // Run compatibility checks after other mods have initialized.
    }
}
