package me.thomas.deathstand.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessagesManager {

    private String standName;
    private String differentBody;
    private String removeBody;
    private String noExperience;
    private int removeTime;

    public void register() {
        StandManager standManager = StandManager.getStandManager();
        FileConfiguration config = standManager.getPlugin().getConfig();
        try {
            standName = ChatColor.translateAlternateColorCodes('&', config.getString("stand-name"));
            differentBody = ChatColor.translateAlternateColorCodes('&', config.getString("different-body"));
            removeBody = ChatColor.translateAlternateColorCodes('&', config.getString("remove-body"));
            noExperience = ChatColor.translateAlternateColorCodes('&', config.getString("no-experience"));
            removeTime = Integer.parseInt(config.getString("remove-time"));
            Bukkit.getLogger().info("[DeathStand] Successfully loaded all messages.");
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("[DeathStand] Unable to load messages from the config file.");
            Bukkit.getLogger().info("[DeathStand] Disabling the plugin due to a syntax error.");
            Bukkit.getPluginManager().disablePlugin(standManager.getPlugin());
        }
    }

    public String getStandName() {
        return standName;
    }

    public String getDifferentBody() {
        return differentBody;
    }

    public String getRemoveBody() {
        return removeBody;
    }

    public String getNoExperience() {
        return noExperience;
    }

    public int getRemoveTime() {
        return removeTime;
    }
}
