package de.tamion.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ReloadPlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("reloadplugin")) {
            e.deferReply().queue();
            EmbedBuilder eb = new EmbedBuilder();
            if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    eb.setColor(Color.RED);
                    eb.setTitle("You aren't allowed to do that!");
                    e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                    return;
                }
            }
            OptionMapping name = e.getOption("name");
            if (name == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid Plugin Name");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            PluginManager pluginManager = Bukkit.getPluginManager();
            if (name.getAsString().equals("*")) {
                for(Plugin pl : pluginManager.getPlugins()) {
                    pluginManager.disablePlugin(pl);
                    pluginManager.enablePlugin(pl);
                }
                eb.setColor(Color.GREEN);
                eb.setTitle("All Plugins Reloaded");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
            } else {
                Plugin pl = pluginManager.getPlugin(name.getAsString());
                if(pl == null) {
                    eb.setColor(Color.RED);
                    eb.setTitle("Plugin doesn't exist");
                    e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                    return;
                }
                pluginManager.disablePlugin(pl);
                pluginManager.enablePlugin(pl);
                eb.setColor(Color.GREEN);
                eb.setTitle("Successfully Reloaded!");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
            }
        }
    }
}
