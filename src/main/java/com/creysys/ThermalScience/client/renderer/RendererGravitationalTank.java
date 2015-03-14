package com.creysys.ThermalScience.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

/**
 * Created by Creysys on 14 Mar 15.
 */
public class RendererGravitationalTank extends TileEntitySpecialRenderer {

    Sphere sphere;

    public RendererGravitationalTank(){
        sphere = new Sphere();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f1) {
        int radius = 80;
        int halfRadius = 40;

        GL11.glPushMatrix();

        GL11.glTranslated(x, y + halfRadius, z);
        sphere.draw(radius, halfRadius, halfRadius);

        GL11.glPopMatrix();
    }
}
