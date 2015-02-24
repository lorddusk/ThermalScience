package com.creysys.ThermalScience;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by Creysys on 1/31/2015.
 */
public class ThermalScienceCreativeTab extends CreativeTabs {
    public ThermalScienceCreativeTab() {
        super(ThermalScience.MODID);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ThermalScience.blockCarbothermicFurnace);
    }


}
