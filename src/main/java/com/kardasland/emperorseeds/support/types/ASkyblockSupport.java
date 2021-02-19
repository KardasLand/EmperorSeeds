package com.kardasland.emperorseeds.support.types;


import com.kardasland.emperorseeds.support.Support;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ASkyblockSupport implements Support {
    @Override
    public boolean isMember(Player p, Location loc) {
        try {
            Island is = ASkyBlockAPI.getInstance().getIslandAt(loc);
            if (is != null){
                List<UUID> members = is.getMembers();
                for (UUID member : members){
                    if (p.getUniqueId().equals(member)){
                        return true;
                    }
                }
            }else {
                return false;
            }

        }catch (NullPointerException ex){
            return false;
        }
        return false;
    }

    @Override
    public boolean hasIsland(Player p) {
        return ASkyBlockAPI.getInstance().hasIsland(p.getUniqueId());
    }
}
