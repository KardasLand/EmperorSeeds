package com.kardasland.emperorseeds.event;

import com.kardasland.emperorseeds.Emperorseeds;
import com.kardasland.emperorseeds.seed.harvest.Harvest;
import com.kardasland.emperorseeds.seed.harvest.HarvestHandler;
import com.kardasland.emperorseeds.seed.seed.Seed;
import com.kardasland.emperorseeds.seed.seed.SeedHandler;
import com.kardasland.emperorseeds.utils.Araclar;
import com.kardasland.emperorseeds.utils.ConfigManager;
import com.kardasland.emperorseeds.utils.CooldownHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class BlockTrample implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void check(PlayerInteractEvent e){
        if (e.getAction().equals(Action.PHYSICAL)){
            Block luk = e.getClickedBlock().getRelative(BlockFace.UP);
            Location loc = luk.getLocation();
            Seed targetseed = new SeedHandler().findSeed(loc);
            if (targetseed != null){
                if (CooldownHandler.isInCooldown(e.getPlayer().getUniqueId(), "place_cooldown")){
                    new Araclar().prefix(e.getPlayer(), ConfigManager.get("messages.yml").getString("still-in-cooldown").replace("%second%", String.valueOf(CooldownHandler.getTimeLeft(e.getPlayer().getUniqueId(), "place_cooldown"))));
                    e.setCancelled(true);
                }else {
                    HarvestHandler harvestHandler = new HarvestHandler();
                    if(Emperorseeds.instance.getWrapper().isMaxAge(luk)){
                        loc.getWorld().dropItemNaturally(loc, harvestHandler.getHarvestItem(new Harvest(targetseed)));
                        loc.getWorld().dropItemNaturally(loc, harvestHandler.getSeedItem(targetseed));
                    }else {
                        loc.getWorld().dropItemNaturally(loc, harvestHandler.getSeedItem(targetseed, 1));
                    }
                    luk.setType(Material.AIR);
                    List<String> list = ConfigManager.get("data.yml").getStringList("data."+targetseed.getType());
                    StringBuilder sm = new StringBuilder();
                    sm.append(loc.getWorld().getName()).append(";");
                    sm.append(loc.getBlockX()).append(";");
                    sm.append(loc.getBlockY()).append(";");
                    sm.append(loc.getBlockZ());
                    if (list.contains(sm.toString())){
                        list.remove(sm.toString());
                    }
                    ConfigManager.get("data.yml").set("data."+targetseed.getType(), list);
                    ConfigManager.save("data.yml");
                    ConfigManager.reload("data.yml");
                }
            }
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void ch(BlockFromToEvent event){
        Block crop = event.getToBlock();
        Location loc = crop.getLocation();
        Seed targetseed = new SeedHandler().findSeed(loc);
        if (targetseed != null){
            HarvestHandler harvestHandler = new HarvestHandler();
            if(Emperorseeds.instance.getWrapper().isMaxAge(crop)){
                loc.getWorld().dropItemNaturally(loc, harvestHandler.getHarvestItem(new Harvest(targetseed)));
                loc.getWorld().dropItemNaturally(loc, harvestHandler.getSeedItem(targetseed));
            }else {
                loc.getWorld().dropItemNaturally(loc, harvestHandler.getSeedItem(targetseed, 1));
            }
            crop.setType(Material.AIR);
            List<String> list = ConfigManager.get("data.yml").getStringList("data."+targetseed.getType());
            StringBuilder sm = new StringBuilder();
            sm.append(loc.getWorld().getName()).append(";");
            sm.append(loc.getBlockX()).append(";");
            sm.append(loc.getBlockY()).append(";");
            sm.append(loc.getBlockZ());
            if (list.contains(sm.toString())){
                list.remove(sm.toString());
            }
            ConfigManager.get("data.yml").set("data."+targetseed.getType(), list);
            ConfigManager.save("data.yml");
            ConfigManager.reload("data.yml");
        }

    }
}
