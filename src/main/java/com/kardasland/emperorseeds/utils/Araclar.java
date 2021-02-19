package com.kardasland.emperorseeds.utils;

import com.kardasland.emperorseeds.Emperorseeds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Araclar {
    public String getPrefix(){
        return ConfigManager.get("config.yml").getString("Prefix");
    }
    public List<String> color(List<String> s){
        List<String> temp = new ArrayList<>();
        for (String key : s){
            temp.add(color(key));
        }
        return temp;
    }
    public String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public void prefix(Player p, String s){
        p.sendMessage(color(getPrefix() + s));
    }
    public void prefix(String s){
        Emperorseeds.instance.getLogger().info(color(s));
    }
    public void nonPrefix(Player p, String s){
        p.sendMessage(color(s));
    }
    public void nonPrefix(String s){
        Bukkit.getLogger().info(color(s));
    }
    public void noPerms(Player p, String placeholder){
        prefix(p, ConfigManager.get("messages.yml").getString("not-enough-permission").replace("%permission%", placeholder));
    }
}
