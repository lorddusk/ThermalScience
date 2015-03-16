package com.creysys.ThermalScience.tileEntity;

import com.creysys.ThermalScience.ThermalScienceNBTTags;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Creysys on 14 Mar 15.
 */
public class TileEntityGravitationalTank extends TileEntity implements IFluidHandler {

    public FluidStack fluid;
    public int capacity;
    public boolean updateRenderer;

    public TileEntityGravitationalTank(){
        capacity = 10000000;
        updateRenderer = true;
    }

    public void writeCustomToNBT(NBTTagCompound compound){
        if(fluid != null) {
            NBTTagCompound fluidCompound = new NBTTagCompound();
            fluid.writeToNBT(fluidCompound);
            compound.setTag(ThermalScienceNBTTags.Fluid, fluidCompound);
        }
    }

    public void readCustomFromNBT(NBTTagCompound compound){
        if(compound.hasKey(ThermalScienceNBTTags.Fluid)){
            fluid = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag(ThermalScienceNBTTags.Fluid));
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if(!canFill(from, resource.getFluid()))
        {
            return 0;
        }

        int canFill = resource.amount;
        if(fluid != null){
            canFill = Math.min(resource.amount, capacity - fluid.amount);
        }

        if(doFill){
            if(fluid == null){
                fluid = resource.copy();
            }
            else
            {
                fluid.amount += canFill;
            }

            worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        }

        return canFill;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if(!canDrain(from, resource.getFluid())){
            return null;
        }

        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if(fluid == null){
            return null;
        }

        int canDrain = Math.min(maxDrain, fluid.amount);

        if(doDrain){
            fluid.amount -= canDrain;

            worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        }

        FluidStack ret = fluid.copy();
        ret.amount = canDrain;
        return ret;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return this.fluid == null || this.fluid.getFluid() == fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return this.fluid != null && this.fluid.getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{new FluidTankInfo(fluid, capacity)};
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readCustomFromNBT(pkt.func_148857_g());
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
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
}
