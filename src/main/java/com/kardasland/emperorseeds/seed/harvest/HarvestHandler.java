package com.kardasland.emperorseeds.seed.harvest;

import com.kardasland.emperorseeds.seed.seed.Seed;
import com.kardasland.emperorseeds.utils.Araclar;
import com.kardasland.emperorseeds.utils.ProbabilityUntilities;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class HarvestHandler {
    public ItemStack getHarvestItem(Harvest harvest){
        ItemStack item = harvest.getType() == null ? NBTEditor.getHead(harvest.getCustomtype()) : new ItemStack(harvest.getType());
        ItemMeta itemMeta = item.getItemMeta();
        if (harvest.getName() != null){
            itemMeta.setDisplayName(new Araclar().color(harvest.getName()));
        }
        if (harvest.getLore() != null){
            itemMeta.setLore(new Araclar().color(harvest.getLore()));
        }
        item.setItemMeta(itemMeta);
        item.setAmount(harvest.getAmount());
        if (harvest.getType() == null){
            item = NBTEditor.set(item, true, "notplaceable");
        }
        return item;
    }
    public ItemStack getSeedItem(Seed seed){
        ItemStack item = new ItemStack(seed.getMaterial());
        Harvest harvest = new Harvest(seed);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(new Araclar().color(seed.getName()));
        itemMeta.setLore(new Araclar().color(seed.getLore()));
        item.setItemMeta(itemMeta);
        ProbabilityUntilities probabilityUntilities = new ProbabilityUntilities();
        for (Map.Entry<Integer, Integer> temp : harvest.getSeed_chances().entrySet()){
            probabilityUntilities.addChance(temp.getKey(), temp.getValue());
        }
        item.setAmount((Integer) probabilityUntilities.getRandomElement());
        item = NBTEditor.set(item, seed.getType(), "type");
        return item;
    }
    public ItemStack getSeedItem(Seed seed, int amount){
        ItemStack item = new ItemStack(seed.getMaterial());
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(new Araclar().color(seed.getName()));
        itemMeta.setLore(new Araclar().color(seed.getLore()));
        item.setItemMeta(itemMeta);
        item.setAmount(amount);
        item = NBTEditor.set(item, seed.getType(),"type");
        return item;
    }
}
