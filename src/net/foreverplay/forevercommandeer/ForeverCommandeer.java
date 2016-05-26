package net.foreverplay.forevercommandeer;

import java.util.logging.Level;
import net.foreverplay.forevercommandeer.command.Command;
import net.foreverplay.forevercommandeer.command.Commandeer;
import net.foreverplay.forevercommandeer.commands.CommandHandler;
import net.foreverplay.forevercommandeer.commands.HelpCommand;
import net.foreverplay.forevercommandeer.commands.ReloadCommand;
import net.foreverplay.forevercommandeer.instructions.InstructionType;
import net.foreverplay.forevercommandeer.utils.ChatFormatter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ForeverCommandeer extends JavaPlugin implements Listener
{
    private final Commandeer commandeer = new Commandeer(this);
    private final CommandHandler command = new CommandHandler(this);

    private final ChatFormatter chat = new ChatFormatter();
    private final String prefix = "&%s[&%sF-CMD%s]";

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        loadCommands();

        command.registerCommand(new HelpCommand(this), true);
        command.registerCommand(new ReloadCommand(this));

        getCommand("commandeer").setExecutor(command);
        getCommand("commandeer").setTabCompleter(command);

        getServer().getPluginManager().registerEvents(this, this);
    }

    public ChatFormatter getChat()
    {
        return chat;
    }

    public CommandHandler getCommand()
    {
        return command;
    }

    public Commandeer getCommandeer()
    {
        return commandeer;
    }

    public String getPrefix(char dark, char light)
    {
        return String.format(prefix, dark, light, dark);
    }

    public void loadCommands()
    {
        for (String index : getConfig().getConfigurationSection("commands").getKeys(false)) {
            if (!getConfig().contains("commands." + index + ".trigger") || !getConfig().contains("commands." + index + ".instructions")) {
                getLogger().log(Level.WARNING, "Failed to load command: {0}", index);
                getLogger().warning("The command are missing required fields: trigger, instructions");
                continue;
            }

            Command customCommand = new Command(getConfig().getString("commands." + index + ".trigger").toLowerCase());

            if (getConfig().contains("commands." + index + ".aliases")) {
                getConfig().getStringList("commands." + index + ".aliases").stream().forEach(( cmd ) -> {
                    customCommand.addTrigger(cmd.toLowerCase());
                });
            }

            for (String ins : getConfig().getStringList("commands." + index + ".instructions")) {
                int split = ins.indexOf("|>");

                InstructionType type = InstructionType.fromName(ins.substring(0, split).trim());

                if (type == null) {
                    getLogger().log(Level.WARNING, "Invalid instruction type for command: {0}", customCommand.getCommand());
                    getLogger().log(Level.WARNING, "Instruction: {0}", ins);
                    continue;
                }

                customCommand.addInstruction(type.createNew(this, ins.substring(split + 2, ins.length())));
            }

            commandeer.addCommand(customCommand);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        if (commandeer.run(e.getPlayer(), e.getMessage())) {
            e.setCancelled(true);
        }
    }
}
