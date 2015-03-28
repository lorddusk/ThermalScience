package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiWiremill;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.awt.*;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerWiremill extends RecipeHandlerMachine {
    @Override
    public void loadTransferRects() {
        this.gui = new GuiWiremill(null, null);
        this.recipes = ThermalScienceRecipes.recipesWiremill;
        this.name = "wiremill";
        super.loadTransferRects();
    }

    @Override
    public Rectangle getRecipesRect() {
        return new Rectangle(70, 20, 30, 20);
    }
}
