package com.creysys.ThermalScience.compat.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.compat.nei.recipeHandler.*;

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
        handlers.add(new RecipeHandlerAssemblingMachine());
        handlers.add(new RecipeHandlerMagnetizer());


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
