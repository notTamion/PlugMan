package de.tamion.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

public class UploadPlugin extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("uploadplugin")) {
            e.deferReply().queue();
            if(!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    e.getHook().sendMessage("You aren't allowed to do that!").setEphemeral(true).queue();
                    return;
                }
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
            e.getHook().sendMessage("Successfully uploaded Plugin").setEphemeral(true).queue();
        }
    }
}
