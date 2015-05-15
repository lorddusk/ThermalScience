package com.creysys.ThermalScience.item;

import cofh.lib.util.helpers.StringHelper;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class ItemTeleporterDestinationCard extends ItemThermalScience {
    public ItemTeleporterDestinationCard() {
        super("TeleporterDestinationCard", ThermalScienceTextures.teleporterDestinationCard);

        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3) {
        if(world.isRemote || stack.hasTagCompound()){
            return false;
        }

        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(ThermalScienceNBTTags.Dim, world.provider.dimensionId);
        tagCompound.setInteger(ThermalScienceNBTTags.XCoord, x);
        tagCompound.setInteger(ThermalScienceNBTTags.YCoord, y + 1);
        tagCompound.setInteger(ThermalScienceNBTTags.ZCoord, z);

        int yaw = (int)player.rotationYaw;

        if(yaw < 0){
            yaw += 360;
        }

        int i = (yaw + 45) / 90;

        tagCompound.setInteger(ThermalScienceNBTTags.Yaw, (i * 90));

        stack.setTagCompound(tagCompound);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if(world.isRemote){
            return stack;
        }

        if(player.isSneaking()){
            stack.setTagCompound(null);
        }
        else if(!stack.hasTagCompound()){
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger(ThermalScienceNBTTags.Dim, world.provider.dimensionId);
            tagCompound.setInteger(ThermalScienceNBTTags.XCoord, (int)player.posX);
            tagCompound.setInteger(ThermalScienceNBTTags.YCoord, (int)player.posY);
            tagCompound.setInteger(ThermalScienceNBTTags.ZCoord, (int)player.posZ);
            tagCompound.setInteger(ThermalScienceNBTTags.Yaw, (int)player.rotationYaw);

            stack.setTagCompound(tagCompound);
        }

        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1) {
        if(stack.hasTagCompound()){
            NBTTagCompound tagCompound = stack.getTagCompound();
            if(tagCompound.hasKey(ThermalScienceNBTTags.Dim) && tagCompound.hasKey(ThermalScienceNBTTags.XCoord) && tagCompound.hasKey(ThermalScienceNBTTags.YCoord) && tagCompound.hasKey(ThermalScienceNBTTags.ZCoord)){
                list.add(StringHelper.RED + "Dimension: " + tagCompound.getInteger(ThermalScienceNBTTags.Dim));
                list.add(StringHelper.PURPLE + "X: " + tagCompound.getInteger(ThermalScienceNBTTags.XCoord) + "; Y: " + tagCompound.getInteger(ThermalScienceNBTTags.YCoord) + "; Z: " + tagCompound.getInteger(ThermalScienceNBTTags.ZCoord));
            }
            else if(tagCompound.hasKey(ThermalScienceNBTTags.Id)){
                list.add(StringHelper.RED + "Controller Id: " + tagCompound.getInteger(ThermalScienceNBTTags.Id));
            }
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return stack.hasTagCompound();
    }
}
