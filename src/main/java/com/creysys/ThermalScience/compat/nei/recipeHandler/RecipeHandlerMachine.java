package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cofh.lib.util.helpers.ColorHelper;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import com.creysys.ThermalScience.client.gui.guiScreen.GuiMachine;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class RecipeHandlerMachine extends TemplateRecipeHandler {

    public GuiMachine gui;
    public List<ThermalScienceRecipe> recipes;

    public int xOffset = -5;
    public int yOffset = 8;
    public int xCrop = 0;
    public int yCrop = 11;

    public RecipeHandlerMachine(GuiMachine gui, List<ThermalScienceRecipe> recipes){
        this.gui = gui;
        this.recipes = recipes;
    }

    private class CachedRecipeMachine extends CachedRecipe {

        private Slot[] inputSlots;
        private Slot[] outputSlots;

        public List<ItemStack> inputs;
        public List<ItemStack> outputs;
        public int energy;

        public boolean isValid;

        public CachedRecipeMachine(GuiMachine gui, ThermalScienceRecipe recipe) {

            inputSlots = gui.getInputSlots();
            outputSlots = gui.getOutputSlots();
            isValid = true;

            outputs = new ArrayList<ItemStack>();
            for (int j = 0; j < recipe.outputs.length; j++) {
                ItemStack stack = ThermalScienceUtil.getStack(recipe.outputs[j]);
                if(stack != null) {
                    outputs.add(stack);
                }
                else{
                    isValid = false;
                    return;
                }
            }

            inputs = new ArrayList<ItemStack>();
            for (int j = 0; j < recipe.inputs.length; j++) {
                ItemStack stack = ThermalScienceUtil.getStack(recipe.inputs[j]);
                if(stack != null) {
                    inputs.add(stack);
                }
                else{
                    isValid = false;
                    return;
                }
            }

            energy = recipe.energy;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            List<PositionedStack> ret = new ArrayList<PositionedStack>();

            for (int i = 0; i < inputs.size(); i++) {
                ret.add(new PositionedStack(inputs.get(i), inputSlots[i].xDisplayPosition + xOffset - xCrop, inputSlots[i].yDisplayPosition + yOffset - yCrop));
            }

            return ret;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            List<PositionedStack> ret = new ArrayList<PositionedStack>();

            for (int i = 0; i < outputs.size(); i++) {
                ret.add(new PositionedStack(outputs.get(i), outputSlots[i].xDisplayPosition + xOffset - xCrop, outputSlots[i].yDisplayPosition + yOffset - yCrop));
            }

            return ret;
        }
    }

    @Override
    public String getGuiTexture() {
        return null;
    }

    @Override
    public String getRecipeName() {
        return gui.getName();
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<ThermalScienceRecipe> recipes = ThermalScienceUtil.getRecipesFor(this.recipes, result);

        arecipes.clear();
        for (int i = 0; i < recipes.size(); i++) {
            CachedRecipeMachine recipe = new CachedRecipeMachine(gui, recipes.get(i));
            if(recipe.isValid) {
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<ThermalScienceRecipe> recipes = ThermalScienceUtil.getUsageFor(this.recipes, ingredient);

        arecipes.clear();
        for(int i = 0; i < recipes.size(); i++){
            CachedRecipeMachine recipe = new CachedRecipeMachine(gui, recipes.get(i));
            if(recipe.isValid) {
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public void drawBackground(int recipe) {
        ThermalScienceUtil.setTexture(gui.guiTexture);
        ThermalScienceUtil.drawTexturedModalRect(xOffset, yOffset, xCrop, yCrop, 166, 48);
    }

    @Override
    public void drawForeground(int recipeID) {
        CachedRecipeMachine recipe = (CachedRecipeMachine) arecipes.get(recipeID);

        gui.drawProgress((cycleticks * 3) % 101, xOffset - xCrop, yOffset - yCrop);

        //10mil is max for energy use
        int energyInterpolated = (int)(16f / 7f * Math.log10(recipe.energy + 1));
        gui.drawEnergy(energyInterpolated, 16, xOffset - xCrop, yOffset - yCrop);

        GuiDraw.drawString(recipe.energy + " RF", gui.energyX + xOffset - xCrop + gui.energyWidth + 10, gui.energyY + yOffset - yCrop + gui.energyHeight - 10, ColorHelper.DYE_WHITE);
    }


    @Override
    public void loadTransferRects() {
        super.loadTransferRects();
    }

    @Override
    public int recipiesPerPage() {
        return 2;
    }
}
