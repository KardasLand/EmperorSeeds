package com.kardasland.emperorseeds.event;

import com.kardasland.emperorseeds.Emperorseeds;
import com.kardasland.emperorseeds.seed.seed.SeedHandler;
import com.kardasland.emperorseeds.utils.Araclar;
import com.kardasland.emperorseeds.utils.ConfigManager;
import com.kardasland.emperorseeds.utils.CooldownHandler;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CropPlace implements Listener {
    @SuppressWarnings("all")
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void crop(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if (Emperorseeds.instance.getWrapper().isFarmland(e.getClickedBlock())) {
                    ItemStack item = Emperorseeds.instance.getWrapper().getIteminHand(e.getPlayer());
                    if (item != null && !(item.getType().equals(Material.AIR))){
                        if (NBTEditor.contains(item, "type")) {
                            if (CooldownHandler.isInCooldown(e.getPlayer().getUniqueId(), "place_cooldown")){
                                new Araclar().prefix(e.getPlayer(), ConfigManager.get("messages.yml").getString("still-in-cooldown").replace("%second%", String.valueOf(CooldownHandler.getTimeLeft(e.getPlayer().getUniqueId(), "place_cooldown"))));
                                e.setCancelled(true);
                            }else {
                                if (Emperorseeds.instance.getWrapper().getIteminHand(e.getPlayer()).getAmount() <= 0){
                                    e.setCancelled(true);
                                }else{
                                    String type = NBTEditor.getString(item, "type");
                                    Location loc = e.getClickedBlock().getLocation();
                                    loc.add(0, 1, 0);
                                    StringBuilder sm = new StringBuilder();
                                    sm.append(loc.getWorld().getName()).append(";");
                                    sm.append(loc.getBlockX()).append(";");
                                    sm.append(loc.getBlockY()).append(";");
                                    sm.append(loc.getBlockZ());
                                    List<String> list = ConfigManager.get("data.yml").getStringList("data." + type);
                                    if (!list.contains(sm.toString())) {
                                        list.add(sm.toString());
                                    }
                                    ConfigManager.get("data.yml").set("data." + type, list);
                                    ConfigManager.save("data.yml");
                                    ConfigManager.reload("data.yml");
                                    new Araclar().prefix(e.getPlayer(), ConfigManager.get("messages.yml").getString("placed-succesfully").replace("%type%", new SeedHandler().getSeed(type).getName()));
                                    CooldownHandler c = new CooldownHandler(e.getPlayer().getUniqueId(), "place_cooldown", Emperorseeds.instance.getCooldown());
                                    c.start();
                                }
                            }

                        }
                    }
                }
        }
    }
}
