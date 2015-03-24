package com.creysys.ThermalScience.compat.nei.recipeHandler;

import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.ItemStack;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Creysys on 24 Mar 15.
 */
public class RecipeHandlerWorld extends TemplateRecipeHandler {

    public static Dictionary mapItems = new Hashtable();

    public static void addWorldCraftingRecipe(ItemStack stack, String text){
        mapItems.put(stack, text);
    }

    @Override
    public String getGuiTexture() {
        return null;
    }

    @Override
    public String getRecipeName() {
        return "world";
    }
}
