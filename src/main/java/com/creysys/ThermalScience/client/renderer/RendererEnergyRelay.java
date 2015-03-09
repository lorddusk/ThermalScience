package com.creysys.ThermalScience.client.renderer;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.block.BlockEnergyRelay;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
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
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        for(int i = 0; i < 6; i++) {
            setTexture(tileEntity, i);
            ThermalScienceUtil.renderBlockFace(i, x, y, z);
        }
    }

    private void setTexture(TileEntity tileEntity, int side){
        if(tileEntity instanceof TileEntityEnergyRelay){
            TileEntityEnergyRelay energyRelay = (TileEntityEnergyRelay)tileEntity;

            switch(energyRelay.sideConfigs[side]){
                case 0:
                    ThermalScienceUtil.setTexture(ThermalScienceTextures.energyRelayOff);
                    break;
                case 1:
                    ThermalScienceUtil.setTexture(ThermalScienceTextures.energyRelayIn);
                    break;
                case 2:
                    ThermalScienceUtil.setTexture(ThermalScienceTextures.energyRelayOut);
                    break;
            }
        }
    }
}
