package br.com.mikael.nexustech.proxy;
import br.com.mikael.nexustech.energy.*;import br.com.mikael.nexustech.gui.GuiHandler;import br.com.mikael.nexustech.registry.ModBlocks;import br.com.mikael.nexustech.util.Reference;import net.minecraft.init.Items;import net.minecraft.init.Blocks;import net.minecraft.item.ItemStack;import net.minecraft.util.ResourceLocation;import net.minecraftforge.fml.common.event.*;import net.minecraftforge.fml.common.network.NetworkRegistry;import net.minecraftforge.fml.common.registry.GameRegistry;
public class CommonProxy{
 public void preInit(FMLPreInitializationEvent e){
  GameRegistry.registerTileEntity(TileEntityEnergyCable.class,Reference.MOD_ID+":energy_cable");
  GameRegistry.registerTileEntity(TileEntitySolarPanel.class,Reference.MOD_ID+":solar_panel");
  GameRegistry.registerTileEntity(TileEntityCoalGenerator.class,Reference.MOD_ID+":coal_generator");
  GameRegistry.registerTileEntity(TileEntityWindTurbine.class,Reference.MOD_ID+":wind_turbine");
  GameRegistry.registerTileEntity(TileEntityEnergyCell.class,Reference.MOD_ID+":energy_cell");
  GameRegistry.registerTileEntity(TileEntityElectricFurnace.class,Reference.MOD_ID+":electric_furnace");
  GameRegistry.registerTileEntity(TileEntityIndustrialSmelter.class,Reference.MOD_ID+":industrial_smelter");
  NetworkRegistry.INSTANCE.registerGuiHandler(br.com.mikael.nexustech.NexusTech.INSTANCE,new GuiHandler());
 }
 public void init(FMLInitializationEvent e){
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"energy_cable"),null,new ItemStack(ModBlocks.ENERGY_CABLE,8),"IRI",'I',Items.IRON_INGOT,'R',Items.REDSTONE);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"solar_panel"),null,new ItemStack(ModBlocks.SOLAR_PANEL),"GGG","IRI","III",'G',Blocks.GLASS_PANE,'I',Items.IRON_INGOT,'R',Items.REDSTONE);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"coal_generator"),null,new ItemStack(ModBlocks.COAL_GENERATOR),"III","IFI","IRI",'I',Items.IRON_INGOT,'F',Blocks.FURNACE,'R',Items.REDSTONE);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"wind_turbine"),null,new ItemStack(ModBlocks.WIND_TURBINE)," I ","III","IRI",'I',Items.IRON_INGOT,'R',Items.REDSTONE);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"energy_cell"),null,new ItemStack(ModBlocks.ENERGY_CELL),"IRI","RGR","IRI",'I',Items.IRON_INGOT,'R',Items.REDSTONE,'G',Blocks.GLASS);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"energy_cell_advanced"),null,new ItemStack(ModBlocks.ENERGY_CELL_ADVANCED),"GRG","RBR","GRG",'G',Items.GOLD_INGOT,'R',Items.REDSTONE,'B',ModBlocks.ENERGY_CELL);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"energy_cell_elite"),null,new ItemStack(ModBlocks.ENERGY_CELL_ELITE),"DGD","GBG","DGD",'D',Items.DIAMOND,'G',Items.GOLD_INGOT,'B',ModBlocks.ENERGY_CELL_ADVANCED);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"energy_cell_ultimate"),null,new ItemStack(ModBlocks.ENERGY_CELL_ULTIMATE),"NEN","EBE","NEN",'N',Items.NETHER_STAR,'E',Items.EMERALD,'B',ModBlocks.ENERGY_CELL_ELITE);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"electric_furnace"),null,new ItemStack(ModBlocks.ELECTRIC_FURNACE),"RRR","IFI","IRI",'I',Items.IRON_INGOT,'F',Blocks.FURNACE,'R',Items.REDSTONE);
  GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"industrial_smelter"),null,new ItemStack(ModBlocks.INDUSTRIAL_SMELTER),"GDG","RFR","GDG",'G',Items.GOLD_INGOT,'D',Items.DIAMOND,'R',Items.REDSTONE,'F',ModBlocks.ELECTRIC_FURNACE);
 }
 public void postInit(FMLPostInitializationEvent e){}
}
