package com.kardasland.emperorseeds.bugfix;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HeadPlaceBug implements Listener {
    @EventHandler
    public void headCheck(BlockPlaceEvent e){
        Material material = Material.getMaterial( "SKULL_ITEM" );
        if ( material == null ) {
            // Most likely 1.13 materials
            material = Material.getMaterial( "PLAYER_HEAD" );
        }
        if (e.getBlockPlaced().getType().equals(material)){
            if (NBTEditor.contains(e.getItemInHand(), "notplaceable")){
                e.setCancelled(true);
            }
        }
    }
}
