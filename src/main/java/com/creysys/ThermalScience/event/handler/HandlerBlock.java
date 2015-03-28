package com.creysys.ThermalScience.event.handler;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceWorldData;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import com.creysys.ThermalScience.util.DXYZ;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 25 Mar 15.
 */
public class HandlerBlock {
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        checkControllers(event.world, event.block, event.x, event.y, event.z);
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event){
        checkControllers(event.world, event.block, event.x, event.y, event.z);
    }

    public void checkControllers(World world, Block block, int x, int y, int z){
        if(world.isRemote){
            return;
        }

        if(block != ThermalScience.blockTeleporterController && block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterPowerTap){
            return;
        }

        List<DXYZ> positions = ThermalScienceWorldData.instance.mapControllerPositions;
        for(int i = 0; i < positions.size(); i++) {
            DXYZ pos = positions.get(i);
            if (pos.d != world.provider.dimensionId) {
                continue;
            }

            TileEntityTeleporterController controller = (TileEntityTeleporterController) world.getTileEntity(pos.x, pos.y, pos.z);
            if (controller == null) {
                continue;
            }

            if(controller.getDistanceFrom(x, y, z) < 256) {
                world.scheduleBlockUpdate(pos.x, pos.y, pos.z, ThermalScience.blockTeleporterController, 0);
            }
        }
    }
}
