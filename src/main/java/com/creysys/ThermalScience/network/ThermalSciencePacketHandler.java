package com.creysys.ThermalScience.network;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.network.packet.PacketEnergy;
import com.creysys.ThermalScience.network.packet.PacketEnergyRelaySettings;
import com.creysys.ThermalScience.network.packet.PacketMachineProgress;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;

import java.util.*;

/**
 * Created by Creysys on 2/1/2015.
 */

@ChannelHandler.Sharable
public class ThermalSciencePacketHandler extends MessageToMessageCodec<FMLProxyPacket, IThermalSciencePacket>{

    private EnumMap<Side, FMLEmbeddedChannel> channels;
    private LinkedList<Class<? extends IThermalSciencePacket>> packets = new LinkedList<Class<? extends IThermalSciencePacket>>();


    public ThermalSciencePacketHandler(){
        channels = NetworkRegistry.INSTANCE.newChannel(ThermalScience.MODID, this);

        packets.add(PacketEnergy.class);
        packets.add(PacketMachineProgress.class);
        packets.add(PacketEnergyRelaySettings.class);

        Collections.sort(packets, new Comparator<Class<? extends IThermalSciencePacket>>() {
            @Override
            public int compare(Class<? extends IThermalSciencePacket> o1, Class<? extends IThermalSciencePacket> o2) {
                int comp = String.CASE_INSENSITIVE_ORDER.compare(o1.getCanonicalName(), o2.getCanonicalName());

                if(comp == 0){
                    comp = o1.getCanonicalName().compareTo(o2.getCanonicalName());
                }

                return comp;
            }
        });
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IThermalSciencePacket msg, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer();

        Class<? extends  IThermalSciencePacket> packetClass = msg.getClass();

        byte packetID = (byte)packets.indexOf(packetClass);
        buffer.writeByte(packetID);

        msg.write(ctx, buffer);

        out.add(new FMLProxyPacket(buffer.copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get()));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
        ByteBuf buffer = msg.payload();

        byte packetID = buffer.readByte();

        IThermalSciencePacket packet = packets.get(packetID).newInstance();
        packet.read(ctx, buffer.slice());

        EntityPlayer player = ThermalScience.proxy.getPlayerFromPacket(ctx);

        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            packet.executeClientSide(player);
        }
        else {
            packet.executeServerSide(player);
        }
    }

    public void sendPacketToServer(IThermalSciencePacket packet){
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(packet);
    }

    public void sendPacketToAll(IThermalSciencePacket packet){
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendPacketToDimension(int dimension, IThermalSciencePacket packet){
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimension);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendPacketAroundPoint(NetworkRegistry.TargetPoint point, IThermalSciencePacket packet){
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }
}
