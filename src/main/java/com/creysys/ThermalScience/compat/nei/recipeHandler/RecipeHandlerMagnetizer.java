package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiMachine;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiMagnetizer;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 21 Mar 15.
 */
public class RecipeHandlerMagnetizer extends RecipeHandlerMachine {
    @Override
    public void loadTransferRects() {
        this.gui = new GuiMagnetizer(null, null);
        this.recipes = ThermalScienceRecipes.recipesMagnetizer;
        this.name = "magnetizer";
        super.loadTransferRects();
    }

    @Override
    public int getOffsetX() {
        return -5;
    }
}
