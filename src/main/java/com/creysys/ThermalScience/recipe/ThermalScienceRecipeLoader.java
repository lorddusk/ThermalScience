package com.creysys.ThermalScience.recipe;

import com.creysys.ThermalScience.recipe.recipe.RecipeAssemblingMachine;
import com.creysys.ThermalScience.recipe.recipe.RecipeCompressor;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Creysys on 22 Mar 15.
 */
public class ThermalScienceRecipeLoader {

    public static ArrayList<ThermalScienceRecipe> currentMachine = ThermalScienceRecipes.recipesAssemblingMachine;
    public static int currentEnergy = 0;
    public static int currentFluidAmount = 0;
    public static String currentFluid = "water";
    public static boolean currentPortable = false;

    public static final String[] defaultText = new String[]{"//machine: carbothermicFurnace","//energy: 1000","//recipe: (<minecraft,sand,2>, <minecraft,coal,2>)->(<EnderIO,itemMaterial,2,4>)","","//machine: compressor","//energy: 100000","//recipe: (\"dustCoal,64\", \"wtf,13\")->(<minecraft,diamond>)","//recipe: (\"ingotIron,4\")->(\"ingotSilver,1\")","","//machine: assemblingMachine","//energy: 50000","//fluid: cryotheum","//fluidAmount: 420","//recipe: (\"ingotIron,10\", \"ingotGold,10\")->(<minecraft,diamond>)","","//machine: compressor","//portable: true","//recipe: (...)(...)","//portable: false","//recipe: (...)(...)"};

    public static void load(File file) {

        if (!file.exists()) {
            try {
                Files.write(file.toPath(), ThermalScienceUtil.toList(defaultText), Charset.defaultCharset(), StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).trim().length() == 0 ||lines.get(i).startsWith("//")) {
                    continue;
                }

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

        if (key.equals("machine")) {
            if (value.equals("assemblingMachine")) {
                currentMachine = ThermalScienceRecipes.recipesAssemblingMachine;
            } else if (value.equals("carbothermicFurnace")) {
                currentMachine = ThermalScienceRecipes.recipesCarbothermicFurnace;
            } else if (value.equals("centrifuge")) {
                currentMachine = ThermalScienceRecipes.recipesCentrifuge;
            } else if (value.equals("compressor")) {
                currentMachine = ThermalScienceRecipes.recipesCompressor;
            } else if (value.equals("magnetizer")) {
                currentMachine = ThermalScienceRecipes.recipesMagnetizer;
            } else if (value.equals("wiremill")) {
                currentMachine = ThermalScienceRecipes.recipesWiremill;
            } else {
                System.out.println("Unknown machine!");
                return false;
            }
        } else if (key.equals("energy")) {
            try {
                currentEnergy = Integer.parseInt(value);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else if (key.equals("fluidAmount")) {
            try {
                currentFluidAmount = Integer.parseInt(value);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else if (key.equals("recipe")) {
            ThermalScienceRecipe recipe = parseRecipe(value);
            if (recipe == null) {
                System.out.println("Failed to parse recipe!");
                return false;
            }

            currentMachine.add(recipe);
        } else if (key.equals("fluid")) {
            currentFluid = value;
        } else if (key.equals("portable")) {
            try {
                currentPortable = Boolean.parseBoolean(value);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Unknown key!");
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
