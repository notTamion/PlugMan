package de.tamion.commands;

import de.tamion.PlugMan;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class TogglePlugin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("PlugMan.toggle")) {
            if(args.length == 1) {
                String Pluginname = args[0];
                PluginManager pluginManager = Bukkit.getPluginManager();
                Plugin plugin = pluginManager.getPlugin(Pluginname);
                if(plugin != null && !plugin.getName().equals("PlugMan")) {
                    if(plugin.isEnabled()) {
                        pluginManager.disablePlugin(plugin);
                        sender.sendMessage(plugin.getName() + " has been successfully disabled");
                    } else {
                        pluginManager.enablePlugin(plugin);
                        sender.sendMessage(plugin.getName() + " has been successfully enabled");
                    }
                } else {
                    sender.sendMessage("Plugin doesn't exist");
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
