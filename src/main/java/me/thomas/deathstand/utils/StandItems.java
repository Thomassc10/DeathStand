package me.thomas.deathstand.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StandItems {

    public static ItemStack getExpItem() {
        StandManager standManager = StandManager.getStandManager();
        FileConfiguration config = standManager.getPlugin().getConfig();
        try {
            ItemStack item = new ItemStack(Material.matchMaterial(config.getString("exp-item.item")));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("exp-item.name")));
            List<String> lore = new ArrayList<>();
            for (String l : config.getStringList("exp-item.lore"))
                lore.add(ChatColor.translateAlternateColorCodes('&', l));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        } catch (IllegalArgumentException e) {
            ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Experience");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Click to get back you experience.");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    public static ItemStack getKeyItem() {
        StandManager standManager = StandManager.getStandManager();
        FileConfiguration config = standManager.getPlugin().getConfig();
        try {
            ItemStack item = new ItemStack(Material.matchMaterial(config.getString("key-item.item")), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("key-item.name")));
            List<String> lore = new ArrayList<>();
            for (String l : config.getStringList("key-item.lore"))
                lore.add(ChatColor.translateAlternateColorCodes('&', l));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        } catch (IllegalArgumentException e) {
            ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.DARK_PURPLE + "Key");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Right-Click on your stand to get back your items.");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }
}
