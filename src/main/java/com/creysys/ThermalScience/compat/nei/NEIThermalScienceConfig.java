package com.creysys.ThermalScience.compat.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.compat.nei.recipeHandler.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Creysys on 07 Feb 15.
 */
public class NEIThermalScienceConfig implements IConfigureNEI {

    public List<TemplateRecipeHandler> handlers;

    @Override
    public void loadConfig() {
        handlers = new ArrayList<>();

        handlers.add(new RecipeHandlerCentrifuge().newInstance());
        handlers.add(new RecipeHandlerCompressor().newInstance());
        handlers.add(new RecipeHandlerCarbothermicFurnace().newInstance());
        handlers.add(new RecipeHandlerWiremill().newInstance().newInstance());
        handlers.add(new RecipeHandlerAssemblingMachine().newInstance());
        handlers.add(new RecipeHandlerMagnetizer().newInstance());
        handlers.add(new RecipeHandlerWorld());


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
