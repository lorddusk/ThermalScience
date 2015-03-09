package com.creysys.ThermalScience.client;

import com.creysys.ThermalScience.ThermalScience;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Creysys on 09 Mar 15.
 */
public class ThermalScienceResourceLocation extends ResourceLocation {

    public String icon;

    public ThermalScienceResourceLocation(String path) {
        super(ThermalScience.modid, path);

        icon = getResourceDomain() + ":";

        String rp = getResourcePath();
        if(rp.startsWith("textures/blocks/")){
            icon += rp.substring(16, rp.length() - 4);
        }
        else if(rp.startsWith("textures/items/")){
            icon += rp.substring(15, rp.length() - 4);
        }
        else {
            icon = null;
        }
    }
}
