package com.creysys.ThermalScience.tileEntity.teleporter;

import cofh.api.energy.IEnergyReceiver;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.block.teleporter.BlockTeleporterPowerTap;
import com.creysys.ThermalScience.compat.waila.IWailaBodyProvider;
import com.creysys.ThermalScience.network.packet.PacketEnergy;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class TileEntityTeleporterPowerTap extends TileEntity implements IEnergyReceiver, IWailaBodyProvider {

    public int maxEnergyReceive;

    public byte facing;

    public int controllerX;
    public int controllerY;
    public int controllerZ;

    public TileEntityTeleporterPowerTap(){
        maxEnergyReceive = 1000;

        facing = 0;
    }

    public TileEntityTeleporterController getTeleporterController(){
        TileEntity tileEntity = worldObj.getTileEntity(controllerX,controllerY,controllerZ);
        if(tileEntity instanceof TileEntityTeleporterController){
            TileEntityTeleporterController teleporterController = (TileEntityTeleporterController)tileEntity;
            if(teleporterController.active){
                return teleporterController;
            }
        }

        return null;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        TileEntityTeleporterController teleporterController = getTeleporterController();
        if(teleporterController != null){
            int canReceive = Math.min(Math.min(teleporterController.maxEnergyStored - teleporterController.energyStored, i), maxEnergyReceive);

            if(!b) {
                teleporterController.energyStored += canReceive;
                ThermalScience.packetHandler.sendPacketToDimension(worldObj.provider.dimensionId, new PacketEnergy(controllerX, controllerY, controllerZ, teleporterController.energyStored));
            }

            return canReceive;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        TileEntityTeleporterController teleporterController = getTeleporterController();
        if(teleporterController != null){
            return teleporterController.energyStored;
        }

        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        TileEntityTeleporterController teleporterController = getTeleporterController();
        if(teleporterController != null){
            return teleporterController.maxEnergyStored;
        }

        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        TileEntityTeleporterController teleporterController = getTeleporterController();
        return teleporterController != null && teleporterController.active && BlockTeleporterPowerTap.faceMap[facing] == forgeDirection.ordinal();
    }

    public void writeCustomToNBT(NBTTagCompound compound){
        compound.setByte(ThermalScienceNBTTags.Facing, facing);

        compound.setInteger(ThermalScienceNBTTags.XCoord, controllerX);
        compound.setInteger(ThermalScienceNBTTags.YCoord, controllerY);
        compound.setInteger(ThermalScienceNBTTags.ZCoord, controllerZ);
    }

    public void readCustomFromNBT(NBTTagCompound compound){
        facing = compound.getByte(ThermalScienceNBTTags.Facing);

        controllerX = compound.getInteger(ThermalScienceNBTTags.XCoord);
        controllerY = compound.getInteger(ThermalScienceNBTTags.YCoord);
        controllerZ = compound.getInteger(ThermalScienceNBTTags.ZCoord);
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
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldBlock != newBlock;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        if(getTeleporterController() == null){
            return list;
        }

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).toLowerCase().contains(" rf")){
                return list;
            }
        }

        list.add(getEnergyStored(null) + " / " + getMaxEnergyStored(null) + " RF");
        return list;
    }
}
