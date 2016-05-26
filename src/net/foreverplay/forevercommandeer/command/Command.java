package net.foreverplay.forevercommandeer.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.foreverplay.forevercommandeer.contracts.Instruction;
import org.bukkit.entity.Player;

public class Command
{
    private final String command;
    private final Set<String> triggers = new HashSet<>();
    private final List<Instruction> instructions = new ArrayList<>();

    public Command(String command)
    {
        this.command = command.toLowerCase();

        this.triggers.add(this.command);
    }

    public boolean addTrigger(String trigger)
    {
        return this.triggers.add(trigger.toLowerCase());
    }

    public void addInstruction(Instruction instruction)
    {
        this.instructions.add(instruction);
    }

    public boolean match(String command)
    {
        return triggers.contains(command.toLowerCase());
    }

    public String getCommand()
    {
        return command;
    }

    public Set<String> getTriggers()
    {
        return triggers;
    }

    public List<Instruction> getInstructions()
    {
        return instructions;
    }

    public void run(Player player)
    {
        getInstructions().stream().forEach(( instruction ) -> {
            instruction.execute(player);
        });
    }
}
