package me.thomas.deathstand.events;

import me.thomas.deathstand.utils.StandManager;
import me.thomas.deathstand.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseStandInventory implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        StandManager standManager =StandManager.getStandManager();
        FileConfiguration config = standManager.getPlugin().getConfig();
        Player player = (Player) event.getPlayer();
        String title = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', StringUtils.replaceLines(config.getString("stand-name"), player)));
        if (!event.getView().getTitle().contains(title)) return;
        Entity entity = Bukkit.getEntity(standManager.getViewingStand().get(player.getUniqueId()));
        standManager.savePlayerItems(player, entity, event.getInventory().getContents());
    }
}
