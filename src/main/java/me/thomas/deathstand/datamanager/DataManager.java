package me.thomas.deathstand.datamanager;

import me.thomas.deathstand.DeathStand;
import me.thomas.deathstand.utils.StandManager;

public class DataManager {

    private ConfigFile standItems;
    private ConfigFile standExp;
    public DataManager() {
        StandManager standManager = StandManager.getStandManager();
        DeathStand plugin = standManager.getPlugin();
        standItems = new ConfigFile(plugin, "stand-items.yml");
        standExp = new ConfigFile(plugin, "stand-exp.yml");

        standItems.saveDefaultConfig();
        standExp.saveDefaultConfig();
    }

    public ConfigFile getStandItems() {
        return standItems;
    }

    public ConfigFile getStandExp() {
        return standExp;
    }
}
