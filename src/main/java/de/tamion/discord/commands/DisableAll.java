package de.tamion.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DisableAll extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("disableall")) {
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
            PluginManager pluginManager = Bukkit.getPluginManager();
            for(Plugin pl : pluginManager.getPlugins()) {
                if(!pl.getName().equals("PlugMan")) {
                    pluginManager.disablePlugin(pl);
                }
            }
            eb.setColor(Color.GREEN);
            eb.setTitle("All Plugins successfully disabled");
            e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
        }
    }
}
