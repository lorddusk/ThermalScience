package com.creysys.ThermalScience.item;

import cofh.api.energy.ItemEnergyContainer;
import cofh.lib.util.helpers.StringHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import com.creysys.ThermalScience.recipe.recipe.RecipeCompressor;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Creysys on 17 Feb 15.
 */
public class ItemPortableCompressor extends ItemEnergyContainer
{
    public static Hashtable<ItemStack, ItemStack> entries;

    public static void registerRecipes(){
        entries = new Hashtable<ItemStack, ItemStack>();
        for(int i = 0; i < ThermalScienceRecipes.compressorRecipes.size(); i++){
            if(ThermalScienceRecipes.compressorRecipes.get(i) instanceof RecipeCompressor){
                RecipeCompressor recipe = (RecipeCompressor)ThermalScienceRecipes.compressorRecipes.get(i);
                if(recipe.portable) {

                    ItemStack input = ThermalScienceUtil.getStack(recipe.inputs[0]);
                    ItemStack output = ThermalScienceUtil.getStack(recipe.outputs[0]);

                    if(input == null || output == null){
                        continue;
                    }

                    entries.put(input, output);
                }
            }
        }
    }

    public IIcon iconOn;
    public IIcon iconOff;

    public ItemPortableCompressor() {
        super(2000000, 2000, 2000);

        setUnlocalizedName("itemPortableCompressor");
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerItem(this, "itemPortableCompressor");

        setMaxStackSize(1);
        setMaxDamage(16);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean b) {
        NBTTagCompound compound;
        if (!stack.hasTagCompound()) {
            compound = new NBTTagCompound();
        } else {
            compound = stack.getTagCompound();
        }

        boolean active = false;
        if (compound.hasKey(ThermalScienceNBTTags.Active)) {
            active = compound.getBoolean(ThermalScienceNBTTags.Active);
        } else {
            compound.setBoolean(ThermalScienceNBTTags.Active, false);
        }

        int energyStored = 0;
        if(compound.hasKey(ThermalScienceNBTTags.EnergyStored)){
            energyStored = compound.getInteger(ThermalScienceNBTTags.EnergyStored);
        }
        else {
            compound.setInteger(ThermalScienceNBTTags.EnergyStored, 0);
        }

        if (active) {
            info.add(StringHelper.GREEN + "Active");
        } else {
            info.add(StringHelper.RED + "Inactive");
        }

        info.add(energyStored + " / " + capacity + " RF");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if(!player.isSneaking() || world.isRemote){
            return stack;
        }

        NBTTagCompound compound;
        if(!stack.hasTagCompound())
        {
            compound = new NBTTagCompound();
        }
        else {
            compound = stack.getTagCompound();
        }

        boolean active = false;
        if(compound.hasKey(ThermalScienceNBTTags.Active)){
            active = compound.getBoolean(ThermalScienceNBTTags.Active);
        }

        compound.setBoolean(ThermalScienceNBTTags.Active, !active);

        stack.setTagCompound(compound);

        return stack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if(entity instanceof EntityPlayer && !world.isRemote && world.getTotalWorldTime() % 5 == 0) {

            int energyStored = 0;

            if(stack.hasTagCompound()){
                NBTTagCompound compound = stack.getTagCompound();
                if(compound.hasKey(ThermalScienceNBTTags.Active)){
                    if(!compound.getBoolean(ThermalScienceNBTTags.Active)){
                        return;
                    }
                }
                else{
                    return;
                }

                if(compound.hasKey(ThermalScienceNBTTags.EnergyStored)){
                    energyStored = compound.getInteger(ThermalScienceNBTTags.EnergyStored);
                    if(energyStored < 1000){
                        return;
                    }
                }
                else {
                    return;
                }
            }

            EntityPlayer player = (EntityPlayer)entity;

            for(Map.Entry<ItemStack, ItemStack> entry : entries.entrySet()){
                ArrayList<Integer> indices = getIndicesOfItem(player.inventory, entry.getKey());

                int amount = countItems(player.inventory, indices);

                if(amount >= entry.getKey().getMaxStackSize()){
                    if(player.inventory.addItemStackToInventory(entry.getValue().copy())){
                        decreaseItemStack(player.inventory, indices, entry.getKey().stackSize);

                        energyStored -= 1000;

                        if(!stack.hasTagCompound())
                        {
                            stack.setTagCompound(new NBTTagCompound());
                        }

                        stack.getTagCompound().setInteger(ThermalScienceNBTTags.EnergyStored, energyStored);

                        updateDamage(stack);

                        break;
                    }
                }
                else if(amount < 64 && indices.size() > 1){
                    player.inventory.getStackInSlot(indices.get(0)).stackSize = amount;

                    for(int i = 1; i < indices.size(); i++){
                        player.inventory.setInventorySlotContents(indices.get(i), null);
                    }
                }
            }
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        iconOn = iconRegister.registerIcon(ThermalScienceTextures.portableCompressorOn.icon);
        iconOff = iconRegister.registerIcon(ThermalScienceTextures.portableCompressorOff.icon);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        boolean active = false;

        if(stack.hasTagCompound()){
            if(stack.getTagCompound().hasKey(ThermalScienceNBTTags.Active)){
                active = stack.getTagCompound().getBoolean(ThermalScienceNBTTags.Active);
            }
        }

        if(active){
            return iconOn;
        }

        return iconOff;
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 1;
    }

    public void updateDamage(ItemStack stack) {
        int energyStored = 0;

        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(ThermalScienceNBTTags.EnergyStored)){
            energyStored = stack.getTagCompound().getInteger(ThermalScienceNBTTags.EnergyStored);
        }

        stack.setItemDamage(getMaxDamage() - (int)Math.floor(((float)getMaxDamage() - 1) / (float)capacity * (float)energyStored));
    }

