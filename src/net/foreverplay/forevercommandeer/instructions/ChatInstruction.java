package net.foreverplay.forevercommandeer.instructions;

import net.foreverplay.forevercommandeer.ForeverCommandeer;
import net.foreverplay.forevercommandeer.contracts.Instruction;
import org.bukkit.entity.Player;

public class ChatInstruction extends Instruction
{
    public ChatInstruction(ForeverCommandeer plugin, String instruction)
    {
        super(plugin, instruction);
    }

    @Override
    public void execute(Player player)
    {
        player.chat(formatInstruction());
    }
}
