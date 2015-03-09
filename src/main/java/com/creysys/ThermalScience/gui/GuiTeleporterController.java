package com.creysys.ThermalScience.gui;

import cofh.lib.util.helpers.ColorHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerTeleporterController;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 07 Mar 15.
 */
public class GuiTeleporterController extends GuiContainer {

    public TileEntityTeleporterController tileEntity;

    public int energyX = 8;
    public int energyY = 12;
    public int energyWidth = 14;
    public int energyHeight = 42;

    public int mouseX;
    public int mouseY;

    public GuiTeleporterController(InventoryPlayer inventory, TileEntityTeleporterController tileEntity) {
        super(new ContainerTeleporterController(inventory, tileEntity));

        this.tileEntity = tileEntity;

        xSize = 175;
        ySize = 150;
    }

    public void drawScreen(int mouseX, int mouseY, float p_73863_3_) {
        super.drawScreen(mouseX, mouseY, p_73863_3_);

        this.mouseX = mouseX - (width - xSize) / 2;
        this.mouseY = mouseY - (height - ySize) / 2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        ThermalScienceUtil.setTexture(ThermalScienceTextures.guiTeleporterController);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        drawCenteredString(fontRendererObj, "Teleporter Controller", guiLeft + xSize / 2, guiTop + 4, ColorHelper.DYE_WHITE);
        drawEnergy();
        drawEnergyOverlay();
        drawCenteredString(fontRendererObj, tileEntity.statusText, guiLeft + xSize / 2, guiTop + 50, tileEntity.statusTextColor);
    }

    private void drawEnergy(){
        drawEnergy(tileEntity.energyStored, tileEntity.maxEnergyStored,guiLeft, guiTop);
    }

    public void drawEnergy(int energyStored, int maxEnergyStored,int xOffset, int yOffset){
        ThermalScienceUtil.setTexture(ThermalScienceTextures.guiEnergy);
        ThermalScienceUtil.drawTexturedModalRect(xOffset + energyX, yOffset + energyY, 1, 0, energyWidth, energyHeight, 32, 64);

        if(energyStored > 0) {
            int cut = energyHeight - 2 - (int) Math.floor(40f / (float) maxEnergyStored * (float) energyStored);
            ThermalScienceUtil.drawTexturedModalRect(xOffset + energyX + 1, yOffset + energyY + cut + 1, 18, cut + 1, energyWidth - 2, energyHeight - cut - 2, 32, 64);
        }
    }

    private void drawEnergyOverlay(){
        drawEnergyOverlay(mouseX, mouseY, tileEntity.energyStored, tileEntity.maxEnergyStored);
    }

    private void drawEnergyOverlay(int mouseX, int mouseY, int energyStored, int maxEnergyStored){
        if(mouseX > energyX && mouseX < energyX + energyWidth &&
                mouseY > energyY && mouseY < energyY + energyHeight) {

            List list = new ArrayList();

            if(maxEnergyStored > 0) {
                list.add(energyStored + "/" + maxEnergyStored + " RF");
            }
            else{
                list.add(energyStored + " RF");
            }

            int x = mouseX;
            int y = mouseY;

            drawHoveringText(list, x + (width - xSize) / 2, y + (height - ySize) / 2, fontRendererObj);
        }
    }
}
