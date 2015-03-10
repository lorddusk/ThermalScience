package com.creysys.ThermalScience;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Creysys on 19 Feb 15.
 */
public class ThermalScienceConfig
{
    public static boolean stopEndermenGrief;
    public static boolean crashOnInvalidRecipe;

    public static boolean recipeOverrideSilicon;
    public static boolean recipesOverrideGunpowder;

    public static boolean showOreDictNames;

    public static void load(File file){
        Configuration config = new Configuration(file);
        config.load();

        stopEndermenGrief = config.getBoolean("stopEndermenGrief", "misc", true, "Prevents endermen from taking your blocks!");
        showOreDictNames = config.getBoolean("showOreDictName", "misc", false, "Shows ore dictionary names in tooltips");

        crashOnInvalidRecipe = config.getBoolean("crashOnInvalidRecipe", "common", true, "Crashes the game if recipes couldnt load properly.");

        config.addCustomCategoryComment("recipe overrides", "These settings will fully replace the way some items are made.");
        recipeOverrideSilicon = config.getBoolean("silicon", "recipe overrides", true, "Make silicon in carb furnace");
        recipesOverrideGunpowder = config.getBoolean("gunpowder", "recipe overrides", true, "Remove charcoal gunpowder recipe");

        config.save();
    }
}
