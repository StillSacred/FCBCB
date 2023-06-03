package model.animals;

public class Command {
    private String commandName;

    public Command(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    @Override
    public String toString() {
        return commandName;
    }
}