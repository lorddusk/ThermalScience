package com.creysys.ThermalScience.client.renderer;

import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.client.ISidedTextureProvider;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Creysys on 09 Mar 15.
 */
public class RendererSidedTexture extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f1) {
        ISidedTextureProvider tileEntitySided = (ISidedTextureProvider)tileEntity;

        for(int i = 0; i < 6; i++){
            ThermalScienceUtil.setTexture(tileEntitySided.getTextureFromSide(i));
            ThermalScienceUtil.renderBlockFace(i,x,y,z);
        }
    }
}
