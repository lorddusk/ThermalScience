package com.creysys.ThermalScience.proxy;

import com.creysys.ThermalScience.client.renderer.RendererEnergyRelay;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
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
    }

    public void registerRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergyRelay.class, new RendererEnergyRelay());
    }

    @Override
    public EntityPlayer getPlayerFromPacket(ChannelHandlerContext ctx){
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            return Minecraft.getMinecraft().thePlayer;
        }

        return super.getPlayerFromPacket(ctx);
    }
}
