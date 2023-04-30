package de.tamion.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

public class DownloadPlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("downloadplugin")) {
            e.deferReply().queue();
            if(!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                e.getHook().sendMessage("You aren't allowed to upload Plugins!").setEphemeral(true).queue();
                return;
            }
            OptionMapping file = e.getOption("file");
            OptionMapping name = e.getOption("name");
            if(file == null) {
                e.getHook().sendMessage("Please provide a valid file").queue();
                return;
            }
            if(name == null) {
                e.getHook().sendMessage("Please provide a valid filename").queue();
                return;
            }
            file.getAsAttachment().downloadToFile("./plugins/" + name.getAsString());
            e.getHook().sendMessage("Successfully downloaded Plugin").setEphemeral(true).queue();
        }
    }
}
