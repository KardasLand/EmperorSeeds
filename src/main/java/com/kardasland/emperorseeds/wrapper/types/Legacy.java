package com.kardasland.emperorseeds.wrapper.types;

import com.kardasland.emperorseeds.wrapper.Wrapper;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;


public class Legacy implements Wrapper {
    @Override
    public boolean isMaxAge(Block block) {
        return block.getState().getData() instanceof Crops && ((Crops) block.getState().getData()).getState() == CropState.RIPE;
    }

    @Override
    public boolean isFarmland(Block block) {
        return block.getType().equals(Material.getMaterial("SOIL"));
    }

    @Override
    public boolean isDispenser(Block block) {
        return block.getType().equals(Material.getMaterial("DISPENSER"));
    }

    @Override
    public BlockFace getBlockFace(Block block) {
        return ((org.bukkit.material.Dispenser) block.getState().getData()).getFacing();
    }

    @Override
    public ItemStack getIteminHand(Player p) {
        return p.getInventory().getItemInHand();
    }
}
