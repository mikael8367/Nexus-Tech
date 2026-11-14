package br.com.mikael.nexustech.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/** Dedicated creative inventory tab for every Nexus Tech block and item. */
public final class ModCreativeTabs {
    public static final CreativeTabs NEXUS_TECH = new CreativeTabs("nexus_tech") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.ENERGY_CELL);
        }
    };

    private ModCreativeTabs() {
    }
}
