package br.com.mikael.nexustech.energy;

import br.com.mikael.nexustech.block.BlockMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/** Base for machines that turn one input stack into one output stack using Forge Energy. */
public abstract class TileEntityMachine extends TileEntity implements ITickable {
    public static final int INPUT = 0;
    public static final int OUTPUT = 1;

    protected final ModEnergyStorage energy = new ModEnergyStorage(20000, 200, 0);
    private int progress;

    private final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }

        /** Only valid inputs may enter the input slot; the output slot can never be filled from outside. */
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot != INPUT || stack.isEmpty() || getResult(stack).isEmpty()) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };

    /** Result of processing one item of the given input, or EMPTY when it cannot be processed. */
    public abstract ItemStack getResult(ItemStack input);

    public abstract int getProcessTime();

    public abstract int getEnergyPerTick();

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getProgress() {
        return progress;
    }

    public int getEnergyStored() {
        return energy.getEnergyStored();
    }

    public int getMaxEnergyStored() {
        return energy.getMaxEnergyStored();
    }

    private boolean canProcess() {
        ItemStack in = inventory.getStackInSlot(INPUT);
        if (in.isEmpty()) {
            return false;
        }
        ItemStack result = getResult(in);
        if (result.isEmpty()) {
            return false;
        }
        ItemStack out = inventory.getStackInSlot(OUTPUT);
        if (out.isEmpty()) {
            return true;
        }
        if (!out.isItemEqual(result) || !ItemStack.areItemStackTagsEqual(out, result)) {
            return false;
        }
        return out.getCount() + result.getCount() <= out.getMaxStackSize();
    }

    private void finishProcess() {
        ItemStack in = inventory.getStackInSlot(INPUT);
        ItemStack result = getResult(in).copy();
        ItemStack out = inventory.getStackInSlot(OUTPUT);
        if (out.isEmpty()) {
            inventory.setStackInSlot(OUTPUT, result);
        } else {
            out.grow(result.getCount());
            inventory.setStackInSlot(OUTPUT, out);
        }
        in.shrink(1);
        inventory.setStackInSlot(INPUT, in.isEmpty() ? ItemStack.EMPTY : in);
    }

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }
        boolean working = false;
        if (canProcess()) {
            int cost = getEnergyPerTick();
            if (energy.getEnergyStored() >= cost) {
                energy.consumeEnergy(cost);
                progress++;
                working = true;
                if (progress >= getProcessTime()) {
                    finishProcess();
                    progress = 0;
                }
            }
        } else if (progress > 0) {
            progress = 0;
        }
        setLit(working);
        if (world.getTotalWorldTime() % 20 == 0) {
            markDirty();
        }
    }

    private void setLit(boolean lit) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockMachine && state.getValue(BlockMachine.LIT) != lit) {
            world.setBlockState(pos, state.withProperty(BlockMachine.LIT, lit), 3);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY
            || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
            || super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) energy;
        }
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        energy.writeEnergy(tag);
        tag.setTag("Inventory", inventory.serializeNBT());
        tag.setInteger("Progress", progress);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        energy.readEnergy(tag);
        if (tag.hasKey("Inventory")) {
            inventory.deserializeNBT(tag.getCompoundTag("Inventory"));
        }
        progress = tag.getInteger("Progress");
    }
}
