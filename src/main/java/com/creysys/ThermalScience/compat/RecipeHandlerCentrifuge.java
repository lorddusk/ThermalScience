package com.creysys.ThermalScience.compat;

import com.creysys.ThermalScience.gui.GuiCentrifuge;
import com.creysys.ThermalScience.gui.GuiMachine;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCentrifuge extends RecipeHandlerMachine{
    public RecipeHandlerCentrifuge() {
        super(new GuiCentrifuge(null,null), ThermalScienceRecipes.centrifugeRecipes);
    }
}
