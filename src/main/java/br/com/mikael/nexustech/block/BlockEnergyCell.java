package br.com.mikael.nexustech.block;
import br.com.mikael.nexustech.energy.TileEntityEnergyCell;
import br.com.mikael.nexustech.util.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
/** Tiered FE storage block. The same registered TileEntity class stores each tier's capacity in its instance. */
public class BlockEnergyCell extends BlockContainer {
 private final int capacity;
 private final int transferRate;
 public BlockEnergyCell(String id,int capacity,int transferRate){
  super(Material.IRON);
  this.capacity=capacity;
  this.transferRate=transferRate;
  setRegistryName(Reference.MOD_ID,id);
  setUnlocalizedName(Reference.MOD_ID+"."+id);
  setHardness(3);
  setCreativeTab(net.minecraft.creativetab.CreativeTabs.REDSTONE);
 }
 public TileEntity createNewTileEntity(World world,int meta){return new TileEntityEnergyCell(capacity,transferRate);}
}