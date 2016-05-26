package net.foreverplay.forevercommandeer.commands;

import java.util.ArrayList;
import java.util.List;
import net.foreverplay.forevercommandeer.ForeverCommandeer;
import net.foreverplay.forevercommandeer.utils.ChatFormatter;
import org.bukkit.entity.Player;

public abstract class CommandeerCommand
{
    protected final ForeverCommandeer plugin;

    public CommandeerCommand(ForeverCommandeer plugin)
    {
        this.plugin = plugin;
    }

    public String getPermission()
    {
        return null;
    }

    public List<String> getTriggers()
    {
        return new ArrayList<>();
    }

    public List<String> getDescription()
    {
        return new ArrayList<>();
    }

    public List<String> getParameters()
    {
        return new ArrayList<>();
    }

    protected boolean sendDescriptionMessage(Player player)
    {
        return sendDescriptionMessage(player, true);
    }

    protected boolean sendDescriptionMessage(Player player, boolean sendDescription)
    {
        if (getTriggers().isEmpty()) {
            return false;
        }

        if (getPermission() != null && !player.hasPermission(getPermission())) {
            return false;
        }

        String command = String.format("&3/&bCommandeer %s ", getTriggers().get(0));

        if (!getParameters().isEmpty()) {
            command = getParameters().stream().map(( parameter )
            -> String.format("&3[&b%s&3] ", parameter)
            ).reduce(command, String::concat);
        }

        plugin.getChat().sendMessage(player, command.trim());

        if (sendDescription) {
            getDescription().stream().forEach(( line ) -> {
                plugin.getChat().sendMessage(player, " &3&l&m*&7 " + line);
            });
        }

        return true;
    }

    protected void sendMessage(Player player, String message)
    {
        chat().sendMessage(player, "%s &b%s", plugin.getPrefix('3', 'b'), message);
    }

    protected void sendErrorMessage(Player player, String message)
    {
        chat().sendMessage(player, "%s &c%s", plugin.getPrefix('4', 'c'), message);
    }

    protected ChatFormatter chat()
    {
        return plugin.getChat();
    }

    public abstract boolean run(Player player, String[] args);
}
