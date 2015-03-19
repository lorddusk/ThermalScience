package com.creysys.ThermalScience.recipe.recipe;

import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Creysys on 19 Mar 15.
 */
public class RecipeAssemblingMachine extends ThermalScienceRecipe {

    public String fluid;
    public int fluidAmount;

    public RecipeAssemblingMachine(Object[] input, Object[] outputs, int energy, String fluid, int fluidAmount) {
        super(input, outputs, energy);

        this.fluid = fluid;
        this.fluidAmount = fluidAmount;
    }

    @Override
    public void writeCustomToNBT(NBTTagCompound compound) {
        compound.setString(ThermalScienceNBTTags.Fluid, fluid);
        compound.setInteger(ThermalScienceNBTTags.Amount, fluidAmount);
    }

    @Override
    public String getId() {
        return "recipeAssemblingMachine";
    }
}
