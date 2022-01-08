package me.thomas.deathstand.utils;

import me.thomas.deathstand.DeathStand;
import me.thomas.deathstand.datamanager.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class StandManager {

    private static StandManager standManager;
    private DeathStand plugin;
    private DataManager dataManager;
    private MessagesManager messagesManager;
    private Map<String, HashMap<String, ItemStack[]>> standItems;
    private Map<String, HashMap<String, Integer>> standExp;
    private Map<UUID, UUID> viewingStand;
    private List<String> stands;
    public StandManager(DeathStand plugin) {
        standManager = this;
        this.plugin = plugin;
        messagesManager = new MessagesManager();
        standItems = new HashMap<>();
        dataManager = new DataManager();
        standExp = new HashMap<>();
        viewingStand = new HashMap<>();
        stands = new ArrayList<>();
    }

    public static StandManager getStandManager() {
        return standManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public MessagesManager getMessagesManager() {
        return messagesManager;
    }

    public DeathStand getPlugin() {
        return plugin;
    }

    public Map<String, HashMap<String, ItemStack[]>> getStandItems() {
        return standItems;
    }

    public Map<String, HashMap<String, Integer>> getStandExp() {
        return standExp;
    }

    public Map<UUID, UUID> getViewingStand() {
        return viewingStand;
    }

    public List<String> getStands() {
        return stands;
    }

    public ItemStack[] getPlayerItems(Player player, Entity entity) {
        if (standItems.containsKey(player.getUniqueId().toString())) {
            if (standItems.get(player.getUniqueId().toString()).containsKey(entity.getUniqueId().toString()))
                return standItems.get(player.getUniqueId().toString()).get(entity.getUniqueId().toString());
        }
        return new ItemStack[]{};
    }

    public void savePlayerItems(Player player, Entity entity, ItemStack[] items) {
        if (!standItems.containsKey(player.getUniqueId().toString())) {
            HashMap<String, ItemStack[]> map = new HashMap<>();
            map.put(entity.getUniqueId().toString(), items);
            standItems.put(player.getUniqueId().toString(), map);
        } else standItems.get(player.getUniqueId().toString()).put(entity.getUniqueId().toString(), items);
    }

    public Integer getPlayerExp(Player player, Entity entity) {
        if (standExp.containsKey(player.getUniqueId().toString())) {
            if (standExp.get(player.getUniqueId().toString()).containsKey(entity.getUniqueId().toString()))
                return standExp.get(player.getUniqueId().toString()).get(entity.getUniqueId().toString());
        }
        return 0;
    }

    public void savePlayerExp(Player player, Entity entity, int amount) {
        if (!standExp.containsKey(player.getUniqueId().toString())) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put(entity.getUniqueId().toString(), amount);
            standExp.put(player.getUniqueId().toString(), map);
        } else standExp.get(player.getUniqueId().toString()).put(entity.getUniqueId().toString(), amount);
    }

    public void setViewingStand(Player player, Entity entity) {
        viewingStand.put(player.getUniqueId(), entity.getUniqueId());
    }

    public void addStand(Entity entity) {
        stands.add(entity.getUniqueId().toString());
    }

    public void rotateStands() {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        for (HashMap<String, ItemStack[]> maps : standItems.values()) {
                            if (maps.containsKey(entity.getUniqueId().toString())) {
                                entity.setRotation(i , i);
                                i+=3;
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(getPlugin(), 1, 1);

    }
}
