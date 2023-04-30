package de.tamion.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class EnableAll extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("enableall")) {
            e.deferReply().queue();
            if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    e.getHook().sendMessage("You aren't allowed to do that!").setEphemeral(true).queue();
                    return;
                }
            }
            PluginManager pluginManager = Bukkit.getPluginManager();
            for(Plugin pl : pluginManager.getPlugins()) {
                pluginManager.enablePlugin(pl);
            }
            e.getHook().sendMessage("All Plugins successfully enabled").setEphemeral(true).queue();
        }
    }
}
