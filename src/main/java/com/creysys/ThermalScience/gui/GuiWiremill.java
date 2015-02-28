package com.creysys.ThermalScience.gui;

import com.creysys.ThermalScience.container.ContainerCompressor;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class GuiWiremill extends GuiMachine {
    public GuiWiremill(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(inventory, tileEntity, "wiremill.png", new ContainerCompressor(inventory, tileEntity));


        arrowX = 78;
        arrowY = 29;
        arrowWidth = 23;
        arrowHeight = 16;

        xSize = 175;
        ySize = 150;

        energyX = 8;
        energyY = 12;
    }

    @Override
    public String getName() {
        return "Wiremill";
    }
}
