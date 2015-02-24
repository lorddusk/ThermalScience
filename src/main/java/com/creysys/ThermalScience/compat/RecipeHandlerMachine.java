package com.creysys.ThermalScience.compat;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.container.ContainerBasic;
import com.creysys.ThermalScience.gui.GuiCentrifuge;
import com.creysys.ThermalScience.gui.GuiMachine;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

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

        gui.drawProgress(100,xOffset - xCrop,yOffset - yCrop);
        gui.drawEnergy(recipe.energy, 100000,xOffset - xCrop,yOffset - yCrop);
    }

    @Override
    public void drawProgressBar(int x, int y, int tx, int ty, int w, int h, float completion, int direction) {
        super.drawProgressBar(x, y, tx, ty, w, h, completion, direction);
    }

    @Override
    public int recipiesPerPage() {
        return 2;
    }
}
