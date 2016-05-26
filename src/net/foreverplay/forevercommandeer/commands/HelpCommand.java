package net.foreverplay.forevercommandeer.commands;

import java.util.Arrays;
import java.util.List;
import net.foreverplay.forevercommandeer.ForeverCommandeer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class HelpCommand extends CommandeerCommand
{
    public HelpCommand(ForeverCommandeer plugin)
    {
        super(plugin);
    }

    @Override
    public List<String> getTriggers()
    {
        return Arrays.asList("help");
    }
    
    @Override
    public String getPermission()
    {
        return "forevercommandeer.help";
    }

    @Override
    public List<String> getDescription()
    {
        return Arrays.asList("Displays a list of commands for ForeverCommandeer.");
    }

    @Override
    public boolean run(Player player, String[] args)
    {
        PluginDescriptionFile info = plugin.getDescription();

        plugin.getChat().sendMessage(player, "&3&l]&3&l&m--------&3&l[ &b%s &3v&7%s &3&l]&3&l&m--------&3&l[",
        info.getName(), info.getVersion()
        );

        boolean gotMessage = false;

        for (CommandeerCommand command : plugin.getCommand().getCommands()) {
            gotMessage = command.sendDescriptionMessage(player) ? true : gotMessage;
        }

        if (!gotMessage) {

        }

        return true;
    }
}
