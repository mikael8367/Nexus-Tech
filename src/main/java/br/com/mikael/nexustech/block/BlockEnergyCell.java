package br.com.mikael.nexustech.block;
import br.com.mikael.nexustech.energy.TileEntityEnergyCell;
import br.com.mikael.nexustech.util.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
/** Tiered FE storage block. Each placed tier creates storage with its own capacity and transfer limit. */
public class BlockEnergyCell extends BlockContainer {
 private final int capacity;
 private final int transferRate;
 private final String id;
 public BlockEnergyCell(String id,int capacity,int transferRate){
  super(Material.IRON);
  this.id=id;
  this.capacity=Math.max(1,capacity);
  this.transferRate=Math.max(1,transferRate);
  setRegistryName(Reference.MOD_ID,id);
  setHardness(3);
  setCreativeTab(net.minecraft.creativetab.CreativeTabs.REDSTONE);
 }
 public String getUnlocalizedName(){return "tile."+Reference.MOD_ID+"."+id;}
 public TileEntity createNewTileEntity(World world,int meta){return new TileEntityEnergyCell(capacity,transferRate);}
}