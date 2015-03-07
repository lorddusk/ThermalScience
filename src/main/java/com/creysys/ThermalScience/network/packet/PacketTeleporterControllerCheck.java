package com.creysys.ThermalScience.network.packet;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.network.IThermalSciencePacket;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Creysys on 07 Mar 15.
 */
public class PacketTeleporterControllerCheck implements IThermalSciencePacket {

    public int x;
    public int y;
    public int z;
    public String statusText;
    public int statusTextColor;

    public PacketTeleporterControllerCheck(){}

    public PacketTeleporterControllerCheck(int x,int y, int z, String statusText, int statusTextColor){
        this.x = x;
        this.y = y;
        this.z = z;
        this.statusText = statusText;
        this.statusTextColor = statusTextColor;
    }

    @Override
    public void write(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);

        byte[] bytes = statusText.getBytes();
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);

        buffer.writeInt(statusTextColor);
    }

    @Override
    public void read(ChannelHandlerContext ctx, ByteBuf buffer) {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();

        statusText = new String(buffer.readBytes(buffer.readInt()).array());

        statusTextColor = buffer.readInt();
    }

    @Override
    public void executeClientSide(EntityPlayer player) {
        TileEntity tileEntity = player.worldObj.getTileEntity(x,y,z);

        if(tileEntity instanceof TileEntityTeleporterController){
            TileEntityTeleporterController teleporterController = (TileEntityTeleporterController)tileEntity;
            teleporterController.statusText = statusText;
            teleporterController.statusTextColor = statusTextColor;
        }
    }

    @Override
    public void executeServerSide(EntityPlayer player) {
        TileEntity tileEntity = player.worldObj.getTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityTeleporterController) {
            TileEntityTeleporterController teleporterController = (TileEntityTeleporterController) tileEntity;
            teleporterController.checkMultiblock();

            ThermalScience.packetHandler.sendPacketToDimension(player.worldObj.provider.dimensionId, new PacketTeleporterControllerCheck(x, y, z, teleporterController.statusText, teleporterController.statusTextColor));
        }
    }
}