    @Override
    public int receiveEnergy(ItemStack stack, int max, boolean simulate) {
        int energyStored = 0;

        NBTTagCompound compound;
        if(!stack.hasTagCompound()){
            compound = new NBTTagCompound();
        }
        else{
            compound = stack.getTagCompound();
        }

        if(compound.hasKey(ThermalScienceNBTTags.EnergyStored)){
            energyStored = compound.getInteger(ThermalScienceNBTTags.EnergyStored);
        }

        int canReceive = Math.min(Math.min(max, maxReceive), capacity - energyStored);

        energyStored += canReceive;

        compound.setInteger(ThermalScienceNBTTags.EnergyStored, energyStored);
        stack.setTagCompound(compound);

        updateDamage(stack);

        return canReceive;
    }

    @Override
    public int extractEnergy(ItemStack itemStack, int i, boolean b) {
        return 0;
    }

    public ArrayList<Integer> getIndicesOfItem(InventoryPlayer inventory, ItemStack itemStack){
         ArrayList<Integer> indices = new ArrayList<Integer>();

        for(int i = 0; i < inventory.getSizeInventory(); i++){
            ItemStack stack = inventory.getStackInSlot(i);
            if(stack != null && ThermalScienceUtil.areOresIdentical(stack, itemStack)){
                indices.add(i);
            }
        }

        return indices;
    }

    public int countItems(InventoryPlayer inventory, ArrayList<Integer> indices) {
        int amount = 0;

        for (int i = 0; i < indices.size(); i++) {
            ItemStack stack = inventory.getStackInSlot(indices.get(i));
            if (stack != null) {
                amount += stack.stackSize;
            }
        }

        return amount;
    }

    public void decreaseItemStack(InventoryPlayer inventory, ArrayList<Integer> indices, int amount){
        for(int i = 0; i < indices.size(); i++){

            ItemStack stack = inventory.getStackInSlot(indices.get(i));
            if(stack == null){
                continue;
            }

            int canDecrease = Math.min(stack.stackSize, amount);

            amount -= canDecrease;
            stack.stackSize -= canDecrease;

            if(stack.stackSize <= 0){
                inventory.setInventorySlotContents(indices.get(i), null);
            }

            if(amount <= 0){
                return;
            }
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(this, 1, getMaxDamage()));

        ItemStack stack = new ItemStack(this, 1, 1);
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger(ThermalScienceNBTTags.EnergyStored, capacity);
        stack.setTagCompound(compound);
        list.add(stack);
    }
}
