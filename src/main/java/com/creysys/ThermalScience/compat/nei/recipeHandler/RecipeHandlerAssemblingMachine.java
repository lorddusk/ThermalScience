package com.creysys.ThermalScience.compat.nei.recipeHandler;

import com.creysys.ThermalScience.client.gui.guiScreen.GuiAssemblingMachine;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiMachine;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class RecipeHandlerAssemblingMachine extends RecipeHandlerMachine {
    public RecipeHandlerAssemblingMachine() {
        super(new GuiAssemblingMachine(null, null), ThermalScienceRecipes.assemblingMachineRecipes);
    }
}
