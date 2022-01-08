package me.thomas.deathstand.events;

import me.thomas.deathstand.utils.StandItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.getPlayer().getInventory().addItem(StandItems.getKeyItem());
    }
}
