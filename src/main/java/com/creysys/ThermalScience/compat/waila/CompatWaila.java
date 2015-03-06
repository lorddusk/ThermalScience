package com.creysys.ThermalScience.compat.waila;

import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Created by Creysys on 05 Mar 15.
 */
public class CompatWaila {

    public static void initialize(){
        FMLInterModComms.sendMessage("Waila", "register", "com.creysys.ThermalScience.compat.waila.ThermalScienceWailaDataProvider.load");
    }

}
