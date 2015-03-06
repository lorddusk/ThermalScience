package com.creysys.ThermalScience.compat.nei.recipeHandler;

import com.creysys.ThermalScience.gui.GuiWiremill;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerWiremill extends RecipeHandlerMachine
{
    public RecipeHandlerWiremill() {
        super(new GuiWiremill(null,null), ThermalScienceRecipes.wiremillRecipes);
    }
}
