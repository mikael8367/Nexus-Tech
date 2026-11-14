package br.com.mikael.nexustech.gui;

import br.com.mikael.nexustech.energy.TileEntityCoalGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCoalGenerator extends Container {
    private final TileEntityCoalGenerator tile;

    public ContainerCoalGenerator(InventoryPlayer inv, TileEntityCoalGenerator te) {
        tile = te;
        addSlotToContainer(new SlotItemHandler(te.getInventory(), 0, 80, 35));
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 9; c++)
                addSlotToContainer(new Slot(inv, c + r * 9 + 9, 8 + c * 18, 84 + r * 18));
        for (int c = 0; c < 9; c++)
            addSlotToContainer(new Slot(inv, c, 8 + c * 18, 142));
    }

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
