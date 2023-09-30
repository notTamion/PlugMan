package de.tamion.commands;

import org.apache.commons.io.FileUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.net.URL;

public class DowloadPlugin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("PlugMan.download")) {
            sender.sendMessage("You aren't allowed to execute this command!");
            return false;
        }
        if(args.length != 2) {
            sender.sendMessage("Invalid Args");
            return false;
        }
        File fl = new File("./plugins/" + args[1]);
        try {
            URL url = new URL(args[0]);
            FileUtils.copyURLToFile(url, fl);
            sender.sendMessage("Successfully downloaded!");
            return true;
        } catch (Exception e) {
            sender.sendMessage("Something went wrong. Check console for Error logs");
            e.printStackTrace();
        }
        return false;
    }
}
