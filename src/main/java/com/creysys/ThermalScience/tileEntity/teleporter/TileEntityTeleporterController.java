package com.creysys.ThermalScience.tileEntity.teleporter;

import cofh.lib.util.helpers.ColorHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.ThermalScienceUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class TileEntityTeleporterController extends TileEntity implements IInventory {

    public byte facing;
    public boolean active;

    public int startX;
    public int startY;
    public int startZ;

    public ItemStack slot;

    public int energyStored;
    public int maxEnergyStored;

    public String statusText;
    public int statusTextColor;

    public TileEntityTeleporterController(){
        facing = 0;
        active = false;

        energyStored = 0;
        maxEnergyStored = 1000000;

        statusText = "Hit Update";
        statusTextColor = ColorHelper.DYE_RED;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {

        if(worldObj.isRemote || slot == null || !slot.hasTagCompound() || !active){
            return;
        }

        for(int i = 0;i < worldObj.playerEntities.size(); i++){
            EntityPlayer player = (EntityPlayer)worldObj.playerEntities.get(i);


            int posX = (int)player.posX;
            if(posX < 0){
                posX -= 1;
            }

            int posZ = (int)player.posZ;
            if(posZ < 0){
                posZ -= 1;
            }

            if(posX == startX + 1 && (int)player.posY == startY + 1 && posZ == startZ + 1) {
                NBTTagCompound compound = slot.getTagCompound();
                int dim = compound.getInteger(ThermalScienceNBTTags.Dim);
                int x = compound.getInteger(ThermalScienceNBTTags.XCoord);
                int y = compound.getInteger(ThermalScienceNBTTags.YCoord);
                int z = compound.getInteger(ThermalScienceNBTTags.ZCoord);

                if (player.dimension != dim) {
                    player.travelToDimension(dim);
                }

                player.setPositionAndUpdate(x, y, z);
            }
        }
    }

    public void checkMultiblock(){

        if(worldObj.isRemote){
            return;
        }

        active = false;

        //Get first block coords
        boolean found = false;
        for(int i = 0; i < 3; i++){
            Block block = worldObj.getBlock(xCoord - i - 1,yCoord,zCoord);
            if(block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block!=ThermalScience.blockTeleporterPowerTap) {
                startX = xCoord - i;
                found = true;
                break;
            }
        }
        if(!found){
            statusText = "Too long in x!";
            statusTextColor = ColorHelper.DYE_RED;
            return;
        }

        found = false;
        for(int i = 0; i < 4; i++) {
            Block block = worldObj.getBlock(startX, yCoord - i - 1, zCoord);
            if (block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block != ThermalScience.blockTeleporterPowerTap) {
                startY = yCoord - i;
                found = true;
                break;
            }
        }
        if(!found){
            statusText = "Too long in y!";
            statusTextColor = ColorHelper.DYE_RED;
            return;
        }

        found = false;
        for(int i = 0; i < 3; i++){
            Block block = worldObj.getBlock(startX,startY,zCoord - i - 1);
            if(block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block!=ThermalScience.blockTeleporterPowerTap) {
                startZ = zCoord - i;
                found = true;
                break;
            }
        }
        if(!found){
            statusText = "Too long in z!";
            statusTextColor = ColorHelper.DYE_RED;
            return;
        }


        boolean hasController = false;
        boolean hasPowerTap = false;
        int door = -1;
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 4; y++){
                for(int z = 0; z < 3; z++){
                    if(x == 1 && z == 1 && (y == 1 || y == 2)){
                        if(!worldObj.isAirBlock(startX + x, startY + y, startZ + z)){
                            statusText = "Middle has to be empty!";
                            statusTextColor = ColorHelper.DYE_RED;
                            return;
                        }
                        continue;
                    }else if(worldObj.isAirBlock(startX + x, startY + y, startZ + z)) {
                        if (y != 1 && y != 2) {
                            statusText = "Incomplete structure!";
                            statusTextColor = ColorHelper.DYE_RED;
                            return;
                        }

                        if (x == 0 && z == 1) {
                            if (door == -1) {
                                door = 0;
                            } else if (door != 0) {
                                statusText = "There must only be one entrance!";
                                statusTextColor = ColorHelper.DYE_RED;
                                return;
                            } else {
                                door = 10;
                            }
                        } else if (x == 2 && z == 1) {
                            if (door == -1) {
                                door = 1;
                            } else if (door != 1) {
                                statusText = "There must only be one entrance!";
                                statusTextColor = ColorHelper.DYE_RED;
                                return;
                            } else {
                                door = 10;
                            }
                        } else if (x == 1 && z == 0) {
                            if (door == -1) {
                                door = 2;
                            } else if (door != 2) {
                                statusText = "There must only be one entrance!";
                                statusTextColor = ColorHelper.DYE_RED;
                                return;
                            } else {
                                door = 10;
                            }
                        } else if (x == 1 && z == 2) {
                            if (door == -1) {
                                door = 3;
                            } else if (door != 3) {
                                statusText = "There must only be one entrance!";
                                statusTextColor = ColorHelper.DYE_RED;
                                return;
                            } else {
                                door = 10;
                            }
                        } else {
                            statusText = "Invalid structure!";
                            statusTextColor = ColorHelper.DYE_RED;
                            return;
                        }

                        continue;
                    }



                    Block block = worldObj.getBlock(startX + x, startY + y, startZ + z);
                    if(block == ThermalScience.blockTeleporterController){
                        if(hasController){
                            statusText = "There must only be one controller!";
                            statusTextColor = ColorHelper.DYE_RED;
                            return;
                        }

                        hasController = true;
                    }
                    else if(block == ThermalScience.blockTeleporterPowerTap){
                        if(hasPowerTap){
                            statusText = "There must only be one power tab!";
                            statusTextColor = ColorHelper.DYE_RED;
                            return;
                        }

                        hasPowerTap = true;
                    }
                    else if(block != ThermalScience.blockTeleporterWall){
                        return;
                    }
                }
            }
        }

        if(!hasController){
            statusText = "Controller is missing!";
            statusTextColor = ColorHelper.DYE_RED;
            return;
        }
        if(!hasPowerTap){
            statusText = "Power tap is missing!";
            statusTextColor = ColorHelper.DYE_RED;
            return;
        }
        if(door != 10){
            statusText = "Entrance is missing!";
            statusTextColor = ColorHelper.DYE_RED;
            return;
        }

        statusText = "Teleporter is ready!";
        statusTextColor = ColorHelper.DYE_GREEN;
        active = true;


    }

    public void writeCustomToNBT(NBTTagCompound compound){
        compound.setByte(ThermalScienceNBTTags.Facing, facing);
        compound.setBoolean(ThermalScienceNBTTags.Active, active);

        compound.setInteger(ThermalScienceNBTTags.XCoord, startX);
        compound.setInteger(ThermalScienceNBTTags.YCoord, startY);
        compound.setInteger(ThermalScienceNBTTags.ZCoord, startZ);

        if(slot != null) {
            NBTTagCompound slotCompound = new NBTTagCompound();
            slot.writeToNBT(slotCompound);
            compound.setTag(ThermalScienceNBTTags.Slot, slotCompound);
        }

        compound.setInteger(ThermalScienceNBTTags.EnergyStored,energyStored);
    }

    public void readCustomFromNBT(NBTTagCompound compound){
        facing = compound.getByte(ThermalScienceNBTTags.Facing);
        active = compound.getBoolean(ThermalScienceNBTTags.Active);

        startX = compound.getInteger(ThermalScienceNBTTags.XCoord);
        startY = compound.getInteger(ThermalScienceNBTTags.YCoord);
        startZ = compound.getInteger(ThermalScienceNBTTags.ZCoord);

        if(compound.hasKey(ThermalScienceNBTTags.Slot)) {
            slot = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(ThermalScienceNBTTags.Slot));
        }

        energyStored = compound.getInteger(ThermalScienceNBTTags.EnergyStored);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readCustomFromNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeCustomToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readCustomFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeCustomToNBT(compound);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i1) {
        return slot;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        ItemStack ret = slot;
        slot = null;
        return ret;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack stack) {
        slot = stack;
    }

    @Override
    public String getInventoryName() {
        return "teleporterController";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int i1, ItemStack stack) {
        return stack != null && stack.getItem() == ThermalScience.itemTeleporterDestinationCard && stack.hasTagCompound() && stack.getTagCompound().hasKey(ThermalScienceNBTTags.Dim);
    }
}
