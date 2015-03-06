package com.creysys.ThermalScience.client.renderer;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.block.BlockEnergyRelay;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Creysys on 21 Feb 15.
 */
public class RendererEnergyRelay extends TileEntitySpecialRenderer {

    public static final ResourceLocation iconOffRes = new ResourceLocation(ThermalScience.MODID.toLowerCase(), "textures/blocks/energyRelayOff.png");
    public static final ResourceLocation iconInRes = new ResourceLocation(ThermalScience.MODID.toLowerCase(), "textures/blocks/energyRelayIn.png");
    public static final ResourceLocation iconOutRes = new ResourceLocation(ThermalScience.MODID.toLowerCase(), "textures/blocks/energyRelayOut.png");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

        Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);


        //-X
        setTexture(tileEntity, 2);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0, 0, 0, 0, 0);
        tessellator.addVertexWithUV(0, 1, 0, 0, 1);
        tessellator.addVertexWithUV(1, 1, 0, 1, 1);
        tessellator.addVertexWithUV(1, 0, 0, 1, 0);
        tessellator.addVertexWithUV(0, 0, 0, 0, 0);
        tessellator.addVertexWithUV(1, 0, 0, 1, 0);
        tessellator.addVertexWithUV(1, 1, 0, 1, 1);
        tessellator.addVertexWithUV(0, 1, 0, 0, 1);
        tessellator.draw();

        //+X
        setTexture(tileEntity, 3);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0, 0, 1, 0, 0);
        tessellator.addVertexWithUV(0, 1, 1, 0, 1);
        tessellator.addVertexWithUV(1, 1, 1, 1, 1);
        tessellator.addVertexWithUV(1, 0, 1, 1, 0);
        tessellator.addVertexWithUV(0, 0, 1, 0, 0);
        tessellator.addVertexWithUV(1, 0, 1, 1, 0);
        tessellator.addVertexWithUV(1, 1, 1, 1, 1);
        tessellator.addVertexWithUV(0, 1, 1, 0, 1);
        tessellator.draw();

        //-Z
        setTexture(tileEntity, 4);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0, 0, 1, 0, 0);
        tessellator.addVertexWithUV(0, 1, 1, 0, 1);
        tessellator.addVertexWithUV(0, 1, 0, 1, 1);
        tessellator.addVertexWithUV(0, 0, 0, 1, 0);
        tessellator.addVertexWithUV(0, 0, 1, 0, 0);
        tessellator.addVertexWithUV(0, 0, 0, 1, 0);
        tessellator.addVertexWithUV(0, 1, 0, 1, 1);
        tessellator.addVertexWithUV(0, 1, 1, 0, 1);
        tessellator.draw();

        //+Z
        setTexture(tileEntity, 5);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(1, 0, 1, 0, 0);
        tessellator.addVertexWithUV(1, 1, 1, 0, 1);
        tessellator.addVertexWithUV(1, 1, 0, 1, 1);
        tessellator.addVertexWithUV(1, 0, 0, 1, 0);
        tessellator.addVertexWithUV(1, 0, 1, 0, 0);
        tessellator.addVertexWithUV(1, 0, 0, 1, 0);
        tessellator.addVertexWithUV(1, 1, 0, 1, 1);
        tessellator.addVertexWithUV(1, 1, 1, 0, 1);
        tessellator.draw();

        //-Y
        setTexture(tileEntity, 0);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(1, 0, 1, 0, 0);
        tessellator.addVertexWithUV(0, 0, 1, 0, 1);
        tessellator.addVertexWithUV(0, 0, 0, 1, 1);
        tessellator.addVertexWithUV(1, 0, 0, 1, 0);
        tessellator.addVertexWithUV(1, 0, 1, 0, 0);
        tessellator.addVertexWithUV(1, 0, 0, 1, 0);
        tessellator.addVertexWithUV(0, 0, 0, 1, 1);
        tessellator.addVertexWithUV(0, 0, 1, 0, 1);
        tessellator.draw();

        //+Y
        setTexture(tileEntity, 1);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(1, 1, 1, 0, 0);
        tessellator.addVertexWithUV(0, 1, 1, 0, 1);
        tessellator.addVertexWithUV(0, 1, 0, 1, 1);
        tessellator.addVertexWithUV(1, 1, 0, 1, 0);
        tessellator.addVertexWithUV(1, 1, 1, 0, 0);
        tessellator.addVertexWithUV(1, 1, 0, 1, 0);
        tessellator.addVertexWithUV(0, 1, 0, 1, 1);
        tessellator.addVertexWithUV(0, 1, 1, 0, 1);
        tessellator.draw();

        GL11.glPopMatrix();
    }

    private void setTexture(TileEntity tileEntity, int side){
        if(tileEntity instanceof TileEntityEnergyRelay){
            TileEntityEnergyRelay energyRelay = (TileEntityEnergyRelay)tileEntity;

            switch(energyRelay.sideConfigs[side]){
                case 0:
                    ThermalScienceUtil.setTexture(iconOffRes);
                    break;
                case 1:
                    ThermalScienceUtil.setTexture(iconInRes);
                    break;
                case 2:
                    ThermalScienceUtil.setTexture(iconOutRes);
                    break;
            }
        }
    }
}
