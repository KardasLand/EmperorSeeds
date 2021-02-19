package com.kardasland.emperorseeds.wrapper;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Wrapper {
    public boolean isMaxAge(Block block);
    public boolean isFarmland(Block block);
    public boolean isDispenser(Block block);
    public BlockFace getBlockFace(Block block);
    public ItemStack getIteminHand(Player p);
}
