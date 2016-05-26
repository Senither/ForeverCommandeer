package net.foreverplay.forevercommandeer.instructions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.foreverplay.forevercommandeer.ForeverCommandeer;
import net.foreverplay.forevercommandeer.contracts.Instruction;

public enum InstructionType
{
    COMMAND("command", CommandInstruction.class),
    MESSAGE("message", MessageInstruction.class),
    CHAT("chat", ChatInstruction.class);

    private final String name;
    private final Object instance;

    private static final Map<String, InstructionType> types = new HashMap<>();

    static {
        for (InstructionType type : values()) {
            types.put(type.getName(), type);
        }
    }

    private InstructionType(String name, Object instance)
    {
        this.name = name;
        this.instance = instance;
    }

    public String getName()
    {
        return name;
    }

    public <T> Class<T> getInstance()
    {
        return (Class<T>) instance;
    }

    public Instruction createNew(ForeverCommandeer plugin, String instruction)
    {
        try {
            Constructor con = getInstance().getDeclaredConstructor(ForeverCommandeer.class, String.class);

            con.setAccessible(true);

            return (Instruction) con.newInstance(plugin, instruction);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(InstructionType.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new NullInstruction(plugin, instruction);
    }

    public static InstructionType fromName(String name)
    {
        if (types.containsKey(name.toLowerCase())) {
            return types.get(name.toLowerCase());
        }

        return null;
    }
}
