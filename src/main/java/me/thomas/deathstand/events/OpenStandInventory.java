package me.thomas.deathstand.events;

import me.thomas.deathstand.utils.MessagesManager;
import me.thomas.deathstand.utils.StandItems;
import me.thomas.deathstand.utils.StandManager;
import me.thomas.deathstand.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;

public class OpenStandInventory implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        StandManager standManager = StandManager.getStandManager();
        MessagesManager messagesManager = standManager.getMessagesManager();
        Entity entity = event.getRightClicked();
        if (!player.getInventory().getItemInMainHand().isSimilar(StandItems.getKeyItem())) return;

        if (standManager.getStandItems().containsKey(player.getUniqueId().toString())) {
            if (standManager.getStandItems().get(player.getUniqueId().toString()).containsKey(entity.getUniqueId().toString())) {
                Inventory inv = Bukkit.createInventory(null, 45, ChatColor.stripColor(entity.getCustomName()));
                inv.setContents(standManager.getPlayerItems(player, entity));

                if (standManager.getPlugin().getConfig().getString("save-exp").contains("true"))
                    inv.setItem(44, StandItems.getExpItem());

                player.openInventory(inv);
                standManager.setViewingStand(player, entity);
                if (!standManager.getStands().contains(entity.getUniqueId().toString())) {
                    player.sendMessage(StringUtils.replaceLines(messagesManager.getRemoveBody(), player));
                    standManager.addStand(entity);
                    Bukkit.getScheduler().runTaskLater(standManager.getPlugin(), () -> {
                        player.sendMessage(ChatColor.RED + "Stand removed!");
                        standManager.getStandItems().get(player.getUniqueId().toString()).remove(entity.getUniqueId().toString());
                        standManager.getStandExp().get(player.getUniqueId().toString()).remove(entity.getUniqueId().toString());
                        entity.remove();
                        player.getInventory().remove(StandItems.getKeyItem());
                    }, messagesManager.getRemoveTime() * 20L);
                }
            }
        } else player.sendMessage(StringUtils.replaceLines(messagesManager.getDifferentBody(), player));
    }
}
