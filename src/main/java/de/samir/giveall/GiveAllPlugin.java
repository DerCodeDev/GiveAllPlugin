package de.samir.giveall;

import de.samir.giveall.command.GiveAllCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveAllPlugin extends JavaPlugin {

    private static String PREFIX;
    private static GiveAllPlugin instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        PREFIX = getValuesFromConfig("Messages.Prefix");
        registerCommand();
        log(PREFIX + "§aPlugin geladen.");
    }

    @Override
    public void onDisable() {
        log(PREFIX + "§cPlugin entladen.");
    }
    private void registerCommand() {
        Bukkit.getPluginCommand("giveall").setExecutor(new GiveAllCommand());
    }
    private void log(final String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }

    public static String getValuesFromConfig(final String path) {
        return ChatColor.translateAlternateColorCodes('&',instance.getConfig().getString(path));
    }

    public static String getPREFIX() {
        return PREFIX;
    }

}

