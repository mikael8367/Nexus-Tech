package br.com.mikael.nexustech.block;
import br.com.mikael.nexustech.NexusTech;import br.com.mikael.nexustech.energy.*;import br.com.mikael.nexustech.util.Reference;import net.minecraft.block.BlockContainer;import net.minecraft.block.material.Material;import net.minecraft.block.state.IBlockState;import net.minecraft.entity.player.EntityPlayer;import net.minecraft.tileentity.TileEntity;import net.minecraft.util.*;import net.minecraft.util.math.BlockPos;import net.minecraft.world.World;
public class BlockEnergyGenerator extends BlockContainer {
 public enum Type{SOLAR,COAL,WIND}private final Type type;private final String id;
 public BlockEnergyGenerator(String id,Type t){super(Material.IRON);this.id=id;type=t;setRegistryName(Reference.MOD_ID,id);setHardness(3);setCreativeTab(net.minecraft.creativetab.CreativeTabs.REDSTONE);}
 public String getUnlocalizedName(){return "tile.nexus_tech."+id;}
 public TileEntity createNewTileEntity(World w,int m){switch(type){case COAL:return new TileEntityCoalGenerator();case WIND:return new TileEntityWindTurbine();default:return new TileEntitySolarPanel();}}
 public boolean onBlockActivated(World w,BlockPos p,IBlockState st,EntityPlayer player,EnumHand h,EnumFacing side,float x,float y,float z){if(type==Type.COAL&&!w.isRemote)player.openGui(NexusTech.INSTANCE,0,w,p.getX(),p.getY(),p.getZ());return true;}
}