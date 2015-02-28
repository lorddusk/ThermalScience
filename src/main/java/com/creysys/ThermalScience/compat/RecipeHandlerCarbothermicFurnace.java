package com.creysys.ThermalScience.compat;

import com.creysys.ThermalScience.gui.GuiCarbothermicFurnace;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCarbothermicFurnace extends RecipeHandlerMachine {
    public RecipeHandlerCarbothermicFurnace() {
        super(new GuiCarbothermicFurnace(null,null), ThermalScienceRecipes.carbothermicFurnaceRecipes);
    }
}
