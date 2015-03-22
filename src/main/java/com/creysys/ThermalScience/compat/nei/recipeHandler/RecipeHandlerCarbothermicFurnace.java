package com.creysys.ThermalScience.compat.nei.recipeHandler;

import com.creysys.ThermalScience.client.gui.guiScreen.GuiCarbothermicFurnace;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCarbothermicFurnace extends RecipeHandlerMachine {
    public RecipeHandlerCarbothermicFurnace() {
        super(new GuiCarbothermicFurnace(null,null), ThermalScienceRecipes.recipesCarbothermicFurnace);
    }
}
