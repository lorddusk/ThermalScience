package com.creysys.ThermalScience.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Created by Creysys on 10 Mar 15.
 */
public class ItemBlockMeta extends ItemBlock {

    public ItemBlockMeta(Block block)
    {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return field_150939_a instanceof ISpecialItemRenderer ? ((ISpecialItemRenderer)field_150939_a).getIcon(stack) : super.getIcon(stack, pass);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return field_150939_a instanceof ISpecialItemRenderer ? true : super.requiresMultipleRenderPasses();
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return field_150939_a instanceof ISpecialItemRenderer ? 1 : super.getRenderPasses(metadata);
    }
}
