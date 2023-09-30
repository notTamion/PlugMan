package de.tamion.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class ReloadPlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("reloadplugin")) {
            e.deferReply().queue();
            if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    e.getHook().sendMessage("You aren't allowed to do that!").setEphemeral(true).queue();
                    return;
                }
            }
            OptionMapping name = e.getOption("name");
            if (name == null) {
                e.getHook().sendMessage("Please provide a valid Plugin Name").queue();
                return;
            }
            PluginManager pluginManager = Bukkit.getPluginManager();
            if (name.getAsString().equals("*")) {
                for(Plugin pl : pluginManager.getPlugins()) {
                    pluginManager.disablePlugin(pl);
                    pluginManager.enablePlugin(pl);
                }
                e.getHook().sendMessage("All Plugins Reloaded").setEphemeral(true).queue();
            } else {
                Plugin pl = pluginManager.getPlugin(name.getAsString());
                if(pl == null) {
                    e.getHook().sendMessage("Plugin doesn't exist").setEphemeral(true).queue();
                    return;
                }
                pluginManager.disablePlugin(pl);
                pluginManager.enablePlugin(pl);
                e.getHook().sendMessage("Successfully Reloaded!").setEphemeral(true).queue();
            }
        }
    }
}
