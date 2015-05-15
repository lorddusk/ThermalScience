package com.creysys.ThermalScience.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Creysys on 21 Feb 15.
 */
public interface IWailaBodyProvider {

    List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config);
}
