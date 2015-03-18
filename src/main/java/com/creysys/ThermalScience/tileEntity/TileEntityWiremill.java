package com.creysys.ThermalScience.tileEntity;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class TileEntityWiremill extends TileEntityMachine {

    @Override
    public List<ThermalScienceRecipe> getRecipes() {
        return ThermalScienceRecipes.wiremillRecipes;
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
        return "wiremill";
    }

    @Override
    public int getCraftingSpeed() {
        return super.getCraftingSpeed() / 2;
    }
}
