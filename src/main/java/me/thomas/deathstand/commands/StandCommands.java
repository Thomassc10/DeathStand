package me.thomas.deathstand.commands;

import me.thomas.deathstand.utils.StandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class StandCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("dstand")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            StandManager standManager = StandManager.getStandManager();
            if (player.hasPermission("dstand.use.commands")) {
                if (args.length == 0) {
                    player.sendMessage("List of commands:");
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    player.sendMessage(ChatColor.RED + "Reloading the plugin...");
                    standManager.getPlugin().reloadConfig();
                    standManager.getMessagesManager().register();
                    player.sendMessage(ChatColor.GREEN + "Reload complete!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("removeAll")) {
                    for (HashMap<String, ItemStack[]> maps : standManager.getStandItems().values()) {
                        if (maps.keySet().isEmpty()) {
                            player.sendMessage(ChatColor.RED + "There are no stands to remove");
                            return true;
                        }
                        for (String uuid : maps.keySet()) {
                            Entity entity = Bukkit.getEntity(UUID.fromString(uuid));
                            if (entity != null)
                                entity.remove();
                            else {
                                player.sendMessage(ChatColor.RED + "There are no stands to be removed!");
                                return true;
                            }
                        }
                    }
                    player.sendMessage(ChatColor.GREEN + "Successfully removed all stands!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Identify the player you want to remove its stands.");
                        return true;
                    }
                    if (Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(ChatColor.RED + "Unable to find player " + ChatColor.GOLD + args[1]);
                        return true;
                    }
                    Player p = Bukkit.getPlayer(args[1]);
                    if (!standManager.getStandItems().containsKey(p.getUniqueId().toString())
                            || standManager.getStandItems().get(p.getUniqueId().toString()).isEmpty()) {
                        player.sendMessage(ChatColor.RED + "This player does not have any stands to be removed.");
                        return true;
                    }
                    for (String uuid : standManager.getStandItems().get(p.getUniqueId().toString()).keySet()) {
                        Entity entity = Bukkit.getEntity(UUID.fromString(uuid));
                        if (entity != null)
                            entity.remove();
                        else {
                            player.sendMessage(ChatColor.RED + "This player does not have any stands to be removed.");
                            return true;
                        }
                    }
                    player.sendMessage(ChatColor.GREEN + "Successfully remove all " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + "'s stands!");
                    return true;
                }
                return true;
            }
            player.sendMessage(ChatColor.RED + "You are now allowed to use this command.");
            return true;
        }
        return false;
    }
}
