package br.com.mikael.nexustech.gui;

import br.com.mikael.nexustech.energy.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/** Slots: 0 = input, 1 = output, 2-28 = player inventory, 29-37 = hotbar. Shift-click moves items in and out. */
public class ContainerMachine extends Container {
    private static class SlotOutput extends SlotItemHandler {
        SlotOutput(IItemHandler handler, int index, int x, int y) {
            super(handler, index, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }

    private final TileEntityMachine tile;
    private int energy;
    private int progress;
    private int lastEnergy = -1;
    private int lastProgress = -1;

    public ContainerMachine(InventoryPlayer inv, TileEntityMachine te) {
        tile = te;
        addSlotToContainer(new SlotItemHandler(te.getInventory(), TileEntityMachine.INPUT, 56, 35));
        addSlotToContainer(new SlotOutput(te.getInventory(), TileEntityMachine.OUTPUT, 116, 35));
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 9; c++)
                addSlotToContainer(new Slot(inv, c + r * 9 + 9, 8 + c * 18, 84 + r * 18));
        for (int c = 0; c < 9; c++)
            addSlotToContainer(new Slot(inv, c, 8 + c * 18, 142));
    }

    public TileEntityMachine getTile() {
        return tile;
    }

    private boolean isClient() {
        return tile.getWorld() != null && tile.getWorld().isRemote;
    }

    public int getEnergy() {
        return isClient() ? energy : tile.getEnergyStored();
    }

    public int getProgress() {
        return isClient() ? progress : tile.getProgress();
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int e = tile.getEnergyStored();
        int p = tile.getProgress();
        for (IContainerListener listener : listeners) {
            if (e != lastEnergy) listener.sendWindowProperty(this, 0, e);
            if (p != lastProgress) listener.sendWindowProperty(this, 1, p);
        }
        lastEnergy = e;
        lastProgress = p;
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0) energy = data;
        else if (id == 1) progress = data;
    }

    @Override
    public boolean canInteractWith(EntityPlayer p) {
        return !tile.isInvalid() && p.getDistanceSq(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5) <= 64.0;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (index < 2) {
                // Machine slot -> player inventory.
                if (!mergeItemStack(stack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, copy);
            } else if (!mergeItemStack(stack, 0, 1, false)) {
                // Player inventory -> input slot, otherwise swap between inventory and hotbar.
                if (index < 29) {
                    if (!mergeItemStack(stack, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!mergeItemStack(stack, 2, 29, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (stack.getCount() == copy.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stack);
        }
        return copy;
    }
}
