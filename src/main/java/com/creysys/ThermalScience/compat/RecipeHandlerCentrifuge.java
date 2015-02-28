package com.creysys.ThermalScience.compat;

import com.creysys.ThermalScience.gui.GuiCentrifuge;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCentrifuge extends RecipeHandlerMachine{
    public RecipeHandlerCentrifuge() {
        super(new GuiCentrifuge(null,null), ThermalScienceRecipes.centrifugeRecipes);
    }
}
