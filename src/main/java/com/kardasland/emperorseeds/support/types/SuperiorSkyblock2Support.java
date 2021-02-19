package com.kardasland.emperorseeds.support.types;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.kardasland.emperorseeds.support.Support;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class SuperiorSkyblock2Support implements Support {
    @Override
    public boolean isMember(Player p, Location loc) {
        List<SuperiorPlayer> members = SuperiorSkyblockAPI.getIslandAt(loc).getIslandMembers(true);
        for (SuperiorPlayer member : members){
            if (SuperiorSkyblockAPI.getPlayer(p).equals(member)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasIsland(Player p) {
        return SuperiorSkyblockAPI.getPlayer(p).getIsland() != null;
    }
}
