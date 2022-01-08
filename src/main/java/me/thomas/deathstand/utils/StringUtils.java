package me.thomas.deathstand.utils;

import org.bukkit.entity.Player;

public class StringUtils {

    private static final String[] placeholders = {"player", "time"};

    public static String replaceLines(String line, Player player) {
        for (String placeholder : placeholders) {
            if (line.contains("%" + placeholder + "%")) {
                line = line.replace("%" + placeholder + "%", getReplacement(placeholder, player));
            }
        }
        return line;
    }

    public static String getReplacement(String placeholder, Player player) {
        StandManager stand = StandManager.getStandManager();
        switch(placeholder) {
            case "player":
                return player.getName();
            case "time":
                return stand.getPlugin().getConfig().getString("remove-time");
            default:
                return "?";
        }
    }
}
