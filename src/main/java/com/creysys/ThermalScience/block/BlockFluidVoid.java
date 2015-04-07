package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.tileEntity.TileEntityFluidVoid;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Creysys on 30 Mar 15.
 */
public class BlockFluidVoid extends BlockContainer {
    public BlockFluidVoid() {
        super(Material.rock);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        String blockName = "blockFluidVoid";
        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerBlock(this, blockName);
        GameRegistry.registerTileEntity(TileEntityFluidVoid.class, "tileEntityFluidVoid");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityFluidVoid();
    }
}
