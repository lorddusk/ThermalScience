package com.creysys.ThermalScience.proxy;

import com.creysys.ThermalScience.client.renderer.RendererGravitationalTank;
import com.creysys.ThermalScience.event.ThermalScienceEventHandler;
import com.creysys.ThermalScience.tileEntity.TileEntityGravitationalTank;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Creysys on 18 Feb 15.
 */
public class ClientProxy extends ServerProxy
{
    @Override
    public void initialize() {
        registerRenderers();

        ThermalScienceEventHandler.register();
    }

    public void registerRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGravitationalTank.class, new RendererGravitationalTank());
    }

    @Override
    public EntityPlayer getPlayerFromPacket(ChannelHandlerContext ctx){
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            return Minecraft.getMinecraft().thePlayer;
        }

        return super.getPlayerFromPacket(ctx);
    }
}
