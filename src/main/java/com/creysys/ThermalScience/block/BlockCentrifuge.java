package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.ThermalScienceGuiID;
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

        iconFrontOff = iconRegister.registerIcon(ThermalScienceTextures.centrifugeOff.icon);
        iconFrontOn = iconRegister.registerIcon(ThermalScienceTextures.centrifugeOn.icon);
    }

    @Override
    public int getCraftingSpeed(int meta) {
        return super.getCraftingSpeed(meta) / 4;
    }
}
