package com.creysys.ThermalScience.compat.nei.recipeHandler;

import com.creysys.ThermalScience.client.gui.guiScreen.GuiExtractor;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.awt.*;

/**
 * Created by Creysys on 06 Apr 15.
 */
public class RecipeHandlerExtractor extends RecipeHandlerMachine {
    @Override
    public void loadTransferRects() {
        this.gui = new GuiExtractor(null,null);
        this.recipes = ThermalScienceRecipes.recipesExtractor;
        this.name = "extractor";
        super.loadTransferRects();
    }

    @Override
    public Rectangle getRecipesRect() {
        return new Rectangle(70, 20, 30, 20);
    }
}
