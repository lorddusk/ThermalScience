package com.creysys.ThermalScience.client.gui.guiScreen;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerCompressor;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Creysys on 31.01.2015.
 */
public class GuiCompressor extends GuiMachine {
    public GuiCompressor(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(tileEntity, ThermalScienceTextures.guiCompressor, new ContainerCompressor(inventory, tileEntity));

        xSize = 175;
        ySize = 150;

        energyX = 8;
        energyY = 12;
    }

    @Override
    public String getName() {
        return "Compressor";
    }
}
