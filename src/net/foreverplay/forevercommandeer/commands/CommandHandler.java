package net.foreverplay.forevercommandeer.commands;

import java.util.ArrayList;
import java.util.List;
import net.foreverplay.forevercommandeer.ForeverCommandeer;
import net.foreverplay.forevercommandeer.utils.StringMatcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor, TabCompleter
{

    private final ForeverCommandeer plugin;
    private final List<CommandeerCommand> commands;

    private CommandeerCommand defaultCommand = null;

    public CommandHandler(ForeverCommandeer plugin)
    {
        this.plugin = plugin;

        commands = new ArrayList<>();
    }

    public void registerCommand(CommandeerCommand command)
    {
        registerCommand(command, false);
    }

    public void registerCommand(CommandeerCommand command, boolean defaultCommand)
    {
        if (defaultCommand) {
            this.defaultCommand = command;
        }

        if (commands.contains(command)) {
            return;
        }

        commands.add(command);
    }

    public List<CommandeerCommand> getCommands()
    {
        return commands;
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't use the /commandeer command in the console.");

            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (defaultCommand == null) {
                return false;
            }

            if (defaultCommand.getPermission() != null && !player.hasPermission(defaultCommand.getPermission())) {
                plugin.getChat().missingPermission(player, defaultCommand.getPermission());

                return false;
            }

            return defaultCommand.run(player, args);
        }

        String commandTrigger = args[0];

        String[] parm = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            parm[i - 1] = args[i];
        }

        for (CommandeerCommand command : commands) {
            for (String trigger : command.getTriggers()) {
                if (!trigger.equalsIgnoreCase(commandTrigger)) {
                    continue;
                }

                if (command.getPermission() != null && !player.hasPermission(command.getPermission())) {
                    plugin.getChat().missingPermission(player, command.getPermission());

                    return false;
                }

                return command.run(player, parm);
            }
        }

        List<String> commandTriggers = new ArrayList<>();

        this.commands.stream().filter(( command ) -> !(command.getTriggers().isEmpty())).forEach(( command ) -> {
            commandTriggers.add(command.getTriggers().get(0));
        });

        String match = StringMatcher.match(commandTrigger, commandTriggers).getMatch();

        plugin.getChat().sendMessage(player, "%s &4%s &cwas not found! Did you mean...",
        plugin.getPrefix('4', 'c'), commandTrigger
        );
        plugin.getChat().sendMessage(player, " &4/&cCommandeer &4[&c%s&4]", match);

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length > 1) {
            return null;
        }

        List<String> commandTriggers = new ArrayList<>();

        this.commands.stream().filter(( command ) -> !(command.getTriggers().isEmpty())).forEach(( command ) -> {
            commandTriggers.addAll(command.getTriggers());
        });

        if (args.length == 0) {
            return commandTriggers;
        }

        String command = args[0];
        List<String> match = new ArrayList<>();

        commandTriggers.stream().filter(( trigger ) -> (trigger.startsWith(command))).forEach(( trigger ) -> {
            match.add(trigger);
        });

        return match;
    }
}
