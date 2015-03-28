package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiCarbothermicFurnace;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.awt.*;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCarbothermicFurnace extends RecipeHandlerMachine {
    @Override
    public void loadTransferRects() {
        this.gui = new GuiCarbothermicFurnace(null,null);
        this.recipes = ThermalScienceRecipes.recipesCarbothermicFurnace;
        this.name = "carbothermicFurnace";
        super.loadTransferRects();
    }

    @Override
    public Rectangle getRecipesRect() {
        return new Rectangle(80, 20, 30, 20);
    }
}
