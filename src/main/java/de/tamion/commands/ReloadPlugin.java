package de.tamion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class ReloadPlugin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("PlugMan.reload")) {
            if(args.length == 1) {
                PluginManager pluginManager = Bukkit.getPluginManager();
                if (args[0].equals("*")) {
                    for(Plugin pl : pluginManager.getPlugins()) {
                        pluginManager.disablePlugin(pl);
                        pluginManager.enablePlugin(pl);
                        sender.sendMessage(pl.getName() + " Reloaded!");
                    }
                    sender.sendMessage("All Plugins Reloaded");
                } else {
                    Plugin pl = pluginManager.getPlugin(args[0]);
                    if(pl != null) {
                        pluginManager.disablePlugin(pl);
                        pluginManager.enablePlugin(pl);
                        sender.sendMessage("Successfully Reloaded!");
                    } else {
                        sender.sendMessage("Plugin doesn't exist");
                    }
                }
            } else {
                sender.sendMessage("Invalid Args!");
            }
        } else {
            sender.sendMessage("You aren't allowed to execute this command!");
        }
        return false;
    }
}
