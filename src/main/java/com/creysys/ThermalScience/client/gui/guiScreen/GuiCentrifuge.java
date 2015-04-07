package com.creysys.ThermalScience.client.gui.guiScreen;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerCentrifuge;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Creysys on 1/31/2015.
 */
public class GuiCentrifuge extends GuiMachine {
    public GuiCentrifuge(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(tileEntity, ThermalScienceTextures.guiCentrifuge, new ContainerCentrifuge(inventory, tileEntity));

        xSize = 175;
        ySize = 142;

        energyX = 8;
        energyY = 12;
    }

    @Override
    public String getName() {
        return "Centrifuge";
    }
}
