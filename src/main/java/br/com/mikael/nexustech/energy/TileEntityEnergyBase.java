package br.com.mikael.nexustech.energy;
import net.minecraft.nbt.NBTTagCompound;import net.minecraft.tileentity.TileEntity;import net.minecraft.util.EnumFacing;import net.minecraft.util.ITickable;import net.minecraftforge.energy.*;
public abstract class TileEntityEnergyBase extends TileEntity implements ITickable {
 protected final EnergyStorage energy=new EnergyStorage(50000,0,100);
 protected abstract int generation();
 public void update(){if(world==null||world.isRemote)return;int g=generation();if(g>0)energy.receiveEnergy(g,false);for(EnumFacing s:EnumFacing.values()){if(energy.getEnergyStored()==0)break;TileEntity n=world.getTileEntity(pos.offset(s));if(n==null)continue;IEnergyStorage t=n.getCapability(CapabilityEnergy.ENERGY,s.getOpposite());if(t!=null&&t.canReceive()){int a=t.receiveEnergy(Math.min(100,energy.getEnergyStored()),false);if(a>0)energy.extractEnergy(a,false);}}if(world.getTotalWorldTime()%20==0)markDirty();}
 public int getEnergyStored(){return energy.getEnergyStored();}public int getMaxEnergyStored(){return energy.getMaxEnergyStored();}
 public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> c,EnumFacing f){return c==CapabilityEnergy.ENERGY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(net.minecraftforge.common.capabilities.Capability<T> c,EnumFacing f){return c==CapabilityEnergy.ENERGY?(T)energy:super.getCapability(c,f);}
 public NBTTagCompound writeToNBT(NBTTagCompound n){n.setTag("Energy",energy.serializeNBT());return super.writeToNBT(n);}public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);if(n.hasKey("Energy"))energy.deserializeNBT(n.getCompoundTag("Energy"));}
}