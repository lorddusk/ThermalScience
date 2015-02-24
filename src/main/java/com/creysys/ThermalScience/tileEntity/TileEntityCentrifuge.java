package com.creysys.ThermalScience.tileEntity;

import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 1/31/2015.
 */
public class TileEntityCentrifuge extends TileEntityMachine{
    public TileEntityCentrifuge() {
        super(100000, 80);
    }

    @Override
    public List<ThermalScienceRecipe> getRecipes() {
        return ThermalScienceRecipes.centrifugeRecipes;
    }

    @Override
    public int getCraftingInputSize() {
        return 1;
    }

    @Override
    public int getCraftingOutputSize() {
        return 4;
    }

    @Override
    public int[] getCraftingInputSlots() {
        return new int[]{0};
    }

    @Override
    public int[] getCraftingOutputSlots() {
        return new int[]{1,2,3,4};
    }

    @Override
    public int getSizeInventory() {
        return 5;
    }

    @Override
    public String getInventoryName() {
        return "centrifuge";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
}
