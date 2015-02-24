package com.creysys.ThermalScience.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;

/**
 * Created by Creysys on 18 Feb 15.
 */
public class ServerProxy
{
    public void initialize(){};

    public void registerRenderers(){}

    public EntityPlayer getPlayerFromPacket(ChannelHandlerContext ctx){
        NetHandlerPlayServer handler = (NetHandlerPlayServer)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
        return handler.playerEntity;
    }
}
