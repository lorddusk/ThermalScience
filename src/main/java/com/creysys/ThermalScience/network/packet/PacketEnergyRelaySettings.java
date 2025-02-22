package com.creysys.ThermalScience.network.packet;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.network.IThermalSciencePacket;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Creysys on 15 Feb 15.
 */
public class PacketEnergyRelaySettings implements IThermalSciencePacket {

    public int x;
    public int y;
    public int z;

    public int maxIn;
    public int maxOut;

    public PacketEnergyRelaySettings(){

    }

    public PacketEnergyRelaySettings(int x,int y,int z,int maxIn, int maxOut) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.maxIn = maxIn;
        this.maxOut = maxOut;
    }

    @Override
    public void write(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);

        buffer.writeInt(maxIn);
        buffer.writeInt(maxOut);
    }

    @Override
    public void read(ChannelHandlerContext ctx, ByteBuf buffer) {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();

        maxIn = buffer.readInt();
        maxOut = buffer.readInt();
    }

    @Override
    public void executeClientSide(EntityPlayer player) {
    }

    @Override
    public void executeServerSide(EntityPlayer player) {
        TileEntity tileEntity = player.worldObj.getTileEntity(x,y,z);

        if(tileEntity instanceof TileEntityEnergyRelay) {
            TileEntityEnergyRelay energyRelay = (TileEntityEnergyRelay) tileEntity;

            energyRelay.setMaxIn(maxIn);
            energyRelay.setMaxOut(maxOut);
            player.worldObj.markBlockForUpdate(x, y, z);
        }
    }
}
