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
        capacity = 100000;
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
        fluid = resource;

        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
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
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
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
