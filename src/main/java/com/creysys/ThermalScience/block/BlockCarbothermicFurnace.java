package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.gui.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.TileEntityCarbothermicFurnace;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Created by Creysys on 1/31/2015.
 */
public class BlockCarbothermicFurnace extends BlockMachine {
    public BlockCarbothermicFurnace() {
        super("CarbothermicFurnace", TileEntityCarbothermicFurnace.class, ThermalScienceGuiID.CarbothermicFurnace);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        iconFrontOff = iconRegister.registerIcon(ThermalScience.modid + ":carbothermicFurnaceOff");
        iconFrontOn = iconRegister.registerIcon(ThermalScience.modid + ":carbothermicFurnaceOn");
    }
}
