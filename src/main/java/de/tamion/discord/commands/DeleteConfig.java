package de.tamion.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class DeleteConfig extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("deleteconfig")) {
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
            File dire = new File("./plugins/" + name.getAsString());
            if(!dire.exists()) {
                e.getHook().sendMessage("This Plugin doesn't have a configuration file").setEphemeral(true).queue();
                return;
            }
            if(!dire.isDirectory()) {
                e.getHook().sendMessage("Please enter the name of the plugin and not the name of the plugin.jar file").setEphemeral(true).queue();
                return;
            }
            try {
                FileUtils.deleteDirectory(dire);
                e.getHook().sendMessage("Configuration file successfully deleted").setEphemeral(true).queue();
            } catch (IOException ex) {
                e.getHook().sendMessage("Something went wrong! Check Console for Error Logs").setEphemeral(true).queue();
            }
        }
    }
}
