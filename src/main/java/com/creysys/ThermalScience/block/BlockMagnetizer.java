package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScienceGuiID;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.tileEntity.TileEntityMagnetizer;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Created by Creysys on 21 Mar 15.
 */
public class BlockMagnetizer extends BlockMachine {
    public BlockMagnetizer() {
        super("Magnetizer", TileEntityMagnetizer.class, ThermalScienceGuiID.Magnetizer);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        iconFrontOff = iconRegister.registerIcon(ThermalScienceTextures.magnetizerOff.icon);
        iconFrontOn = iconRegister.registerIcon(ThermalScienceTextures.magnetizerOn.icon);
    }
}
