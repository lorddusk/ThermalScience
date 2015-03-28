package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiCentrifuge;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCentrifuge extends RecipeHandlerMachine{
    @Override
    public void loadTransferRects() {
        this.gui = new GuiCentrifuge(null,null);
        this.recipes = ThermalScienceRecipes.recipesCentrifuge;
        this.name = "centrifuge";
        super.loadTransferRects();
    }
}
