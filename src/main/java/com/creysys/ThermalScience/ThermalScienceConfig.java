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
    public static boolean recipeOverrideGunpowder;
    public static boolean recipeOverrideNiter;
    public static boolean recipeOverrideSulfur;
    public static boolean recipeOverrideMachines;
    public static boolean recipeOverrideDynamics;
    public static boolean recipeOverrideEnergyCells;
    public static boolean recipeOverrideTesseract;
    public static boolean recipeOverrideServo;
    public static boolean recipeOverrideDynamos;

    public static boolean showOreDictNames;
    public static boolean showItemNames;

    public static void load(File file){
        Configuration config = new Configuration(file);
        config.load();

        stopEndermenGrief = config.getBoolean("stopEndermenGrief", "misc", false, "Prevents endermen from taking your blocks!");
        showOreDictNames = config.getBoolean("showOreDictNames", "misc", false, "Shows ore dictionary names in tooltips");
        showItemNames = config.getBoolean("showItemNames", "misc", false, "Shows item names in tooltips");

        crashOnInvalidRecipe = config.getBoolean("crashOnInvalidRecipe", "common", true, "Crashes the game if recipes couldnt load properly.");

        config.addCustomCategoryComment("recipe overrides", "These settings will fully replace the way some items are made.");
        recipeOverrideSilicon = config.getBoolean("silicon", "recipe overrides", false, "Make silicon only in carb furnace");
        recipeOverrideNiter = config.getBoolean("niter", "recipe overrides", false, "Get niter (saltpeter) only from centrifuge");
        recipeOverrideSulfur = config.getBoolean("sulfur", "recipe overrides", false, "Get sulfur only from centrifuge");
        recipeOverrideGunpowder = config.getBoolean("gunpowder", "recipe overrides", false, "Remove charcoal gunpowder recipe");
        recipeOverrideMachines = config.getBoolean("machines", "recipe overrides", false, "Greg all machine recipes");
        recipeOverrideDynamics = config.getBoolean("dynamics", "recipe overrides", false, "Greg thermal dynamics recipes");
        recipeOverrideEnergyCells = config.getBoolean("energyCells", "recipe overrides", false, "Greg all energy cells");
        recipeOverrideTesseract = config.getBoolean("tesseract", "recipe overrides", false, "Greg tesseracts");
        recipeOverrideServo = config.getBoolean("servo", "recipe overrides", false, "(Greg) servo recipe");
        recipeOverrideDynamos = config.getBoolean("dynamos", "recipe overrides", false, "Greg dynamo recipe");

        config.save();
    }
}
