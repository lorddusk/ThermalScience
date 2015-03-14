package com.creysys.ThermalScience.tileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

import java.nio.FloatBuffer;

/**
 * Created by Creysys on 14 Mar 15.
 */
public class TileEntityGravitationalTank extends TileEntity implements IFluidTank {

    public FluidStack fluid;
    public int capacity;
    public boolean updateRenderer;

    public TileEntityGravitationalTank(){
        capacity = 100000;
        updateRenderer = true;
    }

    @Override
    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public int getFluidAmount() {
        return fluid.amount;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public FluidTankInfo getInfo() {
        return new FluidTankInfo(fluid, capacity);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return null;
    }
}
