package de.tamion;

import de.tamion.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlugMan extends JavaPlugin {

    private static PlugMan plugin;
    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        plugin = this;

        getCommand("toggleplugin").setExecutor(new TogglePlugin());
        getCommand("deleteconfig").setExecutor(new DeleteConfig());
        getCommand("setconfig").setExecutor(new SetConfig());
        getCommand("reloadplugin").setExecutor(new ReloadPlugin());
        getCommand("disableall").setExecutor(new DisableAll());
        getCommand("enableall").setExecutor(new EnableAll());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlugMan getPlugin() {
        return plugin;
    }
}
