package com.creysys.ThermalScience.recipe;

import cofh.api.modhelpers.ThermalExpansionHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceConfig;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.item.ItemDust;
import com.creysys.ThermalScience.item.ItemMaterial;
import com.creysys.ThermalScience.item.ItemPortableCompressor;
import com.creysys.ThermalScience.recipe.recipe.RecipeCompressor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;

/**
 * Created by Creysys on 2/1/2015.
 */
public class ThermalScienceRecipes {

    public static ArrayList<ThermalScienceRecipe> carbothermicFurnaceRecipes;
    public static ArrayList<ThermalScienceRecipe> centrifugeRecipes;
    public static ArrayList<ThermalScienceRecipe> compressorRecipes;
    public static ArrayList<ThermalScienceRecipe> wiremillRecipes;

    public static void preInitialize(){
        carbothermicFurnaceRecipes = new ArrayList<ThermalScienceRecipe>();
        centrifugeRecipes = new ArrayList<ThermalScienceRecipe>();
        compressorRecipes = new ArrayList<ThermalScienceRecipe>();
        wiremillRecipes = new ArrayList<ThermalScienceRecipe>();

        //For some reason TE only accepts recipes in preInit dafuq m9
        if (Loader.isModLoaded("ThermalExpansion")) {
            ThermalExpansionHelper.addPulverizerRecipe(2400, new ItemStack(Blocks.netherrack), ItemDust.getDust("DirtyNetherrack"));
        }

        //EnderIO too lel
        if (Loader.isModLoaded("EnderIO")) {
            ThermalScienceUtil.addEnderIORecipe("sagmill", "Netherrack", "<itemStack modID=\"minecraft\" itemName=\"netherrack\" />", "<itemStack oreDictionary=\"dustDirtyNetherrack\" />", 2400);

            //Silicon
            ThermalScienceUtil.removeEnderIORecipe("sagmill", "EnderIO", "Silicon");
            ThermalScienceUtil.removeEnderIORecipe("sagmill", "EnderIO", "SiliconRedSand");

            addRecipe(carbothermicFurnaceRecipes, new Object[]{new ItemStack(Items.coal, 1), new ItemStack(Blocks.sand)}, new Object[]{new ItemStack(GameRegistry.findItem("EnderIO", "itemMaterial"), 1)}, 2000);
        }
    }

