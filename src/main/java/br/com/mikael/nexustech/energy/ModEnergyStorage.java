package br.com.mikael.nexustech.energy;
import net.minecraft.nbt.NBTTagCompound;import net.minecraftforge.energy.EnergyStorage;
/** EnergyStorage with explicit NBT persistence and safe internal consumption for 1.12.2. */
public class ModEnergyStorage extends EnergyStorage {
 public ModEnergyStorage(int capacity,int receive,int extract){super(capacity,receive,extract);}
 public void writeEnergy(NBTTagCompound tag){tag.setInteger("Energy",getEnergyStored());}
 public void readEnergy(NBTTagCompound tag){energy=Math.max(0,Math.min(getMaxEnergyStored(),tag.getInteger("Energy")));}
 /** Internal production (generators). Ignores the external receive limit so generators can refuse outside input. */
 public int generateEnergy(int amount){int a=Math.max(0,Math.min(getMaxEnergyStored()-energy,amount));energy+=a;return a;}
 public boolean consumeEnergy(int amount){if(amount<0||energy<amount)return false;energy-=amount;return true;}
}
