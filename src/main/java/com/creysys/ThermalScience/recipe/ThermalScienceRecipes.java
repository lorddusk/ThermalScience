package com.creysys.ThermalScience.recipe;

import cofh.thermalexpansion.plugins.nei.handlers.NEIRecipeWrapper;
import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cofh.thermalexpansion.util.crafting.SmelterManager;
import cofh.thermalfoundation.item.TFItems;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceConfig;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.compat.nei.recipeHandler.RecipeHandlerWorld;
import com.creysys.ThermalScience.recipe.recipe.RecipeAssemblingMachine;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import com.creysys.ThermalScience.item.ItemDust;
import com.creysys.ThermalScience.item.ItemMaterial;
import com.creysys.ThermalScience.item.ItemPortableCompressor;
import com.creysys.ThermalScience.recipe.recipe.RecipeCompressor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import crazypants.enderio.machine.crusher.CrusherRecipeManager;
import crazypants.enderio.machine.recipe.Recipe;
import crazypants.enderio.machine.recipe.RecipeOutput;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 2/1/2015.
 */
public class ThermalScienceRecipes {

    public static ArrayList<ThermalScienceRecipe> recipesCarbothermicFurnace;
    public static ArrayList<ThermalScienceRecipe> recipesCentrifuge;
    public static ArrayList<ThermalScienceRecipe> recipesCompressor;
    public static ArrayList<ThermalScienceRecipe> recipesWiremill;
    public static ArrayList<ThermalScienceRecipe> recipesAssemblingMachine;
    public static ArrayList<ThermalScienceRecipe> recipesMagnetizer;
    public static ArrayList<ThermalScienceRecipe> recipesExtractor;

    public static Item teMachine;
    public static Item teFrame;
    public static Item teCapacitor;
    public static Item teMaterial;
    public static Item teCell;
    public static Item teTesseract;
    public static Item teDynamo;
    public static Item tfMaterial;

    public static void preInitialize() {
        recipesCarbothermicFurnace = new ArrayList();
        recipesCentrifuge = new ArrayList();
        recipesCompressor = new ArrayList();
        recipesWiremill = new ArrayList();
        recipesAssemblingMachine = new ArrayList();
        recipesMagnetizer = new ArrayList();
        recipesExtractor = new ArrayList();
    }

