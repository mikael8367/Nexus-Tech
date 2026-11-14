package br.com.mikael.nexustech.block;

import br.com.mikael.nexustech.energy.TileEntityEnergyCell;
import br.com.mikael.nexustech.registry.ModCreativeTabs;
import br.com.mikael.nexustech.util.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** Tiered FE storage block. Each placed tier creates storage with its own capacity and transfer limit. */
public class BlockEnergyCell extends BlockContainer {
    private final int capacity;
    private final int transferRate;
    private final String id;

    public BlockEnergyCell(String id, int capacity, int transferRate) {
        super(Material.IRON);
        this.id = id;
        this.capacity = Math.max(1, capacity);
        this.transferRate = Math.max(1, transferRate);
        setRegistryName(Reference.MOD_ID, id);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 0);
        setCreativeTab(ModCreativeTabs.NEXUS_TECH);
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Reference.MOD_ID + "." + id;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityEnergyCell(capacity, transferRate);
    }

    /** BlockContainer defaults to INVISIBLE in 1.12.2, which made the block disappear. */
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
