Forever Commandeer
==================

Forever Commandeer is a simple plugin that allows you to create custom commands, or aliases for existing commands directly from the configuration.

## Syntax

When Forever Commandeer is first installed, three default commands will be generated for you, their syntax looks something likes this.

```
commands:
    test-help:
        trigger: '/test-command'
        aliases:
            - '/test'
            - '//test'
        instructions:
            - 'message |> &2[&aTest Command &2: &aHelp Menu&2]'
            - 'message |> &2/&atest-command hello'
            - 'message |> &2/&atest-command op'
```

All commands **must** be placed under the *commands:* tag for it to be loaded, from there you add a command index, in the above example that's the *'test-help:'* bit, and after that you can actually setup the command you want.
There is a total of three fields you can use, two of which are required, fields with a start(*) in front of them are required.

* ***trigger:** 
* * Type: string
* * Description: The main command that should be looked for.
* **aliases:**
* * Type: string list
* * Description: Any command aliases for the main command that should also trigger the instructions.
* ***instructions:** 
* * Type: string list
* * Description: The command instructions that should be executed when the trigger, or an aliases is used by a player.

The command instructions has a bit of a special format which is what we will cover next.

## Instructions

The instructions tag is the powerhouse of the command, allowing you to send custom messages, chat for the player, or make the player execute more commands, however the format might be a bit weird so here is how it works...
All instructions are formated in two parts, with the character set of **|>** being the delimiter, the syntax looks something like this.

```
message |> This is a message sent to the player
command |> op Notch
```

In the example above you can see two instructions being declared, one with a **message** action, and one with a **command** action, after defining the action the delimiter is used(**|>**), and the message or command that should be used in the instruction is then placed at the end.

### Instruction Actions

The following instruction actions are avaliable.

* **Message:** Sends a message to the player.
* **Command:** Runs a command as the player.
* **Chat:** Sends a chat message as the player.