package com.creysys.ThermalScience.tileEntity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.StringHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.compat.waila.IWailaBodyProvider;
import com.creysys.ThermalScience.network.packet.PacketEnergyRelaySettings;
import com.creysys.ThermalScience.network.sync.ISyncEnergy;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Creysys on 13 Feb 15.
 */
public class TileEntityEnergyRelay extends TileEntity implements IEnergyHandler, IWailaBodyProvider, ISyncEnergy {

    public int maxEnergyStored;
    public int energyStored;
    public int maxOut;
    public int maxIn;

    public int[] sideConfigs;

    public TileEntityEnergyRelay() {
        maxEnergyStored = 10000;
        energyStored = 0;
        maxOut = 100;
        maxIn = 100;

        sideConfigs = new int[6];
    }

    public void setMaxIn(int i){
        maxIn = MathHelper.clamp_int(i, 0, 400);
    }

    public void setMaxOut(int  i){
        maxOut = MathHelper.clamp_int(i, 0, 400);
    }

    public void setSideConfig(int side){

        if(side < 0 || side >= 6){
            return;
        }

        sideConfigs[side]++;
        if(sideConfigs[side] >= 3){
            sideConfigs[side] = 0;
        }

        ThermalScience.packetHandler.sendPacketToDimension(worldObj.provider.dimensionId, new PacketEnergyRelaySettings(xCoord, yCoord, zCoord, maxIn, maxOut, sideConfigs));
    }

    public void setSideConfigs(int[] sideConfigs){
        this.sideConfigs = sideConfigs;
    }

    public void readCustomFromNBT(NBTTagCompound tag){
        energyStored = tag.getInteger(ThermalScienceNBTTags.EnergyStored);
        maxIn = tag.getInteger(ThermalScienceNBTTags.MaxIn);
        maxOut = tag.getInteger(ThermalScienceNBTTags.MaxOut);
        sideConfigs = tag.getIntArray(ThermalScienceNBTTags.SideConfigs);
    }

    public void writeCustomToNBT(NBTTagCompound tag){
        tag.setInteger(ThermalScienceNBTTags.EnergyStored, energyStored);
        tag.setInteger(ThermalScienceNBTTags.MaxIn, maxIn);
        tag.setInteger(ThermalScienceNBTTags.MaxOut, maxOut);
        tag.setIntArray(ThermalScienceNBTTags.SideConfigs, sideConfigs);
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b) {

        if(worldObj.isRemote || sideConfigs[forgeDirection.ordinal()] != 2){
            return 0;
        }

        int canExtract = Math.min(Math.min(energyStored, maxOut), i);

        if(!b){
            energyStored -= canExtract;

            ThermalScienceUtil.syncEnergy(worldObj,xCoord,yCoord,zCoord,energyStored,maxEnergyStored);
        }

        return canExtract;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {

        if(worldObj.isRemote || sideConfigs[forgeDirection.ordinal()] != 1){
            return 0;
        }

        int canReceive = Math.min(Math.min(maxEnergyStored - energyStored, maxIn), i);

        if(!b){
            energyStored += canReceive;

            ThermalScienceUtil.syncEnergy(worldObj, xCoord, yCoord, zCoord, energyStored, maxEnergyStored);
        }

        return canReceive;
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return energyStored;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return maxEnergyStored;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return sideConfigs[forgeDirection.ordinal()] != 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {

        if(worldObj.isRemote){
            return;
        }

        for(int i = 0; i < 6; i++){
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity tileEntity = worldObj.getTileEntity(xCoord + dir.offsetX,yCoord + dir.offsetY,zCoord + dir.offsetZ);
            if(tileEntity instanceof IEnergyReceiver && !(tileEntity instanceof TileEntityEnergyRelay)){
                IEnergyReceiver energyReceiver = (IEnergyReceiver)tileEntity;

                if(sideConfigs[i] == 2 && energyReceiver.canConnectEnergy(dir.getOpposite())){
                    int canExtract = extractEnergy(dir, maxOut, true);
                    int extracted = energyReceiver.receiveEnergy(dir.getOpposite(), canExtract, false);

                    if(extracted > 0){
                        extractEnergy(dir, extracted, false);
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readCustomFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeCustomToNBT(tag);
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
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldBlock != newBlock;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        boolean b = true;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).toLowerCase().contains(" rf")){
                b = false;
                break;
            }
        }

        if(b) {
            list.add(energyStored + " / " + maxEnergyStored + " RF");
        }

        String sideMode = StringHelper.GRAY + "Deactivated";
        switch(sideConfigs[accessor.getSide().ordinal()]){
            case 1:
                sideMode = StringHelper.BRIGHT_GREEN + "Input";
                break;
            case 2:
                sideMode = StringHelper.LIGHT_RED + "Output";
                break;
        }

        list.add(StringHelper.WHITE + "Current Side: " + sideMode);

        return list;
    }

    @Override
    public void setEnergy(int i) {
        energyStored = i;
    }
}
