package com.creysys.ThermalScience.client.gui;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Creysys on 10 Mar 15.
 */
public interface IItemTooltipProvider {
    void addTooltip(List<String> list, ItemStack stack);
}
