package com.creysys.ThermalScience.client.gui.guiScreen;


import cofh.lib.util.helpers.ColorHelper;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerBasic;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 29.01.2015.
 */
public abstract class GuiMachine extends GuiContainer
{
    public ResourceLocation guiTexture;

    public TileEntityMachine tileEntity;

    public int energyX = 20;
    public int energyY = 30;
    public int energyWidth = 14;
    public int energyHeight = 42;

    public int arrowX = 75;
    public int arrowY = 22;
    public int arrowWidth = 23;
    public int arrowHeight = 16;

    public int mouseX;
    public int mouseY;

    public GuiMachine(TileEntityMachine tileEntity, ResourceLocation guiTexture, ContainerBasic container) {
        super(container);

        this.tileEntity = tileEntity;
        this.guiTexture = guiTexture;

        xSize = 176;
        ySize = 186;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1, int i1, int i2) {
        ThermalScienceUtil.setTexture(guiTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        drawCenteredString(fontRendererObj, getName(), guiLeft + xSize / 2, guiTop + 4, ColorHelper.DYE_WHITE);
        drawProgress();
        drawEnergy();

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i1, int i2) {
        drawEnergyOverlay();

        RenderHelper.enableGUIStandardItemLighting();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f1) {
        this.mouseX = mouseX - (width - xSize) / 2;
        this.mouseY = mouseY - (height - ySize) / 2;

        super.drawScreen(mouseX, mouseY, f1);
    }

    public void drawProgress(){
        drawProgress(tileEntity.getProgress(), guiLeft, guiTop);
    }

    public void drawProgress(int progress, int xOffset, int yOffset){
        ThermalScienceUtil.setTexture(ThermalScienceTextures.guiProgressArrowRight);
        ThermalScienceUtil.drawTexturedModalRect(xOffset + arrowX, yOffset + arrowY, 0, 0, arrowWidth, arrowHeight, 48, 16);

        if (progress > 0) {
            int width = Math.round((float) arrowWidth / 100f * (float) progress);
            ThermalScienceUtil.drawTexturedModalRect(xOffset + arrowX, yOffset + arrowY, 24, 0, width, arrowHeight, 48, 16);
        }
    }

    private void drawEnergy() {
        drawEnergy(tileEntity.energyStored, tileEntity.getMaxEnergyStored(null), guiLeft, guiTop);
    }

    public void drawEnergy(int energyStored, int maxEnergyStored,int xOffset, int yOffset) {
        ThermalScienceUtil.setTexture(ThermalScienceTextures.guiEnergy);
        ThermalScienceUtil.drawTexturedModalRect(xOffset + energyX, yOffset + energyY, 1, 0, energyWidth, energyHeight, 32, 64);

        if (energyStored > 0) {
            int cut = energyHeight - 2 - (int) Math.floor(40f / (float) maxEnergyStored * (float) energyStored);
            ThermalScienceUtil.drawTexturedModalRect(xOffset + energyX + 1, yOffset + energyY + cut + 1, 18, cut + 1, energyWidth - 2, energyHeight - cut - 2, 32, 64);
        }
    }

    private void drawEnergyOverlay(){
        drawEnergyOverlay(mouseX, mouseY, tileEntity.energyStored, tileEntity.getMaxEnergyStored(null));
    }

    public void drawEnergyOverlay(int mouseX, int mouseY, int energyStored, int maxEnergyStored){
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

            drawHoveringText(list, x, y, fontRendererObj);
        }
    }



    public Slot[] getInputSlots(){
        return ((ContainerBasic)inventorySlots).getInputSlots();
    }
    public Slot[] getOutputSlots(){
        return ((ContainerBasic)inventorySlots).getOutputSlots();
    }

    public abstract String getName();
}
