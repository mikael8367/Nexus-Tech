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

/** ItemBlock with a localized Vanilla-style description. */
public class ModItemBlock extends ItemBlock {
    private final String descriptionKey;

    public ModItemBlock(Block block) {
        super(block);
        String unlocalizedName = block.getUnlocalizedName();
        this.descriptionKey = unlocalizedName.startsWith("tile.")
            ? "tooltip." + unlocalizedName.substring("tile.".length())
            : "tooltip." + unlocalizedName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        String description = I18n.translateToLocal(descriptionKey);
        if (!description.equals(descriptionKey)) {
            tooltip.add(description);
        }
    }
}