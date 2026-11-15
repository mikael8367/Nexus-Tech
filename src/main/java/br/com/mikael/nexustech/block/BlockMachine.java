package br.com.mikael.nexustech.block;

import br.com.mikael.nexustech.NexusTech;
import br.com.mikael.nexustech.energy.TileEntityElectricFurnace;
import br.com.mikael.nexustech.energy.TileEntityIndustrialSmelter;
import br.com.mikael.nexustech.energy.TileEntityMachine;
import br.com.mikael.nexustech.registry.ModCreativeTabs;
import br.com.mikael.nexustech.util.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

/** Energy-powered processing machine with a facing and a "lit" state (vanilla furnace look). */
public class BlockMachine extends BlockContainer {
    public enum Kind { FURNACE, SMELTER }

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool LIT = PropertyBool.create("lit");

    private final Kind kind;
    private final String id;
    private final int guiId;

    public BlockMachine(String id, Kind kind, int guiId) {
        super(Material.IRON);
        this.id = id;
        this.kind = kind;
        this.guiId = guiId;
        setRegistryName(Reference.MOD_ID, id);
        setHardness(3.5F);
        setHarvestLevel("pickaxe", 0);
        setCreativeTab(ModCreativeTabs.NEXUS_TECH);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, Boolean.FALSE));
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Reference.MOD_ID + "." + id;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return kind == Kind.FURNACE ? new TileEntityElectricFurnace() : new TileEntityIndustrialSmelter();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, LIT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
            .withProperty(FACING, EnumFacing.getHorizontal(meta & 3))
            .withProperty(LIT, (meta & 4) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(LIT) ? 4 : 0);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState()
            .withProperty(FACING, placer.getHorizontalFacing().getOpposite())
            .withProperty(LIT, Boolean.FALSE);
    }

    @Override
    public int getLightValue(IBlockState state) {
        return state.getValue(LIT) ? 13 : 0;
    }

    /** BlockContainer defaults to INVISIBLE in 1.12.2. */
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(NexusTech.INSTANCE, guiId, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMachine) {
            ItemStackHandler inv = ((TileEntityMachine) te).getInventory();
            for (int i = 0; i < inv.getSlots(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    InventoryHelper.spawnItemStack(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
                }
            }
        }
        super.breakBlock(world, pos, state);
    }
}
