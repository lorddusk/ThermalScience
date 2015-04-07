package com.creysys.ThermalScience.client.gui.guiScreen;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.container.ContainerAssemblingMachine;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityAssemblingMachine;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class GuiAssemblingMachine extends GuiMachine {

    public int fluidX = 150;
    public int fluidY = 12;
    public int fluidWidth = 18;
    public int fluidHeight = 41;

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

    @Override
    public void drawGuiContainerBackgroundLayer(float f1, int i1, int i2) {
        super.drawGuiContainerBackgroundLayer(f1, i1, i2);

        drawFluid();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i1, int i2) {
        super.drawGuiContainerForegroundLayer(i1, i2);

        drawFluidOverlay();
        RenderHelper.enableGUIStandardItemLighting();
    }

    private void drawFluid() {
        TileEntityAssemblingMachine assemblingMachine = (TileEntityAssemblingMachine) tileEntity;
        if (assemblingMachine.fluid != null) {
            drawFluid(assemblingMachine.fluid.amount, assemblingMachine.capacity, guiLeft, guiTop, assemblingMachine.fluid.getFluid());
        }
        else {
            drawFluid(0, assemblingMachine.capacity, guiLeft, guiTop, null);
        }
    }

    public void drawFluid(int amount,int maxAmount, int offsetX, int offsetY, Fluid fluid) {
        ThermalScienceUtil.setTexture(ThermalScienceTextures.guiFluid);
        ThermalScienceUtil.drawTexturedModalRect(offsetX + fluidX, offsetY + fluidY, 0, 0, fluidWidth, fluidHeight, fluidWidth, fluidHeight);

        if(fluid != null) {
            ThermalScienceUtil.setTexture(TextureMap.locationBlocksTexture);
            IIcon icon = fluid.getStillIcon();

            int y = (int) ((fluidHeight - 2) / (float) maxAmount * (float) (maxAmount - amount));
            for (int i = fluidHeight - 2 - y; i > 0; i -= icon.getIconHeight()) {
                ThermalScienceUtil.drawTexturedModalRectWithIcon(icon, offsetX + fluidX + 1, offsetY + fluidY + 1 + y, icon.getIconWidth(), Math.min(i, icon.getIconHeight()), true);
                y += icon.getIconHeight();
            }
        }
    }

    private void drawFluidOverlay(){
        TileEntityAssemblingMachine assemblingMachine = (TileEntityAssemblingMachine) tileEntity;
        int amount = 0;
        if (assemblingMachine.fluid != null) {
            amount = assemblingMachine.fluid.amount;
        }

        drawFluidOverlay(mouseX, mouseY, amount, assemblingMachine.capacity, fontRendererObj);
    }

    public void drawFluidOverlay(int mouseX, int mouseY, int amount, int maxAmount, FontRenderer fontRenderer){
        if(mouseX > fluidX && mouseX < fluidX + fluidWidth &&
                mouseY > fluidY && mouseY < fluidY + fluidHeight) {

            List list = new ArrayList();

            if(maxAmount > 0) {
                list.add(amount + "/" + maxAmount + " Mb");
            }
            else{
                list.add(amount + " Mb");
            }

            int x = mouseX;
            int y = mouseY;

            drawHoveringText(list, x, y, fontRenderer);
        }
    }
}
