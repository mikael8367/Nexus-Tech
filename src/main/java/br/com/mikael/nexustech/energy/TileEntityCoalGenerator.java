package br.com.mikael.nexustech.energy;
import net.minecraft.item.ItemStack;import net.minecraft.nbt.NBTTagCompound;import net.minecraft.util.EnumFacing;import net.minecraftforge.common.capabilities.Capability;import net.minecraftforge.items.*;
public class TileEntityCoalGenerator extends TileEntityEnergyBase {
 private final ItemStackHandler inventory=new ItemStackHandler(1){protected void onContentsChanged(int s){markDirty();}public boolean isItemValid(int s,ItemStack st){return fuelTime(st)>0;}@Override public ItemStack insertItem(int s,ItemStack st,boolean sim){return fuelTime(st)>0?super.insertItem(s,st,sim):st;}};
 private int burnTime,totalBurnTime;
 private static int fuelTime(ItemStack s){if(s.isEmpty()||s.getItem().getRegistryName()==null)return 0;String id=s.getItem().getRegistryName().toString();if(id.equals("minecraft:coal_block"))return 16000;if(id.equals("minecraft:coal")||id.equals("minecraft:charcoal"))return 1600;return 0;}
 protected int generation(){if(energy.getEnergyStored()>=energy.getMaxEnergyStored())return 0;if(burnTime<=0){ItemStack s=inventory.getStackInSlot(0);int d=fuelTime(s);if(d>0){burnTime=totalBurnTime=d;s.shrink(1);inventory.setStackInSlot(0,s);}}if(burnTime>0){burnTime--;return 20;}return 0;}
 public ItemStackHandler getInventory(){return inventory;}public int getBurnTime(){return burnTime;}public int getTotalBurnTime(){return totalBurnTime;}
 public boolean hasCapability(Capability<?> c,EnumFacing f){return c==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(Capability<T> c,EnumFacing f){return c==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY?(T)inventory:super.getCapability(c,f);}
 public NBTTagCompound writeToNBT(NBTTagCompound n){super.writeToNBT(n);n.setTag("Inventory",inventory.serializeNBT());n.setInteger("BurnTime",burnTime);n.setInteger("TotalBurnTime",totalBurnTime);return n;}
 public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);if(n.hasKey("Inventory"))inventory.deserializeNBT(n.getCompoundTag("Inventory"));burnTime=n.getInteger("BurnTime");totalBurnTime=n.getInteger("TotalBurnTime");}
}
