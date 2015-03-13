package com.creysys.ThermalScience.util;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Creysys on 13 Mar 15.
 */
public interface IWrenchable {
    public ItemStack onWrenched(ItemStack stack, TileEntity tileEntity);
}
