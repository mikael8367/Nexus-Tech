package br.com.mikael.nexustech.energy;
import java.util.ArrayDeque;import java.util.EnumSet;import java.util.HashSet;import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;import net.minecraft.tileentity.TileEntity;import net.minecraft.util.EnumFacing;import net.minecraft.util.ITickable;import net.minecraft.util.math.BlockPos;import net.minecraftforge.energy.*;
public class TileEntityEnergyCable extends TileEntity implements ITickable {
 private final ModEnergyStorage energy=new ModEnergyStorage(2000,100,100);
 public void update(){if(world==null||world.isRemote)return;Set<BlockPos> sources=new HashSet<BlockPos>();
 // A cable can draw from an adjacent storage block, but never from another cable or generator.
 for(EnumFacing side:EnumFacing.values()){if(energy.getEnergyStored()>=energy.getMaxEnergyStored())break;BlockPos sourcePos=pos.offset(side);TileEntity sourceTile=world.getTileEntity(sourcePos);if(sourceTile==null||sourceTile instanceof TileEntityEnergyCable||sourceTile instanceof TileEntityEnergyBase)continue;IEnergyStorage source=sourceTile.getCapability(CapabilityEnergy.ENERGY,side.getOpposite());if(source!=null&&source.canExtract()){int moved=source.extractEnergy(Math.min(100,energy.getMaxEnergyStored()-energy.getEnergyStored()),false);if(moved>0){energy.receiveEnergy(moved,false);sources.add(sourcePos);}}}
 // Traverse the connected cable network and deliver to compatible machines.
 if(energy.getEnergyStored()>0){
 ArrayDeque<BlockPos> queue=new ArrayDeque<BlockPos>();Set<BlockPos> visited=new HashSet<BlockPos>();Set<BlockPos> targets=new HashSet<BlockPos>();queue.add(pos);visited.add(pos);
 while(!queue.isEmpty()&&visited.size()<=64){BlockPos cablePos=queue.removeFirst();for(EnumFacing side:EnumFacing.values()){BlockPos next=cablePos.offset(side);if(visited.contains(next))continue;TileEntity neighbor=world.getTileEntity(next);if(neighbor instanceof TileEntityEnergyCable){visited.add(next);queue.addLast(next);}else if(neighbor!=null&&!(neighbor instanceof TileEntityEnergyBase)&&!sources.contains(next)){IEnergyStorage target=neighbor.getCapability(CapabilityEnergy.ENERGY,side.getOpposite());if(target!=null&&target.canReceive())targets.add(next);}}}
 for(BlockPos targetPos:targets){if(energy.getEnergyStored()<=0)break;TileEntity targetTile=world.getTileEntity(targetPos);if(targetTile==null)continue;EnumFacing side=directionToAdjacentCable(targetPos,visited);if(side==null)continue;IEnergyStorage target=targetTile.getCapability(CapabilityEnergy.ENERGY,side);if(target!=null&&target.canReceive()){int moved=target.receiveEnergy(Math.min(100,energy.getEnergyStored()),false);energy.extractEnergy(moved,false);}}
 }
 if(world.getTotalWorldTime()%20==0)markDirty();}
 private EnumFacing directionToAdjacentCable(BlockPos target,Set<BlockPos> cables){for(EnumFacing side:EnumFacing.values())if(cables.contains(target.offset(side)))return side;return null;}
 public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> c,EnumFacing f){return c==CapabilityEnergy.ENERGY||super.hasCapability(c,f);}
 @SuppressWarnings("unchecked") public <T>T getCapability(net.minecraftforge.common.capabilities.Capability<T> c,EnumFacing f){return c==CapabilityEnergy.ENERGY?(T)energy:super.getCapability(c,f);}
 public NBTTagCompound writeToNBT(NBTTagCompound n){energy.writeEnergy(n);return super.writeToNBT(n);}public void readFromNBT(NBTTagCompound n){super.readFromNBT(n);energy.readEnergy(n);}
}
