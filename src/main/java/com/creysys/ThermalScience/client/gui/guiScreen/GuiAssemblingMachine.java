package com.creysys.ThermalScience.client.gui.guiScreen;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerAssemblingMachine;
import com.creysys.ThermalScience.container.ContainerBasic;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class GuiAssemblingMachine extends GuiMachine {
    public GuiAssemblingMachine(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(tileEntity, ThermalScienceTextures.guiAssemblingMachine, new ContainerAssemblingMachine(inventory, tileEntity));

        arrowX = 82;

        xSize = 175;
        ySize = 150;

        energyX = 8;
        energyY = 12;
    }

    @Override
    public String getName() {
        return "Assembling Machine";
    }
}
