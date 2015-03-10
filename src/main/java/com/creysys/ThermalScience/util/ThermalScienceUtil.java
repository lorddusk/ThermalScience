package com.creysys.ThermalScience.util;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.network.packet.PacketEnergy;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 2/1/2015.
 */
public class ThermalScienceUtil {

    private static ArrayList<Item> wrenchItems;

    public static void initialize() {
        wrenchItems = new ArrayList<Item>();

        addWrench("ThermalExpansion", "wrench");
        addWrench("EnderIO", "itemYetaWrench");
        addWrench("BuildCraft|Core", "wrenchItem");
    }

    public static void addWrench(String mod, String id) {
        Item wrench = GameRegistry.findItem(mod, id);
        if (wrench != null) {
            wrenchItems.add(wrench);
        }
    }

    public static boolean isItemWrench(Item item) {
        for (int i = 0; i < wrenchItems.size(); i++) {
            if (wrenchItems.get(i) == item) {
                return true;
            }
        }
        return false;
    }


    public static void writeStacksToNBT(ItemStack[] stacks, NBTTagCompound compound, String tag) {
        NBTTagList stacksList = new NBTTagList();

        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i] != null) {
                NBTTagCompound slotTagCompound = new NBTTagCompound();
                slotTagCompound.setByte(ThermalScienceNBTTags.Slot, (byte) i);
                stacks[i].writeToNBT(slotTagCompound);
                stacksList.appendTag(slotTagCompound);
            }
        }

        compound.setTag(ThermalScienceNBTTags.Slots, stacksList);
    }

    public static ItemStack[] readStacksFromNBT(int length, NBTTagCompound compound, String tag) {
        ItemStack[] stacks = new ItemStack[length];

        NBTTagList slotsList = compound.getTagList(tag, Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < slotsList.tagCount(); i++) {
            NBTTagCompound slotTagCompound = slotsList.getCompoundTagAt(i);

            int slot = (int) slotTagCompound.getByte(ThermalScienceNBTTags.Slot);
            stacks[slot] = ItemStack.loadItemStackFromNBT(slotTagCompound);
        }

        return stacks;
    }

    public static int getIndexOfItem(Object item, ItemStack[] stacks) {
        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i] == null) {
                continue;
            }

            if(item instanceof Item){
                item = new ItemStack((Item)item);
            }
            else if(item instanceof Block){
                item = new ItemStack((Block)item);
            }

            if (item instanceof ItemStack) {
                if (areStacksEqual(stacks[i], (ItemStack) item)) {
                    return i;
                }
            } else if (item instanceof String) {
                List<ItemStack> oreDictEntries = OreDictionary.getOres(((String) item).split(",")[0]);

                for (int j = 0; j < oreDictEntries.size(); j++) {
                    if (areStacksEqual(stacks[i], oreDictEntries.get(j))) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }

    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage() && !stack1.hasTagCompound() && !stack2.hasTagCompound();
    }

    public static ItemStack getStack(Object output) {

        if(output instanceof Item){
            output = new ItemStack((Item)output);
        }
        else if(output instanceof Block){
            output = new ItemStack((Block)output);
        }

        if (output instanceof ItemStack) {
            return ((ItemStack) output).copy();
        } else if (output instanceof String) {
            String[] splits = ((String) output).split(",");

            ItemStack stack = getPrefferedOre(splits[0]);

            if (stack != null && splits.length > 1) {
                stack.stackSize = Integer.parseInt(splits[1]);
                return stack;
            }

            return stack;
        }

        return null;
    }

    public static ItemStack getPrefferedOre(String name) {

        List<ItemStack> oreDictEntries = OreDictionary.getOres(name);

        ItemStack ret = null;

        if (oreDictEntries.size() > 0) {

            ret = oreDictEntries.get(0).copy();

            for (int i = 1; i < oreDictEntries.size(); i++) {
                String modid = GameRegistry.findUniqueIdentifierFor(oreDictEntries.get(i).getItem()).modId;
                if (modid.equals("Minecraft") || modid.equals("ThermalFoundation")) {
                    ret = oreDictEntries.get(i).copy();
                    break;
                }
            }

        }

        return ret;
    }

    public static ThermalScienceRecipe getMatchingRecipe(List<ThermalScienceRecipe> recipes, ItemStack[] input) {
        for (int i = 0; i < recipes.size(); i++) {
            ThermalScienceRecipe recipe = recipes.get(i);
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static boolean areOresIdentical(ItemStack stack1, ItemStack stack2){
        if(areStacksEqual(stack1, stack2)){
            return true;
        }

        int[] ores1 = OreDictionary.getOreIDs(stack1);
        int[] ores2 = OreDictionary.getOreIDs(stack2);

        if(ores1.length == 0 || ores2.length == 0){
            return false;
        }

        if(ores1.length != ores2.length){
            return false;
        }

        for(int i = 0; i < ores1.length; i++){
            if(ores1[i] != ores2[i]){
                return false;
            }
        }

        return true;
    }

    public static List<ThermalScienceRecipe> getRecipesFor(List<ThermalScienceRecipe> recipes,Object obj) {
        List<ThermalScienceRecipe> ret = new ArrayList<ThermalScienceRecipe>();

        for(int i = 0; i < recipes.size(); i++){
            ThermalScienceRecipe recipe = recipes.get(i);
            for(int j = 0; j < recipe.outputs.length; j++) {
                ItemStack stack1 = getStack(obj);
                ItemStack stack2 = getStack(recipe.outputs[j]);

                if(areOresIdentical(stack1, stack2)){
                    ret.add(recipes.get(i));
                    break;
                }
            }
        }

        return ret;
    }

    public static List<ThermalScienceRecipe> getUsageFor(List<ThermalScienceRecipe> recipes,Object obj) {
        List<ThermalScienceRecipe> ret = new ArrayList<ThermalScienceRecipe>();

        for(int i = 0; i < recipes.size(); i++){
            ThermalScienceRecipe recipe = recipes.get(i);
            for(int j = 0; j < recipe.inputs.length; j++) {
                ItemStack stack1 = getStack(obj);
                ItemStack stack2 = getStack(recipe.inputs[j]);

                if(areOresIdentical(stack1, stack2)){
                    ret.add(recipes.get(i));
                    break;
                }
            }
        }

        return ret;
    }

    public static void wrenchBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);

        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots)
        {
            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem itemEntity = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, new ItemStack(block));
            itemEntity.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(itemEntity);

            block.breakBlock(world, x, y, z, block, world.getBlockMetadata(x, y, z));
        }

        world.setBlockToAir(x, y, z);
    }

    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height){
        drawTexturedModalRect(x, y, u, v, width, height, 256, 256);
    }

    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 1F / (float) textureWidth;
        float f1 = 1F / (float) textureHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0, u * f, (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y + height, 0, (u + width) * f, (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y, 0, (u + width) * f, v * f1);
        tessellator.addVertexWithUV(x, y, 0, u * f, v * f1);
        tessellator.draw();
    }

    public static void setTexture(ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    }

    public static void removeEnderIORecipe(String machine, String recipeGroup, String recipeName){

        String msg =
                "<recipeGroup name=\"" + recipeGroup + "\">" +
                        "<recipe name=\"" + recipeName + "\"/>" +
                "</recipeGroup>";
        FMLInterModComms.sendMessage("EnderIO", "recipe:" + machine, msg);
    }

    public static void addEnderIORecipe(String machine, String recipeName, String input, String output, int energyCost)
    {
        String msg =
                "<recipeGroup name=\"ThermalScience\">" +
                        "<recipe name=\"" + recipeName + "\" energyCost=\"" + energyCost +"\">" +
                            "<input>" +
                                input +
                            "</input>" +
                            "<output>" +
                                output +
                            "</output>" +
                        "</recipe>" +
                "</recipeGroup>";
        FMLInterModComms.sendMessage("EnderIO", "recipe:" + machine, msg);
    }

    public static void syncEnergy(World world, int x, int y, int z, int energy, int maxEnergy){
        if(world.getTotalWorldTime() % 10 == 0 || energy == 0 || energy == maxEnergy) {
            ThermalScience.packetHandler.sendPacketToDimension(world.provider.dimensionId, new PacketEnergy(x, y, z, energy));
        }
    }

    public static void removeCraftingRecipeFor(ItemStack stack){
        List list = CraftingManager.getInstance().getRecipeList();
        List remove = new ArrayList<IRecipe>();

        for(int i = 0; i < list.size(); i++){
            if(list.get(i) instanceof IRecipe){
                IRecipe recipe = (IRecipe)list.get(i);

                if(areStacksEqual(recipe.getRecipeOutput(), stack)){
                    remove.add(recipe);
                }
            }
        }

        list.removeAll(remove);
    }

    public static ItemStack setStack(ItemStack stack, int stacks){
        stack.stackSize = stacks;
        return stack;
    }

    public static void renderBlockFace(int face, double x, double y, double z){

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glDisable(GL11.GL_LIGHTING);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        switch(face){
            case 0:
                //-Y
                tessellator.addVertexWithUV(1, 0, 1, 0, 0);
                tessellator.addVertexWithUV(0, 0, 1, 0, 1);
                tessellator.addVertexWithUV(0, 0, 0, 1, 1);
                tessellator.addVertexWithUV(1, 0, 0, 1, 0);
                break;
            case 1:
                //+Y
                tessellator.addVertexWithUV(1, 1, 1, 0, 0);
                tessellator.addVertexWithUV(1, 1, 0, 1, 0);
                tessellator.addVertexWithUV(0, 1, 0, 1, 1);
                tessellator.addVertexWithUV(0, 1, 1, 0, 1);
                break;
            case 2:
                //-X
                tessellator.addVertexWithUV(0, 0, 0, 1, 1);
                tessellator.addVertexWithUV(0, 1, 0, 1, 0);
                tessellator.addVertexWithUV(1, 1, 0, 0, 0);
                tessellator.addVertexWithUV(1, 0, 0, 0, 1);
                break;
            case 3:
                //+X
                tessellator.addVertexWithUV(0, 0, 1, 1, 1);
                tessellator.addVertexWithUV(1, 0, 1, 0, 1);
                tessellator.addVertexWithUV(1, 1, 1, 0, 0);
                tessellator.addVertexWithUV(0, 1, 1, 1, 0);
                break;
            case 4:
                //-Z
                tessellator.addVertexWithUV(0, 0, 1, 1, 1);
                tessellator.addVertexWithUV(0, 1, 1, 1, 0);
                tessellator.addVertexWithUV(0, 1, 0, 0, 0);
                tessellator.addVertexWithUV(0, 0, 0, 0, 1);
                break;
            case 5:
                //+Z
                tessellator.addVertexWithUV(1, 0, 1, 1, 1);
                tessellator.addVertexWithUV(1, 0, 0, 0, 1);
                tessellator.addVertexWithUV(1, 1, 0, 0, 0);
                tessellator.addVertexWithUV(1, 1, 1, 1, 0);
                break;
        }
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    public static File getSaveFolder() {
        File file = new File("world", "thermalScience");
        file.mkdir();

        return file;
    }
}
