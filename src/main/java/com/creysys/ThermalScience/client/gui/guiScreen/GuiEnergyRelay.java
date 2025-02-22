package com.creysys.ThermalScience.client.gui.guiScreen;

import cofh.lib.util.helpers.ColorHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.client.gui.element.GuiNumberField;
import com.creysys.ThermalScience.network.packet.PacketEnergyRelaySettings;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

/**
 * Created by Creysys on 13 Feb 15.
 */
public class GuiEnergyRelay extends GuiScreen {
    public TileEntityEnergyRelay tileEntity;

    public int guiX;
    public int guiY;
    public int guiWidth;
    public int guiHeight;

    public GuiNumberField inField;
    public GuiNumberField outField;
    public GuiNumberField selectedField;

    public GuiEnergyRelay(TileEntityEnergyRelay tileEntity) {
        super();

        this.tileEntity = tileEntity;

        guiWidth = 176;
        guiHeight = 51;

        mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        guiX = (width - guiWidth) / 2;
        guiY = (height - guiHeight) / 2;

        inField = new GuiNumberField(mc.fontRenderer, guiX + 10, guiY + 10, 60, 20);
        outField = new GuiNumberField(mc.fontRenderer, guiX + guiWidth - 70, guiY + 10, 60,20);

        loadSettings();
    }

    public void sendSettings(){
        int maxIn;
        int maxOut;

        try {
            maxIn = Integer.parseInt(inField.getText());
            maxOut = Integer.parseInt(outField.getText());
        }
        catch(Exception ex){
            return;
        }

        ThermalScience.packetHandler.sendPacketToServer(new PacketEnergyRelaySettings(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, maxIn, maxOut));
    }

    public void loadSettings() {
        inField.setText(String.valueOf(tileEntity.maxIn));
        outField.setText(String.valueOf(tileEntity.maxOut));
    }

    @Override
    public void onGuiClosed() {
        sendSettings();
    }

    @Override
    protected void mouseClicked(int x, int y, int p_73864_3_) {
        if(isInField(x, y, inField)){
            selectedField = inField;
            inField.setFocused(true);
            outField.setFocused(false);
        }
        else if(isInField(x,y, outField)) {
            selectedField = outField;
            outField.setFocused(true);
            inField.setFocused(false);
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
        if(selectedField != null){
            selectedField.textboxKeyTyped(c,i);

            if(c == '\r'){
                sendSettings();
            }
        }

        super.keyTyped(c, i);
    }

    public boolean isInField(int x, int y, GuiTextField field){
        return x >= field.xPosition && x <= field.xPosition + field.width && y >= field.yPosition && y <= field.yPosition + field.height;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float p_73863_3_) {
        ThermalScienceUtil.setTexture(ThermalScienceTextures.guiEnergyRelay);
        ThermalScienceUtil.drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);

        inField.drawTextBox();
        outField.drawTextBox();

        drawString(mc.fontRenderer, "Max IN", guiX + 10,guiY + 35, ColorHelper.DYE_CYAN);
        drawString(mc.fontRenderer, "Max OUT", guiX + guiWidth -70,guiY + 35, ColorHelper.DYE_CYAN);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
