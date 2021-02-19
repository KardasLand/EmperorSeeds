package com.kardasland.emperorseeds.seed.seed;

import com.kardasland.emperorseeds.Emperorseeds;
import com.kardasland.emperorseeds.seed.harvest.HarvestHandler;
import com.kardasland.emperorseeds.seed.harvest.SeedHarvest;
import com.kardasland.emperorseeds.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SeedHandler {
    public Seed getSeed(String type){
        try {
            return new Seed(ConfigManager.get("seeds.yml").getString("seeds."+type+".name"), ConfigManager.get("seeds.yml").getStringList("seeds."+type+".lore"), type);
        }catch (NullPointerException ex){
            return null;
        }
    }
    public Seed findSeed(String name){
        for (String key : ConfigManager.get("seeds.yml").getConfigurationSection("seeds.").getKeys(false)){
            // string = key;
            if (ConfigManager.get("seeds.yml").getString("seeds."+key+".name").equals(name)){
                return new Seed(name, ConfigManager.get("seeds.yml").getStringList("seeds."+key+".lore"), key);
            }
        }
        return null;
    }
    public Seed findSeed(Location loc){
        for (String key : ConfigManager.get("data.yml").getConfigurationSection("data.").getKeys(false)){
            // key === type;
            StringBuilder sm = new StringBuilder();
            sm.append(loc.getWorld().getName()).append(";");
            sm.append(loc.getBlockX()).append(";");
            sm.append(loc.getBlockY()).append(";");
            sm.append(loc.getBlockZ());
            if (ConfigManager.get("data.yml").getStringList("data."+key).contains(sm.toString())){
                return getSeed(key);
            }
        }
        return null;
    }
    public boolean giveSeed(Player p, Seed seed, int amount){
        if (p.getInventory().firstEmpty() == -1){
            return false;
        }else {
            ItemStack item = new HarvestHandler().getSeedItem(seed, amount);
            p.getInventory().addItem(item);
            return true;
        }
    }

    public String listSeeds(){
        StringBuilder sm = new StringBuilder();
        for (String key : ConfigManager.get("seeds.yml").getConfigurationSection("seeds.").getKeys(false)){
            sm.append(key).append(", ");
        }
        sm.substring(0, sm.length() - 2);
        return sm.toString();
    }
}
