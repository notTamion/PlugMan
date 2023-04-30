package de.tamion;

import de.tamion.commands.DeleteConfig;
import de.tamion.commands.TogglePlugin;
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlugMan getPlugin() {
        return plugin;
    }
}
