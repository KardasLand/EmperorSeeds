package com.kardasland.emperorseeds.seed.seed;

import com.kardasland.emperorseeds.utils.ConfigManager;
import org.bukkit.Material;

import java.util.List;

public class Seed {
    private String name;
    private List<String> lore;
    private String type;
    private Material material;

    public Seed(String name, List<String> lore, String type){
        this.name = name;
        this.lore = lore;
        this.type = type;
        this.material = Material.getMaterial(ConfigManager.get("seeds.yml").getString("seeds."+type+".material"));
    }

    public String getType() {
        return type;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }
}
