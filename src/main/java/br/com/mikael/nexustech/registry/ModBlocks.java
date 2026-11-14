package br.com.mikael.nexustech.registry;
import br.com.mikael.nexustech.block.*;
import net.minecraft.block.Block;
public final class ModBlocks{
 public static final Block ENERGY_CABLE=new BlockEnergyCable();
 public static final Block SOLAR_PANEL=new BlockEnergyGenerator("solar_panel",BlockEnergyGenerator.Type.SOLAR);
 public static final Block COAL_GENERATOR=new BlockEnergyGenerator("coal_generator",BlockEnergyGenerator.Type.COAL);
 public static final Block WIND_TURBINE=new BlockEnergyGenerator("wind_turbine",BlockEnergyGenerator.Type.WIND);
 public static final Block ENERGY_CELL=new BlockEnergyCell("energy_cell",100000,250);
 public static final Block ENERGY_CELL_ADVANCED=new BlockEnergyCell("energy_cell_advanced",500000,1000);
 public static final Block ENERGY_CELL_ELITE=new BlockEnergyCell("energy_cell_elite",2500000,4000);
 public static final Block ENERGY_CELL_ULTIMATE=new BlockEnergyCell("energy_cell_ultimate",10000000,16000);
 public static final Block[] BLOCKS={ENERGY_CABLE,SOLAR_PANEL,COAL_GENERATOR,WIND_TURBINE,ENERGY_CELL,ENERGY_CELL_ADVANCED,ENERGY_CELL_ELITE,ENERGY_CELL_ULTIMATE};
 private ModBlocks(){}
}