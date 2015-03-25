package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cofh.lib.util.helpers.ColorHelper;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Creysys on 24 Mar 15.
 */
public class RecipeHandlerWorld extends TemplateRecipeHandler {

    public static Hashtable<ItemStack, String> mapItems = new Hashtable();

    public static void addWorldCraftingRecipe(ItemStack stack, String text){
        mapItems.put(stack, text);
    }


    public class CachedRecipeWorld extends CachedRecipe{

        public ItemStack stack;
        public String text;

        public CachedRecipeWorld(ItemStack stack, String text){
            this.stack = stack;
            this.text = text;
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(stack, 73, 0);
        }
    }


    @Override
    public void loadCraftingRecipes(ItemStack result) {
        arecipes.clear();

        for(Map.Entry<ItemStack, String> entry : mapItems.entrySet()){
            if(ThermalScienceUtil.areStacksEqual(result, entry.getKey())){
                arecipes.add(new CachedRecipeWorld(result, entry.getValue()));
            }
        }
    }

    @Override
    public void drawBackground(int recipe) {
        ArrayList<String> list = new ArrayList(mapItems.values());

        String[] splits = list.get(recipe).split("\n");

        for(int i = 0; i < splits.length; i++) {
            GuiDraw.drawStringC(splits[i], 82, 30 + i * 10, ColorHelper.DYE_BLACK, false);
        }
    }

    @Override
    public void drawForeground(int recipe) { }

    @Override
    public String getGuiTexture() {
        return null;
    }

    @Override
    public String getRecipeName() {
        return "World Crafting";
    }
}
