package com.creysys.ThermalScience.container;

import com.creysys.ThermalScience.container.slot.SlotOutput;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class ContainerAssemblingMachine extends ContainerBasic {
    public ContainerAssemblingMachine(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(inventory, tileEntity, 8, 61);

        inputSlots.add(new Slot(tileEntity, 0, 35, 22));
        inputSlots.add(new Slot(tileEntity, 1, 53, 22));
        outputSlots.add(new SlotOutput(tileEntity, 2, 117, 22));

        addSlots();
    }
}
