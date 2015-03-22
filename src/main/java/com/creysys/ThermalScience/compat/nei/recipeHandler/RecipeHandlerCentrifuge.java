package com.creysys.ThermalScience.compat.nei.recipeHandler;

import com.creysys.ThermalScience.client.gui.guiScreen.GuiCentrifuge;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCentrifuge extends RecipeHandlerMachine{
    public RecipeHandlerCentrifuge() {
        super(new GuiCentrifuge(null,null), ThermalScienceRecipes.recipesCentrifuge);
    }
}