    public static void initialize() {
        //Materials
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemMaterial.getMaterial("Tube"), "RIR", "___", "RIR", 'I', "ingotInvar", 'R', Items.iron_ingot));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemMaterial.getMaterial("WireCopper"), "CC", 'C', "ingotCopper"));
        GameRegistry.addRecipe(ItemMaterial.getMaterial("Coil", 4), "WWW", "W_W", "WWW", 'W', ItemMaterial.getMaterial("WireCopper"));
        GameRegistry.addRecipe(ItemMaterial.getMaterial("Motor"), "CIC", 'C', ItemMaterial.getMaterial("Coil"), 'I', Items.iron_ingot);


        //Carbothermic Furnace
        //Ore Processing
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreIron,1", new ItemStack(Items.coal, 1)}, new Object[]{"ingotIron,2"}, 3000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreGold,1", new ItemStack(Items.coal, 1)}, new Object[]{"ingotGold,2"}, 3000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreAdamantium,1", new ItemStack(Items.coal, 1)}, new Object[]{"ingotAdamantium,2"}, 3000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreAluminium,1", new ItemStack(Items.coal, 1)}, new Object[]{"ingotAluminium,2"}, 3000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreCopper,1", new ItemStack(Items.coal, 1)}, new Object[]{"ingotCopper,2"}, 3000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreLead,1", new ItemStack(Items.coal, 1)}, new Object[]{"ingotLead,2"}, 3000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreSilver,1", new ItemStack(Items.coal, 1)}, new Object[]{"ingotSilver,2"}, 3000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreTin,1", new ItemStack(Items.coal, 1)}, new Object[]{"ingotTin,2"}, 3000);


        //Centrifuge
        addRecipe(centrifugeRecipes, new Object[]{new ItemStack(Items.magma_cream)}, new Object[]{new ItemStack(Items.blaze_powder), new ItemStack(Items.slime_ball)}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{new ItemStack(Items.ender_eye)}, new Object[]{new ItemStack(Items.ender_pearl), new ItemStack(Items.blaze_powder)}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{new ItemStack(Items.blaze_powder)}, new Object[]{"dustSulfur,1", "dustCoal,1"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{new ItemStack(Blocks.soul_sand, 4)}, new Object[]{new ItemStack(Blocks.sand, 2), "dustSaltpeter,1"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustDirtyNetherrack,2"}, new Object[]{"dustNetherrack,2", "dustSulfur,1"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustNetherrack,16"}, new Object[]{new ItemStack(Items.redstone), "dustSulfur,4", "dustCoal,1", "nuggetGold,1"}, 80000);


        //Alloy Seperating
        addRecipe(centrifugeRecipes, new Object[]{"dustBrass,4"}, new Object[]{"dustCopper,3", "dustZinc,1"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustBronze,4"}, new Object[]{"dustCopper,3", "dustTin,1"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustElectrum,2"}, new Object[]{"dustSilver,1", "dustGold,1"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustAluminiumBrass,4"}, new Object[]{"dustAluminium,3", "dustCopper,1"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustAlumite,6"}, new Object[]{"dustObsidian,2", "dustIron,4", "dustAluminium,10"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustManyullyn,1"}, new Object[]{"dustCobalt,1", "dustArdite,1"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustInvar,3"}, new Object[]{"dustIron,2", "dustNickel,1"}, 10000);

        //Compressor
        addCompressorRecipe(new Object[]{new ItemStack(Items.blaze_powder, 5)}, new Object[]{new ItemStack(Items.blaze_rod)}, 3200, false);
        addCompressorRecipe(new Object[]{"dustCoal,64"}, new Object[]{new ItemStack(Items.diamond)}, 100000, false);

        //Portable recipes
        addCompressorRecipe(new Object[]{new ItemStack(Items.coal, 9)}, new Object[]{new ItemStack(Blocks.coal_block)}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.diamond, 9)}, new Object[]{new ItemStack(Blocks.diamond_block)}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.redstone, 9)}, new Object[]{new ItemStack(Blocks.redstone_block)}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.dye, 9, 4)}, new Object[]{new ItemStack(Blocks.lapis_block)}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.emerald, 9)}, new Object[]{new ItemStack(Blocks.emerald_block)}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.wheat, 9)}, new Object[]{new ItemStack(Blocks.hay_block)}, 1000, true);

        //Wiremill
        addRecipe(wiremillRecipes, new Object[]{"ingotCopper,1"}, new Object[]{ItemMaterial.getMaterial("WireCopper", 2)}, 3000);
    }

    public static void postInitialize() {
        //Add these recipes after all items have been registered
        addCompressorOreRecipes();
        addModRecipes();
    }

    public static void addRecipe(ArrayList<ThermalScienceRecipe> recipes, ThermalScienceRecipe recipe) {
        if (recipe.isValid()) {
            recipes.add(recipe);
        } else {
            FMLCommonHandler.instance().raiseException(new Exception("Recipe skipped:" + recipe.inputs[0]), "Invalid recipe!", ThermalScienceConfig.crashOnInvalidRecipe);
        }
    }

    public static void addRecipe(ArrayList<ThermalScienceRecipe> recipes, Object[] input, Object[] output, int energy) {
        addRecipe(recipes, new ThermalScienceRecipe(input, output, energy));
    }

    public static void addCompressorRecipe(Object[] input, Object[] output, int energy, boolean portable) {
        addRecipe(compressorRecipes, new RecipeCompressor(input, output, energy, portable));
    }

    public static void addCompressorOreRecipes() {
        //Add a recipe for each block that's made out of ingots
        String[] ores = OreDictionary.getOreNames();

        for (int i = 0; i < ores.length; i++) {
            if (ores[i].startsWith("ingot")) {
                String oreBlock = "block" + ores[i].substring(5);
                for (int j = 0; j < ores.length; j++) {
                    if (ores[j].equals(oreBlock)) {
                        addCompressorRecipe(new Object[]{ores[i] + ",9"}, new Object[]{oreBlock + ",1"}, 1000, true);
                        break;
                    }
                }
            }
        }

        ItemPortableCompressor.registerRecipes();
    }

    public static void addModRecipes(){
        //Thermal Expansion
        if (Loader.isModLoaded("ThermalExpansion")) {

            ItemStack basicMachineFrame = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Frame"), 1, 0);

            Item teMaterial = GameRegistry.findItem("ThermalExpansion", "material");

            //Machines
            GameRegistry.addRecipe(new ItemStack(ThermalScience.blockWatermill), "MGM", "GFG", "MGM", 'G', new ItemStack(GameRegistry.findItem("ThermalFoundation", "material"), 1, 137), 'M', ItemMaterial.getMaterial("Motor"), 'F', basicMachineFrame);
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCompressor, "MPM", "IFI", "MPM", 'M', ItemMaterial.getMaterial("Motor"), 'P', Blocks.piston, 'I', Blocks.iron_bars, 'F', basicMachineFrame));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockWiremill, "MII", "PFT", "MII", 'M', ItemMaterial.getMaterial("Motor"), 'P', Blocks.piston, 'T', ItemMaterial.getMaterial("Tube"), 'I', Items.iron_ingot, 'F', basicMachineFrame));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCarbothermicFurnace, "III", "ISI", "III", 'I', "ingotInvar", 'S', new ItemStack(GameRegistry.findItem("ThermalExpansion", "Machine"), 1, 3)));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCentrifuge, "IMI", "BFB", "IMI", 'I', "ingotInvar", 'M', ItemMaterial.getMaterial("Motor"), 'B', Items.bucket, 'F', basicMachineFrame));
            GameRegistry.addRecipe(new ItemStack(ThermalScience.blockEnergyRelay), "ICI", "TFR","ICI", 'C', new ItemStack(teMaterial, 1, 3), 'T', new ItemStack(teMaterial, 1, 2), 'R', new ItemStack(teMaterial, 1, 1), 'F', basicMachineFrame, 'I', Items.iron_ingot);

            //Items
            GameRegistry.addShapelessRecipe(new ItemStack(ThermalScience.itemPortableCompressor), new ItemStack(ThermalScience.blockCompressor), new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 4));
        }

        //ProjectRed
        if(Loader.isModLoaded("ProjRed|Core") && Loader.isModLoaded("ProjRed|Exploration")) {
            Item projRedGem = GameRegistry.findItem("ProjRed|Core", "projectred.core.part");
            Item projRedBlock = GameRegistry.findItem("ProjRed|Exploration", "projectred.exploration.stone");

            addCompressorRecipe(new Object[]{new ItemStack(projRedGem, 9, 37)}, new Object[]{new ItemStack(projRedBlock, 1, 5)}, 1000, true);
            addCompressorRecipe(new Object[]{new ItemStack(projRedGem, 9, 38)}, new Object[]{new ItemStack(projRedBlock, 1, 6)}, 1000, true);
            addCompressorRecipe(new Object[]{new ItemStack(projRedGem, 9, 39)}, new Object[]{new ItemStack(projRedBlock, 1, 7)}, 1000, true);
        }

        //AE2
        if(Loader.isModLoaded("appliedenergistics2")){
            Item ae2Material = GameRegistry.findItem("appliedenergistics2", "item.ItemMultiMaterial");

            addCompressorRecipe(new Object[]{new ItemStack(ae2Material, 4, 0)}, new Object[]{new ItemStack(GameRegistry.findItem("appliedenergistics2", "tile.BlockQuartz"))}, 1000, true);
            addCompressorRecipe(new Object[]{new ItemStack(ae2Material, 8, 10)}, new Object[]{new ItemStack(GameRegistry.findItem("appliedenergistics2", "tile.BlockQuartz"))}, 1000, false);

            addCompressorRecipe(new Object[]{new ItemStack(ae2Material, 4, 7)}, new Object[]{new ItemStack(GameRegistry.findItem("appliedenergistics2", "tile.BlockFluix"))}, 1000, false);
            addCompressorRecipe(new Object[]{new ItemStack(ae2Material, 8, 12)}, new Object[]{new ItemStack(GameRegistry.findItem("appliedenergistics2", "tile.BlockFluix"))}, 1000, false);
        }

        //ExtraUtilities
        if (Loader.isModLoaded("ExtraUtilities")) {
            Item item = GameRegistry.findItem("ExtraUtilities", "cobblestone_compressed");

            for (int i = 0; i < 16; i++) {
                ItemStack input;

                if (i == 0) {
                    input = new ItemStack(Blocks.cobblestone, 9);
                } else if (i == 8) {
                    input = new ItemStack(Blocks.dirt, 9);
                } else if (i == 12) {
                    input = new ItemStack(Blocks.gravel, 9);
                } else if (i == 14) {
                    input = new ItemStack(Blocks.sand, 9);
                } else {
                    input = new ItemStack(item, 9, i - 1);
                }

                addCompressorRecipe(new Object[]{input}, new Object[]{new ItemStack(item, 1, i)}, 1000, true);
            }
        }
    }
}
