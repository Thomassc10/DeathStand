package me.thomas.deathstand.events;

import me.thomas.deathstand.utils.StandItems;
import me.thomas.deathstand.utils.StandManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RetrieveExp implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().isSimilar(StandItems.getExpItem())) return;
        event.setCancelled(true);
        StandManager standManager = StandManager.getStandManager();
        Player player = (Player) event.getWhoClicked();
        Entity entity = Bukkit.getEntity(standManager.getViewingStand().get(player.getUniqueId()));
        if (standManager.getStandExp().get(player.getUniqueId().toString()).containsKey(entity.getUniqueId().toString())) {
            player.giveExpLevels(standManager.getPlayerExp(player, entity));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            standManager.getStandExp().get(player.getUniqueId().toString()).remove(entity.getUniqueId().toString());
        } else player.sendMessage(standManager.getMessagesManager().getNoExperience());
    }
}
