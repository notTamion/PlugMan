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

public class TogglePlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("toggleplugin")) {
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
            if(name == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid name");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            PluginManager pluginManager = Bukkit.getPluginManager();
            Plugin plugin = pluginManager.getPlugin(name.getAsString());
            if(plugin == null || plugin.getName().equals("PlugMan")) {
                eb.setColor(Color.RED);
                eb.setTitle("Plugin doesn't exist");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            if(plugin.isEnabled()) {
                pluginManager.disablePlugin(plugin);
                eb.setColor(Color.GREEN);
                eb.setTitle(plugin.getName() + " has been successfully disabled");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
            } else {
                pluginManager.enablePlugin(plugin);
                eb.setColor(Color.GREEN);
                eb.setTitle(plugin.getName() + " has been successfully enabled");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
            }
        }
    }
}
