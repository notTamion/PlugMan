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

public class SetConfig extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("setconfig")) {
            e.deferReply().queue();
            EmbedBuilder eb = new EmbedBuilder();
            if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    eb.setColor(Color.RED);
                    eb.setTitle("All Plugins successfully disabled");
                    e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                    return;
                }
            }
            OptionMapping pluginname = e.getOption("plugin");
            OptionMapping key = e.getOption("key");
            OptionMapping value = e.getOption("value");
            if (pluginname == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid Plugin Name");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            if (key == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid key");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            if (value == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid value");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            PluginManager pluginManager = Bukkit.getPluginManager();
            Plugin pl = pluginManager.getPlugin(pluginname.getAsString());
            if(pl == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Plugin doesn't exist");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            if(value.getAsString().equalsIgnoreCase("true") || value.getAsString().equalsIgnoreCase("false")) {
                pl.getConfig().set(key.getAsString(), Boolean.parseBoolean(value.getAsString()));
            } else {
                pl.getConfig().set(key.getAsString(), value.getAsString());
            }
            pl.saveConfig();
            pl.reloadConfig();
            eb.setColor(Color.GREEN);
            eb.setTitle("Successfully set config");
            e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
        }
    }
}
