package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiCompressor;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.awt.*;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCompressor extends RecipeHandlerMachine {
    @Override
    public void loadTransferRects() {
        this.gui = new GuiCompressor(null,null);
        this.recipes = ThermalScienceRecipes.recipesCompressor;
        this.name = "compressor";
        super.loadTransferRects();
    }

    @Override
    public Rectangle getRecipesRect() {
        return new Rectangle(70, 20, 30, 20);
    }
}
