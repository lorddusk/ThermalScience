package com.creysys.ThermalScience.recipe;

import com.creysys.ThermalScience.recipe.recipe.RecipeAssemblingMachine;
import com.creysys.ThermalScience.recipe.recipe.RecipeCompressor;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Creysys on 22 Mar 15.
 */
public class ThermalScienceRecipeLoader {

    public static ArrayList<ThermalScienceRecipe> currentMachine = null;
    public static int currentEnergy = 0;
    public static int currentFluidAmount = 0;
    public static String currentFluid = "water";
    public static boolean currentPortable = false;

    public static void load(File file) {

        if (!file.exists()) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());

            for (int i = 0; i < lines.size(); i++) {
                if (!doLine(lines.get(i))) {
                    System.out.println("Failed to do line: " + (i + 1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean doLine(String line) {
        String[] splits = line.split(": ");

        if (splits.length != 2) {
            return false;
        }

        String key = splits[0];
        String value = splits[1];

        switch (key) {
            case "machine":
                switch (value) {
                    case "assemblingMachine":
                        currentMachine = ThermalScienceRecipes.recipesAssemblingMachine;
                        break;
                    case "carbothermicFurnace":
                        currentMachine = ThermalScienceRecipes.recipesCarbothermicFurnace;
                        break;
                    case "centrifuge":
                        currentMachine = ThermalScienceRecipes.recipesCentrifuge;
                        break;
                    case "compressor":
                        currentMachine = ThermalScienceRecipes.recipesCompressor;
                        break;
                    case "magnetizer":
                        currentMachine = ThermalScienceRecipes.recipesMagnetizer;
                        break;
                    case "wiremill":
                        currentMachine = ThermalScienceRecipes.recipesWiremill;
                        break;
                    default:
                        System.out.println("Unknown machine!");
                        return false;
                }
                break;
            case "energy":
                try {
                    currentEnergy = Integer.parseInt(value);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
                break;
            case "fluidAmount":
                try {
                    currentFluidAmount = Integer.parseInt(value);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
                break;
            case "recipe":
                ThermalScienceRecipe recipe = parseRecipe(value);
                if (recipe == null) {
                    System.out.println("Failed to parse recipe!");
                    return false;
                }

                currentMachine.add(recipe);
            case "fluid":
                currentFluid = value;
                break;
            case "portable":
                try {
                    currentPortable = Boolean.parseBoolean(value);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
                break;
            default:
                System.out.println("Unknown key!");
                break;
        }

        return true;
    }

    public static ThermalScienceRecipe parseRecipe(String value) {
        String[] splits = value.split("\\)->\\(");

        if (splits.length != 2) {
            return null;
        }


        Object[] input = getObjectStackArray(splits[0].substring(1));
        Object[] output = getObjectStackArray(splits[1].substring(0, splits[1].length() - 1));

        if (input == null || output == null) {
            return null;
        }

        if (currentMachine == ThermalScienceRecipes.recipesCompressor) {
            return new RecipeCompressor(input, output, currentEnergy, currentPortable);
        } else if (currentMachine == ThermalScienceRecipes.recipesAssemblingMachine) {
            return new RecipeAssemblingMachine(input, output, currentEnergy, currentFluid, currentFluidAmount);
        }

        return new ThermalScienceRecipe(input, output, currentEnergy);
    }

    public static Object[] getObjectStackArray(String s) {
        ArrayList<Object> list = new ArrayList<Object>();

        String[] splits = s.split(", ");
        for (int i = 0; i < splits.length; i++) {
            Object object = getObject(splits[i]);

            if (object == null) {
                System.out.println("Could not parse object!");
                return null;
            }

            list.add(object);
        }


        Object[] ret = new Object[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i);

        }
        return ret;
    }

    public static Object getObject(String s) {
        if (s.startsWith("<") && s.endsWith(">")) {
            String[] splits = s.substring(1, s.length() - 1).split(",");

            if (splits.length < 2 || splits.length > 4) {
                return null;
            }

            String mod = splits[0];
            String name = splits[1];
            int amount = 1;
            int meta = 0;

            if (splits.length >= 3) {
                try {
                    amount = Integer.parseInt(splits[2]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
            }

            if (splits.length >= 4) {
                try {
                    meta = Integer.parseInt(splits[3]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
            }

            ItemStack stack = GameRegistry.findItemStack(mod, name, amount);

            if (stack == null) {
                System.out.println("Item could not be found!");
                return null;
            }

            stack.setItemDamage(meta);
            return stack;
        } else if (s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }

        return null;
    }
}
