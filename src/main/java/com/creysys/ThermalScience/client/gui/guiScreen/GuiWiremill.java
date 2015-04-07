package com.creysys.ThermalScience.client.gui.guiScreen;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerCompressor;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class GuiWiremill extends GuiMachine {
    public GuiWiremill(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(tileEntity, ThermalScienceTextures.guiWiremill, new ContainerCompressor(inventory, tileEntity));

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
