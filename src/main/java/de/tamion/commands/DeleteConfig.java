package de.tamion.commands;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class DeleteConfig implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("PlugMan.deleteconfig")) {
            if(args.length == 1) {
                String Pluginname = args[0];
                File dire = new File("./plugins/" + Pluginname);
                if(dire.exists()) {
                    if(dire.isDirectory()) {
                        try {
                            FileUtils.deleteDirectory(dire);
                            sender.sendMessage("Configuration file successfully deleted");
                        } catch (IOException e) {
                            sender.sendMessage("Something went wrong! Check Console for Error Logs");
                        }
                    } else {
                        sender.sendMessage("Please enter the name of the plugin and not the name of the plugin.jar file");
                    }
                } else {
                    sender.sendMessage("This Plugin doesn't have a configuration file");
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
