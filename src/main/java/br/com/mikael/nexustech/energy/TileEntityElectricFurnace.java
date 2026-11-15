package br.com.mikael.nexustech.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/** Smelts anything a vanilla furnace can, twice as fast and without fuel. */
public class TileEntityElectricFurnace extends TileEntityMachine {
    @Override
    public ItemStack getResult(ItemStack input) {
        return FurnaceRecipes.instance().getSmeltingResult(input);
    }

    @Override
    public int getProcessTime() {
        return 100;
    }

    @Override
    public int getEnergyPerTick() {
        return 20;
    }
}
