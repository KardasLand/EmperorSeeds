package com.kardasland.emperorseeds;

import com.kardasland.emperorseeds.bugfix.BoneMealBug;
import com.kardasland.emperorseeds.bugfix.HeadPlaceBug;
import com.kardasland.emperorseeds.commands.MainCommand;
import com.kardasland.emperorseeds.event.BlockTrample;
import com.kardasland.emperorseeds.event.CropBreak;
import com.kardasland.emperorseeds.event.CropPlace;
import com.kardasland.emperorseeds.seed.harvest.SeedHarvest;
import com.kardasland.emperorseeds.seed.seed.SeedHandler;
import com.kardasland.emperorseeds.support.Support;
import com.kardasland.emperorseeds.support.types.ASkyblockSupport;
import com.kardasland.emperorseeds.support.types.FabledSkyblockSupport;
import com.kardasland.emperorseeds.support.types.SuperiorSkyblock2Support;
import com.kardasland.emperorseeds.utils.Araclar;
import com.kardasland.emperorseeds.utils.ConfigManager;
import com.kardasland.emperorseeds.wrapper.Wrapper;
import com.kardasland.emperorseeds.wrapper.types.Legacy;
import com.kardasland.emperorseeds.wrapper.types.New;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Emperorseeds extends JavaPlugin {
    public PluginDescriptionFile pdf = this.getDescription();
    public static Emperorseeds instance;
    private Wrapper wrapper;
    private Support support;
    public boolean bonemeal;
    public int cooldown;

    @Override
    public void onEnable() {
        instance = this;
        ConfigManager.load("license.yml");
        if (!new SeedHarvest(ConfigManager.get("license.yml").getString("LicenseKey"), "http://www.kardasland.com/license/verify.php", this).setSecurityKey("YecoF0I9182naML9281HuW8iUhTdIUInjkfF").setConsoleLog(SeedHarvest.LogType.NONE).register()){
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }else {
            loadWrapper();
            loadSupport();
            loadCfgs();
            bonemeal = ConfigManager.get("config.yml").getBoolean("disable-bone-meal");

            ConfigManager.update("config.yml", "Seed-Cooldown", 5);
            ConfigManager.update("messages.yml", "still-in-cooldown", "Hala %second% saniye bekleme süren var!");

            ConfigManager.save("messages.yml");
            ConfigManager.reload("messages.yml");

            ConfigManager.save("config.yml");
            ConfigManager.reload("config.yml");

            this.cooldown = ConfigManager.get("config.yml").getInt("Seed-Cooldown");
            registerCmds();
            registerEvents();
        }
    }
    @Override
    public void onDisable() {

    }

    private void loadWrapper(){
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        int minorVer = Integer.parseInt(split[1]); //For 1.10 will be "10"
        if (NBTEditor.getMinecraftVersion().lessThanOrEqualTo(NBTEditor.MinecraftVersion.v1_12) || minorVer <= 12){
            this.wrapper = new Legacy();
            getLogger().info("Legacy desteği eklendi.");
        }else {
            this.wrapper = new New();
        }
    }

    private void loadSupport(){
        if (Bukkit.getPluginManager().getPlugin("SuperiorSkyblock2") != null){
            this.support = new SuperiorSkyblock2Support();
        }
        if (Bukkit.getPluginManager().getPlugin("FabledSkyblock") != null){
            this.support = new FabledSkyblockSupport();
        }
        if (Bukkit.getPluginManager().getPlugin("ASkyBlock") != null){
            this.support = new ASkyblockSupport();
        }else {
            new Araclar().prefix("Uygun skyblock eklentisi bulunamadı, eklenti kapatılıyor..");
        }
    }

    public void loadCfgs(){
        ConfigManager.load("config.yml");
        ConfigManager.load("data.yml");
        ConfigManager.load("messages.yml");
        ConfigManager.load("seeds.yml");
    }
    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new CropBreak(), this);
        Bukkit.getPluginManager().registerEvents(new CropPlace(), this);
        Bukkit.getPluginManager().registerEvents(new BlockTrample(), this);
        Bukkit.getPluginManager().registerEvents(new HeadPlaceBug(), this);
        Bukkit.getPluginManager().registerEvents(new BoneMealBug(), this);

    }
    public void registerCmds(){
        getCommand("emperorseeds").setExecutor(new MainCommand());
    }

    public Wrapper getWrapper() {
        return wrapper;
    }

    public Support getSupport() {
        return support;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
