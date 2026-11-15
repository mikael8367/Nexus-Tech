package br.com.mikael.nexustech.gui;

import br.com.mikael.nexustech.energy.TileEntityMachine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMachine extends GuiContainer {
    private final ContainerMachine container;

    public GuiMachine(InventoryPlayer inv, TileEntityMachine tile) {
        this(new ContainerMachine(inv, tile));
    }

    private GuiMachine(ContainerMachine c) {
        super(c);
        container = c;
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        TileEntityMachine tile = container.getTile();
        fontRenderer.drawString(I18n.format(tile.getBlockType().getUnlocalizedName() + ".name"), 8, 6, 0x404040);
        fontRenderer.drawString(I18n.format("container.inventory"), 8, 72, 0x404040);
        fontRenderer.drawString(container.getEnergy() + " / " + tile.getMaxEnergyStored() + " FE", 8, 58, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p, int mx, int my) {
        drawDefaultBackground();
        TileEntityMachine tile = container.getTile();
        int l = (width - xSize) / 2, t = (height - ySize) / 2;
        Gui.drawRect(l, t, l + 176, t + 166, 0xFFC6C6C6);
        Gui.drawRect(l + 4, t + 4, l + 172, t + 162, 0xFFE8E8E8);
        // Energy bar.
        Gui.drawRect(l + 7, t + 69, l + 169, t + 72, 0xFF555555);
        int e = (int) (158L * container.getEnergy() / Math.max(1, tile.getMaxEnergyStored()));
        Gui.drawRect(l + 9, t + 70, l + 9 + e, t + 71, 0xFF55AA55);
        // Progress arrow between the slots.
        int total = Math.max(1, tile.getProcessTime());
        int w = (int) (24L * container.getProgress() / total);
        Gui.drawRect(l + 80, t + 39, l + 104, t + 45, 0xFF373737);
        Gui.drawRect(l + 80, t + 39, l + 80 + w, t + 45, 0xFFFF8800);
        drawSlot(l + 55, t + 34);
        drawSlot(l + 115, t + 34);
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 9; c++)
                drawSlot(l + 7 + c * 18, t + 83 + r * 18);
        for (int c = 0; c < 9; c++)
            drawSlot(l + 7 + c * 18, t + 141);
    }

    private void drawSlot(int x, int y) {
        Gui.drawRect(x, y, x + 18, y + 18, 0xFF777777);
        Gui.drawRect(x + 1, y + 1, x + 17, y + 17, 0xFF373737);
    }
}
