package com.creysys.ThermalScience.tileEntity.teleporter;

import cofh.lib.util.helpers.ColorHelper;
import cofh.lib.util.helpers.StringHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.compat.waila.IWailaBodyProvider;
import com.creysys.ThermalScience.network.packet.PacketEnergy;
import com.creysys.ThermalScience.network.packet.PacketTeleporterControllerCheck;
import com.creysys.ThermalScience.network.sync.ISyncEnergy;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;

import java.util.List;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class TileEntityTeleporterController extends TileEntity implements IInventory, ISyncEnergy, IWailaBodyProvider {

    public byte facing;
    public boolean active;

    public int startX;
    public int startY;
    public int startZ;

    public int powerTapX;
    public int powerTapY;
    public int powerTapZ;

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

        statusText = "Updating...";
        statusTextColor = ColorHelper.DYE_RED;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if (worldObj.getTotalWorldTime() % 40 == 0) {
            checkMultiblock();

            if(active && (slot == null || !slot.hasTagCompound() || !slot.getTagCompound().hasKey(ThermalScienceNBTTags.Dim))){
                statusText = "Insert destination card";
                statusTextColor = ColorHelper.DYE_YELLOW;
                active = false;
            }

            setActive(active);



            ThermalScience.packetHandler.sendPacketToDimension(worldObj.provider.dimensionId, new PacketTeleporterControllerCheck(xCoord, yCoord, zCoord, statusText, statusTextColor));
        }

        if (active) {
            if (worldObj.isRemote || slot == null || !slot.hasTagCompound()) {
                return;
            }

            for (int i = 0; i < worldObj.playerEntities.size(); i++) {

                EntityPlayer player = (EntityPlayer) worldObj.playerEntities.get(i);


                int posX = (int) player.posX;
                if (posX < 0) {
                    posX -= 1;
                }

                int posZ = (int) player.posZ;
                if (posZ < 0) {
                    posZ -= 1;
                }

                if (posX == startX + 1 && (int) player.posY == startY + 1 && posZ == startZ + 1) {
                    NBTTagCompound compound = slot.getTagCompound();
                    int dim = compound.getInteger(ThermalScienceNBTTags.Dim);
                    int x = compound.getInteger(ThermalScienceNBTTags.XCoord);
                    int y = compound.getInteger(ThermalScienceNBTTags.YCoord);
                    int z = compound.getInteger(ThermalScienceNBTTags.ZCoord);
                    int yaw = compound.getInteger(ThermalScienceNBTTags.Yaw);

                    int cost = 20000;

                    if (player.dimension != dim) {
                        cost *= 2;
                    }

                    if(energyStored > cost) {
                        if (player.dimension != dim) {
                            player.travelToDimension(dim);
                        }

                        player.rotationYaw = yaw;
                        player.setPositionAndUpdate(x + 0.5f, y, z + 0.5f);

                        energyStored -= cost;
                        ThermalScience.packetHandler.sendPacketToDimension(worldObj.provider.dimensionId, new PacketEnergy(xCoord, yCoord, zCoord, energyStored));
                    }
                }
            }
        }
    }

    public void setActive(boolean active){
        int meta = (int)facing;

        if(active){
            meta += 10;
        }

        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
    }

    public void checkMultiblock(){

        if(worldObj.isRemote){
            return;
        }

        if(active) {
            active = false;
            worldObj.notifyBlocksOfNeighborChange(powerTapX, powerTapY, powerTapZ, worldObj.getBlock(powerTapX, powerTapY, powerTapZ));
        }

        //Get first block coords
        boolean found = false;
        for(int i = 0; i < 3; i++) {
            Block block = worldObj.getBlock(xCoord - i - 1, yCoord, zCoord);
            Block blockNext = worldObj.getBlock(xCoord - i - 2, yCoord, zCoord);
            if (block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block != ThermalScience.blockTeleporterPowerTap &&
                    blockNext != ThermalScience.blockTeleporterWall && blockNext != ThermalScience.blockTeleporterController && blockNext != ThermalScience.blockTeleporterPowerTap) {
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
            Block blockNext = worldObj.getBlock(startX, yCoord - i - 3, zCoord);
            if (block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block != ThermalScience.blockTeleporterPowerTap &&
                    blockNext != ThermalScience.blockTeleporterWall && blockNext != ThermalScience.blockTeleporterController && blockNext != ThermalScience.blockTeleporterPowerTap) {
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
            Block blockNext = worldObj.getBlock(startX,startY,zCoord - i - 2);
            if(block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block != ThermalScience.blockTeleporterPowerTap &&
                    blockNext != ThermalScience.blockTeleporterWall && blockNext != ThermalScience.blockTeleporterController && blockNext != ThermalScience.blockTeleporterPowerTap) {
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
                    else if(block == ThermalScience.blockTeleporterPowerTap) {
                        if (hasPowerTap) {
                            statusText = "There must only be one power tap!";
                            statusTextColor = ColorHelper.DYE_RED;
                            return;
                        }

                        hasPowerTap = true;
                        powerTapX = startX + x;
                        powerTapY = startY + y;
                        powerTapZ = startZ + z;
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
        if(!hasPowerTap || !(worldObj.getTileEntity(powerTapX,powerTapY,powerTapZ) instanceof TileEntityTeleporterPowerTap)) {
            statusText = "Power tap is missing!";
            statusTextColor = ColorHelper.DYE_RED;
            return;
        }
        if(door != 10){
            statusText = "Entrance is missing!";
            statusTextColor = ColorHelper.DYE_RED;
            return;
        }

        TileEntityTeleporterPowerTap powerTap = (TileEntityTeleporterPowerTap)worldObj.getTileEntity(powerTapX,powerTapY,powerTapZ);
        powerTap.controllerX = xCoord;
        powerTap.controllerY = yCoord;
        powerTap.controllerZ = zCoord;

        statusText = "Teleporter is ready!";
        statusTextColor = ColorHelper.DYE_GREEN;
        active = true;

        worldObj.notifyBlocksOfNeighborChange(powerTapX, powerTapY, powerTapZ, worldObj.getBlock(powerTapX, powerTapY, powerTapZ));
    }

    public void writeCustomToNBT(NBTTagCompound compound){
        compound.setByte(ThermalScienceNBTTags.Facing, facing);
        compound.setBoolean(ThermalScienceNBTTags.Active, active);

        compound.setInteger(ThermalScienceNBTTags.XCoord, startX);
        compound.setInteger(ThermalScienceNBTTags.YCoord, startY);
        compound.setInteger(ThermalScienceNBTTags.ZCoord, startZ);

        if(active){
            NBTTagCompound powerTapCompound = new NBTTagCompound();
            powerTapCompound.setInteger(ThermalScienceNBTTags.XCoord, powerTapX);
            powerTapCompound.setInteger(ThermalScienceNBTTags.YCoord, powerTapY);
            powerTapCompound.setInteger(ThermalScienceNBTTags.ZCoord, powerTapZ);
            compound.setTag(ThermalScienceNBTTags.Block, powerTapCompound);
        }

        if(slot != null) {
            NBTTagCompound slotCompound = new NBTTagCompound();
            slot.writeToNBT(slotCompound);
            compound.setTag(ThermalScienceNBTTags.Slot, slotCompound);
        }

        compound.setInteger(ThermalScienceNBTTags.EnergyStored,energyStored);

        compound.setString(ThermalScienceNBTTags.Status, statusText);
        compound.setInteger(ThermalScienceNBTTags.Color, statusTextColor);
    }

    public void readCustomFromNBT(NBTTagCompound compound){
        facing = compound.getByte(ThermalScienceNBTTags.Facing);
        active = compound.getBoolean(ThermalScienceNBTTags.Active);

        startX = compound.getInteger(ThermalScienceNBTTags.XCoord);
        startY = compound.getInteger(ThermalScienceNBTTags.YCoord);
        startZ = compound.getInteger(ThermalScienceNBTTags.ZCoord);

        if(active && compound.hasKey(ThermalScienceNBTTags.Block)){
            NBTTagCompound powerTapCompound = compound.getCompoundTag(ThermalScienceNBTTags.Block);
            powerTapX = powerTapCompound.getInteger(ThermalScienceNBTTags.XCoord);
            powerTapY = powerTapCompound.getInteger(ThermalScienceNBTTags.YCoord);
            powerTapZ = powerTapCompound.getInteger(ThermalScienceNBTTags.ZCoord);
        }

        if(compound.hasKey(ThermalScienceNBTTags.Slot)) {
            slot = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(ThermalScienceNBTTags.Slot));
        }

        energyStored = compound.getInteger(ThermalScienceNBTTags.EnergyStored);

        statusText = compound.getString(ThermalScienceNBTTags.Status);
        statusTextColor = compound.getInteger(ThermalScienceNBTTags.Color);
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

    @Override
    public void setEnergy(int i) {
        energyStored = i;
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldBlock != newBlock;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        boolean found = false;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).toLowerCase().contains(" rf")){
                found = true;
                break;
            }
        }

        if(!found) {
            list.add(energyStored + " / " + maxEnergyStored + " RF");
        }

        list.add(statusText);

        return list;
    }
}
