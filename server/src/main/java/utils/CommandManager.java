package utils;

import commands.Command;
import java.util.ArrayList;
import java.util.List;

public class CommandManager{
    private  List<Command> commands = new ArrayList<>();
    private Command helpCommand;
    private Command infoCommand;
    private Command showCommand;
    private Command addCommand;
    private Command updateCommand;
    private Command removeByIdCommand;
    private Command clearCommand;
    private Command saveCommand;
    private Command exitCommand;
    private Command executeScriptCommand;
    private Command addIfMinCommand;
    private Command addIfMaxCommand;
    private Command removeLowerCommand;
    private Command averageOfHealthCommand;
    private Command filterStartsWithNameCommand;
    private Command countGreaterThanMeleeWeaponCommand;

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand,
                          Command removeByIdCommand, Command clearCommand, Command saveCommand, Command exitCommand, Command executeScriptCommand,
                          Command addIfMinCommand, Command addIfMaxCommand, Command removeLowerCommand, Command averageOfHealthCommand,
                          Command filterStartsWithNameCommand, Command countGreaterThanMeleeWeaponCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.addIfMaxCommand = addIfMaxCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.averageOfHealthCommand = averageOfHealthCommand;
        this.filterStartsWithNameCommand = filterStartsWithNameCommand;
        this.countGreaterThanMeleeWeaponCommand = countGreaterThanMeleeWeaponCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(addIfMinCommand);
        commands.add(addIfMaxCommand);
        commands.add(removeLowerCommand);
        commands.add(averageOfHealthCommand);
        commands.add(filterStartsWithNameCommand);
        commands.add(countGreaterThanMeleeWeaponCommand);
    }


    public List<Command> getCommands() {
        return commands;
    }

    public boolean noSuchCommand(String command) {
        Console.println("Command '" + command + "' not found. Type 'help' for help.");
        return false;
    }
/*
    public boolean help(String argument) {
        if (helpCommand.execute(argument)) {
            for (Command command : commands) {
                Console.printtable(command.getName(), command.getDescription());
            }
            return true;
        } else return false;
    }

    public boolean info(String argument) {
        return infoCommand.execute(argument);
    }
    public boolean show(String argument) {
        return showCommand.execute(argument);
    }
    public boolean add(String argument) {
        return addCommand.execute(argument);
    }
    public boolean update(String argument) {
        return updateCommand.execute(argument);
    }
    public boolean removeById(String argument) {
        return removeByIdCommand.execute(argument);
    }
    public boolean clear(String argument) {
        return clearCommand.execute(argument);
    }

    public boolean save(String argument) {
        return saveCommand.execute(argument);
    }

    public boolean exit(String argument) {
        return exitCommand.execute(argument);
    }

    public boolean executeScript(String argument) {
        return executeScriptCommand.execute(argument);
    }

    public boolean addIfMin(String argument) {
        return addIfMinCommand.execute(argument);
    }
    public boolean addIfMax(String argument) {
        return addIfMaxCommand.execute(argument);
    }
    public boolean removeLower(String argument) {return removeLowerCommand.execute(argument);}
    public boolean avgOfHealth(String argument) {return averageOfHealthCommand.execute(argument);}
    public boolean filterName(String argument) {return filterStartsWithNameCommand.execute(argument);}
    public boolean countMelee(String argument) {return countGreaterThanMeleeWeaponCommand.execute(argument);}
*/

    @Override
    public String toString() {
        return "CommandManager (helper class for working with commands)";
    }
}