package br.com.mikael.nexustech.energy;
import net.minecraft.nbt.NBTTagCompound;import net.minecraft.tileentity.TileEntity;import net.minecraft.util.EnumFacing;import net.minecraft.util.ITickable;import net.minecraftforge.energy.*;
public class TileEntityEnergyCell extends TileEntity implements ITickable {
 private final EnergyStorage energy=new EnergyStorage(100000,250,250);
 public void update(){if(world!=null&&!world.isRemote&&world.getTotalWorldTime()%20==0)markDirty();}
 public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> c,EnumFacing f){return c==CapabilityEnergy.ENERGY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(net.minecraftforge.common.capabilities.Capability<T> c,EnumFacing f){return c==CapabilityEnergy.ENERGY?(T)energy:super.getCapability(c,f);}
 public NBTTagCompound writeToNBT(NBTTagCompound n){n.setTag("Energy",energy.serializeNBT());return super.writeToNBT(n);}public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);if(n.hasKey("Energy"))energy.deserializeNBT(n.getCompoundTag("Energy"));}
}