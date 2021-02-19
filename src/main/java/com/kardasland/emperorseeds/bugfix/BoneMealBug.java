package com.kardasland.emperorseeds.bugfix;

import com.kardasland.emperorseeds.Emperorseeds;
import com.kardasland.emperorseeds.seed.seed.Seed;
import com.kardasland.emperorseeds.seed.seed.SeedHandler;
import com.kardasland.emperorseeds.wrapper.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BoneMealBug implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void interact(PlayerInteractEvent e){
        if (Emperorseeds.instance.bonemeal){
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                Location loc = e.getClickedBlock().getLocation();
                Seed targetseed = new SeedHandler().findSeed(loc);
                if (targetseed != null){
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void dispense(BlockDispenseEvent event){
        if (Emperorseeds.instance.bonemeal){
            Wrapper wrapper = Emperorseeds.instance.getWrapper();
            if (wrapper.isDispenser(event.getBlock())){
                BlockFace face = wrapper.getBlockFace(event.getBlock());
                Block seed = event.getBlock().getRelative(face);
                Seed targetseed = new SeedHandler().findSeed(seed.getLocation());
                if (targetseed != null){
                    event.setCancelled(true);
                }
            }
        }
    }
}
