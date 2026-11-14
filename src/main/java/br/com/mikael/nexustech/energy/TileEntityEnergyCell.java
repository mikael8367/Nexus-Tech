package br.com.mikael.nexustech.energy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
/** FE storage used by every battery tier. Capacity and transfer limits are set by the placed block. */
public class TileEntityEnergyCell extends TileEntity {
 private final ModEnergyStorage energy;
 public TileEntityEnergyCell(){this(100000,250);}
 public TileEntityEnergyCell(int capacity,int transferRate){energy=new ModEnergyStorage(capacity,transferRate,transferRate);}
 public int getEnergyStored(){return energy.getEnergyStored();}
 public int getMaxEnergyStored(){return energy.getMaxEnergyStored();}
 public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> c,EnumFacing f){return c==CapabilityEnergy.ENERGY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(net.minecraftforge.common.capabilities.Capability<T> c,EnumFacing f){return c==CapabilityEnergy.ENERGY?(T)energy:super.getCapability(c,f);}
 public NBTTagCompound writeToNBT(NBTTagCompound n){energy.writeEnergy(n);return super.writeToNBT(n);}
 public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);energy.readEnergy(n);}
}