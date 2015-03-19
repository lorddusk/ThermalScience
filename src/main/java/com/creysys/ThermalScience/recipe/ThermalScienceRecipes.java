package com.creysys.ThermalScience.recipe;

import cofh.api.modhelpers.ThermalExpansionHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceConfig;
import com.creysys.ThermalScience.recipe.recipe.RecipeAssemblingMachine;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import com.creysys.ThermalScience.item.ItemDust;
import com.creysys.ThermalScience.item.ItemMaterial;
import com.creysys.ThermalScience.item.ItemPortableCompressor;
import com.creysys.ThermalScience.recipe.recipe.RecipeCompressor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

/**
 * Created by Creysys on 2/1/2015.
 */
public class ThermalScienceRecipes {

    public static ArrayList<ThermalScienceRecipe> carbothermicFurnaceRecipes;
    public static ArrayList<ThermalScienceRecipe> centrifugeRecipes;
    public static ArrayList<ThermalScienceRecipe> compressorRecipes;
    public static ArrayList<ThermalScienceRecipe> wiremillRecipes;
    public static ArrayList<ThermalScienceRecipe> assemblingMachineRecipes;

    public static void preInitialize(){
        carbothermicFurnaceRecipes = new ArrayList<ThermalScienceRecipe>();
        centrifugeRecipes = new ArrayList<ThermalScienceRecipe>();
        compressorRecipes = new ArrayList<ThermalScienceRecipe>();
        wiremillRecipes = new ArrayList<ThermalScienceRecipe>();
        assemblingMachineRecipes = new ArrayList<ThermalScienceRecipe>();

        if (Loader.isModLoaded("ThermalExpansion")) {

            //Cant override recipes so i have to disable this one in config
            ThermalExpansionHelper.addPulverizerRecipe(2400, new ItemStack(Blocks.netherrack), ItemDust.getDust("DirtyNetherrack"));
        }

        if (Loader.isModLoaded("EnderIO")) {
            ThermalScienceUtil.addEnderIORecipe("sagmill", "Netherrack", "<itemStack modID=\"minecraft\" itemName=\"netherrack\" />", "<itemStack oreDictionary=\"dustDirtyNetherrack\" />", 2400);

            //Remove old silicon recipe
            //Doesnt work yet disable in config too
            if(ThermalScienceConfig.recipeOverrideSilicon) {
                ThermalScienceUtil.removeEnderIORecipe("sagmill", "EnderIO", "Silicon");
                ThermalScienceUtil.removeEnderIORecipe("sagmill", "EnderIO", "SiliconRedSand");
            }
        }
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
        GameRegistry.addRecipe(ItemMaterial.motor, "CIC", 'C', ItemMaterial.coil, 'I', Items.iron_ingot);

        GameRegistry.addRecipe(new ShapedOreRecipe(ItemMaterial.circuitBasic, "WWW", "RCR", "WWW", 'W', ItemMaterial.insulatedWireCopper, 'R', Items.redstone, 'C', "ingotCopper"));

        //Tweaks
        GameRegistry.addRecipe(new ShapedOreRecipe(Items.glowstone_dust, "RGR","GRG","RGR",'G', "dustGold", 'R', Items.redstone));

        //Carbothermic Furnace
        //Ore Processing
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreIron", Items.coal}, new Object[]{"ingotIron,2"}, 7000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreGold", Items.coal}, new Object[]{"ingotGold,2"}, 7000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreAdamantium", Items.coal}, new Object[]{"ingotAdamantium,2"}, 7000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreAluminium", Items.coal}, new Object[]{"ingotAluminium,2"}, 7000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreCopper", Items.coal}, new Object[]{"ingotCopper,2"}, 7000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreLead", Items.coal}, new Object[]{"ingotLead,2"}, 7000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreSilver", Items.coal}, new Object[]{"ingotSilver,2"}, 7000);
        addRecipe(carbothermicFurnaceRecipes, new Object[]{"oreTin", Items.coal}, new Object[]{"ingotTin,2"}, 7000);


        //Centrifuge
        addRecipe(centrifugeRecipes, new Object[]{Items.magma_cream}, new Object[]{Items.blaze_powder, Items.slime_ball}, 30000);
        addRecipe(centrifugeRecipes, new Object[]{Items.ender_eye}, new Object[]{Items.ender_pearl, Items.blaze_powder}, 30000);
        addRecipe(centrifugeRecipes, new Object[]{Items.blaze_powder}, new Object[]{new ItemStack(Items.redstone,2), Items.glowstone_dust}, 30000);
        addRecipe(centrifugeRecipes, new Object[]{new ItemStack(Blocks.soul_sand, 4)}, new Object[]{new ItemStack(Blocks.sand, 2), "dustSaltpeter"}, 30000);
        addRecipe(centrifugeRecipes, new Object[]{"dustDirtyNetherrack,2"}, new Object[]{"dustNetherrack,2", "dustSulfur"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{"dustNetherrack,16"}, new Object[]{Items.redstone, "dustSulfur,4", "dustCoal", "nuggetGold"}, 80000);
        addRecipe(centrifugeRecipes, new Object[]{new ItemStack(Items.gunpowder, 2)}, new Object[]{"dustCoal", "dustSaltpeter,2", "dustSulfur"}, 10000);
        addRecipe(centrifugeRecipes, new Object[]{new ItemStack(Items.glowstone_dust, 16)}, new Object[]{new ItemStack(Items.redstone, 8), "dustGold,8"}, 240000);


        //Alloy Seperating
        addRecipe(centrifugeRecipes, new Object[]{"dustBrass,4"}, new Object[]{"dustCopper,3", "dustZinc"}, 18000);
        addRecipe(centrifugeRecipes, new Object[]{"dustBronze,4"}, new Object[]{"dustCopper,3", "dustTin"}, 18000);
        addRecipe(centrifugeRecipes, new Object[]{"dustElectrum,2"}, new Object[]{"dustSilver", "dustGold"}, 18000);
        addRecipe(centrifugeRecipes, new Object[]{"dustAluminiumBrass,4"}, new Object[]{"dustAluminium,3", "dustCopper"}, 18000);
        addRecipe(centrifugeRecipes, new Object[]{"dustAlumite,6"}, new Object[]{"dustObsidian,2", "dustIron,4", "dustAluminium,10"}, 18000);
        addRecipe(centrifugeRecipes, new Object[]{"dustManyullyn"}, new Object[]{"dustCobalt", "dustArdite"}, 18000);
        addRecipe(centrifugeRecipes, new Object[]{"dustInvar,3"}, new Object[]{"dustIron,2", "dustNickel"}, 18000);

        //Compressor
        addCompressorRecipe(new Object[]{new ItemStack(Items.blaze_powder, 5)}, new Object[]{Items.blaze_rod}, 3200, false);
        addCompressorRecipe(new Object[]{"dustCoal,64"}, new Object[]{Items.diamond}, 100000, false);

        //Portable recipes
        addCompressorRecipe(new Object[]{new ItemStack(Items.coal, 9)}, new Object[]{Blocks.coal_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.diamond, 9)}, new Object[]{Blocks.diamond_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.redstone, 9)}, new Object[]{Blocks.redstone_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.dye, 9, 4)}, new Object[]{Blocks.lapis_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.emerald, 9)}, new Object[]{Blocks.emerald_block}, 1000, true);
        addCompressorRecipe(new Object[]{new ItemStack(Items.wheat, 9)}, new Object[]{Blocks.hay_block}, 1000, true);

        //Wiremill
        addRecipe(wiremillRecipes, new Object[]{"ingotCopper"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireCopper, 2)}, 8000);
        addRecipe(wiremillRecipes, new Object[]{"ingotTin"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireTin, 2)}, 4000);
        addRecipe(wiremillRecipes, new Object[]{"ingotIron"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireIron, 2)}, 1000);
        addRecipe(wiremillRecipes, new Object[]{"ingotGold"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireGold, 2)}, 50000);
        addRecipe(wiremillRecipes, new Object[]{"ingotSilver"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireSilver, 2)}, 40000);
        addRecipe(wiremillRecipes, new Object[]{"ingotLead"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireLead, 2)}, 8000);
        addRecipe(wiremillRecipes, new Object[]{"ingotNickel"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireNickel, 2)}, 8000);
        addRecipe(wiremillRecipes, new Object[]{"ingotPlatinum"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wirePlatinum, 2)}, 60000);
        addRecipe(wiremillRecipes, new Object[]{"ingotMithril"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireMithril, 2)}, 50000);
        addRecipe(wiremillRecipes, new Object[]{"ingotElectrum"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireElectrum, 2)}, 20000);
        addRecipe(wiremillRecipes, new Object[]{"ingotInvar"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireInvar, 2)}, 8000);
        addRecipe(wiremillRecipes, new Object[]{"ingotBronze"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireBronze, 2)}, 20000);
        addRecipe(wiremillRecipes, new Object[]{"ingotSignalum"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireSignalum, 2)}, 8000);
        addRecipe(wiremillRecipes, new Object[]{"ingotLumium"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireLumium, 2)}, 8000);
        addRecipe(wiremillRecipes, new Object[]{"ingotEnderium"}, new Object[]{ThermalScienceUtil.setStack(ItemMaterial.wireEnderium, 2)}, 100000);

        //Assembling Machine
        addAssemblingMachineRecipe(new Object[]{ThermalScienceUtil.setStack(ItemMaterial.insulatedWireCopper, 3), "ingotCopper"}, new Object[]{ItemMaterial.circuitBasic}, 8000, "redstone", 200);
        addAssemblingMachineRecipe(new Object[]{ThermalScienceUtil.setStack(ItemMaterial.insulatedWireInvar, 6), "ingotIron"}, new Object[]{ItemMaterial.circuitHardened}, 8000, "redstone", 400);
        addAssemblingMachineRecipe(new Object[]{ThermalScienceUtil.setStack(ItemMaterial.insulatedWireElectrum, 6), "ingotGold"}, new Object[]{ItemMaterial.circuitReinforced}, 20000, "redstone", 800);
        addAssemblingMachineRecipe(new Object[]{ThermalScienceUtil.setStack(ItemMaterial.insulatedWireEnderium, 6), Items.diamond}, new Object[]{ItemMaterial.circuitResonant}, 100000, "redstone", 1000);
    }

    public static void postInitialize() {
        //Add these recipes after all items have been registered
        addCompressorOreRecipes();
        addModRecipes();
        ItemPortableCompressor.registerRecipes();

        //Tweaks
        if(ThermalScienceConfig.recipesOverrideGunpowder) {
            ThermalScienceUtil.removeCraftingRecipeFor(new ItemStack(Items.gunpowder));
        }
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder,2), "dustCoal", "dustSaltpeter","dustSulfur","dustSaltpeter"));
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
        addRecipe(compressorRecipes, new RecipeCompressor(input, output, energy, portable));
    }

    public static void addAssemblingMachineRecipe(Object[] input, Object[] output, int energy, String fluid, int fluidAmount) {
        addRecipe(assemblingMachineRecipes, new RecipeAssemblingMachine(input, output, energy, fluid, fluidAmount));
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

    public static void addModRecipes(){
        //Thermal Expansion
        if (Loader.isModLoaded("ThermalExpansion")) {

            Item teMachineFrame = GameRegistry.findItem("ThermalExpansion", "Frame");
            Item teMachine = GameRegistry.findItem("ThermalExpansion", "Machine");
            Item teMaterial = GameRegistry.findItem("ThermalExpansion", "material");
            Item teCapacitor = GameRegistry.findItem("ThermalExpansion", "capacitor");

            Item tfMaterial = GameRegistry.findItem("ThermalFoundation", "material");

            //Machines
            GameRegistry.addRecipe(new ItemStack(ThermalScience.blockWatermill), "MGM", "GCG", "MGM", 'G', new ItemStack(tfMaterial, 1, 128), 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitBasic);


            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCompressor, "CPM", "IFI", "CPM", 'M', ItemMaterial.motor, 'P', Blocks.piston, 'I', Blocks.iron_bars, 'F', teMachineFrame, 'C', ItemMaterial.circuitBasic));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockWiremill, "CII", "PFT", "MII", 'M', ItemMaterial.motor, 'P', Blocks.piston, 'T', ItemMaterial.tube, 'I', Items.iron_ingot, 'F', teMachineFrame, 'C', ItemMaterial.circuitBasic));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCarbothermicFurnace, "_C_", "ISI", "_I_", 'I', "ingotInvar", 'S', new ItemStack(teMachine, 1, 3), 'C', ItemMaterial.circuitBasic));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockCentrifuge, "IMI", "CFC", "IMI", 'I', "ingotInvar", 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitBasic, 'F', teMachineFrame));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockAssemblingMachine, "MDM", "CFC", "WCW", 'M', ItemMaterial.motor, 'C', ItemMaterial.circuitBasic, 'F', teMachineFrame, 'D', Items.diamond, 'W', ItemMaterial.insulatedWireTin));

            GameRegistry.addRecipe(new ItemStack(ThermalScience.blockEnergyRelay), "IHI", "TWR","ICI", 'C', new ItemStack(teMaterial, 1, 3), 'T', new ItemStack(teMaterial, 1, 2), 'R', new ItemStack(teMaterial, 1, 1), 'W', ItemMaterial.insulatedWireSilver, 'I', Items.iron_ingot, 'H', ItemMaterial.circuitBasic);

            addUpgradableMachineRecipes(ThermalScience.blockCompressor, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockWiremill, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockCarbothermicFurnace, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockCentrifuge, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockEnergyRelay, tfMaterial);
            addUpgradableMachineRecipes(ThermalScience.blockAssemblingMachine, tfMaterial);

            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockTeleporterController, "ICI", "EFE", "ICI", 'I', "ingotInvar", 'C', new ItemStack(teCapacitor, 1, 5), 'E', "ingotEnderium", 'F', new ItemStack(teMachineFrame, 1, 3), 'C', ItemMaterial.circuitResonant));
            GameRegistry.addRecipe(new ShapedOreRecipe(ThermalScience.blockTeleporterPowerTap, "I_I", "ECE", "ICI", 'I', "ingotInvar", 'E', "ingotEnderium", 'C', new ItemStack(teMaterial, 1, 1), 'C', ItemMaterial.circuitResonant));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ThermalScience.blockTeleporterWall, 4), "IEI", "EIE", "IEI", 'I', "ingotInvar", 'E', "ingotEnderium"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ThermalScience.itemTeleporterDestinationCard, 2), "W_W", "EPE", "_C_", 'W', ItemMaterial.wireCopper, 'E', "ingotEnderium", 'P', Items.paper, 'C', ItemMaterial.circuitResonant));

            //Items
            GameRegistry.addShapelessRecipe(new ItemStack(ThermalScience.itemPortableCompressor), new ItemStack(ThermalScience.blockCompressor), new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 4));



            if(ThermalScienceConfig.recipeOverrideMachines){
                //Redstone Furnace
                ItemStack stack = new ItemStack(teMachine, 1, 0);
                ThermalScienceUtil.removeCraftingRecipeFor(stack);
                GameRegistry.addShapedRecipe(stack, "_C_", "FMF", "ORO", 'C', ItemMaterial.circuitBasic, 'M', new ItemStack(teMachineFrame, 1, 0), 'F', Blocks.furnace, 'O', ItemMaterial.coil, 'R', new ItemStack(teMaterial, 1, 1));
            }
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

                addCompressorRecipe(new Object[]{input}, new Object[]{new ItemStack(item, 1, i)}, 420, true);
            }
        }

        if (Loader.isModLoaded("EnderIO")) {
            Item eioMaterial = GameRegistry.findItem("EnderIO", "itemMaterial");

            if (eioMaterial != null) {
                addRecipe(carbothermicFurnaceRecipes, new Object[]{Items.coal, Blocks.sand}, new Object[]{eioMaterial}, 16000);
            }
        }
    }
}
