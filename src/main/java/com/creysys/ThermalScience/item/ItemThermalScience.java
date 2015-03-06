package com.creysys.ThermalScience.item;

import com.creysys.ThermalScience.ThermalScience;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class ItemThermalScience extends Item {

    public String name;

    public ItemThermalScience(String name){
        super();

        this.name = name;

        name = "item" + name;

        setUnlocalizedName(name);
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerItem(this, name);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(ThermalScience.modid + ":" + name);
    }
}
