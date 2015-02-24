package com.creysys.ThermalScience.compat;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.creysys.ThermalScience.ThermalScience;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Creysys on 07 Feb 15.
 */
public class NEIThermalScienceConfig implements IConfigureNEI {

    public List<RecipeHandlerMachine> handlers;

    @Override
    public void loadConfig() {
        handlers = new ArrayList<RecipeHandlerMachine>();

        handlers.add(new RecipeHandlerCentrifuge());
        handlers.add(new RecipeHandlerCompressor());
        handlers.add(new RecipeHandlerCarbothermicFurnace());
        handlers.add(new RecipeHandlerWiremill());


        for(int i = 0; i < handlers.size(); i++) {
            API.registerRecipeHandler(handlers.get(i));
            API.registerUsageHandler(handlers.get(i));
        }

    }

    @Override
    public String getName() {
        return ThermalScience.MODNAME;
    }

    @Override
    public String getVersion() {
        return ThermalScience.VERSION;
    }
}
