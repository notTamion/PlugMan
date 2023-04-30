package de.tamion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class SetConfig implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("PlugMan.setconfig")) {
            if(args.length == 3) {
                PluginManager pluginManager = Bukkit.getPluginManager();
                Plugin pl = pluginManager.getPlugin(args[0]);
                if(pl == null) {
                    sender.sendMessage("Plugin doesn't exist");
                    return false;
                }
                if(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                    pl.getConfig().set(args[1], Boolean.parseBoolean(args[2]));
                } else {
                    pl.getConfig().set(args[1], args[2]);
                }
                pl.saveConfig();
                sender.sendMessage("Successfully set config");
            } else {
                sender.sendMessage("Invalid Args!");
            }
        } else {
            sender.sendMessage("You aren't allowed to execute this command!");
        }
        return false;
    }
}
