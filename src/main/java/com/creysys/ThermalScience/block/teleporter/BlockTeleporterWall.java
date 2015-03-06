package com.creysys.ThermalScience.block.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class BlockTeleporterWall extends Block {
    public IIcon icon;

    public BlockTeleporterWall() {
        super(Material.iron);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        String blockName = "blockTeleporterWall";
        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerBlock(this, blockName);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        icon = iconRegister.registerIcon(ThermalScience.modid + ":teleporter/wall");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icon;
    }
}
