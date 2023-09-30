package de.tamion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class EnableAll implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("PlugMan.enableall")) {
            PluginManager pluginManager = Bukkit.getPluginManager();
            for(Plugin pl : pluginManager.getPlugins()) {
                pluginManager.enablePlugin(pl);
                sender.sendMessage(pl.getName() + " has been enabled");
            }
            sender.sendMessage("All Plugins successfully enabled");
        } else {
            sender.sendMessage("You aren't allowed to execute this command!");
        }
        return false;
    }
}
