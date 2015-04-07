package com.creysys.ThermalScience.compat.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.compat.nei.recipeHandler.*;
import com.creysys.ThermalScience.item.ItemMaterial;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Creysys on 07 Feb 15.
 */
public class NEIThermalScienceConfig implements IConfigureNEI {

    public List<TemplateRecipeHandler> handlers;

    @Override
    public void loadConfig() {
        handlers = new ArrayList<TemplateRecipeHandler>();

        handlers.add(new RecipeHandlerCentrifuge());
        handlers.add(new RecipeHandlerCompressor());
        handlers.add(new RecipeHandlerCarbothermicFurnace());
        handlers.add(new RecipeHandlerWiremill().newInstance());
        handlers.add(new RecipeHandlerAssemblingMachine());
        handlers.add(new RecipeHandlerMagnetizer());
        handlers.add(new RecipeHandlerExtractor());
        handlers.add(new RecipeHandlerWorld());


        for(int i = 0; i < handlers.size(); i++) {
            API.registerRecipeHandler(handlers.get(i));
            API.registerUsageHandler(handlers.get(i));
        }


        RecipeHandlerWorld.addWorldCraftingRecipe(ItemMaterial.ingotMagneticIron.copy(), "Rub a piece of iron on wool\nto magnetize it.");
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
