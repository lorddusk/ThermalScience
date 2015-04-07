package com.creysys.ThermalScience.tileEntity.machine;

import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 06 Apr 15.
 */
public class TileEntityExtractor extends TileEntityMachine {
    @Override
    public List<ThermalScienceRecipe> getRecipes() {
        return ThermalScienceRecipes.recipesExtractor;
    }

    @Override
    public int getCraftingInputSize() {
        return 1;
    }

    @Override
    public int getCraftingOutputSize() {
        return 2;
    }

    @Override
    public int[] getCraftingInputSlots() {
        return new int[]{0};
    }

    @Override
    public int[] getCraftingOutputSlots() {
        return new int[]{1, 2};
    }

    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public String getInventoryName() {
        return "extractor";
    }

    @Override
    public int getCraftingSpeed() {
        int factor = (int)Math.ceil(10 - 5f / 3f * getBlockMetadata());
        return super.getCraftingSpeed() / factor;
    }
}
