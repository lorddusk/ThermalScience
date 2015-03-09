package com.creysys.ThermalScience.item;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.client.ThermalScienceResourceLocation;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

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

    public ItemThermalScience(String name, ThermalScienceResourceLocation texture){
        super();

        this.name = name;

        name = "item" + name;

        setUnlocalizedName(name);
        setTextureName(texture.icon);

        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerItem(this, name);
    }
}
