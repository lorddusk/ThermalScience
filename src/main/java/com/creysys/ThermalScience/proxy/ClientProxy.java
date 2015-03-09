package com.creysys.ThermalScience.proxy;

import com.creysys.ThermalScience.client.renderer.RendererEnergyRelay;
import com.creysys.ThermalScience.client.renderer.RendererSidedTexture;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterPowerTap;
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
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergyRelay.class, new RendererEnergyRelay());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.class, new RendererSidedTexture());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTeleporterController.class, new RendererSidedTexture());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTeleporterPowerTap.class, new RendererSidedTexture());
    }

    @Override
    public EntityPlayer getPlayerFromPacket(ChannelHandlerContext ctx){
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            return Minecraft.getMinecraft().thePlayer;
        }

        return super.getPlayerFromPacket(ctx);
    }
}
