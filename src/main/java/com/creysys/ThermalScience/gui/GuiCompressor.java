package com.creysys.ThermalScience.gui;

import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.container.ContainerCompressor;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Creysys on 31.01.2015.
 */
public class GuiCompressor extends GuiMachine {
    public GuiCompressor(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(inventory, tileEntity, "compressor.png", new ContainerCompressor(inventory, tileEntity));


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
        return "Compressor";
    }
}
