package com.kardasland.emperorseeds.support.types;

import com.kardasland.emperorseeds.support.Support;
import com.songoda.skyblock.api.SkyBlockAPI;
import com.songoda.skyblock.api.island.IslandManager;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FabledSkyblockSupport implements Support {
    IslandManager skyBlockAPI = SkyBlockAPI.getIslandManager();
    @Override
    public boolean isMember(Player p, Location loc) {
        return skyBlockAPI.getIslandAtLocation(loc).isCoopPlayer(p.getUniqueId());
    }

    @Override
    public boolean hasIsland(Player p) {
        return skyBlockAPI.getIsland(p) != null;
    }
}
