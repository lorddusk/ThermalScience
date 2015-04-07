package com.creysys.ThermalScience.client.gui.guiScreen;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerCarbothermicFurnace;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Creysys on 1/31/2015.
 */
public class GuiCarbothermicFurnace extends GuiMachine {
    public GuiCarbothermicFurnace(InventoryPlayer inventory, TileEntityMachine tileEntity) {
        super(tileEntity, ThermalScienceTextures.guiCarbothermicFurnace, new ContainerCarbothermicFurnace(inventory, tileEntity));

        arrowX = 82;

        xSize = 175;
        ySize = 150;

        energyX = 8;
        energyY = 12;
    }

    @Override
    public String getName() {
        return "Carbothermic Furnace";
    }
}
