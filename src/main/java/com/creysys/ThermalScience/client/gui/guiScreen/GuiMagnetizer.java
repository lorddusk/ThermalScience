package com.creysys.ThermalScience.client.gui.guiScreen;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerMagnetizer;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Creysys on 21 Mar 15.
 */
public class GuiMagnetizer extends GuiMachine {
    public GuiMagnetizer(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(tileEntity, ThermalScienceTextures.guiMagnetizer, new ContainerMagnetizer(inventory, tileEntity));

        xSize = 175;
        ySize = 150;

        energyX = 8;
        energyY = 12;
    }

    @Override
    public String getName() {
        return "Magnetizer";
    }
}
