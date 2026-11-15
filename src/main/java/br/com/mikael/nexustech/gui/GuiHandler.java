package br.com.mikael.nexustech.gui;

import br.com.mikael.nexustech.energy.TileEntityCoalGenerator;
import br.com.mikael.nexustech.energy.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/** GUI ids: 0 = coal generator, 1 = electric furnace, 2 = industrial smelter. */
public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer p, World w, int x, int y, int z) {
        TileEntity te = w.getTileEntity(new BlockPos(x, y, z));
        if (id == 0 && te instanceof TileEntityCoalGenerator) {
            return new ContainerCoalGenerator(p.inventory, (TileEntityCoalGenerator) te);
        }
        if ((id == 1 || id == 2) && te instanceof TileEntityMachine) {
            return new ContainerMachine(p.inventory, (TileEntityMachine) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer p, World w, int x, int y, int z) {
        TileEntity te = w.getTileEntity(new BlockPos(x, y, z));
        if (id == 0 && te instanceof TileEntityCoalGenerator) {
            return new GuiCoalGenerator(p.inventory, (TileEntityCoalGenerator) te);
        }
        if ((id == 1 || id == 2) && te instanceof TileEntityMachine) {
            return new GuiMachine(p.inventory, (TileEntityMachine) te);
        }
        return null;
    }
}
