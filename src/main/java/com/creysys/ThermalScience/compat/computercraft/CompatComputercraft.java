package com.creysys.ThermalScience.compat.computercraft;

import dan200.computercraft.api.ComputerCraftAPI;

/**
 * Created by Creysys on 05 Mar 15.
 */
public class CompatComputercraft {

    public static void initialize(){
        ComputerCraftAPI.registerPeripheralProvider(new ThermalSciencePeripheralProvider());
    }

}
