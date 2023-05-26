package commands;


import exceptions.ElementAmountException;
import exceptions.RecursionException;
import network.TCPClient;
import utils.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * Класс отвечающий за команду execute_script {script-name}
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private HashMap<String, String> map;
    private TCPClient client;
    public ExecuteScriptCommand(HashMap<String, String> map, TCPClient client) {
        super("execute_script", "execute script from specified file");
        this.map = map;
        this.client = client;
    }
    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
        } catch (ElementAmountException e) {
            Console.printerror("Wrong number of arguments");
            return false;
        }
        try{
            BufferedReader reader = new BufferedReader(new FileReader(arg));
            String command;
            while ((command = reader.readLine()) != null) {
                command += " placeholderArg";
                String[] tokens = command.split("\\s+");
                String argument = tokens[1];
                if(argument.equals(arg)){
                    throw new RecursionException();
                }
            }
        } catch (Exception re){
            Console.printerror("Very bad recursion, i am sad about it((");
            return false;
        }
        return true;
    }
    @Override
    public void execute(String arg){
        if(argCheck(arg)){
            try (BufferedReader reader = new BufferedReader(new FileReader(arg))) {
                String command;

                while ((command = reader.readLine()) != null) {
                    command += " placeholderArg";
                    String[] tokens = command.split("\\s+");
                    String commandName = tokens[0];
                    if(map.containsKey(commandName)){
                        try {
                            client.sendRequest(command);
                        } catch (InterruptedException ie){
                            Console.println(ie.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                Console.println(e.getMessage());
            }
        }
    }
}