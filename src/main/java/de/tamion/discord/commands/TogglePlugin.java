package de.tamion.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class TogglePlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("toggleplugin")) {
            e.deferReply().queue();
            if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    e.getHook().sendMessage("You aren't allowed to do that!").setEphemeral(true).queue();
                    return;
                }
            }
            OptionMapping name = e.getOption("name");
            if(name == null) {
                e.getHook().sendMessage("Please provide a valid name").queue();
                return;
            }
            PluginManager pluginManager = Bukkit.getPluginManager();
            Plugin plugin = pluginManager.getPlugin(name.getAsString());
            if(plugin != null && !plugin.getName().equals("PlugMan")) {
                if(plugin.isEnabled()) {
                    pluginManager.disablePlugin(plugin);
                    e.getHook().sendMessage(plugin.getName() + " has been successfully disabled").setEphemeral(true).queue();
                } else {
                    pluginManager.enablePlugin(plugin);
                    e.getHook().sendMessage(plugin.getName() + " has been successfully enabled").setEphemeral(true).queue();
                }
            } else {
                e.getHook().sendMessage("Plugin doesn't exist").setEphemeral(true).queue();
            }
        }
    }
}
