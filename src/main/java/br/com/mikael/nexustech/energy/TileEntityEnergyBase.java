package br.com.mikael.nexustech.energy;
import net.minecraft.nbt.NBTTagCompound;import net.minecraft.network.NetworkManager;import net.minecraft.network.play.server.SPacketUpdateTileEntity;import net.minecraft.tileentity.TileEntity;import net.minecraft.util.EnumFacing;import net.minecraft.util.ITickable;import net.minecraft.block.state.IBlockState;import net.minecraftforge.energy.*;
public abstract class TileEntityEnergyBase extends TileEntity implements ITickable {
 protected final ModEnergyStorage energy=new ModEnergyStorage(50000,0,100);
 protected abstract int generation();
 protected int lastGeneration;public int getLastGeneration(){return lastGeneration;}
 public void update(){if(world==null||world.isRemote)return;int g=generation();lastGeneration=g;if(g>0)energy.generateEnergy(g);for(EnumFacing s:EnumFacing.values()){if(energy.getEnergyStored()==0)break;TileEntity n=world.getTileEntity(pos.offset(s));if(n==null||n instanceof TileEntityEnergyBase)continue;IEnergyStorage t=n.getCapability(CapabilityEnergy.ENERGY,s.getOpposite());if(t!=null&&t.canReceive()){int a=t.receiveEnergy(Math.min(100,energy.getEnergyStored()),false);if(a>0)energy.extractEnergy(a,false);}}if(world.getTotalWorldTime()%20==0)markDirty();}
 public int getEnergyStored(){return energy.getEnergyStored();}public int getMaxEnergyStored(){return energy.getMaxEnergyStored();}
 public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> c,EnumFacing f){return c==CapabilityEnergy.ENERGY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(net.minecraftforge.common.capabilities.Capability<T> c,EnumFacing f){return c==CapabilityEnergy.ENERGY?(T)energy:super.getCapability(c,f);}
 @Override public void markDirty(){super.markDirty();if(world!=null&&!world.isRemote){IBlockState state=world.getBlockState(pos);world.notifyBlockUpdate(pos,state,state,3);}}
 @Override public NBTTagCompound getUpdateTag(){return writeToNBT(new NBTTagCompound());}
 @Override public SPacketUpdateTileEntity getUpdatePacket(){return new SPacketUpdateTileEntity(pos,0,getUpdateTag());}
 @Override public void onDataPacket(NetworkManager net,SPacketUpdateTileEntity packet){readFromNBT(packet.getNbtCompound());}
 public NBTTagCompound writeToNBT(NBTTagCompound n){energy.writeEnergy(n);return super.writeToNBT(n);}public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);energy.readEnergy(n);}
}
