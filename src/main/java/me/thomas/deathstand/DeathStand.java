package me.thomas.deathstand;

import me.thomas.deathstand.commands.StandCommands;
import me.thomas.deathstand.commands.StandTabCompleter;
import me.thomas.deathstand.datamanager.DataManager;
import me.thomas.deathstand.datamanager.StandsContents;
import me.thomas.deathstand.events.*;
import me.thomas.deathstand.utils.StandManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathStand extends JavaPlugin {

    private StandManager standManager;
    private StandsContents standsContents;

    @Override
    public void onEnable() {
        standManager = new StandManager(this);
        standsContents = new StandsContents();
        DataManager data = standManager.getDataManager();
        saveDefaultConfig();
        standManager.getMessagesManager().register();
        standManager.rotateStands();
        registerCommands();
        registerEvents();

        int pluginId = 11412;
        new Metrics(this, pluginId);

        if (data.getStandItems().getConfig().contains("data"))
            standsContents.loadItems();
            data.getStandItems().getConfig().set("data", null);
            data.getStandItems().saveConfig();

        if (data.getStandExp().getConfig().contains("data"))
            standsContents.loadExp();
            data.getStandExp().getConfig().set("data", null);
            data.getStandExp().saveConfig();
    }

    @Override
    public void onDisable() {
        if (!standManager.getStandItems().isEmpty())
            standsContents.saveItems();
        if (!standManager.getStandExp().isEmpty())
            standsContents.saveExp();
    }

    public void registerEvents() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new PlayerDeath(), this);
        manager.registerEvents(new OpenStandInventory(), this);
        manager.registerEvents(new PlayerRespawn(), this);
        manager.registerEvents(new RetrieveExp(), this);
        manager.registerEvents(new CloseStandInventory(), this);
    }

    public void registerCommands() {
        getCommand("dstand").setExecutor(new StandCommands());
        getCommand("dstand").setTabCompleter(new StandTabCompleter());
    }
}
