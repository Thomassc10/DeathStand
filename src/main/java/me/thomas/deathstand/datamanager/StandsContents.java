package me.thomas.deathstand.datamanager;

import me.thomas.deathstand.utils.StandManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandsContents {

    public void saveItems() {
        StandManager standManager = StandManager.getStandManager();
        FileConfiguration config = standManager.getDataManager().getStandItems().getConfig();
        for (Map.Entry<String, HashMap<String, ItemStack[]>> entry : standManager.getStandItems().entrySet()) {
            for (Map.Entry<String, ItemStack[]> entry1 : entry.getValue().entrySet()) {
                config.set("data." + entry.getKey() + "." + entry1.getKey(), entry1.getValue());
            }
        }
        standManager.getDataManager().getStandItems().saveConfig();
    }

    public void loadItems() {
        StandManager standManager = StandManager.getStandManager();
        FileConfiguration config = standManager.getDataManager().getStandItems().getConfig();
        ConfigurationSection section = config.getConfigurationSection("data");
        for (String key : section.getKeys(false)) {
            for (String key1 : section.getConfigurationSection(key).getKeys(false)) {
                ItemStack[] items = ((List<ItemStack>) section.get(key + "." + key1)).toArray(new ItemStack[0]);
                HashMap<String, ItemStack[]> map = new HashMap<>();
                map.put(key1, items);
                standManager.getStandItems().put(key, map);
            }
        }
    }

    public void saveExp() {
        StandManager standManager = StandManager.getStandManager();
        FileConfiguration config = standManager.getDataManager().getStandExp().getConfig();
        for (Map.Entry<String, HashMap<String, Integer>> entry : standManager.getStandExp().entrySet()) {
            for (Map.Entry<String, Integer> entry1 : entry.getValue().entrySet()) {
                config.set("data." + entry.getKey() + "." + entry1.getKey(), entry1.getValue());
            }
        }
        standManager.getDataManager().getStandExp().saveConfig();
    }

    public void loadExp() {
        StandManager standManager = StandManager.getStandManager();
        FileConfiguration config = standManager.getDataManager().getStandExp().getConfig();
        ConfigurationSection section = config.getConfigurationSection("data");
        for (String key : section.getKeys(false)) {
            for (String key1 : section.getConfigurationSection(key).getKeys(false)) {
                Integer exp = (Integer) section.get(key + "." + key1);
                HashMap<String, Integer> map = new HashMap<>();
                map.put(key1, exp);
                standManager.getStandExp().put(key, map);
            }
        }
    }
}
