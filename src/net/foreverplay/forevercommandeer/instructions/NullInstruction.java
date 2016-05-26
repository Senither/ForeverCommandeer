package net.foreverplay.forevercommandeer.instructions;

import net.foreverplay.forevercommandeer.ForeverCommandeer;
import net.foreverplay.forevercommandeer.contracts.Instruction;
import org.bukkit.entity.Player;

class NullInstruction extends Instruction
{
    public NullInstruction(ForeverCommandeer plugin, String instruction)
    {
        super(plugin, instruction);
    }

    @Override
    public void execute(Player player)
    {
        // This does nothing...
    }
}
