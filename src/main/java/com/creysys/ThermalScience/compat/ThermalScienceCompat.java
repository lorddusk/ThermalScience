package com.creysys.ThermalScience.compat;

import com.creysys.ThermalScience.compat.computercraft.CompatComputercraft;
import com.creysys.ThermalScience.compat.waila.CompatWaila;
import cpw.mods.fml.common.Loader;

/**
 * Created by Creysys on 05 Mar 15.
 */
public class ThermalScienceCompat {

    public static void initialize(){

        if(Loader.isModLoaded("Waila")) {
            CompatWaila.initialize();
        }

        if(Loader.isModLoaded("ComputerCraft")){
            CompatComputercraft.initialize();
        }
    }

}
