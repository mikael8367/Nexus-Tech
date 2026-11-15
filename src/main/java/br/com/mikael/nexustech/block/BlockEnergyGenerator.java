package br.com.mikael.nexustech.block;

import br.com.mikael.nexustech.NexusTech;
import br.com.mikael.nexustech.energy.TileEntityCoalGenerator;
import br.com.mikael.nexustech.energy.TileEntityEnergyBase;
import br.com.mikael.nexustech.energy.TileEntitySolarPanel;
import br.com.mikael.nexustech.energy.TileEntityWindTurbine;
import br.com.mikael.nexustech.registry.ModCreativeTabs;
import br.com.mikael.nexustech.util.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class BlockEnergyGenerator extends BlockContainer {
    public enum Type { SOLAR, COAL, WIND }

    private static final AxisAlignedBB SOLAR_BOUNDS = new AxisAlignedBB(0, 0, 0, 1, 4 / 16D, 1);
    private static final AxisAlignedBB WIND_BOUNDS = new AxisAlignedBB(1 / 16D, 0, 1 / 16D, 15 / 16D, 1, 15 / 16D);

    private final Type type;
    private final String id;

    public BlockEnergyGenerator(String id, Type type) {
        super(Material.IRON);
        this.id = id;
        this.type = type;
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
            ItemStackHandler inv = ((TileEntityCoalGenerator) te).getInventory();
            for (int i = 0; i < inv.getSlots(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    InventoryHelper.spawnItemStack(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
                }
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
            EntityPlayer player, EnumHand hand, EnumFacing side,
            float hitX, float hitY, float hitZ) {
        if (type == Type.COAL && !world.isRemote) {
            player.openGui(NexusTech.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
        } else if (type != Type.COAL && !world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityEnergyBase) {
                TileEntityEnergyBase g = (TileEntityEnergyBase) te;
                player.sendStatusMessage(new TextComponentTranslation("message.nexus_tech.generator",
                    g.getEnergyStored(), g.getMaxEnergyStored(), g.getLastGeneration()), true);
            }
        }
        return true;
    }

    /** BlockContainer defaults to INVISIBLE in 1.12.2, which made the block disappear. */
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return type == Type.COAL;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return type == Type.COAL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (type) {
            case SOLAR:
                return SOLAR_BOUNDS;
            case WIND:
                return WIND_BOUNDS;
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return type == Type.COAL ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }
}
