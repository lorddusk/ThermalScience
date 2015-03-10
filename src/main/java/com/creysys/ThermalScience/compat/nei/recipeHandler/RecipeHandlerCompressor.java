package com.creysys.ThermalScience.compat.nei.recipeHandler;

import com.creysys.ThermalScience.client.gui.guiScreen.GuiCompressor;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCompressor extends RecipeHandlerMachine {
    public RecipeHandlerCompressor() {
        super(new GuiCompressor(null,null), ThermalScienceRecipes.compressorRecipes);
    }
}
