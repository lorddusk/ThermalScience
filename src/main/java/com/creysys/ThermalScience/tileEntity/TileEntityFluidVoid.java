package com.creysys.ThermalScience.tileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Creysys on 30 Mar 15.
 */
public class TileEntityFluidVoid extends TileEntity implements IFluidHandler{

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {

        if(worldObj.isRemote || worldObj.getTotalWorldTime() % 40 != 0){
            return;
        }

        for(int i = 0; i < 6; i++){
            TileEntity tileEntity = worldObj.getTileEntity(xCoord + ForgeDirection.values()[i].offsetX, yCoord + ForgeDirection.values()[i].offsetY, zCoord + ForgeDirection.values()[i].offsetZ);

            if(tileEntity instanceof TileEntityFluidVoid){
                continue;
            }

            if(tileEntity instanceof IFluidTank){
                IFluidTank fluidTank = (IFluidTank)tileEntity;
                fluidTank.drain(16000, true);
            }
            else if(tileEntity instanceof IFluidHandler) {
                IFluidHandler fluidTank = (IFluidHandler) tileEntity;
                fluidTank.drain(ForgeDirection.values()[i].getOpposite(), 16000, true);
            }
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return resource.amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }
}
