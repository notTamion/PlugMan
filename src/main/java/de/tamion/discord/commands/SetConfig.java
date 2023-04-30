package de.tamion.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class SetConfig extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getName().equals("setconfig")) {
            e.deferReply().queue();
            if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!e.getMember().getRoles().containsAll(e.getGuild().getRolesByName("PluginPerms", true)) || e.getGuild().getRolesByName("PluginPerms", true).isEmpty()) {
                    e.getHook().sendMessage("You aren't allowed to do that!").setEphemeral(true).queue();
                    return;
                }
            }
            OptionMapping pluginname = e.getOption("plugin");
            OptionMapping key = e.getOption("key");
            OptionMapping value = e.getOption("value");
            if (pluginname == null) {
                e.getHook().sendMessage("Please provide a valid Plugin Name").queue();
                return;
            }
            if (key == null) {
                e.getHook().sendMessage("Please provide a valid key").queue();
                return;
            }
            if (value == null) {
                e.getHook().sendMessage("Please provide a valid value").queue();
                return;
            }
            PluginManager pluginManager = Bukkit.getPluginManager();
            Plugin pl = pluginManager.getPlugin(pluginname.getAsString());
            if(pl == null) {
                e.getHook().sendMessage("Plugin doesn't exist").setEphemeral(true).queue();
                return;
            }
            if(value.getAsString().equalsIgnoreCase("true") || value.getAsString().equalsIgnoreCase("false")) {
                pl.getConfig().set(key.getAsString(), Boolean.parseBoolean(value.getAsString()));
            } else {
                pl.getConfig().set(key.getAsString(), value.getAsString());
            }
            pl.saveConfig();
            pl.reloadConfig();
            e.getHook().sendMessage("Successfully set config").setEphemeral(true).queue();
        }
    }
}
