package com.creysys.ThermalScience.recipe.recipe;

import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;

/**
 * Created by Creysys on 17 Feb 15.
 */
public class RecipeCompressor extends ThermalScienceRecipe {

    public boolean portable;

    public RecipeCompressor(Object[] input, Object[] outputs, int energy, boolean portable) {
        super(input, outputs, energy);

        this.portable = portable;
    }
}
