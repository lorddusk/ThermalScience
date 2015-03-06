package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.gui.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.TileEntityWiremill;
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

        iconFrontOff = iconRegister.registerIcon(ThermalScience.modid + ":wiremillOff");
        iconFrontOn = iconRegister.registerIcon(ThermalScience.modid + ":wiremillOn");
    }
}
