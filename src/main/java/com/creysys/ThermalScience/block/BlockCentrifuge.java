package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.gui.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.TileEntityCentrifuge;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Created by Creysys on 1/31/2015.
 */
public class BlockCentrifuge extends BlockMachine {
    public BlockCentrifuge() {
        super("Centrifuge", TileEntityCentrifuge.class, ThermalScienceGuiID.Centrifuge);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        iconFrontOff = iconRegister.registerIcon(ThermalScience.modid + ":centrifugeOff");
        iconFrontOn = iconRegister.registerIcon(ThermalScience.modid + ":centrifugeOn");
    }
}
