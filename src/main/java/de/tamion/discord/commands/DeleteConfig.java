package de.tamion.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DeleteConfig extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("deleteconfig")) {
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
            File dire = new File("./plugins/" + name.getAsString());
            if(!dire.exists()) {
                eb.setColor(Color.RED);
                eb.setTitle("This Plugin doesn't have a configuration file");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            if(!dire.isDirectory()) {
                eb.setColor(Color.RED);
                eb.setTitle("Please enter the name of the plugin and not the name of the plugin.jar file");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            try {
                FileUtils.deleteDirectory(dire);
                eb.setColor(Color.GREEN);
                eb.setTitle("Configuration file successfully deleted");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
            } catch (IOException ex) {
                eb.setColor(Color.RED);
                eb.setTitle("Something went wrong! Check Console for Error Logs");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
            }
        }
    }
}
