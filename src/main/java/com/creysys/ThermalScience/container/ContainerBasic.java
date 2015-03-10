package com.creysys.ThermalScience.container;

import com.creysys.ThermalScience.util.ThermalScienceUtil;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 29.01.2015.
 */
public class ContainerBasic extends Container
{
    public TileEntity tileEntity;

    public List<Slot> inputSlots;
    public List<Slot> outputSlots;

    public ContainerBasic(InventoryPlayer inventory, TileEntity tileEntity, int inventoryX, int inventoryY) {
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
        if(!(tileEntity instanceof IInventory)){
            return null;
        }


        IInventory tileEntityInventory = (IInventory)tileEntity;

        if(inventorySlots.get(slot) instanceof Slot){
            Slot from = (Slot)inventorySlots.get(slot);

            if(!from.getHasStack())
            {
                return null;
            }

            IInventory to;
            if(from.inventory instanceof InventoryPlayer){
                to = tileEntityInventory;
            }
            else if(from.inventory instanceof IInventory){
                to = player.inventory;
            }
            else {
                return null;
            }

            for(int i = 0; i < to.getSizeInventory(); i++){
                ItemStack toStack = to.getStackInSlot(i);

                if(to instanceof TileEntityMachine && !tileEntityInventory.isItemValidForSlot(i, toStack)){
                    return null;
                }

                if(toStack == null && to.isItemValidForSlot(i, from.getStack())){
                    to.setInventorySlotContents(i, from.getStack().copy());
                    from.putStack(null);
                    break;
                }
                else if(ThermalScienceUtil.areStacksEqual(toStack, from.getStack()) && toStack.stackSize < 64) {

                    int transferred = Math.min(64 - toStack.stackSize, from.getStack().stackSize);
                    toStack.stackSize += transferred;

                    if(from.getStack().stackSize - transferred <= 0){
                        from.putStack(null);
                        break;
                    }
                    else {
                        from.getStack().stackSize -= transferred;
                    }
                }
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
