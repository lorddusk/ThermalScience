package com.creysys.ThermalScience.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Creysys on 2/1/2015.
 */
public interface IThermalSciencePacket {
    public void write(ChannelHandlerContext ctx, ByteBuf buffer);
    public void read(ChannelHandlerContext ctx, ByteBuf buffer);
    public void executeClientSide(EntityPlayer player);
    public void executeServerSide(EntityPlayer player);
}
