package com.creysys.ThermalScience.tileEntity;

import cofh.api.energy.IEnergyReceiver;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.compat.waila.IWailaBodyProvider;
import com.creysys.ThermalScience.network.packet.PacketMachineProgress;
import com.creysys.ThermalScience.network.sync.ISyncEnergy;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 29.01.2015.
 */
public abstract class TileEntityMachine extends TileEntity implements IEnergyReceiver, ISidedInventory, IWailaBodyProvider, ISyncEnergy
{
    public int energyStored;
    public int maxEnergyStored;
    public int maxEnergyReceive;

    public int craftingEnergy;
    public int craftingEnergyNeeded;
    public ItemStack[] craftingOutputs;
    public ItemStack[] craftingInputs;

    public byte facing;

    public ItemStack[] slots;

    public TileEntityMachine(int maxEnergyStored, int maxEnergyReceive)
    {
        energyStored = 0;
        this.maxEnergyStored = maxEnergyStored;
        this.maxEnergyReceive = maxEnergyReceive;

        craftingEnergy = 0;
        craftingEnergyNeeded = 0;
        craftingOutputs = null;
        craftingInputs = null;

        facing = 2;

        slots = new ItemStack[getSizeInventory()];
    }


    public ArrayList<ItemStack> getDrops(){
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

        for(int i = 0; i < getSizeInventory(); i++){
            ItemStack stack = getStackInSlot(i);
            if(stack != null){
                drops.add(stack);
            }
        }

        if(craftingInputs != null) {
            for (int i = 0; i < craftingInputs.length; i++) {
                drops.add(craftingInputs[i]);
            }
        }

        return drops;
    }

    public void setActive(boolean active){
        int meta = (int)facing;

        if(active){
            meta += 10;
        }

        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
    }

    public int getProgress() {
        if(craftingEnergyNeeded == 0){
            return 0;
        }

        return (int) Math.round(100f / (float) craftingEnergyNeeded * (float) craftingEnergy);
    }

    public void writeCustomToNBT(NBTTagCompound compound)
    {
        compound.setByte(ThermalScienceNBTTags.Facing,facing);

        compound.setInteger(ThermalScienceNBTTags.EnergyStored, energyStored);

        compound.setInteger(ThermalScienceNBTTags.CraftingEnergy, craftingEnergy);
        compound.setInteger(ThermalScienceNBTTags.CraftingEnergyNeeded, craftingEnergyNeeded);

        if(craftingOutputs != null) {
            ThermalScienceUtil.writeStacksToNBT(craftingOutputs, compound, ThermalScienceNBTTags.CraftingOutputs);
        }
        if(craftingInputs != null){
            ThermalScienceUtil.writeStacksToNBT(craftingInputs, compound, ThermalScienceNBTTags.CraftingInputs);
        }

        ThermalScienceUtil.writeStacksToNBT(slots, compound, ThermalScienceNBTTags.Slots);
    }

    public void readCustomFromNBT(NBTTagCompound compound)
    {
        facing = compound.getByte(ThermalScienceNBTTags.Facing);

        energyStored = compound.getInteger(ThermalScienceNBTTags.EnergyStored);


        craftingEnergy = compound.getInteger(ThermalScienceNBTTags.CraftingEnergy);
        craftingEnergyNeeded = compound.getInteger(ThermalScienceNBTTags.CraftingEnergyNeeded);

        if(compound.hasKey(ThermalScienceNBTTags.CraftingOutputs)){
            craftingOutputs = ThermalScienceUtil.readStacksFromNBT(getCraftingOutputSize(),compound,ThermalScienceNBTTags.CraftingOutputs);
        }
        if(compound.hasKey(ThermalScienceNBTTags.CraftingInputs)){
            craftingInputs = ThermalScienceUtil.readStacksFromNBT(getCraftingInputSize(),compound,ThermalScienceNBTTags.CraftingInputs);
        }

        slots = ThermalScienceUtil.readStacksFromNBT(getSizeInventory(), compound, ThermalScienceNBTTags.Slots);
    }

