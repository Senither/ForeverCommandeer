package net.foreverplay.forevercommandeer.contracts;

import net.foreverplay.forevercommandeer.ForeverCommandeer;
import org.bukkit.entity.Player;

public abstract class Instruction
{

    protected final ForeverCommandeer plugin;
    protected final String instruction;

    public Instruction(ForeverCommandeer plugin, String instruction)
    {
        this.plugin = plugin;
        this.instruction = instruction;
    }

    public final String getInstruction()
    {
        return formatInstruction();
    }

    protected String formatInstruction()
    {
        return instruction.trim();
    }

    public abstract void execute(Player player);
}
