package com.creysys.ThermalScience.container;

import com.creysys.ThermalScience.container.slot.SlotTeleporterController;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Creysys on 07 Mar 15.
 */
public class ContainerTeleporterController extends ContainerBasic {
    public ContainerTeleporterController(InventoryPlayer inventory, TileEntityTeleporterController tileEntity) {
        super(inventory, tileEntity, 8 , 61);

        inputSlots.add(new SlotTeleporterController(tileEntity, 0, 80,22));

        addSlots();
    }
}