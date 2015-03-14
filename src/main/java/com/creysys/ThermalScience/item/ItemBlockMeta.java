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
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }
}
