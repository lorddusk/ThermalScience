package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.api.API;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cofh.lib.util.helpers.ColorHelper;
import cofh.thermalexpansion.ThermalExpansion;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiAssemblingMachine;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiCarbothermicFurnace;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiMachine;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import com.creysys.ThermalScience.recipe.recipe.RecipeAssemblingMachine;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import crazypants.enderio.machine.recipe.Recipe;
import net.minecraftforge.fluids.FluidRegistry;

import java.awt.*;
import java.util.List;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class RecipeHandlerAssemblingMachine extends RecipeHandlerMachine {
    public void loadTransferRects() {
        this.gui = new GuiAssemblingMachine(null, null);
        this.recipes = ThermalScienceRecipes.recipesAssemblingMachine;
        this.name = "assemblingMachine";
        super.loadTransferRects();
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
