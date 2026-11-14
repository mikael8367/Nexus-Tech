package br.com.mikael.nexustech.block;

import br.com.mikael.nexustech.NexusTech;
import br.com.mikael.nexustech.energy.TileEntityCoalGenerator;
import br.com.mikael.nexustech.energy.TileEntitySolarPanel;
import br.com.mikael.nexustech.energy.TileEntityWindTurbine;
import br.com.mikael.nexustech.registry.ModCreativeTabs;
import br.com.mikael.nexustech.util.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnergyGenerator extends BlockContainer {
    public enum Type { SOLAR, COAL, WIND }

    private final Type type;
    private final String id;

    public BlockEnergyGenerator(String id, Type type) {
        super(Material.IRON);
        this.id = id;
        this.type = type;
        setRegistryName(Reference.MOD_ID, id);
        setHardness(3.0F);
        setCreativeTab(ModCreativeTabs.NEXUS_TECH);
    }

    public String getUnlocalizedName() {
        return "tile." + Reference.MOD_ID + "." + id;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch (type) {
            case COAL:
                return new TileEntityCoalGenerator();
            case WIND:
                return new TileEntityWindTurbine();
            default:
                return new TileEntitySolarPanel();
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        // Drop stored fuel so it is not lost when the generator is broken.
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityCoalGenerator) {
            InventoryHelper.dropInventoryItems(world, pos, ((TileEntityCoalGenerator) te).getInventory());
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
            EntityPlayer player, EnumHand hand, EnumFacing side,
            float hitX, float hitY, float hitZ) {
        if (type == Type.COAL && !world.isRemote) {
            player.openGui(NexusTech.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
