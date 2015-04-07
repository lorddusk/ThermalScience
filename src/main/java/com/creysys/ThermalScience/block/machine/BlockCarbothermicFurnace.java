package com.creysys.ThermalScience.block.machine;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityCarbothermicFurnace;
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

        iconFrontOff = iconRegister.registerIcon(ThermalScienceTextures.carboThermicFurnaceOff.icon);
        iconFrontOn = iconRegister.registerIcon(ThermalScienceTextures.carboThermicFurnaceOn.icon);
    }
}
