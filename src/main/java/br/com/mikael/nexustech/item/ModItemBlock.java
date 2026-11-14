package br.com.mikael.nexustech.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** ItemBlock with a localized Vanilla-style description, derived from its registry ID. */
public class ModItemBlock extends ItemBlock {
    private final String descriptionKey;

    public ModItemBlock(Block block) {
        super(block);
        ResourceLocation registryName = block.getRegistryName();
        this.descriptionKey = registryName == null ? "" :
            "tooltip." + registryName.getResourceDomain() + "." + registryName.getResourcePath();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        if (!descriptionKey.isEmpty()) {
            String description = I18n.translateToLocal(descriptionKey);
            if (!description.equals(descriptionKey)) tooltip.add(description);
        }
    }
}