package com.creysys.ThermalScience.tileEntity.machine;

import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 21 Mar 15.
 */
public class TileEntityMagnetizer extends TileEntityMachine {
    @Override
    public List<ThermalScienceRecipe> getRecipes() {
        return ThermalScienceRecipes.recipesMagnetizer;
    }

    @Override
    public int getCraftingInputSize() {
        return 1;
    }

    @Override
    public int getCraftingOutputSize() {
        return 1;
    }

    @Override
    public int[] getCraftingInputSlots() {
        return new int[]{0};
    }

    @Override
    public int[] getCraftingOutputSlots() {
        return new int[]{1};
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public String getInventoryName() {
        return "magnetizer";
    }
}
