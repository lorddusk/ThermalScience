package com.creysys.ThermalScience.tileEntity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Creysys on 04 Feb 15.
 */
public class TileEntityWatermill extends TileEntity implements IEnergyProvider {

    public static final int rfPerTick = 5;

    public boolean active;

    public TileEntityWatermill(){
        active = false;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return getEnergyStored(from);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        if(active) {
            return rfPerTick;
        }
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return getEnergyStored(from);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return from == ForgeDirection.DOWN || from == ForgeDirection.UP;
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

        if(worldObj.getTotalWorldTime() % 10 == 0){
            checkWater();
        }

        if(active){
            if(!tryExtractEnergy(ForgeDirection.UP)){
                tryExtractEnergy(ForgeDirection.DOWN);
            }
        }
    }

    public boolean tryExtractEnergy(ForgeDirection dir){
        TileEntity tileEntity = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);

        if(tileEntity instanceof IEnergyHandler){
            IEnergyHandler energyHandler = (IEnergyHandler)tileEntity;

            if(!energyHandler.canConnectEnergy(dir.getOpposite())){
                return false;
            }

            if(energyHandler.receiveEnergy(dir.getOpposite(), getEnergyStored(dir), false) == 0){
                return false;
            }

            return true;
        }

        return false;
    }

    public void checkWater(){
        active = worldObj.getBlock(xCoord + 1, yCoord, zCoord) == Blocks.water && worldObj.getBlock(xCoord - 1, yCoord, zCoord) == Blocks.water && worldObj.getBlock(xCoord, yCoord, zCoord + 1) == Blocks.water && worldObj.getBlock(xCoord, yCoord, zCoord - 1) == Blocks.water;
    }
}
