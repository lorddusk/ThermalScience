package com.creysys.ThermalScience.compat.nei.recipeHandler;

import com.creysys.ThermalScience.client.gui.guiScreen.GuiMachine;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiMagnetizer;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 21 Mar 15.
 */
public class RecipeHandlerMagnetizer extends RecipeHandlerMachine {
    public RecipeHandlerMagnetizer() {
        super(new GuiMagnetizer(null, null), ThermalScienceRecipes.magnetizerRecipes);
    }
}
