package net.foreverplay.forevercommandeer.instructions;

import net.foreverplay.forevercommandeer.ForeverCommandeer;
import net.foreverplay.forevercommandeer.contracts.Instruction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageInstruction extends Instruction
{
    public MessageInstruction(ForeverCommandeer plugin, String instruction)
    {
        super(plugin, instruction);
    }

    @Override
    protected String formatInstruction()
    {
        return ChatColor.translateAlternateColorCodes('&', instruction.trim());
    }

    @Override
    public void execute(Player player)
    {
        player.sendMessage(formatInstruction());
    }
}
