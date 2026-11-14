package br.com.mikael.nexustech.energy;
import java.util.EnumSet;import net.minecraft.nbt.NBTTagCompound;import net.minecraft.tileentity.TileEntity;import net.minecraft.util.EnumFacing;import net.minecraft.util.ITickable;import net.minecraftforge.energy.*;
public class TileEntityEnergyCable extends TileEntity implements ITickable {
 private final ModEnergyStorage energy=new ModEnergyStorage(2000,100,100);
 public void update(){if(world==null||world.isRemote)return;EnumSet<EnumFacing> sources=EnumSet.noneOf(EnumFacing.class);
 for(EnumFacing s:EnumFacing.values()){if(energy.getEnergyStored()>=energy.getMaxEnergyStored())break;TileEntity n=world.getTileEntity(pos.offset(s));if(n==null||n instanceof TileEntityEnergyCable||n instanceof TileEntityEnergyBase)continue;IEnergyStorage src=n.getCapability(CapabilityEnergy.ENERGY,s.getOpposite());if(src!=null&&src.canExtract()){int a=src.extractEnergy(Math.min(100,energy.getMaxEnergyStored()-energy.getEnergyStored()),false);if(a>0){energy.receiveEnergy(a,false);sources.add(s);}}}
 for(EnumFacing s:EnumFacing.values()){if(energy.getEnergyStored()==0)break;if(sources.contains(s))continue;TileEntity n=world.getTileEntity(pos.offset(s));if(n==null||n instanceof TileEntityEnergyBase)continue;IEnergyStorage dst=n.getCapability(CapabilityEnergy.ENERGY,s.getOpposite());if(dst!=null&&dst.canReceive()){int a=dst.receiveEnergy(Math.min(100,energy.getEnergyStored()),false);energy.extractEnergy(a,false);}}
 if(world.getTotalWorldTime()%20==0)markDirty();}
 public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> c,EnumFacing f){return c==CapabilityEnergy.ENERGY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(net.minecraftforge.common.capabilities.Capability<T> c,EnumFacing f){return c==CapabilityEnergy.ENERGY?(T)energy:super.getCapability(c,f);}
 public NBTTagCompound writeToNBT(NBTTagCompound n){energy.writeEnergy(n);return super.writeToNBT(n);}public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);energy.readEnergy(n);}
}