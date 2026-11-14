package br.com.mikael.nexustech.block;

import br.com.mikael.nexustech.energy.TileEntityEnergyCable;
import br.com.mikael.nexustech.registry.ModCreativeTabs;
import br.com.mikael.nexustech.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnergyCable extends Block {
    public BlockEnergyCable() {
        super(Material.IRON);
        setRegistryName(Reference.MOD_ID, "energy_cable");
        setHardness(1.0F);
        setCreativeTab(ModCreativeTabs.NEXUS_TECH);
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Reference.MOD_ID + ".energy_cable";
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityEnergyCable();
    }
}
