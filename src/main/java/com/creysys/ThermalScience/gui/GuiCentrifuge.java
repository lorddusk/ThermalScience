package com.creysys.ThermalScience.gui;

import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.container.ContainerCentrifuge;
import com.creysys.ThermalScience.network.ThermalSciencePacketHandler;
import com.creysys.ThermalScience.tileEntity.TileEntityCentrifuge;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Creysys on 1/31/2015.
 */
public class GuiCentrifuge extends GuiMachine {
    public GuiCentrifuge(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(inventory, tileEntity, "centrifuge.png", new ContainerCentrifuge(inventory, tileEntity));

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
