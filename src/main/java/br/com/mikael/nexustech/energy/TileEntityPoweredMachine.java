package br.com.mikael.nexustech.energy;
import net.minecraft.nbt.NBTTagCompound;import net.minecraft.tileentity.TileEntity;import net.minecraft.util.EnumFacing;import net.minecraftforge.energy.CapabilityEnergy;
/** Base class for future electric machines: accepts Forge Energy and exposes a protected consume method. */
public abstract class TileEntityPoweredMachine extends TileEntity {
 protected final ModEnergyStorage energy=new ModEnergyStorage(20000,100,0);
 protected boolean consumeEnergy(int amount){if(amount<0)return false;if(energy.getEnergyStored()<amount)return false;return energy.extractEnergy(amount,false)==amount;}
 public int getEnergyStored(){return energy.getEnergyStored();}public int getMaxEnergyStored(){return energy.getMaxEnergyStored();}
 public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> c,EnumFacing f){return c==CapabilityEnergy.ENERGY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(net.minecraftforge.common.capabilities.Capability<T> c,EnumFacing f){return c==CapabilityEnergy.ENERGY?(T)energy:super.getCapability(c,f);}
 public NBTTagCompound writeToNBT(NBTTagCompound n){energy.writeEnergy(n);return super.writeToNBT(n);}public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);energy.readEnergy(n);}
}