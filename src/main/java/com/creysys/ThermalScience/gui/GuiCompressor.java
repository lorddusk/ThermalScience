package com.creysys.ThermalScience.gui;

import com.creysys.ThermalScience.container.ContainerCompressor;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Creysys on 31.01.2015.
 */
public class GuiCompressor extends GuiMachine {
    public GuiCompressor(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(tileEntity, "compressor.png", new ContainerCompressor(inventory, tileEntity));

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
