package com.creysys.ThermalScience.compat;

import com.creysys.ThermalScience.gui.GuiMachine;
import com.creysys.ThermalScience.gui.GuiWiremill;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerWiremill extends RecipeHandlerMachine
{
    public RecipeHandlerWiremill() {
        super(new GuiWiremill(null,null), ThermalScienceRecipes.wiremillRecipes);
    }
}
