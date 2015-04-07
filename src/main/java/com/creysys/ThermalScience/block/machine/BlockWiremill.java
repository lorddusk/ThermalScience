package com.creysys.ThermalScience.block.machine;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityWiremill;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class BlockWiremill extends BlockMachine {
    public BlockWiremill() {
        super("Wiremill", TileEntityWiremill.class, ThermalScienceGuiID.Wiremill);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        iconFrontOff = iconRegister.registerIcon(ThermalScienceTextures.wiremillOff.icon);
        iconFrontOn = iconRegister.registerIcon(ThermalScienceTextures.wiremillOn.icon);
    }

    @Override
    public int getCraftingSpeed(int meta) {
        return super.getCraftingSpeed(meta) / 2;
    }
}
