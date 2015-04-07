package com.creysys.ThermalScience.block.machine;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityCompressor;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Created by Creysys on 31.01.2015.
 */
public class BlockCompressor extends BlockMachine {
    public BlockCompressor() {
        super("Compressor", TileEntityCompressor.class, ThermalScienceGuiID.Compressor);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        iconFrontOff = iconRegister.registerIcon(ThermalScienceTextures.compressorOff.icon);
        iconFrontOn = iconRegister.registerIcon(ThermalScienceTextures.compressorOn.icon);
    }
}
