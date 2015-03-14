package com.creysys.ThermalScience.tileEntity;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Creysys on 31.01.2015.
 */
public class TileEntityCompressor extends TileEntityMachine {

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public String getInventoryName() {
        return "compressor";
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
    public List<ThermalScienceRecipe> getRecipes() {
        return ThermalScienceRecipes.compressorRecipes;
    }

    @Override
    public int[] getCraftingInputSlots() {
        return new int[]{0};
    }

    @Override
    public int[] getCraftingOutputSlots() {
        return new int[]{1};
    }
}
