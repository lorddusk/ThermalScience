package com.creysys.ThermalScience.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Creysys on 2/1/2015.
 */
public interface IThermalSciencePacket {
    void write(ChannelHandlerContext ctx, ByteBuf buffer);
    void read(ChannelHandlerContext ctx, ByteBuf buffer);
    void executeClientSide(EntityPlayer player);
    void executeServerSide(EntityPlayer player);
}
