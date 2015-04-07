package com.creysys.ThermalScience.container;

import com.creysys.ThermalScience.container.slot.SlotOutput;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class ContainerWiremill extends ContainerBasic {
    public ContainerWiremill(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(inventory, tileEntity, 8, 69);

        inputSlots.add(new Slot(tileEntity, 0, 44,22));
        outputSlots.add(new SlotOutput(tileEntity, 1, 117, 22));

        addSlots();
    }
}
