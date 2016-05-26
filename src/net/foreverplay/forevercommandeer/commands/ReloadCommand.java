package net.foreverplay.forevercommandeer.commands;

import java.util.Arrays;
import java.util.List;
import net.foreverplay.forevercommandeer.ForeverCommandeer;
import net.foreverplay.forevercommandeer.command.Command;
import org.bukkit.entity.Player;

public class ReloadCommand extends CommandeerCommand
{
    public ReloadCommand(ForeverCommandeer plugin)
    {
        super(plugin);
    }

    @Override
    public List<String> getTriggers()
    {
        return Arrays.asList("reload");
    }

    @Override
    public String getPermission()
    {
        return "forevercommandeer.reload";
    }

    @Override
    public List<String> getDescription()
    {
        return Arrays.asList("Reloads all the commands for ForeverCommandeer.");
    }

    @Override
    public boolean run(Player player, String[] args)
    {
        plugin.reloadConfig();
        plugin.getCommandeer().getCommands().clear();
        plugin.loadCommands();

        int commands = 0, triggers = 0, instructions = 0;
        for (Command command : plugin.getCommandeer().getCommands()) {
            commands++;

            triggers += command.getTriggers().size();
            instructions += command.getInstructions().size();
        }

        chat().sendMessage(player, "&2ForeverCommandeer &ahas been reloaded successfully!");
        chat().sendMessage(player, "&2" + commands + " &acommands with &2" + triggers + " &atriggers and &2" + instructions + " &ainstructions");
        chat().sendMessage(player, "&ahas been loaded!");

        return true;
    }
}
