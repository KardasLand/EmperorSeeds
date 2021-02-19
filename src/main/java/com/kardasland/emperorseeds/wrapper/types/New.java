package com.kardasland.emperorseeds.wrapper.types;

import com.kardasland.emperorseeds.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class New implements Wrapper {

    @Override
    public boolean isMaxAge(Block block) {
        BlockData bdata = block.getBlockData();
        if (bdata instanceof Ageable){
           return ((Ageable) bdata).getAge() == ((Ageable) bdata).getMaximumAge();
        }else {
            return false;
        }
    }

    @Override
    public boolean isFarmland(Block block) {
        return block.getType().equals(Material.FARMLAND);
    }

    @Override
    public boolean isDispenser(Block block) {
        return block.getType().equals(Material.DISPENSER);
    }

    @Override
    public BlockFace getBlockFace(Block block) {
        Dispenser dispenserface = (Dispenser) block.getBlockData();
        BlockFace face = dispenserface.getFacing();
        return face;
    }

    @Override
    public ItemStack getIteminHand(Player p) {
        return p.getInventory().getItemInMainHand();
    }
}
