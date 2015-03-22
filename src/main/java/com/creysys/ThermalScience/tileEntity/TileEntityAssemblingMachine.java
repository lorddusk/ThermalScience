package com.creysys.ThermalScience.tileEntity;

import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import com.creysys.ThermalScience.recipe.recipe.RecipeAssemblingMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.List;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class TileEntityAssemblingMachine extends TileEntityMachine implements IFluidHandler {

    public FluidStack fluid;
    public int capacity;
    public int fluidPerTick;

    public TileEntityAssemblingMachine(){
        super();

        capacity = 1000;
        fluidPerTick = 10;
    }

    @Override
    public boolean canCraft(ThermalScienceRecipe recipe, ItemStack[] stacks) {
        RecipeAssemblingMachine recipeAssemblingMachine = (RecipeAssemblingMachine)recipe;

        if(fluid == null && recipeAssemblingMachine.fluid != null){
            return false;
        }

        if(recipeAssemblingMachine.fluid != fluid.getFluid().getName() || recipeAssemblingMachine.fluidAmount > fluid.amount){
            return false;
        }

        return super.canCraft(recipe, stacks);
    }

    @Override
    public void itemsCrafted(ThermalScienceRecipe recipe, ItemStack[] stacks) {
        super.itemsCrafted(recipe, stacks);

        fluid.amount -= ((RecipeAssemblingMachine)recipe).fluidAmount;
    }

    @Override
    public List<ThermalScienceRecipe> getRecipes() {
        return ThermalScienceRecipes.recipesAssemblingMachine;
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
        if (!canFill(from, resource.getFluid())) {
            return 0;
        }

        int canFill = Math.min(resource.amount, fluidPerTick);
        if (fluid != null && fluid.amount > 0) {
            canFill = Math.min(Math.min(resource.amount, capacity - fluid.amount), fluidPerTick);
        }

        if (doFill) {
            if (fluid == null || fluid.amount == 0) {
                fluid = resource.copy();
                fluid.amount = canFill;
            } else {
                fluid.amount += canFill;
            }

            if(canFill > 0) {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }

        return canFill;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (!canDrain(from, resource.getFluid())) {
            return null;
        }

        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (fluid == null) {
            return null;
        }

        int canDrain = Math.min(Math.min(maxDrain, fluid.amount), fluidPerTick);

        if (doDrain) {
            fluid.amount -= canDrain;

            if(canDrain > 0) {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }

        FluidStack ret = fluid.copy();
        ret.amount = canDrain;
        return ret;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return this.fluid == null || this.fluid.amount == 0 || this.fluid.getFluid() == fluid;
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
    public void writeCustomToNBT(NBTTagCompound compound) {
        super.writeCustomToNBT(compound);

        if(fluid != null) {
            NBTTagCompound fluidCompound = new NBTTagCompound();
            fluid.writeToNBT(fluidCompound);
            compound.setTag(ThermalScienceNBTTags.Fluid, fluidCompound);
        }
    }

    @Override
    public void readCustomFromNBT(NBTTagCompound compound) {
        super.readCustomFromNBT(compound);

        if(compound.hasKey(ThermalScienceNBTTags.Fluid)){
            fluid = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag(ThermalScienceNBTTags.Fluid));
        }
    }
}
