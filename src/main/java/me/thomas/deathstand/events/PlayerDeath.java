package me.thomas.deathstand.events;

import me.thomas.deathstand.utils.StandManager;
import me.thomas.deathstand.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        StandManager standManager = StandManager.getStandManager();
        ItemStack[] items = player.getInventory().getContents();
        FileConfiguration config = standManager.getPlugin().getConfig();

        String name = StringUtils.replaceLines(standManager.getMessagesManager().getStandName(), player);

        ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(name);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setOwningPlayer(player);
        head.setItemMeta(skullMeta);
        armorStand.setHelmet(head);

        standManager.savePlayerItems(player, armorStand, items);
        if (config.getString("save-exp").contains("true"))
            standManager.savePlayerExp(player, armorStand, player.getLevel());
        event.getDrops().clear();
        event.setDroppedExp(0);
    }
}
