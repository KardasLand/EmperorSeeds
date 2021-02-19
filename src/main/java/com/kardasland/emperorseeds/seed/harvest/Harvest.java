package com.kardasland.emperorseeds.seed.harvest;


import com.kardasland.emperorseeds.seed.seed.Seed;
import com.kardasland.emperorseeds.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Harvest {
    private Material type;
    private String customtype;
    private int amount;
    public String name;
    public List<String> lore;
    private Map<Integer, Integer> seed_chances;

    public Harvest(Seed seed){

        if (ConfigManager.get("seeds.yml").getString("seeds."+seed.getType()+".harvest.material.type").contains("HEAD-")){
            String custom =ConfigManager.get("seeds.yml").getString("seeds."+seed.getType()+".harvest.material.type");
            this.customtype = "http://textures.minecraft.net/texture/" + custom.substring(5);
        }else {
            this.type = Material.getMaterial(ConfigManager.get("seeds.yml").getString("seeds."+seed.getType()+".harvest.material.type"));
        }
        this.amount = ConfigManager.get("seeds.yml").getInt("seeds."+seed.getType()+".harvest.material.amount");
        this.name = ConfigManager.get("seeds.yml").getString("seeds."+seed.getType()+".harvest.material.name") == null ? null : ConfigManager.get("seeds.yml").getString("seeds."+seed.getType()+".harvest.material.name");
        this.lore = ConfigManager.get("seeds.yml").getStringList("seeds."+seed.getType()+".harvest.material.lore") == null ? new ArrayList<>() : ConfigManager.get("seeds.yml").getStringList("seeds."+seed.getType()+".harvest.material.lore");

        Map<Integer, Integer> temp = new HashMap<>();
        List<String> values = ConfigManager.get("seeds.yml").getStringList("seeds."+seed.getType()+".harvest.seed.chances");
        for (String key : values){
            String[] tempstring = key.split(";", 2);
            temp.put(Integer.parseInt(tempstring[0]), Integer.parseInt(tempstring[1]));
        }
        this.seed_chances = temp;
    }

    public int getAmount() {
        return amount;
    }

    public Map<Integer, Integer> getSeed_chances() {
        return seed_chances;
    }

    public Material getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getCustomtype() {
        return customtype;
    }

    public List<String> getLore() {
        return lore;
    }
}
