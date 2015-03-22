package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.lib.gui.GuiDraw;
import cofh.lib.util.helpers.ColorHelper;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiAssemblingMachine;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiMachine;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import com.creysys.ThermalScience.recipe.recipe.RecipeAssemblingMachine;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.List;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class RecipeHandlerAssemblingMachine extends RecipeHandlerMachine {
    public RecipeHandlerAssemblingMachine() {
        super(new GuiAssemblingMachine(null, null), ThermalScienceRecipes.recipesAssemblingMachine);
    }

    @Override
    public void drawForeground(int recipeID) {
        super.drawForeground(recipeID);

        GuiAssemblingMachine guiAssemblingMachine = (GuiAssemblingMachine) gui;
        RecipeAssemblingMachine recipe = (RecipeAssemblingMachine)((CachedRecipeMachine)arecipes.get(recipeID)).recipe;

        guiAssemblingMachine.drawFluid(recipe.fluidAmount, 1000, xOffset - xCrop, yOffset - yCrop, FluidRegistry.getFluid(recipe.fluid));
        GuiDraw.drawString(recipe.fluidAmount + " Mb", guiAssemblingMachine.fluidX + xOffset - xCrop - 44, guiAssemblingMachine.fluidY + yOffset - yCrop + guiAssemblingMachine.fluidHeight - 10, ColorHelper.DYE_WHITE);
    }
}
