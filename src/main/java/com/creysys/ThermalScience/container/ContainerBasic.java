package com.creysys.ThermalScience.container;

import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 29.01.2015.
 */
public class ContainerBasic extends Container
{
    public TileEntityMachine tileEntity;

    public List<Slot> inputSlots;
    public List<Slot> outputSlots;

    public ContainerBasic(InventoryPlayer inventory, TileEntityMachine tileEntity, int inventoryX, int inventoryY) {
        this.tileEntity = tileEntity;

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventory, i, inventoryX + i * 18, inventoryY + 58));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventory, 9 + j + i * 9, inventoryX + j * 18, inventoryY + i * 18));
            }
        }

        inputSlots = new ArrayList<Slot>();
        outputSlots = new ArrayList<Slot>();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        if(inventorySlots.get(slot) instanceof Slot){
            Slot from = (Slot)inventorySlots.get(slot);

            if(!from.getHasStack())
            {
                return null;
            }

            IInventory to;
            if(from.inventory instanceof InventoryPlayer){
                to = tileEntity;
            }
            else if(from.inventory instanceof TileEntityMachine){
                to = player.inventory;
            }
            else {
                return null;
            }

            boolean success = false;
            for(int i = 0; i < to.getSizeInventory(); i++){
                ItemStack toStack = to.getStackInSlot(i);

                if(to instanceof TileEntityMachine && !tileEntity.isItemValidForSlot(i, toStack)){
                    return null;
                }

                if(toStack == null){
                    to.setInventorySlotContents(i, from.getStack().copy());
                    success = true;
                    break;
                }
                else if(ThermalScienceUtil.areStacksEqual(toStack, from.getStack()) && toStack.stackSize + from.getStack().stackSize <= from.getStack().getMaxStackSize()){
                    toStack.stackSize += from.getStack().stackSize;
                    success = true;
                    break;
                }
            }

            if(success){
                from.putStack(null);
            }
        }

        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return true;
    }

    public void addSlots(){
        for(int i = 0; i < inputSlots.size(); i++){
            addSlotToContainer(inputSlots.get(i));
        }

        for(int i = 0; i < outputSlots.size(); i++){
            addSlotToContainer(outputSlots.get(i));
        }
    }

    public Slot[] getInputSlots(){
        Slot[] slots = new Slot[inputSlots.size()];
        return inputSlots.toArray(slots);
    }
    public Slot[] getOutputSlots(){
        Slot[] slots = new Slot[outputSlots.size()];
        return outputSlots.toArray(slots);
    }
}
