package com.creysys.ThermalScience.container.slot;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Creysys on 07 Mar 15.
 */
public class SlotTeleporterController extends Slot {
    public SlotTeleporterController(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack != null && stack.getItem() == ThermalScience.itemTeleporterDestinationCard && stack.hasTagCompound() && stack.getTagCompound().hasKey(ThermalScienceNBTTags.Dim);
    }
}
