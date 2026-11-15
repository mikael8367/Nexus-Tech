package br.com.mikael.nexustech.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

/** Smelts like a furnace, but ores come out as two ingots. */
public class TileEntityIndustrialSmelter extends TileEntityMachine {
    @Override
    public ItemStack getResult(ItemStack input) {
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
        if (result.isEmpty() || !isOre(input)) {
            return result;
        }
        ItemStack doubled = result.copy();
        doubled.setCount(Math.min(doubled.getMaxStackSize(), result.getCount() * 2));
        return doubled;
    }

    private static boolean isOre(ItemStack stack) {
        for (int id : OreDictionary.getOreIDs(stack)) {
            if (OreDictionary.getOreName(id).startsWith("ore")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getProcessTime() {
        return 200;
    }

    @Override
    public int getEnergyPerTick() {
        return 40;
    }
}
