package com.creysys.ThermalScience.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by Creysys on 21 Feb 15.
 */
public class ContainerFake extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