    public void updateCrafting(boolean checkRecipe) {
        if (checkRecipe || worldObj.getTotalWorldTime() % 10 == 0) {
            if(craftingOutputs != null){
                return;
            }

            ItemStack[] inputs = new ItemStack[getCraftingInputSize()];
            int[] inputSlots = getCraftingInputSlots();

            for(int i = 0; i < inputSlots.length; i++){
                inputs[i] = slots[inputSlots[i]];
            }

            ThermalScienceRecipe recipe = ThermalScienceUtil.getMatchingRecipe(getRecipes(), inputs);
            if(recipe == null){
                return;
            }

            ItemStack[] outputs = new ItemStack[recipe.outputs.length];
            for(int i = 0; i < outputs.length; i++){
                outputs[i] = ThermalScienceUtil.getStack(recipe.outputs[i]);
            }

            if(energyStored >= recipe.energy && canCraft(outputs)){
                craftingEnergyNeeded = recipe.energy;
                craftingEnergy = 0;
                craftingOutputs = outputs;

                for(int i = 0; i < recipe.inputs.length; i++){
                    ItemStack stack = ThermalScienceUtil.getStack(recipe.inputs[i]);

                    int index = ThermalScienceUtil.getIndexOfItem(recipe.inputs[i], inputs);
                    if(index == -1){
                        return;
                    }

                    inputs[index].stackSize -= stack.stackSize;
                    if(inputs[index].stackSize <= 0){
                        inputs[index] = null;
                        slots[index] = null;
                    }
                }

                ItemStack[] craftingInputs = new ItemStack[recipe.inputs.length];
                for(int i = 0; i < craftingInputs.length; i++){
                    craftingInputs[i] = ThermalScienceUtil.getStack(recipe.inputs[i]).copy();
                }

                this.craftingInputs = craftingInputs;

                setActive(true);

                ThermalScience.packetHandler.sendPacketToDimension(worldObj.provider.dimensionId, new PacketMachineProgress(xCoord, yCoord, zCoord, craftingEnergyNeeded, craftingEnergy));
            }

        } else if(craftingOutputs != null){
            int energyUse = Math.min(Math.min(craftingEnergyNeeded - craftingEnergy, getCraftingSpeed()), energyStored);
            craftingEnergy += energyUse;
            energyStored -= energyUse;

            if(craftingEnergy >= craftingEnergyNeeded){
                craftingEnergy = 0;

                itemsCrafted(craftingOutputs);

                craftingOutputs = null;
                craftingInputs = null;
                craftingEnergy = 0;
                craftingEnergyNeeded = 0;

                setActive(false);
            }

            ThermalScience.packetHandler.sendPacketToDimension(worldObj.provider.dimensionId, new PacketMachineProgress(xCoord, yCoord, zCoord, craftingEnergyNeeded, craftingEnergy));
            ThermalScienceUtil.syncEnergy(worldObj, xCoord, yCoord, zCoord, energyStored, maxEnergyStored);

            updateCrafting(true);
        }
    }

    public void itemsCrafted(ItemStack[] stacks) {
        if (stacks != null && stacks.length > 0) {
            for (int i = 0; i < stacks.length; i++) {
                for (int j = 0; j < getCraftingOutputSize(); j++) {
                    int slot = getCraftingOutputSlots()[j];
                    if (slots[slot] == null) {
                        slots[slot] = stacks[i];
                        break;
                    } else if (ThermalScienceUtil.areStacksEqual(slots[slot], stacks[i]) && slots[slot].stackSize + stacks[i].stackSize <= stacks[i].getMaxStackSize()) {
                        slots[slot].stackSize += stacks[i].stackSize;
                        break;
                    }
                }
            }
        }
    }

    public boolean canCraft(ItemStack[] stacks){
        if (stacks != null && stacks.length > 0) {

            ArrayList<Integer> occupiedSlots = new ArrayList<Integer>();

            for (int i = 0; i < stacks.length; i++) {
                boolean success = false;

                for (int j = 0; j < getCraftingOutputSize(); j++) {
                    int slot = getCraftingOutputSlots()[j];

                    boolean occupied = false;

                    for(int k = 0; k < occupiedSlots.size(); k++){
                        if(occupiedSlots.get(k) == slot){
                            occupied = true;
                        }
                    }

                    if(occupied){
                        continue;
                    }

                    if (slots[slot] == null || (ThermalScienceUtil.areStacksEqual(slots[slot], stacks[i]) && slots[slot].stackSize + stacks[i].stackSize <= stacks[i].getMaxStackSize())) {
                        success = true;
                        occupiedSlots.add(slot);
                        break;
                    }
                }

                if(!success){
                    return false;
                }
            }
        }

        return true;
    }

    public int getCraftingSpeed(){
        return maxEnergyReceive / 2;
    }

    public abstract  List<ThermalScienceRecipe> getRecipes();
    public abstract int getCraftingInputSize();
    public abstract int getCraftingOutputSize();
    public abstract int[] getCraftingInputSlots();
    public abstract int[] getCraftingOutputSlots();

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).toLowerCase().contains(" rf")){
                return list;
            }
        }

        list.add(energyStored + " / " + maxEnergyStored + " RF");
        return list;
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
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

        maxReceive = Math.min(maxReceive, maxEnergyReceive);

        int receive = MathHelper.clamp_int(maxEnergyStored - energyStored, 0, maxReceive);

        if(!simulate && receive > 0) {
            energyStored += receive;

            ThermalScienceUtil.syncEnergy(worldObj, xCoord, yCoord, zCoord, energyStored, maxEnergyStored);
        }

        return receive;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return energyStored;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return maxEnergyStored;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
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
    public  ItemStack decrStackSize(int slot, int current){
        if (slots[slot] != null)
        {
            ItemStack itemstack;

            if (slots[slot].stackSize <= current)
            {
                itemstack = slots[slot];
                slots[slot] = null;

                return itemstack;
            }
            else
            {
                itemstack = slots[slot].splitStack(current);

                if (slots[slot].stackSize == 0)
                {
                    slots[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote) {
            updateCrafting(false);
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack){
        slots[slot] = stack;

        if(!worldObj.isRemote) {
            updateCrafting(true);
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot){
        return slots[slot];
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side){
        int[] sides = new int[getSizeInventory()];
        for(int i = 0; i < sides.length; i++){
            sides[i] = i;
        }
        return sides;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return isItemValidForSlot(slot, stack);
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        for (int i = 0; i < getCraftingInputSize(); i++) {
            if (getCraftingInputSlots()[i] == slot) {
                return true;
            }
        }
        return false;
    }

    public boolean canExtractItem(int slot, ItemStack stack, int side){
        for (int i = 0; i < getCraftingOutputSize(); i++) {
            if (getCraftingOutputSlots()[i] == slot) {
                return true;
            }
        }
        return false;
    }

    @Override
    public abstract int getSizeInventory();
    @Override
    public abstract String getInventoryName();
}
