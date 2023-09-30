package de.tamion.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;

public class DownloadPlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("downloadplugin")) {
            e.deferReply().queue();
            if(!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    e.getHook().sendMessage("You aren't allowed to do that!").setEphemeral(true).queue();
                    return;
                }
            }
            OptionMapping urls = e.getOption("url");
            OptionMapping name = e.getOption("name");
            if(urls == null) {
                e.getHook().sendMessage("Please provide a valid URL").queue();
                return;
            }
            if(name == null) {
                e.getHook().sendMessage("Please provide a valid filename").queue();
                return;
            }
            try {
                URL url = new URL(urls.getAsString());
                File fl = new File("./plugins/" + name.getAsString());
                FileUtils.copyURLToFile(url, fl);
                e.getHook().sendMessage("Successfully downloaded Plugin").setEphemeral(true).queue();
            } catch (Exception ex) {
                e.getHook().sendMessage("Something went wrong! check console for error logs").setEphemeral(true).queue();
                ex.printStackTrace();
            }
        }
    }
}
