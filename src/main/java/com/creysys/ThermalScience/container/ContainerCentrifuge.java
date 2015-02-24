package com.creysys.ThermalScience.container;

import com.creysys.ThermalScience.container.slot.SlotOutput;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

/**
 * Created by Creysys on 1/31/2015.
 */
public class ContainerCentrifuge extends ContainerBasic {
    public ContainerCentrifuge(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(inventory, tileEntity, 8 ,61);

        inputSlots.add(new Slot(tileEntity, 0, 44, 22));
        outputSlots.add(new SlotOutput(tileEntity, 1, 116, 13));
        outputSlots.add(new SlotOutput(tileEntity, 2, 134, 13));
        outputSlots.add(new SlotOutput(tileEntity, 3, 116, 31));
        outputSlots.add(new SlotOutput(tileEntity, 4, 134, 31));

        addSlots();
    }
}
