package de.tamion.discord;

import de.tamion.discord.commands.DownloadPlugin;
import de.tamion.discord.commands.Plugins;
import de.tamion.discord.commands.TogglePlugin;
import de.tamion.discord.commands.UploadPlugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class DiscordMain {
    public static JDA jda;
    public static <BufferedReader> void startup() {
        try {
            File tokenfl = new File("./plugins/PlugMan/token.txt");
            if(!tokenfl.exists()) {
                System.out.println("No Bot token set! To use Discord Please create the File Server/plugins/PlugMan/token.txt and paste your bot token in it.");
                return;
            }
            String token = FileUtils.readFileToString(tokenfl);
            jda = JDABuilder.createDefault(token)
                    .addEventListeners(new UploadPlugin())
                    .addEventListeners(new DownloadPlugin())
                    .addEventListeners(new Plugins())
                    .addEventListeners(new TogglePlugin())
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.watching("Minecraft Server"))
                    .build()
                    .awaitReady();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Bot Started");
        jda.upsertCommand("uploadplugin", "Uploads plugin onto server")
                .addOption(OptionType.ATTACHMENT, "file", "File to upload", true)
                .addOption(OptionType.STRING, "name", "Name the file shall have", true)
                .queue();
        jda.upsertCommand("downloadplugin", "downloads plugin onto server")
                .addOption(OptionType.STRING, "url", "File from URL to upload", true)
                .addOption(OptionType.STRING, "name", "Name the file shall have", true)
                .queue();
        jda.upsertCommand("plugins", "Lists Plugins")
                .queue();
        jda.upsertCommand("toggleplugin", "Toggles Plugin on or off")
                .addOption(OptionType.STRING, "name", "Name of the Plugin to toggle", true)
                .queue();
    }
    public static void shutdown() {
        if(jda != null) {
            jda.shutdown();
        } else {
            System.out.println("CANT STOP BOT! BOT NOT RUNNING!");
        }
    }
    public static void restart() {
        shutdown();
        startup();
    }
}
