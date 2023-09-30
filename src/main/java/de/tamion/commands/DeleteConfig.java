package de.tamion.commands;

import org.apache.commons.io.FileUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;

public class DeleteConfig implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("PlugMan.deleteconfig")) {
            sender.sendMessage("You aren't allowed to execute this command!");
            return false;
        }
        if(args.length != 1) {
            sender.sendMessage("Invalid Args!");
            return false;
        }
        String Pluginname = args[0];
        File dire = new File("./plugins/" + Pluginname);
        if(!dire.exists()) {
            sender.sendMessage("This Plugin doesn't have a configuration file");
            return false;
        }
        if(!dire.isDirectory()) {
            sender.sendMessage("Please enter the name of the plugin and not the name of the plugin.jar file");
            return false;
        }
        try {
            FileUtils.deleteDirectory(dire);
            sender.sendMessage("Configuration file successfully deleted");
            return true;
        } catch (IOException e) {
            sender.sendMessage("Something went wrong! Check Console for Error Logs");
        }
        return false;
    }
}