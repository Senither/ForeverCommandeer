package net.foreverplay.forevercommandeer.command;

import java.util.HashSet;
import java.util.Set;
import net.foreverplay.forevercommandeer.ForeverCommandeer;
import org.bukkit.entity.Player;

public class Commandeer
{
    private final ForeverCommandeer plugin;

    private final Set<Command> commands = new HashSet<>();

    public Commandeer(ForeverCommandeer plugin)
    {
        this.plugin = plugin;
    }

    public boolean addCommand(Command command)
    {
        return commands.add(command);
    }

    public Set<Command> getCommands()
    {
        return commands;
    }

    public boolean run(Player player, String message)
    {
        String command = message.trim().toLowerCase();

        for (Command cmd : commands) {
            if (cmd.match(command)) {
                cmd.run(player);

                return true;
            }
        }

        return false;
    }
}
