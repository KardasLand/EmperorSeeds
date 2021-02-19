package com.kardasland.emperorseeds.event;

import com.kardasland.emperorseeds.Emperorseeds;
import com.kardasland.emperorseeds.seed.harvest.Harvest;
import com.kardasland.emperorseeds.seed.harvest.HarvestHandler;
import com.kardasland.emperorseeds.seed.seed.Seed;
import com.kardasland.emperorseeds.seed.seed.SeedHandler;
import com.kardasland.emperorseeds.utils.Araclar;
import com.kardasland.emperorseeds.utils.ConfigManager;
import com.kardasland.emperorseeds.utils.CooldownHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class CropBreak implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e){
        Location loc = e.getBlock().getLocation();
        Seed targetseed = new SeedHandler().findSeed(loc);
        if (targetseed != null){
            if (Emperorseeds.instance.getSupport().isMember(e.getPlayer(), loc)){
                if (CooldownHandler.isInCooldown(e.getPlayer().getUniqueId(), "place_cooldown")){
                    new Araclar().prefix(e.getPlayer(), ConfigManager.get("messages.yml").getString("still-in-cooldown").replace("%second%", String.valueOf(CooldownHandler.getTimeLeft(e.getPlayer().getUniqueId(), "place_cooldown"))));
                    e.setCancelled(true);
                }else {
                    HarvestHandler harvestHandler = new HarvestHandler();
                    if(Emperorseeds.instance.getWrapper().isMaxAge(e.getBlock())){
                        loc.getWorld().dropItemNaturally(loc, harvestHandler.getHarvestItem(new Harvest(targetseed)));
                        loc.getWorld().dropItemNaturally(loc, harvestHandler.getSeedItem(targetseed));
                    }else {
                        loc.getWorld().dropItemNaturally(loc, harvestHandler.getSeedItem(targetseed, 1));
                    }
                    e.getBlock().setType(Material.AIR);
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
                    CooldownHandler c = new CooldownHandler(e.getPlayer().getUniqueId(), "place_cooldown", Emperorseeds.instance.getCooldown());
                    c.start();
                }
                //e.setDropItems(false);
            }

        }
    }
}

