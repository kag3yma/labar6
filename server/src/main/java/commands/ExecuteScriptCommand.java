package commands;

import exceptions.ElementAmountException;
import exceptions.RecursionException;
import exceptions.WrongAmountOfElementsException;
import requests.Request;
import utils.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExecuteScriptCommand extends AbstractCommand {
    private HashMap<String, Command> map;
    private List<String> prevScripts;
    public ExecuteScriptCommand(HashMap<String, Command> map) {
        super("execute_script", "read and execute a script from the specified file");
        this.map = map;
        prevScripts= new ArrayList<String>();
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }

    @Override
    public void execute(Request request){
        if(argCheck(request.getArguments())){
            try (BufferedReader reader = new BufferedReader(new FileReader(request.getArguments()))) {
                String command;

                while ((command = reader.readLine()) != null) {
                    command += " placeholderArg";
                    String[] tokens = command.split("\\s+");
                    command = tokens[0];
                    String argument = tokens[1];
                    Request buffRequest = new Request(command, argument, null);
                    if(!command.equals("execute_script")){
                        Console.println("Command execution: " + command);
                        if(map.containsKey(command)){
                            map.get(command).execute(buffRequest);
                        }
                    } else {
                        if(prevScripts.contains(argument)){
                            throw new RecursionException();
                        } else {
                            prevScripts.add(argument);
                            Console.println("Command execution: " + command);
                            if(map.containsKey(command)){
                                map.get(command).execute(buffRequest);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                Console.printerror("Error while executing the script: " + request.getArguments());
            } catch (RecursionException re){
                Console.printerror("A script or chain of scripts cannot execute itself.");
            }
        }
        prevScripts.clear();
    }
}