    public static void initialize() {
        //Materials
        //Insulated wires
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireCopper, ItemMaterial.wireCopper, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireTin, ItemMaterial.wireTin, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireIron, ItemMaterial.wireIron, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireGold, ItemMaterial.wireGold, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireSilver, ItemMaterial.wireSilver, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireLead, ItemMaterial.wireLead, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireNickel, ItemMaterial.wireNickel, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWirePlatinum, ItemMaterial.wirePlatinum, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireMithril, ItemMaterial.wireMithril, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireElectrum, ItemMaterial.wireElectrum, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireInvar, ItemMaterial.wireInvar, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireBronze, ItemMaterial.wireBronze, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireSignalum, ItemMaterial.wireSignalum, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireLumium, ItemMaterial.wireLumium, "itemRubber"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemMaterial.insulatedWireEnderium, ItemMaterial.wireEnderium, "itemRubber"));

        GameRegistry.addRecipe(new ShapedOreRecipe(ItemMaterial.tube, "RIR", "___", "RIR", 'I', "ingotInvar", 'R', Items.iron_ingot));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemMaterial.wireCopper, "CC", 'C', "ingotCopper"));
        GameRegistry.addRecipe(ThermalScienceUtil.setStack(ItemMaterial.coil, 4), "WWW", "W_W", "WWW", 'W', ItemMaterial.insulatedWireCopper);
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemMaterial.magnet, "RMG", 'R', "dyeRed", 'G', "dyeGreen", 'M', ItemMaterial.ingotMagneticIron));
        GameRegistry.addRecipe(ItemMaterial.motor, "CMC", 'C', ItemMaterial.coil, 'M', ItemMaterial.magnet);
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemMaterial.inductionCoil, "CIC", 'C', ItemMaterial.coil, 'I', "ingotIron"));

        GameRegistry.addRecipe(new ShapedOreRecipe(ItemMaterial.circuitBasic, "WWW", "RCR", "WWW", 'W', ItemMaterial.insulatedWireCopper, 'R', Items.redstone, 'C', "ingotCopper"));

        GameRegistry.addShapedRecipe(ItemMaterial.fluidTransmitter, "_E_", "_T_", "_B_", 'E', ItemMaterial.ingotMagneticEnderium, 'T', Blocks.redstone_torch, 'B', Items.bucket);
        GameRegistry.addShapedRecipe(ItemMaterial.fluidReceiver, "_B_", "_T_", "_E_", 'E', ItemMaterial.ingotMagneticEnderium, 'T', Blocks.redstone_torch, 'B', Items.bucket);
        GameRegistry.addShapedRecipe(new ItemStack(ThermalScience.blockGravitationalTank), "CWT", "WDW", "RWC", 'C', ItemMaterial.circuitResonant, 'W', ItemMaterial.insulatedWireEnderium, 'T', ItemMaterial.fluidTransmitter, 'R', ItemMaterial.fluidReceiver, 'D', ItemMaterial.denseMatterBall);

        //Tweaks
        GameRegistry.addRecipe(new ShapedOreRecipe(Items.glowstone_dust, "RGR", "GRG", "RGR", 'G', "dustGold", 'R', Items.redstone));

        //Carbothermic Furnace
        addRecipe(recipesCarbothermicFurnace, new Object[]{Items.coal, Blocks.sand}, new Object[]{"itemSilicon"}, 16000);
        //Ore Processing
        addRecipe(recipesCarbothermicFurnace, new Object[]{"oreIron", Items.coal}, new Object[]{"ingotIron,2"}, 7000);
        addRecipe(recipesCarbothermicFurnace, new Object[]{"oreGold", Items.coal}, new Object[]{"ingotGold,2"}, 7000);
        addRecipe(recipesCarbothermicFurnace, new Object[]{"oreAdamantium", Items.coal}, new Object[]{"ingotAdamantium,2"}, 7000);
        addRecipe(recipesCarbothermicFurnace, new Object[]{"oreAluminium", Items.coal}, new Object[]{"ingotAluminium,2"}, 7000);
        addRecipe(recipesCarbothermicFurnace, new Object[]{"oreCopper", Items.coal}, new Object[]{"ingotCopper,2"}, 7000);
        addRecipe(recipesCarbothermicFurnace, new Object[]{"oreLead", Items.coal}, new Object[]{"ingotLead,2"}, 7000);
        addRecipe(recipesCarbothermicFurnace, new Object[]{"oreSilver", Items.coal}, new Object[]{"ingotSilver,2"}, 7000);
        addRecipe(recipesCarbothermicFurnace, new Object[]{"oreTin", Items.coal}, new Object[]{"ingotTin,2"}, 7000);


        //Centrifuge
        addRecipe(recipesCentrifuge, new Object[]{Items.magma_cream}, new Object[]{Items.blaze_powder, Items.slime_ball}, 30000);
        addRecipe(recipesCentrifuge, new Object[]{Items.ender_eye}, new Object[]{Items.ender_pearl, Items.blaze_powder}, 30000);
        addRecipe(recipesCentrifuge, new Object[]{Items.blaze_powder}, new Object[]{new ItemStack(Items.redstone, 2), Items.glowstone_dust}, 30000);
        addRecipe(recipesCentrifuge, new Object[]{new ItemStack(Blocks.soul_sand, 4)}, new Object[]{new ItemStack(Blocks.sand, 2), "dustSaltpeter"}, 30000);
        addRecipe(recipesCentrifuge, new Object[]{"dustDirtyNetherrack,2"}, new Object[]{"dustNetherrack,2", "dustSulfur"}, 10000);
        addRecipe(recipesCentrifuge, new Object[]{"dustNetherrack,16"}, new Object[]{Items.redstone, "dustSulfur,4", "dustCoal", "nuggetGold"}, 80000);
        addRecipe(recipesCentrifuge, new Object[]{new ItemStack(Items.gunpowder, 2)}, new Object[]{"dustCoal", "dustSaltpeter,2", "dustSulfur"}, 10000);
        addRecipe(recipesCentrifuge, new Object[]{new ItemStack(Items.glowstone_dust, 16)}, new Object[]{new ItemStack(Items.redstone, 8), "dustGold,8"}, 240000);
        addRecipe(recipesCentrifuge, new Object[]{new ItemStack(Items.golden_apple, 1, 0)}, new Object[]{new ItemStack(Items.gold_ingot, 8)}, 80000);
        addRecipe(recipesCentrifuge, new Object[]{new ItemStack(Items.golden_apple, 1, 1)}, new Object[]{new ItemStack(Items.gold_ingot, 64)}, 300000);


        //Alloy Seperating
        addRecipe(recipesCentrifuge, new Object[]{"dustBrass,4"}, new Object[]{"dustCopper,3", "dustZinc"}, 18000);
        addRecipe(recipesCentrifuge, new Object[]{"dustBronze,4"}, new Object[]{"dustCopper,3", "dustTin"}, 18000);
        addRecipe(recipesCentrifuge, new Object[]{"dustElectrum,2"}, new Object[]{"dustSilver", "dustGold"}, 18000);
        addRecipe(recipesCentrifuge, new Object[]{"dustAluminiumBrass,4"}, new Object[]{"dustAluminium,3", "dustCopper"}, 18000);
        addRecipe(recipesCentrifuge, new Object[]{"dustAlumite,6"}, new Object[]{"dustObsidian,2", "dustIron,4", "dustAluminium,10"}, 18000);
        addRecipe(recipesCentrifuge, new Object[]{"dustManyullyn"}, new Object[]{"dustCobalt", "dustArdite"}, 18000);
        addRecipe(recipesCentrifuge, new Object[]{"dustInvar,3"}, new Object[]{"dustIron,2", "dustNickel"}, 18000);

        //Compressor
        addCompressorRecipe(new Object[]{new ItemStack(Items.blaze_powder, 5)}, new Object[]{Items.blaze_rod}, 10000, false);
        addCompressorRecipe(new Object[]{"dustCoal,64"}, new Object[]{Items.diamond}, 100000, false);
        addCompressorRecipe(new Object[]{new ItemStack(Blocks.netherrack, 2)}, new Object[]{Blocks.nether_brick}, 10000, false);
        addCompressorRecipe(new Object[]{new ItemStack(Blocks.sand, 2)}, new Object[]{Blocks.sandstone}, 10000, false);

        //Portable recipes
        addCompressorRecipe(new Object[]{new ItemStack(Items.coal, 9)}, new Object[]{Blocks.coal_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.diamond, 9)}, new Object[]{Blocks.diamond_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.redstone, 9)}, new Object[]{Blocks.redstone_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.dye, 9, 4)}, new Object[]{Blocks.lapis_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.emerald, 9)}, new Object[]{Blocks.emerald_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.wheat, 9)}, new Object[]{Blocks.hay_block}, 1000, true);

        //Wiremill
        addRecipe(recipesWiremill, new Object[]{"ingotCopper"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireCopper, 2)}, 8000);
        addRecipe(recipesWiremill, new Object[]{"ingotTin"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireTin, 2)}, 4000);
        addRecipe(recipesWiremill, new Object[]{"ingotIron"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireIron, 2)}, 1000);
        addRecipe(recipesWiremill, new Object[]{"ingotGold"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireGold, 2)}, 50000);
        addRecipe(recipesWiremill, new Object[]{"ingotSilver"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireSilver, 2)}, 40000);
        addRecipe(recipesWiremill, new Object[]{"ingotLead"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireLead, 2)}, 8000);
        addRecipe(recipesWiremill, new Object[]{"ingotNickel"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireNickel, 2)}, 8000);
        addRecipe(recipesWiremill, new Object[]{"ingotPlatinum"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wirePlatinum, 2)}, 60000);
        addRecipe(recipesWiremill, new Object[]{"ingotMithril"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireMithril, 2)}, 50000);
        addRecipe(recipesWiremill, new Object[]{"ingotElectrum"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireElectrum, 2)}, 20000);
        addRecipe(recipesWiremill, new Object[]{"ingotInvar"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireInvar, 2)}, 8000);
        addRecipe(recipesWiremill, new Object[]{"ingotBronze"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireBronze, 2)}, 20000);
        addRecipe(recipesWiremill, new Object[]{"ingotSignalum"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireSignalum, 2)}, 8000);
        addRecipe(recipesWiremill, new Object[]{"ingotLumium"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireLumium, 2)}, 8000);
        addRecipe(recipesWiremill, new Object[]{"ingotEnderium"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireEnderium, 2)}, 100000);

        //Assembling Machine
        addAssemblingMachineRecipe(new Object[]{ThermalScienceUtil.setStack(ItemMaterial.insulatedWireCopper, 3), "ingotCopper"}, new Object[]{ItemMaterial.circuitBasic}, 8000, "redstone", 200);
        addAssemblingMachineRecipe(new Object[]{ThermalScienceUtil.setStack(ItemMaterial.insulatedWireInvar, 6), "ingotIron"}, new Object[]{ItemMaterial.circuitHardened}, 8000, "redstone", 400);
        addAssemblingMachineRecipe(new Object[]{ThermalScienceUtil.setStack(ItemMaterial.insulatedWireElectrum, 6), "ingotGold"}, new Object[]{ItemMaterial.circuitReinforced}, 20000, "redstone", 800);
        addAssemblingMachineRecipe(new Object[]{ThermalScienceUtil.setStack(ItemMaterial.insulatedWireEnderium, 6), Items.diamond}, new Object[]{ItemMaterial.circuitResonant}, 100000, "redstone", 1000);

        //Magnetizer
        addRecipe(recipesMagnetizer, new Object[]{"ingotIron"}, new Object[]{ItemMaterial.ingotMagneticIron}, 8000);
        addRecipe(recipesMagnetizer, new Object[]{"ingotTin"}, new Object[]{ItemMaterial.ingotMagneticTin}, 12000);
        addRecipe(recipesMagnetizer, new Object[]{"ingotCopper"}, new Object[]{ItemMaterial.ingotMagneticCopper}, 18000);
        addRecipe(recipesMagnetizer, new Object[]{"ingotInvar"}, new Object[]{ItemMaterial.ingotMagneticInvar}, 30000);
        addRecipe(recipesMagnetizer, new Object[]{"ingotEnderium"}, new Object[]{ItemMaterial.ingotMagneticEnderium}, 60000);

        //Extractor
        addRecipe(recipesExtractor, new Object[]{"woodRubber"}, new Object[]{"itemRawRubber,3"}, 15000);
    }

    public static void postInitialize() {
        //Add these recipes after all items have been registered
        addCompressorOreRecipes();
        addModRecipes();

        ThermalScienceRecipeLoader.load(new File(ThermalScience.configDir, "recipes.txt"));
        ItemPortableCompressor.registerRecipes();
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

    public static void addUpgradableMachineRecipes(Block machine, Item tfMaterial) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machine, 1, 1), "IGI", "CMC", "I_I", 'I', "ingotInvar", 'G', new ItemStack(tfMaterial, 1, 135), 'M', new ItemStack(machine, 1, 0), 'C', ItemMaterial.circuitHardened));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machine, 1, 2), "IGI", "CMC", "IDI", 'I', "blockGlassHardened", 'G', new ItemStack(tfMaterial, 1, 138), 'M', new ItemStack(machine, 1, 1), 'D', Items.diamond, 'C', ItemMaterial.circuitReinforced));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machine, 1, 3), "IGI", "CMC", "IDI", 'I', "ingotSilver", 'G', new ItemStack(tfMaterial, 1, 140), 'M', new ItemStack(machine, 1, 2), 'D', Items.diamond, 'C', ItemMaterial.circuitResonant));
    }


    public static void addCompressorRecipe(Object[] input, Object[] output, int energy, boolean portable) {
        addRecipe(recipesCompressor, new RecipeCompressor(input, output, energy, portable));
    }

    public static void addAssemblingMachineRecipe(Object[] input, Object[] output, int energy, String fluid, int fluidAmount) {
        addRecipe(recipesAssemblingMachine, new RecipeAssemblingMachine(input, output, energy, fluid, fluidAmount));
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
    }

    public static void addModRecipes() {
        //Thermal Expansion
        if (Loader.isModLoaded("ThermalExpansion")) {

            teFrame = GameRegistry.findItem("ThermalExpansion", "Frame");
            teMachine = GameRegistry.findItem("ThermalExpansion", "Machine");
            teMaterial = GameRegistry.findItem("ThermalExpansion", "material");
            teCapacitor = GameRegistry.findItem("ThermalExpansion", "capacitor");
            teCell = GameRegistry.findItem("ThermalExpansion", "Cell");
            teTesseract = GameRegistry.findItem("ThermalExpansion", "Tesseract");
            teDynamo = GameRegistry.findItem("ThermalExpansion", "Dynamo");
            tfMaterial = GameRegistry.findItem("ThermalFoundation", "material");

            //Machines
            GameRegistry.addRecipe(new ItemStack(ThermalScience.blockWatermill), "MGM", "GCG", "MGM", 'G', new ItemStack(tfMaterial, 1, 128), 'M', ItemMaterial.motor, 'C', new ItemStack(teFrame, 1, 0));


            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCompressor, "CPM", "TFI", "CPM", 'M', ItemMaterial.motor, 'P', Blocks.piston, 'I', Blocks.iron_bars, 'F', teFrame, 'C', ItemMaterial.circuitHardened, 'T', ItemMaterial.insulatedWireTin));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockWiremill, "CWI", "PFT", "MWI", 'M', ItemMaterial.motor, 'P', Blocks.piston, 'T', ItemMaterial.tube, 'I', Items.iron_ingot, 'F', teFrame, 'C', ItemMaterial.circuitBasic, 'W', ItemMaterial.insulatedWireCopper));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCarbothermicFurnace, "_C_", "ISI", "_I_", 'I', "ingotInvar", 'S', new ItemStack(teMachine, 1, 3), 'C', ItemMaterial.circuitHardened));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCentrifuge, "IMI", "CFC", "IMI", 'I', ItemMaterial.insulatedWireElectrum, 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitHardened, 'F', teFrame));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockAssemblingMachine, "MDM", "CFC", "WCW", 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitBasic, 'F', teFrame, 'D', Items.diamond, 'W', ItemMaterial.insulatedWireTin));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockMagnetizer, "WCW", "TFT", "WCW", 'W', ItemMaterial.insulatedWireSilver, 'C', ItemMaterial.circuitHardened, 'F', teFrame, 'T', ItemMaterial.tube));

            GameRegistry.addRecipe(new ItemStack(ThermalScience.blockEnergyRelay), "IHI", "TWR", "ICI", 'C', new ItemStack(teMaterial, 1, 3), 'T', new ItemStack(teMaterial, 1, 2), 'R', new ItemStack(teMaterial, 1, 1), 'W', ItemMaterial.insulatedWireSilver, 'I', Items.iron_ingot, 'H', ItemMaterial.circuitBasic);

            addUpgradableMachineRecipes(ThermalScience.blockCompressor, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockWiremill, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockCarbothermicFurnace, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockCentrifuge, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockEnergyRelay, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockAssemblingMachine, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockMagnetizer, tfMaterial);

            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockTeleporterController, "ICI", "EFE", "ICI", 'I', "ingotInvar", 'C', new ItemStack(teCapacitor, 1, 5), 'E', "ingotEnderium", 'F', new ItemStack(teFrame, 1, 3), 'C', ItemMaterial.circuitResonant));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockTeleporterPowerTap, "IEI", "WWC", "IEI", 'I', "ingotInvar", 'E', "ingotEnderium", 'C', new ItemStack(teMaterial, 1, 1), 'W', ItemMaterial.insulatedWireEnderium));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ThermalScience.blockTeleporterWall, 4), "IEI", "EIE", "IEI", 'I', "ingotInvar", 'E', "ingotEnderium"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ThermalScience.itemTeleporterDestinationCard, 2), "W_W", "EPE", "_C_", 'W', ItemMaterial.wireCopper, 'E', "ingotEnderium", 'P', Items.paper, 'C', ItemMaterial.circuitResonant));

            //Items
            //Portable Compressor
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger(ThermalScienceNBTTags.Tier, 0);
            ItemStack rein = new ItemStack(ThermalScience.itemPortableCompressor);
            rein.setTagCompound(compound);

            compound = new NBTTagCompound();
            compound.setInteger(ThermalScienceNBTTags.Tier, 1);
            ItemStack reso = new ItemStack(ThermalScience.itemPortableCompressor);
            reso.setTagCompound(compound);

            GameRegistry.addShapedRecipe(rein, "HCR", "WMW", "___", 'M', new ItemStack(ThermalScience.blockCompressor, 1, 2), 'C', new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 4), 'H', ItemMaterial.circuitHardened, 'R', ItemMaterial.circuitReinforced, 'W', ItemMaterial.insulatedWireElectrum);
            GameRegistry.addShapedRecipe(reso, "RCS", "WMW", "___", 'M', rein, 'C', new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 5), 'R', ItemMaterial.circuitReinforced, 'S', ItemMaterial.circuitResonant, 'W', ItemMaterial.insulatedWireSignalum);

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ThermalScience.itemMachineUpgradeKit, 1, 0), "IGI", "C_C", "I_I", 'I', "ingotInvar", 'G', new ItemStack(tfMaterial, 1, 135), 'C', ItemMaterial.circuitHardened));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ThermalScience.itemMachineUpgradeKit, 1, 1), "IGI", "C_C", "IDI", 'I', "blockGlassHardened", 'G', new ItemStack(tfMaterial, 1, 138), 'D', Items.diamond, 'C', ItemMaterial.circuitReinforced));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ThermalScience.itemMachineUpgradeKit, 1, 2), "IGI", "C_C", "IDI", 'I', "ingotSilver", 'G', new ItemStack(tfMaterial, 1, 140), 'D', Items.diamond, 'C', ItemMaterial.circuitResonant));
        }

        //ProjectRed
        if (Loader.isModLoaded("ProjRed|Core") && Loader.isModLoaded("ProjRed|Exploration")) {
            Item projRedGem = GameRegistry.findItem("ProjRed|Core", "projectred.core.part");
            Item projRedBlock = GameRegistry.findItem("ProjRed|Exploration", "projectred.exploration.stone");

            addCompressorRecipe(new Object[]{new ItemStack(projRedGem, 9, 37)}, new Object[]{new ItemStack(projRedBlock, 1, 5)}, 1000, true);
            addCompressorRecipe(new Object[]{new ItemStack(projRedGem, 9, 38)}, new Object[]{new ItemStack(projRedBlock, 1, 6)}, 1000, true);
            addCompressorRecipe(new Object[]{new ItemStack(projRedGem, 9, 39)}, new Object[]{new ItemStack(projRedBlock, 1, 7)}, 1000, true);
        }

        //AE2
        if (Loader.isModLoaded("appliedenergistics2")) {
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

                addCompressorRecipe(new Object[]{input}, new Object[]{new ItemStack(item, 1, i)}, 420, true);
            }

            GameRegistry.addShapedRecipe(ItemMaterial.denseMatterBall, "_B_", "BUB", "_B_", 'B', GameRegistry.findItem("ExtraUtilities", "bedrockiumIngot"), 'U', GameRegistry.findItem("ExtraUtilities", "unstableingot"));
        }
        else {
            GameRegistry.addShapedRecipe(ItemMaterial.denseMatterBall, "_C_", "CNC", "_C_", 'C', Blocks.cobblestone, 'N', Items.nether_star);
        }
    }

    public static void removeRecipes() {
        if (Loader.isModLoaded("ThermalExpansion")) {

            PulverizerManager.removeRecipe(new ItemStack(Blocks.netherrack));
            PulverizerManager.addRecipe(2400, new ItemStack(Blocks.netherrack), ItemDust.getDust("DirtyNetherrack"));

            if (ThermalScienceConfig.recipeOverrideSilicon) {
                PulverizerManager.removeRecipe(new ItemStack(Blocks.sand));
            }
            if(ThermalScienceConfig.recipeOverrideNiter){
                PulverizerManager.removeRecipe(new ItemStack(Blocks.sandstone));
                PulverizerManager.addRecipe(3200, new ItemStack(Blocks.sand), new ItemStack(Blocks.sand, 2));
            }
            if(ThermalScienceConfig.recipeOverrideSulfur){
                SmelterManager.removeRecipe(new ItemStack(Blocks.netherrack, 4), new ItemStack(Blocks.soul_sand));
                SmelterManager.addRecipe(4000, new ItemStack(Blocks.netherrack, 4), new ItemStack(Blocks.soul_sand), new ItemStack(Blocks.nether_brick, 2));

                PulverizerManager.removeRecipe(new ItemStack(Items.coal));
                PulverizerManager.addRecipe(2400, new ItemStack(Items.coal, 1), TFItems.dustCoal);

                PulverizerManager.removeRecipe(new ItemStack(Blocks.quartz_ore));
                PulverizerManager.addRecipe(2400, new ItemStack(Blocks.quartz_ore), new ItemStack(Items.quartz, 2));

                PulverizerManager.removeRecipe(new ItemStack(Items.blaze_rod));
                PulverizerManager.addTERecipe(1600, new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_powder, 4));
            }

            if (ThermalScienceConfig.recipeOverrideMachines) {
                //Remove all machine crafting
                ArrayList<Integer> excluded = new ArrayList<Integer>();
                excluded.add(7);
                excluded.add(8);
                excluded.add(9);
                excluded.add(10);
                excluded.add(11);


                List list = CraftingManager.getInstance().getRecipeList();
                List remove = new ArrayList();

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) instanceof NEIRecipeWrapper) {
                        NEIRecipeWrapper recipe = (NEIRecipeWrapper) list.get(i);
                        if (recipe.getRecipeType() == NEIRecipeWrapper.RecipeType.MACHINE && !excluded.contains(recipe.getRecipeOutput().getItemDamage())) {
                            remove.add(recipe);
                        }
                    }
                }

                list.removeAll(remove);


                //Redstone Furnace
                GameRegistry.addShapedRecipe(new ItemStack(teMachine, 1, 0), "cCc", "FMF", "ORO", 'C', ItemMaterial.circuitBasic, 'M', teFrame, 'F', Blocks.furnace, 'O', ItemMaterial.inductionCoil, 'R', new ItemStack(teMaterial, 1, 1), 'c', ItemMaterial.insulatedWireCopper);
                //Pulverizer
                GameRegistry.addShapedRecipe(new ItemStack(teMachine, 1, 1), "WCW", "cMc", "FGF", 'W', ItemMaterial.insulatedWireElectrum, 'C', ItemMaterial.circuitReinforced, 'c', new ItemStack(ThermalScience.blockCompressor), 'M', ItemMaterial.motor, 'F', Items.flint, 'G', new ItemStack(tfMaterial, 1, 128));
                //Sawmill
                GameRegistry.addShapedRecipe(new ItemStack(teMachine, 1, 2), "GAG", "MFM", "CcC", 'G', new ItemStack(tfMaterial, 1, 136), 'A', Items.iron_axe, 'M', ItemMaterial.motor, 'F', teFrame, 'C', ItemMaterial.circuitHardened, 'c', ItemMaterial.insulatedWireInvar);
                //Induction Smelter
                GameRegistry.addShapedRecipe(new ItemStack(teMachine, 1, 3), "cCc", "FMF", "iRi", 'C', ItemMaterial.circuitBasic, 'M', teFrame, 'F', teMachine, 'i', ItemMaterial.insulatedWireIron, 'R', new ItemStack(teMaterial, 1, 1), 'c', ItemMaterial.insulatedWireCopper);
                //Magma Crucible
                GameRegistry.addShapedRecipe(new ItemStack(teMachine, 1, 4), "B_B", "IFI", "WCW", 'B', Items.bucket, 'I', ItemMaterial.inductionCoil, 'F', teFrame, 'W', ItemMaterial.insulatedWireInvar, 'C', ItemMaterial.circuitReinforced);
                //Fluid Transposer
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teMachine, 1, 5), "MGB", "CF_", "WIg", 'M', ItemMaterial.motor, 'G', new ItemStack(tfMaterial, 1, 12), 'B', Items.bucket, 'C', ItemMaterial.circuitReinforced, 'F', teFrame, 'W', ItemMaterial.insulatedWireGold, 'I', ItemMaterial.ingotMagneticInvar, 'g', "blockGlass"));
                //Glacial Precipitator
                GameRegistry.addShapedRecipe(new ItemStack(teMachine, 1, 6), "WWC", "cFB", "WWI", 'W', ItemMaterial.insulatedWireCopper, 'C', new ItemStack(ThermalScience.blockCompressor, 1, 0), 'c', ItemMaterial.circuitBasic, 'F', teFrame, 'B', Items.bucket, 'I', ItemMaterial.ingotMagneticIron);
            }

            if(ThermalScienceConfig.recipeOverrideEnergyCells){
                //Energy Cells
                for (int i = 0; i < 5; i++) {
                    ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(teCell, 1, i), true);
                }

                //Leadstone Energy Cell
                GameRegistry.addShapedRecipe(new ItemStack(teCell, 1, 1), "WTW", "CEC", "WRW", 'W', ItemMaterial.insulatedWireCopper, 'T', new ItemStack(teMaterial, 1, 2), 'C', ItemMaterial.circuitBasic, 'E', new ItemStack(teFrame, 1, 4), 'R', new ItemStack(teMaterial, 1, 1));
                //Hardened Energy Cell
                GameRegistry.addShapedRecipe(new ItemStack(teCell, 1, 2), "WTW", "CEC", "WRW", 'W', ItemMaterial.insulatedWireInvar, 'T', new ItemStack(teMaterial, 1, 2), 'C', ItemMaterial.circuitHardened, 'E', new ItemStack(teFrame, 1, 5), 'R', new ItemStack(teMaterial, 1, 1));
                //Redstone Energy Cell
                GameRegistry.addShapedRecipe(new ItemStack(teCell, 1, 3), "WTW", "CEC", "WRW", 'W', ItemMaterial.insulatedWireElectrum, 'T', new ItemStack(teMaterial, 1, 2), 'C', ItemMaterial.circuitReinforced, 'E', new ItemStack(teFrame, 1, 7), 'R', new ItemStack(teMaterial, 1, 1));
                //Resonant Energy Cell
                GameRegistry.addShapedRecipe(new ItemStack(teCell, 1, 4), "WTW", "CEC", "WRW", 'W', ItemMaterial.insulatedWireEnderium, 'T', new ItemStack(teMaterial, 1, 2), 'C', ItemMaterial.circuitResonant, 'E', new ItemStack(teFrame, 1, 9), 'R', new ItemStack(teMaterial, 1, 1));
            }

            if(ThermalScienceConfig.recipeOverrideTesseract) {
                //Tesseract
                ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(teTesseract), true);
                GameRegistry.addShapedRecipe(new ItemStack(teTesseract), "WCT", "MFM", "RCW", 'W', ItemMaterial.insulatedWireEnderium, 'C', ItemMaterial.circuitResonant, 'T', new ItemStack(teMaterial, 1, 2), 'M', ItemMaterial.ingotMagneticEnderium, 'F', new ItemStack(teFrame, 1, 11), 'R', new ItemStack(teMaterial, 1, 1));
            }

            if (ThermalScienceConfig.recipeOverrideGunpowder) {
                ThermalScienceUtil.removeShapelessOreRecipeFor(new ItemStack(Items.gunpowder), "dustCharcoal");
            }

            if(ThermalScienceConfig.recipeOverrideServo){
                ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(teMaterial), true);
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teMaterial), "_I_", "GCG", "_I_", 'I', "ingotIron", 'G', "blockGlass", 'C', ItemMaterial.circuitHardened));
            }

            if(ThermalScienceConfig.recipeOverrideDynamos){
                for(int i = 0; i < 5; i++){
                    ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(teDynamo, 1, i), true);
                }

                //Steam Dynamo
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teDynamo, 1, 0), "_T_", "GIG", "MCM", 'T', new ItemStack(teMaterial, 1, 2), 'G', new ItemStack(tfMaterial, 1, 128), 'I', "ingotCopper", 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitBasic));
                //Magmatic Dynamo
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teDynamo, 1, 1), "_T_", "GIG", "MCM", 'T', new ItemStack(teMaterial, 1, 2), 'G', new ItemStack(tfMaterial, 1, 136), 'I', "ingotInvar", 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitReinforced));
                //Compression Dynamo
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teDynamo, 1, 2), "_T_", "GIG", "MCM", 'T', new ItemStack(teMaterial, 1, 2), 'G', new ItemStack(tfMaterial, 1, 129), 'I', "ingotTin", 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitHardened));
                //Steam Dynamo
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teDynamo, 1, 3), "_T_", "GIG", "MCM", 'T', new ItemStack(teMaterial, 1, 2), 'G', new ItemStack(tfMaterial, 1, 137), 'I', "ingotBronze", 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitHardened));
                //Steam Dynamo
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teDynamo, 1, 4), "_T_", "GIG", "MCM", 'T', new ItemStack(teMaterial, 1, 2), 'G', new ItemStack(tfMaterial, 1, 135), 'I', "ingotElectrum", 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitReinforced));
            }
        }

        if (Loader.isModLoaded("ThermalDynamics")) {
            Item dynamics0 = GameRegistry.findItem("ThermalDynamics", "ThermalDynamics_0");
            Item dynamics16 = GameRegistry.findItem("ThermalDynamics", "ThermalDynamics_16");
            Item dynamics32 = GameRegistry.findItem("ThermalDynamics", "ThermalDynamics_32");

            if (ThermalScienceConfig.recipeOverrideDynamics) {
                //Fluxduct
                ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(dynamics0, 1, 0));
                ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(dynamics0, 1, 1));
                ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(dynamics0, 1, 3));
                ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(dynamics0, 1, 5));

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics0, 3, 0), "RRR", "WGW", "RRR", 'R', Items.redstone, 'W', ItemMaterial.insulatedWireLead, 'G', "blockGlass"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics0, 3, 1), "RRR", "WGW", "RRR", 'R', Items.redstone, 'W', ItemMaterial.insulatedWireInvar, 'G', "blockGlass"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics0, 3, 3), "WGW", 'W', ItemMaterial.insulatedWireElectrum, 'G', "blockGlass"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics0, 3, 5), "WGW", 'W', ItemMaterial.insulatedWireEnderium, 'G', "blockGlass"));


                //Fluiduct
                ThermalScienceUtil.removeShapedOreRecipeFor(new ItemStack(dynamics16, 6, 0), "ingotCopper");
                ThermalScienceUtil.removeShapedOreRecipeFor(new ItemStack(dynamics16, 6, 1), "ingotCopper");
                ThermalScienceUtil.removeShapedOreRecipeFor(new ItemStack(dynamics16, 6, 2), "ingotInvar");
                ThermalScienceUtil.removeShapedOreRecipeFor(new ItemStack(dynamics16, 6, 3), "ingotInvar");

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics16, 6, 0), "CGM", 'C', "ingotCopper", 'G', "blockGlassHardened", 'M', ItemMaterial.ingotMagneticCopper));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics16, 6, 1), "CGM", 'C', "ingotCopper", 'G', "ingotLead", 'M', ItemMaterial.ingotMagneticCopper));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics16, 6, 2), "IGM", 'I', "ingotInvar", 'G', "blockGlassHardened", 'M', ItemMaterial.ingotMagneticInvar));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics16, 6, 3), "IGM", 'I', "ingotInvar", 'G', "ingotLead", 'M', ItemMaterial.ingotMagneticInvar));


                //Itemduct
                ThermalScienceUtil.removeShapedOreRecipeFor(new ItemStack(dynamics32, 1, 0), "ingotTin");
                ThermalScienceUtil.removeShapedOreRecipeFor(new ItemStack(dynamics32, 1, 1), "ingotTin");
                ThermalScienceUtil.removeShapelessOreRecipeFor(new ItemStack(dynamics32, 1, 4), "ingotEnderium");
                ThermalScienceUtil.removeShapelessOreRecipeFor(new ItemStack(dynamics32, 1, 5), "ingotEnderium");
                ThermalScienceUtil.removeShapelessOreRecipeFor(new ItemStack(dynamics32, 1, 4), "nuggetEnderium");
                ThermalScienceUtil.removeShapelessOreRecipeFor(new ItemStack(dynamics32, 1, 5), "nuggetEnderium");

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics32, 6, 0), "TGM", 'T', "ingotTin", 'G', "blockGlassHardened", 'M', ItemMaterial.ingotMagneticTin));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics32, 6, 1), "TGM", 'T', "ingotTin", 'G', "ingotLead", 'M', ItemMaterial.ingotMagneticTin));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics32, 6, 4), "EGM", 'E', "ingotEnderium", 'G', "blockGlassHardened", 'M', ItemMaterial.ingotMagneticEnderium));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamics32, 6, 5), "EGM", 'E', "ingotEnderium", 'G', "ingotLead", 'M', ItemMaterial.ingotMagneticEnderium));
            }
        }

        if (Loader.isModLoaded("EnderIO")) {

            ArrayList<String> ores = new ArrayList<String>();
            if (ThermalScienceConfig.recipeOverrideSilicon) {
                ores.add("itemSilicon");
            }
            if(ThermalScienceConfig.recipeOverrideNiter){
                ores.add("dustSaltpeter");
            }
            if(ThermalScienceConfig.recipeOverrideSulfur){
                ores.add("dustSulfur");
            }

            //Remove silicon from every ender io sag mill recipe
            CrusherRecipeManager recipeManager = CrusherRecipeManager.getInstance();
            List<Recipe> recipes = recipeManager.getRecipes();
            List<Recipe> removeRecipes = new ArrayList<Recipe>();
            List<Recipe> addRecipes = new ArrayList<Recipe>();

            for (int i = 0; i < recipes.size(); i++) {
                RecipeOutput[] outputs = recipes.get(i).getOutputs();
                List<RecipeOutput> newOutputs = null;

                for (int j = 0; j < outputs.length; j++) {
                    ItemStack stack = outputs[j].getOutput();
                    if (oresContain(ores, stack)) {
                        newOutputs = new ArrayList<RecipeOutput>();
                        for (int k = 0; k < outputs.length; k++) {
                            if (!oresContain(ores, outputs[k].getOutput())) {
                                newOutputs.add(outputs[k]);
                            }
                        }

                        break;
                    }
                }

                if (newOutputs != null) {
                    removeRecipes.add(recipes.get(i));

                    if (newOutputs.size() > 0) {
                        addRecipes.add(new Recipe(recipes.get(i).getInputs(), ThermalScienceUtil.toArray(RecipeOutput.class, newOutputs), recipes.get(i).getEnergyRequired(), recipes.get(i).getBonusType()));
                    }
                }
            }

            recipes.removeAll(removeRecipes);
            recipes.addAll(addRecipes);
        }

    }

    public static boolean oresContain(List<String> ores, ItemStack stack){
        for(int i = 0; i< ores.size(); i++){
            if(ThermalScienceUtil.areOresIdentical(stack, ores.get(i))) {
                return true;
            }
        }
        return false;
    }
}
