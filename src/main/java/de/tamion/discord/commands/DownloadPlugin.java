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
import java.net.URL;

public class DownloadPlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("downloadplugin")) {
            e.deferReply().queue();
            EmbedBuilder eb = new EmbedBuilder();
            if(!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    eb.setColor(Color.RED);
                    eb.setTitle("You aren't allowed to do that!");
                    e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                    return;
                }
            }
            OptionMapping urls = e.getOption("url");
            OptionMapping name = e.getOption("name");
            if(urls == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid URL");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            if(name == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid filename");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            try {
                String uri = urls.getAsString();
                if(urls.getAsString().startsWith("https://www.spigotmc.org/resources/")) {
                    String[] uria = urls.getAsString().replaceAll("https://www.spigotmc.org/resources/", "").split("\\.");
                    uri = "https://api.spiget.org/v2/resources/" + uria[uria.length-1].replaceAll("/", "") + "/download";
                }
                URL url = new URL(uri);
                File fl = new File("./plugins/" + name.getAsString());
                FileUtils.copyURLToFile(url, fl);
                eb.setColor(Color.GREEN);
                eb.setTitle("Successfully downloaded Plugin");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
            } catch (Exception ex) {
                eb.setColor(Color.RED);
                eb.setTitle("Something went wrong! check console for error logs");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                ex.printStackTrace();
            }
        }
    }
}
