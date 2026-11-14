package br.com.mikael.nexustech.energy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
/** FE storage used by every battery tier. Tier limits are persisted so chunk reloads retain the correct capacity. */
public class TileEntityEnergyCell extends TileEntity implements ITickable {
 @Override public void update(){
  if(world==null||world.isRemote)return;
  for(EnumFacing side:EnumFacing.values()){
   if(energy.getEnergyStored()<=0)break;
   TileEntity n=world.getTileEntity(pos.offset(side));
   // Cables pull from the cell themselves; skip other cells to avoid pointless ping-pong.
   if(n==null||n instanceof TileEntityEnergyCable||n instanceof TileEntityEnergyCell)continue;
   IEnergyStorage t=n.getCapability(CapabilityEnergy.ENERGY,side.getOpposite());
   if(t!=null&&t.canReceive()){int a=t.receiveEnergy(Math.min(transferRate,energy.getEnergyStored()),false);if(a>0)energy.extractEnergy(a,false);}
  }
  if(world.getTotalWorldTime()%20==0)markDirty();
 }
 private int capacity=100000;
 private int transferRate=250;
 private ModEnergyStorage energy=new ModEnergyStorage(capacity,transferRate,transferRate);
 public TileEntityEnergyCell(){}
 public TileEntityEnergyCell(int capacity,int transferRate){this.capacity=Math.max(1,capacity);this.transferRate=Math.max(1,transferRate);energy=new ModEnergyStorage(this.capacity,this.transferRate,this.transferRate);}
 public int getEnergyStored(){return energy.getEnergyStored();}
 public int getMaxEnergyStored(){return energy.getMaxEnergyStored();}
 public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> c,EnumFacing f){return c==CapabilityEnergy.ENERGY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(net.minecraftforge.common.capabilities.Capability<T> c,EnumFacing f){return c==CapabilityEnergy.ENERGY?(T)energy:super.getCapability(c,f);}
 public NBTTagCompound writeToNBT(NBTTagCompound n){super.writeToNBT(n);n.setInteger("BatteryCapacity",capacity);n.setInteger("BatteryTransferRate",transferRate);energy.writeEnergy(n);return n;}
 public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);capacity=Math.max(1,n.hasKey("BatteryCapacity")?n.getInteger("BatteryCapacity"):100000);transferRate=Math.max(1,n.hasKey("BatteryTransferRate")?n.getInteger("BatteryTransferRate"):250);energy=new ModEnergyStorage(capacity,transferRate,transferRate);energy.readEnergy(n);}
}
