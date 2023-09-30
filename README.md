# PlugMan
PlugMan is a Minecraft Paper Plugin that allows you to manage and download new plugins all in-game.
### Features
- Enable All Plugins: /enableall
- Disable All Plugins: /disableall
- Toggle Plugin on or off: /toggle [PluginName]
- Edit or Add Value to Config of other Plugin: /setconfig [PluginName] [Key] [Value]
- Download Plugin via URL: /downloadPlugin [URL] [FileName.jar]
- Reload Plugin: /reloadPlugin [Pluginname/*]
- View What Plugins you have Enabled/Disabled (Only Discord Bot): /plugins
- Upload locally stored plugins (Only Discord Bot): /uploadPlugin [File] [FileName.jar]
### Discord
To use a DiscordBot you have to
1. Create your Bot on the [Discord Developer Portal](https://discord.com/developers/applications)
2. Create the token.txt file in Server/plugins/PlugMan/
3. Paste the Bot Token into the Text file
4. Restart/Reload your Server

Currently, Only Users with the Administrator Permission or the Role "PluginPerms"(You have to create this one yourself, I recommend turning off all permissions and putting it at the bottom of the role list) can use Discord Commands.
### NOTE: Using a Discord Bot is Optional, you don't have to use one.
### Permissions
- PlugMan.deleteconfig: /deleteconfig
- PlugMan.disableall: /disableall
- PlugMan.download: /downloadplugin
- PlugMan.enableall: /enableall
- PlugMan.reload: /reloadplugin
- PlugMan.setconfig: /setconfig
- PlugMan.toggle: /toggleplugin
### Contributing
If you want to contribute to this Project, create a fork and open a pull request after you have made your changes!