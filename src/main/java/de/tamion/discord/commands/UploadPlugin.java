package de.tamion.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class UploadPlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("uploadplugin")) {
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
            OptionMapping file = e.getOption("file");
            OptionMapping name = e.getOption("name");
            if(file == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid file");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            if(name == null) {
                eb.setColor(Color.RED);
                eb.setTitle("Please provide a valid filename");
                e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
                return;
            }
            file.getAsAttachment().downloadToFile("./plugins/" + name.getAsString());
            eb.setColor(Color.GREEN);
            eb.setTitle("Successfully uploaded Plugin");
            e.getHook().sendMessageEmbeds(eb.build()).setEphemeral(true).queue();
        }
    }
}
