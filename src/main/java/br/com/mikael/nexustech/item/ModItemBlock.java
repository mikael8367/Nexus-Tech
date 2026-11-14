package br.com.mikael.nexustech.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** ItemBlock that displays its localized description in the vanilla-style tooltip. */
public class ModItemBlock extends ItemBlock {
    public ModItemBlock(Block block) { super(block); }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        String key = "tooltip." + getRegistryName().getResourceDomain() + "." + getRegistryName().getResourcePath();
        String description = I18n.translateToLocal(key);
        if (!description.equals(key)) tooltip.add(description);
    }
}