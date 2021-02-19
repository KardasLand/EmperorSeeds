package com.kardasland.emperorseeds.commands;

import com.kardasland.emperorseeds.Emperorseeds;
import com.kardasland.emperorseeds.seed.seed.Seed;
import com.kardasland.emperorseeds.seed.seed.SeedHandler;
import com.kardasland.emperorseeds.utils.Araclar;
import com.kardasland.emperorseeds.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor{
    Araclar araclar = new Araclar();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("version")){
                    araclar.prefix(p,"Version: " + Emperorseeds.instance.pdf.getVersion());
                }
                else if (args[0].equalsIgnoreCase("list")){
                    if (p.hasPermission("emperorseeds.list")){
                        araclar.prefix(p,"Ekin listesi: " + new SeedHandler().listSeeds());
                    }else {
                        araclar.noPerms(p, "emperorseeds.list");
                    }
                }
                else if (args[0].equalsIgnoreCase("reload")){
                    if (p.hasPermission("emperorseeds.reload")){
                        reloadCfgs();
                        araclar.prefix(p,"Başarıyla yenilendi!");
                    }else {
                        araclar.noPerms(p, "emperorseeds.reload");
                    }
                }else {
                    helpScreen(p);
                }
            }else if (args.length == 4){
                if (args[0].equalsIgnoreCase("give")){
                 // /emperorseeds give <oyuncu> <seed> [<miktar>]
                 if (p.hasPermission("emperorseeds.give")){
                     /*
                     if (new SeedHandler().giveSeed(p, new SeedHandler().getSeed("ruby"), 3)){
            Bukkit.broadcastMessage("check 3");
        }
                      */
                     Player target = Bukkit.getPlayer(args[1]);
                     if (target != null && target.isOnline()) {
                         String type = args[2];
                         int amount = 0;
                         try {
                             amount = args[3] != null ? Integer.parseInt(args[3]) : 1;
                             if (amount == 0){
                                 araclar.prefix(p, "Lütfen bir sayı girin!");
                                 return true;
                             }
                         } catch (NumberFormatException ex) {
                            araclar.prefix(p, "Lütfen bir sayı girin!");
                            return true;
                         }
                         Seed targetSeed = new SeedHandler().getSeed(type);
                         if (targetSeed != null) {
                            try {
                                if (new SeedHandler().giveSeed(target, targetSeed, amount)){
                                    araclar.prefix(p, ConfigManager.get("messages.yml").getString("given-seed-to-player").replace("%target%", target.getName()).replace("%type%", type));
                                }
                            }catch (Exception ex){
                                araclar.prefix(p, "Hata oluştu, konsola bakın!");
                                araclar.prefix("HATA OLUŞTU! KardasLand ile iletişim kurun!");
                                araclar.prefix("Hata detayları:");
                                araclar.prefix(ex.getMessage());
                            }
                         }else {
                             araclar.prefix(p,"Girdiğiniz seed yok, acaba doğru mu girdiniz?");
                             return true;
                         }
                     }
                 }else {
                     araclar.noPerms(p, "emperorseeds.give");
                 }

                }
            }else {
                helpScreen(p);
            }
        }else {
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("version")){
                    araclar.prefix("Version: " + Emperorseeds.instance.pdf.getVersion());
                }
                else if (args[0].equalsIgnoreCase("list")){
                    araclar.prefix("Ekin listesi: " + new SeedHandler().listSeeds());
                }
                else if (args[0].equalsIgnoreCase("reload")){
                        reloadCfgs();
                        araclar.prefix("Başarıyla yenilendi!");
                }else {
                    helpScreen();
                }
            }
            else if (args.length == 4){
                if (args[0].equalsIgnoreCase("give")){
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null && target.isOnline()) {
                            String type = args[2];
                            int amount = 0;
                            try {
                                amount = args[3] != null ? Integer.parseInt(args[3]) : 1;
                                if (amount == 0){
                                    araclar.prefix("Lütfen bir sayı girin!");
                                    return true;
                                }
                            } catch (NumberFormatException ex) {
                                araclar.prefix("Lütfen bir sayı girin!");
                                return true;
                            }
                            Seed targetSeed = new SeedHandler().getSeed(type);
                            if (targetSeed != null) {
                                try {
                                    if (new SeedHandler().giveSeed(target, targetSeed, amount)){
                                        araclar.prefix(ConfigManager.get("messages.yml").getString("given-seed-to-player").replace("%target%", target.getName()).replace("%type%", type));
                                    }
                                }catch (Exception ex){
                                    araclar.prefix("HATA OLUŞTU! KardasLand ile iletişim kurun!");
                                    araclar.prefix("Hata detayları:");
                                    araclar.prefix(ex.getMessage());
                                }
                            }else {
                                araclar.prefix("Girdiğiniz seed yok, acaba doğru mu girdiniz?");
                                return true;
                            }
                        }
                }
            }else {
                helpScreen();
            }
        }
        return true;
    }

    public void reloadCfgs(){
        ConfigManager.reload("config.yml");
        ConfigManager.reload("messages.yml");
        ConfigManager.reload("seeds.yml");
        Emperorseeds.instance.bonemeal = ConfigManager.get("config.yml").getBoolean("disable-bone-meal");
        Emperorseeds.instance.setCooldown(ConfigManager.get("config.yml").getInt("Seed-Cooldown"));
    }

    public void helpScreen(Player p){
        /*


&b/emperorseeds help
&7> Bilgi ekranını (bu ekranı) verir.
&b/emperorseeds list
&7> Tüm ekinlerin listesini verir.
&b/emperorseeds give <oyuncu> <seed> [<miktar>]
&7> Oyuncuya belirli bir miktar ekin verir.
         */
        araclar.nonPrefix(p, "&bEmperorMC - KardasLand");
        araclar.nonPrefix(p, " ");
        araclar.nonPrefix(p, "&b/emperorseeds help");
        araclar.nonPrefix(p, "&7> Bilgi ekranını (bu ekranı) verir.");
        araclar.nonPrefix(p, "&b/emperorseeds version");
        araclar.nonPrefix(p, "&7> Eklenti sürümünü verir.");
        if (p.hasPermission("emperorseeds.list")){
            araclar.nonPrefix(p, "&b/emperorseeds list");
            araclar.nonPrefix(p, "&7> Tüm ekinlerin listesini verir.");
        }
        if (p.hasPermission("emperorseeds.reload")){
            araclar.nonPrefix(p, "&b/emperorseeds reload");
            araclar.nonPrefix(p, "&7> Eklentiyi yeniler.");
        }
        if (p.hasPermission("emperorseeds.give")){
            araclar.nonPrefix(p, "&b/emperorseeds give <oyuncu> <seed> [<miktar>]");
            araclar.nonPrefix(p, "&7> Oyuncuya belirli bir miktar ekin verir.");
        }


    }
    public void helpScreen(){
        araclar.nonPrefix("&bEmperorMC - KardasLand");
        araclar.nonPrefix(" ");
        araclar.nonPrefix("&b/emperorseeds help");
        araclar.nonPrefix("&7> Bilgi ekranını (bu ekranı) verir.");
        araclar.nonPrefix("&b/emperorseeds version");
        araclar.nonPrefix("&7> Eklenti sürümünü verir.");
        araclar.nonPrefix("&b/emperorseeds list");
        araclar.nonPrefix("&7> Tüm ekinlerin listesini verir.");
        araclar.nonPrefix("&b/emperorseeds reload");
        araclar.nonPrefix("&7> Eklentiyi yeniler.");
        araclar.nonPrefix("&b/emperorseeds give <oyuncu> <seed> [<miktar>]");
        araclar.nonPrefix("&7> Oyuncuya belirli bir miktar ekin verir.");

    }
}
