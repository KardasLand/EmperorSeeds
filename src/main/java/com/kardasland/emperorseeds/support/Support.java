package com.kardasland.emperorseeds.support;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Support {
    public boolean hasIsland(Player p);
    public boolean isMember(Player p, Location loc);
}
