package com.creysys.ThermalScience.network.packet;

import com.creysys.ThermalScience.network.IThermalSciencePacket;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Creysys on 2/1/2015.
 */
public class PacketMachineProgress implements IThermalSciencePacket {

    public int x;
    public int y;
    public int z;

    public int craftingEnergyNeeded;
    public int craftingEnergy;
    public boolean active;

    public PacketMachineProgress(){}
    public PacketMachineProgress(int x, int y, int z, int craftingEnergyNeeded, int craftingEnergy, boolean active){
        this.x = x;
        this.y = y;
        this.z = z;

        this.craftingEnergyNeeded = craftingEnergyNeeded;
        this.craftingEnergy = craftingEnergy;
        this.active = active;
    }

    @Override
    public void write(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);

        buffer.writeInt(craftingEnergyNeeded);
        buffer.writeInt(craftingEnergy);
        buffer.writeBoolean(active);
    }

    @Override
    public void read(ChannelHandlerContext ctx, ByteBuf buffer) {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();

        craftingEnergyNeeded = buffer.readInt();
        craftingEnergy = buffer.readInt();
        active = buffer.readBoolean();
    }

    @Override
    public void executeClientSide(EntityPlayer player) {
        TileEntity tileEntity = player.worldObj.getTileEntity(x, y, z);

        if(tileEntity instanceof TileEntityMachine) {
            TileEntityMachine machine = (TileEntityMachine) tileEntity;
            machine.craftingEnergyNeeded = craftingEnergyNeeded;
            machine.craftingEnergy = craftingEnergy;
            machine.active = active;
            player.worldObj.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
        }
    }

    @Override
    public void executeServerSide(EntityPlayer player) {

    }
}
