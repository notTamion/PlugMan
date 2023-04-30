package de.tamion.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.util.ArrayList;

public class Plugins extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("plugins")) {
            e.deferReply().queue();
            if(!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    e.getHook().sendMessage("You aren't allowed to do that!").setEphemeral(true).queue();
                    return;
                }
            }
            PluginManager pluginManager = Bukkit.getPluginManager();
            ArrayList<MessageEmbed> embeds = new ArrayList<>();
            for(Plugin pl : pluginManager.getPlugins()) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(pl.getName());
                if(pl.isEnabled()) {
                    eb.setColor(Color.GREEN);
                } else {
                    eb.setColor(Color.RED);
                }
                embeds.add(eb.build());
            }
            e.getHook().sendMessageEmbeds(embeds).setEphemeral(true).queue();
        }
    }
}
