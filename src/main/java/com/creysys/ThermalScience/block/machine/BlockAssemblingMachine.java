package com.creysys.ThermalScience.block.machine;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityAssemblingMachine;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Created by Creysys on 18 Mar 15.
 */
public class BlockAssemblingMachine extends BlockMachine {
    public BlockAssemblingMachine() {
        super("AssemblingMachine", TileEntityAssemblingMachine.class, ThermalScienceGuiID.AssemblingMachine);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        iconFrontOff = iconRegister.registerIcon(ThermalScienceTextures.assemblingMachineOff.icon);
        iconFrontOn = iconRegister.registerIcon(ThermalScienceTextures.assemblingMachineOn.icon);
    }

    @Override
    public int getCraftingSpeed(int meta) {
        return super.getCraftingSpeed(meta) / 6;
    }
}
