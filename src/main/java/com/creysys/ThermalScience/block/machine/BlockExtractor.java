package com.creysys.ThermalScience.block.machine;

import com.creysys.ThermalScience.ThermalScienceGuiID;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityExtractor;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Created by Creysys on 06 Apr 15.
 */
public class BlockExtractor extends BlockMachine {
    public BlockExtractor() {
        super("Extractor", TileEntityExtractor.class, ThermalScienceGuiID.Extractor);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        iconFrontOff = iconRegister.registerIcon(ThermalScienceTextures.extractorOff.icon);
        iconFrontOn = iconRegister.registerIcon(ThermalScienceTextures.extractorOn.icon);
    }

    @Override
    public int getCraftingSpeed(int meta) {
        int factor = (int)Math.ceil(10 - 5f / 3f * meta);
        return super.getCraftingSpeed(meta) / factor;
    }
}
