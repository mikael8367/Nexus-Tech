package br.com.mikael.nexustech.gui;

import br.com.mikael.nexustech.energy.TileEntityCoalGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCoalGenerator extends Container {
    private final TileEntityCoalGenerator tile;

    // Values mirrored to the client every tick (the tile itself only syncs once per second).
    private int energy;
    private int burnTime;
    private int totalBurnTime;
    private int lastEnergy = -1;
    private int lastBurnTime = -1;
    private int lastTotalBurnTime = -1;

    public ContainerCoalGenerator(InventoryPlayer inv, TileEntityCoalGenerator te) {
        tile = te;
        addSlotToContainer(new SlotItemHandler(te.getInventory(), 0, 80, 35));
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 9; c++)
                addSlotToContainer(new Slot(inv, c + r * 9 + 9, 8 + c * 18, 84 + r * 18));
        for (int c = 0; c < 9; c++)
            addSlotToContainer(new Slot(inv, c, 8 + c * 18, 142));
    }

    public int getEnergy() {
        return tile.getWorld() != null && tile.getWorld().isRemote ? energy : tile.getEnergyStored();
    }

    public int getMaxEnergy() {
        return tile.getMaxEnergyStored();
    }

    public int getBurnTime() {
        return tile.getWorld() != null && tile.getWorld().isRemote ? burnTime : tile.getBurnTime();
    }

    public int getTotalBurnTime() {
        return tile.getWorld() != null && tile.getWorld().isRemote ? totalBurnTime : tile.getTotalBurnTime();
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int e = tile.getEnergyStored();
        int b = tile.getBurnTime();
        int t = tile.getTotalBurnTime();
        for (IContainerListener listener : listeners) {
            // Window properties travel as 16-bit values, so the energy is split in two halves.
            if (e != lastEnergy) {
                listener.sendWindowProperty(this, 0, e & 0xFFFF);
                listener.sendWindowProperty(this, 1, (e >>> 16) & 0xFFFF);
            }
            if (b != lastBurnTime) listener.sendWindowProperty(this, 2, b);
            if (t != lastTotalBurnTime) listener.sendWindowProperty(this, 3, t);
        }
        lastEnergy = e;
        lastBurnTime = b;
        lastTotalBurnTime = t;
    }

    @Override
    public void updateProgressBar(int id, int data) {
        switch (id) {
            case 0:
                energy = (energy & 0xFFFF0000) | (data & 0xFFFF);
                break;
            case 1:
                energy = (energy & 0xFFFF) | ((data & 0xFFFF) << 16);
                break;
            case 2:
                burnTime = data;
                break;
            case 3:
                totalBurnTime = data;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p) {
        return !tile.isInvalid() && p.getDistanceSq(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5) <= 64.0;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                // Fuel slot -> player inventory.
                if (!mergeItemStack(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else {
                // Player inventory -> fuel slot first, then the rest.
                if (!mergeItemStack(itemstack1, 0, 1, false)) {
                    if (index < 28) {
                        if (!mergeItemStack(itemstack1, 28, 37, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!mergeItemStack(itemstack1, 1, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }
}
