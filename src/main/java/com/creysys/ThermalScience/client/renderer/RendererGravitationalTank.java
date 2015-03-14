package com.creysys.ThermalScience.client.renderer;

import com.creysys.ThermalScience.tileEntity.TileEntityGravitationalTank;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Creysys on 14 Mar 15.
 */
public class RendererGravitationalTank extends TileEntitySpecialRenderer {

    public FloatBuffer vertices;
    public FloatBuffer normals;
    public FloatBuffer texCoords;
    public ByteBuffer indices;

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f1) {

        if(!(tileEntity instanceof TileEntityGravitationalTank)){
            return;
        }

        TileEntityGravitationalTank gravitationalTank = (TileEntityGravitationalTank)tileEntity;
        if(gravitationalTank.updateRenderer || vertices == null || normals == null || texCoords == null || indices == null || true){
            updateRenderer(gravitationalTank, 10, 10, 10);
        }

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GL11.glVertexPointer(3, GL11.GL_FLOAT, vertices);
        GL11.glNormalPointer(GL11.GL_FLOAT, normals);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, texCoords);
        GL11.glDrawElements(GL11.GL_QUADS, indices.capacity(), GL11.GL_UNSIGNED_SHORT, indices);

        GL11.glPopMatrix();
    }

    public void updateRenderer(TileEntityGravitationalTank gravitationalTank, float radius, int rings, int sectors){
        vertices = BufferUtils.createFloatBuffer(rings * sectors * 3);
        normals = BufferUtils.createFloatBuffer(rings * sectors * 3);
        texCoords = BufferUtils.createFloatBuffer(rings * sectors * 2);
        indices = BufferUtils.createByteBuffer(rings * sectors * 4);

        float R = 1 / (rings - 1);
        float S = 1 / (sectors - 1);

        float piOverTwo = (float)(Math.PI / 2);
        float piTimesTwo = (float)(Math.PI * 2);

        for(int r = 0; r < rings; r++){
            for(int s = 0; s < sectors; s++){
                float x = (float)(Math.cos(piTimesTwo * s * S) * Math.sin(Math.PI * r * R));
                float y = (float)Math.sin(- piOverTwo + Math.PI * r * R);
                float z = (float)(Math.sin(piTimesTwo * s * S) * Math.sin(Math.PI * r * R));

                vertices.put(x * radius);
                vertices.put(y * radius);
                vertices.put(z * radius);

                normals.put(x);
                normals.put(y);
                normals.put(z);

                texCoords.put(s * S);
                texCoords.put(r * R);

                if(r < rings - 1 && s < sectors - 1){
                    indices.put((byte)(r * sectors + s));
                    indices.put((byte)(r * sectors + s + 1));
                    indices.put((byte)((r + 1) * sectors + s + 1));
                    indices.put((byte)((r + 1) * sectors + s));
                }
            }
        }
    }
}
