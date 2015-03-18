package com.creysys.ThermalScience.tileEntity;

import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class TileEntityAssemblingMachine extends TileEntityMachine implements IFluidHandler {
    @Override
    public List<ThermalScienceRecipe> getRecipes() {
        return ThermalScienceRecipes.assemblingMachineRecipes;
    }

    @Override
    public int getCraftingInputSize() {
        return 2;
    }

    @Override
    public int getCraftingOutputSize() {
        return 1;
    }

    @Override
    public int[] getCraftingInputSlots() {
        return new int[]{0,1};
    }

    @Override
    public int[] getCraftingOutputSlots() {
        return new int[]{2};
    }

    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public int getCraftingSpeed() {
        return super.getCraftingSpeed() / 6;
    }

    @Override
    public String getInventoryName() {
        return "assemblingMachine";
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
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
        return false;
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
